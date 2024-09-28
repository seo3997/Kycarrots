package com.whomade.kycarrots.framework.common.util.file;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import com.whomade.kycarrots.framework.common.util.EgovWebUtil;
import com.whomade.kycarrots.framework.common.util.StringUtil;
import jakarta.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import com.whomade.kycarrots.framework.common.constant.Globals;
import com.whomade.kycarrots.framework.common.util.EgovPropertiesUtil;
import com.whomade.kycarrots.framework.common.util.SysUtil;
import com.whomade.kycarrots.framework.common.util.file.vo.AtFileVO;

/**
 * 
 * <PRE>
 * 1. ClassName : AtFileMngUtil.java
 * 2. FileName  : AtFileMngUtil.java
 * 3. Package  : egovframework.com.cmm.service
 * 4. Comment  : 
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   :2017.12.22. 오후 5:31:29
 * </PRE>
 */
@Component("AtFileMngUtil")
public class AtFileMngUtil {
	
	public static final int BUFF_SIZE = 2048;
	private static Log log = LogFactory.getLog(AtFileMngUtil.class);
	
	@Resource(name = "egovMessageSource")
	private EgovMessageSource egovMessageSource;

    /**
     * <PRE>
     * 1. MethodName : parseFileInf
     * 2. ClassName  : AtFileMngUtil
     * 3. Comment   : 다중 파일을 업로드(비고 문구 없음)
     * 4. 작성자    : SooHyun.Seo
     * 5. 작성일    : 2017.12.22. 오전 10:11:05
     * </PRE>
     *   @return List<AtFileVO>
     *   @param files
     *   @param atchDocId
     *   @param pathKey
     *   @param ss_user_id
     *   @return
     *   @throws Exception
     */
	public List<AtFileVO> parseFileInf(Map<String, MultipartFile> files, String atchDocId, String pathKey, String ss_user_id) throws Exception {
		// 기본 파일 저장장소를 설정후 넘김
		return parseFileInf(files, atchDocId, pathKey, ss_user_id, EgovPropertiesUtil.getProperty("Globals.fileSaveWebRoot"));
	}
	
	public List<AtFileVO> parseFileInf(Map<String, MultipartFile> files, String atchDocId, String pathKey, String ss_user_id, String webrootYn) throws Exception {
		// 값이 없을경우 기본값 설정
		if(StringUtil.nvl(webrootYn).equals("")){
			webrootYn = EgovPropertiesUtil.getProperty("Globals.fileSaveWebRoot");
		}
		// 기본 파일 저장타입을 설정후 넘김
		return parseFileInf(files, atchDocId, pathKey, ss_user_id, webrootYn, EgovPropertiesUtil.getProperty("Globals.fileSaveType"));
	}
	
    public List<AtFileVO> parseFileInf(Map<String, MultipartFile> files, String atchDocId, String pathKey, String ss_user_id, String webrootYn, String type) throws Exception {
    	
    	Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
    	List<AtFileVO> result  = new ArrayList<AtFileVO>();
    	
    	while (itr.hasNext()) {
    		MultipartFile file 	= (MultipartFile)itr.next();
    		
    		// 값이 없을경우 기본값 설정
    		if(StringUtil.nvl(type).equals("")){
    			type = EgovPropertiesUtil.getProperty("Globals.fileSaveType");
    		}
    		// 값이 없을경우 기본값 설정
    		if(StringUtil.nvl(webrootYn).equals("")){
    			webrootYn = EgovPropertiesUtil.getProperty("Globals.fileSaveWebRoot");
    		}
    		
    		// 파일을 쓴다.
    		result.add(parseFileInf(file, atchDocId, pathKey, ss_user_id, webrootYn, type));
    	}
    	
    	return result;
    }
    
    /**
     * <PRE>
     * 1. MethodName : parseFileInf
     * 2. ClassName  : AtFileMngUtil
     * 3. Comment   : 하나의 파일을 업로드(비고 문구 없음)
     * 4. 작성자    : SooHyun.Seo
     * 5. 작성일    : 2017.12.22. 오전 10:11:19
     * </PRE>
     *   @return AtFileVO
     *   @param file
     *   @param atchDocId
     *   @param pathKey
     *   @param ss_user_id
     *   @return
     *   @throws Exception
     */
    
