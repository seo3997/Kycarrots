/**
 * 
 *
 * 1. FileName : Const.java
 * 2. Package : egovframework.framework.common.constant
 * 3. Comment : 
 * 4. 작성자  : SooHyun.Seo
 * 5. 작성일  : 2017. 11. 28. 오전 11:18:30
 * 6. 변경이력 : 
 *    이름     : 일자          : 근거자료   : 변경내용
 *    ------------------------------------------------------
 *    SooHyun.Seo : 2017. 11. 28. :            : 신규 개발.
 */

package com.whomade.kycarrots.framework.common.constant;

import com.whomade.kycarrots.framework.common.util.EgovPropertiesUtil;

/**
 * <PRE>
 * 1. ClassName 	:
 * 2. FileName  	: Const.java
 * 3. Package  		: egovframework.framework.common.constant
 * 4. Comment  		:
 * 5. 작성자   		: SooHyun.Seo
 * 6. 작성일   		: 2017. 11. 28. 오전 11:18:30
 * </PRE>
 */

public class Const {

	//페이징처리시 기본설정
	public static final String defFirstPage = "1"; 			//디폴트 첫번째 페이지
	public static final String defRowPerPage = "10"; 		//디폴트 한화면에 보여줘야할 컨텐츠 수
	public static final String defRowPerPage05 = "5"; 		//디폴트 한화면에 보여줘야할 컨텐츠 수
	public static final String defRowPerPage20 = "20"; 		//디폴트 한화면에 보여줘야할 컨텐츠 수
	public static final String defCurrentPage = "1"; 		//디폴트 현재페이지
	public static final String defNaviCount = "10"; 		//디폴트 페이지 갯수
	public static final String defNaviCount05 = "5"; 		//디폴트 페이지 갯수

	public static final String jsRoot = EgovPropertiesUtil.getProperty("path.js");
	public static final String imgRoot = EgovPropertiesUtil.getProperty("path.img");
	public static final String cssRoot = EgovPropertiesUtil.getProperty("path.css");
	
	//URL 정보
	public static final String doHome = EgovPropertiesUtil.getProperty("do.home");
	public static final String doLogin = EgovPropertiesUtil.getProperty("do.login");
	public static final String doFront = EgovPropertiesUtil.getProperty("do.front");
	
	
	//JSP 정보
	public static final String jspHome = EgovPropertiesUtil.getProperty("jsp.home");
	public static final String jspLogin = EgovPropertiesUtil.getProperty("jsp.login");
	public static final String jspClose = EgovPropertiesUtil.getProperty("jsp.close");

	// 파일 용량 정보
	public static final String fileMaxSize = EgovPropertiesUtil.getProperty("Globals.fileMaxSize");
	public static final String imgFileMaxSize = EgovPropertiesUtil.getProperty("Globals.ImgfileMaxSize");
	

	// 암복호화 키
	public static final String seedKey = EgovPropertiesUtil.getProperty("Globals.seedKey");
	
	//회원상태코드
	public static final String userSttusCodeTempJoin = "0";				//임시가입
	public static final String userSttusCodeActive = "10";				//활동
	public static final String userSttusCodeOut = "20";					//탈퇴
	public static final String userSttusCodeStop = "99";				//정지

	//TB_CODE_DTL 그룹코드 리스트
	public static final String upCodeCmsCode = "10001000";
	public static final String upCodeTopMenuId = "100010002000";
	public static final String upCodeMenuTypeCode = "R010010";			// 메뉴유형코드
	public static final String upCodeYn = "R010020";					// 여부코드
	public static final String upCodeSex = "R010030";					// 성코드
	public static final String upCodeCttpcSe = "R010040";				// 연락처구분코드
	public static final String upCodeUserSttus = "R010050";				// 사용자상태코드
	public static final String upCodeUserSe = "R010060";				// 사용자구분코드
	public static final String upCodeArea = "R010070";		    		// 지역코드
	
	public static final String upCodeNoticeBbsSeCode = "R010170";		// 공지사항 게시판 구분 코드
	public static final String upCodeItemCode = "R010610";				// 품목류 구분 코드
	public static final String upCodeAreaInfoCode = "R010070";			// 지역정보 구분 코드


	// 디바이스 OS 코드
	public static final String deviceOsCodeAnd = "AND";					// 안드로이드
	public static final String deviceOsCodeIos = "IOS";					// IOS
	
	// 권한
	public static final String ROLE_ADMIN 	= "ROLE_ADMIN";				// 관리자
	public static final String ROLE_SELL 	= "ROLE_SELL";				// 판매자
	public static final String ROLE_PUB 	= "ROLE_PUB";				// 구매자
	public static final String ROLE_PROJ 	= "ROLE_PROJ";				// 센터관리

	//직거래:1 센터:2
	public static final String SYSTEM_TYPE 	= "1";						// 센터관리

	// 접근 구분
	public static final String accesSeAdmin = "10";						// 관리자
	public static final String accesSeFront = "20";						// 프론트

	public static final String RESULT_NO_USER = "601";
	public static final String RESULT_PWD_ERR = "602";
	public static final String RESULT_MEMBER_CODE_ERR = "603";
	public static final String RESULT_CODE_200 = "200";
	public static final String  RESULT_NO_DATA = "604";
	public static final String  RESULT_CODE_ERR = "0";

}
