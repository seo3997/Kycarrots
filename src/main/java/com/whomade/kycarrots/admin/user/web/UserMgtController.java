package com.whomade.kycarrots.admin.user.web;

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
import com.whomade.kycarrots.admin.user.service.UserMgtService;
import com.whomade.kycarrots.common.service.CommonCodeService;
import com.whomade.kycarrots.framework.common.constant.Const;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.page.util.pageNavigationUtil;
import com.whomade.kycarrots.framework.common.util.EgovFileScrty;
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import com.whomade.kycarrots.framework.common.util.MessageUtil;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.SessionUtil;

/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName  	: UserMgtController.java
 * 3. Package  	: egovframework.admin.user.web
 * 4. Comment  	: 
 * 5. 작성자   	: SooHyun.Seo
 * 6. 작성일   	: 2017.12.22 오후 4:57:45
 * 7. 변경이력 	: 
 *	이름	 : 일자		  : 근거자료   : 변경내용
 *	------------------------------------------------------
 *	SooHyun.Seo : 2017.12.22 :			: 신규 개발.
 * </PRE>
 */ 
@Controller
public class UserMgtController {

	private static Log log = LogFactory.getLog(UserMgtController.class);
	
	@Resource(name="egovMessageSource")
	private EgovMessageSource egovMessageSource;

	@Resource(name = "userMgtService")
	private UserMgtService userMgtService;

	
	/** CommonCodeService */
	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;
	
	/**
	 * <PRE>
	 * 1. MethodName 	: selectPageListUserMgt
	 * 2. ClassName  	: UserMgtController
	 * 3. Comment   	: 사용자 관리 페이지 리스트 조회
	 * 4. 작성자			: 서수현
	 * 5. 작성일			: 2017.12.22. 오후 3:39:26
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value="/admin/user/selectPageListUserMgt.do")
	public String selectPageListUserMgt(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		DataMap param = RequestUtil.getDataMap(request);
		DataMap codeParam= new DataMap();
		
		/* ### Pasing 시작 ### */
		int totCnt = userMgtService.selectTotCntUser(param);
		param.put("totalCount", totCnt);
		param = pageNavigationUtil.createNavigationInfo(model, param);
		List resultList = userMgtService.selectPageListUser(param);
		/* ### Pasing 끝 ### */
		
		// 사용자 구분 코드 조회
		codeParam.put("group_id", Const.upCodeUserSe);
		List userSeComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("userSeComboStr", userSeComboStr);
		
		// 사용자 구분 코드 조회
		codeParam.put("group_id", Const.upCodeUserSttus);
		List userSttusComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("userSttusComboStr", userSttusComboStr);
		
		model.addAttribute("totalCount", totCnt);
		model.addAttribute("resultList", resultList);
		model.addAttribute("param", param);
		
