<%@page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@page import="com.whomade.kycarrots.mgt.survey.vo.TbSurvey"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>

<jsp:useBean id="resultList"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="pageNavigationVo" class="com.whomade.kycarrots.framework.common.page.vo.pageNavigationVo" scope="request"/>
<jsp:useBean id="navigationBar" class="java.lang.String" scope="request"/>




<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>
		<!--fonts-->
		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300">
		<!--ace styles-->
		<link rel="stylesheet" href="/common/assets/css/acesurvey.css">
		<link rel="stylesheet" href="/common/assets/css/ace-skins.min.css">

<html>
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>
	
	<script type="text/javascript">
	//<![CDATA[
		$(function(){
			// 검색 조건 엔터시 이벤트
			$('[name=sch_text]').on({
				'keydown' : function(e){
					if(e.which == 13){
						e.preventDefault();
					}
				},
				'keyup' : function(e){
					if(e.which == 13){
						fnSearch();
					}
				}
			});
		});
		

		function sub_createNewSurveyWin(freshurl) {
			  SmallWin = window.open(freshurl, 'SurveycreateNew','scrollbars=yes,resizable=yes,toolbar=no,height=350,width=800');
			  SmallWin.opener.name = "SubcreateNew";
		}

		function sub_deleteSurveyWin(freshurl) {
			  SmallWin = window.open(freshurl, 'SurveyDelete','scrollbars=yes,resizable=yes,toolbar=no,height=450,width=800');
			  SmallWin.opener.name = "SubSurveyDelete";
		}
		
		function sub_renameSurveyWin(freshurl) {
			  SmallWin = window.open(freshurl, 'SurveyRename','scrollbars=yes,resizable=yes,toolbar=no,height=450,width=800');
			  SmallWin.opener.name = "SubSurveyRename";
		}
		
		function sub_copySurveyWin(freshurl) {
			  SmallWin = window.open(freshurl, 'SurveyCopy','scrollbars=yes,resizable=yes,toolbar=no,height=450,width=800');
			  SmallWin.opener.name = "SubSurveyCopy";
		}

		// 페이지 이동
		function fnGoPage(currentPage){
			$('#currentPage').val(currentPage);
			$('#aform').attr({ action : '/mgt/survey/selectPageListSurvey.do', method : 'get' }).submit();
		}
		
		// 등록폼 이동
		function fnInsertForm(){
			$('#aform').attr({ action : '/mgt/mboard/insertFormBoard.do', method : 'post' }).submit();
		}

		// 상세조회
		function fnSelect(bbs_seq){
			$('[name=bbs_seq]').val(bbs_seq);
			$('#aform').attr({ action : '/mgt/mboard/selectBoard.do', method : 'get' }).submit();
		}
		
		// 검색
		function fnSearch(){
			$('#currentPage').val('1');
			$('#aform').attr({ action : '/mgt/survey/selectPageListSurvey.do', method : 'get' }).submit();
		}
	//]]>
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
    	<div id="pagetitle">
    	</div>
		</section>
		<!-- Main content -->
		<section class="content">
		
			<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<!--PAGE CONTENT BEGINS HERE-->
				<div class="row-fluid">
					<table id="table_report" class="table table-striped table-bordered table-hover">
						<thead>
								<tr>
									<th>설문ID</th>
									<th>설문상태</th>
									<th>답변</th>
									<th>설문변경</th>
								</tr>
						</thead>
						<tbody>							
						<%
							int dataNo = pageNavigationVo.getCurrDataNo();
							String sEntryCnt="";
							for(int i = 0; i < resultList.size(); i++){
								DataMap dataMap = (DataMap) resultList.get(i);
								TbSurvey aTbSurvey = new TbSurvey();
								aTbSurvey.setSURVEY_ID(dataMap.getString("SURVEY_ID"));
								aTbSurvey.setSURVEY_TITLE(dataMap.getString("SURVEY_TITLE"));
								aTbSurvey.setOPENED(dataMap.getString("OPENED"));
								aTbSurvey.setCLOSED(dataMap.getString("CLOSED"));
								
								
								if ( !aTbSurvey.getOPENED().equals ("") ) {
									sEntryCnt=dataMap.getString("NUMENTRIES");
								}
								
							%>
							<tr>
								<td>
									<a href="/mgt/survey/manageSurveyMenu.do?surveyId=<%=aTbSurvey.getSURVEY_ID()%>"><%=aTbSurvey.getSURVEY_TITLE()%></a>
								</td>
								<td><font color="#009900"><%=aTbSurvey.getStatusAsText() %></font></td>
								<td class="center"><%=sEntryCnt%></td>
								<td class="td-actions">
									<div class="hidden-phone visible-desktop btn-group">
										<button class="btn btn-mini btn-success" onClick="sub_copySurveyWin('/mgt/survey/copySurvey.do?templateSurveyId=<%=aTbSurvey.getSURVEY_ID()%>');">
											<i class="icon-ok bigger-120">copy</i>
										</button>

										<button class="btn btn-mini btn-info" onClick="sub_renameSurveyWin('/mgt/survey/renameSurvey.do?surveyId=<%=aTbSurvey.getSURVEY_ID()%>');">
											<i class="icon-edit bigger-120" >rename</i>
										</button>

										<button class="btn btn-mini btn-danger" onClick="sub_deleteSurveyWin('/mgt/survey/deleteSurvey.do?surveyId=<%=aTbSurvey.getSURVEY_ID()%>');">
											<i class="icon-trash bigger-120">delete</i>
										</button>
									</div>
								</td>
							</tr>
							<%}%>
							<%if(resultList.size() == 0){%>
								<tr>
									<td class="text-center" colspan="4" ><spring:message code="msg.data.empty" /></td>
								</tr>
							<%}%>
				
						</tbody>
					</table>
				</div>
				<!--PAGE CONTENT ENDS HERE-->
	            <div >
	              <%=navigationBar %>
	            </div>
                <div class="col-auto form-inline text-right">
                  <button type="button" class="btn btn-info btn-write" onclick="sub_createNewSurveyWin('/mgt/survey/icreateNewSurveyForm.do'); return false;"><i class="fa fa-plus"></i> 설문추가</button>
                </div>
			</div><!--/row-->
		</div><!--/#page-content-->

		
	
		</section><!-- /.content -->
	</div><!-- /.content-wrapper -->
	
	<!-- footer -->
	<%@ include file="/common/inc/footer.jspf" %>
	<!-- //fooer -->
	
</div><!-- ./wrapper -->



</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
