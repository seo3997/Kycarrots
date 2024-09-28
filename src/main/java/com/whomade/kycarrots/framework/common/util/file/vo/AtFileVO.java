package com.whomade.kycarrots.framework.common.util.file.vo;

import java.io.Serializable;


/**
 * 
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : AtFileVO.java
 * 3. Package  : egovframework.com.cmm.service
 * 4. Comment  : TB_FILE 테이블 VO Class
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   :2017.12.22. 오후 5:50:31
 * @Modification Information
 *
 *    수정일       	수정자         수정내용
 *    -------       -------      -------------------
 *   2017.12.22.  SooHyun.Seo		 최초생성
 *
 * </PRE>
 */
@SuppressWarnings("serial")
public class AtFileVO implements Serializable {

	/**
	 * 파일id
	 */
	public String 	file_id;
	
	/**
	 * 파일일련번호
	 */
	public int 	file_seq;
	
	/**
	 * 파일그룹id
	 */
	public String 	doc_id;
	/**
	 * 파일 실제 이름
	 */
	public String 	file_nm;
	/**
	 * 파일사이즈
	 */
	public long 	file_size;
	/**
	 * 파일상대저장경로
	 */
	public String 	file_rltv_path;
	/**
	 * 파일절대저장경로
	 */
	public String 	file_aslt_path;
	/**
	 * srot 
	 */
	public int 		srt_ord;
	/**
	 * 파일 비고
	 */
	public String 	file_rmk;
	/**
	 * 파일확장자
	 */
	public String 	file_ext_nm;
	/**
	 * 저장 유저 id
	 */
	public String	ss_user_id;
	
	/**
	 * 파일 ContentType
	 */
	public String	content_type;
	
	
	
	public String getContent_type() {
		return content_type;
	}
	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}
	
	public String getFile_ext_nm() {
		return file_ext_nm;
	}
	public void setFile_ext_nm(String file_ext_nm) {
		this.file_ext_nm = file_ext_nm;
	}
	public String getSs_user_id() {
		return ss_user_id;
	}
	public void setSs_user_id(String ss_user_id) {
		this.ss_user_id = ss_user_id;
	}

	public String getDoc_id() {
		return doc_id;
	}
	public void setDoc_id(String doc_id) {
		this.doc_id = doc_id;
	}
	public String getFile_nm() {
		return file_nm;
	}
	public void setFile_nm(String file_nm) {
		this.file_nm = file_nm;
	}
	public long getFile_size() {
		return file_size;
	}
	public void setFile_size(long file_size) {
		this.file_size = file_size;
	}
	public int getSrt_ord() {
		return srt_ord;
	}
	public void setSrt_ord(int srt_ord) {
		this.srt_ord = srt_ord;
	}
	public String getFile_rmk() {
		return file_rmk;
	}
	public void setFile_rmk(String file_rmk) {
		this.file_rmk = file_rmk;
	}
	public String getFile_rltv_path() {
		return file_rltv_path;
	}
	public void setFile_rltv_path(String file_rltv_path) {
		this.file_rltv_path = file_rltv_path;
	}
	public String getFile_aslt_path() {
		return file_aslt_path;
	}
	public void setFile_aslt_path(String file_aslt_path) {
		this.file_aslt_path = file_aslt_path;
	}
	public int getFile_seq() {
		return file_seq;
	}
	public void setFile_seq(int file_seq) {
		this.file_seq = file_seq;
	}

	public String getFile_id() {
		return file_id;
	}
	
	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}
	
}
