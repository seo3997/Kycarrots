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

        // Fetch order details for push notifications
        DataMap order = commonMybatisDao.selectOne("mgt.order.selectOrderById",
                Long.parseLong(param.getString("orderId")));
        if (order != null) {
            String orderId = order.getString("ORDER_ID");
            String orderNo = order.getString("ORDER_NO");
            String branchId = order.getString("BRANCH_ID");
            Long userNo = Long.parseLong(order.getString("USER_NO"));

            // 1. Send push to Branch (ROLE_PROJ)
            String branchTitle = "입금 확인 및 배송 시작";
            String branchBody = "주문 " + orderNo + "의 입금이 확인되어 배송이 시작되었습니다.";

            java.util.Map<String, String> branchPayload = new java.util.HashMap<>();
            branchPayload.put("targetId", orderId);
            branchPayload.put("type", "order");
            branchPayload.put("title", branchTitle);
            branchPayload.put("body", branchBody);

            pushService.sendTargetPush(
                    java.util.Arrays.asList("ROLE_PROJ"),
                    branchId,
                    null,
                    null,
                    branchTitle,
                    branchBody,
                    "branch_deposit_confirmed",
                    branchPayload);

            // 2. Send push to Buyer (userNo)
            String buyerTitle = "배송 시작 안내";
            String buyerBody = "주문하신 상품의 입금이 확인되어 배송이 시작되었습니다. (주문번호: " + orderNo + ")";

            java.util.Map<String, String> buyerPayload = new java.util.HashMap<>();
            buyerPayload.put("targetId", orderId);
            buyerPayload.put("type", "order");
            buyerPayload.put("title", buyerTitle);
            buyerPayload.put("body", buyerBody);

            pushService.sendTargetPush(
                    null,
                    null,
                    null,
                    userNo,
                    buyerTitle,
                    buyerBody,
                    "shipping_started",
                    buyerPayload);
        }
    }

    @Override
    public void requestBranchDeposit(DataMap param) throws Exception {
        param.put("branchDepositStatus", "20"); // Status 20: BRREQUESR (입금확인요청)
        commonMybatisDao.update("mgt.order.updateBranchDepositStatus", param);

        // Send push to HQ (ROLE_SELL)
        String orderId = param.getString("orderId");
        String orderNo = param.getString("orderNo");
        String branchName = param.getString("branchName");

        String title = "지점 입금 확인 요청";
        String body = "[" + branchName + "] 지점에서 주문 " + orderNo + "에 대한 입금을 완료하여 확인 요청하였습니다.";

        java.util.Map<String, String> payload = new java.util.HashMap<>();
        payload.put("targetId", orderId);
        payload.put("type", "order");
        payload.put("title", title);
        payload.put("body", body);

        pushService.sendTargetPush(
                Arrays.asList("ROLE_SELL"),
                null,
                null,
                null,
                title,
                body,
                "branch_deposit_request",
                payload);
    }

    @Override
    public void updateOrderShippingInfo(DataMap param) throws Exception {
        String orderStatus = param.getString("orderStatus");
        // If orderStatus is not provided, default to 60 (SHIPPING)
        if (orderStatus == null || orderStatus.isEmpty()) {
            orderStatus = "60";
            param.put("orderStatus", orderStatus);
        }
        commonMybatisDao.update("mgt.order.updateOrderShippingInfo", param);

        // Fetch order details for push notifications
        DataMap order = commonMybatisDao.selectOne("mgt.order.selectOrderById",
                Long.parseLong(param.getString("orderId")));
        if (order != null) {
            String orderId = order.getString("ORDER_ID");
            String orderNo = order.getString("ORDER_NO");
            String branchId = order.getString("BRANCH_ID");

            String title = "";
            String body = "";
            String type = "order";
            String eventType = "";
            java.util.List<String> targetRoles = null;
            String targetBranchId = null;

            if ("70".equals(orderStatus)) {
                title = "배송 완료";
                body = "주문 " + orderNo + "의 배송이 완료되었습니다.";
                eventType = "shipping_completed";
                targetRoles = java.util.Arrays.asList("ROLE_PROJ");
                targetBranchId = branchId;
            } else if ("80".equals(orderStatus)) {
                title = "반품 요청";
                body = "주문 " + orderNo + "에 대한 반품 요청이 접수되었습니다.";
                eventType = "return_requested";
                targetRoles = java.util.Arrays.asList("ROLE_PROJ");
                targetBranchId = branchId;
            } else if ("89".equals(orderStatus)) {
                title = "반품 완료";
                body = "주문 " + orderNo + "의 반품 처리가 완료되었습니다.";
                eventType = "return_completed";
                targetRoles = java.util.Arrays.asList("ROLE_PROJ");
                targetBranchId = branchId;
            } else if ("99".equals(orderStatus)) {
                title = "주문 확정";
                body = "주문 " + orderNo + "이(가) 확정되었습니다.";
                eventType = "order_confirmed";
                targetRoles = java.util.Arrays.asList("ROLE_SELL"); // HQ
            }

            if (!title.isEmpty()) {
                java.util.Map<String, String> payload = new java.util.HashMap<>();
                payload.put("targetId", orderId);
                payload.put("type", type);
                payload.put("title", title);
                payload.put("body", body);

                pushService.sendTargetPush(targetRoles, targetBranchId, null, null, title, body, eventType, payload);
            }
        }
    }

}
