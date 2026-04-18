package com.whomade.kycarrots.framework.common.util.file;

import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.imageio.ImageIO;

import jakarta.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.whomade.kycarrots.framework.common.constant.Globals;
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import com.whomade.kycarrots.framework.common.util.EgovPropertiesUtil; // (Globals.FILE_EXT_C 등 사용 용도)
import com.whomade.kycarrots.framework.common.util.StringUtil;
import com.whomade.kycarrots.framework.common.util.SysUtil;
import com.whomade.kycarrots.framework.common.util.file.vo.AtFileVO;

/**
 * 파일 저장 유틸(개편판)
 * - pathKey: "product" 또는 "board"
 * - Spring 설정(file.*)과 FilePathResolver를 사용
 */
@Component("AtFileMngUtil")
public class AtFileMngUtil {

	public static final int BUFF_SIZE = 2048;
	private static final Log log = LogFactory.getLog(AtFileMngUtil.class);

	@Resource(name = "egovMessageSource")
	private EgovMessageSource egovMessageSource;

	private final FilePathResolver resolver;
	private final OciObjectStorageService ociService;

	public AtFileMngUtil(FilePathResolver resolver, OciObjectStorageService ociService) {
		this.resolver = resolver;
		this.ociService = ociService;
	}

	/* ========= 업로드 용량 제한 (Spring 설정) ========= */
	@Value("${file.max-size-total:50MB}")
	private String maxSizeTotalConf;   // 요청 내 모든 파일 합계

	@Value("${file.max-size-each:10MB}")
	private String maxSizeEachConf;    // 개별 파일 최대

	private long toBytes(String v) {
		try {
			return DataSize.parse(v).toBytes();
		} catch (Exception ignore) {
			try { return Long.parseLong(v.trim()); } catch (Exception e) { return 0L; }
		}
	}

	/* ===========================
	 * Public API (기존 시그니처 호환)
	 * =========================== */

	// 다중 파일 업로드 (비고 없음) - 기본 타입 "I"(file_id.원래확장자)
	public List<AtFileVO> parseFileInf(Map<String, MultipartFile> files,
									   String atchDocId,
									   String pathKey,
									   String ss_user_id) throws Exception {
		return parseFileInf(files, atchDocId, pathKey, ss_user_id, "I");
	}

	// 다중 파일 업로드 (비고 없음, 타입 지정: O/C/I)
	public List<AtFileVO> parseFileInf(Map<String, MultipartFile> files,
									   String atchDocId,
									   String pathKey,
									   String ss_user_id,
									   String type) throws Exception {
		List<AtFileVO> result = new ArrayList<>();
		if (files == null || files.isEmpty()) return result;

		for (Map.Entry<String, MultipartFile> e : files.entrySet()) {
			MultipartFile file = e.getValue();
			if (file == null || file.isEmpty()) continue;
			result.add(chgSaveAtFileVO(file, atchDocId, pathKey, ss_user_id, "", type));
		}
		return result;
	}

	// 단일 파일 업로드 (비고 없음)
	public AtFileVO parseFileInf(MultipartFile file,
								 String atchDocId,
								 String pathKey,
								 String ss_user_id) throws Exception {
		return chgSaveAtFileVO(file, atchDocId, pathKey, ss_user_id, "", "I");
	}

	// 단일 파일 업로드 (비고 있음)
	public AtFileVO chgSaveAtFileVO(MultipartFile file,
									String atchDocId,
									String pathKey,
									String ss_user_id,
									String file_rmk) throws Exception {
		return chgSaveAtFileVO(file, atchDocId, pathKey, ss_user_id, file_rmk, "I");
	}

	/**
	 * 단일 파일 업로드 (비고/타입 지정)
	 * type:
	 *  - O: 원래파일명 그대로 저장
	 *  - C: file_id + Globals.FILE_EXT_C (예: .file)
	 *  - I: file_id + 원래 확장자  ← 기본
	 */
	public AtFileVO chgSaveAtFileVO(MultipartFile file,
									String atchDocId,
									String pathKey,
									String ss_user_id,
									String file_rmk,
									String type) throws Exception {

		if (file == null || file.isEmpty()) throw new IllegalArgumentException("file is empty");
		if (StringUtil.nvl(pathKey).isEmpty()) throw new IllegalArgumentException("pathKey is empty");

		FilePathResolver.Storage storage = resolver.resolve(pathKey);

		// 날짜 폴더 (yyyyMMdd)
		String dateFolder = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);

