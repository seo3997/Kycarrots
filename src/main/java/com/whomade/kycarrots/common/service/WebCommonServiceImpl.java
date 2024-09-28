/**
 *
 * 1. FileName : WebCommonServiceImpl.java
 * 2. Package : egovframework.common.service
 * 3. Comment : 
 * 4. 작성자  : SooHyun.Seo
 * 5. 작성일  : 2017.12.22. 오전 10:05:19
 * 6. 변경이력 : 
 *    이름     : 일자          : 근거자료   : 변경내용
 *    ------------------------------------------------------
 *    SooHyun.Seo : 2017.12.22. :            : 신규 개발.
 */

package com.whomade.kycarrots.common.service;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : WebCommonServiceImpl.java
 * 3. Package  : egovframework.common.service
 * 4. Comment  : 
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   : 2014. 1. 6. 오전 9:14:20
 * </PRE>
 */ 
@Service("webCommonService")
public class WebCommonServiceImpl extends EgovAbstractServiceImpl implements WebCommonService{
	
	/** commonDao */
	@Resource(name="commonMybatisDao")
    private CommonMybatisDao commonMybatisDao;

	
	/**
	 * <PRE>
	 * 1. MethodName : selectBanner
	 * 2. ClassName  : WebCommonServiceImpl
	 * 3. Comment   : 배너 데이터 가져오기
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2014. 1. 22. 오후 8:09:47
	 * </PRE>
	 *   @param dataMap
	 *   @return
	 *   @throws Exception
	 */
	public DataMap selectBanner(DataMap dataMap) throws Exception {
		return (DataMap) commonMybatisDao.selectOne("common.selectBanner", dataMap);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : selectBannerList
	 * 2. ClassName  : WebCommonServiceImpl
	 * 3. Comment   : 배너 리스트
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2014. 1. 23. 오후 8:54:38
	 * </PRE>
	 *   @param dataMap
	 *   @return
	 *   @throws Exception
	 */
	public List selectBannerList(DataMap dataMap) throws Exception {
		return (List) commonMybatisDao.selectList("common.selectBannerList", dataMap);
	}
}
