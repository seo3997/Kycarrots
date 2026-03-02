<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.StringUtil" %>
<%@ page import="com.whomade.kycarrots.framework.common.util.SysUtil" %>
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

        .container { max-width: 800px; margin: 2rem auto; padding: 1rem; }
        .detail-wrapper { display: flex; flex-direction: column; gap: 2rem; background: white; padding: 2rem; border-radius: 20px; box-shadow: var(--shadow); }

        .product-img { width: 100%; border-radius: 12px; aspect-ratio: 16/9; background: #f1f5f9; background-size: cover; background-position: center; }
        
        .product-info h1 { font-size: 1.75rem; margin-bottom: 0.75rem; }
        .price-tag { font-size: 1.5rem; font-weight: 700; color: var(--primary); margin-bottom: 1.5rem; }
        
        .description-card { background: white; border-radius: 20px; padding: 2.5rem; box-shadow: var(--shadow); margin-top: 2rem; }
        .description-title { font-size: 1.25rem; font-weight: 700; margin-bottom: 1.5rem; padding-bottom: 1rem; border-bottom: 2px solid #f1f5f9; }
        .description { color: var(--text-muted); line-height: 1.8; font-size: 1.05rem; }
        
        .additional-images { display: grid; grid-template-columns: repeat(auto-fill, minmax(150px, 1fr)); gap: 1rem; margin-top: 1.5rem; }
        .additional-img { width: 100%; aspect-ratio: 1; object-fit: cover; border-radius: 8px; border: 1px solid #e2e8f0; cursor: zoom-in; }
        .description img { max-width: 100%; height: auto; border-radius: 8px; margin: 1rem 0; box-shadow: 0 4px 12px rgba(0,0,0,0.05); }
        .description table { width: 100% !important; border-collapse: collapse; margin: 1.5rem 0; border-radius: 8px; overflow: hidden; border: 1px solid #e2e8f0; }
        .description table th, .description table td { padding: 12px 16px; border: 1px solid #e2e8f0; }
        .description table th { background: #f8fafc; font-weight: 600; color: var(--text); }
        .description p { margin-bottom: 1rem; }
        .description pre, .description code { background: #f1f5f9; padding: 0.2rem 0.4rem; border-radius: 4px; font-family: monospace; }
        .description blockquote { border-left: 4px solid var(--primary); padding-left: 1rem; margin: 1.5rem 0; font-style: italic; color: var(--text); }

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

    <div class="description-card">
        <div class="description-title">상품 상세 설명</div>
        <div class="description">
            <c:set var="editorMode" value="${productInfo.EDITOR_MODE}" />
            <% 
                com.whomade.kycarrots.framework.common.object.DataMap pInfo = (com.whomade.kycarrots.framework.common.object.DataMap)request.getAttribute("productInfo");
                String mode = pInfo.getString("EDITOR_MODE");
                if (mode.equalsIgnoreCase("true")) mode = "1";
                else if (mode.equalsIgnoreCase("false")) mode = "0";

                String desc = pInfo.getStringOrgn("DESCRIPTION");
                if (desc.equals("")) desc = pInfo.getStringOrgn("description");

                if ("1".equals(mode) || "2".equals(mode)) {
            %>
                <%= desc %>
            <% } else { %>
                <%= StringUtil.getHtmlValue(desc) %>
            <% } %>
        </div>

        <!-- Additional Images Section -->
        <c:if test="${not empty imageList}">
            <div class="additional-images">
                <c:forEach var="img" items="${imageList}">
                    <c:if test="${img.represent == 0 && (img.imageCd == '1' || img.imageCd == '2')}">
                        <img src="${img.imageUrl}" class="additional-img" alt="Additional Image" onclick="window.open(this.src)">
                    </c:if>
                </c:forEach>
            </div>
        </c:if>
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
