package com.whomade.kycarrots.framework.common.util;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import com.whomade.kycarrots.framework.common.object.DataMap;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

public class ExceptionResolver extends AbstractHandlerExceptionResolver {
	
	private static Log log = LogFactory.getLog(ExceptionResolver.class);
	
	private String defaultErrorView;

	public void setDefaultErrorView(String defaultErrorView) {
		this.defaultErrorView = defaultErrorView;
	}
	
	// controller 에서 나는 에러를 여기서 log를 남기기 위해 사용
	@Override
	public ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object obj, Exception exception) {

		exception.printStackTrace();

		ModelAndView model = new ModelAndView();
		model.setViewName(defaultErrorView);

		DataMap param = new DataMap();
		param.put("error", getStackTrace(exception));
		model.addObject("param", param);

		return model;
	}

	public String getStackTrace(Exception e) {
		StringBuffer sBuf = new StringBuffer();
		sBuf.append(e);
		StackTraceElement[] trace = e.getStackTrace();
		for (int i = 0; i < trace.length; i++) {
			sBuf.append("\n " + trace[i]);
		}
		return sBuf.toString();
	}


}