<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/inc/meta.jspf" %>
<title>Error</title>
<link rel="stylesheet" href="<%=cssRoot%>common.css"  media="screen" type="text/css" />
<script type="text/javascript">
	//location.replace("/common/error/errorBase.jsp");
</script>
</head>
<body>
<div class="error-box">
	<p><img src="/common/images/common/error_500.jpg" alt="에러코드 500" /></p>
	<p class="txt">자세한 내용은 고객센터 <strong>info@nexin.kr</strong>로<br />문의 주시기 바랍니다.</p>
	<button type="button" class="" onclick="document.location.href='/'">Main Page &gt;</button>
</div>
</body>
</html>