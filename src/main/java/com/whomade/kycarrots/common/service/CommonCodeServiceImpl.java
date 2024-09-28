/**
 * 
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
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : CommonCodeServiceImpl.java
 * 3. Package  : egovframework.common.service
 * 4. Comment  : 코드관리
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   : 2017.12.22. 오전 9:49:02
 * </PRE>
 */ 
@Service("commonCodeService")
public class CommonCodeServiceImpl extends EgovAbstractServiceImpl implements CommonCodeService{
	
	/** commonDao */
	@Resource(name="commonMybatisDao")
    private CommonMybatisDao commonMybatisDao;
	
	@Resource(name="egovMessageSource")
	private EgovMessageSource egovMessageSource;

	/**
	 * <PRE>
	 * 1. MethodName : codeSelectList
	 * 2. ClassName  : CommonCodeServiceImpl
	 * 3. Comment   : 코드 리스트 조회
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오전 9:55:40
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List selectCodeList(DataMap param) throws Exception {
//		param.put("ss_user_lang", param.getString("ss_user_lang", ""));		
		return (List) commonMybatisDao.selectList("common.selectCodeList", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName : codeSSelectList
	 * 2. ClassName  : CommonCodeServiceImpl
	 * 3. Comment    : 코드 리스트 조회
	 * 4. 작성자       : SooHyun.Seo
	 * 5. 작성일       : 2017.12.22. 오전 9:55:40
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List selectSCodeList(DataMap param) throws Exception {
		return (List) commonMybatisDao.selectList("common.selectSCodeList", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : selectSDCodeList
	 * 2. ClassName  : CommonCodeServiceImpl
	 * 3. Comment    : 코드세 리스트 조회
	 * 4. 작성자       : SooHyun.Seo
	 * 5. 작성일       : 2017.12.22. 오전 9:55:40
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List selectSDCodeList(DataMap param) throws Exception {
		return (List) commonMybatisDao.selectList("common.selectSDCodeList", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : selectCountryCodeList
	 * 2. ClassName  : CommonCodeServiceImpl
	 * 3. Comment   : 국가분류코드 리스트 조회
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2015. 12. 24. 오후 4:07:41
	 * </PRE>
	 *   @return List
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List selectCountryCodeList(DataMap param) throws Exception {
		return (List) commonMybatisDao.selectList("common.selectCountryCodeList", param);
	}
	
}
