/**
 * 
 *
 * 1. FileName : MenuMgtServiceImpl.java
 * 2. Package : egovframework.admin.menu.service
 * 3. Comment : 
 * 4. 작성자  : SooHyun.Seo
 * 5. 작성일  : 2017.12.22. 오전 10:05:19
 * 6. 변경이력 : 
 *    이름     : 일자          : 근거자료   : 변경내용
 *    ------------------------------------------------------
 *    SooHyun.Seo : 2017.12.22. :            : 신규 개발.
 */

package com.whomade.kycarrots.admin.menu.service;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("menuMgtService")
public class MenuMgtServiceImpl extends EgovAbstractServiceImpl implements MenuMgtService{
	
	/** commonDao */
	@Resource(name="commonMybatisDao")
    private CommonMybatisDao commonMybatisDao;
    
    
    /**
     * <PRE>
     * 1. MethodName : selectTotCntMenu
     * 2. ClassName  : MenuServiceImpl
     * 3. Comment   : 메뉴 총 개수 
     * 4. 작성자    : SooHyun.Seo
     * 5. 작성일    : 2017.12.22. 오후 4:06:30
     * </PRE>
     *   @return int
     *   @param param
     *   @return
     *   @throws Exception
     */
    public int selectTotCntMenu(DataMap param) throws Exception {
    	return (Integer) commonMybatisDao.selectOne("admin.menu.selectTotCntMenu", param);
    } 
    
    /**
     * <PRE>
     * 1. MethodName : selectListMenu
     * 2. ClassName  : MenuServiceImpl
     * 3. Comment   : 메뉴 리스트 
     * 4. 작성자    : SooHyun.Seo
     * 5. 작성일    : 2017.12.22. 오후 4:06:52
     * </PRE>
     *   @return List
     *   @param param
     *   @return
     *   @throws Exception
     */
    public List selectListMenu(DataMap param) throws Exception {
    	return (List) commonMybatisDao.selectList("admin.menu.selectListMenu", param);
    }    
    
    /**
     * <PRE>
     * 1. MethodName : insertMenu
     * 2. ClassName  : MenuServiceImpl
     * 3. Comment   : 메뉴 등록 
     * 4. 작성자    : SooHyun.Seo
     * 5. 작성일    : 2017.12.22. 오후 4:07:14
     * </PRE>
     *   @return void
     *   @param param
     *   @throws Exception
     */
    public void insertMenu(DataMap param) throws Exception {
    	
    	String menuId = param.getString("menu_id");
    	List newMenuId = param.getList("new_menu_id");
		List newMenuNm = param.getList("new_menu_nm");
		List newMenuTypeCode = param.getList("new_menu_type_code");
		List newUrl = param.getList("new_url");
		List newSrtOrd = param.getList("new_srt_ord");
		List newDispYn = param.getList("new_disp_yn");

		DataMap tmp = new DataMap();
		tmp.put("ss_user_id", param.getString("ss_user_id"));
		tmp.put("new_menu_lv", param.getString("new_menu_lv"));
		tmp.put("menu_id", param.getString("menu_id"));
		
		tmp.put("sort_ordr_1", param.getString("sort_ordr_1"));
		tmp.put("sort_ordr_2", param.getString("sort_ordr_2"));
		tmp.put("sort_ordr_3", param.getString("sort_ordr_3"));
		tmp.put("sort_ordr_4", param.getString("sort_ordr_4"));
		tmp.put("sort_ordr_5", param.getString("sort_ordr_5"));
		tmp.put("sort_ordr_6", param.getString("sort_ordr_6"));
		
		int sortDepth = param.getString("menu_id").length() / 4 + 1;
		
		for(int i = 0; i < newMenuId.size(); i++){
			switch(sortDepth)
			{
				case 1: tmp.put("sort_ordr_1", newSrtOrd.get(i));break;
				case 2: tmp.put("sort_ordr_2", newSrtOrd.get(i));break;
				case 3: tmp.put("sort_ordr_3", newSrtOrd.get(i));break;
				case 4: tmp.put("sort_ordr_4", newSrtOrd.get(i));break;
				case 5: tmp.put("sort_ordr_5", newSrtOrd.get(i));break;
				case 6: tmp.put("sort_ordr_6", newSrtOrd.get(i));break;
				default:break;
				 
			}
			
			tmp.put("new_menu_id", menuId+newMenuId.get(i));
			tmp.put("new_menu_nm", newMenuNm.get(i));
			tmp.put("new_menu_type_code", newMenuTypeCode.get(i));
			tmp.put("new_url", newUrl.get(i));
			tmp.put("new_srt_ord", newSrtOrd.get(i));
			tmp.put("new_disp_yn", newDispYn.get(i));
			
			
			commonMybatisDao.insert("admin.menu.insertMenu", tmp);		
		}					
    }
    
    /**
     * <PRE>
     * 1. MethodName : selectMenu
     * 2. ClassName  : MenuServiceImpl
     * 3. Comment   : 메뉴 조회
     * 4. 작성자    : SooHyun.Seo
     * 5. 작성일    : 2017.12.22. 오후 4:07:43
     * </PRE>
     *   @return DataMap
     *   @param param
     *   @return
     *   @throws Exception
     */
    public DataMap selectMenu(DataMap param) throws Exception {
    	return (DataMap) commonMybatisDao.selectOne("admin.menu.selectMenu", param);
    }
    
