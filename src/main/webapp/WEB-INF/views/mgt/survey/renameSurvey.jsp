<%@page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>


<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="survey" 				class="com.whomade.kycarrots.mgt.survey.vo.TbSurvey" scope="request"/>

<% 
	String surveyId = param.getString("surveyId");

	String pageTitle = "Edit Survey Name";
    int minLenSurveyName=1;
	int maxLenSurveyName=30;
	
	if (!param.getString("save").equals("")) {
		 out.println("<script>opener.location.href='/mgt/survey/selectPageListSurvey.do'</script>");
		 out.println("<script>self.close();</script>");
		 return;
	}

	
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
			<h3>&nbsp;<img src="/common/img/current.png" style="width: 23px;height: 29px;">&nbsp;<%=pageTitle%></h3>
		</div><!--/.page-header-->
		<br>
		<div class="row-fluid">
			<div class="tab-content">
				<table cellspacing="1" cellpadding="2" width="100%" border="0">
				<tbody>
				<tr>
					<td>
						<form method="post" action="/mgt/survey/renameSurvey_ok.do" name="form">
						<b><%="Survey name:"%></b>
						<font color="#999999"><%="(used for your reference; users do not see the survey name)"%></font><br>
						<input type="text" name="surveyName" value="<%=survey.getSURVEY_TITLE()%>" size="<%=maxLenSurveyName+10%>" maxlength="<%=maxLenSurveyName%>" tabIndex="1">
						<font color="#999999">(<%=minLenSurveyName%> <%="to"%> <%=maxLenSurveyName%> <%="characters"%>)</font>
						<br>
						<br>
						<%
						  if ( surveyId != null )
						    out.print ( "<input type=\"hidden\" name=\"surveyId\" value=\"" + surveyId + "\">\n" );
						%>
						<input type="submit" name="save" value="OK" class="btn btn-primary">&nbsp;&nbsp;<input type="button" name="cancel" value="Cancel" class="btn" onClick="self.close();">
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