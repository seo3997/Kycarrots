package com.whomade.kycarrots.common.service;

import java.util.List;

import com.whomade.kycarrots.framework.common.object.DataMap;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : CommonCodeService.java
 * 3. Package  : egovframework.common.service
 * 4. Comment  : 코드관리
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   : 2017.12.22. 오전 9:49:09
 * </PRE>
 */ 
public interface CommonCodeService {

	/**
	 * <PRE>
	 * 1. MethodName 	: codeSelectList
	 * 2. ClassName  	: CommonCodeService
	 * 3. Comment   	: 코드리스트 조회
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2017.12.22. 오전 9:55:07
	 * </PRE>
	 *   @return List
	 *   @param dataMap
	 *   @return
	 *   @throws Exception
	 */
	List selectCodeList(DataMap dataMap) throws Exception;
	/**
	 * <PRE>
	 * 1. MethodName 	: codeSSelectList
	 * 2. ClassName  	: CommonCodeService
	 * 3. Comment   	: 코드소분류리스트 조회
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2017.12.22. 오전 9:55:07
	 * </PRE>
	 *   @return List
	 *   @param dataMap
	 *   @return
	 *   @throws Exception
	 */
	List selectSCodeList(DataMap dataMap) throws Exception;
	/**
	 * <PRE>
	 * 1. MethodName 	: selectSDCodeList
	 * 2. ClassName  	: CommonCodeService
	 * 3. Comment   	: 코드세분류리스트 조회
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2017.12.22. 오전 9:55:07
	 * </PRE>
	 *   @return List
	 *   @param dataMap
	 *   @return
	 *   @throws Exception
	 */
	List selectSDCodeList(DataMap dataMap) throws Exception;
}
