package com.whomade.kycarrots.service.product;

import com.whomade.kycarrots.config.FileStorageProperties;
import com.whomade.kycarrots.entity.product.TnProductImageVo;
import com.whomade.kycarrots.entity.product.TnProductVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.file.FileUtil;
import com.whomade.kycarrots.repository.mybatis.product.TnProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author: ADMIN
 * @version: 1.0.0
 * @since: 2024-06-23
 */
@Service
@RequiredArgsConstructor
public class TnProductService {

    @Value("${file.public-url}")
    private String publicUrl;


    private final TnProductRepository tnProductRepository;
    @Autowired
    private FileStorageProperties fileStorageProperties;


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
    public void insertProductWithImages(TnProductVo productVo, List<TnProductImageVo> imageMetas, List<MultipartFile> files) throws IOException {
        tnProductRepository.insertTbproduct(productVo);

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            TnProductImageVo meta = imageMetas.get(i);

            if (!file.isEmpty()) {

                String baseDir = fileStorageProperties.getUploadDir();
                String productId = productVo.getProductId(); // 예: "123"
                String imageUrl  ="";

                try {
                    File destFile = FileUtil.saveFile(file, baseDir, productId);
                    // DB에 저장할 경로:
                    imageUrl = publicUrl + "/" + productId + "/" + destFile.getName();
                    meta.setImageUrl(imageUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }


                // 2. DB용 메타 정보 세팅
                meta.setProductId(Long.valueOf(productVo.getProductId()));
                meta.setImageName(file.getOriginalFilename());
                meta.setImageSize(file.getSize());
                meta.setImageType(file.getContentType());
                meta.setImageUrl(imageUrl);
                meta.setImageCd("1");
                meta.setRegisterNo(Integer.parseInt(productVo.getRegisterNo()));
                meta.setUpdusrNo(Integer.parseInt(productVo.getUpdusrNo()));

                // 3. DB insert
                tnProductRepository.insertProductImage(meta);
            }
        }
    }
}
