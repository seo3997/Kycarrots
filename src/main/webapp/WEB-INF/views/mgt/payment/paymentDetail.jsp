<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.StringUtil" %>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>

<jsp:useBean id="resultMap" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>

<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>

<html>
<head>
    <%@ include file="/common/inc/meta.jspf" %>
    <title>결제 상세 정보</title>
    <%@ include file="/common/inc/cssScript.jspf" %>
</head>
<body class="hold-transition">
<div class="pad" style="padding: 20px;">
    <section class="content-header">
        <h1>결제 상세 정보</h1>
    </section>

    <div class="row" style="margin-top: 20px;">
        <div class="col-md-6">
            <div class="box box-info">
                <div class="box-header with-border">
                    <h3 class="box-title">주문 정보</h3>
                </div>
                <div class="box-body">
                    <table class="table table-bordered">
                        <tr>
                            <th class="bg-gray" style="width: 30%;">주문번호</th>
                            <td>${resultMap.ORDER_NO}</td>
                        </tr>
                        <tr>
                            <th class="bg-gray">수령인</th>
                            <td>${resultMap.RECEIVER_NAME}</td>
                        </tr>
                        <tr>
                            <th class="bg-gray">연락처</th>
                            <td>${resultMap.RECEIVER_PHONE}</td>
                        </tr>
                        <tr>
                            <th class="bg-gray">주소</th>
                            <td>(${resultMap.ZIP_CODE}) ${resultMap.ADDRESS1} ${resultMap.ADDRESS2}</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        
        <div class="col-md-6">
            <div class="box box-success">
                <div class="box-header with-border">
                    <h3 class="box-title">PG 승인 정보</h3>
                </div>
                <div class="box-body">
                    <table class="table table-bordered">
                        <tr>
                            <th class="bg-gray" style="width: 30%;">상태</th>
                            <td><b>${resultMap.PAYMENT_STATUS}</b></td>
                        </tr>
                        <tr>
                            <th class="bg-gray">PG 거래번호</th>
                            <td>${resultMap.PG_TID}</td>
                        </tr>
                        <tr>
                            <th class="bg-gray">결제수단</th>
                            <td>${resultMap.PAYMENT_METHOD}</td>
                        </tr>
                        <tr>
                            <th class="bg-gray">카드사</th>
                            <td>${resultMap.CARD_COMPANY}</td>
                        </tr>
                        <tr>
                            <th class="bg-gray">카드번호</th>
                            <td>${resultMap.CARD_NUMBER_MASKED}</td>
                        </tr>
                        <tr>
                            <th class="bg-gray">영수증</th>
                            <td>
                                <c:if test="${not empty resultMap.RECEIPT_URL}">
                                    <a href="${resultMap.RECEIPT_URL}" target="_blank" class="btn btn-xs btn-default">영수증 보기</a>
                                </c:if>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">주문 상품 정보</h3>
                </div>
                <div class="box-body no-padding">
                    <table class="table table-striped table-bordered">
                        <thead>
                            <tr class="bg-gray">
                                <th>상품ID</th>
                                <th>상품명</th>
                                <th>옵션</th>
                                <th class="text-right">단가</th>
                                <th class="text-right">수량</th>
                                <th class="text-right">합계</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${resultMap.orderItems}">
                                <tr>
                                    <td>${item.productId}</td>
                                    <td>${item.productName}</td>
                                    <td>${item.optionName}</td>
                                    <td class="text-right">${StringUtil.setComma(item.unitPrice)}원</td>
                                    <td class="text-right">${item.quantity}</td>
                                    <td class="text-right">${StringUtil.setComma(item.itemTotalAmount)}원</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                        <tfoot>
                            <tr class="bg-gray">
                                <th colspan="5" class="text-right">총 결제금액</th>
                                <th class="text-right">${StringUtil.setComma(resultMap.AMOUNT_TOTAL)}원</th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="text-center" style="margin-top: 20px;">
        <button type="button" class="btn btn-default" onclick="window.close();">닫기</button>
    </div>
</div>
</body>
</html>
