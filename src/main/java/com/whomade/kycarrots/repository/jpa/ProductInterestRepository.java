package com.whomade.kycarrots.repository.jpa;

import com.whomade.kycarrots.entity.product.ProductInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProductInterestRepository extends JpaRepository<ProductInterest, Long> {
    boolean existsByUserNoAndProductId(Long userNo, Long productId);
    void deleteByUserNoAndProductId(Long userNo, Long productId);
    List<ProductInterest> findByUserNoOrderByInterestIdDesc(Long userNo);
    long countByProductId(Long productId);
    Optional<ProductInterest> findByUserNoAndProductId(Long userNo, Long productId);
}
