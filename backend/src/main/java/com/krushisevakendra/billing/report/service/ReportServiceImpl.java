package com.krushisevakendra.billing.report.service;

import com.krushisevakendra.billing.product.entity.Product;
import com.krushisevakendra.billing.product.repository.ProductRepository;
import com.krushisevakendra.billing.purchase.entity.Purchase;
import com.krushisevakendra.billing.purchase.repository.PurchaseRepository;
import com.krushisevakendra.billing.report.dto.*;
import com.krushisevakendra.billing.sale.entity.Sale;
import com.krushisevakendra.billing.sale.repository.SaleItemRepository;
import com.krushisevakendra.billing.sale.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{

    private final SaleRepository saleRepository;
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final SaleItemRepository saleItemRepository;

    @Override
    @Transactional(readOnly = true)
    public SalesReportDto getSaleReport(LocalDate fromDate, LocalDate toDate) {

        //Validate Dates
        if(fromDate.isAfter(toDate)){
            throw new IllegalArgumentException("From date cannot be after To date");
        }

        //Fetch Sales
        List<Sale> sales = saleRepository.findByActiveTrueAndSaleDateBetweenOrderBySaleDateAsc(
                fromDate,
                toDate

        );

        //Calculate Total Sales
        BigDecimal totalSales =
                saleRepository.getTotalSalesBetween(
                        fromDate,
                        toDate
                );

        List<SalesReportItemDto> reportItems =
                sales.stream()
                        .map(sale ->
                                SalesReportItemDto.builder()
                                        .invoiceNumber(
                                                sale.getInvoiceNumber()
                                        )
                                        .customerName(
                                                sale.getCustomer().getName()
                                        )
                                        .saleDate(
                                                sale.getSaleDate()
                                        )
                                        .totalAmount(
                                                sale.getTotalAmount()
                                        )
                                        .build()
                        )
                        .toList();
        return SalesReportDto.builder()
                .fromDate(fromDate)
                .toDate(toDate)
                .totalInvoices((long) sales.size())
                .totalSales(totalSales)
                .sales(reportItems)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public PurchaseReportDto getPurchaseReport(LocalDate fromDate, LocalDate toDate) {

        if (fromDate.isAfter(toDate)) {
            throw new IllegalArgumentException(
                    "From date cannot be after To date."
            );
        }
        List<Purchase> purchases =
                purchaseRepository.findByActiveTrueAndPurchaseDateBetweenOrderByPurchaseDateAsc(
                        fromDate,
                        toDate
                );
        BigDecimal totalAmount =
                purchaseRepository.getTotalPurchaseAmountBetween(
                        fromDate,
                        toDate
                );
        List<PurchaseReportItemDto> reportItems =
                purchases.stream()
                        .map(purchase ->
                                PurchaseReportItemDto.builder()
                                        .purchaseNumber(purchase.getPurchaseNumber())
                                        .supplierName(purchase.getSupplier().getSupplierName())
                                        .purchaseDate(purchase.getPurchaseDate())
                                        .totalAmount(purchase.getTotalAmount())
                                        .build()
                        )
                        .toList();

        return PurchaseReportDto.builder()
                .fromDate(fromDate)
                .toDate(toDate)
                .totalPurchases((long) purchases.size())
                .totalAmount(totalAmount)
                .purchases(reportItems)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public StockReportDto getStockReport(){

        //Fetch All Products
        List<Product> products = productRepository.findByActiveTrueOrderByNameAsc();

        //Count Products
        long totalProducts = productRepository.countByActiveTrue();

        long lowStockProducts = productRepository.countByActiveTrueAndStockQuantityLessThanEqual(10);

        long outOfStockProducts = productRepository.countByActiveTrueAndStockQuantity(0);

        //Convert Entity → DTO
        List<StockReportItemDto> reportItems =
                products.stream()
                        .map(product ->
                                StockReportItemDto.builder()
                                        .productId(product.getId())
                                        .productName(product.getName())
                                        .categoryName(product.getCategory().getName())
                                        .stockQuantity(product.getStockQuantity())
                                        .unit(product.getUnit().name())
                                        .purchasePrice(product.getPurchasePrice())
                                        .sellingPrice(product.getSellingPrice())
                                        .build()
                        )
                        .toList();
        return StockReportDto.builder()
                .totalProducts(totalProducts)
                .lowStockProducts(lowStockProducts)
                .outOfStockProducts(outOfStockProducts)
                .products(reportItems)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ProfitReportDto getProfitReport(LocalDate fromDate, LocalDate toDate) {
        //Validate Dates
        if(fromDate.isAfter(toDate)){
            throw new IllegalArgumentException("From date cannot be after To date.");
        }

        //Fetch Total Sales
        BigDecimal totalSales = saleRepository.getTotalSales(fromDate,toDate);

        //Fetch Total Cost
        BigDecimal totalCosts = saleItemRepository.getTotalCost(fromDate,toDate);

        //Calculate Gross Profit
        BigDecimal grossProfit = totalSales.subtract(totalCosts);

        //Calculate Profit Percentage
        BigDecimal profitPercentage;
        if(totalSales.compareTo(BigDecimal.ZERO)==0){
            profitPercentage = BigDecimal.ZERO;
        }else {
            profitPercentage =  grossProfit
                    .multiply(BigDecimal.valueOf(100))
                    .divide(totalSales,2, RoundingMode.HALF_UP);
        }

        //Build DTO
        return ProfitReportDto.builder()
                .fromDate(fromDate)
                .toDate(toDate)
                .totalSales(totalSales)
                .totalCost(totalCosts)
                .grossProfit(grossProfit)
                .profitPercentage(profitPercentage)
                .build();

    }
    }



