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
    private Long productId;
    private String productName;
    private String optionName;
    private Integer unitPrice;
    private Integer quantity;
    private Integer registerNo;
    private String registDt;
    private Integer updusrNo;
    private String updtDt;
}
