package com.krushisevakendra.billing.supplier.controller;

import com.krushisevakendra.billing.common.response.ApiResponse;
import com.krushisevakendra.billing.supplier.dto.SupplierRequestDto;
import com.krushisevakendra.billing.supplier.dto.SupplierResponseDto;
import com.krushisevakendra.billing.supplier.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    public ResponseEntity<ApiResponse<SupplierResponseDto>> createSupplier(
            @Valid @RequestBody SupplierRequestDto requestDto) {

        SupplierResponseDto response =
                supplierService.createSupplier(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "Supplier created successfully.",
                                HttpStatus.CREATED.value(),
                                response
                        )
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<SupplierResponseDto>>> getAllSuppliers(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "supplierName") String sortBy,

            @RequestParam(defaultValue = "asc") String direction
    ) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<SupplierResponseDto> suppliers =
                supplierService.getAllSuppliers(pageable);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Suppliers fetched successfully.",
                        HttpStatus.OK.value(),
                        suppliers
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SupplierResponseDto>> getSupplierById(
            @PathVariable Long id) {

        SupplierResponseDto response =
                supplierService.getSupplierById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Supplier fetched successfully.",
                        HttpStatus.OK.value(),
                        response
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SupplierResponseDto>> updateSupplier(
            @PathVariable Long id,
            @Valid @RequestBody SupplierRequestDto requestDto) {

        SupplierResponseDto response =
                supplierService.updateSupplier(id, requestDto);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Supplier updated successfully.",
                        HttpStatus.OK.value(),
                        response
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSupplier(
            @PathVariable Long id) {

        supplierService.deleteSupplier(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Supplier deleted successfully.",
                        HttpStatus.OK.value(),
                        null
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<SupplierResponseDto>>> searchSuppliers(

            @RequestParam String keyword,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "supplierName") String sortBy,

            @RequestParam(defaultValue = "asc") String direction
    ) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<SupplierResponseDto> suppliers =
                supplierService.searchSuppliers(keyword, pageable);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Suppliers fetched successfully.",
                        HttpStatus.OK.value(),
                        suppliers
                )
        );
    }
}
