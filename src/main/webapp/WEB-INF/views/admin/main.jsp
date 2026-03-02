<%@page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@ page import="com.whomade.kycarrots.framework.common.util.CommboUtil" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Data Beans --%>
<jsp:useBean id="prductCntMap" 		class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="dashboardStats" 	class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="dashboardOrderList" class="java.util.ArrayList"  								  scope="request" type="java.util.List"/>
<jsp:useBean id="deliveryCompanyList" class="java.util.ArrayList"  								  scope="request" type="java.util.List"/>
<jsp:useBean id="headQuarterBranch"   class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>

<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>
<html>
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary: #6366f1;
            --primary-light: #818cf8;
            --secondary: #10b981;
            --warning: #f59e0b;
            --danger: #ef4444;
            --info: #3b82f6;
            --bg-canvas: #f8fafc;
            --card-bg: rgba(255, 255, 255, 0.8);
            --text-main: #1e293b;
            --text-muted: #64748b;
            --radius-lg: 16px;
            --radius-md: 12px;
            --shadow-sm: 0 1px 3px rgba(0,0,0,0.1);
            --shadow-md: 0 4px 6px -1px rgba(0,0,0,0.1), 0 2px 4px -1px rgba(0,0,0,0.06);
        }

        body {
            font-family: 'Outfit', 'Pretendard', sans-serif;
            background-color: var(--bg-canvas);
            color: var(--text-main);
        }

        .dashboard-container {
            padding: 2rem;
            max-width: 1400px;
            margin: 0 auto;
        }

        .dashboard-header {
            display: flex;
            justify-content: space-between;
            align-items: flex-end;
            margin-bottom: 2rem;
        }

        .dashboard-title {
            font-size: 1.875rem;
            font-weight: 700;
            color: var(--text-main);
            margin: 0;
        }

        .dashboard-title span {
            color: var(--primary);
        }

        .dashboard-date {
            color: var(--text-muted);
            font-size: 0.875rem;
        }

        /* Stats Grid */
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
            gap: 1.5rem;
            margin-bottom: 2rem;
        }

        .stat-card {
            background: var(--card-bg);
            backdrop-filter: blur(8px);
            border: 1px solid rgba(255, 255, 255, 0.3);
            border-radius: var(--radius-lg);
            padding: 1.5rem;
            box-shadow: var(--shadow-sm);
            transition: transform 0.2s, box-shadow 0.2s;
        }

        .stat-card:hover {
            transform: translateY(-4px);
            box-shadow: var(--shadow-md);
        }

        .stat-label {
            display: block;
            color: var(--text-muted);
            font-size: 0.875rem;
            font-weight: 500;
            margin-bottom: 0.5rem;
        }

        .stat-value {
            display: block;
            font-size: 1.5rem;
            font-weight: 700;
            color: var(--text-main);
        }

        .stat-card.primary { border-top: 4px solid var(--primary); }
        .stat-card.secondary { border-top: 4px solid var(--secondary); }
        .stat-card.warning { border-top: 4px solid var(--warning); }
        .stat-card.info { border-top: 4px solid var(--info); }
        .stat-card.danger { border-top: 4px solid var(--danger); }

        .stat-value.primary { color: var(--primary); }
        .stat-value.secondary { color: var(--secondary); }
        .stat-value.warning { color: var(--warning); }
        .stat-value.info { color: var(--info); }
        .stat-value.danger { color: var(--danger); }

        /* Notice Banner */
        .notice-banner {
            background: #f1f5f9;
            border-radius: var(--radius-md);
            padding: 1rem 1.5rem;
            margin-bottom: 2rem;
            display: flex;
            align-items: center;
            gap: 1rem;
            font-size: 0.9rem;
            border-left: 4px solid var(--text-muted);
        }

        /* Content Card */
        .content-card {
            background: white;
            border-radius: var(--radius-lg);
            box-shadow: var(--shadow-sm);
            padding: 1.5rem;
            margin-bottom: 2rem;
        }

        .card-title-row {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1.5rem;
        }

        .card-title {
            font-size: 1.1rem;
            font-weight: 600;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        /* Table Styles */
        .table-custom {
            width: 100%;
            border-collapse: collapse;
        }

        .table-custom th {
            text-align: left;
            padding: 1rem;
            color: var(--text-muted);
            font-weight: 500;
            font-size: 0.85rem;
            border-bottom: 1px solid #f1f5f9;
        }

        .table-custom td {
            padding: 1rem;
            font-size: 0.9rem;
            border-bottom: 1px solid #f8fafc;
        }

        .table-custom tr:hover td {
            background-color: #f8fafc;
        }

        .badge-status {
            padding: 0.25rem 0.6rem;
            border-radius: 6px;
            font-size: 0.75rem;
            font-weight: 600;
        }

        .badge-waiting { background: #fff7ed; color: #ea580c; }
        .badge-confirmed { background: #ecfdf5; color: #059669; }
        .badge-shipping { background: #eff6ff; color: #2563eb; }

        .btn-action {
            padding: 0.4rem 0.8rem;
            border-radius: 6px;
            font-size: 0.8rem;
            font-weight: 500;
            border: none;
            cursor: pointer;
            transition: opacity 0.2s;
        }

        .btn-action.primary { background: var(--primary); color: white; }
        .btn-action.secondary { background: var(--secondary); color: white; }

        .excel-btn {
            background: #059669;
            color: white;
            padding: 0.5rem 1rem;
            border-radius: 8px;
            font-size: 0.85rem;
            font-weight: 500;
            display: flex;
            align-items: center;
            gap: 0.5rem;
            text-decoration: none;
        }

    </style>
</head>
<body class="hold-transition skin-green-light">
<div class="wrapper">
	<c:import url="/common/inc/header.do" charEncoding="utf-8" />
	
    <div class="content-wrapper">
        <div class="dashboard-container">

            <c:choose>
                <%-- SYSTEM ADMIN ROLE: System Status Dashboard --%>
                <c:when test="${memberCode == 'ROLE_ADMIN'}">
                    <div class="dashboard-header">
                        <h1 class="dashboard-title">시스템 관리자 모드 <span>[asagong Control]</span></h1>
                        <span class="dashboard-date"><%=DateUtil.getToday("yyyy년 MM월 dd일")%> 기준</span>
                    </div>

                    <div class="stats-grid">
                        <div class="stat-card primary">
                            <span class="stat-label">총 사용자 건수</span>
                            <span class="stat-value primary"><fmt:formatNumber value="${dashboardStats.totalUsers}" />명</span>
                        </div>
                        <div class="stat-card secondary">
                            <span class="stat-label">등록된 지점 수</span>
                            <span class="stat-value secondary"><fmt:formatNumber value="${dashboardStats.totalBranches}" />개</span>
                        </div>
                        <div class="stat-card info">
                            <span class="stat-label">누적 완료 주문</span>
                            <span class="stat-value info"><fmt:formatNumber value="${dashboardStats.totalOrders}" />건</span>
                        </div>
                        <div class="stat-card warning">
                            <span class="stat-label">총 누적 매출액</span>
                            <span class="stat-value warning">₩<fmt:formatNumber value="${dashboardStats.totalRevenue}" /></span>
                        </div>
                    </div>

                    <div class="content-card">
                        <div class="card-title-row">
                            <h2 class="card-title">🚀 본사 주문 현황 요약</h2>
                        </div>
                        <div class="table-responsive">
                            <table class="table-custom">
                                <thead>
                                    <tr>
                                        <th>지점명</th>
                                        <th>주문번호</th>
                                        <th>공급가액</th>
                                        <th>입금상태</th>
                                        <th>배송상태</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="order" items="${dashboardOrderList}">
                                        <tr>
                                            <td>${order.BRANCH_NAME}</td>
                                            <td><a href="/mgt/order/selectOrder.do?orderNo=${order.ORDER_NO}" style="color: var(--primary); font-weight: 600; text-decoration: underline;">${order.ORDER_NO}</a></td>
                                            <td>₩<fmt:formatNumber value="${order.SUPPLY_PRICE_SUM}" /></td>
                                            <td>
                                                <span class="badge-status ${order.BRANCH_DEPOSIT_STATUS == '10' ? 'badge-waiting' : 'badge-confirmed'}">
                                                    ${order.BRANCH_DEPOSIT_STATUS == '10' ? '지점입금대기' : '입금확인완료'}
                                                </span>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${order.ORDER_STATUS == '10'}">결제대기</c:when>
                                                    <c:when test="${order.ORDER_STATUS == '30'}">결제완료</c:when>
                                                    <c:when test="${order.ORDER_STATUS == '40'}">주문취소</c:when>
                                                    <c:when test="${order.ORDER_STATUS == '60'}">배송중</c:when>
                                                    <c:when test="${order.ORDER_STATUS == '70'}">배송완료</c:when>
                                                    <c:otherwise>${order.ORDER_STATUS}</c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:when>

                <%-- SELL ROLE: HQ Dashboard (Image 2) --%>
                <c:when test="${memberCode == 'ROLE_SELL'}">
                    <div class="dashboard-header">
                        <h1 class="dashboard-title">본사 통합 관리 시스템</h1>
                        <span class="dashboard-date"><%=DateUtil.getToday("yyyy년 MM월 dd일")%> 기준</span>
                    </div>

                    <div class="stats-grid">
                        <div class="stat-card info">
                            <span class="stat-label">미처리 주문 (전체)</span>
                            <span class="stat-value"><fmt:formatNumber value="${dashboardStats.unprocessedOrders}" />건</span>
                        </div>
                        <div class="stat-card danger">
                            <span class="stat-label">지점 미입금액 (수금대상)</span>
                            <span class="stat-value danger">₩<fmt:formatNumber value="${dashboardStats.branchPendingAmount}" /></span>
                        </div>
                        <div class="stat-card secondary">
                            <span class="stat-label">출고 대기 (입금확인완료)</span>
                            <span class="stat-value secondary"><fmt:formatNumber value="${dashboardStats.shipmentPending}" />건</span>
                        </div>
                        <div class="stat-card info">
                            <span class="stat-label">배송 중인 주문</span>
                            <span class="stat-value info"><fmt:formatNumber value="${dashboardStats.inTransit}" />건</span>
                        </div>
                    </div>

                    <div class="content-card">
                        <div class="card-title-row">
                            <h2 class="card-title">📦 지점 입금 및 배송 관리</h2>
                        </div>
                        <div class="table-responsive">
                            <table class="table-custom">
                                <thead>
                                    <tr>
                                        <th>지점명</th>
                                        <th>주문번호</th>
                                        <th>입금액 (공급가)</th>
                                        <th>지점 입금상태</th>
                                        <th>택배사</th>
                                        <th>운송장 번호</th>
                                        <th>실행</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="order" items="${dashboardOrderList}">
                                        <tr>
                                            <td>${order.BRANCH_NAME}</td>
                                            <td><a href="/mgt/order/selectOrder.do?orderNo=${order.ORDER_NO}" style="color: var(--primary); font-weight: 600; text-decoration: underline;">${order.ORDER_NO}</a></td>
                                            <td>₩<fmt:formatNumber value="${order.SUPPLY_PRICE_SUM}" /></td>
                                            <td>
                                                <span class="badge-status ${order.BRANCH_DEPOSIT_STATUS == '10' ? 'badge-waiting' : 'badge-confirmed'}">
                                                    ${order.BRANCH_DEPOSIT_STATUS == '10' ? '지점입금대기' : '입금확인완료'}
                                                </span>
                                            </td>
                                            <td>
                                                 <select name="deliveryCompanyCode" class="form-control input-sm" style="width: 120px;">
                                                     <%=CommboUtil.getComboStr(deliveryCompanyList, "CODE", "CODE_NM", ((DataMap)pageContext.getAttribute("order")).getString("DELIVERY_COMPANY_CODE") , "C")%>
                                                 </select>
                                            </td>
                                            <td>
                                                <input type="text" name="trackingNo" class="form-control input-sm" placeholder="${order.BRANCH_DEPOSIT_STATUS == '10' ? '입금 확인 후 입력 가능' : '송장번호 입력'}" 
                                                       value="${order.TRACKING_NO}">
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${order.BRANCH_DEPOSIT_STATUS == '10'}">
                                                        <button type="button" class="btn-action primary" onclick="fnConfirmDeposit('${order.ORDER_NO}', this)">입금확인</button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${order.ORDER_STATUS == '50'}">
                                                                <button type="button" class="btn-action secondary" onclick="fnUpdateShipping('${order.ORDER_NO}', this)">배송처리</button>
                                                            </c:when>
                                                            <c:when test="${order.ORDER_STATUS == '60'}">
                                                                <button type="button" class="btn-action secondary" style="margin-bottom: 4px;" onclick="fnUpdateShipping('${order.ORDER_NO}', this)">정보수정</button>
                                                                <button type="button" class="btn-action primary" onclick="fnDeliveryComplete('${order.ORDER_NO}', this)">배송완료</button>
                                                            </c:when>
                                                            <c:otherwise>-</c:otherwise>
                                                        </c:choose>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty dashboardOrderList}">
                                        <tr><td colspan="8" style="text-align: center; color: var(--text-muted); padding: 3rem;">최근 주문 내역이 없습니다.</td></tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:when>

                <%-- PROJ ROLE: Branch Dashboard (Image 1) --%>
                <c:when test="${memberCode == 'ROLE_PROJ'}">
                    <div class="dashboard-header">
                        <h1 class="dashboard-title">지점 판매 어드민 <span>[${userInfoVo.branchName != null ? userInfoVo.branchName : '서울 강남점'}]</span></h1>
                        <span class="dashboard-date"><%=DateUtil.getToday("yyyy년 MM월 dd일")%> 기준</span>
                    </div>

                    <div class="stats-grid">
                        <div class="stat-card">
                            <span class="stat-label">오늘의 총 매출 (고객결제액)</span>
                            <span class="stat-value">₩<fmt:formatNumber value="${dashboardStats.todayTotalSales}" /></span>
                        </div>
                        <div class="stat-card secondary">
                            <span class="stat-label">나의 예상 순이익</span>
                            <span class="stat-value secondary">₩<fmt:formatNumber value="${dashboardStats.estimatedProfit}" /></span>
                        </div>
                        <div class="stat-card danger">
                            <span class="stat-label">본사 송금 대기 (배송지연주의)</span>
                            <span class="stat-value danger"><fmt:formatNumber value="${dashboardStats.remittancePending}" />건</span>
                        </div>
                        <div class="stat-card">
                            <span class="stat-label">완료된 주문 (이번 달)</span>
                            <span class="stat-value"><fmt:formatNumber value="${dashboardStats.completedOrders}" />건</span>
                        </div>
                    </div>

                    <div class="notice-banner">
                        <i class="fas fa-bullhorn" style="color: var(--text-muted);"></i>
                        <span>
                            <strong>본사 입금 안내:</strong> 본사 계좌(${headQuarterBranch.BANK_NM} ${headQuarterBranch.ACCOUNT_NO} / 예금주: ${headQuarterBranch.ACCOUNT_HOLDER})로 <strong>공급가액을 입금하셔야 배송이 시작됩니다.</strong>
                            입금 시 반드시 <strong>[주문번호 뒷 4자리 + 지점명]</strong>으로 입금자명을 설정해 주세요.
                        </span>
                    </div>

                    <div class="content-card">
                        <h2 class="card-title">📦 주문 및 본사 정산 현황</h2>
                        <div class="table-responsive">
                            <table class="table-custom">
                                <thead>
                                    <tr>
                                        <th>주문일시</th>
                                        <th>주문번호</th>
                                        <th>판매가</th>
                                        <th>공급가</th>
                                        <th>나의 수익</th>
                                        <th>정산</th>
                                        <th>택배사</th>
                                        <th>운송장번호</th>
                                        <th>상태</th>
                                        <th>관리</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="order" items="${dashboardOrderList}">
                                        <tr>
                                            <td>${order.ORDERED_AT}</td>
                                            <td><a href="/mgt/order/selectOrder.do?orderNo=${order.ORDER_NO}" style="color: var(--primary); font-weight: 600; text-decoration: underline;">${order.ORDER_NO}</a></td>
                                            <td>₩<fmt:formatNumber value="${order.TOTAL_PAY_AMOUNT}" /></td>
                                            <td style="color: var(--danger); font-weight: 500;">₩<fmt:formatNumber value="${order.SUPPLY_PRICE_SUM}" /></td>
                                             <td>₩<fmt:formatNumber value="${order.MY_PROFIT}" /></td>
                                             <td>
                                                 <span class="badge-status ${order.BRANCH_DEPOSIT_STATUS == '10' ? 'badge-waiting' : 'badge-confirmed'}">
                                                     ${order.BRANCH_DEPOSIT_STATUS == '10' ? '송금필요' : '확인됨'}
                                                 </span>
                                             </td>
                                             <td style="font-size: 0.85rem; color: var(--text-muted);">${not empty order.DELIVERY_COMPANY_NM ? order.DELIVERY_COMPANY_NM : '-'}</td>
                                             <td style="font-size: 0.85rem; color: var(--primary); font-family: monospace;">${not empty order.TRACKING_NO ? order.TRACKING_NO : '-'}</td>
                                             <td>
                                                 <c:choose>
                                                     <c:when test="${order.ORDER_STATUS == '30'}">결제완료</c:when>
                                                     <c:when test="${order.ORDER_STATUS == '50'}">배송준비중</c:when>
                                                     <c:when test="${order.ORDER_STATUS == '60'}">배송중</c:when>
                                                     <c:when test="${order.ORDER_STATUS == '70'}">배송완료</c:when>
                                                     <c:when test="${order.ORDER_STATUS == '80'}">반품요청</c:when>
                                                     <c:when test="${order.ORDER_STATUS == '89'}">반품완료</c:when>
                                                     <c:when test="${order.ORDER_STATUS == '99'}">주문확정</c:when>
                                                     <c:otherwise>${order.ORDER_STATUS}</c:otherwise>
                                                 </c:choose>
                                             </td>
                                             <td>
                                                 <div style="display: flex; flex-direction: column; gap: 4px;">
                                                     <c:if test="${order.ORDER_STATUS == '70'}">
                                                         <button type="button" class="btn-action primary" style="padding: 0.25rem 0.5rem; font-size: 0.75rem;" onclick="fnConfirmOrderByBranch('${order.ORDER_NO}')">주문확정</button>
                                                     </c:if>
                                                     <c:if test="${order.ORDER_STATUS == '30' || order.ORDER_STATUS == '50' || order.ORDER_STATUS == '60' || order.ORDER_STATUS == '70' || order.ORDER_STATUS == '80'}">
                                                         <button type="button" class="btn-action danger" style="padding: 0.25rem 0.5rem; font-size: 0.75rem;" onclick="fnCancelByBranch('${order.ORDER_NO}', '${order.ORDER_STATUS}')">
                                                             ${order.ORDER_STATUS == '80' ? '반품승인(환불)' : '결제취소'}
                                                         </button>
                                                     </c:if>
                                                 </div>
                                             </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty dashboardOrderList}">
                                        <tr><td colspan="10" style="text-align: center; color: var(--text-muted); padding: 3rem;">최근 주문 내역이 없습니다.</td></tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:when>

                <%-- Default or Other Roles --%>
                <c:otherwise>
                    <div class="dashboard-header">
                        <h1 class="dashboard-title">관리 시스템 <span>[ asagong ]</span></h1>
                    </div>
                    <div class="stats-grid">
                        <div class="stat-card">
                            <span class="stat-label">상품 현황</span>
                            <span class="stat-value">판매중: ${prductCntMap.saleCnt}건</span>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>

        </div>
    </div>

	<%@ include file="/common/inc/footer.jspf" %>
</div>

<%@ include file="/common/inc/msg.jspf" %>
<script type="text/javascript">
    function fnConfirmDeposit(orderNo, btn) {
        var $tr = $(btn).closest('tr');
        var $select = $tr.find('[name=deliveryCompanyCode]');
        var deliveryCompanyCode = $select.val();
        var deliveryCompanyName = $select.find('option:selected').text();
        var trackingNo = $tr.find('[name=trackingNo]').val();

        if (!deliveryCompanyCode) {
            alert('택배사를 선택해주세요.');
            $select.focus();
            return;
        }
        if (!trackingNo) {
            alert('운송장 번호를 입력해주세요.');
            $tr.find('[name=trackingNo]').focus();
            return;
        }

        var msg = "해당 주문의 지점 입금을 확인하고 배송 처리를 진행하시겠습니까?\n\n" +
                  "택배사: " + deliveryCompanyName + "\n" +
                  "운송장번호: " + trackingNo;

        if (confirm(msg)) {
            location.href = '/mgt/order/confirmBranchDeposit.do?orderNo=' + orderNo + 
                          '&deliveryCompanyCode=' + deliveryCompanyCode + 
                          '&trackingNo=' + trackingNo;
        }
    }

    function fnUpdateShipping(orderNo, btn) {
        var $tr = $(btn).closest('tr');
        var $select = $tr.find('[name=deliveryCompanyCode]');
        var deliveryCompanyCode = $select.val();
        var deliveryCompanyName = $select.find('option:selected').text();
        var trackingNo = $tr.find('[name=trackingNo]').val();

        if (!deliveryCompanyCode) {
            alert('택배사를 선택해주세요.');
            $select.focus();
            return;
        }
        if (!trackingNo) {
            alert('운송장 번호를 입력해주세요.');
            $tr.find('[name=trackingNo]').focus();
            return;
        }

        var msg = "배송 정보를 업데이트하시겠습니까?\n\n" +
                  "택배사: " + deliveryCompanyName + "\n" +
                  "운송장번호: " + trackingNo;

        if (confirm(msg)) {
            location.href = '/mgt/order/updateOrderShippingInfo.do?orderNo=' + orderNo + 
                          '&deliveryCompanyCode=' + deliveryCompanyCode + 
                          '&trackingNo=' + trackingNo +
                          '&orderStatus=60';
        }
    }
    function fnCancelByBranch(orderNo, status) {
        var msg = status === '80' ? '반품을 승인하고 환불 처리를 진행하시겠습니까?' : '해당 결제를 취소하시겠습니까?';
        var cancelReason = prompt('취소/반품 사유를 입력해주세요.', status === '80' ? '반품 승인 환불' : '지점 요청 취소');
        if (cancelReason === null) return;
        
        if (confirm(msg)) {
            location.href = '/mgt/order/cancelOrder.do?orderNo=' + orderNo + '&cancelReason=' + encodeURIComponent(cancelReason);
        }
    }

    function fnConfirmOrderByBranch(orderNo) {
        if (confirm('해당 주문을 확정 처리하시겠습니까?\n확정 후에는 취소/반품이 불가능합니다.')) {
            location.href = '/mgt/order/confirmOrder.do?orderNo=' + orderNo;
        }
    }

    function fnDeliveryComplete(orderNo, btn) {
        var $tr = $(btn).closest('tr');
        var $select = $tr.find('[name=deliveryCompanyCode]');
        var deliveryCompanyCode = $select.val();
        var deliveryCompanyName = $select.find('option:selected').text();
        var trackingNo = $tr.find('[name=trackingNo]').val();

        if (!deliveryCompanyCode) {
            alert('택배사를 선택해주세요.');
            $select.focus();
            return;
        }
        if (!trackingNo) {
            alert('운송장 번호를 입력해주세요.');
            $tr.find('[name=trackingNo]').focus();
            return;
        }

        var msg = "배송 완료 처리를 진행하시겠습니까?\n\n" +
                  "택배사: " + deliveryCompanyName + "\n" +
                  "운송장번호: " + trackingNo;

        if (confirm(msg)) {
            location.href = '/mgt/order/updateOrderShippingInfo.do?orderNo=' + orderNo + 
                          '&deliveryCompanyCode=' + deliveryCompanyCode + 
                          '&trackingNo=' + trackingNo +
                          '&orderStatus=70';
        }
    }
</script>
</body>
</html>
