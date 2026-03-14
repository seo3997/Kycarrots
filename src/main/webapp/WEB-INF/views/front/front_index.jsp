<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>asagong - 아는 사람끼리 공동구매</title>
    <style>
        :root {
            --primary: #ff6b35;
            --secondary: #2ec4b6;
            --dark: #011627;
            --light: #fdfffc;
            --gray: #e0e0e0;
        }
        
        body, html {
            margin: 0;
            padding: 0;
            font-family: 'Pretendard', -apple-system, BlinkMacSystemFont, system-ui, Roboto, sans-serif;
            background-color: var(--light);
            color: var(--dark);
            scroll-behavior: smooth;
        }

        /* Header */
        header {
            position: fixed;
            top: 0;
            width: 100%;
            background: rgba(253, 255, 252, 0.95);
            backdrop-filter: blur(10px);
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
            z-index: 1000;
        }

        .header-content {
            max-width: 1200px;
            margin: 0 auto;
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logo {
            font-size: 1.5rem;
            font-weight: 800;
            color: var(--primary);
            text-decoration: none;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .nav-links {
            display: flex;
            gap: 30px;
        }

        .nav-links a {
            text-decoration: none;
            color: var(--dark);
            font-weight: 600;
            transition: color 0.3s;
        }

        .nav-links a:hover {
            color: var(--primary);
        }

        /* Hero Section */
        .hero {
            height: 100vh;
            background: linear-gradient(135deg, #011627 0%, #023658 100%);
            color: white;
            display: flex;
            align-items: center;
            justify-content: center;
            text-align: center;
            position: relative;
            overflow: hidden;
            padding-top: 60px; /* Offset for header */
        }

        .hero::before {
            content: '';
            position: absolute;
            background: url('https://images.unsplash.com/photo-1595856417124-b1523f66f916?q=80&w=2670&auto=format&fit=crop') no-repeat center center/cover;
            opacity: 0.2;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            z-index: 0;
        }

        .hero-content {
            position: relative;
            z-index: 1;
            max-width: 800px;
            padding: 0 20px;
        }

        .hero h1 {
            font-size: 4rem;
            margin-bottom: 20px;
            background: linear-gradient(to right, #ff6b35, #feb47b);
            -webkit-background-clip: text;
            background-clip: text;
            -webkit-text-fill-color: transparent;
            font-weight: 800;
            line-height: 1.2;
        }

        .hero p {
            font-size: 1.2rem;
            line-height: 1.6;
            margin-bottom: 40px;
            color: #e0e0e0;
        }

        .btn {
            display: inline-block;
            padding: 15px 40px;
            border-radius: 50px;
            text-decoration: none;
            font-weight: 700;
            font-size: 1.1rem;
            transition: all 0.3s ease;
            cursor: pointer;
        }

        .btn-primary {
            background-color: var(--primary);
            color: white;
            box-shadow: 0 10px 20px rgba(255, 107, 53, 0.3);
        }

        .btn-primary:hover {
            transform: translateY(-3px);
            box-shadow: 0 15px 30px rgba(255, 107, 53, 0.5);
        }

        .btn-outline {
            border: 2px solid white;
            color: white;
            margin-left: 15px;
        }

        .btn-outline:hover {
            background: white;
            color: var(--dark);
        }

        /* Features Section */
        .features {
            padding: 100px 20px;
            background-color: var(--light);
            max-width: 1200px;
            margin: 0 auto;
        }

        .section-title {
            text-align: center;
            font-size: 2.5rem;
            margin-bottom: 60px;
            color: var(--dark);
        }

        .feature-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 40px;
        }

        .feature-card {
            background: white;
            padding: 40px;
            border-radius: 20px;
            text-align: center;
            box-shadow: 0 10px 30px rgba(0,0,0,0.05);
            transition: transform 0.3s;
        }

        .feature-card:hover {
            transform: translateY(-10px);
        }

        .feature-icon {
            font-size: 3rem;
            color: var(--primary);
            margin-bottom: 20px;
        }

        .feature-card h3 {
            font-size: 1.5rem;
            margin-bottom: 15px;
        }

        .feature-card p {
            color: #666;
            line-height: 1.6;
        }

        /* About Section */
        .about {
            background-color: #f8f9fa;
            padding: 100px 20px;
        }

        .about-content {
            max-width: 1200px;
            margin: 0 auto;
            display: flex;
            align-items: center;
            gap: 60px;
        }

        .about-text {
            flex: 1;
        }

        .about-text h2 {
            font-size: 2.5rem;
            margin-bottom: 20px;
            color: var(--dark);
        }

        .about-text p {
            font-size: 1.1rem;
            line-height: 1.8;
            color: #555;
            margin-bottom: 20px;
        }

        .about-image {
            flex: 1;
            border-radius: 20px;
            overflow: hidden;
            box-shadow: 0 20px 40px rgba(0,0,0,0.1);
        }

        .about-image img {
            width: 100%;
            height: auto;
            display: block;
        }

        /* Call to Action */
        .cta {
            padding: 100px 20px;
            text-align: center;
            background: linear-gradient(135deg, var(--primary) 0%, #feb47b 100%);
            color: white;
        }

        .cta h2 {
            font-size: 2.5rem;
            margin-bottom: 20px;
        }

        .cta p {
            font-size: 1.2rem;
            margin-bottom: 40px;
            max-width: 600px;
            margin-left: auto;
            margin-right: auto;
        }

        .cta .btn-outline {
            border-color: white;
            color: var(--primary);
            background: white;
        }

        .cta .btn-outline:hover {
            background: transparent;
            color: white;
        }

        /* Footer */
        footer {
            background-color: var(--dark);
            color: white;
            padding: 60px 20px 20px;
        }

        .footer-content {
            max-width: 1200px;
            margin: 0 auto;
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 40px;
            border-bottom: 1px solid rgba(255,255,255,0.1);
            padding-bottom: 40px;
            margin-bottom: 20px;
        }

        .footer-logo {
            font-size: 1.5rem;
            font-weight: bold;
            color: var(--primary);
            margin-bottom: 20px;
            display: block;
        }

        .footer-col h4 {
            font-size: 1.2rem;
            margin-bottom: 20px;
            color: white;
        }

        .footer-col p, .footer-col a {
            color: #aaa;
            line-height: 1.8;
            text-decoration: none;
            display: block;
            margin-bottom: 10px;
        }

        .footer-col a:hover {
            color: var(--primary);
        }

        .copyright {
            text-align: center;
            color: #888;
            font-size: 0.9rem;
        }

        @media (max-width: 768px) {
            .hero h1 { font-size: 2.5rem; }
            .about-content { flex-direction: column; }
            .nav-links { display: none; } /* Could add a hamburger menu here */
            .btn-outline { margin-left: 0; margin-top: 15px; }
        }
    </style>
</head>
<body>

    <!-- Header -->
    <header>
        <div class="header-content">
            <a href="/" class="logo">🥕 asagong</a>
            <div class="nav-links">
                <a href="#about">브랜드 소개</a>
                <a href="#features">핵심 가치</a>
                <a href="/admin/login.do">지점 로그인</a>
            </div>
        </div>
    </header>

    <!-- Hero Section -->
    <section class="hero" id="home">
        <div class="hero-content">
            <h1>자연을 담은 프리미엄 농산물,<br>asagong</h1>
            <p>산지의 신선함을 그대로 식탁까지. 까다롭게 엄선한 최고 품질의 농산물만을 취급하는 전국 최대 규모의 아는 사람끼리 공동구매 유통 플랫폼입니다.</p>
            <div>
                <!-- /shop/list.do 로 가면 지점 권한 등 분기 처리에 걸릴 수 있으므로, 지점 가맹 문의나 서비스 소개 페이지로 유도 등을 할 수 있습니다. 
                     현재는 기획상 로그인이나 샵으로 유도 -->
                <a href="/front/login.do" class="btn btn-primary">서비스 체험하기</a>
                <a href="#about" class="btn btn-outline">자세히 알아보기</a>
            </div>
        </div>
    </section>

    <!-- Features Section -->
    <section class="features" id="features">
        <h2 class="section-title">우리의 핵심 가치</h2>
        <div class="feature-grid">
            <div class="feature-card">
                <div class="feature-icon">🌱</div>
                <h3>극상의 신선도</h3>
                <p>당일 수확, 당일 배송을 원칙으로 산지의 숨결이 살아있는 가장 신선한 농산물을 제공합니다.</p>
            </div>
            <div class="feature-card">
                <div class="feature-icon">🤝</div>
                <h3>투명한 직거래</h3>
                <p>복잡한 유통 구조를 혁신하여 생산자와 소비자 모두가 웃을 수 있는 합리적인 가격을 실현합니다.</p>
            </div>
            <div class="feature-card">
                <div class="feature-icon">✨</div>
                <h3>깐깐한 품질 검증</h3>
                <p>수십 년 경력의 농산물 전문가들이 맛과 크기, 당도를 직접 확인하여 상위 1%의 프리미엄 상품만 선별합니다.</p>
            </div>
        </div>
    </section>

    <!-- About Section -->
    <section class="about" id="about">
        <div class="about-content">
            <div class="about-image">
                <img src="https://images.unsplash.com/photo-1464226184884-fa280b87c399?q=80&w=2070&auto=format&fit=crop" alt="Fresh Vegetables">
            </div>
            <div class="about-text">
                <h2>전국 50여 개 지점과 함께하는 놀라운 성장</h2>
                <p>asagong은 본사를 중심으로 전국 주요 거점에 지점망을 구축하여 가장 빠르고 안전하게 농산물을 유통하고 있습니다.</p>
                <p>데이터 기반의 수요 예측 시스템과 콜드체인 물류망을 결합하여, 버려지는 농산물을 최소화하고 지속 가능한 농업 생태계 조성에 앞장서고 있습니다.</p>
                <ul style="color: #555; line-height: 1.8; margin-top: 20px;">
                    <li>✓ 생산 농가 1,000곳 이상 제휴</li>
                    <li>✓ 월간 유통량 500톤 이상 돌파</li>
                    <li>✓ B2B 신선 식자재 공급 만족도 1위</li>
                </ul>
            </div>
        </div>
    </section>

    <!-- Call to Action -->
    <section class="cta">
        <h2>지금 바로 asagong의 파트너가 되어보세요</h2>
        <p>신선한 식자재가 필요한 요식업 대표님, 혹은 지역 거점 지점 사업에 관심 있으신 분들의 연락을 기다립니다.</p>
        <a href="mailto:contact@asagong.com" class="btn btn-outline" style="color:#ff6b35 !important; background:white !important;">가맹 및 제휴 문의</a>
    </section>

    <!-- Footer -->
    <footer>
        <div class="footer-content">
            <div class="footer-col">
                <span class="footer-logo">🥕 asagong</span>
                <p>농업의 미래를 바꾸는 혁신 유통 플랫폼</p>
            </div>
            <div class="footer-col">
                <h4>고객 서비스</h4>
                <a href="#">자주 묻는 질문</a>
                <a href="#">배송 안내</a>
                <a href="#">반품/교환 규정</a>
            </div>
            <div class="footer-col">
                <h4>회사소개</h4>
                <a href="#">브랜드 스토리</a>
                <a href="#">인재 채용</a>
                <a href="#">오시는 길</a>
            </div>
            <div class="footer-col">
                <h4>연락처</h4>
                <p>서울특별시 강남구 테헤란로 123</p>
                <p>대표전화: 1588-0000</p>
                <p>이메일: hello@asagong.com</p>
            </div>
        </div>
        <div class="copyright">
            &copy; 2026 asagong Co., Ltd. All rights reserved.
        </div>
    </footer>

</body>
</html>
