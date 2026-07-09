package com.whomade.kycarrots.entity.branch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbBranchVo {
    private Long branchId;
    private String branchCode;
    private String branchName;
    private String domainUrl;
    private String logoImageUrl;
    private String branchStatus;
    private String companyName;
    private String representativeName;
    private String businessNumber;
    private String tongsinNumber;
    private String csPhone;
    private String address;
    private String tossClientKey;
    private String tossSecretKey;
    private String tossMid;
    private String billingCycle;
    private Boolean isUseCustomPrice;
    private String shippingFeePolicy; // Using String for JSON
    private Boolean isActive;
    private String bankCd;
    private String accountNo;
    private String accountHolder;
    private Integer baseShippingFee;
    private Integer freeShippingThreshold;
    private Integer extraShippingFee;
    private Integer registerNo;
    private String registDt;
    private Integer updusrNo;
    private String updtDt;
    private String sdkTossClientKey;
    private String sdkTossSecretKey;
}
