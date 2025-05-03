package com.whomade.kycarrots.repository.mybatis.product;

import com.whomade.kycarrots.entity.product.TnProductVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: ADMIN
 * @version: 1.0.0
 * @since: 2024-06-23
 */
@Repository
@RequiredArgsConstructor
@Transactional
public class TnProductRepository {

    private final TnProductMapper tnProductMapper;

    public List<TnProductVo> selectTbproduct(DataMap param) {
        List<TnProductVo> tbproducts= tnProductMapper.selectTbproduct(param);
        return tbproducts;
    }

}
