package com.whomade.kycarrots.repository.mybatis.product;

import com.whomade.kycarrots.entity.product.TnProductImageVo;
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

    // INSERT
    public int insertTbproduct(TnProductVo vo) {
        return tnProductMapper.insertTbproduct(vo);
    }

    // UPDATE
    public int updateTbproduct(TnProductVo vo) {
        return tnProductMapper.updateTbproduct(vo);
    }

    // DELETE
    public int deleteTbproduct(String productId) {
        return tnProductMapper.deleteTbproduct(productId);
    }

    public List<TnProductImageVo> selectProductImages(Long productId) {
        return tnProductMapper.selectProductImages(productId);
    }

    public int insertProductImage(TnProductImageVo vo) {
        return tnProductMapper.insertProductImage(vo);
    }

    public int updateProductImage(TnProductImageVo vo) {
        return tnProductMapper.updateProductImage(vo);
    }

    public int deleteProductImage(Long imageId) {
        return tnProductMapper.deleteProductImage(imageId);
    }
}
