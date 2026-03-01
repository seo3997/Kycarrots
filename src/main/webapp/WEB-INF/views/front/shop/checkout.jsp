<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>주문/결제 - ${branchInfo.BRANCH_NAME}</title>
    <script src="https://js.tosspayments.com/v1/payment-widget"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;600;700&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary: #2563eb;
            --bg: #f8fafc;
            --text: #1e293b;
            --shadow: 0 4px 6px -1px rgb(0 0 0 / 0.1);
        }

        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Outfit', sans-serif; background-color: var(--bg); color: var(--text); }

        .header { background: white; padding: 1rem 2rem; display: flex; justify-content: space-between; align-items: center; box-shadow: 0 1px 2px 0 rgb(0 0 0 / 0.05); }
        .logo { display: flex; align-items: center; gap: 0.75rem; text-decoration: none; color: var(--text); font-weight: 700; font-size: 1.25rem; }

        .container { max-width: 800px; margin: 2rem auto; padding: 0 1rem; }
        .checkout-card { background: white; padding: 2rem; border-radius: 20px; box-shadow: var(--shadow); }
        
        h1 { font-size: 1.5rem; margin-bottom: 2rem; border-bottom: 2px solid #f1f5f9; padding-bottom: 1rem; }
        
        .section-title { font-size: 1.125rem; font-weight: 600; margin-bottom: 1rem; margin-top: 2rem; }

        .order-summary { background: #f8fafc; padding: 1.5rem; border-radius: 12px; margin-bottom: 2rem; }
        .summary-row { display: flex; justify-content: space-between; margin-bottom: 0.5rem; }
        .total-row { border-top: 1px solid #e2e8f0; padding-top: 1rem; margin-top: 1rem; font-weight: 700; font-size: 1.25rem; color: var(--primary); }

        #payment-method { margin-top: 2rem; }
        .btn-pay { width: 100%; padding: 1.25rem; background: var(--primary); color: white; border: none; border-radius: 12px; font-size: 1.25rem; font-weight: 700; cursor: pointer; margin-top: 2rem; }
    </style>
</head>
<body>

<header class="header">
    <a href="/shop/list.do" class="logo">
        <div style="width: 40px; height: 40px; background: var(--primary); border-radius: 8px; display: flex; align-items: center; justify-content: center; color: white;">
            <i class="fas fa-shopping-bag"></i>
        </div>
        <span>${branchInfo.BRANCH_NAME}</span>
    </a>
</header>

<main class="container">
    <div class="checkout-card">
        <h1>주문서 작성</h1>
        
        <div class="order-summary">
            <div class="summary-row">
                <span>프리미엄 무선 헤드폰 x 1</span>
                <span>129,000원</span>
            </div>
            <div class="summary-row">
                <span>배송비</span>
                <span>3,000원</span>
            </div>
            <div class="total-row">
                <span>최종 결제 금액</span>
                <span>132,000원</span>
            </div>
        </div>

        <div class="section-title">결제 수단</div>
        <div id="payment-method"></div>

        <button class="btn-pay" id="payment-button">132,000원 결제하기</button>
    </div>
</main>

<script>
    const clientKey = "${branchInfo.TOSS_CLIENT_KEY}"; // 지점별 클라이언트 키 주입
    const customerKey = "${userInfoVo.userNo || 'ANONYMOUS'}"; // 고객 식별키
    
    const paymentWidget = PaymentWidget(clientKey, customerKey);
    
    paymentWidget.renderPaymentMethods(
        "#payment-method",
        { value: 132000 },
        { variantKey: "DEFAULT" }
    );

    document.getElementById("payment-button").addEventListener("click", function () {
        paymentWidget.requestPayment({
            orderId: "ORDER_" + new Date().getTime(),
            orderName: "프리미엄 무선 헤드폰 외 1건",
            successUrl: window.location.origin + "/shop/success.do",
            failUrl: window.location.origin + "/shop/fail.do",
            customerEmail: "${userInfoVo.email}",
            customerName: "${userInfoVo.userNm}"
        });
    });
</script>

</body>
</html>
