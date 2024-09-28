package com.whomade.kycarrots.framework.common.util.file.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Map;

import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.StringUtil;
import com.whomade.kycarrots.framework.common.util.file.service.AtFileMngService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whomade.kycarrots.framework.common.constant.Globals;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.EgovPropertiesUtil;
import com.whomade.kycarrots.framework.common.util.file.vo.AtFileVO;

/**
 * 파일 다운로드를 위한 컨트롤러 클래스
 * @author SooHyun.Seo
 * @since 2017.12.22
 * @version 1.0
 * @see
 */
@Controller
public class AtFileDownloadController {

	@Resource(name = "AtFileMngService")
	private AtFileMngService fileService;
	
	private int BUFFER_SIZE = 8192;

	private static Log log = LogFactory.getLog(AtFileDownloadController.class);

	/**
	 * 브라우저 구분 얻기.
	 * 
	 * @param request
	 * @return
	 */
	private String getBrowser(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		if (header.indexOf("MSIE") > -1 || header.indexOf("Trident") > -1) {
			return "MSIE";
		} else if (header.indexOf("Chrome") > -1 || header.indexOf("Android") > -1) {
			return "Chrome";
		} else if (header.indexOf("Opera") > -1) {
			return "Opera";
		}
		return "Firefox";
	}

	/**
	 * Disposition 지정하기.
	 * 
	 * @param filename
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void setDisposition(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String browser = getBrowser(request);

		String dispositionPrefix = "attachment; filename=\"";
		String encodedFilename = null;

		if (browser.equals("MSIE")) {
			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
		} else if (browser.equals("Firefox")) {
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Opera")) {
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Chrome")) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < filename.length(); i++) {
				char c = filename.charAt(i);
				if (c > '~') {
					sb.append(URLEncoder.encode("" + c, "UTF-8"));
				} else {
					sb.append(c);
				}
			}
			encodedFilename = sb.toString();
		} else {
			//throw new RuntimeException("Not supported browser");
			throw new IOException("Not supported browser");
		}

		response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename +"\"");

		if ("Opera".equals(browser)) {
			response.setContentType("application/octet-stream;charset=UTF-8");
		}
	}

	/**
	 * 첨부파일로 등록된 파일에 대하여 다운로드를 제공한다.(file_id)
	 * 
	 * @param commandMap
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/common/file/FileDown.do")
	public void cvplFileDownload(
				Map<String, Object> commandMap
				, HttpServletRequest request
				, HttpServletResponse response) throws Exception {
		
    	DataMap param = RequestUtil.getDataMap(request);

		String atchFileId = param.getString("file_id");

//		인증된 사용자가 다운로드 받도록 인증확인 코드 일단 주석처리
//		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

//		if (isAuthenticated) {
			AtFileVO fileVO = new AtFileVO();
			fileVO.setFile_id(atchFileId);
			AtFileVO fvo = fileService.selectFileInf(fileVO);
			
			File uFile;
			
			// d_type 설정이 안되어있으면 기본값
	    	if(StringUtil.nvl(param.getString("d_type")).equals("")){
	    		param.put("d_type", EgovPropertiesUtil.getProperty("Globals.fileSaveType"));
	    	}
			
			// type 별 다운로드 방식 다름
			// 원래파일이름, 확장자
			if(param.getString("d_type").equals("O")){
				uFile = new File(fvo.getFile_aslt_path(), fvo.getFile_nm());
			}
			// 변환 이름, 원래 확장자
			else if(param.getString("d_type").equals("I")){
				uFile = new File(fvo.getFile_aslt_path(), fvo.getFile_id() + "."+fvo.getFile_ext_nm());
			}
			// 변환 이름, 확장자
			else {
				uFile = new File(fvo.getFile_aslt_path(), fvo.getFile_id() + Globals.FILE_EXT_C);
			}
			// .file 을 사용하기에 file확장자로 파일을 검색한다
//			File uFile = new File(fvo.getFile_path(), fvo.getFile_id() + "."+fvo.getFile_ext());
			
//			uFile = new File(fvo.getFile_path(), fvo.getFile_id() + Globals.FILE_EXT_C);
			int fSize = (int) uFile.length();
//			String file_ext = "." + fvo.getFile_ext();

			if (fSize > 0) {
				String mimetype = "application/octet-stream";
				
				if (fvo.getFile_ext_nm().equalsIgnoreCase("hwp")) mimetype = "application/x-hwp";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("mdb")) mimetype = "application/msaccess";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("doc") || fvo.getFile_ext_nm().equalsIgnoreCase("docx")) mimetype = "application/msword";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("bin")) mimetype = "application/octet-stream";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("pdf")) mimetype = "application/pdf";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("ai") || fvo.getFile_ext_nm().equalsIgnoreCase("ps") || fvo.getFile_ext_nm().equalsIgnoreCase("eps")) mimetype = "application/postscript";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("rtf")) mimetype = "application/rtf";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("js"))  mimetype = "application/x-javascript";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("latex")) mimetype = "application/x-latex";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("mif"))  mimetype = "application/x-mif";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("xls") || fvo.getFile_ext_nm().equalsIgnoreCase("xlsx")) mimetype = "application/vnd.ms-excel";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("ppt") || fvo.getFile_ext_nm().equalsIgnoreCase("pptx")) mimetype = "application/vnd.ms-powerpoint";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("tcl")) mimetype = "application/x-tcl";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("tex")) mimetype = "application/x-tex";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("src")) mimetype = "application/x-wais-source";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("zip")) mimetype = "application/zip";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("au") || fvo.getFile_ext_nm().equalsIgnoreCase("snd")) mimetype = "audio/basic";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("wav")) mimetype = "audio/x-wav";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("gif")) mimetype = "image/gif";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("bmp")) mimetype = "image/bmp";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("png")) mimetype = "image/png";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("jpeg") || fvo.getFile_ext_nm().equalsIgnoreCase("jpg") || fvo.getFile_ext_nm().equalsIgnoreCase("jpe")) mimetype = "image/jpeg";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("tiff") || fvo.getFile_ext_nm().equalsIgnoreCase("tif")) mimetype = "image/tiff";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("gzip")) mimetype = "multipart/x-gzip";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("zip"))  mimetype = "multipart/x-zip";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("css"))  mimetype = "tfileType/css";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("html") || fvo.getFile_ext_nm().equalsIgnoreCase("htm"))	mimetype = "tfileType/html";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("rtx")) mimetype = "tfileType/richtfileType";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("xml")) mimetype = "tfileType/xml";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("xsl")) mimetype = "tfileType/xsl";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("mpeg") || fvo.getFile_ext_nm().equalsIgnoreCase("mpg") || fvo.getFile_ext_nm().equalsIgnoreCase("mpe"))	mimetype = "video/mpeg";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("qt") || fvo.getFile_ext_nm().equalsIgnoreCase("mov")) mimetype = "video/quicktime";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("avi")) 	mimetype = "video/x-msvideo";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("movie")) mimetype = "video/x-sgi-movie";
				else if (fvo.getFile_ext_nm().equalsIgnoreCase("mp3")) mimetype = "audio/x-mpeg";
				else mimetype = "application/octet-stream";
				
//				String mimetype = "application/octet-stream;";
				

				//response.setBufferSize(fSize);	// OutOfMemeory 발생
				response.setContentType(mimetype);
				//response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fvo.getOrignlFileNm(), "utf-8") + "\"");
				setDisposition(fvo.getFile_nm(), request, response);
				response.setContentLength(fSize);

				/*
				 * FileCopyUtils.copy(in, response.getOutputStream());
				 * in.close(); 
				 * response.getOutputStream().flush();
				 * response.getOutputStream().close();
				 */
				BufferedInputStream in = null;
				BufferedOutputStream out = null;

