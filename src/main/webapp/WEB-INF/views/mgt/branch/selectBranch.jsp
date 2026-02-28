<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>

<jsp:useBean id="resultMap" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>

<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>

<html>
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title>지점 상세 - <%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>
    
	<script type="text/javascript">
		function fnGoList(){
			$('#aform').attr({ action : '/mgt/branch/selectPageListBranch.do', method : 'get' }).submit();
		}
		
		function fnGoUpdateForm(){
			$('#aform').attr({ action : '/mgt/branch/updateFormBranch.do', method : 'get' }).submit();
		}
		
		function fnGoDelete(){
			if(confirm('지점 정보를 삭제하시겠습니까? 관련 데이터가 모두 삭제될 수 있습니다.')){
				$('#aform').attr({ action : '/mgt/branch/deleteBranch.do', method : 'post' }).submit();
			}
		}
	</script>
</head>
<body class="hold-transition skin-green-light sidebar-mini">
<div class="wrapper">
	<c:import url="/common/inc/header.do" charEncoding="utf-8" />
	<c:import url="/common/inc/menu.do" charEncoding="utf-8" />
	
	<div class="content-wrapper">
		<section class="content-header">
		    <div id="navi"><i class="fa fa-home f12 color-lgray"></i><span class="blind">home</span> > 지점관리 > <span class="text">지점 상세</span></div>
    	    <div id="pagetitle"><h1>지점 상세 정보</h1></div>
		</section>

		<section class="content container-fluid vw-page">
			<form role="form" id="aform" method="post" class="form-horizontal">
				<input type="hidden" name="branchId" value="${resultMap.BRANCH_ID}" />
				<input type="hidden" name="sch_text" value="${param.sch_text}" />

			<div class="card">
				<h4 class="cardTitle"><i class="fa fa-caret-square-right"></i> 지점 상세 정보</h4>
				<div class="card-body viewForm">
					<div class="form-group row">
						<label class="control-label col-sm-2">지점명</label>
						<div class="col-sm-4 py-2">${resultMap.BRANCH_NAME}</div>
						<label class="control-label col-sm-2">지점코드</label>
						<div class="col-sm-4 py-2">${resultMap.BRANCH_CODE}</div>
					</div>
					<div class="form-group row">
						<label class="control-label col-sm-2">상호명</label>
						<div class="col-sm-4 py-2">${resultMap.COMPANY_NAME}</div>
						<label class="control-label col-sm-2">대표자명</label>
						<div class="col-sm-4 py-2">${resultMap.REPRESENTATIVE_NAME}</div>
					</div>
					<div class="form-group row">
						<label class="control-label col-sm-2">사업자번호</label>
						<div class="col-sm-4 py-2">${resultMap.BUSINESS_NUMBER}</div>
						<label class="control-label col-sm-2">도메인 URL</label>
						<div class="col-sm-4 py-2">${resultMap.DOMAIN_URL}</div>
					</div>
					<div class="form-group row">
						<label class="control-label col-sm-2">상태</label>
						<div class="col-sm-4 py-2">${resultMap.BRANCH_STATUS}</div>
						<label class="control-label col-sm-2">등록일</label>
						<div class="col-sm-4 py-2">${resultMap.REGIST_DT}</div>
					</div>
				</div>

				<div class="box-footer">
					<div class="text-center">
						<button type="button" class="btn btn-list" onclick="fnGoList(); return false;"><i class="fa fa-reply"></i> 목록</button>
						<button type="button" class="btn btn-modify" onclick="fnGoUpdateForm(); return false;"><i class="fa fa-eraser"></i> 수정</button>
						<button type="button" class="btn btn-delete" onclick="fnGoDelete(); return false;"><i class="fa fa-trash"></i> 삭제</button>
					</div>
				</div>
			</div>
			</form>
		</section>
	</div>
	<%@ include file="/common/inc/footer.jspf" %>
</div>
</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
