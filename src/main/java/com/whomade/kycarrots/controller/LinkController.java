package com.whomade.kycarrots.controller;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.SessionUtil;
import com.whomade.kycarrots.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: ADMIN
 * @version: 1.0.0
 * @since: 2024-06-23
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class LinkController {

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
