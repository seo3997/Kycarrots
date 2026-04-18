package com.whomade.kycarrots.mgt.product.service;

import com.whomade.kycarrots.entity.product.TnProductImageVo;
import com.whomade.kycarrots.entity.product.TnProductVo;
import com.whomade.kycarrots.framework.common.dao.CommonMybatisDao;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.page.util.pageNavigationUtil;
import com.whomade.kycarrots.framework.common.util.StringUtil;
import com.whomade.kycarrots.framework.common.util.SysUtil;
import com.whomade.kycarrots.framework.common.util.file.AtFileMngUtil;
import com.whomade.kycarrots.framework.common.util.file.FileUtil;
import com.whomade.kycarrots.framework.common.util.file.dao.AtFileManageDAO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("procuctService")
public class ProducterviceImpl extends EgovAbstractServiceImpl implements ProductService {



	/** commonDao */
	@Resource(name = "commonMybatisDao")
	private CommonMybatisDao commonMybatisDao;

	/** 파일관련 */
	@Resource(name = "AtFileManageDAO")
	private AtFileManageDAO atFileManageDAO;

	@Resource(name = "AtFileMngUtil")
	private AtFileMngUtil atFileMngUtil;

	@Resource
	private com.whomade.kycarrots.framework.common.util.file.OciObjectStorageService ociService;

	@Resource
	private com.whomade.kycarrots.framework.common.util.file.FilePathResolver resolver;

	@Autowired
	private com.whomade.kycarrots.push.PushService pushService;

	/**
	 * <PRE>
	 * 1. MethodName 	: selectPageListProcuct
	 * 2. ClassName  	: ProducterviceImpl
	 * 3. Comment   	: 상품 리스트
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2025. 09. 19. 오후 4:08:41
	 * </PRE>
	 * 
	 * @param model
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectPageListProcuct(ModelMap model, DataMap param) throws Exception {
		List<DataMap> resultList = new ArrayList<DataMap>();
		int totCnt = commonMybatisDao.selectOne("mgt.product.selectTotCntProduct", param);

		// 개수가 없는경우는 리스트 조회 하지 않는다.
		if (totCnt > 0) {
			param.put("totalCount", totCnt);
			// param 객체에 페이지 관련 정보를 담는다.
			param = pageNavigationUtil.createNavigationInfo(model, param);
			resultList = commonMybatisDao.selectList("mgt.product.selectPageListProduct", param);
		}
		return resultList;
	}

	public List<DataMap> selectListProduct(DataMap param) throws Exception {
		return commonMybatisDao.selectList("mgt.product.selectListProduct", param);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: selectProduct
	 * 2. ClassName  	: ProducterviceImpl
	 * 3. Comment   	: 상품 상세
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2025. 09. 19. 오후 4:08:41
	 * </PRE>
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public DataMap selectProduct(DataMap param) throws Exception {
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
	 * 
	 * @param param
	 * @param fileList
	 * @throws Exception
	 */
	public void insertProduct(DataMap param, List<MultipartFile> fileList, List<TnProductImageVo> metas)
			throws Exception {

		// 0) 기본정보 매핑 + INSERT (useGeneratedKeys로 tnProductVo.productId 세팅)
		TnProductVo tnProductVo = buildInsTnProductVo(param);
		commonMybatisDao.insert("mgt.product.insertProduct", tnProductVo);

		String productIdStr = tnProductVo.getProductId();
		if (productIdStr == null || productIdStr.isEmpty()) {
			throw new IllegalStateException(
					"productId 생성 실패: mgt.product.insertProduct 매퍼에 useGeneratedKeys/keyProperty 설정을 확인하세요.");
		}
		Long productId = Long.valueOf(productIdStr);

		// 등록/수정 사용자 번호
		String userNoStr = tnProductVo.getUserNo();
		int userNo = (userNoStr == null || userNoStr.isEmpty()) ? 0 : Integer.parseInt(userNoStr);

		// 이미지 메타가 없으면 빈 리스트로
		if (metas == null)
			metas = java.util.Collections.emptyList();

		// 1) 이미지가 없으면 종료
		if (fileList == null || fileList.isEmpty())
			return;

		// 대표 후보 추적
		Long representId = null;
		Long firstInsertedId = null;

		// 2) 신규 파일 저장 + DB insert
		int nMetas = metas.size();
		for (int i = 0; i < fileList.size(); i++) {
			MultipartFile file = fileList.get(i);
			if (file == null || file.isEmpty())
				continue;

			// 메타에서 대표 여부(없으면 0)
			int represent = 0;
			if (i < nMetas) {
				TnProductImageVo m = metas.get(i);
				if (m != null && m.getRepresent() != null && m.getRepresent() == 1) {
					represent = 1;
				}
			}

			// 2-1) 물리 저장
			com.whomade.kycarrots.framework.common.util.file.FilePathResolver.Storage storage = resolver.resolve("product");
			String dateFolder = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.BASIC_ISO_DATE);
			String ext = SysUtil.getFileExtName(file.getOriginalFilename());
			String storeName = SysUtil.getFileId() + (ext.isEmpty() ? "" : "." + ext);
			String imageUrl;

			if ("Y".equalsIgnoreCase(storage.getStorageType())) {
				String objectName = storage.getPathPrefix() + dateFolder + "/" + storeName;
				ociService.uploadFile(storage.getNamespace(), storage.getBucketName(), objectName, file);
				imageUrl = storage.getPublicUrl() + objectName;
			} else {
				java.io.File destFile = FileUtil.saveFile(file, storage.getUploadDir(), dateFolder, storeName);
				imageUrl = storage.getPublicUrl() + dateFolder + "/" + destFile.getName();
			}

			// 2-2) DB insert (useGeneratedKeys → imageId 세팅)
			TnProductImageVo toInsert = new TnProductImageVo();
			toInsert.setProductId(productId);
			toInsert.setImageCd("1");
			toInsert.setImageUrl(imageUrl);
			toInsert.setImageName(file.getOriginalFilename());
			toInsert.setImageSize(file.getSize());
			toInsert.setImageType(file.getContentType()); // MIME 타입 저장
			toInsert.setRepresent(represent); // 대표 플래그(0/1)
			toInsert.setRegisterNo(userNo);
			toInsert.setUpdusrNo(userNo);

			commonMybatisDao.update("mgt.product.insertProductImage", toInsert);

			if (firstInsertedId == null)
				firstInsertedId = toInsert.getImageId();
			if (represent == 1)
				representId = toInsert.getImageId();
		}

