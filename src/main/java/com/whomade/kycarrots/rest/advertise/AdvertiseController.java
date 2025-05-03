package com.whomade.kycarrots.rest.advertise;

import com.whomade.kycarrots.dto.advertise.AdvertiseItem;
import com.whomade.kycarrots.dto.advertise.AdvertiseResponse;
import com.whomade.kycarrots.entity.product.TnProductVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.service.product.TnProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/advertises")
@Slf4j
public class AdvertiseController {
    private final TnProductService tnProductService;
    @PostMapping(
            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AdvertiseResponse getListAdvertise(
            @RequestParam("token") String token,
            @RequestParam("ad_code") String adCode,
            @RequestParam("pageno") int pageNo) {

        // 실제 구현에서는 token, ad_code, pageno를 활용하여 광고 목록을 조회합니다.
        // 아래는 예제용으로 고정된 데이터를 리턴하는 예시입니다.
        DataMap param = new DataMap();
        param.put("saleStatus", "1");
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
}