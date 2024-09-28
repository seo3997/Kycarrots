package com.whomade.kycarrots.framework.common.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.whomade.kycarrots.mgt.survey.vo.TbSurveyUserEntryResult;
import edu.vt.ward.survey.StringExt;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.whomade.kycarrots.framework.common.object.DataMap;


public class StringUtil {
	
	/**
	*
	* @param src
	* @param defaultValue
	* @return String
	*/
	public static String nvl(String src, String defaultValue) {
		return (src == null || "".equals(src.trim())) ? defaultValue : src;
	}

	/**
	*
	* @param src
	* @return String
	*/
	public static String nvl(String src) {
		return nvl(src, "");
	}
	
	/**
	 * 문자 길이 자르기
	 *
	 * @param contents
	 * @param length
	 * @return String
	 */
	public static String getReSize(String contents, int length) throws Exception {

		if (contents != null && contents.length() > length) {
			contents = contents.substring(0, length) + "...";
		}

		return contents;
	}
	
	/**
	 * 문자 길이 자르기 (말줄임표 없음)
	 *
	 * @param contents
	 * @param length
	 * @return String
	 */
	public static String getReSizeRemoveDot(String contents, int length) throws Exception {

		if (contents != null && contents.length() > length) {
			contents = contents.substring(0, length);
		}

		return contents;
	}

	public static String toEng(String str) {
		if (str == null)
			return null;
		try {
			return new String(str.getBytes("KSC5601"), "8859_1");
		} catch (UnsupportedEncodingException e) {

			return str;
		}
	}

	public static String toKor(String input) {

		String str = "";
		try {
			str = new String(input.getBytes("8859_1"), "KSC5601");
		} catch (UnsupportedEncodingException e) {

		}

		return str;
	}

