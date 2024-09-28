<%@page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
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

<html>
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>
	
	<link rel="stylesheet" href="/common/assets/css/font-awesome.min.css" />
	<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />
	<!--ace styles-->
	<link rel="stylesheet" href="/common/assets/css/ace-responsive.min.css" />
	<link rel="stylesheet" href="/common/assets/css/ace-skins.min.css" />
	
	
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
							for(int i = 0; i < resultList.size(); i++){
								DataMap dataMap = (DataMap) resultList.get(i);
							%>
							<tr>
								<td>
									<a href="/mgt/survey/manageSurveyMenu.do?surveyId=1378531117670"><%=dataMap.getString("SJ") %></a>
								</td>
								<td><font color="#009900">opened on 2014-05-09 18:36:40</font></td>
								<td class="center">1</td>
								<td class="td-actions">
									<div class="hidden-phone visible-desktop btn-group">
										<button class="btn btn-mini btn-success" onClick="sub_copySurveyWin('/isurvey/createNewSurvey.jsp?templateSurveyId=1378531117670');">
											<i class="icon-ok bigger-120">copy</i>
										</button>

										<button class="btn btn-mini btn-info" onClick="sub_renameSurveyWin('/isurvey/renameSurvey.jsp?surveyId=1378531117670');">
											<i class="icon-edit bigger-120" >rename</i>
										</button>

										<button class="btn btn-mini btn-danger" onClick="sub_deleteSurveyWin('/isurvey/deleteSurvey.jsp?surveyId=1378531117670');">
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
				  <div class="pull-right">
					<a href="javascript:fnInsertForm()" class="btn btn-block btn-primary">설문추가</a>
				  </div>
	            </div>
			</div><!--/row-->
		</div><!--/#page-content-->
		
		
	
		</section><!-- /.content -->
	</div><!-- /.content-wrapper -->
	
	<!-- footer -->
	<%@ include file="/common/inc/footer.jspf" %>
	<!-- //fooer -->
	
</div><!-- ./wrapper -->


<script src="/common/assets/js/jquery.dataTables.min.js"></script>
<script src="/common/assets/js/jquery.dataTables.bootstrap.js"></script>

<!--ace scripts-->

<script src="/common/assets/js/ace-elements.min.js"></script>
<script src="/common/assets/js/ace.min.js"></script>

</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
