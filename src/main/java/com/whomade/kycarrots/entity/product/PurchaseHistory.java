// src/main/java/com/whomade/kycarrots/purchase/entity/PurchaseHistory.java
package com.whomade.kycarrots.entity.product;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_purchase_history")
public class PurchaseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PURCHASE_ID")
    private Long purchaseId;

    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

    @Column(name = "SELLER_NO", nullable = false)
    private Long sellerNo;

    @Column(name = "BUYER_NO", nullable = false)
    private Long buyerNo;

    @Column(name = "ROOM_ID", nullable = false, length = 128)
    private String roomId;

    @Column(name = "CREATED_AT", nullable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    @Column(name = "REGISTER_NO")
    private Integer registerNo;

    @Column(name = "REGIST_DT")
    private LocalDateTime registDt;

    @Column(name = "UPDUSR_NO")
    private Integer updusrNo;

    @Column(name = "UPDT_DT")
    private LocalDateTime updtDt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (registDt == null)  registDt  = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updtDt = LocalDateTime.now();
    }
}
