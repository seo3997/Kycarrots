<%@page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@page import="com.whomade.kycarrots.framework.common.util.EgovPropertiesUtil"%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>

<jsp:useBean id="survey" class="com.whomade.kycarrots.mgt.survey.vo.TbSurvey" scope="request"/>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>

<%
	String surveyId = survey.getSURVEY_ID();
    String sActionUrl=EgovPropertiesUtil.getProperty("Globals.mainUrl");
%>


<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>
	   <link rel="stylesheet" href="/common/assets/css/font-awesome.min.css">
		
		<!--fonts-->
		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300">
		<!--ace styles-->
		<link rel="stylesheet" href="/common/assets/css/acesurvey.css">
		<link rel="stylesheet" href="/common/assets/css/ace-responsive.min.css">
		<link rel="stylesheet" href="/common/assets/css/ace-skins.min.css">


<html>
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>
	
	<script type="text/javascript">
		$(function(){
			// 검색 조건 엔터시 이벤트
		});
		function new_window(freshurl) {
			  SmallWin = window.open(freshurl, 'Survey','scrollbars=yes,resizable=yes,toolbar=no,height=600,width=800');
			  if (!isIE4())	{
			    if (window.focus) { SmallWin.focus(); }
			  }
			  if (SmallWin.opener == null) SmallWin.opener = window;
			  SmallWin.opener.name = "Main";
		}
		function sub_viewResultWin(freshurl) {
			  SmallWin = window.open(freshurl, 'SurveyViewResult','scrollbars=yes,resizable=yes,toolbar=no,height=600,width=800');
			  SmallWin.opener.name = "SubViewResult";
		}

		function sub_registWin(freshurl) {
			SmallWin = window.open(freshurl, 'SurveyRegist','scrollbars=yes,resizable=yes,toolbar=no,height=600,width=800');
			SmallWin.opener.name = "SubRegist";
		}

		function sub_registNewWin(freshurl) {
			SmallWin = window.open(freshurl, 'SurveyRegistNew','scrollbars=yes,resizable=yes,toolbar=no,height=768,width=1024');
			SmallWin.opener.name = "SubRegistNew";
		}

		function sub_deleteEntriesWin(freshurl) {
			  SmallWin = window.open(freshurl, 'SurveyDeleteEntries','scrollbars=yes,resizable=yes,toolbar=no,height=600,width=800');
			  SmallWin.opener.name = "SubDeleteEntries";
		}
		
		function sub_exportResultsWin(freshurl) {
			  SmallWin = window.open(freshurl, 'SurveyExportResults','scrollbars=yes,resizable=yes,toolbar=no,height=600,width=800');
			  SmallWin.opener.name = "SubExportResults";
		}

		function sub_dbResultWin(freshurl) {
			  SmallWin = window.open(freshurl, 'sub_dbResultWin','scrollbars=yes,resizable=yes,toolbar=no,height=600,width=800');
			  SmallWin.opener.name = "sub_dbResultWin";
		}

		function sub_deleteEntrys(pUrl) {
			if(!confirm("삭제 하시겠습니까?")) {
				return false
			}else{
				location.href="/mgt/survey/deleteEntries.do?surveyId="+pUrl;
			};
		}
		
	</script>
