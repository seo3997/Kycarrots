package com.whomade.kycarrots.repository.mybatis.payment;

import com.whomade.kycarrots.entity.payment.OrderItemVo;
import com.whomade.kycarrots.entity.payment.OrderVo;
import com.whomade.kycarrots.entity.payment.PaymentVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class PaymentRepository {

    private final PaymentMapper paymentMapper;

    public int insertOrder(OrderVo orderVo) {
        return paymentMapper.insertOrder(orderVo);
    }

    public int updateOrderStatus(OrderVo orderVo) {
        return paymentMapper.updateOrderStatus(orderVo);
    }

    public OrderVo selectOrderByNo(String orderNo) {
        return paymentMapper.selectOrderByNo(orderNo);
    }

    public OrderVo selectOrderById(Long orderId) {
        return paymentMapper.selectOrderById(orderId);
    }

    public int insertOrderItem(OrderItemVo orderItemVo) {
        return paymentMapper.insertOrderItem(orderItemVo);
    }

    public List<OrderItemVo> selectOrderItemsByOrderId(Long orderId) {
        return paymentMapper.selectOrderItemsByOrderId(orderId);
    }

    public int insertPayment(PaymentVo paymentVo) {
        return paymentMapper.insertPayment(paymentVo);
    }

    public int updatePaymentStatus(PaymentVo paymentVo) {
        return paymentMapper.updatePaymentStatus(paymentVo);
    }

    public PaymentVo selectPaymentByMerchantUid(String merchantUid) {
        return paymentMapper.selectPaymentByMerchantUid(merchantUid);
    }

    public DataMap selectPaymentStats(DataMap param) {
        return paymentMapper.selectPaymentStats(param);
    }

    public List<DataMap> selectRecentPaymentList(DataMap param) {
        return paymentMapper.selectRecentPaymentList(param);
    }

    public DataMap selectPaymentDetail(DataMap param) {
        return paymentMapper.selectPaymentDetail(param);
    }
}
