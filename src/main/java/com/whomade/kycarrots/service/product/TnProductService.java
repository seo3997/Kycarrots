package com.whomade.kycarrots.service.product;

import com.whomade.kycarrots.config.FileStorageProperties;
import com.whomade.kycarrots.dto.advertise.TnProductDetailResponse;
import com.whomade.kycarrots.entity.product.TnProductImageVo;
import com.whomade.kycarrots.entity.product.TnProductVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.file.FileUtil;
import com.whomade.kycarrots.push.FcmService;
import com.whomade.kycarrots.push.PushTargetDto;
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
import java.util.Map;

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

    @Autowired
    private FcmService fcmService;

    // SELECT
    public List<TnProductVo> selectTbproduct(DataMap param) {
        return tnProductRepository.selectTbProduct(param);
    }

    // INSERT
    public int insertTbproduct(TnProductVo vo) {
        return tnProductRepository.insertTbProduct(vo);
    }

    // UPDATE
    public int updateTbProduct(TnProductVo vo) {
        return tnProductRepository.updateTbProduct(vo);
    }

    // DELETE
    public int deleteTbProduct(String productId) {
        return tnProductRepository.deleteTbProduct(productId);
    }

    public List<TnProductImageVo> selectProductImages(Long productId) {
        return tnProductRepository.selectProductImagesByProductId(productId);
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
        tnProductRepository.insertTbProduct(productVo);

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
        //PUSH 전송
        String saleStatus = productVo.getSaleStatus(); // "0" or "1"
        String userId = productVo.getUserId();
        String productId = productVo.getProductId();
        String productTitle = productVo.getTitle();
        String messaeTitle = "";
        String messaeBody = "";

        if ("0".equals(saleStatus)) {
            // 승인요청: 중간센터/도매상에게 token으로 전송
            List<PushTargetDto> centerUsers = tnProductRepository.selectPushTargetsByProductId(productId); // token + userId
            for (PushTargetDto user : centerUsers) {
                messaeTitle = "상품 승인 요청";
                messaeBody  = productTitle + " 상품이 등록되었습니다. 승인해주세요.";
                fcmService.sendPushToUserAndLog(
                        user.getUserNo(),
                        user.getPushToken(),
                        messaeTitle,
                        messaeBody,
                        productId,
                        "승인요청",
                        Map.of(
                                "productId", productId,
                                "userId", userId,
                                "type", "product",
                                "title", messaeTitle,
                                "body", messaeBody
                        )
                );
            }
        } else if ("1".equals(saleStatus)) {
            // 판매중: 일반 구매자에게 topic으로 브로드캐스트
            productId="48";
            messaeTitle = "신규 상품 등록";
            messaeBody  = productTitle + " 상품이 판매중으로 등록되었습니다.";
            fcmService.sendPushToTopic(
                    "ROLE_PUB",
                    messaeTitle,
                    messaeBody,
                    Map.of(
                            "productId", productId,
                            "userId", userId,
                            "type", "product",
                            "title", messaeTitle,
                            "body", messaeBody
                    )
            );
        }
    }

    @Transactional
    public void updateProductWithImages(TnProductVo productVo,
                                        List<TnProductImageVo> imageMetas,
                                        List<MultipartFile> images) throws IOException {

        // 1. 상품 정보 수정
        tnProductRepository.updateTbProduct(productVo);

        // 2. 이미지 메타 정보와 파일 동기화
        for (int i = 0; i < imageMetas.size(); i++) {
            TnProductImageVo meta = imageMetas.get(i);
            MultipartFile file = (images != null && images.size() > i) ? images.get(i) : null;

            boolean isNew = (meta.getImageId() == null); // imageId 없으면 새 이미지

            // 새 이미지 추가
            if (isNew && file != null && !file.isEmpty()) {
                File destFile = FileUtil.saveFile(file, fileStorageProperties.getUploadDir(), productVo.getProductId());
                String imageUrl = publicUrl + "/" + productVo.getProductId() + "/" + destFile.getName();

                meta.setImageUrl(imageUrl);
                meta.setProductId(Long.valueOf(productVo.getProductId()));
                meta.setImageName(file.getOriginalFilename());
                meta.setImageSize(file.getSize());
                meta.setImageType(file.getContentType());
                meta.setImageCd("1");
                meta.setRegisterNo(Integer.parseInt(productVo.getRegisterNo()));
                meta.setUpdusrNo(Integer.parseInt(productVo.getUpdusrNo()));

                tnProductRepository.insertProductImage(meta);
            }

            // 기존 이미지 수정
            else if (meta.getImageId() != null) {
                if (file != null && !file.isEmpty()) {
                    File destFile = FileUtil.saveFile(file, fileStorageProperties.getUploadDir(), productVo.getProductId());
                    String imageUrl = publicUrl + "/" + productVo.getProductId() + "/" + destFile.getName();
                    meta.setImageUrl(imageUrl);
                    meta.setImageName(file.getOriginalFilename());
                    meta.setImageSize(file.getSize());
                    meta.setImageType(file.getContentType());
                }

                meta.setUpdusrNo(Integer.parseInt(productVo.getUpdusrNo()));
                tnProductRepository.updateProductImage(meta);
            }
        }
    }

    public TnProductDetailResponse getProductDetail(DataMap param) {
        TnProductVo product = tnProductRepository.selectProductById(param);
        if (product == null) {
            throw new IllegalArgumentException("상품을 찾을 수 없습니다: " + product.getProductId());
        }
        List<TnProductImageVo> images = tnProductRepository.selectProductImagesByProductId(Long.parseLong(product.getProductId()));
        return new TnProductDetailResponse(product, images);
    }

    public void deleteImageById(Long imageId) {
        // 1. DB에서 이미지 정보 조회
        /*
        TnProductImageVo image = tnProductRepository.selectProductImageById(imageId);

        // 2. 파일 경로 추출
        String productIdStr = image.getProductId().toString();

        // 3. 파일 삭제
        boolean deleted = FileUtil.deleteFile(fileStorageProperties.getUploadDir(),productIdStr,image.getImageName());
        if (!deleted) {
            String targetPath = fileStorageProperties.getUploadDir() + File.separator + productIdStr + File.separator + image.getImageName();
            throw new RuntimeException("파일 삭제 실패: " + targetPath);
        }
        */
        System.out.println("****imageId["+imageId+"]");

        // 4. DB에서 이미지 레코드 삭제
        tnProductRepository.deleteProductImage(imageId);
    }

    public DataMap getProductStatusCounts(Long userNo) {
        return tnProductRepository.selectProductStatusCounts(userNo);
    }

    public List<TnProductVo> getRecentProductsByUser(Long userNo){
        return tnProductRepository.selectRecentProductsByUser(userNo);
    }

    public int updateProductStatus(TnProductVo vo) {
        return tnProductRepository.updateProductStatus(vo);
    }

    public TnProductVo getProduct(DataMap param) {
        TnProductVo product = tnProductRepository.selectProductById(param);
        if (product == null) {
            throw new IllegalArgumentException("상품을 찾을 수 없습니다: ");
        }
        return product;
    }
}
