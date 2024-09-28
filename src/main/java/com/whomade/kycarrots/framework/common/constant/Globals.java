package com.whomade.kycarrots.framework.common.constant;

import com.whomade.kycarrots.framework.common.util.EgovPropertiesUtil;

/**
 *  Class Name : Globals.java
 *  Description : 시스템 구동 시 프로퍼티를 통해 사용될 전역변수를 정의한다.
 *  Modification Information
 *
 *     수정일         수정자                   수정내용
 *   -------    --------    ---------------------------
 *   2009.01.19    박지욱          최초 생성
 *
 *  @author 공통 서비스 개발팀 박지욱
 *  @since 2009. 01. 19
 *  @version 1.0
 *  @see
 *
 */

public class Globals {
	public static final String OS_TYPE = EgovPropertiesUtil.getProperty("Globals.osType");
	public static final String DB_TYPE = EgovPropertiesUtil.getProperty("Globals.DbType");
	public static final String MAIN_PAGE = EgovPropertiesUtil.getProperty("Globals.MainPage");
	public static final String SHELL_FILE_PATH = EgovPropertiesUtil.getPathProperty("Globals.ShellFilePath");
	public static final String CONF_PATH = EgovPropertiesUtil.getPathProperty("Globals.ConfPath");
	public static final String SERVER_CONF_PATH = EgovPropertiesUtil.getPathProperty("Globals.ServerConfPath");
	public static final String CLIENT_CONF_PATH = EgovPropertiesUtil.getPathProperty("Globals.ClientConfPath");
	public static final String FILE_FORMAT_PATH = EgovPropertiesUtil.getPathProperty("Globals.FileFormatPath");
	public static final String ORIGIN_FILE_NM = "originalFileName";
	public static final String FILE_EXT = "fileExtension";
	public static final String FILE_SIZE = "fileSize";
	public static final String UPLOAD_FILE_NM = "uploadFileName";
	public static final String FILE_PATH = "filePath";
	public static final String FILE_EXT_C = ".file";
	public static final String MAIL_REQUEST_PATH = EgovPropertiesUtil.getPathProperty("Globals.MailRequestPath");
	public static final String MAIL_RESPONSE_PATH = EgovPropertiesUtil.getPathProperty("Globals.MailRResponsePath");
	public static final String LOCAL_IP = EgovPropertiesUtil.getProperty("Globals.LocalIp");
}