package com.whomade.kycarrots.entity.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVo {
    private Long orderId;
    private String orderNo;
    private Long userNo;
    private String orderStatus; // READY/FAILED/PAID/CANCEL/PREPARING/SHIPPING/DELIVERED/RETURN_REQUESTED/EXCHANGED
    private String paymentStatus; // READY/PAID/CANCEL/FAILED
    private Integer totalItemAmount;
    private Integer deliveryFee;
    private Integer discountAmount;
    private Integer totalPayAmount;
    private String receiverName;
    private String receiverPhone;
    private String zipCode;
    private String address1;
    private String address2;
    private String orderMemo;
    private String orderedAt;
    private String paidAt;
    private String cancelledAt;
    private Integer registerNo;
    private String registDt;
    private Integer updusrNo;
    private String updtDt;
}
