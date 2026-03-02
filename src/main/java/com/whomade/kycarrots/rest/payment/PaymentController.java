package com.whomade.kycarrots.rest.payment;

import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.service.payment.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Payment", description = "결제 관련 API (주문 생성, 승인, 웹훅)")
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

        private final PaymentService paymentService;

        @Operation(summary = "주문서 생성", description = "토스 결제창 호출 전, 서버에 주문 정보를 미리 생성합니다.", responses = {
                        @ApiResponse(responseCode = "200", description = "성공 (주문성공)", content = @Content(schema = @Schema(type = "object", example = "{ \"success\": true, \"orderId\": 123, \"orderNo\": \"ORDER-1708348400000\", \"amount\": 53000, \"orderName\": \"상품A 외 1건\" }")))
        })
        @PostMapping("/order/create")
        public DataMap createOrder(
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "주문 데이터 (In)", content = @Content(schema = @Schema(type = "object", example = "{\"userNo\": 1, \"totalItemAmount\": 50000, \"deliveryFee\": 3000, \"discountAmount\": 0, \"totalPayAmount\": 53000, \"receiverName\": \"홍길동\", \"receiverPhone\": \"010-1234-5678\", \"zipCode\": \"12345\", \"address1\": \"서울시 강남구...\", \"address2\": \"101호\", \"orderMemo\": \"배송 전 연락 바랍니다.\", \"items\": [{\"productId\": 1, \"quantity\": 1, \"optionName\": \"XL\"}]}"))) @RequestBody DataMap param) {
                return paymentService.createOrder(param);
        }

        @Operation(summary = "결제 승인", description = "토스 결제창 인증 후 받은 키들로 최종 승인을 요청합니다.", responses = {
                        @ApiResponse(responseCode = "200", description = "승인 결과", content = @Content(schema = @Schema(type = "object", example = "{ \"success\": true, \"message\": \"Payment confirmed\" }")))
        })
        @PostMapping("/confirm")
        public DataMap confirmPayment(
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "승인 데이터 (In)", content = @Content(schema = @Schema(type = "object", example = "{\"paymentKey\": \"tgen_20240219...\", \"orderId\": \"ORDER-1708348400000\", \"amount\": 53000}"))) @RequestBody DataMap param) {
                String paymentKey = param.getString("paymentKey");
                String orderId = param.getString("orderId");
                Integer amount = param.getInt("amount");

                return paymentService.confirmPayment(paymentKey, orderId, amount);
        }

        @Operation(summary = "토스 웹훅 수신", description = "결제 상태 변경, 가상계좌 입금 등을 토스로부터 비동기로 수신합니다.", responses = {
                        @ApiResponse(responseCode = "200", description = "성공 수신")
        })
        @PostMapping("/webhook")
        public void handleWebhook(
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "토스 웹훅 데이터 (가상계좌 입금 등)", content = @Content(schema = @Schema(type = "object", example = "{\"eventType\": \"PAYMENT_STATUS_CHANGED\", \"data\": {\"paymentKey\": \"tgen_2024...\", \"status\": \"DONE\", \"orderId\": \"ORDER-1708348400000\", \"totalAmount\": 53000}}"))) @RequestBody DataMap param) {
                paymentService.handleWebhook(param);
        }

        @Operation(summary = "결제 취소", description = "주문 번호와 취소 사유를 받아 결제를 취소합니다.", responses = {
                        @ApiResponse(responseCode = "200", description = "취소 결과", content = @Content(schema = @Schema(type = "object", example = "{ \"success\": true, \"message\": \"주문이 정상적으로 취소되었습니다.\" }")))
        })
        @PostMapping("/cancel")
        public DataMap cancelPayment(
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "취소 데이터 (In)", content = @Content(schema = @Schema(type = "object", example = "{\"orderNo\": \"ORDER-1708348400000\", \"cancelReason\": \"고객 변심\", \"userNo\": 1}"))) @RequestBody DataMap param) {
                String orderNo = param.getString("orderNo");
                String cancelReason = param.getString("cancelReason");
                Integer userNo = param.getInt("userNo");

                return paymentService.cancelPayment(orderNo, cancelReason, userNo);
        }

        @Operation(summary = "반품 요청", description = "배송 중 또는 배송 완료 상태에서 반품을 요청합니다.", responses = {
                        @ApiResponse(responseCode = "200", description = "반품 결과", content = @Content(schema = @Schema(type = "object", example = "{ \"success\": true, \"message\": \"반품 요청이 정상적으로 접수되었습니다.\" }")))
        })
        @PostMapping("/return")
        public DataMap requestReturn(
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "반품 데이터 (In)", content = @Content(schema = @Schema(type = "object", example = "{\"orderNo\": \"ORDER-1708348400000\", \"returnReason\": \"단순 변심\", \"userNo\": 1}"))) @RequestBody DataMap param) {
                String orderNo = param.getString("orderNo");
                String returnReason = param.getString("returnReason");
                Integer userNo = param.getInt("userNo");

                return paymentService.requestReturn(orderNo, returnReason, userNo);
        }
}
