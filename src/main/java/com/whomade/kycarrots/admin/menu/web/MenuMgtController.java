
package com.whomade.kycarrots.admin.menu.web;

import java.io.IOException;
import java.util.List;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.admin.menu.service.MenuMgtService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import com.whomade.kycarrots.common.service.CommonCodeService;
import com.whomade.kycarrots.framework.common.constant.Const;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import com.whomade.kycarrots.framework.common.util.MessageUtil;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.SessionUtil;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : MenuMgtController.java
 * 3. Package  : egovframework.admin.menu.web
 * 4. Comment  : 
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   : 2017.12.22 오전 10:54:21
 * 7. 변경이력 : 
 *    이름     : 일자          : 근거자료   : 변경내용
 *    ------------------------------------------------------
 *    SooHyun.Seo : 2017.12.22 :            : 신규 개발.
 * </PRE>
 */ 
@Controller
public class MenuMgtController {

	private static Log log = LogFactory.getLog(MenuMgtController.class);

	@Resource(name = "egovMessageSource")
	private EgovMessageSource egovMessageSource;

	/** menuMgtService */
	@Resource(name = "menuMgtService")
	private MenuMgtService menuMgtService;

	/** CommonCodeService */
	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;
	
	/**
	 * <PRE>
	 * 1. MethodName : selectListMenuMgt
	 * 2. ClassName  : MenuMgtController
	 * 3. Comment   : 메뉴 리스트 조회 
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 4:04:02
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/admin/menu/selectListMenuMgt.do")
	public String selectListMenuMgt(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);		
		
		
		int totCnt = menuMgtService.selectTotCntMenu(param); //메뉴 전체갯수 
		param.put("totalCount", totCnt);
		List resultList = menuMgtService.selectListMenu(param); //메뉴 리스트정보 가져오기
		

		model.addAttribute("resultList", resultList);
		model.addAttribute("param", param);
		
		DataMap codeParam = new DataMap();
		codeParam.put("group_id", Const.upCodeMenuTypeCode); //메뉴 유형 정보정보
		
		List menuTypeComboStr = commonCodeService.selectCodeList(codeParam);
		
		codeParam.put("group_id", Const.upCodeYn); //메뉴 전시여부
		List ynComboStr = commonCodeService.selectCodeList(codeParam);
		
		model.addAttribute("menuTypeComboStr", menuTypeComboStr);
		model.addAttribute("ynComboStr", ynComboStr);

		return "admin/menu/selectListMenuMgt";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : selectMenuMgtAjax
	 * 2. ClassName  : MenuMgtController
	 * 3. Comment   : 메뉴 상세 조회
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 4:04:36
	 * </PRE>
	 *   @return void
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @throws Exception
	 */
	@RequestMapping(value = "/admin/menu/selectMenuMgtAjax.do")
	public @ResponseBody void selectMenuMgtAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);	
		
		param.put("up_code_id", Const.upCodeMenuTypeCode);//메뉴 유형 정보
		
		DataMap resultMap = menuMgtService.selectMenu(param);//메뉴 정보 가져오기

		JSONObject resultJSON = new JSONObject();
		resultJSON.put("resultMap", resultMap);
		
		//return 상태
		DataMap resultStats = new DataMap();
		resultStats.put("resultCode", "ok");
		resultStats.put("resultMsg", "");
		resultJSON.put("resultStats", resultStats);//리턴값 : 선택된 메뉴 정보
		
		response.setContentType("text/html; charset=utf-8");
		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
	}
	

	/**
	 * <PRE>
	 * 1. MethodName : selectExistYnMenuMgtAjax
	 * 2. ClassName  : MenuMgtController
	 * 3. Comment   : 메뉴 존재 여부 체크 
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 4:15:12
	 * </PRE>
	 *   @return void
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @throws Exception
	 */
	@RequestMapping(value = "/admin/menu/selectExistYnMenuMgtAjax.do")
	public @ResponseBody void selectExistYnMenuMgtAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		DataMap resultMap = menuMgtService.selectExistYnMenu(param);//메뉴 존재 여부 확인

		JSONObject resultJSON = new JSONObject();
		resultJSON.put("resultMap", resultMap);
		

		//return 상태
		DataMap resultStats = new DataMap();
		resultStats.put("resultCode", "ok");
		resultStats.put("resultMsg", "");
		resultJSON.put("resultStats", resultStats); //리턴값 Y / N

		response.setContentType("text/html; charset=utf-8");
		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
	}


	/**
	 * <PRE>
	 * 1. MethodName : insertMenuMgt
	 * 2. ClassName  : MenuMgtController
	 * 3. Comment   : 메뉴 등록 
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 4:15:29
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @param status
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/admin/menu/insertMenuMgt.do")
	public String insertMenuMgt(HttpServletRequest request, HttpServletResponse response, ModelMap model, SessionStatus status) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_id", userInfoVo.getUserNo());
		
		int upMenuLv = param.getInt("menu_lv");//메뉴 레벨(뎁스)
		param.put("new_menu_lv", upMenuLv+1);
		
		
		
		
		List newMenuId = param.getList("new_menu_id");//새로운 메뉴 코드
		DataMap menuExistMap = null;
		
		DataMap tmp = new DataMap();
		for(int i = 0; i < newMenuId.size(); i++){
			tmp.put("new_menu_id", param.getString("menu_id")+(String)newMenuId.get(i));
			menuExistMap = menuMgtService.selectExistYnMenu(tmp);	
			
			if(menuExistMap.getString("EXIST_YN").equals("Y")){
				String[] dupMenuIdArray = new String[1];
				dupMenuIdArray[0] = param.getString("menu_id")+(String)newMenuId.get(i);
				MessageUtil.setMessage(request, egovMessageSource.getMessage("error.menu.id.dup", dupMenuIdArray ));
				return "redirect:/admin/menu/menuSelectList.do";
			}
		}
		menuMgtService.insertMenu(param);
		model.addAttribute("param", param);
		MessageUtil.setMessage(request, egovMessageSource.getMessage("succ.data.insert"));
		status.setComplete();
		
		return "redirect:/admin/menu/selectListMenuMgt.do";
	}
	

	/**
	 * <PRE>
	 * 1. MethodName : updateMenuMgt
	 * 2. ClassName  : MenuMgtController
	 * 3. Comment   : 메뉴 수정 
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 4:16:28
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @param status
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/admin/menu/updateMenuMgt.do")
	public String updateMenuMgt(HttpServletRequest request, HttpServletResponse response, ModelMap model, SessionStatus status) throws Exception {
		
		DataMap param = RequestUtil.getDataMap(request);
		
		param.put("url", param.getStringOrgn("url"));

		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());

		menuMgtService.updateMenu(param);//해당 메뉴 업데이트
		menuMgtService.updateMenuSub(param);//하위 메뉴 업데이트
		
		model.addAttribute("param", param);
		MessageUtil.setMessage(request, egovMessageSource.getMessage("succ.data.update"));
		status.setComplete();
		return "redirect:/admin/menu/selectListMenuMgt.do";
	}


	/**
	 * <PRE>
	 * 1. MethodName : deleteMenuMgt
	 * 2. ClassName  : MenuMgtController
	 * 3. Comment   : 메뉴 삭제 
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 4:17:21
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @param status
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/admin/menu/deleteMenuMgt.do")
	public String deleteMenuMgt(HttpServletRequest request, HttpServletResponse response, ModelMap model, SessionStatus status) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		
		/*
		DataMap existSubMenuYnMap = menuMgtService.subMenuExistYnSelect(param);
		if(existSubMenuYnMap.getString("EXIST_SUB_YN").equals("Y")){			
			
			MessageUtil.setMessage(request, egovMessageSource.getMessage("fail.delete.menu.submenu", ssAdminInfoVo.getLanguage()));
			return "redirect:/admin/menu/selectMenuList.do";
		}*/
		menuMgtService.deleteMenu(param);//선택된 메뉴 및 하위 메뉴 삭제
		model.addAttribute("param", param);
		MessageUtil.setMessage(request, egovMessageSource.getMessage("succ.data.delete"));
		status.setComplete();
		return "redirect:/admin/menu/selectListMenuMgt.do";
	}
	/**
	 * <PRE>
	 * 1. MethodName : selectExistSortMenuMgtAjax
	 * 2. ClassName  : MenuMgtController
	 * 3. Comment   : 메뉴 정렬 정보 존재 여부 체크 
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 4:15:12
	 * </PRE>
	 *   @return void
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @throws Exception
	 */
	@RequestMapping(value = "/admin/menu/selectExistSortMenuMgtAjax.do")
	public @ResponseBody void selectExistSortMenuMgtAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		DataMap resultMap = menuMgtService.selectExistSortSubMenu(param);//메뉴의 동일 뎁스의 정렬 순서가 존재하는지 확인

		JSONObject resultJSON = new JSONObject();
		resultJSON.put("resultMap", resultMap);
		

		//return 상태
		DataMap resultStats = new DataMap();
		resultStats.put("resultCode", "ok");
		resultStats.put("resultMsg", "");
		resultJSON.put("resultStats", resultStats);//return 값 Y:N

		response.setContentType("text/html; charset=utf-8");
		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
	}

}
