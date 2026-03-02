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
    <link rel="stylesheet" href="/common/front/css/front_common.css">
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
        .btn-cancel:hover {
            background: #fef2f2;
        }
        .order-status {
            display: flex;
            flex-direction: column;
            align-items: flex-end;
            justify-content: center;
        }
        .badge-cancel {
            background: #f1f5f9;
            color: #64748b;
        }
    </style>
</head>
<body>

<header class="header">
    <a href="/shop/list.do" class="logo">
        <c:choose>
            <c:when test="${not empty branchInfo.LOGO_IMAGE_URL}">
                <img src="${branchInfo.LOGO_IMAGE_URL}" alt="Logo" style="height: 40px; border-radius: 8px;">
            </c:when>
            <c:otherwise>
                <div style="width: 40px; height: 40px; background: var(--primary); border-radius: 8px; display: flex; align-items: center; justify-content: center; color: white;">
                    <i class="fas fa-shopping-bag"></i>
                </div>
            </c:otherwise>
        </c:choose>
        <span>${branchInfo.BRANCH_NAME}</span>
    </a>
    <nav class="nav-links" style="display: flex; gap: 1.5rem; align-items: center;">
        <a href="/shop/list.do" style="text-decoration: none; color: var(--text); font-weight: 500;">상품목록</a>
        <a href="/shop/orderList.do" style="text-decoration: none; color: var(--text); font-weight: 500;">주문현황</a>
        <c:choose>
            <c:when test="${empty userInfoVo}">
                <a href="/front/registForm.do" style="text-decoration: none; color: var(--text); font-weight: 500;">회원가입</a>
                <a href="/front/login.do" style="text-decoration: none; color: white; background: var(--primary); padding: 0.5rem 1.25rem; border-radius: var(--radius); font-weight: 500;">로그인</a>
            </c:when>
            <c:otherwise>
                <span style="font-weight: 500;">${userInfoVo.userNm}님</span>
                <a href="/front/logout.do" style="text-decoration: none; color: var(--text); font-weight: 500;">로그아웃</a>
            </c:otherwise>
        </c:choose>
    </nav>
</header>

<main class="container">
    <h1>주문 내역</h1>

    <c:choose>
        <c:when test="${not empty resultList}">
            <c:forEach var="item" items="${resultList}">
                <div class="order-card">
                    <div class="order-header" style="cursor: pointer;" onclick="location.href='/shop/orderDetail.do?orderNo=${item.ORDER_NO}'">
                        <span class="order-id" style="color: var(--primary); text-decoration: underline;">주문번호: ${item.ORDER_NO}</span>
                        <span class="order-date">${item.ORDERED_AT}</span>
                    </div>
                    <div class="order-item">
                        <a href="/shop/detail.do?productId=${item.PRODUCT_ID}" style="text-decoration: none; display: flex; align-items: center; gap: 1rem; flex: 1;">
                            <div class="item-img" style="background-image: url('${item.IMAGE_URL}')"></div>
                            <div class="item-info">
                                <div class="item-name" style="color: var(--text);">${item.TITLE}</div>
                                <div class="item-meta">수량: ${item.QUANTITY}개 | 결제금액: <fmt:formatNumber value="${item.TOTAL_PAY_AMOUNT}" type="number" maxFractionDigits="0"/>원</div>
                            </div>
                        </a>
                        <div class="order-status">
                            <span class="badge ${item.ORDER_STATUS == '40' ? 'badge-cancel' : 'badge-success'}">
                                <c:choose>
                                    <c:when test="${item.ORDER_STATUS == '30'}">결제완료</c:when>
                                    <c:when test="${item.ORDER_STATUS == '40'}">주문취소</c:when>
                                    <c:when test="${item.ORDER_STATUS == '50'}">배송준비중</c:when>
                                    <c:when test="${item.ORDER_STATUS == '60'}">배송중</c:when>
                                    <c:when test="${item.ORDER_STATUS == '70'}">배송완료</c:when>
                                    <c:when test="${item.ORDER_STATUS == '80'}">반품요청</c:when>
                                    <c:when test="${item.ORDER_STATUS == '89'}">반품완료</c:when>
                                    <c:when test="${item.ORDER_STATUS == '99'}">주문확정</c:when>
                                    <c:otherwise>${item.ORDER_STATUS}</c:otherwise>
                                </c:choose>
                            </span>
                            <c:if test="${item.ORDER_STATUS == '60'}">
                                <div style="margin-top: 0.5rem; font-size: 0.85rem; text-align: right; color: var(--text-muted);">
                                    택배사: ${item.DELIVERY_COMPANY_NM} | 송장번호: ${item.TRACKING_NO}
                                </div>
                            </c:if>
                            <c:if test="${item.ORDER_STATUS == '30'}">
                                <button type="button" class="btn-cancel" id="btn-cancel-${item.ORDER_NO}"
                                        onclick="handleCancel('${item.ORDER_NO}', '${item.ORDERED_AT}')">주문취소</button>
                            </c:if>
                            <c:if test="${item.ORDER_STATUS == '60' || item.ORDER_STATUS == '70'}">
                                <%-- 배송완료(70)인 경우 7일 이내만 표시 --%>
                                <c:set var="showReturn" value="true" />
                                <c:if test="${item.ORDER_STATUS == '70' && not empty item.DELIVERED_AT}">
                                    <%-- Date check in JSP/Taglib is complex, better do a basic check or just show it and check in JS --%>
                                </c:if>
                                <c:if test="${showReturn}">
                                    <button type="button" class="btn-cancel" id="btn-return-${item.ORDER_NO}"
                                            onclick="handleReturn('${item.ORDER_NO}', '${item.ORDER_STATUS == '70' ? item.DELIVERED_AT : item.ORDERED_AT}', '${item.ORDER_STATUS}')">반품요청</button>
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
</main>

<script>
    async function handleCancel(orderNo, orderedAt) {
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

        const btn = document.getElementById('btn-cancel-' + orderNo);
        btn.disabled = true;
        btn.innerText = "취소 중...";

        try {
            const response = await fetch("/api/payment/cancel", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    orderNo: orderNo,
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
    async function handleReturn(orderNo, baseDate, status) {
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

        const btn = document.getElementById('btn-return-' + orderNo);
        btn.disabled = true;
        btn.innerText = "처리 중...";

        try {
            const response = await fetch("/api/payment/return", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    orderNo: orderNo,
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

</body>
</html>
