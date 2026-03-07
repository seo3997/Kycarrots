package com.whomade.kycarrots.front.shop.web;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.SessionUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.whomade.kycarrots.entity.product.TnProductImageVo;
import com.whomade.kycarrots.repository.mybatis.product.TnProductRepository;
import java.util.List;

@Slf4j
@Controller
public class ShopController {

    @Resource(name = "mgtOrderService")
    private com.whomade.kycarrots.mgt.order.service.MgtOrderService mgtOrderService;

    @Resource(name = "procuctService")
    private com.whomade.kycarrots.mgt.product.service.ProductService productService;

    @Resource(name = "orderService")
    private com.whomade.kycarrots.service.order.OrderService orderService;

    @Resource
    private TnProductRepository tnProductRepository;

    @Resource(name = "addressBookService")
    private com.whomade.kycarrots.front.member.service.AddressBookService addressBookService;

    @RequestMapping(value = "/shop/list.do")
    public String selectListShop(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        HttpSession session = request.getSession();

        Long branchId = (Long) session.getAttribute("BRANCH_ID");
        if (branchId == null) {
            return "common/error_branch";
        }

        // Fetch products for the shop
        // Assuming branchId matches WHOLESALER_NO for filtering products
        param.put("branchId", branchId);
        param.put("rowCount", param.getString("rowCount", "20"));

        // Default sale status to '1' (On Sale) if not provided and not 'ALL'
        if (param.getString("sch_sale_status_code").isEmpty()) {
            param.put("sch_sale_status_code", "1");
        } else if ("ALL".equals(param.getString("sch_sale_status_code"))) {
            param.put("sch_sale_status_code", "");
        }

        // Only fetch valid shop statuses (1, 20, 30, 99)
        param.put("sch_shop_sale_statuses", "Y");

        List<DataMap> productList = productService.selectPageListProcuct(model, param);

        model.addAttribute("resultList", productList);
        model.addAttribute("searchParam", param);
        return "front/shop/list";
    }

    @RequestMapping(value = "/shop/detail.do")
    public String selectDetailShop(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);

        DataMap productInfo = productService.selectProduct(param);

        // Fetch all images for this product
        List<TnProductImageVo> imageList = tnProductRepository
                .selectProductImagesByProductId(param.getLong("productId"));

        model.addAttribute("productInfo", productInfo);
        model.addAttribute("imageList", imageList);
        model.addAttribute("param", param);
        return "front/shop/detail";
    }

    @RequestMapping(value = "/shop/checkout.do")
    public String checkoutShop(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);

        // Fetch product info if productId is provided
        if (param.get("productId") != null) {
            DataMap productInfo = productService.selectProduct(param);
            model.addAttribute("productInfo", productInfo);
        }
        // Fetch default address if user is logged in
        UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
        if (userInfoVo != null) {
            DataMap addressParam = new DataMap();
            addressParam.put("userNo", userInfoVo.getUserNo());
            DataMap defaultAddress = addressBookService.selectDefaultAddress(addressParam);
            model.addAttribute("defaultAddress", defaultAddress);
        }

        model.addAttribute("reqParam", param);
        return "front/shop/checkout";
    }

    @RequestMapping(value = "/shop/success.do")
    public String successPayment(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        model.addAttribute("param", param);
        // paymentKey, orderId, amount will be in param
        return "front/shop/success";
    }

    @RequestMapping(value = "/shop/fail.do")
    public String failPayment(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        model.addAttribute("param", param);
        // code, message will be in param
        return "front/shop/fail";
    }

    @RequestMapping(value = "/shop/orderList.do")
    public String selectOrderListShop(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);

        if (userInfoVo == null) {
            return "redirect:/front/login.do";
        }

        param.put("userNo", userInfoVo.getUserNo());
        param.put("sch_not_order_status", "10");
        List<DataMap> resultList = mgtOrderService.selectPageListOrder(model, param);

        model.addAttribute("resultList", resultList);
        model.addAttribute("param", param);
        return "front/shop/order_list";
    }

    @RequestMapping(value = "/shop/orderDetail.do")
    public String selectOrderDetailShop(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        String orderIdStr = param.getString("orderId");

        if (orderIdStr == null || orderIdStr.isEmpty()) {
            return "redirect:/shop/orderList.do";
        }

        Long orderId = Long.parseLong(orderIdStr);
        com.whomade.kycarrots.entity.payment.OrderVo resultVo = orderService.selectOrderById(orderId);
        if (resultVo == null) {
            return "redirect:/shop/orderList.do";
        }

        List<com.whomade.kycarrots.entity.payment.OrderItemVo> itemList = orderService
                .selectOrderItemsByOrderId(resultVo.getOrderId());

        model.addAttribute("resultVo", resultVo);
        model.addAttribute("itemList", itemList);
        model.addAttribute("param", param);
        return "front/shop/order_detail";
    }
}
