<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>주문 내역 - ${branchInfo.BRANCH_NAME}</title>
    <link rel="stylesheet" href="/common/front/lib/font-awesome/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/common/front/css/front_common.css?v=20260419">
    <style>
        .btn-cancel {
            margin-top: 0.5rem;
            padding: 0.4rem 0.8rem;
            background: white;
            color: #ef4444;
            border: 1px solid #ef4444;
            border-radius: 6px;
            font-size: 0.85rem;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.2s;
        }
        /* Pagination Styles */
        .pagination {
            display: flex;
            justify-content: center;
            list-style: none;
            padding: 0;
            gap: 0.5rem;
        }
        .page-item .page-link {
            display: flex;
            align-items: center;
            justify-content: center;
            width: 40px;
            height: 40px;
            border-radius: 8px;
            background: white;
            color: var(--text);
            text-decoration: none;
            font-weight: 500;
            border: 1px solid #e2e8f0;
            transition: all 0.2s;
        }
        .page-item.active .page-link {
            background: var(--primary);
            color: white;
            border-color: var(--primary);
        }
        .sr-only { display: none; }
    </style>
</head>
<body>
    <div id="loadingOverlay" class="loading-overlay" style="display: flex;">
        <div class="spinner"></div>
    </div>

<%@ include file="/common/frontinc/shop_header.jspf" %>

<main class="container" style="padding-top: 1.5rem;">
    <h1 style="font-size: 1.5rem; font-weight: 700; margin-bottom: 2rem; color: var(--text);">나의 주문 내역</h1>
    <jsp:useBean id="now" class="java.util.Date" />

    <c:choose>
        <c:when test="${not empty resultList}">
            <c:forEach var="item" items="${resultList}">
                <div class="order-card">
                    <div class="order-header" style="cursor: pointer;" onclick="location.href='/shop/orderDetail.do?orderId=${item.ORDER_ID}'">
                        <span class="order-id" style="color: var(--primary); text-decoration: underline;">주문번호: ${item.ORDER_NO}</span>
                        <span class="order-date">${item.ORDERED_AT}</span>
                    </div>
                    <div class="order-item">
                        <a href="/shop/detail.do?productId=${item.PRODUCT_ID}" class="order-item-link">
                            <div class="item-img" style="background-image: url('${item.IMAGE_URL}')"></div>
                            <div class="item-info">
                                <div class="item-name">${item.TITLE}</div>
                                <div class="item-meta">수량: ${item.QUANTITY}개 | 결제금액: <fmt:formatNumber value="${item.TOTAL_PAY_AMOUNT}" type="number" maxFractionDigits="0"/>원</div>
                            </div>
                        </a>
                        <div class="order-status">
                            <span class="badge ${item.ORDER_STATUS == '40' ? 'badge-cancel' : 'badge-success'}">
                                ${not empty item.ORDER_STATUS_NM ? item.ORDER_STATUS_NM : item.ORDER_STATUS}
                            </span>
                            <c:if test="${item.ORDER_STATUS == '60'}">
                                <div class="tracking-info">
                                    택배사: ${item.DELIVERY_COMPANY_NM} | 송장번호: ${item.TRACKING_NO}
                                </div>
                            </c:if>
                            <c:if test="${item.ORDER_STATUS == '50'}">
                                <button type="button" class="btn-cancel" id="btn-cancel-${item.ORDER_ID}"
                                        onclick="handleCancel('${item.ORDER_ID}', '${item.ORDERED_AT}')">주문취소</button>
                            </c:if>
                            <c:if test="${item.ORDER_STATUS == '70' and not empty item.DELIVERED_AT}">
                                <%-- 배송완료(70)인 경우 7일 이내만 표시 --%>
                                <fmt:parseDate value="${fn:replace(item.DELIVERED_AT, 'T', ' ')}" var="deliveredAtDate" pattern="yyyy-MM-dd HH:mm:ss" />
                                <c:if test="${not empty deliveredAtDate}">
                                    <fmt:parseNumber value="${(now.time - deliveredAtDate.time) / (1000 * 60 * 60 * 24)}" var="diffDays" integerOnly="true" />
                                    <c:if test="${diffDays <= 7}">
                                        <button type="button" class="btn-cancel" id="btn-return-${item.ORDER_ID}"
                                                onclick="handleReturn('${item.ORDER_ID}', '${item.DELIVERED_AT}', '${item.ORDER_STATUS}')">반품요청</button>
                                    </c:if>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="empty-state">
                <i class="fas fa-receipt"></i>
                <p>주문 내역이 없습니다.</p>
                <a href="/shop/list.do" class="btn-home">쇼핑하러 가기</a>
            </div>
        </c:otherwise>
    </c:choose>

    <div style="margin-top: 3rem; margin-bottom: 2rem;">
        ${navigationBar}
    </div>

    <form name="aform" id="aform" method="get" action="/shop/orderList.do">
        <input type="hidden" name="curPage" id="curPage" value="${param.curPage}">
    </form>
