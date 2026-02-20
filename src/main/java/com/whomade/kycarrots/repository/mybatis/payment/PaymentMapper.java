package com.whomade.kycarrots.repository.mybatis.payment;

import com.whomade.kycarrots.entity.payment.OrderItemVo;
import com.whomade.kycarrots.entity.payment.OrderVo;
import com.whomade.kycarrots.entity.payment.PaymentVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaymentMapper {
    // Orders
    int insertOrder(OrderVo orderVo);

    int updateOrderStatus(OrderVo orderVo);

    int updateOrderAmount(OrderVo orderVo);

    OrderVo selectOrderByNo(String orderNo);

    OrderVo selectOrderById(Long orderId);

    // Order Items
    int insertOrderItem(OrderItemVo orderItemVo);

    List<OrderItemVo> selectOrderItemsByOrderId(Long orderId);

    // Payments
    int insertPayment(PaymentVo paymentVo);

    int updatePaymentStatus(PaymentVo paymentVo);

    PaymentVo selectPaymentByMerchantUid(String merchantUid);

    // Admin Dashboard
    DataMap selectPaymentStats(DataMap param);

    List<DataMap> selectRecentPaymentList(DataMap param);

    DataMap selectPaymentDetail(DataMap param);
}
