package com.whomade.kycarrots.framework.common.util.file.service;

import java.util.Iterator;
import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.file.vo.AtFileVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : AtFileMngServiceImpl.java
 * 3. Package  : egovframework.com.cmm.service.impl
 * 4. Comment  : 파일정보의 관리를 위한 구현 클래스
 * 5. 작성자   : sshinb
 * 6. 작성일   : 2013. 10. 1. 오전 10:08:43
 * @Modification Information
 *
 *    수정일       		수정자         수정내용
 *    -------        	-------     -------------------
 *    2013. 10. 1.     	sshinb    	최초생성
 *    2015. 12. 08		SooHyun.Seo			mybatis로 변경(공통 mybatis dao사용) > 기존 dao 로직으로 impl단으로 올림
 *
 * </PRE>
 */
@Service("AtFileMngService")
public class AtFileMngServiceImpl extends EgovAbstractServiceImpl implements AtFileMngService {
	
	/** commonDao */
	@Resource(name="commonMybatisDao")
    private CommonMybatisDao commonMybatisDao;
	

    /**
     * 하나의 파일에 대한 정보(속성 및 상세)를 등록한다.
     * 
     */
    public String insertFileInf(AtFileVO fvo) throws Exception 
    {
		String docId = fvo.getDoc_id();
		commonMybatisDao.insert("common.file.insertAttchFile", fvo);
		
		return docId;
    }

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 등록한다.
     * 
     */
    public String insertFileInfs(List fvoList) throws Exception 
    {
		String atchFileId = "";
		
		if (fvoList.size() != 0) {
			
			AtFileVO vo = (AtFileVO)fvoList.get(0);
			atchFileId = vo.getDoc_id();
		
			Iterator iter = fvoList.iterator();
			while (iter.hasNext()) {
				vo = (AtFileVO)iter.next();
				commonMybatisDao.insert("common.file.insertAttchFile", vo);
			}
		}
		
		if(atchFileId == ""){
			atchFileId = null;
		}
		
		return atchFileId;
    }

    /**
     * 파일에 대한 목록을 조회한다.
     * 
     * @param fvo : AtFileVO
     * @return
     * @throws Exception
     */
    public List<AtFileVO> selectFileInfs(AtFileVO fvo) throws Exception 
    {
    	List<AtFileVO> fileInfs= null;
    	if(fvo.getDoc_id() != null && !fvo.getDoc_id().equals("")){
    		return commonMybatisDao.selectList("common.file.selectAttchFiles", fvo);
    	}
    	
    	return fileInfs;
    }

    /**
     * 하나의 파일을 삭제한다.
     * 
     */
    public void deleteFileInf(DataMap param) throws Exception 
    {
    	commonMybatisDao.delete("common.file.deleteAttchFile", param);
    }
    
    /**
     * 여러 개의 파일을 삭제한다.
     * 
     */
    public void deleteFileInfs(DataMap param) throws Exception 
    {
    	commonMybatisDao.delete("common.file.deleteAttchFiles", param);
    }

    /**
     * 파일에 대한 상세정보를 조회한다.
     * 
     */
    public AtFileVO selectFileInf(AtFileVO fvo) throws Exception 
    {
    	return commonMybatisDao.selectOne("common.file.selectAttchFile", fvo);
    }
}
