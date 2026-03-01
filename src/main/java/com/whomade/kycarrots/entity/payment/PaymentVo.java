package com.whomade.kycarrots.entity.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentVo {
    private Long paymentId;
    private Long orderId;
    private Long branchId;
    private Long userNo;
    private String paymentStatus; // READY/PAID/CANCEL/FAILED
    private String paymentMethod; // CARD/VBANK/BANK/MOBILE
    private String pgProvider; // toss/inicis/nicepay/kakao
    private String pgTid; // PG Transaction ID
    private String merchantUid; // 가맹점 주문번호 (orderNo)
    private Integer amountTotal;
    private Integer amountTaxFree;
    private Integer amountVat;
    private String currency;
    private String cardCompany;
    private String cardNumberMasked;
    private Integer cardInstallmentMonth;
    private String receiptUrl;
    private String tossPaymentKey;
    private String tossMid;
    private String paidAt;
    private String cancelledAt;
    private String failCode;
    private String failMessage;
    private Integer registerNo;
    private String registDt;
    private Integer updusrNo;
    private String updtDt;
}
