<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
    <a href="#" class="logo">
        <c:choose>
            <c:when test="${not empty branchInfo.LOGO_IMAGE_URL}">
                <img src="${branchInfo.LOGO_IMAGE_URL}" alt="Logo">
            </c:when>
            <c:otherwise>
                <div style="width: 40px; height: 40px; background: var(--primary); border-radius: 8px; display: flex; align-items: center; justify-content: center; color: white;">
                    <i class="fas fa-shopping-bag"></i>
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
                <a href="/member/registForm.do">회원가입</a>
                <a href="/login/loginForm.do" class="btn-login">로그인</a>
            </c:when>
            <c:otherwise>
                <span>${userInfoVo.userNm}님</span>
                <a href="/login/logout.do">로그아웃</a>
            </c:otherwise>
        </c:choose>
    </nav>
</header>

<section class="hero">
    <h1>${branchInfo.BRANCH_NAME}에 오신 것을 환영합니다</h1>
    <p>${branchInfo.COMPANY_NAME}이 운영하는 ${branchInfo.BRANCH_NAME} 회원분들만을 위한 특별한 혜택을 만나보세요.</p>
</section>

<main class="container">
    <h2 class="section-title"><i class="fas fa-th-large text-primary"></i> 오늘의 추천 상품</h2>
    <div class="product-grid">
        <!-- Temporary Product Display -->
        <a href="/shop/detail.do" class="product-card">
            <div class="product-img" style="background-image: url('https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500&auto=format&fit=crop&q=60')"></div>
            <div class="product-info">
                <div class="product-name">프리미엄 무선 헤드폰</div>
                <div class="product-price">129,000원</div>
            </div>
        </a>
        <a href="/shop/detail.do" class="product-card">
            <div class="product-img" style="background-image: url('https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500&auto=format&fit=crop&q=60')"></div>
            <div class="product-info">
                <div class="product-name">스마트 터치 워치</div>
                <div class="product-price">89,000원</div>
            </div>
        </a>
        <a href="/shop/detail.do" class="product-card">
            <div class="product-img" style="background-image: url('https://images.unsplash.com/photo-1526170315873-3a98658c7a6b?w=500&auto=format&fit=crop&q=60')"></div>
            <div class="product-info">
                <div class="product-name">빈티지 필름 카메라</div>
                <div class="product-price">155,000원</div>
            </div>
        </a>
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
