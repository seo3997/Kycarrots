/**
 * 1. FileName : LoginController.java
 * 2. Package : egovframework.framework.security
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

import com.whomade.kycarrots.common.service.CommonCodeService;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import com.whomade.kycarrots.framework.common.util.RequestUtil;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : CommonCodeController.java
 * 3. Package  : egovframework.common.web
 * 4. Comment  : 코드 관리
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   : 2017.12.22. 오전 9:48:48
 * </PRE>
 */ 
@Controller
public class CommonCodeController {

	private static Log log = LogFactory.getLog(CommonCodeController.class);

	@Resource(name = "egovMessageSource")
	private EgovMessageSource egovMessageSource;

	/** commonCodeService */
	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;
	
	@RequestMapping(value = "/common/selectCodeListAjax.do")
	public @ResponseBody void selectCodeListAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		JSONObject resultJSON = new JSONObject();
		
		List resultList = commonCodeService.selectCodeList(param);
		
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
	
	@RequestMapping(value = "/common/selectSCodeListAjax.do")
	public @ResponseBody void selectSCodeListAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		JSONObject resultJSON = new JSONObject();
		
		List resultList = commonCodeService.selectSCodeList(param);
		
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

	@RequestMapping(value = "/common/selectSDCodeListAjax.do")
	public @ResponseBody void selectSDCodeListAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		JSONObject resultJSON = new JSONObject();
		
		List resultList = commonCodeService.selectSDCodeList(param);
		
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
	
}
