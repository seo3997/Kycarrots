
package com.whomade.kycarrots.admin.menu.service;

import java.util.List;

import com.whomade.kycarrots.framework.common.object.DataMap;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : MenuMgtService.java
 * 3. Package  : egovframework.admin.menu.service
 * 4. Comment  : 
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   : 2017.12.22 오전 10:54:52
 * 7. 변경이력 : 
 *    이름     : 일자          : 근거자료   : 변경내용
 *    ------------------------------------------------------
 *    SooHyun.Seo : 2017.12.22 :            : 신규 개발.
 * </PRE>
 */ 
public interface MenuMgtService {
	
	/**
	 * <PRE>
	 * 1. MethodName : selectTotCntMenu
	 * 2. ClassName  : MenuService
	 * 3. Comment   : 메뉴 총 갯수 
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 4:11:03
	 * </PRE>
	 *   @return int
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	int selectTotCntMenu(DataMap param) throws Exception;
	
	/**
	 * <PRE>
	 * 1. MethodName : selectListMenu
	 * 2. ClassName  : MenuService
	 * 3. Comment   : 메뉴 리스트 조회 
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 4:11:20
	 * </PRE>
	 *   @return List
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	List selectListMenu(DataMap param) throws Exception;
	
	/**
	 * <PRE>
	 * 1. MethodName : insertMenu
	 * 2. ClassName  : MenuService
	 * 3. Comment   : 메뉴 등록 
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 4:11:41
	 * </PRE>
	 *   @return void
	 *   @param param
	 *   @throws Exception
	 */
	void insertMenu(DataMap param) throws Exception;
	
	/**
	 * <PRE>
	 * 1. MethodName : selectMenu
	 * 2. ClassName  : MenuService
	 * 3. Comment   : 메뉴 조회 
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 4:11:57
	 * </PRE>
	 *   @return DataMap
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	DataMap selectMenu(DataMap param) throws Exception;
	
	/**
	 * <PRE>
	 * 1. MethodName : selectMenuExistYn
	 * 2. ClassName  : MenuService
	 * 3. Comment   : 동일 메뉴 ID 존재 여부 조회 
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 4:12:17
	 * </PRE>
	 *   @return DataMap
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	DataMap selectExistYnMenu(DataMap param) throws Exception;
	
	/**
	 * <PRE>
	 * 1. MethodName : updateMenu
	 * 2. ClassName  : MenuService
	 * 3. Comment   : 메뉴 수정 
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 4:12:35
	 * </PRE>
	 *   @return void
	 *   @param param
	 *   @throws Exception
	 */
	void updateMenu(DataMap param) throws Exception;
	
	/**
	 * <PRE>
	 * 1. MethodName : updateMenuSub
	 * 2. ClassName  : MenuService
	 * 3. Comment   : 메뉴 하위 메뉴 sort 수정 
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 4:12:35
	 * </PRE>
	 *   @return void
	 *   @param param
	 *   @throws Exception
	 */
	void updateMenuSub(DataMap param) throws Exception;
	
	/**
	 * <PRE>
	 * 1. MethodName : deleteMenu
	 * 2. ClassName  : MenuService
	 * 3. Comment   : 메뉴 삭제 
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 4:12:49
	 * </PRE>
	 *   @return void
	 *   @param param
	 *   @throws Exception
	 */
	void deleteMenu(DataMap param) throws Exception;
	
	/**
	 * <PRE>
	 * 1. MethodName : selectSubMenuExistYn
	 * 2. ClassName  : MenuService
	 * 3. Comment   : 하위메뉴 존재 여부 조회 
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 4:13:07
	 * </PRE>
	 *   @return DataMap
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	DataMap selectExistYnSubMenu(DataMap param) throws Exception;
	
	/**
	 * <PRE>
	 * 1. MethodName : selectSubMenuExistSort
	 * 2. ClassName  : MenuService
	 * 3. Comment   : 선택 메뉴 정렬 순위 조회 
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 4:13:07
	 * </PRE>
	 *   @return DataMap
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	DataMap selectExistSortSubMenu(DataMap param) throws Exception;
	
	
	
}
