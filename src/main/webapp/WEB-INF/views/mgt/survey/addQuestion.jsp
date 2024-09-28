<%@page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>


<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>

<% 
	String surveyId = param.getString("surveyId");
	String above    = param.getString("above");
	int sectionNr 	= Integer.parseInt (param.getString( "section" ));
	int questionNr 	= Integer.parseInt (param.getString( "question" ));
%>

<!doctype html>
<html lang="en"><head>
		<meta charset="utf-8">
		<title>Survey Tool</title>
		<meta name="description" content="Common Buttons &amp; Icons">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<!--basic styles-->
		<link href="/common/assets/css/bootstrap.min.css" rel="stylesheet">
		<link href="/common/assets/css/bootstrap-responsive.min.css" rel="stylesheet">
		<link rel="stylesheet" href="/common/assets/css/font-awesome.min.css">
		<!--[if IE 7]>
		  <link rel="stylesheet" href="/common/assets/css/font-awesome-ie7.min.css" />
		<![endif]-->
		<!--page specific plugin styles-->
		<!--fonts-->
		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300">
		<!--ace styles-->
		<link rel="stylesheet" href="/common/assets/css/ace.min.css">
		<link rel="stylesheet" href="/common/assets/css/ace-responsive.min.css">
		<link rel="stylesheet" href="/common/assets/css/ace-skins.min.css">
		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="/common/assets/css/ace-ie.min.css" />
		<![endif]-->
		<!--inline styles if any-->
		<script language="JavaScript" type="text/javascript">
		</script>
	</head>
<body>
	<div id="page-content" class="clearfix">
		<div>
			<h3><small>&nbsp;입력양식 편집 &nbsp;<i class="icon-double-angle-right"></i></small>&nbsp;<img src="/common/img/current.png" style="width: 23px;height: 29px;">&nbsp;질문 유형 선택</h3>
		</div><!--/.page-header-->
		<br>
		<div class="row-fluid">
			<div class="tab-content">
				<table cellspacing="1" cellpadding="2" width="100%" border="0">
				<tbody>
				<tr>
					<td bgcolor="#5D5D5D">
						<table cellspacing="0" cellpadding="0" width="100%" border="0">
						<tbody>
						<tr>
							<td style="COLOR: #ffffff" align="center">아래에서 질문 유형 하나를 선택하세요.</td>
							</tr></tbody></table></td></tr>
							<tr>
							<td bgcolor="#ffffff" valign="top" colspan="3">
							<table cellspacing="6" cellpadding="3" width="302" border="0">
							<tbody>
							<tr>
							<td valign="top" nowrap="" align="left"><a href="/mgt/survey/editQuestion.do?surveyId=<%=surveyId%>&section=<%=sectionNr%>&question=<%=questionNr%>&type=radio&above=<%=above%>">1)Multiple choice - only one response allowed<br><img border="0" alt="1)Multiple choice - only one response allowed" src="/common/img/questiontype_radio.gif" width="39" height="60"></a><br></td></tr>
							<tr>
							<td valign="top" nowrap="" align="left"><a href="/mgt/survey/editQuestion.do?surveyId=<%=surveyId%>&section=<%=sectionNr%>&question=<%=questionNr%>&type=checkbox&above=<%=above%>">2)Check all that apply<br><img border="0" alt="2)Check all that apply" src="/common/img/questiontype_checkbox.gif" width="39" height="60"></a></td></tr>
							<tr>
							<td valign="top" align="left"><a href="/mgt/survey/editQuestion.do?surveyId=<%=surveyId%>&section=<%=sectionNr%>&question=<%=questionNr%>&type=textline&above=<%=above%>">3)Short answer - one line<br><img border="0" alt="3)Short answer - one line" src="/common/img/questiontype_textline.gif" width="120" height="32"></a></td></tr>
							<tr>
							<td class="menuiteminactive" valign="top" nowrap="" align="left"><a href="/mgt/survey/editQuestion.do?surveyId=<%=surveyId%>&section=<%=sectionNr%>&question=<%=questionNr%>&type=textarea&above=<%=above%>">4)Comment/Essay question<br><img border="0" alt="4)Comment/Essay question" src="/common/img/questiontype_textarea.gif" width="145" height="48"></a></td></tr>
						<tr>
							<td valign="top" align="left"><a href="/mgt/survey/editQuestion.do?surveyId=<%=surveyId%>&section=<%=sectionNr%>&question=<%=questionNr%>&type=textimage&above=<%=above%>">5)Input Image<br><img border="0" alt="3)Short answer - one line" src="/common/img/questiontype_textline.gif" width="120" height="32"></a>
							</td>
						</tr>
						<tr>
							<td valign="top" align="left"><a href="/mgt/survey/editQuestion.do?surveyId=<%=surveyId%>&section=<%=sectionNr%>&question=<%=questionNr%>&type=textlocation&above=<%=above%>">6)Input Location<br><img border="0" alt="3)Short answer - one line" src="/common/img/questiontype_textline.gif" width="120" height="32"></a>
							</td>
						</tr>
						</tbody>
						</table>
					</td>
				</tr>
				</tbody>
				</table>											
			</div><!--/tab-content-->
		</div><!--/row-fluid-->
	</div><!--/#page-content-->
</body>

</html>
