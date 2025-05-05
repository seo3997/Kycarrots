package com.whomade.kycarrots.service.product;

import com.whomade.kycarrots.entity.product.TnProductImageVo;
import com.whomade.kycarrots.entity.product.TnProductVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.repository.mybatis.product.TnProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    // SELECT
    public List<TnProductVo> selectTbproduct(DataMap param) {
        return tnProductRepository.selectTbproduct(param);
    }

    // INSERT
    public int insertTbproduct(TnProductVo vo) {
        return tnProductRepository.insertTbproduct(vo);
    }

    // UPDATE
    public int updateTbproduct(TnProductVo vo) {
        return tnProductRepository.updateTbproduct(vo);
    }

    // DELETE
    public int deleteTbproduct(String productId) {
        return tnProductRepository.deleteTbproduct(productId);
    }

    public List<TnProductImageVo> selectProductImages(Long productId) {
        return tnProductRepository.selectProductImages(productId);
    }

    public int insertProductImage(TnProductImageVo vo) {
        return tnProductRepository.insertProductImage(vo);
    }

    public int updateProductImage(TnProductImageVo vo) {
        return tnProductRepository.updateProductImage(vo);
    }

    public int deleteProductImage(Long imageId) {
        return tnProductRepository.deleteProductImage(imageId);
    }

    @Transactional
    public void insertProductWithImages(TnProductVo productVo, List<TnProductImageVo> imageMetas, List<MultipartFile> files) {
        tnProductRepository.insertTbproduct(productVo);

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            TnProductImageVo meta = imageMetas.get(i);

            if (!file.isEmpty()) {
                meta.setProductId(Long.valueOf(productVo.getProductId()));
                meta.setImageName(file.getOriginalFilename());
                meta.setImageSize(file.getSize());
                meta.setImageType(file.getContentType());
                meta.setImageUrl("/uploads/" + file.getOriginalFilename());
                meta.setRegisterNo(Integer.parseInt(productVo.getRegisterNo()));
                meta.setUpdusrNo(Integer.parseInt(productVo.getUpdusrNo()));

                tnProductRepository.insertProductImage(meta);
            }
        }
    }
}
