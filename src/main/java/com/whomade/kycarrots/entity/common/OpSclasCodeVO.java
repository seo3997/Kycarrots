package com.whomade.kycarrots.entity.common;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OpSclasCodeVO {

    private String groupId;      // GROUP_ID
    private String code;         // CODE
    private String sclasCode;    // SCLAS_CODE
    private String sclasNm;      // SCLAS_NM

    private String attrb1;       // ATTRB_1
    private String attrb2;       // ATTRB_2
    private String attrb3;       // ATTRB_3
    private String attrb4;       // ATTRB_4

    private Integer sortOrdr;    // SORT_ORDR
    private String useYn;        // USE_YN

    private Integer registerNo;  // REGISTER_NO
    private LocalDateTime registDt; // REGIST_DT

    private Integer updusrNo;    // UPDUSR_NO
    private LocalDateTime updtDt;   // UPDT_DT
}
