/**
 *
 * 1. FileName : WebCommonService.java
 * 2. Package : egovframework.common.service
 * 3. Comment : 
 * 4. 작성자  : SooHyun.Seo
 * 5. 작성일  : 2017.12.22. 오전 10:05:03
 * 6. 변경이력 : 
 *    이름     : 일자          : 근거자료   : 변경내용
 *    ------------------------------------------------------
 *    SooHyun.Seo : 2017.12.22. :            : 신규 개발.
 */

package com.whomade.kycarrots.common.service;

import java.util.List;

import com.whomade.kycarrots.framework.common.object.DataMap;

public interface WebCommonService {

	/**
	 * <PRE>
	 * 1. MethodName : selectBanner
	 * 2. ClassName  : WebCommonService
	 * 3. Comment   : 배너 데이터 가져오기
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2014. 1. 6. 오전 9:22:48
	 * </PRE>
	 *   @return List
	 *   @param dataMap
	 *   @return
	 *   @throws Exception
	 */
	DataMap selectBanner(DataMap dataMap) throws Exception;
	
	/**
	 * <PRE>
	 * 1. MethodName : selectBannerList
	 * 2. ClassName  : WebCommonService
	 * 3. Comment   : 하단배너 리스트
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2014. 1. 23. 오후 8:53:39
	 * </PRE>
	 *   @return List
	 *   @param dataMap
	 *   @return
	 *   @throws Exception
	 */
	List selectBannerList(DataMap dataMap) throws Exception;
	
}
