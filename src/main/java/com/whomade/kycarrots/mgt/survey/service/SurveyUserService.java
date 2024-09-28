package com.whomade.kycarrots.mgt.survey.service;

import java.util.List;

import com.whomade.kycarrots.framework.common.object.DataMap;
import org.springframework.ui.ModelMap;

import com.whomade.kycarrots.mgt.survey.vo.TbQuestion;
import com.whomade.kycarrots.mgt.survey.vo.TbQuestionUser;
import com.whomade.kycarrots.mgt.survey.vo.TbSectionUser;
import com.whomade.kycarrots.mgt.survey.vo.TbSurveyUserDegree;
import com.whomade.kycarrots.mgt.survey.vo.TbSurveyUserEntry;
import com.whomade.kycarrots.mgt.survey.vo.TbSurveyUserEntryResult;
import com.whomade.kycarrots.mgt.survey.vo.TbUserGroup;
import com.whomade.kycarrots.mgt.survey.vo.TbSurveyUser;

public interface SurveyUserService {
	
	DataMap selectUser(DataMap param)throws Exception;
	
	List<DataMap> selectListUserGroup(DataMap param)throws Exception;

	List<DataMap> selectListServeyGroup(DataMap param)throws Exception;

	TbUserGroup selectUserGroup(DataMap param)throws Exception;

	void insertUserGroup(TbUserGroup param)throws Exception;

	void updateUserGroup(TbUserGroup param)throws Exception;

	void deleteUserGroup(TbUserGroup param)throws Exception;

	List<DataMap> selectListUserGroupSurvey(DataMap param)throws Exception;

	List<DataMap> selectListUserGroupDegreeSurvey(DataMap param)throws Exception;

	TbSurveyUser selectUserGroupSurvey(DataMap param)throws Exception;

	TbSurveyUserDegree selectListUserGroupMaxDegreeSurvey(DataMap param)throws Exception;

	void insertUserGroupSurvey(TbSurveyUser param)throws Exception;

	void insertUserGroupDegreeSurvey(TbSurveyUserDegree param)throws Exception;

	void updateUserGroupSurvey(TbSurveyUser param)throws Exception;

	void deleteUserGroupSurvey(TbSurveyUser param)throws Exception;

	void deleteUserGroupDegreeSurvey(TbSurveyUserDegree param)throws Exception;

	void insertUserGroupSurveyEntry(TbSurveyUserEntry param)throws Exception;

	List<DataMap> selectListQuestionSummaryCd(DataMap param)throws Exception;

	List<DataMap> selectListSurveyCd(DataMap param)throws Exception;

	DataMap selectMaleCnt(DataMap param)throws Exception;

	DataMap selectAgeCnt(DataMap param)throws Exception;
	
	DataMap selectSchoolCnt(DataMap param)throws Exception;

	DataMap selectHealthCnt(DataMap param)throws Exception;

	List<DataMap> selectSubjectCnt(DataMap param)throws Exception;

	DataMap selectWayCnt(DataMap param)throws Exception;

	List<DataMap> selectDimensionAvg(DataMap param)throws Exception;

	List<DataMap> selectDimensionNps(DataMap param)throws Exception;

	void insertUserSection(TbSectionUser param)throws Exception;

	void updateUserSection(TbSectionUser param)throws Exception;

	int selectCntUserSection(TbSectionUser param)throws Exception;
	
	void insertUserQuestion(TbQuestionUser param)throws Exception;

	void updateUserQuestion(TbQuestionUser param)throws Exception;
	
	int selectCntUserQuestion(TbQuestionUser param)throws Exception;

	List<DataMap> selectListUserSection(TbSectionUser param)throws Exception;

	List<DataMap> selectListUserQuestion(TbQuestionUser param)throws Exception;

	List<DataMap> selectListUserEntryResult(DataMap param)throws Exception;

	void deleteUserEntryResult(TbQuestionUser param)throws Exception;

	void deleteUserEntryResultE(DataMap param)throws Exception;

	void insertUserEntryResult(TbSurveyUserEntryResult param)throws Exception;

	void insertUserEntryResultE(DataMap param)throws Exception;

	List<DataMap> selectListResultsLabelCnt(TbQuestionUser param)throws Exception;

	DataMap selectListResultsAgeCnt(TbQuestionUser param)throws Exception;

	List<DataMap> selectListstatisticsCd12(TbSectionUser param)throws Exception;
	
	List<DataMap> selectListstatisticsCd13(DataMap param)throws Exception;

	List<DataMap> selectListstatisticsCd14(DataMap param)throws Exception;
	
	List<DataMap> selectListstatisticsCd15(DataMap param)throws Exception;

	List<DataMap> selectListstatisticsCd16(DataMap param)throws Exception;

	List<String> selectListYearDegrees(DataMap param)throws Exception;


}
