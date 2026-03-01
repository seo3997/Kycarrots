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
    <title>주문/결제 - ${branchInfo.BRANCH_NAME}</title>
    <script src="https://js.tosspayments.com/v1"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
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

        .form-group { margin-bottom: 1.5rem; }
        .form-group label { display: block; margin-bottom: 0.5rem; font-weight: 500; }
        .form-control { width: 100%; padding: 0.75rem; border: 1px solid #e2e8f0; border-radius: 8px; font-family: inherit; }

        #payment-method { margin-top: 2rem; }
        .btn-pay { width: 100%; padding: 1.25rem; background: var(--primary); color: white; border: none; border-radius: 12px; font-size: 1.25rem; font-weight: 700; cursor: pointer; margin-top: 2rem; }
        .btn-pay:disabled { background: #94a3b8; cursor: not-allowed; }

        /* Address Layer Modal */
        #address-layer {
            display: none;
            position: fixed;
            overflow: hidden;
            z-index: 1000;
            -webkit-overflow-scrolling: touch;
            background: white;
            border-radius: 20px;
            box-shadow: 0 20px 25px -5px rgb(0 0 0 / 0.1), 0 8px 10px -6px rgb(0 0 0 / 0.1);
        }
        #address-layer-bg {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0,0,0,0.5);
            z-index: 999;
            backdrop-filter: blur(4px);
        }
    </style>
</head>
<body>

<header class="header">
    <c:set var="deliveryFee" value="0" />
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
        
        <div class="section-title">주문 상품</div>
        <div class="order-summary">
            <c:set var="quantity" value="${not empty reqParam.quantity ? reqParam.quantity : 1}" />
            <c:set var="itemPrice" value="${not empty productInfo.PRICE ? productInfo.PRICE : 0}" />
            <c:set var="totalAmount" value="${itemPrice * quantity}" />
            <div class="summary-row">
                <span>${productInfo.TITLE} x ${quantity}</span>
                <span><fmt:formatNumber value="${totalAmount}" type="number" maxFractionDigits="0"/>원</span>
            </div>
            <div class="summary-row">
                <span>배송비</span>
                <span><fmt:formatNumber value="${deliveryFee}" type="number" maxFractionDigits="0"/>원</span>
            </div>
            <div class="total-row">
                <span>최종 결제 금액</span>
                <span><fmt:formatNumber value="${totalAmount + deliveryFee}" type="number" maxFractionDigits="0"/>원</span>
            </div>
        </div>

        <div class="section-title">배송 정보</div>
        <div class="form-group">
            <label for="receiverName">받는 분</label>
            <input type="text" id="receiverName" class="form-control" value="${userInfoVo.userNm}">
        </div>
        <div class="form-group">
            <label for="receiverPhone">연락처</label>
            <input type="tel" id="receiverPhone" class="form-control" value="${userInfoVo.cttpc}">
        </div>
        <div class="form-group">
            <label for="zipCode">우편번호</label>
            <div style="display: flex; gap: 0.5rem;">
                <input type="text" id="zipCode" class="form-control" style="width: 120px;" readonly>
                <button type="button" class="form-control" style="width: auto; background: #e2e8f0; cursor: pointer;" onclick="execDaumPostcode()">주소 찾기</button>
            </div>
        </div>
        <div class="form-group">
            <label for="address1">도로명 주소</label>
            <input type="text" id="address1" class="form-control" readonly>
        </div>
        <div class="form-group">
            <label for="address2">상세 주소</label>
            <input type="text" id="address2" class="form-control" placeholder="나머지 상세 주소를 입력해주세요">
        </div>
        <div class="form-group">
            <label for="orderMemo">배송 메모</label>
            <textarea id="orderMemo" class="form-control" rows="3" placeholder="배송 시 요청사항을 입력해주세요 (예: 문 앞에 놓아주세요)"></textarea>
        </div>

        <div class="section-title">결제 수단</div>
        <div style="padding: 1rem; background: #f8fafc; border-radius: 12px; margin-bottom: 2rem;">
            <p><i class="fas fa-info-circle"></i> 결제하기 버튼을 누르면 결제창이 열립니다.</p>
        </div>

        <button class="btn-pay" id="payment-button">
            <fmt:formatNumber value="${totalAmount + deliveryFee}" type="number" maxFractionDigits="0"/>원 결제하기
        </button>
    </div>
</main>

<!-- Address Search Modal -->
<div id="address-layer-bg" onclick="closeDaumPostcode()"></div>
<div id="address-layer">
    <div style="padding: 1rem; display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #eee;">
        <span style="font-weight: 600;">주소 검색</span>
        <i class="fas fa-times" style="cursor: pointer; padding: 0.5rem;" onclick="closeDaumPostcode()"></i>
    </div>
    <div id="address-embed" style="width:100%; height:400px; position:relative;"></div>
</div>

