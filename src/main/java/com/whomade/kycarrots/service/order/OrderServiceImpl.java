package com.whomade.kycarrots.service.order;

import com.whomade.kycarrots.entity.payment.OrderItemVo;
import com.whomade.kycarrots.entity.payment.OrderVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.repository.mybatis.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import com.whomade.kycarrots.framework.common.page.util.pageNavigationUtil;
import org.springframework.ui.ModelMap;

import java.util.List;

@Slf4j
@Service("orderService")
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Page<DataMap> selectPageListOrder(DataMap param, Pageable pageable) {
        param.put("offset", pageable.getOffset());
        param.put("limit", pageable.getPageSize());

        List<DataMap> list = orderRepository.selectPageListOrder(param);
        int total = orderRepository.selectOrderCount(param);

        return new PageImpl<>(list, pageable, total);
    }

    @Override
    public List<DataMap> selectShopOrderList(ModelMap model, DataMap param) {
        int totalCount = orderRepository.selectOrderCount(param);
        param.put("totalCount", totalCount);

        pageNavigationUtil.createNavigationInfo(model, param);

        param.put("offset", param.get("limitStart"));
        param.put("limit", param.get("limitEnd"));

        List<DataMap> resultList = orderRepository.selectPageListOrder(param);
        model.addAttribute("totalCount", totalCount);

        return resultList;
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
