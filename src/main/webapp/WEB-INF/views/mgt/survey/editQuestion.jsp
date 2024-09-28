<%@page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@page import="edu.vt.ward.survey.iSurveyEntryForm"%>
<%@page import="edu.vt.ward.survey.iSection"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>


<jsp:useBean id="param" 				class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="tbQuestion"   			class="com.whomade.kycarrots.mgt.survey.vo.TbQuestion" scope="request"/>


<% 
    String surveyId = param.getString("surveyId");
	int sectionNr 	= Integer.parseInt (param.getString( "section" ));
	int questionNr 	= Integer.parseInt (param.getString( "question" ));
    String type 	= param.getString("type");
	String above    = param.getString("above");

    iSurveyEntryForm entryForm = new iSurveyEntryForm (surveyId);
    String inputErrorId = null;
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
			<h3><small>&nbsp;입력양식 편집 &nbsp;<i class="icon-double-angle-right"></i></small>&nbsp;<img src="/common/img/current.png" style="width: 23px;height: 29px;">&nbsp;Edit</h3>
		</div><!--/.page-header-->
		<br>
		<div class="row-fluid">
			<div class="tab-content">
				<table cellspacing="1" cellpadding="2" width="100%" border="0">
				<tr>
				<td>
				<%
				  iSection section = new iSection(entryForm);
				  out.print (section.getQuestion ( questionNr, type,tbQuestion ).getEditFormHTML_New ( sectionNr, questionNr, type,above, inputErrorId ) );
				%>
				</td>
				</tr>
				</table>
			</div><!--/tab-content-->
			<div class="vspace-6"></div>
		</div><!--/row-fluid-->
	</div><!--/#page-content-->

</body>

</html>
