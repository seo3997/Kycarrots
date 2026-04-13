package com.whomade.kycarrots.front.member.web;

import java.io.IOException;

import com.whomade.kycarrots.common.service.CommonCodeService;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.EgovFileScrty;
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.front.member.service.MemberService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;

@Controller
public class MemberController {

	private static Log log = LogFactory.getLog(MemberController.class);

	@Resource(name = "egovMessageSource")
	private EgovMessageSource egovMessageSource;

	@Resource(name = "memberService")
	private MemberService memberService;

	/** CommonCodeService */
	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;

	/**
	 * <PRE>
	 * 1. MethodName 	: registForm
	 * 2. ClassName  	: MemberController
	 * 3. Comment   	: 회원가입 폼 호출
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2021. 08. 07. 오후 4:09:06
	 * </PRE>
	 * 
	 * @return String
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/front/registForm.do")
	public String registForm(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		DataMap param = RequestUtil.getDataMap(request);

		model.addAttribute("param", param);
		return "front/registForm";
	}

	@RequestMapping(value = "/front/joinTerms.do")
	public String joinTerms(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		DataMap param = RequestUtil.getDataMap(request);

		model.addAttribute("param", param);
		return "link/join_terms";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: memberSaveAjax
	 * 2. ClassName  	: MemberController
	 * 3. Comment   	: 회원가입
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2021. 08. 07. 오후 4:09:06
	 * </PRE>
	 * 
	 * @return String
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/front/memberSaveAjax.do")
	public @ResponseBody void memberSaveAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {

		DataMap param = RequestUtil.getDataMap(request);

		String endPassword = EgovFileScrty.encryptSHA512(param.getString("password"));
		param.put("email", param.getString("user_id"));
		param.put("enPwd", endPassword);

		// 지점 ID는 RequestUtil.getDataMap(request)를 통해 화면에서 넘어온 값이 param에 이미 포함되어 있음

		// 일반 사용자 권한 세팅
		param.put("author_id", "ROLE_PUB");
		// 사용자 상태 (정상)
		param.put("user_sttus_code", "10");

		int iUserNo = 0;
		Boolean bInsertresult = false;

		try {
			iUserNo = memberService.selecMaxUserNo();
			param.put("user_no", "" + iUserNo);
			memberService.insertUser(param);
			bInsertresult = true;
		} catch (Exception e) {
			bInsertresult = false;
		}

		String resultMap = "";
		if (bInsertresult)
			resultMap = "Y";
		else
			resultMap = "N";

		JSONObject resultJSON = new JSONObject();
		resultJSON.put("resultMap", resultMap);
		String returnMsg = "";

		// return 상태
		DataMap resultStats = new DataMap();
		resultStats.put("resultCode", "ok");
		resultStats.put("resultMsg", returnMsg);
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
	 * 1. MethodName 	: memberAddEnd
	 * 2. ClassName  	: MemberController
	 * 3. Comment   	: 회원가입 완료
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2021. 08. 07. 오후 4:09:06
	 * </PRE>
	 * 
	 * @return String
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/front/memberAddEnd.do")
	public String memberAddEnd(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {

		DataMap param = RequestUtil.getDataMap(request);

		model.addAttribute("param", param);

		return "/";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: memberIdCheckAjax
	 * 2. ClassName  	: MemberController
	 * 3. Comment   	: 회원가입시 아이디 중복 체크
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2021. 08. 07. 오후 4:09:06
	 * </PRE>
	 * 
	 * @return String
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/front/memberIdCheckAjax.do", method = org.springframework.web.bind.annotation.RequestMethod.POST)
	public @ResponseBody void memberIdCheckAjax(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		String resultMap = memberService.selectIdExistYn(param);

		JSONObject resultJSON = new JSONObject();
		resultJSON.put("resultMap", resultMap);

		String returnMsg = "";
		if (resultMap.equals("Y")) {
			returnMsg = egovMessageSource.getMessage("error.duple.id");
		}

		// return 상태
		DataMap resultStats = new DataMap();
		resultStats.put("resultCode", "ok");
		resultStats.put("resultMsg", returnMsg);
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
	 * 1. MethodName 	: myPage
	 * 2. ClassName  	: MemberController
	 * 3. Comment   	: 마이페이지
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2021. 08. 07. 오후 4:09:06
	 * </PRE>
	 * 
	 * @return String
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/front/myPage.do")
	public String myPage(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataMap param = RequestUtil.getDataMap(request);

		model.addAttribute("param", param);
		return "front/myPage";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: setPassForm
	 * 2. ClassName  	: MemberController
	 * 3. Comment   	: 패스워드폼호출
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2021. 08. 07. 오후 4:09:06
	 * </PRE>
	 * 
	 * @return String
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/front/setPassForm.do")
	public String setPassForm(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		DataMap param = RequestUtil.getDataMap(request);

		model.addAttribute("param", param);
		return "front/setPassForm";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: memberSaveAjax
	 * 2. ClassName  	: MemberController
	 * 3. Comment   	: 회원가입
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2021. 08. 07. 오후 4:09:06
	 * </PRE>
	 * 
	 * @return String
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/front/setPassAjax.do")
	public @ResponseBody void setPassAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {

		DataMap param = RequestUtil.getDataMap(request);

		String endPassword = EgovFileScrty.encryptSHA512(param.getString("password"));
		param.put("enPwd", endPassword);

		int iUserNo = 0;
		Boolean bInsertresult = false;

		try {
			param.put("register_no", "1");
			memberService.updateSetPass(param);
			bInsertresult = true;
		} catch (Exception e) {
			bInsertresult = false;
		}

		String resultMap = "";
		if (bInsertresult)
			resultMap = "Y";
		else
			resultMap = "N";

		JSONObject resultJSON = new JSONObject();
		resultJSON.put("resultMap", resultMap);
		String returnMsg = "";

		// return 상태
		DataMap resultStats = new DataMap();
		resultStats.put("resultCode", "ok");
		resultStats.put("resultMsg", returnMsg);
		resultJSON.put("resultStats", resultStats);

		response.setContentType("text/html; charset=utf-8");
		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
	}

	@RequestMapping(value = "/front/findMember.do")
	public String findMember(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataMap param = RequestUtil.getDataMap(request);

		model.addAttribute("param", param);
		return "front/findMember";
	}

	@RequestMapping(value = "/front/resetPw.do")
	public String resetPw(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataMap param = RequestUtil.getDataMap(request);
		
		// 사용자의 지점 도메인 정보 조회
		String userId = param.getString("uid");
		if (userId != null && !userId.isEmpty()) {
			DataMap userParam = new DataMap();
			userParam.put("userId", userId);
			com.whomade.kycarrots.admin.common.vo.UserInfoVo user = memberService.selectUserInfo(userParam);
			if (user != null && user.getDomainUrl() != null) {
				String domainUrl = user.getDomainUrl();
				// 프로토콜 보정
				if (!domainUrl.startsWith("http")) {
					domainUrl = "http://" + domainUrl;
				}
				model.addAttribute("domainUrl", domainUrl);
			}
		}

		model.addAttribute("param", param);
		return "front/resetPw";
	}

}
