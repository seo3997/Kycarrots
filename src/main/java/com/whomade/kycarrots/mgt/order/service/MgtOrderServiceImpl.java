package com.whomade.kycarrots.mgt.order.service;

import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.push.PushService;
import jakarta.annotation.Resource;
import java.util.Arrays;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

@Service("mgtOrderService")
public class MgtOrderServiceImpl implements MgtOrderService {

    @Resource(name = "commonMybatisDao")
    private CommonMybatisDao commonMybatisDao;

    @Resource
    private PushService pushService;

    @Override
    public List<DataMap> selectPageListOrder(ModelMap model, DataMap param) throws Exception {
        // Use limitStart and limitEnd set by pageNavigationUtil
        int limit = Integer.parseInt(param.getString("limitEnd", "5"));
        int offset = Integer.parseInt(param.getString("limitStart", "0"));

        param.put("offset", offset);
        param.put("limit", limit);

        List<DataMap> resultList = commonMybatisDao.selectList("mgt.order.selectPageListOrder", param);
        int totalCount = commonMybatisDao.selectOne("mgt.order.selectOrderCount", param);
        param.put("totalCount", totalCount);
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
        param.put("branchDepositStatus", "30"); // Status 30: DEPOSITED (입금완료)
        commonMybatisDao.update("mgt.order.updateBranchDepositStatus", param);

        // Always set status to 60 (SHIPPING) as requested: "입금확인이 되면 60"
        param.put("orderStatus", "60");
        commonMybatisDao.update("mgt.order.updateOrderShippingInfo", param);
    }

    @Override
    public void requestBranchDeposit(DataMap param) throws Exception {
        param.put("branchDepositStatus", "20"); // Status 20: BRREQUESR (입금확인요청)
        commonMybatisDao.update("mgt.order.updateBranchDepositStatus", param);

        // Send push to HQ (ROLE_SELL)
        String orderNo = param.getString("orderNo");
        String branchName = param.getString("branchName");

        pushService.sendTargetPush(
                Arrays.asList("ROLE_SELL"),
                null,
                null,
                null,
                "지점 입금 확인 요청",
                "[" + branchName + "] 지점에서 주문 " + orderNo + "에 대한 입금을 완료하여 확인 요청하였습니다.",
                "branch_deposit_request",
                null);
    }

    @Override
    public void updateOrderShippingInfo(DataMap param) throws Exception {
        // If orderStatus is not provided, default to 60 (SHIPPING)
        if (param.getString("orderStatus") == null || param.getString("orderStatus").isEmpty()) {
            param.put("orderStatus", "60");
        }
        commonMybatisDao.update("mgt.order.updateOrderShippingInfo", param);
    }

}