    public AtFileVO parseFileInf(MultipartFile file, String atchDocId, String pathKey, String ss_user_id) throws Exception {
    	// 기본 파일 저장장소를 설정후 넘김
    	return parseFileInf(file, atchDocId, pathKey, ss_user_id, EgovPropertiesUtil.getProperty("Globals.fileSaveWebRoot"));
    }
    public AtFileVO parseFileInf(MultipartFile file, String atchDocId, String pathKey, String ss_user_id, String webrootYn) throws Exception {
    	// 값이 없을경우 기본값 설정
		if(StringUtil.nvl(webrootYn).equals("")){
			webrootYn = EgovPropertiesUtil.getProperty("Globals.fileSaveWebRoot");
		}
    	// 기본 파일 저장타입을 설정후 넘김
    	return parseFileInf(file, atchDocId, pathKey, ss_user_id, webrootYn, EgovPropertiesUtil.getProperty("Globals.fileSaveType"));
    }
    public AtFileVO parseFileInf(MultipartFile file, String atchDocId, String pathKey, String ss_user_id, String webrootYn, String type) throws Exception {
    	
    	// 값이 없을경우 기본값 설정
		if(StringUtil.nvl(type).equals("")){
			type = EgovPropertiesUtil.getProperty("Globals.fileSaveType");
		}
		// 값이 없을경우 기본값 설정
		if(StringUtil.nvl(webrootYn).equals("")){
			webrootYn = EgovPropertiesUtil.getProperty("Globals.fileSaveWebRoot");
		}
		
    	// 비고는 공란으로 파일쓰기
    	return chgSaveAtFileVO(file, atchDocId, pathKey, ss_user_id, "", webrootYn, type);
    }
    
    /**
     * <PRE>
     * 1. MethodName : chgSaveAtFileVO
     * 2. ClassName  : AtFileMngUtil
     * 3. Comment   : 하나의 파일을 업로드(비고 문구 있음)
     * 4. 작성자    : SooHyun.Seo
     * 5. 작성일    : 2017.12.22. 오전 11:41:41
     * </PRE>
     *   @return AtFileVO
     *   @param file
     *   @param atchDocId
     *   @param pathKey
     *   @param ss_user_id
     *   @param file_rmk
     *   @return
     *   @throws Exception
     */
    public AtFileVO chgSaveAtFileVO(MultipartFile file, String atchDocId, String pathKey, String ss_user_id, String file_rmk) throws Exception {
    	// 기본은 변환으로 파일 저장
    	return chgSaveAtFileVO(file, atchDocId, pathKey, ss_user_id, file_rmk, EgovPropertiesUtil.getProperty("Globals.fileSaveWebRoot"));
    }
    
