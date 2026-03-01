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
                    <div class="order-header">
                        <span class="order-id">주문번호: ${item.ORDER_NO}</span>
                        <span class="order-date">${item.ORDERED_AT}</span>
                    </div>
                    <div class="order-item">
                        <div class="item-img" style="background-image: url('${item.IMAGE_URL}')"></div>
                        <div class="item-info">
                            <div class="item-name">${item.TITLE}</div>
                            <div class="item-meta">수량: ${item.QUANTITY}개 | 결제금액: <fmt:formatNumber value="${item.TOTAL_PAY_AMOUNT}" type="number" maxFractionDigits="0"/>원</div>
                        </div>
                        <div class="order-status">
                            <span class="badge badge-success">
                                <c:choose>
                                    <c:when test="${item.ORDER_STATUS == 'PAID'}">결제완료</c:when>
                                    <c:when test="${item.ORDER_STATUS == 'CANCEL'}">주문취소</c:when>
                                    <c:otherwise>${item.ORDER_STATUS}</c:otherwise>
                                </c:choose>
                            </span>
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

</body>
</html>
