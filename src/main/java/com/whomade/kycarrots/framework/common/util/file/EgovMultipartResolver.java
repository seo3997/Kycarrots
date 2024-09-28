package com.whomade.kycarrots.framework.common.util.file;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

public class EgovMultipartResolver extends StandardServletMultipartResolver {

	@Override
	public void setResolveLazily(boolean resolveLazily) {
		super.setResolveLazily(resolveLazily);
	}

	@Override
	public void setStrictServletCompliance(boolean strictServletCompliance) {
		super.setStrictServletCompliance(strictServletCompliance);
	}

	@Override
	public boolean isMultipart(HttpServletRequest request) {
		return super.isMultipart(request);
	}

	@Override
	public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {
		return super.resolveMultipart(request);
	}

	@Override
	public void cleanupMultipart(MultipartHttpServletRequest request) {
		super.cleanupMultipart(request);
	}

	public EgovMultipartResolver() {
	}


}
