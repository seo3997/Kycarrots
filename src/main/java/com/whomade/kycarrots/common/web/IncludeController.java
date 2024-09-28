package com.whomade.kycarrots.common.web;

import java.io.IOException;
import java.util.List;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.common.service.CommonCodeService;
import com.whomade.kycarrots.common.service.IncludeService;
import com.whomade.kycarrots.framework.common.constant.Const;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.SessionUtil;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class IncludeController {

	@Resource(name = "egovMessageSource")
	private EgovMessageSource egovMessageSource;
	
	/** CommonCodeService */
	@Resource(name = "includeService")
	private IncludeService includeService;
	
	/** CommonCodeService */
	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;

	@RequestMapping(value = "/common/inc/header.do")
	public String header(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		
		DataMap param = RequestUtil.getDataMap(request);
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());


		//CMS메뉴코드 
		param.put("up_menu_id", Const.upCodeCmsCode);
		
		
		List topMenuList = includeService.selectTopMenuList(param);

		String requestUri = request.getRequestURI();
		String queryString = request.getQueryString();
		String url= UriComponentsBuilder.fromUriString(requestUri)
				.query(queryString)
				.build()
				.toString();

//		if(url.indexOf("selectPageListData.do") > -1){
//			url += "?bbs_code="+param.getString("bbs_code");
//		}
		
		param.put("url", url);
		DataMap resultMap = includeService.selectMenuByUrl(param);

		
		String top_menu_id = Const.upCodeTopMenuId;
		String top_menu_nm="";
		if ( resultMap != null ) {
			//top_menu_id = resultMap.getString("MENU_ID").substring(0, 12);
			if(resultMap.getString("MENU_ID").length()>15)
			top_menu_id = resultMap.getString("MENU_ID").substring(0, 16);
			else top_menu_id = resultMap.getString("MENU_ID");
		}

        for (Object tempList : topMenuList ){
        	DataMap atemp=(DataMap)tempList;
        	if(top_menu_id.equals(atemp.get("MENU_ID")))	top_menu_nm=atemp.getString("MENU_NM");
        }
		
		model.addAttribute("top_menu_id", top_menu_id);
		model.addAttribute("top_menu_nm", top_menu_nm);
		model.addAttribute("topMenuList", topMenuList);
		
		return "common/inc/header";
	}
	
	@RequestMapping(value = "/common/inc/menu.do")
	public String menu(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
				
		DataMap param = RequestUtil.getDataMap(request);
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		 
		
		//CMS메뉴코드

		String requestUri = request.getRequestURI();
		String queryString = request.getQueryString();
		String url= UriComponentsBuilder.fromUriString(requestUri)
				.query(queryString)
				.build()
				.toString();

		String top_menu_id = Const.upCodeTopMenuId;
		
//		if(url.indexOf("selectPageListData.do") > -1){
//			url += "?bbs_code="+param.getString("bbs_code");
//		}

		//spring boot에서는 WEB-INF/views는 제외한다.
		int startIndex = "/WEB-INF/views/".length() - 1;
		url = url.substring(startIndex);
		url= url.substring(0, url.lastIndexOf(".jsp")) + ".do";

		param.put("url", url);
		DataMap resultMap = includeService.selectMenuByUrl(param);

		if ( resultMap != null ) {
			model.addAttribute("menu_id", resultMap.getString("MENU_ID"));
			//top_menu_id = resultMap.getString("MENU_ID").substring(0, 12);
			if(resultMap.getString("MENU_ID").length()>15) top_menu_id = resultMap.getString("MENU_ID").substring(0, 16);
			else	top_menu_id = resultMap.getString("MENU_ID");
			model.addAttribute("cur_menu_nm", resultMap.getString("MENU_NM"));
		}
		
		model.addAttribute("top_menu_id", top_menu_id);
		
		param.put("authorId", userInfoVo.getAuthorId());
		
		return "common/inc/menu";
	}
	
	@RequestMapping(value = "/common/inc/selectLeftMenuListAjax.do")
	public @ResponseBody void selectLeftMenuListAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

				
		DataMap param = RequestUtil.getDataMap(request);
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		param.put("authorId", userInfoVo.getAuthorId());
		
		JSONObject resultJSON = new JSONObject();
		
		List leftMenuList = includeService.selectLeftMenuList(param);
		
		
		//return 상태
		DataMap resultStats = new DataMap();
		resultStats.put("resultCode", "ok");
		resultStats.put("resultMsg", "");
		resultStats.put("leftMenuList", leftMenuList);
		resultJSON.put("resultStats", resultStats);

		response.setContentType("text/html; charset=utf-8");
		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
//			log.error(e);
		}
		
	}
}
