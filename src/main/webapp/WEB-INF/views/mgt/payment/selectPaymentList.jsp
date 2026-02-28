<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.StringUtil" %>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>

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
            $('#aform').attr({ action : '/mgt/payment/selectPaymentList.do', method : 'get' }).submit();
        }
        
        function fnDetail(paymentId) {
            var url = "/mgt/payment/selectPaymentDetail.do?paymentId=" + paymentId;
            window.open(url, "paymentDetail", "width=800,height=700,scrollbars=yes");
        }
    </script>
</head>
<body class="hold-transition skin-green-light sidebar-mini">
<div class="wrapper">
    <c:import url="/common/inc/header.do" charEncoding="utf-8" />
    <c:import url="/common/inc/menu.do" charEncoding="utf-8" />

    <div class="content-wrapper">
        <section class="content-header">
            <div id="navi"><i class="fa fa-home f12 color-lgray"></i> Home &gt; 결제 관리 &gt; 결제 내역</div>
            <h1>결제 내역 관리</h1>
        </section>

        <section class="content container-fluid">
            <!-- Search Filter -->
            <div class="box box-default">
                <div class="box-header with-border">
                    <h3 class="box-title">검색 및 필터</h3>
                </div>
                <div class="box-body">
                    <form id="aform" method="get" action="/mgt/payment/selectPaymentList.do">
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>결제 상태</label>
                                    <select name="paymentStatus" class="form-control">
                                        <option value="">전체</option>
                                        <option value="READY" ${param.paymentStatus == 'READY' ? 'selected' : ''}>준비</option>
                                        <option value="PAID" ${param.paymentStatus == 'PAID' ? 'selected' : ''}>결제완료</option>
                                        <option value="CANCEL" ${param.paymentStatus == 'CANCEL' ? 'selected' : ''}>취소됨</option>
                                        <option value="FAILED" ${param.paymentStatus == 'FAILED' ? 'selected' : ''}>실패</option>
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
                                    <label>기간</label>
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

            <div class="box box-primary">
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
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${resultList}">
                                <tr onclick="fnDetail('${item.PAYMENT_ID}')" style="cursor:pointer;">
                                    <td>${item.ORDER_NO}</td>
                                    <td>${item.RECEIVER_NAME}</td>
                                    <td>${item.productNames}</td>
                                    <td class="text-right">${StringUtil.setComma(item.AMOUNT_TOTAL)}원</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${item.PAYMENT_STATUS == 'PAID'}"><span class="label label-success">결제완료</span></c:when>
                                            <c:when test="${item.PAYMENT_STATUS == 'CANCEL'}"><span class="label label-danger">취소됨</span></c:when>
                                            <c:otherwise><span class="label label-default">${item.PAYMENT_STATUS}</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${item.PAYMENT_METHOD}</td>
                                    <td>${item.PAID_AT}</td>
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
            </div>
        </section>
    </div>

    <%@ include file="/common/inc/footer.jspf" %>
</div>
</body>
</html>
