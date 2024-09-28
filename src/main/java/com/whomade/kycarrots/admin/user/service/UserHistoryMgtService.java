package com.whomade.kycarrots.admin.user.service;

import java.util.List;

import com.whomade.kycarrots.framework.common.object.DataMap;

/**
 * <PRE>
 * 1. ClassName	: 
 * 2. FileName  	: UserService.java
 * 3. Package 	: egovframework.admin.user.web.service
 * 4. Comment 	: 사용자 관리
 * 5. 작성자			: 서수현
 * 6. 작성일			: 2017.12.22. 오후 3:34:36
 * </PRE>
 */ 
public interface UserHistoryMgtService {
	
	/**
	 * <PRE>
	 * 1. MethodName	: selectTotCntUser
	 * 2. ClassName		: UserService
	 * 3. Comment		: 사용자 총 개수 조회 
	 * 4. 작성자				: 서수현
	 * 5. 작성일				: 2017.12.22. 오후 3:34:36
	 * </PRE>
	 *   @return int
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	int selectTotCntuserchgHst(DataMap param) throws Exception;

	/**
	 * <PRE>
	 * 1. MethodName	: selectPageListUser
	 * 2. ClassName		: UserService
	 * 3. Comment		: 사용자 페이지 리스트 조회
	 * 4. 작성자				: 서수현
	 * 5. 작성일				: 2017.12.22. 오후 3:34:35
	 * </PRE>
	 *   @return List
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	List<DataMap> selectPageListUserchgHst(DataMap param) throws Exception;

}
