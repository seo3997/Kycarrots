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
import jakarta.annotation.Resource;
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
    private String defaultSecretKey;

    @Resource(name = "mgtBranchService")
    private com.whomade.kycarrots.mgt.branch.service.MgtBranchService mgtBranchService;

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public DataMap createOrder(DataMap param) {
        DataMap result = new DataMap();

        String userNo = param.getString("userNo");
        List<Map<String, Object>> items = (List<Map<String, Object>>) param.get("items");

        String orderNo = "ORDER_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8);

        int totalItemAmount = 0;
        int supplyPriceSum = 0;

        OrderVo orderVo = new OrderVo();
        orderVo.setOrderNo(orderNo);
        orderVo.setUserNo(Long.parseLong(userNo));
        orderVo.setOrderStatus("10");
        orderVo.setPaymentStatus("10");
        orderVo.setReceiverName(param.getString("receiverName"));
        orderVo.setReceiverPhone(param.getString("receiverPhone"));
        orderVo.setZipCode(param.getString("zipCode"));
        orderVo.setAddress1(param.getString("address1"));
        orderVo.setAddress2(param.getString("address2"));
        orderVo.setOrderMemo(param.getString("orderMemo"));
        orderVo.setDeliveryFee(param.get("deliveryFee") == null ? 0 : param.getInt("deliveryFee"));
        orderVo.setTotalItemAmount(totalItemAmount);
        orderVo.setSupplyPriceSum(supplyPriceSum);
        orderVo.setTotalPayAmount(totalItemAmount);
        orderVo.setRegisterNo(Integer.parseInt(userNo));
        orderVo.setUpdusrNo(Integer.parseInt(userNo));
        orderVo.setDiscountAmount(param.get("discountAmount") == null ? 0 : param.getInt("discountAmount"));
        orderVo.setBranchId(param.getLong("branchId"));

        paymentRepository.insertOrder(orderVo);
        Long orderId = orderVo.getOrderId();
        Long branchId = orderVo.getBranchId();

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
            int supplyPrice = 0;
            if (productVo.getSupplyPrice() != null && !productVo.getSupplyPrice().isEmpty()) {
                supplyPrice = (int) Double.parseDouble(productVo.getSupplyPrice());
            }

            int itemTotalAmount = unitPrice * quantity;
            int itemSupplyTotal = supplyPrice * quantity;

            totalItemAmount += itemTotalAmount;
            supplyPriceSum += itemSupplyTotal;

            OrderItemVo orderItemVo = new OrderItemVo();
            orderItemVo.setOrderId(orderId);
            orderItemVo.setBranchId(branchId);
            orderItemVo.setProductId(Long.parseLong(productId));
            orderItemVo.setProductName(productVo.getTitle());
            orderItemVo.setOptionName((String) item.get("optionName"));
            orderItemVo.setUnitPrice(unitPrice);
            orderItemVo.setSupplyPrice(supplyPrice);
            orderItemVo.setSalePrice(unitPrice); // 기본 판매가를 실제 판매가로 기록
            orderItemVo.setQuantity(quantity);
            orderItemVo.setTotalPrice(unitPrice * quantity);
            orderItemVo.setDiscountAmount(0);
            orderItemVo.setFinalPrice(unitPrice * quantity);
            orderItemVo.setRegisterNo(Integer.parseInt(userNo));
            orderItemVo.setUpdusrNo(Integer.parseInt(userNo));

            paymentRepository.insertOrderItem(orderItemVo);
        }

        orderVo.setTotalItemAmount(totalItemAmount);
        orderVo.setSupplyPriceSum(supplyPriceSum);
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
            // Fetch Branch Secret Key
            String secretKey = defaultSecretKey;
            if (orderVo.getBranchId() != null) {
                DataMap branchParam = new DataMap();
                branchParam.put("branchId", orderVo.getBranchId());
                DataMap branchInfo = mgtBranchService.selectBranch(branchParam);
                if (branchInfo != null && branchInfo.getString("TOSS_SECRET_KEY") != null
                        && !branchInfo.getString("TOSS_SECRET_KEY").isEmpty()) {
                    secretKey = branchInfo.getString("TOSS_SECRET_KEY").trim();
                    log.info("Using TOSS_SECRET_KEY from database for branchId: {}", orderVo.getBranchId());
                } else {
                    log.warn("TOSS_SECRET_KEY not found in database for branchId: {}. Using default fallback.",
                            orderVo.getBranchId());
                }
            } else {
                log.info("orderVo.getBranchId() is null. Using default TOSS_SECRET_KEY.");
            }

            log.info("Final secretKey length: {}", (secretKey != null ? secretKey.length() : 0));

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
                paymentVo.setPaymentStatus("30");
                paymentVo.setPaymentMethod((String) responseBody.get("method"));
                paymentVo.setPgProvider("toss");
                paymentVo.setPgTid((String) responseBody.get("paymentKey"));
                paymentVo.setTossPaymentKey((String) responseBody.get("paymentKey"));
                paymentVo.setTossMid((String) responseBody.get("mId"));
                paymentVo.setMerchantUid(orderId);
                paymentVo.setAmountTotal(amount);
                paymentVo.setBranchId(orderVo.getBranchId());

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

                orderVo.setOrderStatus("30");
                orderVo.setPaymentStatus("30");
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
                    orderVo.setOrderStatus("40");
                    orderVo.setPaymentStatus("40");
                    paymentRepository.updateOrderStatus(orderVo);

                    PaymentVo paymentVo = new PaymentVo();
                    paymentVo.setMerchantUid(orderId);
                    paymentVo.setPaymentStatus("40");
                    paymentRepository.updatePaymentStatus(paymentVo);
                }
            }
        }
    }

    @Override
    @Transactional
    public DataMap cancelPayment(String orderNo, String cancelReason, Integer userNo) {
        DataMap result = new DataMap();

        OrderVo orderVo = paymentRepository.selectOrderByNo(orderNo);
        if (orderVo == null) {
            result.put("success", false);
            result.put("message", "주문 정보를 찾을 수 없습니다.");
            return result;
        }

        // PAID 상태일 때만 취소 가능 (또는 준비 상태 등 비즈니스 규칙에 따라)
        if (!"30".equals(orderVo.getOrderStatus())) {
            result.put("success", false);
            result.put("message", "취소 가능한 상태가 아닙니다. (현재 상태: " + orderVo.getOrderStatus() + ")");
            return result;
        }

        PaymentVo paymentVo = paymentRepository.selectPaymentByMerchantUid(orderNo);
        if (paymentVo == null || paymentVo.getPgTid() == null) {
            result.put("success", false);
            result.put("message", "결제 정보를 찾을 수 없습니다.");
            return result;
        }

        try {
            // Fetch Branch Secret Key
            String secretKey = defaultSecretKey;
            if (orderVo.getBranchId() != null) {
                DataMap branchParam = new DataMap();
                branchParam.put("branchId", orderVo.getBranchId());
                DataMap branchInfo = mgtBranchService.selectBranch(branchParam);
                if (branchInfo != null && branchInfo.getString("TOSS_SECRET_KEY") != null
                        && !branchInfo.getString("TOSS_SECRET_KEY").isEmpty()) {
                    secretKey = branchInfo.getString("TOSS_SECRET_KEY");
                }
            }

            String authorizations = Base64.getEncoder()
                    .encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Basic " + authorizations);

            Map<String, Object> body = Map.of("cancelReason", cancelReason);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            String url = "https://api.tosspayments.com/v1/payments/" + paymentVo.getPgTid() + "/cancel";
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                // DB Update
                orderVo.setOrderStatus("40");
                orderVo.setPaymentStatus("40");
                orderVo.setCancelReason(cancelReason);
                orderVo.setUpdusrNo(userNo);
                paymentRepository.updateOrderStatus(orderVo);

                PaymentVo updatePayment = new PaymentVo();
                updatePayment.setPgTid(paymentVo.getPgTid());
                updatePayment.setPaymentStatus("40");
                updatePayment.setUpdusrNo(userNo);
                paymentRepository.updatePaymentStatus(updatePayment);

                result.put("success", true);
                result.put("message", "주문이 정상적으로 취소되었습니다.");
            } else {
                result.put("success", false);
                result.put("message", "토스 API 오류: " + response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Order cancellation failed", e);
            result.put("success", false);
            result.put("message", "취소 처리 중 오류가 발생했습니다: " + e.getMessage());
        }

        return result;
    }
}
