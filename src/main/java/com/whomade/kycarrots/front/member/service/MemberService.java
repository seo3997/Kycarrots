package com.whomade.kycarrots.front.member.service;

import com.whomade.kycarrots.framework.common.object.DataMap;

import java.util.List;


public interface MemberService {

	/**
	 * <PRE>
	 * 1. MethodName 	: insertUser
	 * 2. ClassName  	: MemberService
	 * 3. Comment   	: 사용자 등록
	 * 4. 작성자    		: 서수현
	 * 5. 작성일    		: 2021.08.06. 오후 3:34:34
	 * </PRE>
	 * 
	 * @return void
	 * @param param
	 * @throws Exception
	 */
	void insertUser(DataMap param) throws Exception;

	/**
	 * <PRE>
	 * 1. MethodName 	: updateSetPass
	 * 2. ClassName  	: MemberService
	 * 3. Comment   	: 패스워드 설정
	 * 4. 작성자    		: 서수현
	 * 5. 작성일    		: 2021.08.06. 오후 3:34:34
	 * </PRE>
	 * 
	 * @return void
	 * @param param
	 * @throws Exception
	 */
	void updateSetPass(DataMap param) throws Exception;

	/**
	 * <PRE>
	 * 1. MethodName 	: selecMaxUserNo
	 * 2. ClassName  	: MemberService
	 * 3. Comment   	: 사용자No 
	 * 4. 작성자    		: 서수현
	 * 5. 작성일    		: 2021.08.06. 오후 3:34:34
	 * </PRE>
	 * 
	 * @return int
	 * @param param
	 * @return
	 * @throws Exception
	 */
	int selecMaxUserNo() throws Exception;

	/**
	 * <PRE>
	 * 1. MethodName 	: selectIdExistYn
	 * 2. ClassName  	: MemberService
	 * 3. Comment   	: 사용자 아이디 중복 검사
	 * 4. 작성자    		: 서수현
	 * 5. 작성일    		: 2017.12.22. 오후 3:34:32
	 * </PRE>
	 * 
	 * @return String
	 * @param param
	 * @return
	 * @throws Exception
	 */
	String selectIdExistYn(DataMap param) throws Exception;

}
