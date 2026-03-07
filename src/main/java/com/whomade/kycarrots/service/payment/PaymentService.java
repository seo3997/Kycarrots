package com.whomade.kycarrots.service.payment;

import com.whomade.kycarrots.framework.common.object.DataMap;

public interface PaymentService {
    DataMap createOrder(DataMap param);

    DataMap confirmPayment(String paymentKey, String orderNo, Integer amount);

    void handleWebhook(DataMap webhookData);

    DataMap cancelPayment(String orderNo, String cancelReason, Integer userNo);

    DataMap requestReturn(String orderNo, String returnReason, Integer userNo);
}
