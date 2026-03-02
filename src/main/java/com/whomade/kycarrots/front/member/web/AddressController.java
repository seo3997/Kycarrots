package com.whomade.kycarrots.front.member.web;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.entity.member.TbAddressBookVo;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.SessionUtil;
import com.whomade.kycarrots.front.member.service.AddressBookService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import com.whomade.kycarrots.framework.common.util.SysUtil;

@Controller
public class AddressController {

    @Resource(name = "addressBookService")
    private AddressBookService addressBookService;

    @RequestMapping(value = "/front/member/addressList.do")
    public String addressList(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);

        if (userInfoVo == null) {
            return "redirect:/front/login.do";
        }

        param.put("userNo", userInfoVo.getUserNo());
        List<DataMap> addressList = addressBookService.selectAddressList(param);

        model.addAttribute("addressList", addressList);
        return "front/member/addressList";
    }

    @RequestMapping(value = "/front/member/addressListAjax.do")
    public String addressListAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);

        if (userInfoVo == null) {
            return "common/error_auth";
        }

        param.put("userNo", userInfoVo.getUserNo());
        List<DataMap> addressList = addressBookService.selectAddressList(param);

        model.addAttribute("addressList", addressList);
        return "front/member/addressListAjax";
    }

    @RequestMapping(value = "/front/member/saveAddressAjax.do")
    @ResponseBody
    public String saveAddressAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);

        if (userInfoVo == null) {
            return "fail:auth";
        }

        TbAddressBookVo vo = new TbAddressBookVo();
        vo.setUserNo(userInfoVo.getUserNo());
        vo.setAddressName(param.getString("addressName"));
        vo.setRecipientName(param.getString("recipientName"));
        vo.setRecipientPhone(param.getString("recipientPhone"));
        vo.setZipCode(param.getString("zipCode"));
        vo.setAddressMain(param.getString("addressMain"));
        vo.setAddressDetail(param.getString("addressDetail"));
        vo.setIsDefault(param.getInt("isDefault"));
        vo.setMemo(param.getString("memo"));

        if (param.get("addressId") == null || param.getString("addressId").isEmpty()) {
            addressBookService.insertAddress(vo);
        } else {
            vo.setAddressId(param.getLong("addressId"));
            addressBookService.updateAddress(vo);
        }

        return "success";
    }

    @RequestMapping(value = "/front/member/deleteAddressAjax.do")
    @ResponseBody
    public String deleteAddressAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);

        if (userInfoVo == null) {
            return "fail:auth";
        }

        param.put("userNo", userInfoVo.getUserNo());
        addressBookService.deleteAddress(param);
        return "success";
    }

    @RequestMapping(value = "/front/member/setDefaultAddressAjax.do")
    @ResponseBody
    public String setDefaultAddressAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DataMap param = RequestUtil.getDataMap(request);
        UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);

        if (userInfoVo == null) {
            return "fail:auth";
        }

        param.put("userNo", userInfoVo.getUserNo());
        addressBookService.setDefaultAddress(param);
        return "success";
    }
}
