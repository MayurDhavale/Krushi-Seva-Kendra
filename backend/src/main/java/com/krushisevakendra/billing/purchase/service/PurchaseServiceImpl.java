package com.krushisevakendra.billing.purchase.service;

import com.krushisevakendra.billing.exception.ResourceNotFoundException;
import com.krushisevakendra.billing.product.entity.Product;
import com.krushisevakendra.billing.product.repository.ProductRepository;
import com.krushisevakendra.billing.purchase.dto.PurchaseItemRequestDto;
import com.krushisevakendra.billing.purchase.dto.PurchaseRequestDto;
import com.krushisevakendra.billing.purchase.dto.PurchaseResponseDto;
import com.krushisevakendra.billing.purchase.entity.Purchase;
import com.krushisevakendra.billing.purchase.entity.PurchaseItem;
import com.krushisevakendra.billing.purchase.mapper.PurchaseMapper;
import com.krushisevakendra.billing.purchase.repository.PurchaseItemRepository;
import com.krushisevakendra.billing.purchase.repository.PurchaseRepository;
import com.krushisevakendra.billing.supplier.entity.Supplier;
import com.krushisevakendra.billing.supplier.repository.SupplierRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final ProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;
    private final SupplierRepository supplierRepository;
    private final PurchaseMapper purchaseMapper;


