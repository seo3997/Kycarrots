package com.whomade.kycarrots.service.product;

import com.whomade.kycarrots.entity.product.TnProductVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.repository.mybatis.product.TnProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: ADMIN
 * @version: 1.0.0
 * @since: 2024-06-23
 */
@Service
@RequiredArgsConstructor
public class TnProductService {
    private final TnProductRepository tnProductRepository;


    public List<TnProductVo> selectTbproduct(DataMap param) {
        return tnProductRepository.selectTbproduct(param);
    }


}
