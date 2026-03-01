package com.whomade.kycarrots.mgt.order.service;

import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

@Service("mgtOrderService")
public class MgtOrderServiceImpl implements MgtOrderService {

    @Resource(name = "commonMybatisDao")
    private CommonMybatisDao commonMybatisDao;

    @Override
    public List<DataMap> selectPageListOrder(ModelMap model, DataMap param) throws Exception {
        int currentPage = Integer.parseInt(param.getString("curPage", "1"));
        int rowCount = Integer.parseInt(param.getString("rowCount", "10"));

        param.put("offset", (currentPage - 1) * rowCount);
        param.put("limit", rowCount);

        List<DataMap> resultList = commonMybatisDao.selectList("mgt.order.selectPageListOrder", param);
        int totalCount = commonMybatisDao.selectOne("mgt.order.selectOrderCount", param);

        model.addAttribute("totalCount", totalCount);

        return resultList;
    }

    @Override
    public DataMap selectDashboardStats(DataMap param) throws Exception {
        String memberCode = param.getString("memberCode");
        if ("ROLE_ADMIN".equals(memberCode)) {
            // ROLE_ADMIN is System Admin dashboard
            return commonMybatisDao.selectOne("mgt.order.selectDashboardStatsAdmin", param);
        } else if ("ROLE_SELL".equals(memberCode)) {
            // ROLE_SELL is HQ dashboard (Image 2)
            return commonMybatisDao.selectOne("mgt.order.selectDashboardStatsProj", param);
        } else {
            // ROLE_PROJ is Branch dashboard (Image 1)
            return commonMybatisDao.selectOne("mgt.order.selectDashboardStatsSell", param);
        }
    }

    @Override
    public List<DataMap> selectDashboardOrderList(DataMap param) throws Exception {
        String memberCode = param.getString("memberCode");
        if ("ROLE_ADMIN".equals(memberCode)) {
            // Admin doesn't necessarily need a specific order list here, or could use HQ
            // one
            return commonMybatisDao.selectList("mgt.order.selectDashboardOrderListProj", param);
        } else if ("ROLE_SELL".equals(memberCode)) {
            return commonMybatisDao.selectList("mgt.order.selectDashboardOrderListProj", param);
        } else {
            return commonMybatisDao.selectList("mgt.order.selectDashboardOrderListSell", param);
        }
    }

    @Override
    public void confirmBranchDeposit(DataMap param) throws Exception {
        param.put("branchDepositStatus", "CONFIRMED");
        commonMybatisDao.update("mgt.order.updateBranchDepositStatus", param);

        // At the same time, if shipping info is provided, update it.
        if (param.getString("deliveryCompanyCode") != null && !param.getString("deliveryCompanyCode").isEmpty()
                && param.getString("trackingNo") != null && !param.getString("trackingNo").isEmpty()) {
            param.put("orderStatus", "SHIPPING");
            commonMybatisDao.update("mgt.order.updateOrderShippingInfo", param);
        }
    }

    @Override
    public void updateOrderShippingInfo(DataMap param) throws Exception {
        param.put("orderStatus", "SHIPPING");
        commonMybatisDao.update("mgt.order.updateOrderShippingInfo", param);
    }
}
