<%@page import="com.whomade.kycarrots.framework.common.util.*"%>
<%@page import="java.io.*" %>
<%@page import="java.util.*" %>
<%@page import="java.net.*" %>
<%@page import="java.text.*" %>
<%@page import="edu.vt.ward.survey.*" %>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>

<jsp:useBean id="param" 				class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="survey" 				class="com.whomade.kycarrots.mgt.survey.vo.TbSurvey" scope="request"/>
<jsp:useBean id="tbQuestion"   			class="com.whomade.kycarrots.mgt.survey.vo.TbQuestion" scope="request"/>
<jsp:useBean id="QuestionResultsIds"  	type="java.util.List" class="java.util.ArrayList" scope="request"/>


<%
	String surveyId 	= param.getString("surveyId");

	iSurveyEntryForm entryForm = new iSurveyEntryForm (surveyId);
	entryForm.setExitSurvey(true);

	int sectionNr = -1;
	if ( param.getString("section") != null ) {
	 sectionNr = Integer.parseInt (param.getString( "section" ));
	}
	int questionNr = -1;
	if ( param.getString("question") != null ) {
	  questionNr = Integer.parseInt (param.getString("question"));
	}
	int optionNr = -1;
	
	System.out.println("option["+param.getString("option")+"]");

	if ( !StringUtil.isEmpty(param.getString("option"))) {
	  optionNr = Integer.parseInt (param.getString("option"));
	}
	
	String pageTitle = "View Question";


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
			<h3><small>&nbsp;View Summary&nbsp;<i class="icon-double-angle-right"></i></small>&nbsp;<img src="/common/img/current.png" style="width: 23px;height: 29px;">&nbsp;<%=pageTitle%></h3>
		</div><!--/.page-header-->
		<br>
		<div class="row-fluid">
		<div align="right">
      	<form method="post" action="/mgt/survey/viewResults.do">
		<input type="hidden" name="surveyId" value="<%=surveyId%>">
		<input type="submit" name="backToSummary" value="<<  summary화면으로 이동" class="btn btn-small btn-warning">&nbsp;&nbsp;
		</form>
		</div>
		</div>
		<br>

		<div class="row-fluid">
		<br>
		<h2 class="icon-caret-right blue">&nbsp;<%=survey.getSURVEY_TITLE()%></h1>
			<div class="tab-content">
				<table cellspacing="1" cellpadding="2" width="100%" border="0">
				<tbody>
				<tr>
					<td>
						<b><%=tbQuestion.getQUESTIONTEXT()%></b><br>
 						<% 
 							for(int i=0;i<QuestionResultsIds.size();i++){
 								DataMap	adataMap=(DataMap)QuestionResultsIds.get(i);
						%>
						<a href="/mgt/survey/view.do?surveyId=<%=surveyId%>&entryId=<%=adataMap.getString("RESULTS_ID")%>">#1:&nbsp;&nbsp;<%=adataMap.getString("INSERT_DATE_CHAR")%></a><br><span class="highlightresponses"><%=adataMap.getString("QUESTION_RESULT")%></span><br>
						<% 
							}
						%>
					</td>
				</tr>
				</tbody>
				</table>											
			</div><!--/tab-content-->
		</div><!--/row-fluid-->
	</div><!--/#page-content-->
</body>
</html>