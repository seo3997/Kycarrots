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
			//f_divView('main_2', 'main_2');	//메뉴구조 유지
		});
		function f_GoList(){
			document.location.href="/admin/author/selectPageListAuthorMgt.do";
		}

		function f_GoInsert(){
			
			if($("#author_nm").val() == ""){
				alert("권한명을 입력해 주세요.");
				$("#author_nm").focus();
				return;
			} else if($("#author_id").val() == ""){
				alert("권한ID를 입력해 주세요.");
				$("#author_id").focus();
				return;
			}
			
			var param = { 'author_id' : $('#author_id').val() };
			$.ajax({
				url : '/admin/author/selectExistYnAuthorMgtAjax.do',
				data : param,
				dataType : 'json',
				type : 'post',
				success : function(response){
					if(response.resultStats.resultCode == 'success'){
						if(response.resultStats.resultMsg.existYn == 'N'){
							if(confirm("등록하시겠습니까?")){
								$("#aform").attr({action:"/admin/author/insertAuthorMgt.do", method:'post'}).submit();
							}
						} else {
							alert('이미 등록된 권한 ID 입니다.');
							$("#auth_id").focus();
							return;
						}
					}
				},
				error : function(jqXHR, textStatus, thrownError){
					ajaxJsonErrorAlert(jqXHR, textStatus, thrownError)
				}
			});
			
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

			<form role="form" id="aform" method="post" action="/admin/code/selectPageListCodeMgt.do">
			<div class="card">

				<div class="card-body">

					<div class="form-group row">
						<label for="author_nm" class="col-form-label col-xs-12 col-sm-3 col-md-3 col-lg-2">권한명</label>
						<div class="col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<input type="text" class="form-control" id="author_nm" name="author_nm" placeholder="권한명" onkeyup="cfLengthCheck('권한명은', this, 100);">
						</div>
  					</div>
  
					<div class="form-group row">
						<label for="author_id" class="col-form-label col-xs-12 col-sm-3 col-md-3 col-lg-2">권한ID</label>
						<div class="col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<input type="text" class="form-control" id="author_id" name="author_id" placeholder="권한ID" onkeyup="cfLengthCheck('권한ID는', this, 10);">
						</div>
  					</div>

					<div class="form-group row">
						<label for="rm" class="col-form-label col-xs-12 col-sm-3 col-md-3 col-lg-2">내용</label>
						<div class="col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<textarea class="form-control" rows="3" name="rm" placeholder="내용..." onkeyup="cfLengthCheck('내용은', this, 100);"></textarea>
						</div>
  					</div>

				</div>

				<div class="box-footer">
					<div class="text-center">
						<button type="button" class="btn btn-list" onclick="f_GoList(); return false;"><i class="fa fa-reply"></i> 목록</button>
						<button type="button" class="btn btn-write" onclick="f_GoInsert(); return false;"><i class="fa fa-pen"></i> 등록</button>
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
