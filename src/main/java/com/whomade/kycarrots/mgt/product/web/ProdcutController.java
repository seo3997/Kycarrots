package com.whomade.kycarrots.mgt.product.web;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.common.service.CommonCodeService;
import com.whomade.kycarrots.entity.product.TnProductImageVo;
import com.whomade.kycarrots.framework.common.constant.Const;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.*;
import com.whomade.kycarrots.framework.common.util.file.AtFileMngUtil;
import com.whomade.kycarrots.framework.common.util.file.service.AtFileMngService;
import com.whomade.kycarrots.framework.common.util.file.vo.AtFileVO;
import com.whomade.kycarrots.mgt.product.service.ProductService;
import com.whomade.kycarrots.repository.mybatis.product.TnProductRepository;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class ProdcutController {

	private static Log log = LogFactory.getLog(ProdcutController.class);

	@Resource(name = "egovMessageSource")
	private EgovMessageSource egovMessageSource;

	@Resource(name = "procuctService")
	private ProductService productService;

	/** CommonCodeService */
	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;
	
	@Resource(name="AtFileMngService")
	private AtFileMngService atFileMngService;
	
	@Resource(name="AtFileMngUtil")
	private AtFileMngUtil atFileMngUtil;

	private final TnProductRepository tnProductRepository;
	/**
	 * <PRE>
	 * 1. MethodName 	: selectPageListProduct
	 * 2. ClassName  	: ProdcutController
	 * 3. Comment   	: 상품 리스트
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2025. 09. 19 18:00
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/product/selectPageListProduct.do")
	public String selectPageListProduct(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataMap param = RequestUtil.getDataMap(request);
		DataMap codeParam = new DataMap();

		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());

		// 판매상태 R010630
		codeParam.put("group_id","R010630");
		List saleStatusComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("saleStatusComboStr", saleStatusComboStr);

		// 카테고리 R010610
		codeParam.put("group_id","R010610");
		List cateooryMComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("cateooryMComboStr", cateooryMComboStr);

		// 카테고리 R010070
		codeParam.put("group_id","R010070");
		List areaMComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("areaMComboStr", areaMComboStr);

		//리스트 조회
		List<DataMap> resultList = productService.selectPageListProcuct(model, param);

		model.addAttribute("resultList", resultList);
		model.addAttribute("param", param);
		
		return "mgt/product/selectPageListProduct";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 		: selectProduct
	 * 2. ClassName  		: ProdcutController
	 * 3. Comment   		: 상품 상세
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2025. 09. 19 18:00
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/product/selectProduct.do")
	public String selectProduct(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		DataMap resultMap = productService.selectProduct(param);
		
		// #### FILE LIST 검색 Start ####
		List<TnProductImageVo> fileList = tnProductRepository.selectProductImagesByProductId(param.getLong("productId"));
		// #### FILE LIST 검색 End ####
		
		model.addAttribute("fileList", fileList);
		model.addAttribute("resultMap", resultMap);
		model.addAttribute("param", param);
		
		return "mgt/product/selectProduct";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 		: insertFormProduct
	 * 2. ClassName  		: ProdcutController
	 * 3. Comment  		 	: 상품 등록폼
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2025. 09. 19 18:00
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/product/insertFormProduct.do")
	public String insertFormProduct(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		DataMap param = RequestUtil.getDataMap(request);
		DataMap codeParam = new DataMap();
		
		// 게시판 구분 코드 조회 R010170
		codeParam.put("group_id", Const.upCodeNoticeBbsSeCode);
		List boardComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("boardComboStr", boardComboStr);
		
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		
		model.addAttribute("param", param);
		
		return "mgt/product/insertFormProduct";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: insertProduct
	 * 2. ClassName  	: ProdcutController
	 * 3. Comment   	: 상품 등록
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2025. 09. 19 18:00
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/product/insertProduct.do")
	public String insertProduct(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());

		// 파일 객체 가져옴
		List fileList = atFileMngUtil.getFiles((MultipartHttpServletRequest)request);
		
		// 파일 확장자 계산
		String msg = atFileMngUtil.checkFileExt(fileList);
		if(!msg.equals("")){
			MessageUtil.setMessage(request, msg);
			param.put("redirectUrl", "/mgt/product/insertFormProduct.do");
			model.addAttribute("param", param);
			return "common/redirect";
		}
		
		// 파일 크기 계산(최대크기 넘었을경우 다시 쓰기 페이지로 리턴)
		if(!atFileMngUtil.checkEachFileSize(fileList)){
			MessageUtil.setMessage(request, egovMessageSource.getMessage("error.file.size.over", new String[]{atFileMngUtil.getFileSize(EgovPropertiesUtil.getProperty("Globals.fileMaxSize"))}));
			
			param.put("redirectUrl", "/mgt/product/insertFormProduct.do");
			model.addAttribute("param", param);
			return "common/redirect";
		}

		productService.insertProduct(param, fileList);
		
		MessageUtil.setMessage(request, egovMessageSource.getMessage("succ.data.insert"));
		
		param.put("redirectUrl", "/mgt/product/selectPageListProduct.do");
		model.addAttribute("param", param);
		
		return "common/redirect";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: updateFormProduct
	 * 2. ClassName  	: ProdcutController
	 * 3. Comment   	: 상품 수정폼
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2025. 09. 19 18:00
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/product/updateFormProduct.do")
	public String updateFormProduct(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		DataMap param = RequestUtil.getDataMap(request);
		DataMap codeParam = new DataMap();
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		// 게시판 구분 코드 조회 R010170
		codeParam.put("group_id", Const.upCodeNoticeBbsSeCode);
		List boardComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("boardComboStr", boardComboStr);
		
		
		DataMap resultMap = productService.selectProduct(param);
		
		// #### FILE LIST 검색 Start ####
		AtFileVO fvo = new AtFileVO();
		fvo.setDoc_id(resultMap.getString("ATCH_DOC_ID"));
		
		List<AtFileVO> fileList = atFileMngService.selectFileInfs(fvo);
		// #### FILE LIST 검색 End ####
		
		model.addAttribute("fileList", fileList);
		model.addAttribute("resultMap", resultMap);
		model.addAttribute("param", param);
		
		return "mgt/product/updateFormProduct";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateProduct
	 * 2. ClassName  	: ProdcutController
	 * 3. Comment   	: 상품 수정
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2025. 09. 19 18:00
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/product/updateProduct.do")
	public String updateProduct(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		// 파일 객체 가져옴
		List fileList = atFileMngUtil.getFiles((MultipartHttpServletRequest)request);
		
		// 파일 확장자 계산
		String msg = atFileMngUtil.checkFileExt(fileList);
		if(!msg.equals("")){
			MessageUtil.setMessage(request, msg);
			param.put("redirectUrl", "/mgt/product/updateFormProduct.do");
			model.addAttribute("param", param);
			return "common/redirect";
		}
		
		// 파일 크기 계산(최대크기 넘었을경우 다시 쓰기 페이지로 리턴)
		if(!atFileMngUtil.checkEachFileSize(fileList)){
			MessageUtil.setMessage(request, egovMessageSource.getMessage("error.file.size.over", new String[]{atFileMngUtil.getFileSize(EgovPropertiesUtil.getProperty("Globals.fileMaxSize"))}));
			
			param.put("redirectUrl", "/mgt/product/updateFormProduct.do");
			model.addAttribute("param", param);
			
			return "common/redirect";
		}

		productService.updateProduct(param, fileList);

		model.addAttribute("param", param);
		MessageUtil.setMessage(request, egovMessageSource.getMessage("succ.data.update"));
		param.put("redirectUrl", "/mgt/product/selectProduct.do");
		
		return "common/redirect";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: deleteProduct
	 * 2. ClassName  	: ProdcutController
	 * 3. Comment   	: 상품 삭제
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2025. 09. 19 18:00
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/product/deleteProduct.do")
	public String deleteProduct(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);

		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		DataMap resultMap = productService.selectProduct(param);
		
		// doc_id 및 내용 doc_id 셋팅
		param.put("atch_doc_id", resultMap.getString("ATCH_DOC_ID"));
		param.put("cn_doc_id", resultMap.getString("CN_DOC_ID"));
		
		// 참고자료 삭제
		productService.deleteProduct(param);
		MessageUtil.setMessage(request, egovMessageSource.getMessage("succ.data.delete"));
		model.addAttribute("param", param);
		return "redirect:/mgt/product/selectPageListProduct.do";
	}
}
