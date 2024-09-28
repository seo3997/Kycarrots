/**
 *
 * 1. FileName : DebugInterceptor.java
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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/
import com.whomade.kycarrots.framework.common.util.EgovPropertiesUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DebugInterceptor.java
 * 3. Package  : egovframework.framework.interceptor
 * 4. Comment  : 
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   : 2013. 8. 27. 오후 6:40:06
 * </PRE>
 */
public class DebugInterceptor implements HandlerInterceptor {
	
	private static Log log = LogFactory.getLog(DebugInterceptor.class);
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String printYn = "N";
		if(EgovPropertiesUtil.getProperty("interceptor.log.print.yn") != null){
			printYn = EgovPropertiesUtil.getProperty("interceptor.log.print.yn");
		}
		
		if(printYn.equals("Y")){		
			
			log.debug("####################################DebugInterceptor############################################");
			log.debug("##" + request.getRequestURI());
			log.debug("#####################################DebugInterceptor###########################################");
			
			int initBlankCnt = 15;
			String space = "";
			int keyNameLenghtCnt = 0;
			String valueStr = ""; 
					
			
			Enumeration<?> e = request.getParameterNames();
			
			log.debug("--------------------------------------------------------------------------------");
			log.debug("|Name           |Value");
			log.debug("--------------------------------------------------------------------------------");
			
			while(e.hasMoreElements()){
				
				space = "";
				valueStr = "";
				
				String key = (String)e.nextElement();
				String[] values = request.getParameterValues(key);	
				
				keyNameLenghtCnt = (key).length();
				
				for(int i = 0; i < initBlankCnt - keyNameLenghtCnt; i++){
					space += " ";
				}
				
				if (values.length > 1) {
					
					List<Object> list = new ArrayList<Object>(values.length);
					for (int i = 0; i < values.length; i++) {
						
						valueStr+="{"+values[i]+"}";					
					}				
					log.debug("|"+key+space+"|"+valueStr);
					
						
				} else {
					
					if(log.isDebugEnabled()){					
						
						log.debug("|"+key+space+"|"+request.getParameter(key));					
					}
				}		
				
				log.debug("--------------------------------------------------------------------------------");
			}
		}		
		
		return true;		
	}
}
