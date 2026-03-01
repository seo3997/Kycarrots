package com.whomade.kycarrots.entity.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemVo {
    private Long orderItemId;
    private Long orderId;
    private Long branchId;
    private Long productId;
    private String productName;
    private String optionName;
    private Integer unitPrice;
    private Integer supplyPrice; // 본사 공급 원가
    private Integer salePrice; // 지점 실제 판매가
    private Integer quantity;
    private Integer registerNo;
    private String registDt;
    private Integer updusrNo;
    private String updtDt;

    // Joined fields from tb_product and tb_product_image
    private String imageUrl;
    private String description;
    private String title;
    private String categoryGroup;
    private String categoryMid;
    private String categoryScls;
}
