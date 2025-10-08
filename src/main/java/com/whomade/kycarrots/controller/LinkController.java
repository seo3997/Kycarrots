package com.whomade.kycarrots.controller;

import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: ADMIN
 * @version: 1.0.0
 * @since: 2024-06-23
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class LinkController {

    @RequestMapping(value = "/link/join_terms.do")
    public String join_terms(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        DataMap codeParam = new DataMap();

        model.addAttribute("param", param);

        return "link/join_terms";
    }

    @RequestMapping(value = "/link/join_terms1.do")
    public String join_terms1(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        DataMap codeParam = new DataMap();

        model.addAttribute("param", param);

        return "link/join_terms1";
    }

    @RequestMapping(value = "/link/join_terms2.do")
    public String join_terms2(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        DataMap codeParam = new DataMap();

        model.addAttribute("param", param);

        return "link/join_terms2";
    }

}
