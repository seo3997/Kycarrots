package com.whomade.kycarrots.mgt.mboard.web;

import java.util.List;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.common.service.CommonCodeService;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.*;
import com.whomade.kycarrots.framework.common.util.file.AtFileMngUtil;
import com.whomade.kycarrots.framework.common.util.file.service.AtFileMngService;
import com.whomade.kycarrots.framework.common.util.file.vo.AtFileVO;
import com.whomade.kycarrots.mgt.mboard.service.MicroBizBoardService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;


@Controller
public class MicrobizBoardFileController {

	private static Log log = LogFactory.getLog(MicrobizBoardFileController.class);

	@Resource(name = "egovMessageSource")
	private EgovMessageSource egovMessageSource;

	@Resource(name = "microBizBoardService")
	private MicroBizBoardService boardService;

	/** CommonCodeService */
	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;
	
	@Resource(name="AtFileMngService")
	private AtFileMngService atFileMngService;
	
	@Resource(name="AtFileMngUtil")
	private AtFileMngUtil atFileMngUtil;

	/**
	 * <PRE>
	 * 1. MethodName 	: selectPageListBoard
	 * 2. ClassName  	: BoardController
	 * 3. Comment   	: 게시판 리스트
	 * 4. 작성자    	: SooHyun.Seo
	 * 5. 작성일    	: 2017. 12. 21 15:59
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/mfileboard/selectPageListBoard.do")
	public String selectPageListBoard(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		param.put("sch_bbs_se_code", "30");		//창고
		param.put("bbs_se_code_m", "30");			//창고
		//리스트 조회
		List<DataMap> resultList = boardService.selectPageListBoard(model, param);

		model.addAttribute("resultList", resultList);
		model.addAttribute("param", param);
		
		return "/mgt/mfileboard/selectPageListBoard";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 		: selectBoard
	 * 2. ClassName  		: BoardController
	 * 3. Comment   		: 게시판 상세
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2017. 3. 13. 오후 4:09:06
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/mfileboard/selectBoard.do")
	public String selectBoard(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		DataMap resultMap = boardService.selectBoard(param);
		
		// #### FILE LIST 검색 Start ####
		AtFileVO fvo = new AtFileVO();
		fvo.setDoc_id(resultMap.getString("ATCH_DOC_ID"));
		
		List<AtFileVO> fileList = atFileMngService.selectFileInfs(fvo);
		// #### FILE LIST 검색 End ####
		
		model.addAttribute("fileList", fileList);
		model.addAttribute("resultMap", resultMap);
		model.addAttribute("param", param);
		
		return "/mgt/mfileboard/selectBoard";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 		: insertFormBoard
	 * 2. ClassName  		: BoardController
	 * 3. Comment  		 	: 게시판 등록폼
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2017. 3. 13. 오후 4:09:12
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/mfileboard/insertFormBoard.do")
	public String insertFormBoard(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		
		model.addAttribute("param", param);
		
		return "/mgt/mfileboard/insertFormBoard";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: insertBoard
	 * 2. ClassName  	: BoardController
	 * 3. Comment   	: 게시판 등록
	 * 4. 작성자    	: SooHyun.Seo
	 * 5. 작성일    	: 2017. 3. 13. 오후 4:09:20
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/mfileboard/insertBoard.do")
	public String insertBoard(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());

		// 파일 객체 가져옴
		List fileList = atFileMngUtil.getFiles((MultipartHttpServletRequest)request);
		
		// 파일 확장자 계산
		String msg = atFileMngUtil.checkFileExt(fileList);
		if(!msg.equals("")){
			MessageUtil.setMessage(request, msg);
			param.put("redirectUrl", "/mgt/mfileboard/insertFormBoard.do");
			model.addAttribute("param", param);
			return "common/redirect";
		}
		
		// 파일 크기 계산(최대크기 넘었을경우 다시 쓰기 페이지로 리턴)
		if(!atFileMngUtil.checkEachFileSize(fileList)){
			MessageUtil.setMessage(request, egovMessageSource.getMessage("error.file.size.over", new String[]{atFileMngUtil.getFileSize(EgovPropertiesUtil.getProperty("Globals.fileMaxSize"))}));
			
			param.put("redirectUrl", "/mgt/mfileboard/insertFormBoard.do");
			model.addAttribute("param", param);
			return "common/redirect";
		}
		
		boardService.insertBoard(param, fileList);
		
		MessageUtil.setMessage(request, egovMessageSource.getMessage("succ.data.insert"));
		
		param.put("redirectUrl", "/mgt/mfileboard/selectPageListBoard.do");
		model.addAttribute("param", param);
		
		return "common/redirect";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: updateFormBoard
	 * 2. ClassName  	: BoardController
	 * 3. Comment   	: 게시판 수정폼
	 * 4. 작성자    	: SooHyun.Seo
	 * 5. 작성일    	: 2017. 3. 13. 오후 4:09:28
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/mfileboard/updateFormBoard.do")
	public String updateFormBoard(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		
		DataMap resultMap = boardService.selectBoard(param);
		
		// #### FILE LIST 검색 Start ####
		AtFileVO fvo = new AtFileVO();
		fvo.setDoc_id(resultMap.getString("ATCH_DOC_ID"));
		
		List<AtFileVO> fileList = atFileMngService.selectFileInfs(fvo);
		// #### FILE LIST 검색 End ####
		
		model.addAttribute("fileList", fileList);
		model.addAttribute("resultMap", resultMap);
		model.addAttribute("param", param);
		
		return "/mgt/mfileboard/updateFormBoard";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateBoard
	 * 2. ClassName  	: BoardController
	 * 3. Comment   	: 게시판 수정
	 * 4. 작성자    	: SooHyun.Seo
	 * 5. 작성일    	: 2017. 3. 13. 오후 4:09:36
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/mfileboard/updateBoard.do")
	public String updateBoard(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataMap param = RequestUtil.getDataMap(request);
		
		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		// 파일 객체 가져옴
		List fileList = atFileMngUtil.getFiles((MultipartHttpServletRequest)request);
		
		// 파일 확장자 계산
		String msg = atFileMngUtil.checkFileExt(fileList);
		if(!msg.equals("")){
			MessageUtil.setMessage(request, msg);
			param.put("redirectUrl", "/mgt/mfileboard/updateFormBoard.do");
			model.addAttribute("param", param);
			return "common/redirect";
		}
		
		// 파일 크기 계산(최대크기 넘었을경우 다시 쓰기 페이지로 리턴)
		if(!atFileMngUtil.checkEachFileSize(fileList)){
			MessageUtil.setMessage(request, egovMessageSource.getMessage("error.file.size.over", new String[]{atFileMngUtil.getFileSize(EgovPropertiesUtil.getProperty("Globals.fileMaxSize"))}));
			
			param.put("redirectUrl", "/mgt/mfileboard/updateFormBoard.do");
			model.addAttribute("param", param);
			
			return "common/redirect";
		}
		boardService.updateBoard(param, fileList);
		
		
		//Push전송
		/*
		String msgTitle;
		String msgCont;
		String msgUrl;
		msgTitle=param.getString("sj");
		msgCont	=StringUtil.getReSizeRemoveDot(param.getString("cn"),100);
		msgUrl="/mgt/mboard/selectBoard.do?bbs_seq="+param.getString("bbs_seq")+"&sch_bbs_se_code=30";

		FcmPush aFcmPush=new FcmPush(msgTitle,msgCont,msgUrl);
		aFcmPush.sendPush();
		*/

		
		model.addAttribute("param", param);
		MessageUtil.setMessage(request, egovMessageSource.getMessage("succ.data.update"));
		param.put("redirectUrl", "/mgt/mfileboard/selectBoard.do");
		
		return "common/redirect";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: deleteBoard
	 * 2. ClassName  	: BoardController
	 * 3. Comment   	: 게시판 삭제
	 * 4. 작성자    	: SooHyun.Seo
	 * 5. 작성일    	: 2017. 3. 13. 오후 4:09:49
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/mgt/mfileboard/deleteBoard.do")
	public String deleteBoard(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);

		UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		param.put("ss_user_no", userInfoVo.getUserNo());
		
		DataMap resultMap = boardService.selectBoard(param);
		
		// doc_id 및 내용 doc_id 셋팅
		param.put("atch_doc_id", resultMap.getString("ATCH_DOC_ID"));
		param.put("cn_doc_id", resultMap.getString("CN_DOC_ID"));
		
		// 참고자료 삭제
		boardService.deleteBoard(param);
		MessageUtil.setMessage(request, egovMessageSource.getMessage("succ.data.delete"));
		model.addAttribute("param", param);
		return "redirect:/mgt/mfileboard/selectPageListBoard.do";
	}
}
