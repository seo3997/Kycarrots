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
    <link rel="stylesheet" href="/common/front/css/front_common.css">
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
        .logo { display: flex; align-items: center; gap: 0.75rem; text-decoration: none; color: var(--text); font-weight: 700; font-size: 1.25rem; white-space: nowrap; }
        .logo img { height: 40px; border-radius: 8px; }

        .nav-links { display: flex; align-items: center; gap: 1.5rem; flex-shrink: 0; }
        .nav-links a { text-decoration: none; color: var(--text); font-weight: 500; font-size: 0.95rem; white-space: nowrap; transition: color 0.2s; }
        .nav-links a:hover { color: var(--primary); }
        
        .btn-login { 
            background: var(--primary); 
            color: white !important; 
            padding: 0.5rem 1.25rem; 
            border-radius: var(--radius); 
            font-weight: 600;
        }

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

        .detail-tabs { 
            display: flex; 
            border-bottom: 2px solid #f1f5f9; 
            margin-top: 2rem;
            background: white;
            border-radius: 12px 12px 0 0;
            overflow: hidden;
        }

        /* Modal Styles */
        .modal-overlay { 
            display: none; 
            position: fixed; 
            top: 0; left: 0; 
            width: 100%; height: 100%; 
            background: rgba(0,0,0,0.5); 
            z-index: 1000; 
            align-items: center; 
            justify-content: center; 
        }
        .modal-content { 
            background: white; 
            width: 90%; 
            max-width: 500px; 
            padding: 2rem; 
            border-radius: 20px; 
            box-shadow: 0 20px 25px -5px rgb(0 0 0 / 0.1); 
        }
        .form-group { margin-bottom: 1.5rem; }
        .form-group label { display: block; font-weight: 600; margin-bottom: 0.5rem; font-size: 0.9rem; }
        .form-control { 
            width: 100%; 
            padding: 0.75rem; 
            border: 1px solid #e2e8f0; 
            border-radius: 8px; 
            font-size: 1rem; 
            outline: none;
            font-family: inherit;
        }
        .form-control:focus { border-color: var(--primary); box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.1); }
        .modal-btns { display: flex; gap: 1rem; margin-top: 2rem; }
        .btn-cancel { flex: 1; padding: 0.75rem; border: 1px solid #e2e8f0; border-radius: 8px; background: white; cursor: pointer; }
        .btn-submit { flex: 1; padding: 0.75rem; border: none; border-radius: 8px; background: var(--primary); color: white; font-weight: 600; cursor: pointer; }
        
        .star-rating { 
            display: flex; 
            flex-direction: row-reverse; 
            justify-content: flex-end; 
            gap: 4px; 
            font-size: 1.5rem; 
        }
        .star-rating input { display: none; }
        .star-rating label { cursor: pointer; color: #e2e8f0; transition: color 0.1s; }
        .star-rating label:hover,
        .star-rating label:hover ~ label,
        .star-rating input:checked ~ label { color: var(--accent); }

        @media (max-width: 768px) {
            .detail-wrapper { grid-template-columns: 1fr; }
            .header { padding: 0.75rem 1rem; }
            .nav-links { gap: 0.75rem; }
            .nav-links a { font-size: 0.85rem; }
            .logo span { display: none; }
        }
        .detail-tab { 
            flex: 1; 
            padding: 1.25rem 1rem; 
            text-align: center; 
            cursor: pointer; 
            font-weight: 600; 
            color: var(--text-muted);
            transition: all 0.2s;
        }
        .detail-tab.active { 
            color: var(--primary); 
            border-bottom: 3px solid var(--primary); 
            background: #f8faff;
        }
        
        .tab-content { 
            display: none; 
            background: white; 
            padding: 2rem; 
            border-radius: 0 0 12px 12px;
            box-shadow: var(--shadow);
            margin-bottom: 2rem;
        }
        .tab-content.active { display: block; }
        
        .review-item, .qna-item { 
            padding: 1.5rem 0; 
            border-bottom: 1px solid #f1f5f9; 
        }
        .review-header, .qna-header { 
            display: flex; 
            justify-content: space-between; 
            margin-bottom: 0.75rem;
            font-size: 0.9rem;
        }
        .rating-stars { color: var(--accent); }
        .item-content { line-height: 1.6; color: var(--text); margin-bottom: 0.75rem; }
        .item-meta { font-size: 0.85rem; color: var(--text-muted); }
        
        .qna-answer { 
            background: #f8fafc; 
            padding: 1rem; 
            border-radius: 8px; 
            margin-top: 0.75rem; 
            font-size: 0.95rem;
        }
        .qna-answer-meta { font-size: 0.8rem; color: var(--text-muted); margin-top: 0.5rem; }

        .btn-write { 
            float: right;
            padding: 0.5rem 1rem;
            border-radius: 8px;
            background: var(--primary);
            color: white;
            border: none;
            font-size: 0.9rem;
            font-weight: 600;
            cursor: pointer;
        }

        .btn-delete {
            padding: 0.25rem 0.5rem;
            border-radius: 4px;
            background: #fee2e2;
            color: #dc2626;
            border: none;
            font-size: 0.75rem;
            font-weight: 600;
            cursor: pointer;
            margin-left: 8px;
            transition: all 0.2s;
        }
        .btn-delete:hover {
            background: #fecaca;
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

<main class="container">
    <div class="detail-wrapper">
        <div class="product-img" style="background-image: url('${productInfo.IMAGE_URL}')"></div>
        <div class="product-info">
            <div style="display: flex; align-items: center; gap: 0.75rem; margin-bottom: 0.5rem;">
                <h1 style="margin-bottom: 0;">${productInfo.TITLE}</h1>
                <c:choose>
                    <c:when test="${productInfo.SALE_STATUS eq '0'}"><span class="status-badge" style="background: #f1f5f9; color: #94a3b8;">승인요청</span></c:when>
                    <c:when test="${productInfo.SALE_STATUS eq '10'}"><span class="status-badge" style="background: #eff6ff; color: #3b82f6;">예약중</span></c:when>
                    <c:when test="${productInfo.SALE_STATUS eq '20'}"><span class="status-badge" style="background: #fdf2f2; color: #ef4444;">품절</span></c:when>
                    <c:when test="${productInfo.SALE_STATUS eq '30'}"><span class="status-badge" style="background: #fff7ed; color: #f59e0b;">판매중지</span></c:when>
                    <c:when test="${productInfo.SALE_STATUS eq '98'}"><span class="status-badge" style="background: #fef2f2; color: #dc2626;">반려</span></c:when>
                    <c:when test="${productInfo.SALE_STATUS eq '99'}"><span class="status-badge" style="background: #f8fafc; color: #64748b;">판매완료</span></c:when>
                    <c:when test="${productInfo.SALE_STATUS ne '1'}"><span class="status-badge" style="background: #f1f5f9; color: #94a3b8;">준비중</span></c:when>
                </c:choose>
            </div>
            <p class="price-tag"><fmt:formatNumber value="${productInfo.PRICE}" type="number" maxFractionDigits="0"/>원</p>
            
            <div class="shipping-info" style="margin-bottom: 1.5rem; padding: 1rem; background: #f8fafc; border-radius: 12px; border: 1px solid #e2e8f0;">
                <div style="display: flex; align-items: center; gap: 0.5rem; font-size: 0.95rem; color: var(--text);">
                    <i class="fas fa-truck" style="color: var(--primary);"></i>
                    <span style="font-weight: 600;">배송비: </span>
                    <span><fmt:formatNumber value="${branchInfo.BASE_SHIPPING_FEE}" type="number" maxFractionDigits="0"/>원</span>
                </div>
                <div style="font-size: 0.85rem; color: var(--text-muted); margin-left: 1.7rem; margin-top: 0.25rem;">
                    (<fmt:formatNumber value="${branchInfo.FREE_SHIPPING_THRESHOLD}" type="number" maxFractionDigits="0"/>원 이상 구매 시 무료)
                </div>
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
                    <c:when test="${productInfo.SALE_STATUS eq '1' && productInfo.AVAILABLE_QUANTITY > 0}">
                        <button class="btn-order" onclick="goToCheckout()">구매하기</button>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${productInfo.SALE_STATUS eq '20' || productInfo.AVAILABLE_QUANTITY <= 0}">
                                <button class="btn-order" style="background: #cbd5e1; cursor: not-allowed;" disabled>품절</button>
                            </c:when>
                            <c:when test="${productInfo.SALE_STATUS eq '30'}">
                                <button class="btn-order" style="background: #cbd5e1; cursor: not-allowed;" disabled>판매중지</button>
                            </c:when>
                            <c:when test="${productInfo.SALE_STATUS eq '99'}">
                                <button class="btn-order" style="background: #cbd5e1; cursor: not-allowed;" disabled>판매완료</button>
                            </c:when>
                            <c:otherwise>
                                <button class="btn-order" style="background: #cbd5e1; cursor: not-allowed;" disabled>구매불가</button>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <ul class="detail-tabs">
        <li class="detail-tab active" onclick="moveTab(this, 'desc')">상품상세</li>
        <li class="detail-tab" onclick="moveTab(this, 'review')">리뷰</li>
        <li class="detail-tab" onclick="moveTab(this, 'qna')">상품문의</li>
    </ul>

    <!-- Tab 1: Description -->
    <div id="tab-desc" class="tab-content active">
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

    <!-- Tab 2: Reviews -->
    <div id="tab-review" class="tab-content">
        <div class="description-title">
            상품 리뷰
            <c:if test="${not empty userInfoVo}">
                <button class="btn-write" onclick="openReviewModal()">리뷰 쓰기</button>
            </c:if>
        </div>
        <div id="review-list">
            <div class="text-center" style="padding: 2rem; color: #94a3b8;">로딩 중...</div>
        </div>
    </div>

    <!-- Tab 3: QnA -->
    <div id="tab-qna" class="tab-content">
        <div class="description-title">
            상품 문의
            <c:if test="${not empty userInfoVo}">
                <button class="btn-write" onclick="openQnaModal()">문의 하기</button>
            </c:if>
        </div>
        <div id="qna-list">
            <div class="text-center" style="padding: 2rem; color: #94a3b8;">로딩 중...</div>
        </div>
    </div>
    <!-- Review Modal -->
    <div id="modal-review" class="modal-overlay">
        <div class="modal-content">
            <h3>리뷰 작성</h3>
            <form id="form-review" enctype="multipart/form-data">
                <input type="hidden" name="productId" value="${productInfo.PRODUCT_ID}">
                <div class="form-group">
                    <label>평점</label>
                    <div class="star-rating">
                        <input type="radio" name="rating" value="5" id="s5" checked><label for="s5">★</label>
                        <input type="radio" name="rating" value="4" id="s4"><label for="s4">★</label>
                        <input type="radio" name="rating" value="3" id="s3"><label for="s3">★</label>
                        <input type="radio" name="rating" value="2" id="s2"><label for="s2">★</label>
                        <input type="radio" name="rating" value="1" id="s1"><label for="s1">★</label>
                    </div>
                </div>
                <div class="form-group">
                    <label>내용</label>
                    <textarea name="contents" class="form-control" rows="4" placeholder="상품에 대한 솔직한 후기를 남겨주세요."></textarea>
                </div>
                <div class="form-group">
                    <label>사진 첨부</label>
                    <input type="file" name="reviewFile" class="form-control">
                </div>
                <div class="modal-btns">
                    <button type="button" class="btn-cancel" onclick="closeModal('review')">취소</button>
                    <button type="submit" class="btn-submit">등록하기</button>
                </div>
            </form>
        </div>
    </div>

    <!-- QnA Modal -->
    <div id="modal-qna" class="modal-overlay">
        <div class="modal-content">
            <h3>상품 문의</h3>
            <form id="form-qna">
                <input type="hidden" name="productId" value="${productInfo.PRODUCT_ID}">
                <input type="hidden" name="branchId" value="${productInfo.BRANCH_ID}">
                <div class="form-group">
                    <label>제목</label>
                    <input type="text" name="title" class="form-control" placeholder="문의 제목을 입력하세요.">
                </div>
                <div class="form-group">
                    <label>내용</label>
                    <textarea name="contents" class="form-control" rows="4" placeholder="문의하실 내용을 입력해 주세요."></textarea>
                </div>
                <div class="form-group">
                    <label style="display: flex; align-items: center; gap: 8px; cursor: pointer;">
                        <input type="checkbox" name="secretYn" value="Y"> 비밀글로 문의하기
                    </label>
                </div>
                <div class="modal-btns">
                    <button type="button" class="btn-cancel" onclick="closeModal('qna')">취소</button>
                    <button type="submit" class="btn-submit">등록하기</button>
                </div>
            </form>
        </div>
    </div>

<script>
    let quantity = 1;
    const maxQty = parseInt('${productInfo.AVAILABLE_QUANTITY}') || 0;
    const quantityVal = document.getElementById('quantity-val');
    
    if (quantityVal) {
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
    }
    
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
                    const currentPath = window.location.pathname + window.location.search;
                    const redirectUrl = encodeURIComponent(currentPath);
                    location.href = "/front/login.do?redirectUrl=" + redirectUrl;
                }
            </c:when>
            <c:otherwise>
                location.href = "/shop/checkout.do?productId=" + productId + "&quantity=" + currentQty;
            </c:otherwise>
        </c:choose>
    }

    function moveTab(obj, tabId) {
        // Tab header toggle
        document.querySelectorAll('.detail-tab').forEach(t => t.classList.remove('active'));
        obj.classList.add('active');

        // Content toggle
        document.querySelectorAll('.tab-content').forEach(c => c.classList.remove('active'));
        document.getElementById('tab-' + tabId).classList.add('active');

        if (tabId === 'review') loadReviews();
        if (tabId === 'qna') loadQnas();
    }

    function loadReviews() {
        const productId = '${productInfo.PRODUCT_ID}';
        const $list = document.getElementById('review-list');
        
        fetch('/rest/product/review/list?productId=' + productId)
            .then(res => res.json())
            .then(data => {
                if (data.success) {
                    if (data.list.length === 0) {
                        $list.innerHTML = '<div class="text-center" style="padding: 2rem; color: #94a3b8;">등록된 리뷰가 없습니다.</div>';
                        return;
                    }

                    let html = '';
                    data.list.forEach(item => {
                        const isMine = '${userInfoVo.userNo}' == item.USER_NO;
                        const isAdmin = '${ssAuthorId}' == 'ROLE_ADMIN' || '${ssAuthorId}' == 'ROLE_SELL';
                        
                        let deleteBtn = '';
                        if (isMine || isAdmin) {
                            deleteBtn = `<button class="btn-delete" onclick="deleteReview('\${item.REVIEW_ID}')">삭제</button>`;
                        }

                        let imgHtml = '';
                        if (item.FILE_RLTV_PATH) {
                            imgHtml = `<img src="\${item.FILE_RLTV_PATH}" style="width: 80px; height: 80px; object-fit: cover; border-radius: 8px; margin-bottom: 0.75rem;">`;
                        }

                        html += `
                            <div class="review-item">
                                <div class="review-header">
                                    <div style="display: flex; align-items: center;">
                                        <div class="rating-stars">` + '★'.repeat(item.RATING) + '☆'.repeat(5-item.RATING) + `</div>
                                        \${deleteBtn}
                                    </div>
                                    <div class="item-meta">\${item.USER_NM} | \${item.REGIST_DT}</div>
                                </div>
                                \${imgHtml}
                                <div class="item-content">\${item.CONTENTS}</div>
                            </div>
                        `;
                    });
                    $list.innerHTML = html;
                }
            });
    }

    function deleteReview(reviewId) {
        if (!confirm('리뷰를 삭제하시겠습니까?')) return;
        
        fetch('/rest/product/review/delete', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: 'reviewId=' + reviewId
        })
        .then(res => res.json())
        .then(data => {
            if (data.success) {
                alert('삭제되었습니다.');
                loadReviews();
            } else {
                alert(data.message || '삭제 중 오류가 발생했습니다.');
            }
        });
    }

    function loadQnas() {
        const productId = '${productInfo.PRODUCT_ID}';
        const $list = document.getElementById('qna-list');

        fetch('/rest/product/qna/list?productId=' + productId)
            .then(res => res.json())
            .then(data => {
                if (data.success) {
                    if (data.list.length === 0) {
                        $list.innerHTML = '<div class="text-center" style="padding: 2rem; color: #94a3b8;">등록된 문의가 없습니다.</div>';
                        return;
                    }

                    let html = '';
                    data.list.forEach(item => {
                        const isSecret = item.SECRET_YN === 'Y';
                        const isMine = '${userInfoVo.userNo}' == item.USER_NO;
                        const isAdmin = '${ssAuthorId}' == 'ROLE_ADMIN' || '${ssAuthorId}' == 'ROLE_SELL' || '${ssAuthorId}' == 'ROLE_PROJ';
                        const canSee = !isSecret || isMine || isAdmin;

                        let deleteBtn = '';
                        if (isMine || isAdmin) {
                            deleteBtn = `<button class="btn-delete" onclick="deleteQna('\${item.QNA_ID}')" style="margin-left: 0;">삭제</button>`;
                        }

                        let statusBadge = `
                            <span class="badge" style="background: \${item.QNA_STATUS === '20' ? '#ecfdf5; color: #059669;' : '#f1f5f9; color: #64748b;'} padding: 2px 6px; border-radius: 4px; font-size: 0.75rem;">
                                \${item.QNA_STATUS === '20' ? '답변완료' : '접수'}
                            </span>`;

                        let answerHtml = '';
                        if (canSee && item.QNA_STATUS === '20') {
                            answerHtml = `
                                <div class="qna-answer">
                                    <div style="font-weight: 600; margin-bottom: 0.5rem;"><i class="fas fa-reply fa-rotate-180" style="margin-right: 8px; color: var(--primary);"></i>답변</div>
                                    <div>\${item.ANSWER_CONTENTS}</div>
                                    <div class="qna-answer-meta">\${item.ANSWERER_NM} (\${item.ANSWERED_AT})</div>
                                </div>`;
                        }

                        html += `
                            <div class="qna-item">
                                <div class="qna-header">
                                    <div style="display: flex; align-items: center; gap: 8px;">
                                        <div style="font-weight: 600;">
                                            \${isSecret ? '<i class="fas fa-lock" style="margin-right: 4px;"></i>' : ''}
                                            \${item.TITLE}
                                        </div>
                                        \${statusBadge}
                                        \${deleteBtn}
                                    </div>
                                    <div class="item-meta">\${item.USER_NM} | \${item.REGIST_DT}</div>
                                </div>
                                <div class="item-content">
                                    \${canSee ? item.CONTENTS : '<span style="color: #94a3b8;">비밀글입니다.</span>'}
                                </div>
                                \${answerHtml}
                            </div>
                        `;
                    });
                    $list.innerHTML = html;
                }
            });
    }

    function deleteQna(qnaId) {
        if (!confirm('문의를 삭제하시겠습니까?')) return;
        
        fetch('/rest/product/qna/delete', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: 'qnaId=' + qnaId
        })
        .then(res => res.json())
        .then(data => {
            if (data.success) {
                alert('삭제되었습니다.');
                loadQnas();
            } else {
                alert(data.message || '삭제 중 오류가 발생했습니다.');
            }
        });
    }

    // Modal handlers
    function openReviewModal() {
        document.getElementById('modal-review').style.display = 'flex';
    }
    function openQnaModal() {
        document.getElementById('modal-qna').style.display = 'flex';
    }
    function closeModal(type) {
        document.getElementById('modal-' + type).style.display = 'none';
        document.getElementById('form-' + type).reset();
    }

    let isReviewSubmitting = false;
    document.getElementById('form-review').addEventListener('submit', function(e) {
        e.preventDefault();
        if (isReviewSubmitting) return;
        
        const $btn = this.querySelector('.btn-submit');
        $btn.disabled = true;
        $btn.innerText = '등록 중...';
        isReviewSubmitting = true;

        const formData = new FormData(this);
        
        fetch('/rest/product/review/insert', {
            method: 'POST',
            body: formData
        })
        .then(res => {
            if (!res.ok) throw new Error('Server error');
            return res.json();
        })
        .then(data => {
            if (data.success) {
                alert('리뷰가 등록되었습니다.');
                closeModal('review');
                loadReviews();
            } else {
                alert(data.message || '리뷰 등록 중 오류가 발생했습니다.');
            }
        })
        .catch(err => {
            console.error(err);
            alert('리뷰 등록 중 오류가 발생했습니다.');
        })
        .finally(() => {
            $btn.disabled = false;
            $btn.innerText = '등록하기';
            isReviewSubmitting = false;
        });
    });

    let isQnaSubmitting = false;
    document.getElementById('form-qna').addEventListener('submit', function(e) {
        e.preventDefault();
        if (isQnaSubmitting) return;

        const $btn = this.querySelector('.btn-submit');
        $btn.disabled = true;
        $btn.innerText = '등록 중...';
        isQnaSubmitting = true;

        const formData = new FormData(this);
        if (!formData.has('secretYn')) formData.append('secretYn', 'N');

        fetch('/rest/product/qna/insert', {
            method: 'POST',
            body: formData
        })
        .then(res => {
            if (!res.ok) throw new Error('Server error');
            return res.json();
        })
        .then(data => {
            if (data.success) {
                alert('문의가 등록되었습니다.');
                closeModal('qna');
                loadQnas();
            } else {
                alert(data.message || '문의 등록 중 오류가 발생했습니다.');
            }
        })
        .catch(err => {
            console.error(err);
            alert('문의 등록 중 오류가 발생했습니다.');
        })
        .finally(() => {
            $btn.disabled = false;
            $btn.innerText = '등록하기';
            isQnaSubmitting = false;
        });
    });

    window.onclick = function(event) {
        if (event.target.classList.contains('modal-overlay')) {
            event.target.style.display = "none";
        }
    }
</script>

</body>
</html>
