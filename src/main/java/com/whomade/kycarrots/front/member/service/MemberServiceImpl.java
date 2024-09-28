package com.whomade.kycarrots.front.member.service;

import java.util.ArrayList;
import java.util.List;

import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("memberService")
public class MemberServiceImpl extends EgovAbstractServiceImpl implements MemberService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberServiceImpl.class);

	/** commonDao */
	@Resource(name="commonMybatisDao")
	private CommonMybatisDao commonMybatisDao;


	/**
	 * <PRE>
	 * 1. MethodName 	: insertUser
	 * 2. ClassName  	: MemberServiceImpl
	 * 3. Comment   	: 사용자 등록
	 * 4. 작성자    		: 서수현
	 * 5. 작성일    		: 2020.08.06. 오후 3:34:28
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	@Transactional 
	public void insertUser(DataMap param) throws Exception {
		commonMybatisDao.insert("front.user.insertUser", param);
		
		DataMap authMap = new DataMap();
		authMap.put("user_no",    param.getString("user_no"));
		authMap.put("author_id", "ROLE_PUB");
		authMap.put("register_no",1);
		commonMybatisDao.insert("front.user.insertAuthUser", authMap);
		
	}
	/**
	 * <PRE>
	 * 1. MethodName 	: updateSetPass
	 * 2. ClassName  	: MemberServiceImpl
	 * 3. Comment   	: 패스워드 설정
	 * 4. 작성자    		: 서수현
	 * 5. 작성일    		: 2020.08.06. 오후 3:34:28
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateSetPass(DataMap param) throws Exception {
		commonMybatisDao.update("front.user.updateSetPass", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selecMaxUserNo
	 * 2. ClassName  	: MemberServiceImpl
	 * 3. Comment   	: 사용자No 
	 * 4. 작성자    		: 서수현
	 * 5. 작성일    		: 2020.08.06. 오후 3:34:28
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public int selecMaxUserNo() throws Exception {
		return (Integer) commonMybatisDao.selectOne("front.user.selecMaxUserNo");
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: selectIdExistYn
	 * 2. ClassName  	: MemberServiceImpl
	 * 3. Comment   	: 사용자 아이디 중복 검사
	 * 4. 작성자    		: 서수현
	 * 5. 작성일    		: 2020.08.06. 오후 3:34:28
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public String selectIdExistYn(DataMap param) throws Exception {
    	return (String) commonMybatisDao.selectOne("front.user.selectIdExistYn", param);
    }
	
	
}
