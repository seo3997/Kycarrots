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
<jsp:useBean id="tbSection"   			class="com.whomade.kycarrots.mgt.survey.vo.TbSection" scope="request"/>


<% 
    String surveyId = param.getString("surveyId");
	int sectionNr 	= Integer.parseInt (param.getString( "section" ));

    iSurveyEntryForm entryForm = new iSurveyEntryForm (surveyId);
    String inputErrorId = null;
    
    String Stitle = "Section Edit";
    String Scont = tbSection.getSECTION_TITLE();

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
	</head>
<body>
	<div id="page-content" class="clearfix">
		<div>
			<h3><small>&nbsp;입력양식 편집 &nbsp;<i class="icon-double-angle-right"></i></small>&nbsp;<img src="/common/img/current.png" style="width: 23px;height: 29px;">&nbsp;Section Edit</h3>
		</div><!--/.page-header-->
		<br>
		<div class="row-fluid">
			<div class="tab-content">
				<table cellspacing="1" cellpadding="2" width="100%" border="0">
				<tr>
				<td>
					<form action="/mgt/survey/editSection_ok.do" method="post">
					<input type="hidden" name="surveyId" value="<%=surveyId%>">
					<input type="hidden" name="section" value="<%=sectionNr%>">
					<b>Text:</b> 
					<input type="radio" name="texttype" value="plain" checked> Plain Text &nbsp;&nbsp;&nbsp;
					<!--  
					<input type="radio" name="texttype" value="html" checked> HTML
					-->
					<br><textarea name="comment" wrap="virtual" cols="60" class="span12" rows="15"><%=Scont%></textarea>
					<br>
					<br>
					<input type="submit" class="btn btn-primary" name="save" value="&nbsp;&nbsp;&nbsp;OK&nbsp;&nbsp;&nbsp;">&nbsp;&nbsp;<input type="submit" class="btn" name="cancel" value="Cancel">
					</form>
				</td>
				</tr>
				</table>
			</div><!--/tab-content-->
			<div class="vspace-6"></div>
		</div><!--/row-fluid-->
	</div><!--/#page-content-->
</body>
</html>