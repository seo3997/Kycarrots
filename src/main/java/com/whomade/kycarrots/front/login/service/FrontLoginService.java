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

package com.whomade.kycarrots.front.login.service;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.framework.common.object.DataMap;

import java.util.List;


public interface FrontLoginService {
	UserInfoVo selectUserInfo(DataMap param) throws Exception;
	List<DataMap> selectListUserauth(String pUserId)throws Exception;
	void updateUserLoginDt(String pUserNo)throws Exception;
}
