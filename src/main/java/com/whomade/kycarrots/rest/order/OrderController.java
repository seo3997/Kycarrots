package com.whomade.kycarrots.rest.order;

import com.whomade.kycarrots.entity.payment.OrderItemVo;
import com.whomade.kycarrots.entity.payment.OrderVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Order", description = "주문(결제 완료/취소) 상품 목록 및 상세 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "구매자별 주문 상품 목록 조회 (구매내역)", description = "구매자의 회원번호를 기준으로 결제 완료된 상품 주문 목록을 조회합니다. (READY 상태 제외)")
    @GetMapping("/buyer/{buyerNo}")
    public Page<DataMap> listByBuyer(
            @Parameter(description = "구매자 회원번호") @PathVariable Long buyerNo,
            @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지당 건수") @RequestParam(defaultValue = "20") int size) {
        DataMap param = new DataMap();
        param.put("userNo", buyerNo);
        return orderService.selectPageListOrder(param, PageRequest.of(page, size));
    }

    @Operation(summary = "주문 상세 조회 (앱 구매내역 상세)", description = "주문 번호를 기준으로 주문 마스터 정보와 포함된 상품들의 상세 정보(상품명, 이미지 등)를 조회합니다. "
            +
            "orderStatusNm 필드에 주문 상태 명칭이 포함되며, 결제 취소 기능을 위해 paymentId, pgTid 정보가 포함됩니다. " +
            "주문 상태 코드: 10(결제대기), 20(결제실패), 30(결제완료), 40(주문취소), 50(배송준비중), 60(배송중), 70(배송완료), 80(반품요청), 90(교환완료)")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(type = "object", example = "{\"order\": {\"orderId\": 1, \"orderNo\": \"ORDER-20240222...\", \"orderStatus\": \"30\", \"orderStatusNm\": \"결제완료\", \"totalPayAmount\": 50000, \"paymentId\": 123, \"pgTid\": \"toss_tid_123\", ...}, \"items\": [{\"productId\": 1, \"productName\": \"유기농 당근\", \"title\": \"산지직송 유기농 당근 1kg\", \"imageUrl\": \"https://.../image.jpg\", \"unitPrice\": 15000, \"quantity\": 2}]}")))
    @ApiResponse(responseCode = "404", description = "주문을 찾을 수 없음")
    @GetMapping("/{orderId}")
    public ResponseEntity<Map<String, Object>> getOrderDetail(
            @Parameter(description = "주문ID (PK)") @PathVariable Long orderId) {
        Map<String, Object> res = new HashMap<>();

        // 결제 취소(환불) 기능을 위해 paymentId, pgTid 정보를 포함하여 주문 상세를 조회합니다.
        OrderVo orderVo = orderService.selectOrderById(orderId);
        if (orderVo == null) {
            return ResponseEntity.notFound().build();
        }

        List<OrderItemVo> items = orderService.selectOrderItemsByOrderId(orderVo.getOrderId());
        res.put("order", orderVo);
        res.put("items", items);
        return ResponseEntity.ok(res);
    }

}
