package com.krushisevakendra.billing.supplier.service;



import com.krushisevakendra.billing.exception.DuplicateResourceException;
import com.krushisevakendra.billing.exception.ResourceNotFoundException;
import com.krushisevakendra.billing.product.entity.Product;
import com.krushisevakendra.billing.sale.entity.Sale;
import com.krushisevakendra.billing.sale.entity.SaleItem;
import com.krushisevakendra.billing.sale.repository.SaleRepository;
import com.krushisevakendra.billing.supplier.dto.SupplierRequestDto;
import com.krushisevakendra.billing.supplier.dto.SupplierResponseDto;
import com.krushisevakendra.billing.supplier.entity.Supplier;
import com.krushisevakendra.billing.supplier.mapper.SupplierMapper;
import com.krushisevakendra.billing.supplier.repository.SupplierRepository;
import com.krushisevakendra.billing.supplier.service.SupplierService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final SaleRepository saleRepository;

    @Override
    @Transactional
    public SupplierResponseDto createSupplier(SupplierRequestDto requestDto) {

        if (supplierRepository.existsBySupplierName(requestDto.getSupplierName())) {
            throw new DuplicateResourceException(
                    "Supplier already exists with name: " + requestDto.getSupplierName()
            );
        }

        if (supplierRepository.existsByMobileNumber(requestDto.getMobileNumber())) {
            throw new DuplicateResourceException(
                    "Supplier already exists with mobile number: " + requestDto.getMobileNumber()
            );
        }

        Supplier supplier = supplierMapper.toEntity(requestDto);

        Supplier savedSupplier = supplierRepository.save(supplier);

        return supplierMapper.toResponse(savedSupplier);
    }

    @Override
    public Page<SupplierResponseDto> getAllSuppliers(Pageable pageable) {

        return supplierRepository.findByActiveTrue(pageable)
                .map(supplierMapper::toResponse);
    }

    @Override
    public SupplierResponseDto getSupplierById(Long id) {

        Supplier supplier = supplierRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supplier not found with id: " + id));

        return supplierMapper.toResponse(supplier);
    }

    @Override
    @Transactional
    public SupplierResponseDto updateSupplier(Long id, SupplierRequestDto requestDto) {

        // Step 1: Find supplier by ID
        Supplier supplier = supplierRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supplier not found with id: " + id));

        // Step 2: Check duplicate supplier name
        Optional<Supplier> supplierByName =
                supplierRepository.findBySupplierName(requestDto.getSupplierName());

        if (supplierByName.isPresent()) {

            if (!supplierByName.get().getId().equals(id)) {
                throw new DuplicateResourceException(
                        "Supplier already exists with name: " + requestDto.getSupplierName());
            }
        }

        // Step 3: Check duplicate mobile number
        Optional<Supplier> supplierByMobile =
                supplierRepository.findByMobileNumber(requestDto.getMobileNumber());

        if (supplierByMobile.isPresent()) {

            if (!supplierByMobile.get().getId().equals(id)) {
                throw new DuplicateResourceException(
                        "Supplier already exists with mobile number: " + requestDto.getMobileNumber());
            }
        }

        // Step 4: Check duplicate GST Number (only if provided)
        if (requestDto.getGstNumber() != null &&
                !requestDto.getGstNumber().isBlank()) {

            Optional<Supplier> supplierByGst =
                    supplierRepository.findByGstNumber(requestDto.getGstNumber());

            if (supplierByGst.isPresent()) {

                if (!supplierByGst.get().getId().equals(id)) {
                    throw new DuplicateResourceException(
                            "Supplier already exists with GST Number: " + requestDto.getGstNumber());
                }
            }
        }

        // Step 5: Update supplier details
        supplier.setSupplierName(requestDto.getSupplierName());
        supplier.setContactPerson(requestDto.getContactPerson());
        supplier.setMobileNumber(requestDto.getMobileNumber());
        supplier.setEmail(requestDto.getEmail());
        supplier.setAddress(requestDto.getAddress());
        supplier.setGstNumber(requestDto.getGstNumber());

        // Step 6: Save updated supplier
        Supplier updatedSupplier = supplierRepository.save(supplier);

        // Step 7: Convert Entity to Response DTO
        return supplierMapper.toResponse(updatedSupplier);
    }

    @Override
    @Transactional
    public void deleteSupplier(Long id) {

        Supplier supplier = supplierRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Supplier not found with id: " + id));

        supplier.setActive(false);

        supplierRepository.save(supplier);
    }

    @Override
    public Page<SupplierResponseDto> searchSuppliers(String keyword, Pageable pageable) {

        return supplierRepository
                .findByActiveTrueAndSupplierNameContainingIgnoreCaseOrActiveTrueAndContactPersonContainingIgnoreCaseOrActiveTrueAndMobileNumberContaining(
                        keyword,
                        keyword,
                        keyword,
                        pageable
                )
                .map(supplierMapper::toResponse);
    }


}