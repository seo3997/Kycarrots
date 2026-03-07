<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@ page import="com.whomade.kycarrots.framework.common.util.CommboUtil" %>
<%@ page import="java.util.List" %>

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
			if(confirm("정말로 이 주문을 결제취소하시겠습니까? (결제 취소 API가 호출됩니다)")){
				var reason = prompt("취소 사유를 입력하세요", "관리자 직접 취소");
				if(reason != null){
					location.href = "/mgt/order/cancelOrder.do?orderId=${resultVo.orderId}&cancelReason=" + encodeURIComponent(reason);
				}
			}
		}
		function fnUpdateShipping(){
			var deliveryCompanyCode = $("#deliveryCompanyCode").val();
			var trackingNo = $("#trackingNo").val();
			
			if(!deliveryCompanyCode){
				alert("택배사를 선택해주세요.");
				return;
			}
			if(!trackingNo){
				alert("운송장 번호를 입력해주세요.");
				return;
			}
			
			if(confirm("배송 정보를 업데이트하시겠습니까?")){
				location.href = "/mgt/order/updateOrderShippingInfo.do?orderId=${resultVo.orderId}&deliveryCompanyCode=" + deliveryCompanyCode + "&trackingNo=" + trackingNo;
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
											<c:when test="${resultVo.orderStatus == '10'}"><span class="label label-default">결제 대기</span></c:when>
											<c:when test="${resultVo.orderStatus == '20'}"><span class="label label-warning">결제 실패</span></c:when>
											<c:when test="${resultVo.orderStatus == '30'}"><span class="label label-success">결제완료</span></c:when>
											<c:when test="${resultVo.orderStatus == '40'}"><span class="label label-danger">주문취소</span></c:when>
											<c:when test="${resultVo.orderStatus == '50'}"><span class="label label-primary">배송준비중</span></c:when>
											<c:when test="${resultVo.orderStatus == '60'}"><span class="label label-info">배송중</span></c:when>
											<c:when test="${resultVo.orderStatus == '70'}"><span class="label label-success" style="background-color: #00a65a !important;">배송완료</span></c:when>
											<c:when test="${resultVo.orderStatus == '80'}"><span class="label label-warning">반품요청</span></c:when>
											<c:when test="${resultVo.orderStatus == '90'}"><span class="label label-info">교환완료</span></c:when>
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
								<tr>
									<th>택배사</th>
									<td>
										<c:choose>
											<c:when test="${ssAuthorId == 'ROLE_PROJ'}">
												${not empty resultVo.deliveryCompanyNm ? resultVo.deliveryCompanyNm : '-'}
											</c:when>
											<c:otherwise>
												<select id="deliveryCompanyCode" class="form-control input-sm" style="width: 150px; display: inline-block;">
													<%=CommboUtil.getComboStr((List)request.getAttribute("deliveryCompanyList"), "CODE", "CODE_NM", (String)((com.whomade.kycarrots.entity.payment.OrderVo)request.getAttribute("resultVo")).getDeliveryCompanyCode() , "C")%>
												</select>
											</c:otherwise>
										</c:choose>
									</td>
									<th>운송장번호</th>
									<td>
										<c:choose>
											<c:when test="${ssAuthorId == 'ROLE_PROJ'}">
												${not empty resultVo.trackingNo ? resultVo.trackingNo : '-'}
											</c:when>
											<c:otherwise>
												<input type="text" id="trackingNo" class="form-control input-sm" style="width: 200px; display: inline-block;" value="${resultVo.trackingNo}">
												<button type="button" class="btn btn-sm btn-primary" onclick="fnUpdateShipping();" style="margin-left: 5px;">업데이트</button>
											</c:otherwise>
										</c:choose>
									</td>
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
							<c:if test="${resultVo.orderStatus != '40'}">
								<button type="button" class="btn btn-danger" onclick="fnCancel();">결제취소</button>
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
