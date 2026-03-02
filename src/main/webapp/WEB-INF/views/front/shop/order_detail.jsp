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
    <title>주문 상세 - ${branchInfo.BRANCH_NAME}</title>
    <link rel="stylesheet" href="/common/front/lib/font-awesome/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/common/front/css/front_common.css">
    <style>
        .detail-card {
            background: white;
            border-radius: 16px;
            padding: 2rem;
            box-shadow: 0 4px 6px -1px rgb(0 0 0 / 0.1);
            margin-bottom: 2rem;
        }
        .section-title {
            font-size: 1.25rem;
            font-weight: 700;
            margin-bottom: 1.5rem;
            padding-bottom: 0.75rem;
            border-bottom: 2px solid #f1f5f9;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }
        .info-grid {
            display: grid;
            grid-template-columns: 120px 1fr;
            gap: 1rem;
            margin-bottom: 0.5rem;
        }
        .info-label {
            color: var(--text-muted);
            font-weight: 500;
        }
        .info-value {
            font-weight: 600;
        }
        .item-list {
            display: flex;
            flex-direction: column;
            gap: 1rem;
        }
        .item-row {
            display: flex;
            align-items: center;
            gap: 1rem;
            padding: 1rem;
            border-radius: 12px;
            background: #f8fafc;
        }
        .item-img {
            width: 80px;
            height: 80px;
            border-radius: 8px;
            background-size: cover;
            background-position: center;
        }
        .item-details {
            flex: 1;
        }
        .item-name {
            font-weight: 700;
            margin-bottom: 0.25rem;
        }
        .item-price {
            color: var(--primary);
            font-weight: 600;
        }
        .status-badge {
            padding: 0.25rem 0.75rem;
            border-radius: 20px;
            font-size: 0.85rem;
            font-weight: 600;
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

<main class="container" style="max-width: 800px; margin: 2rem auto;">
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
        <h1>주문 상세 내역</h1>
        <a href="/shop/orderList.do" class="btn-cancel" style="text-decoration: none; color: var(--text-muted); border-color: #cbd5e1;">목록으로</a>
    </div>

    <div class="detail-card">
        <div class="section-title"><i class="fas fa-info-circle"></i> 주문 정보</div>
        <div class="info-grid">
            <span class="info-label">주문번호</span>
            <span class="info-value">${resultVo.orderNo}</span>
        </div>
        <div class="info-grid">
            <span class="info-label">주문일시</span>
            <span class="info-value">${resultVo.orderedAt}</span>
        </div>
        <div class="info-grid">
            <span class="info-label">주문상태</span>
            <span class="info-value">
                <c:choose>
                    <c:when test="${resultVo.orderStatus == '30'}"><span class="status-badge" style="background: #eff6ff; color: #1e40af;">결제완료</span></c:when>
                    <c:when test="${resultVo.orderStatus == '40'}"><span class="status-badge" style="background: #fee2e2; color: #991b1b;">주문취소</span></c:when>
                    <c:when test="${resultVo.orderStatus == '50'}"><span class="status-badge" style="background: #fff7ed; color: #9a3412;">배송준비중</span></c:when>
                    <c:when test="${resultVo.orderStatus == '60'}"><span class="status-badge" style="background: #dbeafe; color: #1e40af;">배송중</span></c:when>
                    <c:when test="${resultVo.orderStatus == '70'}"><span class="status-badge" style="background: #f0fdf4; color: #166534;">배송완료</span></c:when>
                    <c:when test="${resultVo.orderStatus == '80'}"><span class="status-badge" style="background: #fff1f2; color: #9f1239;">반품요청</span></c:when>
                    <c:when test="${resultVo.orderStatus == '89'}"><span class="status-badge" style="background: #f1f5f9; color: #475569;">반품완료</span></c:when>
                    <c:when test="${resultVo.orderStatus == '99'}"><span class="status-badge" style="background: #f8fafc; color: #0f172a;">주문확정</span></c:when>
                    <c:otherwise><span class="status-badge" style="background: #f1f5f9; color: #475569;">${resultVo.orderStatus}</span></c:otherwise>
                </c:choose>
            </span>
        </div>
        <c:if test="${resultVo.orderStatus == '40'}">
            <div class="info-grid">
                <span class="info-label">취소 사유</span>
                <span class="info-value" style="color: #ef4444;">${resultVo.cancelReason}</span>
            </div>
        </c:if>
    </div>

    <div class="detail-card">
        <div class="section-title"><i class="fas fa-box"></i> 주문 상품</div>
        <div class="item-list">
            <c:forEach var="item" items="${itemList}">
                <div class="item-row">
                    <div class="item-img" style="background-image: url('${item.imageUrl}')"></div>
                    <div class="item-details">
                        <div class="item-name">${item.title}</div>
                        <div class="item-meta" style="font-size: 0.9rem; color: var(--text-muted);">수량: ${item.quantity}개</div>
                        <div class="item-price"><fmt:formatNumber value="${item.unitPrice}" type="number" maxFractionDigits="0"/>원</div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div style="margin-top: 1.5rem; padding-top: 1.5rem; border-top: 1px dashed #e2e8f0; display: flex; justify-content: space-between; align-items: center;">
            <span style="font-weight: 700; font-size: 1.1rem;">총 결제 금액</span>
            <span style="font-weight: 800; font-size: 1.5rem; color: var(--primary);"><fmt:formatNumber value="${resultVo.totalPayAmount}" type="number" maxFractionDigits="0"/>원</span>
        </div>
    </div>

    <div class="detail-card">
        <div class="section-title"><i class="fas fa-truck"></i> 배송 정보</div>
        <div class="info-grid">
            <span class="info-label">수령인</span>
            <span class="info-value">${resultVo.receiverName}</span>
        </div>
        <div class="info-grid">
            <span class="info-label">연락처</span>
            <span class="info-value">${resultVo.receiverPhone}</span>
        </div>
        <div class="info-grid">
            <span class="info-label">주소</span>
            <span class="info-value">[${resultVo.zipCode}] ${resultVo.address1} ${resultVo.address2}</span>
        </div>
        <div class="info-grid">
            <span class="info-label">배송메모</span>
            <span class="info-value" style="font-weight: 400;">${not empty resultVo.orderMemo ? resultVo.orderMemo : '-'}</span>
        </div>
        <c:if test="${resultVo.orderStatus == '60' or resultVo.orderStatus == '70'}">
            <div style="margin-top: 1rem; padding: 1rem; border-radius: 8px; background: #eff6ff; border: 1px solid #bfdbfe;">
                <div class="info-grid">
                    <span class="info-label">택배사</span>
                    <span class="info-value">${resultVo.deliveryCompanyNm}</span>
                </div>
                <div class="info-grid">
                    <span class="info-label">운송장번호</span>
                    <span class="info-value" style="color: var(--primary);">${resultVo.trackingNo}</span>
                </div>
            </div>
        </c:if>
    </div>
</main>

<%@ include file="/common/inc/msg.jspf" %>
</body>
</html>
