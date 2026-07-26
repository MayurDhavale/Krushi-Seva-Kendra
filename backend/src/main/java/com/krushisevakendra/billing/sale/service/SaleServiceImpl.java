package com.krushisevakendra.billing.sale.service;

import com.krushisevakendra.billing.customer.entity.Customer;
import com.krushisevakendra.billing.customer.repository.CustomerRepository;
import com.krushisevakendra.billing.exception.ResourceNotFoundException;
import com.krushisevakendra.billing.product.entity.Product;
import com.krushisevakendra.billing.product.repository.ProductRepository;
import com.krushisevakendra.billing.sale.dto.SaleItemRequestDto;
import com.krushisevakendra.billing.sale.dto.SaleRequestDto;
import com.krushisevakendra.billing.sale.dto.SaleResponseDto;
import com.krushisevakendra.billing.sale.entity.Sale;
import com.krushisevakendra.billing.sale.entity.SaleItem;
import com.krushisevakendra.billing.sale.mapper.SaleMapper;
import com.krushisevakendra.billing.sale.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public SaleResponseDto createSale(SaleRequestDto request) {

        // Fetch customer from database
        Customer customer = getCustomer(request.getCustomerId());

        //Create Sale Entity
        Sale sale = createSaleEntity(request,customer);

        //Process sale items and update stock
        processSaleItems(request.getSaleItems(),sale);

        //Calculate Invoice total
        calculateTotals(sale);

        //Save Sale along with all sale items
        Sale savedSale = saleRepository.save(sale);

        //Convert Entity to response
        return saleMapper.toResponse(savedSale);

    }

    @Override
    @Transactional(readOnly = true)
    public SaleResponseDto getSaleById(Long saleId) {


        Sale sale = saleRepository
                .findByIdAndActiveTrue(saleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Sale not found with ID: " + saleId
                        ));

        return saleMapper.toResponse(sale);

    }

    @Override
    public Page<SaleResponseDto> getAllSales(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Sale> salePage = saleRepository.findAllByActiveTrue(pageable);

        return salePage.map(saleMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SaleResponseDto> searchSales(String keyword, int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page,size,sort);

        return saleRepository.findByActiveTrueAndInvoiceNumberContainingIgnoreCaseOrActiveTrueAndCustomer_NameContainingIgnoreCase(
                keyword,
                keyword,
                pageable
        ).map(saleMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SaleResponseDto> filterSalesByDate(
            LocalDate fromDate,
            LocalDate toDate,
            int page,
            int size,
            String sortBy,
            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return saleRepository
                .findBySaleDateBetween(fromDate, toDate, pageable)
                .map(saleMapper::toResponse);
    }

    @Override
    @jakarta.transaction.Transactional
    public void deleteSale(Long saleId) {

        //Fetch Active sale
        Sale sale = saleRepository.findByIdAndActiveTrue(saleId)
                .orElseThrow(()-> new ResourceNotFoundException("Sale not found with id : "+ saleId));

        //Restore product stock
        for(SaleItem saleItem : sale.getSaleItems()){

            Product product = saleItem.getProduct();

            product.setStockQuantity(
                    product.getStockQuantity()+saleItem.getQuantity()
            );
        }
        // Soft delete sale
        sale.setActive(false);

        saleRepository.save(sale);
    }

    //Generate Invoice number
    private String generateInvoiceNumber(){

        // Fetch the latest sale record
        Optional<Sale> lastsSale = saleRepository.findTopByOrderByIdDesc();

        //If this is the first sale
        if(lastsSale.isEmpty()){
            return "INV-000001";
        }

        //Get last Invoice Number
        String lastInvoiceNumber = lastsSale.get().getInvoiceNumber();

        //Validate Invoice format
        if(!lastInvoiceNumber.startsWith("INV-")){
            throw new IllegalArgumentException("Invalid invoice number format "+ lastInvoiceNumber);
        }

        try{

            //Extract numeric part
            int lastNumber = Integer.parseInt(lastInvoiceNumber.substring(4));
            //Generate next invoice number

            return String.format("INV-%06d",lastNumber+1);

        } catch (NumberFormatException ex) {
            throw new IllegalStateException("Invalid Invoice number format "+ lastInvoiceNumber);
        }
    }

    /**
     * Fetch active customer by ID.
     */
    private Customer getCustomer(Long customerId) {

        return customerRepository
                .findByIdAndActiveTrue(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with ID: " + customerId
                        ));
    }

    /**
     * Create Sale entity from request.
     */
    private Sale createSaleEntity(
            SaleRequestDto request,
            Customer customer
    ) {

        return Sale.builder()
                .invoiceNumber(generateInvoiceNumber())
                .customer(customer)
                .saleDate(request.getSaleDate())
                .discount(
                        request.getDiscount() == null
                                ? BigDecimal.ZERO
                                : request.getDiscount()
                )
                .remarks(request.getRemarks())
                .active(true)
                .build();
    }


    /**
     * Process all sale items.
     * - Validate product
     * - Check available stock
     * - Calculate GST
     * - Create SaleItem
     * - Deduct product stock
     */
    private void processSaleItems(
            List<SaleItemRequestDto> saleItems,
            Sale sale) {

        // Loop through each product in the request
        for (SaleItemRequestDto itemRequest : saleItems) {

            // Fetch product from database
            Product product = productRepository
                    .findByIdAndActiveTrue(itemRequest.getProductId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Product not found with ID: "
                                            + itemRequest.getProductId()
                            ));

            // ================================
            // Validate available stock
            // ================================
            if (product.getStockQuantity() < itemRequest.getQuantity()) {
                throw new IllegalArgumentException(
                        "Insufficient stock for product: "
                                + product.getName()
                );
            }

            // ================================
            // Calculate line amount
            // Quantity × Selling Price
            // ================================
            BigDecimal lineAmount = product.getSellingPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            // ================================
            // Calculate GST
            // ================================
            BigDecimal taxAmount = lineAmount
                    .multiply(product.getGstRate())
                    .divide(BigDecimal.valueOf(100),
                            2,
                            RoundingMode.HALF_UP);

            // ================================
            // Total Price
            // ================================
            BigDecimal totalPrice = lineAmount.add(taxAmount);

            // ================================
            // Create SaleItem
            // ================================
            SaleItem saleItem = SaleItem.builder()
                    .sale(sale)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .unit(product.getUnit())
                    .sellingPrice(product.getSellingPrice())
                    .gstRate(product.getGstRate())
                    .taxAmount(taxAmount)
                    .totalPrice(totalPrice)
                    .build();

            // Add item to sale
            sale.getSaleItems().add(saleItem);

            // ================================
            // Deduct stock
            // ================================
            product.setStockQuantity(
                    product.getStockQuantity()
                            - itemRequest.getQuantity()
            );
        }
    }

    /**
     * Calculate invoice totals.
     * Subtotal = Sum of all line amounts
     * Total Tax = Sum of all GST amounts
     * Grand Total = Subtotal + GST - Discount
     */
    private void calculateTotals(Sale sale) {

        // Calculate subtotal
        BigDecimal subtotal = sale.getSaleItems()
                .stream()
                .map(item ->
                        item.getSellingPrice()
                                .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate total GST
        BigDecimal totalTaxAmount = sale.getSaleItems()
                .stream()
                .map(SaleItem::getTaxAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate grand total
        BigDecimal totalAmount = subtotal
                .add(totalTaxAmount)
                .subtract(
                        sale.getDiscount() == null
                                ? BigDecimal.ZERO
                                : sale.getDiscount()
                );

        // Set calculated values
        sale.setSubtotal(subtotal);
        sale.setTotalTaxAmount(totalTaxAmount);
        sale.setTotalAmount(totalAmount);
    }
}
