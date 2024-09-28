<%@ page language="java" contentType="application/vnd.ms-excel;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/inc/docType.jspf" %>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@ page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@ page import="java.net.URLEncoder"%>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<%
// 	String header = request.getHeader("User-Agent");
// 	String browserType = "";
// 	String encodeFileName = "";
// 	if (header.indexOf("MSIE") > -1) {
// 		encodeFileName = URLEncoder.encode(param.getString("file_name"), "UTF-8").replaceAll("\\+", "%20");
// 	} 
// 	else if (header.indexOf("Chrome") > -1) {
// 		encodeFileName = new String(param.getString("file_name").getBytes("UTF-8"), "8859_1"); 
// 	} 
// 	else if (header.indexOf("Opera") > -1) {
// 		encodeFileName = new String(param.getString("file_name").getBytes("UTF-8"), "8859_1");
// 	}
// 	else{
// 		encodeFileName = new String(param.getString("file_name").getBytes("UTF-8"), "8859_1");
// 	}
	
	String header = request.getHeader("User-Agent");
	String browserType = "";
	String encodeFileName = "";
	if (header.indexOf("MSIE") > -1) {
		encodeFileName = URLEncoder.encode(param.getString("file_name"), "UTF-8").replaceAll("\\+", "%20");
	} 
	else if (header.indexOf("Chrome") > -1) {
// 		encodeFileName = new String(file_nm.getBytes("UTF-8"), "8859_1");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < param.getString("file_name").length(); i++) {
			char c = param.getString("file_name").charAt(i);
			if (c > '~') {
				sb.append(URLEncoder.encode("" + c, "UTF-8"));
			} else {
				sb.append(c);
			}
		}
		encodeFileName = sb.toString();
	} 
	else if (header.indexOf("Opera") > -1) {
		encodeFileName = new String(param.getString("file_name").getBytes("UTF-8"), "8859_1");
	}
	else{
		encodeFileName = new String(param.getString("file_name").getBytes("UTF-8"), "8859_1");
	}
	
	response.setHeader("Content-Disposition","attachment;filename="+encodeFileName);
	response.setHeader("Content-Description", "JSP Generated Data");
%>
<%	
//******************************MS excel******************************
// MS excel로 다운로드/실행, filename에 저장될 파일명을 적어준다.
	//response.setHeader("Content-Disposition","attachment;filename="+new String(param.getString("file_name").getBytes("UTF-8"), "8859_1"));
	response.setHeader("Content-Disposition","attachment;filename="+encodeFileName);
	response.setHeader("Content-Description", "JSP Generated Data");
     
// ↓ 이걸 풀어주면 열기/저장 선택창이 뜨는 게 아니라 그냥 바로 저장된다.
//  response.setContentType("application/vnd.ms-excel");
//*********************************************************************
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="application/vnd.ms-excel;charset=UTF-8" />
<style type="text/css">  
<!--   
 #container {   
  width: 100%;   
  font-size: 12px;   
  line-height: 20px;   
  }   
 #container table {   
  width: 100%;   
  border-collapse: collapse;   
  font-size: 12px;   
  line-height: 20px;   
  }   
 #container th {   
  padding: 10px;   
  border: #d3d3d3 solid 1px;   
  text-align: center;   
  background: #F4F4FF;
	mso-number-format:"\@";
  }     
 #container td {   
  padding: 10px;   
  border: #d3d3d3 solid 1px;   
  text-align: center;   
	mso-number-format:"\@";
  }
.alignL{text-align:left !important}
-->  
</style>
</head>
<div id="container">
<%=param.getString("excel_data")%>
</div>
</html>