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

@Service("sdclasService")
public class SdclasServiceImpl extends EgovAbstractServiceImpl implements SdclasService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SdclasServiceImpl.class);

	/** commonDao */
	@Resource(name="commonMybatisDao")
	private CommonMybatisDao commonMybatisDao;
	
	/**
	 * <PRE>
	 * 1. MethodName 	: selectListSdclas
	 * 2. ClassName  	: SdclasServiceImpl
	 * 3. Comment   	: 품목 리스트
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017. 4. 3. 오전 10:57:00
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List selectListSdclas(DataMap param) throws Exception {
		return (List) commonMybatisDao.selectList("mgt.sdclas.selectListSdclas", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName	: selectSdclas
	 * 2. ClassName		: SclasServiceImpl
	 * 3. Comment		: 품종조회
	 * 4. 작성자 				: SooHyun.Seo
	 * 5. 작성일				: 2017. 4. 3. 오전 11:29:19
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public DataMap selectSdclas(DataMap param) throws Exception {
		return (DataMap) commonMybatisDao.selectOne("mgt.sdclas.selectSdclas", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName : selectMenu
	 * 2. ClassName  : SdclasServiceImpl
	 * 3. Comment   : 메뉴정보조회
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017. 4. 3. 오전 11:29:19
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public DataMap selectItemName(DataMap param) throws Exception {
		return (DataMap) commonMybatisDao.selectOne("mgt.sdclas.selectItemName", param);
	}
	

	/**
	 * <PRE>
	 * 1. MethodName 	: insertSdclas
	 * 2. ClassName  	: SdclasServiceImpl
	 * 3. Comment   	: 품종 저장
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 4. 3. 오후 1:04:33
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void insertSdclas(DataMap param) throws Exception {
		commonMybatisDao.insert("mgt.sdclas.insertSdclas", param);
	}

	
	/**
	 * <PRE>
	 * 1. MethodName 	: updateSdclas
	 * 2. ClassName  	: SdclasServiceImpl
	 * 3. Comment   	: 품종 수정
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 4. 3. 오후 1:04:33
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateSdclas(DataMap param) throws Exception {
		commonMybatisDao.insert("mgt.sdclas.updateSdclas", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: insertSclas
	 * 2. ClassName  	: SclasServiceImpl
	 * 3. Comment   	: 품종 삭제
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 4. 3. 오후 1:04:33
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteSdclas(DataMap param) throws Exception {
		commonMybatisDao.update("mgt.sdclas.updateSdclasUseYn", param);
	}

}
