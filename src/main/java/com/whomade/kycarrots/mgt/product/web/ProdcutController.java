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
import org.springframework.web.multipart.MultipartFile;
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
		model.addAttribute("categoryMComboStr", cateooryMComboStr);

		// 지역 R010070
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

		// 판매상태 R010630
		codeParam.put("group_id","R010630");
		List saleStatusComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("saleStatusComboStr", saleStatusComboStr);

		// 카테고리 R010610
		codeParam.put("group_id","R010610");
		List cateooryMComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("categoryMComboStr", cateooryMComboStr);

		// 카테고리 R010070
		codeParam.put("group_id","R010070");
		List areaMComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("areaMComboStr", areaMComboStr);

		// 단위코드 R010070
		codeParam.put("group_id","R010620");
		List unitCodeComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("unitCodeComboStr", unitCodeComboStr);
		
		
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

		String metasJson =request.getParameter("imageMetasJson");
		log.debug("metasJson: "+metasJson);

		List<TnProductImageVo> metas = parseImageMetas(metasJson);
		log.debug("metas: {}"+ metas);

		// 파일 객체 가져옴
		List<MultipartFile> fileList =  atFileMngUtil.getFiles((MultipartHttpServletRequest)request);

		productService.insertProduct(param, fileList, metas);
		
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

		// 판매상태 R010630
		codeParam.put("group_id","R010630");
		List saleStatusComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("saleStatusComboStr", saleStatusComboStr);

		// 카테고리 R010610
		codeParam.put("group_id","R010610");
		List cateooryMComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("categoryMComboStr", cateooryMComboStr);

		// 카테고리 R010070
		codeParam.put("group_id","R010070");
		List areaMComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("areaMComboStr", areaMComboStr);

		// 단위코드 R010070
		codeParam.put("group_id","R010620");
		List unitCodeComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("unitCodeComboStr", unitCodeComboStr);



		DataMap resultMap = productService.selectProduct(param);
		
		// #### FILE LIST 검색 Start ####
		List<TnProductImageVo> fileList = tnProductRepository.selectProductImagesByProductId(param.getLong("productId"));
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

		String metasJson =request.getParameter("imageMetasJson");
		log.debug("metasJson: "+metasJson);

		List<TnProductImageVo> metas = parseImageMetas(metasJson);
		log.debug("metas: {}"+ metas);


		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		// 파일 객체 가져옴
		//MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) request;
		//List<MultipartFile> fileList = extractIndexedFiles(mreq, "images"); // ↓ 헬
		List<MultipartFile> fileList = atFileMngUtil.getFiles((MultipartHttpServletRequest)request);

		productService.updateProduct(param, fileList, metas);

		model.addAttribute("param", param);
		MessageUtil.setMessage(request, egovMessageSource.getMessage("succ.data.update"));
		param.put("redirectUrl", "/mgt/product/selectProduct.do");
		
		return "common/redirect";
	}

	private List<MultipartFile> extractIndexedFiles(MultipartHttpServletRequest req, String prefix) {
		java.util.regex.Pattern p = java.util.regex.Pattern.compile("^" + java.util.regex.Pattern.quote(prefix) + "\\[(\\d+)]$");
		java.util.TreeMap<Integer, MultipartFile> sorted = new java.util.TreeMap<>();
		for (java.util.Map.Entry<String, MultipartFile> e : req.getFileMap().entrySet()) {
			java.util.regex.Matcher m = p.matcher(e.getKey());
			if (m.matches()) {
				int idx = Integer.parseInt(m.group(1));
				MultipartFile f = e.getValue();
				if (f != null && !f.isEmpty()) {
					sorted.put(idx, f);
				}
			}
		}
		return new java.util.ArrayList<>(sorted.values());
	}

	private List<TnProductImageVo> parseImageMetas(String json){
		if (json == null || json.isBlank()) return java.util.Collections.emptyList();
		try {
			var om = new com.fasterxml.jackson.databind.ObjectMapper()
					.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			TnProductImageVo[] arr = om.readValue(json, TnProductImageVo[].class);
			return java.util.Arrays.asList(arr);
		} catch (Exception e) {
			throw new IllegalArgumentException("imageMetasJson 파싱 오류: " + e.getMessage(), e);
		}
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

		productService.deleteProduct(param);

		MessageUtil.setMessage(request, egovMessageSource.getMessage("succ.data.delete"));
		model.addAttribute("param", param);
		return "redirect:/mgt/product/selectPageListProduct.do";
	}
}
