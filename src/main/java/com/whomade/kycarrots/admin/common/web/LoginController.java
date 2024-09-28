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

package com.whomade.kycarrots.admin.common.web;

import java.net.URLDecoder;
import java.util.List;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whomade.kycarrots.admin.common.service.LoginService;
import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.common.service.CommonCodeService;
import com.whomade.kycarrots.framework.common.constant.Const;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.CookieUtil;
import com.whomade.kycarrots.framework.common.util.EgovFileScrty;
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import com.whomade.kycarrots.framework.common.util.MessageUtil;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.SeedScrtyUtil;
import com.whomade.kycarrots.framework.common.util.SessionUtil;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : LoginController.java
 * 3. Package  : egovframework.front.common.web
 * 4. Comment  : 
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   : 2017.12.22. 오전 9:55:47
 * </PRE>
 */
@Controller
public class LoginController {

	private static Log log = LogFactory.getLog(LoginController.class);

	@Resource(name = "egovMessageSource")
	private EgovMessageSource egovMessageSource;

	/** adminLoginService */
	@Resource(name = "loginService")
	private LoginService loginService;	
	
	/** CommonCodeService */
	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;
	
	
	/**
	 * <PRE>
	 * 1. MethodName : Login
	 * 2. ClassName  : LoginController
	 * 3. Comment   : 
	 * 4. 작성자    : 박재현
	 * 5. 작성일    : 2015. 8. 21. 오후 3:12:24
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/admin/login.do")
 	public String Login(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo ssUserInfoVo = SessionUtil.getSessionUserInfoVo(request);
		
		model.addAttribute("param", param);
		
		if (ssUserInfoVo != null && ssUserInfoVo.getId() != null && !ssUserInfoVo.getId().equals("")) {
			return "redirect:" + Const.doHome;
		}
		
		String autoLoginId = CookieUtil.getCookieValue(request, "autoLoginId");
		String autoLoginPw = CookieUtil.getCookieValue(request, "autoLoginPw");
		
		if(!autoLoginId.equals("") && !autoLoginPw.equals("")){
			param.put("id", autoLoginId);
			param.put("pwd", SeedScrtyUtil.decryptText(autoLoginPw));
		}		
		
		if(!param.getString("id").equals("") && !param.getString("pwd").equals("")){
			String endPassword = EgovFileScrty.encryptSHA512(param.getString("pwd"));
			//String endPassword = param.getString("pwd");
			// 암호화된 비밀번호
			param.put("enPwd", endPassword);
			UserInfoVo userInfoVo = loginService.selectUserInfo(param);
			
			if(userInfoVo != null){
				log.debug("##userInfoVo.getId:"+userInfoVo.getId());
				if(!userInfoVo.getPassword().equals(endPassword)){									//패스워드 불일치
					MessageUtil.setMessage(request, egovMessageSource.getMessage("error.pwd.wrong"));
					return Const.jspLogin;
				}
				
				if(userInfoVo.getUserSttusCode().equals("10")){											//활동상태일경우만 섹션을 만들어줌
					//지역을 가져오자 
					param.put("area_code_m", userInfoVo.getAreaCode());
					param.put("area_code_s", userInfoVo.getAreaCodeS());
					param.put("area_code_d", userInfoVo.getAreaCodeD());
				    String sAreaName = loginService.selectUserAreaName(param);
				    userInfoVo.setAreaName(sAreaName);
					
					
					//권한을 가져오자
					List resultList = loginService.selectListUserauth(userInfoVo.getUserNo());
					String sAuthorId="";
					for(int i = 0; i < resultList.size(); i++){
						DataMap dataMap = (DataMap) resultList.get(i);
						if(i==0)	sAuthorId=dataMap.getString("AUTHOR_ID");
						else sAuthorId=sAuthorId+","+dataMap.getString("AUTHOR_ID");
					}		
					
					userInfoVo.setAuthorId(sAuthorId);
					request.getSession().setAttribute("userInfoVo", userInfoVo);
					
					//login DateTime Update 
					loginService.updateUserLoginDt(userInfoVo.getUserNo());
					
					if(param.getString("checkAutoLogin").equals("on")){
						CookieUtil.addCookie(request, response, 60 * 60 * 24 * 365, "autoLoginId", param.getString("id"));
						CookieUtil.addCookie(request, response, 60 * 60 * 24 * 365, "autoLoginPw", SeedScrtyUtil.encryptText(param.getString("pwd")));
					}
					String returnPage = Const.doHome;
					if(!param.getString("ru").equals("")){
						//파라메터 넘어오는것 수정 2018.04.05 soohyun.Seo	
						//returnPage = param.getString("ru");
						returnPage = request.getParameter("ru");
						returnPage=  URLDecoder.decode(returnPage,"UTF-8");
						
					}
					
					return "redirect:" + returnPage;
				}else if(userInfoVo.getUserSttusCode().equals("0")){
					MessageUtil.setMessage(request, egovMessageSource.getMessage("error.common.login0"));
					return Const.jspLogin;
				}else if(userInfoVo.getUserSttusCode().equals("20")){
					MessageUtil.setMessage(request, egovMessageSource.getMessage("error.common.login20"));
					return Const.jspLogin;
				}else if(userInfoVo.getUserSttusCode().equals("99")){
					MessageUtil.setMessage(request, egovMessageSource.getMessage("error.common.login99"));
					request.getSession().setAttribute("errUserId", param.getString("id"));
					return Const.jspLogin;
				}else {
					MessageUtil.setMessage(request, egovMessageSource.getMessage("error.common.login"));
					return Const.jspLogin;
				}
				
			} else {																																			//아이디가 없는경우
				MessageUtil.setMessage(request, egovMessageSource.getMessage("error.no.id"));
				return Const.jspLogin;
			}
		} else {
//			MessageUtil.setMessage(request, egovMessageSource.getMessage("error.common.login"));
			return Const.jspLogin;		
		}
	}	
	

	@RequestMapping(value = "/admin/logout.do")
	public String logout(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		request.getSession().removeAttribute("userInfoVo");
		CookieUtil.removeCookie(request, response, "autoLoginId");
		CookieUtil.removeCookie(request, response, "autoLoginPw");
		return "redirect:" + Const.doFront;
	}
}
