package com.whomade.kycarrots.front.board.main.web;

import com.whomade.kycarrots.admin.common.vo.UserInfoVo;
import com.whomade.kycarrots.common.service.CommonCodeService;
import com.whomade.kycarrots.framework.common.constant.Const;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;


@Controller
public class FrontBoardController {

	private static Log log = LogFactory.getLog(FrontBoardController.class);

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

	@Value("${file.max-size-each}")   // 예: "10MB"
	private String maxSizeEachConf;

	/**
	 * <PRE>
	 * 1. MethodName 	: selectPageListBoard
	 * 2. ClassName  	: BoardController
	 * 3. Comment   	: 게시판 리스트
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2017. 12. 21 15:59
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/front/board/selectPageListBoard.do")
	public String selectPageListBoard(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataMap param = RequestUtil.getDataMap(request);
		DataMap codeParam = new DataMap();

		//UserInfoVo userInfoVo = SessionUtil.getSessionUserInfoVo(request);
		//param.put("ss_user_no", userInfoVo.getUserNo());

		String schBbsSeCodeM = param.getString("sch_bbs_se_code_m","10");
		String ss_user_no = param.getString("ss_user_no","1");
		param.put("ss_user_no", ss_user_no);
		param.put("sch_bbs_se_code_m", schBbsSeCodeM);
		param.put("sch_bbs_se_code", schBbsSeCodeM);
		param.put("front_yn", "1");


		// 게시판 구분 코드 조회 R010170
		codeParam.put("group_id", Const.upCodeNoticeBbsSeCode);
		List boardComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("boardComboStr", boardComboStr);
		
		//리스트 조회
		List<DataMap> resultList = boardService.selectPageListBoard(model, param);

		model.addAttribute("resultList", resultList);
		model.addAttribute("param", param);
		
		return "front/board/selectPageListBoard";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 		: selectBoard
	 * 2. ClassName  		: BoardController
	 * 3. Comment   		: 게시판 상세
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 3. 13. 오후 4:09:06
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/front/board/selectBoard.do")
	public String selectBoard(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		DataMap param = RequestUtil.getDataMap(request);
		DataMap resultMap = boardService.selectBoard(param);
		
		// #### FILE LIST 검색 Start ####
		AtFileVO fvo = new AtFileVO();
		fvo.setDoc_id(resultMap.getString("ATCH_DOC_ID"));
		
		List<AtFileVO> fileList = atFileMngService.selectFileInfs(fvo);
		// #### FILE LIST 검색 End ####
		
		model.addAttribute("fileList", fileList);
		model.addAttribute("resultMap", resultMap);
		model.addAttribute("param", param);
		
		return "front/board/selectBoard";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 		: insertFormBoard
	 * 2. ClassName  		: BoardController
	 * 3. Comment  		 	: 게시판 등록폼
	 * 4. 작성자    			: SooHyun.Seo
	 * 5. 작성일    			: 2017. 3. 13. 오후 4:09:12
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/front/board/insertFormBoard.do")
	public String insertFormBoard(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		DataMap param = RequestUtil.getDataMap(request);
		DataMap codeParam = new DataMap();
		
		// 게시판 구분 코드 조회 R010170
		codeParam.put("group_id", Const.upCodeNoticeBbsSeCode);
		List boardComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("boardComboStr", boardComboStr);

		model.addAttribute("param", param);
		
		return "front/board/insertFormBoard";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: insertBoard
	 * 2. ClassName  	: BoardController
	 * 3. Comment   	: 게시판 등록
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2017. 3. 13. 오후 4:09:20
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/front/board/insertBoard.do")
	public String insertBoard(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataMap param = RequestUtil.getDataMap(request);
		

		String ssUserNo = param.getString("ss_user_no","1");
		param.put("ss_user_no", ssUserNo);

		// 파일 객체 가져옴
		List fileList = atFileMngUtil.getFiles((MultipartHttpServletRequest)request);
		
		// 파일 확장자 계산
		String msg = atFileMngUtil.checkFileExt(fileList);
		if(!msg.equals("")){
			MessageUtil.setMessage(request, msg);
			param.put("redirectUrl", "/front/board/insertFormBoard.do");
			model.addAttribute("param", param);
			return "common/redirect";
		}
		
		// 파일 크기 계산(최대크기 넘었을경우 다시 쓰기 페이지로 리턴)
		if(!atFileMngUtil.checkEachFileSize(fileList)){

			String pretty = atFileMngUtil.humanReadable(maxSizeEachConf); // "10.0MB" 등
			MessageUtil.setMessage(request,
					egovMessageSource.getMessage("error.file.size.over", new String[]{ pretty })
			);

			param.put("redirectUrl", "/front/board/insertFormBoard.do");
			model.addAttribute("param", param);
			return "common/redirect";
		}
		
		boardService.insertBoard(param, fileList);
		
		MessageUtil.setMessage(request, egovMessageSource.getMessage("succ.data.insert"));
		
		param.put("redirectUrl", "/front/board/selectPageListBoard.do");
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
	@RequestMapping(value = "/front/board/updateFormBoard.do")
	public String updateFormBoard(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		DataMap param = RequestUtil.getDataMap(request);
		DataMap codeParam = new DataMap();
		
		// 게시판 구분 코드 조회 R010170
		codeParam.put("group_id", Const.upCodeNoticeBbsSeCode);
		List boardComboStr = commonCodeService.selectCodeList(codeParam);
		model.addAttribute("boardComboStr", boardComboStr);
		
		
		DataMap resultMap = boardService.selectBoard(param);
		
		// #### FILE LIST 검색 Start ####
		AtFileVO fvo = new AtFileVO();
		fvo.setDoc_id(resultMap.getString("ATCH_DOC_ID"));
		
		List<AtFileVO> fileList = atFileMngService.selectFileInfs(fvo);
		// #### FILE LIST 검색 End ####
		
		model.addAttribute("fileList", fileList);
		model.addAttribute("resultMap", resultMap);
		model.addAttribute("param", param);
		
		return "front/board/updateFormBoard";
	}

	/**
	 * <PRE>
	 * 1. MethodName 	: updateBoard
	 * 2. ClassName  	: BoardController
	 * 3. Comment   	: 게시판 수정
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2017. 3. 13. 오후 4:09:36
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/front/board/updateBoard.do")
	public String updateBoard(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataMap param = RequestUtil.getDataMap(request);

		String ssUserNo = param.getString("ss_user_no","1");
		param.put("ss_user_no", ssUserNo);
		
		// 파일 객체 가져옴
		List fileList = atFileMngUtil.getFiles((MultipartHttpServletRequest)request);
		
		// 파일 확장자 계산
		String msg = atFileMngUtil.checkFileExt(fileList);
		if(!msg.equals("")){
			MessageUtil.setMessage(request, msg);
			param.put("redirectUrl", "/front/board/updateFormBoard.do");
			model.addAttribute("param", param);
			return "common/redirect";
		}
		
		// 파일 크기 계산(최대크기 넘었을경우 다시 쓰기 페이지로 리턴)
		if(!atFileMngUtil.checkEachFileSize(fileList)){
			String pretty = atFileMngUtil.humanReadable(maxSizeEachConf); // "10.0MB" 등
			MessageUtil.setMessage(request,
					egovMessageSource.getMessage("error.file.size.over", new String[]{ pretty })
			);

			param.put("redirectUrl", "/front/board/updateFormBoard.do");
			model.addAttribute("param", param);
			
			return "common/redirect";
		}
		
		boardService.updateBoard(param, fileList);

		model.addAttribute("param", param);
		MessageUtil.setMessage(request, egovMessageSource.getMessage("succ.data.update"));
		param.put("redirectUrl", "/front/board/selectBoard.do");
		
		return "common/redirect";
	}
	
	/**
	 * <PRE>
	 * 1. MethodName 	: deleteBoard
	 * 2. ClassName  	: BoardController
	 * 3. Comment   	: 게시판 삭제
	 * 4. 작성자    		: SooHyun.Seo
	 * 5. 작성일    		: 2017. 3. 13. 오후 4:09:49
	 * </PRE>
	 *   @return String
	 *   @param request
	 *   @param response
	 *   @param model
	 *   @return
	 *   @throws Exception
	 */
	@RequestMapping(value = "/front/board/deleteBoard.do")
	public String deleteBoard(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);

		DataMap resultMap = boardService.selectBoard(param);
		
		// doc_id 및 내용 doc_id 셋팅
		param.put("atch_doc_id", resultMap.getString("ATCH_DOC_ID"));
		param.put("cn_doc_id", resultMap.getString("CN_DOC_ID"));
		
		// 참고자료 삭제
		boardService.deleteBoard(param);
		MessageUtil.setMessage(request, egovMessageSource.getMessage("succ.data.delete"));
		model.addAttribute("param", param);
		String schBbsSeCodeM = param.getString("sch_bbs_se_code_m","10");
		String ssUserNo = param.getString("ss_user_no","1");

		return "redirect:/front/board/selectPageListBoard.do?sch_bbs_se_code_m="+schBbsSeCodeM+"&ss_user_no="+ssUserNo;
	}
}
