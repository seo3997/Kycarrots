<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@ page import="com.whomade.kycarrots.framework.common.constant.Const"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@ page import="com.whomade.kycarrots.framework.common.util.StringUtil" %>
<%@ page import="java.util.List" %>

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
		function fnSelect(orderId){
			location.href = "/mgt/order/selectOrder.do?orderId=" + orderId;
		}
		
		function fnSearch(){
			$('#aform').attr({ action : '/mgt/order/selectPageListOrder.do', method : 'get' }).submit();
		}
		
		function fnCancel(orderId){
			if(confirm("정말로 결제취소하시겠습니까? (결제 취소 API가 호출됩니다)")){
				var reason = prompt("취소 사유를 입력하세요", "관리자 직접 취소");
				if(reason != null){
					$.ajax({
						url: '/mgt/payment/cancelPayment.do',
						type: 'POST',
						data: {
							orderId: orderId,
							cancelReason: reason
						},
						success: function(res) {
							if (res.success) {
								alert("취소되었습니다.");
								location.reload();
							} else {
								alert("취소 실패: " + res.message);
							}
						}
						});
				}
			}
		}

		function fnRequestBranchDeposit(orderId, orderNo) {
			if (confirm('본사에 입금 확인 요청을 보내시겠습니까?\n주문번호: ' + orderNo)) {
				location.href = '/mgt/order/requestBranchDeposit.do?orderId=' + orderId + '&orderNo=' + orderNo;
			}
		}

		function fnConfirmOrder(orderId) {
			if (confirm('해당 주문을 확정 처리하시겠습니까?\n확정 후에는 취소/반품이 불가능합니다.')) {
				location.href = '/mgt/order/confirmOrder.do?orderId=' + orderId;
			}
		}

		function fnConfirmDeposit(orderId, btn) {
			var $tr = $(btn).closest('tr');
			var deliveryCompanyCode = $tr.find('[name=deliveryCompanyCode]').val();
			var trackingNo = $tr.find('[name=trackingNo]').val();

			if(!deliveryCompanyCode){
				alert("입금 확인 시 택배사를 먼저 선택해주세요.");
				return;
			}
			if(!trackingNo){
				alert("입금 확인 시 운송장 번호를 먼저 입력해주세요.");
				return;
			}

			if (confirm('해당 주문의 입금을 확인하고 배송 처리(60:배송중)를 진행하시겠습니까?')) {
				location.href = '/mgt/order/confirmBranchDeposit.do?orderId=' + orderId + '&deliveryCompanyCode=' + deliveryCompanyCode + '&trackingNo=' + trackingNo;
			}
		}

		function fnUpdateShipping(orderId, btn) {
			var $tr = $(btn).closest('tr');
			var deliveryCompanyCode = $tr.find('[name=deliveryCompanyCode]').val();
			var trackingNo = $tr.find('[name=trackingNo]').val();

			if(!deliveryCompanyCode){
				alert("택배사를 선택해주세요.");
				return;
			}
			if(!trackingNo){
				alert("운송장 번호를 입력해주세요.");
				return;
			}

			if (confirm('배송 정보를 업데이트하시겠습니까?')) {
				location.href = '/mgt/order/updateOrderShippingInfo.do?orderId=' + orderId + '&deliveryCompanyCode=' + deliveryCompanyCode + '&trackingNo=' + trackingNo;
			}
		}

		function fnDeliveryComplete(orderId, btn) {
			var $tr = $(btn).closest('tr');
			var deliveryCompanyCode = $tr.find('[name=deliveryCompanyCode]').val();
			var trackingNo = $tr.find('[name=trackingNo]').val();

			if(!deliveryCompanyCode){
				alert("배송 완료 처리 시 택배사를 먼저 선택해주세요.");
				return;
			}
			if(!trackingNo){
				alert("배송 완료 처리 시 운송장 번호를 먼저 입력해주세요.");
				return;
			}

			if (confirm('배송 완료 처리를 진행하시겠습니까?')) {
				location.href = '/mgt/order/updateOrderShippingInfo.do?orderId=' + orderId + '&deliveryCompanyCode=' + deliveryCompanyCode + '&trackingNo=' + trackingNo + '&orderStatus=70';
			}
		}

		function fnGoPage(pageNo) {
			$("#currentPage").val(pageNo);
			$("#aform").attr("action", "/mgt/order/selectPageListOrder.do");
			$("#aform").submit();
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
											<option value="20" ${param.sch_order_status == '20' ? 'selected' : ''}>결제 실패</option>
											<option value="30" ${param.sch_order_status == '30' ? 'selected' : ''}>결제완료</option>
											<option value="40" ${param.sch_order_status == '40' ? 'selected' : ''}>주문취소</option>
											<option value="50" ${param.sch_order_status == '50' ? 'selected' : ''}>배송준비중</option>
											<option value="60" ${param.sch_order_status == '60' ? 'selected' : ''}>배송중</option>
											<option value="70" ${param.sch_order_status == '70' ? 'selected' : ''}>배송완료</option>
											<option value="80" ${param.sch_order_status == '80' ? 'selected' : ''}>반품요청</option>
											<option value="90" ${param.sch_order_status == '90' ? 'selected' : ''}>교환완료</option>
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
										<th>본사입금확인</th>
										<th>택배사</th>
										<th>운송장번호</th>
										<th>주문일시</th>
										<th>관리</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="item" items="${resultList}">
										<tr onclick="fnSelect('${item.ORDER_ID}');" style="cursor:pointer;" class="text-center">
											<td>${item.ORDER_NO}</td>
											<td class="text-left">${item.PRODUCT_NAME}</td>
											<td class="text-right">${item.TOTAL_PAY_AMOUNT}원</td>
											<td>
												<c:choose>
													<c:when test="${item.ORDER_STATUS == '10'}"><span class="label label-default">결제 대기</span></c:when>
													<c:when test="${item.ORDER_STATUS == '20'}"><span class="label label-warning">결제 실패</span></c:when>
													<c:when test="${item.ORDER_STATUS == '30'}"><span class="label label-success">결제완료</span></c:when>
													<c:when test="${item.ORDER_STATUS == '40'}"><span class="label label-danger">주문취소</span></c:when>
													<c:when test="${item.ORDER_STATUS == '50'}"><span class="label label-primary">배송준비중</span></c:when>
													<c:when test="${item.ORDER_STATUS == '60'}"><span class="label label-info">배송중</span></c:when>
													<c:when test="${item.ORDER_STATUS == '70'}"><span class="label label-success" style="background-color: #00a65a !important;">배송완료</span></c:when>
													<c:when test="${item.ORDER_STATUS == '80'}"><span class="label label-warning">반품요청</span></c:when>
													<c:when test="${item.ORDER_STATUS == '90'}"><span class="label label-info">교환완료</span></c:when>
													<c:otherwise><span class="label label-default">${not empty item.ORDER_STATUS_NM ? item.ORDER_STATUS_NM : item.ORDER_STATUS}</span></c:otherwise>
												</c:choose>
											</td>
											<td>
												<c:choose>
													<c:when test="${item.BRANCH_DEPOSIT_STATUS == '10'}"><span class="label label-default">입금대기</span></c:when>
													<c:when test="${item.BRANCH_DEPOSIT_STATUS == '20'}"><span class="label label-info">입금확인요청</span></c:when>
													<c:when test="${item.BRANCH_DEPOSIT_STATUS == '30'}"><span class="label label-success">입금완료</span></c:when>
													<c:otherwise><span class="label label-default">${item.BRANCH_DEPOSIT_STATUS_NM}</span></c:otherwise>
												</c:choose>
											</td>
											<td>
												<c:if test="${(ssAuthorId == 'ROLE_ADMIN' || ssAuthorId == 'ROLE_SELL') && item.ORDER_STATUS != '40'}">
													<select name="deliveryCompanyCode" class="form-control input-sm" style="width: 100px;" onclick="event.stopPropagation();">
														<%=CommboUtil.getComboStr((List)request.getAttribute("deliveryCompanyList"), "CODE", "CODE_NM", (String)((DataMap)pageContext.getAttribute("item")).getString("DELIVERY_COMPANY_CODE") , "C")%>
													</select>
												</c:if>
												<c:if test="${ssAuthorId == 'ROLE_PROJ' || item.ORDER_STATUS == '40'}">
													${item.DELIVERY_COMPANY_NM}
												</c:if>
											</td>
											<td>
												<c:if test="${(ssAuthorId == 'ROLE_ADMIN' || ssAuthorId == 'ROLE_SELL') && item.ORDER_STATUS != '40'}">
													<input type="text" name="trackingNo" class="form-control input-sm" style="width: 120px;" value="${item.TRACKING_NO}" onclick="event.stopPropagation();">
												</c:if>
												<c:if test="${ssAuthorId == 'ROLE_PROJ' || item.ORDER_STATUS == '40'}">
													${item.TRACKING_NO}
												</c:if>
											</td>
											<td>${item.ORDERED_AT}</td>
											<td>
												<c:if test="${item.ORDER_STATUS != '40' && ssAuthorId != 'ROLE_SELL'}">
													<button type="button" class="btn btn-xs btn-danger" onclick="event.stopPropagation(); fnCancel('${item.ORDER_ID}');">주문취소</button>
												</c:if>
												<c:if test="${(ssAuthorId == 'ROLE_ADMIN' || ssAuthorId == 'ROLE_SELL') && item.ORDER_STATUS != '40' && (item.BRANCH_DEPOSIT_STATUS == '10' || item.BRANCH_DEPOSIT_STATUS == '20')}">
													<button type="button" class="btn btn-xs btn-primary" onclick="event.stopPropagation(); fnConfirmDeposit('${item.ORDER_ID}', this)">입금확인</button>
												</c:if>
												<c:if test="${(ssAuthorId == 'ROLE_ADMIN' || ssAuthorId == 'ROLE_SELL') && item.ORDER_STATUS != '40' && item.BRANCH_DEPOSIT_STATUS == '30'}">
													<c:if test="${item.ORDER_STATUS == '50' || item.ORDER_STATUS == '60'}">
														<button type="button" class="btn btn-xs btn-info" onclick="event.stopPropagation(); fnUpdateShipping('${item.ORDER_ID}', this)">배송정보 업데이트</button>
													</c:if>
													<c:if test="${item.ORDER_STATUS == '60'}">
														<button type="button" class="btn btn-xs btn-primary" onclick="event.stopPropagation(); fnDeliveryComplete('${item.ORDER_ID}', this)">배송완료</button>
													</c:if>
												</c:if>
												<c:if test="${ssAuthorId == 'ROLE_PROJ' && item.ORDER_STATUS == '50' && (item.BRANCH_DEPOSIT_STATUS == '10' || item.BRANCH_DEPOSIT_STATUS == '20')}">
													<button type="button" class="btn btn-xs btn-info" onclick="event.stopPropagation(); fnRequestBranchDeposit('${item.ORDER_ID}', '${item.ORDER_NO}')">입금확인요청</button>
												</c:if>
												<c:if test="${ssAuthorId == 'ROLE_PROJ' && item.ORDER_STATUS == '70'}">
													<button type="button" class="btn btn-xs btn-success" onclick="event.stopPropagation(); fnConfirmOrder('${item.ORDER_ID}')">주문확정</button>
												</c:if>
												<button type="button" class="btn btn-xs btn-default" onclick="event.stopPropagation(); fnSelect('${item.ORDER_ID}');">상세</button>
											</td>
										</tr>
									</c:forEach>
									<c:if test="${empty resultList}">
										<tr>
											<td colspan="7" class="text-center">데이터가 없습니다.</td>
										</tr>
									</c:if>
								</tbody>
							</table>
						</div>
						<div class="box-footer text-center">
							${navigationBar}
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