    public AtFileVO chgSaveAtFileVO(MultipartFile file, String atchDocId, String pathKey, String ss_user_id, String file_rmk, String webrootYn) throws Exception {
    	// 값이 없을경우 기본값 설정
		if(StringUtil.nvl(webrootYn).equals("")){
			webrootYn = EgovPropertiesUtil.getProperty("Globals.fileSaveWebRoot");
		}
    	// 기본은 변환으로 파일 저장
    	return chgSaveAtFileVO(file, atchDocId, pathKey, ss_user_id, file_rmk, webrootYn, EgovPropertiesUtil.getProperty("Globals.fileSaveType"));
    }
    /**
     * <PRE>
     * 1. MethodName : chgSaveAtFileVO
     * 2. ClassName  : AtFileMngUtil
     * 3. Comment   : 하나의 파일을 업로드(비고 문구 있음); FILE_EXT_C 는 globals.java 참조
     * 4. 작성자    : SooHyun.Seo
     * 5. 작성일    : 2017. 12. 22. 오전 10:18:27
     * </PRE>
     *   @return AtFileVO
     *   @param file
     *   @param atchDocId
     *   @param pathKey 
     *   <br/>&nbsp;&nbsp;1. 기본 저장경로(webroot or WEB-INF)에 추가로 패스 지정 [ex)board/]
     *   <br/>&nbsp;&nbsp;2. properties에 선언된 문자열도 사용가능
     *   <br/>&nbsp;&nbsp;* 처음에는 / 넣지않고 끝에는 / 를 넣어야함.
     *   @param ss_user_id
     *   @param file_rmk
     *   @param webrootYn (Y : webroot 저장, N : WEB-INF 저장)
     *   @param type (O : 이미지명.이미지확장자, C : file_id.FILE_EXT_C(선언된확장자), I : file_id.이미지확장자)
     *   @return
     *   @throws Exception
     */
    public AtFileVO chgSaveAtFileVO(MultipartFile file, String atchDocId, String pathKey, String ss_user_id, String file_rmk, String webrootYn, String type) throws Exception {
    	
    	// 값이 없을경우 기본값 설정
		if(StringUtil.nvl(type).equals("")){
			type = EgovPropertiesUtil.getProperty("Globals.fileSaveType");
		}
		// 값이 없을경우 기본값 설정
		if(StringUtil.nvl(webrootYn).equals("")){
			webrootYn = EgovPropertiesUtil.getProperty("Globals.fileSaveWebRoot");
		}
		
    	String filePathString = "";
		String atchFileIdString = "";
		String fileRPathString = "";
		
		// webroot 저장시거나 아닐경우
		if(webrootYn.equals("Y")){
			filePathString = EgovPropertiesUtil.getProperty("Globals.fileWebrootPath");
		}
		else {
			filePathString = EgovPropertiesUtil.getProperty("Globals.filePath");
		}
		
		if (!"".equals(pathKey) && pathKey != null) {
			fileRPathString = EgovPropertiesUtil.getProperty(pathKey);
			
			// 해당 프로퍼티 값이 없을경우 99로 넘어옴.
			if(fileRPathString.equals("99")){
				fileRPathString = pathKey;
				filePathString += pathKey;
			}
			else {
				filePathString += fileRPathString;
			}
		}
		
		// filePathString 마지막에는 년도들 넣어서 구분해준다.
		filePathString += getTimeStamp() + "/";
		fileRPathString += getTimeStamp() + "/";
		
		File saveFolder = new File(EgovWebUtil.filePathBlackList(filePathString));
		
		if (!saveFolder.exists() || saveFolder.isFile()) {
		    saveFolder.mkdirs();
		}
		
		AtFileVO fvo;
		String filePath = "";
			
	    String orginFileName 				= file.getOriginalFilename();
	    String content_type					= file.getContentType();			// contentType
	    atchFileIdString 					= SysUtil.getFileId();				// file_id    
	    
	    int index 		= orginFileName.lastIndexOf(".");
	    String fileExt 	= orginFileName.substring(index + 1);
	    String fileName = atchFileIdString;
	    long _size 		= file.getSize();
	
//	    if (!"".equals(orginFileName)) {
//			filePath = filePathString + File.separator + fileName;
//			file.transferTo(new File(EgovWebUtil.filePathBlackList(filePath)));
//	    }
	    
	    fvo = new AtFileVO();
	    fvo.setFile_id(atchFileIdString);					// file_id   시퀸스로 대체함 2017.12.22
	    fvo.setDoc_id(atchDocId);							// doc_id
	    fvo.setFile_rmk(file_rmk);							// 파일 설명
	    fvo.setFile_nm(orginFileName);						// 파일 원래명
	    // webroot 저장일경우 상대경로 넣어줌
	    if(webrootYn.equals("Y")){
	    	fvo.setFile_rltv_path(EgovPropertiesUtil.getProperty("Globals.fileWebrootURL") + fileRPathString);			// 파일 저장 경로(상대)
	    }
	    fvo.setFile_aslt_path(filePathString);				// 파일 저장 경로(절대)
	    fvo.setFile_size(_size);							// 파일 크기
	    fvo.setSs_user_id(ss_user_id);						// 사용자 id
	    fvo.setContent_type(content_type);					// 파입 타입
	    fvo.setFile_ext_nm(fileExt);							// 파일 확장자
	    
	    // type : O => 원래의 이미지명.이미지확장자
	    // type : C => file_id.FILE_EXT_C(선언된 확장자)
	    // type : I => fild_id.이미지확장자
	    if(type.equals("O")){ writeFile(fvo, file, fileName, EgovWebUtil.filePathBlackList(filePathString)); }
	    if(type.equals("C")){ writeFile2(fvo, file, fileName, EgovWebUtil.filePathBlackList(filePathString)); }
	    if(type.equals("I")){ writeFile3(fvo, file, fileName, EgovWebUtil.filePathBlackList(filePathString)); }
    	
    	return fvo;
    	
    }
    
