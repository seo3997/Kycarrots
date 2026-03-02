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
    <style>
        :root {
            --primary: #2563eb;
            --accent: #f59e0b;
            --bg: #f8fafc;
            --card-bg: #ffffff;
            --text: #1e293b;
            --text-muted: #64748b;
            --radius: 12px;
            --shadow: 0 4px 6px -1px rgb(0 0 0 / 0.1), 0 2px 4px -2px rgb(0 0 0 / 0.1);
        }

        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { 
            font-family: 'Outfit', sans-serif; 
            background-color: var(--bg); 
            color: var(--text);
            line-height: 1.6;
        }

        .header {
            background: rgba(255, 255, 255, 0.8);
            backdrop-filter: blur(12px);
            padding: 1rem 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            position: sticky;
            top: 0;
            z-index: 100;
            box-shadow: 0 1px 2px 0 rgb(0 0 0 / 0.05);
        }

        .logo {
            display: flex;
            align-items: center;
            gap: 0.75rem;
            text-decoration: none;
            color: var(--text);
            font-weight: 700;
            font-size: 1.25rem;
        }

        .logo img {
            height: 40px;
            border-radius: 8px;
        }

        .nav-links {
            display: flex;
            gap: 1.5rem;
            align-items: center;
        }

        .nav-links a {
            text-decoration: none;
            color: var(--text);
            font-weight: 500;
            transition: color 0.2s;
        }

        .nav-links a:hover { color: var(--primary); }

        .btn-login {
            background: var(--primary);
            color: white !important;
            padding: 0.5rem 1.25rem;
            border-radius: var(--radius);
        }

        .hero {
            padding: 4rem 2rem;
            text-align: center;
            background: linear-gradient(135deg, #eff6ff 0%, #ffffff 100%);
        }

        .hero h1 {
            font-size: 2.5rem;
            font-weight: 700;
            margin-bottom: 1rem;
            color: #1e3a8a;
        }

        .hero p {
            color: var(--text-muted);
            font-size: 1.125rem;
            max-width: 600px;
            margin: 0 auto;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 2rem;
        }

        .section-title {
            font-size: 1.5rem;
            font-weight: 600;
            margin-bottom: 2rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .product-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 2rem;
        }

        .product-card {
            background: var(--card-bg);
            border-radius: var(--radius);
            overflow: hidden;
            box-shadow: var(--shadow);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            text-decoration: none;
            color: inherit;
        }

        .product-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 15px -3px rgb(0 0 0 / 0.1);
        }

        .product-img {
            height: 200px;
            background: #e2e8f0;
            background-size: cover;
            background-position: center;
        }

        .product-info { padding: 1.5rem; }

        .product-name {
            font-weight: 600;
            font-size: 1.125rem;
            margin-bottom: 0.5rem;
        }

        .product-price {
            color: var(--primary);
            font-weight: 700;
            font-size: 1.25rem;
        }

        .footer {
            margin-top: 4rem;
            background: #1e293b;
            color: #cbd5e1;
            padding: 4rem 2rem;
            text-align: center;
        }

        .footer-info {
            max-width: 800px;
            margin: 0 auto;
            font-size: 0.875rem;
        }

        .footer-info p { margin-bottom: 0.5rem; }
    </style>
</head>
<body>

<header class="header">
    <a href="/shop/list.do" class="logo">
        <c:choose>
            <c:when test="${not empty branchInfo.LOGO_IMAGE_URL}">
                <img src="${branchInfo.LOGO_IMAGE_URL}" alt="Logo">
            </c:when>
            <c:otherwise>
                <div style="width: 36px; height: 36px; background: var(--primary); border-radius: 8px; display: flex; align-items: center; justify-content: center; color: white;">
                    <i class="fas fa-shopping-bag" style="font-size: 0.9rem;"></i>
                </div>
            </c:otherwise>
        </c:choose>
        <span>${branchInfo.BRANCH_NAME}</span>
    </a>
    <nav class="nav-links">
        <a href="/shop/list.do">상품목록</a>
        <a href="/shop/orderList.do">주문현황</a>
        <c:choose>
            <c:when test="${empty userInfoVo}">
                <a href="/front/login.do" class="btn-login">로그인</a>
            </c:when>
            <c:otherwise>
                <a href="/front/logout.do">로그아웃</a>
            </c:otherwise>
        </c:choose>
    </nav>
</header>

<section class="hero" style="padding: 3rem 1.5rem; background: linear-gradient(135deg, #f0f9ff 0%, #ffffff 100%);">
    <div style="max-width: 800px; margin: 0 auto;">
        <h1 style="font-size: 2rem; color: #0c4a6e; line-height: 1.2;">${branchInfo.BRANCH_NAME}</h1>
        <p class="text-muted mt-3" style="font-size: 1rem;">${branchInfo.COMPANY_NAME} 회원 전용 쇼핑몰입니다.</p>
    </div>
</section>

<style>
    /* Grid optimization: 2 columns on small screens, more on large */
    .product-grid {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(160px, 1fr));
        gap: 1rem;
    }
    
    @media (min-width: 768px) {
        .product-grid {
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 2rem;
        }
    }

    .product-img {
        aspect-ratio: 1; /* Force square images for consistency */
        height: auto;
        position: relative;
        overflow: hidden;
    }

    .not-for-sale {
        opacity: 0.8;
    }

    .status-overlay {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(0, 0, 0, 0.4);
        color: white;
        display: flex;
        align-items: center;
        justify-content: center;
        font-weight: 700;
        font-size: 1.25rem;
        backdrop-filter: blur(2px);
    }
</style>

<style>
    .filter-section {
        margin-bottom: 2rem;
        display: flex;
        gap: 0.5rem;
        flex-wrap: wrap;
    }
    .filter-btn {
        padding: 0.5rem 1.25rem;
        border-radius: 20px;
        background: #f1f5f9;
        color: #64748b;
        font-size: 0.9rem;
        font-weight: 600;
        cursor: pointer;
        border: 2px solid transparent;
        transition: all 0.2s;
        text-decoration: none;
    }
    .filter-btn:hover {
        background: #e2e8f0;
    }
    .filter-btn.active {
        background: white;
        color: var(--primary);
        border-color: var(--primary);
        box-shadow: 0 4px 6px -1px rgba(37, 99, 235, 0.1);
    }
</style>

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

<footer class="footer">
    <div class="footer-info">
        <p><strong>(주)${branchInfo.COMPANY_NAME} | 대표자: ${branchInfo.REPRESENTATIVE_NAME}</strong></p>
        <p>사업자등록번호: ${branchInfo.BUSINESS_NUMBER} | 통신판매업신고: ${branchInfo.TONGSIN_NUMBER}</p>
        <p>주소: ${branchInfo.ADDRESS}</p>
        <p>고객센터: ${branchInfo.CS_PHONE} | 도메인: ${branchInfo.DOMAIN_URL}</p>
        <p style="margin-top: 2rem; opacity: 0.6;">&copy; 2024 Kycarrots. All rights reserved.</p>
    </div>
</footer>

</body>
</html>
