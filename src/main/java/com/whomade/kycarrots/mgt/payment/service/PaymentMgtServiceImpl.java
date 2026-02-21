package com.whomade.kycarrots.mgt.payment.service;

import com.whomade.kycarrots.entity.payment.OrderItemVo;
import com.whomade.kycarrots.entity.payment.OrderVo;
import com.whomade.kycarrots.entity.payment.PaymentVo;
import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("paymentMgtService")
@RequiredArgsConstructor
public class PaymentMgtServiceImpl implements PaymentMgtService {

    @Resource(name = "commonMybatisDao")
    private CommonMybatisDao commonMybatisDao;

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${toss.payments.secret-key:test_sk_z60G06bY86xlMG9OnW0VW3W47jyp}")
    private String secretKey;

    @Override
    public DataMap getDashboardStats(DataMap param) {
        return commonMybatisDao.selectOne("mgt.payment.selectPaymentStats", param);
    }

    @Override
    public List<DataMap> getPaymentList(DataMap param) {
        return commonMybatisDao.selectList("mgt.payment.selectRecentPaymentList", param);
    }

    @Override
    public DataMap getPaymentDetail(DataMap param) {
        DataMap detail = commonMybatisDao.selectOne("mgt.payment.selectPaymentDetail", param);
        if (detail != null) {
            Long orderId = detail.getLong("ORDER_ID");
            List<OrderItemVo> items = commonMybatisDao.selectList("mgt.payment.selectOrderItemsByOrderId", orderId);
            detail.put("orderItems", items);
        }
        return detail;
    }

    @Override
    @Transactional
    public DataMap cancelPayment(DataMap param) {
        DataMap result = new DataMap();
        String paymentKey = param.getString("paymentKey");
        String cancelReason = param.getString("cancelReason", "관리자 취소");

        try {
            String authorizations = Base64.getEncoder()
                    .encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Basic " + authorizations);

            Map<String, Object> body = Map.of("cancelReason", cancelReason);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    "https://api.tosspayments.com/v1/payments/" + paymentKey + "/cancel",
                    HttpMethod.POST,
                    entity,
                    new org.springframework.core.ParameterizedTypeReference<Map<String, Object>>() {
                    });

            if (response.getStatusCode() == HttpStatus.OK) {
                PaymentVo paymentVo = new PaymentVo();
                paymentVo.setPgTid(paymentKey);
                paymentVo.setPaymentStatus("CANCELLED");
                commonMybatisDao.update("mgt.payment.updatePaymentStatus", paymentVo);

                PaymentVo fullPaymentVo = commonMybatisDao.selectOne("mgt.payment.selectPaymentByMerchantUid",
                        param.getString("merchantUid"));
                if (fullPaymentVo != null) {
                    OrderVo orderVo = new OrderVo();
                    orderVo.setOrderId(fullPaymentVo.getOrderId());
                    orderVo.setOrderStatus("CANCEL");
                    orderVo.setPaymentStatus("CANCELLED");
                    commonMybatisDao.update("mgt.payment.updateOrderStatus", orderVo);
                }

                result.put("success", true);
            } else {
                result.put("success", false);
                result.put("message", "Toss API error: " + response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Payment cancellation failed", e);
            result.put("success", false);
            result.put("message", e.getMessage());
        }

        return result;
    }
}