    /**
     * <PRE>
     * 1. MethodName : writeFile
     * 2. ClassName  : AtFileMngUtil
     * 3. Comment   : 파일명 그대로 쓰기
     * 4. 작성자    : SooHyun.Seo
     * 5. 작성일    : 2017.12.22. 오전 10:12:19
     * </PRE>
     *   @return void
     *   @param fvo
     *   @param file
     *   @param newName
     *   @param stordFilePath
     *   @throws Exception
     */
    protected static void writeFile(AtFileVO fvo, MultipartFile file, String newName, String stordFilePath) throws Exception {
		InputStream stream = null;
		OutputStream bos = null;
	
		try {
		    stream = file.getInputStream();
		    File cFile = new File(EgovWebUtil.filePathBlackList(stordFilePath));
	
		    if (!cFile.isDirectory())
			cFile.mkdir();
	
		    bos = new FileOutputStream(EgovWebUtil.filePathBlackList(stordFilePath + File.separator + fvo.getFile_nm()));
	
		    int bytesRead = 0;
		    byte[] buffer = new byte[BUFF_SIZE];
	
		    while ((bytesRead = stream.read(buffer, 0, BUFF_SIZE)) != -1) {
			bos.write(buffer, 0, bytesRead);
		    }
		} catch (Exception e) {
		    //e.printStackTrace();
		    //throw new RuntimeException(e);	// 보안점검 후속조치
			log.debug("IGNORED: " + e.getMessage());
		} finally {
		    if (bos != null) {
			try {
			    bos.close();
			} catch (Exception ignore) {
			    log.debug("IGNORED: " + ignore.getMessage());
			}
		    }
		    if (stream != null) {
			try {
			    stream.close();
			} catch (Exception ignore) {
			    log.debug("IGNORED: " + ignore.getMessage());
			}
		    }
		}
    }
    
	/**
	 * <PRE>
	 * 1. MethodName : writeFile2
	 * 2. ClassName  : AtFileMngUtil
	 * 3. Comment   : file_id로 쓰기
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오전 10:12:29
	 * </PRE>
	 *   @return void
	 *   @param fvo
	 *   @param file
	 *   @param newName
	 *   @param stordFilePath
	 *   @throws Exception
	 */
	protected static void writeFile2(AtFileVO fvo, MultipartFile file, String newName, String stordFilePath) throws Exception {
		InputStream stream = null;
		OutputStream bos = null;

		try {
			stream = file.getInputStream();
			File cFile = new File(EgovWebUtil.filePathBlackList(stordFilePath));

			if (!cFile.isDirectory())
				cFile.mkdir();

			// 파일 확장자는 고정으로 설정(2013.09.24 SooHyun.Seo : 파일 확장자는 .file로 고정)	    
			bos = new FileOutputStream(EgovWebUtil.filePathBlackList(stordFilePath + File.separator + newName + Globals.FILE_EXT_C));

			int bytesRead = 0;
			byte[] buffer = new byte[BUFF_SIZE];

			while ((bytesRead = stream.read(buffer, 0, BUFF_SIZE)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//throw new RuntimeException(e);	// 보안점검 후속조치
			log.debug("IGNORED: " + e.getMessage());
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception ignore) {
					log.debug("IGNORED: " + ignore.getMessage());
				}
			}
			if (stream != null) {
				try {
					stream.close();
				} catch (Exception ignore) {
					log.debug("IGNORED: " + ignore.getMessage());
				}
			}
		}
	}
	
