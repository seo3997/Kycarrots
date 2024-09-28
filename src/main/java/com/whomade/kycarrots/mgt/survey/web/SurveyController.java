package com.whomade.kycarrots.mgt.survey.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.vt.ward.survey.DateUtil;
import edu.vt.ward.survey.Question;
import edu.vt.ward.survey.iSurveyEntryForm;
import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.common.service.CommonCodeService;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.SessionUtil;
import com.whomade.kycarrots.framework.common.util.StringUtil;
import com.whomade.kycarrots.mgt.survey.dto.QuestionDragDto;
import com.whomade.kycarrots.mgt.survey.dto.QuestionPosition;
import com.whomade.kycarrots.mgt.survey.dto.SectionDragDto;
import com.whomade.kycarrots.mgt.survey.dto.SectionPosition;
import com.whomade.kycarrots.mgt.survey.service.SurveyService;
import com.whomade.kycarrots.mgt.survey.service.SurveyUserService;
import com.whomade.kycarrots.mgt.survey.vo.TbQuestion;
import com.whomade.kycarrots.mgt.survey.vo.TbQuestionLabel;
import com.whomade.kycarrots.mgt.survey.vo.TbResults;
import com.whomade.kycarrots.mgt.survey.vo.TbResultsLabel;
import com.whomade.kycarrots.mgt.survey.vo.TbSection;
import com.whomade.kycarrots.mgt.survey.vo.TbSurvey;
import com.whomade.kycarrots.mgt.survey.vo.TbSurveyUser;
import com.whomade.kycarrots.mgt.survey.vo.TbSurveyUserEntry;
import net.sf.json.JSONObject;

@Controller
public class SurveyController {

	private static Log log = LogFactory.getLog(SurveyController.class);

	@Resource(name = "egovMessageSource")
	private EgovMessageSource egovMessageSource;

	@Resource(name = "surveyService")
	private SurveyService surveyService;

	@Resource(name = "surveyUserService")
	private SurveyUserService surveyUserService;

	/** CommonCodeService */
	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;

