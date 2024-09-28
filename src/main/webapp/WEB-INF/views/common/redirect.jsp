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
	<title></title>
	<%@ include file="/common/inc/cssScript.jspf" %>
	<script type="text/javascript">
		function f_init(){
			document.aform.target = "_self";
			document.aform.submit();
			<%if(!"".equals(param.getString("redirectMsg"))){%>
			alert('<%=param.getString("redirectMsg")%>');	
			<%}%>
		}
	</script> 
</head>

<body onload="f_init();">
<form name = "aform" method = "post" action = "<%=param.getString("redirectUrl")%>">
<div style="display:none">
<%=SysUtil.createInputObj(param)%>
</div>
</form>
</body>
</html>