				try {
					in = new BufferedInputStream(new FileInputStream(uFile));
					out = new BufferedOutputStream(response.getOutputStream());

					FileCopyUtils.copy(in, out);
					out.flush();
				} catch (Exception ex) {
					//ex.printStackTrace();
					// 다음 Exception 무시 처리
					// Connection reset by peer: socket write error
					log.debug("IGNORED: " + ex.getMessage());
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (Exception ignore) {
							// no-op
							log.debug("IGNORED: " + ignore.getMessage());
						}
					}
					if (out != null) {
						try {
							out.close();
						} catch (Exception ignore) {
							// no-op
							log.debug("IGNORED: " + ignore.getMessage());
						}
					}
				}

			} else {
				response.setContentType("application/octet-stream");

				response.setCharacterEncoding("UTF-8");
				setDisposition(fvo.getFile_nm(), request, response);
				PrintWriter printwriter = response.getWriter();
				
//				printwriter.println(fvo.getFile_nm() + " : 해당파일을 찾을수 없습니다.");
				printwriter.flush();
				printwriter.close();
			}
			
//		}
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : directFileDown
	 * 2. ClassName  : AtFileDownloadController
	 * 3. Comment   : 해당 경로에서 해당 이름의 파일을 다운로드한다.
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 30. 오후 1:21:28
	 * </PRE>
	 *   @return void
	 *   @param commandMap
	 *   @param request
	 *   @param response
	 *   @throws Exception
	 */
	@RequestMapping(value = "/common/file/directFileDown.do")
	public void directFileDown(Map<String, Object> commandMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
//		DataMap param = RequestUtil.getDataMap(request);
//		String pathKey = param.getString("path");
//		String filePathString = "";
//		
//		if ("".equals(pathKey) || pathKey == null) {
//			filePathString = EgovPropertiesUtil.getProperty("Globals.filePath");
//		} else {
//			filePathString = EgovPropertiesUtil.getProperty(pathKey);
//		}
//		
//		// filePathString 이 값이 없을경우(path경로라고 생각한다)
//		if(filePathString.equals("99")){
//			filePathString = pathKey;
//		}
//		
//		// 확장자 명까지 적혀있어야 함
//		String fileNm = param.getString("file_nm");
//		fileNm = URLDecoder.decode(fileNm, "UTF-8");
//		
//
//		File uFile = new File(filePathString, fileNm);
//		int fSize = (int) uFile.length();
//
//		if (fSize > 0) {
//			String mimetype = "application/x-msdownload";
//			response.setContentType(mimetype);
//			setDisposition(fileNm, request, response);
//			response.setContentLength(fSize);
//
//			BufferedInputStream in = null;
//			BufferedOutputStream out = null;
//
//			try {
//				in = new BufferedInputStream(new FileInputStream(uFile));
//				out = new BufferedOutputStream(response.getOutputStream());
//
//				FileCopyUtils.copy(in, out);
//				out.flush();
//			} catch (Exception ex) {
//				// Connection reset by peer: socket write error
//				log.debug("IGNORED: " + ex.getMessage());
//			} finally {
//				if (in != null) {
//					try {
//						in.close();
//					} catch (Exception ignore) {
//						// no-op
//						log.debug("IGNORED: " + ignore.getMessage());
//					}
//				}
//				if (out != null) {
//					try {
//						out.close();
//					} catch (Exception ignore) {
//						// no-op
//						log.debug("IGNORED: " + ignore.getMessage());
//					}
//				}
//			}
//
//		} else {
//			response.setContentType("application/x-msdownload");
//
//			PrintWriter printwriter = response.getWriter();
//			printwriter.println("<html>");
//			printwriter.println("<br><br><br><h2>Could not get file name:<br>" + fileNm + "</h2>");
//			printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
//			printwriter.println("<br><br><br>&copy; webAccess");
//			printwriter.println("</html>");
//			printwriter.flush();
//			printwriter.close();
//		}
	}
	
	// 수정요망
	@RequestMapping(value = "/common/file/viewFile.do")
	public void viewFile(Map<String, Object> commandMap
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {
		
//		DataMap param = RequestUtil.getDataMap(request);
//		String atchFileId = param.getString("file_id");
//		
//		AtFileVO fileVO = new AtFileVO();
//		fileVO.setFile_id(atchFileId);
//		AtFileVO fvo = fileService.selectFileInf(fileVO);
//		
//		String mimeType 	= fvo.getContenttype();
//		String downFileName = fvo.getFile_path() + fvo.getFile_id()+"."+fvo.getFile_ext();
//
////		File file = new File(fvo.getFile_path(), fvo.getFile_id()+"."+fvo.getFile_ext());
//		File file = new File(fvo.getFile_path(), fvo.getFile_id() + Globals.FILE_EXT_C);
//
//		if (!file.exists()) {
//		    throw new FileNotFoundException(downFileName);
//		}
//
//		if (!file.isFile()) {
//		    throw new FileNotFoundException(downFileName);
//		}
//
//		byte[] b = new byte[BUFFER_SIZE];
//
//		if (mimeType == null) {
//		    mimeType = "application/octet-stream;";
//		}
//
//		response.setContentType(EgovWebUtil.removeCRLF(mimeType));
//		response.setHeader("Content-Disposition", "filename=image;");
//
//		BufferedInputStream fin = null;
//		BufferedOutputStream outs = null;
//
//		try {
//		    fin = new BufferedInputStream(new FileInputStream(file));
//		    outs = new BufferedOutputStream(response.getOutputStream());
//
//		    int read = 0;
//
//		    while ((read = fin.read(b)) != -1) {
//			outs.write(b, 0, read);
//		    }
//	// 2011.10.10 보안점검 후속조치
//		} finally {
//		    if (outs != null) {
//				try {
//				    outs.close();
//				} catch (Exception ignore) {
//				    System.out.println("IGNORE: " + ignore);
//				}
//			    }
//			    if (fin != null) {
//				try {
//				    fin.close();
//				} catch (Exception ignore) {
//				    System.out.println("IGNORE: " + ignore);
//				}
//			    }
//			}
	    }
}
