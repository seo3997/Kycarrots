package com.whomade.kycarrots.repository.mybatis.product;

import com.whomade.kycarrots.entity.product.TnProductImageVo;
import com.whomade.kycarrots.entity.product.TnProductVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.push.PushTargetDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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

    public List<TnProductVo> selectTbProduct(DataMap param) {
        List<TnProductVo> tbproducts= tnProductMapper.selectTbProduct(param);
        return tbproducts;
    }

    public List<TnProductVo> selectBuyTbProduct(DataMap param) {
        List<TnProductVo> tbproducts= tnProductMapper.selectBuyTbProduct(param);
        return tbproducts;
    }

    public TnProductVo selectProductById(DataMap param) {
        return tnProductMapper.selectProductById(param);
    }
    // INSERT
    public int insertTbProduct(TnProductVo vo) {
        return tnProductMapper.insertTbProduct(vo);
    }

    // UPDATE
    public int updateTbProduct(TnProductVo vo) {
        return tnProductMapper.updateTbProduct(vo);
    }

    // DELETE
    public int deleteTbProduct(String productId) {
        return tnProductMapper.deleteTbProduct(productId);
    }

    public List<TnProductImageVo> selectProductImagesByProductId(Long productId) {
        return tnProductMapper.selectProductImagesByProductId(productId);
    }

    public TnProductImageVo selectProductImageById(Long imageId) {
        return tnProductMapper.selectProductImageById(imageId);
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

    public DataMap selectProductStatusCounts(Long userNo) {
        return tnProductMapper.selectProductStatusCounts(userNo);
    }

    public List<TnProductVo> selectRecentProductsByUser(Long userNo){
        return tnProductMapper.selectRecentProductsByUser(userNo);
    }

    public List<PushTargetDto> selectPushTargetsByProductId(String productId) {
        return tnProductMapper.selectPushTargetsByProductId(productId);
    }

    public int updateProductStatus(TnProductVo vo) {
        return tnProductMapper.updateProductStatus(vo);
    }

    public List<TnProductVo> selectInterestProducts(DataMap param) {
        return tnProductMapper.selectInterestProducts(param);
    }

    public List<TnProductVo> selectPurchasedProducts(DataMap param) {
        return tnProductMapper.selectPurchasedProducts(param);
    }

    public List<Map<String, Object>> findChatBuyersByProductAndSeller(DataMap param) {
        return tnProductMapper.selectChatBuyersByProductAndSeller(param);
    }
}
