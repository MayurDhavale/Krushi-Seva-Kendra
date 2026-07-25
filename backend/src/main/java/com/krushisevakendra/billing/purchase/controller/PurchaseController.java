package com.krushisevakendra.billing.purchase.controller;

import com.krushisevakendra.billing.common.response.ApiResponse;
import com.krushisevakendra.billing.purchase.dto.PurchaseRequestDto;
import com.krushisevakendra.billing.purchase.dto.PurchaseResponseDto;
import com.krushisevakendra.billing.purchase.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<ApiResponse<PurchaseResponseDto>> createPurchase(
            @Valid @RequestBody PurchaseRequestDto request
            ){

        PurchaseResponseDto response = purchaseService.createPurchase(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(
                        "Purchase Created sucessfully.",
                        HttpStatus.CREATED.value(),
                        response
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PurchaseResponseDto>> getPurchaseById(@PathVariable Long id){

        PurchaseResponseDto response = purchaseService.getPurchaseById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Purchase fetched sucessfully.",
                        HttpStatus.OK.value(),
                        response
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PurchaseResponseDto>>> getAllPurchase(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "purchaseDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ){
        Sort sort = direction.equalsIgnoreCase("desc")
                ?Sort.by(sortBy).descending()
                :Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page,size,sort);

        Page<PurchaseResponseDto> purchases = purchaseService.getAllPurchase(pageable);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Purchases fetched successfully.",
                        HttpStatus.OK.value(),
                        purchases
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<PurchaseResponseDto>>> searchPurchase(

            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "purchaseDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ){
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page,size,sort);

        Page<PurchaseResponseDto> response = purchaseService.searchPurchases(keyword,pageable);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Purchases fetched successfully.",
                        HttpStatus.OK.value(),
                        response
                )
        );
    }

    @GetMapping("/filter")
    public  ResponseEntity<ApiResponse<Page<PurchaseResponseDto>>> filterPurchases(

            @RequestParam LocalDate fromDate,

            @RequestParam LocalDate toDate,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "purchaseDate") String sortBy,

            @RequestParam(defaultValue = "desc") String direction
            ){

        Sort sort = direction.equalsIgnoreCase("desc")
                ?Sort.by(sortBy).descending()
                :Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page,size,sort);

        Page<PurchaseResponseDto> responses = purchaseService.filterPurchaseByDate(
                fromDate,
                toDate,
                pageable
        );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Purchases fetched successfully.",
                        HttpStatus.OK.value(),
                        responses
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePurchase(
            @PathVariable Long id
    ){

        purchaseService.deletePurchase(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Purchase deleted sucessfully",
                        HttpStatus.OK.value(),
                        null
                )
        );
    }
}
