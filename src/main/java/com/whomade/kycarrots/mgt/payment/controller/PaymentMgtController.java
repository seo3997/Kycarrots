package com.whomade.kycarrots.mgt.payment.controller;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.SessionUtil;
import com.whomade.kycarrots.mgt.payment.service.PaymentMgtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PaymentMgtController {

    @jakarta.annotation.Resource(name = "paymentMgtService")
    private PaymentMgtService paymentMgtService;

    @RequestMapping(value = "/mgt/payment/dashboard.do")
    public String dashboard(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        DataMap stats = paymentMgtService.getDashboardStats(param);
        List<DataMap> recentList = paymentMgtService.getPaymentList(param);

        model.addAttribute("stats", stats);
        model.addAttribute("resultList", recentList);
        model.addAttribute("param", param);

        return "mgt/payment/dashboard";
    }

    @RequestMapping(value = "/mgt/payment/selectPaymentList.do")
    public String selectPaymentList(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        List<DataMap> resultList = paymentMgtService.getPaymentList(param);

        model.addAttribute("resultList", resultList);
        model.addAttribute("param", param);

        return "mgt/payment/selectPaymentList";
    }

    @RequestMapping(value = "/mgt/payment/selectPaymentDetail.do")
    public String selectPaymentDetail(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        DataMap resultMap = paymentMgtService.getPaymentDetail(param);

        model.addAttribute("resultMap", resultMap);
        model.addAttribute("param", param);

        return "mgt/payment/paymentDetail";
    }

    @RequestMapping(value = "/mgt/payment/cancelPayment.do")
    @ResponseBody
    public DataMap cancelPayment(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
        param.put("ss_user_no", userInfoVo.getUserNo());

        return paymentMgtService.cancelPayment(param);
    }
}
