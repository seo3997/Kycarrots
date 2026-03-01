<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>상품 상세 - ${branchInfo.BRANCH_NAME}</title>
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
            --shadow: 0 4px 6px -1px rgb(0 0 0 / 0.1);
        }

        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Outfit', sans-serif; background-color: var(--bg); color: var(--text); }

        .header { background: white; padding: 1rem 2rem; display: flex; justify-content: space-between; align-items: center; position: sticky; top: 0; z-index: 100; box-shadow: 0 1px 2px 0 rgb(0 0 0 / 0.05); }
        .logo { display: flex; align-items: center; gap: 0.75rem; text-decoration: none; color: var(--text); font-weight: 700; font-size: 1.25rem; }
        .logo img { height: 40px; border-radius: 8px; }

        .container { max-width: 1000px; margin: 2rem auto; padding: 2rem; }
        .detail-wrapper { display: grid; grid-template-columns: 1fr 1fr; gap: 3rem; background: white; padding: 2rem; border-radius: 20px; box-shadow: var(--shadow); }

        .product-img { width: 100%; border-radius: 12px; aspect-ratio: 1; background: #f1f5f9; background-size: cover; background-position: center; }
        
        .product-info h1 { font-size: 2rem; margin-bottom: 1rem; }
        .price-tag { font-size: 1.75rem; font-weight: 700; color: var(--primary); margin-bottom: 2rem; }
        
        .description { color: var(--text-muted); margin-bottom: 2rem; line-height: 1.8; }

        .order-box { border-top: 1px solid #e2e8f0; padding-top: 2rem; }
        .quantity-selector { display: flex; align-items: center; gap: 1rem; margin-bottom: 2rem; }
        .btn-qty { border: 1px solid #cbd5e1; background: white; width: 32px; height: 32px; border-radius: 50%; cursor: pointer; display: flex; align-items: center; justify-content: center; }
        
        .btn-order { width: 100%; padding: 1rem; background: var(--primary); color: white; border: none; border-radius: 12px; font-size: 1.125rem; font-weight: 600; cursor: pointer; transition: background 0.2s; }
        .btn-order:hover { background: #1d4ed8; }

        @media (max-width: 768px) {
            .detail-wrapper { grid-template-columns: 1fr; }
        }
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
                <div style="width: 40px; height: 40px; background: var(--primary); border-radius: 8px; display: flex; align-items: center; justify-content: center; color: white;">
                    <i class="fas fa-shopping-bag"></i>
                </div>
            </c:otherwise>
        </c:choose>
        <span>${branchInfo.BRANCH_NAME}</span>
    </a>
</header>

<main class="container">
    <div class="detail-wrapper">
        <div class="product-img" style="background-image: url('https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500&auto=format&fit=crop&q=60')"></div>
        <div class="product-info">
            <h1>프리미엄 무선 헤드폰</h1>
            <p class="price-tag">129,000원</p>
            <div class="description">
                최고의 음질과 노이즈 캔슬링 기능을 제공하는 프리미엄 무선 헤드폰입니다. 
                최대 40시간의 배터리 수명과 인체공학적 디자인으로 하루 종일 편안한 착용감을 선사합니다.
            </div>
            
            <div class="order-box">
                <div class="quantity-selector">
                    <span>수량</span>
                    <button class="btn-qty"><i class="fas fa-minus"></i></button>
                    <span style="font-weight: 600;">1</span>
                    <button class="btn-qty"><i class="fas fa-plus"></i></button>
                </div>
                <button class="btn-order" onclick="location.href='/shop/checkout.do'">구매하기</button>
            </div>
        </div>
    </div>
</main>

</body>
</html>
