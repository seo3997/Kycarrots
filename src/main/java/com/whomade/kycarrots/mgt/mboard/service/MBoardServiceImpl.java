package com.whomade.kycarrots.mgt.mboard.service;

import java.util.ArrayList;
import java.util.List;

import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.page.util.pageNavigationUtil;
import com.whomade.kycarrots.framework.common.util.StringUtil;
import com.whomade.kycarrots.framework.common.util.SysUtil;
import com.whomade.kycarrots.framework.common.util.file.AtFileMngUtil;
import com.whomade.kycarrots.framework.common.util.file.dao.AtFileManageDAO;
import com.whomade.kycarrots.framework.common.util.file.vo.AtFileVO;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("microBizBoardService")
public class MBoardServiceImpl extends EgovAbstractServiceImpl implements MicroBizBoardService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MBoardServiceImpl.class);

	/** commonDao */
	@Resource(name="commonMybatisDao")
	private CommonMybatisDao commonMybatisDao;

	/** 파일관련 */
	@Resource(name = "AtFileManageDAO")
	private AtFileManageDAO atFileManageDAO;
	 
	@Resource(name="AtFileMngUtil")
	private AtFileMngUtil atFileMngUtil;
	
	/**
	 * <PRE>
	 * 1. MethodName : selectPageListBoard
	 * 2. ClassName  : BoardServiceImpl
	 * 3. Comment   : 게시판 리스트
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017. 3. 13. 오후 4:08:41
	 * </PRE>
	 *   @param model
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectPageListBoard(ModelMap model, DataMap param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		int totCnt = commonMybatisDao.selectOne("mgt.mboard.selectTotCntBoard", param);
		
		// 개수가 없는경우는 리스트 조회 하지 않는다.
		if(totCnt > 0){
			param.put("totalCount", totCnt);
			// param 객체에 페이지 관련 정보를 담는다.
			param = pageNavigationUtil.createNavigationInfo(model, param);
			resultList =  commonMybatisDao.selectList("mgt.mboard.selectPageListBoard", param);
		}
		return resultList;
	}

	/**
	 * <PRE>
	 * 1. MethodName : selectBoard
	 * 2. ClassName  : BoardServiceImpl
	 * 3. Comment   : 게시판 상세
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017. 3. 13. 오후 4:10:00
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public DataMap selectBoard(DataMap param)throws Exception {
		return (DataMap) commonMybatisDao.selectOne("mgt.mboard.selectBoard", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : insertBoard
	 * 2. ClassName  : BoardServiceImpl
	 * 3. Comment   : 게시판 등록
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @param fileList
	 *   @throws Exception
	 */
	public void insertBoard(DataMap param, List fileList)throws Exception {
		
		// ########### Upload File 처리 시작 #############
		AtFileVO _reAtFile = null;
		if(!fileList.isEmpty()){
			// 문서 ID 셋팅
			String doc_id = StringUtil.nvl(param.getString("atch_doc_id"), SysUtil.getDocId());
			param.put("atch_doc_id", doc_id );
			
			for(int i=0; i < fileList.size(); i++){
				MultipartFile mfile = (MultipartFile)fileList.get(i);
				if(!mfile.isEmpty()){
					// 파일을 서버에 물리적으로 저장하고
					_reAtFile	= atFileMngUtil.parseFileInf(mfile, doc_id, "mboard/", param.getString("ss_user_no"), "Y");
					// 파일이 생성되고나면 생성된 첨부파일 정보를 DB에 넣는다.
					commonMybatisDao.insert("common.file.insertAttchFile", _reAtFile);
				}
			}
		}
		// ########### Upload File 처리 종료 ############
		
		commonMybatisDao.insert("mgt.mboard.insertBoard", param);
		
		//Push전송
		/*
		String msgTitle;
		String msgCont;
		String msgUrl;
		msgTitle=param.getString("sj");
		msgCont	=StringUtil.getReSizeRemoveDot(param.getString("cn"),100);
		msgUrl="/mgt/mboard/selectBoard.do?bbs_seq="+param.getString("bbs_seq");
		FcmPush aFcmPush=new FcmPush(msgTitle,msgCont,msgUrl);
		aFcmPush.sendPush();
		*/
		
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : updateBoard
	 * 2. ClassName  : BoardServiceImpl
	 * 3. Comment   : 게시판 수정
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017. 3. 13. 오후 4:10:15
	 * </PRE>
	 *   @param param
	 *   @param fileList
	 *   @throws Exception
	 */
	public void updateBoard(DataMap param, List fileList)throws Exception {

		// ########### Upload File 처리 시작 #############
		AtFileVO _reAtFile = null;
		if(!fileList.isEmpty()){
			// 문서 ID 셋팅
			String doc_id = StringUtil.nvl(param.getString("atch_doc_id", SysUtil.getDocId()));
			param.put("atch_doc_id", doc_id);

			for(int i=0; i < fileList.size(); i++){
				MultipartFile mfile = (MultipartFile)fileList.get(i);
				if(!mfile.isEmpty()){
					// 파일을 서버에 물리적으로 저장하고
					_reAtFile	= atFileMngUtil.parseFileInf(mfile, doc_id, "mboard/", param.getString("ss_user_no"), "Y");
					// 파일이 생성되고나면 생성된 첨부파일 정보를 DB에 넣는다.
					commonMybatisDao.insert("common.file.insertAttchFile", _reAtFile);
				}
			}
		}
		// ########### Upload File 처리 종료 ############
		
		commonMybatisDao.update("mgt.mboard.updateBoard", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : deleteBoard
	 * 2. ClassName  : BoardServiceImpl
	 * 3. Comment   : 게시판 삭제
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017. 12. 21 15:59
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteBoard(DataMap param)throws Exception {
		AtFileVO fvo = new AtFileVO();
		
		// 글 첨부파일 삭제 ================================================================
		fvo.setDoc_id(param.getString("atch_doc_id"));
		List<AtFileVO> fileList = commonMybatisDao.selectList("common.file.selectAttchFiles", fvo);
		
		// 글 첨부파일 삭제
		atFileMngUtil.deleteFile(fileList);
		// 글 첨부파일 DB 삭제
		param.put("doc_id", fvo.getDoc_id());
		commonMybatisDao.delete("common.file.deleteAttchFiles", param);
		//===================================================================================
		//comment삭제
		//commonMybatisDao.update("mgt.mboard.deleteBoardComment", param);
		// 글 삭제
		commonMybatisDao.update("mgt.mboard.deleteBoard", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: selectListBoard
	 * 2. ClassName  	: BoardServiceImpl
	 * 3. Comment   	: 게시판 리스트
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2017. 3. 13. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectListBoard(DataMap param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		resultList =  commonMybatisDao.selectList("mgt.mboard.selectListBoard", param);
		
		return resultList;
	}
	
}
