package com.whomade.kycarrots.entity.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TnProductVo {
    private String productId;
    private String userNo;
    private String title;
    private String description;
    private String price;
    private String categoryGroup;
    private String categoryMid;
    private String categoryScls;
    private String saleStatus;
    private String registerNo;
    private String registDt;
    private String updusrNo;
    private String updtDt;
    private String imageUrl;
    private String userId;


    private String areaGroup;        // 도시 대분류 코드
    private String areaMid;          // 도시 중분류 코드
    private String areaScls;         // 도시 소분류 코드


    private String quantity;         // 남은 수량
    private String unitGroup;        // 단위 코드
    private String unitCode;         // 단위 코드

    private String desiredShippingDate; // 희망 출하일 (yyyy-MM-dd)
}
