package com.whomade.kycarrots.service.product;

import com.whomade.kycarrots.dto.advertise.InterestRequest;
import com.whomade.kycarrots.entity.product.ProductInterest;
import com.whomade.kycarrots.repository.jpa.ProductInterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductInterestService {

    private final ProductInterestRepository repo;

    /** 생성(중복 시 기존 그대로 유지하고 기존 ID 반환 원하면 find 사용) */
    @Transactional
    public Long add(InterestRequest req) {
        if (repo.existsByUserNoAndProductId(req.getUserNo(), req.getProductId())) {
            return repo.findByUserNoAndProductId(req.getUserNo(), req.getProductId())
                       .map(ProductInterest::getInterestId).orElse(null);
        }
        ProductInterest saved = repo.save(ProductInterest.builder()
                .userNo(req.getUserNo())
                .productId(req.getProductId())
                .registerNo(req.getActorNo())
                .build());
        return saved.getInterestId();
    }

    /** 삭제(존재하지 않아도 예외 없이 통과) */
    @Transactional
    public void remove(Long userNo, Long productId, Integer actorNo) {
        // 필요 시 삭제 로그용 업데이트 로직 추가 가능
        repo.deleteByUserNoAndProductId(userNo, productId);
    }

    /** 사용자별 목록 */
    public List<ProductInterest> listByUser(Long userNo) {
        return repo.findByUserNoOrderByInterestIdDesc(userNo);
    }

    /** 토글: 없으면 생성(true), 있으면 삭제(false) */
    @Transactional
    public boolean toggle(InterestRequest req) {
        boolean exists = repo.existsByUserNoAndProductId(req.getUserNo(), req.getProductId());
        if (exists) {
            repo.deleteByUserNoAndProductId(req.getUserNo(), req.getProductId());
            return false;
        } else {
            add(req);
            return true;
        }
    }

    /** 특정 상품의 찜 수 */
    public long countByProduct(Long productId) {
        return repo.countByProductId(productId);
    }
}
