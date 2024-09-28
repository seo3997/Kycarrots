/**
 * 
 *
 * 1. FileName : LoginServiceImpl.java
 * 2. Package : egovframework.framework.security.service
 * 3. Comment : 
 * 4. 작성자  : SooHyun.Seo
 * 5. 작성일  : 2017.12.22. 오전 10:05:19
 * 6. 변경이력 : 
 *    이름     : 일자          : 근거자료   : 변경내용
 *    ------------------------------------------------------
 *    SooHyun.Seo : 2017.12.22. :            : 신규 개발.
 */

package com.whomade.kycarrots.front.login.service;

import java.util.ArrayList;
import java.util.List;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("frontLoginService")
public class FrontLoginServiceImpl extends EgovAbstractServiceImpl implements FrontLoginService{
	
	/** commonDao */
	@Resource(name="commonMybatisDao")
	private CommonMybatisDao commonMybatisDao;
	
	@Resource(name = "egovMessageSource")
	private EgovMessageSource egovMessageSource;
	
	/**
	 * <PRE>
	 * 1. MethodName 	: selectUserInfo
	 * 2. ClassName  	: FrontLoginServiceImpl
	 * 3. Comment   	: 로그인 사용자 정보 조회
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2021. 08. 07. 오후 7:00:48
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public UserInfoVo selectUserInfo(DataMap param) throws Exception {
		// 사용자 조회
		UserInfoVo userInfoVo = (UserInfoVo) commonMybatisDao.selectOne("front.login.selectUserInfo", param);
		return userInfoVo;
	}

	
	/**
	 * <PRE>
	 * 1. MethodName 	: selectListUserauth
	 * 2. ClassName  	: FrontLoginServiceImpl
	 * 3. Comment   	: 사용자권한리스트
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2021. 08. 07. 오후 7:00:48
	 * </PRE>
	 *   @param model
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListUserauth(String pUserId)throws Exception {
		List<DataMap> resultList = new ArrayList<DataMap>();
		resultList = commonMybatisDao.selectList("front.login.selectListAuthor", pUserId);
		return resultList;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateUserLoginDt
	 * 2. ClassName  	: FrontLoginServiceImpl
	 * 3. Comment   	: 사용자권한리스트
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2021. 08. 07. 오후 7:00:48
	 * </PRE>
	 *   @param model
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public void updateUserLoginDt(String pUserNo)throws Exception {
		 commonMybatisDao.update("front.login.updateUserLoginDt", pUserNo);
	}
	
}
