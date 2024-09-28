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
			//f_divView('main_0', 'main_0');	//메뉴구조 유지
		});				

		function fnGoInsert(){
			
			if($("#group_id").val() == ""){
				alert("그룹코드를 입력해 주세요.");
				$("#group_id").focus();
				return;
			}

			if($("#group_nm").val() == ""){
				alert("그룹코드명을 입력해 주세요.");
				$("#group_nm").focus();
				return;
			}

			if(confirm("등록하시겠습니까?")){
				$("#aform").attr({action:"/admin/code/insertGroupCodeMgt.do", method:'post'}).submit(); 
			}			
		}

		function fnGoList(){
			document.location.href="/admin/code/selectPageListGroupCodeMgt.do";
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
						<label for="group_id" class="col-form-label col-xs-12 col-sm-3 col-md-3 col-lg-2">그룹코드</label>
						<div class="col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<input type="text" class="form-control" id="group_id" name="group_id" placeholder="그룹코드" onkeyup="cfLengthCheck('그룹코드는', this, 10);" />
						</div>
  					</div>
  
					<div class="form-group row">
						<label for="group_id" class="col-form-label col-xs-12 col-sm-3 col-md-3 col-lg-2">그룹코드명</label>
						<div class="col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<input type="text" class="form-control" id="group_nm" name="group_nm" placeholder="그룹코드명" onkeyup="cfLengthCheck('그룹코드명은', this, 50);" />
						</div>
  					</div>

					<div class="form-group row">
						<label for="group_id" class="col-form-label col-xs-12 col-sm-3 col-md-3 col-lg-2">영문그룹코드명</label>
						<div class="col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<input type="text" class="form-control" id="group_nm_eng" name="group_nm_eng" placeholder="영문그룹코드명" onkeyup="cfLengthCheck('영문그룹코드명은', this, 50);" />
						</div>
  					</div>
  
					<div class="form-group row">
						<label for="group_id" class="col-form-label col-xs-12 col-sm-3 col-md-3 col-lg-2">비고</label>
						<div class="col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<textarea class="form-control" rows="3" name="rm" placeholder="비고" onkeyup="cfLengthCheck('비고는', this, 100);"></textarea>
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
