package com.whomade.kycarrots.common.service;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("includeService")
public class IncludeServiceImpl extends EgovAbstractServiceImpl implements
		IncludeService {

	/** commonDao */
	@Resource(name="commonMybatisDao")
    private CommonMybatisDao commonMybatisDao;
	
	public List selectTopMenuList(DataMap dataMap) throws Exception {
		return commonMybatisDao.selectList("common.selectTopMenuList", dataMap);
	}

	public List selectLeftMenuList(DataMap dataMap) throws Exception {
		return commonMybatisDao.selectList("common.selectLeftMenuList", dataMap);
	}

	public DataMap selectMenuByUrl(DataMap dataMap) throws Exception {
		return (DataMap)commonMybatisDao.selectOne("common.selectMenuByUrl", dataMap);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : selectProjectList
	 * 2. ClassName  : IncludeServiceImpl
	 * 3. Comment   : 프로젝트 목록 조회
	 * 4. 작성자    : 박재현
	 * 5. 작성일    : 2016. 6. 1. 오후 3:36:31
	 * </PRE>
	 *   @param dataMap
	 *   @return
	 *   @throws Exception
	 */
	public List selectProjectList(DataMap dataMap) throws Exception {
		return (List) commonMybatisDao.selectList("common.selectProjectList", dataMap);
	}
}