</head>
<body class="hold-transition skin-green-light sidebar-mini">
<div class="wrapper">
	<!-- 헤더  -->
	<c:import url="/common/inc/header.do" charEncoding="utf-8" />
	<!-- 좌측 메뉴 -->
	<c:import url="/common/inc/menu.do" charEncoding="utf-8" />
	<jsp:useBean id="top_menu_id" class="java.lang.String" scope="request"/>

	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper">
		<!-- Content Header (Page header) -->
		<section class="content-header">
		<div id="navi"><i class="fa fa-home f12 color-lgray"></i><span class="blind">home</span>><span class="text" id="spnavi"></span></div>
    	<div id="pagetitleSurvey"><ul><li><span id="spTitleSurvey">설문관리</span></li></ul></div>
		
		</section>
		<!-- Main content -->
		<section class="content">
		
			<div id="page-content" class="clearfix">
			<div class="row-fluid">
						<!--PAGE CONTENT BEGINS HERE-->
						<div>
							<div class="widget-box">
									<div class="widget-header header-color-pink">
										<h5>준비단계</h5>
										<div class="widget-toolbar no-border">
											<span class="badge badge-info">Step1</span>
										</div>
										<div class="widget-toolbar">
											<a href="#" data-action="collapse">
												<i class="1 icon-chevron-up bigger-125"></i>
											</a>
										</div>
									</div>

									<div class="widget-body">
										<div class="widget-main">
											<p class="alert-survey alert-info-survey">
											 <%
									  			if ( survey.getOPENED().equals("") || !survey.getCLOSED().equals("") ) {
									    			if ( survey.getNUMENTRIES() == 0 ) {
									      				out.print( "<a href=\"javascript:sub_registWin('/mgt/survey/editEntryForm.do?surveyId="+survey.getSURVEY_ID()+"')\">"+"입력 양식 편집"+"</a>" );
									      				out.print( "<a href=\"javascript:sub_registNewWin('/mgt/survey/editEntryJsonForm.do?surveyId="+survey.getSURVEY_ID()+"')\">"+"입력 양식 편집"+"</a>" );
									      				if ( survey.getQUESTION_CNT()<1 ) { out.print(" "+"(entry form must contain at least one question)"); }
									    				} else {
											  %>
												      <%="입력 양식 편집"%>
												      <%="(You can only edit the entry form if you"%> <a href="/mgt/survey/deleteEntries.do?surveyId=<%=survey.getSURVEY_ID()%>" class="menuiteminactive"><%="delete all existing results"%></a>)
												      <!--  
												      <%="If you don't want that, just"%> <a href="createNewSurvey.jsp?templateSurveyId=<%=survey.getSURVEY_ID()%>" class="menuiteminactive"><%="create a new survey"%></a> <%="taking this one as a template.)"%></span>
												      -->
										      <%
									    				}
									  			} else {
									 		   %>
											    <%="if left blank, no notification will be send"%> <%="(설문이 종료되는 경우에만 입력 양식을 편집 할 수 있습니다)"%>
											  <%
									  			}
									  		   %>		
											
											</p>
											<p class="alert-survey alert-info-survey">
												<a href="javascript:sub_endPageWin('/isurvey/editExitPage.jsp?surveyId=1367055178754')">마무리 페이지 편집</a>
			      								
											</p>
											<!--  
											<p class="alert alert-info">
												<a href="editAdminEmail.jsp?surveyId=1367055178754">e-mail 알림변경</a>
											    (알림은 현재 비활성화되어 있습니다)
											</p>
											-->
											
											
											<p class="alert-survey alert-info-survey">
												<a href="javascript:sub_manageAdminWin('/isurvey/manageAdmins.jsp?surveyId=1367055178754')">관리자 추가/삭제</a>
											</p>
											
											<p class="alert-survey alert-info-survey">
												<a href="javascript:sub_editEntryRestrictionsWin('/isurvey/editEntryRestrictions.jsp?surveyId=1367055178754')">입장 제한 편집</a>
											</p>
										</div>
									</div>
								</div>
							</div>
						<div class="space"></div>
						<div>
							<div class="widget-box">
									<div class="widget-header header-color-orange">
										<h5>설문시작/설문종료</h5>
										<div class="widget-toolbar no-border">
											<span class="badge badge-info">Step2</span>
										</div>
										<div class="widget-toolbar">
											<a href="#" data-action="collapse">
												<i class="1 icon-chevron-up bigger-125"></i>
											</a>
										</div>
									</div>
									<div class="widget-body">
										<div class="widget-main">
											<p class="alert-survey alert-info-survey">
			     							<%
			  									if ( StringUtil.isAcceptingDataEntry(survey.getOPENED(),survey.getCLOSED()) ) {
											%>
			      							<span style="color : #009900"><b><%="설문조사"%></b></span> <%="URL:"%>
			      							&quot;<a style="color : #000000" href="JavaScript:new_window('/mgt/survey/entry.do?surveyId=<%=surveyId%>&surveyMode=0')"><%=sActionUrl%>mgt/entry.do?surveyId=<%=survey.getSURVEY_ID()%>&surveyMode=0</a>&quot;<br>
			      							<%
			  									}else {
											%>
			      							<a href="/mgt/survey/openSurvey.do?surveyId=<%=surveyId%>&stated=open"><%="데이타 수집 시작"%></a> <%="(설문 조사를 시작하고 웹에서 사용할 수 있도록 합니다.)"%>
			      							<%
			  									}
											%>  									
											</p>
											<p class="alert-survey alert-info-survey">
											<%
  												if ( !survey.getCLOSED().equals("") && !StringUtil.isAcceptingDataEntry(survey.getOPENED(),survey.getCLOSED()) ) {
    										%>
		      								<%="데이터 수집 종료"%> 
		      								<%
  												}else if ( survey.getOPENED().equals("") ) { // not opened yet
											%>
		      								<%="데이터 수집 종료"%>
		      								<%
  												}else {
											%>
		      								<a href="/mgt/survey/openSurvey.do?surveyId=<%=surveyId%>&stated=closed"><%="데이터 수집 종료"%></a> <%="(설문 조사를 중지하며 웹에서  사용할 수 없게됩니다.)"%>
		      								<%
    												if ( !survey.getCLOSED().equals("") ) { %>
		      										(<%="survey will automatically close on"%> <%=survey.getCLOSED()%>)
		      								<% 		}
  												}
											%>
		      								
											</p>
											
										</div>
									</div>
								</div>
							</div>
						</div>  
						<div class="space"></div>
						<div>
							<div class="widget-box">
									<div class="widget-header header-color-dark">
										<h5>설문결과</h5>
										<div class="widget-toolbar no-border">
											<span class="badge badge-info">Step3</span>
										</div>
										<div class="widget-toolbar">
											<a href="#" data-action="collapse">
												<i class="1 icon-chevron-up bigger-125"></i>
											</a>
										</div>
									</div>
									<div class="widget-body">
										<div class="widget-main">
											<p class="alert-survey alert-info-survey">
											<a href="javascript:sub_editResultsPasswordWin('/isurvey/editResultsPassword.jsp?surveyId=1367055178754')">접속 제한을 변경</a> (현재 관리자만이, 설문 조사 결과를 볼 수 있습니다.)
											</p>
											<p class="alert-survey alert-info-survey">
											
									      	<a href="javascript:sub_viewResultWin('/mgt/survey/viewResults.do?surveyId=<%=survey.getSURVEY_ID()%>')">설문결과보기</a> <!--(설문결과 URL: goodsurvey.co.kr/isurvey/viewResults.jsp?id=1367055178754)-->
									  										
											</p>
											<p class="alert-survey alert-info-survey">
											
									      	<a href="javascript:sub_viewResultWin('/mgt/survey/viewResultsList.do?surveyId=<%=survey.getSURVEY_ID()%>')">설문Entry수정</a>
									  										
											</p>
											<p class="alert-survey alert-info-survey">
		      								 <a href="javascript:sub_deleteEntrys('<%=survey.getSURVEY_ID()%>');">설문결과삭제</a>
											</p>
											<p class="alert-survey alert-info-survey">
											
		    								<a href="javascript:sub_exportResultsWin('/mgt/survey/exportResults.do?surveyId=<%=survey.getSURVEY_ID()%>')">설문결과 내보내기</a> (into Excel, SAS, SPSS etc.)
																			
											</p>
											
											<p class="alert-survey alert-info-survey">
											<a href="javascript:sub_dbResultWin('/isurvey/dbResults.jsp?surveyId=<%=survey.getSURVEY_ID()%>')">설문결과분석</a>
											</p>
											
										</div>
									</div>
								</div>
							</div>
						</div>  

						<!--PAGE CONTENT ENDS HERE-->
					</div><!--/row-->

		   </div><!--/#page-content-->
		
		
	
		</section><!-- /.content -->
	</div><!-- /.content-wrapper -->
	
	<!-- footer -->
	<%@ include file="/common/inc/footer.jspf" %>
	<!-- //fooer -->
		<script src="/common/assets/js/bootstrap.min.js"></script>
		<!--page specific plugin scripts-->
		<script src="/common/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script src="/common/assets/js/jquery.ui.touch-punch.min.js"></script>
		<script src="/common/assets/js/jquery.slimscroll.min.js"></script>
		<!--ace scripts-->
		<script src="/common/assets/js/ace-elements.min.js"></script>
		<script src="/common/assets/js/ace.min.js"></script>
	
</div><!-- ./wrapper -->

</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
