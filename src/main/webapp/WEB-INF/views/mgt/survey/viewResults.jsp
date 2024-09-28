<%@page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@page import="edu.vt.ward.survey.iSurveyEntryForm"%>
<%@page import="java.io.*" %>
<%@page import="java.util.*" %>
<%@page import="java.net.*" %>
<%@page import="java.text.*" %>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>

<jsp:useBean id="param" 				class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="survey" 				class="com.whomade.kycarrots.mgt.survey.vo.TbSurvey" scope="request"/>
<jsp:useBean id="SectionList"   		type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="QuestionList"  		type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="QuestionLableList"  	type="java.util.List" class="java.util.ArrayList" scope="request"/>


<%
	String surveyId 	= param.getString("surveyId");

	iSurveyEntryForm entryForm = new iSurveyEntryForm (surveyId);
	entryForm.setExitSurvey(true);
	entryForm.load(SectionList,QuestionList,QuestionLableList);

	String pageTitle = "View Summary";
	
	int iResultCnt=survey.getNUMENTRIES();


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
	  	<style>
	    	.globalsettings { font-family : Verdana, Geneva, Arial, Helvetica, sans-serif; background-color : #ffffff; color : #000000 }
	  	</style>
	</head>
<body>
<form method="post" action="<%=HttpUtils.getRequestURL ( request )%>" name="form">
<input type="hidden" name="surveyId" value="<%=surveyId%>">

<div id="page-content" class="clearfix">
	<div>
		<h2><img src="/common/img/current.png" style="width: 23px;height: 29px;">&nbsp;<%=pageTitle%></h2>	
	</div>
	<input type="hidden" name="surveyId" value="<%=surveyId%>">
	<div class="row-fluid">
	<div align="right">
	<input type="button" name="Close" value="Close" class="btn btn-small btn-warning" onclick="self.close();">&nbsp;&nbsp;
	</div>
	</div>

	<div class="row-fluid">
		<br>
		<h2 class="icon-caret-right blue">&nbsp;<%=survey.getSURVEY_TITLE()%></h1>
		<div class="tab-content">							

			<div align="left">
				<table cellspacing="1" cellpadding="2" width="100%" border="0">
				<tbody>
				<tr>
					<td align="center" bgcolor="#5D5D5D" style="COLOR: #ffffff">
					  설문 조사는 <b><%=iResultCnt%></b> 명의 답변이 있습니다.&nbsp; &nbsp; 
					  <%if("".equals(param.getString("surveyGroupId")))
					  	{ 
					  %>
					  <a href="/mgt/survey/viewResultsDetails.do?surveyId=<%=surveyId%>" style="COLOR: #FFBB00">모든 항목의 세부 정보를 표시</a>
					  <%
					  	}else{ 
					  %>
					  <a href="/mgt/survey/viewResultsDetails.do?surveyId=<%=surveyId%>&userNo=<%=param.getString("userNo")%>&surveyGroupId=<%=param.getString("surveyGroupId")%>&surveyYear=<%=param.getString("surveyYear")%>&surveyDegree=<%=param.getString("surveyDegree")%>" style="COLOR: #FFBB00">모든 항목의 세부 정보를 표시</a>
					  <%
					  	} 
					  %>

					  <br>
				
					</td>
				</tr>
				</tbody>
				</table>		 
				<% 
				    int i=0;
				    int j=0;
				    int QuestionId=0;
				    
					for (i=0;i<entryForm.getNumSections();i++){
				%>
					<br>
					<table cellspacing="0" cellpadding="3" border="0" width="100%">
					<tbody>
					<tr>
						<td class="globalsettings"><img src="/common/img/text.png" width = "4%" >&nbsp;<%=entryForm.getSection(i).getTitle()%></td>
					</tr>
					</tbody>
					</table>
					<%  
						for (j = 0; j < entryForm.getSection(i).getNumQuestions (); j++ ) {
							QuestionId=entryForm.getSection(i).getQuestion (j).getQuestionId();
					%>
					<table cellspacing="0" cellpadding="3" border="0" width="100%">
					<tbody>
					<tr>
						<td class="globalsettings">
						<b><%=entryForm.getSection(i).getQuestion(j).getText()%></b>
						<%= entryForm.getSection(i).getQuestion (j).getHTML_New(survey,entryForm.modeResultsSummary,i, QuestionId )%>
						</td>
					</tr>
					</tbody>
					</table>

					<%  
						}
					%>
		
				<% 
				    }
				%>
		
			</div><!-- left -->
		</div><!-- tab-content -->
	</div><!-- row-fluid -->
	 
	 
	<br>
	<div align="right">
	<input type="button" name="Close" value="Close" class="btn btn-small btn-warning" onclick="self.close();">&nbsp;&nbsp;
	</div>
	 
</div><!--id="page-content"  -->	
</form>
</body>
</html>