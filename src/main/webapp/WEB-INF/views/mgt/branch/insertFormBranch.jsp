<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>

<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>

<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>

<html>
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title>지점 등록 - <%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>
    
	<script type="text/javascript">
		function fnGoList(){
			$('#aform').attr({ action : '/mgt/branch/selectPageListBranch.do', method : 'get' }).submit();
		}
		
		function fnGoInsert(){
			if($('[name=branchName]').val() == ''){
				alert('지점명을 입력해 주세요.');
				$('[name=branchName]').focus();
				return false;
			}
            if($('[name=branchCode]').val() == ''){
				alert('지점코드를 입력해 주세요.');
				$('[name=branchCode]').focus();
				return false;
			}
            if($('[name=sellerId]').val() == ''){
				alert('판매자 아이디를 입력해 주세요.');
				$('[name=sellerId]').focus();
				return false;
			}
            if($('[name=sellerPassword]').val() == ''){
				alert('판매자 비밀번호를 입력해 주세요.');
				$('[name=sellerPassword]').focus();
				return false;
			}
			
			if(confirm('등록하시겠습니까?')){
				$('#aform').attr({ action : '/mgt/branch/insertBranch.do', method : 'post' }).submit();
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
		    <div id="navi"><i class="fa fa-home f12 color-lgray"></i><span class="blind">home</span> > 지점관리 > <span class="text">지점 등록</span></div>
    	    <div id="pagetitle"><h1>지점 및 판매자 등록</h1></div>
		</section>

		<section class="content container-fluid vw-page">
			<form role="form" id="aform" method="post" action="/mgt/branch/insertBranch.do" class="form-horizontal">
			<div class="card">
				<h4 class="cardTitle"><i class="fa fa-caret-square-right"></i> 지점 정보</h4>
				<div class="card-body viewForm">
					<div class="form-group row">
						<label class="control-label col-sm-2" for="branchName">지점명</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="branchName" id="branchName" maxlength="100" />
						</div>
						<label class="control-label col-sm-2" for="branchCode">지점코드</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="branchCode" id="branchCode" maxlength="20" />
						</div>
					</div>
					<div class="form-group row">
						<label class="control-label col-sm-2" for="companyName">상호명</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="companyName" id="companyName" />
						</div>
						<label class="control-label col-sm-2" for="representativeName">대표자명</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="representativeName" id="representativeName" />
						</div>
					</div>
					<div class="form-group row">
						<label class="control-label col-sm-2" for="businessNumber">사업자번호</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="businessNumber" id="businessNumber" />
						</div>
						<label class="control-label col-sm-2" for="domainUrl">도메인 URL</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="domainUrl" id="domainUrl" />
						</div>
					</div>
                    <div class="form-group row">
						<label class="control-label col-sm-2" for="tossClientKey">Toss Client Key</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="tossClientKey" id="tossClientKey" />
						</div>
						<label class="control-label col-sm-2" for="tossSecretKey">Toss Secret Key</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="tossSecretKey" id="tossSecretKey" />
						</div>
					</div>
				</div>

				<h4 class="cardTitle mt-4"><i class="fa fa-user"></i> 판매자 계정 정보</h4>
				<div class="card-body viewForm">
					<div class="form-group row">
						<label class="control-label col-sm-2" for="sellerId">판매자 ID</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="sellerId" id="sellerId" />
						</div>
						<label class="control-label col-sm-2" for="sellerPassword">비밀번호</label>
						<div class="col-sm-4">
							<input type="password" class="form-control" name="sellerPassword" id="sellerPassword" />
						</div>
					</div>
					<div class="form-group row">
						<label class="control-label col-sm-2" for="sellerName">판매자명</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="sellerName" id="sellerName" />
						</div>
						<label class="control-label col-sm-2" for="sellerEmail">이메일</label>
						<div class="col-sm-4">
							<input type="email" class="form-control" name="sellerEmail" id="sellerEmail" />
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
	<%@ include file="/common/inc/footer.jspf" %>
</div>
</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
