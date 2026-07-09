package com.whomade.kycarrots.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchInfoVo {
    private long branchId;
    private String branchCode;
    private String branchName;
    private String logoImageUrl;
    private String branchStatus;
    private String tossClientKey;
    private String bankCd;
    private String accountNo;
    private String accountHolder;
    private int baseShippingFee;
    private int freeShippingThreshold;
    private int extraShippingFee;
    private int isUseCustomPrice;
    private String companyName;
    private String representativeName;
    private String businessNumber;
    private String tongsinNumber;
    private String csPhone;
    private String address;
    private String sdkTossClientKey;
}
