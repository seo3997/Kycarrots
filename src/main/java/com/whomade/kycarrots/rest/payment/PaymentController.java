package com.whomade.kycarrots.rest.payment;

import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/order/create")
    public DataMap createOrder(@RequestBody DataMap param) {
        return paymentService.createOrder(param);
    }

    @PostMapping("/confirm")
    public DataMap confirmPayment(@RequestBody DataMap param) {
        String paymentKey = param.getString("paymentKey");
        String orderId = param.getString("orderId");
        Integer amount = param.getInt("amount");

        return paymentService.confirmPayment(paymentKey, orderId, amount);
    }

    @PostMapping("/webhook")
    public void handleWebhook(@RequestBody DataMap param) {
        paymentService.handleWebhook(param);
    }
}
