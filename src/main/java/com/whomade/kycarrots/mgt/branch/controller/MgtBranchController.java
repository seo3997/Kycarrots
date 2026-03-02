package com.whomade.kycarrots.mgt.branch.controller;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.SessionUtil;
import com.whomade.kycarrots.mgt.branch.service.MgtBranchService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
public class MgtBranchController {

    @Resource(name = "mgtBranchService")
    private MgtBranchService mgtBranchService;

    @Resource(name = "commonCodeService")
    private com.whomade.kycarrots.common.service.CommonCodeService commonCodeService;

    @RequestMapping(value = "/mgt/branch/selectPageListBranch.do")
    public String selectPageListBranch(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        List<DataMap> resultList = mgtBranchService.selectPageListBranch(model, param);

        model.addAttribute("resultList", resultList);
        model.addAttribute("param", param);

        return "mgt/branch/selectPageListBranch";
    }

    @RequestMapping(value = "/mgt/branch/selectBranch.do")
    public String selectBranch(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        DataMap resultMap = mgtBranchService.selectBranch(param);

        model.addAttribute("resultMap", resultMap);
        model.addAttribute("param", param);

        return "mgt/branch/selectBranch";
    }

    @RequestMapping(value = "/mgt/branch/insertFormBranch.do")
    public String insertFormBranch(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        String nextBranchCode = mgtBranchService.selectNextBranchCode();

        DataMap codeParam = new DataMap();
        codeParam.put("group_id", "R010690");
        List bankList = commonCodeService.selectCodeList(codeParam);

        model.addAttribute("nextBranchCode", nextBranchCode);
        model.addAttribute("bankList", bankList);
        model.addAttribute("param", param);
        return "mgt/branch/insertFormBranch";
    }

    @RequestMapping(value = "/mgt/branch/insertBranch.do")
    public String insertBranch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
        param.put("ss_user_no", userInfoVo.getUserNo());

        mgtBranchService.insertBranch(param);
        return "redirect:/mgt/branch/selectPageListBranch.do";
    }

    @RequestMapping(value = "/mgt/branch/updateFormBranch.do")
    public String updateFormBranch(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        DataMap resultMap = mgtBranchService.selectBranch(param);

        DataMap codeParam = new DataMap();
        codeParam.put("group_id", "R010690");
        List bankList = commonCodeService.selectCodeList(codeParam);

        model.addAttribute("bankList", bankList);
        model.addAttribute("resultMap", resultMap);
        model.addAttribute("param", param);

        return "mgt/branch/updateFormBranch";
    }

    @RequestMapping(value = "/mgt/branch/updateBranch.do")
    public String updateBranch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
        param.put("ss_user_no", userInfoVo.getUserNo());

        mgtBranchService.updateBranch(param);
        return "redirect:/mgt/branch/selectBranch.do?branchId=" + param.getString("branchId");
    }

    @RequestMapping(value = "/mgt/branch/deleteBranch.do")
    public String deleteBranch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        mgtBranchService.deleteBranch(param);
        return "redirect:/mgt/branch/selectPageListBranch.do";
    }

    // Ajax registration remains for backward compatibility or direct use
    @RequestMapping(value = "/mgt/branch/registerBranch.do")
    @ResponseBody
    public DataMap registerBranchAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);

        if (userInfoVo == null || !("ROLE_SELL".equals(userInfoVo.getMemberCode())
                || "ROLE_ADMIN".equals(userInfoVo.getMemberCode()))) {
            DataMap result = new DataMap();
            result.put("success", false);
            result.put("message", "지점 등록 권한이 없습니다.");
            return result;
        }

        param.put("ss_user_no", userInfoVo.getUserNo());
        return mgtBranchService.insertBranch(param);
    }
}
