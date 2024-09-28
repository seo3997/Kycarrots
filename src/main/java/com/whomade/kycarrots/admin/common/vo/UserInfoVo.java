/**
 * 
 *
 * 1. FileName : InfoVo.java
 * 2. Package : egovframework.framework.security.vo
 * 3. Comment : 
 * 4. 작성자  : SooHyun.Seo
 * 5. 작성일  : 2017.12.22. 오전 10:06:26
 * 6. 변경이력 : 
 *    이름     : 일자          : 근거자료   : 변경내용
 *    ------------------------------------------------------
 *    SooHyun.Seo : 2017.12.22. :            : 신규 개발.
 */

package com.whomade.kycarrots.admin.common.vo;

import java.io.Serializable;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : InfoVo.java
 * 3. Package  : egovframework.framework.security.vo
 * 4. Comment  : 
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   : 2017.12.22. 오전 10:06:26
 * </PRE>
 */

public class UserInfoVo implements Serializable{
	
	private static final long serialVersionUID = 100L;

	private String userNo;					// 사용자 번호
	private String id;						// 사용자 id
	private String userNm;					// 성명
	private String cttpcSeCode;				// 연락처구분코드
	private String cttpc;					// 연락처
	private String email;					// 이메일
	private String areaCode;				// 지역코드
	private String authorId;				// 권한아이디
	private String userSttusCode;			// 유저상태코드
	private String password;				// 패스워드

	private String areaCodeL;				// 지역대분류 
	private String areaCodeS;				// 지역소분류 
	private String areaCodeD;				// 지역세분류 
	private String areaName;				// 지역명 
	
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getCttpcSeCode() {
		return cttpcSeCode;
	}
	public void setCttpcSeCode(String cttpcSeCode) {
		this.cttpcSeCode = cttpcSeCode;
	}
	public String getCttpc() {
		return cttpc;
	}
	public void setCttpc(String cttpc) {
		this.cttpc = cttpc;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getUserSttusCode() {
		return userSttusCode;
	}
	public void setUserSttusCode(String userSttusCode) {
		this.userSttusCode = userSttusCode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAreaCodeL() {
		return areaCodeL;
	}
	public void setAreaCodeL(String areaCodeL) {
		this.areaCodeL = areaCodeL;
	}
	public String getAreaCodeS() {
		return areaCodeS;
	}
	public void setAreaCodeS(String areaCodeS) {
		this.areaCodeS = areaCodeS;
	}
	public String getAreaCodeD() {
		return areaCodeD;
	}
	public void setAreaCodeD(String areaCodeD) {
		this.areaCodeD = areaCodeD;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	
	
	
}
