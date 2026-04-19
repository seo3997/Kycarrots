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
    <title>${branchInfo.BRANCH_NAME} - 전용 쇼핑몰</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/common/front/css/front_common.css?v=20260419">
</head>
<body>
    <div id="loadingOverlay" class="loading-overlay" style="display: flex;">
        <div class="spinner"></div>
    </div>

<%@ include file="/common/frontinc/shop_header.jspf" %>

<section class="hero">
    <div style="max-width: 800px; margin: 0 auto;">
        <h1>${branchInfo.BRANCH_NAME}</h1>
        <p>${branchInfo.COMPANY_NAME} 회원 전용 쇼핑몰입니다.</p>
    </div>
</section>


<main class="container">
    <div class="filter-section">
        <a href="/shop/list.do?sch_sale_status_code=ALL" class="filter-btn ${searchParam.sch_sale_status_code eq 'ALL' or empty searchParam.sch_sale_status_code ? 'active' : ''}">전체</a>
        <a href="/shop/list.do?sch_sale_status_code=1" class="filter-btn ${searchParam.sch_sale_status_code eq '1' ? 'active' : ''}">판매중</a>
        <a href="/shop/list.do?sch_sale_status_code=20" class="filter-btn ${searchParam.sch_sale_status_code eq '20' ? 'active' : ''}">품절</a>
        <a href="/shop/list.do?sch_sale_status_code=30" class="filter-btn ${searchParam.sch_sale_status_code eq '30' ? 'active' : ''}">판매중지</a>
        <a href="/shop/list.do?sch_sale_status_code=99" class="filter-btn ${searchParam.sch_sale_status_code eq '99' ? 'active' : ''}">판매완료</a>
    </div>

    <h2 class="section-title"><i class="fas fa-th-large text-primary"></i> 오늘의 추천 상품</h2>
    <div class="product-grid">
        <c:choose>
            <c:when test="${not empty resultList}">
                <c:forEach var="item" items="${resultList}">
                    <a href="/shop/detail.do?productId=${item.PRODUCT_ID}" class="product-card ${item.SALE_STATUS ne '1' ? 'not-for-sale' : ''}">
                        <div class="product-img" style="background-image: url('${item.IMAGE_URL}')">
                            <c:choose>
                                <c:when test="${item.SALE_STATUS eq '0'}"><div class="status-overlay">승인요청</div></c:when>
                                <c:when test="${item.SALE_STATUS eq '10'}"><div class="status-overlay">예약중</div></c:when>
                                <c:when test="${item.SALE_STATUS eq '20'}"><div class="status-overlay">품절</div></c:when>
                                <c:when test="${item.SALE_STATUS eq '30'}"><div class="status-overlay">판매중지</div></c:when>
                                <c:when test="${item.SALE_STATUS eq '98'}"><div class="status-overlay">반려</div></c:when>
                                <c:when test="${item.SALE_STATUS eq '99'}"><div class="status-overlay">판매완료</div></c:when>
                                <c:when test="${item.SALE_STATUS ne '1'}"><div class="status-overlay">준비중</div></c:when>
                            </c:choose>
                        </div>
                        <div class="product-info">
                            <div class="product-name">${item.TITLE}</div>
                            <c:choose>
                                <c:when test="${item.SALE_STATUS eq '1'}">
                                    <div class="product-price"><fmt:formatNumber value="${item.PRICE}" type="number" maxFractionDigits="0"/>원</div>
                                </c:when>
                                <c:otherwise>
                                    <div class="product-price" style="color: #94a3b8; font-size: 0.9rem;">구매불가</div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </a>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div style="grid-column: 1/-1; text-align: center; padding: 4rem; color: var(--text-muted);">
                    <i class="fas fa-box-open" style="font-size: 3rem; margin-bottom: 1rem; display: block;"></i>
                    등록된 상품이 없습니다.
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</main>

<%@ include file="/common/frontinc/shop_footer.jspf" %>


<script src="/common/front/lib/jquery-3.6.0.min.js"></script>
<script src="/common/front/js/front_common.js?v=20240316"></script>
<%@ include file="/common/frontinc/msg.jspf" %>
</body>
</html>
