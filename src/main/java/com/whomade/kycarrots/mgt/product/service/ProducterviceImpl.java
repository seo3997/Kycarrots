package com.whomade.kycarrots.mgt.product.service;

import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.page.util.pageNavigationUtil;
import com.whomade.kycarrots.framework.common.util.StringUtil;
import com.whomade.kycarrots.framework.common.util.SysUtil;
import com.whomade.kycarrots.framework.common.util.file.AtFileMngUtil;
import com.whomade.kycarrots.framework.common.util.file.dao.AtFileManageDAO;
import com.whomade.kycarrots.framework.common.util.file.vo.AtFileVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service("procuctService")
public class ProducterviceImpl extends EgovAbstractServiceImpl implements ProductService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProducterviceImpl.class);

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
	 * 1. MethodName 	: selectPageListProcuct
	 * 2. ClassName  	: ProducterviceImpl
	 * 3. Comment   	: 상품 리스트
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2025. 09. 19. 오후 4:08:41
	 * </PRE>
	 *   @param model
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public List<DataMap> selectPageListProcuct(ModelMap model, DataMap param)throws Exception {
		
		List<DataMap> resultList = new ArrayList <DataMap>();
		int totCnt = commonMybatisDao.selectOne("mgt.product.selectTotCntProduct", param);
		
		// 개수가 없는경우는 리스트 조회 하지 않는다.
		if(totCnt > 0){
			param.put("totalCount", totCnt);
			// param 객체에 페이지 관련 정보를 담는다.
			param = pageNavigationUtil.createNavigationInfo(model, param);
			resultList =  commonMybatisDao.selectList("mgt.product.selectPageListProduct", param);
		}
		return resultList;
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectProduct
	 * 2. ClassName  	: ProducterviceImpl
	 * 3. Comment   	: 상품 상세
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2025. 09. 19. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @return
	 *   @throws Exception
	 */
	public DataMap selectProduct(DataMap param)throws Exception {
		return (DataMap) commonMybatisDao.selectOne("mgt.product.selectProduct", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: insertProduct
	 * 2. ClassName  	: ProducterviceImpl
	 * 3. Comment   	: 상품 등록
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2025. 09. 19. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @param fileList
	 *   @throws Exception
	 */
	public void insertProduct(DataMap param, List fileList)throws Exception {
		
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
		
		commonMybatisDao.insert("mgt.product.insertProduct", param);

	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: updateProduct
	 * 2. ClassName  	: ProducterviceImpl
	 * 3. Comment   	: 상품 수정
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2025. 09. 19. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @param fileList
	 *   @throws Exception
	 */
	public void updateProduct(DataMap param, List fileList)throws Exception {

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
		
		commonMybatisDao.update("mgt.product.updateProduct", param);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: deleteProduct
	 * 2. ClassName  	: ProducterviceImpl
	 * 3. Comment   	: 상품 삭제
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2025. 09. 19. 오후 4:08:41
	 * </PRE>
	 *   @param param
	 *   @throws Exception
	 */
	public void deleteProduct(DataMap param)throws Exception {
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
		//commonMybatisDao.update("mgt.product.deleteBoardComment", param);
		// 글 삭제
		commonMybatisDao.update("mgt.product.deleteProduct", param);
	}

}
