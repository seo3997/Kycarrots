package com.whomade.kycarrots.mgt.review.web;

import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.page.util.pageNavigationUtil;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.service.product.ProductReviewService;
import com.whomade.kycarrots.mgt.product.service.ProductService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MgtReviewController {

    @Resource(name = "productReviewService")
    private ProductReviewService productReviewService;

    @Resource(name = "procuctService")
    private ProductService productService;

    @RequestMapping("/mgt/product/review/selectPageListReview.do")
    public String selectPageListReview(HttpServletRequest request, ModelMap model) throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        param.put("mgtYn", "Y");

        int totalCount = productReviewService.selectTotCntReview(param);
        param.put("totalCount", totalCount);
        param = pageNavigationUtil.createNavigationInfo(model, param);

        List<DataMap> resultList = productReviewService.selectPageListReview(param);
        List<DataMap> productList = productService.selectListProduct(param);

        model.addAttribute("resultList", resultList);
        model.addAttribute("productList", productList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("param", param);

        return "mgt/product/review/selectPageListReview";
    }
}