	/**
     * <PRE>
     * 1. MethodName : writeFile3
     * 2. ClassName  : AtFileMngUtil
     * 3. Comment   : 파일명 변경 하고 확장자는 그대로 사용
     * 4. 작성자    : devmin
     * 5. 작성일    : 2017.12.22. 오전 10:12:19
     * </PRE>
     *   @return void
     *   @param fvo
     *   @param file
     *   @param newName
     *   @param stordFilePath
     *   @throws Exception
     */
    protected static void writeFile3(AtFileVO fvo, MultipartFile file, String newName, String stordFilePath) throws Exception {
		InputStream stream = null;
		OutputStream bos = null;
	
		try {
		    stream = file.getInputStream();
		    File cFile = new File(EgovWebUtil.filePathBlackList(stordFilePath));
	
		    if (!cFile.isDirectory())
			cFile.mkdir();
	
		    /**
		     * 파일이름은 변경하고 확장자는 그대로 파일 저장
		     * 기존 파일이름 그대로 쓰기  ==>  bos = new FileOutputStream(EgovWebUtil.filePathBlackList(stordFilePath + File.separator + fvo.getFile_nm()));
		     */
		    String ext = SysUtil.getFileExtName(fvo.getFile_nm());
		    bos = new FileOutputStream(EgovWebUtil.filePathBlackList(stordFilePath + File.separator + newName +"."+ ext));
	
		    int bytesRead = 0;
		    byte[] buffer = new byte[BUFF_SIZE];
	
		    while ((bytesRead = stream.read(buffer, 0, BUFF_SIZE)) != -1) {
			bos.write(buffer, 0, bytesRead);
		    }
		} catch (Exception e) {
		    //e.printStackTrace();
		    //throw new RuntimeException(e);	// 보안점검 후속조치
			log.debug("IGNORED: " + e.getMessage());
		} finally {
		    if (bos != null) {
			try {
			    bos.close();
			} catch (Exception ignore) {
			    log.debug("IGNORED: " + ignore.getMessage());
			}
		    }
		    if (stream != null) {
			try {
			    stream.close();
			} catch (Exception ignore) {
			    log.debug("IGNORED: " + ignore.getMessage());
			}
		    }
		}
    }
	
    
    /**
     * <PRE>
     * 1. MethodName : deleteFile
     * 2. ClassName  : AtFileMngUtil
     * 3. Comment   : 파일삭제
     * 4. 작성자    : SooHyun.Seo
     * 5. 작성일    : 2017. 12. 22. 오후 3:33:28
     * </PRE>
     *   @return boolean
     *   @param fvoList
     *   @return
     *   @throws Exception
     */
    public boolean deleteFile(List <AtFileVO> fvoList) throws Exception {
    	
    	return deleteFile(fvoList, EgovPropertiesUtil.getProperty("Globals.fileSaveType"));
	}
    
	public boolean deleteFile(List <AtFileVO> fvoList, String type) throws Exception {
    	
		for(int i = 0; i < fvoList.size(); i++){
			AtFileVO fvo = fvoList.get(i);
			
			if(!deleteFile(fvo, EgovPropertiesUtil.getProperty("Globals.fileSaveType"))){
				return false;
			}
		}
		
		return true;
	}
	/**
	 * <PRE>
	 * 1. MethodName : deleteFile
	 * 2. ClassName  : AtFileMngUtil
	 * 3. Comment   : 파일삭제
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.22. 오전 10:29:09
	 * </PRE>
	 *   @return void
	 *   @param fvo
	 *   @throws Exception
	 */
	public boolean deleteFile(AtFileVO fvo) throws Exception {
		// 기본은 변환으로 파일 저장
		return deleteFile(fvo, EgovPropertiesUtil.getProperty("Globals.fileSaveType"));
	}
	
	public boolean deleteFile(AtFileVO fvo, String type) throws Exception {
		// 값이 없을경우 기본값 설정
		if(StringUtil.nvl(type).equals("")){
			type = EgovPropertiesUtil.getProperty("Globals.fileSaveType");
		}
		
		EgovFileTool filetool = new EgovFileTool();
		
		// 파일명으로 삭제
		if(type.equals("O")){
			// 파일 존재하는지 확인
			if(EgovFileTool.checkFileExstByName(fvo.getFile_aslt_path(), fvo.getFile_nm())){
				if(!filetool.deletePath(fvo.getFile_aslt_path() + fvo.getFile_nm()).equals("")){
					return true;
				} else {
					return false;
				}
			}
		}
		// file_id로 삭제(확장자 변경)
		if(type.equals("C")){
			// 파일 존재하는지 확인
			if(EgovFileTool.checkFileExstByName(fvo.getFile_aslt_path(), fvo.getFile_id() + Globals.FILE_EXT_C)){
				if(!filetool.deletePath(fvo.getFile_aslt_path() + fvo.getFile_id() + Globals.FILE_EXT_C).equals("")){
					return true;
				} else {
					return false;
				}
			}
		}
		
		// file_id로 삭제(확장자는 기존)
		if(type.equals("I")){
			// 파일 존재하는지 확인
			if(EgovFileTool.checkFileExstByName(fvo.getFile_aslt_path(), fvo.getFile_id() + "." + fvo.getFile_ext_nm())){
				if(!filetool.deletePath(fvo.getFile_aslt_path() + fvo.getFile_id() + "." + fvo.getFile_ext_nm()).equals("")){
					return true;
				} else {
					return false;
				}
			}
		}
		
		return false;
	}
	
