package com.whomade.kycarrots.repository.mybatis.product;

import com.whomade.kycarrots.entity.product.TnProductImageVo;
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
    // SELECT
    List<TnProductVo> selectTbProduct(DataMap param);

    TnProductVo selectProductById(Long productId); // 🔹 상품 상세 조회 추가

    // INSERT
    int insertTbProduct(TnProductVo vo);

    // UPDATE
    int updateTbProduct(TnProductVo vo);

    // DELETE
    int deleteTbProduct(String productId);

    TnProductImageVo selectProductImageById(Long imageId);

    List<TnProductImageVo> selectProductImagesByProductId(Long productId);

    int insertProductImage(TnProductImageVo vo);

    int updateProductImage(TnProductImageVo vo);

    int deleteProductImage(Long imageId);
}
