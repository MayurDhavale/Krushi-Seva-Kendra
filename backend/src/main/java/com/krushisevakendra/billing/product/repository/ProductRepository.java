package com.krushisevakendra.billing.product.repository;

import com.krushisevakendra.billing.product.dto.ProductResponseDto;
import com.krushisevakendra.billing.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByActiveTrue(Pageable pageable);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndActiveTrue(String name);

    Optional<Product> findByIdAndActiveTrue(Long id);

    long countByActiveTrue();

    long countByActiveTrueAndStockQuantityLessThanEqual(Integer stockQuantity);

    List<Product> findByActiveTrueAndStockQuantityLessThanEqualOrderByStockQuantityAsc(
            Integer stockQuantity
    );

    List<Product> findByActiveTrueOrderByNameAsc();

   long countByActiveTrueAndStockQuantity(Integer stockQuantity);


}
