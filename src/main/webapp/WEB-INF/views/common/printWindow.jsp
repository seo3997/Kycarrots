<%@page import="com.whomade.kycarrots.framework.common.util.file.vo.AtFileVO"%>
<%@page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>

	<script type="text/javascript">
		$(function(){
			
		});
	</script>
</head>
<body id="subBody">
	<div class="printArea">
		<%=param.getString("html") %>
	</div>
</body>
</html>
