<%@page import="org.apache.poi.xssf.usermodel.XSSFCell"%>
<%@page import="org.apache.poi.xssf.usermodel.XSSFRow"%>
<%@page import="org.apache.poi.xssf.usermodel.XSSFCellStyle"%>
<%@page import="org.apache.poi.xssf.usermodel.XSSFSheet"%>
<%@page import="org.apache.poi.xssf.usermodel.XSSFWorkbook"%>
<%@page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@page import="org.apache.poi.ss.util.Region"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFCell"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFRow"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFCellStyle"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFSheet"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFWorkbook"%>
<%@page import="com.whomade.kycarrots.framework.common.util.ExcelUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLEncoder"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/inc/common.jspf" %>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="excelDataList"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="excelInfo" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<% 
// 	String header = request.getHeader("User-Agent");
	// 파일 설정
	String file_nm = excelInfo.getString("file_nm") + ".xlsx";
	
// 	if(header.indexOf("MSIE") > -1){ response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(file_nm, "UTF-8")); }
// 	else { response.setHeader("Content-Disposition", "attachment; filename=" + new String(file_nm.getBytes(),"ISO-8859-1")); }
	
// 	response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(file_nm, "UTF-8"));
	
	String header = request.getHeader("User-Agent");
	String browserType = "";
	String encodeFileName = "";
	if (header.indexOf("MSIE") > -1) {
		encodeFileName = URLEncoder.encode(file_nm, "UTF-8").replaceAll("\\+", "%20");
	} 
	else if (header.indexOf("Chrome") > -1) {
// 		encodeFileName = new String(file_nm.getBytes("UTF-8"), "8859_1");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < file_nm.length(); i++) {
			char c = file_nm.charAt(i);
			if (c > '~') {
				sb.append(URLEncoder.encode("" + c, "UTF-8"));
			} else {
				sb.append(c);
			}
		}
		encodeFileName = sb.toString();
	} 
	else if (header.indexOf("Opera") > -1) {
		encodeFileName = new String(file_nm.getBytes("UTF-8"), "8859_1");
	}
	else{
		encodeFileName = new String(file_nm.getBytes("UTF-8"), "8859_1");
	}
	
	response.setHeader("Content-Disposition","attachment;filename="+encodeFileName);
	response.setHeader("Content-Description", "JSP Generated Data");
	
	// 엑셀 파일 
	ExcelUtil excelUtil = new ExcelUtil();
	excelUtil.createWorkbookXLSX();
	excelUtil.createSheetXLSX();
	
	XSSFWorkbook x_wb = excelUtil.getWorkbookXLSX();
	XSSFSheet x_st = excelUtil.getX_st();
	
	// 스타일 설정
	XSSFCellStyle h_style = x_wb.createCellStyle();
	h_style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
	h_style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
	h_style.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
	h_style.setBorderTop(XSSFCellStyle.BORDER_THIN);
	h_style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	h_style.setBorderRight(XSSFCellStyle.BORDER_THIN);
	
	/////////////////////// header 정의 /////////////////////////////////////////////////////////////////////////////////////////
	List e_header = (List) excelDataList.get(0);
	
	// header 컬럼 을 미리 정의(셀 병합시 border가 제대로 적용되지 않기에)
	for(int i = 0; i < 1; i++)
	{
		XSSFRow r = x_st.createRow(i);
		for(int j = 0; j < e_header.size(); j++)
		{
			XSSFCell c = r.createCell(j);
			c.setCellStyle(h_style);
			c.setCellType(XSSFCell.CELL_TYPE_STRING);
		}
	}
	
	// header 값 셋팅
	XSSFRow r = x_st.getRow(0);
	XSSFCell c = null;
	
	for(int i = 0; i < e_header.size(); i++){
		c = r.getCell(i);
		c.setCellValue((String)e_header.get(i));
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 내용추가(통계수치)
	// 통계 스타일
	XSSFCellStyle c_style = x_wb.createCellStyle();
	c_style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
	c_style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
	c_style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
	c_style.setBorderTop(XSSFCellStyle.BORDER_THIN);
	c_style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	c_style.setBorderRight(XSSFCellStyle.BORDER_THIN);
	
	// 헤더의 정보를 삭제
	excelDataList.remove(0);
	excelUtil.addDataXLSX(excelDataList, c_style);
	
	// 시트 추가 설정
// 	for(int i = 0; i < e_header.size(); i++)
// 	{
// 		// 컬럼 사이즈 내용보다 약간 크게 설정
// 		x_st.autoSizeColumn(i);
// 		x_st.setColumnWidth(i, x_st.getColumnWidth(i) + 1024);
// 	}
	if(excelInfo.getString("type").equals("ssoUser")){
		for(int i = 0; i < e_header.size(); i++)
		 	{
	 		// 컬럼 사이즈 내용보다 약간 크게 설정
		 	x_st.autoSizeColumn(i);
	 		x_st.setColumnWidth(i, x_st.getColumnWidth(i) + 1024);
		 	}
	}
	
	// jsp 에서 이미지나 다운로드를 처리하기 위해 OutputStream 을 받아서 처리하는데, jsp에서 servlet으로 변환될때 내부적으로 out 객체가 생성된다고 합니다.
	out.clear();
	out = pageContext.pushBody();
	///// 2줄 추가
	
	x_wb.write(response.getOutputStream());
%>