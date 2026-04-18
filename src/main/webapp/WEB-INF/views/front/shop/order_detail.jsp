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
    <link rel="stylesheet" href="/common/front/css/front_common.css?v=20240316">
</head>
<body>
    <div id="loadingOverlay" class="loading-overlay" style="display: flex;">
        <div class="spinner"></div>
    </div>

<%@ include file="/common/frontinc/shop_header.jspf" %>

<main class="container" style="max-width: 800px; margin: 2rem auto;">
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
        <h1>주문 상세 내역</h1>
        <a href="/shop/orderList.do" class="btn-cancel" style="text-decoration: none; color: var(--text-muted); border-color: #cbd5e1;">목록으로</a>
    </div>

    <div class="detail-card">
        <div class="section-title"><i class="fas fa-receipt"></i> 주문 요약</div>
        <div class="summary-box">
            <div class="summary-item">
                <div class="summary-label">주문번호</div>
                <div class="summary-value primary">${resultVo.orderNo}</div>
            </div>
            <div class="summary-item">
                <div class="summary-label">주문상태</div>
                <div>
                    <c:choose>
                        <c:when test="${resultVo.orderStatus == '30'}"><span class="badge" style="background: #eff6ff; color: #1e40af; padding: 4px 12px;">결제완료</span></c:when>
                        <c:when test="${resultVo.orderStatus == '40'}"><span class="badge" style="background: #fee2e2; color: #991b1b; padding: 4px 12px;">주문취소</span></c:when>
                        <c:when test="${resultVo.orderStatus == '50'}"><span class="badge" style="background: #fff7ed; color: #9a3412; padding: 4px 12px;">배송준비중</span></c:when>
                        <c:when test="${resultVo.orderStatus == '60'}"><span class="badge" style="background: #dbeafe; color: #1e40af; padding: 4px 12px;">배송중</span></c:when>
                        <c:when test="${resultVo.orderStatus == '70'}"><span class="badge" style="background: #f0fdf4; color: #166534; padding: 4px 12px;">배송완료</span></c:when>
                        <c:when test="${resultVo.orderStatus == '99'}"><span class="badge" style="background: #f8fafc; color: #0f172a; padding: 4px 12px;">주문확정</span></c:when>
                        <c:otherwise><span class="badge" style="background: #f1f5f9; color: #475569; padding: 4px 12px;">${resultVo.orderStatus}</span></c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="summary-item">
                <div class="summary-label">주문일시</div>
                <div class="summary-value">${resultVo.orderedAt}</div>
            </div>
        </div>
        
        <c:if test="${resultVo.orderStatus == '40'}">
            <div class="info-grid" style="padding: 1rem; background: #fff1f2; border-radius: 8px; border: 1px solid #fda4af;">
                <span class="info-label" style="color: #991b1b;">취소 사유</span>
                <span class="info-value" style="color: #e11d48;">${resultVo.cancelReason}</span>
            </div>
        </c:if>
    </div>

    <div class="detail-card">
        <div class="section-title"><i class="fas fa-shopping-basket"></i> 주문 상품</div>
        <div class="item-list">
            <c:forEach var="item" items="${itemList}">
                <div class="item-row">
                    <div class="item-img" style="background-image: url('${item.imageUrl}')"></div>
                    <div class="item-details">
                        <div class="item-name">${item.title}</div>
                        <div style="display: flex; justify-content: space-between; align-items: flex-end;">
                            <div class="item-meta" style="font-size: 0.9rem; color: var(--text-muted);">수량: ${item.quantity}개</div>
                            <div class="item-price"><fmt:formatNumber value="${item.unitPrice}" type="number" maxFractionDigits="0"/>원</div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="price-summary">
            <span class="total-price-label">총 결제 금액</span>
            <span class="total-price-value"><fmt:formatNumber value="${resultVo.totalPayAmount}" type="number" maxFractionDigits="0"/>원</span>
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
            <div style="margin-top: 1.5rem; padding: 1.25rem; border-radius: 12px; background: #eff6ff; border: 1px solid #bfdbfe; display: flex; align-items: center; gap: 1rem;">
                <div style="width: 48px; height: 48px; background: white; border-radius: 50%; display: flex; align-items: center; justify-content: center; color: var(--primary); border: 1px solid #bfdbfe;">
                    <i class="fas fa-truck-moving"></i>
                </div>
                <div>
                    <div style="font-size: 0.85rem; color: #1e40af; font-weight: 600;">${resultVo.deliveryCompanyNm}</div>
                    <div style="font-size: 1.1rem; font-weight: 700; color: #1e3a8a;">송장번호: ${resultVo.trackingNo}</div>
                </div>
            </div>
        </c:if>
    </div>
    <div style="display: flex; gap: 1rem; margin-top: 1rem;">
        <c:if test="${resultVo.orderStatus == '50'}">
            <button type="button" class="btn-cancel" style="flex: 1; padding: 1rem; font-size: 1.1rem;" id="btn-cancel-${resultVo.orderId}"
                    onclick="handleCancel('${resultVo.orderId}', '${resultVo.orderedAt}')">주문취소</button>
        </c:if>
        <c:if test="${resultVo.orderStatus == '70'}">
            <fmt:parseDate value="${resultVo.deliveredAt}" var="deliveredAtDate" pattern="yyyy-MM-dd HH:mm:ss" />
            <jsp:useBean id="now" class="java.util.Date" />
            <fmt:parseNumber value="${(now.time - deliveredAtDate.time) / (1000 * 60 * 60 * 24)}" var="diffDays" integerOnly="true" />
            <c:if test="${diffDays <= 7}">
                <button type="button" class="btn-cancel" style="flex: 1; padding: 1rem; font-size: 1.1rem; border-color: var(--primary); color: var(--primary);" id="btn-return-${resultVo.orderId}"
                        onclick="handleReturn('${resultVo.orderId}', '${resultVo.deliveredAt}', '${resultVo.orderStatus}')">반품요청</button>
            </c:if>
        </c:if>
    </div>
