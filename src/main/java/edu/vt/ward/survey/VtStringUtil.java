/**
 *     수정일         수정자                   수정내용
 *    -------       --------      ---------------------------
 *
 */

package edu.vt.ward.survey;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;




/**
 * <pre>
 * 문자열 유틸리티
 * 현재 기존개발된 edi의 string utility를 사용
 * </pre>
 *
 * @version 2008. 09. 15
 * @author 김창교
 */
public class VtStringUtil  {
	 /**
	  * 파일의 확장자를 가져온다.
	  * @return 파일의 확장자
	  */
	public static String uniToKsc(String uni) throws UnsupportedEncodingException{
        String sTemp="";
        char a = 44032;
		return sTemp;
	 }
	public static String  kscToUni(String uni) throws UnsupportedEncodingException{
        String sTemp="";
        sTemp=uni.getBytes("KSC5601").toString();
		return sTemp;
		 
	 }
	
	
}
