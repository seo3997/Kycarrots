package com.whomade.kycarrots.service.product;

import com.whomade.kycarrots.entity.product.TnProductImageVo;
import com.whomade.kycarrots.entity.product.TnProductVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.file.FileUtil;
import com.whomade.kycarrots.dto.advertise.TnProductDetailResponse;
import com.whomade.kycarrots.repository.mybatis.product.TnProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class TnProductService {

    @Autowired
    private com.whomade.kycarrots.framework.common.util.file.FilePathResolver filePathResolver;

    private final TnProductRepository tnProductRepository;

    @Autowired
    private com.whomade.kycarrots.push.PushService pushService;

    // SELECT
    public List<TnProductVo> selectTbproduct(DataMap param) {
        return tnProductRepository.selectTbProduct(param);
    }

    // SELECT
    public List<TnProductVo> selectBuyTbProduct(DataMap param) {
        return tnProductRepository.selectBuyTbProduct(param);
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
    public void insertProductWithImages(TnProductVo productVo, List<TnProductImageVo> imageMetas,
            List<MultipartFile> files) throws IOException {
        tnProductRepository.insertTbProduct(productVo);

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            TnProductImageVo meta = imageMetas.get(i);

            if (!file.isEmpty()) {

                String baseDir = filePathResolver.resolve("product").getUploadDir();
                String productId = productVo.getProductId(); // 예: "123"
                String imageUrl = "";

                try {
                    File destFile = FileUtil.saveFile(file, baseDir, productId);
                    // DB에 저장할 경로:
                    imageUrl = filePathResolver.resolve("product").getPublicUrl() + "/" + productId + "/" + destFile.getName();
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
        // PUSH 전송
        String saleStatus = productVo.getSaleStatus(); // "0" or "1"
        String productId = productVo.getProductId();
        String productTitle = productVo.getTitle();

        String messaeTitle = "";
        String messaeBody = "";

        if ("1".equals(saleStatus)) {
            // 판매중: 일반 구매자에게 topic으로 브로드캐스트
            messaeTitle = "신규 상품 등록";
            messaeBody = productTitle + " 상품이 판매중으로 등록되었습니다.";
            // [Fix] actorUserNo 추가하여 본인 제외 처리
            Long actorUserNo = (productVo.getRegisterNo() != null) ? Long.valueOf(productVo.getRegisterNo()) : null;

            pushService.sendTargetPush(
                    actorUserNo,
                    List.of("ROLE_PUB", "ROLE_PROJ", "ROLE_SELL"),
                    null,
                    null,
                    null,
                    messaeTitle,
                    messaeBody,
                    "PRODUCT_REGISTER",
                    Map.of(
                            "targetId", productId,
                            "type", "product",
                            "title", messaeTitle,
                            "body", messaeBody));
        }
    }

    @Transactional
    public void updateProductWithImages(TnProductVo productVo,
            List<TnProductImageVo> imageMetas,
            List<MultipartFile> images) throws IOException {

        // 1. 상품 정보 수정
        tnProductRepository.updateTbProduct(productVo);
        log.debug("imageMetas:", imageMetas);
        log.debug("imageMetas Size:", imageMetas.size());
        // 2. 이미지 메타 정보와 파일 동기화
        for (int i = 0; i < imageMetas.size(); i++) {
            TnProductImageVo meta = imageMetas.get(i);
            MultipartFile file = (images != null && images.size() > i) ? images.get(i) : null;

            boolean isNew = (meta.getImageId() == null); // imageId 없으면 새 이미지

            // 새 이미지 추가
            if (isNew && file != null && !file.isEmpty()) {
                File destFile = FileUtil.saveFile(file, filePathResolver.resolve("product").getUploadDir(),
                        productVo.getProductId());
                String imageUrl = filePathResolver.resolve("product").getPublicUrl() + "/" + productVo.getProductId() + "/" + destFile.getName();

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
                    File destFile = FileUtil.saveFile(file, filePathResolver.resolve("product").getUploadDir(),
                            productVo.getProductId());
                    String imageUrl = filePathResolver.resolve("product").getPublicUrl() + "/" + productVo.getProductId() + "/" + destFile.getName();
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
            throw new IllegalArgumentException("상품을 찾을 수 없습니다: " + param.getString("productId"));
        }
        List<TnProductImageVo> images = tnProductRepository
                .selectProductImagesByProductId(Long.parseLong(product.getProductId()));
        return new TnProductDetailResponse(product, images);
    }

    public void deleteImageById(Long imageId) {
        // 1. DB에서 이미지 정보 조회
        /*
         * TnProductImageVo image = tnProductRepository.selectProductImageById(imageId);
         * 
         * // 2. 파일 경로 추출
         * String productIdStr = image.getProductId().toString();
         * 
         * // 3. 파일 삭제
         * boolean deleted =
         * FileUtil.deleteFile(fileStorageProperties.getUploadDir(),productIdStr,image.
         * getImageName());
         * if (!deleted) {
         * String targetPath = fileStorageProperties.getUploadDir() + File.separator +
         * productIdStr + File.separator + image.getImageName();
         * throw new RuntimeException("파일 삭제 실패: " + targetPath);
         * }
         */
        System.out.println("****imageId[" + imageId + "]");

        // 4. DB에서 이미지 레코드 삭제
        tnProductRepository.deleteProductImage(imageId);
    }

    public DataMap getProductStatusCounts(DataMap param) {
        return tnProductRepository.selectProductStatusCounts(param);
    }

    public List<TnProductVo> getRecentProductsByUser(DataMap param) {
        return tnProductRepository.selectRecentProductsByUser(param);
    }

    public int updateProductStatus(TnProductVo vo) {
        int iReturn = 0;
        // 1) 기존 상태 조회
        String productId = vo.getProductId();
        DataMap param = new DataMap();
        param.put("productId", productId);
        TnProductVo product = tnProductRepository.selectProductById(param);
        String oldStatus = product.getSaleStatus();

        iReturn = tnProductRepository.updateProductStatus(vo);

        if (vo != null && oldStatus != null && vo.getSaleStatus() != null) {
            handleStatusChange(product, oldStatus, vo.getSaleStatus());
        }

        return iReturn;
    }

    public TnProductVo getProduct(DataMap param) {
        TnProductVo product = tnProductRepository.selectProductById(param);
        if (product == null) {
            throw new IllegalArgumentException("상품을 찾을 수 없습니다: ");
        }
        return product;
    }

    public List<TnProductVo> getInterestProducts(DataMap param) {
        return tnProductRepository.selectInterestProducts(param);
    }

    // 구매이력 목록
    public List<TnProductVo> getPurchasedProducts(DataMap param) {
        return tnProductRepository.selectPurchasedProducts(param);
    }

    public List<Map<String, Object>> getChatBuyers(DataMap param) {
        return tnProductRepository.findChatBuyersByProductAndSeller(param);
    }

    public void handleStatusChange(TnProductVo p, String oldStatusCode, String newStatusCode) {
        // [수정] 판매중(1)으로 변경될 때만 푸시 발송
        if ("1".equals(newStatusCode) && !"1".equals(oldStatusCode)) {
            String title = "신규 상품 등록";
            String body = "[신상품] 새로운 상품이 등록되었습니다. 지금 확인해보세요!";

            Map<String, String> payload = new java.util.HashMap<>();
            payload.put("type", "product");
            payload.put("targetId", p.getProductId());
            payload.put("title", title);
            payload.put("body", body);

            // [Fix] actorUserNo 추가
            Long actorUserNo = (p.getRegisterNo() != null) ? Long.valueOf(p.getRegisterNo()) : null;

            pushService.sendTargetPush(
                    actorUserNo,
                    List.of("ROLE_PUB", "ROLE_PROJ", "ROLE_SELL"),
                    null,
                    null,
                    null,
                    title,
                    body,
                    "PRODUCT_REGISTER",
                    payload);
        }
    }
}