</main>

<script>
    async function handleCancel(orderId, orderedAt) {
        if (!confirm("정말로 주문을 취소하시겠습니까?")) return;
        const cancelReason = prompt("취소 사유를 입력해주세요.", "고객 변심");
        if (cancelReason === null) return;

        const btn = document.getElementById('btn-cancel-' + orderId);
        btn.disabled = true;
        btn.innerText = "취소 중...";

        try {
            const response = await fetch("/api/payment/cancel", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    orderId: orderId,
                    cancelReason: cancelReason,
                    userNo: "${userInfoVo.userNo}"
                })
            });
            const result = await response.json();
            if (result.success) {
                alert("주문이 정상적으로 취소되었습니다.");
                location.reload();
            } else {
                alert("취소 실패: " + result.message);
                btn.disabled = false;
                btn.innerText = "주문취소";
            }
        } catch (error) {
            console.error(error);
            alert("처리 중 오류가 발생했습니다.");
            btn.disabled = false;
            btn.innerText = "주문취소";
        }
    }

    async function handleReturn(orderId, deliveredAt, status) {
        if (!confirm("반품을 요청하시겠습니까? (배송비가 발생할 수 있습니다.)")) return;
        const returnReason = prompt("반품 사유를 입력해주세요.", "단순 변심");
        if (returnReason === null) return;

        const btn = document.getElementById('btn-return-' + orderId);
        btn.disabled = true;
        btn.innerText = "처리 중...";

        try {
            const response = await fetch("/api/payment/return", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    orderId: orderId,
                    returnReason: returnReason,
                    userNo: "${userInfoVo.userNo}"
                })
            });
            const result = await response.json();
            if (result.success) {
                alert("반품 요청이 정상적으로 접수되었습니다.");
                location.reload();
            } else {
                alert("반품 요청 실패: " + result.message);
                btn.disabled = false;
                btn.innerText = "반품요청";
            }
        } catch (error) {
            console.error(error);
            alert("처리 중 오류가 발생했습니다.");
            btn.disabled = false;
            btn.innerText = "반품요청";
        }
    }
</script>


<%@ include file="/common/frontinc/shop_footer.jspf" %>

<script src="/common/front/lib/jquery-3.6.0.min.js"></script>
<script src="/common/front/js/front_common.js?v=20240316"></script>
<%@ include file="/common/frontinc/msg.jspf" %>
</body>
</html>