	// 확장자 체크
	public String checkFileExt(List fileList) throws Exception {
		String exceptExtNames = "EXE,BAT,COM,JSP,ASP,HTML,PHP,SH";	//제외될 파일 확장자
		
		String checkMsg = "";
		
		
		for(int i = 0; i < fileList.size(); i++){
			MultipartFile mfile = (MultipartFile)fileList.get(i);
			// 파일명이 존재하는경우
			if(!mfile.getOriginalFilename().equals("")){
				if(exceptExtNames.indexOf((mfile.getOriginalFilename().substring(mfile.getOriginalFilename().lastIndexOf(".")+1)).toUpperCase()) > -1){
					checkMsg = egovMessageSource.getMessage("error.file.ext", new String[]{mfile.getOriginalFilename().substring(mfile.getOriginalFilename().lastIndexOf(".")+1)});
					break;
				}
			}
		}
		
		return checkMsg;
	}
	
	public String checkFileAcceptExt(List fileList, String fileExt) throws Exception {
		String checkMsg = "";
		
		for(int i = 0; i < fileList.size(); i++){
			MultipartFile mfile = (MultipartFile)fileList.get(i);
			// 파일명이 존재하는경우
			if(!mfile.getOriginalFilename().equals("")){
				if(fileExt.indexOf((mfile.getOriginalFilename().substring(mfile.getOriginalFilename().lastIndexOf(".")+1)).toUpperCase()) == -1){
					checkMsg = egovMessageSource.getMessage("error.file.ext", new String[]{mfile.getOriginalFilename().substring(mfile.getOriginalFilename().lastIndexOf(".")+1)});
					break;
				}
			}
		}
		
		return checkMsg;
	}
	
	// 파일 크기 비교(총 크기)
	public boolean checkFileSize(List fileList) throws Exception {
		return checkFileSize(fileList, "Globals.fileMaxSize");
	}
	
	// 파일 크기 비교(총 크기)
	public boolean checkFileSize(List fileList, String propertyName) throws Exception {
		boolean result = true;
		long size = 0;
		
		if(!fileList.isEmpty()){
			for(int i=0; i < fileList.size(); i++){
				MultipartFile mfile = (MultipartFile)fileList.get(i);
				
				if(!mfile.isEmpty()){
					// 파일 크기 합산
					size += mfile.getSize();
				}
			}
		}
		
		// 사이즈가 클경우
		if(size > Long.parseLong(EgovPropertiesUtil.getProperty(propertyName))){
			result = false;
		}
		return result;
	}
	
	// 파일 크기 비교(각각 크기)
	public boolean checkEachFileSize(List fileList) throws Exception {
		return checkEachFileSize(fileList, "Globals.fileMaxSize");
	}
	
	// 파일 크기 비교(각각 크기)
	public boolean checkEachFileSize(List fileList, String propertyName) throws Exception {
		boolean result = true;
		
		if(!fileList.isEmpty()){
			for(int i=0; i < fileList.size(); i++){
				MultipartFile mfile = (MultipartFile)fileList.get(i);
				
				if(!mfile.isEmpty()){
					// 파일 크기 비교
					long size 		= mfile.getSize();
					// 사이즈가 클경우
					if(size > Long.parseLong(EgovPropertiesUtil.getProperty(propertyName))){
						result = false;
						break;
					}
				}
			}
		}
		
		
		return result;
	}
	
	// byte 단위 크기를 보기쉽게 표현
	public static String getFileSize(String size){
		String gubn[] = {"Byte", "KB", "MB" } ;
		String returnSize = new String ();
		int gubnKey = 0;
		double changeSize = 0;
		long fileSize = 0;
		
		try{
			fileSize =  Long.parseLong(size);
			for( int x=0 ; (fileSize / (double)1024 ) >0 ; x++, fileSize/= (double) 1024 ){
				gubnKey = x;
				changeSize = fileSize;
			}
			returnSize = changeSize + gubn[gubnKey];
		}catch ( Exception ex){ returnSize = "0.0 Byte"; }
		
		return returnSize;
	}
	
