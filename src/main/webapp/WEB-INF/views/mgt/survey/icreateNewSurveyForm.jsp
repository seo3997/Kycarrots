<%@page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>

<jsp:useBean id="resultList"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
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
			function fn_send()
			{
				if (form.surveyName.value=="")
				{
				  alert("Survey Name을 입력하세요");	
				  return;
				}	
				
				if(!confirm("전송 하시겠습니까?")) return ;
				form.submit();
			}
		</script>		
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
								Create New Survey
							</h3>
						</div>
	
						<div class="widget-body">
							<div class="widget-main no-padding">
								<form method="post" action="/mgt/survey/icreateNewSurvey.do" name="form">
								   <input type='hidden' name='createSurvey' />
									<fieldset>
										<label>Survey name (used for your reference; users do not see the survey name)</label>
										<input type="text" name="surveyName" id="surveyName"  placeholder="Type something&hellip;" />
										<span class="help-block">(1 to 30 characters) </span>
									</fieldset>

									<div class="form-actions center">
										<button type="button" name="btncreateSurvey"  class="btn btn-small btn-success" onclick="fn_send();">
											Submit
											<i class="icon-arrow-right icon-on-right bigger-110"></i>
										</button>
										<button  class="btn btn-small btn-error" onClick="self.close();">
											Cancel
											<i class="icon-arrow-right icon-on-right bigger-110"></i>
										</button>
									</div>
								</form>
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
		window.jQuery || document.write("<script src='assets/js/jquery-1.9.1.min.js'>"+"<"+"/script>");
	</script>
	<script src="/common/assets/js/bootstrap.min.js"></script>

	<!--page specific plugin scripts-->

	<!--ace scripts-->

	<script src="/common/assets/js/ace-elements.min.js"></script>
	<script src="/common/assets/js/ace.min.js"></script>

	<!--inline scripts related to this page-->
</body>

</html>
