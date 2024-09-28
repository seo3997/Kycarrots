/**
 * 
 *
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

package com.whomade.kycarrots.front.login.web;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.common.service.CommonCodeService;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.*;
import com.whomade.kycarrots.front.login.service.FrontLoginService;
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
public class FrontLoginController {

	private static Log log = LogFactory.getLog(FrontLoginController.class);

	@Resource(name = "egovMessageSource")
	private EgovMessageSource egovMessageSource;

	/** adminLoginService */
	@Resource(name = "frontLoginService")
	private FrontLoginService frontLoginService;
	
	/** CommonCodeService */
	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;
	
	
	/**
	 * <PRE>
	 * 1. MethodName 	: Login
	 * 2. ClassName  	: FrontLoginController
	 * 3. Comment   	: 로그인
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2021. 08. 07. 오후 4:09:06
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/front/loginAjax.do")
	public void Login(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataMap param = RequestUtil.getDataMap(request);
		model.addAttribute("param", param);
		response.setContentType("text/html; charset=utf-8");

		JSONObject resultJSON = new JSONObject();
		String returnMsg = "";
		DataMap resultStats = new DataMap();

		String autoLoginId = CookieUtil.getCookieValue(request, "autoLoginId");
		String autoLoginPw = CookieUtil.getCookieValue(request, "autoLoginPw");
		
		if(!autoLoginId.equals("") && !autoLoginPw.equals("")){
			param.put("id", autoLoginId);
			param.put("pwd", SeedScrtyUtil.decryptText(autoLoginPw));
		}else {
			param.put("id", param.getString("user_id"));
			param.put("pwd",param.getString("user_pw"));
		}
		System.out.println("Id:"+param.getString("id"));
		System.out.println("Pw:"+param.getString("pwd"));
		
		if(!param.getString("id").equals("") && !param.getString("pwd").equals("")){
			String endPassword = EgovFileScrty.encryptSHA512(param.getString("pwd"));
			//String endPassword = param.getString("pwd");
			// 암호화된 비밀번호
			param.put("enPwd", endPassword);
			UserInfoVo userInfoVo = frontLoginService.selectUserInfo(param);
			
			if(userInfoVo != null){
				log.debug("##userInfoVo.getId:"+userInfoVo.getId());
				if(!userInfoVo.getPassword().equals(endPassword)){										//패스워드 불일치
					returnMsg ="패스워드를 확인하세요";
					resultStats.put("resultCode", "error");
					resultStats.put("resultMsg",  returnMsg);
					resultJSON.put("resultStats", resultStats);
					try {
						response.getWriter().write(resultJSON.toString());
					} catch (IOException e) {
						log.error(e);
					}
					return;
				}
				
				if(userInfoVo.getUserSttusCode().equals("10")){											//활동상태일경우만 섹션을 만들어줌
					
					
					//권한을 가져오자
					List resultList = frontLoginService.selectListUserauth(userInfoVo.getUserNo());
					String sAuthorId="";
					for(int i = 0; i < resultList.size(); i++){
						DataMap dataMap = (DataMap) resultList.get(i);
						if(i==0)	sAuthorId=dataMap.getString("AUTHOR_ID");
						else sAuthorId=sAuthorId+","+dataMap.getString("AUTHOR_ID");
					}		
					
					userInfoVo.setAuthorId(sAuthorId);
					request.getSession().setAttribute("userInfoVo", userInfoVo);
					
					//login DateTime Update 
					frontLoginService.updateUserLoginDt(userInfoVo.getUserNo());
					
					if(param.getString("checkAutoLogin").equals("on")){
						CookieUtil.addCookie(request, response, 60 * 60 * 24 * 365, "autoLoginId", param.getString("id"));
						CookieUtil.addCookie(request, response, 60 * 60 * 24 * 365, "autoLoginPw", SeedScrtyUtil.encryptText(param.getString("pwd")));
					}
					returnMsg ="로그인성공";
				}else {
					returnMsg ="활동상태 아닙니다.";
					resultStats.put("resultCode", "error");
					resultStats.put("resultMsg",  returnMsg);
					resultJSON.put("resultStats", resultStats);
					try {
						response.getWriter().write(resultJSON.toString());
					} catch (IOException e) {
						log.error(e);
					}
					return;
				}
				
			} else {																																			//아이디가 없는경우
				returnMsg ="존재하지 않는 아이디입니다.";
				resultStats.put("resultCode", "error");
				resultStats.put("resultMsg",  returnMsg);
				resultJSON.put("resultStats", resultStats);
				try {
					response.getWriter().write(resultJSON.toString());
				} catch (IOException e) {
					log.error(e);
				}
				return;
			}
		} else {
			returnMsg ="아이디, 패스워드를 확인 하세요";
			resultStats.put("resultCode", "error");
			resultStats.put("resultMsg",  returnMsg);
			resultJSON.put("resultStats", resultStats);
			try {
				response.getWriter().write(resultJSON.toString());
			} catch (IOException e) {
				log.error(e);
			}
			return;
		}
		
		//return 상태
		resultStats.put("resultCode", "ok");
		resultStats.put("resultMsg",  returnMsg);
		resultJSON.put("resultStats", resultStats);
		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
		
	}	
	

	@RequestMapping(value = "/front/logout.do")
	public String logout(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		request.getSession().removeAttribute("userInfoVo");
		CookieUtil.removeCookie(request, response, "autoLoginId");
		CookieUtil.removeCookie(request, response, "autoLoginPw");
		return "redirect:/";
	}
}
