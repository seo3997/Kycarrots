package com.whomade.kycarrots.entity.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TnProductImageVo {
    private Long imageId;
    private Long productId;
    private String imageCd;
    private String imageUrl;
    private String imageName;
    private Integer represent;
    private Long imageSize;
    private String imageText;
    private String imageType;
    private Integer registerNo;
    private String registDt;
    private Integer updusrNo;
    private String updtDt;
}
