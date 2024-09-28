
package com.whomade.kycarrots.admin.authmenu.service;

import java.util.List;

import com.whomade.kycarrots.framework.common.object.DataMap;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : AuthMenuMgtService.java
 * 3. Package  : egovframework.admin.authmenu.service
 * 4. Comment  : 
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   : 2017.12.22. 오후 4:04:16
 * 7. 변경이력 : 
 *    이름     : 일자          : 근거자료   : 변경내용
 *    ------------------------------------------------------
 *    SooHyun.Seo : 2017.12.22. :            : 신규 개발.
 * </PRE>
 */ 
public interface AuthMenuMgtService {
	
	/**
	 * <PRE>
	 * 1. MethodName : selectTotCntAuthMenu
	 * 2. ClassName  : AuthMenuService
	 * 3. Comment   : 권한별메뉴 총 갯수
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 2:30:16
	 * </PRE>
	 *   @return int
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	int selectTotCntAuthMenu(DataMap param) throws Exception;
	
	/**
	 * <PRE>
	 * 1. MethodName : selectPageListAuthMenu
	 * 2. ClassName  : AuthMenuService
	 * 3. Comment   : 권한별메뉴 페이지 리스트 조회
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 1:54:40
	 * </PRE>
	 *   @return List
	 *   @param dataMap
	 *   @return
	 *   @throws Exception
	 */
	List selectPageListAuthMenu(DataMap param) throws Exception;
	
	/**
	 * <PRE>
	 * 1. MethodName : selectListAuthMenu
	 * 2. ClassName  : AuthMenuService
	 * 3. Comment   : 권한별메뉴 리스트 조회
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 1:54:40
	 * </PRE>
	 *   @return List
	 *   @param dataMap
	 *   @return
	 *   @throws Exception
	 */
	List selectListAuthMenu(DataMap param) throws Exception;
	
	
	/**
	 * <PRE>
	 * 1. MethodName : insertAuthMenu
	 * 2. ClassName  : AuthMenuService
	 * 3. Comment   : 권한별메뉴 등록
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 2:19:03
	 * </PRE>
	 *   @return void
	 *   @param param
	 *   @throws Exception
	 */
	void insertAuthMenu(DataMap param) throws Exception;
	
	DataMap selectInfoAuthMenu(DataMap param) throws Exception;
	
}
