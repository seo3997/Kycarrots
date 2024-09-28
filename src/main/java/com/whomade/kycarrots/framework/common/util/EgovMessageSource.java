package com.whomade.kycarrots.framework.common.util;

import java.util.Locale;

import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class EgovMessageSource {

	private final ReloadableResourceBundleMessageSource messageSource;

	public EgovMessageSource(ReloadableResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public ReloadableResourceBundleMessageSource getReloadableResourceBundleMessageSource() {
		return messageSource;
	}

	public String getMessage(String code) {
		try {
			return messageSource.getMessage(code, null, Locale.KOREAN);
		} catch (NoSuchMessageException e) {
			return e.toString();
		}
	}

	public String getMessage(String code, String[] args) {
		try {
			return messageSource.getMessage(code, args, Locale.KOREAN);
		} catch (NoSuchMessageException e) {
			return e.toString();
		}
	}

	public String getMessage(String code, String locale) {
		return getMessage(code, null, locale);
	}

	public String getMessage(String code, String[] args, String locale) {
		String rtnMessage = "";

		try {
			if ("KO".equals(locale)) {
				rtnMessage = messageSource.getMessage(code, args, Locale.KOREAN);
			} else if ("CN".equals(locale)) {
				rtnMessage = messageSource.getMessage(code, args, Locale.CHINESE);
			}

			if (rtnMessage == null || rtnMessage.isEmpty()) {
				rtnMessage = messageSource.getMessage(code, args, Locale.KOREAN);
			}
		} catch (NoSuchMessageException e) {
			rtnMessage = e.toString();
		}

		return rtnMessage;
	}
}
