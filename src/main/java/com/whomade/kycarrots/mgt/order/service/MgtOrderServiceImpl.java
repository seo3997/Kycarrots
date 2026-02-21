package com.whomade.kycarrots.mgt.order.service;

import com.whomade.kycarrots.entity.payment.OrderVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.repository.mybatis.order.OrderRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

@Service("mgtOrderService")
public class MgtOrderServiceImpl implements MgtOrderService {

    @Resource(name = "orderRepository")
    private OrderRepository orderRepository;

    @Override
    public List<DataMap> selectPageListOrder(ModelMap model, DataMap param) throws Exception {
        int currentPage = Integer.parseInt(param.getString("curPage", "1"));
        int rowCount = Integer.parseInt(param.getString("rowCount", "10"));

        param.put("offset", (currentPage - 1) * rowCount);
        param.put("limit", rowCount);

        List<DataMap> resultList = orderRepository.selectPageListOrder(param);
        int totalCount = orderRepository.selectOrderCount(param);

        // Paging logic often used in this project
        // (Assuming standard paging utility is available or just passing count back)
        model.addAttribute("totalCount", totalCount);

        return resultList;
    }
}
