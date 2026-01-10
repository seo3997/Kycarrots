package com.whomade.kycarrots.rest.advertise;

import com.whomade.kycarrots.dto.SimpleResultResponse;
import com.whomade.kycarrots.dto.advertise.AdvertiseItem;
import com.whomade.kycarrots.dto.advertise.AdvertiseQueryParams;
import com.whomade.kycarrots.dto.advertise.AdvertiseResponse;
import com.whomade.kycarrots.dto.advertise.TnProductDetailResponse;
import com.whomade.kycarrots.dto.login.LoginResponse;
import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.entity.product.TnProductImageVo;
import com.whomade.kycarrots.entity.product.TnProductVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.PagingUtil;
import com.whomade.kycarrots.framework.common.util.encrypt.EncodedTokenizer;
import com.whomade.kycarrots.push.FcmService;
import com.whomade.kycarrots.service.member.OpUserService;
import com.whomade.kycarrots.service.product.TnProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/product")
@Slf4j
public class AdvertiseController {
    private final EncodedTokenizer tokenizer;
    private final TnProductService tnProductService;
    private final OpUserService opUserService;
    private final FcmService fcmService;

    @PostMapping(
            value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public AdvertiseResponse getListAdvertise(@RequestBody AdvertiseQueryParams q) {
        // 실제 구현에서는 token, ad_code, pageno를 활용하여 광고 목록을 조회합니다.
        // 아래는 예제용으로 고정된 데이터를 리턴하는 예시입니다.
        OpUserVO opUserVO = null;
        try {
            opUserVO = tokenizer.getMember(q.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataMap param = new DataMap();
        param.put("userNo", opUserVO.getUserNo());
        param.put("saleStatus", q.getSaleStatus());
        param.put("memberCode", q.getMemberCode());
        // 페이지당 항목 수
        PagingUtil.applyPaging(param, q.getPageno(), PagingUtil.DEFAULT_PAGE_SIZE);

        List<TnProductVo> tnProductVos = tnProductService.selectTbproduct(param);

        // 광고 코드는 파라미터 ad_code, 페이지 번호는 pageNo 등을 활용해서 실제 조회를 구현하면 됩니다.
        // 여기서는 예시 데이터로 고정된 세 개의 광고 항목을 리턴합니다.
        AdvertiseResponse response = new AdvertiseResponse();
        response.setItems(tnProductVos);
        return response;
    }
    @PostMapping(
            value = "/buyListAdvertise",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public AdvertiseResponse getBuyListAdvertise(@RequestBody AdvertiseQueryParams q) {
        // 실제 구현에서는 token, ad_code, pageno를 활용하여 광고 목록을 조회합니다.
        // 아래는 예제용으로 고정된 데이터를 리턴하는 예시입니다.
        OpUserVO opUserVO = null;
        try {
            opUserVO = tokenizer.getMember(q.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataMap param = new DataMap();
        param.put("saleStatus", q.getSaleStatus());
        // 페이지당 항목 수
        PagingUtil.applyPaging(param, q.getPageno(), PagingUtil.DEFAULT_PAGE_SIZE);
        // 카테고리 필터
        if (q.getCategoryGroup() != null && !"ALL".equals(q.getCategoryGroup())) {
            param.put("categoryGroup", q.getCategoryGroup());
        }
        if (q.getCategoryMid() != null && !"ALL".equals(q.getCategoryMid())) {
            param.put("categoryMid", q.getCategoryMid());
        }
        if (q.getCategoryScls() != null && !"ALL".equals(q.getCategoryScls())) {
            param.put("categoryScls", q.getCategoryScls());
        }

        // 지역 필터
        if (q.getAreaGroup() != null && !"ALL".equals(q.getAreaGroup())) {
            param.put("areaGroup", q.getAreaGroup());
        }
        if (q.getAreaMid() != null && !"ALL".equals(q.getAreaMid())) {
            param.put("areaMid", q.getAreaMid());
        }
        if (q.getAreaScls() != null && !"ALL".equals(q.getAreaScls())) {
            param.put("areaScls", q.getAreaScls());
        }

        // 가격 필터
        if (q.getMinPrice() != null) {
            param.put("minPrice", q.getMinPrice());
        }
        if (q.getMaxPrice() != null) {
            param.put("maxPrice", q.getMaxPrice());
        }

        List<TnProductVo> tnProductVos = tnProductService.selectBuyTbProduct(param);

        // 광고 코드는 파라미터 ad_code, 페이지 번호는 pageNo 등을 활용해서 실제 조회를 구현하면 됩니다.
        // 여기서는 예시 데이터로 고정된 세 개의 광고 항목을 리턴합니다.
        AdvertiseResponse response = new AdvertiseResponse();
        response.setItems(tnProductVos);
        return response;
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SimpleResultResponse> registerProductWithImages(
            @RequestPart("product") TnProductVo productVo,
            @RequestPart("imageMetas") List<TnProductImageVo> imageMetas,
            @RequestPart("images") List<MultipartFile> images) {

        try {
            if(!productVo.getSystemType().isEmpty() && productVo.getSystemType().equals("2")) {
                Long defaultWh = opUserService.findWholesalerNoByUserNo(Long.parseLong(productVo.getUserNo()));
                if (defaultWh == null) {
                    return ResponseEntity.status(HttpStatus.PRECONDITION_REQUIRED)
                            .body(SimpleResultResponse.fail("중간센터 미지정. 먼저 기본 중간센터를 설정하세요."));
                }
                productVo.setWholesalerNo(defaultWh+"");
            }

            tnProductService.insertProductWithImages(productVo, imageMetas, images);
            return ResponseEntity.ok(SimpleResultResponse.ok("등록 성공"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(SimpleResultResponse.fail("등록 실패: " + e.getMessage()));
        }
    }

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SimpleResultResponse> updateProductWithImages(
            @RequestPart("product") TnProductVo productVo,
            @RequestPart(name = "imageMetas", required = false) List<TnProductImageVo> imageMetas,
            @RequestPart(name = "images", required = false) List<MultipartFile> images) {
        try {
            tnProductService.updateProductWithImages(productVo, imageMetas, images);
            return ResponseEntity.ok(SimpleResultResponse.ok("수정 성공"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(SimpleResultResponse.fail("수정 실패: " + e.getMessage()));
        }
    }

    @GetMapping("/detail/{productId}")
    public ResponseEntity<TnProductDetailResponse> getProductDetail(@PathVariable Long productId
            ,@RequestParam(name = "userNo", required = false) Long userNo) {
        DataMap param = new DataMap();
        param.put("productId", productId);
        param.put("userNo", userNo);
        TnProductDetailResponse detail = tnProductService.getProductDetail(param);
        return ResponseEntity.ok(detail);
    }

    @PostMapping("/image/delete")
    public ResponseEntity<String> deleteProductImage(@RequestParam("imageId") String imageId) {
        try {
            long id = Long.parseLong(imageId); // 문자열 → long 변환
            tnProductService.deleteImageById(id);
            return ResponseEntity.ok("이미지 삭제 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("이미지 삭제 실패: " + e.getMessage());
        }
    }
    @GetMapping("/dashboard")
    public ResponseEntity<DataMap> getProductDashboard(@RequestParam("token") String token) {
        try {
            OpUserVO user = tokenizer.getMember(token);
            DataMap param = new DataMap();
            param.put("userNo", user.getUserNo());
            param.put("memberCode", user.getMemberCode());

            DataMap result = tnProductService.getProductStatusCounts(param);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("상품 대시보드 정보 조회 실패", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/recent")
    public ResponseEntity<List<TnProductVo>> getRecentProducts(@RequestParam("token") String token) {
        try {
            OpUserVO user = tokenizer.getMember(token);
            DataMap param = new DataMap();
            param.put("userNo", user.getUserNo());
            param.put("memberCode", user.getMemberCode());
            List<TnProductVo> recentProducts = tnProductService.getRecentProductsByUser(param);
            return ResponseEntity.ok(recentProducts);
        } catch (Exception e) {
            log.error("최근 상품 조회 실패", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendPush(
            @RequestParam String token,
            @RequestParam String title,
            @RequestParam String body
    ) {
        //fcmService.sendPush(token, title, body);
        return ResponseEntity.ok("푸시 전송 완료");
    }

    @PostMapping("/status/update")
    public ResponseEntity<SimpleResultResponse> updateProductStatus(
            @RequestParam String token,
            @RequestBody TnProductVo productVo
    ) {
        try {
            // 1. 토큰으로 사용자 조회
            OpUserVO user = tokenizer.getMember(token);
            if (user == null) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(SimpleResultResponse.fail("유효하지 않은 토큰"));
            }

            // 2. 사용자 번호 주입
            productVo.setUserNo(user.getUserNo());

            // 3. 상태 업데이트 실행
            int updated = tnProductService.updateProductStatus(productVo);
            if (updated > 0) {
                return ResponseEntity.ok(
                        SimpleResultResponse.ok("상태 변경 성공")
                );
            } else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(SimpleResultResponse.fail("변경된 항목 없음"));
            }

        } catch (Exception e) {
            log.error("상태 변경 실패", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(SimpleResultResponse.fail("상태 변경 실패"));
        }
    }
    @GetMapping(value = "/interests/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public AdvertiseResponse getInterestList(
            @RequestParam("token") String token,
            @RequestParam("pageno") int pageNo
    ) {
        OpUserVO user;
        try {
            user = tokenizer.getMember(token);
        } catch (Exception e) {
            throw new IllegalArgumentException("유효하지 않은 토큰");
        }

        DataMap param = new DataMap();
        param.put("userNo", Long.parseLong(user.getUserNo()));

        PagingUtil.applyPaging(param, pageNo, PagingUtil.DEFAULT_PAGE_SIZE);
        List<TnProductVo> items = tnProductService.getInterestProducts(param);

        AdvertiseResponse res = new AdvertiseResponse();
        res.setItems(items);
        return res;
    }

    @GetMapping(value = "/purchases/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public AdvertiseResponse getPurchaseList(
            @RequestParam("token") String token,
            @RequestParam("pageno") int pageNo
    ) {
        OpUserVO user;
        try {
            user = tokenizer.getMember(token);
        } catch (Exception e) {
            throw new IllegalArgumentException("유효하지 않은 토큰");
        }

        DataMap param = new DataMap();
        param.put("userNo", Long.parseLong(user.getUserNo()));

        PagingUtil.applyPaging(param, pageNo, PagingUtil.DEFAULT_PAGE_SIZE);
        List<TnProductVo> items = tnProductService.getPurchasedProducts(param);

        AdvertiseResponse res = new AdvertiseResponse();
        res.setItems(items);
        return res;
    }

    @GetMapping("/chat/buyers")
    public List<Map<String,Object>> buyers(@RequestParam Long productId,
                                           @RequestParam String sellerId) {
        DataMap param = new DataMap();
        param.put("productId", productId);
        param.put("sellerId", sellerId);

        return tnProductService.getChatBuyers(param);
    }

}