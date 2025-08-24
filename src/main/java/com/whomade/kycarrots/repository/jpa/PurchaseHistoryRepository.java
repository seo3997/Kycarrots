// src/main/java/com/whomade/kycarrots/purchase/repository/PurchaseHistoryRepository.java
package com.whomade.kycarrots.repository.jpa;


import com.whomade.kycarrots.entity.product.PurchaseHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {
    Optional<PurchaseHistory> findByProductIdAndBuyerNo(Long productId, Long buyerNo);
    // 구매자 앱 리스트용 (자주 쓰일 가능성 높음)
    Page<PurchaseHistory> findByBuyerNoOrderByCreatedAtDesc(Long buyerNo, Pageable pageable);

    // 판매자 기준 조회 필요 시
    Page<PurchaseHistory> findBySellerNoOrderByCreatedAtDesc(Long sellerNo, Pageable pageable);
}