	// MultipartHttpServletRequest 에서 file 객체 가져오기
	// upload 객체에 빈값으로 넘어와도 그냥 가져오면 size가 1이 되어서 비어도 있는것처럼 보이게 된다.
	public static List<MultipartFile> getFiles(MultipartHttpServletRequest request){
		
		return getFiles(request, "upload");
	}
	
	// MultipartHttpServletRequest 에서 file 객체 가져오기
	// upload 객체에 빈값으로 넘어와도 그냥 가져오면 size가 1이 되어서 비어도 있는것처럼 보이게 된다.
	public static List<MultipartFile> getFiles(MultipartHttpServletRequest request, String name){
		List files = new ArrayList();
		
		List t_files = request.getFiles(name);
		
		for(int i=0; i < t_files.size(); i++){
			MultipartFile mfile = (MultipartFile)t_files.get(i);
			if(!mfile.isEmpty()){
				files.add(mfile);
			}
		}
		
		return files;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : copyFiles
	 * 2. ClassName  : AtFileMngUtil
	 * 3. Comment   : 파일 복사
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017. 12. 21. 15:43
	 * </PRE>
	 *   @return boolean
	 *   @return
	 *   @throws Exception
	 */
	public static boolean copyFiles(AtFileVO _reAtFile) throws Exception {

		final char FILE_SEPARATOR = File.separatorChar;
//		final char FILE_SEPARATOR = '/';
		
		boolean result = false;
		File f = null;
		try {
			String originalDirPathAbs = EgovWebUtil.filePathBlackList(_reAtFile.getFile_aslt_path()); //=> D:/eGovFrameDev-3.0.0-32bit-SMP/workspace/AllNewSMP/src/main/webapp/upload//cntnts/temp/
			String targetDirPathAbs = EgovWebUtil.filePathBlackList(EgovPropertiesUtil.getProperty("Globals.fileStillcutPath")); //=> D:/eGovFrameDev-3.0.0-32bit-SMP/workspace/AllNewSMP/src/main/webapp/upload/cntnts/
			targetDirPathAbs += getTimeStamp() + "/";
			f = new File(originalDirPathAbs);
			
			
			// 원본은 유효하고 대상이 신규로 생성가능한 상태인경우만 진행한다.
			//if(f.exists() && f.isDirectory() ){ // 디렉토리만 이동할수 있도록 제한하는 경우
			if (f.exists()) {
				// 타겟으로 설정한 경로가 유효한지 확인(중간경로에 파일명 이 포함되어있으면 유효하지 못하므로 진행안함.
				File targetDir = new File(targetDirPathAbs);

				// 타겟 경로 생성
				if(!targetDir.exists()) {
					EgovFileTool.createDirectory(targetDirPathAbs);
//					targetDir.mkdirs();
				}
				
				String fileNm = _reAtFile.getFile_id(); //=> 1442463082951RXWME0LMKOE0MGZEAWKHP2UJS
				fileNm += "." + _reAtFile.getFile_ext_nm(); //=> 1442463082951RXWME0LMKOE0MGZEAWKHP2UJS.jpg

				result = execCommand("copy", _reAtFile.getFile_aslt_path()+"/"+fileNm, targetDirPathAbs);

			} else {
				// 원본자체가 유효하지 않은 경우는 false 리턴하고 종료
				result = false;
			}
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		} finally {

		}	
		return result;
	}
	
	public static boolean execCommand(String cmd, String originalDirPath, String targetDirPath) throws Exception {

		final char FILE_SEPARATOR = File.separatorChar;
		// 최대 문자길이
		final int MAX_STR_LEN = 1024;
		
		boolean result = false;
		BufferedReader b_err = null;
		BufferedReader b_out = null;
		try {
//			String originalDirPathAbs = Globals.FILE_PATH + FILE_SEPARATOR + originalDirPath;
//			String targetDirPathAbs = Globals.FILE_PATH + FILE_SEPARATOR + targetDirPath;
			
			//SHELL.win.copy
			String cmdStr = EgovPropertiesUtil.getProperty("SHELL." + EgovPropertiesUtil.getProperty("Globals.osType") + "." + cmd);
			
			String commandStr = cmdStr + " "+ EgovWebUtil.filePathBlackList(originalDirPath).replace('\\', '/').replace("//", "/").replace('/', FILE_SEPARATOR) 
					+" "+ EgovWebUtil.filePathBlackList(targetDirPath).replace('\\', '/').replace("//", "/").replace('/', FILE_SEPARATOR);
			Process p = Runtime.getRuntime().exec(commandStr);
			//Process p = Runtime.getRuntime().exec("cmd /c copy /Y D:/eGovFrameDev-3.0.0-32bit/test/doc/temp_doc/test*, D:/eGovFrameDev-3.0.0-32bit/test/doc");
			
			//String access = "";
			p.waitFor();
			//프로세스 에러시 종료
			if (p != null && p.exitValue() != 0) {
				b_err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				while (b_err.ready()) {
					String line = b_err.readLine();
//					if (line.length() <= MAX_STR_LEN) log("ERR\n" + line);
				}
				b_err.close();
			}
			//프로세스 실행 성공시 결과 확인
			else {
				result = true;
			}
		} catch (Exception e) {
//			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			close(b_err);
			close(b_out);
		}	
		return result;
	} 
	
	protected static void close(Closeable closable) {
		if (closable != null) {
			try {
				closable.close();
			} catch (IOException ignore) {
//				 log("IGNORE: " + ignore);
			}
		}
	}
	
	private static String getTimeStamp() {

		String rtnStr = null;

		// 문자열로 변환하기 위한 패턴 설정(년도-월-일 시:분:초:초(자정이후 초))
		String pattern = "yyyyMMdd";

		SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
		Timestamp ts = new Timestamp(System.currentTimeMillis());

		rtnStr = sdfCurrent.format(ts.getTime());

		return rtnStr;
	}
	
	
    /**
     * 이미지 파일의 해쉬코드를 생성한다
     * 
     */
    public String getImageHash(String filePath) throws Exception {
    	String hashStr = "";
    	
    	File mfile = new File(filePath);	//파일 존재 확인
    	if(mfile.exists()) {
			BufferedImage bufImg 				= ImageIO.read(mfile);
			ByteArrayOutputStream outputStream	= new ByteArrayOutputStream();
			ImageIO.write(bufImg, "JPG", outputStream);
			
			byte [] data = outputStream.toByteArray();
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(data);
			byte[] hash = md.digest();
			hashStr = returnHex(hash);
    	}
    	return hashStr;
    }

    /**
     * 파일의 해쉬코드를 생성한다
     * 
     */
    public String getFileHash(String filePath) throws Exception {
    	String hashStr = "";
    	
    	File mfile = new File(filePath);	//파일 존재 확인
    	if(mfile.exists()) {
			BufferedImage bufImg 				= ImageIO.read(mfile);
			ByteArrayOutputStream outputStream	= new ByteArrayOutputStream();
			ImageIO.write(bufImg, "JPG", outputStream);
			
			byte [] data = outputStream.toByteArray();
		
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(data);
			byte[] hash = md.digest();
			hashStr = returnHex(hash);
    	}
    	return hashStr;
    }
    
    /**
     * 해쉬 코드 추출
     * 
     */
    public String returnHex(byte[] inBytes) throws Exception {
        String hexString = "";
        for (int i=0; i < inBytes.length; i++) { //for loop ID:1
        	hexString +=
            Integer.toString( ( inBytes[i] & 0xff ) + 0x100, 16).substring( 1 );
        }                                   // Belongs to for loop ID:1
        return hexString;
	} 
	
    
    public static String checksum(String filepath, MessageDigest md) throws IOException {
        // DigestInputStream is better, but you also can hash file like this.\
    	
	    String md5 = "";
    	try (InputStream is = Files.newInputStream(Paths.get(filepath))) {
    	    md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(is);
    	}
    	
    	/*	
        try (InputStream fis = new FileInputStream(filepath)) {
            byte[] buffer = new byte[1024];
            int nread;
            while ((nread = fis.read(buffer)) != -1) {
                md.update(buffer, 0, nread);
            }
        }
        // bytes to hex
        StringBuilder result = new StringBuilder();
        for (byte b : md.digest()) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
        */

        return md5;
    }
    
    public static String checksumString(String fileStr, MessageDigest md) throws IOException {
        // DigestInputStream is better, but you also can hash file like this.
          md.update(fileStr.getBytes());

        // bytes to hex
        StringBuilder result = new StringBuilder();
        for (byte b : md.digest()) {
            result.append(String.format("%02x", b));
        }
        return result.toString();

    }
    
}