		return "admin/user/selectPageListUserMgt";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: insertFormUserMgt
	 * 2. ClassName  	: UserMgtController
	 * 3. Comment   	: 사용자 등록 페이지
	 * 4. 작성자			: 서수현
	 * 5. 작성일			: 2017.12.22. 오후 3:39:25
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value="/admin/user/insertFormUserMgt.do")
	public String insertFormUserMgt(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		DataMap param = RequestUtil.getDataMap(request);
		DataMap codeParam = new DataMap();
		
		// 권한 리스트 조회
		List authList = userMgtService.selectListAuthor(param);
		
		// 연락처구분코드 조회 
		codeParam.put("group_id", Const.upCodeCttpcSe);
		List cttpcSeComboStr = commonCodeService.selectCodeList(codeParam);

		// 사용자상태코드 조회
		codeParam.put("group_id", Const.upCodeUserSttus);
		List userSttusComboStr = commonCodeService.selectCodeList(codeParam);
		
		// 지역코드 조회
		codeParam.put("group_id", Const.upCodeArea);
		List areaCodeComboStr = commonCodeService.selectCodeList(codeParam);
		
		model.addAttribute("authList", authList);
		model.addAttribute("cttpcSeComboStr", cttpcSeComboStr);
		model.addAttribute("userSttusComboStr", userSttusComboStr);
		model.addAttribute("areaCodeComboStr", areaCodeComboStr);
		
		return "admin/user/insertFormUserMgt";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: insertUserMgt
	 * 2. ClassName  	: UserMgtController
	 * 3. Comment   	: 사용자 등록
	 * 4. 작성자			: 서수현
	 * 5. 작성일			: 2017.12.22. 오후 3:39:24
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value="/admin/user/insertUserMgt.do")
	public String insertUserMgt(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		DataMap param = RequestUtil.getDataMap(request);
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		String endPassword = EgovFileScrty.encryptSHA512(param.getString("password"));
		//String endPassword = param.getString("password");
		// 암호화된 비밀번호
		param.put("enPwd", endPassword);
		
		
		int iUserNo = userMgtService.selecMaxUserNo();
		param.put("user_no", ""+iUserNo);
		
		userMgtService.insertUser(param);
		
		model.addAttribute("param", param);
		MessageUtil.setMessage(request, egovMessageSource.getMessage("succ.data.insert"));
		
		return "redirect:/admin/user/selectPageListUserMgt.do";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectUserMgt
	 * 2. ClassName  	: UserMgtController
	 * 3. Comment   	: 사용자 조회
	 * 4. 작성자			: 서수현
	 * 5. 작성일			: 2017.12.22. 오후 3:39:22
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value="/admin/user/selectUserMgt.do")
	public String selectUserMgt(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		DataMap resultMap = userMgtService.selectUser(param);
		// 사용자 권한 조회
		List authList = userMgtService.selectListAuthorUser(param);
		
		model.addAttribute("authList", authList);
		model.addAttribute("resultMap", resultMap);
		model.addAttribute("param", param);
		
		return "admin/user/selectUserMgt";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: selectUserMgtAjax
	 * 2. ClassName  	: UserMgtController
	 * 3. Comment   	: 사용자 조회 ajax
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2017. 3. 16. 오전 11:40:21
	 * </PRE>
	 *   @return void
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @throws Exception
	 */
	@RequestMapping(value="/admin/user/selectUserMgtAjax.do")
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
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: updateFormUserMgt
	 * 2. ClassName  	: UserMgtController
	 * 3. Comment   	: 사용자 수정 페이지
	 * 4. 작성자			: 서수현
	 * 5. 작성일			: 2017.12.22. 오후 3:39:21
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value="/admin/user/updateFormUserMgt.do")
	public String updateFormUserMgt(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		DataMap codeParam = new DataMap();
		DataMap resultMap = userMgtService.selectUser(param);
		
		// 권한 리스트 조회
		List authList = userMgtService.selectListAuthor(param);
		// 사용자 권한 조회
		List userAuthList = userMgtService.selectListAuthorUser(param);
		
		// 연락처구분코드 조회
		codeParam.put("group_id", Const.upCodeCttpcSe);
		List cttpcSeComboStr = commonCodeService.selectCodeList(codeParam);
		
		// 사용자상태코드 조회
		codeParam.put("group_id", Const.upCodeUserSttus);
		List userSttusComboStr = commonCodeService.selectCodeList(codeParam);
		
		// 지역코드 조회
		codeParam.put("group_id", Const.upCodeArea);
		// 사무담당자 지역만 가져온다.
		//codeParam.put("attrb_1", "Y");
		List areaCodeComboStr = commonCodeService.selectCodeList(codeParam);
		
		model.addAttribute("param", param);
		model.addAttribute("resultMap", resultMap);
		model.addAttribute("authList", authList);
		model.addAttribute("userAuthList", userAuthList);
		model.addAttribute("cttpcSeComboStr", cttpcSeComboStr);
		model.addAttribute("userSttusComboStr", userSttusComboStr);
		model.addAttribute("areaCodeComboStr", areaCodeComboStr);
		
		return "admin/user/updateFormUserMgt";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateUserMgt
	 * 2. ClassName  	: UserMgtController
	 * 3. Comment   	: 사용자 수정
	 * 4. 작성자				: 서수현
	 * 5. 작성일				: 2017.12.22. 오후 3:39:20
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value="/admin/user/updateUserMgt.do")
	public String updateUserMgt(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		// 패스워드 변경이 있는경우
		if(!param.getString("password").equals("")){
			String endPassword = EgovFileScrty.encryptSHA512(param.getString("password"));
			// 암호화된 비밀번호
			param.put("enPwd", endPassword);
		}
		
		userMgtService.updateUser(param);
		
		
		MessageUtil.setMessage(request, egovMessageSource.getMessage("succ.data.update"));
		
		model.addAttribute("param", param);
		
		param.put("redirectUrl", "/admin/user/selectUserMgt.do");
		return "common/redirect";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: updateUserMgtAjax
	 * 2. ClassName  	: UserMgtController
	 * 3. Comment   	: 사용자 정보 수정
	 * 4. 작성자			: SooHyun.Seo
	 * 5. 작성일			: 2016. 6. 3. 오후 3:13:22
	 * </PRE>
	 *   @return void
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @throws Exception
	 */
	@RequestMapping(value="/admin/user/updateUserMgtAjax.do")
	public @ResponseBody void updateUserMgtAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		// 패스워드 변경이 있는경우
		if(!param.getString("modal_password").equals("")){
			//String endPassword = EgovFileScrty.encryptSHA512(param.getString("modal_password"));
			String endPassword = param.getString("modal_password");
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
	
	/**
	 * <PRE>
	 * 1. MethodName 	: deleteUserMgt
	 * 2. ClassName  	: UserMgtController
	 * 3. Comment   	: 사용자 삭제
	 * 4. 작성자				: 서수현
	 * 5. 작성일				: 2017.12.22. 오후 3:39:19
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value="/admin/user/deleteUserMgt.do")
	public String deleteUser(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		param.put("user_sttus_code_stop", Const.userSttusCodeOut);
		userMgtService.deleteUser(param);
		
		MessageUtil.setMessage(request, egovMessageSource.getMessage("succ.data.delete"));
		
		return "redirect:/admin/user/selectPageListUserMgt.do";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName	: selectIdExistYnAjax
	 * 2. ClassName  	: UserMgtController
	 * 3. Comment   	: 사용자 아이디 중복 검사
	 * 4. 작성자				: 서수현
	 * 5. 작성일				: 2017.12.22. 오후 3:39:17
	 * </PRE>
	 *   @return void
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @throws Exception
	 */
	@RequestMapping(value = "/admin/user/selectIdExistYnAjax.do")
	public @ResponseBody void selectIdExistYnAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		String resultMap = userMgtService.selectIdExistYn(param);

		JSONObject resultJSON = new JSONObject();
		resultJSON.put("resultMap", resultMap);
		
		String returnMsg = "";
		if(resultMap.equals("Y")){
			returnMsg = egovMessageSource.getMessage("error.duple.id");
		}
		//return 상태
		DataMap resultStats = new DataMap();
		resultStats.put("resultCode", "success");
		resultStats.put("resultMsg", returnMsg);
		resultJSON.put("resultStats", resultStats);

		response.setContentType("text/html; charset=utf-8");
		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
	}

}
