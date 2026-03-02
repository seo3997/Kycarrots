package com.whomade.kycarrots.mgt.order.controller;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.entity.payment.OrderItemVo;
import com.whomade.kycarrots.entity.payment.OrderVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.MessageUtil;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.SessionUtil;
import com.whomade.kycarrots.mgt.order.service.MgtOrderService;
import com.whomade.kycarrots.service.order.OrderService;
import com.whomade.kycarrots.service.payment.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.whomade.kycarrots.common.service.CommonCodeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MgtOrderController {

    @Resource(name = "mgtOrderService")
    private MgtOrderService mgtOrderService;

    private final OrderService orderService;
    private final PaymentService paymentService;
    private final CommonCodeService commonCodeService;

    @RequestMapping(value = "/mgt/order/selectPageListOrder.do")
    public String selectPageListOrder(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);

        List<DataMap> resultList = mgtOrderService.selectPageListOrder(model, param);

        model.addAttribute("resultList", resultList);
        model.addAttribute("param", param);

        return "mgt/order/selectPageListOrder";
    }

    @RequestMapping(value = "/mgt/order/selectOrder.do")
    public String selectOrder(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        String orderNo = param.getString("orderNo");

        OrderVo resultVo = orderService.selectOrderByNo(orderNo);
        if (resultVo != null) {
            List<OrderItemVo> itemList = orderService.selectOrderItemsByOrderId(resultVo.getOrderId());
            model.addAttribute("resultVo", resultVo);
            model.addAttribute("itemList", itemList);

            // 택배사 코드 조회 (R010660)
            DataMap codeParam = new DataMap();
            codeParam.put("group_id", "R010660");
            List deliveryCompanyList = commonCodeService.selectCodeList(codeParam);
            model.addAttribute("deliveryCompanyList", deliveryCompanyList);
        }

        model.addAttribute("param", param);
        return "mgt/order/selectOrder";
    }

    @RequestMapping(value = "/mgt/order/cancelOrder.do")
    public String cancelOrder(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        String orderNo = param.getString("orderNo");
        String cancelReason = param.getString("cancelReason", "관리자 취소");

        UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);

        DataMap result = paymentService.cancelPayment(orderNo, cancelReason,
                Integer.parseInt(String.valueOf(userInfoVo.getUserNo())));

        if (result.getBoolean("success")) {
            MessageUtil.setMessage(request, "주문이 취소되었습니다.");
        } else {
            MessageUtil.setMessage(request, "취소 실패: " + result.getString("message"));
        }

        return "redirect:/mgt/order/selectOrder.do?orderNo=" + orderNo;
    }

    @RequestMapping(value = "/mgt/order/confirmBranchDeposit.do")
    public String confirmBranchDeposit(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
        param.put("updusrNo", userInfoVo.getUserNo());

        mgtOrderService.confirmBranchDeposit(param);

        MessageUtil.setMessage(request, "입금 확인 처리되었습니다.");
        return "redirect:/mgt/main/dashBoard.do";
    }

    @RequestMapping(value = "/mgt/order/updateOrderShippingInfo.do")
    public String updateOrderShippingInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
        param.put("updusrNo", userInfoVo.getUserNo());

        mgtOrderService.updateOrderShippingInfo(param);

        MessageUtil.setMessage(request, "배송 정보가 업데이트되었습니다.");
        return "redirect:/mgt/main/dashBoard.do";
    }

    @RequestMapping(value = "/mgt/order/confirmOrder.do")
    public String confirmOrder(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
        param.put("updusrNo", userInfoVo.getUserNo());

        mgtOrderService.confirmOrder(param);

        MessageUtil.setMessage(request, "주문이 확정되었습니다.");
        return "redirect:/mgt/main/dashBoard.do";
    }
}
