package com.whomade.kycarrots.mgt.survey.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.admin.user.service.UserMgtService;
import com.whomade.kycarrots.common.service.CommonCodeService;
import com.whomade.kycarrots.framework.common.constant.Const;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.page.util.pageNavigationUtil;
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.SessionUtil;
import com.whomade.kycarrots.framework.common.util.StringUtil;
import com.whomade.kycarrots.mgt.sclas.service.SclasService;
import com.whomade.kycarrots.mgt.survey.service.SurveyService;
import com.whomade.kycarrots.mgt.survey.service.SurveyUserService;
import com.whomade.kycarrots.mgt.survey.vo.TbQuestion;
import com.whomade.kycarrots.mgt.survey.vo.TbQuestionUser;
import com.whomade.kycarrots.mgt.survey.vo.TbSatisfaction;
import com.whomade.kycarrots.mgt.survey.vo.TbSatisfactionItem;
import com.whomade.kycarrots.mgt.survey.vo.TbSurveyUserDegree;
import com.whomade.kycarrots.mgt.survey.vo.TbSurveyUserEntryResult;
import com.whomade.kycarrots.mgt.survey.vo.TbSectionUser;
import com.whomade.kycarrots.mgt.survey.vo.TbStatistics;
import com.whomade.kycarrots.mgt.survey.vo.TbSurvey;
import com.whomade.kycarrots.mgt.survey.vo.TbSurveyUser;
import com.whomade.kycarrots.mgt.survey.vo.TbUserGroup;

@Controller
public class SurveyUserController {

	private static Log log = LogFactory.getLog(SurveyController.class);

	@Resource(name = "egovMessageSource")
	private EgovMessageSource egovMessageSource;

	@Resource(name = "surveyUserService")
	private SurveyUserService surveyUserService;

	@Resource(name = "surveyService")
	private SurveyService surveyService;
	
	/** CommonCodeService */
	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;

	@Resource(name = "userMgtService")
	private UserMgtService userMgtService;
	
	@Resource(name = "sclasService")
	private SclasService sclasService;
	
	
	/**
	 * <PRE>
	 * 1. MethodName 	: userGroup
	 * 2. ClassName  	: SurveyUserController
	 * 3. Comment   	: 강사리스트
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2017. 3. 13. 오후 4:08:28
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/survey/userGroup.do")
	public String userGroup(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		DataMap codeParam= new DataMap();
		
		/* ### Pasing 시작 ### */
		param.put("sch_user_sttus_code", "10");	//활동인 사용자만 조회
		int totCnt = userMgtService.selectTotCntUser(param);
		param.put("totalCount", totCnt);
		param = pageNavigationUtil.createNavigationInfo(model, param);
		List resultList = userMgtService.selectPageListUser(param);
		/* ### Pasing 끝 ### */
		
		// 사용자 구분 코드 조회
		codeParam.put("group_id", Const.upCodeUserSe);
		List userSeComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("userSeComboStr", userSeComboStr);
		
		// 사용자 구분 코드 조회
		codeParam.put("group_id", Const.upCodeUserSttus);
		List userSttusComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("userSttusComboStr", userSttusComboStr);
		
		model.addAttribute("totalCount", totCnt);
		model.addAttribute("resultList", resultList);
		model.addAttribute("param", param);
		
		return "/mgt/survey/userGroup";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: userGroupMgt
	 * 2. ClassName  	: SclasAreaController
	 * 3. Comment   	: 강사그룹등록
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2018. 01. 26. 오후 1:03:07
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/survey/userGroupMgt.do")
	public String userGroupMgt(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		DataMap param = RequestUtil.getDataMap(request);
		param.put("USER_NO", param.getString("user_no"));
		
		// 사용자명
		DataMap resultMap = surveyUserService.selectUser(param);
		// 그룹리스트
		List<DataMap> resultList = surveyUserService.selectListUserGroup(param);
		
		model.addAttribute("resultList", resultList);
		model.addAttribute("resultMap", resultMap);
		model.addAttribute("param", param);
		
		return "mgt/survey/userGroupMgt";
	}
	

	/**
	 * <PRE>
	 * 1. MethodName 	: updateUserGroup
	 * 2. ClassName  	: SurveyUserController
	 * 3. Comment   	: 강사그룹저장
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2018. 01. 26. 오후 1:03:07
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */

	@RequestMapping(value = "/mgt/survey/updateUserGroup.do")
	public @ResponseBody void updateUserGroup(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		JSONObject resultJSON = new JSONObject();

		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		
		TbUserGroup aTbUserGroup=null;
		 aTbUserGroup=surveyUserService.selectUserGroup(param);

		String resultMsg="";
		String resultCode="ok";
		
		
		if(aTbUserGroup!=null){
			aTbUserGroup.setUSER_NO(aTbUserGroup.getUSER_NO());
			aTbUserGroup.setSURVEY_GROUP_ID(aTbUserGroup.getSURVEY_GROUP_ID());
			aTbUserGroup.setSURVEY_GROUP_NM(param.getString("SURVEY_GROUP_NM"));
			aTbUserGroup.setSORT_ORDR(param.getInt("SORT_ORDR"));
			aTbUserGroup.setUPDATE_USER_NO(Integer.parseInt( userInfoVo.getUserNo()));
			surveyUserService.updateUserGroup(aTbUserGroup);
		}else{
		    aTbUserGroup=new TbUserGroup();
			aTbUserGroup.setUSER_NO(param.getInt("USER_NO"));
			aTbUserGroup.setSURVEY_GROUP_NM(param.getString("SURVEY_GROUP_NM"));
			aTbUserGroup.setSORT_ORDR(param.getInt("SORT_ORDR"));
			aTbUserGroup.setINSERT_USER_NO(Integer.parseInt( userInfoVo.getUserNo()));
			aTbUserGroup.setUPDATE_USER_NO(Integer.parseInt( userInfoVo.getUserNo()));
			surveyUserService.insertUserGroup(aTbUserGroup);
		}
		
		List<DataMap> resultList =surveyUserService.selectListUserGroup(param);
		
		//return 상태
		DataMap resultStats = new DataMap();
		resultStats.put("resultCode", resultCode);
		resultStats.put("resultMsg", resultMsg);
		resultStats.put("resultList", resultList);
		resultJSON.put("resultStats", resultStats);

		response.setContentType("text/html; charset=utf-8");
		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
	}
	/**
	 * <PRE>
	 * 1. MethodName 	: deleteUserGroup
	 * 2. ClassName  	: SurveyUserController
	 * 3. Comment   	: 강사그룹삭제
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2018. 01. 26. 오후 1:03:07
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */

	@RequestMapping(value = "/mgt/survey/deleteUserGroup.do")
	public @ResponseBody void deleteUserGroup(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		JSONObject resultJSON = new JSONObject();

		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("USER_NO", userInfoVo.getUserNo());
		
		TbUserGroup aTbUserGroup=new TbUserGroup();

		String resultMsg="";
		String resultCode="ok";
		
		
		aTbUserGroup.setUSER_NO(aTbUserGroup.getUSER_NO());
		aTbUserGroup.setSURVEY_GROUP_ID(aTbUserGroup.getSURVEY_GROUP_ID());
		surveyUserService.deleteUserGroup(aTbUserGroup);
		
		List<DataMap> resultList =surveyUserService.selectListUserGroup(param);
		
		//return 상태
		DataMap resultStats = new DataMap();
		resultStats.put("resultCode", resultCode);
		resultStats.put("resultMsg", resultMsg);
		resultStats.put("resultList", resultList);
		resultJSON.put("resultStats", resultStats);

		response.setContentType("text/html; charset=utf-8");
		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
	}
	
	
	/**
	 * <PRE>
	 * 1. MethodName 	: userGroupSurvey
	 * 2. ClassName  	: SurveyUserController
	 * 3. Comment   	: 강사그룹리스트
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2017. 3. 13. 오후 4:08:28
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/survey/userGroupSurvey.do")
	public String userGroupSurvey(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		DataMap codeParam= new DataMap();
		
		List resultList = surveyUserService.selectListServeyGroup(param);
		
		// 사용자 구분 코드 조회
		codeParam.put("group_id", Const.upCodeUserSe);
		List userSeComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("userSeComboStr", userSeComboStr);
		
		// 사용자 구분 코드 조회
		codeParam.put("group_id", Const.upCodeUserSttus);
		List userSttusComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("userSttusComboStr", userSttusComboStr);
		
		model.addAttribute("resultList", resultList);
		model.addAttribute("param", param);
		
		return "/mgt/survey/userGroupSurvey";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: userGroupSurveyMgt
	 * 2. ClassName  	: SurveyUserController
	 * 3. Comment   	: 강사그룹등록
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2018. 01. 26. 오후 1:03:07
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/survey/userGroupSurveyMgt.do")
	public String userGroupSurveyMgt(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		
		// 사용자 구분 코드 조회
		DataMap codeParam= new DataMap();
		codeParam.put("group_id", "R010302");
		List userSeComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("userHseComboStr", userSeComboStr);

		
		DataMap param = RequestUtil.getDataMap(request);
		param.put("USER_NO", param.getString("user_no"));
		param.put("SURVEY_GROUP_ID", param.getString("survey_group_id"));
		
		if(param.getString("survey_year").equals("")){
			TbSurveyUserDegree aTbSurveyUserDegree=null;
			aTbSurveyUserDegree=surveyUserService.selectListUserGroupMaxDegreeSurvey(param);
			
			if(aTbSurveyUserDegree != null) {
				param.put("SURVEY_YEAR", aTbSurveyUserDegree.getSURVEY_YEAR());
				param.put("SURVEY_DEGREE",aTbSurveyUserDegree.getSURVEY_DEGREE());
			}else {
				param.put("SURVEY_YEAR", "");
				param.put("SURVEY_DEGREE","");
			}

		}else{
			param.put("SURVEY_YEAR", param.getString("survey_year"));
			param.put("SURVEY_DEGREE", param.getString("survey_degree"));
		}
		
		
		
		
		// 그룹명
		TbUserGroup resultMap = surveyUserService.selectUserGroup(param);
		// 진단지조사년도차수리스트
		List<DataMap> resultDegreeList = surveyUserService.selectListUserGroupDegreeSurvey(param);
		
		// 진단지리스트
		List<DataMap> resultList = surveyUserService.selectListUserGroupSurvey(param);
		
		model.addAttribute("resultList", resultList);
		model.addAttribute("resultDegreeList", resultDegreeList);
		model.addAttribute("resultMap", resultMap);
		model.addAttribute("param", param);
		
		return "mgt/survey/userGroupSurveyMgt";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateUserGroupDegreeSurvey
	 * 2. ClassName  	: SurveyUserController
	 * 3. Comment   	: 강사그룹년도차수 등록
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2018. 01. 26. 오후 1:03:07
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/survey/updateUserGroupDegreeSurvey.do")
	public String updateUserGroupDegreeSurvey(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);

		DataMap param = RequestUtil.getDataMap(request);
		
		TbSurveyUserDegree aTbSurveyUserDegree=new TbSurveyUserDegree();
		aTbSurveyUserDegree.setUSER_NO(param.getInt("USER_NO"));
		aTbSurveyUserDegree.setSURVEY_GROUP_ID(param.getInt("SURVEY_GROUP_ID"));
		aTbSurveyUserDegree.setSURVEY_YEAR(param.getString("SURVEY_YEAR"));
		aTbSurveyUserDegree.setSURVEY_DEGREE(param.getString("SURVEY_DEGREE"));
		aTbSurveyUserDegree.setINSERT_USER_NO(Integer.parseInt( userInfoVo.getUserNo()));
		aTbSurveyUserDegree.setUPDATE_USER_NO(Integer.parseInt( userInfoVo.getUserNo()));
		surveyUserService.insertUserGroupDegreeSurvey(aTbSurveyUserDegree);
		
		return "redirect:/mgt/survey/userGroupSurveyMgt.do?user_no="+param.getString("USER_NO")+"&survey_group_id="+param.getString("SURVEY_GROUP_ID")+"&survey_year="+param.getString("SURVEY_YEAR")+"&survey_degree="+param.getString("SURVEY_DEGREE");	
	}
	
	/**
	 * <PRE>
	 * 1. MethodName	: deleteUserGroupDegreeSurvey
	 * 2. ClassName  	: SurveyUserController
	 * 3. Comment   	: 강사그룹년도차수 삭제
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2018. 01. 26. 오후 1:03:07
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/survey/deleteUserGroupDegreeSurvey.do")
	public String deleteUserGroupDegreeSurvey(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);

		DataMap param = RequestUtil.getDataMap(request);
		
		TbSurveyUserDegree aTbSurveyUserDegree=new TbSurveyUserDegree();
		aTbSurveyUserDegree.setUSER_NO(param.getInt("USER_NO"));
		aTbSurveyUserDegree.setSURVEY_GROUP_ID(param.getInt("SURVEY_GROUP_ID"));
		aTbSurveyUserDegree.setSURVEY_YEAR(param.getString("SURVEY_YEAR"));
		aTbSurveyUserDegree.setSURVEY_DEGREE(param.getString("SURVEY_DEGREE"));
		surveyUserService.deleteUserGroupDegreeSurvey(aTbSurveyUserDegree);
		
		return "redirect:/mgt/survey/userGroupSurveyMgt.do?user_no="+param.getString("USER_NO")+"&survey_group_id="+param.getString("SURVEY_GROUP_ID")+"&survey_year="+param.getString("SURVEY_YEAR")+"&survey_degree="+param.getString("SURVEY_DEGREE");	
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: insertUserEntryResult
	 * 2. ClassName  	: SurveyUserController
	 * 3. Comment   	: 강사그룹년도차수 데이타생성
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2018. 01. 26. 오후 1:03:07
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/survey/insertUserEntryResult.do")
	public @ResponseBody void insertUserEntryResult(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		DataMap param = RequestUtil.getDataMap(request);
		JSONObject resultJSON = new JSONObject();
		String resultMsg="";
		String resultCode="ok";
		
		System.out.println("1SURVEY_YEAR["+param.getString("survey_year")+"]SURVEY_DEGREE["+param.getString("survey_degree")+"]");
		
		// 진단지리스트
		DataMap degreeParam = new DataMap();
		degreeParam.put("USER_NO", param.getString("user_no"));
		degreeParam.put("SURVEY_GROUP_ID", param.getString("survey_group_id"));
		degreeParam.put("SURVEY_YEAR", param.getString("survey_year"));
		degreeParam.put("SURVEY_DEGREE", param.getString("survey_degree"));
		degreeParam.put("INSERT_USER_NO", userInfoVo.getUserNo());
		degreeParam.put("UPDATE_USER_NO",userInfoVo.getUserNo());
		List<DataMap> degreeList = surveyUserService.selectListUserGroupSurvey(degreeParam);
		
		for (DataMap adegree :degreeList){
			//TB_SURVEY_USER_ENTRY_RESULT 저장
			TbQuestionUser aTbQuestionUserResult= new TbQuestionUser();
			aTbQuestionUserResult.setUSER_NO(adegree.getInt("USER_NO"));
			aTbQuestionUserResult.setSURVEY_GROUP_ID(adegree.getInt("SURVEY_GROUP_ID"));
			aTbQuestionUserResult.setSURVEY_ID(adegree.getString("SURVEY_ID"));
			aTbQuestionUserResult.setSURVEY_YEAR(adegree.getString("SURVEY_YEAR"));
			aTbQuestionUserResult.setSURVEY_DEGREE(adegree.getString("SURVEY_DEGREE"));
			surveyUserService.deleteUserEntryResult(aTbQuestionUserResult);
			
			List<DataMap> SurveyLableCnt=surveyUserService.selectListResultsLabelCnt(aTbQuestionUserResult);
			List<TbSurveyUserEntryResult> tbSurveyUserEntryResults = new ArrayList<TbSurveyUserEntryResult>();
			tbSurveyUserEntryResults=StringUtil.questionUsers(SurveyLableCnt);
			
			//System.out.println("tbSurveyUserEntryResults["+tbSurveyUserEntryResults+"]");
			for (TbSurveyUserEntryResult aTbSurveyUserEntryResult:tbSurveyUserEntryResults){
				aTbSurveyUserEntryResult.setINSERT_USER_NO(userInfoVo.getUserNo());
				aTbSurveyUserEntryResult.setUPDATE_USER_NO(userInfoVo.getUserNo());
				System.out.println("aTbSurveyUserEntryResult SECTION_ID["+aTbSurveyUserEntryResult.getSECTION_ID()+"]QUESTION_ID["+aTbSurveyUserEntryResult.getQUESTION_ID()+"]"+aTbSurveyUserEntryResult.getGRADE_01_CNT()+"/"+aTbSurveyUserEntryResult.getGRADE_02_CNT()+"/"+aTbSurveyUserEntryResult.getGRADE_03_CNT()+"/"+aTbSurveyUserEntryResult.getGRADE_04_CNT()+"]");
				surveyUserService.insertUserEntryResult(aTbSurveyUserEntryResult);
			}
			System.out.println("SURVEY_YEAR["+adegree.getString("SURVEY_YEAR")+"]SURVEY_DEGREE["+adegree.getString("SURVEY_DEGREE")+"]");
			
			//나이저장
			DataMap aLableAgeCnt=surveyUserService.selectListResultsAgeCnt(aTbQuestionUserResult);
			TbSurveyUserEntryResult ageTbSurveyUserEntryResult = new TbSurveyUserEntryResult();
			ageTbSurveyUserEntryResult=StringUtil.questionUsersAge(aLableAgeCnt);
			ageTbSurveyUserEntryResult.setUSER_NO(adegree.getInt("USER_NO"));
			ageTbSurveyUserEntryResult.setSURVEY_GROUP_ID(""+adegree.getInt("SURVEY_GROUP_ID"));
			ageTbSurveyUserEntryResult.setSURVEY_ID(adegree.getString("SURVEY_ID"));
			ageTbSurveyUserEntryResult.setSURVEY_YEAR(adegree.getString("SURVEY_YEAR"));
			ageTbSurveyUserEntryResult.setSURVEY_DEGREE(adegree.getString("SURVEY_DEGREE"));
			ageTbSurveyUserEntryResult.setINSERT_USER_NO(userInfoVo.getUserNo());
			ageTbSurveyUserEntryResult.setUPDATE_USER_NO(userInfoVo.getUserNo());
			System.out.println("selectListResultsAgeCnt");
			surveyUserService.insertUserEntryResult(ageTbSurveyUserEntryResult);
		}

		//진료과목저장
		surveyUserService.deleteUserEntryResultE(degreeParam);
		surveyUserService.insertUserEntryResultE(degreeParam);
		
		//return 상태
		DataMap resultStats = new DataMap();
		resultStats.put("resultCode", resultCode);
		resultStats.put("resultMsg", resultMsg);
		resultJSON.put("resultStats", resultStats);

		System.out.println("resultJSON["+resultJSON.toString()+"]");

		response.setContentType("text/html; charset=utf-8");
		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			System.out.println("IOException["+e+"]");
			log.error(e);
		}
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateUserGroupSurvey
	 * 2. ClassName  	: SurveyUserController
	 * 3. Comment   	: 진단지저장
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2018. 01. 26. 오후 1:03:07
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */

