<%@ page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.SysUtil"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="boardComboStr"  type="java.util.List" class="java.util.ArrayList" scope="request"/>

<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>
<html>
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>
    
	<script type="text/javascript">
	//<![CDATA[
		$(function(){
			fnComboStrFile('.fileBoxWrap', 0, 5);
		});
		
		// 목록
		function fnGoList(){
			$('#aform').attr({ action : '/mgt/group/selectPageListPlanFGroup.do', method : 'post' }).submit();
		}
		
		// 등록
		function fnGoInsert(){

			if($('[name=GROUP_NAME]').val() == ''){
				alert('PlanF 그룹명을 입력해 주세요.');
				$('[name=GROUP_NAME]').focus();
				return false;
			}
			
			if(confirm('등록하시겠습니까?')){
				$('#aform').attr({ action : '/mgt/group/insertPlanFGroup.do', method : 'post' }).submit();
			}
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
	
	<!-- content -->
	<div class="content-wrapper">
		<!-- Content Header (Page header) -->
		<section class="content-header">
		<div id="navi"><i class="fa fa-home f12 color-lgray"></i><span class="blind">home</span>><span class="text" id="spnavi"></span></div>
    	<div id="pagetitle">
    	</div>
		</section>

		<!-- Main content -->
		<section class="content container-fluid vw-page">
			<form role="form" id="aform" method="post" action="/mgt/group/insertPlanFGroup.do" enctype="multipart/form-data" class="form-horizontal">

			<div class="card">

				<h4 class="cardTitle"><i class="fa fa-caret-square-right"></i> 기본정보</h4>
				<div class="card-body viewForm">

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="GROUP_NAME">회원사명</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<input type="text" class="form-control" name="GROUP_NAME" id="GROUP_NAME" placeholder="회원사명" value="<%=param.getString("GROUP_NAME") %>" maxlength="100" />
						</div>
					</div>
					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="GROUP_NAME">분배포인트</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<input type="text" class="form-control" name="GROUP_NAME" id="GROUP_NAME" placeholder="포인트" value="100,000" maxlength="100" />
						</div>
					</div>
					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="GROUP_NAME">참여교원</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<input type="text" class="form-control" name="GROUP_NAME" id="GROUP_NAME" placeholder="교원이름" value="" maxlength="100" />
						</div>
					</div>

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="GROUP_ETC">비고</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<textarea class="form-control" rows="3" name="GROUP_ETC" id="GROUP_ETC"><%=param.getString("GROUP_ETC") %></textarea>
						</div>
					</div>
				
				</div>

				<div class="box-footer">
					<div class="text-center">
						<button type="button" class="btn btn-list" onclick="fnGoList(); return false;"><i class="fa fa-reply"></i> 목록</button>
						<button type="button" class="btn btn-write" onclick="fnGoInsert(); return false;"><i class="fa fa-pen"></i> 등록</button>
					</div>
				</div>

			</div>


			</form>
		</section>
	</div>
	<!-- footer -->
	<%@ include file="/common/inc/footer.jspf" %>
	<!-- //fooer -->
</div>
</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
