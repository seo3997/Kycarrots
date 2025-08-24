// src/main/java/com/whomade/kycarrots/purchase/controller/PurchaseHistoryController.java
package com.whomade.kycarrots.rest.advertise;


import com.whomade.kycarrots.entity.product.PurchaseHistory;
import com.whomade.kycarrots.service.product.PurchaseHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/purchases")
public class PurchaseHistoryController {

    private final PurchaseHistoryService service;

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> create(@RequestBody PurchaseHistory ph) {
        Map<String, Object> res = new HashMap<>();
        try {
            PurchaseHistory saved = service.create(ph); // 업서트
            res.put("result", true);
            res.put("message", "구매이력이 등록(또는 갱신)되었습니다.");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.put("result", false);
            res.put("message", "서버 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }
    @GetMapping("/{purchaseId}")
    public PurchaseHistory get(@PathVariable Long purchaseId) {
        return service.get(purchaseId);
    }

    @GetMapping("/buyer/{buyerNo}")
    public Page<PurchaseHistory> listByBuyer(@PathVariable Long buyerNo,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "20") int size) {
        return service.listByBuyer(buyerNo, PageRequest.of(page, size));
    }

    @GetMapping("/seller/{sellerNo}")
    public Page<PurchaseHistory> listBySeller(@PathVariable Long sellerNo,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "20") int size) {
        return service.listBySeller(sellerNo, PageRequest.of(page, size));
    }

    @PutMapping("/{purchaseId}")
    public PurchaseHistory update(@PathVariable Long purchaseId, @RequestBody PurchaseHistory ph) {
        ph.setPurchaseId(purchaseId);
        return service.update(ph);
    }

    @DeleteMapping("/{purchaseId}")
    public void delete(@PathVariable Long purchaseId) {
        service.delete(purchaseId);
    }
}
