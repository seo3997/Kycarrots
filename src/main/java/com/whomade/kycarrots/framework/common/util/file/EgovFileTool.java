/**
 *  Class Name : EgovFileTool.java
 *  Description : 시스템 디렉토리 정보를 확인하여 제공하는  Business class
 *  Modification Information
 *
 *     수정일         수정자                   수정내용
 *   -------    --------    ---------------------------
 *   2009.01.13    조재영          최초 생성
 *
 *  @author 공통 서비스 개발팀 조재영,박지욱
 *  @since 2009. 01. 13
 *  @version 1.0
 *  @see
 *
 *  Copyright (C) 2009 by MOPAS  All right reserved.
 */
package com.whomade.kycarrots.framework.common.util.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Vector;

import com.whomade.kycarrots.framework.common.util.EgovWebUtil;
import com.whomade.kycarrots.framework.common.constant.Globals;
import com.whomade.kycarrots.framework.common.util.EgovPropertiesUtil;

public class EgovFileTool {

	// 파일사이즈 1K
	static final long BUFFER_SIZE = 1024L;
	// 파일구분자
	static final char FILE_SEPARATOR = File.separatorChar;
	// 윈도우시스템 파일 접근권한
	static final char ACCESS_READ = 'R'; // 읽기전용
	static final char ACCESS_SYS = 'S'; // 시스템
	static final char ACCESS_HIDE = 'H'; // 숨김
	// 최대 문자길이
	static final int MAX_STR_LEN = 1024;

	// Log
	//protected static final Log log = LogFactory.getLog(EgovFileTool.class);

	/**
	 * <pre>
	 * Comment : 디렉토리 존재여부를 확인한다. (단일디렉토리 확인용)
	 * </pre>
	 *
	 * @return String result 존재하는 디렉토리 경로를 리턴한다.
	 */
	public static boolean getExistDirectory(String targetDirPath) throws Exception {

		// 인자값 유효하지 않은 경우 공백 리턴
		if (targetDirPath == null || targetDirPath.equals("")) {
			return false;
		}

		boolean result = false;
		File f = null;
		try {
			f = new File(EgovWebUtil.filePathBlackList(targetDirPath));
			if (f.exists() && f.isDirectory()) {
				result = true;
			}

		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		}

		return result;
	}

	/**
	 * <pre>
	 * Comment : 디렉토리 존재여부를 확인한다. (하위디렉토리 확인용)
	 * </pre>
	 *
	 * @return String result 존재하는 디렉토리 경로를 리턴한다.
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList getExistDirectory(String baseDirPath, String targetDirPath, int cnt) throws Exception {

		// 인자값 유효하지 않은 경우 빈 ArrayList 리턴
		if (baseDirPath == null || baseDirPath.equals("") || targetDirPath == null || targetDirPath.equals("") || cnt == 0) {
			return new ArrayList();
		}
		int dirCnt = 0;
		if (cnt < 0)
			dirCnt = 21474846;
		else
			dirCnt = cnt;

		// 찾은 결과를 전달할 ArrayList
		ArrayList result = new ArrayList();
		// 하위의 결과를 임시 보관할 ArrayList
		ArrayList subResult = new ArrayList();
		// 현재경로(baseDirPath)에서 발견된 targetDirPath 갯수
		int dirFindCnt = 0;
		boolean isExist = false;
		String[] dirList = null;
		String subDirPath = "";
		File f = null;

		try {
			f = new File(EgovWebUtil.filePathBlackList(baseDirPath));
			isExist = f.exists();

			if (isExist) {
				dirList = f.list();
			}
			for (int i = 0; dirList != null && i < dirList.length; i++) {
				//log.debug("dirList["+i+"]:"+dirList[i] +"--->"+baseDirPath+"/"+dirList[i]);
				subDirPath = baseDirPath + "/" + dirList[i];
				//log.debug("_"+targetDirPath+"_");
				//log.debug("_"+dirList[i]+"_");

				f = new File(EgovWebUtil.filePathBlackList(subDirPath));

				//현재경로(baseDirPath)에서 검색
				if (targetDirPath.equals(dirList[i])) {
					// 중간에 발견하면 반복체크는 종료한다.(결과요청 갯수에 도달한 경우에 한해) - 이곳에서 종료되면 이후 하위에서 체크할 필요가 없다.
					if (new File(EgovWebUtil.filePathBlackList(baseDirPath) + "/" + dirList[i]).isDirectory()) {
						dirFindCnt++;
						result.add(baseDirPath + "/" + dirList[i]);
						if (dirFindCnt == dirCnt)
							break;
					}
				}

				//현재경로(baseDirPath)에서 발견된 하위 경로에서 반복하여 재귀적으로 검색
				int subCnt = dirCnt - dirFindCnt;
				if (f.isDirectory()) {
					//log.debug("f.isDirectory():"+f.isDirectory());
					subResult = getExistDirectory(subDirPath, targetDirPath, subCnt);
					// 하위에서 발견된 디렉토리 갯수를 현재까지 찾은 디렉토리갯수에 추가한다.
					dirFindCnt = dirFindCnt + subResult.size();
					// 하위에서 모두 발견된 경우 반복 체크는 종료한다.
					if (dirCnt <= dirFindCnt) {
						for (int j = 0; j < subResult.size(); j++) {
							result.add((String)subResult.get(j));
						}

						break;
					} else {
						for (int j = 0; j < subResult.size(); j++) {
							result.add((String)subResult.get(j));
						}
					}
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		}

		return result;
	}

	/**
	 * <pre>
	 * Comment : 디렉토리 존재여부를 확인한다. (생성일자를 조건으로 조건구간내 포함되는지 확인)
	 * </pre>
	 *
	 * @return String result 존재하는 디렉토리 경로를 리턴한다.
	 */
	public static boolean getExistDirectory(String targetDirPath, String fromDate, String toDate) throws Exception {

		// 인자값 유효하지 않은 경우 공백 리턴
		if (targetDirPath == null || targetDirPath.equals("") || fromDate == null || fromDate.equals("") || toDate == null || toDate.equals("")) {
			return false;
		}

		boolean result = false;
		String lastModifyedDate = "";
		File f = null;

		try {
			f = new File(EgovWebUtil.filePathBlackList(targetDirPath));
			lastModifyedDate = getLastModifiedDateFromFile(f);
			//log.debug("getLastModifiedDateFromFile(f):"+lastModifyedDate);
			if (Integer.parseInt(lastModifyedDate) >= Integer.parseInt(fromDate) && Integer.parseInt(lastModifyedDate) <= Integer.parseInt(toDate)) {
				result = true;
			}

		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		}

		return result;
	}

	/**
	 * <pre>
	 * Comment : 디렉토리 존재여부를 확인한다. (생성자를 조건으로 일치하는지 확인)
	 * </pre>
	 *
	 * @return String result 존재하는 디렉토리 경로를 리턴한다.
	 */
	public static boolean getExistDirectory(String targetDirPath, String ownerName) throws Exception {

		// 인자값 유효하지 않은 경우 공백 리턴
		if (targetDirPath == null || targetDirPath.equals("") || ownerName == null || ownerName.equals("")) {
			return false;
		}

		boolean result = false;
		//String tmp = "";

		// 실행할 명령을 프로퍼티 파일에서 확인한다.
		//Process p = null;

		try {
			String realOwner = getOwner(targetDirPath);
			if (ownerName.equals(realOwner)) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		}

		return result;
	}

