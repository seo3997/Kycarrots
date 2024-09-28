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
public class SclasController {

	private static Log log = LogFactory.getLog(SclasController.class);

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
	 * 2. ClassName  	: SclasController
	 * 3. Comment   	: 코드대분류/중분류 리스트
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
	@RequestMapping(value = "/mgt/sclas/selectPageListSclas.do")
	public String selectPageListSclas(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		DataMap codeParam= new DataMap();
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		// 아이템
		codeParam.put("group_id", Const.upCodeItemCode);
		codeParam.put("item_code", param.getString("item_code"));
		
		//코드리스트    -  대분류/중분류
		List codeList = sclasService.selectListItem(codeParam);

		// 중분류리스트 -품목류
		codeParam.put("group_id", Const.upCodeItemCode);
		List itemComboStr = commonCodeService.selectCodeList(codeParam);

		
		
		model.addAttribute("param", param);
		model.addAttribute("codeList", codeList);
		model.addAttribute("itemComboStr", itemComboStr);
		
		return "mgt/sclas/selectPageListSclas";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : selectSclas
	 * 2. ClassName  	: SclasController
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
	@RequestMapping(value = "/mgt/sclas/selectSclas.do")
	public String selectSclas(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		// 코드 중분류명-품목
		DataMap opCodeMap = sclasService.selectOpCodeNm(param);
		// 코드 소분류리스트-품목
		List<DataMap> resultList = sclasService.selectListSclas(param);
		
		model.addAttribute("resultList", resultList);
		model.addAttribute("opCodeMap", opCodeMap);
		model.addAttribute("param", param);
		
		return "mgt/sclas/selectSclas";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateSclas
	 * 2. ClassName  	: SclasController
	 * 3. Comment   	: 품목 수정
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
	
	@RequestMapping(value = "/mgt/sclas/updateSclas.do")
	public @ResponseBody void updateSclas(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		JSONObject resultJSON = new JSONObject();

		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		DataMap Sclas=sclasService.selectSclas(param);
		String resultMsg="";
		String resultCode="ok";
		if(Sclas!=null){
			if("N".equals(Sclas.getString("USE_YN"))){				
				resultCode="du";
			}else{
				sclasService.updateSclas(param);
			}
		}else{
			sclasService.insertSclas(param);
		}
		
		List<DataMap> resultList =sclasService.selectListSclas(param);
		
		//return 상태
		DataMap resultStats = new DataMap();
		resultStats.put("resultCode", resultCode);
		resultStats.put("resultMsg", resultMsg);
		resultStats.put("resultList", resultList);
		resultJSON.put("resultStats", resultStats);

		response.setContentType("text/html; charset=utf-8");
		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
	}

	/*
	@RequestMapping(value = "/mgt/sclas/updateSclas.do")
	public String updateSclas(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		if(sclasService.selectSclas(param)!=null){
			sclasService.updateSclas(param);
		}else{
			sclasService.insertSclas(param);
		}
		
		
		model.addAttribute("param", param);
		MessageUtil.setMessage(request, egovMessageSource.getMessage("succ.data.update"));
		param.put("redirectUrl", "/mgt/sclas/selectSclas.do");
		
		return "common/redirect";
	}
	*/
	
	/**
	 * <PRE>
	 * 1. MethodName 	: deleteSclas
	 * 2. ClassName  	: SclasController
	 * 3. Comment   	: 품목 삭제
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
	@RequestMapping(value = "/mgt/sclas/deleteSclas.do")
	public @ResponseBody void deleteSclas(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		JSONObject resultJSON = new JSONObject();

		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		sclasService.deleteSclas(param);
		
		List<DataMap> resultList =sclasService.selectListSclas(param);
		
		//return 상태
		DataMap resultStats = new DataMap();
		resultStats.put("resultCode", "ok");
		resultStats.put("resultMsg", "");
		resultStats.put("resultList", resultList);
		resultJSON.put("resultStats", resultStats);

		response.setContentType("text/html; charset=utf-8");
		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
	}

	/*
	@RequestMapping(value = "/mgt/sclas/deleteSclas.do")
	public String deleteSclas(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		sclasService.deleteSclas(param);
		
		model.addAttribute("param", param);
		MessageUtil.setMessage(request, egovMessageSource.getMessage("succ.data.update"));
		param.put("redirectUrl", "/mgt/sclas/selectSclas.do");
		
		return "common/redirect";
	}
*/
}
