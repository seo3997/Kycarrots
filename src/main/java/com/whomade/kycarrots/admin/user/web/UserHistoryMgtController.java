package com.whomade.kycarrots.admin.user.web;

import java.util.List;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.whomade.kycarrots.admin.user.service.UserHistoryMgtService;
import com.whomade.kycarrots.common.service.CommonCodeService;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.page.util.pageNavigationUtil;
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import com.whomade.kycarrots.framework.common.util.RequestUtil;

/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName	: UserHistoryController.java
 * 3. Package	: egovframework.admin.user.web
 * 4. Comment	: 
 * 5. 작성자			: SooHyun.Seo
 * 6. 작성일			: 2017.12.22 오후 4:57:45
 * 7. 변경이력 : 
 *	이름	 : 일자		  : 근거자료   : 변경내용
 *	------------------------------------------------------
 *	SooHyun.Seo : 2017.12.22 :			: 신규 개발.
 * </PRE>
 */ 
@Controller
public class UserHistoryMgtController {

	private static Log log = LogFactory.getLog(UserHistoryMgtController.class);
	
	@Resource(name="egovMessageSource")
	private EgovMessageSource egovMessageSource;

	@Resource(name = "userHistoryMgtService")
	private UserHistoryMgtService userHistoryMgtService;
	
	/** CommonCodeService */
	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;
	
	/**
	 * <PRE>
	 * 1. MethodName	: selectPageListUserHistory
	 * 2. ClassName  	: UserHistoryController
	 * 3. Comment   	: 사용자변경이력 페이지 리스트 조회
	 * 4. 작성자				: 서수현
	 * 5. 작성일				: 2017.12.22. 오후 3:39:26
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value="/admin/user/selectPageListUserHistoryMgt.do")
	public String selectPageListUserHistory(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		DataMap param = RequestUtil.getDataMap(request);
		DataMap codeParam= new DataMap();
		
		/* ### Pasing 시작 ### */
		int totCnt = userHistoryMgtService.selectTotCntuserchgHst(param);
		param.put("totalCount", totCnt);
		param = pageNavigationUtil.createNavigationInfo(model, param);
		List<DataMap> resultList = userHistoryMgtService.selectPageListUserchgHst(param);
		/* ### Pasing 끝 ### */
		
		model.addAttribute("totalCount", totCnt);
		model.addAttribute("resultList", resultList);
		model.addAttribute("param", param);
		
		return "admin/user/selectPageListUserHistoryMgt";
	}

	
}
