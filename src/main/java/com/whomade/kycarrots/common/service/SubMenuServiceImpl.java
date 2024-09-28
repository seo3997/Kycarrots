/**
 *
 * 1. FileName : LoginServiceImpl.java
 * 2. Package : egovframework.framework.security.service
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
 * 2. FileName  : SubMenuServiceImpl.java
 * 3. Package  : egovframework.common.service
 * 4. Comment  : 
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   : 2014. 1. 6. 오전 9:14:20
 * </PRE>
 */ 
@Service("subMenuService")
public class SubMenuServiceImpl extends EgovAbstractServiceImpl implements SubMenuService{
	
	/** commonDao */
	@Resource(name="commonMybatisDao")
    private CommonMybatisDao commonMybatisDao;

	/**
	 * <PRE>
	 * 1. MethodName : menuSelectList
	 * 2. ClassName  : SubMenuServiceImpl
	 * 3. Comment   : 메뉴 리스트 조회
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2014. 1. 6. 오전 9:23:13
	 * </PRE>
	 *   @param dataMap
	 *   @return
	 *   @throws Exception
	 */
	public List menuSelectList(DataMap dataMap) throws Exception {
		return (List) commonMybatisDao.selectList("admin.common.selectTopMenuList", dataMap);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : menuTreeSelectList
	 * 2. ClassName  : SubMenuServiceImpl
	 * 3. Comment   : 하위 메뉴 리스트 조회
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2014. 10. 1. 오후 6:05:12
	 * </PRE>
	 *   @param dataMap
	 *   @return
	 *   @throws Exception
	 */
	public List menuTreeSelectList(DataMap dataMap) throws Exception {
		return (List) commonMybatisDao.selectList("common.menuTreeSelectList", dataMap);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : menuSelect
	 * 2. ClassName  : SubMenuServiceImpl
	 * 3. Comment   : 메뉴 조회
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2014. 9. 4. 오후 1:59:20
	 * </PRE>
	 *   @param dataMap
	 *   @return
	 *   @throws Exception
	 */
	public DataMap menuSelect(DataMap dataMap) throws Exception {
		return (DataMap) commonMybatisDao.selectOne("common.menuSelect", dataMap);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : menuUrlSelect
	 * 2. ClassName  : SubMenuServiceImpl
	 * 3. Comment   : 메뉴조회(URL)
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2014. 10. 2. 오후 4:21:11
	 * </PRE>
	 *   @param dataMap
	 *   @return
	 *   @throws Exception
	 */
	public DataMap menuUrlSelect(DataMap dataMap) throws Exception {
		return (DataMap) commonMybatisDao.selectOne("common.menuUrlSelect", dataMap);
	}
}
