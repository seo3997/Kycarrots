package com.whomade.kycarrots.rest.advertise;

import com.whomade.kycarrots.dto.advertise.AdvertiseItem;
import com.whomade.kycarrots.dto.advertise.AdvertiseResponse;
import com.whomade.kycarrots.dto.advertise.TnProductDetailResponse;
import com.whomade.kycarrots.dto.login.LoginResponse;
import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.entity.product.TnProductImageVo;
import com.whomade.kycarrots.entity.product.TnProductVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.encrypt.EncodedTokenizer;
import com.whomade.kycarrots.push.FcmService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/product")
@Slf4j
public class AdvertiseController {
    private final EncodedTokenizer tokenizer;
    private final TnProductService tnProductService;
    private final FcmService fcmService;
    @PostMapping(
            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AdvertiseResponse getListAdvertise(
            @RequestParam("token") String token,
            @RequestParam("ad_code") String adCode,
            @RequestParam("pageno") int pageNo,
            @RequestParam(value = "categoryGroup", required = false) String categoryGroup,
            @RequestParam(value = "categoryMid",    required = false) String categoryMid,
            @RequestParam(value = "categoryScls",   required = false) String categoryScls,
            @RequestParam(value = "areaGroup", required = false) String areaGroup,
            @RequestParam(value = "areaMid",   required = false) String areaMid,
            @RequestParam(value = "areaScls",  required = false) String areaScls,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice
            ) {

        // 실제 구현에서는 token, ad_code, pageno를 활용하여 광고 목록을 조회합니다.
        // 아래는 예제용으로 고정된 데이터를 리턴하는 예시입니다.
        OpUserVO opUserVO = null;
        try {
            opUserVO = tokenizer.getMember(token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataMap param = new DataMap();
        param.put("saleStatus", "1");

        // 페이지당 항목 수
        int pageSize = 7;
        int offset   = (pageNo - 1) * pageSize;
        param.put("offset", offset);
        param.put("limit",  pageSize);
        // 카테고리 필터
        if (categoryGroup != null && !"ALL".equals(categoryGroup)) {
            param.put("categoryGroup", categoryGroup);
        }
        if (categoryMid != null && !"ALL".equals(categoryMid)) {
            param.put("categoryMid", categoryMid);
        }
        if (categoryScls != null && !"ALL".equals(categoryScls)) {
            param.put("categoryScls", categoryScls);
        }

        // 지역 필터
        if (areaGroup != null && !"ALL".equals(areaGroup)) {
            param.put("areaGroup", areaGroup);
        }
        if (areaMid != null && !"ALL".equals(areaMid)) {
            param.put("areaMid", areaMid);
        }
        if (areaScls != null && !"ALL".equals(areaScls)) {
            param.put("areaScls", areaScls);
        }

        // 가격 범위 필터
        if (minPrice != null) {
            param.put("minPrice", minPrice);
        }
        if (maxPrice != null) {
            param.put("maxPrice", maxPrice);
        }


        List<TnProductVo> tnProductVos = tnProductService.selectTbproduct(param);

        /*
        AdvertiseItem item1 = new AdvertiseItem(
                5,
                "▶불백큐, 양천구맛집",
                "http://52.231.229.156/common/img" +
                        "/ad/5/149154878820454.jpg",
                18,
                "- 3G/LTE 환경에서는 과금이 될 수 있습니다.",
                "",
                "불향가득 맛있는 양천구 \"돼지불백\"",
                4.4,
                0
        );

        AdvertiseItem item2 = new AdvertiseItem(
                4,
                "▶karrimor<구미>홈플러스",
                "http://52.231.229.156/common/img" +
                        "/ad/4/149153903390088.jpg",
                18,
                "- 3G/LTE 환경에서는 과금이 될 수 있습니다.",
                "",
                "▶영국 정통 아웃도어 브랜드 '카리모어'◀",
                4.2,
                0
        );

        AdvertiseItem item3 = new AdvertiseItem(
                1,
                "마케팅 망설이지 않아도 됩니다.",
                "http://52.231.229.156/common/img/ad/1/149128947877039.jpg",
                18,
                "- 3G/LTE 환경에서는 과금이 될 수 있습니다.",
                "",
                "마케팅에 대해 모든 궁금한 사항을 속시원하게 풀어드립니다. 6~7년차 광고 전문가들이 포진해 있는 햇님달님. 귀사의 마케팅 컨설팅에 밤낮없이 열중하겠습니다.",
                4.2,
                1
        );
        */
        // 광고 코드는 파라미터 ad_code, 페이지 번호는 pageNo 등을 활용해서 실제 조회를 구현하면 됩니다.
        // 여기서는 예시 데이터로 고정된 세 개의 광고 항목을 리턴합니다.
        AdvertiseResponse response = new AdvertiseResponse();
        response.setItems(tnProductVos);
        return response;
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> registerProductWithImages(
            @RequestPart("product") TnProductVo productVo,
            @RequestPart("imageMetas") List<TnProductImageVo> imageMetas,
            @RequestPart("images") List<MultipartFile> images) {

        try {
            tnProductService.insertProductWithImages(productVo, imageMetas, images);
            return ResponseEntity.ok("등록 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("등록 실패: " + e.getMessage());
        }
    }

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateProductWithImages(
            @RequestPart("product") TnProductVo productVo,
            @RequestPart(name = "imageMetas", required = false) List<TnProductImageVo> imageMetas,
            @RequestPart(name = "images", required = false) List<MultipartFile> images) {
        try {
            tnProductService.updateProductWithImages(productVo, imageMetas, images);
            return ResponseEntity.ok("수정 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("수정 실패: " + e.getMessage());
        }
    }

    @GetMapping("/detail/{productId}")
    public ResponseEntity<TnProductDetailResponse> getProductDetail(@PathVariable Long productId) {
        TnProductDetailResponse detail = tnProductService.getProductDetail(productId);
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
            Long userNo = Long.parseLong(user.getUserNo());

            DataMap result = tnProductService.getProductStatusCounts(userNo);
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
            Long userNo = Long.parseLong(user.getUserNo());

            List<TnProductVo> recentProducts = tnProductService.getRecentProductsByUser(userNo);
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
        fcmService.sendPush(token, title, body);
        return ResponseEntity.ok("푸시 전송 완료");
    }

    @PostMapping("/status/update")
    public ResponseEntity<String> updateProductStatus(
            @RequestParam String token,
            @RequestBody TnProductVo productVo
    ) {
        try {
            // 1. 토큰으로 사용자 조회
            OpUserVO user = tokenizer.getMember(token);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰");
            }
            // 2. 사용자 번호 주입
            productVo.setUserNo(user.getUserNo());

            // 3. 상태 업데이트 실행
            int updated = tnProductService.updateProductStatus(productVo);
            if (updated > 0) {
                return ResponseEntity.ok("상태 변경 성공");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("변경된 항목 없음");
            }
        } catch (Exception e) {
            log.error("상태 변경 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상태 변경 실패: " + e.getMessage());
        }
    }
}