		// 절대 저장 경로 (예: E:/uploads/board/yyyyMMdd)
		Path absDir = Paths.get(storage.getUploadDir(), dateFolder);
		Files.createDirectories(absDir);

		String originalName = sanitizeFilename(file.getOriginalFilename());
		String fileId = SysUtil.getFileId();
		String contentType = file.getContentType();
		long size = file.getSize();

		String ext = getExt(originalName);

		// 저장 파일명
		String storeName;
		if ("O".equalsIgnoreCase(type)) {
			storeName = originalName;
		} else if ("C".equalsIgnoreCase(type)) {
			storeName = fileId + Globals.FILE_EXT_C;
		} else { // "I" default
			storeName = fileId + (ext.isEmpty() ? "" : "." + ext);
		}

		// 저장
		if ("Y".equalsIgnoreCase(storage.getStorageType())) {
			// Bucket Upload - DB 경로와 일치하도록 pathKey/dateFolder/fileName 구조로 업로드
			String pathPrefix = (subPath != null && !subPath.isEmpty()) ? subPath + "/" : "";
			String objectName = pathPrefix + dateFolder + "/" + storeName;
			ociService.uploadFile(storage.getNamespace(), storage.getBucketName(), objectName, file);
		} else {
			// Local Save
			Path target = absDir.resolve(storeName);
			try (InputStream in = file.getInputStream()) {
				Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
			}
		}

		// VO 세팅
		AtFileVO fvo = new AtFileVO();
		fvo.setFile_id(fileId);
		fvo.setDoc_id(atchDocId);
		fvo.setFile_rmk(file_rmk);
		fvo.setFile_nm(originalName);
		fvo.setFile_aslt_path(absDir.toString() + File.separator + storeName);  // 절대 경로(파일명 포함)
		fvo.setFile_rltv_path(storage.getPublicUrl() + dateFolder + "/" + storeName);   // 공개 URL(파일명 포함)
		fvo.setFile_size(size);
		fvo.setSs_user_id(ss_user_id);
		fvo.setContent_type(contentType);
		fvo.setFile_ext_nm(ext);

