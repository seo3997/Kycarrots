package com.whomade.kycarrots.front.member.web;

import java.io.IOException;
import java.util.List;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.common.service.CommonCodeService;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.EgovFileScrty;
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.SessionUtil;
import com.whomade.kycarrots.front.member.service.MemberService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
		HttpSession session = request.getSession();

		String endPassword = EgovFileScrty.encryptSHA512(param.getString("password"));
		param.put("email", param.getString("user_id"));
		param.put("user_nm", param.getString("user_id"));
		param.put("enPwd", endPassword);

		// 지점 ID 세팅 (BranchInterceptor에서 세션에 저장됨)
		Long branchId = (Long) session.getAttribute("BRANCH_ID");
		if (branchId != null) {
			param.put("branch_id", branchId);
		}

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
	@RequestMapping(value = "/front/memberIdCheckAjax.do")
	public @ResponseBody void selectSCodeListAjax(HttpServletRequest request, HttpServletResponse response,
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

}
