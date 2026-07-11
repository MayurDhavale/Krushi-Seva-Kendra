package com.krushisevakendra.billing.category.repository;

import com.krushisevakendra.billing.category.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    boolean existsByName(String name);

    //Replace List<Category> findActiveTrue();
    Page<Category> findByActiveTrue(Pageable pageable);

    List<Category> findByNameContainingIgnoreCase(String keyword);


}
