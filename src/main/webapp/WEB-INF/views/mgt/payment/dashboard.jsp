<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.StringUtil" %>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>

<jsp:useBean id="stats" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="resultList" type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>

<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>

<html>
<head>
    <%@ include file="/common/inc/meta.jspf" %>
    <title><%=headTitle%></title>
    <%@ include file="/common/inc/cssScript.jspf" %>
    <script type="text/javascript">
        function fnSearch() {
            $('#aform').attr({ action : '/mgt/payment/dashboard.do', method : 'get' }).submit();
        }
        
        function fnOrder(orderId) {
            location.href = "/mgt/order/selectOrder.do?orderId=" + orderId;
        }
        
        function fnCancel(paymentId, orderId, paymentKey) {
            if (confirm("정말로 결제를 취소하시겠습니까?")) {
                $.ajax({
                    url: '/mgt/payment/cancelPayment.do',
                    type: 'POST',
                    data: {
                        paymentId: paymentId,
                        orderId: orderId,
                        paymentKey: paymentKey,
                        cancelReason: "관리자 직접 취소"
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
    </script>
</head>
<body class="hold-transition skin-green-light sidebar-mini">
<div class="wrapper">
    <c:import url="/common/inc/header.do" charEncoding="utf-8" />
    <c:import url="/common/inc/menu.do" charEncoding="utf-8" />

    <div class="content-wrapper">
        <section class="content-header">
            <div id="navi"><i class="fa fa-home f12 color-lgray"></i> Home &gt; 결제 관리 &gt; 대시보드</div>
            <h1>결제 현황 대시보드</h1>
        </section>

        <section class="content container-fluid">
            <!-- Stats Widgets -->
            <div class="row">
                <div class="col-lg-3 col-xs-6">
                    <div class="small-box bg-aqua" style="border-radius: 10px; padding: 20px; color: white; background-color: #00c0ef !important;">
                        <div class="inner">
                            <h3><%=StringUtil.setComma(stats.getString("todayTotalAmount"))%>원</h3>
                            <p>오늘 결제 금액</p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-xs-6">
                    <div class="small-box bg-green" style="border-radius: 10px; padding: 20px; color: white; background-color: #00a65a !important;">
                        <div class="inner">
                            <h3><%=stats.getString("todayPaidCount")%>건</h3>
                            <p>오늘 결제 건수</p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-xs-6">
                    <div class="small-box bg-red" style="border-radius: 10px; padding: 20px; color: white; background-color: #dd4b39 !important;">
                        <div class="inner">
                            <h3><%=StringUtil.setComma(stats.getString("todayCancelledAmount"))%>원</h3>
                            <p>오늘 취소 금액</p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-xs-6">
                    <div class="small-box bg-red" style="border-radius: 10px; padding: 20px; color: white; background-color: #dd4b39 !important;">
                        <div class="inner">
                            <h3><%=stats.getString("todayCancelledCount")%>건</h3>
                            <p>오늘 취소 건수</p>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Search Filter -->
            <div class="box box-default" style="margin-top: 20px;">
                <div class="box-header with-border">
                    <h3 class="box-title">검색 및 필터</h3>
                </div>
                <div class="box-body">
                    <form id="aform" method="get" action="/mgt/payment/dashboard.do">
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>결제 상태</label>
                                    <select name="paymentStatus" class="form-control">
                                        <option value="">전체</option>
                                        <option value="10" ${param.paymentStatus == '10' ? 'selected' : ''}>준비</option>
                                        <option value="30" ${param.paymentStatus == '30' ? 'selected' : ''}>결제완료</option>
                                        <option value="40" ${param.paymentStatus == '40' ? 'selected' : ''}>취소됨</option>
                                        <option value="20" ${param.paymentStatus == '20' ? 'selected' : ''}>실패</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>결제 수단</label>
                                    <select name="paymentMethod" class="form-control">
                                        <option value="">전체</option>
                                        <option value="CARD" ${param.paymentMethod == 'CARD' ? 'selected' : ''}>카드</option>
                                        <option value="TRANSFER" ${param.paymentMethod == 'TRANSFER' ? 'selected' : ''}>계좌이체</option>
                                        <option value="VIRTUAL_ACCOUNT" ${param.paymentMethod == 'VIRTUAL_ACCOUNT' ? 'selected' : ''}>가상계좌</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>기간 (시작일 ~ 종료일)</label>
                                    <div class="input-group">
                                        <input type="date" name="startDate" class="form-control" value="${param.startDate}">
                                        <span class="input-group-addon">~</span>
                                        <input type="date" name="endDate" class="form-control" value="${param.endDate}">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-2" style="padding-top: 25px;">
                                <button type="button" class="btn btn-primary btn-block" onclick="fnSearch()">검색</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Recent List -->
            <div class="box box-primary" style="margin-top: 20px;">
                <div class="box-header">
                    <h3 class="box-title">최근 결제 내역</h3>
                </div>
                <div class="box-body table-responsive no-padding">
                    <table class="table table-hover table-bordered">
                        <thead>
                            <tr class="bg-gray">
                                <th>주문번호</th>
                                <th>수령인</th>
                                <th>상품명</th>
                                <th>결제금액</th>
                                <th>상태</th>
                                <th>결제수단</th>
                                <th>결제일시</th>
                                <th>관리</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${resultList}">
                                <tr>
                                    <td><a href="javascript:fnOrder('${item.ORDER_ID}')">${item.ORDER_NO}</a></td>
                                    <td>${item.RECEIVER_NAME}</td>
                                    <td>${item.productNames}</td>
                                    <td class="text-right">${StringUtil.setComma(item.AMOUNT_TOTAL)}원</td>
                                    <td style="cursor: pointer;" onclick="fnOrder('${item.ORDER_ID}')">
                                        <c:choose>
                                             <c:when test="${item.PAYMENT_STATUS == '30'}"><span class="label label-success">결제완료</span></c:when>
                                             <c:when test="${item.PAYMENT_STATUS == '40'}"><span class="label label-danger">취소됨</span></c:when>
                                            <c:otherwise><span class="label label-default">${item.PAYMENT_STATUS}</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${item.PAYMENT_METHOD}</td>
                                    <td>${item.PAID_AT}</td>
                                    <td>
                                         <c:if test="${item.PAYMENT_STATUS == '30'}">
                                            <button type="button" class="btn btn-xs btn-danger" onclick="fnCancel('${item.PAYMENT_ID}', '${item.ORDER_ID}', '${item.PG_TID}')">결제취소</button>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty resultList}">
                                <tr>
                                    <td colspan="8" class="text-center">데이터가 없습니다.</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </section>
    </div>

    <%@ include file="/common/inc/footer.jspf" %>
</div>
</body>
</html>
