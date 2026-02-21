package com.whomade.kycarrots.service.order;

import com.whomade.kycarrots.entity.payment.OrderItemVo;
import com.whomade.kycarrots.entity.payment.OrderVo;
import com.whomade.kycarrots.entity.payment.PaymentVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.repository.mybatis.order.OrderRepository;
import com.whomade.kycarrots.repository.mybatis.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${toss.payments.secret-key:test_sk_vZnjEJeQVxawBOMzxKXZrPmOoBN0}")
    private String secretKey;

    @Override
    public Page<DataMap> selectPageListOrder(DataMap param, Pageable pageable) {
        param.put("offset", pageable.getOffset());
        param.put("limit", pageable.getPageSize());

        List<DataMap> list = orderRepository.selectPageListOrder(param);
        int total = orderRepository.selectOrderCount(param);

        return new PageImpl<>(list, pageable, total);
    }

    @Override
    public OrderVo selectOrderByNo(String orderNo) {
        return orderRepository.selectOrderByNo(orderNo);
    }

    @Override
    public List<OrderItemVo> selectOrderItemsByOrderId(Long orderId) {
        return orderRepository.selectOrderItemsByOrderId(orderId);
    }

    @Override
    @Transactional
    public DataMap cancelOrder(String orderNo, String cancelReason, Integer userNo) {
        DataMap result = new DataMap();

        OrderVo orderVo = orderRepository.selectOrderByNo(orderNo);
        if (orderVo == null) {
            result.put("success", false);
            result.put("message", "주문 정보를 찾을 수 없습니다.");
            return result;
        }

        if (!"PAID".equals(orderVo.getOrderStatus())) {
            result.put("success", false);
            result.put("message", "취소 가능한 상태가 아닙니다.");
            return result;
        }

        PaymentVo paymentVo = paymentRepository.selectPaymentByMerchantUid(orderNo);
        if (paymentVo == null || paymentVo.getPgTid() == null) {
            result.put("success", false);
            result.put("message", "결제 정보를 찾을 수 없습니다.");
            return result;
        }

        try {
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
                orderVo.setOrderStatus("CANCEL");
                orderVo.setPaymentStatus("CANCEL");
                orderVo.setUpdusrNo(userNo);
                orderRepository.updateOrderStatus(orderVo);

                PaymentVo updatePayment = new PaymentVo();
                updatePayment.setPgTid(paymentVo.getPgTid());
                updatePayment.setPaymentStatus("CANCEL");
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

    @Override
    public List<DataMap> selectPageListOrderMgt(DataMap param) {
        return orderRepository.selectPageListOrder(param);
    }
}
