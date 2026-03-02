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
    private Long branchId;
    private String orderStatus; // 10:READY, 20:FAILED, 30:PAID, 40:CANCEL, 50:PREPARING, 60:SHIPPING,
                                // 70:DELIVERED, 80:RETURN_REQUESTED, 90:EXCHANGED
    private String paymentStatus; // 10:READY, 20:FAILED, 30:PAID, 40:CANCEL
    private Integer totalItemAmount;
    private Integer supplyPriceSum; // 본사 공급가 합계
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
    private String cancelReason;
    private Long paymentId;
    private String pgTid;
    private String deliveryCompanyCode;
    private String deliveryCompanyNm;
    private String trackingNo;
    private String shippedAt;
    private Integer registerNo;
    private String registDt;
    private String branchDepositStatus; // R010680 (10:WAITING, 20:DEPOSITED, 30:CANCEL)
    private String branchDepositConfirmedAt;
    private String deliveredAt;
    private Integer updusrNo;
    private String updtDt;
}
