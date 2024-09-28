package com.whomade.kycarrots.admin.author.service;

import java.util.List;

import com.whomade.kycarrots.framework.common.object.DataMap;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : CodeMgtService.java
 * 3. Package  : egovframework.admin.code.service
 * 4. Comment  : 
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   : 2017.12.22 오후 4:25:56
 * 7. 변경이력 : 
 *    이름     : 일자          : 근거자료   : 변경내용
 *    ------------------------------------------------------
 *    SooHyun.Seo : 2017.12.22 :            : 신규 개발.
 * </PRE>
 */ 
public interface CodeMgtService {
	

	/**
	 * <PRE>
	 * 1. MethodName : selectTotCntGroupCode
	 * 2. ClassName  : CodeService
	 * 3. Comment   : 그룹코드 카운트
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 3:46:42
	 * </PRE>
	 *   @return int
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	int selectTotCntGroupCode(DataMap param) throws Exception;
	

	/**
	 * <PRE>
	 * 1. MethodName : selectPageListGroupCode
	 * 2. ClassName  : CodeService
	 * 3. Comment   : 그룹코드 조회
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 3:46:50
	 * </PRE>
	 *   @return List
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	List selectPageListGroupCode(DataMap param) throws Exception;

	
	/**
	 * <PRE>
	 * 1. MethodName : selectListCode
	 * 2. ClassName  : CodeService
	 * 3. Comment   : 상세코드 리스트 
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 3:47:00
	 * </PRE>
	 *   @return List
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	List selectListCode(DataMap param) throws Exception;
	

	/**
	 * <PRE>
	 * 1. MethodName : selectGroupCode
	 * 2. ClassName  : CodeService
	 * 3. Comment   : 그룹코드 상세 
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 3:47:11
	 * </PRE>
	 *   @return DataMap
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	DataMap selectGroupCode(DataMap param) throws Exception;
	

	/**
	 * <PRE>
	 * 1. MethodName : deleteGroupCode
	 * 2. ClassName  : CodeService
	 * 3. Comment   : 그룹코드 및 상세코드 삭제
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 3:47:19
	 * </PRE>
	 *   @return void
	 *   @param param
	 *   @throws Exception
	 */
	void deleteGroupCode(DataMap param) throws Exception;

	
	/**
	 * <PRE>
	 * 1. MethodName : selectGroupCodeExistYn
	 * 2. ClassName  : CodeService
	 * 3. Comment   : 그룹코드 중복 체크
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 3:47:32
	 * </PRE>
	 *   @return DataMap
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	DataMap selectExistYnGroupCode(DataMap param) throws Exception;

	
	/**
	 * <PRE>
	 * 1. MethodName : insertGroupCode
	 * 2. ClassName  : CodeService
	 * 3. Comment   : 그룹코드 추가 
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 3:47:50
	 * </PRE>
	 *   @return void
	 *   @param param
	 *   @throws Exception
	 */
	void insertGroupCode(DataMap param) throws Exception;
	

	/**
	 * <PRE>
	 * 1. MethodName : updateCode
	 * 2. ClassName  : CodeService
	 * 3. Comment   : 그룹코드 및 상세코드 수정
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오후 3:47:58
	 * </PRE>
	 *   @return void
	 *   @param param
	 *   @throws Exception
	 */
	void updateCode(DataMap param) throws Exception;
	
}
