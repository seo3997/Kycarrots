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

import java.net.URLEncoder;

import com.whomade.kycarrots.framework.common.constant.Const;
import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import com.whomade.kycarrots.framework.common.util.MessageUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;

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
public class AuthCheckInterceptor implements HandlerInterceptor {
	
	private static Log log = LogFactory.getLog(AuthCheckInterceptor.class);
	
	/** commonDao */
	@Resource(name="commonMybatisDao")
	private CommonMybatisDao commonMybatisDao;
	
	@Resource(name="egovMessageSource")
	private EgovMessageSource egovMessageSource;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub

		// session검사
		HttpSession session = request.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.getAttribute("userInfoVo");
		String requestUrl = request.getRequestURI();

		//파라메터 넘어오는것 수정 2018.04.05 soohyun.Seo	
		String reqUrl 	= request.getRequestURI();
		if(request.getQueryString()!=null){
			reqUrl=reqUrl+"?"+request.getQueryString();
		}

		
		log.debug("################################AuthCheckInterceptor################################################");

		if (userInfoVo != null && userInfoVo.getId() != null && !userInfoVo.getId().equals("")) {
			/*
			DataMap dataMap = new DataMap();
			dataMap.put("ss_user_no", userInfoVo.getUserNo());
			List authMenuList = commonMybatisDao.selectList("common.selectListUserAcessMenu", dataMap);
			String reqUrl = requestUrl;
			
			if(log.isDebugEnabled()){
				log.debug("###request.getRequestURI():"+request.getRequestURI());
			}
			
			if(authMenuList.contains(reqUrl)){
				log.debug("##Allow Auth Menu##");
				return true;
			}
			else{
				
				if(reqUrl.indexOf("Ajax") > 0){
					DataMap resultStats = new DataMap();
					resultStats.put("resultCode", "error");
					resultStats.put("resultMsg", egovMessageSource.getMessage("error.access.menu"));
					JSONObject resultJSON = new JSONObject();
					resultJSON.put("resultStats", resultStats);
					response.setContentType("application/json; charset=utf-8");
					try {
						response.getWriter().write(resultJSON.toString());
					} catch (IOException e) {
						log.error(e);
					}
				}
				else{
					MessageUtil.setMessage(request, egovMessageSource.getMessage("error.access.menu"));
					response.sendRedirect(Const.doHome);
				}
				
				if(log.isDebugEnabled())
					log.debug("##Not Auth Menu##");
				return false;				
			}
			*/			
			return true;

		}
		else{
			MessageUtil.setMessage(request, egovMessageSource.getMessage("msg.do.login"));
			
			response.sendRedirect(Const.doLogin+"?ru="+URLEncoder.encode(reqUrl, "UTF-8"));

			//response.sendRedirect(Const.doLogin);
			if(log.isDebugEnabled())
				log.debug("##Session is Dead##");
			return false;
		}		
		
	}
}
