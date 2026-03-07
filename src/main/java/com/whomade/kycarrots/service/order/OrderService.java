package com.whomade.kycarrots.service.order;

import com.whomade.kycarrots.entity.payment.OrderItemVo;
import com.whomade.kycarrots.entity.payment.OrderVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Page<DataMap> selectPageListOrder(DataMap param, Pageable pageable);

    OrderVo selectOrderByNo(String orderNo);

    OrderVo selectOrderById(Long orderId);

    List<OrderItemVo> selectOrderItemsByOrderId(Long orderId);

    // Admin methods
    List<DataMap> selectPageListOrderMgt(DataMap param);
}
