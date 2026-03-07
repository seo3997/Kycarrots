package com.whomade.kycarrots.service.order;

import com.whomade.kycarrots.entity.payment.OrderItemVo;
import com.whomade.kycarrots.entity.payment.OrderVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.repository.mybatis.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("orderService")
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

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
    public OrderVo selectOrderById(Long orderId) {
        return orderRepository.selectOrderById(orderId);
    }

    @Override
    public List<OrderItemVo> selectOrderItemsByOrderId(Long orderId) {
        return orderRepository.selectOrderItemsByOrderId(orderId);
    }

    @Override
    public List<DataMap> selectPageListOrderMgt(DataMap param) {
        return orderRepository.selectPageListOrder(param);
    }
}
