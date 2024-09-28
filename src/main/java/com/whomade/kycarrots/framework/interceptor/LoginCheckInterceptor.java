/**
 *
 * 1. FileName : LoginCheckInterceptor.java
 * 2. Package : egovframework.framework.interceptor
 * 3. Comment : 
 * 4. 작성자  : SooHyun.Seo
 * 5. 작성일  : 2013. 8. 27. 오후 6:40:06
 * 6. 변경이력 : 
 *    이름     : 일자          : 근거자료   : 변경내용
 *    ------------------------------------------------------
 *    SooHyun.Seo : 2013. 8. 27. :            : 신규 개발.
 */

package com.whomade.kycarrots.framework.interceptor;

import java.io.IOException;
import java.net.URLEncoder;

import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import com.whomade.kycarrots.framework.common.util.MessageUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.framework.common.constant.Const;
import com.whomade.kycarrots.framework.common.object.DataMap;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : LoginCheckInterceptor.java
 * 3. Package  : egovframework.framework.interceptor
 * 4. Comment  : 
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   : 2013. 8. 27. 오후 6:40:06
 * </PRE>
 */
public class LoginCheckInterceptor implements HandlerInterceptor {
	
	private static Log log = LogFactory.getLog(LoginCheckInterceptor.class);
	
	@Resource(name="egovMessageSource")
    private EgovMessageSource egovMessageSource;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.debug("################################LoginCheckInterceptor################################################");

		// session검사
		HttpSession session = request.getSession();
		
		UserInfoVo userInfoVo = (UserInfoVo)session.getAttribute("userInfoVo");
		
		//파라메터 넘어오는것 수정 2018.04.05 soohyun.Seo	
		String reqUrl 	= request.getRequestURI();
		if(request.getQueryString()!=null){
			reqUrl=reqUrl+"?"+request.getQueryString();
		}
		
		if (userInfoVo != null && userInfoVo.getUserNo() != null && !userInfoVo.getUserNo().equals("")) {		
			
			if(log.isDebugEnabled()){
				log.debug("################################################################################");
				log.debug("## User Session is Live");
				log.debug("################################################################################");
			}
				
			return true;
		}
		
		else{
			
			if(reqUrl.indexOf("Ajax") > 0){
				DataMap resultStats = new DataMap();
				resultStats.put("resultCode", "error");
				resultStats.put("resultMsg", egovMessageSource.getMessage("dmsg.do.login"));
				JSONObject resultJSON = new JSONObject();
				resultJSON.put("resultStats", resultStats);
				response.setContentType("text/html; charset=utf-8");
				try {
					response.getWriter().write(resultJSON.toString());
				} catch (IOException e) {
					log.error(e);
				}
			}
			else{
				MessageUtil.setMessage(request, egovMessageSource.getMessage("msg.do.login"));
				response.sendRedirect(Const.doLogin+"?ru="+URLEncoder.encode(reqUrl, "UTF-8"));
			}			
			
			if(log.isDebugEnabled()){
				log.debug("################################################################################");
				log.debug("## User Session is Dead");
				log.debug("################################################################################");
			}
			
			return false;
		}
		
	}
}