<script>
    const clientKey = "${branchInfo.TOSS_CLIENT_KEY}"; 
    const customerKey = "${not empty userInfoVo.userNo ? 'USER_' : ''}${not empty userInfoVo.userNo ? userInfoVo.userNo : 'ANONYMOUS'}";
    
    const tossPayments = TossPayments(clientKey);

    // Phone number formatting
    const phoneInput = document.getElementById('receiverPhone');
    const formatPhone = (val) => {
        val = val.replace(/[^0-9]/g, '').slice(0, 11);
        if (val.length < 4) return val;
        if (val.length < 7) return val.replace(/(\d{3})(\d{1,3})/, '$1-$2');
        if (val.length < 11) return val.replace(/(\d{3})(\d{3})(\d{1,4})/, '$1-$2-$3');
        return val.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
    };

    if (phoneInput.value) {
        phoneInput.value = formatPhone(phoneInput.value);
    }

    phoneInput.addEventListener('input', function(e) {
        e.target.value = formatPhone(e.target.value);
    });

    const element_layer = document.getElementById('address-layer');
    const element_bg = document.getElementById('address-layer-bg');

    function closeDaumPostcode() {
        element_layer.style.display = 'none';
        element_bg.style.display = 'none';
    }

    function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                var addr = '';
                if (data.userSelectedType === 'R') {
                    addr = data.roadAddress;
                } else {
                    addr = data.jibunAddress;
                }

                document.getElementById('zipCode').value = data.zonecode;
                document.getElementById("address1").value = addr;
                document.getElementById("address2").focus();
                closeDaumPostcode();
            },
            width : '100%',
            height : '100%',
            maxSuggestItems : 5
        }).embed(document.getElementById('address-embed'));

        element_layer.style.display = 'block';
        element_bg.style.display = 'block';

        // Set position and size
        const width = Math.min(window.innerWidth - 40, 500);
        const height = 480;
        element_layer.style.width = width + 'px';
        element_layer.style.height = height + 'px';
        element_layer.style.left = ((window.innerWidth - width) / 2) + 'px';
        element_layer.style.top = ((window.innerHeight - height) / 2) + 'px';
    }

    document.getElementById("payment-button").addEventListener("click", async function () {
        const receiverName = document.getElementById("receiverName").value;
        const receiverPhone = document.getElementById("receiverPhone").value;
        const zipCode = document.getElementById("zipCode").value;
        const address1 = document.getElementById("address1").value;
        const address2 = document.getElementById("address2").value;
        const orderMemo = document.getElementById("orderMemo").value;

        if(!receiverName.trim()) {
            alert("받는 분 성함을 입력해주세요.");
            document.getElementById("receiverName").focus();
            return;
        }

        const phoneRegex = /^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$/;
        if(!receiverPhone || !phoneRegex.test(receiverPhone)) {
            alert("올바른 연락처 형식을 입력해주세요.");
            document.getElementById("receiverPhone").focus();
            return;
        }

        if(!zipCode || !address1) {
            alert("주소 검색을 통해 주소를 입력해주세요.");
            execDaumPostcode();
            return;
        }

        if(!address2.trim()) {
            alert("상세 주소를 입력해주세요.");
            document.getElementById("address2").focus();
            return;
        }

        this.disabled = true;
        this.innerText = "처리 중...";

        try {
            // 1. Create Order in Backend
            const orderResponse = await fetch("/api/payment/order/create", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    userNo: "${userInfoVo.userNo}",
                    branchId: "${branchInfo.BRANCH_ID}",
                    totalItemAmount: ${totalAmount},
                    deliveryFee: ${deliveryFee},
                    totalPayAmount: ${totalAmount + deliveryFee},
                    receiverName: receiverName,
                    receiverPhone: receiverPhone,
                    zipCode: zipCode,
                    address1: address1,
                    address2: address2,
                    orderMemo: orderMemo,
                    items: [
                        {
                            productId: "${productInfo.PRODUCT_ID}",
                            quantity: "${quantity}",
                            productName: "${productInfo.TITLE}"
                        }
                    ]
                })
            });

            const orderData = await orderResponse.json();

            if (orderData.success) {
                // 2. Request Payment via Toss (Direct Implementation)
                await tossPayments.requestPayment('카드', {
                    amount: ${totalAmount + deliveryFee},
                    orderId: orderData.orderNo,
                    orderName: "${productInfo.TITLE}" + (parseInt("${quantity}") > 1 ? " 외" : ""),
                    successUrl: window.location.origin + "/shop/success.do",
                    failUrl: window.location.origin + "/shop/fail.do",
                    customerName: "${userInfoVo.userNm}"
                });
            } else {
                alert("주문 생성에 실패했습니다: " + orderData.message);
                this.disabled = false;
                this.innerText = "결제하기";
            }
        } catch (error) {
            console.error(error);
            alert("오류가 발생했습니다.");
            this.disabled = false;
            this.innerText = "결제하기";
        }
    });
</script>

</body>
</html>
