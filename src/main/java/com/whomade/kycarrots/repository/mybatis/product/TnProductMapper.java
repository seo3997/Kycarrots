package com.whomade.kycarrots.repository.mybatis.product;

import com.whomade.kycarrots.entity.product.TnProductVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: ADMIN
 * @version: 1.0.0
 * @since: 2024-06-23
 */
@Mapper
public interface TnProductMapper {
    List<TnProductVo> selectTbproduct(DataMap param);
}
