package com.whomade.kycarrots.dto.advertise;

import com.whomade.kycarrots.entity.product.TnProductImageVo;
import com.whomade.kycarrots.entity.product.TnProductVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TnProductDetailResponse {
    private TnProductVo product;
    private List<TnProductImageVo> imageMetas;
}
