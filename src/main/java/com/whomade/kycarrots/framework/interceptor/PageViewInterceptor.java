/**
 *
 * 1. FileName : PageViewInterceptor.java
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

import java.util.Arrays;
import java.util.List;

import com.whomade.kycarrots.framework.common.constant.Const;
import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.DateUtil;
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.SessionUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : PageViewInterceptor.java
 * 3. Package  : egovframework.framework.interceptor
 * 4. Comment  : 
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   : 2013. 8. 27. 오후 6:40:06
 * </PRE>
 */
public class PageViewInterceptor implements HandlerInterceptor {
	
	private static Log log = LogFactory.getLog(PageViewInterceptor.class);
	
	/** commonDao */
	@Resource(name="commonMybatisDao")
    private CommonMybatisDao commonMybatisDao;
	
	@Resource(name="egovMessageSource")
    private EgovMessageSource egovMessageSource;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		// ################# PAGE VIEW INSERT START ################# //
		// 페이지 URL
		String reqUrl = request.getRequestURI();
		DataMap param = RequestUtil.getDataMap(request);
		
		// url이 do가 아닌경우 쌓지 않는다.현재 do 호출후 상세페이지에서 header.do 등 import do 때문에 jsp 페이지로 다시 호출하게 되어서 jsp 호출 이력은 제외한다.
		if(reqUrl.lastIndexOf(".jsp") == -1){
			// 특정 URL들은 제외한다.
			List exceptUrl = Arrays.asList(
                    "/common/inc/selectLeftMenuListAjax.do"                // 좌측메뉴조회
					, "/admin/login.do"									// 로그인 페이지
					, "/common/file/viewFile.do"                        // 이미지 뷰어
			);
			
			String ip = request.getHeader("X-FORWARDED-FOR");
			if(ip ==null) ip =request.getRemoteAddr();
			System.out.println("******ip["+ip+"]*******************");
			
			
			if(!exceptUrl.contains(reqUrl)){
				// 파라미터 json string으로 변환
				JSONObject resultJSON = new JSONObject();
				resultJSON.accumulateAll(param);
				
				DataMap pvInfo = new DataMap();
				
				UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
				if(userInfoVo != null){
					pvInfo.put("ss_user_no", userInfoVo.getUserNo());
				}
				
				pvInfo.put("visit_de", DateUtil.getToday("yyyyMMdd"));
				pvInfo.put("page_url", reqUrl);
				pvInfo.put("page_paramtr", resultJSON.toString());
				pvInfo.put("acces_se", Const.accesSeAdmin);
				pvInfo.put("visit_ip", ip);
				
				commonMybatisDao.insert("common.insertPageView", pvInfo);
			}
		}
		
		// ################# PAGE VIEW INSERT END ################# //
		
		return true;
		
	}
}
