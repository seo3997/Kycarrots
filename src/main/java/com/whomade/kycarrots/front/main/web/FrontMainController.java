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
		if (session.getAttribute("BRANCH_ID") != null) {
			return "redirect:/shop/list.do";
		}
		return "home";
	}





}
