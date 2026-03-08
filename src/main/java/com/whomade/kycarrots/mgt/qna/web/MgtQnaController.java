package com.whomade.kycarrots.mgt.qna.web;

import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.service.product.ProductQnaService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MgtQnaController {

    @Resource(name = "productQnaService")
    private ProductQnaService productQnaService;

    @RequestMapping("/mgt/product/qna/selectPageListQna.do")
    public String selectPageListQna(HttpServletRequest request, ModelMap model) throws Exception {
        DataMap param = RequestUtil.getDataMap(request);

        List<DataMap> resultList = productQnaService.selectPageListQna(param);
        int totalCount = productQnaService.selectTotCntQna(param);

        model.addAttribute("resultList", resultList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("param", param);

        return "mgt/product/qna/selectPageListQna";
    }
}