		return fvo;
	}

	/* ===========================
	 * 삭제
	 * =========================== */

	public boolean deleteFile(List<AtFileVO> list) throws Exception {
		if (list == null || list.isEmpty()) return true;
		boolean ok = true;
		for (AtFileVO fvo : list) ok &= deleteFile(fvo);
		return ok;
	}

	// 타입 지정 리스트 삭제 (O/C/I)
	public boolean deleteFile(List<AtFileVO> list, String type) throws Exception {
		if (list == null || list.isEmpty()) return true;
		boolean ok = true;
		for (AtFileVO fvo : list) ok &= deleteFile(fvo, type);
		return ok;
	}

	// 타입 지정 단건 삭제 (O/C/I)
	public boolean deleteFile(AtFileVO fvo, String type) throws Exception {
		if (fvo == null) return false;
		String t = (type == null) ? "" : type.trim().toUpperCase(Locale.ROOT);

		Path base = Paths.get(String.valueOf(fvo.getFile_aslt_path()));

		String fileId = String.valueOf(fvo.getFile_id());
		String ext    = String.valueOf(fvo.getFile_ext_nm());
		String origin = sanitizeFilename(String.valueOf(fvo.getFile_nm()));

		Path target;
		switch (t) {
			case "O":
				target = base.resolve(origin);
				break;
			case "C":
				target = base.resolve(fileId + Globals.FILE_EXT_C);
				break;
			case "I":
			default:
				target = base.resolve(fileId + (ext.isEmpty() ? "" : "." + ext));
				break;
		}

		try {
			// AtFileVO에 storage_type이 없으므로 현재 global 설정을 따르거나 
			// file_rltv_path가 http로 시작하는지 등으로 판단할 수도 있음.
			// 여기서는 resolver를 통해 현재 설정을 가져와서 처리함.
			// 주의: 과거 파일이 로컬에 있고 현재 설정이 Y라면 로컬 삭제가 안될 수 있음.
			
			// 일단 global 설정을 따름
			FilePathResolver.Storage storage = resolver.getGlobalConfig(); // Need to add this method or similar

			if ("Y".equalsIgnoreCase(storage.getStorageType())) {
				String rltvPath = fvo.getFile_rltv_path();
				String publicUrl = storage.getPublicUrl();
				String objectName;
				if (rltvPath.startsWith(publicUrl)) {
					objectName = rltvPath.substring(publicUrl.length());
				} else {
					// Fallback to legacy parsing
					String fullPath = fvo.getFile_aslt_path().replace("\\", "/");
					if (fullPath.endsWith("/")) fullPath = fullPath.substring(0, fullPath.length()-1);
					String dateFolder = fullPath.substring(fullPath.lastIndexOf("/") + 1);
					objectName = dateFolder + "/" + target.getFileName().toString();
				}
				
				ociService.deleteFile(storage.getNamespace(), storage.getBucketName(), objectName);
				return true;
			} else {
				if (target.toFile().exists()) {
					return Files.deleteIfExists(target);
				} else {
					// Check if fvo.getFile_aslt_path() is the full path
					Path asltPath = Paths.get(fvo.getFile_aslt_path());
					return Files.deleteIfExists(asltPath);
				}
			}
		} catch (Exception e) {
			log.warn("Delete failed: " + target + " - " + e.getMessage());
			return false;
		}
	}

	// 타입 모를 때 자동 시도(I -> C -> O)
	public boolean deleteFile(AtFileVO fvo) throws Exception {
		Path base = Paths.get(fvo.getFile_aslt_path());
		String ext = StringUtil.nvl(fvo.getFile_ext_nm());
		String fileId = fvo.getFile_id();
		String origin = sanitizeFilename(fvo.getFile_nm());

		Path pI = base.resolve(fileId + (ext.isEmpty() ? "" : "." + ext));
		Path pC = base.resolve(fileId + Globals.FILE_EXT_C);
		Path pO = base.resolve(origin);

		return deleteIfExists(pI) || deleteIfExists(pC) || deleteIfExists(pO);
	}

	private boolean deleteIfExists(Path p) {
		try {
			return Files.deleteIfExists(p);
		} catch (IOException e) {
			log.warn("Delete failed: " + p + " - " + e.getMessage());
			return false;
		}
	}

	/* ===========================
	 * 검증/유틸
	 * =========================== */

	// 총 용량(요청 내 모든 파일 합계) 체크
	public boolean checkFileSize(List files) {
		long maxBytes = toBytes(maxSizeTotalConf);
		long sum = 0;
		for (Object o : files) {
			MultipartFile mf = (MultipartFile) o;
			if (mf != null && !mf.isEmpty()) sum += mf.getSize();
		}
		return sum <= maxBytes;
	}

	/** @deprecated propertyName은 사용하지 않습니다. Spring 설정(file.max-size-total)을 사용합니다. */
	@Deprecated
	public boolean checkFileSize(List files, String propertyName) {
		return checkFileSize(files);
	}

	// 개별 파일 용량 체크
	public boolean checkEachFileSize(List files) {
		long maxEach = toBytes(maxSizeEachConf);
		for (Object o : files) {
			MultipartFile mf = (MultipartFile) o;
			if (mf != null && !mf.isEmpty() && mf.getSize() > maxEach) return false;
		}
		return true;
	}

	/** @deprecated propertyName은 사용하지 않습니다. Spring 설정(file.max-size-each)을 사용합니다. */
	@Deprecated
	public boolean checkEachFileSize(List files, String propertyName) {
		return checkEachFileSize(files);
	}

	// 업로드 파라미터에서 빈 파일 제거하여 가져오기
	public static List<MultipartFile> getFiles(MultipartHttpServletRequest request) {
		return getFiles(request, "upload");
	}

	public static List<MultipartFile> getFiles(MultipartHttpServletRequest request, String name) {
		List<MultipartFile> out = new ArrayList<>();
		List<MultipartFile> in = request.getFiles(name);
		for (MultipartFile mf : in) {
			if (mf != null && !mf.isEmpty()) out.add(mf);
		}
		return out;
	}

	// 확장자 블록 리스트 체크
	public String checkFileExt(List files) {
		String exceptExtNames = "EXE,BAT,COM,JSP,ASP,HTML,PHP,SH";
		String checkMsg = "";
		for (Object o : files) {
			MultipartFile mf = (MultipartFile) o;
			if (mf != null && !mf.isEmpty()) {
				String ext = getExt(mf.getOriginalFilename()).toUpperCase(Locale.ROOT);
				if (exceptExtNames.contains(ext)) {
					checkMsg = egovMessageSource.getMessage("error.file.ext", new String[]{ext});
					break;
				}
			}
		}
		return checkMsg;
	}

	// 허용 확장자 화이트리스트 체크 (대문자 CSV 문자열)
	public String checkFileAcceptExt(List files, String acceptExtCsvUppercase) {
		String checkMsg = "";
		for (Object o : files) {
			MultipartFile mf = (MultipartFile) o;
			if (mf != null && !mf.isEmpty()) {
				String ext = getExt(mf.getOriginalFilename()).toUpperCase(Locale.ROOT);
				if (!acceptExtCsvUppercase.contains(ext)) {
					checkMsg = egovMessageSource.getMessage("error.file.ext", new String[]{ext});
					break;
				}
			}
		}
		return checkMsg;
	}

	// 이미지 해시
	public String getImageHash(String filePath) throws Exception {
		String hashStr = "";
		File mfile = new File(filePath);
		if (mfile.exists()) {
			BufferedImage bufImg = ImageIO.read(mfile);
			try (java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream()) {
				ImageIO.write(bufImg, "JPG", outputStream);
				byte[] data = outputStream.toByteArray();
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(data);
				byte[] hash = md.digest();
				hashStr = returnHex(hash);
			}
		}
		return hashStr;
	}

	// 파일 해시(호환성 위해 이미지 방식 재사용)
	public String getFileHash(String filePath) throws Exception {
		return getImageHash(filePath);
	}

	public String returnHex(byte[] inBytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : inBytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	public static String checksum(String filepath, MessageDigest md) throws IOException {
		try (InputStream is = Files.newInputStream(Paths.get(filepath))) {
			return org.apache.commons.codec.digest.DigestUtils.md5Hex(is);
		}
	}

	public static String checksumString(String fileStr, MessageDigest md) {
		md.update(fileStr.getBytes());
		StringBuilder result = new StringBuilder();
		for (byte b : md.digest()) result.append(String.format("%02x", b));
		return result.toString();
	}

	/* ===========================
	 * 내부 헬퍼
	 * =========================== */

	private static String getExt(String filename) {
		if (filename == null) return "";
		String name = filename.trim();
		int dot = name.lastIndexOf('.');
		if (dot < 0 || dot == name.length() - 1) return "";
		return name.substring(dot + 1)
				.replaceAll("[^A-Za-z0-9]", "")
				.toLowerCase(Locale.ROOT);
	}

	private static String sanitizeFilename(String filename) {
		if (filename == null) return "file";
		String name = filename.replace("\\", "/");
		name = name.substring(name.lastIndexOf('/') + 1);
		return name.replaceAll("[\\r\\n\\t]", "_");
	}

	@SuppressWarnings("unused")
	private static String getTimeStampLegacy() {
		String pattern = "yyyyMMdd";
		SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		return sdfCurrent.format(ts.getTime());
	}

	protected static void close(Closeable closable) {
		if (closable != null) {
			try { closable.close(); } catch (IOException ignore) {}
		}
	}
	public String humanReadable(String sizeOrDataSize) {
		long bytes = toBytes(sizeOrDataSize); // "10MB" 또는 "10485760" 모두 처리
		if (bytes <= 0) return "0B";
		final String[] u = {"B","KB","MB","GB","TB"};
		int i = (int) Math.floor(Math.log(bytes) / Math.log(1024));
		double v = bytes / Math.pow(1024, i);
		return String.format(Locale.KOREA, "%.1f%s", v, u[i]);
	}
}