		// 3) 대표 플래그 일관성 정리 (대표가 명시되지 않았다면 첫 이미지 대표)
		if (representId == null)
			representId = firstInsertedId;
		if (representId != null) {
			DataMap r = new DataMap();
			r.put("productId", productId);
			r.put("imageId", representId);
			commonMybatisDao.update("mgt.product.updateRepresentByProduct", r);
		}

		// 4) 판매중(1) 상태로 등록될 경우 푸시 발송
		if ("1".equals(tnProductVo.getSaleStatus())) {
			String title = "신규 상품 등록";
			String body = "[신상품] 새로운 상품이 등록되었습니다. 지금 확인해보세요!";
			java.util.Map<String, String> payload = java.util.Map.of(
					"targetId", String.valueOf(productId),
					"type", "product",
					"title", title,
					"body", body);
			pushService.sendTargetPush(
					param.getLong("ss_user_no"),
					java.util.Arrays.asList("ROLE_PUB", "ROLE_PROJ", "ROLE_SELL"),
					null, null, null,
					title, body, "PRODUCT_REGISTER", payload);
		}

	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateProduct
	 * 2. ClassName  	: ProducterviceImpl
	 * 3. Comment   	: 상품 수정
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2025. 09. 19. 오후 4:08:41
	 * </PRE>
	 * 
	 * @throws Exception
	 */
	public void updateProduct(DataMap param, List<MultipartFile> files, List<TnProductImageVo> metas) throws Exception {
		// 현재 상태 확인 (상태 변경 체크를 위해)
		DataMap oldProduct = selectProduct(param);
		String oldStatus = (oldProduct != null) ? oldProduct.getString("SALE_STATUS") : "";

		// 상품 기본정보 업데이트
		TnProductVo tnProductVo = buildUpTnProductVo(param);
		commonMybatisDao.update("mgt.product.updateProduct", tnProductVo);

		// 상태가 '판매중(1)'으로 변경된 경우에만 푸시 발송
		if ("1".equals(tnProductVo.getSaleStatus()) && !"1".equals(oldStatus)) {
			String title = "신규 상품 등록";
			String body = "[신상품] 새로운 상품이 등록되었습니다. 지금 확인해보세요!";
			java.util.Map<String, String> payload = java.util.Map.of(
					"targetId", tnProductVo.getProductId(),
					"type", "product",
					"title", title,
					"body", body);
			pushService.sendTargetPush(
					param.getLong("ss_user_no"),
					java.util.Arrays.asList("ROLE_PUB", "ROLE_PROJ", "ROLE_SELL"),
					null, null, null,
					title, body, "PRODUCT_REGISTER", payload);
		}

		// ########### Upload File 처리 시작 #############
		// 1) 이미지 변경 안했으면 종료
		String imagesTouched = StringUtil.nvl(param.getString("imagesTouched"), "0");
		if (!"1".equals(imagesTouched))
			return;

		Long productId = Long.valueOf(tnProductVo.getProductId());
		int userNo = Integer.parseInt(tnProductVo.getUserNo());

		if (metas == null)
			metas = java.util.Collections.emptyList();

		// 2) 대표 후보 먼저 찾기(기존 이미지 중 represent=1)
		Long representId = null;
		for (TnProductImageVo m : metas) {
			if (m.getImageId() != null && Integer.valueOf(1).equals(m.getRepresent())) {
				representId = m.getImageId();
				break;
			}
		}

		// 3) 신규만 파일 저장 + DB insert (metas 순회하며 imageId == null 인 항목마다 files에서 하나씩 소비)
		int cursor = 0;
		if (files != null && !files.isEmpty()) {
			for (TnProductImageVo m : metas) {
				if (m.getImageId() != null)
					continue; // 기존은 건너뜀
				if (cursor >= files.size())
					break;

				MultipartFile file = files.get(cursor++);
				if (file == null || file.isEmpty())
					continue;

				// 물리 저장
				com.whomade.kycarrots.framework.common.util.file.FilePathResolver.Storage storage = resolver.resolve("product");
				String dateFolder = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.BASIC_ISO_DATE);
				String ext = SysUtil.getFileExtName(file.getOriginalFilename());
				String storeName = SysUtil.getFileId() + (ext.isEmpty() ? "" : "." + ext);
				String imageUrl;

				if ("Y".equalsIgnoreCase(storage.getStorageType())) {
					String objectName = storage.getPathPrefix() + dateFolder + "/" + storeName;
					ociService.uploadFile(storage.getNamespace(), storage.getBucketName(), objectName, file);
					imageUrl = storage.getPublicUrl() + objectName;
				} else {
					java.io.File destFile = FileUtil.saveFile(file, storage.getUploadDir(), dateFolder, storeName);
					imageUrl = storage.getPublicUrl() + dateFolder + "/" + destFile.getName();
				}

				// 서버에서 MAIN/SUB 계산(대표=MAIN)
				boolean isRep = Integer.valueOf(1).equals(m.getRepresent());

				TnProductImageVo toInsert = new TnProductImageVo();
				toInsert.setProductId(productId);
				toInsert.setImageCd("1");
				toInsert.setImageUrl(imageUrl);
				toInsert.setImageName(file.getOriginalFilename());
				toInsert.setImageSize(file.getSize());
				toInsert.setImageType(file.getContentType());
				toInsert.setRepresent(isRep ? 1 : 0);
				toInsert.setRegisterNo(userNo);
				toInsert.setUpdusrNo(userNo);
				commonMybatisDao.update("mgt.product.insertProductImage", toInsert);
				if (isRep) {
					representId = toInsert.getImageId(); // 신규가 대표라면 대표 후보 갱신
				}
			}
			// 4) 대표 한 방에 정리
			if (representId != null) {
				DataMap r = new DataMap();
				r.put("productId", productId);
				r.put("imageId", representId);
				commonMybatisDao.update("mgt.product.updateRepresentByProduct", r);
			}
			// ########### Upload File 처리 종료 ############

		}
	}

	/** DataMap → TnProductVo 매핑 */
	public TnProductVo buildUpTnProductVo(DataMap param) {
		TnProductVo tnProductVo = new TnProductVo();
		tnProductVo.setProductId(param.getString("productId"));
		tnProductVo.setTitle(param.getString("title"));
		tnProductVo.setDescription(param.getString("description"));
		tnProductVo.setPrice(StringUtil.stripComma(param.getString("price")));
		tnProductVo.setSupplyPrice(StringUtil.stripComma(param.getString("supplyPrice")));
		tnProductVo.setTaxType(param.getString("taxType"));
		tnProductVo.setCategoryGroup(param.getString("categoryGroup"));
		tnProductVo.setCategoryMid(param.getString("categoryMid"));
		tnProductVo.setCategoryScls(param.getString("categoryScls"));
		tnProductVo.setAreaGroup(param.getString("areaGroup"));
		tnProductVo.setAreaMid(param.getString("areaMid"));
		tnProductVo.setAreaScls(param.getString("areaScls"));
		tnProductVo.setQuantity(StringUtil.stripComma(param.getString("quantity")));
		tnProductVo.setUnitGroup(param.getString("unitGroup"));
		tnProductVo.setUnitCode(param.getString("unitCode"));
		tnProductVo.setDesiredShippingDate(param.getString("desiredShippingDate"));
		tnProductVo.setEditorMode(param.getInt("editorMode"));
		tnProductVo.setSaleStatus(param.getString("saleStatus"));
		tnProductVo.setUserNo(param.getString("ss_user_no"));
		tnProductVo.setUpdusrNo(param.getString("ss_user_no"));

		return tnProductVo;
	}

	/** DataMap → TnProductVo 매핑 */
	public TnProductVo buildInsTnProductVo(DataMap param) {
		TnProductVo tnProductVo = new TnProductVo();
		// USER_NO 와 WHOLESALER_NO 가 빠졌음
		tnProductVo.setUserNo(param.getString("userNo"));

		tnProductVo.setSaleStatus(param.getString("saleStatus"));
		tnProductVo.setProductId(param.getString("productId"));
		tnProductVo.setTitle(param.getString("title"));
		tnProductVo.setDescription(param.getString("description"));
		tnProductVo.setPrice(StringUtil.stripComma(param.getString("price")));
		tnProductVo.setSupplyPrice(StringUtil.stripComma(param.getString("supplyPrice")));
		tnProductVo.setTaxType(param.getString("taxType"));
		tnProductVo.setCategoryGroup(param.getString("categoryGroup"));
		tnProductVo.setCategoryMid(param.getString("categoryMid"));
		tnProductVo.setCategoryScls(param.getString("categoryScls"));
		tnProductVo.setAreaGroup(param.getString("areaGroup"));
		tnProductVo.setAreaMid(param.getString("areaMid"));
		tnProductVo.setAreaScls(param.getString("areaScls"));
		tnProductVo.setQuantity(StringUtil.stripComma(param.getString("quantity")));
		tnProductVo.setUnitGroup(param.getString("unitGroup"));
		tnProductVo.setUnitCode(param.getString("unitCode"));
		tnProductVo.setDesiredShippingDate(param.getString("desiredShippingDate"));
		tnProductVo.setEditorMode(param.getInt("editorMode"));
		tnProductVo.setSaleStatus(param.getString("saleStatus"));
		tnProductVo.setUserNo(param.getString("ss_user_no"));
		tnProductVo.setUpdusrNo(param.getString("ss_user_no"));
		return tnProductVo;
	}

	/**
	 * <PRE>
	 * 1. MethodName    : uploadSummernoteImage
	 * 2. ClassName     : ProducterviceImpl
	 * 3. Comment       : Summernote 이미지 업로드
	 * 4. 작성자            : SooHyun.Seo
	 * 5. 작성일            : 2026. 03. 02.
	 * </PRE>
	 * 
	 * @param param
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@Override
	public DataMap uploadSummernoteImage(DataMap param, MultipartFile file) throws Exception {
		DataMap result = new DataMap();
		if (file == null || file.isEmpty()) {
			throw new IllegalArgumentException("업로드할 파일이 없습니다.");
		}

		String productIdStr = param.getString("productId");
		if (productIdStr == null || productIdStr.isEmpty()) {
			productIdStr = "0"; // 신규 등록 시 임시 ID
		}
		Long productId = Long.valueOf(productIdStr);

		String userNoStr = param.getString("ss_user_no");
		int userNo = (userNoStr == null || userNoStr.isEmpty()) ? 0 : Integer.parseInt(userNoStr);

		// 물리 저장
		com.whomade.kycarrots.framework.common.util.file.FilePathResolver.Storage storage = resolver.resolve("product/editor");
		String dateFolder = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.BASIC_ISO_DATE);
		String ext = SysUtil.getFileExtName(file.getOriginalFilename());
		String storeName = SysUtil.getFileId() + (ext.isEmpty() ? "" : "." + ext);
		String imageUrl;

		if ("Y".equalsIgnoreCase(storage.getStorageType())) {
			String objectName = storage.getPathPrefix() + dateFolder + "/" + storeName;
			ociService.uploadFile(storage.getNamespace(), storage.getBucketName(), objectName, file);
			imageUrl = storage.getPublicUrl() + objectName;
		} else {
			java.io.File destFile = FileUtil.saveFile(file, storage.getUploadDir(), dateFolder, storeName);
			imageUrl = storage.getPublicUrl() + dateFolder + "/" + destFile.getName();
		}

		// DB Insert (ImageCd 3 for Summernote images)
		TnProductImageVo toInsert = new TnProductImageVo();
		toInsert.setProductId(productId);
		toInsert.setImageCd("3"); // 1: 상품이미지, 3: 에디터이미지
		toInsert.setImageUrl(imageUrl);
		toInsert.setImageName(storeName);
		toInsert.setImageSize(file.getSize());
		toInsert.setImageType(file.getContentType());
		toInsert.setRepresent(0);
		toInsert.setRegisterNo(userNo);
		toInsert.setUpdusrNo(userNo);

		commonMybatisDao.update("mgt.product.insertProductImage", toInsert);

		result.put("url", imageUrl);
		result.put("imageId", toInsert.getImageId());
		return result;
	}

	/**
	 * <PRE>
	 * 1. MethodName    : deleteSummernoteImage
	 * 2. ClassName     : ProducterviceImpl
	 * 3. Comment       : Summernote 이미지 삭제
	 * 4. 작성자            : SooHyun.Seo
	 * 5. 작성일            : 2026. 03. 02.
	 * </PRE>
	 * 
	 * @param param
	 * @throws Exception
	 */
	@Override
	public void deleteSummernoteImage(DataMap param) throws Exception {
		String imageIdStr = param.getString("imageId");
		String src = param.getString("src");

		if (imageIdStr == null || imageIdStr.isEmpty()) {
			// imageId가 없는 경우 URL(src)로 조회 시도
			if (src != null && !src.isEmpty()) {
				DataMap searchParam = new DataMap();
				searchParam.put("imageUrl", src);
				TnProductImageVo img = (TnProductImageVo) commonMybatisDao
						.selectOne("mgt.product.selectProductImageByUrl", searchParam);
				if (img != null) {
					imageIdStr = String.valueOf(img.getImageId());
				}
			}
		}

		if (imageIdStr != null && !imageIdStr.isEmpty()) {
			Long imageId = Long.valueOf(imageIdStr);
			TnProductImageVo img = (TnProductImageVo) commonMybatisDao
					.selectOne("mgt.product.selectProductImageByImageId", imageId);
			if (img != null) {
				com.whomade.kycarrots.framework.common.util.file.FilePathResolver.Storage storage = resolver.resolve("product/editor");
				if ("Y".equalsIgnoreCase(storage.getStorageType())) {
					String imageUrl = img.getImageUrl();
					String publicUrl = storage.getPublicUrl();
					if (imageUrl != null && imageUrl.startsWith(publicUrl)) {
						String objectName = imageUrl.substring(publicUrl.length());
						ociService.deleteFile(storage.getNamespace(), storage.getBucketName(), objectName);
					}
				} else {
					// 물리 파일 삭제
					String baseDir = storage.getUploadDir();
					FileUtil.deleteFile(baseDir, String.valueOf(img.getProductId()), img.getImageName());
				}
				// DB 삭제
				commonMybatisDao.delete("mgt.product.deleteProductImageByImageId", imageId);
			}
		}
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: deleteProduct
	 * 2. ClassName  	: ProducterviceImpl
	 * 3. Comment   	: 상품 삭제
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2025. 09. 19. 오후 4:08:41
	 * </PRE>
	 * 
	 * @param param
	 * @throws Exception
	 */
	public void deleteProduct(DataMap param) throws Exception {
		// 0) 필수값
		String productIdStr = param.getString("productId");
		if (productIdStr == null || productIdStr.isEmpty()) {
			throw new IllegalArgumentException("productId가 없습니다.");
		}
		Long productId = Long.valueOf(productIdStr);

		// 1) 이미지 목록 조회
		List<TnProductImageVo> images = commonMybatisDao.selectList("mgt.product.selectProductImagesByProductId",
				productId);

		// 2) 물리 파일 삭제
		com.whomade.kycarrots.framework.common.util.file.FilePathResolver.Storage storage = resolver.resolve("product");
		for (TnProductImageVo img : images) {
			try {
				if ("Y".equalsIgnoreCase(storage.getStorageType())) {
					String imageUrl = img.getImageUrl();
					String publicUrl = storage.getPublicUrl();
					if (imageUrl != null && imageUrl.startsWith(publicUrl)) {
						String objectName = imageUrl.substring(publicUrl.length());
						ociService.deleteFile(storage.getNamespace(), storage.getBucketName(), objectName);
					}
				} else {
					String baseDir = storage.getUploadDir();
					boolean deleted = FileUtil.deleteFile(baseDir, productIdStr, img.getImageName());
					if (!deleted) {
						String targetPath = baseDir + java.io.File.separator + productIdStr + java.io.File.separator
								+ img.getImageName();
						log.warn("파일 삭제 실패 또는 존재하지 않음: {}", targetPath);
					}
				}
			} catch (Exception e) {
				log.warn("파일 삭제 중 오류(imageId={}): {}", img.getImageId(), e.getMessage());
			}
		}

		// 3) 이미지 DB 삭제
		commonMybatisDao.delete("mgt.product.deleteProductImagesByProductId", productId);

		// 4) 상품 삭제
		commonMybatisDao.update("mgt.product.deleteProduct", productId);
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateProduct
	 * 2. ClassName  	: ProducterviceImpl
	 * 3. Comment   	: 상품 수정
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2025. 09. 19. 오후 4:08:41
	 * </PRE>
	 * 
	 * @throws Exception
	 */
	public void updateProductStatus(DataMap param) throws Exception {
		TnProductVo tnProductVo = new TnProductVo();
		tnProductVo.setProductId(param.getString("productId"));
		tnProductVo.setUserNo(param.getString("userNo"));
		tnProductVo.setSaleStatus(param.getString("saleStatus"));
		tnProductVo.setRejectReason(param.getString("rejectReason"));
		tnProductVo.setUpdusrNo(param.getString("ss_user_no"));
		commonMybatisDao.update("mgt.product.updateProductStatus", tnProductVo);

		// [추가] 상태가 '판매중(1)'으로 변경될 경우 알림 발송
		if ("1".equals(tnProductVo.getSaleStatus())) {
			String title = "신규 상품 등록";
			String body = "[신상품] 새로운 상품이 등록되었습니다. 지금 확인해보세요!";
			java.util.Map<String, String> payload = java.util.Map.of(
					"targetId", tnProductVo.getProductId(),
					"type", "product",
					"title", title,
					"body", body);
			pushService.sendTargetPush(
					param.getLong("ss_user_no"),
					java.util.Arrays.asList("ROLE_PUB", "ROLE_PROJ", "ROLE_SELL"),
					null, null, null,
					title, body, "PRODUCT_REGISTER", payload);
		}
	}

}