</main>

<script>
    function fnGoPage(page) {
        document.getElementById('curPage').value = page;
        document.aform.submit();
    }

    async function handleCancel(orderId, orderedAt) {
        // Date check: orderedAt is "YYYY.MM.DD HH:mm"
        const orderDate = new Date(orderedAt.replace(/\./g, '/'));
        const now = new Date();
        const diffDays = (now - orderDate) / (1000 * 60 * 60 * 24);

        if (diffDays > 7) {
            alert("결제 후 7일이 경과하여 직접 취소가 불가능합니다. 고객센터로 문의해주세요.");
            return;
        }

        if (!confirm("정말로 주문을 취소하시겠습니까?")) {
            return;
        }

        const cancelReason = prompt("취소 사유를 입력해주세요.", "고객 변심");
        if (cancelReason === null) return; // User cancelled prompt

        const btn = document.getElementById('btn-cancel-' + orderId);
        btn.disabled = true;
        btn.innerText = "취소 중...";

        try {
            const response = await fetch("/api/payment/cancel", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    orderId: orderId,
                    cancelReason: cancelReason,
                    userNo: "${userInfoVo.userNo}"
                })
            });

            const result = await response.json();
            if (result.success) {
                alert("주문이 정상적으로 취소되었습니다.");
                location.reload();
            } else {
                alert("취소 실패: " + result.message);
                btn.disabled = false;
                btn.innerText = "주문취소";
            }
        } catch (error) {
            console.error(error);
            alert("처리 중 오류가 발생했습니다.");
            btn.disabled = false;
            btn.innerText = "주문취소";
        }
    }
    async function handleReturn(orderId, baseDate, status) {
        // baseDate is "YYYY-MM-DD HH:mm:ss" or similar from DB
        if (status === '70') {
            const deliveredDate = new Date(baseDate.replace(/-/g, '/'));
            const now = new Date();
            const diffDays = (now - deliveredDate) / (1000 * 60 * 60 * 24);

            if (diffDays > 7) {
                alert("배송 완료 후 7일이 경과하여 반품 요청이 불가능합니다. 고객센터로 문의해주세요.");
                return;
            }
        }

        if (!confirm("반품을 요청하시겠습니까? (배송비가 발생할 수 있습니다.)")) {
            return;
        }

        const returnReason = prompt("반품 사유를 입력해주세요.", "단순 변심");
        if (returnReason === null) return;

        const btn = document.getElementById('btn-return-' + orderId);
        btn.disabled = true;
        btn.innerText = "처리 중...";

        try {
            const response = await fetch("/api/payment/return", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    orderId: orderId,
                    returnReason: returnReason,
                    userNo: "${userInfoVo.userNo}"
                })
            });

            const result = await response.json();
            if (result.success) {
                alert("반품 요청이 정상적으로 접수되었습니다.");
                location.reload();
            } else {
                alert("반품 요청 실패: " + result.message);
                btn.disabled = false;
                btn.innerText = "반품요청";
            }
        } catch (error) {
            console.error(error);
            alert("처리 중 오류가 발생했습니다.");
            btn.disabled = false;
            btn.innerText = "반품요청";
        }
    }
</script>


<%@ include file="/common/frontinc/shop_footer.jspf" %>

<script src="/common/front/lib/jquery-3.6.0.min.js"></script>
<script src="/common/front/js/front_common.js?v=20240316"></script>
<%@ include file="/common/frontinc/msg.jspf" %>
</body>
</html>
