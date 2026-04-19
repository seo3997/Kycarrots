<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>배송지 관리 - ${branchInfo.BRANCH_NAME}</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/common/front/css/front_common.css?v=20260419">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <style>
        .addr-card { background: white; border-radius: 16px; padding: 1.5rem; margin-bottom: 1rem; border: 1px solid #e2e8f0; position: relative; transition: border-color 0.2s; }
        .addr-card.default { border: 2px solid var(--primary); }
        .addr-card:hover { border-color: var(--primary); }
        .tag-default { background: var(--primary); color: white; padding: 0.2rem 0.6rem; border-radius: 4px; font-size: 0.75rem; font-weight: 600; vertical-align: middle; margin-left: 0.5rem; }
        .addr-actions { display: flex; gap: 0.5rem; margin-top: 1rem; padding-top: 1rem; border-top: 1px solid #f1f5f9; }
        .btn-sm { padding: 0.4rem 0.8rem; font-size: 0.85rem; border-radius: 6px; cursor: pointer; border: 1px solid #e2e8f0; background: white; color: #64748b; transition: all 0.2s; }
        .btn-sm:hover { border-color: var(--primary); color: var(--primary); }
        .btn-add { display: block; width: 100%; padding: 1rem; text-align: center; border: 2px dashed #e2e8f0; border-radius: 12px; color: #64748b; text-decoration: none; font-weight: 600; margin-bottom: 2rem; transition: all 0.2s; }
        .btn-add:hover { border-color: var(--primary); color: var(--primary); background: #eff6ff; }
        
        /* Modal Style */
        .modal { display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); z-index: 2000; align-items: center; justify-content: center; backdrop-filter: blur(4px); }
        .modal-content { background: white; padding: 2rem; border-radius: 20px; width: 90%; max-width: 500px; box-shadow: 0 20px 25px -5px rgb(0 0 0 / 0.1); }
        .modal-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.5rem; }
        .form-group { margin-bottom: 1rem; }
        .form-control { width: 100%; padding: 0.75rem; border: 1px solid #e2e8f0; border-radius: 8px; font-family: inherit; }
        .btn-primary { background: var(--primary); color: white; border: none; width: 100%; padding: 0.8rem; border-radius: 8px; font-weight: 600; cursor: pointer; }
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
        <a href="/front/logout.do">로그아웃</a>
    </nav>
</header>

<main class="container" style="max-width: 600px; padding: 2rem 1rem;">
    <h1 style="font-size: 1.5rem; font-weight: 700; margin-bottom: 1.5rem;">배송지 관리</h1>
    
    <a href="javascript:void(0)" onclick="openModal()" class="btn-add">
        <i class="fas fa-plus"></i> 새 배송지 추가
    </a>

    <c:forEach var="item" items="${addressList}">
        <c:set var="isDefaultStr">${item.IS_DEFAULT}</c:set>
        <div class="addr-card ${isDefaultStr eq 'true' or isDefaultStr eq '1' ? 'default' : ''}">
            <div style="display: flex; justify-content: space-between; align-items: flex-start;">
                <div>
                    <span style="font-weight: 700; font-size: 1.1rem;">${item.ADDRESS_NAME}</span>
                    <c:if test="${isDefaultStr eq 'true' or isDefaultStr eq '1'}"><span class="tag-default">기본</span></c:if>
                    <div style="margin-top: 0.5rem; font-weight: 500;">${item.RECIPIENT_NAME} | ${item.RECIPIENT_PHONE}</div>
                    <div style="margin-top: 0.3rem; color: #64748b; font-size: 0.95rem;">
                        [${item.ZIP_CODE}] ${item.ADDRESS_MAIN}<br>${item.ADDRESS_DETAIL}
                    </div>
                    <c:if test="${not empty item.MEMO}">
                        <div style="margin-top: 0.5rem; font-size: 0.85rem; color: var(--primary);"><i class="far fa-comment-dots"></i> ${item.MEMO}</div>
                    </c:if>
                </div>
            </div>
            <div class="addr-actions">
                <button class="btn-sm" onclick="editAddress(${item.ADDRESS_ID}, '${item.ADDRESS_NAME}', '${item.RECIPIENT_NAME}', '${item.RECIPIENT_PHONE}', '${item.ZIP_CODE}', '${item.ADDRESS_MAIN}', '${item.ADDRESS_DETAIL}', '${item.MEMO}', ${isDefaultStr eq 'true' or isDefaultStr eq '1' ? 1 : 0})">수정</button>
                <button class="btn-sm" onclick="deleteAddress(${item.ADDRESS_ID})" style="color: #ef4444;">삭제</button>
                <c:if test="${not (isDefaultStr eq 'true' or isDefaultStr eq '1')}">
                    <button class="btn-sm" onclick="setDefault(${item.ADDRESS_ID})">기본 배송지로 설정</button>
                </c:if>
            </div>
        </div>
    </c:forEach>
</main>

<!-- Address Modal -->
<div id="addrModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2 id="modalTitle" style="font-size: 1.25rem; font-weight: 700;">배송지 추가</h2>
            <i class="fas fa-times" onclick="closeModal()" style="cursor: pointer; padding: 0.5rem; font-size: 1.2rem;"></i>
        </div>
        <form id="addrForm">
            <input type="hidden" id="addressId" name="addressId">
            <div class="form-group">
                <label style="font-size: 0.85rem; color: #64748b;">배송지 별칭 (예: 집, 회사)</label>
                <input type="text" id="addressName" name="addressName" class="form-control" value="기본배송지" required>
            </div>
            <div class="form-group">
                <label style="font-size: 0.85rem; color: #64748b;">수령인</label>
                <input type="text" id="recipientName" name="recipientName" class="form-control" required>
            </div>
            <div class="form-group">
                <label style="font-size: 0.85rem; color: #64748b;">연락처</label>
                <input type="tel" id="recipientPhone" name="recipientPhone" class="form-control" required>
            </div>
            <div class="form-group">
                <label style="font-size: 0.85rem; color: #64748b;">주소</label>
                <div style="display: flex; gap: 0.5rem; margin-bottom: 0.5rem;">
                    <input type="text" id="zipCode" name="zipCode" class="form-control" placeholder="우편번호" readonly required>
                    <button type="button" onclick="execDaumPostcode()" class="btn-sm" style="min-width: 80px;">검색</button>
                </div>
                <input type="text" id="addressMain" name="addressMain" class="form-control" placeholder="기본 주소" readonly required style="margin-bottom: 0.5rem; background: #f8fafc;">
                <input type="text" id="addressDetail" name="addressDetail" class="form-control" placeholder="상세 주소">
            </div>
            <div class="form-group">
                <label style="font-size: 0.85rem; color: #64748b;">배송 요청사항</label>
                <input type="text" id="memo" name="memo" class="form-control">
            </div>
            <div class="form-group">
                <label style="display: flex; align-items: center; gap: 0.5rem; cursor: pointer; font-size: 0.9rem;">
                    <input type="checkbox" id="isDefault" name="isDefault" value="1"> 기본 배송지로 설정
                </label>
            </div>
            <button type="button" onclick="saveAddress()" class="btn-primary">저장하기</button>
        </form>
    </div>
</div>

<script>
    function openModal() {
        $('#modalTitle').text('배송지 추가');
        $('#addrForm')[0].reset();
        $('#addressId').val('');
        $('#addrModal').css('display', 'flex');
    }

    function closeModal() {
        $('#addrModal').hide();
    }

    function editAddress(id, name, recipient, phone, zip, addr1, addr2, memo, isDef) {
        $('#modalTitle').text('배송지 수정');
        $('#addressId').val(id);
        $('#addressName').val(name);
        $('#recipientName').val(recipient);
        $('#recipientPhone').val(phone);
        $('#zipCode').val(zip);
        $('#addressMain').val(addr1);
        $('#addressDetail').val(addr2);
        $('#memo').val(memo);
        $('#isDefault').prop('checked', isDef == 1);
        $('#addrModal').css('display', 'flex');
    }

    function saveAddress() {
        const formData = $('#addrForm').serialize();
        $.post('/front/member/saveAddressAjax.do', formData, function(res) {
            if (res === 'success') {
                location.reload();
            } else {
                alert('저장에 실패했습니다.');
            }
        });
    }

    function deleteAddress(id) {
        if (confirm('정말 삭제하시겠습니까?')) {
            $.post('/front/member/deleteAddressAjax.do', {addressId: id}, function(res) {
                if (res === 'success') {
                    location.reload();
                } else {
                    alert('삭제에 실패했습니다.');
                }
            });
        }
    }

    function setDefault(id) {
        $.post('/front/member/setDefaultAddressAjax.do', {addressId: id}, function(res) {
            if (res === 'success') {
                location.reload();
            } else {
                alert('설정에 실패했습니다.');
            }
        });
    }

    function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                var addr = data.userSelectedType === 'R' ? data.roadAddress : data.jibunAddress;
                document.getElementById('zipCode').value = data.zonecode;
                document.getElementById("addressMain").value = addr;
                document.getElementById("addressDetail").focus();
            }
        }).open();
    }
</script>

</body>
</html>
