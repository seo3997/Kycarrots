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
}