	@RequestMapping(value = "/mgt/survey/updateUserGroupSurvey.do")
	public @ResponseBody void updateUserGroupSurvey(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		JSONObject resultJSON = new JSONObject();

		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		
		TbSurveyUser aTbSurveyUser=null;
		aTbSurveyUser=surveyUserService.selectUserGroupSurvey(param);

		String resultMsg="";
		String resultCode="ok";
		
		
		if(aTbSurveyUser!=null){
			aTbSurveyUser.setUSER_NO(aTbSurveyUser.getUSER_NO());
			aTbSurveyUser.setSURVEY_GROUP_ID(aTbSurveyUser.getSURVEY_GROUP_ID());
			aTbSurveyUser.setSURVEY_ID(param.getString("SURVEY_ID"));
			aTbSurveyUser.setSORT_ORDR(param.getInt("SORT_ORDR"));
			aTbSurveyUser.setSURVEY_YEAR(param.getString("SURVEY_YEAR"));
			aTbSurveyUser.setSURVEY_DEGREE(param.getString("SURVEY_DEGREE"));
			aTbSurveyUser.setUPDATE_USER_NO(Integer.parseInt( userInfoVo.getUserNo()));
			surveyUserService.updateUserGroupSurvey(aTbSurveyUser);
		}else{
			aTbSurveyUser=new TbSurveyUser();
			aTbSurveyUser.setUSER_NO(param.getInt("USER_NO"));
			aTbSurveyUser.setSURVEY_GROUP_ID(param.getInt("SURVEY_GROUP_ID"));
			aTbSurveyUser.setSURVEY_ID(param.getString("SURVEY_ID"));
			aTbSurveyUser.setSORT_ORDR(param.getInt("SORT_ORDR"));
			aTbSurveyUser.setSURVEY_YEAR(param.getString("SURVEY_YEAR"));
			aTbSurveyUser.setSURVEY_DEGREE(param.getString("SURVEY_DEGREE"));
			aTbSurveyUser.setINSERT_USER_NO(Integer.parseInt( userInfoVo.getUserNo()));
			aTbSurveyUser.setUPDATE_USER_NO(Integer.parseInt( userInfoVo.getUserNo()));
			surveyUserService.insertUserGroupSurvey(aTbSurveyUser);
		}
		
		List<DataMap> resultList =surveyUserService.selectListUserGroupSurvey(param);
		
		//return 상태
		DataMap resultStats = new DataMap();
		resultStats.put("resultCode", resultCode);
		resultStats.put("resultMsg", resultMsg);
		resultStats.put("resultList", resultList);
		resultJSON.put("resultStats", resultStats);

		response.setContentType("text/html; charset=utf-8");
		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: deleteUserGroupSurvey
	 * 2. ClassName  	: SurveyUserController
	 * 3. Comment   	: 진단지저장
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2018. 01. 26. 오후 1:03:07
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */

	@RequestMapping(value = "/mgt/survey/deleteUserGroupSurvey.do")
	public @ResponseBody void deleteUserGroupSurvey(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		JSONObject resultJSON = new JSONObject();

		String resultMsg="";
		String resultCode="ok";
		
		
		TbSurveyUser aTbSurveyUser=new TbSurveyUser();
		aTbSurveyUser.setUSER_NO(param.getInt("USER_NO"));
		aTbSurveyUser.setSURVEY_ID(param.getString("SURVEY_ID"));
		aTbSurveyUser.setSURVEY_GROUP_ID(param.getInt("SURVEY_GROUP_ID"));

		surveyUserService.deleteUserGroupSurvey(aTbSurveyUser);
			
		List<DataMap> resultList =surveyUserService.selectListUserGroupSurvey(param);
		
		//return 상태
		DataMap resultStats = new DataMap();
		resultStats.put("resultCode", resultCode);
		resultStats.put("resultMsg", resultMsg);
		resultStats.put("resultList", resultList);
		resultJSON.put("resultStats", resultStats);

		response.setContentType("text/html; charset=utf-8");
		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: statisticsEntry
	 * 2. ClassName  	: SurveyUserController
	 * 3. Comment   	: 설문항목추가
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2019. 05. 11 15:59
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/survey/statisticsEntry.do")
	public String statisticsEntry(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);

	    // 진단지리스트
		DataMap degreeParam = new DataMap();
		degreeParam.put("USER_NO", param.getString("userNo"));
		degreeParam.put("SURVEY_GROUP_ID", param.getString("surveyGroupId"));
		degreeParam.put("SURVEY_YEAR", param.getString("surveyYear"));
		degreeParam.put("SURVEY_DEGREE", param.getString("surveyDegree"));
		List<DataMap> SurveyCdS = surveyUserService.selectListUserGroupSurvey(degreeParam);

		TbUserGroup aTbUserGroup=null;
		 aTbUserGroup=surveyUserService.selectUserGroup(degreeParam);
		 if(aTbUserGroup !=null) 	 param.put("SURVEY_GROUP_NM", aTbUserGroup.getSURVEY_GROUP_NM());
		
		//성별
		String SurveyId10="";
		String SurveyId20="";
		String SurveyId30="";
		int SurveyId10EntryCnt=0;
		int SurveyId20EntryCnt=0;
		int SurveyId30EntryCnt=0;
		
		List<DataMap> SummaryCd =null;
		DataMap SummaryParam =new DataMap();
		
		TbStatistics aTbStatistics10 =null;
		TbStatistics aTbStatistics20 =null;
		TbStatistics aTbStatistics30 =null;
		
		for(DataMap aSurvey:SurveyCdS){
			if(aSurvey.getInt("SORT_ORDR")==10){
				SurveyId10=aSurvey.getString("SURVEY_ID");
				SurveyId10EntryCnt=aSurvey.getInt("NUMENTRIES");
				SummaryParam.put("SURVEY_ID", SurveyId10);
				SummaryCd=surveyUserService.selectListQuestionSummaryCd(SummaryParam);
				
				aTbStatistics10 = new TbStatistics(SummaryCd,SurveyId10);

			}else if(aSurvey.getInt("SORT_ORDR")==20){
				SurveyId20=aSurvey.getString("SURVEY_ID");
				SurveyId20EntryCnt=aSurvey.getInt("NUMENTRIES");
				SummaryParam.put("SURVEY_ID", SurveyId20);
				SummaryCd=surveyUserService.selectListQuestionSummaryCd(SummaryParam);

				aTbStatistics20 = new TbStatistics(SummaryCd,SurveyId20);

				
			}else if(aSurvey.getInt("SORT_ORDR")==30){
				SurveyId30=aSurvey.getString("SURVEY_ID");
				SurveyId30EntryCnt=aSurvey.getInt("NUMENTRIES");
				SummaryParam.put("SURVEY_ID", SurveyId30);
				SummaryCd=surveyUserService.selectListQuestionSummaryCd(SummaryParam);

				aTbStatistics30 = new TbStatistics(SummaryCd,SurveyId30);

			}
		}


		
		//성별챠트
		DataMap graphparam = new DataMap();
		DataMap statInData= null;
		DataMap statData=null;
		String statArray ="";
	
		statInData= new DataMap();
		statInData.put("USER_NO", param.getString("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getString("surveyGroupId"));
		statInData.put("SURVEY_YEAR", param.getString("surveyYear"));
		statInData.put("SURVEY_DEGREE", param.getString("surveyDegree"));
		statInData.put("SORT_ORDR", "10");
		statInData.put("SUMMARY_CD", "1");
		statData=surveyUserService.selectMaleCnt(statInData);
		statArray = StringUtil.jsonStatStr(statData,1,10);
		graphparam.put("sex1Array", statArray);

		statInData= new DataMap();
		statInData.put("USER_NO", param.getString("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getString("surveyGroupId"));
		statInData.put("SURVEY_YEAR", param.getString("surveyYear"));
		statInData.put("SURVEY_DEGREE", param.getString("surveyDegree"));
		statInData.put("SORT_ORDR", "20");
		statInData.put("SUMMARY_CD", "1");
		statData=surveyUserService.selectMaleCnt(statInData);
		statArray= StringUtil.jsonStatStr(statData,1,20);
		graphparam.put("sex2Array", statArray);
		
		statInData= new DataMap();
		statInData.put("USER_NO", param.getString("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getString("surveyGroupId"));
		statInData.put("SURVEY_YEAR", param.getString("surveyYear"));
		statInData.put("SURVEY_DEGREE", param.getString("surveyDegree"));
		statInData.put("SORT_ORDR", "30");
		statInData.put("SUMMARY_CD", "1");
		statData=surveyUserService.selectMaleCnt(statInData);
		statArray= StringUtil.jsonStatStr(statData,1,30);
		graphparam.put("sex3Array", statArray);
		
		//연령
		statInData= new DataMap();
		statInData.put("USER_NO", param.getString("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getString("surveyGroupId"));
		statInData.put("SURVEY_YEAR", param.getString("surveyYear"));
		statInData.put("SURVEY_DEGREE", param.getString("surveyDegree"));
		statInData.put("SORT_ORDR", "10");
		statInData.put("SUMMARY_CD", "2");
		statData=surveyUserService.selectAgeCnt(statInData);
		statArray = StringUtil.jsonStatStr(statData,2,10);
		graphparam.put("age1Array", statArray);

		statInData= new DataMap();
		statInData.put("USER_NO", param.getString("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getString("surveyGroupId"));
		statInData.put("SURVEY_YEAR", param.getString("surveyYear"));
		statInData.put("SURVEY_DEGREE", param.getString("surveyDegree"));
		statInData.put("SORT_ORDR", "20");
		statInData.put("SUMMARY_CD", "2");
		statData=surveyUserService.selectAgeCnt(statInData);
		statArray = StringUtil.jsonStatStr(statData,2,20);
		graphparam.put("age2Array", statArray);

		statInData= new DataMap();
		statInData.put("USER_NO", param.getString("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getString("surveyGroupId"));
		statInData.put("SURVEY_YEAR", param.getString("surveyYear"));
		statInData.put("SURVEY_DEGREE", param.getString("surveyDegree"));
		statInData.put("SORT_ORDR", "30");
		statInData.put("SUMMARY_CD", "2");
		statData=surveyUserService.selectAgeCnt(statInData);
		statArray = StringUtil.jsonStatStr(statData,2,30);
		graphparam.put("age3Array", statArray);

		//학력
		statInData= new DataMap();
		statInData.put("USER_NO", param.getString("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getString("surveyGroupId"));
		statInData.put("SURVEY_YEAR", param.getString("surveyYear"));
		statInData.put("SURVEY_DEGREE", param.getString("surveyDegree"));
		statInData.put("SORT_ORDR", "10");
		statInData.put("SUMMARY_CD", "3");
		statData=surveyUserService.selectSchoolCnt(statInData);
		statArray = StringUtil.jsonStatStr(statData,3,10);
		graphparam.put("school1Array", statArray);

		statInData= new DataMap();
		statInData.put("USER_NO", param.getString("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getString("surveyGroupId"));
		statInData.put("SURVEY_YEAR", param.getString("surveyYear"));
		statInData.put("SURVEY_DEGREE", param.getString("surveyDegree"));
		statInData.put("SORT_ORDR", "10");
		statInData.put("SUMMARY_CD", "3");
		statData=surveyUserService.selectSchoolCnt(statInData);
		statArray = StringUtil.jsonStatStr(statData,3,20);
		graphparam.put("school2Array", statArray);

		/*
		statInData= new DataMap();
		statInData.put("SURVEY_ID", aTbStatistics30.getSURVEY_ID());
		statInData.put("SECTION_ID", aTbStatistics30.getSECTION_ID_3());
		statInData.put("QUESTION_ID", aTbStatistics30.getQUESTION_ID_3());
		statData=surveyUserService.selectSchoolCnt(statInData);
		statArray = StringUtil.jsonStatStr(statData,3);
		graphparam.put("school3Array", statArray);
		*/
		//건강상태
		statInData= new DataMap();
		statInData.put("USER_NO", param.getString("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getString("surveyGroupId"));
		statInData.put("SURVEY_YEAR", param.getString("surveyYear"));
		statInData.put("SURVEY_DEGREE", param.getString("surveyDegree"));
		statInData.put("SORT_ORDR", "10");
		statInData.put("SUMMARY_CD", "4");
		statData=surveyUserService.selectHealthCnt(statInData);
		statArray = StringUtil.jsonStatStr(statData,4,10);
		graphparam.put("health1Array", statArray);

		statInData= new DataMap();
		statInData.put("USER_NO", param.getString("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getString("surveyGroupId"));
		statInData.put("SURVEY_YEAR", param.getString("surveyYear"));
		statInData.put("SURVEY_DEGREE", param.getString("surveyDegree"));
		statInData.put("SORT_ORDR", "20");
		statInData.put("SUMMARY_CD", "4");
		statData=surveyUserService.selectHealthCnt(statInData);
		statArray = StringUtil.jsonStatStr(statData,4,20);
		graphparam.put("health2Array", statArray);
		/*
		statInData= new DataMap();
		statInData.put("SURVEY_ID", aTbStatistics30.getSURVEY_ID());
		statInData.put("SECTION_ID", aTbStatistics30.getSECTION_ID_4());
		statInData.put("QUESTION_ID", aTbStatistics30.getQUESTION_ID_4());
		statData=surveyUserService.selectHealthCnt(statInData);
		statArray = StringUtil.jsonStatStr(statData,4);
		graphparam.put("health3Array", statArray);
		*/

		//입원경로
		statInData= new DataMap();
		statInData.put("USER_NO", param.getString("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getString("surveyGroupId"));
		statInData.put("SURVEY_YEAR", param.getString("surveyYear"));
		statInData.put("SURVEY_DEGREE", param.getString("surveyDegree"));
		statInData.put("SORT_ORDR", "20");
		statInData.put("SUMMARY_CD", "5");
		statData=surveyUserService.selectWayCnt(statInData);
		statArray = StringUtil.jsonStatStr(statData,5,20);
		graphparam.put("way2Array", statArray);
		

		//진료과목
		statInData= new DataMap();
		statInData.put("USER_NO", param.getString("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getString("surveyGroupId"));
		statInData.put("SURVEY_YEAR", param.getString("surveyYear"));
		statInData.put("SURVEY_DEGREE", param.getString("surveyDegree"));
		statInData.put("SURVEY_ID", aTbStatistics10.getSURVEY_ID());
		statInData.put("SORT_ORDR", "10");
		statInData.put("SUMMARY_CD", "6");
		List<DataMap> statDatas = new ArrayList <DataMap>();
		statDatas=surveyUserService.selectSubjectCnt(statInData);
		statArray = StringUtil.jsonStatStr(statDatas,6);
		graphparam.put("subject1Array", statArray);

		statInData= new DataMap();
		statInData.put("USER_NO", param.getString("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getString("surveyGroupId"));
		statInData.put("SURVEY_YEAR", param.getString("surveyYear"));
		statInData.put("SURVEY_DEGREE", param.getString("surveyDegree"));
		statInData.put("SURVEY_ID", aTbStatistics10.getSURVEY_ID());
		statInData.put("SORT_ORDR", "20");
		statInData.put("SUMMARY_CD", "6");
		statDatas = new ArrayList <DataMap>();
		statDatas=surveyUserService.selectSubjectCnt(statInData);
		statArray = StringUtil.jsonStatStr(statDatas,6);
		graphparam.put("subject2Array", statArray);
		
		//응급실유형
		statInData= new DataMap();
		statInData.put("USER_NO", param.getString("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getString("surveyGroupId"));
		statInData.put("SURVEY_YEAR", param.getString("surveyYear"));
		statInData.put("SURVEY_DEGREE", param.getString("surveyDegree"));
		statInData.put("SORT_ORDR", "30");
		statInData.put("SUMMARY_CD", "7");
		statData=surveyUserService.selectWayCnt(statInData);
		statArray = StringUtil.jsonStatStr(statData,7,30);
		graphparam.put("emer3Array", statArray);
		
		//응답자유형
		statInData= new DataMap();
		statInData.put("USER_NO", param.getString("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getString("surveyGroupId"));
		statInData.put("SURVEY_YEAR", param.getString("surveyYear"));
		statInData.put("SURVEY_DEGREE", param.getString("surveyDegree"));
		statInData.put("SORT_ORDR", "30");
		statInData.put("SUMMARY_CD", "8");
		statData=surveyUserService.selectWayCnt(statInData);
		statArray = StringUtil.jsonStatStr(statData,8,30);
		graphparam.put("reway3Array", statArray);

		
		graphparam.put("entryCnt1", SurveyId10EntryCnt);
		graphparam.put("entryCnt2", SurveyId20EntryCnt);
		graphparam.put("entryCnt3", SurveyId30EntryCnt);
		
		
		model.addAttribute("param", param);
		//model.addAttribute("survey", survey);
		//model.addAttribute("surveyUser", surveyUser);
		model.addAttribute("graphparam", graphparam);
		model.addAttribute("surveyCds", SurveyCdS);
		
		
		return "/mgt/survey/statisticsEntry";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: statisticsCd1Entry
	 * 2. ClassName  	: SurveyUserController
	 * 3. Comment   	: 설문항목추가
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2019. 05. 11 15:59
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/survey/statisticsCd1Entry.do")
	public String statisticsCd1(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
	    // 진단지리스트
		DataMap degreeParam = new DataMap();
		degreeParam.put("USER_NO", param.getString("userNo"));
		degreeParam.put("SURVEY_GROUP_ID", param.getString("surveyGroupId"));
		degreeParam.put("SURVEY_YEAR", param.getString("surveyYear"));
		degreeParam.put("SURVEY_DEGREE", param.getString("surveyDegree"));
		List<DataMap> SurveyCdS = surveyUserService.selectListUserGroupSurvey(degreeParam);

		TbUserGroup aTbUserGroup=null;
		 aTbUserGroup=surveyUserService.selectUserGroup(degreeParam);
		 if(aTbUserGroup !=null) 	 param.put("SURVEY_GROUP_NM", aTbUserGroup.getSURVEY_GROUP_NM());

		
		//SurveyID
		String SurveyId10="";
		String SurveyId20="";
		String SurveyId30="";
		String SurveyYear="";
		String SurveyDegree="";
  	
		int SurveyId10EntryCnt=0;
		int SurveyId20EntryCnt=0;
		int SurveyId30EntryCnt=0;
		
		List<DataMap> SummaryCd =null;
		DataMap SummaryParam =new DataMap();
		
		TbStatistics aTbStatistics10 =null;
		TbStatistics aTbStatistics20 =null;
		TbStatistics aTbStatistics30 =null;
		
		for(DataMap aSurvey:SurveyCdS){
			SurveyYear=aSurvey.getString("SURVEY_YEAR");
			SurveyDegree=aSurvey.getString("SURVEY_DEGREE");

			if(aSurvey.getInt("SORT_ORDR")==10){
				SurveyId10=aSurvey.getString("SURVEY_ID");
				SurveyId10EntryCnt=aSurvey.getInt("NUMENTRIES");
				
				SummaryParam.put("SURVEY_ID", SurveyId10);
				SummaryCd=surveyUserService.selectListQuestionSummaryCd(SummaryParam);
				
				aTbStatistics10 = new TbStatistics(SummaryCd,SurveyId10);

			}else if(aSurvey.getInt("SORT_ORDR")==20){
				SurveyId20=aSurvey.getString("SURVEY_ID");
				SurveyId20EntryCnt=aSurvey.getInt("NUMENTRIES");
				SummaryParam.put("SURVEY_ID", SurveyId20);
				SummaryCd=surveyUserService.selectListQuestionSummaryCd(SummaryParam);

				aTbStatistics20 = new TbStatistics(SummaryCd,SurveyId20);

				
			}else if(aSurvey.getInt("SORT_ORDR")==30){
				SurveyId30=aSurvey.getString("SURVEY_ID");
				SurveyId30EntryCnt=aSurvey.getInt("NUMENTRIES");
				SummaryParam.put("SURVEY_ID", SurveyId30);
				SummaryCd=surveyUserService.selectListQuestionSummaryCd(SummaryParam);

				aTbStatistics30 = new TbStatistics(SummaryCd,SurveyId30);

			}
		}
		
		//차원 
		DataMap graphparam = new DataMap();
		DataMap statInData= null;
		List<DataMap> statData10=new ArrayList<DataMap>();
		List<DataMap> statData20=null;
		List<DataMap> statData30=null;
		List<DataMap> statData40=null;
		String statArray ="";
		
		List<String> surveyIds = new ArrayList<String>();
		surveyIds.add(aTbStatistics10.getSURVEY_ID());
		surveyIds.add(aTbStatistics20.getSURVEY_ID());
		surveyIds.add(aTbStatistics30.getSURVEY_ID());

		List<String> surveyCds = null;
		
		//체감만족도
		surveyCds = new ArrayList<String>();
		surveyCds.add("10");
		surveyCds.add("11");
		
		statInData= new DataMap();
		statInData.put("USER_NO", param.getInt("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getInt("surveyGroupId"));
		statInData.put("SURVEY_YEAR", SurveyYear);
		statInData.put("SURVEY_DEGREE", SurveyDegree);
		statInData.put("SURVEY_IDS", surveyIds);
		statInData.put("SUMMARY_CDS", surveyCds);
		statData20=surveyUserService.selectDimensionAvg(statInData);
		statArray = StringUtil.jsonDimesionStr(statData20,1,aTbStatistics10.getSURVEY_ID(),aTbStatistics20.getSURVEY_ID(),aTbStatistics30.getSURVEY_ID());
		
		graphparam.put("summary2Array", statArray);

		//Nps
		surveyCds = new ArrayList<String>();
		surveyCds.add("11");
		
		statInData= new DataMap();
		statInData.put("USER_NO", param.getInt("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getInt("surveyGroupId"));
		statInData.put("SURVEY_YEAR", SurveyYear);
		statInData.put("SURVEY_DEGREE", SurveyDegree);
		statData30=surveyUserService.selectDimensionNps(statInData);
		statArray = StringUtil.jsonDimesionStr(statData30,1,aTbStatistics10.getSURVEY_ID(),aTbStatistics20.getSURVEY_ID(),aTbStatistics30.getSURVEY_ID());
		graphparam.put("summary3Array", statArray);

		DataMap aDataMap3h = new DataMap();
		DataMap aDataMap3l = new DataMap();
		List<DataMap> DataMapDim3=new ArrayList<DataMap>();
		for(DataMap aDataMap:statData30){
			 if(aTbStatistics10.getSURVEY_ID().equals(aDataMap.getString("SURVEY_ID"))){
				 aDataMap3h.put("Survey10", aDataMap.getString("AVG_QUESTION_VALH"));
				 aDataMap3l.put("Survey10", aDataMap.getString("AVG_QUESTION_VALL"));
			 }else if(aTbStatistics20.getSURVEY_ID().equals(aDataMap.getString("SURVEY_ID"))){
				 aDataMap3h.put("Survey20", aDataMap.getString("AVG_QUESTION_VALH"));
				 aDataMap3l.put("Survey20", aDataMap.getString("AVG_QUESTION_VALL"));
			 }else if(aTbStatistics30.getSURVEY_ID().equals(aDataMap.getString("SURVEY_ID"))){
				 aDataMap3h.put("Survey30", aDataMap.getString("AVG_QUESTION_VALH"));
				 aDataMap3l.put("Survey30", aDataMap.getString("AVG_QUESTION_VALL"));
			 }else{
				 aDataMap3h.put("Survey99", aDataMap.getString("AVG_QUESTION_VALH"));
				 aDataMap3l.put("Survey99", aDataMap.getString("AVG_QUESTION_VALL"));
			 }
		}
		DataMapDim3.add(aDataMap3h);
		DataMapDim3.add(aDataMap3l);
		
		
		//총차원만족도
		surveyCds = new ArrayList<String>();
		surveyCds.add("9");
		
		statInData= new DataMap();
		statInData.put("USER_NO", param.getInt("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getInt("surveyGroupId"));
		statInData.put("SURVEY_YEAR", SurveyYear);
		statInData.put("SURVEY_DEGREE", SurveyDegree);
		statInData.put("SURVEY_IDS", surveyIds);
		statInData.put("SUMMARY_CDS", surveyCds);
		statData40=surveyUserService.selectDimensionAvg(statInData);
		statArray = StringUtil.jsonDimesionStr(statData40,1,aTbStatistics10.getSURVEY_ID(),aTbStatistics20.getSURVEY_ID(),aTbStatistics30.getSURVEY_ID());
		graphparam.put("summary4Array", statArray);
		
		//종합만족도
		Double  AVG_QUESTION_SCORE_70=0D;
		Double  AVG_QUESTION_SCORE_30=0D;
		Double  AVG_QUESTION_SCORE=0D;
		for(DataMap aDataMap:statData40){
			
			AVG_QUESTION_SCORE_70=Double.parseDouble(aDataMap.getString("AVG_QUESTION_SCORE_70"))*10;
			for(DataMap bDataMap:statData20){
				if(aDataMap.getString("SURVEY_ID").equals(bDataMap.getString("SURVEY_ID"))){
					AVG_QUESTION_SCORE_30=Double.parseDouble(bDataMap.getString("AVG_QUESTION_SCORE_30"))*10;
					break;
				}
			}
			
			AVG_QUESTION_SCORE=AVG_QUESTION_SCORE_70+AVG_QUESTION_SCORE_30;
			AVG_QUESTION_SCORE=AVG_QUESTION_SCORE/10;
			System.out.println("AVG_QUESTION_SCORE_70["+AVG_QUESTION_SCORE_70+"]AVG_QUESTION_SCORE_30["+AVG_QUESTION_SCORE_30+"]");
			System.out.println("AVG_QUESTION_SCORE["+AVG_QUESTION_SCORE+"]");
			
			aDataMap.put("AVG_QUESTION_SCORE",AVG_QUESTION_SCORE);
			
			statData10.add(aDataMap);
		}
		statArray = StringUtil.jsonDimesionStr(statData10,1,aTbStatistics10.getSURVEY_ID(),aTbStatistics20.getSURVEY_ID(),aTbStatistics30.getSURVEY_ID());
		graphparam.put("summary1Array", statArray);
		

		//환자경험 차원평가
		float SUM_SCORE = 0.0f;
		float CNT_SCORE = 0.0f;
		float AVG_SCORE = 0.0f;

		LinkedHashMap<String, String> eXamMap = new LinkedHashMap<String, String>();

		TbSectionUser aParamTbSectionUser= new TbSectionUser();
		aParamTbSectionUser.setUSER_NO(param.getInt("userNo"));
		aParamTbSectionUser.setSURVEY_GROUP_ID(param.getInt("surveyGroupId"));
		aParamTbSectionUser.setSURVEY_YEAR(SurveyYear);
		aParamTbSectionUser.setSURVEY_DEGREE(SurveyDegree);
		aParamTbSectionUser.setSURVEY_ID(SurveyId10);

		List<DataMap> eXamMaps=surveyUserService.selectListstatisticsCd12(aParamTbSectionUser);

		SUM_SCORE = 0.0f;
		CNT_SCORE = 0.0f;
		for(DataMap aDataMap:eXamMaps){
			SUM_SCORE =SUM_SCORE+(float)aDataMap.getFloat("SUM_SCORE");
			CNT_SCORE =CNT_SCORE+(float)aDataMap.getFloat("CNT_SCORE");
		}
		AVG_SCORE=SUM_SCORE/CNT_SCORE;
		eXamMap.put("총차원만족도", String.format("%.1f", AVG_SCORE));
		for(DataMap aDataMap:eXamMaps){
			eXamMap.put(aDataMap.getString("SECTION_RTITLE"), aDataMap.getString("AVG_SCORE"));
		}
		/*
		eXamMap.put("총차원만족도", "72.7");
		eXamMap.put("의사진료서비스", "77.2");
		eXamMap.put("검사/처방", "76.1");
		eXamMap.put("환경", "70.1");
		eXamMap.put("기타서비스", "77.7");
		eXamMap.put("진료대기시간", "63.1");
		*/
		statArray = StringUtil.jsonDimesionStr(eXamMap,1);
		graphparam.put("summary5Array", statArray);
		
		eXamMap = new LinkedHashMap<String, String>();

		aParamTbSectionUser.setSURVEY_ID(SurveyId20);
		eXamMaps=surveyUserService.selectListstatisticsCd12(aParamTbSectionUser);

		SUM_SCORE = 0.0f;
		CNT_SCORE = 0.0f;
		for(DataMap aDataMap:eXamMaps){
			SUM_SCORE =SUM_SCORE+(float)aDataMap.getFloat("SUM_SCORE");
			CNT_SCORE =CNT_SCORE+(float)aDataMap.getFloat("CNT_SCORE");
		}
		AVG_SCORE=SUM_SCORE/CNT_SCORE;
		eXamMap.put("총차원만족도", String.format("%.1f", AVG_SCORE));
		for(DataMap aDataMap:eXamMaps){
			eXamMap.put(aDataMap.getString("SECTION_RTITLE"), aDataMap.getString("AVG_SCORE"));
		}
	
		/*
		eXamMap.put("총차원만족도", "93.9");
		eXamMap.put("간호사의료서비스", "94.6");
		eXamMap.put("의사진료서비스", "94.6");
		eXamMap.put("투약/처방", "93.9");
		eXamMap.put("환경", "92.5");
		eXamMap.put("기타서비스", "93.5");
		eXamMap.put("입원/퇴실 절차", "92.5");
		eXamMap.put("환자권리보장", "95.7");
		*/
		
		statArray = StringUtil.jsonDimesionStr(eXamMap,1);
		graphparam.put("summary6Array", statArray);

		eXamMap = new LinkedHashMap<String, String>();
		aParamTbSectionUser.setSURVEY_ID(SurveyId30);
		eXamMaps=surveyUserService.selectListstatisticsCd12(aParamTbSectionUser);

		SUM_SCORE = 0.0f;
		CNT_SCORE = 0.0f;
		for(DataMap aDataMap:eXamMaps){
			SUM_SCORE =SUM_SCORE+(float)aDataMap.getFloat("SUM_SCORE");
			CNT_SCORE =CNT_SCORE+(float)aDataMap.getFloat("CNT_SCORE");
		}
		AVG_SCORE=SUM_SCORE/CNT_SCORE;
		eXamMap.put("총차원만족도", String.format("%.1f", AVG_SCORE));
		for(DataMap aDataMap:eXamMaps){
			eXamMap.put(aDataMap.getString("SECTION_RTITLE"), aDataMap.getString("AVG_SCORE"));
		}

		/*
		eXamMap.put("총차원만족도", "90.7");
		eXamMap.put("간호사의료서비스", "92.7");
		eXamMap.put("의사진료서비스", "91.5");
		eXamMap.put("투약/처방", "89.2");
		eXamMap.put("환경", "91.8");
		eXamMap.put("기타서비스", "87.5");
		eXamMap.put("퇴실 절차", "89.7");
		*/
		statArray = StringUtil.jsonDimesionStr(eXamMap,1);
		graphparam.put("summary7Array", statArray);
		
		// 조사차수별 평가를 하기 위해서 현재 차수포함  이전 4개의 조차 차수를 가져와야함  
		List<String> aYEARDEGREE = new ArrayList<String>();
		statInData= new DataMap();
		statInData.put("USER_NO", param.getInt("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getInt("surveyGroupId"));
		statInData.put("SURVEY_YEAR", SurveyYear);
		statInData.put("SURVEY_DEGREE", SurveyDegree);
		statInData.put("YEARDEGREE", SurveyYear+SurveyDegree);
		aYEARDEGREE = surveyUserService.selectListYearDegrees(statInData);
		
		//조사차수별 비교평가 종합만족도
		//3.환자경험 조사차수별 비교평가 ③체감만족도
		List<DataMap> statData13=new ArrayList<DataMap>();
		
		statInData= new DataMap();
		statInData.put("USER_NO", param.getInt("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getInt("surveyGroupId"));
		statInData.put("SURVEY_YEAR", SurveyYear);
		statInData.put("SURVEY_DEGREE", SurveyDegree);
		statInData.put("YEARDEGREES", aYEARDEGREE);
		statInData.put("SORT_ORDR", "");

		statData13=surveyUserService.selectListstatisticsCd13(statInData);
		eXamMap = new LinkedHashMap<String, String>();
		
		for(DataMap aststData13: statData13){
			eXamMap.put(aststData13.getString("SURVEY_YEAR_TITLE"),aststData13.getString("AVG_GRADE_AVG_VAL"));
		}
		
		statArray = StringUtil.jsonDimesionStr(eXamMap,1);
		graphparam.put("summary31Array", statArray);
		
		statData13=new ArrayList<DataMap>();
		
		statInData= new DataMap();
		statInData.put("USER_NO", param.getInt("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getInt("surveyGroupId"));
		statInData.put("SURVEY_YEAR", SurveyYear);
		statInData.put("SURVEY_DEGREE", SurveyDegree);
		statInData.put("YEARDEGREES", aYEARDEGREE);
		statInData.put("SORT_ORDR", "10");

		statData13=surveyUserService.selectListstatisticsCd13(statInData);
		eXamMap = new LinkedHashMap<String, String>();
		
		for(DataMap aststData13: statData13){
			eXamMap.put(aststData13.getString("SURVEY_YEAR_TITLE"),aststData13.getString("AVG_GRADE_AVG_VAL"));
		}
		
		statArray = StringUtil.jsonDimesionStr(eXamMap,1);
		graphparam.put("summary32Array", statArray);

		statData13=new ArrayList<DataMap>();
		
		statInData= new DataMap();
		statInData.put("USER_NO", param.getInt("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getInt("surveyGroupId"));
		statInData.put("SURVEY_YEAR", SurveyYear);
		statInData.put("SURVEY_DEGREE", SurveyDegree);
		statInData.put("YEARDEGREES", aYEARDEGREE);
		statInData.put("SORT_ORDR", "20");

		statData13=surveyUserService.selectListstatisticsCd13(statInData);
		eXamMap = new LinkedHashMap<String, String>();
		
		for(DataMap aststData13: statData13){
			eXamMap.put(aststData13.getString("SURVEY_YEAR_TITLE"),aststData13.getString("AVG_GRADE_AVG_VAL"));
		}
		
		statArray = StringUtil.jsonDimesionStr(eXamMap,1);
		graphparam.put("summary33Array", statArray);

		statData13=new ArrayList<DataMap>();
		
		statInData= new DataMap();
		statInData.put("USER_NO", param.getInt("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getInt("surveyGroupId"));
		statInData.put("SURVEY_YEAR", SurveyYear);
		statInData.put("SURVEY_DEGREE", SurveyDegree);
		statInData.put("YEARDEGREES", aYEARDEGREE);
		statInData.put("SORT_ORDR", "30");

		statData13=surveyUserService.selectListstatisticsCd13(statInData);
		eXamMap = new LinkedHashMap<String, String>();
		
		for(DataMap aststData13: statData13){
			eXamMap.put(aststData13.getString("SURVEY_YEAR_TITLE"),aststData13.getString("AVG_GRADE_AVG_VAL"));
		}
		
		statArray = StringUtil.jsonDimesionStr(eXamMap,1);
		graphparam.put("summary34Array", statArray);

		/*
		eXamMap = new LinkedHashMap<String, String>();
		eXamMap.put("19년도2차", "84.6");
		eXamMap.put("19년도1차", "87.1");
		eXamMap.put("18년도2차", "89.3");
		eXamMap.put("18년도1차", "81.7");
		
		graphparam.put("summary32Array", statArray);
		graphparam.put("summary33Array", statArray);
		graphparam.put("summary34Array", statArray);
		 */
		/*
		eXamMap = new LinkedHashMap<String, String>();
		eXamMap.put("19년도2차", "84.6");
		eXamMap.put("19년도1차", "87.1");
		eXamMap.put("18년도2차", "89.3");
		eXamMap.put("18년도1차", "81.7");
		statArray = StringUtil.jsonDimesionStr(eXamMap,1);
		graphparam.put("summary41Array", statArray);
		graphparam.put("summary42Array", statArray);
		graphparam.put("summary43Array", statArray);
		graphparam.put("summary44Array", statArray);
		*/
		
		
		//3.환자경험 조사차수별 비교평가 ③체감만족도
		List<DataMap> statData14=new ArrayList<DataMap>();
		
		statInData= new DataMap();
		statInData.put("USER_NO", param.getInt("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getInt("surveyGroupId"));
		statInData.put("SURVEY_YEAR", SurveyYear);
		statInData.put("SURVEY_DEGREE", SurveyDegree);
		statInData.put("YEARDEGREES", aYEARDEGREE);
		statInData.put("SORT_ORDR", "");

		statData14=surveyUserService.selectListstatisticsCd14(statInData);
		eXamMap = new LinkedHashMap<String, String>();
		
		for(DataMap aststData14: statData14){
			eXamMap.put(aststData14.getString("SURVEY_YEAR_TITLE"),aststData14.getString("AVG_QUESTION_SCORE"));
		}
		
		statArray = StringUtil.jsonDimesionStr(eXamMap,1);
		graphparam.put("summary41Array", statArray);

		statData14=new ArrayList<DataMap>();
		
		statInData= new DataMap();
		statInData.put("USER_NO", param.getInt("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getInt("surveyGroupId"));
		statInData.put("SURVEY_YEAR", SurveyYear);
		statInData.put("SURVEY_DEGREE", SurveyDegree);
		statInData.put("YEARDEGREES", aYEARDEGREE);
		statInData.put("SORT_ORDR", "10");

		statData14=surveyUserService.selectListstatisticsCd14(statInData);
		eXamMap = new LinkedHashMap<String, String>();
		
		for(DataMap aststData14: statData14){
			eXamMap.put(aststData14.getString("SURVEY_YEAR_TITLE"),aststData14.getString("AVG_QUESTION_SCORE"));
		}
		
		statArray = StringUtil.jsonDimesionStr(eXamMap,1);
		graphparam.put("summary42Array", statArray);

		statData14=new ArrayList<DataMap>();
		
		statInData= new DataMap();
		statInData.put("USER_NO", param.getInt("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getInt("surveyGroupId"));
		statInData.put("SURVEY_YEAR", SurveyYear);
		statInData.put("SURVEY_DEGREE", SurveyDegree);
		statInData.put("YEARDEGREES", aYEARDEGREE);
		statInData.put("SORT_ORDR", "20");

		statData14=surveyUserService.selectListstatisticsCd14(statInData);
		eXamMap = new LinkedHashMap<String, String>();
		
		for(DataMap aststData14: statData14){
			eXamMap.put(aststData14.getString("SURVEY_YEAR_TITLE"),aststData14.getString("AVG_QUESTION_SCORE"));
		}
		
		statArray = StringUtil.jsonDimesionStr(eXamMap,1);
		graphparam.put("summary43Array", statArray);
		
		statData14=new ArrayList<DataMap>();
		
		statInData= new DataMap();
		statInData.put("USER_NO", param.getInt("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getInt("surveyGroupId"));
		statInData.put("SURVEY_YEAR", SurveyYear);
		statInData.put("SURVEY_DEGREE", SurveyDegree);
		statInData.put("YEARDEGREES", aYEARDEGREE);
		statInData.put("SORT_ORDR", "30");

		statData14=surveyUserService.selectListstatisticsCd14(statInData);
		eXamMap = new LinkedHashMap<String, String>();
		
		for(DataMap aststData14: statData14){
			eXamMap.put(aststData14.getString("SURVEY_YEAR_TITLE"),aststData14.getString("AVG_QUESTION_SCORE"));
		}
		
		statArray = StringUtil.jsonDimesionStr(eXamMap,1);
		graphparam.put("summary44Array", statArray);
	
		//3.환자경험 조사차수별 비교평가 ③체감만족도
		List<DataMap> statData15=new ArrayList<DataMap>();
		surveyCds = new ArrayList<String>();
		surveyCds.add("10");
		surveyCds.add("11");
		
		statInData= new DataMap();
		statInData.put("USER_NO", param.getInt("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getInt("surveyGroupId"));
		statInData.put("SURVEY_YEAR", SurveyYear);
		statInData.put("SURVEY_DEGREE", SurveyDegree);
		statInData.put("YEARDEGREES", aYEARDEGREE);
		statInData.put("SORT_ORDR", "");
		statInData.put("SUMMARY_CDS", surveyCds);

		statData15=surveyUserService.selectListstatisticsCd15(statInData);
		eXamMap = new LinkedHashMap<String, String>();
		
		for(DataMap aststData15: statData15){
			eXamMap.put(aststData15.getString("SURVEY_YEAR_TITLE"),aststData15.getString("AVG_GRADE_AVG_VAL"));
		}
		
		statArray = StringUtil.jsonDimesionStr(eXamMap,1);
		graphparam.put("summary51Array", statArray);

		statData15=new ArrayList<DataMap>();
		surveyCds = new ArrayList<String>();
		surveyCds.add("10");
		surveyCds.add("11");
		
		statInData= new DataMap();
		statInData.put("USER_NO", param.getInt("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getInt("surveyGroupId"));
		statInData.put("SURVEY_YEAR", SurveyYear);
		statInData.put("SURVEY_DEGREE", SurveyDegree);
		statInData.put("YEARDEGREES", aYEARDEGREE);
		statInData.put("SORT_ORDR", "10");
		statInData.put("SUMMARY_CDS", surveyCds);

		statData15=surveyUserService.selectListstatisticsCd15(statInData);
		eXamMap = new LinkedHashMap<String, String>();
		
		for(DataMap aststData15: statData15){
			eXamMap.put(aststData15.getString("SURVEY_YEAR_TITLE"),aststData15.getString("AVG_GRADE_AVG_VAL"));
		}
		
		statArray = StringUtil.jsonDimesionStr(eXamMap,1);
		graphparam.put("summary52Array", statArray);
	
		

		statData15=new ArrayList<DataMap>();
		surveyCds = new ArrayList<String>();
		surveyCds.add("10");
		surveyCds.add("11");
		
		statInData= new DataMap();
		statInData.put("USER_NO", param.getInt("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getInt("surveyGroupId"));
		statInData.put("SURVEY_YEAR", SurveyYear);
		statInData.put("SURVEY_DEGREE", SurveyDegree);
		statInData.put("YEARDEGREES", aYEARDEGREE);
		statInData.put("SORT_ORDR", "20");
		statInData.put("SUMMARY_CDS", surveyCds);

		statData15=surveyUserService.selectListstatisticsCd15(statInData);
		eXamMap = new LinkedHashMap<String, String>();
		
		for(DataMap aststData15: statData15){
			eXamMap.put(aststData15.getString("SURVEY_YEAR_TITLE"),aststData15.getString("AVG_GRADE_AVG_VAL"));
		}
		
		statArray = StringUtil.jsonDimesionStr(eXamMap,1);
		graphparam.put("summary53Array", statArray);
		
		

		statData15=new ArrayList<DataMap>();
		surveyCds = new ArrayList<String>();
		surveyCds.add("10");
		surveyCds.add("11");
		
		statInData= new DataMap();
		statInData.put("USER_NO", param.getInt("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getInt("surveyGroupId"));
		statInData.put("SURVEY_YEAR", SurveyYear);
		statInData.put("SURVEY_DEGREE", SurveyDegree);
		statInData.put("YEARDEGREES", aYEARDEGREE);
		statInData.put("SORT_ORDR", "30");
		statInData.put("SUMMARY_CDS", surveyCds);

		statData15=surveyUserService.selectListstatisticsCd15(statInData);
		eXamMap = new LinkedHashMap<String, String>();
		
		for(DataMap aststData15: statData15){
			eXamMap.put(aststData15.getString("SURVEY_YEAR_TITLE"),aststData15.getString("AVG_GRADE_AVG_VAL"));
		}
		statArray = StringUtil.jsonDimesionStr(eXamMap,1);
		graphparam.put("summary54Array", statArray);

		//조사차수별 비교평가 종합만족도
		List<LinkedHashMap> eXamList = new ArrayList<LinkedHashMap>();
		List<DataMap> statData16=new ArrayList<DataMap>();
		surveyCds = new ArrayList<String>();
		surveyCds.add("9");
		statInData= new DataMap();
		statInData.put("USER_NO", param.getInt("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getInt("surveyGroupId"));
		statInData.put("SURVEY_ID", SurveyId10);
		statInData.put("SURVEY_YEAR", SurveyYear);
		statInData.put("SURVEY_DEGREE", SurveyDegree);
		statInData.put("YEARDEGREES", aYEARDEGREE);
		statInData.put("SORT_ORDR", "10");
		statInData.put("SUMMARY_CDS", surveyCds);
		statData16=surveyUserService.selectListstatisticsCd16(statInData);

		int i16=0;
		String  i16section="";
	    float GRADE_01_VAL = 0.0f;
	    float GRADE_02_VAL = 0.0f;
	    float GRADE_03_VAL = 0.0f;
	    float GRADE_04_VAL = 0.0f;
        float GRADE_AVG_VAL =0.0f;
		for(DataMap aDataMap16: statData16){
			
			if(!i16section.equals(aDataMap16.getString("SECTION_ID"))){
				eXamMap = new LinkedHashMap<String, String>();
				eXamMap.put("SECTON_TITLE",aDataMap16.getString("SECTION_RTITLE"));
				eXamMap.put("GRADE_04_VAL", aDataMap16.getString("AVG_GRADE_AVG_VAL"));
				GRADE_04_VAL =   Float.parseFloat(aDataMap16.getString("AVG_GRADE_AVG_VAL"));
			}

			i16section=aDataMap16.getString("SECTION_ID");
			
			if(i16==1){
				eXamMap.put("GRADE_03_VAL", aDataMap16.getString("AVG_GRADE_AVG_VAL"));
				GRADE_03_VAL =   Float.parseFloat(aDataMap16.getString("AVG_GRADE_AVG_VAL"));
			}else if(i16==2){
				eXamMap.put("GRADE_02_VAL",  aDataMap16.getString("AVG_GRADE_AVG_VAL"));
				GRADE_02_VAL =   Float.parseFloat(aDataMap16.getString("AVG_GRADE_AVG_VAL"));
			}else if(i16== 3){
				eXamMap.put("GRADE_01_VAL",  aDataMap16.getString("AVG_GRADE_AVG_VAL"));
				GRADE_01_VAL =   Float.parseFloat(aDataMap16.getString("AVG_GRADE_AVG_VAL"));
			}
			i16++;
			
			if(i16==aDataMap16.getInt("CNT_SECTION")){
				 i16=0;
				 
				 if(0!=aDataMap16.getInt("CNT_SECTION"))
					 GRADE_AVG_VAL=(GRADE_04_VAL+GRADE_03_VAL+GRADE_02_VAL+GRADE_01_VAL);
				 else
					 GRADE_AVG_VAL=0f;
					 
     		    String  sGRADE_AVG_VAL = String.format("%.1f", GRADE_AVG_VAL);
     		    		 
				eXamMap.put("GRADE_AVG_VAL", sGRADE_AVG_VAL);
				eXamList.add(eXamMap);
			    GRADE_01_VAL = 0.0f;
			    GRADE_02_VAL = 0.0f;
			    GRADE_03_VAL = 0.0f;
			    GRADE_04_VAL = 0.0f;

			}
		}
		
		/*
		eXamMap = new LinkedHashMap<String, String>();
		eXamMap.put("YEARDEGREE", "검사/처방");
		eXamMap.put("GRADE_04_VAL", "76.1");
		eXamMap.put("GRADE_03_VAL", "79.4");
		eXamMap.put("GRADE_02_VAL", "89.0");
		eXamMap.put("GRADE_01_VAL", "79.5");
		eXamMap.put("GRADE_AVG_VAL", "77");
		eXamList.add(eXamMap);
		
		eXamMap = new LinkedHashMap<String, String>();
		eXamMap.put("YEARDEGREE", "환경");
		eXamMap.put("GRADE_04_VAL", "70.1");
		eXamMap.put("GRADE_03_VAL", "80.4");
		eXamMap.put("GRADE_02_VAL", "85.6");
		eXamMap.put("GRADE_01_VAL", "76.4");
		eXamMap.put("GRADE_AVG_VAL", "77");
		eXamList.add(eXamMap);

		eXamMap = new LinkedHashMap<String, String>();
		eXamMap.put("YEARDEGREE", "기타서비스");
		eXamMap.put("GRADE_04_VAL", "76.7");
		eXamMap.put("GRADE_03_VAL", "80.3");
		eXamMap.put("GRADE_02_VAL", "91.7");
		eXamMap.put("GRADE_01_VAL", "80.8");
		eXamMap.put("GRADE_AVG_VAL", "88");
		eXamList.add(eXamMap);

		eXamMap = new LinkedHashMap<String, String>();
		eXamMap.put("YEARDEGREE", "진료대기시간");
		eXamMap.put("GRADE_04_VAL", "63.1");
		eXamMap.put("GRADE_03_VAL", "67.5");
		eXamMap.put("GRADE_02_VAL", "79.4");
		eXamMap.put("GRADE_01_VAL", "65.9");
		eXamMap.put("GRADE_AVG_VAL", "65");
		eXamList.add(eXamMap);
        */
		statArray = StringUtil.jsonDimesionStr(eXamList);
		graphparam.put("summary61Array", statArray);

		eXamList = new ArrayList<LinkedHashMap>();
		statData16=new ArrayList<DataMap>();
		surveyCds = new ArrayList<String>();
		surveyCds.add("9");
		statInData= new DataMap();
		statInData.put("USER_NO", param.getInt("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getInt("surveyGroupId"));
		statInData.put("SURVEY_ID", SurveyId20);
		statInData.put("SURVEY_YEAR", SurveyYear);
		statInData.put("SURVEY_DEGREE", SurveyDegree);
		statInData.put("YEARDEGREES", aYEARDEGREE);
		statInData.put("SORT_ORDR", "20");
		statInData.put("SUMMARY_CDS", surveyCds);
		statData16=surveyUserService.selectListstatisticsCd16(statInData);

		i16=0;
		 i16section="";
	    GRADE_01_VAL = 0.0f;
	    GRADE_02_VAL = 0.0f;
	    GRADE_03_VAL = 0.0f;
	     GRADE_04_VAL = 0.0f;
        GRADE_AVG_VAL =0.0f;
        
        List surveyYear16s = new ArrayList<String>();
		 String i16SurveyYearTitle="";
        
		for(DataMap aDataMap16: statData16){
			
			if(!i16SurveyYearTitle.equals(aDataMap16.getString("SURVEY_YEAR_TITLE"))){
				surveyYear16s.add(aDataMap16.getString("SURVEY_YEAR_TITLE"));
			}
			i16SurveyYearTitle=aDataMap16.getString("SURVEY_YEAR_TITLE");
			
			if(!i16section.equals(aDataMap16.getString("SECTION_ID"))){
				eXamMap = new LinkedHashMap<String, String>();
				eXamMap.put("SECTON_TITLE",aDataMap16.getString("SECTION_RTITLE"));
				eXamMap.put("GRADE_04_VAL", aDataMap16.getString("AVG_GRADE_AVG_VAL"));
				GRADE_04_VAL =   Float.parseFloat(aDataMap16.getString("AVG_GRADE_AVG_VAL"));
			}

			i16section=aDataMap16.getString("SECTION_ID");
			
			if(i16==1){
				eXamMap.put("GRADE_03_VAL", aDataMap16.getString("AVG_GRADE_AVG_VAL"));
				GRADE_03_VAL =   Float.parseFloat(aDataMap16.getString("AVG_GRADE_AVG_VAL"));
			}else if(i16==2){
				eXamMap.put("GRADE_02_VAL",  aDataMap16.getString("AVG_GRADE_AVG_VAL"));
				GRADE_02_VAL =   Float.parseFloat(aDataMap16.getString("AVG_GRADE_AVG_VAL"));
			}else if(i16== 3){
				eXamMap.put("GRADE_01_VAL",  aDataMap16.getString("AVG_GRADE_AVG_VAL"));
				GRADE_01_VAL =   Float.parseFloat(aDataMap16.getString("AVG_GRADE_AVG_VAL"));
			}
			i16++;
			
			if(i16==aDataMap16.getInt("CNT_SECTION")){
				 i16=0;
				 
				 if(0!=aDataMap16.getInt("CNT_SECTION"))
					 GRADE_AVG_VAL=(GRADE_04_VAL+GRADE_03_VAL+GRADE_02_VAL+GRADE_01_VAL);
				 else
					 GRADE_AVG_VAL=0f;
					 
     		    String  sGRADE_AVG_VAL = String.format("%.1f", GRADE_AVG_VAL);
     		    		 
				eXamMap.put("GRADE_AVG_VAL", sGRADE_AVG_VAL);
				eXamList.add(eXamMap);
			    GRADE_01_VAL = 0.0f;
			    GRADE_02_VAL = 0.0f;
			    GRADE_03_VAL = 0.0f;
			    GRADE_04_VAL = 0.0f;

			}
		}
		statArray = StringUtil.jsonDimesionStr(eXamList);
		graphparam.put("summary62Array", statArray);

		/*
		eXamList = new ArrayList<LinkedHashMap>();
		eXamMap = new LinkedHashMap<String, String>();
		eXamMap.put("YEARDEGREE", "간호사 의료서비스");
		eXamMap.put("GRADE_04_VAL", "77.2");
		eXamMap.put("GRADE_03_VAL", "85.9");
		eXamMap.put("GRADE_02_VAL", "89.4");
		eXamMap.put("GRADE_01_VAL", "83.3");
		eXamMap.put("GRADE_AVG_VAL", "80");
		eXamList.add(eXamMap);

		eXamMap = new LinkedHashMap<String, String>();
		eXamMap.put("YEARDEGREE", "의사진료서비스");
		eXamMap.put("GRADE_04_VAL", "76.1");
		eXamMap.put("GRADE_03_VAL", "79.4");
		eXamMap.put("GRADE_02_VAL", "89.0");
		eXamMap.put("GRADE_01_VAL", "79.5");
		eXamMap.put("GRADE_AVG_VAL", "77");
		eXamList.add(eXamMap);
		
		eXamMap = new LinkedHashMap<String, String>();
		eXamMap.put("YEARDEGREE", "투약처방");
		eXamMap.put("GRADE_04_VAL", "70.1");
		eXamMap.put("GRADE_03_VAL", "80.4");
		eXamMap.put("GRADE_02_VAL", "85.6");
		eXamMap.put("GRADE_01_VAL", "76.4");
		eXamMap.put("GRADE_AVG_VAL", "77");
		eXamList.add(eXamMap);

		eXamMap = new LinkedHashMap<String, String>();
		eXamMap.put("YEARDEGREE", "환경");
		eXamMap.put("GRADE_04_VAL", "76.7");
		eXamMap.put("GRADE_03_VAL", "80.3");
		eXamMap.put("GRADE_02_VAL", "91.7");
		eXamMap.put("GRADE_01_VAL", "80.8");
		eXamMap.put("GRADE_AVG_VAL", "88");
		eXamList.add(eXamMap);

		eXamMap = new LinkedHashMap<String, String>();
		eXamMap.put("YEARDEGREE", "기타서비스");
		eXamMap.put("GRADE_04_VAL", "76.7");
		eXamMap.put("GRADE_03_VAL", "80.3");
		eXamMap.put("GRADE_02_VAL", "91.7");
		eXamMap.put("GRADE_01_VAL", "80.8");
		eXamMap.put("GRADE_AVG_VAL", "88");
		eXamList.add(eXamMap);

		eXamMap = new LinkedHashMap<String, String>();
		eXamMap.put("YEARDEGREE", "입실/퇴실절차");
		eXamMap.put("GRADE_04_VAL", "76.7");
		eXamMap.put("GRADE_03_VAL", "80.3");
		eXamMap.put("GRADE_02_VAL", "91.7");
		eXamMap.put("GRADE_01_VAL", "80.8");
		eXamMap.put("GRADE_AVG_VAL", "88");
		eXamList.add(eXamMap);

		eXamMap = new LinkedHashMap<String, String>();
		eXamMap.put("YEARDEGREE", "환자권리보장");
		eXamMap.put("GRADE_04_VAL", "76.7");
		eXamMap.put("GRADE_03_VAL", "80.3");
		eXamMap.put("GRADE_02_VAL", "91.7");
		eXamMap.put("GRADE_01_VAL", "80.8");
		eXamMap.put("GRADE_AVG_VAL", "88");
		eXamList.add(eXamMap);
	    */
		/*
		eXamList = new ArrayList<LinkedHashMap>();
		eXamMap = new LinkedHashMap<String, String>();
		eXamMap.put("YEARDEGREE", "간호사 의료서비스");
		eXamMap.put("GRADE_04_VAL", "77.2");
		eXamMap.put("GRADE_03_VAL", "85.9");
		eXamMap.put("GRADE_02_VAL", "89.4");
		eXamMap.put("GRADE_01_VAL", "83.3");
		eXamMap.put("GRADE_AVG_VAL", "80");
		eXamList.add(eXamMap);

		eXamMap = new LinkedHashMap<String, String>();
		eXamMap.put("YEARDEGREE", "의사의료서비스");
		eXamMap.put("GRADE_04_VAL", "76.1");
		eXamMap.put("GRADE_03_VAL", "79.4");
		eXamMap.put("GRADE_02_VAL", "89.0");
		eXamMap.put("GRADE_01_VAL", "79.5");
		eXamMap.put("GRADE_AVG_VAL", "77");
		eXamList.add(eXamMap);
		
		eXamMap = new LinkedHashMap<String, String>();
		eXamMap.put("YEARDEGREE", "투약/통증");
		eXamMap.put("GRADE_04_VAL", "70.1");
		eXamMap.put("GRADE_03_VAL", "80.4");
		eXamMap.put("GRADE_02_VAL", "85.6");
		eXamMap.put("GRADE_01_VAL", "76.4");
		eXamMap.put("GRADE_AVG_VAL", "77");
		eXamList.add(eXamMap);

		eXamMap = new LinkedHashMap<String, String>();
		eXamMap.put("YEARDEGREE", "환경");
		eXamMap.put("GRADE_04_VAL", "76.7");
		eXamMap.put("GRADE_03_VAL", "80.3");
		eXamMap.put("GRADE_02_VAL", "91.7");
		eXamMap.put("GRADE_01_VAL", "80.8");
		eXamMap.put("GRADE_AVG_VAL", "88");
		eXamList.add(eXamMap);

		eXamMap = new LinkedHashMap<String, String>();
		eXamMap.put("YEARDEGREE", "기타서비스");
		eXamMap.put("GRADE_04_VAL", "76.7");
		eXamMap.put("GRADE_03_VAL", "80.3");
		eXamMap.put("GRADE_02_VAL", "91.7");
		eXamMap.put("GRADE_01_VAL", "80.8");
		eXamMap.put("GRADE_AVG_VAL", "88");
		eXamList.add(eXamMap);

		eXamMap = new LinkedHashMap<String, String>();
		eXamMap.put("YEARDEGREE", "퇴실절차");
		eXamMap.put("GRADE_04_VAL", "76.7");
		eXamMap.put("GRADE_03_VAL", "80.3");
		eXamMap.put("GRADE_02_VAL", "91.7");
		eXamMap.put("GRADE_01_VAL", "80.8");
		eXamMap.put("GRADE_AVG_VAL", "88");
		eXamList.add(eXamMap);
		 */

		eXamList = new ArrayList<LinkedHashMap>();
		statData16=new ArrayList<DataMap>();
		surveyCds = new ArrayList<String>();
		surveyCds.add("9");
		statInData= new DataMap();
		statInData.put("USER_NO", param.getInt("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getInt("surveyGroupId"));
		statInData.put("SURVEY_ID", SurveyId30);
		statInData.put("SURVEY_YEAR", SurveyYear);
		statInData.put("SURVEY_DEGREE", SurveyDegree);
		statInData.put("YEARDEGREES", aYEARDEGREE);
		statInData.put("SORT_ORDR", "30");
		statInData.put("SUMMARY_CDS", surveyCds);
		statData16=surveyUserService.selectListstatisticsCd16(statInData);

		i16=0;
		i16section="";
	    GRADE_01_VAL = 0.0f;
	    GRADE_02_VAL = 0.0f;
	    GRADE_03_VAL = 0.0f;
	    GRADE_04_VAL = 0.0f;
        GRADE_AVG_VAL =0.0f;
		for(DataMap aDataMap16: statData16){
			
			if(!i16section.equals(aDataMap16.getString("SECTION_ID"))){
				eXamMap = new LinkedHashMap<String, String>();
				eXamMap.put("SECTON_TITLE",aDataMap16.getString("SECTION_RTITLE"));
				eXamMap.put("GRADE_04_VAL", aDataMap16.getString("AVG_GRADE_AVG_VAL"));
				GRADE_04_VAL =   Float.parseFloat(aDataMap16.getString("AVG_GRADE_AVG_VAL"));
			}

			i16section=aDataMap16.getString("SECTION_ID");
			
			if(i16==1){
				eXamMap.put("GRADE_03_VAL", aDataMap16.getString("AVG_GRADE_AVG_VAL"));
				GRADE_03_VAL =   Float.parseFloat(aDataMap16.getString("AVG_GRADE_AVG_VAL"));
			}else if(i16==2){
				eXamMap.put("GRADE_02_VAL",  aDataMap16.getString("AVG_GRADE_AVG_VAL"));
				GRADE_02_VAL =   Float.parseFloat(aDataMap16.getString("AVG_GRADE_AVG_VAL"));
			}else if(i16== 3){
				eXamMap.put("GRADE_01_VAL",  aDataMap16.getString("AVG_GRADE_AVG_VAL"));
				GRADE_01_VAL =   Float.parseFloat(aDataMap16.getString("AVG_GRADE_AVG_VAL"));
			}
			i16++;
			
			if(i16==aDataMap16.getInt("CNT_SECTION")){
				 i16=0;
				 
				 if(0!=aDataMap16.getInt("CNT_SECTION"))
					 GRADE_AVG_VAL=(GRADE_04_VAL+GRADE_03_VAL+GRADE_02_VAL+GRADE_01_VAL);
				 else
					 GRADE_AVG_VAL=0f;
					 
     		    String  sGRADE_AVG_VAL = String.format("%.1f", GRADE_AVG_VAL);
     		    		 
				eXamMap.put("GRADE_AVG_VAL", sGRADE_AVG_VAL);
				eXamList.add(eXamMap);
			    GRADE_01_VAL = 0.0f;
			    GRADE_02_VAL = 0.0f;
			    GRADE_03_VAL = 0.0f;
			    GRADE_04_VAL = 0.0f;

			}
		}
		statArray = StringUtil.jsonDimesionStr(eXamList);
		graphparam.put("summary63Array", statArray);
		
		
		graphparam.put("entryCnt1", SurveyId10EntryCnt);
		graphparam.put("entryCnt2", SurveyId20EntryCnt);
		graphparam.put("entryCnt3", SurveyId30EntryCnt);
		
		
		model.addAttribute("param", param);
		model.addAttribute("graphparam", graphparam);
		model.addAttribute("dim3", DataMapDim3);
		model.addAttribute("surveyCds", SurveyCdS);
		model.addAttribute("surveyYear16s", surveyYear16s);
		
		
		
		
		return "/mgt/survey/statisticsCd1Entry";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: statisticsCd2
	 * 2. ClassName  	: SurveyUserController
	 * 3. Comment   	: 진단지_외래서비스
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 05. 11 15:59
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/survey/statisticsCd2Entry.do")
	public String statisticsCd2(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		
		/*
		String surveyId 	= param.getString("surveyId");
		//리스트 조회

		TbSurvey survey = surveyService.selectSurvey(param);

		DataMap userParam = new DataMap();
		userParam.put("SURVEY_ID", param.getString("surveyId"));
		userParam.put("SURVEY_GROUP_ID", param.getString("surveyGroupId"));
		userParam.put("USER_NO", param.getString("userNo"));
		TbSurveyUser surveyUser = surveyUserService.selectUserGroupSurvey(userParam);
		
		List<DataMap> SurveyCdS=surveyUserService.selectListSurveyCd(userParam);
		*/
	    // 진단지리스트
		DataMap degreeParam = new DataMap();
		degreeParam.put("USER_NO", param.getString("userNo"));
		degreeParam.put("SURVEY_GROUP_ID", param.getString("surveyGroupId"));
		degreeParam.put("SURVEY_YEAR", param.getString("surveyYear"));
		degreeParam.put("SURVEY_DEGREE", param.getString("surveyDegree"));
		List<DataMap> SurveyCdS = surveyUserService.selectListUserGroupSurvey(degreeParam);

		TbUserGroup aTbUserGroup=null;
		 aTbUserGroup=surveyUserService.selectUserGroup(degreeParam);
		 if(aTbUserGroup !=null) 	 param.put("SURVEY_GROUP_NM", aTbUserGroup.getSURVEY_GROUP_NM());

		
		//SurveyID
		String SurveyId="";
		String SurveyYear="";
		String SurveyDegree="";
		String SurveyId10="";
		String SurveyId20="";
		String SurveyId30="";
		int SurveyId10EntryCnt=0;
		int SurveyId20EntryCnt=0;
		int SurveyId30EntryCnt=0;
		
		
		
		for(DataMap aSurvey:SurveyCdS){
			if(aSurvey.getInt("SORT_ORDR")==10){
				SurveyId10=aSurvey.getString("SURVEY_ID");
				SurveyId10EntryCnt=aSurvey.getInt("NUMENTRIES");
				SurveyYear=aSurvey.getString("SURVEY_YEAR");
				SurveyDegree=aSurvey.getString("SURVEY_DEGREE");
			}else if(aSurvey.getInt("SORT_ORDR")==20){
				SurveyId20=aSurvey.getString("SURVEY_ID");
				SurveyId20EntryCnt=aSurvey.getInt("NUMENTRIES");
				SurveyYear=aSurvey.getString("SURVEY_YEAR");
				SurveyDegree=aSurvey.getString("SURVEY_DEGREE");
			}else if(aSurvey.getInt("SORT_ORDR")==30){
				SurveyId30=aSurvey.getString("SURVEY_ID");
				SurveyId30EntryCnt=aSurvey.getInt("NUMENTRIES");
				SurveyYear=aSurvey.getString("SURVEY_YEAR");
				SurveyDegree=aSurvey.getString("SURVEY_DEGREE");
			}
		}
		String sortOrdr="";
		if("3".equals(param.getString("statisticsCd"))){
			SurveyId=SurveyId10;
			sortOrdr="10";
		}else if("4".equals(param.getString("statisticsCd"))){
			SurveyId=SurveyId20;
			sortOrdr="20";
		}else if("5".equals(param.getString("statisticsCd"))){
			SurveyId=SurveyId30;
			sortOrdr="30";
		}
		param.put("sortOrdr",sortOrdr);
		
		//TB_SURVEY_USER_ENTRY_RESULT 저장
		/* 삭제 조사년도 차수리스트에서 데이타 생성에서 데이타 생성함
		TbQuestionUser aTbQuestionUserResult= new TbQuestionUser();
		aTbQuestionUserResult.setUSER_NO(param.getInt("userNo"));
		aTbQuestionUserResult.setSURVEY_GROUP_ID(param.getInt("surveyGroupId"));
		aTbQuestionUserResult.setSURVEY_ID(SurveyId);
		aTbQuestionUserResult.setSURVEY_YEAR(SurveyYear);
		aTbQuestionUserResult.setSURVEY_DEGREE(SurveyDegree);
		surveyUserService.deleteUserEntryResult(aTbQuestionUserResult);
		
		List<DataMap> SurveyLableCnt=surveyUserService.selectListResultsLabelCnt(aTbQuestionUserResult);
		List<TbSurveyUserEntryResult> tbSurveyUserEntryResults = new ArrayList<TbSurveyUserEntryResult>();
		tbSurveyUserEntryResults=StringUtil.questionUsers(SurveyLableCnt);
		
		//System.out.println("tbSurveyUserEntryResults["+tbSurveyUserEntryResults+"]");
		for (TbSurveyUserEntryResult aTbSurveyUserEntryResult:tbSurveyUserEntryResults){
			aTbSurveyUserEntryResult.setINSERT_USER_NO(userInfoVo.getUserNo());
			aTbSurveyUserEntryResult.setUPDATE_USER_NO(userInfoVo.getUserNo());
			System.out.println("aTbSurveyUserEntryResult["+aTbSurveyUserEntryResult.getSECTION_ID()+"/"+aTbSurveyUserEntryResult.getQUESTION_ID()+"/"+aTbSurveyUserEntryResult.getGRADE_01_CNT()+"/"+aTbSurveyUserEntryResult.getGRADE_02_CNT()+"/"+aTbSurveyUserEntryResult.getGRADE_03_CNT()+"/"+aTbSurveyUserEntryResult.getGRADE_04_CNT()+"]");
			surveyUserService.insertUserEntryResult(aTbSurveyUserEntryResult);
		}
		*/
		
		
		
		System.out.println("SurveyId["+SurveyId+"]");
		
		//차원 
		DataMap graphparam = new DataMap();
		graphparam.put("entryCnt1", SurveyId10EntryCnt);
		graphparam.put("entryCnt2", SurveyId20EntryCnt);
		graphparam.put("entryCnt3", SurveyId30EntryCnt);
		
		TbSectionUser aTbSectionUser = new TbSectionUser();
		aTbSectionUser.setSURVEY_ID(SurveyId);
		aTbSectionUser.setUSER_NO(param.getInt("userNo"));
		aTbSectionUser.setSURVEY_GROUP_ID(param.getInt("surveyGroupId"));
		aTbSectionUser.setSURVEY_YEAR(SurveyYear);
		aTbSectionUser.setSURVEY_DEGREE(SurveyDegree);
		aTbSectionUser.setEXPORTINCLUDER("1");

		TbQuestionUser aTbQuestionUser = new TbQuestionUser();
		aTbQuestionUser.setSURVEY_ID(SurveyId);
		aTbQuestionUser.setUSER_NO(param.getInt("userNo"));
		aTbQuestionUser.setSURVEY_GROUP_ID(param.getInt("surveyGroupId"));
		aTbQuestionUser.setSURVEY_YEAR(SurveyYear);
		aTbQuestionUser.setSURVEY_DEGREE(SurveyDegree);
		aTbQuestionUser.setEXPORTINCLUDER("1");

		List<DataMap> SectionUserList   = surveyUserService.selectListUserSection( aTbSectionUser);
		List<DataMap> QuestionUserList = surveyUserService.selectListUserQuestion( aTbQuestionUser);
		
		// 조사차수별 평가를 하기 위해서 현재 차수포함  이전 4개의 조차 차수를 가져와야함  
		List<String> aYEARDEGREE = new ArrayList<String>();
		DataMap statInData= new DataMap();
		statInData.put("USER_NO", param.getInt("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getInt("surveyGroupId"));
		statInData.put("SURVEY_YEAR", SurveyYear);
		statInData.put("SURVEY_DEGREE", SurveyDegree);
		statInData.put("YEARDEGREE", SurveyYear+SurveyDegree);
		aYEARDEGREE = surveyUserService.selectListYearDegrees(statInData);
		
		statInData= new DataMap();
		statInData.put("SURVEY_ID", SurveyId);
		statInData.put("USER_NO", param.getInt("userNo"));
		statInData.put("SURVEY_GROUP_ID", param.getInt("surveyGroupId"));
		statInData.put("SURVEY_YEAR", SurveyYear);
		statInData.put("YEARDEGREES", aYEARDEGREE);

		List<DataMap> QuestionUserEntryResultList = surveyUserService.selectListUserEntryResult(statInData);
		
		
		
		TbSatisfaction aTbSatisfaction =new TbSatisfaction();
		TbSatisfactionItem aTbSatisfactionItem = new TbSatisfactionItem();
		ArrayList<TbSatisfaction> tbSatisfactions = new ArrayList<TbSatisfaction>();
		TbSurveyUserEntryResult aTbSatisfactionItemVal =new TbSurveyUserEntryResult();
		ArrayList<TbSurveyUserEntryResult> aSatisfactionItemVals = new ArrayList<TbSurveyUserEntryResult>();
		ArrayList<TbSatisfactionItem> tbSatisfactionItem = new ArrayList<TbSatisfactionItem>();
		
		
		for(DataMap SectionUserMap:SectionUserList){
			aTbSatisfaction =new TbSatisfaction();
			aTbSatisfaction.setSURVEY_ID(SectionUserMap.getString("SURVEY_ID"));
			aTbSatisfaction.setSECTION_ID(SectionUserMap.getInt("SECTION_ID"));
			aTbSatisfaction.setSECTION_TITLE(SectionUserMap.getString("SECTION_RTITLE"));
			aTbSatisfaction.setSECTION_CONT(SectionUserMap.getString("SECTION_CONT"));
			aTbSatisfaction.setSECTION_END(SectionUserMap.getString("SECTION_END"));
			tbSatisfactionItem = new ArrayList<TbSatisfactionItem>();
			
			for(DataMap QuestionUserMap:QuestionUserList){
				 if(QuestionUserMap.getInt("SECTION_ID")==SectionUserMap.getInt("SECTION_ID") ){
					aTbSatisfactionItem = new TbSatisfactionItem();
					aTbSatisfactionItem.setSURVEY_ID(QuestionUserMap.getString("SURVEY_ID"));
					aTbSatisfactionItem.setSECTION_ID(QuestionUserMap.getInt("SECTION_ID"));
					aTbSatisfactionItem.setQUESTION_ID(QuestionUserMap.getInt("QUESTION_ID"));;
					aTbSatisfactionItem.setQUESTION_LABEL(QuestionUserMap.getString("QUESTION_RTITLE"));

					aSatisfactionItemVals = new ArrayList<TbSurveyUserEntryResult>();
					
					for(DataMap QuestionUserResultMap:QuestionUserEntryResultList){
						if(QuestionUserResultMap.getInt("SECTION_ID")==SectionUserMap.getInt("SECTION_ID") && QuestionUserResultMap.getInt("QUESTION_ID")==QuestionUserMap.getInt("QUESTION_ID") ){
							aTbSatisfactionItemVal =new TbSurveyUserEntryResult();
							aTbSatisfactionItemVal.setSURVEY_ID(QuestionUserResultMap.getString("SURVEY_ID"));
							aTbSatisfactionItemVal.setSURVEY_YEAR(QuestionUserResultMap.getString("SURVEY_YEAR"));
							aTbSatisfactionItemVal.setSURVEY_DEGREE(QuestionUserResultMap.getString("SURVEY_DEGREE"));
							aTbSatisfactionItemVal.setSECTION_ID(QuestionUserResultMap.getInt("SECTION_ID"));
							aTbSatisfactionItemVal.setQUESTION_ID(QuestionUserResultMap.getInt("QUESTION_ID"));
							aTbSatisfactionItemVal.setGRADE_01_VAL(QuestionUserResultMap.getString("GRADE_01_VAL"));
							aTbSatisfactionItemVal.setGRADE_02_VAL(QuestionUserResultMap.getString("GRADE_02_VAL"));
							aTbSatisfactionItemVal.setGRADE_03_VAL(QuestionUserResultMap.getString("GRADE_03_VAL"));
							aTbSatisfactionItemVal.setGRADE_04_VAL(QuestionUserResultMap.getString("GRADE_04_VAL"));
							aTbSatisfactionItemVal.setGRADE_05_VAL(QuestionUserResultMap.getString("GRADE_05_VAL"));
							aTbSatisfactionItemVal.setGRADE_AVG_VAL(QuestionUserResultMap.getString("GRADE_AVG_VAL"));
							aSatisfactionItemVals.add(aTbSatisfactionItemVal);
						}
					}

					
					aTbSatisfactionItem.setTbSurveyUserEntryResult(aSatisfactionItemVals);
					

					
					tbSatisfactionItem.add(aTbSatisfactionItem);
				 }
			}

			aTbSatisfaction.setTbSatisfactionItem(tbSatisfactionItem);
			tbSatisfactions.add(aTbSatisfaction);
		}
		/*
		aTbSatisfaction.setSECTION_TITLE("1.진료 대기시간 만족도");
		tbSatisfactionItem = new ArrayList<TbSatisfactionItem>();
		
		 aTbSatisfactionItem = new TbSatisfactionItem();
		aTbSatisfactionItem.setSECTION_ID(1);
		aTbSatisfactionItem.setQUESTION_ID(1);;
		aTbSatisfactionItem.setQUESTION_LABEL("예약시간대비 30분초과 대기");
	
		aSatisfactionItemVals = new ArrayList<TbSurveyUserEntryResult>();
		aTbSatisfactionItemVal =new TbSurveyUserEntryResult();
		aTbSatisfactionItemVal.setSURVEY_YEAR("2018");
		aTbSatisfactionItemVal.setSURVEY_DEGREE("1");
		aTbSatisfactionItemVal.setGRADE_01_VAL("20");
		aTbSatisfactionItemVal.setGRADE_02_VAL("20");
		aTbSatisfactionItemVal.setGRADE_03_VAL("20");
		aTbSatisfactionItemVal.setGRADE_04_VAL("20");
		aTbSatisfactionItemVal.setGRADE_AVG_VAL("74");
		aSatisfactionItemVals.add(aTbSatisfactionItemVal);
		
		aTbSatisfactionItemVal =new TbSurveyUserEntryResult();
		aTbSatisfactionItemVal.setSURVEY_YEAR("2018");
		aTbSatisfactionItemVal.setSURVEY_DEGREE("2");
		aTbSatisfactionItemVal.setGRADE_01_VAL("20");
		aTbSatisfactionItemVal.setGRADE_02_VAL("20");
		aTbSatisfactionItemVal.setGRADE_03_VAL("20");
		aTbSatisfactionItemVal.setGRADE_04_VAL("20");
		aTbSatisfactionItemVal.setGRADE_AVG_VAL("74");
		aSatisfactionItemVals.add(aTbSatisfactionItemVal);

		aTbSatisfactionItemVal =new TbSurveyUserEntryResult();
		aTbSatisfactionItemVal.setSURVEY_YEAR("2019");
		aTbSatisfactionItemVal.setSURVEY_DEGREE("2");
		aTbSatisfactionItemVal.setGRADE_01_VAL("20");
		aTbSatisfactionItemVal.setGRADE_02_VAL("20");
		aTbSatisfactionItemVal.setGRADE_03_VAL("20");
		aTbSatisfactionItemVal.setGRADE_04_VAL("20");
		aTbSatisfactionItemVal.setGRADE_AVG_VAL("74");
		aSatisfactionItemVals.add(aTbSatisfactionItemVal);
	
		aTbSatisfactionItem.setTbSurveyUserEntryResult(aSatisfactionItemVals);
		
		tbSatisfactionItem.add(aTbSatisfactionItem);

		 aTbSatisfactionItem = new TbSatisfactionItem();
		 aTbSatisfactionItem.setSECTION_ID(1);
		 aTbSatisfactionItem.setQUESTION_ID(2);;
		 aTbSatisfactionItem.setQUESTION_LABEL("대기시간 공지수준");
		 
		 tbSatisfactionItem.add(aTbSatisfactionItem);

		 aTbSatisfactionItem = new TbSatisfactionItem();
		 aTbSatisfactionItem.setSECTION_ID(1);
		 aTbSatisfactionItem.setQUESTION_ID(3);;
		 aTbSatisfactionItem.setQUESTION_LABEL("대기사유 공지수준");
		 tbSatisfactionItem.add(aTbSatisfactionItem);
		
		 aTbSatisfaction.setTbSatisfactionItem(tbSatisfactionItem);
		 tbSatisfactions.add(aTbSatisfaction);
		 
		 aTbSatisfaction =new TbSatisfaction();
		 aTbSatisfaction.setSECTION_TITLE("2.의사진료서비스 만족도");
		 tbSatisfactionItem = new ArrayList<TbSatisfactionItem>();
			
		 aTbSatisfactionItem = new TbSatisfactionItem();
		 aTbSatisfactionItem.setSECTION_ID(2);
		 aTbSatisfactionItem.setQUESTION_ID(1);;
		 aTbSatisfactionItem.setQUESTION_LABEL("진료시간 할애수준");
		 tbSatisfactionItem.add(aTbSatisfactionItem);

		aTbSatisfactionItem = new TbSatisfactionItem();
		aTbSatisfactionItem.setSECTION_ID(2);
		aTbSatisfactionItem.setQUESTION_ID(2);;
		aTbSatisfactionItem.setQUESTION_LABEL("설명이해 용어수준");
		tbSatisfactionItem.add(aTbSatisfactionItem);

		aTbSatisfactionItem = new TbSatisfactionItem();
		aTbSatisfactionItem.setSECTION_ID(2);
		aTbSatisfactionItem.setQUESTION_ID(3);;
		aTbSatisfactionItem.setQUESTION_LABEL("환자설명 경청수준");
		tbSatisfactionItem.add(aTbSatisfactionItem);

		aTbSatisfactionItem = new TbSatisfactionItem();
		aTbSatisfactionItem.setSECTION_ID(2);
		aTbSatisfactionItem.setQUESTION_ID(4);;
		aTbSatisfactionItem.setQUESTION_LABEL("환자응대태도");
		tbSatisfactionItem.add(aTbSatisfactionItem);

		aTbSatisfactionItem = new TbSatisfactionItem();
		aTbSatisfactionItem.setSECTION_ID(2);
		aTbSatisfactionItem.setQUESTION_ID(5);;
		aTbSatisfactionItem.setQUESTION_LABEL("환자응대태도1");
		tbSatisfactionItem.add(aTbSatisfactionItem);
		
		aTbSatisfaction.setTbSatisfactionItem(tbSatisfactionItem);
		tbSatisfactions.add(aTbSatisfaction);
		 */
		 
		model.addAttribute("param", param);
		model.addAttribute("graphparam", graphparam);
		model.addAttribute("surveyCds", SurveyCdS);
		model.addAttribute("tbSatisfactions", tbSatisfactions);
		
		
		return "/mgt/survey/statisticsCd2Entry";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: exportItems
	 * 2. ClassName  	: SurveyUserController
	 * 3. Comment   		: 설문항목지정_레포트용
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 05. 11 15:59
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/survey/exportItems.do")
	public String exportItems(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		//리스트 조회
		TbSurvey survey = surveyService.selectSurvey(param);

		//질문리스트
		List<DataMap> SectionList   = surveyService.selectListSection( param);
		List<DataMap> QuestionList = surveyService.selectListQuestion( param);
		List<DataMap> QuestionLableList = surveyService.selectListQuestionLabel( param);

		TbSectionUser aTbSectionUser = new TbSectionUser();
		aTbSectionUser.setSURVEY_ID(param.getString("surveyId"));
		aTbSectionUser.setUSER_NO(param.getInt("userNo"));
		aTbSectionUser.setSURVEY_GROUP_ID(param.getInt("surveyGroupId"));
		aTbSectionUser.setSURVEY_YEAR(param.getString("surveyYear"));
		aTbSectionUser.setSURVEY_DEGREE(param.getString("surveyDegree"));

		TbQuestionUser aTbQuestionUser = new TbQuestionUser();
		aTbQuestionUser.setSURVEY_ID(param.getString("surveyId"));
		aTbQuestionUser.setUSER_NO(param.getInt("userNo"));
		aTbQuestionUser.setSURVEY_GROUP_ID(param.getInt("surveyGroupId"));
		aTbQuestionUser.setSURVEY_YEAR(param.getString("surveyYear"));
		aTbQuestionUser.setSURVEY_DEGREE(param.getString("surveyDegree"));

		List<DataMap> SectionUserList   = surveyUserService.selectListUserSection( aTbSectionUser);
		List<DataMap> QuestionUserList = surveyUserService.selectListUserQuestion( aTbQuestionUser);

		model.addAttribute("param", param);
		model.addAttribute("survey", survey);
		model.addAttribute("SectionList", SectionList);
		model.addAttribute("QuestionList", QuestionList);
		model.addAttribute("QuestionLableList", QuestionLableList);
		model.addAttribute("SectionUserList", SectionUserList);
		model.addAttribute("QuestionUserList", QuestionUserList);
		
		return "/mgt/survey/exportItems";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: exportItems_ok
	 * 2. ClassName  	: SurveyUserController
	 * 3. Comment   		: 설문항목지정_레포트용
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 05. 11 15:59
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/survey/exportItems_ok.do")
	public String exportItems_ok(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		

		
		//리스트 조회
		TbSurvey survey = surveyService.selectSurvey(param);

		int sectionNr=0;
		int questionNr=0;
		int questionCnt=0;
		int sectionCnt=0;
		
		//섹션등록
		TbSectionUser aTbSection = null;
		List<DataMap> SectionList = surveyService.selectListSection( param);
		for(DataMap aSection:SectionList){
			sectionNr=aSection.getInt("SECTION_ID");

			String sectioninclude = (String) request.getParameter ( "sectioninclude_"+sectionNr );
			String rsectionTitle = (String) request.getParameter ( "section_"+sectionNr);
			String rsectionCont = (String) request.getParameter ( "section_cont_"+sectionNr);
			String rsectionEnd = (String) request.getParameter ( "section_end_"+sectionNr);

			
			aTbSection = new TbSectionUser();
			aTbSection.setSURVEY_ID(param.getString("surveyId"));
			aTbSection.setUSER_NO(param.getInt("USER_NO"));
			aTbSection.setSURVEY_GROUP_ID(param.getInt("SURVEY_GROUP_ID"));
			aTbSection.setSURVEY_YEAR(param.getString("SURVEY_YEAR"));
			aTbSection.setSURVEY_DEGREE(param.getString("SURVEY_DEGREE"));
			aTbSection.setSECTION_ID(sectionNr);
			aTbSection.setSECTION_RTITLE(rsectionTitle);
			aTbSection.setSECTION_CONT(rsectionCont);
			aTbSection.setSECTION_END(rsectionEnd);

		     if ( sectioninclude != null && sectioninclude.equals("1") ) { aTbSection.setEXPORTINCLUDER("1"); }
		     else {  aTbSection.setEXPORTINCLUDER("0"); }

		     sectionCnt=surveyUserService.selectCntUserSection(aTbSection);
		     
		     if(sectionCnt<1) surveyUserService.insertUserSection(aTbSection);
		     else surveyUserService.updateUserSection(aTbSection);
			
			
		}
		//짊문등록
		TbQuestionUser aTbQuestion = null;
		List<DataMap> QuestionList = surveyService.selectListQuestion( param);
		for(DataMap aQuestion:QuestionList){
			
			sectionNr=aQuestion.getInt("SECTION_ID");
			questionNr=aQuestion.getInt("QUESTION_ID");
			String exportInclude = (String) request.getParameter ( "include_"+sectionNr+"_" + questionNr );
			String rquestion = (String) request.getParameter ( "rquestion_"+sectionNr+"_" + questionNr );
			
			aTbQuestion = new TbQuestionUser();
			aTbQuestion.setSURVEY_ID(param.getString("surveyId"));
			aTbQuestion.setUSER_NO(param.getInt("USER_NO"));
			aTbQuestion.setSURVEY_GROUP_ID(param.getInt("SURVEY_GROUP_ID"));
			aTbQuestion.setSURVEY_YEAR(param.getString("SURVEY_YEAR"));
			aTbQuestion.setSURVEY_DEGREE(param.getString("SURVEY_DEGREE"));
			aTbQuestion.setSECTION_ID(sectionNr);
			aTbQuestion.setQUESTION_ID(questionNr);
		    aTbQuestion.setQUESTION_RTITLE(rquestion);
		    
		     if ( exportInclude != null && exportInclude.equals("1") ) { aTbQuestion.setEXPORTINCLUDER("1"); }
		     else {  aTbQuestion.setEXPORTINCLUDER("0"); }

		     questionCnt=surveyUserService.selectCntUserQuestion(aTbQuestion);
		     
		     if(questionCnt<1) surveyUserService.insertUserQuestion(aTbQuestion);
		     else surveyUserService.updateUserQuestion(aTbQuestion);
			
		}


		model.addAttribute("param", param);
		model.addAttribute("survey", survey);
		
		return "redirect:/mgt/survey/exportItems.do?surveyId="+param.getString("surveyId")+"&surveyMode=0&userNo="+param.getString("USER_NO")+"&surveyGroupId="+param.getString("SURVEY_GROUP_ID")+"&surveyYear="+param.getString("SURVEY_YEAR")+"&surveyDegree="+param.getString("SURVEY_DEGREE");	

	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: userSurveyMenu
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	    : 설문 관리
	 * 4. 작성자    	        : SooHyun.Seo
	 * 5. 작성일    	        : 2019. 05. 11 15:59
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/survey/userSurveyMenu.do")
	public String userSurveyMenu(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());

		//설문상세
		TbSurvey resultMap = surveyService.selectSurvey(param);

		TbSurveyUser aTbSurveyUser=null;
		DataMap inParam = new DataMap();
		inParam.put("SURVEY_ID",param.getString("surveyId"));
		inParam.put("USER_NO",param.getString("userNo"));
		inParam.put("SURVEY_GROUP_ID",param.getString("surveyGroupId"));
		inParam.put("SURVEY_YEAR",param.getString("surveyYear"));
		inParam.put("SURVEY_DEGREE",param.getString("surveyDegree"));
		aTbSurveyUser=surveyUserService.selectUserGroupSurvey(inParam);

		
		
		model.addAttribute("survey", resultMap);
		model.addAttribute("surveyUser", aTbSurveyUser);
		model.addAttribute("param", param);
		
		
		return "/mgt/survey/userSurveyMenu";
	}
	
}