	/**
	 * <pre>
	 * Comment : 디렉토리(파일)의 최종 수정일자를 확인한다. (기본로케일 java.util.Locale.KOREA 기준)
	 * </pre>
	 *
	 * @return String result 최종수정일자를 문자열로 리턴한다.
	 */
	public static String getLastModifiedDateFromFile(File f) {

		String result = "";
		try {
			if (f.exists()) {
				long date = f.lastModified();
				java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyyMMdd", java.util.Locale.KOREA);
				result = dateFormat.format(new java.util.Date(date));
			} else {
				result = "";
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		}

		return result;
	}

	/**
	 * <pre>
	 * Comment : 디렉토리(파일)의 최종 수정일자를 확인한다. (기본로케일 java.util.Locale.KOREA 기준)
	 * </pre>
	 *
	 * @return String result 최종수정일자를 문자열로 리턴한다.
	 */
	public static String getLastModifiedDateFromFile(String filePath) {

		File f = null;
		String result = "";
		try {
			f = new File(EgovWebUtil.filePathBlackList(filePath));
			result = getLastModifiedDateFromFile(f);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		}

		return result;
	}

	/**
	 * <pre>
	 * Comment : 조건구간내에 생성된 디렉토리 목록을 조회한다.
	 * </pre>
	 *
	 * @return ArrayList result 조건구간내에 생성된 디렉토리 목록을 리턴한다.
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList getLastDirectoryForModifiedDate(String baseDirPath, String fromDate, String toDate) {

		// 인자값 유효하지 않은 경우 빈 ArrayList 리턴
		if (baseDirPath == null || baseDirPath.equals("") || fromDate == null || fromDate.equals("") || toDate == null || toDate.equals("")) {
			return new ArrayList();
		}

		File f = null;
		File childFile = null;
		String[] subDirList;
		String subDirPath = "";
		ArrayList childResult = null;
		ArrayList result = new ArrayList();

		try {
			f = new File(EgovWebUtil.filePathBlackList(baseDirPath));
			subDirList = f.list();
			for (int i = 0; i < subDirList.length; i++) {

				subDirPath = baseDirPath + "/" + subDirList[i];
				childFile = new File(EgovWebUtil.filePathBlackList(subDirPath));
				if (childFile.isDirectory()) {
					//childResult = getLastDirectoryForModifiedDate(subDirPath , fromDate, toDate);
					String lastModifyedDate = getLastModifiedDateFromFile(childFile);
					if (Integer.parseInt(lastModifyedDate) >= Integer.parseInt(fromDate)
							&& Integer.parseInt(lastModifyedDate) <= Integer.parseInt(toDate)) {
						result.add(baseDirPath + "/" + subDirList[i]);
					}
					childResult = getLastDirectoryForModifiedDate(baseDirPath + "/" + subDirList[i], fromDate, toDate);
					// 하위디렉토리의 결과를 추가한다.
					for (int j = 0; j < childResult.size(); j++) {
						result.add((String)childResult.get(j));
					}
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		}

		return result;
	}

	/**
	 * <pre>
	 * Comment : 디렉토리(파일)의 읽기권한을 확인한다.
	 * </pre>
	 *
	 * @return boolean result 읽기가능하면 true를 리턴한다. 권한이 없어가 파일이 없는 경우는 false를 리턴한다.
	 */
	public static boolean canRead(String filePath) {

		// 인자값 유효하지 않은 경우 빈 false 리턴
		if (filePath == null || filePath.equals("")) {
			return false;
		}

		File f = null;
		boolean result = false;
		try {
			f = new File(EgovWebUtil.filePathBlackList(filePath));
			if (f.exists()) {
				result = f.canRead();
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		}

		return result;
	}

	/**
	 * <pre>
	 * Comment : 디렉토리(파일)의 쓰기권한을 확인한다.(대상경로가 파일인 경우만 정보가 유효함)
	 * </pre>
	 *
	 * @return boolean result 쓰기가능하면 true를 리턴한다. 권한이 없어가 파일이 없는 경우는 false를 리턴한다.
	 */
	public static boolean canWrite(String filePath) {

		// 인자값 유효하지 않은 경우 빈 false 리턴
		if (filePath == null || filePath.equals("")) {
			return false;
		}

		File f = null;
		boolean result = false;
		try {
			f = new File(EgovWebUtil.filePathBlackList(filePath));
			if (f.exists()) {
				result = f.canWrite();
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		}

		return result;
	}

	/**
	 * <pre>
	 * Comment : 디렉토리(파일)의 이름을  확인한다.
	 * </pre>
	 *
	 * @return String result 이름을 리턴한다. 존재하지 않는 경우는 블랭크를 리턴한다.
	 */
	public static String getName(String filePath) {

		// 인자값 유효하지 않은 경우 빈 false 리턴
		if (filePath == null || filePath.equals("")) {
			return "";
		}

		File f = null;
		String result = "";
		try {
			f = new File(EgovWebUtil.filePathBlackList(filePath));
			if (f.exists()) {
				result = f.getName();
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		}

		return result;
	}

	/**
	 * <pre>
	 * Comment : 디렉토리(파일)를 삭제한다. (파일,디렉토리 구분없이 존재하는 경우 무조건 삭제한다)
	 * </pre>
	 *
	 * @return 성공하면 삭제된 절대경로, 아니면블랭크
	 */

	public static String deletePath(String filePath) {
		File file = new File(EgovWebUtil.filePathBlackList(filePath));
		String result = "";
		try {
	    /*
	    if(!file.exists()){
	    	//log.debug("File Exist..");
	    }else{
	        result = file.getAbsolutePath();
	        if(!file.delete()){
	            result="";
	        }
	    }
	    */
			if (file.exists()) {
				result = file.getAbsolutePath();
				if (!file.delete()) {
					result = "";
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * <pre>
	 * Comment : 디렉토리를 생성한다.
	 * </pre>
	 *
	 * @param dirPath 생성하고자 하는 절대경로
	 * @return 성공하면 새성된 절대경로, 아니면 블랭크
	 */

	public static String createDirectory(String dirPath) {
		File file = new File(EgovWebUtil.filePathBlackList(dirPath));
		String result = "";
		try {
			//            if(file.exists()){
			//            	//log.debug("File Exist..");
			//            }else{
			//                file.createNewFile();
			//                file.getAbsolutePath();
			//            }

			if (!file.exists()) {
				file.mkdirs();
				file.getAbsolutePath();
			}

		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * 디렉토리에 파일이 존재하는지 체크하는 기능
	 *
	 * @return boolean result 존재여부 True / False
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public static boolean checkFileExstByName(String dir, String file) throws Exception {

		// 파일 존재 여부
		boolean result = false;

		// 디렉토리 오픈
		String drctry = dir.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		File srcDrctry = new File(EgovWebUtil.filePathBlackList(drctry));

		// 디렉토리이면서, 존재하면
		if (srcDrctry.exists() && srcDrctry.isDirectory()) {

			// 디렉토리 안 목록을 조회한다. (파일명)
			File[] fileArray = srcDrctry.listFiles();
			ArrayList list = getSubFilesByName(fileArray, file);
			if (list != null && list.size() > 0) {
				result = true;
			}
		}

		return result;
	}

	/**
	 * 확장자별로 디렉토리에 파일이 존재하는지 체크하는 기능
	 *
	 * @return boolean result 존재여부 True / False
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public static boolean checkFileExstByExtnt(String dir, String eventn) throws Exception {

		// 파일 존재 여부
		boolean result = false;

		// 디렉토리 오픈
		String drctry = dir.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		File srcDrctry = new File(EgovWebUtil.filePathBlackList(drctry));

		// 디렉토리이면서, 존재하면
		if (srcDrctry.exists() && srcDrctry.isDirectory()) {

			// 디렉토리 안 목록을 조회한다. (확장자별)
			File[] fileArray = srcDrctry.listFiles();
			ArrayList list = getSubFilesByExtnt(fileArray, eventn);
			if (list != null && list.size() > 0) {
				result = true;
			}
		}

		return result;
	}

	/**
	 * 디렉토리에 생성자별 파일이 존재하는지 체크하는 기능
	 *
	 * @return boolean result 존재여부 True / False
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public static boolean checkFileExstByOwner(String dir, String owner) throws Exception {

		// 파일 존재 여부
		boolean result = false;

		// 디렉토리 오픈
		String drctry = dir.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		File srcDrctry = new File(EgovWebUtil.filePathBlackList(drctry));

		// 디렉토리이면서, 존재하면
		if (srcDrctry.exists() && srcDrctry.isDirectory()) {

			// 디렉토리 안 목록을 조회한다. (생성자)
			File[] fileArray = srcDrctry.listFiles();
			ArrayList list = getSubFilesByOwner(fileArray, owner);
			if (list != null && list.size() > 0) {
				result = true;
			}
		}

		return result;
	}

	/**
	 * 수정기간별로 디렉토리에 파일이 존재하는지 체크하는 기능
	 *
	 * @return boolean result 존재여부 True / False
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public static boolean checkFileExstByUpdtPd(String dir, String updtFrom, String updtTo) throws Exception {

		// 파일 존재 여부
		boolean result = false;

		// 디렉토리 오픈
		String drctry = dir.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		File srcDrctry = new File(EgovWebUtil.filePathBlackList(drctry));

		// 디렉토리이면서, 존재하면
		if (srcDrctry.exists() && srcDrctry.isDirectory()) {

			// 디렉토리 안 목록을 조회한다. (수정기간별)
			File[] fileArray = srcDrctry.listFiles();
			ArrayList list = getSubFilesByUpdtPd(fileArray, updtFrom, updtTo);
			if (list != null && list.size() > 0) {
				result = true;
			}
		}

		return result;
	}

	/**
	 * 사이즈별로 디렉토리에 파일이 존재하는지 체크하는 기능
	 *
	 * @return boolean result 존재여부 True / False
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public static boolean checkFileExstBySize(String dir, long sizeFrom, long sizeTo) throws Exception {

		// 파일 존재 여부
		boolean result = false;

		// 디렉토리 오픈
		String drctry = dir.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		File srcDrctry = new File(EgovWebUtil.filePathBlackList(drctry));

		// 디렉토리이면서, 존재하면
		if (srcDrctry.exists() && srcDrctry.isDirectory()) {

			// 디렉토리 안 목록을 조회한다. (사이즈별)
			File[] fileArray = srcDrctry.listFiles();
			ArrayList list = getSubFilesBySize(fileArray, sizeFrom, sizeTo);
			if (list != null && list.size() > 0) {
				result = true;
			}
		}

		return result;
	}

	/**
	 * 디렉토리 내부 하위목록들 중에서 파일을 찾는 기능(모든 목록 조회)
	 *
	 * @return ArrayList list 파일목록(절대경로)
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList getSubFilesByAll(File[] fileArray) throws Exception {

		ArrayList list = new ArrayList();

		for (int i = 0; i < fileArray.length; i++) {
			// 디렉토리 안에 디렉토리면 그 안의 파일목록에서 찾도록 재귀호출한다.
			if (fileArray[i].isDirectory()) {
				File[] tmpArray = fileArray[i].listFiles();
				list.addAll(getSubFilesByAll(tmpArray));
				// 파일이면 담는다.
			} else {
				list.add(fileArray[i].getAbsolutePath());
			}
		}

		return list;
	}

	/**
	 * 디렉토리 내부 하위목록들 중에서 파일을 찾는 기능(파일명)
	 *
	 * @return ArrayList list 파일목록(절대경로)
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList getSubFilesByName(File[] fileArray, String file) throws Exception {

		ArrayList list = new ArrayList();

		for (int i = 0; i < fileArray.length; i++) {
			// 디렉토리 안에 디렉토리면 그 안의 파일목록에서 찾도록 재귀호출한다.
			if (fileArray[i].isDirectory()) {
				File[] tmpArray = fileArray[i].listFiles();
				list.addAll(getSubFilesByName(tmpArray, file));
				// 파일이면 파일명이 같은지 비교한다.
			} else {
				if (fileArray[i].getName().equals(file)) {
					list.add(fileArray[i].getAbsolutePath());
				}
			}
		}

		return list;
	}

	/**
	 * 디렉토리 내부 하위목록들 중에서 파일을 찾는 기능(확장자별)
	 *
	 * @return ArrayList list 파일목록(절대경로)
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList getSubFilesByExtnt(File[] fileArray, String extnt) throws Exception {

		ArrayList list = new ArrayList();

		for (int i = 0; i < fileArray.length; i++) {
			// 디렉토리 안에 디렉토리면 그 안의 파일목록에서 찾도록 재귀호출한다.
			if (fileArray[i].isDirectory()) {
				File[] tmpArray = fileArray[i].listFiles();
				list.addAll(getSubFilesByExtnt(tmpArray, extnt));
				// 파일이면 확장자명이 들어있는지 비교한다.
			} else {
				if (fileArray[i].getName().indexOf(extnt) != -1) {
					list.add(fileArray[i].getAbsolutePath());
				}
			}
		}

		return list;
	}

	/**
	 * 디렉토리 내부 하위목록들 중에서 파일을 찾는 기능(최종수정기간별)
	 *
	 * @return ArrayList list 파일목록(절대경로)
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList getSubFilesByUpdtPd(File[] fileArray, String updtFrom, String updtTo) throws Exception {

		ArrayList list = new ArrayList();

		for (int i = 0; i < fileArray.length; i++) {
			// 디렉토리 안에 디렉토리면 그 안의 파일목록에서 찾도록 재귀호출한다.
			if (fileArray[i].isDirectory()) {
				File[] tmpArray = fileArray[i].listFiles();
				list.addAll(getSubFilesByUpdtPd(tmpArray, updtFrom, updtTo));
				// 파일이면 수정기간내에 존재하는지 비교한다.
			} else {
				// 파일의 최종수정일자 조회
				long date = fileArray[i].lastModified();
				java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyyMMdd", java.util.Locale.KOREA);
				String lastUpdtDate = dateFormat.format(new java.util.Date(date));
				// 수정기간 내에 존재하는지 확인
				if (Integer.parseInt(lastUpdtDate) >= Integer.parseInt(updtFrom) && Integer.parseInt(lastUpdtDate) <= Integer.parseInt(updtTo)) {
					list.add(fileArray[i].getAbsolutePath());
				}
			}
		}

		return list;
	}

	/**
	 * 디렉토리 내부 하위목록들 중에서 파일을 찾는 기능(사이즈별)
	 *
	 * @return ArrayList list 파일목록(절대경로)
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList getSubFilesBySize(File[] fileArray, long sizeFrom, long sizeTo) throws Exception {

		ArrayList list = new ArrayList();

		for (int i = 0; i < fileArray.length; i++) {
			// 디렉토리 안에 디렉토리면 그 안의 파일목록에서 찾도록 재귀호출한다.
			if (fileArray[i].isDirectory()) {
				File[] tmpArray = fileArray[i].listFiles();
				list.addAll(getSubFilesBySize(tmpArray, sizeFrom, sizeTo));
				// 파일이면, 사이즈내에 존재하는지 비교한다.
			} else {
				// 파일의 사이즈 조회
				long size = fileArray[i].length();
				// 사이즈 내에 존재하는지 확인
				if (size >= (sizeFrom * BUFFER_SIZE) && size <= (sizeTo * BUFFER_SIZE)) {
					list.add(fileArray[i].getAbsolutePath());
				}
			}
		}

		return list;
	}

	/**
	 * 디렉토리 내부 하위목록들 중에서 파일을 찾는 기능(생성자별)
	 *
	 * @return ArrayList list 파일목록(절대경로)
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList getSubFilesByOwner(File[] fileArray, String owner) throws Exception {

		ArrayList list = new ArrayList();

		for (int i = 0; i < fileArray.length; i++) {
			// 디렉토리 안에 디렉토리면 그 안의 파일목록에서 찾도록 재귀호출한다.
			if (fileArray[i].isDirectory()) {
				File[] tmpArray = fileArray[i].listFiles();
				ArrayList list1 = getSubFilesByOwner(tmpArray, owner);
				if (list1 != null)
					list.addAll(list1);

				// 파일이면, 생성자가 같은지 비교한다.
			} else {
				// 파일 생성자 조회
				String fullpath = EgovWebUtil.filePathBlackList(fileArray[i].getAbsolutePath());
				Process p = null;
				if (Globals.OS_TYPE.equals("UNIX")) {
					String[] command = { EgovPropertiesUtil.getProperty(Globals.SHELL_FILE_PATH, "SHELL." + Globals.OS_TYPE + ".getDrctryByOwner"),
							fullpath.substring(0, fullpath.lastIndexOf("/")), fullpath.substring(fullpath.lastIndexOf("/"), fullpath.length()), owner };
					p = Runtime.getRuntime().exec(command);
					p.waitFor();
				} else if (Globals.OS_TYPE.equals("WINDOWS")) {
					String command = EgovPropertiesUtil.getProperty(Globals.SHELL_FILE_PATH, "SHELL." + Globals.OS_TYPE + ".getDrctryByOwner");
					p = Runtime.getRuntime().exec(command);
					p.waitFor();
				}
				//프로세스 에러시 종료
				if (p != null && p.exitValue() != 0) {
					BufferedReader b_err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
					try {
						while (b_err.ready()) {
							//String line = b_err.readLine();
							//if (line.length() <= MAX_STR_LEN) log.debug("ERR\n" + line);
						}
						b_err.close();
					} catch (Exception e) {
						//e.printStackTrace();
						throw new RuntimeException(e);
					} finally {
						close(b_err);
					}
				}
				//프로세스 실행 성공시 결과 확인
				else {
					BufferedReader b_out = null;
					try {
						b_out = new BufferedReader(new InputStreamReader(p.getInputStream()));
						while (b_out.ready()) {
							// 결과문자가 있으면 생성자가 일치하는 파일이 존재한다는 의미
							String tmpStr = b_out.readLine();
							if (tmpStr != null && "".equals(tmpStr) && tmpStr.length() <= MAX_STR_LEN) {
								list.add(fileArray[i].getAbsolutePath());
							}
						}
						b_out.close();
					} catch (Exception e) {
						//e.printStackTrace();
						throw new RuntimeException(e);
					} finally {
						close(b_out);
					}
				}
			}
		}

		return list;
	}

	/**
	 * <pre>
	 * Comment : 디렉토리를 생성한다.
	 * </pre>
	 *
	 * @param dirPath 생성하고자 하는 절대경로
	 * @return 성공하면 새성된 절대경로, 아니면 블랭크
	 */

	public static String createNewDirectory(String dirPath) {

		// 인자값 유효하지 않은 경우 블랭크 리턴
		if (dirPath == null || dirPath.equals("")) {
			return "";
		}

		File file = new File(EgovWebUtil.filePathBlackList(dirPath));
		String result = "";
		try {
			// 없으면 생성
			if (file.exists()) {
				// 혹시 존재해도 파일이면 생성 - 생성되지 않는다.(아래는 실질적으로는 진행되지 않음)
				if (file.isFile()) {
					//new File(file.getParent()).mkdirs();
					if (file.mkdirs()) {
						result = file.getAbsolutePath();
					}
				} else {
					result = file.getAbsolutePath();
				}
			} else {
				// 존해하지 않으면 생성
				if (file.mkdirs()) {
					result = file.getAbsolutePath();
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		}

		return result;
	}

	/**
	 * <pre>
	 * Comment : 파일을 생성한다.
	 * </pre>
	 *
	 *
	 */
	public static String createNewFile(String filePath) {

		// 인자값 유효하지 않은 경우 블랭크 리턴
		if (filePath == null || filePath.equals("")) {
			return "";
		}

		File file = new File(EgovWebUtil.filePathBlackList(filePath));
		String result = "";
		try {
			if (file.exists()) {
				result = filePath;
			} else {
				// 존재하지 않으면 생성함
				new File(file.getParent()).mkdirs();
				if (file.createNewFile()) {
					result = file.getAbsolutePath();
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		}

		return result;
	}

	/**
	 * <pre>
	 * Comment : 디렉토리를 삭제한다.
	 * </pre>
	 *
	 * @param dirDeletePath 삭제하고자 하는디렉토리의 절대경로(파일의 경로가 들어오는 경우 삭제하지 않음)
	 * @return 성공하면 삭제된 절대경로, 아니면블랭크
	 */

	public static String deleteDirectory(String dirDeletePath) {

		// 인자값 유효하지 않은 경우 블랭크 리턴
		if (dirDeletePath == null || dirDeletePath.equals("")) {
			return "";
		}
		String result = "";
		File file = new File(EgovWebUtil.filePathBlackList(dirDeletePath));
		if (file.isDirectory()) {
			String[] fileList = file.list();
			//소속된 파일을 모두 삭제
			for (int i = 0; i < fileList.length; i++) {

				//log.debug("fileList["+i+"] : "+ dirDeletePath +"/"+fileList[i]);
				File f = new File(EgovWebUtil.filePathBlackList(dirDeletePath) + "/" + fileList[i]);
				if (f.isFile()) {
					//디렉토리에 속한 파일들을 모두 삭제한다.
					f.delete();
				} else {
					//디렉토리에 속한 하위 디렉토리들에 대한 삭제 명령을 재귀적으로 호출시킨다.
					deleteDirectory(dirDeletePath + "/" + fileList[i]);
				}
			}
			// 디렉토리에 속한 파일들과 하위 디렉토리가 삭제되었으면 디렉토리 자신을 삭제한다.
			result = deletePath(dirDeletePath);
		} else {
			result = "";
		}

		return result;
	}

	/**
	 * <pre>
	 * Comment : 파일을 삭제한다.
	 * </pre>
	 *
	 * @param fileDeletePath 삭제하고자 하는파일의 절대경로
	 * @return 성공하면 삭제된 파일의 절대경로, 아니면블랭크
	 */

	public static String deleteFile(String fileDeletePath) {

		// 인자값 유효하지 않은 경우 블랭크 리턴
		if (fileDeletePath == null || fileDeletePath.equals("")) {
			return "";
		}
		String result = "";
		File file = new File(EgovWebUtil.filePathBlackList(fileDeletePath));
		if (file.isFile()) {
			result = deletePath(fileDeletePath);
		} else {
			result = "";
		}

		return result;
	}

	/**
	 * 파일의 읽기권한을 체크한다.
	 *
	 * @return boolean result 읽기권한 True / False
	 * @exception Exception
	 */
	public static boolean checkReadAuth(String file) throws Exception {

		// 읽기가능여부
		boolean result = false;

		try {
			// 전달받은 경로를 통해 파일 인스턴스를 생성한다.
			String file1 = file.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
			File srcFile = new File(EgovWebUtil.filePathBlackList(file1));

			// 존재하는지 확인한다.
			if (srcFile.exists()) {
				// 읽기 가능한지 체크한다.
				result = srcFile.canRead();
			}// else {
			// 지정된 위치가 올바르지 않습니다.
			//log.debug("+++ 지정된 위치가 올바르지 않습니다.");
			//}

		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new RuntimeException(ex);
		}

		return result;
	}

	/**
	 * 파일의 쓰기권한을 체크한다.
	 *
	 * @return boolean result 쓰기권한 True / False
	 * @exception Exception
	 */
	public static boolean checkWriteAuth(String file) throws Exception {

		// 쓰기가능여부
		boolean result = false;

		try {
			// 전달받은 경로를 통해 파일 인스턴스를 생성한다.
			String file1 = file.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
			File srcFile = new File(EgovWebUtil.filePathBlackList(file1));

			// 존재하는지 확인한다.
			if (srcFile.exists()) {
				// 쓰기 가능한지 체크한다.
				result = srcFile.canWrite();
			}// else {
			// 지정된 위치가 올바르지 않습니다.
			//log.debug("+++ 지정된 위치가 올바르지 않습니다.");
			//}

		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new RuntimeException(ex);
		}

		return result;
	}

	/**
	 * 파일의 최종수정일자별 파일목록 조회하는 기능
	 *
	 * @return ArrayList list 파일목록
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList getFileListByDate(String drctry, String updtDate) throws Exception {

		// 결과 목록
		ArrayList list = null;

		// 디렉토리 오픈
		String drctry1 = drctry.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		File file = new File(EgovWebUtil.filePathBlackList(drctry1));

		// 디렉토리이며, 존재하면 최종수정일자가 같은 파일목록 조회 시작
		if (file.exists() && file.isDirectory()) {
			File[] fileArray = file.listFiles();
			list = getSubFilesByDate(fileArray, updtDate);
		}

		return list;
	}

	/**
	 * 파일의 최종수정기간내 파일목록 조회하는 기능
	 *
	 * @return ArrayList list 파일목록
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList getFileListByUpdtPd(String drctry, String updtFrom, String updtTo) throws Exception {

		// 결과 목록
		ArrayList list = null;

		// 디렉토리 오픈
		String drctry1 = drctry.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		File file = new File(EgovWebUtil.filePathBlackList(drctry1));

		// 디렉토리이며, 최종수정기간내  존재하는 파일목록 조회 시작
		if (file.exists() && file.isDirectory()) {
			File[] fileArray = file.listFiles();
			list = getSubFilesByUpdtPd(fileArray, updtFrom, updtTo);
		}

		return list;
	}

	/**
	 * 하위디렉토리 포함 최종수정일자가 같은 파일목록을 찾는 기능
	 *
	 * @return ArrayList list 파일목록
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList getSubFilesByDate(File[] fileArray, String updtDate) throws Exception {

		ArrayList list = new ArrayList();

		for (int i = 0; i < fileArray.length; i++) {
			// 디렉토리 안에 디렉토리면 그 안의 파일목록에서 찾도록 재귀호출한다.
			if (fileArray[i].isDirectory()) {
				File[] tmpArray = fileArray[i].listFiles();
				list.addAll(getSubFilesByDate(tmpArray, updtDate));
				// 파일이면 파일명이 같은지 비교한다.
			} else {
				// 파일의 최종수정일자 조회
				long date = fileArray[i].lastModified();
				java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyyMMdd", java.util.Locale.KOREA);
				String lastUpdtDate = dateFormat.format(new java.util.Date(date));
				if (Integer.parseInt(lastUpdtDate) == Integer.parseInt(updtDate)) {
					list.add(fileArray[i].getAbsolutePath());
				}
			}
		}

		return list;
	}

	/**
	 * 파일을 특정 구분자(',', '|', 'TAB')로 파싱하는 기능
	 *
	 * @return Vector parResult 파싱결과 구조체
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public static Vector parsFileByChar(String parFile, String parChar, int parField) throws Exception {

		// 파싱결과 구조체
		Vector parResult = new Vector();

		// 파일 오픈
		String parFile1 = parFile.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		File file = new File(EgovWebUtil.filePathBlackList(parFile1));
		BufferedReader br = null;
		try {
			// 파일이며, 존재하면 파싱 시작
			if (file.exists() && file.isFile()) {

				// 1. 파일 텍스트 내용을 읽어서 StringBuffer에 쌓는다.
				br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				StringBuffer strBuff = new StringBuffer();
				String line = "";
				while ((line = br.readLine()) != null) {
					if (line.length() < MAX_STR_LEN)
						strBuff.append(line);
				}

				// 2. 쌓은 내용을 특정 구분자로 파싱하여 String 배열로 얻는다.
//		String[] strArr = EgovStringUtil.split(strBuff.toString(), parChar);
				String[] strArr = strBuff.toString().split(parChar);

				// 3. 필드 수 만큼 돌아가며 Vector<ArrayList> 형태로 만든다.
				int filedCnt = 1;
				ArrayList arr = new ArrayList();	// 2011.10.21 보안점검 후속조치
				for (int i = 0; i < strArr.length; i++) {

					if (parField != 1) {
						if ((filedCnt % parField) == 1) {
							if (strArr[i] != null) {
								arr.add(strArr[i]);
							}
							if (i == (strArr.length - 1)) {
								parResult.add(arr);
							}
						} else if ((filedCnt % parField) == 0) {
							if (strArr[i] != null) {	// 2011.10.21 보안점검 후속조치
								arr.add(strArr[i]);
								parResult.add(arr);
							}
						} else {
							if (strArr[i] != null) {	// 2011.10.21 보안점검 후속조치
								arr.add(strArr[i]);
								if (i == (strArr.length - 1)) {
									parResult.add(arr);
								}
							}
						}
					} else {
						arr = new ArrayList();
						if (strArr[i] != null)
							arr.add(strArr[i]);
						parResult.add(arr);
					}

					filedCnt++;
				}
			}
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			close(br);
		}

		return parResult;
	}

	/**
	 * 파일을 일정 길이로 파싱하는 기능
	 *
	 * @return Vector parResult 파싱결과 구조체
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public static Vector parsFileBySize(String parFile, int[] parLen, int parLine) throws Exception {

		// 파싱결과 구조체
		Vector parResult = new Vector();

		// 파일 오픈
		String parFile1 = parFile.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		File file = new File(EgovWebUtil.filePathBlackList(parFile1));
		BufferedReader br = null;
		try {
			// 파일이며, 존재하면 파싱 시작
			if (file.exists() && file.isFile()) {

				// 1. 입력된 라인수만큼 파일 텍스트 내용을 읽어서 String[]에 쌓는다.
				br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				if (parLine < 0) {	// 2011.11.1 보안점검 후속조치
					parLine = 0;
				}
				String[] strArr = new String[parLine];
				String line = "";
				int readCnt = 0;
				while ((line = br.readLine()) != null && readCnt < parLine) {
					if (line.length() <= MAX_STR_LEN)
						strArr[readCnt++] = line;
				}

				// 2. Vector<ArrayList> 형태로 만든다.
				for (int i = 0; i < strArr.length; i++) {
					String text = strArr[i];
					ArrayList arr = new ArrayList();
					int idx = 0;
					boolean result = false;
					for (int j = 0; j < parLen.length; j++) {
						if (!result) { //if(result != true){
							String split = "";
							if (text.length() < (idx + parLen[j])) {
								split = text.substring(idx, text.length());
								result = true;
							} else {
								split = text.substring(idx, idx + parLen[j]);
							}
							arr.add(split);
							idx = idx + parLen[j];
						}
					}
					parResult.add(arr);
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			close(br);
		}

		return parResult;
	}

	/**
	 * 두 파일의 사이즈를 비교하는 기능 (KB 단위 비교)
	 *
	 * @return boolean result 동일여부 True / False
	 * @exception Exception
	 */
	public static boolean cmprFilesBySize(String cmprFile1, String cmprFile2) throws Exception {

		// 파일 동일 여부
		boolean result = false;

		// 파일 오픈
		String cmprFile11 = cmprFile1.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		String cmprFile22 = cmprFile2.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		File file1 = new File(EgovWebUtil.filePathBlackList(cmprFile11));
		File file2 = new File(EgovWebUtil.filePathBlackList(cmprFile22));

		// 파일이며, 존재하면 파일 사이즈 비교
		if (file1.exists() && file2.exists() && file1.isFile() && file2.isFile()) {

			// 파일1 사이즈
			long size1 = file1.length();

			// 파일2 사이즈
			long size2 = file2.length();

			// 사이즈 비교
			if (size1 == size2) {
				result = true;
			}

		}

		return result;
	}

	/**
	 * 두 파일의 수정일자를 비교하는 기능
	 *
	 * @return boolean result 동일여부 True / False
	 * @exception Exception
	 */
	public static boolean cmprFilesByUpdtPd(String cmprFile1, String cmprFile2) throws Exception {

		// 파일 동일 여부
		boolean result = false;

		// 파일 오픈
		String cmprFile11 = cmprFile1.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		String cmprFile22 = cmprFile2.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		File file1 = new File(EgovWebUtil.filePathBlackList(cmprFile11));
		File file2 = new File(EgovWebUtil.filePathBlackList(cmprFile22));

		// 파일이며, 존재하면 파일 수정일자 비교
		if (file1.exists() && file2.exists() && file1.isFile() && file2.isFile()) {

			// 파일1 수정일자
			long date1 = file1.lastModified();
			java.text.SimpleDateFormat dateFormat1 = new java.text.SimpleDateFormat("yyyyMMdd", java.util.Locale.KOREA);
			String lastUpdtDate1 = dateFormat1.format(new java.util.Date(date1));

			// 파일2 수정일자
			long date2 = file2.lastModified();
			java.text.SimpleDateFormat dateFormat2 = new java.text.SimpleDateFormat("yyyyMMdd", java.util.Locale.KOREA);
			String lastUpdtDate2 = dateFormat2.format(new java.util.Date(date2));

			// 수정일자 비교
			if (lastUpdtDate1.equals(lastUpdtDate2)) {
				result = true;
			}
		}

		return result;
	}

	/**
	 * 두 파일의 내용을 비교하는 기능 (TEXT파일만 가능)
	 *
	 * @return boolean result 동일여부 True / False
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public static boolean cmprFilesByContent(String cmprFile1, String cmprFile2) throws Exception {

		// 파일 동일 여부
		boolean result = false;

		// 파일 오픈
		String cmprFile11 = cmprFile1.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		String cmprFile22 = cmprFile2.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		File file1 = new File(EgovWebUtil.filePathBlackList(cmprFile11));
		File file2 = new File(EgovWebUtil.filePathBlackList(cmprFile22));

		BufferedReader br1 = null;
		BufferedReader br2 = null;		// 2011.10.21 보안조치

		try {
			// 파일이며, 존재하면 파일 내용 비교
			if (file1.exists() && file2.exists() && file1.isFile() && file2.isFile()) {

				ArrayList cmprText1 = new ArrayList();
				ArrayList cmprText2 = new ArrayList();

				// 파일1 텍스트 내용
				br1 = new BufferedReader(new InputStreamReader(new FileInputStream(file1)));
				String line1 = "";
				while ((line1 = br1.readLine()) != null) {
					if (line1.length() < MAX_STR_LEN)
						cmprText1.add(line1);
				}

				// 파일2 텍스트 내용
				br2 = new BufferedReader(new InputStreamReader(new FileInputStream(file2)));
				String line2 = "";
				while ((line2 = br2.readLine()) != null) {
					if (line2.length() <= MAX_STR_LEN)
						cmprText2.add(line2);
				}

				// 내용 비교
				boolean isWrong = false;
				for (int i = 0; i < cmprText1.size(); i++) {
					if (!isWrong) { //   if(isWrong != true){
						String text1 = (String)cmprText1.get(i);
						String text2 = (String)cmprText2.get(i);

						if (!text1.equals(text2)) {
							isWrong = true;
						}
					}
				}

				if (!isWrong) {
					result = true;
				}
			}
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			close(br1);	// 2012.11 KISA 보안조치
			close(br2);	// 2012.11 KISA 보안조치
		}

		return result;
	}

	/**
	 * 두 파일의 생성자를 비교하는 기능
	 *
	 * @return boolean result 동일여부 True / False
	 * @exception Exception
	 */
	public static boolean cmprFilesByOwner(String cmprFile1, String cmprFile2) throws Exception {

		// 파일 동일 여부
		boolean result = false;

		// 파일1 생성자
		String owner1 = getOwner(cmprFile1);

		// 파일2 생성자
		String owner2 = getOwner(cmprFile2);

		if (owner1 != null && owner2 != null && !"".equals(owner1) && !"".equals(owner2) && owner1.equals(owner2)) {
			result = true;
		}

		return result;
	}

	/**
	 * <PRE>
	 * 1. MethodName : selectFile
	 * 2. ClassName  : EgovFileTool
	 * 3. Comment   : 해당 경로의 파일 정보 획득
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017.12.221. 오후 3:38:19
	 * </PRE>
	 *   @return File
	 *   @param source
	 *   @return
	 *   @throws Exception
	 */
	public static File selectFile(String source) throws Exception {

		String src = source.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		File srcFile = new File(EgovWebUtil.filePathBlackList(src));

		return srcFile;
	}

	/**
	 * 단일 파일을 다른 파일에 복사(Copy)한다.
	 *
	 * @return boolean result 복사여부 True / False
	 * @exception Exception
	 */
	public static boolean copyFile(String source, String target) throws Exception {

		// 복사여부
		boolean result = false;

		// 원본 파일
		String src = source.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		File srcFile = new File(EgovWebUtil.filePathBlackList(src));

		// 타켓 파일
		String tar = target.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);

		try {
			// 원본 파일이 존재하는지 확인한다.
			if (srcFile.exists()) {

				// 복사될 target 파일 생성
				tar = createNewFile(tar);
				//log.debug("tar:"+tar);
				File tarFile = new File(EgovWebUtil.filePathBlackList(tar));
				//log.debug("tarFile:"+tarFile.getAbsolutePath());
				// 복사
				result = execCopyFile(srcFile, tarFile);

			}//    else {
			// 원본 파일이 존재하지 않습니다.
			//log.debug("+++ 원본 파일이 존재하지 않습니다.");
			// }

		} catch (IOException ex) {
			//ex.printStackTrace();
			throw new RuntimeException(ex);
		}

		return result;
	}

	/**
	 * 여러 파일을 다른 디렉토리에 복사(Copy)한다.
	 *
	 * @return boolean result 복사여부 True / False
	 * @exception Exception
	 */
	public static boolean copyFiles(String[] source, String target) throws Exception {

		// 복사여부
		boolean result = true;

		// 복사 이전에 복사할 파일들의 경로가 올바른지 확인한다.
		for (int i = 0; i < source.length; i++) {
			String src = source[i].replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
			File chkFile = new File(EgovWebUtil.filePathBlackList(src));
			if (!chkFile.exists()) {
				//log.debug("+++ 원본 파일이 존재하지 않습니다.");
				return result;
			}
		}

		String tar = target.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);

		// 복사를 시작한다.
		for (int j = 0; j < source.length; j++) {

			if (result) { //result != false

				// 타겟파일이름 명명
				File chkFile = new File(EgovWebUtil.filePathBlackList(source[j]));
				String tarTemp = tar + FILE_SEPARATOR + chkFile.getName();

				try {
					// 복사될 target 파일 생성
					tarTemp = createNewFile(tarTemp);
					File tarFile = new File(EgovWebUtil.filePathBlackList(tarTemp));

					// 복사
					result = execCopyFile(chkFile, tarFile);

				} catch (IOException ex) {
					//ex.printStackTrace();
					throw new RuntimeException(ex);
				}

			}

		} // end for

		return result;
	}

	/**
	 * 확장자별 파일들을 다른 디렉토리에 복사(Copy)한다.
	 *
	 * @return boolean result 복사여부 True / False
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public static boolean copyFilesByExtnt(String source, String extnt, String target) throws Exception {

		// 복사여부
		boolean result = true;

		// 원본 파일
		String src = source.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		File srcFile = new File(EgovWebUtil.filePathBlackList(src));

		// 원본 디렉토리가 존재하는지 확인한다.
		if (srcFile.exists() && srcFile.isDirectory()) {

			String tar = target.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);

			// 원본 디렉토리 안에서 확장자가 일치하는 파일목록을 가져온다.
			File[] fileArray = srcFile.listFiles();
			ArrayList list = getSubFilesByExtnt(fileArray, extnt);

			// 복사를 시작한다.
			for (int i = 0; i < list.size(); i++) {
				if (result) { //f(result != false){
					// 원본파일 절대경로
					String abspath = (String)list.get(i);

					// 타겟파일이름 명명
					File chkFile = new File(EgovWebUtil.filePathBlackList(abspath));
					String tarTemp = tar + FILE_SEPARATOR + chkFile.getName();

					try {
						// 복사될 target 파일 생성
						tarTemp = createNewFile(tarTemp);
						File tarFile = new File(EgovWebUtil.filePathBlackList(tarTemp));

						// 복사
						result = execCopyFile(chkFile, tarFile);

					} catch (IOException ex) {
						//ex.printStackTrace();
						throw new RuntimeException(ex);
					}
				}

			} // end for

		}// else {
		// 원본 파일이 존재하지 않습니다.
		//log.debug("+++ 원본 디렉토리가 존재하지 않습니다.");
		//}

		return result;
	}

	/**
	 * 수정기간내 파일들을 다른 디렉토리에 복사(Copy)한다.
	 *
	 * @return boolean result 복사여부 True / False
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public static boolean copyFilesByUpdtPd(String source, String updtFrom, String updtTo, String target) throws Exception {

		// 복사여부
		boolean result = true;

		// 원본 파일
		String src = source.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		File srcFile = new File(EgovWebUtil.filePathBlackList(src));

		// 원본 디렉토리가 존재하는지 확인한다.
		if (srcFile.exists() && srcFile.isDirectory()) {

			String tar = target.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);

			// 원본 디렉토리 안에서 수정기간내 존재하는 파일목록을 가져온다.
			File[] fileArray = srcFile.listFiles();
			ArrayList list = getSubFilesByUpdtPd(fileArray, updtFrom, updtTo);

			// 복사를 시작한다.
			for (int i = 0; i < list.size(); i++) {

				if (result) { //f(result != false){

					// 원본파일 절대경로
					String abspath = (String)list.get(i);

					// 타겟파일이름 명명
					File chkFile = new File(EgovWebUtil.filePathBlackList(abspath));
					String tarTemp = tar + FILE_SEPARATOR + chkFile.getName();

					try {
						// 복사될 target 파일 생성
						tarTemp = createNewFile(tarTemp);
						File tarFile = new File(tarTemp);

						// 복사
						result = execCopyFile(chkFile, tarFile);

					} catch (IOException ex) {
						//ex.printStackTrace();
						throw new RuntimeException(ex);
					}

				}

			} // end for

		} //else {
		// 원본 파일이 존재하지 않습니다.
		//log.debug("+++ 원본 디렉토리가 존재하지 않습니다.");
		//}

		return result;
	}

	/**
	 * 사이즈내 파일들을 다른 디렉토리에 복사(Copy)한다.
	 *
	 * @return boolean result 복사여부 True / False
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public static boolean copyFilesBySize(String source, long sizeFrom, long sizeTo, String target) throws Exception {

		// 복사여부
		boolean result = true;

		// 원본 파일
		String src = source.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		File srcFile = new File(EgovWebUtil.filePathBlackList(src));

		// 원본 디렉토리가 존재하는지 확인한다.
		if (srcFile.exists() && srcFile.isDirectory()) {

			String tar = target.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);

			// 원본 디렉토리 안에서 사이즈내 존재하는 파일목록을 가져온다.
			File[] fileArray = srcFile.listFiles();
			ArrayList list = getSubFilesBySize(fileArray, sizeFrom, sizeTo);

			// 복사를 시작한다.
			for (int i = 0; i < list.size(); i++) {

				if (result) { //result != false
					// 원본파일 절대경로
					String abspath = (String)list.get(i);

					// 타겟파일이름 명명
					File chkFile = new File(EgovWebUtil.filePathBlackList(abspath));
					String tarTemp = tar + FILE_SEPARATOR + chkFile.getName();

					try {
						// 복사될 target 파일 생성
						tarTemp = createNewFile(tarTemp);
						File tarFile = new File(EgovWebUtil.filePathBlackList(tarTemp));

						// 복사
						result = execCopyFile(chkFile, tarFile);
						if (result) {
							break;
						}

					} catch (IOException ex) {
						//ex.printStackTrace();
						throw new RuntimeException(ex);
					}

				}

			} // end for

		}// else {
		// 원본 파일이 존재하지 않습니다.
		//log.debug("+++ 원본 디렉토리가 존재하지 않습니다.");
		//}

		return result;
	}

	/**
	 * 생성자별 파일들을 다른 디렉토리에 복사(Copy)한다.
	 *
	 * @return boolean result 복사여부 True / False
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public static boolean copyFilesByOwner(String source, String owner, String target) throws Exception {

		// 복사여부
		boolean result = true;

		// 원본 파일
		String src = source.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		File srcFile = new File(EgovWebUtil.filePathBlackList(src));

		// 원본 디렉토리가 존재하는지 확인한다.
		if (srcFile.exists() && srcFile.isDirectory()) {

			String tar = target.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);

			// 원본 디렉토리 안에서 생성자별 일치하는 파일목록을 가져온다.
			File[] fileArray = srcFile.listFiles();
			ArrayList list = getSubFilesByOwner(fileArray, owner);

			// 복사를 시작한다.
			for (int i = 0; i < list.size(); i++) {

				if (result) { //result != false

					// 원본파일 절대경로
					String abspath = (String)list.get(i);

					// 타겟파일이름 명명
					File chkFile = new File(EgovWebUtil.filePathBlackList(abspath));
					String tarTemp = tar + FILE_SEPARATOR + chkFile.getName();

					try {
						// 복사될 target 파일 생성
						tarTemp = createNewFile(tarTemp);
						File tarFile = new File(EgovWebUtil.filePathBlackList(tarTemp));

						// 복사
						result = execCopyFile(chkFile, tarFile);

						if (!result) {
							break;
						}

					} catch (IOException ex) {
						//ex.printStackTrace();
						throw new RuntimeException(ex);
					}

				}

			} // end for

		}// else {
		// 원본 파일이 존재하지 않습니다.
		//log.debug("+++ 원본 디렉토리가 존재하지 않습니다.");
		//}

		return result;
	}

	/**
	 * 복사를 수행하는 기능
	 *
	 * @return boolean result 복사여부 True / False
	 * @exception Exception
	 */
	public static boolean execCopyFile(File srcFile, File tarFile) throws Exception {

		// 결과
		boolean result = false;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			// 복사
			fis = new FileInputStream(srcFile);

			//예외상황에 따른 처리 추가함. -> 만약 tarFile 이 디렉토리명인 경우 디렉토리 밑으로 새로 파일을 생성해서 복사한다.. like DOS
			File tarFile1 = tarFile;
			if (tarFile1.isDirectory()) {
				tarFile1 = new File(EgovWebUtil.filePathBlackList(tarFile1.getAbsolutePath()) + "/" + srcFile.getName());
			}
			fos = new FileOutputStream(tarFile1);
			byte[] buffer = new byte[(int)BUFFER_SIZE];
			int i = 0;
			if (fis != null && fos != null) {
				while ((i = fis.read(buffer)) != -1) {
					fos.write(buffer, 0, i);
				}
			}

			result = true;
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			close(fis);	// 2012.11 KISA 보안조치
			close(fos);	// 2012.11 KISA 보안조치
		}

		return result;
	}

	/**
	 * <pre>
	 * Comment : 디렉토리를 삭제한다. (소유자 정보를 통해 삭제)
	 * </pre>
	 *
	 * @param dirDeletePath 삭제하고자 하는디렉토리의 절대경로(파일의 경로가 들어오는 경우 삭제하지 않음)
	 * @return 성공하면 삭제된 절대경로, 아니면블랭크
	 */

	public static String deleteDirectory(String dirDeletePath, String dirOwner) {

		// 인자값 유효하지 않은 경우 블랭크 리턴
		if (dirDeletePath == null || dirDeletePath.equals("") || dirOwner == null || dirOwner.equals("")) {
			return "";
		}

		// 찾은 결과를 전달할 ArrayList
		String result = "";
		File file = new File(EgovWebUtil.filePathBlackList(dirDeletePath));

		try {
			// 추가된 삭제조건 옵션에 합당한지 확인
			boolean isInCondition = false;
			String realOwner = getOwner(dirDeletePath);
			//log.debug("realOwner:"+realOwner);
			if (dirOwner.equals(realOwner)) {
				isInCondition = true;
			}
			// 삭제조건에 부합되면 디렉토리 삭제조치함
			if (file.isDirectory() && isInCondition) {
				result = deleteDirectory(dirDeletePath);
			} else {
				result = realOwner;
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		}

		return result;
	}

	/**
	 * <pre>
	 * Comment : 디렉토리를 삭제한다. (생성일자 조건으로 삭제)
	 * </pre>
	 *
	 * @param dirDeletePath 삭제하고자 하는디렉토리의 절대경로(파일의 경로가 들어오는 경우 삭제하지 않음)
	 * @param fromDate 디렉토리의 삭제조건 시작일자
	 * @param toDate 디렉토리의 삭제조건 종료일자
	 * @return 성공하면 삭제된 절대경로, 아니면블랭크
	 */
	public static String deleteDirectory(String dirDeletePath, String fromDate, String toDate) {

		// 인자값 유효하지 않은 경우 블랭크 리턴
		if (dirDeletePath == null || dirDeletePath.equals("") || fromDate == null || fromDate.equals("") || toDate == null || toDate.equals("")) {
			return "";
		}

		// 찾은 결과를 전달할 ArrayList
		String result = "";
		File file = new File(EgovWebUtil.filePathBlackList(dirDeletePath));

		// 추가된 삭제조건 옵션에 합당한지 확인
		boolean isInCondition = false;
		String lastModifyedDate = getLastModifiedDateFromFile(file);
		//log.debug("lastModifyedDate:"+lastModifyedDate);
		try {
			if (Integer.parseInt(lastModifyedDate) >= Integer.parseInt(fromDate) && Integer.parseInt(lastModifyedDate) <= Integer.parseInt(toDate)) {
				isInCondition = true;
			}

			// 삭제조건에 부합되면 디렉토리 삭제조치함
			if (file.isDirectory() && isInCondition) {
				result = deleteDirectory(dirDeletePath);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		}

		return result;
	}

	/**
	 * 파일(디렉토리)가 존재하는 파일시스템(마운트된 위치)을 조회하는 기능
	 *
	 * @return String mountLc 마운트위치
	 * @exception Exception
	 */
	public static String getMountLc(String file) throws Exception {

		// 디스크명
		String diskName = "";

		//String drctryName = "";
		String src = file.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);

		try {
			File srcFile = new File(EgovWebUtil.filePathBlackList(src));
			if (srcFile.exists()) {

				// 유닉스 파일시스템명 조회 (df -k $1 | grep $2 | awk -F" " '{print $7}')
				if (Globals.OS_TYPE.equals("UNIX")) {
					Process p = null;
					String[] command = { EgovPropertiesUtil.getProperty(Globals.SHELL_FILE_PATH, "SHELL." + Globals.OS_TYPE + ".getMountLc"), src, "/" };
					p = Runtime.getRuntime().exec(command);
					//p.waitFor();

					//boolean result = false;
					BufferedReader b_out = new BufferedReader(new InputStreamReader(p.getInputStream()));
					try {
						while (true) {
							String str = b_out.readLine();
							if (str.length() <= MAX_STR_LEN) {
								if (str == null)
									break;
								if (str != null && !"".equals(str)) {
									diskName = str;
								}
							}
						}
						b_out.close();
					} catch (Exception e) {
						//e.printStackTrace();
						System.out.println(e);
					} finally {
						close(b_out);
					}

					if (p != null)
						p.destroy();

					// 윈도우 파일시스템명 조회
				} else if (Globals.OS_TYPE.equals("WINDOWS")) {

					diskName = src.substring(0, 1).toUpperCase();
					//log.debug(diskName);
				}
			}// else {
			//log.debug("+++ 지정된 위치가 올바르지 않습니다.");
			// }

		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new RuntimeException(ex);
		}

		return diskName;
	}

	/**
	 * 파일(디렉토리)가 존재하는 디렉토리(Parent)를 조회하는 기능
	 *
	 * @return String drctryName 디렉토리
	 * @exception Exception
	 */
	public static String getDrctryName(String file) throws Exception {

		String drctryName = "";
		String src = file.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);

		try {
			File srcFile = new File(EgovWebUtil.filePathBlackList(src));
			if (srcFile.exists()) {
				drctryName = srcFile.getParent();
			}// else {
			//log.debug("+++ 지정된 위치가 올바르지 않습니다.");
			//}

		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new RuntimeException(ex);
		}

		return drctryName;
	}

	/**
	 * 파일(디렉토리)가 존재하는 파일명을 조회하는 기능
	 *
	 * @return String fileName 파일명
	 * @exception Exception
	 */
	public static String getFileName(String file) throws Exception {

		String fileName = "";
		String src = file.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);

		try {
			File srcFile = new File(EgovWebUtil.filePathBlackList(src));
			if (srcFile.exists()) {
				fileName = srcFile.getName();
			}// else {
			//log.debug("+++ 지정된 위치가 올바르지 않습니다.");
			//}

		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new RuntimeException(ex);
		}

		return fileName;
	}

	/**
	 * 파일(디렉토리)의 최종수정일자를 조회하는 기능
	 *
	 * @return String updtDate 최종수정일자(YYYYMMDD 형태)
	 * @exception Exception
	 */
	public static String getUpdtDate(String file) throws Exception {

		String updtDate = "";
		String src = file.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);

		try {
			File srcFile = new File(EgovWebUtil.filePathBlackList(src));
			if (srcFile.exists()) {
				long date = srcFile.lastModified();
				java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyyMMdd", java.util.Locale.KOREA);
				updtDate = dateFormat.format(new java.util.Date(date));
			}// else {
			//log.debug("+++ 지정된 위치가 올바르지 않습니다.");
			//}

		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new RuntimeException(ex);
		}

		return updtDate;
	}

	/**
	 * 파일(디렉토리)의 생성자를 조회하는 기능
	 *
	 * @return String owner 생성자
	 * @exception Exception
	 */
	public static String getOwner(String file) throws Exception {

		String owner = "";
		String src = file.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		BufferedReader b_err = null;
		BufferedReader b_out = null;
		try {
			File srcFile = new File(EgovWebUtil.filePathBlackList(src));
			if (srcFile.exists()) {

				// 파일 생성자 조회
				String parentPath = srcFile.getParent();
				String fname = srcFile.getName();

				Process p = null;
				String cmdStr = EgovPropertiesUtil.getProperty(Globals.SHELL_FILE_PATH, "SHELL." + Globals.OS_TYPE + ".getDrctryOwner");
				String[] command = { cmdStr.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR),
						parentPath.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR), fname };
				p = Runtime.getRuntime().exec(command);
				p.waitFor();
				//프로세스 에러시 종료
				if (p != null && p.exitValue() != 0) {
					b_err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
					while (b_err.ready()) {
						//String line = b_err.readLine();
						//if (line.length() <= MAX_STR_LEN) log.debug("ERR\n" + line);
					}
					b_err.close();
				}
				//프로세스 실행 성공시 결과 확인
				else {
					boolean result = false;
					b_out = new BufferedReader(new InputStreamReader(p.getInputStream()));
					while (b_out.ready()) {
						if (!result) { // result != true
							// 결과문자가 있으면 생성자가 있다는 의미
							owner = b_out.readLine();
							if (owner.length() <= MAX_STR_LEN) {
								if (owner != null && !"".equals(owner)) {
									result = true;
									break;
								}
							}
						}
					}
					b_out.close();
				}

			}// else {
			//log.debug("+++ 지정된 위치가 올바르지 않습니다.");
			//}

		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			close(b_err);
			close(b_out);
		}

		return owner;
	}

	/**
	 * 파일(디렉토리)의 접근권한을 조회하는 기능
	 *
	 * @return String access 접근권한(유닉스=777, 666, 윈도우=Read, Write, Read Only)
	 * @exception Exception
	 */
	public static String getAccess(String file) throws Exception {

		String access = "";
		String src = file.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		BufferedReader b_err = null;
		BufferedReader b_out = null;
		try {
			File srcFile = new File(EgovWebUtil.filePathBlackList(src));
			if (srcFile.exists()) {

				// 접근권한 조회
				String parentPath = EgovWebUtil.filePathBlackList(srcFile.getParent());
				String fname = EgovWebUtil.filePathBlackList(srcFile.getName());

				Process p = null;
				if (Globals.OS_TYPE.equals("UNIX")) {
					String[] command = { EgovPropertiesUtil.getProperty(Globals.SHELL_FILE_PATH, "SHELL." + Globals.OS_TYPE + ".getDrctryAccess"),
							parentPath, fname };
					p = Runtime.getRuntime().exec(command);
					p.waitFor();
					//프로세스 에러시 종료
					if (p != null && p.exitValue() != 0) {
						b_err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
						while (b_err.ready()) {
							//String line = b_err.readLine();
							//if (line.length() <= MAX_STR_LEN) log.debug("ERR\n" + line);
						}
						b_err.close();
					}
					//프로세스 실행 성공시 결과 확인
					else {
						boolean result = false;
						b_out = new BufferedReader(new InputStreamReader(p.getInputStream()));
						while (b_out.ready()) {
							if (!result) { //result != true
								access = b_out.readLine();
								if (access != null && !"".equals(access) && access.length() <= MAX_STR_LEN) {
									result = true;
									break;
								}
							}
						}
						b_out.close();
					}
				} else if (Globals.OS_TYPE.equals("WINDOWS")) {
					String[] command = { "cmd", "/c", "attrib", src };
					p = Runtime.getRuntime().exec(command);
					p.waitFor();
					//프로세스 에러시 종료
					if (p != null && p.exitValue() != 0) {
						b_err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
						while (b_err.ready()) {
							//String line = b_err.readLine();
							//if (line.length() <= MAX_STR_LEN) log.debug("ERR\n" + line);
						}
						b_err.close();
					}
					//프로세스 실행 성공시 결과 확인
					else {
						boolean result = false;
						b_out = new BufferedReader(new InputStreamReader(p.getInputStream()));
						while (b_out.ready()) {
							if (!result) { //result != true
								access = b_out.readLine();
								if (access != null && !"".equals(access) && access.length() <= MAX_STR_LEN) {
									access = access.toUpperCase().replace(src.toUpperCase(), "");
									access = access.replace(" ", "");
									result = true;
									if (result) {
										break;
									}
								}
							}
						}
						b_out.close();

						if (result) {
							String acs = "";
							boolean read = false;
							boolean write = true;
							boolean system = false;
							boolean hidden = false;

							for (int i = 0; i < access.length(); i++) {
								char chr = access.charAt(i);
								switch (chr) {
									case ACCESS_READ:
										read = true;
										write = false;
										break;
									case ACCESS_SYS:
										system = true;
										break;
									case ACCESS_HIDE:
										hidden = true;
										break;
									default:
										break;
								}
							}

							if (read) {
								acs += "READ-ONLY|";
							} else {
								acs += "READ|";
							}
							if (write) {
								acs += "WRITE|";
							}
							if (system) {
								acs += "SYSTEM|";
							}
							if (hidden) {
								acs += "HIDDEN|";
							}
							access = acs;
						}
					}
				}

			}// else {
			//log.debug("+++ 지정된 위치가 올바르지 않습니다.");
			//}

		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			close(b_err);
			close(b_out);
		}

		return access;
	}

	/**
	 * 파일(디렉토리)의 사이즈를 조회하는 기능
	 *
	 * @return Long size 사이즈(Byte)
	 * @exception Exception
	 */
	public static long getSize(String file) throws Exception {

		long size = 0L;
		String src = file.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);

		try {
			File srcFile = new File(EgovWebUtil.filePathBlackList(src));
			if (srcFile.exists()) {
				size = srcFile.length();
			}// else {
			//log.debug("+++ 지정된 위치가 올바르지 않습니다.");
			//}

		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new RuntimeException(ex);
		}

		return size;
	}

	/**
	 * 파일(디렉토리)의 포맷을 조회하는 기능
	 *
	 * @return String format 포맷
	 * @exception Exception
	 */
	public static String getFormat(String file) throws Exception {

		// 포맷, 타입
		String format = "";
		String type = "";

		String src = file.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);

		try {
			File srcFile = new File(EgovWebUtil.filePathBlackList(src));
			if (srcFile.exists()) {

				String[] strArr = src.split("\\.");
				if (strArr.length >= 2) {
					format = strArr[strArr.length - 1].toLowerCase();
					type = EgovPropertiesUtil.getProperty(Globals.FILE_FORMAT_PATH, format);
				}

			}// else {
			//log.debug("+++ 지정된 위치가 올바르지 않습니다.");
			//}

		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new RuntimeException(ex);
		}

		return type;
	}

	/**
	 * <pre>
	 * Comment : 디렉토리를 복사한다.
	 * </pre>
	 *
	 * @return boolean result 복사가 성공하면 true, 실패하면 false를 리턴한다.
	 */
	public static boolean copyDirectory(String originalDirPath, String targetDirPath) throws Exception {

		// 인자값 유효하지 않은 경우 공백 리턴
		if (originalDirPath == null || originalDirPath.equals("") || targetDirPath == null || targetDirPath.equals("")) {
			return false;
		}
		boolean result = false;
		File f = null;
		try {
			f = new File(EgovWebUtil.filePathBlackList(originalDirPath));
			// 원본이 유효해야 진행한다.
			if (f.exists() && f.isDirectory()) {

				//타겟으로 설정한 경로가 유효한지 확인(중간경로에 파일명 이 포함되어있으면 유효하지 못하므로 진행안함.
				String targetDirPath1 = createNewDirectory(targetDirPath);
				if (targetDirPath1.equals("")) {
					result = false;
				} else {
					File targetDir = new File(EgovWebUtil.filePathBlackList(targetDirPath1));
					targetDir.mkdirs();
					// 디렉토리에 속한 파일들을 복사한다.
					String[] originalFileList = f.list();
					if (originalFileList.length > 0) {
						for (int i = 0; i < originalFileList.length; i++) {
							File subF = new File(EgovWebUtil.filePathBlackList(originalDirPath) + FILE_SEPARATOR + originalFileList[i]);
							if (subF.isFile()) {
								//하위목록이 파일이면 파일복사실행 -> 실패 발생하는 경우 복사를 중단한다.
								result = copyFile(originalDirPath + FILE_SEPARATOR + originalFileList[i], targetDir.getAbsolutePath()
										+ FILE_SEPARATOR + originalFileList[i]);
							} else {
								//하위목록이 디렉토리이면 복사를 재귀적으로 호출한다.
								result = copyDirectory(originalDirPath + "/" + originalFileList[i], targetDirPath1 + "/" + originalFileList[i]);
							}
						}
					} else {
						result = true;
					}
				}
			} else {
				// 원본자체가 유효하지 않은 경우는 false 리턴하고 종료
				result = false;
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		}

		return result;
	}

	/**
	 * <pre>
	 * Comment : 디렉토리를 복사한다. (생성일자 조건으로  복사)
	 * </pre>
	 *
	 * @param fromDate 디렉토리의 복사조건 시작일자
	 * @param toDate 디렉토리의 복사조건 종료일자
	 * @return boolean result 복사가 성공함변 true, 실패하면 false를 리턴한다.
	 */
	public static boolean copyDirectory(String originalDirPath, String targetDirPath, String fromDate, String toDate) throws Exception {

		// 인자값 유효하지 않은 경우 공백 리턴
		if (originalDirPath == null || originalDirPath.equals("") || targetDirPath == null || targetDirPath.equals("") || fromDate == null
				|| fromDate.equals("") || toDate == null || toDate.equals("")) {
			return false;
		}
		boolean result = false;
		File f = null;
		try {
			f = new File(EgovWebUtil.filePathBlackList(originalDirPath));
			boolean isInCondition = false;
			String lastModifyedDate = getLastModifiedDateFromFile(f);
			if (Integer.parseInt(lastModifyedDate) >= Integer.parseInt(fromDate) && Integer.parseInt(lastModifyedDate) <= Integer.parseInt(toDate)) {
				isInCondition = true;
			}

			// 원본이 유효하고 조건에 부합되야 진행한다.
			if (f.exists() && f.isDirectory() && isInCondition) {

				//타겟으로 설정한 경로가 유효한지 확인(중간경로에 파일명 이 포함되어있으면 유효하지 못하므로 진행안함.
				String targetDirPath1 = createNewDirectory(targetDirPath);
				if (targetDirPath1.equals("")) {
					result = false;
				} else {
					File targetDir = new File(EgovWebUtil.filePathBlackList(targetDirPath1));
					targetDir.mkdirs();
					// 디렉토리에 속한 파일들을 복사한다.
					String[] originalFileList = f.list();
					if (originalFileList.length > 0) {
						for (int i = 0; i < originalFileList.length; i++) {
							File subF = new File(EgovWebUtil.filePathBlackList(originalDirPath) + FILE_SEPARATOR + originalFileList[i]);
							if (subF.isFile()) {
								//하위목록이 파일이면 파일복사실행 -> 실패 발생하는 경우 복사를 중단한다.
								result = copyFile(originalDirPath + FILE_SEPARATOR + originalFileList[i], targetDir.getAbsolutePath()
										+ FILE_SEPARATOR + originalFileList[i]);
							} else {
								//하위목록이 디렉토리이면 복사를 재귀적으로 호출한다.
								//하위목록에 해당하는 폴더에 대해서는 생성일자 검사를 하지 않는다.(현재 폴더가 복사대상이면 현재폴더의 하위는 제외없이 복사함)
								result = copyDirectory(originalDirPath + "/" + originalFileList[i], targetDirPath1 + "/" + originalFileList[i]);
							}
						}
					} else {
						result = true;
					}
				}

			} else {
				// 원본자체가 유효하지 않은 경우는 false 리턴하고 종료
				result = false;
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		}

		return result;
	}

	/**
	 * <pre>
	 * Comment : 디렉토리를 복사한다. (생성자 조건으로복사)
	 * </pre>
	 *
	 * @return boolean result 복사가 성공함변 true, 실패하면 false를 리턴한다.
	 */
	public static boolean copyDirectory(String originalDirPath, String targetDirPath, String owner) throws Exception {

		// 인자값 유효하지 않은 경우 공백 리턴
		if (originalDirPath == null || originalDirPath.equals("") || targetDirPath == null || targetDirPath.equals("") || owner == null
				|| owner.equals("")) {
			return false;
		}
		boolean result = false;
		File f = null;
		try {
			f = new File(EgovWebUtil.filePathBlackList(originalDirPath));
			boolean isInCondition = false;
			String realOwner = getOwner(originalDirPath);
			if (realOwner.equals(owner)) {
				isInCondition = true;
			}

			// 원본이 유효하고 조건에 부합되야 진행한다.
			if (f.exists() && f.isDirectory() && isInCondition) {

				String targetDirPath1 = createNewDirectory(targetDirPath);
				if (targetDirPath1.equals("")) {
					//타겟으로 설정한 경로가 유효한지 확인(중간경로에 파일명 이 포함되어있으면 유효하지 못하므로 진행안함.
					result = false;
				} else {
					File targetDir = new File(EgovWebUtil.filePathBlackList(targetDirPath1));
					targetDir.mkdirs();
					// 디렉토리에 속한 파일들을 복사한다.
					String[] originalFileList = f.list();
					if (originalFileList.length > 0) {
						for (int i = 0; i < originalFileList.length; i++) {
							File subF = new File(EgovWebUtil.filePathBlackList(originalDirPath) + FILE_SEPARATOR + originalFileList[i]);
							if (subF.isFile()) {
								//하위목록이 파일이면 파일복사실행 -> 실패 발생하는 경우 복사를 중단한다.
								result = copyFile(originalDirPath + FILE_SEPARATOR + originalFileList[i], targetDir.getAbsolutePath()
										+ FILE_SEPARATOR + originalFileList[i]);
							} else {
								//하위목록이 디렉토리이면 복사를 재귀적으로 호출한다.
								//하위목록에 해당하는 폴더에 대해서는 생성일자 검사를 하지 않는다.(현재 폴더가 복사대상이면 현재폴더의 하위는 제외없이 복사함)
								result = copyDirectory(originalDirPath + "/" + originalFileList[i], targetDirPath1 + "/" + originalFileList[i]);
							}
						}
					} else {
						result = false;
					}
				}

			} else {
				// 원본자체가 유효하지 않은 경우는 false 리턴하고 종료
				result = false;
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		}

		return result;
	}

	/**
	 * 디렉토리의 사이즈를 조회한다.
	 *
	 * @return long size 디렉토리사이즈
	 * @exception Exception
	 */
	public static long getDirectorySize(String targetDirPath) throws Exception {

		File f = new File(EgovWebUtil.filePathBlackList(targetDirPath));
		if (!f.exists()) {
			return 0;
		}
		if (f.isFile()) {
			return f.length();
		}

		File[] list = f.listFiles();
		long size = 0;
		long fileSize = 0;

		for (int i = 0; i < list.length; i++) {

			if (list[i].isDirectory()) {
				// 디렉토리 안에 디렉토리면 그 안의 파일목록에서 찾도록 재귀호출한다.
				fileSize = getDirectorySize(list[i].getAbsolutePath());
			} else {
				// 파일의 사이즈 조회
				fileSize = list[i].length();
			}
			size = size + fileSize;
		}
		return size;
	}

	/**
	 * <pre>
	 * Comment : 디렉토리를 이동한다.
	 * </pre>
	 *
	 * @return boolean result 이동이 성공하면 true, 실패하면 false를 리턴한다.
	 */
	public static boolean moveFile(String originalDirPath, String targetDirPath) throws Exception {

		// 인자값 유효하지 않은 경우 공백 리턴
		if (originalDirPath == null || originalDirPath.equals("") || targetDirPath == null || targetDirPath.equals("")) {
			return false;
		}
		boolean result = false;
		File f = null;
		BufferedReader b_err = null;
		BufferedReader b_out = null;
		try {
			f = new File(EgovWebUtil.filePathBlackList(originalDirPath));
			// 원본은 유효하고 대상이 신규로 생성가능한 상태인경우만 진행한다.
			//if(f.exists() && f.isDirectory() ){ // 디렉토리만 이동할수 있도록 제한하는 경우
			if (f.exists()) {
				// 타겟으로 설정한 경로가 유효한지 확인(중간경로에 파일명 이 포함되어있으면 유효하지 못하므로 진행안함.
				File targetDir = new File(EgovWebUtil.filePathBlackList(targetDirPath));
				if (targetDir.exists()) {
					// 타겟경로가 이미 있는 경우는 종료
					result = false;
				} else {
					// 새로 생성되는 경우만 진행한다. (이동쉘을 실행시킨다.)
					String cmdStr = EgovPropertiesUtil.getProperty(Globals.SHELL_FILE_PATH, "SHELL." + Globals.OS_TYPE + ".moveDrctry");
					String[] command = { cmdStr.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR),
							EgovWebUtil.filePathBlackList(originalDirPath.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR)),
							EgovWebUtil.filePathBlackList(targetDirPath.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR)) };
					Process p = Runtime.getRuntime().exec(command);
					//String access = "";
					p.waitFor();
					//프로세스 에러시 종료
					if (p != null && p.exitValue() != 0) {
						b_err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
						while (b_err.ready()) {
							//String line = b_err.readLine();
							//if (line.length() <= MAX_STR_LEN) log.debug("ERR\n" + line);
						}
						b_err.close();
					}
					//프로세스 실행 성공시 결과 확인
					else {
						result = true;
					}
				}

			} else {
				// 원본자체가 유효하지 않은 경우는 false 리턴하고 종료
				result = false;
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			close(b_err);
			close(b_out);
		}

		return result;
	}

	/**
	 * <pre>
	 * Comment : 디렉토리를 이동한다. (생성일자 조건으로 이동)
	 * </pre>
	 *
	 * @param fromDate 디렉토리의이동조건 시작일자
	 * @param toDate 디렉토리의 이동조건 종료일자
	 * @return boolean result 이동이 성공하면 true, 실패하면 false를 리턴한다.
	 */
	public static boolean moveFile(String originalDirPath, String targetDirPath, String fromDate, String toDate) throws Exception {

		// 인자값 유효하지 않은 경우 공백 리턴
		if (originalDirPath == null || originalDirPath.equals("") || targetDirPath == null || targetDirPath.equals("") || fromDate == null
				|| fromDate.equals("") || toDate == null || toDate.equals("")) {
			return false;
		}
		boolean result = false;
		File f = null;
		BufferedReader b_err = null;
		BufferedReader b_out = null;
		try {
			f = new File(originalDirPath);
			// 원본은 유효하고 대상이 신규로 생성가능한 상태인경우만 진행한다.
			//if(f.exists() && f.isDirectory() ){ // 디렉토리만 이동할수 있도록 제한하는 경우
			if (f.exists()) {
				// 타겟으로 설정한 경로가 유효한지 확인(중간경로에 파일명 이 포함되어있으면 유효하지 못하므로 진행안함.
				File targetDir = new File(EgovWebUtil.filePathBlackList(targetDirPath));
				if (targetDir.exists()) {
					// 타겟경로가 이미 있는 경우는 종료
					result = false;
				} else {
					// 새로 생성되는 경우만 진행한다. (이동쉘을 실행시킨다.)
					boolean isInCondition = false;
					String lastModifyedDate = getLastModifiedDateFromFile(f);
					if (Integer.parseInt(lastModifyedDate) >= Integer.parseInt(fromDate)
							&& Integer.parseInt(lastModifyedDate) <= Integer.parseInt(toDate)) {
						isInCondition = true;
					}

					if (isInCondition) {
						String cmdStr = EgovPropertiesUtil.getProperty(Globals.SHELL_FILE_PATH, "SHELL." + Globals.OS_TYPE + ".moveDrctry");
						String[] command = { cmdStr.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR),
								EgovWebUtil.filePathBlackList(originalDirPath.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR)),
								EgovWebUtil.filePathBlackList(targetDirPath.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR)) };
						Process p = Runtime.getRuntime().exec(command);
						String access = "";
						p.waitFor();
						//프로세스 에러시 종료
						if (p != null && p.exitValue() != 0) {
							b_err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
							while (b_err.ready()) {
								//String line = b_err.readLine();
								//if (line.length() <= MAX_STR_LEN) log.debug("ERR\n" + line);
							}
							b_err.close();
						}
						//프로세스 실행 성공시 결과 확인
						else {
							result = false;
							b_out = new BufferedReader(new InputStreamReader(p.getInputStream()));
							while (b_out.ready()) {
								if (!result) { // result != true
									access = b_out.readLine();
									if (access != null && !"".equals(access) && access.length() <= MAX_STR_LEN) {
										result = true;
										break;
									}
								}
							}
							b_out.close();
						}
					}
				}

			} else {
				// 원본자체가 유효하지 않은 경우는 false 리턴하고 종료
				result = false;
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			close(b_err);
			close(b_out);
		}

		return result;
	}

	/**
	 * <pre>
	 * Comment : 디렉토리를 이동한다. (생성자 조건으로 이동)
	 * </pre>
	 *
	 * @return boolean result 이동이 성공하면 true, 실패하면 false를 리턴한다.
	 */
	public static boolean moveFile(String originalDirPath, String targetDirPath, String owner) throws Exception {

		// 인자값 유효하지 않은 경우 공백 리턴
		if (originalDirPath == null || originalDirPath.equals("") || targetDirPath == null || targetDirPath.equals("") || owner == null
				|| owner.equals("")) {
			return false;
		}
		//log.debug("originalDirPath:"+originalDirPath);
		//log.debug("targetDirPath:"+targetDirPath);
		boolean result = false;
		File f = null;
		BufferedReader b_err = null;
		BufferedReader b_out = null;
		try {
			f = new File(originalDirPath);
			// 원본은 유효하고 대상이 신규로 생성가능한 상태인경우만 진행한다.
			//if(f.exists() && f.isDirectory() ){ // 디렉토리만 이동할수 있도록 제한하는 경우
			if (f.exists()) {
				// 타겟으로 설정한 경로가 유효한지 확인(중간경로에 파일명 이 포함되어있으면 유효하지 못하므로 진행안함.
				File targetDir = new File(EgovWebUtil.filePathBlackList(targetDirPath));
				if (targetDir.exists()) {
					// 타겟경로가 이미 있는 경우는 종료
					result = false;
				} else {
					// 새로 생성되는 경우만 진행한다. (이동쉘을 실행시킨다.)
					boolean isInCondition = false;
					String realOwner = getOwner(originalDirPath);
					if (realOwner.equals(owner)) {
						isInCondition = true;
					}

					if (isInCondition) {
						String cmdStr = EgovPropertiesUtil.getProperty(Globals.SHELL_FILE_PATH, "SHELL." + Globals.OS_TYPE + ".moveDrctry");
						String[] command = { cmdStr.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR),
								EgovWebUtil.filePathBlackList(originalDirPath.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR)),
								EgovWebUtil.filePathBlackList(targetDirPath.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR)) };
						Process p = Runtime.getRuntime().exec(command);
						String access = "";
						p.waitFor();
						//프로세스 에러시 종료
						if (p != null && p.exitValue() != 0) {
							b_err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
							while (b_err.ready()) {
								//String line = b_err.readLine();
								//if (line.length() <= MAX_STR_LEN) log.debug("ERR\n" + line);
							}
							b_err.close();
						}
						//프로세스 실행 성공시 결과 확인
						else {
							result = false;
							b_out = new BufferedReader(new InputStreamReader(p.getInputStream()));
							while (b_out.ready()) {

								if (!result) { //result != true
									access = b_out.readLine();
									if (access != null && !"".equals(access) && access.length() <= MAX_STR_LEN) {
										result = true;
										if (result) {
											break;
										}
									}
								}
							}
							b_out.close();
						}
					}
				}
			} else {
				// 원본자체가 유효하지 않은 경우는 false 리턴하고 종료
				result = false;
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			close(b_err);
			close(b_out);
		}

		return result;
	}

	/**
	 * <pre>
	 * Comment : 디렉토리감시를 시작한다. 모니터링 시작시 해당 디렉토리의 이름으로 생성된 로그파일에 START기입하고 종료시END기입한다.
	 *           (로그파일이 이미 존재하는 경우는 모니터링이 현재 진행중인 상태이므로 새로 감시기능을 시작하지 않는다.)
	 * </pre>
	 *
	 * @return boolean result 모니터링 시작 여부를 리턴한다. (모니터링 시작했으면 true, 모니터링이 시작되지
	 *         않았으면 false)
	 */
	public static boolean startDirectoryMonitering(String targetDirPath) throws Exception {

		// 인자값 유효하지 않은 경우 false 리턴
		if (targetDirPath == null || targetDirPath.equals("")) {
			return false;
		}

		boolean result = false;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			// 로그파일을 생성한다. 만약 로그파일이 존재하면 이미 감시 프로세스가 동작중이므로 새로 시작하지 않는다.

			File targetF = new File(EgovWebUtil.filePathBlackList(targetDirPath));
			File logF = new File(EgovWebUtil.filePathBlackList(Globals.CONF_PATH) + "/" + targetF.getName() + ".log");

			if (targetF.exists() && targetF.isDirectory()) {

				if (logF.exists()) {
					// 이미 감시 프로세스 동작중임
					result = true;
					//로그파일에서 중단여부 확인하여 중단된 상태이면 재실행함
					String lastStr = "";
					fr = new FileReader(logF);
					br = new BufferedReader(fr);
					//int ch = 0;
					String line = "";
					while ((line = br.readLine()) != null) {
						if (line.length() < MAX_STR_LEN)
							lastStr = line;
					}
					//log.debug("lastStr:"+lastStr);
					if (lastStr.equals("END")) {
						EgovFileMntrg t = new EgovFileMntrg(EgovWebUtil.filePathBlackList(targetDirPath), logF);
						t.start();
					}
				} else {
					result = logF.createNewFile();
					EgovFileMntrg t = new EgovFileMntrg(targetDirPath, logF);
					t.start();
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			close(fr);
			close(br);
		}

		return result;
	}

	/**
	 * <pre>
	 * Comment : 디렉토리감시를 종료한다. 모니터링 시작시 해당 디렉토리의 이름으로 생성된 로그파일에 START기입하고 종료시END기입한다.
	 *           (로그파일이 존재하지 않는 경우는  모니터링이 아직 시작되지 않은 상태이므로별도로   종료하지 않는다.)
	 * </pre>
	 *
	 * @return boolean result 모니터링 시작 여부를 리턴한다. (모니터링 시작했으면 true, 모니터링이 시작되지
	 *         않았으면 false)
	 */
	public static boolean stopDirectoryMonitering(String targetDirPath) throws Exception {

		// 인자값 유효하지 않은 경우 false 리턴
		if (targetDirPath == null || targetDirPath.equals("")) {
			return false;
		}

		boolean result = false;
		FileReader fr = null;
		BufferedReader br = null;
		FileWriter fWriter = null;
		BufferedWriter bWriter = null;
		try {
			File targetF = new File(EgovWebUtil.filePathBlackList(targetDirPath));
			File logF = new File(EgovWebUtil.filePathBlackList(Globals.CONF_PATH) + "/" + targetF.getName() + ".log");
			if (logF.exists()) {

				//로그파일 최종라인 확인 : END 여부 확인
				String lastStr = "";
				fr = new FileReader(logF);
				br = new BufferedReader(fr);
				//int ch = 0;
				String line = "";
				while ((line = br.readLine()) != null) {
					if (line.length() < MAX_STR_LEN)
						lastStr = line;
				}
				fr.close();
				br.close();

				//        		if(lastStr.equals("END")){
				//        			// 로그파일이 존재하고 이미 종료요청이 된 상태이므로 작업없음
				//        			//log.debug("Already Ending Requested Status");
				//        		}else{
				if (!lastStr.equals("END")) {
					fWriter = new FileWriter(logF, true);
					bWriter = new BufferedWriter(fWriter);
					br = new BufferedReader(new StringReader("END"));
					while ((line = br.readLine()) != null && !lastStr.equals("END")) {
						if (line.length() < MAX_STR_LEN) {
							bWriter.write(line + "\n", 0, line.length() + 1);
						}
					}
					br.close();
					bWriter.close();
					fWriter.close();
				}
				result = true;
			} else {
				result = false;
			}
	    /* 파일을 삭제하면 처리하도록 변경는 경우 참조
	    File targetF = new File (targetDirPath);
	    File logF = new File(Globals.CONF_PATH+"/"+targetF.getName()+".log");
	    log.debug(logF.getAbsolutePath());
	    if(logF.exists()){
	    	log.debug("EXISTS");
	    	// 감시 프로세스 동작중임
	    	result = logF.delete();
	    	log.debug(result);
	    }else{
	    	// 이미 감시 프로세스 중단됨
	    	log.debug("NOT EXISTS");
	    	result = true;
	    }
	    */
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			close(fr);	// 2012.11 KISA 보안조치
			close(br);	// 2012.11 KISA 보안조치
			close(fWriter);	// 2012.11 KISA 보안조치
			close(bWriter);	// 2012.11 KISA 보안조치
		}

		return result;
	}

	/**
	 * <pre>
	 * Comment : 디렉토리감시정보를 로그파일로부터 읽어온다.
	 * </pre>
	 *
	 * @return ArrayList result 로그파일의 정보를 라인단위로 담아서 리턴한다.
	 */
	public static StringBuffer getDirectoryMoniteringInfo(String targetDirPath) throws Exception {

		// 인자값 유효하지 않은 경우 빈 ArrayList 리턴
		if (targetDirPath == null || targetDirPath.equals("")) {
			return new StringBuffer();
		}

		StringBuffer result = new StringBuffer();
		FileReader fr = null;
		try {
			File targetF = new File(EgovWebUtil.filePathBlackList(targetDirPath));
			File logF = new File(EgovWebUtil.filePathBlackList(Globals.CONF_PATH) + "/" + targetF.getName() + ".log");
			if (!logF.exists()) {
				result = new StringBuffer();
			}
			fr = new FileReader(logF);
			int ch = 0;
			while ((ch = fr.read()) != -1) {
				result.append((char)ch);
			}
			fr.close();
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			close(fr);
		}

		return result;
	}

	/**
	 * InputStream, Reader, Writer close 처리 (finally 부분에서 호출되어야 함).
	 *
	 * @param closable
	 */
	protected static void close(Closeable closable) {
		if (closable != null) {
			try {
				closable.close();
			} catch (IOException ignore) {
				System.out.println("IGNORE: " + ignore);
			}
		}
	}
}