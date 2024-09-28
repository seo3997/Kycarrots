package com.whomade.kycarrots.framework.common.util;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SimpleCORSFilter  implements Filter {
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletResponse response = (HttpServletResponse) res;
		
		if (!(req instanceof HttpServletRequest)) {
			throw new ServletException("This filter can "+ " only process HttpServletRequest requests");
		}
        
		//response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        //response.setHeader("Access-Control-Max-Age", "3600");
        
		response.setHeader("Access-Control-Allow-Headers", "origin, x-requested-with, content-type, accept");
        response.setHeader("Access-Control-Allow-Origin", "*");  /*여기의 *을 내가 허용하고 싶은 특정 도메인으로 바꾸면 설정한 도메인에 한에서만 크로스 도메인을 허용하게된다. 여러 도메인의 경우 여러번 설정하면된다. */
        
        chain.doFilter(req, res);
    
	}

	@Override
    public void init(FilterConfig filterConfig) {}

	@Override
    public void destroy() {}

}

