package com.whomade.kycarrots.dto.advertise;

import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

@Data
public class AdvertiseQueryParams {
    private String token;
    private Integer adCode;      // 사용 안 해도 유지 가능
    private Integer pageno;      // 클라와 동일한 "pageno"

    private String categoryGroup;
    private String categoryMid;
    private String categoryScls;

    private String areaGroup;
    private String areaMid;
    private String areaScls;

    private BigDecimal minPrice; // 클라 Int여도 Jackson이 BigDecimal로 매핑 가능
    private BigDecimal maxPrice;

    private String saleStatus;
}