package com.whomade.kycarrots.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchInfoVo {
    private long branch_id;
    private String branch_code;
    private String branch_name;
    private String logo_image_url;
    private String branch_status;
    private String toss_client_key;
    private String bank_cd;
    private String account_no;
    private String account_holder;
    private int base_shipping_fee;
    private int free_shipping_threshold;
    private int extra_shipping_fee;
    private int is_use_custom_price;
    private String company_name;
    private String representative_name;
    private String business_number;
    private String tongsin_number;
    private String cs_phone;
    private String address;
}
