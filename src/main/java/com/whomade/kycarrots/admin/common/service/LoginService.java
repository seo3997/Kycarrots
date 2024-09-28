/**
 * 
 *
 * 1. FileName : LoginService.java
 * 2. Package : egovframework.framework.security.service
 * 3. Comment : 
 * 4. 작성자  : SooHyun.Seo
 * 5. 작성일  : 2017.12.22. 오전 10:05:03
 * 6. 변경이력 : 
 *    이름     : 일자          : 근거자료   : 변경내용
 *    ------------------------------------------------------
 *    SooHyun.Seo : 2017.12.22. :            : 신규 개발.
 */

package com.whomade.kycarrots.admin.common.service;

import java.util.List;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.framework.common.object.DataMap;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : LoginService.java
 * 3. Package  : egovframework.front.common.service
 * 4. Comment  : 
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   : 2017.12.22. 오전 10:05:03
 * </PRE>
 */

public interface LoginService {

	
	/**
	 * <PRE>
	 * 1. MethodName : selectUserInfo
	 * 2. ClassName  : LoginService
	 * 3. Comment   : 로그인 사용자 정보 조회
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 9. 4. 오후 7:00:56
	 * </PRE>
	 *   @return UserInfoVo
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	UserInfoVo selectUserInfo(DataMap param) throws Exception;

	List selectListUserauth(String pUserId)throws Exception;
	
	void updateUserLoginDt(String pUserNo)throws Exception;

	String selectUserAreaName(DataMap param) throws Exception;		
	
}
