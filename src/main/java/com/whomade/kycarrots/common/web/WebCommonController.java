/**
 * 
 *
 * 1. FileName : WebCommonController.java
 * 2. Package : egovframework.common.web
 * 3. Comment : 
 * 4. 작성자  : SooHyun.Seo
 * 5. 작성일  : 2017.12.22. 오전 9:55:47
 * 6. 변경이력 : 
 *    이름     : 일자          : 근거자료   : 변경내용
 *    ------------------------------------------------------
 *    SooHyun.Seo : 2017.12.22. :            : 신규 개발.
 */

package com.whomade.kycarrots.common.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.admin.user.service.UserMgtService;
import com.whomade.kycarrots.common.service.CommonCodeService;
import com.whomade.kycarrots.common.service.WebCommonService;
import com.whomade.kycarrots.framework.common.constant.Const;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.EgovFileScrty;
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import com.whomade.kycarrots.framework.common.util.HttpUtil;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.SessionUtil;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : WebCommonController.java
 * 3. Package  : egovframework.common.web
 * 4. Comment  : 
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   : 2017.12.22. 오전 9:55:47
 * </PRE>
 */
@Controller
public class WebCommonController {

	private static Log log = LogFactory.getLog(WebCommonController.class);

	@Resource(name = "egovMessageSource")
	private EgovMessageSource egovMessageSource;
	
	@Resource(name = "webCommonService")
	private WebCommonService webCommonService;
	
	@Resource(name = "userMgtService")
	private UserMgtService userMgtService;
	
	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;

	@RequestMapping(value = "/web/useInfo.do")
	public String useInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		model.addAttribute("param", param);
		
		return "common/useInfo";
	}
	
	@RequestMapping(value = "/web/viewer.do")
	public String viewer(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		model.addAttribute("param", param);
		
		return "common/viewer";
	}
	
	@RequestMapping(value = "/common/excelDownload.do")
	public String excelDownload(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		param.put("excel_data", param.getStringOrgn("excel_data"));
		
		model.addAttribute("param", param);
		
		return "common/excelDownload";
	}
	
	@RequestMapping(value = "/common/close.do")
	public String close(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		return "common/close";
	}
	
	@RequestMapping(value = "/common/xmlToJsonAjax.do")
	public @ResponseBody void xmlToJsonAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);	
		
		param.put("xml_url", param.getStringOrgn("xml_url"));
		
		Map xmlMap = null;
    	if(!param.getString("xml_url").equals("")){
    		xmlMap = HttpUtil.getXmlToMap(param.getString("xml_url") , "UTF-8");
    	}		

		JSONObject resultJSON = new JSONObject();
		resultJSON.put("resultMap", xmlMap);
		
		//return 상태
		DataMap resultStats = new DataMap();
		resultStats.put("resultCode", "ok");
		resultStats.put("resultMsg", "");
		resultJSON.put("resultStats", resultStats);
		
		response.setContentType("text/html; charset=utf-8");
		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : selectUserMgtAjax
	 * 2. ClassName  : WebCommonController
	 * 3. Comment   : 사용자 상세 정보
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017. 5. 2. 오전 9:27:33
	 * </PRE>
	 *   @return void
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @throws Exception
	 */
	@RequestMapping(value= "/common/selectUserMgtAjax.do")
	public @ResponseBody void selectUserMgtAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataMap param = RequestUtil.getDataMap(request);
		DataMap codeParam = new DataMap();
		
		DataMap resultMap = userMgtService.selectUser(param);
		
		// 연락처구분코드 조회
		codeParam.put("group_id", Const.upCodeCttpcSe);
		List cttpcSeComboStr = commonCodeService.selectCodeList(codeParam);
		
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("resultMap", resultMap);
		resultJSON.put("cttpcSeComboStr", cttpcSeComboStr);
		
		//return 상태
		DataMap resultStats = new DataMap();
		resultStats.put("resultCode", "ok");
		resultStats.put("resultMsg", "");
		resultJSON.put("resultStats", resultStats);

		response.setContentType("text/json; charset=utf-8");
		try {
			System.out.println("resultJSON.toString()["+resultJSON.toString()+"]");
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : updateUserMgtAjax
	 * 2. ClassName  : WebCommonController
	 * 3. Comment   : 회원정보 수정
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017. 5. 2. 오전 9:34:53
	 * </PRE>
	 *   @return void
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @throws Exception
	 */
	@RequestMapping(value= "/common/updateUserMgtAjax.do")
	public @ResponseBody void updateUserMgtAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		// 패스워드 변경이 있는경우
		if(!param.getString("modal_password").equals("")){
			String endPassword = EgovFileScrty.encryptSHA512(param.getString("modal_password"));
			//String endPassword = param.getString("modal_password");
			// 암호화된 비밀번호
			param.put("enPwd", endPassword);
		}
		
		
		userMgtService.updateUserInfo(param);
		
		
		// 사용자명이 변경될수도 있기때문에 세션 사용자명을 변경하여 준다.
		userInfoVo.setUserNm(param.getString("modal_user_nm"));
		
		JSONObject resultJSON = new JSONObject();
		
		//return 상태
		DataMap resultStats = new DataMap();
		resultStats.put("resultCode", "success");
		resultStats.put("resultMsg", egovMessageSource.getMessage("succ.data.update"));
		resultJSON.put("resultStats", resultStats);

		response.setContentType("text/json; charset=utf-8");
		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
	}
}