	/**
	 * 개행문자 처리
	 *
	 * @param strOriData (개행처리할 문자열)
	 * @return String (개행처리된 문자열)
	 */
	public static String getHtmlValue(String strOriData) {
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < strOriData.length(); i++) {
			//System.out.println("$$$$$:"+strOriData.charAt(i)+":"+String.valueOf(strOriData.charAt(i)).hashCode());
			if (String.valueOf(strOriData.charAt(i)).hashCode() == 10 || String.valueOf(strOriData.charAt(i)).hashCode() == 13) {
				buffer.append("<br/>");
			} else {
				buffer.append(String.valueOf(strOriData.charAt(i)));
			}
		}
		return buffer.toString();
	}

	/**
	 * 전자결재를 위한 개행문자 처리 & 특수문자 처리
	 *
	 * @param strOriData (개행처리할 문자열)
	 * @return String (개행처리된 문자열)
	 */
	public static String newLineForEapprov(String strOriData) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < strOriData.length(); i++) {

			int intCode = String.valueOf(strOriData.charAt(i)).hashCode();
			if (intCode == 10 || intCode == 13) {
				buffer.append("\\\\r\\\\n");
				i++;
			} else if (intCode == 34) // ["]
			{
				buffer.append("\\\"");
			} else if (intCode == 39 || intCode == 44) // [']
			{
				buffer.append("\\\'");
			} else {
				buffer.append(String.valueOf(strOriData.charAt(i)));
			}
		}
		String strReturn = buffer.toString();

		return strReturn;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : toHtmlFormatDataMap
	 * 2. ClassName  : StringUtil
	 * 3. Comment   : dataMap 에서 사용하는 htmlTagFilterRestore 를 반대로 적용한것
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2014. 8. 12. 오전 11:25:08
	 * </PRE>
	 *   @return String
	 *   @param src
	 *   @return
	 */
	public static String toHtmlFormatDataMap(String src) {

		if (src != null) {
			src = src.replaceAll("&", "&amp;");
			src = src.replaceAll("<", "&lt;");
			src = src.replaceAll(">", "&gt;");
			src = src.replaceAll("\"", "&quot;");
			src = src.replaceAll("\'", "&#039;");
		}
		return src;
	}
	
	/**
	 * HTHML태그들을 브라우져에 표현되도록 인코딩하는 기능
	 * @return String
	 */
	public static String toHtmlFormat(String src) {

		if (src != null) {
			src = src.replaceAll("&", "&amp;");
			src = src.replaceAll("<", "&lt;");
			src = src.replaceAll(">", "&gt;");
			src = src.replaceAll("\n", "<br/>");
			src = src.replaceAll(" ", "&nbsp;");
			/* 2010.03.02 by SooHyun.Seo
			src = src.replaceAll("·","&#8228;");
			src = src.replaceAll("「","&#65378;");
			src = src.replaceAll("」","&#65379;");
			src = src.replaceAll("ㆍ","&#9702;");
			src = src.replaceAll("⇒","&#8680;");
			src = src.replaceAll("●","&#8226;");
			src = src.replaceAll("▶","&#983803;");
			src = src.replaceAll("①","&#983729;");
			src = src.replaceAll("②","&#983730;");
			src = src.replaceAll("③","&#983731;");
			src = src.replaceAll("④","&#983732;");
			src = src.replaceAll("⑤","&#983733;");
			src = src.replaceAll("⑥","&#983734;");
			src = src.replaceAll("⑦","&#983735;");
			src = src.replaceAll("⑧","&#983736;");
			src = src.replaceAll("⑨","&#983737;");
			//src = src.replaceAll("○","&#61549;");
			src = src.replaceAll("\"","&#034;");
			src = src.replaceAll("\'","&#039;");
			*/
		}
		return src;
	}

	/**
	 * HTHML태그들을 브라우져에 표현되도록 인코딩하는 기능
	 * @return String
	 */
	public static String toHtmlFormat_AZ(String src) {
		src = src.replaceAll("&", "&amp;");
		src = src.replaceAll("<", "&lt;");
		src = src.replaceAll(">", "&gt;");
		src = src.replaceAll("\n", "<br/>");
		src = src.replaceAll(" ", "&nbsp;");
		src = src.replaceAll("·", "&#8228;");
		src = src.replaceAll("「", "&#65378;");
		src = src.replaceAll("」", "&#65379;");
		src = src.replaceAll("ㆍ", "&#9702;");
		src = src.replaceAll("⇒", "&#8680;");
		src = src.replaceAll("●", "&#8226;");
		src = src.replaceAll("▶", "&#983803;");
		src = src.replaceAll("①", "&#983729;");
		src = src.replaceAll("②", "&#983730;");
		src = src.replaceAll("③", "&#983731;");
		src = src.replaceAll("④", "&#983732;");
		src = src.replaceAll("⑤", "&#983733;");
		src = src.replaceAll("⑥", "&#983734;");
		src = src.replaceAll("⑦", "&#983735;");
		src = src.replaceAll("⑧", "&#983736;");
		src = src.replaceAll("⑨", "&#983737;");
		//src = src.replaceAll("○","&#61549;");
		src = src.replaceAll("\"", "&#034;");
		src = src.replaceAll("\'", "&#039;");
		return src;
	}

	/**
	 * HTHML태그들을 브라우져에 표현되도록 인코딩하는 기능
	 * (한글에디터에서 사용)
	 */
	public static String toHtmlFormat_hwp(String src) {
		src = src.replaceAll("&amp;", "&");
		src = src.replaceAll("&lt;", "<");
		src = src.replaceAll("&gt;", ">");
		src = src.replaceAll("<br/>", "\n");
		src = src.replaceAll("&nbsp;", " ");
		src = src.replaceAll("&#8228;", "·");
		src = src.replaceAll("&#65378;", "「");
		src = src.replaceAll("&#65379;", "」");
		src = src.replaceAll("&#9702;", "ㆍ");
		src = src.replaceAll("&#8680;", "⇒");
		src = src.replaceAll("&#8226;", "●");
		src = src.replaceAll("&#983803;", "▶");
		src = src.replaceAll("&#983729;", "①");
		src = src.replaceAll("&#983730;", "②");
		src = src.replaceAll("&#983731;", "③");
		src = src.replaceAll("&#983732;", "④");
		src = src.replaceAll("&#983733;", "⑤");
		src = src.replaceAll("&#983734;", "⑥");
		src = src.replaceAll("&#983735;", "⑦");
		src = src.replaceAll("&#983736;", "⑧");
		src = src.replaceAll("&#983737;", "⑨");
		src = src.replaceAll("&#61549;", "○");
		src = src.replaceAll("\"", "&#034;");
		src = src.replaceAll("\'", "&#039;");
		return src;
	}

	/**
	 * HTHML태그들을 브라우져에 표현되도록 인코딩하는 기능
	 * (한글에디터에서 사용)
	 */
	public static String toHtmlFormat_forLawNm(String src) {
		src = src.replaceAll("\"", "&#034;");
		src = src.replaceAll("\'", "&#039;");
		return src;
	}

	/**
	 * HTHML태그들을 브라우져에 표현되도록 인코딩하는 기능(상세조회화면)
	 * 특수문자 다시 처리(안맞는 부분 있는 듯함)
	 * @return String
	 */
	public static String toHtmlFormat2(String src) {
		//특수문자 처리 후
		src = src.replaceAll("&#8228;", "·");
		src = src.replaceAll("&#65378;", "「");
		src = src.replaceAll("&#65379;", "」");
		src = src.replaceAll("&#9702;", "ㆍ");
		src = src.replaceAll("&#8680;", "⇒");
		src = src.replaceAll("&#8226;", "●");
		src = src.replaceAll("&#983803;", "▶");
		src = src.replaceAll("&#983729;", "①");
		src = src.replaceAll("&#983730;", "②");
		src = src.replaceAll("&#983731;", "③");
		src = src.replaceAll("&#983732;", "④");
		src = src.replaceAll("&#983733;", "⑤");
		src = src.replaceAll("&#983734;", "⑥");
		src = src.replaceAll("&#983735;", "⑦");
		src = src.replaceAll("&#983736;", "⑧");
		src = src.replaceAll("&#983737;", "⑨");
		src = src.replaceAll("&#61549;", "○");
		src = src.replaceAll("&#8718;", "·");

		//html태그처리 해야함
		src = src.replaceAll("&", "&amp;");
		src = src.replaceAll("<", "&lt;");
		src = src.replaceAll(">", "&gt;");
		src = src.replaceAll("\n", "<br/>");
		src = src.replaceAll(" ", "&nbsp;");
		return src;
	}

	/**
	 * HTHML태그들을 브라우져에 표현되도록 인코딩하는 기능(상세조회화면)
	 * 특수문자 다시 처리(안맞는 부분 있는 듯함)
	 * @return String
	 */
	public static String toHtmlFormat3(String src) {
		//특수문자 처리 후
		src = src.replaceAll("&#8228;", "·");
		src = src.replaceAll("&#65378;", "「");
		src = src.replaceAll("&#65379;", "」");
		src = src.replaceAll("&#9702;", "ㆍ");
		src = src.replaceAll("&#8680;", "⇒");
		src = src.replaceAll("&#8226;", "●");
		src = src.replaceAll("&#983803;", "▶");
		src = src.replaceAll("&#983729;", "①");
		src = src.replaceAll("&#983730;", "②");
		src = src.replaceAll("&#983731;", "③");
		src = src.replaceAll("&#983732;", "④");
		src = src.replaceAll("&#983733;", "⑤");
		src = src.replaceAll("&#983734;", "⑥");
		src = src.replaceAll("&#983735;", "⑦");
		src = src.replaceAll("&#983736;", "⑧");
		src = src.replaceAll("&#983737;", "⑨");
		src = src.replaceAll("&#61549;", "○");
		src = src.replaceAll("&#8718;", "·");

		return src;
	}

	/**
	 * HTML태그들을 복원시키는 기능
	 * @return String
	 */
	public static String toRawFormat(String src) {
		src = src.replaceAll("&amp;", "&");
		src = src.replaceAll("&lt;", "<");
		src = src.replaceAll("&gt;", ">");
		src = src.replaceAll("<br/>", "\n");
		src = src.replaceAll("&nbsp;", " ");
		src = src.replaceAll("&#8228;", "·");
		src = src.replaceAll("&#65378;", "「");
		src = src.replaceAll("&#65379;", "」");
		src = src.replaceAll("&#9702;", "ㆍ");
		src = src.replaceAll("&#8680;", "⇒");
		src = src.replaceAll("&#8226;", "●");
		src = src.replaceAll("&#983803;", "▶");
		src = src.replaceAll("&#983729;", "①");
		src = src.replaceAll("&#983730;", "②");
		src = src.replaceAll("&#983731;", "③");
		src = src.replaceAll("&#983732;", "④");
		src = src.replaceAll("&#983733;", "⑤");
		src = src.replaceAll("&#983734;", "⑥");
		src = src.replaceAll("&#983735;", "⑦");
		src = src.replaceAll("&#983736;", "⑧");
		src = src.replaceAll("&#983737;", "⑨");
		src = src.replaceAll("&#61549;", "○");
		src = src.replaceAll("&#034;", "\"");
		src = src.replaceAll("&#039;", "\'");
		return src;
	}
	
	/**
	 *<pre>엔터를 <BR>태그로 변환한다. young^^ </pre>
	 *@param inStr                변환할 문자열
	 *@return String
	 */
	public static String putsBR(String inStr) {
		if (inStr == null) {
			return "";
		}
		int length = inStr.length();
		StringBuffer buffer = new StringBuffer();

		String comp, comp2, comp3 = "";
		String bef_comp = "";

		int lot = 0;
		for (int i = 0; i < length; ++i) {
			comp = inStr.substring(i, i + 1);

			if ("<".compareTo(comp) == 0) {
				if (i < length - 8) {
					comp3 = inStr.substring(i, i + 6);
					if ("<TABLE".compareTo(comp3.toUpperCase()) == 0) {
						comp2 = inStr.substring(i, inStr.length());
						lot = comp2.toUpperCase().indexOf("</TABLE>");
						buffer.append(inStr.substring(i, i + lot + 8));
						i = i + lot + 8;
					} else {
						buffer.append(comp);
					}
				} else {
					buffer.append(comp);
				}

			} else {
				if ("\r".compareTo(comp) == 0) {
					if (i < length - 1) {
						comp = inStr.substring(++i, i + 1);
						if ("\n".compareTo(comp) == 0) {
							buffer.append("<BR/>");
						} else {
							buffer.append("<BR/>");
						}
					} else
						buffer.append("<BR/>");
				} else if ("\n".compareTo(comp) == 0) {
					boolean check = true;
					if (i > 9) {
						comp2 = inStr.substring(i - 9, i - 1);

						if ("</TABLE>".compareTo(comp2.toUpperCase()) == 0) {
							check = false;
						}
					}

					if (check) {
						if ("\r".compareTo(bef_comp) != 0)
							buffer.append("<BR/>");
					}
				}

				bef_comp = comp;
				buffer.append(comp);
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 엔터제거 기능
	 * @return String
	 */
	public static String convertHtmlTags(String s) {
		s = s.replaceAll("<[^>]*>", ""); //정규식 태그삭제
		s = s.replaceAll("\r\n", "<br/>"); //엔터제거
		s = s.replaceAll("\n", "<br/>"); //엔터제거
		return s;
	}

	/**
	 * 화면에 표시되는 script, embed, iframe태그를 무효화 시키는 기능
	 * @return String
	 */
	public static String toSafeHtmlFormat(String src) {
		StringBuffer sb = new StringBuffer(src);
		String srcLower = src.toLowerCase();
		int appendix = 0;

		for (int i = 0; i < srcLower.length(); i++) {
			int begin = srcLower.indexOf("<", i);
			int end = 0;

			if (-1 != begin) {
				end = srcLower.indexOf(">", i + 1);
				if (-1 != end) {
					String temp = srcLower.substring(begin, end + 1);
					if (-1 != temp.indexOf("script") || -1 != temp.indexOf("embed") || -1 != temp.indexOf("iframe")) {
						sb.insert(begin + appendix + 1, "!--");
						appendix = appendix + 3;

						sb.insert(end + appendix, "--");
						appendix = appendix + 2;
					}
					i = end;
				}
			}
		}

		return sb.toString();
	}
	
	/**
	 * 대상문자열(strTarget)에서 지정문자열(strSearch)이 검색된 횟수를,
	 * 지정문자열이 없으면 0 을 반환한다.
	 *
	 * @param strTarget 대상문자열
	 * @param strSearch 검색할 문자열
	 * @return 지정문자열이 검색되었으면 검색된 횟수를, 검색되지 않았으면 0 을 반환한다.
	 */
	public static int search(String strTarget, String strSearch) throws Exception {
		int result = 0;
		try {

			String strCheck = new String(strTarget);
			for (int i = 0; i < strTarget.length();) {
				int loc = strCheck.indexOf(strSearch);
				if (loc == -1) {
					break;
				} else {
					result++;
					i = loc + strSearch.length();
					strCheck = strCheck.substring(i);
				}
			}

		} catch (Exception e) {

		}
		return result;
	}

	/*
	 * 특수문자를 제거한다
	 */
	public static String specialCharRemove(String str) {

		String strImsi = "";

		String[] filterWord = { " ", "\\`", "\\,", "\\.", "\\?", "\\~", "\\!", "\\@", "\\#", "\\$", "\\%", "\\^", "\\&", "\\*", "\\(", "\\)", "\\-", "\\_", "\\+", "\\=", "\\|",
				"\\\\", "\\}", "\\]", "\\{", "\\[", "\\\"", "\\'", "\\:", "\\;", "\\<", "\\>", "\\.", "\\?", "\\/" };

		for (int i = 0; i < filterWord.length; i++) {

			strImsi = str.replaceAll(filterWord[i], "");
			str = strImsi;
		}

		return str;
	}

	/*
	 * 쿼리관련 특수문자를 제거한다
	 */
	public static String queryCharRemove(String str) {

		String strImsi = "";

		String[] filterWord = { "\\`", "\\'", "\\/", "\\\\", "\\;", "\\:", "\\-", "\\+", "\\=", "\\’" };

		for (int i = 0; i < filterWord.length; i++) {

			strImsi = str.replaceAll(filterWord[i], "");
			str = strImsi;
		}

		return str;
	}

	/*
	 * Byte[] 를 String 으로 변환한다
	 */
	public static String convByteToString(byte[] byteSrc, String addStringValue) {

		//byte 를 String 에 담기
		String stringSrc = "";
		for (int i = 0; i < byteSrc.length; i++) {

			if (i != 0) {
				stringSrc += addStringValue;
			}

			stringSrc += Integer.toString(byteSrc[i]);
		}

		return stringSrc;
	}

	/*
	 * String 을 Byte[] 로 변환한다
	 */
	public static byte[] convStringToByte(String stringSrc, String cutStringValue) {

		String[] byteSrc = stringSrc.split(cutStringValue);

		byte[] byteArray = new byte[byteSrc.length];

		for (int i = 0; i < byteSrc.length; i++) {

			byteArray[i] = (byte) Integer.parseInt(byteSrc[i]);
		}

		return byteArray;
	}

	
	/**
	 * <PRE>
	 * 1. MethodName : removeTag
	 * 2. ClassName  : StringUtil
	 * 3. Comment   : HTML TAG 제거
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 31. 오후 4:12:41
	 * </PRE>
	 *   @return String
	 *   @param str
	 *   @param rmTag
	 *   @return
	 */
	public static String removeTag(String str, String rmTag) {

		int lt = str.toUpperCase().indexOf(rmTag.toUpperCase());

		if (lt != -1) {

			int gt = str.indexOf(">", lt);
			if ((gt != -1)) {

				str = str.substring(0, lt) + str.substring(gt + 1);

				// 재귀 호출
				str = removeTag(str, rmTag);
			}
		}
		return str;
	}

	/**
	 * <PRE>
	 * 1. MethodName : changeWidthTag
	 * 2. ClassName  : StringUtil
	 * 3. Comment   : Contents 에서 Img 태그를 조회하여 Img 태그의 width 를 100% 로 변경
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 31. 오후 4:11:13
	 * </PRE>
	 *   @return String
	 *   @param str
	 *   @return
	 */
	public static String changeWidthTag(String str) {

		int lt = str.toUpperCase().indexOf("<IMG");
		String frontData = "";
		String endData = "";
		String imgData = "";

		//System.out.println("str:"+str);	

		if (lt != -1) {

			int gt = str.indexOf(">", lt);

			if ((gt != -1)) {

				frontData = str.substring(0, lt);
				endData = str.substring(gt + 1);
				imgData = "<#" + str.substring(lt + 2, str.indexOf(">", lt) + 1);

				int imgFrontNo = imgData.toUpperCase().indexOf("WIDTH=\"");

				if (imgFrontNo != -1) {

					int imgEndNo = imgData.indexOf("\"", imgFrontNo + 7);

					//System.out.println("widthFrontNo:"+imgFrontNo);
					//System.out.println("widthEndNo:"+imgEndNo);	

					String imgFrontData = imgData.substring(0, imgFrontNo);
					String imgEndData = imgData.substring(imgEndNo + 1);
					String widthData = imgData.substring(imgFrontNo, imgEndNo + 1);

					//System.out.println(imgFrontData);
					//System.out.println(imgEndData);
					//System.out.println(widthData);

					int widthFrontNo = widthData.toUpperCase().indexOf("\"");

					if (widthFrontNo != -1) {

						int widthEndNo = widthData.indexOf("\"", widthFrontNo + 1);

						//System.out.println(widthEndNo);

						String widthFrontData = widthData.substring(0, widthFrontNo + 1);
						String widthEndData = widthData.substring(widthEndNo);
						String sizeData = "100%";

						widthData = widthFrontData + sizeData + widthEndData;

						//System.out.println(widthFrontData);
						//System.out.println(widthEndData);
						//System.out.println(sizeData);
						//System.out.println(widthData);
					}

					imgData = imgFrontData + widthData + imgEndData;
					//System.out.println(imgData);
				} else {
					imgData = "<#" + str.substring(lt + 2, str.indexOf(">", lt) - 1) + " width=\"100%\" />";
				}

				str = frontData + imgData + endData;
				//System.out.println(str);	        	

				// 재귀 호출
				str = changeWidthTag(str);
			}
		} else {

			str = str.replaceAll("#mg", "img");
			str = str.replaceAll("#MG", "IMG");
		}
		return str;
	}

	/**
	 * <PRE>
	 * 1. MethodName : imgTagExtract
	 * 2. ClassName  : StringUtil
	 * 3. Comment   : 실제 이미지 URL 만 추출
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 31. 오후 4:11:20
	 * </PRE>
	 *   @return String
	 *   @param str
	 *   @return
	 */
	public static String imgTagExtract(String str) {

		int lt = str.toUpperCase().indexOf("<IMG");

		//System.out.println("before str:"+str);	

		if (lt != -1) {

			int gt = str.indexOf(">", lt);

			if ((gt != -1)) {

				str = str.substring(lt, gt + 1);
				//System.out.println("\n>>>>>>>>>>before src lt : "+ str);
				lt = str.indexOf("src=\"");
				str = str.substring(lt + 5);
				//System.out.println("\n>>>>>>>>>>after src lt : "+ Integer.toString(lt) + "\n str : " + str);
				if (lt != -1) {
					gt = str.indexOf("\"");
					//System.out.println("\n>>>>>>>>>>after src gt : "+ Integer.toString(gt));
					if (gt != -1) {
						str = str.substring(0, gt);
					}
				}
			}
		} else {

			str = str.replaceAll("#mg", "img");
			str = str.replaceAll("#MG", "IMG");
		}

		//System.out.println("\n>>>>>>>>>>after str: "+str);

		return str;
	}

	/**
	 * <PRE>
	 * 1. MethodName : changeHeightTag
	 * 2. ClassName  : StringUtil
	 * 3. Comment   :  Contents 에서 Img 태그를 조회하여 Img 태그의 height 를 auto 로 변경
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 31. 오후 4:11:33
	 * </PRE>
	 *   @return String
	 *   @param str
	 *   @return
	 */
	public static String changeHeightTag(String str) {

		int lt = str.toUpperCase().indexOf("<IMG");
		String frontData = "";
		String endData = "";
		String imgData = "";

		//System.out.println("str:"+str);

		if (lt != -1) {

			int gt = str.indexOf(">", lt);

			if ((gt != -1)) {

				frontData = str.substring(0, lt);
				endData = str.substring(gt + 1);
				imgData = "<#" + str.substring(lt + 2, str.indexOf(">", lt) + 1);

				int imgFrontNo = imgData.toUpperCase().indexOf("HEIGHT=\"");

				if (imgFrontNo != -1) {

					int imgEndNo = imgData.indexOf("\"", imgFrontNo + 8);

					//System.out.println("widthFrontNo:"+imgFrontNo);
					//System.out.println("widthEndNo:"+imgEndNo);	

					String imgFrontData = imgData.substring(0, imgFrontNo);
					String imgEndData = imgData.substring(imgEndNo + 1);
					String widthData = imgData.substring(imgFrontNo, imgEndNo + 1);

					//System.out.println("imgFrontData:"+imgFrontData);
					//System.out.println("widthData"+widthData);
					//System.out.println("imgEndData:"+imgEndData);

					int widthFrontNo = widthData.toUpperCase().indexOf("\"");

					if (widthFrontNo != -1) {

						int widthEndNo = widthData.indexOf("\"", widthFrontNo + 1);

						//System.out.println(widthEndNo);

						String widthFrontData = widthData.substring(0, widthFrontNo + 1);
						String widthEndData = widthData.substring(widthEndNo);
						String sizeData = "auto";

						widthData = widthFrontData + sizeData + widthEndData;

						//System.out.println(widthFrontData);
						//System.out.println(widthEndData);
						//System.out.println(sizeData);
						//System.out.println(widthData);
					}

					imgData = imgFrontData + widthData + imgEndData;
					//System.out.println(imgData);
				} else {
					imgData = "<#" + str.substring(lt + 2, str.indexOf(">", lt) - 1) + " height=\"auto\" />";
				}

				str = frontData + imgData + endData;
				//System.out.println(str);

				/*
				System.out.println(frontData);
				System.out.println(endData);
				System.out.println(imgData);
				*/

				// 재귀 호출
				str = changeHeightTag(str);
			}
		} else {

			str = str.replaceAll("#mg", "img");
			str = str.replaceAll("#MG", "IMG");
		}
		return str;
	}
	
	/**
     * <p>오라클의 decode 함수와 동일한 기능을 가진 메서드이다.
     * <code>sourStr</code>과 <code>compareStr</code>의 값이 같으면
     * <code>returStr</code>을 반환하며, 다르면  <code>defaultStr</code>을 반환한다.
     * </p>
     *
     * <pre>
     * StringUtil.decode(null, null, "foo", "bar")= "foo"
     * StringUtil.decode("", null, "foo", "bar") = "bar"
     * StringUtil.decode(null, "", "foo", "bar") = "bar"
     * StringUtil.decode("하이", "하이", null, "bar") = null
     * StringUtil.decode("하이", "이  ", "foo", null) = null
     * StringUtil.decode("하이", "하이", "foo", "bar") = "foo"
     * StringUtil.decode("하이", "이  ", "foo", "bar") = "bar"
     * </pre>
     *
     * @param sourceStr 비교할 문자열
     * @param compareStr 비교 대상 문자열
     * @param returnStr sourceStr와 compareStr의 값이 같을 때 반환할 문자열
     * @param defaultStr sourceStr와 compareStr의 값이 다를 때 반환할 문자열
     * @return sourceStr과 compareStr의 값이 동일(equal)할 때 returnStr을 반환하며,
     *         <br/>다르면 defaultStr을 반환한다.
     */
    public static String decode(String sourceStr, String compareStr, String returnStr, String defaultStr) {
        if (sourceStr == null && compareStr == null) {
            return returnStr;
        }

        if (sourceStr == null && compareStr != null) {
            return defaultStr;
        }

        if (sourceStr.trim().equals(compareStr)) {
            return returnStr;
        }

        return defaultStr;
    }

    /**
     * <p>오라클의 decode 함수와 동일한 기능을 가진 메서드이다.
     * <code>sourStr</code>과 <code>compareStr</code>의 값이 같으면
     * <code>returStr</code>을 반환하며, 다르면  <code>sourceStr</code>을 반환한다.
     * </p>
     *
     * <pre>
     * StringUtil.decode(null, null, "foo") = "foo"
     * StringUtil.decode("", null, "foo") = ""
     * StringUtil.decode(null, "", "foo") = null
     * StringUtil.decode("하이", "하", "foo") = "foo"
     * StringUtil.decode("하이", "하이 ", "foo") = "하이"
     * StringUtil.decode("하이", "바이", "foo") = "하이"
     * </pre>
     *
     * @param sourceStr 비교할 문자열
     * @param compareStr 비교 대상 문자열
     * @param returnStr sourceStr와 compareStr의 값이 같을 때 반환할 문자열
     * @return sourceStr과 compareStr의 값이 동일(equal)할 때 returnStr을 반환하며,
     *         <br/>다르면 sourceStr을 반환한다.
     */
    public static String decode(String sourceStr, String compareStr, String returnStr) {
        return decode(sourceStr, compareStr, returnStr, sourceStr);
    }
	
	/**
	 * <PRE>
	 * 1. MethodName : splitList
	 * 2. ClassName  : SysUtil
	 * 3. Comment   : 문자열을 해당 문자로 잘라서 리스트로 변환
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2013. 10. 30. 오후 8:38:14
	 * </PRE>
	 *   @return List
	 *   @param str
	 *   @return
	 */
	public static List splitList(String str, String splitStr)
	{
		List returnList = new ArrayList();
		
		returnList = Arrays.asList(str.split(splitStr, str.split(splitStr).length+1));
		return returnList;
	}
	
	/**
     * <p>{@link String#toUpperCase()}를 이용하여 대문자로 변환한다.</p>
     *
     * <pre>
     * StringUtil.upperCase(null)  = null
     * StringUtil.upperCase("")    = ""
     * StringUtil.upperCase("aBc") = "ABC"
     * </pre>
     *
     * @param str 대문자로 변환되어야 할 문자열
     * @return 대문자로 변환된 문자열, null이 입력되면 <code>null</code> 리턴
     */
    public static String upperCase(String str) {
        if (str == null) {
            return null;
        }

        return str.toUpperCase();
    }

    /**
     * <p>입력된 String의 앞쪽에서 두번째 인자로 전달된 문자(stripChars)를 모두 제거한다.</p>
     *
     * <pre>
     * StringUtil.stripStart(null, *)          = null
     * StringUtil.stripStart("", *)            = ""
     * StringUtil.stripStart("abc", "")        = "abc"
     * StringUtil.stripStart("abc", null)      = "abc"
     * StringUtil.stripStart("  abc", null)    = "abc"
     * StringUtil.stripStart("abc  ", null)    = "abc  "
     * StringUtil.stripStart(" abc ", null)    = "abc "
     * StringUtil.stripStart("yxabc  ", "xyz") = "abc  "
     * </pre>
     *
     * @param str 지정된 문자가 제거되어야 할 문자열
     * @param stripChars 제거대상 문자열
     * @return 지정된 문자가 제거된 문자열, null이 입력되면 <code>null</code> 리턴
     */
    public static String stripStart(String str, String stripChars) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        int start = 0;
        if (stripChars == null) {
            while ((start != strLen) && Character.isWhitespace(str.charAt(start))) {
                start++;
            }
        } else if (stripChars.length() == 0) {
            return str;
        } else {
            while ((start != strLen) && (stripChars.indexOf(str.charAt(start)) != -1)) {
                start++;
            }
        }

        return str.substring(start);
    }


    /**
     * <p>입력된 String의 뒤쪽에서 두번째 인자로 전달된 문자(stripChars)를 모두 제거한다.</p>
     *
     * <pre>
     * StringUtil.stripEnd(null, *)          = null
     * StringUtil.stripEnd("", *)            = ""
     * StringUtil.stripEnd("abc", "")        = "abc"
     * StringUtil.stripEnd("abc", null)      = "abc"
     * StringUtil.stripEnd("  abc", null)    = "  abc"
     * StringUtil.stripEnd("abc  ", null)    = "abc"
     * StringUtil.stripEnd(" abc ", null)    = " abc"
     * StringUtil.stripEnd("  abcyx", "xyz") = "  abc"
     * </pre>
     *
     * @param str 지정된 문자가 제거되어야 할 문자열
     * @param stripChars 제거대상 문자열
     * @return 지정된 문자가 제거된 문자열, null이 입력되면 <code>null</code> 리턴
     */
    public static String stripEnd(String str, String stripChars) {
        int end;
        if (str == null || (end = str.length()) == 0) {
            return str;
        }

        if (stripChars == null) {
            while ((end != 0) && Character.isWhitespace(str.charAt(end - 1))) {
                end--;
            }
        } else if (stripChars.length() == 0) {
            return str;
        } else {
            while ((end != 0) && (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
                end--;
            }
        }

        return str.substring(0, end);
    }

    /**
     * <p>입력된 String의 앞, 뒤에서 두번째 인자로 전달된 문자(stripChars)를 모두 제거한다.</p>
     *
     * <pre>
     * StringUtil.strip(null, *)          = null
     * StringUtil.strip("", *)            = ""
     * StringUtil.strip("abc", null)      = "abc"
     * StringUtil.strip("  abc", null)    = "abc"
     * StringUtil.strip("abc  ", null)    = "abc"
     * StringUtil.strip(" abc ", null)    = "abc"
     * StringUtil.strip("  abcyx", "xyz") = "  abc"
     * </pre>
     *
     * @param str 지정된 문자가 제거되어야 할 문자열
     * @param stripChars 제거대상 문자열
     * @return 지정된 문자가 제거된 문자열, null이 입력되면 <code>null</code> 리턴
     */
    public static String strip(String str, String stripChars) {
	if (isEmpty(str)) {
	    return str;
	}

	String srcStr = str;
	srcStr = stripStart(srcStr, stripChars);

	return stripEnd(srcStr, stripChars);
    }
    
    /**
     * <p>
     * String이 비었거나("") 혹은 null 인지 검증한다.
     * </p>
     *
     * <pre>
     *  StringUtil.isEmpty(null)      = true
     *  StringUtil.isEmpty("")        = true
     *  StringUtil.isEmpty(" ")       = false
     *  StringUtil.isEmpty("bob")     = false
     *  StringUtil.isEmpty("  bob  ") = false
     * </pre>
     *
     * @param str - 체크 대상 스트링오브젝트이며 null을 허용함
     * @return <code>true</code> - 입력받은 String 이 빈 문자열 또는 null인 경우
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0|| str.equals("null");
    }
    
    /**
     * <PRE>
     * 1. MethodName : setPoint
     * 2. ClassName  : StringUtil
     * 3. Comment   : 검색결과 리스트 색상 칠하기
     * 4. 작성자    : SooHyun.Seo
     * 5. 작성일    : 2014. 8. 22. 오후 1:34:51
     * </PRE>
     *   @return String
     *   @param src
     *   @param colorCode
     *   @return
     */
    public static String setTextColor(String src, HashMap schTxtMap, String colorCode) {
    	
    	Set key = schTxtMap.keySet();
    	
    	System.out.println("schTxtMap"+key.size());
        
        for (Iterator iterator = key.iterator(); iterator.hasNext();) {

            String keyName = (String) iterator.next();
            String value = (String) schTxtMap.get(keyName);
            
            System.out.println("keyName"+keyName);
            System.out.println("value"+value);
            
            src = src.replaceAll(value, "<span style=\"color:"+colorCode+"; font-weight:bold\">"+value+"</span>");
            
        }

		return src;
	}    
    
    
    public static String removeHtmlTag(String srcStr){
    	
    	String rtnStr = null;
    	
    	if(srcStr != null && !srcStr.equals("")){
    	
    		rtnStr = srcStr.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
    	}
    	
    	return rtnStr;
    }
    
    
    /**
     * <PRE>
     * 1. MethodName : setComma
     * 2. ClassName  : StringUtil
     * 3. Comment   : 3자리 마다 콤마 찍기
     * 4. 작성자    : 박재현
     * 5. 작성일    : 2017.12.226. 오후 5:23:40
     * </PRE>
     *   @return String
     *   @param num
     *   @return
     */
    public static String setComma(String num) {
        
        //Null 체크
        if(num == null || num.isEmpty()) return "0"; 
    
        //숫자형태가 아닌 문자열일경우 디폴트 0으로 반환 
        String numberExpr = "^[-+]?(0|[1-9][0-9]*)(\\.[0-9]+)?([eE][-+]?[0-9]+)?$";
        boolean isNumber = num.matches(numberExpr);
        if (!isNumber) return "0";             
    
        String strResult = num; //출력할 결과를 저장할 변수
        Pattern p = Pattern.compile("(^[+-]?\\d+)(\\d{3})"); //정규표현식 
        Matcher regexMatcher = p.matcher(num); 
        
        int cnt = 0;
        while(regexMatcher.find()) {                
            strResult = regexMatcher.replaceAll("$1,$2"); //치환 : 그룹1 + "," + 그룹2
                
            //System.out.println("과정("+ (++cnt) +"):"+strResult);
                
            //치환된 문자열로 다시 matcher객체 얻기 
            //regexMatcher = p.matcher(strResult); 
            regexMatcher.reset(strResult); 
        }        
        return strResult;
    }
    
    
    public static String castMoneyType(int amount) {

		java.text.DecimalFormat decFormat = new java.text.DecimalFormat("###,###,###,###");

		return decFormat.format(amount);
	}

	public static String castMoneyType(double amount) {

		java.text.DecimalFormat decFormat = new java.text.DecimalFormat("###,###,###,###");

		return decFormat.format(amount);
	}

	public static String castMoneyType(String amount) {

		java.text.DecimalFormat decFormat = new java.text.DecimalFormat("###,###,###,###");

		return decFormat.format(Double.parseDouble(amount));
	}

	public static String getQuestionType(String type) {
		String sReturnType="";
		if (type.equals ( "inputComment" ) ) {
			sReturnType="comment";
	    }
	    else if ( type.equals ( "inputRadio" ) ) {
	    	sReturnType="radio";
	    }
	    else if ( type.equals ( "inputCheckbox" ) ) {
	    	sReturnType="checkbox";
	    }
	    else if ( type.equals ( "inputTextline" ) ) {
	    	sReturnType="textline";
	    }
	    else if ( type.equals ( "inputTextarea" ) ) {
	    	sReturnType="textarea";
	    }
	    else if (type.equals ( "inputImage" ) ) {
    		type="textimage";
	    }
	    else if (type.equals ( "inputLocation" ) ) {
	    	sReturnType="textlocation";
	    }
		return sReturnType;
	}
	public static String translateExportDelimiters ( String content, String delimiter ) {
		StringExt s = new StringExt( content );
		String substituteDelimiter = ",";
		if ( delimiter.equals ( ";" ) ) { substituteDelimiter = ","; }
		else if ( delimiter.equals ( "," ) ) { substituteDelimiter = ";"; }
		else if ( delimiter.equals ( "|" ) ) { substituteDelimiter = ";"; }

		return s.substitute( delimiter, substituteDelimiter );
	}
	
	  public static boolean isAcceptingDataEntry (String pOpended,String pClosed) {
				  
		    return
		    ( !pOpended.equals("")
		       &&
		      ( pClosed.equals("")
		       // ||
		       // DateUtil.parseDate (pClosed, DateUtil.ISO4601DateFormat ).compareTo ( new java.util.Date () ) >= 0
		      )
		    );
		  }
	  
	  public static String shortenText ( String text, int numChars ) {
		    String s = new String ( text );

		    if ( s.indexOf("<br>") > 0 ) {
		      StringBuffer sb = new StringBuffer ( "" );
		      while ( s.indexOf("<br>") > 0 ) {
		        int pos = s.indexOf("<br>");
		        String part = shortenText( s.substring(0,pos), numChars );
		        sb.append(part+"<br>");
		        s = s.substring(pos+4);
		      }
		      if ( !s.equals("") ) { sb.append ( shortenText(s, numChars) ); }
		      s = sb.toString();
		    }
		    else {
		      if ( s.length () > numChars ) {
		        s = s.substring(0,numChars) + "...";
		      }
		    }
		    return s;
		  }
	  
	  public static String selectSelected (String selectVal, String checkVal ) {
		   String sReturn = "";
		   if (isEmpty(selectVal)) {
			   sReturn="selected";
			    return sReturn;
		  } 
		  if(selectVal.equals(checkVal))  sReturn="selected";
		  
		  return sReturn;
	  }
	  
	  public static String jsonStatStr(DataMap pDataMap,int pType,int surveyCd) {
		   String sReturn = "";
		   if(pDataMap==null) return sReturn;

			JSONArray jsonArray =new JSONArray();
			JSONObject RateJSON = null;

		   switch (pType){
		   case 1:
			   RateJSON = new JSONObject();
			   RateJSON.put("cateval", "여성");
			   RateJSON.put("rateval", pDataMap.getString("FMALE_P"));
			   jsonArray.add(RateJSON);
			   RateJSON = new JSONObject();
			   RateJSON.put("cateval", "남성");
			   RateJSON.put("rateval", pDataMap.getString("MALE_P"));
			   jsonArray.add(RateJSON);
			   sReturn=jsonArray.toString();
		   	  break;
		   case 2:
			   RateJSON = new JSONObject();
			   RateJSON.put("cateval", "70대 이상");
			   RateJSON.put("rateval", pDataMap.getString("AGE6_P"));
			   jsonArray.add(RateJSON);

			   RateJSON = new JSONObject();
			   RateJSON.put("cateval", "60대");
			   RateJSON.put("rateval", pDataMap.getString("AGE5_P"));
			   jsonArray.add(RateJSON);

			   RateJSON = new JSONObject();
			   RateJSON.put("cateval", "50대");
			   RateJSON.put("rateval", pDataMap.getString("AGE4_P"));
			   jsonArray.add(RateJSON);

			   RateJSON = new JSONObject();
			   RateJSON.put("cateval", "40대");
			   RateJSON.put("rateval", pDataMap.getString("AGE3_P"));
			   jsonArray.add(RateJSON);

			   RateJSON = new JSONObject();
			   RateJSON.put("cateval", "30대");
			   RateJSON.put("rateval", pDataMap.getString("AGE2_P"));
			   jsonArray.add(RateJSON);

			   RateJSON = new JSONObject();
			   RateJSON.put("cateval", "20대이하");
			   RateJSON.put("rateval", pDataMap.getString("AGE1_P"));
			   jsonArray.add(RateJSON);
			   
			   sReturn=jsonArray.toString();
		   	  break;
		   case 3:
			   RateJSON = new JSONObject();
			   RateJSON.put("cateval", "대졸이상");
			   RateJSON.put("rateval", pDataMap.getString("SCHOOL2_P"));
			   jsonArray.add(RateJSON);

			   RateJSON = new JSONObject();
			   RateJSON.put("cateval", "고졸이하");
			   RateJSON.put("rateval", pDataMap.getString("SCHOOL1_P"));
			   jsonArray.add(RateJSON);
			   
			   sReturn=jsonArray.toString();
		   	  break;

		   case 4:
			   RateJSON = new JSONObject();
			   RateJSON.put("cateval", "매우 좋지 않음");
			   RateJSON.put("rateval", pDataMap.getString("HEALTH5_P"));
			   jsonArray.add(RateJSON);

			   RateJSON = new JSONObject();
			   RateJSON.put("cateval", "좋지않음");
			   RateJSON.put("rateval", pDataMap.getString("HEALTH4_P"));
			   jsonArray.add(RateJSON);

			   RateJSON = new JSONObject();
			   RateJSON.put("cateval", "보통");
			   RateJSON.put("rateval", pDataMap.getString("HEALTH3_P"));
			   jsonArray.add(RateJSON);

			   RateJSON = new JSONObject();
			   RateJSON.put("cateval", "좋음");
			   RateJSON.put("rateval", pDataMap.getString("HEALTH2_P"));
			   jsonArray.add(RateJSON);

			   RateJSON = new JSONObject();
			   RateJSON.put("cateval", "매우좋음");
			   RateJSON.put("rateval", pDataMap.getString("HEALTH1_P"));
			   jsonArray.add(RateJSON);

			   
			   sReturn=jsonArray.toString();
		   	  break;
		   case 5:

			   RateJSON = new JSONObject();
			   RateJSON.put("cateval", "비응급실");
			   RateJSON.put("rateval", pDataMap.getString("WAY2_P"));
			   jsonArray.add(RateJSON);

			   RateJSON = new JSONObject();
			   RateJSON.put("cateval", "응급실");
			   RateJSON.put("rateval", pDataMap.getString("WAY1_P"));
			   jsonArray.add(RateJSON);

			   
			   sReturn=jsonArray.toString();
		   	  break;
		   case 6:
			   if(surveyCd==10){
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "기타");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT25_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "심장혈관센터");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT24_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "소아청소년과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT23_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "당뇨내분비센터");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT22_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "유방/갑상선센터");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT21_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "혈액종양내과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT20_P"));
				   jsonArray.add(RateJSON);
					
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "호흡기내과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT19_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "피부과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT18_P"));
				   jsonArray.add(RateJSON);
				
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "치과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT17_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "척추센터");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT16_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "정형외과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT15_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "재활의학과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT14_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "이비인후과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT13_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "안과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT12_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "심장내과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT11_P"));
				   jsonArray.add(RateJSON);
					
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "신장내과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT10_P"));
				   jsonArray.add(RateJSON);
					
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "신경외과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT9_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "신경과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT8_P"));
				   jsonArray.add(RateJSON);
				
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "소화기내과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT7_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "성형외과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT6_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "비뇨의학과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT5_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "마취통증의학과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT4_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "류마티스내과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT3_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "내분비내과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT2_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "가정의학과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT1_P"));
				   jsonArray.add(RateJSON);
			   }else if(surveyCd==20){

				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "기타");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT20_P"));
				   jsonArray.add(RateJSON);
					
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "혈액종양내과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT19_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "흉부외과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT18_P"));
				   jsonArray.add(RateJSON);
				
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "피부과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT17_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "치과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT16_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "정형외과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT15_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "재활의학과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT14_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "이비인후과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT13_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "외과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT12_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "안과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT11_P"));
				   jsonArray.add(RateJSON);
					
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "심장내과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT10_P"));
				   jsonArray.add(RateJSON);
					
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "신장내과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT9_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "신경외과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT8_P"));
				   jsonArray.add(RateJSON);
				
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "신경과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT7_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "소화기내과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT6_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "성형외과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT5_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "산부인과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT4_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "비뇨의학과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT3_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "내분비내과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT2_P"));
				   jsonArray.add(RateJSON);
	
				   RateJSON = new JSONObject();
				   RateJSON.put("cateval", "가정의학과");
				   RateJSON.put("rateval", pDataMap.getString("SUBJECT1_P"));
				   jsonArray.add(RateJSON);
				   
			   }
			   
			   sReturn=jsonArray.toString();
		   	  break;
		   case 7:

			   RateJSON = new JSONObject();
			   RateJSON.put("cateval", "소아응급실");
			   RateJSON.put("rateval", pDataMap.getString("WAY2_P"));
			   jsonArray.add(RateJSON);

			   RateJSON = new JSONObject();
			   RateJSON.put("cateval", "성인응급실");
			   RateJSON.put("rateval", pDataMap.getString("WAY1_P"));
			   jsonArray.add(RateJSON);

			   
			   sReturn=jsonArray.toString();
		   	  break;
		   case 8:

			   RateJSON = new JSONObject();
			   RateJSON.put("cateval", "보호자");
			   RateJSON.put("rateval", pDataMap.getString("WAY2_P"));
			   jsonArray.add(RateJSON);

			   RateJSON = new JSONObject();
			   RateJSON.put("cateval", "환자");
			   RateJSON.put("rateval", pDataMap.getString("WAY1_P"));
			   jsonArray.add(RateJSON);

			   
			   sReturn=jsonArray.toString();
		   	  break;
		   }
		   
		  
		  return sReturn;
	  }
	public static String jsonStatStr(List<DataMap> pDataMaps,int pType) {
		String sReturn = "";
		if(pDataMaps==null) return sReturn;

		JSONArray jsonArray =new JSONArray();
		JSONObject RateJSON = null;
		int iGradeTotCnt=0;
		float fGradeCntVal=0.0f;
		float fGradeCntValSum=0.0f;
		String sGradeCntVal="";

		switch (pType){
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				iGradeTotCnt=0;
				for(DataMap aDataMap:pDataMaps){
					iGradeTotCnt=iGradeTotCnt+aDataMap.getInt("GRADE_CNT");
				}
				fGradeCntValSum = 0.0f;
				int i=0;
				for(DataMap aDataMap:pDataMaps){

					if(i==pDataMaps.size()-1){			//100%맟추기 위해
						fGradeCntVal = 100f - fGradeCntValSum;
					}else{
						fGradeCntVal = ((float) aDataMap.getInt("GRADE_CNT")/(float)iGradeTotCnt) *100;
						fGradeCntValSum=fGradeCntValSum+fGradeCntVal;
					}


					sGradeCntVal = String.format("%.1f", fGradeCntVal);

					RateJSON = new JSONObject();
					RateJSON.put("cateval", aDataMap.getString("QUESTION_LABEL"));
					RateJSON.put("rateval", sGradeCntVal);
					jsonArray.add(RateJSON);
				}

				sReturn=jsonArray.toString();

				break;
			case 7:
				break;
			case 8:
				break;
		}


		return sReturn;
	}

	  public static String jsonDimesionStr(List<DataMap> pDataMap,int pType,String pSurvey10,String pSurvey20,String pSurvey30) {
		   String sReturn = "";
		   if(pDataMap==null) return sReturn;

			JSONArray jsonArray =new JSONArray();
			JSONObject RateJSON = null;

		   switch (pType){
		   case 1:
			   for (DataMap aDataMap:pDataMap){
				   RateJSON = new JSONObject();
				
				   if(pSurvey10.equals(aDataMap.getString("SURVEY_ID"))){
					   RateJSON.put("cateseq", "10");
					   RateJSON.put("cateval", "외래서비스");
				   } else if(pSurvey20.equals(aDataMap.getString("SURVEY_ID"))){
					   RateJSON.put("cateseq", "20");
					   RateJSON.put("cateval", "입원서비스");
				   }else if(pSurvey30.equals(aDataMap.getString("SURVEY_ID"))){
					   RateJSON.put("cateseq", "30");
					   RateJSON.put("cateval", "응급실");
				   }else{
					   RateJSON.put("cateseq", "0");
					   RateJSON.put("cateval", "병원전체");
				   }
				   
				   
				   
				   RateJSON.put("rateval", aDataMap.getString("AVG_QUESTION_SCORE"));
				   jsonArray.add(RateJSON);
			   	}

			   sReturn=jsonArray.toString();
		   	  break;
		   }
		   
		  
		  return sReturn;
	  }
	  
	  public static String jsonDimesionStr(LinkedHashMap<String, String> pDataMap, int pType) {
		   String sReturn = "";
		   if(pDataMap==null) return sReturn;

			JSONArray jsonArray =new JSONArray();
			JSONObject RateJSON = null;

		   switch (pType){
		   case 1:
				   Set<Entry<String, String>> set = pDataMap.entrySet();
					Iterator<Entry<String, String>> it = set.iterator();
			        // HashMap에 포함된 key, value 값을 호출 한다.
					int i=0;
					while (it.hasNext()) {
						Map.Entry<String, String> e = (Map.Entry<String, String>)it.next();
						   RateJSON = new JSONObject();
						   RateJSON.put("cateseq",i+"");
						   RateJSON.put("cateval", e.getKey() );
						   RateJSON.put("rateval", e.getValue());
						   jsonArray.add(RateJSON);
						   i++;
						  // System.out.println("cateval : " + e.getKey() + ", rateval : " + e.getValue());
					}
			  }
		  
		  sReturn=jsonArray.toString();
		  return sReturn;
	  }

	  public static String jsonDimesionStr(List<LinkedHashMap> pDataMap) {
		   String sReturn = "";
		   if(pDataMap==null) return sReturn;

			JSONArray jsonArray =new JSONArray();
			JSONObject RateJSON = null;
			for (LinkedHashMap<String, String> aDataMap:pDataMap){
				   Set<Entry<String, String>> set = aDataMap.entrySet();
					Iterator<Entry<String, String>> it = set.iterator();
			        // HashMap에 포함된 key, value 값을 호출 한다.
					int i=0;
					RateJSON = new JSONObject();
					while (it.hasNext()) {
						Map.Entry<String, String> e = (Map.Entry<String, String>)it.next();
						   RateJSON.put(e.getKey(), e.getValue());
						   i++;
						  // System.out.println("cateval : " + e.getKey() + ", rateval : " + e.getValue());
					}
					jsonArray.add(RateJSON);
			 }
		  sReturn=jsonArray.toString();
		  return sReturn;
	  }
	  
	  
	  public static DataMap sectionUser (int pSectionId,String sectionTit, List<DataMap> pSectionList ) {
		  DataMap sReturn = new DataMap();
		  sReturn.put("SECTION_RTITLE", sectionTit);
		  sReturn.put("SECTION_CONT", "");
		  sReturn.put("SECTION_END", "");
		  sReturn.put("EXPORTINCLUDER", "1");
		   
		   if(pSectionList==null) {
			   return sReturn;
		   }
		
		   for (DataMap aDataMap:pSectionList){
			   if(aDataMap.getInt("SECTION_ID")==pSectionId){
				   sReturn.put("SECTION_RTITLE", aDataMap.getString("SECTION_RTITLE"));
				   sReturn.put("SECTION_CONT", aDataMap.getString("SECTION_CONT"));
				   sReturn.put("SECTION_END", aDataMap.getString("SECTION_END"));
				   sReturn.put("EXPORTINCLUDER", aDataMap.getString("EXPORTINCLUDER"));
				   break;
			   }
		   }
		  
		   
		  return sReturn;
	  }

	  public static DataMap questionUser (int pSectionId,int pQuestionId,String questionTit, List<DataMap> pQuestionList) {
		  DataMap sReturn = new DataMap();
		  sReturn.put("QUESTION_RTITLE", questionTit);
		  sReturn.put("EXPORTINCLUDER", "1");
		   
		   if(pQuestionList==null) {
			   return sReturn;
		   }
		
		   for (DataMap aDataMap:pQuestionList){
			   if(aDataMap.getInt("SECTION_ID")==pSectionId && aDataMap.getInt("QUESTION_ID")==pQuestionId){
					  sReturn.put("QUESTION_RTITLE", aDataMap.getString("QUESTION_RTITLE"));
					  sReturn.put("EXPORTINCLUDER", aDataMap.getString("EXPORTINCLUDER"));
				   break;
			   }
		   }

		   return sReturn;
	  }

	  public static List<DataMap> questionUsers (int pSectionId, List<DataMap> pQuestionList) {
		  List<DataMap> questionUsers=new ArrayList<DataMap>(); 
		  DataMap questionUserMap = new DataMap();
		  
		   if(pQuestionList==null) {
			   return questionUsers;
		   }
		
		   for (DataMap aDataMap:pQuestionList){
			   if(aDataMap.getInt("SECTION_ID")==pSectionId ){
				   questionUserMap.put("QUESTION_RTITLE", aDataMap.getString("QUESTION_RTITLE"));
				   questionUserMap.put("EXPORTINCLUDER", aDataMap.getString("EXPORTINCLUDER"));
				   
				   questionUsers.add(questionUserMap);
			   }
		   }

		   return questionUsers;
	  }

	public static List<TbSurveyUserEntryResult> questionUsers (List<DataMap> pSurveyLableCnt) {
		List<TbSurveyUserEntryResult> tbSurveyUserEntryResults = new ArrayList<TbSurveyUserEntryResult>();
		TbSurveyUserEntryResult  aTbSurveyUserEntryResult = null;

		int SectionId=-1;
		int QuestionId=-1;
		int QuestionL=0;
		if(pSurveyLableCnt==null) {
			return tbSurveyUserEntryResults;
		}

		int GRADE_01_CNT = 0;
		int GRADE_02_CNT = 0;
		int GRADE_03_CNT = 0;
		int GRADE_04_CNT = 0;
		int GRADE_05_CNT = 0;
		int GRADE_06_CNT = 0;
		int GRADE_07_CNT = 0;
		int GRADE_08_CNT = 0;
		int GRADE_09_CNT = 0;
		int GRADE_10_CNT = 0;
		int GRADE_11_CNT = 0;
		int GRADE_TOT_CNT = 0;
		int Lable_CNT = 0;

		float GRADE_01_VAL = 0.0f;
		float GRADE_02_VAL = 0.0f;
		float GRADE_03_VAL = 0.0f;
		float GRADE_04_VAL = 0.0f;
		float GRADE_05_VAL = 0.0f;
		float GRADE_06_VAL = 0.0f;
		float GRADE_07_VAL = 0.0f;
		float GRADE_08_VAL = 0.0f;
		float GRADE_09_VAL = 0.0f;
		float GRADE_10_VAL = 0.0f;
		float GRADE_11_VAL = 0.0f;
		float GRADE_AVG_VAL = 0.0f;

		String sGRADE_01_VAL = "0.0";
		String sGRADE_02_VAL = "0.0";
		String sGRADE_03_VAL = "0.0";
		String sGRADE_04_VAL = "0.0";
		String sGRADE_05_VAL = "0.0";
		String sGRADE_06_VAL = "0.0";
		String sGRADE_07_VAL = "0.0";
		String sGRADE_08_VAL = "0.0";
		String sGRADE_09_VAL = "0.0";
		String sGRADE_10_VAL = "0.0";
		String sGRADE_11_VAL = "0.0";
		String sGRADE_AVG_VAL = "0.0";

		for (DataMap aDataMap:pSurveyLableCnt){
			if(SectionId!=aDataMap.getInt("SECTION_ID") || QuestionId!=aDataMap.getInt("QUESTION_ID")){
				GRADE_01_CNT = 0;
				GRADE_02_CNT = 0;
				GRADE_03_CNT = 0;
				GRADE_04_CNT = 0;
				GRADE_05_CNT = 0;
				GRADE_06_CNT = 0;
				GRADE_07_CNT = 0;
				GRADE_08_CNT = 0;
				GRADE_09_CNT = 0;
				GRADE_10_CNT = 0;
				GRADE_11_CNT = 0;
				GRADE_TOT_CNT = 0;
				Lable_CNT = 0;

				GRADE_01_VAL = 0.0f;
				GRADE_02_VAL = 0.0f;
				GRADE_03_VAL = 0.0f;
				GRADE_04_VAL = 0.0f;
				GRADE_05_VAL = 0.0f;
				GRADE_06_VAL = 0.0f;
				GRADE_07_VAL = 0.0f;
				GRADE_08_VAL = 0.0f;
				GRADE_09_VAL = 0.0f;
				GRADE_10_VAL = 0.0f;
				GRADE_11_VAL = 0.0f;
				GRADE_AVG_VAL = 0.0f;

				SectionId=aDataMap.getInt("SECTION_ID");
				QuestionId= aDataMap.getInt("QUESTION_ID");
				aTbSurveyUserEntryResult =new TbSurveyUserEntryResult();
				aTbSurveyUserEntryResult.setUSER_NO(aDataMap.getInt("USER_NO"));
				aTbSurveyUserEntryResult.setSURVEY_GROUP_ID(aDataMap.getString("SURVEY_GROUP_ID"));
				aTbSurveyUserEntryResult.setSURVEY_ID(aDataMap.getString("SURVEY_ID"));
				aTbSurveyUserEntryResult.setSURVEY_YEAR(aDataMap.getString("SURVEY_YEAR"));
				aTbSurveyUserEntryResult.setSURVEY_DEGREE(aDataMap.getString("SURVEY_DEGREE"));
				aTbSurveyUserEntryResult.setSECTION_ID(SectionId);
				aTbSurveyUserEntryResult.setQUESTION_ID(QuestionId);
				QuestionL=0;
				Lable_CNT=aDataMap.getInt("QUESTION_LABEL_ID_CNT");
			}

			QuestionL++;

			if(aDataMap.getInt("QUESTION_LABEL_ID")==0){
				GRADE_01_CNT=aDataMap.getInt("QUESTION_CNT");
				aTbSurveyUserEntryResult.setGRADE_01_CNT(GRADE_01_CNT);
			}else  if(aDataMap.getInt("QUESTION_LABEL_ID")==1){
				GRADE_02_CNT=aDataMap.getInt("QUESTION_CNT");
				aTbSurveyUserEntryResult.setGRADE_02_CNT(GRADE_02_CNT);
			}else  if(aDataMap.getInt("QUESTION_LABEL_ID")==2){
				GRADE_03_CNT=aDataMap.getInt("QUESTION_CNT");
				aTbSurveyUserEntryResult.setGRADE_03_CNT(GRADE_03_CNT);
			}else  if(aDataMap.getInt("QUESTION_LABEL_ID")==3){
				GRADE_04_CNT=aDataMap.getInt("QUESTION_CNT");
				aTbSurveyUserEntryResult.setGRADE_04_CNT(GRADE_04_CNT);
			}else  if(aDataMap.getInt("QUESTION_LABEL_ID")==4){
				GRADE_05_CNT=aDataMap.getInt("QUESTION_CNT");
				aTbSurveyUserEntryResult.setGRADE_05_CNT(GRADE_05_CNT);
			}else  if(aDataMap.getInt("QUESTION_LABEL_ID")==5){
				GRADE_06_CNT=aDataMap.getInt("QUESTION_CNT");
				aTbSurveyUserEntryResult.setGRADE_06_CNT(GRADE_06_CNT);
			}else  if(aDataMap.getInt("QUESTION_LABEL_ID")==6){
				GRADE_07_CNT=aDataMap.getInt("QUESTION_CNT");
				aTbSurveyUserEntryResult.setGRADE_07_CNT(GRADE_07_CNT);
			}else  if(aDataMap.getInt("QUESTION_LABEL_ID")==7){
				GRADE_08_CNT=aDataMap.getInt("QUESTION_CNT");
				aTbSurveyUserEntryResult.setGRADE_08_CNT(GRADE_08_CNT);
			}else  if(aDataMap.getInt("QUESTION_LABEL_ID")==8){
				GRADE_09_CNT=aDataMap.getInt("QUESTION_CNT");
				aTbSurveyUserEntryResult.setGRADE_09_CNT(GRADE_09_CNT);
			}else  if(aDataMap.getInt("QUESTION_LABEL_ID")==9){
				GRADE_10_CNT=aDataMap.getInt("QUESTION_CNT");
				aTbSurveyUserEntryResult.setGRADE_10_CNT(GRADE_10_CNT);
			}else  if(aDataMap.getInt("QUESTION_LABEL_ID")==10){
				GRADE_11_CNT=aDataMap.getInt("QUESTION_CNT");
				aTbSurveyUserEntryResult.setGRADE_11_CNT(GRADE_11_CNT);
			}

			if(aDataMap.getInt("QUESTION_LABEL_ID_CNT")==QuestionL){
				GRADE_TOT_CNT=GRADE_01_CNT+GRADE_02_CNT+GRADE_03_CNT+GRADE_04_CNT+GRADE_05_CNT
						+GRADE_06_CNT+GRADE_07_CNT+GRADE_08_CNT+GRADE_09_CNT+GRADE_10_CNT
						+GRADE_11_CNT;

				aTbSurveyUserEntryResult.setGRADE_TOT_CNT(GRADE_TOT_CNT);
				//System.out.println("GRADE_TOT_CNT["+GRADE_TOT_CNT+"]");



				if(GRADE_TOT_CNT==0){
					GRADE_01_VAL = 0.0f;
					GRADE_02_VAL = 0.0f;
					GRADE_03_VAL = 0.0f;
					GRADE_04_VAL = 0.0f;
					GRADE_05_VAL = 0.0f;
					GRADE_06_VAL = 0.0f;
					GRADE_07_VAL = 0.0f;
					GRADE_08_VAL = 0.0f;
					GRADE_09_VAL = 0.0f;
					GRADE_10_VAL = 0.0f;
					GRADE_11_VAL = 0.0f;
					GRADE_AVG_VAL = 0.0f;
				} else{
					GRADE_01_VAL = ((float)GRADE_01_CNT/(float)GRADE_TOT_CNT) *100;
					GRADE_02_VAL = ((float)GRADE_02_CNT/(float)GRADE_TOT_CNT) *100;
					GRADE_03_VAL = ((float)GRADE_03_CNT/(float)GRADE_TOT_CNT) *100;
					GRADE_04_VAL = ((float)GRADE_04_CNT/(float)GRADE_TOT_CNT) *100;
					GRADE_05_VAL = ((float)GRADE_05_CNT/(float)GRADE_TOT_CNT) *100;
					GRADE_06_VAL = ((float)GRADE_06_CNT/(float)GRADE_TOT_CNT) *100;
					GRADE_07_VAL = ((float)GRADE_07_CNT/(float)GRADE_TOT_CNT) *100;
					GRADE_08_VAL = ((float)GRADE_08_CNT/(float)GRADE_TOT_CNT) *100;
					GRADE_09_VAL = ((float)GRADE_09_CNT/(float)GRADE_TOT_CNT) *100;
					GRADE_10_VAL = ((float)GRADE_10_CNT/(float)GRADE_TOT_CNT) *100;
					GRADE_11_VAL = ((float)GRADE_11_CNT/(float)GRADE_TOT_CNT) *100;


					if(Lable_CNT==2){
						GRADE_02_VAL=100f-(GRADE_01_VAL);
						if("9".equals(aDataMap.getString("SUMMARY_CD")) || "10".equals(aDataMap.getString("SUMMARY_CD")) || "11".equals(aDataMap.getString("SUMMARY_CD"))){
							GRADE_AVG_VAL =( (float)(2-1)/(float)(Lable_CNT-1)*100)*GRADE_02_CNT;
						}
					}else if (Lable_CNT==3) {
						GRADE_03_VAL=100f-(GRADE_01_VAL+GRADE_02_VAL);
						if("9".equals(aDataMap.getString("SUMMARY_CD")) || "10".equals(aDataMap.getString("SUMMARY_CD")) || "11".equals(aDataMap.getString("SUMMARY_CD"))){
							GRADE_AVG_VAL =( (float)(3-1)/(float)(Lable_CNT-1)*100)*GRADE_03_CNT
									+ ( (float)(2-1)/(float)(Lable_CNT-1)*100)*GRADE_02_CNT;
						}
					}else if (Lable_CNT==4) {
						GRADE_04_VAL=100f-(GRADE_01_VAL+GRADE_02_VAL+GRADE_03_VAL);
						if("9".equals(aDataMap.getString("SUMMARY_CD")) || "10".equals(aDataMap.getString("SUMMARY_CD")) || "11".equals(aDataMap.getString("SUMMARY_CD"))){
							GRADE_AVG_VAL =( (float)(4-1)/(float)(Lable_CNT-1)*100)*GRADE_04_CNT
									+ ( (float)(3-1)/(float)(Lable_CNT-1)*100)*GRADE_03_CNT
									+ ( (float)(2-1)/(float)(Lable_CNT-1)*100)*GRADE_02_CNT;
						}
					}else if (Lable_CNT==5) {
						GRADE_05_VAL=100f-(GRADE_01_VAL+GRADE_02_VAL+GRADE_03_VAL+GRADE_04_VAL);
						if("9".equals(aDataMap.getString("SUMMARY_CD")) || "10".equals(aDataMap.getString("SUMMARY_CD")) || "11".equals(aDataMap.getString("SUMMARY_CD"))){
							GRADE_AVG_VAL =( (float)(5-1)/(float)(Lable_CNT-1)*100)*GRADE_05_CNT
									+ ( (float)(4-1)/(float)(Lable_CNT-1)*100)*GRADE_04_CNT
									+ ( (float)(3-1)/(float)(Lable_CNT-1)*100)*GRADE_03_CNT
									+ ( (float)(2-1)/(float)(Lable_CNT-1)*100)*GRADE_02_CNT;
						}
					}else if (Lable_CNT==6) {
						GRADE_06_VAL=100f-(GRADE_01_VAL+GRADE_02_VAL+GRADE_03_VAL+GRADE_04_VAL+GRADE_05_VAL);
						if("9".equals(aDataMap.getString("SUMMARY_CD")) || "10".equals(aDataMap.getString("SUMMARY_CD")) || "11".equals(aDataMap.getString("SUMMARY_CD"))){
							GRADE_AVG_VAL =( (float)(6-1)/(float)(Lable_CNT-1)*100)*GRADE_06_CNT
									+( (float)(5-1)/(float)(Lable_CNT-1)*100)*GRADE_05_CNT
									+ ( (float)(4-1)/(float)(Lable_CNT-1)*100)*GRADE_04_CNT
									+ ( (float)(3-1)/(float)(Lable_CNT-1)*100)*GRADE_03_CNT
									+ ( (float)(2-1)/(float)(Lable_CNT-1)*100)*GRADE_02_CNT;
						}
					}else if (Lable_CNT==7) {
						GRADE_07_VAL=100f-(GRADE_01_VAL+GRADE_02_VAL+GRADE_03_VAL+GRADE_04_VAL+GRADE_05_VAL+GRADE_06_VAL);
						if("9".equals(aDataMap.getString("SUMMARY_CD")) || "10".equals(aDataMap.getString("SUMMARY_CD")) || "11".equals(aDataMap.getString("SUMMARY_CD"))){
							GRADE_AVG_VAL =( (float)(7-1)/(float)(Lable_CNT-1)*100)*GRADE_07_CNT
									+( (float)(6-1)/(float)(Lable_CNT-1)*100)*GRADE_06_CNT
									+( (float)(5-1)/(float)(Lable_CNT-1)*100)*GRADE_05_CNT
									+ ( (float)(4-1)/(float)(Lable_CNT-1)*100)*GRADE_04_CNT
									+ ( (float)(3-1)/(float)(Lable_CNT-1)*100)*GRADE_03_CNT
									+ ( (float)(2-1)/(float)(Lable_CNT-1)*100)*GRADE_02_CNT;
						}
					}else if (Lable_CNT==8) {
						GRADE_08_VAL=100f-(GRADE_01_VAL+GRADE_02_VAL+GRADE_03_VAL+GRADE_04_VAL+GRADE_05_VAL+GRADE_06_VAL+GRADE_07_VAL);
						if("9".equals(aDataMap.getString("SUMMARY_CD")) || "10".equals(aDataMap.getString("SUMMARY_CD")) || "11".equals(aDataMap.getString("SUMMARY_CD"))){
							GRADE_AVG_VAL =( (float)(8-1)/(float)(Lable_CNT-1)*100)*GRADE_08_CNT
									+( (float)(7-1)/(float)(Lable_CNT-1)*100)*GRADE_07_CNT
									+( (float)(6-1)/(float)(Lable_CNT-1)*100)*GRADE_06_CNT
									+( (float)(5-1)/(float)(Lable_CNT-1)*100)*GRADE_05_CNT
									+ ( (float)(4-1)/(float)(Lable_CNT-1)*100)*GRADE_04_CNT
									+ ( (float)(3-1)/(float)(Lable_CNT-1)*100)*GRADE_03_CNT
									+ ( (float)(2-1)/(float)(Lable_CNT-1)*100)*GRADE_02_CNT;
						}
					}else if (Lable_CNT==9) {
						GRADE_09_VAL=100f-(GRADE_01_VAL+GRADE_02_VAL+GRADE_03_VAL+GRADE_04_VAL+GRADE_05_VAL+GRADE_06_VAL+GRADE_07_VAL+GRADE_08_VAL);
						if("9".equals(aDataMap.getString("SUMMARY_CD")) || "10".equals(aDataMap.getString("SUMMARY_CD")) || "11".equals(aDataMap.getString("SUMMARY_CD"))){
							GRADE_AVG_VAL =( (float)(9-1)/(float)(Lable_CNT-1)*100)*GRADE_09_CNT
									+( (float)(8-1)/(float)(Lable_CNT-1)*100)*GRADE_08_CNT
									+( (float)(7-1)/(float)(Lable_CNT-1)*100)*GRADE_07_CNT
									+( (float)(6-1)/(float)(Lable_CNT-1)*100)*GRADE_06_CNT
									+( (float)(5-1)/(float)(Lable_CNT-1)*100)*GRADE_05_CNT
									+ ( (float)(4-1)/(float)(Lable_CNT-1)*100)*GRADE_04_CNT
									+ ( (float)(3-1)/(float)(Lable_CNT-1)*100)*GRADE_03_CNT
									+ ( (float)(2-1)/(float)(Lable_CNT-1)*100)*GRADE_02_CNT;
						}
					}else if (Lable_CNT==10) {
						GRADE_10_VAL=100f-(GRADE_01_VAL+GRADE_02_VAL+GRADE_03_VAL+GRADE_04_VAL+GRADE_05_VAL+GRADE_06_VAL+GRADE_07_VAL+GRADE_08_VAL+GRADE_09_VAL);
						if("9".equals(aDataMap.getString("SUMMARY_CD")) || "10".equals(aDataMap.getString("SUMMARY_CD")) || "11".equals(aDataMap.getString("SUMMARY_CD"))){
							GRADE_AVG_VAL =( (float)(10-1)/(float)(Lable_CNT-1)*100)*GRADE_10_CNT
									+( (float)(9-1)/(float)(Lable_CNT-1)*100)*GRADE_09_CNT
									+( (float)(8-1)/(float)(Lable_CNT-1)*100)*GRADE_08_CNT
									+( (float)(7-1)/(float)(Lable_CNT-1)*100)*GRADE_07_CNT
									+( (float)(6-1)/(float)(Lable_CNT-1)*100)*GRADE_06_CNT
									+( (float)(5-1)/(float)(Lable_CNT-1)*100)*GRADE_05_CNT
									+ ( (float)(4-1)/(float)(Lable_CNT-1)*100)*GRADE_04_CNT
									+ ( (float)(3-1)/(float)(Lable_CNT-1)*100)*GRADE_03_CNT
									+ ( (float)(2-1)/(float)(Lable_CNT-1)*100)*GRADE_02_CNT;
						}
					}else if (Lable_CNT==11) {
						GRADE_11_VAL=100f-(GRADE_01_VAL+GRADE_02_VAL+GRADE_03_VAL+GRADE_04_VAL+GRADE_05_VAL+GRADE_06_VAL+GRADE_07_VAL+GRADE_08_VAL+GRADE_09_VAL+GRADE_10_VAL);
						if("9".equals(aDataMap.getString("SUMMARY_CD")) || "10".equals(aDataMap.getString("SUMMARY_CD")) || "11".equals(aDataMap.getString("SUMMARY_CD"))){
							GRADE_AVG_VAL =( (float)(11-1)/(float)(Lable_CNT-1)*100)*GRADE_11_CNT
									+( (float)(10-1)/(float)(Lable_CNT-1)*100)*GRADE_10_CNT
									+( (float)(9-1)/(float)(Lable_CNT-1)*100)*GRADE_09_CNT
									+( (float)(8-1)/(float)(Lable_CNT-1)*100)*GRADE_08_CNT
									+( (float)(7-1)/(float)(Lable_CNT-1)*100)*GRADE_07_CNT
									+( (float)(6-1)/(float)(Lable_CNT-1)*100)*GRADE_06_CNT
									+( (float)(5-1)/(float)(Lable_CNT-1)*100)*GRADE_05_CNT
									+ ( (float)(4-1)/(float)(Lable_CNT-1)*100)*GRADE_04_CNT
									+ ( (float)(3-1)/(float)(Lable_CNT-1)*100)*GRADE_03_CNT
									+ ( (float)(2-1)/(float)(Lable_CNT-1)*100)*GRADE_02_CNT;
						}
					}

					if("9".equals(aDataMap.getString("SUMMARY_CD")) || "10".equals(aDataMap.getString("SUMMARY_CD")) || "11".equals(aDataMap.getString("SUMMARY_CD"))){
						GRADE_AVG_VAL =GRADE_AVG_VAL/(float)GRADE_TOT_CNT;
					}
				}
				sGRADE_01_VAL = String.format("%.1f", GRADE_01_VAL);
				sGRADE_02_VAL = String.format("%.1f", GRADE_02_VAL);
				sGRADE_03_VAL =String.format("%.1f", GRADE_03_VAL);
				sGRADE_04_VAL =String.format("%.1f", GRADE_04_VAL);
				sGRADE_05_VAL =String.format("%.1f", GRADE_05_VAL);
				sGRADE_06_VAL = String.format("%.1f", GRADE_06_VAL);
				sGRADE_07_VAL = String.format("%.1f", GRADE_07_VAL);
				sGRADE_08_VAL =String.format("%.1f", GRADE_08_VAL);
				sGRADE_09_VAL =String.format("%.1f", GRADE_09_VAL);
				sGRADE_10_VAL =String.format("%.1f", GRADE_10_VAL);
				sGRADE_11_VAL =String.format("%.1f", GRADE_11_VAL);
				sGRADE_AVG_VAL =String.format("%.1f", GRADE_AVG_VAL);
				aTbSurveyUserEntryResult.setGRADE_01_VAL(sGRADE_01_VAL);
				aTbSurveyUserEntryResult.setGRADE_02_VAL(sGRADE_02_VAL);
				aTbSurveyUserEntryResult.setGRADE_03_VAL(sGRADE_03_VAL);
				aTbSurveyUserEntryResult.setGRADE_04_VAL(sGRADE_04_VAL);
				aTbSurveyUserEntryResult.setGRADE_05_VAL(sGRADE_05_VAL);
				aTbSurveyUserEntryResult.setGRADE_06_VAL(sGRADE_06_VAL);
				aTbSurveyUserEntryResult.setGRADE_07_VAL(sGRADE_07_VAL);
				aTbSurveyUserEntryResult.setGRADE_08_VAL(sGRADE_08_VAL);
				aTbSurveyUserEntryResult.setGRADE_09_VAL(sGRADE_09_VAL);
				aTbSurveyUserEntryResult.setGRADE_10_VAL(sGRADE_10_VAL);
				aTbSurveyUserEntryResult.setGRADE_11_VAL(sGRADE_11_VAL);
				aTbSurveyUserEntryResult.setGRADE_AVG_VAL(sGRADE_AVG_VAL);
				tbSurveyUserEntryResults.add(aTbSurveyUserEntryResult);
			}
		}
		return tbSurveyUserEntryResults;
	}

	public static TbSurveyUserEntryResult questionUsersAge (DataMap pDataMap) {
		TbSurveyUserEntryResult  aTbSurveyUserEntryResult = new TbSurveyUserEntryResult();

		if(pDataMap==null) {
			return aTbSurveyUserEntryResult;
		}

		aTbSurveyUserEntryResult =new TbSurveyUserEntryResult();
		aTbSurveyUserEntryResult.setUSER_NO(pDataMap.getInt("USER_NO"));
		aTbSurveyUserEntryResult.setSURVEY_GROUP_ID(pDataMap.getString("SURVEY_GROUP_ID"));
		aTbSurveyUserEntryResult.setSURVEY_ID(pDataMap.getString("SURVEY_ID"));
		aTbSurveyUserEntryResult.setSURVEY_YEAR(pDataMap.getString("SURVEY_YEAR"));
		aTbSurveyUserEntryResult.setSURVEY_DEGREE(pDataMap.getString("SURVEY_DEGREE"));
		aTbSurveyUserEntryResult.setSECTION_ID(pDataMap.getInt("SECTION_ID"));
		aTbSurveyUserEntryResult.setQUESTION_ID(pDataMap.getInt("QUESTION_ID"));
		aTbSurveyUserEntryResult.setGRADE_01_CNT(pDataMap.getInt("AGE1_CNT"));
		aTbSurveyUserEntryResult.setGRADE_02_CNT(pDataMap.getInt("AGE2_CNT"));
		aTbSurveyUserEntryResult.setGRADE_03_CNT(pDataMap.getInt("AGE3_CNT"));
		aTbSurveyUserEntryResult.setGRADE_04_CNT(pDataMap.getInt("AGE4_CNT"));
		aTbSurveyUserEntryResult.setGRADE_05_CNT(pDataMap.getInt("AGE5_CNT"));
		aTbSurveyUserEntryResult.setGRADE_06_CNT(pDataMap.getInt("AGE6_CNT"));
		aTbSurveyUserEntryResult.setGRADE_07_CNT(0);
		aTbSurveyUserEntryResult.setGRADE_08_CNT(0);
		aTbSurveyUserEntryResult.setGRADE_09_CNT(0);
		aTbSurveyUserEntryResult.setGRADE_10_CNT(0);
		aTbSurveyUserEntryResult.setGRADE_11_CNT(0);
		aTbSurveyUserEntryResult.setGRADE_TOT_CNT (pDataMap.getInt("TOTAL_CNT"));

		aTbSurveyUserEntryResult.setGRADE_01_VAL(pDataMap.getString("AGE1_P"));
		aTbSurveyUserEntryResult.setGRADE_02_VAL(pDataMap.getString("AGE2_P"));
		aTbSurveyUserEntryResult.setGRADE_03_VAL(pDataMap.getString("AGE3_P"));
		aTbSurveyUserEntryResult.setGRADE_04_VAL(pDataMap.getString("AGE4_P"));
		aTbSurveyUserEntryResult.setGRADE_05_VAL(pDataMap.getString("AGE5_P"));
		aTbSurveyUserEntryResult.setGRADE_06_VAL(pDataMap.getString("AGE6_P"));
		aTbSurveyUserEntryResult.setGRADE_07_VAL("0");
		aTbSurveyUserEntryResult.setGRADE_08_VAL("0");
		aTbSurveyUserEntryResult.setGRADE_09_VAL("0");
		aTbSurveyUserEntryResult.setGRADE_10_VAL("0");
		aTbSurveyUserEntryResult.setGRADE_11_VAL("0");
		aTbSurveyUserEntryResult.setGRADE_AVG_VAL("0");

		return aTbSurveyUserEntryResult;
	}


}
