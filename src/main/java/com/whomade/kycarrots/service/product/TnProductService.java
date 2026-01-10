package com.whomade.kycarrots.service.product;

import com.whomade.kycarrots.config.FileStorageProperties;
import com.whomade.kycarrots.dto.advertise.TnProductDetailResponse;
import com.whomade.kycarrots.entity.product.TnProductImageVo;
import com.whomade.kycarrots.entity.product.TnProductVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.file.FileUtil;
import com.whomade.kycarrots.push.FcmService;
import com.whomade.kycarrots.push.PushTargetDto;
import com.whomade.kycarrots.push.SaleStatus;
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
import java.util.LinkedHashMap;
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

    @Value("${file.product.public-url}")
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
    public void insertProductWithImages(TnProductVo productVo, List<TnProductImageVo> imageMetas, List<MultipartFile> files) throws IOException {
        tnProductRepository.insertTbProduct(productVo);

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            TnProductImageVo meta = imageMetas.get(i);

            if (!file.isEmpty()) {

                String baseDir = fileStorageProperties.getProduct().getUploadDir();
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
            long wholesalerNo = Long.parseLong(productVo.getWholesalerNo());
            PushTargetDto centerUsers = tnProductRepository.selectPushTargetsByProductId(wholesalerNo); // token + userId
            if(centerUsers != null) {
                messaeTitle = "상품 승인 요청";
                messaeBody = productTitle + " 상품이 등록되었습니다. 승인해주세요.";
                fcmService.sendPushToUserAndLog(
                        centerUsers.getDeviceType(),
                        centerUsers.getUserNo(),
                        centerUsers.getPushToken(),
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
        log.debug("imageMetas:",imageMetas);
        log.debug("imageMetas Size:",imageMetas.size());
        // 2. 이미지 메타 정보와 파일 동기화
        for (int i = 0; i < imageMetas.size(); i++) {
            TnProductImageVo meta = imageMetas.get(i);
            MultipartFile file = (images != null && images.size() > i) ? images.get(i) : null;

            boolean isNew = (meta.getImageId() == null); // imageId 없으면 새 이미지

            // 새 이미지 추가
            if (isNew && file != null && !file.isEmpty()) {
                File destFile = FileUtil.saveFile(file, fileStorageProperties.getProduct().getUploadDir(), productVo.getProductId());
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
                    File destFile = FileUtil.saveFile(file, fileStorageProperties.getProduct().getUploadDir(), productVo.getProductId());
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

    public DataMap getProductStatusCounts(DataMap param) {
        return tnProductRepository.selectProductStatusCounts(param);
    }

    public List<TnProductVo> getRecentProductsByUser(DataMap param){
        return tnProductRepository.selectRecentProductsByUser(param);
    }

    public int updateProductStatus(TnProductVo vo) {
        int iReturn =0;
        // 1) 기존 상태 조회
        String productId = vo.getProductId();
        DataMap param = new DataMap();
        param.put("productId", productId);
        TnProductVo product = tnProductRepository.selectProductById(param);
        String oldStatus = product.getSaleStatus();

        iReturn = tnProductRepository.updateProductStatus(vo);

        //중간센터 도매상용
        if (vo.getSystemType().equals("2")) {
            if (vo != null && oldStatus != null && vo.getSaleStatus() != null) {
                handleStatusChange(product, oldStatus, vo.getSaleStatus());
            }
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
    public List<Map<String,Object>> getChatBuyers(DataMap param) {
        return tnProductRepository.findChatBuyersByProductAndSeller(param);
    }

    public void handleStatusChange(TnProductVo p, String oldStatusCode, String newStatusCode) {
        var oldS = SaleStatus.of(oldStatusCode);
        var newS = SaleStatus.of(newStatusCode);
        Map<String, String> payload = new LinkedHashMap<>(); // 순서 유지해서 로그 가독성 ↑

        // 0->1: 승인됨 → 모든 구매자에게 브로드캐스트
        if (oldS == SaleStatus.REQUEST && newS == SaleStatus.ON_SALE) {
            String title = "신규 상품 등록";
            String body  = p.getTitle() + " 상품이 판매중으로 등록되었습니다.";

            payload.put("type", "product");
            payload.put("productId", p.getProductId());
            payload.put("userId", p.getUserId());
            payload.put("title", title);
            payload.put("body", body);
            log.debug("####payload[" + payload + "]");

            fcmService.sendPushToTopic(
                    "ROLE_PUB",
                    title,
                    body,
                    payload
            );
        }

        // 0->98: 반려됨 → 판매자에게 수정요청
        else if (oldS == SaleStatus.REQUEST && newS == SaleStatus.REJECT) {
            String title = "상품 반려 안내";
            String body  = p.getTitle() + " 상품이 반려되었습니다. 내용을 수정 후 재승인 요청해주세요.";
            // 판매자 단일 대상 푸시 (토큰/유저 조회)
            PushTargetDto seller = tnProductRepository.selectPushTargetsByProductId(Long.parseLong(p.getUserNo())); // userNo, pushToken 등
            if (seller != null) {
                payload.put("type", "product");
                payload.put("productId", p.getProductId());
                payload.put("userId", p.getUserId());
                payload.put("title", title);
                payload.put("body", body);
                log.debug("####payload[" + payload + "]");

                fcmService.sendPushToUserAndLog(
                        seller.getDeviceType(),
                        seller.getUserNo(),
                        seller.getPushToken(),
                        title,
                        body,
                        p.getProductId(),
                        "반려",
                        payload
                );
            }
        }

        // 98->0: 재승인 요청 → 중간센터/도매상에게 알림
        else if (oldS == SaleStatus.REJECT && newS == SaleStatus.REQUEST) {
            String title = "재승인 요청";
            String body  = p.getTitle() + " 상품이 수정되어 재승인 요청되었습니다.";
            long wholesalerNo = Long.parseLong(p.getWholesalerNo());
            PushTargetDto centerUsers = tnProductRepository.selectPushTargetsByProductId(wholesalerNo);
            if (centerUsers != null) {
                payload.put("type", "product");
                payload.put("productId", p.getProductId());
                payload.put("userId", p.getUserId());
                payload.put("title", title);
                payload.put("body", body);
                log.debug("####payload[" + payload + "]");

                fcmService.sendPushToUserAndLog(
                        centerUsers.getDeviceType(),
                        centerUsers.getUserNo(),
                        centerUsers.getPushToken(),
                        title,
                        body,
                        p.getProductId(),
                        "재승인요청",
                        payload
                );
            }
        }

        // 필요 시: 1->99(판매완료) 등도 여기서 추가 가능
        else if (oldS == SaleStatus.ON_SALE && newS == SaleStatus.DONE) {
            String title = "판매 완료";
            String body  = p.getTitle() + " 상품이 판매 완료되었습니다.";

            payload = new LinkedHashMap<>();
            payload.put("type", "product");
            payload.put("productId", p.getProductId());
            payload.put("userId", p.getUserId());
            payload.put("title", title);
            payload.put("body", body);
            log.debug("####payload {}", payload);

            // 판매자 단건 (p.getUserId()를 판매자 ID로 사용)
            PushTargetDto seller = tnProductRepository.selectPushTargetsByProductId(Long.parseLong(p.getUserNo()));
            if (seller != null) {
                fcmService.sendPushToUserAndLog(
                        seller.getDeviceType(),
                        seller.getUserNo(),
                        seller.getPushToken(),
                        title,
                        body,
                        p.getProductId(),
                        "판매완료",
                        payload
                );
            } else {
                log.warn("판매완료 푸시 스킵: 판매자 토큰 없음 userId={}", p.getUserId());
            }
        }
    }
}
