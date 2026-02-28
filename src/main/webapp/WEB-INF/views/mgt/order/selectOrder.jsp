<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>

<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>

<html>
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>
	<script type="text/javascript">
		function fnList(){
			location.href = "/mgt/order/selectPageListOrder.do";
		}
		
		function fnCancel(){
			if(confirm("정말로 이 주문을 취소하시겠습니까? (Toss 결제 취소 API가 호출됩니다)")){
				var reason = prompt("취소 사유를 입력하세요", "관리자 취소");
				if(reason != null){
					location.href = "/mgt/order/cancelOrder.do?orderNo=${resultVo.orderNo}&cancelReason=" + encodeURIComponent(reason);
				}
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
			<div id="navi"><i class="fa fa-home f12 color-lgray"></i> Home > 주문관리 > 상세</div>
			<div id="pagetitle"><h1>주문 상세 정보</h1></div>
		</section>

		<section class="content container-fluid">
			<div class="row">
		   		<div class="col-12">
					<div class="box box-info">
						<div class="box-header with-border">
							<h3 class="box-title">주문 정보 [${resultVo.orderNo}]</h3>
						</div>
						<div class="box-body">
							<table class="table table-bordered">
								<colgroup>
									<col style="width:150px;">
									<col>
									<col style="width:150px;">
									<col>
								</colgroup>
								<tr>
									<th>주민번호</th>
									<td>${resultVo.orderNo}</td>
									<th>주문상태</th>
									<td>
										<c:choose>
											<c:when test="${resultVo.orderStatus == 'READY'}"><span class="label label-default">결제 대기</span></c:when>
											<c:when test="${resultVo.orderStatus == 'FAILED'}"><span class="label label-warning">결제 실패</span></c:when>
											<c:when test="${resultVo.orderStatus == 'PAID'}"><span class="label label-success">결제완료</span></c:when>
											<c:when test="${resultVo.orderStatus == 'CANCEL'}"><span class="label label-danger">주문취소</span></c:when>
											<c:when test="${resultVo.orderStatus == 'PREPARING'}"><span class="label label-primary">배송준비중</span></c:when>
											<c:when test="${resultVo.orderStatus == 'SHIPPING'}"><span class="label label-info">배송중</span></c:when>
											<c:when test="${resultVo.orderStatus == 'DELIVERED'}"><span class="label label-success" style="background-color: #00a65a !important;">배송완료</span></c:when>
											<c:when test="${resultVo.orderStatus == 'RETURN_REQUESTED'}"><span class="label label-warning">반품요청</span></c:when>
											<c:when test="${resultVo.orderStatus == 'EXCHANGED'}"><span class="label label-info">교환완료</span></c:when>
											<c:otherwise><span class="label label-default">${resultVo.orderStatus}</span></c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<th>결제금액</th>
									<td>${resultVo.totalPayAmount}원 (상품: ${resultVo.totalItemAmount} / 배송비: ${resultVo.deliveryFee} / 할인: ${resultVo.discountAmount})</td>
									<th>주문일시</th>
									<td>${resultVo.orderedAt}</td>
								</tr>
								<tr>
									<th>수령인</th>
									<td>${resultVo.receiverName} (${resultVo.receiverPhone})</td>
									<th>배송지</th>
									<td>[${resultVo.zipCode}] ${resultVo.address1} ${resultVo.address2}</td>
								</tr>
								<tr>
									<th>배송메모</th>
									<td colspan="3">${resultVo.orderMemo}</td>
								</tr>
							</table>
						</div>

						<div class="box-header with-border" style="margin-top:20px;">
							<h3 class="box-title">주문 상품 목록</h3>
						</div>
						<div class="box-body">
							<table class="table table-bordered">
								<thead>
									<tr class="text-center">
										<th>상품명</th>
										<th>옵션</th>
										<th>단가</th>
										<th>수량</th>
										<th>합계</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="item" items="${itemList}">
										<tr class="text-center">
											<td class="text-left">${item.productName}</td>
											<td>${item.optionName}</td>
											<td class="text-right">${item.unitPrice}원</td>
											<td>${item.quantity}</td>
											<td class="text-right">${item.unitPrice * item.quantity}원</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>

						<div class="box-footer text-right">
							<c:if test="${resultVo.orderStatus == 'PAID'}">
								<button type="button" class="btn btn-danger" onclick="fnCancel();">주문 취소</button>
							</c:if>
							<button type="button" class="btn btn-default" onclick="fnList();">목록으로</button>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>
	<%@ include file="/common/inc/footer.jspf" %>
</div>
</body>
</html>
