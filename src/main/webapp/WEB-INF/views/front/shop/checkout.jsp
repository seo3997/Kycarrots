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
    <link rel="stylesheet" href="/common/front/css/front_common.css?v=20240316">
    <style>
        .summary-row { display: flex; justify-content: space-between; margin-bottom: 0.5rem; }
        .total-row { border-top: 1px solid #e2e8f0; padding-top: 1rem; margin-top: 1rem; font-weight: 700; font-size: 1.25rem; color: var(--primary); }

        #payment-method { margin-top: 2rem; }
        
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
    <div id="loadingOverlay" class="loading-overlay" style="display: flex;">
        <div class="spinner"></div>
    </div>

<%@ include file="/common/frontinc/shop_header.jspf" %>

<main class="container" style="max-width: 700px; padding: 1.5rem 1rem;">
    <div class="checkout-card" style="padding: 1.5rem; border-radius: var(--radius-lg);">
        <h1 style="font-size: 1.5rem; font-weight: 700; margin-bottom: 2rem; color: var(--text);">주문/결제</h1>
        
        <div class="section-title">주문 상품 정보</div>
        <div class="order-summary" style="background: #f1f5f9; padding: 1.25rem; border-radius: var(--radius); margin-bottom: 2rem;">
            <c:set var="quantity" value="${not empty reqParam.quantity ? reqParam.quantity : 1}" />
            <c:set var="itemPrice" value="${not empty productInfo.PRICE ? productInfo.PRICE : 0}" />
            <c:set var="totalAmount" value="${itemPrice * quantity}" />
            <c:set var="deliveryFee" value="${totalAmount >= branchInfo.FREE_SHIPPING_THRESHOLD ? 0 : branchInfo.BASE_SHIPPING_FEE}" />
            
            <div class="summary-row" style="font-weight: 600; margin-bottom: 1rem;">
                <span style="flex: 1;">${productInfo.TITLE}</span>
                <span style="color: var(--text-muted); font-weight: 400; margin-right: 1rem;">${quantity}개</span>
                <span><fmt:formatNumber value="${totalAmount}" type="number" maxFractionDigits="0"/>원</span>
            </div>
            <div class="summary-row" style="font-size: 0.9rem; margin-bottom: 0.5rem; color: var(--text-muted);">
                <span>배송비</span>
                <span>
                    <c:choose>
                        <c:when test="${deliveryFee == 0}">무료</c:when>
                        <c:otherwise><fmt:formatNumber value="${deliveryFee}" type="number" maxFractionDigits="0"/>원</c:otherwise>
                    </c:choose>
                </span>
            </div>
            <div class="total-row" style="margin-top: 1rem; padding-top: 1rem; border-top: 1.5px dashed #cbd5e1; display: flex; justify-content: space-between; align-items: center;">
                <span style="font-size: 1rem; color: var(--text);">최종 결제 금액</span>
                <span style="font-size: 1.5rem; font-weight: 800; color: var(--primary);"><fmt:formatNumber value="${totalAmount + deliveryFee}" type="number" maxFractionDigits="0"/>원</span>
            </div>
        </div>

        <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 2rem; margin-bottom: 1rem;">
            <div class="section-title" style="margin: 0;">배송 정보 입력</div>
            <c:if test="${not empty userInfoVo}">
                <button type="button" onclick="openAddressPopup()" style="padding: 0.4rem 0.8rem; font-size: 0.85rem; border: 1px solid var(--primary); color: var(--primary); background: white; border-radius: 6px; cursor: pointer;">
                    <i class="fas fa-list"></i> 배송지 목록
                </button>
            </c:if>
        </div>

        <div class="form-group">
            <label for="receiverName">받는 사람</label>
            <input type="text" id="receiverName" class="form-control" value="${not empty defaultAddress ? defaultAddress.RECIPIENT_NAME : userInfoVo.userNm}" placeholder="이름을 입력하세요">
        </div>
        <div class="form-group">
            <label for="receiverPhone">연락처</label>
            <input type="tel" id="receiverPhone" class="form-control" value="${not empty defaultAddress ? defaultAddress.RECIPIENT_PHONE : userInfoVo.cttpc}" placeholder="'-' 제외하고 입력">
        </div>
        <div class="form-group">
            <label for="zipCode">우편번호</label>
            <div class="input-group" style="display: flex; gap: 0.5rem;">
                <input type="text" id="zipCode" class="form-control" value="${defaultAddress.ZIP_CODE}" placeholder="우편번호" readonly>
                <button type="button" onclick="execDaumPostcode()" style="min-width: 100px; padding: 0.75rem; background: #64748b; color: white; border: none; border-radius: 8px; cursor: pointer; font-size: 0.9rem;">주소 검색</button>
            </div>
        </div>
        <div class="form-group">
            <label for="address1">주소</label>
            <input type="text" id="address1" class="form-control" value="${defaultAddress.ADDRESS_MAIN}" placeholder="기본 주소" readonly style="background: #f8fafc;">
        </div>
        <div class="form-group">
            <label for="address2">상세 주소</label>
            <input type="text" id="address2" class="form-control" value="${defaultAddress.ADDRESS_DETAIL}" placeholder="나머지 상세 주소를 입력하세요">
        </div>
        
        <c:if test="${not empty userInfoVo}">
            <div class="form-group" style="margin-top: 1rem;">
                <label style="display: flex; align-items: center; gap: 0.5rem; cursor: pointer; font-weight: 400; font-size: 0.95rem;">
                    <input type="checkbox" id="saveAddress" ${empty defaultAddress ? 'checked' : ''} style="width: 18px; height: 18px; accent-color: var(--primary);">
                    이 배송지를 목록에 저장
                </label>
            </div>
        </c:if>

        <div class="form-group">
            <label for="orderMemo">배송 메시지 (선택)</label>
            <select id="memoSelect" class="form-control" style="margin-bottom: 0.5rem;" onchange="updateMemo(this)">
                <option value="">직접 입력</option>
                <option value="문 앞에 놓아주세요" ${defaultAddress.MEMO == '문 앞에 놓아주세요' ? 'selected' : ''}>문 앞에 놓아주세요</option>
                <option value="경비실에 맡겨주세요" ${defaultAddress.MEMO == '경비실에 맡겨주세요' ? 'selected' : ''}>경비실에 맡겨주세요</option>
                <option value="배송 전 연락주세요" ${defaultAddress.MEMO == '배송 전 연락주세요' ? 'selected' : ''}>배송 전 연락주세요</option>
                <option value="부재 시 전화주세요" ${defaultAddress.MEMO == '부재 시 전화주세요' ? 'selected' : ''}>부재 시 전화주세요</option>
            </select>
            <textarea id="orderMemo" class="form-control" rows="2" placeholder="배송 시 요청사항이 있다면 입력해주세요">${defaultAddress.MEMO}</textarea>
        </div>

        <div class="section-title">결제 방법</div>
        <div style="padding: 1.25rem; background: #eff6ff; border-radius: var(--radius); border: 1px solid #dbeafe; margin-bottom: 2.5rem;">
            <p style="font-size: 0.9rem; color: #1e40af; display: flex; align-items: center; gap: 0.5rem;">
                <i class="fas fa-shield-alt"></i> 안전한 결제를 위해 보안 연결(SSL)을 사용합니다.
            </p>
        </div>

        <button class="btn-pay" id="payment-button" style="width: 100%; padding: 1.25rem; font-size: 1.25rem; border-radius: var(--radius);">
            총 <fmt:formatNumber value="${totalAmount + deliveryFee}" type="number" maxFractionDigits="0"/>원 결제하기
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

<!-- Address Book Modal -->
<div id="address-book-layer-bg" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%; background:rgba(0,0,0,0.5); z-index:2000; backdrop-filter:blur(4px);" onclick="closeAddressPopup()"></div>
<div id="address-book-layer" style="display:none; position:fixed; z-index:2001; background:white; border-radius:16px; overflow:hidden; box-shadow:0 20px 25px -5px rgba(0,0,0,0.1);">
    <div style="padding: 1rem; display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #eee;">
        <span style="font-weight: 600;">저장된 배송지</span>
        <i class="fas fa-times" style="cursor: pointer; padding: 0.5rem;" onclick="closeAddressPopup()"></i>
    </div>
    <div id="address-book-content" style="width:100%; height:400px; overflow-y:auto; padding: 1rem; background: #f8fafc;">
    </div>
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

    const addrBookLayer = document.getElementById('address-book-layer');
    const addrBookBg = document.getElementById('address-book-layer-bg');

    function closeAddressPopup() {
        addrBookLayer.style.display = 'none';
        addrBookBg.style.display = 'none';
    }

    async function openAddressPopup() {
        const width = Math.min(window.innerWidth - 40, 500);
        const height = Math.min(window.innerHeight - 80, 600);
        
        addrBookLayer.style.width = width + 'px';
        addrBookLayer.style.height = height + 'px';
        addrBookLayer.style.left = ((window.innerWidth - width) / 2) + 'px';
        addrBookLayer.style.top = ((window.innerHeight - height) / 2) + 'px';
        
        const contentDiv = document.getElementById('address-book-content');
        contentDiv.innerHTML = '<div style="text-align:center; padding:2rem; color:#64748b;"><i class="fas fa-spinner fa-spin"></i> 불러오는 중...</div>';
        
        addrBookLayer.style.display = 'block';
        addrBookBg.style.display = 'block';

        try {
            const res = await fetch('/front/member/addressListAjax.do');
            if (res.ok) {
                const html = await res.text();
                contentDiv.innerHTML = html;
            } else {
                contentDiv.innerHTML = '<div style="text-align:center; padding:2rem; color:#ef4444;">데이터를 불러오는데 실패했습니다.</div>';
            }
        } catch (e) {
            contentDiv.innerHTML = '<div style="text-align:center; padding:2rem; color:#ef4444;">에러가 발생했습니다.</div>';
        }
    }

    function updateMemo(select) {
        const memoText = document.getElementById('orderMemo');
        if (select.value) {
            memoText.value = select.value;
        } else {
            memoText.value = '';
            memoText.focus();
        }
    }

    function selectAddress(address) {
        document.getElementById('receiverName').value = address.recipientName;
        document.getElementById('receiverPhone').value = formatPhone(address.recipientPhone);
        document.getElementById('zipCode').value = address.zipCode;
        document.getElementById('address1').value = address.addressMain;
        document.getElementById('address2').value = address.addressDetail;
        
        const memoSelect = document.getElementById('memoSelect');
        const memoText = document.getElementById('orderMemo');
        memoText.value = address.memo || '';
        
        let found = false;
        for(let i=0; i<memoSelect.options.length; i++){
            if(memoSelect.options[i].value === (address.memo || '')){
                memoSelect.selectedIndex = i;
                found = true;
                break;
            }
        }
        if(!found) memoSelect.selectedIndex = 0;
        
        closeAddressPopup();
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
            // 0. Save Address to Address Book if requested or if it's the first one
            const saveAddressChecked = document.getElementById("saveAddress") ? document.getElementById("saveAddress").checked : false;
            const hasDefaultAddress = "${not empty defaultAddress}";
            
            if (("${not empty userInfoVo}" === "true") && (saveAddressChecked || hasDefaultAddress === "false")) {
                await fetch("/front/member/saveAddressAjax.do", {
                    method: "POST",
                    headers: { "Content-Type": "application/x-www-form-urlencoded" },
                    body: new URLSearchParams({
                        recipientName: receiverName,
                        recipientPhone: receiverPhone.replace(/-/g, ''),
                        zipCode: zipCode,
                        addressMain: address1,
                        addressDetail: address2,
                        memo: orderMemo,
                        isDefault: hasDefaultAddress === "false" ? "1" : "0",
                        addressName: "기본배송지"
                    })
                });
            }


            showLoading();
            // 1. Create Order in Backend
            const totalAmountVal = parseInt("${not empty totalAmount ? totalAmount : 0}");
            const deliveryFeeVal = parseInt("${not empty deliveryFee ? deliveryFee : 0}");

            const orderResponse = await fetch("/api/payment/order/create", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    userNo: "${userInfoVo.userNo}",
                    branchId: "${branchInfo.BRANCH_ID}",
                    totalItemAmount: totalAmountVal,
                    deliveryFee: deliveryFeeVal,
                    totalPayAmount: totalAmountVal + deliveryFeeVal,
                    receiverName: receiverName,
                    receiverPhone: receiverPhone,
                    zipCode: zipCode,
                    address1: address1,
                    address2: address2,
                    orderMemo: orderMemo,
                    items: [
                        {
                            productId: "${productInfo.PRODUCT_ID}",
                            quantity: "${not empty quantity ? quantity : 1}",
                            productName: "${productInfo.TITLE}"
                        }
                    ]
                })
            });

            const orderData = await orderResponse.json();

            if (orderData.success) {
                // hideLoading() will be naturally followed by navigation or Toss popup
                // 2. Request Payment via Toss (Direct Implementation)
                await tossPayments.requestPayment('카드', {
                    amount: totalAmountVal + deliveryFeeVal,
                    orderId: orderData.orderNo,
                    orderName: "${productInfo.TITLE}" + (parseInt("${not empty quantity ? quantity : 1}") > 1 ? " 외" : ""),
                    successUrl: window.location.origin + "/shop/success.do",
                    failUrl: window.location.origin + "/shop/fail.do",
                    customerName: "${userInfoVo.userNm}"
                });
            } else {
                hideLoading();
                alert("주문 생성에 실패했습니다: " + orderData.message);
                this.disabled = false;
                this.innerText = "결제하기";
            }
        } catch (error) {
            hideLoading();
            console.error(error);
            alert("오류가 발생했습니다.");
            this.disabled = false;
            this.innerText = "결제하기";
        }
    });
</script>


<%@ include file="/common/frontinc/shop_footer.jspf" %>

<script src="/common/front/lib/jquery-3.6.0.min.js"></script>
<script src="/common/front/js/front_common.js?v=20240316"></script>
<%@ include file="/common/frontinc/msg.jspf" %>
</body>
</html>
