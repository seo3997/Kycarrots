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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @Operation(summary = "주문 상세 조회", description = "주문 번호를 기준으로 주문 마스터 정보와 상품 상세 목록을 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(type = "object", example = "{\"order\": {\"orderId\": 1, \"orderNo\": \"ORDER-123\", ...}, \"items\": [{\"productId\": 1, \"productName\": \"상품A\"}]}"))),
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

    @Operation(summary = "주문 취소", description = "주문번호와 취소 사유를 입력받아 주문을 전액 취소 처리합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "취소 성공", content = @Content(schema = @Schema(type = "object", example = "{\"success\": true, \"message\": \"취소되었습니다.\"}"))),
            @ApiResponse(responseCode = "400", description = "취소 실패 (이미 취소됨, 혹은 잘못된 요청)")
    })
    @PostMapping(value = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> cancelOrder(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "취소 정보", content = @Content(schema = @Schema(type = "object", example = "{\"orderNo\": \"ORDER-123\", \"cancelReason\": \"고객 변심\", \"userNo\": 1}"))) @RequestBody Map<String, Object> param) {
        String orderNo = (String) param.get("orderNo");
        String cancelReason = (String) param.get("cancelReason");
        Integer userNo = (Integer) param.get("userNo");

        DataMap result = orderService.cancelOrder(orderNo, cancelReason, userNo);

        if (result.getBoolean("success")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }
}
