package com.whomade.kycarrots.front.shop.web;

import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class ShopController {

    @RequestMapping(value = "/shop/list.do")
    public String selectListShop(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        HttpSession session = request.getSession();

        // BRANCH_ID is set by BranchInterceptor
        Long branchId = (Long) session.getAttribute("BRANCH_ID");
        if (branchId == null) {
            // Handle case where domain is not recognized
            // For now, redirect to error or show a message
            return "common/error_branch";
        }

        model.addAttribute("param", param);
        return "front/shop/list";
    }

    @RequestMapping(value = "/shop/detail.do")
    public String selectDetailShop(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        model.addAttribute("param", param);
        return "front/shop/detail";
    }

    @RequestMapping(value = "/shop/checkout.do")
    public String checkoutShop(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        model.addAttribute("param", param);
        return "front/shop/checkout";
    }

    @RequestMapping(value = "/shop/orderList.do")
    public String selectOrderListShop(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        model.addAttribute("param", param);
        return "front/shop/order_list";
    }
}
