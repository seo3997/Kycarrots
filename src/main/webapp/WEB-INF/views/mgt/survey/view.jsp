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
<jsp:useBean id="QuestionResult"  		type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="QuestionResultLabel"  	type="java.util.List" class="java.util.ArrayList" scope="request"/>


<%
	String surveyId 	= param.getString("surveyId"); 
	String entryId      = param.getString("entryId"); 
	String entryDate    = param.getString("entryDate");

	iSurveyEntryForm entryForm = new iSurveyEntryForm (surveyId);
	entryForm.setExitSurvey(true);
	entryForm.load(SectionList,QuestionList,QuestionLableList);

	boolean entryExists = entryForm.readEntryResults(entryId,QuestionResult,QuestionResultLabel);
	
	
	String pageTitle = "View Entry";
	
	survey.singleEntry=true;

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
	   <link rel="stylesheet" href="/common/assets/css/colorbox.css" />
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
<form method="post" action="/mgt/survey/viewResults.do" name="form">
<input type="hidden" name="surveyId" value="<%=surveyId%>">

<div id="page-content" class="clearfix">
	<div>
		<h2><small>&nbsp;View Summary &nbsp;<i class="icon-double-angle-right"></i></small>&nbsp;<img src="/common/img/current.png" style="width: 23px;height: 29px;">&nbsp;<%=pageTitle%></h2>	
	</div>
	<div class="row-fluid">
	<div align="right">
	<input type="submit" name="backToSummary" value="<<  summary화면으로 이동" class="btn btn-small btn-warning">&nbsp;&nbsp;
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
					  Showing entry from <b><%=entryDate%>&nbsp; &nbsp; <a href="/mgt/survey/viewResults.do?surveyId=<%=surveyId%>" style="COLOR: #FFBB00">Show all entries</a>
					 &nbsp; &nbsp; <a href="deleteEntry.jsp?surveyId=<%=surveyId%>&entryId=<%=entryId%>" style="COLOR: #FFBB00">Delete this entry</a> <br>
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
						<br>
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
	<input type="submit" name="backToSummary" value="<<summary화면으로 이동" class="btn btn-small btn-warning" onclick="fn_done();">&nbsp;&nbsp;
	</div>
	 
</div><!--id="page-content"  -->	
</form>

		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
		<script type="text/javascript">
			window.jQuery || document.write("<script src='/common/assets/js/jquery-1.9.1.min.js'>"+"<"+"/script>");
		</script>
		<script src="/common/assets/js/bootstrap.min.js"></script>

		<!--page specific plugin scripts-->

		<script src="/common/assets/js/jquery.colorbox-min.js"></script>

		<!--ace scripts-->

		<script src="/common/assets/js/ace-elements.min.js"></script>
		<script src="/common/assets/js/ace.min.js"></script>

		<script src="/common/assets/js/ace-elements.min.js"></script>
		<script src="/common/assets/js/ace.min.js"></script>

		<!--inline scripts related to this page-->

		<script type="text/javascript">
		$(function() {
			var colorbox_params = {
			reposition:true,
			scalePhotos:true,
			scrolling:false,
			previous:'<i class="icon-arrow-left"></i>',
			next:'<i class="icon-arrow-right"></i>',
			close:'&times;',
			current:'{current} of {total}',
			maxWidth:'100%',
			maxHeight:'100%',
			onOpen:function(){
				document.body.style.overflow = 'hidden';
			},
			onClosed:function(){
				document.body.style.overflow = 'auto';
			},
			onComplete:function(){
				$.colorbox.resize();
			}
		};

		$('.ace-thumbnails [data-rel="colorbox"]').colorbox(colorbox_params);
		$("#cboxLoadingGraphic").append("<i class='icon-spinner orange'></i>");//let's add a custom loading icon

		})
		function fn_popLocation(platitude,plongitude) {
    		var latitude =platitude;
    		var longitude=plongitude;
    		var goUrl="location.jsp?latitude="+latitude+"&longitude="+longitude;
    		SmallWin = window.open(goUrl, 'popLocation','scrollbars=yes,resizable=yes,toolbar=no,height=600,width=800');
			SmallWin.opener.name = "SubLocation";
		}		
		
	</script>

</body>
</html>