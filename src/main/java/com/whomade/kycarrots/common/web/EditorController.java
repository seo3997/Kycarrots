package com.whomade.kycarrots.common.web;

import java.io.IOException;
import java.util.List;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.common.service.CommonCodeService;
import com.whomade.kycarrots.common.service.EditorService;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import com.whomade.kycarrots.framework.common.util.EgovPropertiesUtil;
import com.whomade.kycarrots.framework.common.util.RequestUtil;
import com.whomade.kycarrots.framework.common.util.SessionUtil;
import com.whomade.kycarrots.framework.common.util.file.AtFileMngUtil;
import com.whomade.kycarrots.framework.common.util.file.vo.AtFileVO;

@Controller
public class EditorController {

	private static Log log = LogFactory.getLog(EditorController.class);

	@Resource(name = "egovMessageSource")
	private EgovMessageSource egovMessageSource;

	@Resource(name = "editorService")
	private EditorService editorService;
	
	/** CommonCodeService */
	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;
	
	@Resource(name="AtFileMngUtil")
	private AtFileMngUtil atFileMngUtil;
	
	@RequestMapping(value = "/editor/editorFileUpload.do")
	public @ResponseBody void ckeditorFileUpload(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		// 파일 객체 가져옴
		List fileList = atFileMngUtil.getFiles((MultipartHttpServletRequest)request);
		
		String returnStr = "";
		String msg = "";
		
		// 파일 크기 계산(최대크기 넘었을경우 다시 쓰기 페이지로 리턴)
		if(!atFileMngUtil.checkEachFileSize(fileList, "Globals.ImgfileMaxSize")){
			msg = egovMessageSource.getMessage("error.img.size.over", new String[]{atFileMngUtil.getFileSize(EgovPropertiesUtil.getProperty("Globals.ImgfileMaxSize"))});
			// 메세지 셋팅하여 호출
			returnStr += "<script type=\"text/javascript\">window.parent.fnEditorFileUploadCallback.apply(window.parent, ['" + param.getString("CKEditorFuncNum") + "', '', false, '" + msg + "']);</script>";
		} else {
			try {
				
				UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
				param.put("ss_user_no", userInfoVo.getUserNo());
				
				// 파일 저장
				editorService.insertEditorFile(param, fileList);
				// 파일 정보
				AtFileVO fileInfo = (AtFileVO)param.get("fileInfo");
				
				String imgPath = fileInfo.getFile_rltv_path() + fileInfo.getFile_id() + "." + fileInfo.getFile_ext_nm();
				// 해당 창이 iframe으로 되어있기때문에 상위 윈도우창을 타겟으로 스크립트문을 작성하여 준다.
				returnStr += "<script type=\"text/javascript\">window.parent.fnEditorFileUploadCallback.apply(window.parent, ['" + param.getString("CKEditorFuncNum") + "', '" + imgPath + "', true, '']);</script>";
			} catch (Exception e1) {
				// 에러시
				returnStr += "<script type=\"text/javascript\">window.parent.fnEditorFileUploadCallback.apply(window.parent, ['" + param.getString("CKEditorFuncNum") + "', '', false, '']);</script>";
			}
		}
		
		response.setContentType("text/html; charset=utf-8");
		try {
			response.getWriter().write(returnStr);
		} catch (IOException e) {
			log.error(e);
		}
	}
	
}
