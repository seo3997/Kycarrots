package com.whomade.kycarrots.mgt.order.service;

import com.whomade.kycarrots.framework.common.object.DataMap;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface MgtOrderService {
    List<DataMap> selectPageListOrder(ModelMap model, DataMap param) throws Exception;

    DataMap selectDashboardStats(DataMap param) throws Exception;

    List<DataMap> selectDashboardOrderList(DataMap param) throws Exception;

    void confirmBranchDeposit(DataMap param) throws Exception;

    void updateOrderShippingInfo(DataMap param) throws Exception;
}
