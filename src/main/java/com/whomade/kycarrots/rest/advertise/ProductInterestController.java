package com.whomade.kycarrots.rest.advertise;

import com.whomade.kycarrots.dto.advertise.InterestRequest;
import com.whomade.kycarrots.entity.product.ProductInterest;
import com.whomade.kycarrots.service.product.ProductInterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/interests")
public class ProductInterestController {

    private final ProductInterestService service;

    @GetMapping("/{userNo}")
    public ResponseEntity<List<ProductInterest>> list(@PathVariable Long userNo) {
        return ResponseEntity.ok(service.listByUser(userNo));
    }

    @PostMapping("/toggle")
    public ResponseEntity<Boolean> toggle(@RequestBody InterestRequest req) {
        return ResponseEntity.ok(service.toggle(req));
    }

    @GetMapping("/count/{productId}")
    public ResponseEntity<Long> count(@PathVariable Long productId) {
        return ResponseEntity.ok(service.countByProduct(productId));
    }

}
