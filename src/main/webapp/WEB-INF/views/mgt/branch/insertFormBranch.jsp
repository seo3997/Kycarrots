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
    	    <div id="pagetitle"><h1>지점 등록</h1></div>
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
							<input type="text" class="form-control" name="branchCode" id="branchCode" maxlength="20" value="${nextBranchCode}" readonly="readonly" />
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
						<label class="control-label col-sm-2" for="tongsinNumber">통신판매번호</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="tongsinNumber" id="tongsinNumber" />
						</div>
					</div>
                    <div class="form-group row">
						<label class="control-label col-sm-2" for="csPhone">고객센터 전화번호</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="csPhone" id="csPhone" />
						</div>
						<label class="control-label col-sm-2" for="domainUrl">도메인 URL</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="domainUrl" id="domainUrl" />
						</div>
					</div>
                    <div class="form-group row">
						<label class="control-label col-sm-2" for="address">사업장 주소</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="address" id="address" />
						</div>
					</div>
                    <div class="form-group row">
						<label class="control-label col-sm-2" for="logoImageUrl">로고 이미지 URL</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="logoImageUrl" id="logoImageUrl" />
						</div>
						<label class="control-label col-sm-2" for="branchStatus">운영 상태</label>
						<div class="col-sm-4">
							<select class="form-control" name="branchStatus" id="branchStatus">
                                <option value="RUNNING">운영중</option>
                                <option value="STOPPED">정지</option>
                                <option value="TERMINATED">해지</option>
                            </select>
						</div>
					</div>
                    <h4 class="cardTitle"><i class="fa fa-caret-square-right"></i> 결제 및 정책 정보</h4>
                    <div class="form-group row">
						<label class="control-label col-sm-2" for="tossMid">토스 MID</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="tossMid" id="tossMid" />
						</div>
						<label class="control-label col-sm-2" for="billingCycle">정산 주기</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="billingCycle" id="billingCycle" placeholder="예: MONTHLY_1" />
						</div>
					</div>
                    <div class="form-group row">
						<label class="control-label col-sm-2" for="tossClientKey">토스 클라이언트 키</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="tossClientKey" id="tossClientKey" />
						</div>
						<label class="control-label col-sm-2" for="tossSecretKey">토스 시크릿 키</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="tossSecretKey" id="tossSecretKey" />
						</div>
					</div>
                    <div class="form-group row">
						<label class="control-label col-sm-2" for="isUseCustomPrice">판매가 수정 가능</label>
						<div class="col-sm-4">
							<select class="form-control" name="isUseCustomPrice" id="isUseCustomPrice">
                                <option value="N">불가</option>
                                <option value="Y">가능</option>
                            </select>
						</div>
						<label class="control-label col-sm-2" for="isActive">사이트 활성화</label>
						<div class="col-sm-4">
							<select class="form-control" name="isActive" id="isActive">
                                <option value="Y">활성</option>
                                <option value="N">비활성</option>
                            </select>
						</div>
					</div>
                    <div class="form-group row">
						<label class="control-label col-sm-2" for="bankCd">입금 은행</label>
						<div class="col-sm-4">
							<select class="form-control" name="bankCd" id="bankCd">
                                <option value="">선택하세요</option>
                                <c:forEach var="item" items="${bankList}">
                                    <option value="${item.CODE}">${item.CODE_NM}</option>
                                </c:forEach>
                            </select>
						</div>
                        <label class="control-label col-sm-2" for="accountHolder">예금주 성명</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="accountHolder" id="accountHolder" />
						</div>
					</div>
                    <div class="form-group row">
						<label class="control-label col-sm-2" for="accountNo">입금 계좌번호</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="accountNo" id="accountNo" />
						</div>
					</div>
					<div class="form-group row">
						<label class="control-label col-sm-2" for="baseShippingFee">기본 배송비</label>
						<div class="col-sm-2">
							<input type="number" class="form-control" name="baseShippingFee" id="baseShippingFee" value="3000" />
						</div>
						<label class="control-label col-sm-2" for="freeShippingThreshold">무료배송 기준 금액</label>
						<div class="col-sm-2">
							<input type="number" class="form-control" name="freeShippingThreshold" id="freeShippingThreshold" value="50000" />
						</div>
						<label class="control-label col-sm-2" for="extraShippingFee">추가 배송비</label>
						<div class="col-sm-2">
							<input type="number" class="form-control" name="extraShippingFee" id="extraShippingFee" value="3000" />
						</div>
					</div>
                    <div class="form-group row">
						<label class="control-label col-sm-2" for="shippingFeePolicy">배송비 정책 (JSON)</label>
						<div class="col-sm-10">
							<textarea class="form-control" name="shippingFeePolicy" id="shippingFeePolicy" rows="3"></textarea>
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
