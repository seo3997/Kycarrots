package com.whomade.kycarrots.mgt.survey.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.page.util.pageNavigationUtil;
import com.whomade.kycarrots.framework.common.util.file.AtFileMngUtil;
import com.whomade.kycarrots.mgt.survey.vo.TbQuestionUser;
import com.whomade.kycarrots.mgt.survey.vo.TbSectionUser;
import com.whomade.kycarrots.mgt.survey.vo.TbSurveyUser;
import com.whomade.kycarrots.mgt.survey.vo.TbSurveyUserDegree;
import com.whomade.kycarrots.mgt.survey.vo.TbSurveyUserEntry;
import com.whomade.kycarrots.mgt.survey.vo.TbSurveyUserEntryResult;
import com.whomade.kycarrots.mgt.survey.vo.TbUserGroup;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("surveyUserService")
public class SurveyUserServiceImpl extends EgovAbstractServiceImpl implements SurveyUserService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SurveyServiceImpl.class);

	/** commonDao */
	@Resource(name="commonMybatisDao")
	private CommonMybatisDao commonMybatisDao;
	 
	@Resource(name="AtFileMngUtil")
	private AtFileMngUtil atFileMngUtil;	

	/**
	 * <PRE>
	 * 1. MethodName : selectUser
	 * 2. ClassName  : SurveyUserServiceImpl
	 * 3. Comment   : 사용자 조회
	 * 4. 작성자    : 서수현
	 * 5. 작성일    : 2017.12.22. 오후 3:34:28
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public DataMap selectUser(DataMap param) throws Exception {
		return (DataMap) commonMybatisDao.selectOne("mgt.surveyuser.selectUser", param);
	}
	
	
	/**
	 * <PRE>
	 * 1. MethodName 	: selectListServeyUserGroup
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	: 강사그룹리스트
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListUserGroup(DataMap param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.surveyuser.selectListUserGroup", param);
		
		return resultList;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectListServeyUserGroup
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	: 강사그룹리스트
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListServeyGroup(DataMap param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.surveyuser.selectListServeyGroup", param);
		
		return resultList;
	}

	/**
	 * <PRE>
	 * 1. MethodName : selectUserGroup
	 * 2. ClassName    : SurveyUserServiceImpl
	 * 3. Comment       : 강사그룹상세
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 3. 13. 오후 4:10:00
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public TbUserGroup selectUserGroup(DataMap param)throws Exception {
		return (TbUserGroup) commonMybatisDao.selectOne("mgt.surveyuser.selectUserGroup", param);
	}

	
	/**
	 * <PRE>
	 * 1. MethodName 	: insertUserGroup
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	: 강사그룹 등록
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void insertUserGroup(TbUserGroup param)throws Exception {
		commonMybatisDao.insert("mgt.surveyuser.insertUserGroup", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateUserGroup
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	: 강사그룹 수정
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateUserGroup(TbUserGroup param)throws Exception {
		commonMybatisDao.insert("mgt.surveyuser.updateUserGroup", param);
	}


	/**
	 * <PRE>
	 * 1. MethodName 	: deleteUserGroup
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	: 강사그룹l 삭제
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteUserGroup(TbUserGroup param)throws Exception {
		commonMybatisDao.delete("mgt.surveyuser.deleteUserGroup", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectListServeyUserGroup
	 * 2. ClassName  	: selectListUserGroupSurvey
	 * 3. Comment   	: 강사진단지리스트
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListUserGroupSurvey(DataMap param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.surveyuser.selectListUserGroupSurvey", param);
		
		return resultList;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectListUserGroupDegreeSurvey
	 * 2. ClassName  	: selectListUserGroupSurvey
	 * 3. Comment     	: 강사진단지년도 차수리스트
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListUserGroupDegreeSurvey(DataMap param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.surveyuser.selectListUserGroupDegreeSurvey", param);
		
		return resultList;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : selectUserGroupSurvey
	 * 2. ClassName    : SurveyUserServiceImpl
	 * 3. Comment       : 강사진단지상세
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 3. 13. 오후 4:10:00
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public TbSurveyUser selectUserGroupSurvey(DataMap param)throws Exception {
		return (TbSurveyUser) commonMybatisDao.selectOne("mgt.surveyuser.selectUserGroupSurvey", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName : selectListUserGroupMaxDegreeSurvey
	 * 2. ClassName    : SurveyUserServiceImpl
	 * 3. Comment       : 강사진단지max 년도상세상세
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 3. 13. 오후 4:10:00
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public TbSurveyUserDegree selectListUserGroupMaxDegreeSurvey(DataMap param)throws Exception {
		return (TbSurveyUserDegree) commonMybatisDao.selectOne("mgt.surveyuser.selectListUserGroupMaxDegreeSurvey", param);
	}

	
	/**
	 * <PRE>
	 * 1. MethodName 	: insertUserGroupSurvey
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	: 강사진단지 등록
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void insertUserGroupSurvey(TbSurveyUser param)throws Exception {
		commonMybatisDao.insert("mgt.surveyuser.insertUserGroupSurvey", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: insertUserGroupSurvey
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	    : 강사진단지 년도차수 등록
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void insertUserGroupDegreeSurvey(TbSurveyUserDegree param)throws Exception {
		commonMybatisDao.insert("mgt.surveyuser.insertUserGroupDegreeSurvey", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: updateServeyUser
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	: 강사진단지 수정
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateUserGroupSurvey(TbSurveyUser param)throws Exception {
		commonMybatisDao.insert("mgt.surveyuser.updateUserGroupSurvey", param);
	}


	/**
	 * <PRE>
	 * 1. MethodName 	: deleteServeyUser
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	: 강사진단지 삭제
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteUserGroupSurvey(TbSurveyUser param)throws Exception {
		commonMybatisDao.delete("mgt.surveyuser.deleteUserGroupSurvey", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: deleteUserGroupDegreeSurvey
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	   : 강사진단지년도 차수 삭제
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteUserGroupDegreeSurvey(TbSurveyUserDegree param)throws Exception {
		commonMybatisDao.delete("mgt.surveyuser.deleteUserGroupDegreeSurvey", param);
	}
	
	
	/**
	 * <PRE>
	 * 1. MethodName 	: insertUserGroupSurveyEntry
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	: 강사진단지답변 등록
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void insertUserGroupSurveyEntry(TbSurveyUserEntry param)throws Exception {
		commonMybatisDao.insert("mgt.surveyuser.insertUserGroupSurveyEntry", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectListQuestionSummaryCd
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 설문유형통계
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListQuestionSummaryCd(DataMap param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.surveyuser.selectListQuestionSummaryCd", param);

		return resultList;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectListQuestionSummaryCd
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 설문유형통계
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListSurveyCd(DataMap param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.surveyuser.selectListSurveyCd", param);

		return resultList;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectMaleCnt
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 성별Cnt
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public DataMap selectMaleCnt(DataMap param)throws Exception {
		
		DataMap resultMap = new DataMap();
		resultMap =  commonMybatisDao.selectOne("mgt.surveyuser.selectMaleCnt", param);

		return resultMap;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectMaleCnt
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 연령Cnt
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public DataMap selectAgeCnt(DataMap param)throws Exception {
		
		DataMap resultMap = new DataMap();
		resultMap =  commonMybatisDao.selectOne("mgt.surveyuser.selectAgeCnt", param);

		return resultMap;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectSchoolCnt
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 학력Cnt
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public DataMap selectSchoolCnt(DataMap param)throws Exception {
		
		DataMap resultMap = new DataMap();
		resultMap =  commonMybatisDao.selectOne("mgt.surveyuser.selectSchoolCnt", param);

		return resultMap;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: selectHealthCnt
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 건강상태Cnt
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public DataMap selectHealthCnt(DataMap param)throws Exception {
		
		DataMap resultMap = new DataMap();
		resultMap =  commonMybatisDao.selectOne("mgt.surveyuser.selectHealthCnt", param);

		return resultMap;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: selectSubjectCnt
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   		: 진료과목 외래Cnt
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectSubjectCnt(DataMap param)throws Exception {
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.surveyuser.selectSubjectCnt", param);
		return resultList;
	}

	
	/**
	 * <PRE>
	 * 1. MethodName 	: selectWayCnt
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 경로Cnt
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public DataMap selectWayCnt(DataMap param)throws Exception {
		
		DataMap resultMap = new DataMap();
		resultMap =  commonMybatisDao.selectOne("mgt.surveyuser.selectWayCnt", param);

		return resultMap;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectDimensionAvg
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: 차수 평균
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectDimensionAvg(DataMap param)throws Exception {
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.surveyuser.selectDimensionAvg", param);

		return resultList;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectDimensionNps
	 * 2. ClassName  	: SurveyServiceImpl
	 * 3. Comment   	: Nps 평균
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectDimensionNps(DataMap param)throws Exception {
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.surveyuser.selectDimensionNps", param);

		return resultList;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: insertUserSection
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	: 섹션항목등록
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void insertUserSection(TbSectionUser param)throws Exception {
		commonMybatisDao.insert("mgt.surveyuser.insertUserSection", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateUserSection
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	: 섹션항목등록
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateUserSection(TbSectionUser param)throws Exception {
		commonMybatisDao.update("mgt.surveyuser.updateUserSection", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: insertUserQuestion
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	: 질문항목등록
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void insertUserQuestion(TbQuestionUser param)throws Exception {
		commonMybatisDao.insert("mgt.surveyuser.insertUserQuestion", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateUserQuestion
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	: 질문항목등록
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateUserQuestion(TbQuestionUser param)throws Exception {
		commonMybatisDao.update("mgt.surveyuser.updateUserQuestion", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : selectCntUserSection
	 * 2. ClassName    : SurveyUserServiceImpl
	 * 3. Comment       : 섹션항목여부
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 3. 13. 오후 4:10:00
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public int selectCntUserSection(TbSectionUser param)throws Exception {
		return (Integer) commonMybatisDao.selectOne("mgt.surveyuser.selectCntUserSection", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName : selectCntQuestion
	 * 2. ClassName    : SurveyUserServiceImpl
	 * 3. Comment       : 질문항목여부
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 3. 13. 오후 4:10:00
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public int selectCntUserQuestion(TbQuestionUser param)throws Exception {
		return (Integer) commonMybatisDao.selectOne("mgt.surveyuser.selectCntUserQuestion", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: selectListUserSection
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	: 섹션리스트
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListUserSection(TbSectionUser param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.surveyuser.selectListUserSection", param);
		
		return resultList;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectListUserQuestion
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	: 질문리스트
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListUserQuestion(TbQuestionUser param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.surveyuser.selectListUserQuestion", param);
		
		return resultList;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectListUserEntryResult
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	: 만족도그래프
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListUserEntryResult(DataMap param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.surveyuser.selectListUserEntryResult", param);
		
		return resultList;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: TbSurveyUserEntryResult
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	: 유저Entry삭제
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteUserEntryResult(TbQuestionUser param)throws Exception {
		commonMybatisDao.delete("mgt.surveyuser.deleteUserEntryResult", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: deleteUserEntryResultE
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	    : 유저Entry진료과목삭제
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteUserEntryResultE(DataMap param)throws Exception {
		commonMybatisDao.delete("mgt.surveyuser.deleteUserEntryResultE", param);
	}

	
	/**
	 * <PRE>
	 * 1. MethodName 	: insertUserEntryResult
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	: 유저Entry 등록
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */

	public void insertUserEntryResult(TbSurveyUserEntryResult param)throws Exception {
		commonMybatisDao.insert("mgt.surveyuser.insertUserEntryResult", param);
	}
	/**
	 * <PRE>
	 * 1. MethodName 	: insertUserEntryResultE
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	    : 유저Entry 진료과목등록
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void insertUserEntryResultE(DataMap param)throws Exception {
		commonMybatisDao.insert("mgt.surveyuser.insertUserEntryResultE", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: selectListResultsLabelCnt
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	: 유저Entry 건수
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListResultsLabelCnt(TbQuestionUser param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.surveyuser.selectListResultsLabelCnt", param);
		
		return resultList;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectListResultsAgeCnt
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	    : 유저나이 Entry 건수
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public DataMap selectListResultsAgeCnt(TbQuestionUser param)throws Exception {

		DataMap resultMap = new DataMap();
		resultMap =  commonMybatisDao.selectOne("mgt.surveyuser.selectListResultsAgeCnt", param);
		
		return resultMap;
	}
	
	
	/**
	 * <PRE>
	 * 1. MethodName 	: selectListstatisticsCd12
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	    : 환자경험차원평가
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListstatisticsCd12(TbSectionUser param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.surveyuser.selectListstatisticsCd12", param);
		
		return resultList;
	}

	
	/**
	 * <PRE>
	 * 1. MethodName 	: selectListstatisticsCd13
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	    : 3.환자경험 조사차수별 비교평가 ①비교평가 종합만족도
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListstatisticsCd13(DataMap param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.surveyuser.selectListstatisticsCd13", param);
		
		return resultList;

	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectListstatisticsCd14
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	    :3.환자경험 조사차수별 비교평가 ②추천지수(NPS)
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListstatisticsCd14(DataMap param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.surveyuser.selectListstatisticsCd14", param);
		
		return resultList;

	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: selectListstatisticsCd15
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	    : 환자경험 조사차수별 비교평가 ③체감만족도
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListstatisticsCd15(DataMap param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.surveyuser.selectListstatisticsCd15", param);
		
		return resultList;

	}
	/**
	 * <PRE>
	 * 1. MethodName 	: selectListstatisticsCd16
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	    : 3.환자경험 조사차수별 비교평가 ④차원만족도
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2019. 5. 11. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListstatisticsCd16(DataMap param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.surveyuser.selectListstatisticsCd16", param);
		
		return resultList;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectListYearDegrees
	 * 2. ClassName  	: SurveyUserServiceImpl
	 * 3. Comment   	: 조사차수별 평가를 하기 위해서 현재 차수포함  이전 4개의 조차 차수
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2020.08.15 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<String> selectListYearDegrees(DataMap param)throws Exception {
		
		List<String> resultList = new ArrayList <String>();
		resultList =  commonMybatisDao.selectList("mgt.surveyuser.selectListYearDegrees", param);
		
		return resultList;

	}
	
}
