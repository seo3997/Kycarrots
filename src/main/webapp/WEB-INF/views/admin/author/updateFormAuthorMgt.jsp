<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>

	<script type="text/javascript">	
	//<![CDATA[
		$(function(){
		});

		function fnGoUpdate(){
			
			if($("#auth_nm").val() == ""){
				alert("권한명을 입력해 주세요.");
				$("#auth_nm").focus();
				return;
			}
			
			if(confirm("수정하시겠습니까?")){
				$("#aform").attr({action:"/admin/author/updateAuthorMgt.do", method:'post'}).submit();
			}
		}

		function fnGoList(){
			$("#aform").attr({action:"/admin/author/selectPageListAuthorMgt.do", method:'post'}).submit(); 
		}

		function fnGoDelete(){

			if(confirm("삭제하시겠습니까?")){
				$("#aform").attr({action:"/admin/author/deleteAuthorMgt.do", method:'post'}).submit(); 
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

		<section class="content container-fluid vw-page">

			<form role="form" id="aform" method="post" action="/admin/author/insertAuthorMgt.do">
			<input type = "hidden" name = "author_id" value = "${param.author_id }"/>
			<input type = "hidden" name = "sch_auth_nm" value = "${param.sch_auth_nm }"/>
			<input type = "hidden" name = "currentPage" value = "${param.currentPage }"/>
			
			<div class="card">

				<div class="card-body">

					<div class="form-group row">
						<label for="author_id" class="col-form-label col-xs-12 col-sm-3 col-md-3 col-lg-2">권한ID</label>
						<div class="col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<input type="text" class="form-control" id="author_id" value="${resultMap.AUTHOR_ID }" disabled>
						</div>
  					</div>
  
					<div class="form-group row">
						<label for="author_nm" class="col-form-label col-xs-12 col-sm-3 col-md-3 col-lg-2">권한명</label>
						<div class="col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<input type="text" class="form-control" id="author_nm" name="author_nm" value="${resultMap.AUTHOR_NM }" placeholder="권한명" onkeyup="cfLengthCheck('권한명은', this, 100);">
						</div>
  					</div>
  
					<div class="form-group row">
						<label for="rm" class="col-form-label col-xs-12 col-sm-3 col-md-3 col-lg-2">내용</label>
						<div class="col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<textarea class="form-control" rows="3" name="rm" placeholder="내용..." onkeyup="cfLengthCheck('내용은', this, 100);">${resultMap.RM }</textarea>
						</div>
  					</div>

				</div>

				<div class="box-footer">
					<div class="text-center">
						<button type="button" class="btn btn-list" onclick="fnGoList(); return false;"><i class="fa fa-reply"></i> 목록</button>
						<button type="button" class="btn btn-modify" onclick="fnGoUpdate(); return false;"><i class="fa fa-eraser"></i> 수정</button>
						<button type="button" class="btn btn-delete" onclick="fnGoDelete(); return false;"><i class="fa fa-trash"></i> 삭제</button>
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
