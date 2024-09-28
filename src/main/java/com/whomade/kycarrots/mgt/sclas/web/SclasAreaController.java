package com.whomade.kycarrots.mgt.sclas.web;

import java.io.IOException;
import java.util.List;

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
import com.whomade.kycarrots.common.service.CommonCodeService;
import com.whomade.kycarrots.framework.common.constant.Const;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.SessionUtil;
import com.whomade.kycarrots.mgt.sclas.service.SclasService;

@Controller
public class SclasAreaController {

	private static Log log = LogFactory.getLog(SclasAreaController.class);

	@Resource(name = "egovMessageSource")
	private EgovMessageSource egovMessageSource;

	@Resource(name = "sclasService")
	private SclasService sclasService;

	/** CommonCodeService */
	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;

	/**
	 * <PRE>
	 * 1. MethodName 	: selectPageListSclas
	 * 2. ClassName  	: SclasAreaController
	 * 3. Comment   	: 코드중분류리스트
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 3. 13. 오후 4:08:28
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/sclas/selectPageListOpCodeAreas.do")
	public String selectPageListOpCodeAreas(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		DataMap codeParam= new DataMap();
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		//중분류리스트-지역
		codeParam.put("group_id", Const.upCodeAreaInfoCode);
		List codeList = commonCodeService.selectCodeList(codeParam);
		
		model.addAttribute("param", param);
		model.addAttribute("codeList", codeList);
		
		return "mgt/sclas/selectPageListOpCodeAreas";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : selectSclas
	 * 2. ClassName  	: SclasAreaController
	 * 3. Comment   	: 코드소분류리스트
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2018. 01. 26. 오후 1:03:07
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/sclas/selectSclasArea.do")
	public String selectSclas(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		// 코드 중분류명-지역
		DataMap opCodeMap = sclasService.selectOpCodeNm(param);
		// 코드 소분류리스트-지역
		List<DataMap> resultList = sclasService.selectListSclas(param);
		
		model.addAttribute("resultList", resultList);
		model.addAttribute("opCodeMap", opCodeMap);
		model.addAttribute("param", param);
		
		return "mgt/sclas/selectSclasArea";
	}

}
