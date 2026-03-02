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
        param.put("branchDepositStatus", "20");
        commonMybatisDao.update("mgt.order.updateBranchDepositStatus", param);

        // Always set status to 60 (SHIPPING) as requested: "입금확인이 되면 60"
        param.put("orderStatus", "60");
        commonMybatisDao.update("mgt.order.updateOrderShippingInfo", param);
    }

    @Override
    public void updateOrderShippingInfo(DataMap param) throws Exception {
        // If orderStatus is not provided, default to 60 (SHIPPING)
        if (param.getString("orderStatus") == null || param.getString("orderStatus").isEmpty()) {
            param.put("orderStatus", "60");
        }
        commonMybatisDao.update("mgt.order.updateOrderShippingInfo", param);
    }

    @Override
    public void confirmOrder(DataMap param) throws Exception {
        param.put("orderStatus", "99"); // 주문확정
        commonMybatisDao.update("mgt.order.updateOrderShippingInfo", param);
    }
}
