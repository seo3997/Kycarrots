package com.whomade.kycarrots.repository.mybatis.order;

import com.whomade.kycarrots.entity.payment.OrderItemVo;
import com.whomade.kycarrots.entity.payment.OrderVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final OrderMapper orderMapper;

    public List<DataMap> selectPageListOrder(DataMap param) {
        return orderMapper.selectPageListOrder(param);
    }

    public int selectOrderCount(DataMap param) {
        return orderMapper.selectOrderCount(param);
    }

    public OrderVo selectOrderByNo(String orderNo) {
        return orderMapper.selectOrderByNo(orderNo);
    }

    public OrderVo selectOrderById(Long orderId) {
        return orderMapper.selectOrderById(orderId);
    }

    public List<OrderItemVo> selectOrderItemsByOrderId(Long orderId) {
        return orderMapper.selectOrderItemsByOrderId(orderId);
    }

    public void updateOrderStatus(OrderVo orderVo) {
        orderMapper.updateOrderStatus(orderVo);
    }
}
