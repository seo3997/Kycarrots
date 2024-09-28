package com.whomade.kycarrots.mgt.main.web;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.admin.user.service.UserMgtService;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.SessionUtil;
import com.whomade.kycarrots.mgt.mboard.service.MicroBizBoardService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Handles requests for the application home page.
 */
@Controller
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	

	@Resource(name = "userMgtService")
	private UserMgtService userMgtService;
	
	@Resource(name = "microBizBoardService")
	private MicroBizBoardService boardService;


	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/mgt/main/dashBoard.do", method = RequestMethod.GET)
	public String newsmain(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		DataMap param = RequestUtil.getDataMap(request);
		
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		//List resultList = userMgtService.selectPageListUser(param);
		
		//공지사항 리스트 조회
		param.put("sch_bbs_se_code", 	 "10");		//공지사항 
		List<DataMap> resultBoardList = boardService.selectListBoard(param);

		
		model.addAttribute("resultBoardList", resultBoardList);
		model.addAttribute("paramin",param);
		
		
		
		return "admin/main";
	}
	
}
