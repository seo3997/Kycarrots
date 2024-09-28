package com.whomade.kycarrots.admin.author.web;

import java.io.IOException;
import java.util.List;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
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

import com.whomade.kycarrots.admin.authmenu.service.AuthorMgtService;
import com.whomade.kycarrots.common.service.CommonCodeService;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.page.util.pageNavigationUtil;
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import com.whomade.kycarrots.framework.common.util.MessageUtil;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.SessionUtil;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : AuthorMgtController.java
 * 3. Package  : egovframework.admin.author.web
 * 4. Comment  : 권한 관리 
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   : 2017.12.22. 오후 3:44:37
 * </PRE>
 */ 
@Controller
public class AuthorMgtController {
	
	private static Log log = LogFactory.getLog(AuthorMgtController.class);
	
	@Resource(name="egovMessageSource")
    private EgovMessageSource egovMessageSource;

	/** authorMgtService */
    @Resource(name = "authorMgtService")
    private AuthorMgtService authorMgtService;
    
    /** CommonCodeService */
	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;
	

	/**
	 * <PRE>
	 * 1. MethodName : selectPageListAuthorMgt
	 * 2. ClassName  : AuthorMgtController
	 * 3. Comment   : 권한 목록
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 3:44:49
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value="/admin/author/selectPageListAuthorMgt.do")
    public String selectPageListAuthorMgt(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		DataMap param = RequestUtil.getDataMap(request);
    	/* ### Pasing 시작 ### */
    	int totCnt = authorMgtService.selectTotCntAuthor(param);
    	param.put("totalCount", totCnt);
    	param = pageNavigationUtil.createNavigationInfo(model, param);
        List resultList = authorMgtService.selectPageListAuthor(param);
        /* ### Pasing 끝 ### */
        
        model.addAttribute("totalCount", totCnt);
        model.addAttribute("resultList", resultList);
        model.addAttribute("param", param);
        
        return "admin/author/selectPageListAuthorMgt";
    } 
    

    /**
     * <PRE>
     * 1. MethodName : insertFormAuthorMgt
     * 2. ClassName  : AuthorMgtController
     * 3. Comment   : 권한 추가폼
     * 4. 작성자    : SooHyun.Seo
     * 5. 작성일    : 2017.12.22. 오후 3:44:58
     * </PRE>
     *   @return String
     *   @param request
     *   @param response
     *   @param model
     *   @return
     *   @throws Exception
     */
    @RequestMapping(value="/admin/author/insertFormAuthorMgt.do")
    public String insertFormAuthorMgt(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	
        return "admin/author/insertFormAuthorMgt";
    }
    

    /**
     * <PRE>
     * 1. MethodName : insertAuthorMgt
     * 2. ClassName  : AuthorMgtController
     * 3. Comment   : 권한 추가
     * 4. 작성자    : SooHyun.Seo
     * 5. 작성일    : 2017.12.22. 오후 3:45:14
     * </PRE>
     *   @return String
     *   @param request
     *   @param response
     *   @param model
     *   @return
     *   @throws Exception
     */
    @RequestMapping(value="/admin/author/insertAuthorMgt.do")
    public String insertAuthorMgt(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	
    	DataMap param = RequestUtil.getDataMap(request);
    	UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
    	param.put("ss_user_no", userInfoVo.getUserNo());
    	authorMgtService.insertAuthor(param);
        model.addAttribute("param", param);
        MessageUtil.setMessage(request, egovMessageSource.getMessage("succ.data.insert"));
        return "redirect:/admin/author/selectPageListAuthorMgt.do";
    }
    

    /**
     * <PRE>
     * 1. MethodName : updateFormAuthorMgt
     * 2. ClassName  : AuthorMgtController
     * 3. Comment   : 권한 수정폼
     * 4. 작성자    : SooHyun.Seo
     * 5. 작성일    : 2017.12.22. 오후 3:45:31
     * </PRE>
     *   @return String
     *   @param request
     *   @param response
     *   @param model
     *   @return
     *   @throws Exception
     */
    @RequestMapping(value="/admin/author/updateFormAuthorMgt.do")
    public String updateFormAuthorMgt(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	
    	DataMap param = RequestUtil.getDataMap(request);
    	DataMap resultMap = authorMgtService.selectAuthor(param);
    	
    	model.addAttribute("resultMap", resultMap);
        model.addAttribute("param", param);
        
        return "admin/author/updateFormAuthorMgt";
    }
    

    /**
     * <PRE>
     * 1. MethodName : updateAuthorMgt
     * 2. ClassName  : AuthorMgtController
     * 3. Comment   : 권한 수정
     * 4. 작성자    : SooHyun.Seo
     * 5. 작성일    : 2017.12.22. 오후 3:45:38
     * </PRE>
     *   @return String
     *   @param request
     *   @param response
     *   @param model
     *   @return
     *   @throws Exception
     */
    @RequestMapping(value="/admin/author/updateAuthorMgt.do")
    public String updateAuthorMgt(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	
    	DataMap param = RequestUtil.getDataMap(request);
    	UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
    	param.put("ss_user_no", userInfoVo.getUserNo());
    	
    	authorMgtService.updateAuthor(param);
        model.addAttribute("param", param);
        MessageUtil.setMessage(request, egovMessageSource.getMessage("succ.data.update"));

        param.put("redirectUrl", "/admin/author/updateFormAuthorMgt.do");
        return "common/redirect";
    }
    

    /**
     * <PRE>
     * 1. MethodName : deleteAuthorMgt
     * 2. ClassName  : AuthorMgtController
     * 3. Comment   : 권한 삭제
     * 4. 작성자    : SooHyun.Seo
     * 5. 작성일    : 2017.12.22. 오후 3:45:47
     * </PRE>
     *   @return String
     *   @param request
     *   @param response
     *   @param model
     *   @return
     *   @throws Exception
     */
    @RequestMapping(value="/admin/author/deleteAuthorMgt.do")
    public String deleteAuthorMgt(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	
    	DataMap param = RequestUtil.getDataMap(request);
    	authorMgtService.deleteAuthor(param);
        model.addAttribute("param", param);
        MessageUtil.setMessage(request, egovMessageSource.getMessage("succ.data.delete"));
        return "redirect:/admin/author/selectPageListAuthorMgt.do";
    }
    

    /**
     * <PRE>
     * 1. MethodName : selectExistYnAuthorMgtAjax
     * 2. ClassName  : AuthorMgtController
     * 3. Comment   : 권한 중복 체크
     * 4. 작성자    : SooHyun.Seo
     * 5. 작성일    : 2017.12.22. 오후 3:45:54
     * </PRE>
     *   @return void
     *   @param request
     *   @param response
     *   @param model
     *   @throws Exception
     */
    @RequestMapping(value="/admin/author/selectExistYnAuthorMgtAjax.do")
	public @ResponseBody void selectExistYnAuthorMgtAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_id", userInfoVo.getId());
		
		String existYn = authorMgtService.selectExistYnAuthor(param);
		model.addAttribute("param", param);
		
		JSONObject resultJSON = new JSONObject();
		DataMap resultMsg = new DataMap();
		resultMsg.put("existYn", existYn);
		
		//return 상태
		DataMap resultStats = new DataMap();
		resultStats.put("resultCode", "success");
		resultStats.put("resultMsg", resultMsg);
		resultJSON.put("resultStats", resultStats);
				
		response.setContentType("text/plain; charset=utf-8");
		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
	}

}