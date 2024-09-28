package com.whomade.kycarrots.front.main.service;

import java.util.ArrayList;
import java.util.List;

import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("frontMainService")
public class FrontMainServiceImpl extends EgovAbstractServiceImpl implements FrontMainService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FrontMainServiceImpl.class);

	/** commonDao */
	@Resource(name="commonMybatisDao")
	private CommonMybatisDao commonMybatisDao;


	/**
	 * <PRE>
	 * 1. MethodName 	: selectPlanFList
	 * 2. ClassName  	: FrontMainServiceImpl
	 * 3. Comment   	: PlanF리스트
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2021. 08. 09. 오후 4:10:00
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectPlanFList(DataMap param)throws Exception {
		List<DataMap> resultList = new ArrayList<DataMap>();
		resultList =  commonMybatisDao.selectList("front.main.selectPlanFList", param);
		return resultList;
	}

	/**
	 * <PRE>
	 * 1. MethodName : selectPlanF
	 * 2. ClassName  	: FrontMainServiceImpl
	 * 3. Comment   	: PlanF 상세
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2021. 08. 09. 오후 4:10:00
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public DataMap selectPlanF(DataMap param)throws Exception {
		return (DataMap) commonMybatisDao.selectOne("front.main.selectPlanF", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: insertPlanF
	 * 2. ClassName  	: FrontMainServiceImpl
	 * 3. Comment   	: PlanF등록
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2021. 08. 09. 오후 4:10:00
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void insertPlanF(DataMap param)throws Exception {
		commonMybatisDao.insert("front.main.insertPlanF", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: updatePlanF
	 * 2. ClassName  	: FrontMainServiceImpl
	 * 3. Comment   	: PlanF수정
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2021. 08. 09. 오후 4:10:00
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updatePlanF(DataMap param)throws Exception {
		commonMybatisDao.update("front.main.updatePlanF", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: deletePlanF
	 * 2. ClassName  	: FrontMainServiceImpl
	 * 3. Comment   	: PlanF삭제
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2021. 08. 09. 오후 4:10:00
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deletePlanF(DataMap param)throws Exception {
		commonMybatisDao.update("front.main.deletePlanF", param);
	}

}
