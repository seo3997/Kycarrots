// src/main/java/com/whomade/kycarrots/purchase/service/PurchaseHistoryService.java
package com.whomade.kycarrots.service.product;


import com.whomade.kycarrots.entity.product.PurchaseHistory;
import com.whomade.kycarrots.repository.jpa.PurchaseHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseHistoryService {

    private final PurchaseHistoryRepository repository;

    public PurchaseHistory create(PurchaseHistory ph) {
        return repository.findByProductIdAndBuyerNo(ph.getProductId(), ph.getBuyerNo())
                .map(exist -> {
                    mergeNonNull(exist, ph);        // 필요한 필드만 갱신(널은 덮지 않음)
                    return repository.save(exist);
                })
                .orElseGet(() -> {
                    try {
                        return repository.save(ph); // 최초 생성 시도
                    } catch (DataIntegrityViolationException e) {
                        // 거의 동시에 같은 (productId,buyerNo)로 insert 시도 → 유니크 충돌
                        PurchaseHistory exist = repository
                                .findByProductIdAndBuyerNo(ph.getProductId(), ph.getBuyerNo())
                                .orElseThrow(() -> e);
                        mergeNonNull(exist, ph);
                        return repository.save(exist);
                    }
                });
    }

    public PurchaseHistory get(Long purchaseId) {
        return repository.findById(purchaseId).orElse(null);
    }

    public Page<PurchaseHistory> listByBuyer(Long buyerNo, Pageable pageable) {
        return repository.findByBuyerNoOrderByCreatedAtDesc(buyerNo, pageable);
    }

    public Page<PurchaseHistory> listBySeller(Long sellerNo, Pageable pageable) {
        return repository.findBySellerNoOrderByCreatedAtDesc(sellerNo, pageable);
    }

    public PurchaseHistory update(PurchaseHistory ph) {
        // 단순 덮어쓰기용 (필요 시 필드별 patch로 변경)
        return repository.save(ph);
    }

    public void delete(Long purchaseId) {
        repository.deleteById(purchaseId);
    }

    private static void mergeNonNull(PurchaseHistory target, PurchaseHistory src) {
        if (src.getRoomId()   != null) target.setRoomId(src.getRoomId());
        if (src.getSellerNo() != null) target.setSellerNo(src.getSellerNo());
    }
}
