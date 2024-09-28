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

package com.whomade.kycarrots.admin.common.service;

import java.util.ArrayList;
import java.util.List;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : LoginServiceImpl.java
 * 3. Package  : egovframework.framework.security.service
 * 4. Comment  : 
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   : 2017.12.22. 오전 10:05:19
 * </PRE>
 */
@Service("loginService")
public class LoginServiceImpl extends EgovAbstractServiceImpl implements LoginService{
	
	/** commonDao */
	@Resource(name="commonMybatisDao")
	private CommonMybatisDao commonMybatisDao;
	
	@Resource(name = "egovMessageSource")
	private EgovMessageSource egovMessageSource;
	
	/**
	 * <PRE>
	 * 1. MethodName 	: selectUserInfo
	 * 2. ClassName  	: LoginServiceImpl
	 * 3. Comment   	: 로그인 사용자 정보 조회
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2013. 9. 4. 오후 7:00:48
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public UserInfoVo selectUserInfo(DataMap param) throws Exception {
		// 사용자 조회
		UserInfoVo userInfoVo = (UserInfoVo) commonMybatisDao.selectOne("login.selectUserInfo", param);
		return userInfoVo;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectUserAreaName
	 * 2. ClassName  	: LoginServiceImpl
	 * 3. Comment   	: 로그인 사용자지역  정보 조회
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2013. 9. 4. 오후 7:00:48
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public String selectUserAreaName(DataMap param) throws Exception {
		String  userAreaName = (String) commonMybatisDao.selectOne("login.selectUserAreaName", param);
		return userAreaName;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: selectListUserauth
	 * 2. ClassName  	: LoginServiceImpl
	 * 3. Comment   	: 사용자권한리스트
	 * 4. 작성자    		: hsbae
	 * 5. 작성일    		: 2017. 12. 5. 오후 4:08:41
	 * </PRE>
	 *   @param model
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List selectListUserauth(String pUserId)throws Exception {
		List resultList = new ArrayList();
		resultList = (List) commonMybatisDao.selectList("login.selectListAuthor", pUserId);
		return resultList;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateUserLoginDt
	 * 2. ClassName  	: LoginServiceImpl
	 * 3. Comment   	: 로그인 시간 업데이트
	 * 4. 작성자    		: hsbae
	 * 5. 작성일    		: 2017. 12. 5. 오후 4:08:41
	 * </PRE>
	 *   @param model
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public void updateUserLoginDt(String pUserNo)throws Exception {
		 commonMybatisDao.update("login.updateUserLoginDt", pUserNo);
	}
	
	
}
