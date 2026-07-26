package com.krushisevakendra.billing.sale.controller;

import com.krushisevakendra.billing.common.response.ApiResponse;
import com.krushisevakendra.billing.sale.dto.SaleRequestDto;
import com.krushisevakendra.billing.sale.dto.SaleResponseDto;
import com.krushisevakendra.billing.sale.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<ApiResponse<SaleResponseDto>> createSale(
            @Valid @RequestBody SaleRequestDto request) {

        SaleResponseDto response = saleService.createSale(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Sale created successfully.",
                        response));
    }

    /**
     * Get Sale By ID
     */
    @GetMapping("/{saleId}")
    public ResponseEntity<ApiResponse<SaleResponseDto>> getSaleById(
            @PathVariable Long saleId) {

        SaleResponseDto response = saleService.getSaleById(saleId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Sale retrieved successfully.",
                        response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<SaleResponseDto>>> getAllSales(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "saleDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ){

        Page<SaleResponseDto> response = saleService.getAllSales(page, size, sortBy, sortDir);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Sales retrieved successfully.",
                        HttpStatus.OK.value(),
                        response
                )
        );
    }


    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<SaleResponseDto>>> searchSales(

            @RequestParam String keyword,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "saleDate") String sortBy,

            @RequestParam(defaultValue = "desc") String sortDir) {

        Page<SaleResponseDto> response = saleService.searchSales(
                keyword,
                page,
                size,
                sortBy,
                sortDir);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Sales retrieved successfully.",
                        response
                )
        );
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<Page<SaleResponseDto>>> filterSalesByDate(

            @RequestParam LocalDate fromDate,

            @RequestParam LocalDate toDate,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "saleDate") String sortBy,

            @RequestParam(defaultValue = "desc") String sortDir) {

        Page<SaleResponseDto> response = saleService.filterSalesByDate(
                fromDate,
                toDate,
                page,
                size,
                sortBy,
                sortDir);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Sales retrieved successfully.",
                        response
                )
        );
    }

    @DeleteMapping("/{saleId}")
    public ResponseEntity<ApiResponse<Void>> deleteSale(
            @PathVariable Long saleId) {

        saleService.deleteSale(saleId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Sale deleted successfully.",
                        null
                )
        );
    }

}

