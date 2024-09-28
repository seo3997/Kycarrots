<%@page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@page import="com.whomade.kycarrots.framework.common.util.EgovPropertiesUtil"%>

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
<jsp:useBean id="surveyUser" 			class="com.whomade.kycarrots.mgt.survey.vo.TbSurveyUser" scope="request"/>
<jsp:useBean id="SectionList"   		type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="QuestionList"  		type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="QuestionLableList"  	type="java.util.List" class="java.util.ArrayList" scope="request"/>


<%
	String surveyId 	= param.getString("surveyId");
	String surveyMode 	= param.getString("surveyMode");
	
	String surveyModeName 	= "Entry";
	
	if ("0".equals(surveyMode))
		surveyModeName 	= "Entry";
	else
		surveyModeName 	= "Preview";

	DateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
	Date nowDate = new Date();
	String tempDate = sdFormat.format(nowDate);
	
	System.out.println("editEntryForm surveyId["+surveyId+"]*********");
	System.out.println("editEntryForm surveyModeName["+surveyModeName+"]*********");
	System.out.println("**********************************************************");
    System.out.println("Globals.mainUrl["+EgovPropertiesUtil.getProperty("Globals.mainUrl")+"]");
	System.out.println("**********************************************************");

	iSurveyEntryForm entryForm = new iSurveyEntryForm (surveyId);
	entryForm.setExitSurvey(true);
	entryForm.load(SectionList,QuestionList,QuestionLableList);

    String sActionUrl=EgovPropertiesUtil.getProperty("Globals.mainUrl")+"mgt/survey/ientry.do";
    
    String sSurveyUserTitle="";
	int QuestionId=0;


%>


<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta charset="utf-8" />
		<title>MySurvey</title>

		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />

		<!--basic styles-->

		<link href="/common/assets/css/bootstrap.min.css" rel="stylesheet" />
		<link href="/common/assets/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="/common/assets/css/font-awesome.min.css" />
  		<link rel="stylesheet" href="/common/assets/css/jquery.printpage.css" type="text/css" media="screen" />

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
    	<script src="/common/assets/js/jquery-1.9.1.min.js"></script>
 		<script type="text/javascript" src="/common/assets/js/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="/common/assets/js/jquery.fileupload.js"></script>
		<script src="/common/assets/js/jquery.iframe-transport.js"></script>

		<script src="/common/assets/js/bootstrap.min.js"></script>
		<script src="/common/assets/js/ace-elements.min.js"></script>
		<script src="/common/assets/js/ace.min.js"></script>

		<!--inline styles if any-->
		<script language="JavaScript" type="text/javascript">
			function f_savechk() {
			    <%			    
				    for (int i=0;i<entryForm.getNumSections();i++){
						for (int j = 0; j < entryForm.getSection(i).getNumQuestions (); j++ ) {
							QuestionId=entryForm.getSection(i).getQuestion(j).getQuestionId();
							
							if ( entryForm.getSection(i).getQuestion(j).getClass().getName().equals("edu.vt.ward.survey.InputRadio") ) {
				%>
				var itemchecked=$('input:radio[name=q_<%=i%>_<%=QuestionId%>]').is(':checked');
			    if(!itemchecked){
			    	alert("항목을 다 체크 하셔야 합니다.\n섹션[<%=(i+1)%>]항목[<%=QuestionId%>]");
					return false;
			    }
			    <%
							}
			    %>
			    <%
							if ( entryForm.getSection(i).getQuestion(j).getClass().getName().equals("edu.vt.ward.survey.InputTextline")) {
								if("2".equals(entryForm.getSection(i).getQuestion(j).getSummaryCd())){
								System.out.println("SummaryCd["+entryForm.getSection(i).getQuestion(j).getSummaryCd()+"]");
			    %>
				var itemchecked=$('input[name=q_<%=i%>_<%=QuestionId%>]').val();
			    if(itemchecked==""){
			    	alert("나이을 입력 하셔야 합니다.\n섹션[<%=(i+1)%>]항목[<%=QuestionId%>]");
					return false;
			    }

			    <%	
								}
							}
			    %>
			    <%
						}
					}
				%>
			    return true;
			}
			
			function fn_save()
			{
				if(!confirm("전송 하시겠습니까?")) return ;
				if(f_savechk()){
					form.submit();
				}else{
					return false;
				}
			}
		</script>		
	</head>

	<body>
	<div id="page-content" class="clearfix">
		<div class="row-fluid">
			<!--PAGE CONTENT BEGINS HERE-->
			<div class="space-6"></div>
			<div class="row-fluid">
				<div class="span10 offset1">
					<div class="widget-box transparent invoice-box">
						<div class="widget-header widget-header-large">
							<h3 class="grey lighter pull-left position-relative">
								<i class="icon-leaf green"></i>
								Survey <%=surveyModeName%>
							</h3>
	
							<div class="widget-toolbar no-border invoice-info">
								<span class="invoice-info-label">survey:</span>
								<span class="red"><%=survey.getSURVEY_TITLE()%></span>
	
								<br />
								<span class="invoice-info-label">Date:</span>
								<span class="blue"><%=tempDate%></span>
							</div>
	
							<div class="widget-toolbar hidden-480">
								<span class="print">print</span>
								<!--  
								<a href="#">
									<i class="icon-print"></i>
								</a>
								-->
							</div>
						</div>

