package com.krushisevakendra.billing.purchase.repository;

import com.krushisevakendra.billing.purchase.entity.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem,Long> {
}
