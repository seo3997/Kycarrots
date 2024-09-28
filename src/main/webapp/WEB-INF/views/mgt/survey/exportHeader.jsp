<%@page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@page import="edu.vt.ward.survey.iSurveyEntryForm"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>

<jsp:useBean id="param" 				class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="survey" 				class="com.whomade.kycarrots.mgt.survey.vo.TbSurvey" scope="request"/>



<%
	String surveyId 	= param.getString("surveyId");
	System.out.println("editEntryForm surveyId["+surveyId+"]*********");
	String pageTitle = "설문결과 내보내기";
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
		<script language="JavaScript" type="text/javascript"><!--
		function isIE4()
		{ return( navigator.appName.indexOf("Microsoft") != -1 && (navigator.appVersion.charAt(0)=='4') ); }
		
		function new_window(freshurl) {
		  SmallWin = window.open(freshurl, 'Export','scrollbars=yes,resizable=yes,status=yes,toolbar=no,menubar=yes,left=150,top=100,height=200,width=350');
		  if (!isIE4())	{
		    if (window.focus) { SmallWin.focus(); }
		  }
		  if (SmallWin.opener == null) SmallWin.opener = window;
		  SmallWin.opener.name = "Main";
			return SmallWin;
		}
		
		function submitForm(form) {
		  w.close();
			form.submit();
		}
		
		var exportUrl = "/mgt/survey/export.do?surveyId=<%=surveyId%>&userNo=<%=param.getString("userNo")%>&surveyGroupId=<%=param.getString("surveyGroupId")%>&surveyYear=<%=param.getString("surveyYear")%>&surveyDegree=<%=param.getString("surveyDegree")%>";
		//-->
		</script>

	</head>
<body>
	<div id="page-content" class="clearfix">
		<div>
			<h3><img src="/common/img/current.png" style="width: 23px;height: 29px;">&nbsp;<%=pageTitle%></h3>
		</div><!--/.page-header-->
		<br>
		<div class="row-fluid">
			<div class="tab-content">
				<table cellspacing="1" cellpadding="2" width="100%" border="0">
				<tbody>
				<tr>
					<td>
						<form name="exportinfo" target="_top" method="post" action="/mgt/survey/exportResults.do">
						 <input type="hidden" name="surveyId" value="<%=surveyId%>">
						 <input type='hidden' name='userNo' value='<%=param.getString("userNo")%>'>
						 <input type='hidden' name='surveyGroupId' value='<%=param.getString("surveyGroupId")%>'>
						 <input type='hidden' name='surveyYear' value='<%=param.getString("surveyYear")%>'>
						 <input type='hidden' name='surveyDegree' value='<%=param.getString("surveyDegree")%>'>
						  In order to export your data to a file on your computer:<br><br>
						  1) <a href="JavaScript:w = new_window(exportUrl); w.focus()">Click here to display the data in a pop-up window</a> <br> 
						  2) Wait until the data is fully loaded in the pop-up window (see status bar) <br> 
						  3) In the pop-up window select the &quot;File/Save As...&quot; command <br>
						  4) Save your data as a "Text File" on your computer <br><br>
						   After saving it on your computer you can import the data into Microsoft Excel for example.<br>
						 To import data into a spreadsheet, use Excel's import menu command: 
						 &quot;Data/Get External Data/Import Text File&quot;
  						 <br>
						 <br>
						 <input type="submit" name="backToMenu" value="Done" class="btn" onClick="javascript:submitForm(document.exportinfo);">&nbsp;&nbsp;
						</form>
					</td>
				</tr>
				</tbody>
				</table>											
			</div><!--/tab-content-->
		</div><!--/row-fluid-->
	</div><!--/#page-content-->
</body>
</html>