    /**
     * <PRE>
     * 1. MethodName : selectExistYnMenu
     * 2. ClassName  : MenuServiceImpl
     * 3. Comment   : 동일 메뉴 ID 존재 여부 조회
     * 4. 작성자    : SooHyun.Seo
     * 5. 작성일    : 2017.12.22. 오후 4:08:21
     * </PRE>
     *   @return DataMap
     *   @param param
     *   @return
     *   @throws Exception
     */
    public DataMap selectExistYnMenu(DataMap param) throws Exception {
    	return (DataMap) commonMybatisDao.selectOne("admin.menu.selectExistYnMenu", param);
    }
    
    /**
     * <PRE>
     * 1. MethodName : updateMenu
     * 2. ClassName  : MenuServiceImpl
     * 3. Comment   : 메뉴 수정
     * 4. 작성자    : SooHyun.Seo
     * 5. 작성일    : 2017.12.22. 오후 4:08:47
     * </PRE>
     *   @return void
     *   @param param
     *   @throws Exception
     */
    public void updateMenu(DataMap param) throws Exception { 
    	int sortDepth = param.getString("menu_id").length() / 4; // 현재 메뉴의 뎁스 정보 가져오기
    	//현재 뎁스로 업데이트 필드 정하기
		switch(sortDepth)
		{
			case 1: param.put("sort_ordr_1", param.getString("srt_ord"));break;
			case 2: param.put("sort_ordr_2", param.getString("srt_ord"));break;
			case 3: param.put("sort_ordr_3", param.getString("srt_ord"));break;
			case 4: param.put("sort_ordr_4", param.getString("srt_ord"));break;
			case 5: param.put("sort_ordr_5", param.getString("srt_ord"));break;
			case 6: param.put("sort_ordr_6", param.getString("srt_ord"));break;
		}
    	commonMybatisDao.update("admin.menu.updateMenu", param);
    }
    
    
    /**
     * <PRE>
     * 1. MethodName : updateMenuSub
     * 2. ClassName  : MenuServiceImpl
     * 3. Comment   : 하위 메뉴 소트 수정
     * 4. 작성자    : SooHyun.Seo
     * 5. 작성일    : 2017.12.22. 오후 4:08:47
     * </PRE>
     *   @return void
     *   @param param
     *   @throws Exception
     */
    public void updateMenuSub(DataMap param) throws Exception {
    	int sortDepth = param.getString("menu_id").length() / 4;
    	//현재 뎁스로 업데이트 필드 정하기
    	param.put("sortDepth", sortDepth);
    	commonMybatisDao.update("admin.menu.updateMenuSub", param);
    }
    
    /**
     * <PRE>
     * 1. MethodName : deleteMenu
     * 2. ClassName  : MenuServiceImpl
     * 3. Comment   : 메뉴 삭제 
     * 4. 작성자    : SooHyun.Seo
     * 5. 작성일    : 2017.12.22. 오후 4:09:23
     * </PRE>
     *   @return void
     *   @param param
     *   @throws Exception
     */
    public void deleteMenu(DataMap param) throws Exception {
    	commonMybatisDao.delete("admin.menu.deleteAuthMenu", param);
    	commonMybatisDao.delete("admin.menu.deleteMenu", param);	
    }
    
    /**
     * <PRE>
     * 1. MethodName : selectExistYnSubMenu
     * 2. ClassName  : MenuServiceImpl
     * 3. Comment   : 하위메뉴 존재 여부 조회
     * 4. 작성자    : SooHyun.Seo
     * 5. 작성일    : 2017.12.22. 오후 4:10:08
     * </PRE>
     *   @param param
     *   @return
     *   @throws Exception
     */
    public DataMap selectExistYnSubMenu(DataMap param) throws Exception {
    	return (DataMap) commonMybatisDao.selectOne("admin.menu.selectExistYnSubMenu", param);
    }
    /**
     * <PRE>
     * 1. MethodName : selectExistSortSubMenu
     * 2. ClassName  : MenuServiceImpl
     * 3. Comment   : 동일 레벨 메뉴에 동일 정렬순서가 있는지 조회
     * 4. 작성자    : SooHyun.Seo
     * 5. 작성일    : 2017.12.22. 오후 4:10:08
     * </PRE>
     *   @param param
     *   @return
     *   @throws Exception
     */
    public DataMap selectExistSortSubMenu(DataMap param) throws Exception {
    	int menuLevel = param.getString("menu_id").length() / 4;//현재 선택 메뉴의 뎁스정보 알기
    	if (menuLevel>1) menuLevel=menuLevel-1;					  //현재의 뎁스에서 동일정렬 있느지로 수정 2017.12.13 SooHyun.Seo           
    	param.put("menuLevel", menuLevel);
    	return (DataMap) commonMybatisDao.selectOne("admin.menu.selectExistSortSubMenu", param);
    }
    
    

}
