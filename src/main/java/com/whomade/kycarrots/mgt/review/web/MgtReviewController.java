package com.whomade.kycarrots.mgt.review.web;

import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.service.product.ProductReviewService;
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

    @RequestMapping("/mgt/product/review/selectPageListReview.do")
    public String selectPageListReview(HttpServletRequest request, ModelMap model) throws Exception {
        DataMap param = RequestUtil.getDataMap(request);

        List<DataMap> resultList = productReviewService.selectPageListReview(param);
        int totalCount = productReviewService.selectTotCntReview(param);

        model.addAttribute("resultList", resultList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("param", param);

        return "mgt/product/review/selectPageListReview";
    }
}