//    @Override
//    public PurchaseResponseDto createPurchase(PurchaseRequestDto request) {
//
//        //load the supplier from the database.
//        Supplier supplier = supplierRepository.findByIdAndActiveTrue(request.getSupplierId())
//                .orElseThrow(()-> new ResourceNotFoundException("Supplier not found with id : "+ request.getSupplierId()));
//
//        //Create Purchase Object
//        Purchase purchase = Purchase.builder()
//                .supplier(supplier)
//                .purchaseDate(request.getPurchaseDate())
//                .discount(request.getDiscount())
//                .remarks(request.getRemarks())
//                .active(true)
//                .build();
//        //Save Once
//        purchase = purchaseRepository.save(purchase);
//
//        //Generate Purchase Number
//        purchase.setPurchaseNumber(generatePurchaseNumber(purchase.getId()));
//
//        //Save Again
//        purchase = purchaseRepository.save(purchase);
//
//        // Initialize totals
//        BigDecimal subtotal = BigDecimal.ZERO;
//        BigDecimal totalTaxAmount = BigDecimal.ZERO;
//
//        // Process each purchase item
//        for(PurchaseItemRequestDto itemRequest : request.getPurchaseItems()){
//
//            //Find Product
//            Product product = productRepository.findByIdAndActiveTrue(itemRequest.getProductId())
//                    .orElseThrow(()-> new ResourceNotFoundException("Product not found with id : "+
//                            itemRequest.getProductId()));
//
//            // Calculate Line Amount (Quantity × Purchase Price)
//            BigDecimal lineAmount = itemRequest.getPurchasePrice()
//                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
//
//            // Calculate GST Amount
//            BigDecimal taxAmount = lineAmount
//                    .multiply(product.getGstRate())
//                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
//
//            // Calculate Total Price (Including GST)
//            BigDecimal totalPrice = lineAmount.add(taxAmount);
//
//            // Create Purchase Item
//            PurchaseItem purchaseItem = PurchaseItem.builder()
//                    .purchase(purchase)
//                    .product(product)
//                    .quantity(itemRequest.getQuantity())
//                    .unit(product.getUnit())
//                    .purchasePrice(itemRequest.getPurchasePrice())
//                    .gstRate(product.getGstRate())
//                    .taxAmount(taxAmount)
//                    .totalPrice(totalPrice)
//                    .build();
//
//            // Add Purchase Item to Purchase
//            purchase.getPurchaseItems().add(purchaseItem);
//
//            // Update Product Stock
//            product.setStockQuantity(
//                    product.getStockQuantity() + itemRequest.getQuantity()
//            );
//
//            productRepository.save(product);
//
//            // Update Running Totals
//            subtotal= subtotal.add(lineAmount);
//            totalTaxAmount= totalTaxAmount.add(taxAmount);
//        }
//
//        //Grand Total = Subtotal + Total GST - Discount
//        BigDecimal totalAmount = subtotal.add(totalTaxAmount).subtract(request.getDiscount());
//
//        //Update Purchase Entity
//        purchase.setSubtotal(subtotal);
//        purchase.setTotalTaxAmount(totalTaxAmount);
//        purchase.setTotalAmount(totalAmount);
//
//        //Save Purchase  Save all purchase_items automatically because of CascadeType.ALL
//        purchase = purchaseRepository.save(purchase);
//
//        return purchaseMapper.toResponse(purchase);
//    }

    @Override
    public PurchaseResponseDto createPurchase(PurchaseRequestDto request){

        Supplier supplier = getSupplier(request.getSupplierId());

        Purchase purchase = createPurchaseEntity(request, supplier);

        processPurchaseItems(request.getPurchaseItems(), purchase);

        calculateTotals(purchase);

        Purchase savedPurchase = purchaseRepository.save(purchase);

        return purchaseMapper.toResponse(savedPurchase);

    }

    @Override
    @Transactional(readOnly = true)
    public PurchaseResponseDto getPurchaseById(Long id) {

        Purchase purchase = purchaseRepository.findByIdAndActiveTrue(id)
                .orElseThrow(()-> new ResourceNotFoundException("Purchase not found with id : "+ id));
        return purchaseMapper.toResponse(purchase);
    }

    @Override
    public Page<PurchaseResponseDto> getAllPurchase(Pageable pageable) {

        Page<Purchase> purchases = purchaseRepository.findAllByActiveTrue(pageable);

        return purchases.map(purchaseMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseResponseDto> searchPurchases(String keyword, Pageable pageable) {

        Page<Purchase> purchases = purchaseRepository.findByActiveTrueAndPurchaseNumberContainingIgnoreCaseOrActiveTrueAndSupplier_SupplierNameContainingIgnoreCase(
                keyword,
                keyword,
                pageable
        );
        return purchases.map(purchaseMapper::toResponse);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseResponseDto> filterPurchaseByDate(
            LocalDate fromDate,
            LocalDate toDate,
            Pageable pageable
    ){

        Page<Purchase> purchases = purchaseRepository.filterByPurchaseDateBetween(
                fromDate,
                toDate,
                pageable
        );

        return purchases.map(purchaseMapper::toResponse);
    }

    @Override
    public void deletePurchase(Long id){

        Purchase purchase = purchaseRepository.findByIdAndActiveTrue(id)
                .orElseThrow(()-> new ResourceNotFoundException(" Purchase not found with id :" + id));

        purchase.setActive(false);

        purchaseRepository.save(purchase);
    }


    //private String generatePurchaseNumber(Long id) {
     //   return String.format("PUR-%04d", id);
   // }

    private String generatePurchaseNumber() {

        Optional<Purchase> lastPurchase = purchaseRepository.findTopByOrderByIdDesc();

        if (lastPurchase.isEmpty()) {
            return "PUR-000001";
        }


        String lastPurchaseNumber = lastPurchase.get().getPurchaseNumber();

        if (!lastPurchaseNumber.startsWith("PUR-")) {
            throw new IllegalStateException(
                    "Invalid purchase number format: " + lastPurchaseNumber
            );
        }
        try {
            int lastNumber = Integer.parseInt(lastPurchaseNumber.substring(4));
            return String.format("PUR-%06d", lastNumber + 1);
        } catch (NumberFormatException ex) {
            throw new IllegalStateException(
                    "Invalid purchase number format: " + lastPurchaseNumber
            );
        }

        //int lastNumber = Integer.parseInt(lastPurchaseNumber.substring(4));

        //return String.format("PUR-%06d", lastNumber + 1);

    }

    //Get Supplier Find supplier.
    private Supplier getSupplier(@NotNull(message = "Supplier ID is required.") Long supplierId) {

        return supplierRepository
                .findByIdAndActiveTrue(supplierId)
                .orElseThrow(()-> new ResourceNotFoundException("Supplier not found with ID : " + supplierId));
    }
//

    //Create Purchase Entity
    private Purchase createPurchaseEntity(PurchaseRequestDto request, Supplier supplier) {

        return Purchase.builder()
                .purchaseNumber(generatePurchaseNumber())
                .supplier(supplier)
                .purchaseDate(request.getPurchaseDate())
                .discount(
                request.getDiscount() == null
                        ? BigDecimal.ZERO
                        : request.getDiscount()
        )
                .remarks(request.getRemarks())
                .active(true)
                .build();




    }

    //Process Purchase Items
    private void processPurchaseItems(List<PurchaseItemRequestDto> purchaseItems, Purchase purchase) {

        for(PurchaseItemRequestDto itemRequest : purchaseItems){

            // Fetch product from database
            Product product = productRepository.findByIdAndActiveTrue(itemRequest.getProductId())
                    .orElseThrow(()-> new ResourceNotFoundException("Product  Not found with id : "+ itemRequest.getProductId()));

            // Calculate line amount (Quantity × Purchase Price)
            BigDecimal lineAmount =  itemRequest.getPurchasePrice()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            // Calculate GST amount
            BigDecimal taxAmount = lineAmount
                    .multiply(product.getGstRate())
                    .divide(BigDecimal.valueOf(100),2,RoundingMode.HALF_UP);

            // Calculate total price (Including GST)
            BigDecimal totalPrice =  lineAmount.add(taxAmount);

            // Create PurchaseItem entity
            PurchaseItem purchaseItem = PurchaseItem.builder()
                    .purchase(purchase)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .unit(product.getUnit())
                    .purchasePrice(itemRequest.getPurchasePrice())
                    .gstRate(product.getGstRate())
                    .taxAmount(taxAmount)
                    .totalPrice(totalPrice)
                    .build();


            // Add purchase item to purchase
            purchase.getPurchaseItems().add(purchaseItem);

            // Update product stock
            product.setStockQuantity(
                    product.getStockQuantity()
                            + itemRequest.getQuantity()
            );
        }
    }


    //Calculate Total
    private void calculateTotals(Purchase purchase) {

        BigDecimal subtotal = purchase.getPurchaseItems()
                .stream()
                .map(item ->
                        item.getPurchasePrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        BigDecimal totalTaxAmount = purchase.getPurchaseItems()
                .stream()
                .map(PurchaseItem::getTaxAmount)
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        BigDecimal totalAmount = subtotal
                .add(totalTaxAmount)
                .subtract(purchase.getDiscount());

        purchase.setSubtotal(subtotal);
        purchase.setTotalTaxAmount(totalTaxAmount);
        purchase.setTotalAmount(totalAmount);
    }
}
