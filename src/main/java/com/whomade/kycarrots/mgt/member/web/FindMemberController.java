/**
 * 
 *
S * 1. FileName : MemberController.java
 * 2. Package : egovframework.mgt.member.web
 * 3. Comment : 
 * 4. 작성자  : SooHyun.Seo
 * 5. 작성일  : 2017.12.22. 오전 9:55:47
 * 6. 변경이력 : 
 *    이름     : 일자          : 근거자료   : 변경내용
 *    ------------------------------------------------------
 *    SooHyun.Seo : 2017.12.22. :            : 신규 개발.
 */

package com.whomade.kycarrots.mgt.member.web;

import java.io.IOException;
import javax.annotation.Resource;
import com.whomade.kycarrots.common.service.CommonCodeService;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.EgovFileScrty;
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.mgt.member.service.FindMemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import net.sf.json.JSONObject;

/**
 * <PRE>
 * 1. ClassName 	:
 * 2. FileName  	: MemberController.java
 * 3. Package  		: com.whomade.kycarrots.mgt.member.web
 * 4. Comment  		:
 * 5. 작성자   		: SooHyun.Seo
 * 6. 작성일   		: 2025.10.08. 오전 9:55:47
 * </PRE>
 */
@Controller
public class FindMemberController {

	private static Log log = LogFactory.getLog(FindMemberController.class);

	@Resource(name = "egovMessageSource")
	private EgovMessageSource egovMessageSource;

	/** adminLoginService */
	@Resource(name = "findMemberService")
	private FindMemberService findMemberService;

	/** CommonCodeService */
	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;

	@RequestMapping(value = "/mgt/member/findIdPw.do")
	public String findIdPw(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataMap param = RequestUtil.getDataMap(request);
		model.addAttribute("param", param);
		return "mgt/member/findIdPw";
	}

	@RequestMapping(value = "/mgt/member/findIdAjax.do")
	public @ResponseBody void findIdAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		DataMap param = RequestUtil.getDataMap(request);
		JSONObject resultJSON = new JSONObject();

		DataMap resultMap = findMemberService.selectId(model, param);
		
		boolean user_id_exist;
		DataMap resultStats = new DataMap();
		if (resultMap == null) {
			user_id_exist = false;
		} else {
			user_id_exist = true;
			String user_id_res = resultMap.getString("user_id");
			user_id_res = user_id_res.substring(0, user_id_res.length() - 3) + "***"; // 아이디 뒤에 3자리 제외하고 노출
			resultStats.put("user_id_res", user_id_res);
		}
		
		resultStats.put("resultCode", "ok");
		resultStats.put("user_id_exist", user_id_exist);
		resultJSON.put("resultStats", resultStats);

		response.setContentType("text/html; charset=utf-8");
		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
	}

	@RequestMapping(value = "/mgt/member/resetPw.do", method = RequestMethod.GET)
	public String resetPw(
			@RequestParam(value = "uid", required = false) String uid,
			@RequestParam(value = "sel", required = false) String sel,
			@RequestParam(value = "ver", required = false) String ver,
			ModelMap model) {

		// 간단 유효성 (없는 경우 에러 메시지/화면 처리)
		boolean hasAll = uid != null && !uid.isBlank()
				&& sel != null && !sel.isBlank()
				&& ver != null && !ver.isBlank();

		if (!hasAll) {
			model.addAttribute("invalidLink", true);
			model.addAttribute("msg", "유효하지 않은 접근입니다. 메일의 링크를 다시 확인해 주세요.");
			return "mgt/member/resetPw"; // 같은 화면에서 에러 메시지 표시
		}

		// 뷰에서 hidden으로 사용하도록 모델에 담기
		model.addAttribute("uid", uid.trim());
		model.addAttribute("sel", sel.trim());
		model.addAttribute("ver", ver.trim());

		return "mgt/member/resetPw";
	}

	@RequestMapping(value = "/mgt/member/resetPw.do")
	public @ResponseBody void resetPw(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {		
			DataMap param = RequestUtil.getDataMap(request);
			JSONObject resultJSON = new JSONObject();
			String member_old_pw = findMemberService.selectPw(model, param).getString("user_pw");
			DataMap resultStats = new DataMap();
			
			param.put("member_new_pw", EgovFileScrty.encryptSHA512(param.getString("member_new_pw"))); //새로운 비밀번호 암호화
			
			//비밀번호 기존과 동일한지 체크 AJAX
			if(param.getString("member_new_pw").equals(member_old_pw)) {
				resultStats.put("resultCode", false);
				resultStats.put("resultMsg", "기존의 비밀번호와 동일합니다. <br> 새로운 비밀번호를 입력하세요.");
			} else {
				findMemberService.updatePw(model, param);
				resultStats.put("resultCode", true);
				resultStats.put("resultMsg", "비밀번호가 변경되었습니다. <br> 다시 로그인하세요.");
			}
			resultJSON.put("resultStats", resultStats);
			response.setContentType("text/html; charset=utf-8");
			try {
				response.getWriter().write(resultJSON.toString());
			} catch (IOException e) {
				log.error(e);
			}			
	}
}
