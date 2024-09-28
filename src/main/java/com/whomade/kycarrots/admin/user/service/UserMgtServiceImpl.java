package com.whomade.kycarrots.admin.user.service;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : UserServiceImpl.java
 * 3. Package  : egovframework.admin.user.web.service
 * 4. Comment  : 사용자 관리
 * 5. 작성자   : 서수현
 * 6. 작성일   : 2017.12.22. 오후 3:34:30
 * </PRE>
 */ 
@Service("userMgtService")
public class UserMgtServiceImpl extends EgovAbstractServiceImpl implements UserMgtService {

	/** commonDao */
	@Resource(name="commonMybatisDao")
    private CommonMybatisDao commonMybatisDao;
	
	/**
	 * <PRE>
	 * 1. MethodName 	: selecMaxUserNo
	 * 2. ClassName  	: UserServiceImpl
	 * 3. Comment   	: 사용자No 
	 * 4. 작성자    		: 서수현
	 * 5. 작성일    		: 2017.12.22. 오후 3:34:30
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public int selecMaxUserNo() throws Exception {
		return (Integer) commonMybatisDao.selectOne("admin.user.selecMaxUserNo");
	}
	/**
	 * <PRE>
	 * 1. MethodName 	: selectUserTotCnt
	 * 2. ClassName  	: UserServiceImpl
	 * 3. Comment   	: 사용자 총 개수 조회 
	 * 4. 작성자    		: 서수현
	 * 5. 작성일    		: 2017.12.22. 오후 3:34:30
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public int selectTotCntUser(DataMap param) throws Exception {
		return (Integer) commonMybatisDao.selectOne("admin.user.selectTotCntUser", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectPageListUser
	 * 2. ClassName  	: UserServiceImpl
	 * 3. Comment   	: 사용자 페이지 리스트 조회
	 * 4. 작성자    		: 서수현
	 * 5. 작성일    		: 2017.12.22. 오후 3:34:29
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List selectPageListUser(DataMap param) throws Exception {
		return (List) commonMybatisDao.selectList("admin.user.selectPageListUser", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: insertUser
	 * 2. ClassName  	: UserServiceImpl
	 * 3. Comment   	: 사용자 등록
	 * 4. 작성자    		: 서수현
	 * 5. 작성일    		: 2017.12.22. 오후 3:34:28
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void insertUser(DataMap param) throws Exception {
		commonMybatisDao.insert("admin.user.insertUser", param);
		
		List authList = param.getList("author_id");
		
		DataMap authMap = new DataMap();
		authMap.put("user_no", param.getString("user_no"));
		authMap.put("ss_user_no", param.getString("ss_user_no"));
		if(authList != null){
			for(int i = 0; i < authList.size(); i++){
				if("ROLE_EXT".equals( authList.get(i))) continue;
				authMap.put("author_id", authList.get(i));
				commonMybatisDao.insert("admin.user.insertAuthUser", authMap);
			}
		}
		
		
	}
	/**
	 * <PRE>
	 * 1. MethodName 	: insertUser
	 * 2. ClassName  	: UserServiceImpl
	 * 3. Comment   	: 사용자 등록
	 * 4. 작성자    		: 서수현
	 * 5. 작성일    		: 2017.12.22. 오후 3:34:28
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public Boolean	insertUserFront(DataMap param) {
		try{ 
			commonMybatisDao.insert("admin.user.insertUserFront", param);
			return true;
		}	catch (Exception e) {
			return false;
		}

	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectUser
	 * 2. ClassName  	: UserServiceImpl
	 * 3. Comment   	: 사용자 조회
	 * 4. 작성자    		: 서수현
	 * 5. 작성일    		: 2017.12.22. 오후 3:34:28
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public DataMap selectUser(DataMap param) throws Exception {
		return (DataMap) commonMybatisDao.selectOne("admin.user.selectUser", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateUser
	 * 2. ClassName  	: UserServiceImpl
	 * 3. Comment   	: 사용자 수정
	 * 4. 작성자    		: 서수현
	 * 5. 작성일    		: 2017.12.22. 오후 3:34:27
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateUser(DataMap param) throws Exception {
		commonMybatisDao.update("admin.user.updateUser", param);
		commonMybatisDao.delete("admin.user.deleteAuthUser", param);
		
		List authList = param.getList("author_id");

		DataMap authMap = new DataMap();
		authMap.put("user_no", param.getString("user_no"));
		authMap.put("ss_user_no", param.getString("ss_user_no"));
		if(authList != null){
			for(int i = 0; i < authList.size(); i++){
				if("ROLE_EXT".equals( authList.get(i))) continue;
				authMap.put("author_id", authList.get(i));
				commonMybatisDao.insert("admin.user.insertAuthUser", authMap);
			}
		}

		
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: updateUserInfo
	 * 2. ClassName  	: UserMgtServiceImpl
	 * 3. Comment   	: 사용자 정보만 변경
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2016. 6. 3. 오후 3:06:12
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateUserInfo(DataMap param) throws Exception {
		commonMybatisDao.update("admin.user.updateUserInfo", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateUserInfo
	 * 2. ClassName  	: UserMgtServiceImpl
	 * 3. Comment   	: 사용자 수정 변경내역저장
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2016. 6. 3. 오후 3:06:12
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void insertUserChgHst(DataMap param) throws Exception {
		commonMybatisDao.insert("admin.user.insertUserChgHst", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateUserInfo
	 * 2. ClassName  	: UserMgtServiceImpl
	 * 3. Comment   	: 사용자 패스워드수정
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2016. 6. 3. 오후 3:06:12
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void updateUserPsssword(DataMap param) throws Exception {
		commonMybatisDao.update("admin.user.updateUserPsssword", param);
	}
	
	
	/**
	 * <PRE>
	 * 1. MethodName 	: deleteUser
	 * 2. ClassName  	: UserServiceImpl
	 * 3. Comment   	: 사용자 삭제
	 * 4. 작성자    		: 서수현
	 * 5. 작성일    		: 2017.12.22. 오후 3:34:25
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteUser(DataMap param) throws Exception {
		commonMybatisDao.delete("admin.user.deleteAuthUser", param);  //권한을 삭제 하지 않는다.
    	commonMybatisDao.delete("admin.user.deleteUser", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectIdExistYn
	 * 2. ClassName  	: UserServiceImpl
	 * 3. Comment   	: 사용자 아이디 중복 검사
	 * 4. 작성자    		: 서수현
	 * 5. 작성일    		: 2017.12.22. 오후 3:34:23
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public String selectIdExistYn(DataMap param) throws Exception {
    	return (String) commonMybatisDao.selectOne("admin.user.selectIdExistYn", param);
    }

	/**
	 * <PRE>
	 * 1. MethodName 	: selectFindIdbyName
	 * 2. ClassName  	: UserServiceImpl
	 * 3. Comment   	: 아이디조회
	 * 4. 작성자    		: 서수현
	 * 5. 작성일    		: 2017.12.22. 오후 3:34:23
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public DataMap selectFindIdbyName(DataMap param) throws Exception {
    	return (DataMap) commonMybatisDao.selectOne("admin.user.selectFindIdbyName", param);
    }

	/**
	 * <PRE>
	 * 1. MethodName 	: selectFindIdbyId
	 * 2. ClassName  	: UserServiceImpl
	 * 3. Comment   	: 아이디조회
	 * 4. 작성자    		: 서수현
	 * 5. 작성일    		: 2017.12.22. 오후 3:34:23
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public DataMap selectFindIdbyId(DataMap param) throws Exception {
    	return (DataMap) commonMybatisDao.selectOne("admin.user.selectFindIdbyId", param);
    }


	/**
	 * <PRE>
	 * 1. MethodName 	: selectAuthorList
	 * 2. ClassName  	: UserServiceImpl
	 * 3. Comment   	: 권한 리스트 조회
	 * 4. 작성자    		: 서수현
	 * 5. 작성일    		: 2017.12.22. 오후 5:55:08
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List selectListAuthor(DataMap param) throws Exception {
		return (List) commonMybatisDao.selectList("admin.user.selectListAuthor", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectListAuthorUser
	 * 2. ClassName  	: UserServiceImpl
	 * 3. Comment   	: 사용자 권한 리스트 조회
	 * 4. 작성자    		: 서수현
	 * 5. 작성일    		: 2015. 9. 2. 오전 9:21:42
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List selectListAuthorUser(DataMap param) throws Exception {
		return (List) commonMybatisDao.selectList("admin.user.selectListAuthorUser", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectPageListAllUser
	 * 2. ClassName  	: UserServiceImpl
	 * 3. Comment   	: 사용자 리스트 전체 조회
	 * 4. 작성자    		: 서수현
	 * 5. 작성일    		: 2015. 11. 3. 오전 9:33:18
	 * </PRE>
	 *   @return List
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List selectPageListAllUser(DataMap param) throws Exception {
		return (List) commonMybatisDao.selectList("admin.user.selectPageListAllUser", param);
	}
}
