package com.whomade.kycarrots.admin.user.service;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : UserServiceImpl.java
 * 3. Package  : egovframework.admin.user.web.service
 * 4. Comment  : 사용자 관리
 * 5. 작성자   : 서수현
 * 6. 작성일   : 2017.12.22. 오후 3:34:30
 * </PRE>
 */ 
@Service("userHistoryMgtService")
public class UserHistoryServiceImpl extends EgovAbstractServiceImpl implements UserHistoryMgtService {

	/** commonDao */
	@Resource(name="commonMybatisDao")
    private CommonMybatisDao commonMybatisDao;
	
	/**
	 * <PRE>
	 * 1. MethodName	: selectUserTotCnt
	 * 2. ClassName		: UserServiceImpl
	 * 3. Comment		: 사용자변경이력 총 개수 조회 
	 * 4. 작성자				: 서수현
	 * 5. 작성일				: 2017.12.22. 오후 3:34:30
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public int selectTotCntuserchgHst(DataMap param) throws Exception {
		return (Integer) commonMybatisDao.selectOne("admin.userchghst.selectTotCntuserchgHst", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName		: selectPageListUser
	 * 2. ClassName			: UserServiceImpl
	 * 3. Comment			: 사용자변경이력 페이지 리스트 조회
	 * 4. 작성자					: 서수현
	 * 5. 작성일					: 2017.12.22. 오후 3:34:29
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectPageListUserchgHst(DataMap param) throws Exception {
		return  commonMybatisDao.selectList("admin.userchghst.selectPageListUserchgHst", param);
	}

}
