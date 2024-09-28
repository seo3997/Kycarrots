<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<%@ include file="/common/inc/meta.jspf" %>
<title>Error</title>
<link rel="stylesheet" href="<%=cssRoot%>common.css"  media="screen" type="text/css" />
</head>
<body>
<div class="error-box">
	<p><img src="/common/images/common/error_common.jpg" alt="사이트 에러" /></p>
	<p class="txt">자세한 내용은 고객센터 <strong>info@nexin.kr</strong>로<br />문의 주시기 바랍니다.</p>
	<button type="button" class="" onclick="document.location.href='/'">Main Page &gt;</button>
</div>
</body>
</html>