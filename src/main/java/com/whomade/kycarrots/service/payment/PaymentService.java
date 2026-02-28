package com.whomade.kycarrots.service.payment;

import com.whomade.kycarrots.framework.common.object.DataMap;

public interface PaymentService {
    DataMap createOrder(DataMap param);

    DataMap confirmPayment(String paymentKey, String orderId, Integer amount);

    void handleWebhook(DataMap webhookData);

    DataMap cancelPayment(String orderNo, String cancelReason, Integer userNo);
}
