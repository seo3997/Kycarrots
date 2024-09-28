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
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import com.whomade.kycarrots.framework.common.util.MessageUtil;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.SessionUtil;
import com.whomade.kycarrots.mgt.sclas.service.SdclasService;

@Controller
public class SdclasController {

	private static Log log = LogFactory.getLog(SdclasController.class);

	@Resource(name = "egovMessageSource")
	private EgovMessageSource egovMessageSource;

	@Resource(name = "sdclasService")
	private SdclasService sdclasService;

	/** CommonCodeService */
	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;

	
	/**
	 * <PRE>
	 * 1. MethodName : selectSdclas
	 * 2. ClassName  	: SdclasController
	 * 3. Comment   	: 품종 상세
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 3. 13. 오후 4:09:06
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/sclas/selectSdclas.do")
	public String selectSdclas(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		// 품목명
		DataMap itemMap = sdclasService.selectItemName(param);
		
		// 품종 리스트
		List resultList = sdclasService.selectListSdclas(param);
		
		model.addAttribute("resultList", resultList);
		model.addAttribute("itemMap", itemMap);
		model.addAttribute("param", param);
		
		return "mgt/sclas/selectSdclas";
	}

	
	/**
	 * <PRE>
	 * 1. MethodName 	: deleteSclas
	 * 2. ClassName  	: SclasController
	 * 3. Comment   	: 품종 삭제
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
	@RequestMapping(value = "/mgt/sclas/deleteSdclas.do")
	public @ResponseBody void deleteSclas(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		JSONObject resultJSON = new JSONObject();

		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		sdclasService.deleteSdclas(param);
		
		List<DataMap> resultList =sdclasService.selectListSdclas(param);
		
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
	
	@RequestMapping(value = "/mgt/sclas/updateSdclas.do")
	public @ResponseBody void updateSclas(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		JSONObject resultJSON = new JSONObject();

		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		DataMap Sdclas=sdclasService.selectSdclas(param);
		String resultMsg="";
		String resultCode="ok";
		
		if(Sdclas!=null){
			if("N".equals(Sdclas.getString("USE_YN"))){				
				resultCode="du";
			}else{
				sdclasService.updateSdclas(param);
				resultCode="ok";
			}
		}else{
				sdclasService.insertSdclas(param);
		}
		
		List<DataMap> resultList =sdclasService.selectListSdclas(param);
		
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

	
}



