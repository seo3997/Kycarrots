package com.whomade.kycarrots.mgt.survey.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;


import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.page.util.pageNavigationUtil;
import com.whomade.kycarrots.framework.common.util.file.AtFileMngUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.whomade.kycarrots.mgt.survey.dto.QuestionDragDto;
import com.whomade.kycarrots.mgt.survey.dto.QuestionPosition;
import com.whomade.kycarrots.mgt.survey.dto.SectionDragDto;
import com.whomade.kycarrots.mgt.survey.dto.SectionPosition;
import com.whomade.kycarrots.mgt.survey.vo.TbQuestion;
import com.whomade.kycarrots.mgt.survey.vo.TbQuestionLabel;
import com.whomade.kycarrots.mgt.survey.vo.TbResults;
import com.whomade.kycarrots.mgt.survey.vo.TbResultsLabel;
import com.whomade.kycarrots.mgt.survey.vo.TbSection;
import com.whomade.kycarrots.mgt.survey.vo.TbSurvey;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("surveyService")
public class SurveyServiceImpl extends EgovAbstractServiceImpl implements SurveyService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SurveyServiceImpl.class);

	/** commonDao */
	@Resource(name="commonMybatisDao")
	private CommonMybatisDao commonMybatisDao;
	 
	@Resource(name="AtFileMngUtil")
	private AtFileMngUtil atFileMngUtil;
	
	/**
	 * <PRE>
	 * 1. MethodName 	: selectPageListSurvey
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 설문 리스트
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param model
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectPageListSurvey(ModelMap model, DataMap param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		int totCnt = commonMybatisDao.selectOne("mgt.survey.selectTotCntSurvey", param);
		
		// 개수가 없는경우는 리스트 조회 하지 않는다.
		if(totCnt > 0){
			param.put("totalCount", totCnt);
			// param 객체에 페이지 관련 정보를 담는다.
			param = pageNavigationUtil.createNavigationInfo(model, param);
			resultList =  commonMybatisDao.selectList("mgt.survey.selectPageListSurvey", param);
		}
		return resultList;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectSurvey
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment    	: 설문 상세
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2017. 3. 13. 오후 4:10:00
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public TbSurvey selectSurvey(DataMap param)throws Exception {
		return (TbSurvey) commonMybatisDao.selectOne("mgt.survey.selectSurvey", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName : selectQuestion
	 * 2. ClassName    : SurveyServiceImpl
	 * 3. Comment       : 질문 상세
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 3. 13. 오후 4:10:00
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public TbQuestion selectQuestion(DataMap param)throws Exception {
		return (TbQuestion) commonMybatisDao.selectOne("mgt.survey.selectQuestion", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName : selectSection
	 * 2. ClassName    : SurveyServiceImpl
	 * 3. Comment       : 섹션 상세
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 3. 13. 오후 4:10:00
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public TbSection selectSection(DataMap param)throws Exception {
		return (TbSection) commonMybatisDao.selectOne("mgt.survey.selectSection", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: insertSurvey
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 설문 등록
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void insertSurvey(TbSurvey param)throws Exception {
		commonMybatisDao.insert("mgt.survey.insertSurvey", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateSurvey
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 설문명수정
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateSurvey(TbSurvey param)throws Exception {
		commonMybatisDao.insert("mgt.survey.updateSurvey", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateExportSurvey
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 설문 수정
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateExportSurvey(TbSurvey param)throws Exception {
		commonMybatisDao.insert("mgt.survey.updateExportSurvey", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateOpenedSurvey
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 설문 수정
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateOpenedSurvey(TbSurvey param)throws Exception {
		commonMybatisDao.insert("mgt.survey.updateOpenedSurvey", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateClosedSurvey
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 설문 수정
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateClosedSurvey(TbSurvey param)throws Exception {
		commonMybatisDao.insert("mgt.survey.updateClosedSurvey", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: insertSection
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 섹션 등록
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void insertSection(TbSection param)throws Exception {
		commonMybatisDao.insert("mgt.survey.insertSection", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: insertQuestion
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: Question 등록
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void insertQuestion(TbQuestion param)throws Exception {
		commonMybatisDao.insert("mgt.survey.insertQuestion", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: insertQuestion
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: Question 수정
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateQuestion(TbQuestion param)throws Exception {
		commonMybatisDao.insert("mgt.survey.updateQuestion", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: processQuestion
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: Question 등록
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2017. 12. 21 15:59
	 * </PRE>
	 *   @throws Exception
	 */

	public TbQuestion processQuestion(String mode, TbQuestion tbQuestion, String surveyId, int sectionId,int QuestionId) throws Exception{
	    
		int questionId;
	    if (mode.equals("insert")) {
	        questionId = selectQuestionId(tbQuestion);
	        tbQuestion.setQUESTION_ID(questionId);
			commonMybatisDao.insert("mgt.survey.insertQuestion", tbQuestion);
	    } else {
	        questionId = QuestionId;
	        tbQuestion.setQUESTION_ID(questionId);
			commonMybatisDao.insert("mgt.survey.updateQuestion", tbQuestion);
	    }
	    
	    // 옵션리스트 저장
	    if (tbQuestion.getQUESTION_TYPE().equals("inputRadio") || tbQuestion.getQUESTION_TYPE().equals("inputCheckbox")) {
	        if (mode.equals("update")) {
	            TbQuestionLabel aQuestionLabel = new TbQuestionLabel();
	            aQuestionLabel.setSURVEY_ID(surveyId);
	            aQuestionLabel.setSECTION_ID(sectionId);
	            aQuestionLabel.setQUESTION_ID(questionId);
	            commonMybatisDao.delete("mgt.survey.deleteQuestion", aQuestionLabel);
	        }
	        for (TbQuestionLabel tbQuestionLabel : tbQuestion.getQUESTIONLABELS()) {
	            tbQuestionLabel.setSURVEY_ID(surveyId);
	            tbQuestionLabel.setSECTION_ID(sectionId);
	            tbQuestionLabel.setQUESTION_ID(questionId);
	    		commonMybatisDao.insert("mgt.survey.insertQuestionLabel", tbQuestionLabel);
	        }
	    }
	    
	    return tbQuestion;
	}	
	
	/**
	 * <PRE>
	 * 1. MethodName 	: updateQuestionPositions
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: Question 포지션수정
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2017. 12. 21 15:59
	 * </PRE>
	 *   @throws Exception
	 */


	public void updateQuestionPositions(QuestionDragDto questionDragDto) throws Exception{
	    String surveyId = questionDragDto.getSurveyId();
	    ArrayList<QuestionPosition> questionListPositions = questionDragDto.getQuestionListPositions();
	    int questionId = 0;
	    int sectionId = 0;
	    int position = 0;
		DataMap aQuestion = new DataMap();

	    // SurveyId와 QuestionListPositions를 이용하여 드래그한 질문들의 위치를 업데이트합니다.
	    for (QuestionPosition questionPosition : questionListPositions) {
	        questionId = questionPosition.getQuestionId();
	        sectionId = questionPosition.getSectionId();
	        position = questionPosition.getPosition();

	        aQuestion = new DataMap();
	        aQuestion.put("SURVEY_ID", surveyId);
	        aQuestion.put("SECTION_ID", sectionId);
	        aQuestion.put("QUESTION_ID", questionId);
	        aQuestion.put("POSITION", position);
			commonMybatisDao.update("mgt.survey.updatePositionQuestionLabel", aQuestion);
			commonMybatisDao.update("mgt.survey.updatePositionQuestion", aQuestion);
	        // questionId를 사용하여 해당 질문의 위치를 업데이트합니다.
	        // sectionId와 position을 사용하여 해당 섹션의 위치를 업데이트합니다.
	        System.out.println("questionId[" + questionId + "] position[" + position + "]");
	        // 여기에 질문의 위치를 업데이트하는 로직을 추가하세요.
	        // 예를 들어 데이터베이스에서 질문을 업데이트하거나 서비스를 호출하여 해당 작업을 수행할 수 있습니다.
	    }
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateQuestionPositions
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: Question 포지션수정
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2017. 12. 21 15:59
	 * </PRE>
	 *   @throws Exception
	 */


	public void updateSectionPositions(SectionDragDto sectionDragDto) throws Exception{
	    String surveyId = sectionDragDto.getSurveyId();
	    ArrayList<SectionPosition> sectionListPositions = sectionDragDto.getSectionListPositions();

	    // SurveyId와 SectionListPositions를 이용하여 드래그한 섹션들의 위치를 업데이트하는 로직을 작성합니다.
        int sectionId = 0;
        int position = 0;
        DataMap aSection = new DataMap();
        for (SectionPosition sposition : sectionListPositions) {
	        sectionId 	= sposition.getSectionId();
	        position 	= sposition.getPosition();

	        aSection = new DataMap();
	        aSection.put("SURVEY_ID", surveyId);
	        aSection.put("SECTION_ID", sectionId);
	        aSection.put("POSITION", position);
			commonMybatisDao.update("mgt.survey.updatePositionSectionQuestionLabel", aSection);
			commonMybatisDao.update("mgt.survey.updatePositionSectionQuestion", aSection);
			commonMybatisDao.update("mgt.survey.updatePositionSection", aSection);
	        System.out.println("sectionId["+sectionId+"]position[["+position+"]");

	        // sectionId를 사용하여 해당 섹션의 위치를 업데이트합니다.
	    }

	}

	
	/**
	 * <PRE>
	 * 1. MethodName 	: insertQuestionLabel
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: QuestionLabel 등록
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void insertQuestionLabel(TbQuestionLabel param)throws Exception {
		commonMybatisDao.insert("mgt.survey.insertQuestionLabel", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: deleteQuestionLabel
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: QuestionLabel 등록
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteQuestion(TbQuestion param)throws Exception {
		commonMybatisDao.delete("mgt.survey.deleteQuestion", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: deleteSection
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 섹션삭제
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteSection(TbSection param)throws Exception {
		commonMybatisDao.delete("mgt.survey.deleteSection", param);
	}

	
	/**
	 * <PRE>
	 * 1. MethodName 	: deleteSectionQuestion
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 세셕삭제
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteSectionQuestion(TbSection param)throws Exception {
		commonMybatisDao.delete("mgt.survey.deleteSectionQuestion", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: deleteQuestionLabel
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: QuestionLabel 등록
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteSectionQuestionLabel(TbSection param)throws Exception {
		commonMybatisDao.delete("mgt.survey.deleteSectionQuestionLabel", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: updateQuestionExport
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: Question 수정
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateQuestionExport(TbQuestion param)throws Exception {
		commonMybatisDao.update("mgt.survey.updateQuestionExport", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateSection
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 세션 수정
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateSection(TbSection param)throws Exception {
		commonMybatisDao.update("mgt.survey.updateSection", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: deleteQuestionLabel
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: QuestionLabel 등록
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteQuestionLabel(TbQuestionLabel param)throws Exception {
		commonMybatisDao.delete("mgt.survey.deleteQuestionLabel", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : selectExistSurvey
	 * 2. ClassName    : SurveyServiceImpl
	 * 3. Comment       : 설문존재여부
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 3. 13. 오후 4:10:00
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public int selectExistSurvey(String param)throws Exception {
		return (Integer) commonMybatisDao.selectOne("mgt.survey.selectExistSurvey", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : selectQuestionId
	 * 2. ClassName    : SurveyServiceImpl
	 * 3. Comment       : 설문ID
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 3. 13. 오후 4:10:00
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public int selectQuestionId(TbQuestion param)throws Exception {
		return (Integer) commonMybatisDao.selectOne("mgt.survey.selectQuestionId", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName : selectSectionId
	 * 2. ClassName    : SurveyServiceImpl
	 * 3. Comment       : 설문ID
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 3. 13. 오후 4:10:00
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public int selectSectionId(String param)throws Exception {
		return (Integer) commonMybatisDao.selectOne("mgt.survey.selectSectionId", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName : selectSectionAboveCnt
	 * 2. ClassName    : SurveyServiceImpl
	 * 3. Comment       : 섹션업데이트
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 3. 13. 오후 4:10:00
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public int selectSectionAboveCnt(DataMap param)throws Exception {
		return (Integer) commonMybatisDao.selectOne("mgt.survey.selectSectionAboveCnt", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: selectListSection
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 섹션 리스트
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListSection(DataMap param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.survey.selectListSection", param);

		return resultList;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectListQuestion
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: Question 리스트
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListQuestion(DataMap param)throws Exception {

		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.survey.selectListQuestion", param);

		return resultList;
		
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectListQuestionLabel
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: QuestionLabel 리스트
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListQuestionLabel(DataMap param)throws Exception {

		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.survey.selectListQuestionLabel", param);

		return resultList;
		
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: selectQuestionLabel
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: QuestionLabel 리스트
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectQuestionLabel(DataMap param)throws Exception {

		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.survey.selectQuestionLabel", param);

		return resultList;
		
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: updateQuestionUpDown
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 설문항목 수정
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateQuestionUpDown(DataMap param)throws Exception {
		commonMybatisDao.update("mgt.survey.updateQuestionUpDown", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateQuestionLabelUpDown
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 설문항목레이블 수정
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateQuestionLabelUpDown(DataMap param)throws Exception {
		commonMybatisDao.update("mgt.survey.updateQuestionLabelUpDown", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateQuestionCopy
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 설문항목레이블 수정
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateQuestionCopy(DataMap param)throws Exception {
		commonMybatisDao.update("mgt.survey.updateQuestionCopy", param);
	}
	/**
	 * <PRE>
	 * 1. MethodName 	: updateQuestionLabelUpDown
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 설문항목레이블 수정
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateQuestionLabelCopy(DataMap param)throws Exception {
		commonMybatisDao.update("mgt.survey.updateQuestionLabelCopy", param);
	}
	/**
	 * <PRE>
	 * 1. MethodName 	: updateQuestionAbove
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 설문항목레이블 수정
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateQuestionAbove(DataMap param)throws Exception {
		commonMybatisDao.update("mgt.survey.updateQuestionAbove", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateQuestionLabelAboveCopy
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 설문항목레이블 수정
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateQuestionLabelAbove(DataMap param)throws Exception {
		commonMybatisDao.update("mgt.survey.updateQuestionLabelAbove", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateSectionAbove
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 섹션등록
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateSectionAbove(DataMap param)throws Exception {
		commonMybatisDao.update("mgt.survey.updateSectionAbove", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateSectionQuestionAbove
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 섹션등록
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateSectionQuestionAbove(DataMap param)throws Exception {
		commonMybatisDao.update("mgt.survey.updateSectionQuestionAbove", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateSectionQuestionLabelAbove
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 섹션등록
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateSectionQuestionLabelAbove(DataMap param)throws Exception {
		commonMybatisDao.update("mgt.survey.updateSectionQuestionLabelAbove", param);
	}
	
	
	/**
	 * <PRE>
	 * 1. MethodName : selectUpQuestionId
	 * 2. ClassName    : SurveyServiceImpl
	 * 3. Comment       : 설문업로드 ID
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 3. 13. 오후 4:10:00
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public int selectUpQuestionId(DataMap param)throws Exception {
		return (Integer) commonMybatisDao.selectOne("mgt.survey.selectUpQuestionId", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : selectUpQuestionId
	 * 2. ClassName    : SurveyServiceImpl
	 * 3. Comment       : 설문다운로드 ID
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 3. 13. 오후 4:10:00
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public int selectDownQuestionId(DataMap param)throws Exception {
		return (Integer) commonMybatisDao.selectOne("mgt.survey.selectDownQuestionId", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName : selectSectionQuestionCnt
	 * 2. ClassName    : SurveyServiceImpl
	 * 3. Comment       : 섹션갯수
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 3. 13. 오후 4:10:00
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public int selectSectionCnt(DataMap param)throws Exception {
		return (Integer) commonMybatisDao.selectOne("mgt.survey.selectSectionCnt", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName : selectSectionQuestionCnt
	 * 2. ClassName    : SurveyServiceImpl
	 * 3. Comment       : 섹션설문갯수
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 3. 13. 오후 4:10:00
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public int selectSectionQuestionCnt(DataMap param)throws Exception {
		return (Integer) commonMybatisDao.selectOne("mgt.survey.selectSectionQuestionCnt", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName : selectSectionQuestionMax
	 * 2. ClassName    : SurveyServiceImpl
	 * 3. Comment       : 섹션설문맥스
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 3. 13. 오후 4:10:00
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public int selectSectionQuestionMax(DataMap param)throws Exception {
		return (Integer) commonMybatisDao.selectOne("mgt.survey.selectSectionQuestionMax", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: insertResults
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: Question 
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void insertResults(TbResults param)throws Exception {
		commonMybatisDao.insert("mgt.survey.insertResults", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: insertResultsLabel
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: QuestionLabel
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void insertResultsLabel(TbResultsLabel param)throws Exception {
		commonMybatisDao.insert("mgt.survey.insertResultsLabel", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName : selectQuestionResultId
	 * 2. ClassName    : SurveyServiceImpl
	 * 3. Comment       : 설문
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 3. 13. 오후 4:10:00
	 * </PRE>
	 *   @return
	 *   @throws Exception
	 */
	public int selectQuestionResultId(String surveyId)throws Exception {
		return (Integer) commonMybatisDao.selectOne("mgt.survey.selectQuestionResultId",surveyId);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: selectListResultsResultIds
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 이질문보기
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListResultsResultIds(DataMap param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.survey.selectListResultsResultIds", param);

		return resultList;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectListResultsMaxDate
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 답변리스트
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListResultsMaxDate(DataMap param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.survey.selectListResultsMaxDate", param);

		return resultList;
	}
	/**
	 * <PRE>
	 * 1. MethodName 	: selectListResultsEntryDate
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 답변일자
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public String selectListResultsEntryDate(DataMap param)throws Exception {
		
		String sReturnt =  commonMybatisDao.selectOne("mgt.survey.selectListResultsEntryDate", param);

		return sReturnt;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectListResults
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 답변항목
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListResults(DataMap param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.survey.selectListResults", param);

		return resultList;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectListResultsLabel
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 답변항목
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListResultsLabel(DataMap param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.survey.selectListResultsLabel", param);

		return resultList;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectAllListResults
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 모든답변항목
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectAllListResults(DataMap param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.survey.selectAllListResults", param);

		return resultList;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectAllListResultsLabel
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 모든답변항목
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectAllListResultsLabel(DataMap param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.survey.selectAllListResultsLabel", param);

		return resultList;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateResults
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 답변수정
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateResults(TbResults param)throws Exception {
		commonMybatisDao.update("mgt.survey.updateResults", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: deleteResultLabel
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 답변삭제
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteResultLabel(DataMap param)throws Exception {
		commonMybatisDao.delete("mgt.survey.deleteResultLabel", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: deleteResultLabel
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 답변삭제
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteResults(DataMap param)throws Exception {
		commonMybatisDao.delete("mgt.survey.deleteResults", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: deleteResultLabel
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 답변삭제
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteResultsBySurveyId(DataMap param)throws Exception {
		commonMybatisDao.delete("mgt.survey.deleteResultsBySurveyId", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: deleteResultLabel
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 답변삭제
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteResultLabelBySurveyId(DataMap param)throws Exception {
		commonMybatisDao.delete("mgt.survey.deleteResultLabelBySurveyId", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: deleteResultUserEntryBySurveyId
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 답변삭제
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteResultUserEntryBySurveyId(DataMap param)throws Exception {
		commonMybatisDao.delete("mgt.survey.deleteResultUserEntryBySurveyId", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: deleteSurvey
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 설문삭제
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteSurvey(TbSurvey param)throws Exception {
		commonMybatisDao.delete("mgt.survey.deleteSurvey", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: deleteSurveySection
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 설문삭제
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteSurveySection(TbSurvey param)throws Exception {
		commonMybatisDao.delete("mgt.survey.deleteSurveySection", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: deleteSurveyQuestion
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 설문삭제
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteSurveyQuestion(TbSurvey param)throws Exception {
		commonMybatisDao.delete("mgt.survey.deleteSurveyQuestion", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: deleteSurveyQuestionLabel
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 설문삭제
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteSurveyQuestionLabel(TbSurvey param)throws Exception {
		commonMybatisDao.delete("mgt.survey.deleteSurveyQuestionLabel", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: deleteSurveyResult
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 설문삭제
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteSurveyResult(TbSurvey param)throws Exception {
		commonMybatisDao.delete("mgt.survey.deleteSurveyResult", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: deleteSurveyResultLabel
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 설문삭제
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteSurveyResultLabel(TbSurvey param)throws Exception {
		commonMybatisDao.delete("mgt.survey.deleteSurveyResultLabel", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: copySurvey
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 설문 Copy
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void copySurvey(DataMap param)throws Exception {
		commonMybatisDao.insert("mgt.survey.copySurvey", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: copySection
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 설문 Copy
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void copySection(DataMap param)throws Exception {
		commonMybatisDao.insert("mgt.survey.copySection", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: copyQuestion
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 설문 Copy
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void copyQuestion(DataMap param)throws Exception {
		commonMybatisDao.insert("mgt.survey.copyQuestion", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: copySurvey
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 설문 Copy
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void copyQuestionLabel(DataMap param)throws Exception {
		commonMybatisDao.insert("mgt.survey.copyQuestionLabel", param);
	}
	
}
