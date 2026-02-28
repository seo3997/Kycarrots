<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@ page import="com.whomade.kycarrots.framework.common.constant.Const"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@ page import="com.whomade.kycarrots.framework.common.util.StringUtil" %>

<jsp:useBean id="resultList"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>

<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>

<html>
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>
	<script type="text/javascript">
		function fnSelect(orderNo){
			location.href = "/mgt/order/selectOrder.do?orderNo=" + orderNo;
		}
		
		function fnSearch(){
			$('#aform').attr({ action : '/mgt/order/selectPageListOrder.do', method : 'get' }).submit();
		}
	</script>
</head>
<body class="hold-transition skin-green-light sidebar-mini">
<div class="wrapper">
	<c:import url="/common/inc/header.do" charEncoding="utf-8" />
	<c:import url="/common/inc/menu.do" charEncoding="utf-8" />

	<div class="content-wrapper">
		<section class="content-header">
			<div id="navi"><i class="fa fa-home f12 color-lgray"></i> Home > 주문관리</div>
			<div id="pagetitle"><h1>주문 목록</h1></div>
		</section>

		<section class="content container-fluid">
			<div class="row">
		   		<div class="col-12">
					<form role="form" id="aform" method="get" action="/mgt/order/selectPageListOrder.do">
						<div class="box-header">
							<div class="col-12 form-row">
								<div class="row col-md-6 mb-1 form-group form-inline">
									<label class="control-label col-md-2 px-0">주문번호</label>
									<div class="col-md-9 px-0">
										<input type="text" class="form-control w-100" name="sch_order_no" value="${param.sch_order_no}" />
									</div>
								</div>
								<div class="row col-md-6 mb-1 form-group form-inline">
									<label class="control-label col-md-2 px-0">주문상태</label>
									<div class="col-md-9 px-0">
										<select name="sch_order_status" class="form-control w-100">
											<option value="">전체</option>
											<option value="READY" ${param.sch_order_status == 'READY' ? 'selected' : ''}>결제 대기</option>
											<option value="FAILED" ${param.sch_order_status == 'FAILED' ? 'selected' : ''}>결제 실패</option>
											<option value="PAID" ${param.sch_order_status == 'PAID' ? 'selected' : ''}>결제완료</option>
											<option value="CANCEL" ${param.sch_order_status == 'CANCEL' ? 'selected' : ''}>주문취소</option>
											<option value="PREPARING" ${param.sch_order_status == 'PREPARING' ? 'selected' : ''}>배송준비중</option>
											<option value="SHIPPING" ${param.sch_order_status == 'SHIPPING' ? 'selected' : ''}>배송중</option>
											<option value="DELIVERED" ${param.sch_order_status == 'DELIVERED' ? 'selected' : ''}>배송완료</option>
											<option value="RETURN_REQUESTED" ${param.sch_order_status == 'RETURN_REQUESTED' ? 'selected' : ''}>반품요청</option>
											<option value="EXCHANGED" ${param.sch_order_status == 'EXCHANGED' ? 'selected' : ''}>교환완료</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col text-right">
								<button type="button" class="btn btn-search ml-2" onclick="fnSearch();"><i class="fa fa-search"></i> 검색</button>
							</div>
						</div>

						<div class="box-body table-responsive">
							<table class="table table-bordered table-hover">
								<thead>
									<tr class="text-center">
										<th>주문번호</th>
										<th>상품명</th>
										<th>결제금액</th>
										<th>주문상태</th>
										<th>결제상태</th>
										<th>주문일시</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="item" items="${resultList}">
										<tr onclick="fnSelect('${item.ORDER_NO}');" style="cursor:pointer;" class="text-center">
											<td>${item.ORDER_NO}</td>
											<td class="text-left">${item.PRODUCT_NAME}</td>
											<td class="text-right">${item.TOTAL_PAY_AMOUNT}원</td>
											<td>
												<c:choose>
													<c:when test="${item.ORDER_STATUS == 'READY'}"><span class="label label-default">결제 대기</span></c:when>
													<c:when test="${item.ORDER_STATUS == 'FAILED'}"><span class="label label-warning">결제 실패</span></c:when>
													<c:when test="${item.ORDER_STATUS == 'PAID'}"><span class="label label-success">결제완료</span></c:when>
													<c:when test="${item.ORDER_STATUS == 'CANCEL'}"><span class="label label-danger">주문취소</span></c:when>
													<c:when test="${item.ORDER_STATUS == 'PREPARING'}"><span class="label label-primary">배송준비중</span></c:when>
													<c:when test="${item.ORDER_STATUS == 'SHIPPING'}"><span class="label label-info">배송중</span></c:when>
													<c:when test="${item.ORDER_STATUS == 'DELIVERED'}"><span class="label label-success" style="background-color: #00a65a !important;">배송완료</span></c:when>
													<c:when test="${item.ORDER_STATUS == 'RETURN_REQUESTED'}"><span class="label label-warning">반품요청</span></c:when>
													<c:when test="${item.ORDER_STATUS == 'EXCHANGED'}"><span class="label label-info">교환완료</span></c:when>
													<c:otherwise><span class="label label-default">${item.ORDER_STATUS}</span></c:otherwise>
												</c:choose>
											</td>
											<td>
												<c:choose>
													<c:when test="${item.PAYMENT_STATUS == 'READY'}"><span class="badge badge-default">대기</span></c:when>
													<c:when test="${item.PAYMENT_STATUS == 'PAID'}"><span class="badge badge-success">결제완료</span></c:when>
													<c:when test="${item.PAYMENT_STATUS == 'CANCEL'}"><span class="badge badge-danger">결제취소</span></c:when>
													<c:when test="${item.PAYMENT_STATUS == 'FAILED'}"><span class="badge badge-warning">결제실패</span></c:when>
													<c:otherwise>${item.PAYMENT_STATUS}</c:otherwise>
												</c:choose>
											</td>
											<td>${item.ORDERED_AT}</td>
										</tr>
									</c:forEach>
									<c:if test="${empty resultList}">
										<tr>
											<td colspan="6" class="text-center">데이터가 없습니다.</td>
										</tr>
									</c:if>
								</tbody>
							</table>
						</div>
					</form>
				</div>
			</div>
		</section>
	</div>
	<%@ include file="/common/inc/footer.jspf" %>
</div>
</body>
</html>
