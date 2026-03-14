package com.whomade.kycarrots.front.main.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontMainController {

	private static Log log = LogFactory.getLog(FrontMainController.class);

	@RequestMapping(value = "/")
	public String home(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		HttpSession session = request.getSession();
		// 만약 세션에 지점 정보가 있다면 즉각 쇼핑 리스트로 이동합니다.
		if (session.getAttribute("BRANCH_ID") != null) {
			return "redirect:/shop/list.do";
		}

		// 본사(메인 도메인)로 바로 접속한 경우 프론트 인덱스로 이동시킵니다.
		return "redirect:/front_index.do";
	}

	@RequestMapping(value = "/front_index.do")
	public String frontIndex(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		return "front/front_index";
	}

}
