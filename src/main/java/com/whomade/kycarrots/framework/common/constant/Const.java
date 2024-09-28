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
 * 1. ClassName : 
 * 2. FileName  : Const.java
 * 3. Package  : egovframework.framework.common.constant
 * 4. Comment  : 
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   : 2017. 11. 28. 오전 11:18:30
 * </PRE>
 */

public class Const {

	//페이징처리시 기본설정
	public static final String defFirstPage = "1"; //디폴트 첫번째 페이지
	public static final String defRowPerPage = "10"; //디폴트 한화면에 보여줘야할 컨텐츠 수
	public static final String defRowPerPage05 = "5"; //디폴트 한화면에 보여줘야할 컨텐츠 수
	public static final String defRowPerPage20 = "20"; //디폴트 한화면에 보여줘야할 컨텐츠 수
	public static final String defCurrentPage = "1"; //디폴트 현재페이지
	public static final String defNaviCount = "10"; //디폴트 페이지 갯수
	public static final String defNaviCount05 = "5"; //디폴트 페이지 갯수
	
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
	
	// 적정 이미지 해상도
	public static final String imgMaxSize = EgovPropertiesUtil.getProperty("Globals.imgMaxSize");
	
	// 암복호화 키
	public static final String seedKey = EgovPropertiesUtil.getProperty("Globals.seedKey");
	
	//회원상태코드
	public static final String userSttusCodeTempJoin = "0";	//임시가입
	public static final String userSttusCodeActive = "10";		//활동
	public static final String userSttusCodeOut = "20";			//탈퇴
	public static final String userSttusCodeStop = "99";			//정지
	
	//TB_CODE_DTL 그룹코드 리스트
	public static final String upCodeCmsCode = "10001000";
	public static final String upCodeFrontCode = "10002000";
	public static final String upCodeMobileCode = "10003000";
	public static final String upCodeTopMenuId = "100010002000";
	public static final String upCodeMenuTypeCode = "R010010";	// 메뉴유형코드
	public static final String upCodeYn = "R010020";			// 여부코드
	public static final String upCodeSex = "R010030";			// 성코드
	public static final String upCodeCttpcSe = "R010040";		// 연락처구분코드
	public static final String upCodeUserSttus = "R010050";		// 사용자상태코드
	public static final String upCodeUserSe = "R010060";		// 사용자구분코드
	public static final String upCodeArea = "R010070";		    // 지역코드 
	
	public static final String upCodeNewsBbsSeCode = "R010130";			// 게시판 구분 코드
	public static final String upCodeVoteTyCode = "R010140";			// 투표타입 코드
	public static final String upCodePropseSttuCode = "R010150";		// 제안상태 코드
	public static final String upCodeAreaCode = "R010160";				// 지역 코드(사용안함)
	public static final String upCodeNoticeBbsSeCode = "R010170";		// 공지사항 게시판 구분 코드
	public static final String upCodePushSrvcCode = "R010180";			// 푸시 서비스 코드
	public static final String upCodeDeviceCode = "R010190";			// 디바이스 코드
	public static final String upCodeSndngSttusCode = "R010200";		// 발송 상태
	public static final String upCodeItemCode = "R010210";		//품목류 구분 코드
	public static final String upCodeAreaInfoCode = "R010220";		// 지역정보 구분 코드
	public static final String upCodeATBranchCode = "R010230";		//  at 지사 코드
	public static final String upCodeLdrOrgCode = "R010240";		//  선도조직 코드
	public static final String upCodeCountryCode = "R010250";		//  국가 코드
	public static final String upCodeCertCode = "R010260";		//  인증 코드
	public static final String upCodeDlivyCode = "R010290";		// 출고구분
	
	// 푸시 발송 상태코드
	public static final String sndngSttusWait = "10";	// 대기중
	public static final String sndngSttusSend = "20";	// 전송중
	public static final String sndngSttusEnd = "30";	// 전송완료
	public static final String sndngSttusError = "40";	// 전송오류
	
	// 푸시 서비스 코드
	public static final String pushSrvcCodeNoti = "10";		// 공지
	public static final String pushSrvcCodeEjang = "20";		// 전체 공지
	
	// 디바이스 OS 코드
	public static final String deviceOsCodeAnd = "AND";		// 안드로이드
	public static final String deviceOsCodeIos = "IOS";		// IOS
	
	// 권한
	public static final String authorIdAdmin = "ROLE_ADMIN";		// 관리자
	public static final String authorIdAdminSub = "ROLE_SUB";		// 사무담당관
	
	// 접근 구분
	public static final String accesSeAdmin = "10";						// 관리자
	public static final String accesSeFront = "20";						// 프론트
}
