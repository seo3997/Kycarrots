<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>결제 완료 - ${branchInfo.BRANCH_NAME}</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;600;700&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary: #2563eb;
            --bg: #f8fafc;
            --text: #1e293b;
        }
        body { font-family: 'Outfit', sans-serif; background-color: var(--bg); color: var(--text); text-align: center; padding: 4rem 1rem; }
        .success-card { background: white; width: 100%; max-width: 500px; margin: 0 auto; padding: 3rem; border-radius: 24px; box-shadow: 0 10px 25px rgba(0,0,0,0.05); }
        .icon { font-size: 4rem; color: #10b981; margin-bottom: 1.5rem; }
        h1 { font-size: 1.75rem; margin-bottom: 1rem; line-height: 1.3; }
        p { color: #64748b; margin-bottom: 2rem; }
        .order-info { background: #f1f5f9; padding: 1.5rem; border-radius: 12px; text-align: left; margin-bottom: 2rem; }
        .info-row { display: flex; justify-content: space-between; margin-bottom: 0.75rem; gap: 1rem; }
        .info-row span { color: #64748b; font-size: 0.9rem; flex-shrink: 0; }
        .info-row strong { word-break: break-all; text-align: right; }
        .btn-home { display: block; width: 100%; padding: 1.125rem; background: var(--primary); color: white; text-decoration: none; border-radius: 14px; font-weight: 700; font-size: 1.1rem; transition: all 0.2s; }
        .btn-home:hover { transform: translateY(-2px); filter: brightness(1.1); }
        
        @media (max-width: 480px) {
            body { padding: 2rem 1rem; }
            .success-card { padding: 2rem 1.5rem; border-radius: 20px; }
            .info-row { flex-direction: column; gap: 0.25rem; }
            .info-row strong { text-align: left; font-size: 1rem; }
            h1 { font-size: 1.5rem; }
        }
        #loading { display: block; }
        #result { display: none; }
    </style>
</head>
<body>

<div class="success-card">
    <div id="loading">
        <i class="fas fa-spinner fa-spin icon" style="color: var(--primary);"></i>
        <h1>결제 승인 중...</h1>
        <p>잠시만 기다려 주세요. 결제가 승인되고 있습니다.</p>
    </div>

    <div id="result">
        <i class="fas fa-check-circle icon"></i>
        <h1>결제가 완료되었습니다!</h1>
        <p>주문해주셔서 감사합니다. 소중한 상품을 빠르게 배송해 드리겠습니다.</p>
        
        <div class="order-info">
            <div class="info-row">
                <span>주문번호</span>
                <strong id="res-orderNo"></strong>
            </div>
            <div class="info-row">
                <span>결제금액</span>
                <strong id="res-amount"></strong>
            </div>
            <div class="info-row">
                <span>결제방법</span>
                <strong id="res-method"></strong>
            </div>
        </div>

        <a href="/shop/list.do" class="btn-home">계속 쇼핑하기</a>
        <a href="/shop/orderList.do" style="display: block; margin-top: 1rem; color: var(--primary); text-decoration: none;">주문 내역 확인</a>
    </div>

    <div id="error" style="display: none;">
        <i class="fas fa-exclamation-triangle icon" style="color: #ef4444;"></i>
        <h1>결제 승인 실패</h1>
        <p id="error-message">결제 승인 처리 중 오류가 발생했습니다.</p>
        <a href="/shop/list.do" class="btn-home" style="background: #ef4444;">홈으로 가기</a>
    </div>
</div>

<script>
    $(document).ready(function() {
        const urlParams = new URLSearchParams(window.location.search);
        const paymentKey = urlParams.get('paymentKey');
        const orderId = urlParams.get('orderId');
        const amount = urlParams.get('amount');

        if (!paymentKey || !orderId || !amount) {
            $("#loading").hide();
            $("#error").show();
            $("#error-message").text("결제 정보가 없거나 잘못되었습니다.");
            return;
        }

        // Call Backend Confirm API
        $.ajax({
            url: "/api/payment/confirm",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                paymentKey: paymentKey,
                orderNo: orderId,
                amount: parseInt(amount)
            }),
            success: function(res) {
                $("#loading").hide();
                if (res.success) {
                    $("#result").show();
                    $("#res-orderNo").text(orderId);
                    $("#res-amount").text(Number(amount).toLocaleString() + "원");
                    $("#res-method").text(res.paymentVo.paymentMethod || "카드");
                } else {
                    $("#error").show();
                    $("#error-message").text(res.message || "결제 승인에 실패했습니다.");
                }
            },
            error: function(xhr) {
                $("#loading").hide();
                $("#error").show();
                $("#error-message").text("서버 통신 중 오류가 발생했습니다.");
            }
        });
    });
</script>

</body>
</html>
