<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.SysUtil" %>
<%@ page import="com.whomade.kycarrots.framework.common.constant.Const" %>
<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<title>close</title>
	<%@ include file="/common/inc/cssScript.jspf" %>
	<script type="text/javascript">
<%
	String message = (String)request.getSession().getAttribute("message");
	if(message != null && !message.equals("")){
%>
		alert("<%=message%>");
<%
		request.getSession().removeAttribute("message");
	}
%>
// 		window.open('', '_self', '');
		window.close();
		
	</script> 
</head>

<body>
</body>
</html>