	/**
	 * <PRE>
	 * 1. MethodName 	: selectPageListSurvey
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문 리스트
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
	@RequestMapping(value = "/mgt/survey/selectPageListSurvey.do")
	public String selectPageListSurvey(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		//리스트 조회
		List<DataMap> resultList = surveyService.selectPageListSurvey(model, param);

		model.addAttribute("resultList", resultList);
		model.addAttribute("param", param);
		
		return "/mgt/survey/selectPageListSurvey";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: manageSurveyMenu
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문 관리
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
	@RequestMapping(value = "/mgt/survey/manageSurveyMenu.do")
	public String manageSurveyMenu(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());

		//설문상세
		TbSurvey resultMap = surveyService.selectSurvey(param);
		System.out.println("resultMap"+resultMap);
		model.addAttribute("survey", resultMap);
		model.addAttribute("param", param);
		
		return "/mgt/survey/manageSurveyMenu";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: editEntryForm
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 입력양식편집
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
	@RequestMapping(value = "/mgt/survey/editEntryForm.do")
	public String editEntryForm(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("userNo", userInfoVo.getUserNo());
		
		String surveyId 	= param.getString("surveyId");
		String surveyModeName 	= "Entry";
		//리스트 조회

		TbSurvey survey = surveyService.selectSurvey(param);

		TbSection section = null;
		TbQuestion question = new TbQuestion(); 
		
		int iSectionCnt  = surveyService.selectExistSurvey(surveyId);

		if(iSectionCnt<1){
			section = new TbSection();
			//섹션저장
			section.setSURVEY_ID(surveyId);
			section.setSECTION_ID(0);
			section.setSECTION_TITLE("sec0");
			surveyService.insertSection(section);
			//comment저장
			question = new TbQuestion();
			question.setSURVEY_ID(surveyId);
			question.setSECTION_ID(0);
			question.setQUESTION_TYPE("inputComment");
			question.setQUESTIONTEXT(survey.getSURVEY_TITLE() );
			question.setQUESTION_ISHTML("1");
			question.setSHOWDIVIDER("0");
			surveyService.insertQuestion(question);
		}
		//질문리스트
		List<DataMap> SectionList   = surveyService.selectListSection( param);
		List<DataMap> QuestionList = surveyService.selectListQuestion( param);
		List<DataMap> QuestionLableList = surveyService.selectListQuestionLabel( param);
		

		model.addAttribute("param", param);
		model.addAttribute("survey", survey);
		model.addAttribute("SectionList", SectionList);
		model.addAttribute("QuestionList", QuestionList);
		model.addAttribute("QuestionLableList", QuestionLableList);
		
		return "/mgt/survey/editEntryForm";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: editEntryForm
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 입력양식편집
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
	@RequestMapping(value = "/mgt/survey/editEntryJsonForm.do")
	public String editEntryJsonForm(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("userNo", userInfoVo.getUserNo());
		
		String surveyId 	= param.getString("surveyId");
		String surveyModeName 	= "Entry";
		//리스트 조회

		TbSurvey survey = surveyService.selectSurvey(param);

		TbSection section = null;
		TbQuestion question = new TbQuestion(); 
		
		int iSectionCnt  = surveyService.selectExistSurvey(surveyId);

		if(iSectionCnt<1){
			section = new TbSection();
			//섹션저장
			section.setSURVEY_ID(surveyId);
			section.setSECTION_ID(0);
			section.setSECTION_TITLE("sec0");
			surveyService.insertSection(section);
			//comment저장
			question = new TbQuestion();
			question.setSURVEY_ID(surveyId);
			question.setSECTION_ID(0);
			question.setQUESTION_TYPE("inputComment");
			question.setQUESTIONTEXT(survey.getSURVEY_TITLE() );
			question.setQUESTION_ISHTML("1");
			question.setSHOWDIVIDER("0");
			surveyService.insertQuestion(question);
		}
		//질문리스트
		List<DataMap> SectionList   = surveyService.selectListSection( param);
		List<DataMap> QuestionList = surveyService.selectListQuestion( param);
		List<DataMap> QuestionLableList = surveyService.selectListQuestionLabel( param);
		

		model.addAttribute("param", param);
		model.addAttribute("survey", survey);
		model.addAttribute("SectionList", SectionList);
		model.addAttribute("QuestionList", QuestionList);
		model.addAttribute("QuestionLableList", QuestionLableList);
		
		return "/mgt/survey/editEntryJsonForm";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: icreateNewSurveyForm
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문추가폼 호출
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
	@RequestMapping(value = "/mgt/survey/icreateNewSurveyForm.do")
	public String icreateNewSurveyForm(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		model.addAttribute("param", param);
		
		return "/mgt/survey/icreateNewSurveyForm";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: addQuestion
	 * 2. ClassName  	: SurveyController
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
	@RequestMapping(value = "/mgt/survey/addQuestion.do")
	public String addQuestion(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		model.addAttribute("param", param);
		
		return "/mgt/survey/addQuestion";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: editQuestion
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문항목편집
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
	@RequestMapping(value = "/mgt/survey/editQuestion.do")
	public String editQuestion(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		System.out.println("type["+param.getString("type")+"]");
		TbQuestion tbQuestion = null;
		
		if(param.getString("type").equals("")){
			tbQuestion = surveyService.selectQuestion(param);
			List<DataMap> QuestionLables= surveyService.selectQuestionLabel( param);
			
			if(tbQuestion.getQUESTION_TYPE().equals("inputRadio") || tbQuestion.getQUESTION_TYPE().equals("inputCheckbox") ){
				ArrayList<TbQuestionLabel> tbQuestionLabels = new ArrayList<TbQuestionLabel>();
				for(DataMap aDataMap : QuestionLables){
					TbQuestionLabel tbQuestionLabel = new TbQuestionLabel();
					tbQuestionLabel.setSURVEY_ID(aDataMap.getString("SURVEY_ID"));
					tbQuestionLabel.setSECTION_ID(aDataMap.getInt("SECTION_ID"));
					tbQuestionLabel.setQUESTION_ID(aDataMap.getInt("QUESTION_ID"));
					tbQuestionLabel.setQUESTION_LABEL_ID(aDataMap.getInt("QUESTION_LABEL_ID"));
					tbQuestionLabel.setQUESTION_LABEL(aDataMap.getString("QUESTION_LABEL"));
					tbQuestionLabel.setEXPORTCODE(aDataMap.getString("EXPORTCODE"));
					tbQuestionLabel.setSELECTED(aDataMap.getString("SELECTED"));
					tbQuestionLabels.add(tbQuestionLabel);
				}
				tbQuestion.setQUESTIONLABELS(tbQuestionLabels);
			}
			
		}
		System.out.println("tbQuestion["+tbQuestion+"]");
		
		model.addAttribute("tbQuestion", tbQuestion);
		model.addAttribute("param", param);
		
		return "/mgt/survey/editQuestion";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: surveyeditQuestion_ok
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문항목편집 완료
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
	@RequestMapping(value = "/mgt/survey/editQuestion_ok.do")
	public String editQuestion_ok(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		String surveyId 	= param.getString("surveyId");
		int section 	= param.getInt("section");
		String type 	= param.getString("type");
		String above 	= param.getString("above");
		String mode 	=null;

		System.out.println("cancel["+param.getString("cancel")+"]");
		
		if(param.getString("cancel").equals("Cancel")){
			return "redirect:/mgt/survey/editEntryForm.do?surveyId="+surveyId;	
		}
		
		if(!type.equals("")) mode="insert";
		else mode="update";
		System.out.println("surveyId["+surveyId+"]");
		System.out.println("section["+section+"]");
		System.out.println("type["+type+"]");

		//입력항목저장
	    //entryForm.getSection ( sectionNr ).addQuestion ( questionNr, Question.getNewQuestion (surveyId, type ) );
	    //inputErrorId = entryForm.getSection ( sectionNr ).getQuestion ( questionNr ).makeEditFormChanges ( request );
		
		int QuestionId=0;
		if(mode.equals("update")){																
			TbQuestion aQuestion = surveyService.selectQuestion(param);
			if(aQuestion!=null && !aQuestion.getQUESTION_TYPE().equals("") )
			type=StringUtil.getQuestionType(aQuestion.getQUESTION_TYPE());
			System.out.println("update type["+type+"]");
		}		
		
		Question question=Question.getNewQuestion (surveyId,type);
		question.makeEditFormChanges(request);
		
		TbQuestion tbQuestion=question.getTbQuestion(1);
		tbQuestion.setSURVEY_ID(surveyId);
		tbQuestion.setSECTION_ID(section);
		tbQuestion.setQUESTIONTEXT(question.getText());
		
		if(mode.equals("insert")){																//insert
			if(!StringUtil.isEmpty(above) && above.equals("Y")){  					//question above
				QuestionId=param.getInt("question");	
				
			   DataMap upParam = new DataMap();
			   upParam.put("SURVEY_ID", surveyId);
			   upParam.put("SECTION_ID", section);
			   upParam.put("QUESTION_ID", QuestionId);
			   System.out.println("section["+section+"]");
			   System.out.println("QuestionId["+QuestionId+"]");
			   surveyService.updateQuestionAbove(upParam);
			   surveyService.updateQuestionLabelAbove(upParam);
				
			}else{																						//신규
				QuestionId=surveyService.selectQuestionId(tbQuestion);
			}
			tbQuestion.setQUESTION_ID(QuestionId);
			surveyService.insertQuestion(tbQuestion);

		}else{																				//update
			QuestionId=param.getInt("question");
			tbQuestion.setQUESTION_ID(QuestionId);
			surveyService.updateQuestion(tbQuestion);
		}
		
		//옵션리스트 저장
		if(tbQuestion.getQUESTION_TYPE().equals("inputRadio") || tbQuestion.getQUESTION_TYPE().equals("inputCheckbox") ){
			if(mode.equals("update")){
				TbQuestionLabel aQuestionLabel = new TbQuestionLabel();
				aQuestionLabel.setSURVEY_ID(surveyId);
				aQuestionLabel.setSECTION_ID(section);
				aQuestionLabel.setQUESTION_ID(QuestionId);
				surveyService.deleteQuestionLabel(aQuestionLabel);
			}
			for(TbQuestionLabel tbQuestionLabel : tbQuestion.getQUESTIONLABELS()){
				tbQuestionLabel.setSURVEY_ID(surveyId);
				tbQuestionLabel.setSECTION_ID(section);
				tbQuestionLabel.setQUESTION_ID(QuestionId);
				surveyService.insertQuestionLabel(tbQuestionLabel);
			}
		}
		
		System.out.println("tbQuestion.getQUESTIONTEXT["+tbQuestion.getQUESTIONTEXT()+"]");
		System.out.println("tbQuestion.getQUESTION_LABEL()["+tbQuestion.getQUESTION_LABEL()+"]");

		
		
		
		model.addAttribute("param", param);

		
		return "redirect:/mgt/survey/editEntryForm.do?surveyId="+surveyId;	
		//return "/mgt/survey/editQuestion_ok";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: savePlanFAjax
	 * 2. ClassName  	: FrontLoginController
	 * 3. Comment   	: planF저장
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2021. 08. 07. 오후 4:09:06
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/mgt/survey/editQuestionAjax.do")
	public void editQuestionAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataMap param 	= RequestUtil.getDataMap(request);
		String surveyId = param.getString("surveyId");
		int sectionId 	= param.getInt("section");
		int questionId 	= param.getInt("question");
		String type 	= param.getString("type");
		String mode 	= param.getString("mode");

		
		System.out.println("surveyId:"+surveyId);
		System.out.println("sectionId:"+sectionId);
		System.out.println("questionId:"+questionId);
		
		if(mode.equals("")) mode="insert";

		
		int QuestionId=0;
		type=StringUtil.getQuestionType(type);
		
		Question question=Question.getNewQuestion (surveyId,type);
		question.makeEditFormChanges(request);
		
		TbQuestion tbQuestion=question.getTbQuestion(1);
		tbQuestion.setSURVEY_ID(surveyId);
		tbQuestion.setSECTION_ID(sectionId);
		tbQuestion.setQUESTIONTEXT(question.getText());

		//question 저장트랜잭션 관리 필요
		tbQuestion =surveyService.processQuestion(mode,tbQuestion,surveyId,sectionId,questionId);
		
		//저장하고 난다음 문항 html 가져오기
		tbQuestion.setQUESTION_TYPETEXT_HTML(question.getQuestionTypeTextHTML());
		tbQuestion.setHTML_NEW(question.getHTML_New(null,1,tbQuestion.getSECTION_ID(), tbQuestion.getQUESTION_ID()));

		
		model.addAttribute("param", param);
		JSONObject resultJSON = new JSONObject();
		String returnMsg = "";
		DataMap resultStats = new DataMap();
       		
		//return 상태
		returnMsg="저장되었습니다.";
		resultStats.put("resultCode", "ok");
		resultStats.put("resultMsg",  returnMsg);
		resultJSON.put("QUESTION", tbQuestion);
		try {
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
		
	}	

	@ResponseBody
	@RequestMapping(value = "/mgt/survey/dragQuestion", method = RequestMethod.POST, consumes = "application/json")
	public void dragQuestion(@RequestBody QuestionDragDto questionDragDto,HttpServletResponse response, ModelMap model) {

	    try {
			surveyService.updateQuestionPositions(questionDragDto);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
		JSONObject resultJSON = new JSONObject();
		String returnMsg = "";
		DataMap resultStats = new DataMap();
       		
		//return 상태
		returnMsg="저장되었습니다.";
		resultStats.put("resultCode", "ok");
		resultStats.put("resultMsg",  returnMsg);
		resultJSON.put("QUESTIONS", questionDragDto);
		try {
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/mgt/survey/dragSection", method = RequestMethod.POST, consumes = "application/json")
	public void dragSection(@RequestBody SectionDragDto sectionDragDto,HttpServletResponse response, ModelMap model) {

	    try {
			surveyService.updateSectionPositions(sectionDragDto);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JSONObject resultJSON = new JSONObject();
		String returnMsg = "";
		DataMap resultStats = new DataMap();
       		
		//return 상태
		returnMsg="저장되었습니다.";
		resultStats.put("resultCode", "ok");
		resultStats.put("resultMsg",  returnMsg);
		resultJSON.put("SECTION", sectionDragDto);
		try {
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
	}	
	
	@ResponseBody
	@RequestMapping(value = "/mgt/survey/test.do", method = RequestMethod.POST, produces="application/json")
	public void test(@RequestBody  HashMap<String, Object> payload,HttpServletResponse response) {
		System.out.println("******************************test**********************************");
		System.out.println("payload:"+payload);
	    // {name=vita, age=25} 출력
		
		JSONObject resultJSON = new JSONObject();
		String returnMsg = "";
		DataMap resultStats = new DataMap();

		
		//return 상태
		returnMsg="저장되었습니다.";
		resultStats.put("resultCode", "ok");
		resultStats.put("resultMsg",  returnMsg);
		resultJSON.put("payload", payload);
		try {
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
	}	

	
	/**
	 * <PRE>
	 * 1. MethodName 	: icreateNewSurvey
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문추가
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
	@RequestMapping(value = "/mgt/survey/icreateNewSurvey.do")
	public String icreateNewSurvey(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataMap param = RequestUtil.getDataMap(request);
		TbSurvey aTbSurvey = new TbSurvey();
		
		Date date = new Date ();
		String newSurveyId = String.valueOf ( date.getTime() );
		aTbSurvey.setSURVEY_ID(newSurveyId);
		aTbSurvey.setSURVEY_TITLE(param.getString("surveyName"));
		surveyService.insertSurvey(aTbSurvey);
		
		model.addAttribute("param", param);
		return "/mgt/survey/icreateNewSurvey";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: iupQuestion
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문항목Up
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
	@RequestMapping(value = "/mgt/survey/iupQuestion.do")
	public String iupQuestion(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		String surveyId 	= param.getString("surveyId");

		  int sectionNr = param.getInt( "section" );
		  int questionNr = param.getInt( "question" );
		  int newSectionNr = sectionNr;
		  int newQuestionNr = questionNr;

		  DataMap inParam = new DataMap();
		  inParam.put("surveyId", surveyId);
		  inParam.put("section", sectionNr);
		  inParam.put("question", questionNr);
		  
		  if ( ! (sectionNr == 0 && questionNr == 0 ) ) {

		    TbQuestion tbQuestion = surveyService.selectQuestion(inParam);
			List<DataMap> QuestionLables= surveyService.selectQuestionLabel( inParam);
			
			//현재 설문 delete 	
			surveyService.deleteQuestion(tbQuestion);
			TbQuestionLabel aQuestionLabel = new TbQuestionLabel();
			aQuestionLabel.setSURVEY_ID(surveyId);
			aQuestionLabel.setSECTION_ID(sectionNr);
			aQuestionLabel.setQUESTION_ID(questionNr);
			surveyService.deleteQuestionLabel(aQuestionLabel);

			
		    if ( questionNr > 0 ) {
		      newSectionNr = sectionNr;
		      //newQuestionNr = questionNr - 1;
		      newQuestionNr=surveyService.selectUpQuestionId(inParam);
		    }
		    else { // transfer question to previous section
		      newSectionNr = sectionNr - 1;
		      newQuestionNr =0;			//section 나중에 함
		    }

		    //Up Move 설문 	
		   DataMap upParam = new DataMap();
		   upParam.put("SURVEY_ID", surveyId);
		   upParam.put("SECTION_ID", sectionNr);
		   upParam.put("QUESTION_ID", questionNr);
		   upParam.put("newSectionNr", newSectionNr);
		   upParam.put("newQuestionNr", newQuestionNr);
		   
		   
		   System.out.println("upParam["+upParam+"]");
		   System.out.println("upParam["+upParam.getString("SURVEY_ID")+"]");
		   
		  surveyService.updateQuestionUpDown(upParam);
		  surveyService.updateQuestionLabelUpDown(upParam);

		   //설문 Up
		   System.out.println("newSectionNr["+newSectionNr+"]");
		   System.out.println("newQuestionNr["+newQuestionNr+"]");

		  tbQuestion.setSECTION_ID(newSectionNr);
		   tbQuestion.setQUESTION_ID(newQuestionNr);
		   surveyService.insertQuestion(tbQuestion);

			 ArrayList<TbQuestionLabel> tbQuestionLabels = new ArrayList<TbQuestionLabel>();
			for(DataMap aDataMap : QuestionLables){
				TbQuestionLabel tbQuestionLabel = new TbQuestionLabel();
				tbQuestionLabel.setSURVEY_ID(aDataMap.getString("SURVEY_ID"));
				tbQuestionLabel.setSECTION_ID(newSectionNr);
				tbQuestionLabel.setQUESTION_ID(newQuestionNr);
				tbQuestionLabel.setQUESTION_LABEL_ID(aDataMap.getInt("QUESTION_LABEL_ID"));
				tbQuestionLabel.setQUESTION_LABEL(aDataMap.getString("QUESTION_LABEL"));
				tbQuestionLabel.setEXPORTCODE(aDataMap.getString("EXPORTCODE"));
				tbQuestionLabel.setSELECTED(aDataMap.getString("SELECTED"));
				surveyService.insertQuestionLabel(tbQuestionLabel);
			}
			
		  }
		
		return "redirect:/mgt/survey/editEntryForm.do?surveyId="+surveyId;	
	}
	/**
	 * <PRE>
	 * 1. MethodName 	: idownQuestion
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문항목Down
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
	@RequestMapping(value = "/mgt/survey/idownQuestion.do")
	public String idownQuestion(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		String surveyId 	= param.getString("surveyId");

		  int sectionNr = param.getInt( "section" );
		  int questionNr = param.getInt( "question" );
		  int newSectionNr = sectionNr;
		  int newQuestionNr = questionNr;
		  
		  int QuestionCnt=0;
		  int QuestionMax=0;
		  int SectionCnt=0;
		  

		  DataMap inParam = new DataMap();
		  inParam.put("surveyId", surveyId);
		  inParam.put("section", sectionNr);
		  inParam.put("question", questionNr);
		  
		  
		   SectionCnt=surveyService.selectSectionCnt(inParam);
		   QuestionCnt=surveyService.selectSectionQuestionCnt(inParam);
		   QuestionMax=surveyService.selectSectionQuestionMax(inParam);
	
		   System.out.println("QuestionCnt["+QuestionCnt+"]");
		   System.out.println("SectionCnt["+SectionCnt+"]");

		  
		   if ( sectionNr >0 ||  QuestionCnt>0 ) {

			    TbQuestion tbQuestion = surveyService.selectQuestion(inParam);
				List<DataMap> QuestionLables= surveyService.selectQuestionLabel( inParam);
				
				//현재 설문 delete 	
				surveyService.deleteQuestion(tbQuestion);
				TbQuestionLabel aQuestionLabel = new TbQuestionLabel();
				aQuestionLabel.setSURVEY_ID(surveyId);
				aQuestionLabel.setSECTION_ID(sectionNr);
				aQuestionLabel.setQUESTION_ID(questionNr);
				surveyService.deleteQuestionLabel(aQuestionLabel);

				 if ( questionNr < QuestionMax ) {
			      newSectionNr = sectionNr;
			      //newQuestionNr = questionNr + 1;
			      newQuestionNr=surveyService.selectDownQuestionId(inParam);
			    }
			    else { //transfer question to next section
			        newSectionNr = sectionNr + 1;
			        newQuestionNr = 0;
			    }

			    //Up Move 설문 	
			   DataMap upParam = new DataMap();
			   upParam.put("SURVEY_ID", surveyId);
			   upParam.put("SECTION_ID", sectionNr);
			   upParam.put("QUESTION_ID", questionNr);
			   upParam.put("newSectionNr", newSectionNr);
			   upParam.put("newQuestionNr", newQuestionNr);
			   
			   surveyService.updateQuestionUpDown(upParam);
			   surveyService.updateQuestionLabelUpDown(upParam);
	
			   //설문 Up
			   System.out.println("newSectionNr["+newSectionNr+"]");
			   System.out.println("newQuestionNr["+newQuestionNr+"]");
	
			   tbQuestion.setSECTION_ID(newSectionNr);
			   tbQuestion.setQUESTION_ID(newQuestionNr);
			   surveyService.insertQuestion(tbQuestion);
	
				ArrayList<TbQuestionLabel> tbQuestionLabels = new ArrayList<TbQuestionLabel>();
				for(DataMap aDataMap : QuestionLables){
					TbQuestionLabel tbQuestionLabel = new TbQuestionLabel();
					tbQuestionLabel.setSURVEY_ID(aDataMap.getString("SURVEY_ID"));
					tbQuestionLabel.setSECTION_ID(newSectionNr);
					tbQuestionLabel.setQUESTION_ID(newQuestionNr);
					tbQuestionLabel.setQUESTION_LABEL_ID(aDataMap.getInt("QUESTION_LABEL_ID"));
					tbQuestionLabel.setQUESTION_LABEL(aDataMap.getString("QUESTION_LABEL"));
					tbQuestionLabel.setEXPORTCODE(aDataMap.getString("EXPORTCODE"));
					tbQuestionLabel.setSELECTED(aDataMap.getString("SELECTED"));
					surveyService.insertQuestionLabel(tbQuestionLabel);
				}
			
		  }
		
		return "redirect:/mgt/survey/editEntryForm.do?surveyId="+surveyId;	
	}

	
	/**
	 * <PRE>
	 * 1. MethodName 	: icopyQuestion
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문항목Copy
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
	@RequestMapping(value = "/mgt/survey/icopyQuestion.do")
	public String icopyQuestion(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		String surveyId 	= param.getString("surveyId");

		  int sectionNr = param.getInt( "section" );
		  int questionNr = param.getInt( "question" );
		  int newSectionNr = sectionNr;
		  int newQuestionNr = questionNr;
		  
		  int QuestionCnt=0;
		  int QuestionMax=0;
		  int SectionCnt=0;
		  

		  DataMap inParam = new DataMap();
		  inParam.put("surveyId", surveyId);
		  inParam.put("section", sectionNr);
		  inParam.put("question", questionNr);
		  
		  
		   SectionCnt=surveyService.selectSectionCnt(inParam);
		   QuestionCnt=surveyService.selectSectionQuestionCnt(inParam);
		   QuestionMax=surveyService.selectSectionQuestionMax(inParam);
	
		   System.out.println("QuestionCnt["+QuestionCnt+"]");
		   System.out.println("SectionCnt["+SectionCnt+"]");

		   if ( sectionNr >= 0 && sectionNr < SectionCnt && questionNr >= 0 && questionNr < QuestionCnt ) {

			    TbQuestion tbQuestion = surveyService.selectQuestion(inParam);
				List<DataMap> QuestionLables= surveyService.selectQuestionLabel( inParam);

			    //Up Move 설문 	
			   DataMap upParam = new DataMap();
			   upParam.put("SURVEY_ID", surveyId);
			   upParam.put("SECTION_ID", sectionNr);
			   upParam.put("QUESTION_ID", questionNr);

			   System.out.println("sectionNr["+sectionNr+"]");
			   System.out.println("questionNr["+questionNr+"]");

			   
			   surveyService.updateQuestionCopy(upParam);
			   surveyService.updateQuestionLabelCopy(upParam);
	
			   newQuestionNr = questionNr+1;
			   tbQuestion.setSECTION_ID(newSectionNr);
			   tbQuestion.setQUESTION_ID(newQuestionNr);
			   tbQuestion.setQUESTIONTEXT("Copy of..."+tbQuestion.getQUESTIONTEXT());
			   
			  
			   
			   surveyService.insertQuestion(tbQuestion);
	
				ArrayList<TbQuestionLabel> tbQuestionLabels = new ArrayList<TbQuestionLabel>();
				for(DataMap aDataMap : QuestionLables){
					TbQuestionLabel tbQuestionLabel = new TbQuestionLabel();
					tbQuestionLabel.setSURVEY_ID(aDataMap.getString("SURVEY_ID"));
					tbQuestionLabel.setSECTION_ID(newSectionNr);
					tbQuestionLabel.setQUESTION_ID(newQuestionNr);
					tbQuestionLabel.setQUESTION_LABEL_ID(aDataMap.getInt("QUESTION_LABEL_ID"));
					tbQuestionLabel.setQUESTION_LABEL(aDataMap.getString("QUESTION_LABEL"));
					tbQuestionLabel.setEXPORTCODE(aDataMap.getString("EXPORTCODE"));
					tbQuestionLabel.setSELECTED(aDataMap.getString("SELECTED"));
					surveyService.insertQuestionLabel(tbQuestionLabel);
				}
			
		  }
		
		return "redirect:/mgt/survey/editEntryForm.do?surveyId="+surveyId;	
	}
	
	
	/**
	 * <PRE>
	 * 1. MethodName 	: entry
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문보기
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
	@RequestMapping(value = "/mgt/survey/entry.do")
	public String entry(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		String surveyId 	= param.getString("surveyId");
		String returnUrl 	= "";
		//리스트 조회

		TbSurvey survey = surveyService.selectSurvey(param);

		DataMap userParam = new DataMap();
		userParam.put("SURVEY_ID", param.getString("surveyId"));
		userParam.put("SURVEY_GROUP_ID", param.getString("surveyGroupId"));
		userParam.put("USER_NO", param.getString("userNo"));
		userParam.put("SURVEY_YEAR", param.getString("surveyYear"));
		userParam.put("SURVEY_DEGREE", param.getString("surveyDegree"));
		TbSurveyUser surveyUser = surveyUserService.selectUserGroupSurvey(userParam);

		
		//질문리스트
		List<DataMap> SectionList   = surveyService.selectListSection( param);
		List<DataMap> QuestionList = surveyService.selectListQuestion( param);
		List<DataMap> QuestionLableList = surveyService.selectListQuestionLabel( param);
		
		
		if("".equals(param.getString("surveyGroupId"))){
			returnUrl="/mgt/survey/entry";
		}else{
			returnUrl="/mgt/survey/entrySafa";
		}
		
		model.addAttribute("param", param);
		model.addAttribute("survey", survey);
		model.addAttribute("surveyUser", surveyUser);
		model.addAttribute("SectionList", SectionList);
		model.addAttribute("QuestionList", QuestionList);
		model.addAttribute("QuestionLableList", QuestionLableList);
		
		//return "/mgt/survey/entry";
		return returnUrl;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: ideleteQuestion.do
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문항목삭제
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
	@RequestMapping(value = "/mgt/survey/ideleteQuestion.do")
	public String ideleteQuestion(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		String surveyId 	= param.getString("surveyId");
		int section 		= param.getInt("section");
		int question 		= param.getInt( "question" );
		
		TbQuestionLabel aQuestionLabel = new TbQuestionLabel();
		aQuestionLabel.setSURVEY_ID(surveyId);
		aQuestionLabel.setSECTION_ID(section);
		aQuestionLabel.setQUESTION_ID(question);
		surveyService.deleteQuestionLabel(aQuestionLabel);

		TbQuestion tbQuestion=new TbQuestion();
		tbQuestion.setSURVEY_ID(surveyId);
		tbQuestion.setSECTION_ID(section);
		tbQuestion.setQUESTION_ID(question);

		surveyService.deleteQuestion(tbQuestion);
		
		return "redirect:/mgt/survey/editEntryForm.do?surveyId="+surveyId;	
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: ientry
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 입력저장
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
	@RequestMapping(value = "/mgt/survey/ientry.do")
	public String ientry(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		String surveyId 	= param.getString("surveyId");
		String entryId 	= param.getString("entryId");
		
		System.out.println("entryId["+entryId+"]");

		String type 	    = "insert";

		if(!StringUtil.isEmpty(entryId)){
			type 	    = "update";
		}
		System.out.println("type["+type+"]");
		
		int resultId=0;
		int SectionId=0;
		
		//리스트 조회
		TbSurvey survey = surveyService.selectSurvey(param);
		
		//질문리스트
		List<DataMap> SectionList   = surveyService.selectListSection( param);
		List<DataMap> QuestionList = surveyService.selectListQuestion( param);
		List<DataMap> QuestionLableList = surveyService.selectListQuestionLabel( param);
		
		iSurveyEntryForm entryForm = new iSurveyEntryForm (surveyId);
		entryForm.load(SectionList,QuestionList,QuestionLableList);
	    String inputErrorId = entryForm.makeEntryChanges ( request );
	    
	    
	    //저장
	    int QuestionId=0;
	    TbResults tbResults = null; 
	    TbResultsLabel tbResultsLabel = null; 
	    String questionResult="";
	    String otherAnswerResult="";
	    String selectEd="";
	    
	    if(type.equals("insert")) {
	    	resultId=surveyService.selectQuestionResultId(surveyId);
	    	if(!param.getString("SURVEY_GROUP_ID").equals("")){   //진단지 그룹 저장
	    		TbSurveyUserEntry  aTbSurveyUserEntry =new TbSurveyUserEntry();
	    		aTbSurveyUserEntry.setUSER_NO(param.getInt("USER_NO"));
	    		aTbSurveyUserEntry.setSURVEY_GROUP_ID(param.getInt("SURVEY_GROUP_ID"));
	    		aTbSurveyUserEntry.setSURVEY_ID(surveyId);
	    		aTbSurveyUserEntry.setSURVEY_YEAR(param.getString("SURVEY_YEAR"));
	    		aTbSurveyUserEntry.setSURVEY_DEGREE(param.getString("SURVEY_DEGREE"));
	    		aTbSurveyUserEntry.setRESULTS_ID(resultId);
	    		aTbSurveyUserEntry.setINSERT_USER_NO(param.getInt("USER_NO"));
	    		aTbSurveyUserEntry.setUPDATE_USER_NO(param.getInt("USER_NO"));
	    		surveyUserService.insertUserGroupSurveyEntry(aTbSurveyUserEntry);
	    	}
	    	//
	   }else {
		   resultId=Integer.parseInt(entryId);
	   }
	    	
		if(type.equals("update")){ 
			DataMap deleteParam = new DataMap();
			deleteParam.put("RESULTS_ID",resultId);
			deleteParam.put("SURVEY_ID",surveyId);
			surveyService.deleteResultLabel(deleteParam);
		}
	    for (int i=0;i<entryForm.getNumSections();i++){
			SectionId=i;
			for (int j = 0; j < entryForm.getSection(i).getNumQuestions (); j++ ) {
				Question aQuestion = entryForm.getSection(i).getQuestion(j);
				QuestionId=aQuestion.getQuestionId();
				
				tbResults = new TbResults();
				tbResults.setRESULTS_ID(resultId);
				tbResults.setSURVEY_ID(surveyId);
				tbResults.setSECTION_ID(SectionId);
				tbResults.setQUESTION_ID(QuestionId);
				
				if(aQuestion.getTbQuestion(0).getQUESTION_RESULT()==null || aQuestion.getTbQuestion(0).getQUESTION_RESULT().equals("null")) {
					questionResult="";
				}else {
					questionResult=aQuestion.getTbQuestion(0).getQUESTION_RESULT();
				}
				System.out.println("questionResult["+questionResult+"]");
					
				tbResults.setQUESTION_RESULT(questionResult);
				
				if(aQuestion.getTbQuestion(0).getOTHERANSWER_RESULT()==null || aQuestion.getTbQuestion(0).getOTHERANSWER_RESULT().equals("null")){
					otherAnswerResult="";
				}else {
					otherAnswerResult=aQuestion.getTbQuestion(0).getOTHERANSWER_RESULT();
				}
				System.out.println("otherAnswerResult["+otherAnswerResult+"]");
				
				tbResults.setOTHERANSWER_RESULT(otherAnswerResult);
				if(type.equals("insert")) surveyService.insertResults(tbResults);
				else surveyService.updateResults(tbResults);
				/*
				if(type.equals("update")){ 
					DataMap deleteParam = new DataMap();
					deleteParam.put("RESULTS_ID",resultId);
					deleteParam.put("SURVEY_ID",surveyId);
					surveyService.deleteResultLabel(deleteParam);
				}
				*/
			   if(aQuestion.getQuestionType().equals("inputRadio") || aQuestion.getQuestionType().equals("inputCheckbox") ){
					//System.out.println("aQuestion"+QuestionId+"["+aQuestion.getTbQuestion(0).getTbQuestionLabels().size()+"]");
					for(TbQuestionLabel aTbTbQuestionLabel:aQuestion.getTbQuestion(0).getQUESTIONLABELS()){
						//System.out.println("aQuestion"+aTbTbQuestionLabel.getQUESTION_LABEL_ID()+"["+aTbTbQuestionLabel.getSELECTED()+"]");

						tbResultsLabel = new TbResultsLabel();
						tbResultsLabel.setRESULTS_ID(resultId);
						tbResultsLabel.setSURVEY_ID(surveyId);
						tbResultsLabel.setSECTION_ID(SectionId);
						tbResultsLabel.setQUESTION_ID(QuestionId);
						tbResultsLabel.setQUESTION_LABEL_ID(aTbTbQuestionLabel.getQUESTION_LABEL_ID());
						
						if(aTbTbQuestionLabel.getSELECTED()==null) selectEd="0";
						else selectEd=aTbTbQuestionLabel.getSELECTED();
						
						tbResultsLabel.setQUESTION_LABEL_RESULT(selectEd);
						
						
						surveyService.insertResultsLabel(tbResultsLabel);
						
					}
			   }
				
				
			}
		}	
	    
	    

		model.addAttribute("param", param);
		model.addAttribute("survey", survey);
	
		String goPage="";
	    if(type.equals("insert")){
	    	goPage= "/mgt/survey/exitPage";
	    } else {
	    	
            if("".equals(param.getString("SURVEY_GROUP_ID"))){
            	goPage= "redirect:/mgt/survey/viewResultsList.do?surveyId="+surveyId;
            }else{
               	goPage= "redirect:/mgt/survey/viewResultsList.do?surveyId="+surveyId
               																				+"&userNo="+param.getString("USER_NO")
               																				+"&surveyGroupId="+param.getString("SURVEY_GROUP_ID")
               																				+"&surveyYear="+param.getString("SURVEY_YEAR")
               																				+"&surveyDegree="+param.getString("SURVEY_DEGREE");
            }
	    }

	    return goPage;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: viewResults
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문결과
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
	@RequestMapping(value = "/mgt/survey/viewResults.do")
	public String viewResults(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		String surveyId 	= param.getString("surveyId");
		//리스트 조회

		TbSurvey survey = surveyService.selectSurvey(param);

		
		//질문리스트
		List<DataMap> SectionList   = surveyService.selectListSection( param);
		List<DataMap> QuestionList = surveyService.selectListQuestion( param);
		List<DataMap> QuestionLableList = surveyService.selectListQuestionLabel( param);
		

		model.addAttribute("param", param);
		model.addAttribute("survey", survey);
		model.addAttribute("SectionList", SectionList);
		model.addAttribute("QuestionList", QuestionList);
		model.addAttribute("QuestionLableList", QuestionLableList);
		
		return "/mgt/survey/viewResults";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: viewResultsQuestion
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 이질문보기
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
	@RequestMapping(value = "/mgt/survey/viewResultsQuestion.do")
	public String viewResultsQuestion(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		String surveyId 	= param.getString("surveyId");
		//리스트 조회

		TbSurvey survey = surveyService.selectSurvey(param);

		
		//질문
		TbQuestion tbQuestion = null;
		tbQuestion = surveyService.selectQuestion(param);
		
		//답변
		DataMap inResultparam = RequestUtil.getDataMap(request);
		inResultparam.put("SURVEY_ID", param.getString("surveyId"));
		inResultparam.put("SECTION_ID", param.getString("section"));
		inResultparam.put("QUESTION_ID", param.getString("question"));

		List<DataMap> QuestionResultsIds = surveyService.selectListResultsResultIds(inResultparam);
		
		model.addAttribute("param", param);
		model.addAttribute("survey", survey);
		model.addAttribute("tbQuestion", tbQuestion);
		model.addAttribute("QuestionResultsIds", QuestionResultsIds);
		
		return "/mgt/survey/viewResultsQuestion";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: viewResultsList
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 답변리스트
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
	@RequestMapping(value = "/mgt/survey/viewResultsList.do")
	public String viewResultsList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		String surveyId 	= param.getString("surveyId");
		//리스트 조회

		TbSurvey survey = surveyService.selectSurvey(param);

		
		
		//답변
		DataMap inResultparam = RequestUtil.getDataMap(request);
		inResultparam.put("SURVEY_ID", param.getString("surveyId"));
		inResultparam.put("USER_NO", param.getString("userNo"));
		inResultparam.put("SURVEY_GROUP_ID", param.getString("surveyGroupId"));
		inResultparam.put("SURVEY_YEAR", param.getString("surveyYear"));
		inResultparam.put("SURVEY_DEGREE", param.getString("surveyDegree"));
		
		//inResultparam.put("SECTION_ID", param.getString("section"));
		//inResultparam.put("QUESTION_ID", param.getString("question"));

		List<DataMap> QuestionResultsMaxDate = surveyService.selectListResultsMaxDate(inResultparam);
		
		model.addAttribute("param", param);
		model.addAttribute("survey", survey);
		model.addAttribute("QuestionResultsMaxDate", QuestionResultsMaxDate);
		
		return "/mgt/survey/viewResultsList";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: view
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문상세
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
	@RequestMapping(value = "/mgt/survey/view.do")
	public String view(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		String surveyId 	= param.getString("surveyId");
		//리스트 조회

		TbSurvey survey = surveyService.selectSurvey(param);

		
		//질문리스트
		List<DataMap> SectionList   = surveyService.selectListSection( param);
		List<DataMap> QuestionList = surveyService.selectListQuestion( param);
		List<DataMap> QuestionLableList = surveyService.selectListQuestionLabel( param);
		List<DataMap> QuestionResult = surveyService.selectListResults( param);
		List<DataMap> QuestionResultLabel = surveyService.selectListResultsLabel( param);
		String  entryDate = surveyService.selectListResultsEntryDate( param);
		
		param.put("entryDate",entryDate);
		
		model.addAttribute("param", param);
		model.addAttribute("survey", survey);
		model.addAttribute("SectionList", SectionList);
		model.addAttribute("QuestionList", QuestionList);
		model.addAttribute("QuestionLableList", QuestionLableList);
		model.addAttribute("QuestionResult", QuestionResult);
		model.addAttribute("QuestionResultLabel", QuestionResultLabel);
		
		return "/mgt/survey/view";
	}
	
	
	/**
	 * <PRE>
	 * 1. MethodName 	: viewResultsDetails
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 답변모두보기
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
	@RequestMapping(value = "/mgt/survey/viewResultsDetails.do")
	public String viewResultsDetails(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		String surveyId 	= param.getString("surveyId");
		//리스트 조회

		TbSurvey survey = surveyService.selectSurvey(param);

		
		//질문리스트
		List<DataMap> SectionList   = surveyService.selectListSection( param);
		List<DataMap> QuestionList = surveyService.selectListQuestion( param);
		List<DataMap> QuestionLableList = surveyService.selectListQuestionLabel( param);
		List<DataMap> QuestionResult = surveyService.selectAllListResults( param);
		List<DataMap> QuestionResultLabel = surveyService.selectAllListResultsLabel( param);
		
		 ArrayList<TbResults>tbResults = new  ArrayList<TbResults>();
		 ArrayList<TbResultsLabel> tbTbResultsLabels =null;
		
		for (DataMap aDataResult : QuestionResult){
		  TbResults aResult = new TbResults(); 
		  aResult.setRESULTS_ID(aDataResult.getInt("RESULTS_ID"));
		  aResult.setSURVEY_ID(aDataResult.getString("SURVEY_ID"));
		  aResult.setSECTION_ID(aDataResult.getInt("SECTION_ID"));
		  aResult.setQUESTION_ID(aDataResult.getInt("QUESTION_ID"));
		  aResult.setQUESTION_RESULT(aDataResult.getString("QUESTION_RESULT"));  				
		  aResult.setOTHERANSWER_RESULT(aDataResult.getString("OTHERANSWER_RESULT"));           	
		  aResult.setINSERT_DATE(aDataResult.getString("INSERT_DATE"));     
		  aResult.setQUESTION_TYPE(aDataResult.getString("QUESTION_TYPE"));
		  aResult.setOTHERANSWER(aDataResult.getString("OTHERANSWER"));
		  
  		    TbResultsLabel resultLabel = null;
  		    tbTbResultsLabels = new ArrayList<TbResultsLabel>();
		    for(DataMap aDataResultLabel :QuestionResultLabel){
		    	if(aResult.getRESULTS_ID()==aDataResultLabel.getInt("RESULTS_ID")&&aResult.getSECTION_ID()==aDataResultLabel.getInt("SECTION_ID") && aResult.getQUESTION_ID() == aDataResultLabel.getInt("QUESTION_ID")){
		    		resultLabel = new TbResultsLabel();
		    		resultLabel.setRESULTS_ID(aDataResult.getInt("RESULTS_ID"));
		    		resultLabel.setRESULTS_LABEL_ID(aDataResult.getInt("RESULTS_LABEL_ID"));
		    		resultLabel.setSURVEY_ID(aDataResult.getString("SURVEY_ID"));
		    		resultLabel.setSECTION_ID(aDataResultLabel.getInt("SECTION_ID"));
		    		resultLabel.setQUESTION_ID(aDataResultLabel.getInt("QUESTION_ID"));
		    		resultLabel.setQUESTION_LABEL_ID(aDataResultLabel.getInt("QUESTION_LABEL_ID"));
		    		resultLabel.setQUESTION_LABEL_RESULT(aDataResultLabel.getString("QUESTION_LABEL_RESULT")); 
		    		resultLabel.setQUESTION_LABEL(aDataResultLabel.getString("QUESTION_LABEL")); 
		    		tbTbResultsLabels.add(resultLabel);
		    	}
		    }

		  aResult.setTbResultsLabels(tbTbResultsLabels);
		  tbResults.add(aResult);
	  }
		
		
		

		model.addAttribute("param", param);
		model.addAttribute("survey", survey);
		model.addAttribute("SectionList", SectionList);
		model.addAttribute("QuestionList", QuestionList);
		model.addAttribute("QuestionLableList", QuestionLableList);
		model.addAttribute("tbResults", tbResults);
		
		return "/mgt/survey/viewResultsDetails";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: entryMod
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문답변삭제
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
	@RequestMapping(value = "/mgt/survey/entryMod.do")
	public String entryMod(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		String surveyId 	= param.getString("surveyId");
		//리스트 조회

		TbSurvey survey = surveyService.selectSurvey(param);

		
		//질문리스트
		List<DataMap> SectionList   = surveyService.selectListSection( param);
		List<DataMap> QuestionList = surveyService.selectListQuestion( param);
		List<DataMap> QuestionLableList = surveyService.selectListQuestionLabel( param);
		List<DataMap> QuestionResult = surveyService.selectListResults( param);
		List<DataMap> QuestionResultLabel = surveyService.selectListResultsLabel( param);
		

		model.addAttribute("param", param);
		model.addAttribute("survey", survey);
		model.addAttribute("SectionList", SectionList);
		model.addAttribute("QuestionList", QuestionList);
		model.addAttribute("QuestionLableList", QuestionLableList);
		model.addAttribute("QuestionResult", QuestionResult);
		model.addAttribute("QuestionResultLabel", QuestionResultLabel);
		
		
		String returnUrl="";
		if("".equals(param.getString("surveyGroupId"))){
			returnUrl="/mgt/survey/entryMod";
		}else{
			returnUrl="/mgt/survey/entryModSafa";
		}	
		//return "/mgt/survey/entryMod";
		return returnUrl;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: exportResults
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문결과조건
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
	@RequestMapping(value = "/mgt/survey/exportResults.do")
	public String exportResults(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		//리스트 조회
		TbSurvey survey = surveyService.selectSurvey(param);

		//질문리스트
		List<DataMap> SectionList   = surveyService.selectListSection( param);
		List<DataMap> QuestionList = surveyService.selectListQuestion( param);
		List<DataMap> QuestionLableList = surveyService.selectListQuestionLabel( param);

		model.addAttribute("param", param);
		model.addAttribute("survey", survey);
		model.addAttribute("SectionList", SectionList);
		model.addAttribute("QuestionList", QuestionList);
		model.addAttribute("QuestionLableList", QuestionLableList);
		
		return "/mgt/survey/exportResults";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: exportResults_ok
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문결과조건완료
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
	@RequestMapping(value = "/mgt/survey/exportResults_ok.do")
	public String exportResults_ok(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);

	    String exportDelimiter = param.getString ( "exportDelimiter" );
	    String sExportDelimiter="";
	    if ( exportDelimiter.equals ("tab") ) { sExportDelimiter=String.valueOf ( '\u0009' ); }
	    else if ( exportDelimiter.equals ("semicolon") ) { sExportDelimiter= ";" ; }
	    else if ( exportDelimiter.equals ("comma") ) { sExportDelimiter=  ","; }
	    else if ( exportDelimiter.equals ("pipe") ) { sExportDelimiter=  "|" ; }
	    else { sExportDelimiter=  "," ; }

	    String exportIncludeQuestions = param.getString ( "exportIncludeQuestions" );
		
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		//리스트 조회
		TbSurvey survey = surveyService.selectSurvey(param);

		TbSurvey aTbSurvey = new TbSurvey();
		
		String newSurveyId = param.getString("surveyId");
		aTbSurvey.setSURVEY_ID(newSurveyId);
		aTbSurvey.setEXPORTDELIMITER(sExportDelimiter);
		aTbSurvey.setEXPORTINCLUDEQUESTIONS(exportIncludeQuestions);
		surveyService.updateExportSurvey(aTbSurvey);

		int sectionNr=0;
		int questionNr=0;
		TbQuestion aTbQuestion = null;
		List<DataMap> QuestionList = surveyService.selectListQuestion( param);
		for(DataMap aQuestion:QuestionList){
			
			sectionNr=aQuestion.getInt("SECTION_ID");
			questionNr=aQuestion.getInt("QUESTION_ID");
			String exportInclude = (String) request.getParameter ( "include_"+sectionNr+"_" + questionNr );
			String summary = (String) request.getParameter ( "summary_"+sectionNr+"_" + questionNr );
			
			aTbQuestion = new TbQuestion();
			aTbQuestion.setSURVEY_ID(param.getString("surveyId"));
			aTbQuestion.setSECTION_ID(sectionNr);
			aTbQuestion.setQUESTION_ID(questionNr);
			
		     if ( exportInclude != null && exportInclude.equals("1") ) { aTbQuestion.setEXPORTINCLUDE("1"); }
		     else {  aTbQuestion.setEXPORTINCLUDE("0"); }
		      
		     aTbQuestion.setSUMMARY_CD(summary);
		     
		     surveyService.updateQuestionExport(aTbQuestion);
			
		}
		

		model.addAttribute("param", param);
		model.addAttribute("survey", survey);
		
		return "/mgt/survey/exportHeader";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: export
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문결과
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
	@RequestMapping(value = "/mgt/survey/export.do")
	public String export(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		String surveyId 	= param.getString("surveyId");
		//리스트 조회

		TbSurvey survey = surveyService.selectSurvey(param);

		
		//질문리스트
		List<DataMap> SectionList   = surveyService.selectListSection( param);
		List<DataMap> QuestionList = surveyService.selectListQuestion( param);
		List<DataMap> QuestionLableList = surveyService.selectListQuestionLabel( param);
		List<DataMap> QuestionResult = surveyService.selectAllListResults( param);
		List<DataMap> QuestionResultLabel = surveyService.selectAllListResultsLabel( param);
		
		 ArrayList<TbResults>tbResults = new  ArrayList<TbResults>();
		 ArrayList<TbResultsLabel> tbTbResultsLabels =null;
		
		for (DataMap aDataResult : QuestionResult){
		  TbResults aResult = new TbResults(); 
		  aResult.setRESULTS_ID(aDataResult.getInt("RESULTS_ID"));
		  aResult.setSURVEY_ID(aDataResult.getString("SURVEY_ID"));
		  aResult.setSECTION_ID(aDataResult.getInt("SECTION_ID"));
		  aResult.setQUESTION_ID(aDataResult.getInt("QUESTION_ID"));
		  aResult.setQUESTION_RESULT(aDataResult.getString("QUESTION_RESULT"));  				
		  aResult.setOTHERANSWER_RESULT(aDataResult.getString("OTHERANSWER_RESULT"));           	
		  aResult.setINSERT_DATE(aDataResult.getString("INSERT_DATE"));     
		  aResult.setQUESTION_TYPE(aDataResult.getString("QUESTION_TYPE"));
		  aResult.setOTHERANSWER(aDataResult.getString("OTHERANSWER"));
		  
  		    TbResultsLabel resultLabel = null;
  		    tbTbResultsLabels = new ArrayList<TbResultsLabel>();
		    for(DataMap aDataResultLabel :QuestionResultLabel){
		    	if(aResult.getRESULTS_ID()==aDataResultLabel.getInt("RESULTS_ID")&&aResult.getSECTION_ID()==aDataResultLabel.getInt("SECTION_ID") && aResult.getQUESTION_ID() == aDataResultLabel.getInt("QUESTION_ID")){
		    		resultLabel = new TbResultsLabel();
		    		resultLabel.setRESULTS_ID(aDataResult.getInt("RESULTS_ID"));
		    		resultLabel.setRESULTS_LABEL_ID(aDataResult.getInt("RESULTS_LABEL_ID"));
		    		resultLabel.setSURVEY_ID(aDataResult.getString("SURVEY_ID"));
		    		resultLabel.setSECTION_ID(aDataResultLabel.getInt("SECTION_ID"));
		    		resultLabel.setQUESTION_ID(aDataResultLabel.getInt("QUESTION_ID"));
		    		resultLabel.setQUESTION_LABEL_ID(aDataResultLabel.getInt("QUESTION_LABEL_ID"));
		    		resultLabel.setQUESTION_LABEL_RESULT(aDataResultLabel.getString("QUESTION_LABEL_RESULT")); 
		    		resultLabel.setQUESTION_LABEL(aDataResultLabel.getString("QUESTION_LABEL")); 
		    		tbTbResultsLabels.add(resultLabel);
		    	}
		    }

		  aResult.setTbResultsLabels(tbTbResultsLabels);
		  tbResults.add(aResult);
	  }
		
		
		

		model.addAttribute("param", param);
		model.addAttribute("survey", survey);
		model.addAttribute("SectionList", SectionList);
		model.addAttribute("QuestionList", QuestionList);
		model.addAttribute("QuestionLableList", QuestionLableList);
		model.addAttribute("tbResults", tbResults);
		
		return "/mgt/survey/export";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: openSurvey
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문 관리
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
	@RequestMapping(value = "/mgt/survey/openSurvey.do")
	public String openSurvey(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());

		TbSurvey aTbSurvey = new TbSurvey();

		String SurveyId = param.getString("surveyId");
		aTbSurvey.setSURVEY_ID(SurveyId);
		
		if(param.getString("stated").equals("open")){
			aTbSurvey.setOPENED(DateUtil.formatDate ( DateUtil.ISO4601DateFormat, new java.util.Date() ));
			surveyService.updateOpenedSurvey(aTbSurvey);
		}else{
			aTbSurvey.setCLOSED(DateUtil.formatDate ( DateUtil.ISO4601DateFormat, new java.util.Date() ));
			surveyService.updateClosedSurvey(aTbSurvey);
		}

		
		//설문상세
		TbSurvey resultMap = surveyService.selectSurvey(param);
		model.addAttribute("survey", resultMap);
		model.addAttribute("param", param);
		
		return "/mgt/survey/manageSurveyMenu";
	}
	

	/**
	 * <PRE>
	 * 1. MethodName 	: deleteEntry
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 답변삭제
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
	@RequestMapping(value = "/mgt/survey/deleteEntry.do")
	public String deleteEntry(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		String surveyId 	= param.getString("surveyId");
		String entryId 	= param.getString("entryId");
		//리스트 조회

		DataMap deleteParam = new DataMap();
		deleteParam.put("RESULTS_ID",entryId);
		deleteParam.put("SURVEY_ID",surveyId);
		surveyService.deleteResults(deleteParam);
		surveyService.deleteResultLabel(deleteParam);
		
		return "redirect:/mgt/survey/viewResultsDetails.do?surveyId="+surveyId;	
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: deleteEntries
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 답변삭제
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
	@RequestMapping(value = "/mgt/survey/deleteEntries.do")
	public String deleteEntrys(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		String surveyId 	= param.getString("surveyId");
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());

		DataMap deleteParam = new DataMap();
		deleteParam.put("SURVEY_ID",surveyId);
		deleteParam.put("USER_NO",param.getString("userNo"));
		deleteParam.put("SURVEY_GROUP_ID",param.getString("surveyGroupId"));
		deleteParam.put("SURVEY_YEAR",param.getString("surveyYear"));
		deleteParam.put("SURVEY_DEGREE",param.getString("surveyDegree"));

		surveyService.deleteResultsBySurveyId(deleteParam);
		surveyService.deleteResultLabelBySurveyId(deleteParam);
		surveyService.deleteResultUserEntryBySurveyId(deleteParam);

		
		//설문상세
		TbSurvey resultMap = surveyService.selectSurvey(param);
		model.addAttribute("survey", resultMap);
		model.addAttribute("param", param);
		
       String returnUrl="";
		if("".equals(param.getString("surveyGroupId"))){
			returnUrl="/mgt/survey/manageSurveyMenu";
		}else{
			returnUrl="/mgt/survey/userSurveyMenu";
		}
		
		//return "/mgt/survey/manageSurveyMenu";
		return returnUrl;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: addSection
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 섹션등록
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
	@RequestMapping(value = "/mgt/survey/addSection.do")
	public String addSection(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		String surveyId 	= param.getString("surveyId");
		//int sectionId 	= param.getInt("section");
		int sectionId 	    =0;
		//리스트 조회

		TbSection section = null;
		section = new TbSection();

		DataMap inParam = new DataMap();;
		inParam.put("SURVEY_ID", surveyId);
		inParam.put("SECTION_ID", param.getString("section"));
		
		if(surveyService.selectSectionAboveCnt(inParam)>0){
			sectionId=param.getInt("section");
			surveyService.updateSectionAbove(inParam);
			surveyService.updateSectionQuestionAbove(inParam);
			surveyService.updateSectionQuestionLabelAbove(inParam);
		}else{
			sectionId=surveyService.selectSectionId(surveyId);
		}
		//섹션저장
		section.setSURVEY_ID(surveyId);
		section.setSECTION_ID(sectionId);
		section.setSECTION_TITLE("sec"+sectionId);
		surveyService.insertSection(section);

		//comment저장
		TbQuestion question = new TbQuestion();
		question.setSURVEY_ID(surveyId);
		question.setSECTION_ID(sectionId);
		question.setQUESTION_TYPE("inputComment");
		question.setQUESTIONTEXT(section.getSECTION_TITLE());
		question.setQUESTION_ISHTML("1");
		question.setSHOWDIVIDER("0");
		surveyService.insertQuestion(question);
		
		return "redirect:/mgt/survey/editEntryForm.do?surveyId="+surveyId;	
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: deleteSection
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 섹션삭제
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
	@RequestMapping(value = "/mgt/survey/deleteSection.do")
	public String deleteSection(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		String surveyId 	= param.getString("surveyId");
		int sectionId 	= param.getInt("section");

		TbSection section = null;
		section = new TbSection();
		
		section.setSURVEY_ID(surveyId);
		section.setSECTION_ID(sectionId);

		surveyService.deleteSectionQuestionLabel(section);
		surveyService.deleteSectionQuestion(section);
		surveyService.deleteSection(section);
		
		return "redirect:/mgt/survey/editEntryForm.do?surveyId="+surveyId;	
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: editSection
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 섹션편집
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
	@RequestMapping(value = "/mgt/survey/editSection.do")
	public String editSection(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		System.out.println("type["+param.getString("type")+"]");
		
		DataMap inParam = new DataMap();
		inParam.put("SURVEY_ID",param.getString("surveyId"));
		inParam.put("SECTION_ID",param.getString("section"));

		TbSection tbSection = surveyService.selectSection(inParam);
		
		model.addAttribute("tbSection", tbSection);
		model.addAttribute("param", param);
		
		return "/mgt/survey/editSection";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: editSection_ok
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 섹션편집완료
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
	@RequestMapping(value = "/mgt/survey/editSection_ok.do")
	public String editSection_ok(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		String surveyId 	= param.getString("surveyId");
		
		if(param.getString("cancel").equals("Cancel")){
			return "redirect:/mgt/survey/editEntryForm.do?surveyId="+surveyId;	
		}
		
		
		TbSection aTbSection = new TbSection();
		aTbSection.setSURVEY_ID(param.getString("surveyId"));
		aTbSection.setSECTION_ID(param.getInt("section"));
		aTbSection.setSECTION_TITLE(param.getString("comment"));
		surveyService.updateSection(aTbSection);
		
		
		return "redirect:/mgt/survey/editEntryForm.do?surveyId="+surveyId;	
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: renameSurvey
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문변경
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
	@RequestMapping(value = "/mgt/survey/renameSurvey.do")
	public String renameSurvey(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		TbSurvey survey = surveyService.selectSurvey(param);
	
		model.addAttribute("param", param);
		model.addAttribute("survey", survey);
		
		return "/mgt/survey/renameSurvey";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: renameSurvey_ok
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문변경
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
	@RequestMapping(value = "/mgt/survey/renameSurvey_ok.do")
	public String renameSurvey_ok(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		TbSurvey survey = surveyService.selectSurvey(param);
		
		survey.setSURVEY_TITLE(param.getString("surveyName"));
		surveyService.updateSurvey(survey);
		
		model.addAttribute("param", param);
		model.addAttribute("survey", survey);
		
		return "/mgt/survey/renameSurvey";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: deleteSurvey
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문삭제
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
	@RequestMapping(value = "/mgt/survey/deleteSurvey.do")
	public String deleteSurvey(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		TbSurvey survey = surveyService.selectSurvey(param);
	
		model.addAttribute("param", param);
		model.addAttribute("survey", survey);
		
		return "/mgt/survey/deleteSurvey";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: deleteSurvey_ok
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문삭제
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
	@RequestMapping(value = "/mgt/survey/deleteSurvey_ok")
	public String deleteSurvey_ok(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		TbSurvey survey = new TbSurvey();
		survey.setSURVEY_ID(param.getString("surveyId"));

		surveyService.deleteSurveyResultLabel(survey);
		surveyService.deleteSurveyResult(survey);
		surveyService.deleteSurveyQuestionLabel(survey);
		surveyService.deleteSurveyQuestion(survey);
		surveyService.deleteSurveySection(survey);
		surveyService.deleteSurvey(survey);
	
		model.addAttribute("param", param);
		model.addAttribute("survey", survey);
		
		return "/mgt/survey/deleteSurvey";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: copySurvey
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문Copy
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
	@RequestMapping(value = "/mgt/survey/copySurvey.do")
	public String copySurvey(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		param.put("surveyId", param.getString("templateSurveyId"));
		
		TbSurvey survey = surveyService.selectSurvey(param);
	
		model.addAttribute("param", param);
		model.addAttribute("survey", survey);
		
		return "/mgt/survey/copySurvey";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: copySurvey_ok
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문Copy
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
	@RequestMapping(value = "/mgt/survey/copySurvey_ok.do")
	public String copySurvey_ok(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		param.put("surveyId", param.getString("templateSurveyId"));
		
		TbSurvey survey = surveyService.selectSurvey(param);

		Date date = new Date ();
		String newSurveyId = String.valueOf ( date.getTime() );

		DataMap inParam = new DataMap();
		inParam.put("templateSurveyId", param.getString("templateSurveyId"));
		inParam.put("SURVEY_TITLE", param.getString("surveyName"));
		inParam.put("SURVEY_ID", newSurveyId);
		
		surveyService.copySurvey(inParam);
		surveyService.copySection(inParam);
		surveyService.copyQuestion(inParam);
		surveyService.copyQuestionLabel(inParam);
	
		model.addAttribute("param", param);
		model.addAttribute("survey", survey);
		
		return "/mgt/survey/copySurvey";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: surveylist
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문리스트팝업
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
	@RequestMapping(value = "/mgt/survey/surveylist.do")
	public String surveylist(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		//설문리스트 조회
		List<DataMap> resultList = surveyService.selectPageListSurvey(model, param);

		model.addAttribute("resultList", resultList);
		model.addAttribute("param", param);
		
		return "/mgt/survey/surveylist";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: surveylist_ok
	 * 2. ClassName  	: SurveyController
	 * 3. Comment   	: 설문문항가졍오기
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
	@RequestMapping(value = "/mgt/survey/surveylist_ok.do")
	public String surveylist_ok(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		param.put("surveyId", param.getString("OrgSurveyId"));

		
		TbSurvey survey = new TbSurvey();
		survey.setSURVEY_ID(param.getString("OrgSurveyId"));

		surveyService.deleteSurveyResultLabel(survey);
		surveyService.deleteSurveyResult(survey);
		surveyService.deleteSurveyQuestionLabel(survey);
		surveyService.deleteSurveyQuestion(survey);
		surveyService.deleteSurveySection(survey);
		
		DataMap inParam = new DataMap();
		inParam.put("templateSurveyId", param.getString("ChgSurveyId"));
		inParam.put("SURVEY_ID", param.getString("OrgSurveyId"));
		//설문copy
		surveyService.copySection(inParam);
		surveyService.copyQuestion(inParam);
		surveyService.copyQuestionLabel(inParam);
	
		model.addAttribute("param", param);
		
		return "/mgt/survey/surveylist_ok";
	}


	
	
}
