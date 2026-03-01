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
    <div class="detail-wrapper">
        <div class="product-img" style="background-image: url('${productInfo.IMAGE_URL}')"></div>
        <div class="product-info">
            <h1>${productInfo.TITLE}</h1>
            <p class="price-tag"><fmt:formatNumber value="${productInfo.PRICE}" type="number" maxFractionDigits="0"/>원</p>
            <div class="description">
                ${productInfo.DESCRIPTION}
            </div>
            
            <div class="order-box">
                <div class="quantity-info" style="margin-bottom: 1rem; font-size: 0.9rem; color: var(--text-muted);">
                    구매 가능 수량: <span id="max-quantity" style="color: var(--primary); font-weight: 600;">${productInfo.AVAILABLE_QUANTITY}</span>개
                </div>
                <div class="quantity-selector">
                    <span>수량</span>
                    <button class="btn-qty" id="btn-minus"><i class="fas fa-minus"></i></button>
                    <span id="quantity-val" style="font-weight: 600;">1</span>
                    <button class="btn-qty" id="btn-plus"><i class="fas fa-plus"></i></button>
                </div>
                <c:choose>
                    <c:when test="${productInfo.AVAILABLE_QUANTITY > 0}">
                        <button class="btn-order" onclick="goToCheckout()">구매하기</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn-order" style="background: #cbd5e1; cursor: not-allowed;" disabled>품절</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</main>

<script>
    let quantity = 1;
    const maxQty = parseInt('${productInfo.AVAILABLE_QUANTITY}') || 0;
    const quantityVal = document.getElementById('quantity-val');
    
    if (maxQty <= 0) {
        quantity = 0;
        quantityVal.innerText = 0;
    }

    document.getElementById('btn-minus').addEventListener('click', () => {
        if (quantity > 1) {
            quantity--;
            quantityVal.innerText = quantity;
        }
    });
    
    document.getElementById('btn-plus').addEventListener('click', () => {
        if (quantity < maxQty) {
            quantity++;
            quantityVal.innerText = quantity;
        } else {
            alert('구매 가능한 최대 수량은 ' + maxQty + '개입니다.');
        }
    });
    
    function goToCheckout() {
        const productId = '${productInfo.PRODUCT_ID}';
        const currentQty = parseInt(document.getElementById('quantity-val').innerText);
        
        if (currentQty <= 0) {
            alert('수량을 선택해주세요.');
            return;
        }

        if (currentQty > maxQty) {
            alert('구매 가능한 수량을 초과했습니다.');
            return;
        }

        <c:choose>
            <c:when test="${empty userInfoVo}">
                if(confirm("구매를 위해 로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?")) {
                    const currentUrl = encodeURIComponent(window.location.pathname + window.location.search);
                    location.href = `/front/login.do?redirectUrl=${currentUrl}`;
                }
            </c:when>
            <c:otherwise>
                location.href = "/shop/checkout.do?productId=" + productId + "&quantity=" + currentQty;
            </c:otherwise>
        </c:choose>
    }
</script>

</body>
</html>
