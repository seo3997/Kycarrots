package com.whomade.kycarrots.service.payment;

import com.whomade.kycarrots.entity.payment.OrderItemVo;
import com.whomade.kycarrots.entity.payment.OrderVo;
import com.whomade.kycarrots.entity.payment.PaymentVo;
import com.whomade.kycarrots.entity.product.TnProductVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.repository.mybatis.payment.PaymentRepository;
import com.whomade.kycarrots.repository.mybatis.product.TnProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final TnProductRepository tnProductRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${toss.payments.secret-key:test_sk_vZnjEJeQVxawBOMzxKXZrPmOoBN0}")

    private String secretKey;

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public DataMap createOrder(DataMap param) {
        DataMap result = new DataMap();

        String userNo = param.getString("userNo");
        List<Map<String, Object>> items = (List<Map<String, Object>>) param.get("items");

        String orderNo = "ORDER_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8);

        int totalItemAmount = 0;

        OrderVo orderVo = new OrderVo();
        orderVo.setOrderNo(orderNo);
        orderVo.setUserNo(Long.parseLong(userNo));
        orderVo.setOrderStatus("READY");
        orderVo.setPaymentStatus("READY");
        orderVo.setReceiverName(param.getString("receiverName"));
        orderVo.setReceiverPhone(param.getString("receiverPhone"));
        orderVo.setZipCode(param.getString("zipCode"));
        orderVo.setAddress1(param.getString("address1"));
        orderVo.setAddress2(param.getString("address2"));
        orderVo.setOrderMemo(param.getString("orderMemo"));
        orderVo.setDeliveryFee(param.get("deliveryFee") == null ? 0 : param.getInt("deliveryFee"));
        orderVo.setTotalItemAmount(totalItemAmount);
        orderVo.setTotalPayAmount(totalItemAmount);
        orderVo.setRegisterNo(Integer.parseInt(userNo));
        orderVo.setUpdusrNo(Integer.parseInt(userNo));
        orderVo.setDiscountAmount(param.get("discountAmount") == null ? 0 : param.getInt("discountAmount"));

        paymentRepository.insertOrder(orderVo);
        Long orderId = orderVo.getOrderId();

        for (Map<String, Object> item : items) {
            String productId = String.valueOf(item.get("productId"));
            int quantity = Integer.parseInt(String.valueOf(item.get("quantity")));

            DataMap productParam = new DataMap();
            productParam.put("productId", productId);
            TnProductVo productVo = tnProductRepository.selectProductById(productParam);

            if (productVo == null) {
                throw new RuntimeException("Product not found: " + productId);
            }

            int unitPrice = (int) Double.parseDouble(productVo.getPrice());
            int itemTotalAmount = unitPrice * quantity;
            totalItemAmount += itemTotalAmount;

            OrderItemVo orderItemVo = new OrderItemVo();
            orderItemVo.setOrderId(orderId);
            orderItemVo.setProductId(Long.parseLong(productId));
            orderItemVo.setProductName(productVo.getTitle());
            orderItemVo.setOptionName((String) item.get("optionName"));
            orderItemVo.setUnitPrice(unitPrice);
            orderItemVo.setQuantity(quantity);
            orderItemVo.setRegisterNo(Integer.parseInt(userNo));
            orderItemVo.setUpdusrNo(Integer.parseInt(userNo));

            paymentRepository.insertOrderItem(orderItemVo);
        }

        orderVo.setTotalItemAmount(totalItemAmount);
        orderVo.setTotalPayAmount(totalItemAmount + orderVo.getDeliveryFee() - orderVo.getDiscountAmount());
        paymentRepository.updateOrderAmount(orderVo);

        result.put("success", true);
        result.put("orderId", orderId);
        result.put("orderNo", orderNo);
        result.put("amount", orderVo.getTotalPayAmount());
        result.put("orderName", items.size() > 1 ? items.get(0).get("productName") + " 외 " + (items.size() - 1) + "건"
                : items.get(0).get("productName"));

        return result;
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public DataMap confirmPayment(String paymentKey, String orderId, Integer amount) {
        DataMap result = new DataMap();

        // 1. Amount verification
        OrderVo orderVo = paymentRepository.selectOrderByNo(orderId);
        if (orderVo == null) {
            result.put("success", false);
            result.put("message", "Order not found");
            return result;
        }

        if (!orderVo.getTotalPayAmount().equals(amount)) {
            result.put("success", false);
            result.put("message", "Amount mismatch. Possible forgery detected.");
            return result;
        }

        // 2. Toss Payments Confirm API Call
        try {
            String authorizations = Base64.getEncoder()
                    .encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Basic " + authorizations);

            Map<String, Object> body = Map.of(
                    "paymentKey", paymentKey,
                    "orderId", orderId,
                    "amount", amount);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    "https://api.tosspayments.com/v1/payments/confirm",
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    });
            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = response.getBody();

                // 3. Update DB
                PaymentVo paymentVo = new PaymentVo();
                paymentVo.setOrderId(orderVo.getOrderId());
                paymentVo.setUserNo(orderVo.getUserNo());
                paymentVo.setPaymentStatus("PAID");
                paymentVo.setPaymentMethod((String) responseBody.get("method"));
                paymentVo.setPgProvider("toss");
                paymentVo.setPgTid((String) responseBody.get("paymentKey"));
                paymentVo.setMerchantUid(orderId);
                paymentVo.setAmountTotal(amount);

                // Set default values for NOT NULL columns
                paymentVo.setAmountTaxFree(responseBody.get("taxFreeAmount") != null
                        ? ((Number) responseBody.get("taxFreeAmount")).intValue()
                        : 0);
                paymentVo.setAmountVat(
                        responseBody.get("vat") != null ? ((Number) responseBody.get("vat")).intValue() : 0);
                paymentVo.setCurrency(
                        responseBody.get("currency") != null ? (String) responseBody.get("currency") : "KRW");
                paymentVo.setCardInstallmentMonth(0);

                // Audit info
                paymentVo.setRegisterNo(orderVo.getRegisterNo());
                paymentVo.setUpdusrNo(orderVo.getUpdusrNo());

                Map<String, Object> card = (Map<String, Object>) responseBody.get("card");
                if (card != null) {
                    paymentVo.setCardCompany((String) card.get("company"));
                    paymentVo.setCardNumberMasked((String) card.get("number"));
                    if (card.get("installmentPlanMonths") != null) {
                        paymentVo.setCardInstallmentMonth(((Number) card.get("installmentPlanMonths")).intValue());
                    }
                }

                Map<String, Object> receipt = (Map<String, Object>) responseBody.get("receipt");
                if (receipt != null) {
                    paymentVo.setReceiptUrl((String) receipt.get("url"));
                }

                paymentRepository.insertPayment(paymentVo);

                orderVo.setOrderStatus("PAID");
                orderVo.setPaymentStatus("PAID");
                paymentRepository.updateOrderStatus(orderVo);

                result.put("success", true);
                result.put("paymentVo", paymentVo);
            } else {
                result.put("success", false);
                result.put("message", "Toss API error: " + response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Payment confirmation failed", e);
            result.put("success", false);
            result.put("message", e.getMessage());
        }

        return result;
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public void handleWebhook(DataMap webhookData) {
        String eventType = webhookData.getString("eventType");
        Map<String, Object> data = (Map<String, Object>) webhookData.get("data");

        if ("PAYMENT_STATUS_CHANGED".equals(eventType)) {
            String status = (String) data.get("status");
            String orderId = (String) data.get("orderId");

            if ("DONE".equals(status)) {
                // Already handled by confirm API usually, but good for redundancy
            } else if ("CANCELED".equals(status)) {
                OrderVo orderVo = paymentRepository.selectOrderByNo(orderId);
                if (orderVo != null) {
                    orderVo.setOrderStatus("CANCEL");
                    orderVo.setPaymentStatus("CANCELLED");
                    paymentRepository.updateOrderStatus(orderVo);

                    PaymentVo paymentVo = new PaymentVo();
                    paymentVo.setMerchantUid(orderId);
                    paymentVo.setPaymentStatus("CANCELLED");
                    paymentRepository.updatePaymentStatus(paymentVo);
                }
            }
        }
    }
}
