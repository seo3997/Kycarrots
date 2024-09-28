package com.whomade.kycarrots.admin.code.web;

import java.util.List;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.whomade.kycarrots.admin.author.service.CodeMgtService;
import com.whomade.kycarrots.common.service.CommonCodeService;
import com.whomade.kycarrots.framework.common.constant.Const;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.page.util.pageNavigationUtil;
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import com.whomade.kycarrots.framework.common.util.MessageUtil;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.SessionUtil;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : CodeMgtController.java
 * 3. Package  : egovframework.admin.code.web
 * 4. Comment  : 
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   : 2017.12.22 오후 4:23:30
 * 7. 변경이력 : 
 *    이름     : 일자          : 근거자료   : 변경내용
 *    ------------------------------------------------------
 *    SooHyun.Seo : 2017.12.22 :            : 신규 개발.
 * </PRE>
 */ 
@Controller
public class CodeMgtController{
	
	private static Log log = LogFactory.getLog(CodeMgtController.class);
	
	@Resource(name="egovMessageSource")
	private EgovMessageSource egovMessageSource;

	/** codeMgtService */
	@Resource(name = "codeMgtService")
	private CodeMgtService codeMgtService;
	
	/** CommonCodeService */
	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;
	
	
	/**
	 * <PRE>
	 * 1. MethodName : selectPageListGroupCodeMgt
	 * 2. ClassName  : CodeController
	 * 3. Comment   : 그룹코드 리스트
	 * 4. 작성자	: SooHyun.Seo
	 * 5. 작성일	: 2015. 8. 31. 오후 9:59:57
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value="/admin/code/selectPageListGroupCodeMgt.do")
	public String selectPageListGroupCodeMgt(HttpServletRequest request,
											 HttpServletResponse response,
											 ModelMap model) throws Exception {
		
		log.debug("####" + this.getClass().getName() + " START ####");
		
		DataMap param = RequestUtil.getDataMap(request);
		/* ### Pasing 시작 ### */
		int totCnt = codeMgtService.selectTotCntGroupCode(param);
		param.put("totalCount", totCnt);		
		param = pageNavigationUtil.createNavigationInfo(model, param);		
		List resultList = codeMgtService.selectPageListGroupCode(param);
		/* ### Pasing 끝 ### */
		
		model.addAttribute("resultList", resultList);
		model.addAttribute("param", param);
		
		return "admin/code/selectPageListGroupCodeMgt";
	} 
	
	
	/**
	 * <PRE>
	 * 1. MethodName : selectListCodeMgt
	 * 2. ClassName  : CodeController
	 * 3. Comment   : 코드 리스트 조회
	 * 4. 작성자	: SooHyun.Seo
	 * 5. 작성일	: 2013. 9. 3. 오후 11:43:25
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value="/admin/code/selectListCodeMgt.do")
	public String selectListCodeMgt(HttpServletRequest request,
									HttpServletResponse response,
									ModelMap model) throws Exception {
		
		log.debug("####" + this.getClass().getName() + " START ####");
		DataMap param = RequestUtil.getDataMap(request);
		
		List resultList = codeMgtService.selectListCode(param);
		model.addAttribute("resultList", resultList);
		DataMap resultMap = codeMgtService.selectGroupCode(param);
		model.addAttribute("resultMap", resultMap);
		
		model.addAttribute("param", param);
		
		DataMap codeParam = new DataMap();
		codeParam.put("group_id", Const.upCodeYn);
		model.addAttribute("ynCodeList", commonCodeService.selectCodeList(codeParam));		
		
		return "admin/code/selectListCodeMgt";
	}
	
	
	/**
	 * <PRE>
	 * 1. MethodName : deleteGroupCodeMgt
	 * 2. ClassName  : CodeMgtController
	 * 3. Comment   : 그룹코드 및 상세코드 삭제
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 3:50:22
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @param status
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value="/admin/code/deleteGroupCodeMgt.do")
	public String deleteGroupCodeMgt(HttpServletRequest request,
									HttpServletResponse response,
									ModelMap model,
									SessionStatus status) throws Exception {
		
		log.debug("####" + this.getClass().getName() + " START ####");
		
		
		DataMap param = RequestUtil.getDataMap(request);
		codeMgtService.deleteGroupCode(param);
		model.addAttribute("param", param);
		MessageUtil.setMessage(request, egovMessageSource.getMessage("succ.data.delete"));
		status.setComplete();
		return "redirect:/admin/code/selectPageListGroupCodeMgt.do";
	}	
	
	
	/**
	 * <PRE>
	 * 1. MethodName : insertFormGroupCodeMgt
	 * 2. ClassName  : CodeMgtController
	 * 3. Comment   : 그룹코드 입력폼
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 3:50:33
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @param status
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value="/admin/code/insertFormGroupCodeMgt.do")
	public String insertFormGroupCodeMgt(HttpServletRequest request,
									HttpServletResponse response,
									ModelMap model,
									SessionStatus status) throws Exception {
		
		log.debug("####" + this.getClass().getName() + " START ####");
		
		DataMap param = RequestUtil.getDataMap(request);
		model.addAttribute("param", param);
		
		return "admin/code/insertFormGroupCodeMgt";
	}
	
	
	/**
	 * <PRE>
	 * 1. MethodName : insertGroupCodeMgt
	 * 2. ClassName  : CodeMgtController
	 * 3. Comment   : 그룹코드 추가
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 3:50:49
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @param status
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value="/admin/code/insertGroupCodeMgt.do")
	public String insertGroupCodeMgt(HttpServletRequest request, HttpServletResponse response, ModelMap model, SessionStatus status) throws Exception {
		
		log.debug("####" + this.getClass().getName() + " START ####");
		
		DataMap param = RequestUtil.getDataMap(request);
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		DataMap groupCodeExistYn = codeMgtService.selectExistYnGroupCode(param);
		if(groupCodeExistYn.getString("EXIST_YN").equals("Y")){
			String[] grpCode = new String[1];
			grpCode[0] = param.getString("group_id");
			MessageUtil.setMessage(request, egovMessageSource.getMessage("error.grp.code.dup", grpCode));
			return "redirect:/admin/code/insertFormGroupCodeMgt.do";
		}
		codeMgtService.insertGroupCode(param);
		model.addAttribute("param", param);
		MessageUtil.setMessage(request, egovMessageSource.getMessage("succ.data.insert"));
		status.setComplete();
		return "redirect:/admin/code/selectPageListGroupCodeMgt.do";
	}	 
	
	
	/**
	 * <PRE>
	 * 1. MethodName : updateCodeMgt
	 * 2. ClassName  : CodeMgtController
	 * 3. Comment   : 그룹코드 및 상세코드 수정
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 3:51:04
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @param status
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value="/admin/code/updateCodeMgt.do")
	public String updateCodeMgt(HttpServletRequest request,
									HttpServletResponse response,
									ModelMap model,
									SessionStatus status) throws Exception {
		
		log.debug("####" + this.getClass().getName() + " START ####");		
		
		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		codeMgtService.updateCode(param);
		model.addAttribute("param", param);
		MessageUtil.setMessage(request, egovMessageSource.getMessage("succ.data.update"));
		status.setComplete();
		
		param.put("redirectUrl", "/admin/code/selectListCodeMgt.do");
		return "common/redirect";
	}	

}