<form action='<%=sActionUrl %>' method='post' name='form' >
<input type='hidden' name='surveyId' value='<%=surveyId%>'>
<input type='hidden' name='USER_NO' value='<%=param.getString("userNo")%>'>
<input type='hidden' name='SURVEY_GROUP_ID' value='<%=param.getString("surveyGroupId")%>'>
<input type='hidden' name='SURVEY_YEAR' value='<%=surveyUser.getSURVEY_YEAR()%>'>
<input type='hidden' name='SURVEY_DEGREE' value='<%=surveyUser.getSURVEY_DEGREE()%>'>
<input type='hidden' name='javascriptEnabled' value='1'>
<input type='hidden' name='save' value='Submit'>
	
						<div class="widget-body">
							<div class="widget-main padding-24">
								<div class="hr hr8 hr-double hr-dotted"></div>
								
								<div class="row-fluid">
								<% 
										if(!param.getString("surveyGroupId").equals("")){
											sSurveyUserTitle=surveyUser.getSURVEY_GROUP_NM()+" "+surveyUser.getSURVEY_YEAR()+"년도"+surveyUser.getSURVEY_DEGREE()+"차";
								%>
									<div class="alert alert-warn" style="margin-bottom:0px">
										<%=sSurveyUserTitle%> <%=survey.getSURVEY_TITLE()%>
									</div>
								<% 
										}
								%>
<% 
	for (int i=0;i<entryForm.getNumSections();i++){
%>
									<!-- section Title-->
									<br>
									<div class="alert alert-success" style="margin-bottom:0px">
										Sec<%=i+1%> &nbsp;<%=entryForm.getSection(i).getTitle()%> 
									</div>
									<!-- section Title -->
									<!-- section Content -->
									<div class="row-fluid">
										<dl id="dt-list-1">
									<%  
										for ( int j = 0; j < entryForm.getSection(i).getNumQuestions (); j++ ) {
											QuestionId=entryForm.getSection(i).getQuestion (j).getQuestionId();
									%>
											<%= entryForm.getSection(i).getQuestion (j).getHTML_New(survey,2,i, QuestionId )%>
									<%  
										}
									%>
										</dl>	
									</div>
									<!-- section Content -->
<% 
    }
%>
								  </div><!--row-->
									<div class="space"></div>
									<div class="hr hr8 hr-double hr-dotted"></div>
									<div class="space-6"></div>
									<% 
										if ("0".equals(surveyMode)){
									%>
									<div class="form-actions">
									<button class="btn btn-info" type="button" name="save" onClick="fn_save();">
										<i class="icon-ok bigger-110"></i>
										Submit
									</button>
									&nbsp; &nbsp; &nbsp;
									<button class="btn" type="reset">
									<i class="icon-undo bigger-110"></i>
									Reset
									</button>
									</div>

									<% 
										}	
									%>
									<div class="form-actions">
											Thank you for choosing whomade products.
											We believe you will be satisfied by our services.
									</div>
									
								</div>
							</div>
						</div>
</form>					
					</div>
				</div>
			</div>
			<!--PAGE CONTENT ENDS HERE-->
		</div><!--/row-->
	</div><!--/.fluid-container#main-container-->

	<!--basic scripts-->

	<script src="/common/assets/js/jquery.printpage.js"></script>
	<script>
	     $(document).ready(function() {
	    	 $('span.print').printPage();
	     });
	</script>
   	<script>
		$(function() {
		});
		
		$("input:text[numberOnly]").on("keyup", function() {
		    $(this).val($(this).val().replace(/[^0-9]/g,""));
		});
   	</script>

	</body>
</html>
