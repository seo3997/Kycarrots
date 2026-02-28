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

    @Operation(summary = "주문 상세 조회 (앱 구매내역 상세)", description = "주문 번호를 기준으로 주문 마스터 정보와 포함된 상품들의 상세 정보(상품명, 이미지 등)를 조회합니다. 앱의 구매내역 상세 페이지에서 사용됩니다.", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(type = "object", example = "{\"order\": {\"orderId\": 1, \"orderNo\": \"ORDER-20240222...\", \"totalPayAmount\": 50000, ...}, \"items\": [{\"productId\": 1, \"productName\": \"유기농 당근\", \"title\": \"산지직송 유기농 당근 1kg\", \"imageUrl\": \"https://.../image.jpg\", \"unitPrice\": 15000, \"quantity\": 2}]}"))),
            @ApiResponse(responseCode = "404", description = "주문을 찾을 수 없음")
    })
    @GetMapping("/{orderNo}")
    public ResponseEntity<Map<String, Object>> getOrderDetail(
            @Parameter(description = "주문번호 (예: ORDER-20240219...)") @PathVariable String orderNo) {
        Map<String, Object> res = new HashMap<>();
        OrderVo orderVo = orderService.selectOrderByNo(orderNo);
        if (orderVo == null) {
            return ResponseEntity.notFound().build();
        }

        List<OrderItemVo> items = orderService.selectOrderItemsByOrderId(orderVo.getOrderId());
        res.put("order", orderVo);
        res.put("items", items);
        return ResponseEntity.ok(res);
    }

}
