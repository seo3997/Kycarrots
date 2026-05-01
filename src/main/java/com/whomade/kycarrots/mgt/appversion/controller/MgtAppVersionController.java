package com.whomade.kycarrots.mgt.appversion.controller;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.SessionUtil;
import com.whomade.kycarrots.mgt.appversion.service.MgtAppVersionService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
public class MgtAppVersionController {

    @Resource(name = "mgtAppVersionService")
    private MgtAppVersionService mgtAppVersionService;

    @RequestMapping(value = "/mgt/appversion/selectPageListAppVersion.do")
    public String selectPageListAppVersion(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        List<DataMap> resultList = mgtAppVersionService.selectPageListAppVersion(model, param);

        model.addAttribute("resultList", resultList);
        model.addAttribute("param", param);

        return "mgt/appversion/selectPageListAppVersion";
    }

    @RequestMapping(value = "/mgt/appversion/selectAppVersion.do")
    public String selectAppVersion(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        DataMap resultMap = mgtAppVersionService.selectAppVersion(param);

        model.addAttribute("resultMap", resultMap);
        model.addAttribute("param", param);

        return "mgt/appversion/selectAppVersion";
    }

    @RequestMapping(value = "/mgt/appversion/insertFormAppVersion.do")
    public String insertFormAppVersion(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        model.addAttribute("param", param);
        return "mgt/appversion/insertFormAppVersion";
    }

    @RequestMapping(value = "/mgt/appversion/insertAppVersion.do")
    public String insertAppVersion(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
        param.put("ss_user_no", userInfoVo.getUserNo());

        mgtAppVersionService.insertAppVersion(param);
        return "redirect:/mgt/appversion/selectPageListAppVersion.do";
    }

    @RequestMapping(value = "/mgt/appversion/updateFormAppVersion.do")
    public String updateFormAppVersion(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        DataMap resultMap = mgtAppVersionService.selectAppVersion(param);

        model.addAttribute("resultMap", resultMap);
        model.addAttribute("param", param);

        return "mgt/appversion/updateFormAppVersion";
    }

    @RequestMapping(value = "/mgt/appversion/updateAppVersion.do")
    public String updateAppVersion(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
        param.put("ss_user_no", userInfoVo.getUserNo());

        mgtAppVersionService.updateAppVersion(param);
        return "redirect:/mgt/appversion/selectAppVersion.do?versionId=" + param.getString("versionId");
    }

    @RequestMapping(value = "/mgt/appversion/deleteAppVersion.do")
    public String deleteAppVersion(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        mgtAppVersionService.deleteAppVersion(param);
        return "redirect:/mgt/appversion/selectPageListAppVersion.do";
    }
}
