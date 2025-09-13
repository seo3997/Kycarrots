package com.whomade.kycarrots.push;

public enum SaleStatus {
    REQUEST("0"),   // 승인요청
    ON_SALE("1"),   // 판매중(승인됨)
    REJECT("98"),   // 반려
    DONE("99");     // 판매완료

    public final String code;
    SaleStatus(String code) { this.code = code; }

    public static SaleStatus of(String code) {
        for (var s : values()) if (s.code.equals(code)) return s;
        throw new IllegalArgumentException("Unknown saleStatus: " + code);
    }
}
