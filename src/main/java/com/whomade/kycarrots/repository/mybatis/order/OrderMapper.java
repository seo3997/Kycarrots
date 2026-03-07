package com.whomade.kycarrots.repository.mybatis.order;

import com.whomade.kycarrots.entity.payment.OrderItemVo;
import com.whomade.kycarrots.entity.payment.OrderVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    List<DataMap> selectPageListOrder(DataMap param);

    int selectOrderCount(DataMap param);

    OrderVo selectOrderByNo(String orderNo);

    OrderVo selectOrderById(Long orderId);

    List<OrderItemVo> selectOrderItemsByOrderId(Long orderId);

    void updateOrderStatus(OrderVo orderVo);
}
