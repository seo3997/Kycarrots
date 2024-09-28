package com.whomade.kycarrots.common.service;

import java.util.List;

import com.whomade.kycarrots.framework.common.object.DataMap;

public interface IncludeService {
	
	/**
	 * <PRE>
	 * 1. MethodName : selectTopMenuList
	 * 2. ClassName  : IncludeService
	 * 3. Comment   : 헤더의 상단 메뉴 리스트 조회 
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2015. 9. 7. 오전 11:07:42
	 * </PRE>
	 *   @return List
	 *   @param dataMap
	 *   @return
	 *   @throws Exception
	 */
	List selectTopMenuList(DataMap dataMap) throws Exception;
	
	/**
	 * <PRE>
	 * 1. MethodName : selectLeftMenuList
	 * 2. ClassName  : IncludeService
	 * 3. Comment   : 좌측 메뉴 리스트 조회 
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2015. 9. 7. 오전 11:08:05
	 * </PRE>
	 *   @return List
	 *   @param dataMap
	 *   @return
	 *   @throws Exception
	 */
	List selectLeftMenuList(DataMap dataMap) throws Exception;
	
	/**
	 * <PRE>
	 * 1. MethodName : selectMenuByUrl
	 * 2. ClassName  : IncludeService
	 * 3. Comment   : URL정보로 메뉴정보 조회
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2015. 9. 7. 오후 5:21:01
	 * </PRE>
	 *   @return DataMap
	 *   @param dataMap
	 *   @return
	 *   @throws Exception
	 */
	DataMap selectMenuByUrl(DataMap dataMap) throws Exception;
	
	
	/**
	 * <PRE>
	 * 1. MethodName : selectProjectList
	 * 2. ClassName  : IncludeService
	 * 3. Comment   : 프로젝트 목록 조회
	 * 4. 작성자    : 박재현
	 * 5. 작성일    : 2016. 6. 1. 오후 3:36:22
	 * </PRE>
	 *   @return List
	 *   @param dataMap
	 *   @return
	 *   @throws Exception
	 */
	List selectProjectList(DataMap dataMap) throws Exception;
	
}