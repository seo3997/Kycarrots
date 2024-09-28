<%@ page import="com.whomade.kycarrots.framework.common.util.file.vo.AtFileVO"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.SysUtil"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="resultMap" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="fileList"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
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
		});
		
		// 상세
		function fnDetail(){
			$('#aform').attr({ action : '/mgt/group/selectPlanFGroup.do', method : 'get' }).submit();
		}
		
		// 수정
		function fnGoUpdate(){
			
			if($('[name=sj]').val() == ''){
				alert('제목을 입력해 주세요.');
				$('[name=sj]').focus();
				return false;
			}
			
			
			if(confirm('수정하시겠습니까?')){
				$('#aform').attr({ action : '/mgt/group/updatePlanFGroup.do', method : 'post' }).submit();
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

			<form role="form" id="aform" method="post" action="/mgt/group/updatePlanFGroup.do" enctype="multipart/form-data" class="form-horizontal">
				<input type="hidden" name="GROUP_ID" 				value="<%=resultMap.getString("GROUP_ID") %>" />
				<input type="hidden" name="sch_text" 				value="<%=param.getString("sch_text")%>" />
				<input type="hidden" name="currentPage" 			value="<%=param.getString("currentPage")%>"/>

			<div class="card">
			
				<h4 class="cardTitle"><i class="fa fa-caret-square-right"></i> 기본정보</h4>
				<div class="card-body viewForm">

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="GROUP_NAME">PlanF 그룹명</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<input type="text" class="form-control" name="GROUP_NAME" id="GROUP_NAME" placeholder="제목" value="<%=param.getString("GROUP_NAME", resultMap.getString("GROUP_NAME")) %>" maxlength="100" />
						</div>
					</div>

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="GROUP_ETC">PlanF 내용</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<textarea class="form-control" rows="5" name="GROUP_ETC" id="GROUP_ETC"><%=param.getString("GROUP_ETC", resultMap.getString("GROUP_ETC")) %></textarea>
						</div>
					</div>


				</div>

				<div class="box-footer">
             		<div class="text-center">
						<button type="button" class="btn btn-reset" onclick="fnDetail(); return false;"><i class="fa fa-reply"></i> 취소</button>
						<button type="button" class="btn btn-write" onclick="fnGoUpdate(); return false;"><i class="fa fa-pen"></i> 확인</button>
					</div>
				</div>

			</div>

			</form>
		</section><!-- /.content -->
	</div>
	<!-- footer -->
	<%@ include file="/common/inc/footer.jspf" %>
	<!-- //fooer -->
</div>
</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
