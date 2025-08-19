package com.whomade.kycarrots.entity.product;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "tb_product_interest")
public class ProductInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INTEREST_ID")
    private Long interestId;

    @Column(name = "USER_NO", nullable = false)
    private Long userNo;

    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

    @Column(name = "REGISTER_NO")
    private Integer registerNo;

    @Column(name = "REGIST_DT")
    private LocalDateTime registDt;

    @Column(name = "UPDUSR_NO")
    private Integer updusrNo;

    @Column(name = "UPDT_DT")
    private LocalDateTime updtDt;

    @PrePersist
    void onCreate() {
        if (registDt == null) registDt = LocalDateTime.now();
    }
    @PreUpdate
    void onUpdate() {
        updtDt = LocalDateTime.now();
    }
}
