package com.whomade.kycarrots.mgt.sclas.service;

import java.util.ArrayList;
import java.util.List;

import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("sclasService")
public class SclasServiceImpl extends EgovAbstractServiceImpl implements SclasService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SclasServiceImpl.class);

	/** commonDao */
	@Resource(name="commonMybatisDao")
	private CommonMybatisDao commonMybatisDao;
	
	/**
	 * <PRE>
	 * 1. MethodName 	: selectListSclas
	 * 2. ClassName  	: SclasServiceImpl
	 * 3. Comment   	: 품목 리스트
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017. 4. 3. 오전 10:57:00
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListSclas(DataMap param) throws Exception {
		return  commonMybatisDao.selectList("mgt.sclas.selectListSclas", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : selectListItem
	 * 2. ClassName  	: SclasServiceImpl
	 * 3. Comment   	: 품목메뉴 리스트
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 4. 3. 오전 10:57:00
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListItem(DataMap param)throws Exception {
		List<DataMap> resultList = new ArrayList<DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.sclas.selectListItem", param);
		return resultList;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName	: selectMenu
	 * 2. ClassName		: SclasServiceImpl
	 * 3. Comment		: 메뉴정보조회
	 * 4. 작성자 				: SooHyun.Seo
	 * 5. 작성일				: 2017. 4. 3. 오전 11:29:19
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public DataMap selectOpCodeNm(DataMap param) throws Exception {
		return (DataMap) commonMybatisDao.selectOne("mgt.sclas.selectOpCodeNm", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName	: selectSclas
	 * 2. ClassName		: SclasServiceImpl
	 * 3. Comment		: 품목조회조회
	 * 4. 작성자 				: SooHyun.Seo
	 * 5. 작성일				: 2017. 4. 3. 오전 11:29:19
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public DataMap selectSclas(DataMap param) throws Exception {
		return (DataMap) commonMybatisDao.selectOne("mgt.sclas.selectSclas", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: updateSclas
	 * 2. ClassName  	: SclasServiceImpl
	 * 3. Comment   	: 품목 수정
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 4. 3. 오후 1:04:33
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateSclas(DataMap param) throws Exception {
		// 데이터 삭제
		commonMybatisDao.update("mgt.sclas.updateSclas", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: insertSclas
	 * 2. ClassName  	: SclasServiceImpl
	 * 3. Comment   	: 품목 등록
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 4. 3. 오후 1:04:33
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void insertSclas(DataMap param) throws Exception {
		commonMybatisDao.insert("mgt.sclas.insertSclas", param);
	}
	/**
	 * <PRE>
	 * 1. MethodName 	: insertSclas
	 * 2. ClassName  	: SclasServiceImpl
	 * 3. Comment   	: 품목 삭제
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 4. 3. 오후 1:04:33
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteSclas(DataMap param) throws Exception {
		//commonMybatisDao.delete("mgt.sclas.deleteSclas", param);
		commonMybatisDao.update("mgt.sclas.updateSclasUseYn", param);
	}

}
