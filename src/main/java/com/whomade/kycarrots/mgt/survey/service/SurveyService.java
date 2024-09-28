package com.whomade.kycarrots.mgt.survey.service;

import java.util.List;

import com.whomade.kycarrots.framework.common.object.DataMap;
import org.springframework.ui.ModelMap;

import com.whomade.kycarrots.mgt.survey.dto.QuestionDragDto;
import com.whomade.kycarrots.mgt.survey.dto.SectionDragDto;
import com.whomade.kycarrots.mgt.survey.vo.TbQuestion;
import com.whomade.kycarrots.mgt.survey.vo.TbQuestionLabel;
import com.whomade.kycarrots.mgt.survey.vo.TbResults;
import com.whomade.kycarrots.mgt.survey.vo.TbResultsLabel;
import com.whomade.kycarrots.mgt.survey.vo.TbSection;
import com.whomade.kycarrots.mgt.survey.vo.TbSurvey;

public interface SurveyService {
	
	List<DataMap> selectPageListSurvey(ModelMap model, DataMap param)throws Exception;

	TbSurvey selectSurvey(DataMap param)throws Exception;

	void insertSurvey(TbSurvey param)throws Exception;

	void updateSurvey(TbSurvey param)throws Exception;

	void updateExportSurvey(TbSurvey param)throws Exception;
	
	void updateOpenedSurvey(TbSurvey param)throws Exception;

	void updateClosedSurvey(TbSurvey param)throws Exception;

	int selectExistSurvey(String param)throws Exception;

	int selectQuestionId(TbQuestion param)throws Exception;

	int selectSectionId(String param)throws Exception;

	int selectSectionAboveCnt(DataMap param)throws Exception;
	
	void insertSection(TbSection param)throws Exception;
	
	void insertQuestion(TbQuestion param)throws Exception;

	void updateQuestion(TbQuestion param)throws Exception;
	
	void insertQuestionLabel(TbQuestionLabel param)throws Exception;

	List<DataMap> selectListSection(DataMap param)throws Exception;

	List<DataMap> selectListQuestion(DataMap param)throws Exception;

	List<DataMap> selectListQuestionLabel(DataMap param)throws Exception;

	TbSection selectSection(DataMap param)throws Exception;

	TbQuestion selectQuestion(DataMap param)throws Exception;

	List<DataMap> selectQuestionLabel(DataMap param)throws Exception;

	void deleteSection(TbSection param)throws Exception;

	void updateSection(TbSection param)throws Exception;

	void deleteSectionQuestion(TbSection param)throws Exception;
	
	void deleteSectionQuestionLabel(TbSection param)throws Exception;
	
	void deleteQuestion(TbQuestion param)throws Exception;

	void updateQuestionExport(TbQuestion param)throws Exception;

	void deleteQuestionLabel(TbQuestionLabel param)throws Exception;

	void updateQuestionUpDown(DataMap param)throws Exception;

	void updateQuestionLabelUpDown(DataMap param)throws Exception;

	void updateQuestionCopy(DataMap param)throws Exception;

	void updateQuestionLabelCopy(DataMap param)throws Exception;

	void updateQuestionAbove(DataMap param)throws Exception;

	void updateQuestionLabelAbove(DataMap param)throws Exception;

	void updateSectionAbove(DataMap param)throws Exception;

	void updateSectionQuestionAbove(DataMap param)throws Exception;
	
	void updateSectionQuestionLabelAbove(DataMap param)throws Exception;
	
	int selectUpQuestionId(DataMap param)throws Exception;

	int selectDownQuestionId(DataMap param)throws Exception;

	int selectSectionCnt(DataMap param)throws Exception;

	int selectSectionQuestionCnt(DataMap param)throws Exception;

	int selectSectionQuestionMax(DataMap param)throws Exception;

	int selectQuestionResultId(String surveyId)throws Exception;

	void insertResults(TbResults param)throws Exception;

	void insertResultsLabel(TbResultsLabel param)throws Exception;

	List<DataMap> selectListResultsResultIds(DataMap param)throws Exception;
	
	List<DataMap> selectListResultsMaxDate(DataMap param)throws Exception;

	String selectListResultsEntryDate(DataMap param)throws Exception;

	List<DataMap> selectListResults(DataMap param)throws Exception;

	List<DataMap> selectListResultsLabel(DataMap param)throws Exception;

	List<DataMap> selectAllListResults(DataMap param)throws Exception;

	List<DataMap> selectAllListResultsLabel(DataMap param)throws Exception;

	void updateResults(TbResults param)throws Exception;

	void deleteResultLabel(DataMap param)throws Exception;

	void deleteResults(DataMap param)throws Exception;
	
	void deleteResultsBySurveyId(DataMap param)throws Exception;
	
	void deleteResultLabelBySurveyId(DataMap param)throws Exception;
	
	void deleteResultUserEntryBySurveyId(DataMap param)throws Exception;

	void deleteSurvey(TbSurvey param)throws Exception;

	void deleteSurveySection(TbSurvey param)throws Exception;
	
	void deleteSurveyQuestion(TbSurvey param)throws Exception;
	
	void deleteSurveyQuestionLabel(TbSurvey param)throws Exception;
	
	void deleteSurveyResult(TbSurvey param)throws Exception;
	
	void deleteSurveyResultLabel(TbSurvey param)throws Exception;
	
	void copySurvey(DataMap param)throws Exception;
	
	void copySection(DataMap param)throws Exception;
	
	void copyQuestion(DataMap param)throws Exception;
	
	void copyQuestionLabel(DataMap param)throws Exception;
	
	TbQuestion processQuestion(String mode, TbQuestion tbQuestion, String surveyId, int sectionId, int QuestionId) throws Exception;

	void updateQuestionPositions(QuestionDragDto questionDragDto) throws Exception;

	void  updateSectionPositions(SectionDragDto sectionDragDto) throws Exception;
	


}
