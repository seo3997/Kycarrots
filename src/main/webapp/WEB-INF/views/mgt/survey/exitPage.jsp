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

<%
	String surveyId 	= param.getString("surveyId");

	DateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
	Date nowDate = new Date();
	String tempDate = sdFormat.format(nowDate);

%>


<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta charset="utf-8" />
		<title>Survey Tool</title>

		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />

		<!--basic styles-->

		<link href="/common/assets/css/bootstrap.min.css" rel="stylesheet" />
		<link href="/common/assets/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="/common/assets/css/font-awesome.min.css" />

		<!--[if IE 7]>
		  <link rel="stylesheet" href="/common/assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!--page specific plugin styles-->

		<!--fonts-->

		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />

		<!--ace styles-->

		<link rel="stylesheet" href="/common/assets/css/ace.min.css" />
		<link rel="stylesheet" href="/common/assets/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="/common/assets/css/ace-skins.min.css" />

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="/common/assets/css/ace-ie.min.css" />
		<![endif]-->

		<!--inline styles if any-->
	</head>

	<body>

	<div class="container-fluid" id="main-container">
		<div class="row-fluid">
			<!--PAGE CONTENT BEGINS HERE-->
			<div class="space-6"></div>
			<div class="row-fluid">
				<div class="span10 offset1">
					<div class="widget-box transparent invoice-box">
						<div class="widget-header widget-header-large">
							<h3 class="grey lighter pull-left position-relative">
								<i class="icon-leaf green"></i>
								Survey Entry 
							</h3>
	
							<div class="widget-toolbar no-border invoice-info">
								<span class="invoice-info-label">survey:</span>
								<span class="red"><%=survey.getSURVEY_TITLE()%></span>
	
								<br />
								<span class="invoice-info-label">Date:</span>
								<span class="blue"><%=tempDate%></span>
							</div>
	
							<div class="widget-toolbar hidden-480">
								<a href="#">
									<i class="icon-print"></i>
								</a>
							</div>
						</div>
	
						<div class="widget-body">
							<div class="widget-main padding-24">
								<div class="hr hr8 hr-double hr-dotted"></div>
								
								   <div class="row-fluid">
									<%
									  	//exit page
										//out.print ( entryForm.getExitPageHTML_New (2) );
									%>
								   </div><!--row-fluid-->
								  
									<!--  
									<div class="space"></div>
									<div class="hr hr8 hr-double hr-dotted"></div>
									<div class="space-6"></div>
									
									<div class="form-actions">
									<button class="btn btn-info" type="submit">
										<i class="icon-ok bigger-110"></i>
										Submit
									</button>
									&nbsp; &nbsp; &nbsp;
									<button class="btn" type="reset">
									<i class="icon-undo bigger-110"></i>
									Reset
									</button>
									</div>
									-->
									<div class="form-actions">
											Thank you for choosing whomade products.
											We believe you will be satisfied by our services.
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--PAGE CONTENT ENDS HERE-->
		</div><!--/row-->
	</div><!--/.fluid-container#main-container-->

	<!--basic scripts-->

	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript">
		window.jQuery || document.write("<script src='/common/assets/js/jquery-1.9.1.min.js'>"+"<"+"/script>");
	</script>
	<script src="/common/assets/js/bootstrap.min.js"></script>

	<!--page specific plugin scripts-->

	<!--ace scripts-->

	<script src="/common/assets/js/ace-elements.min.js"></script>
	<script src="/common/assets/js/ace.min.js"></script>

	<!--inline scripts related to this page-->
	</body>
</html>
