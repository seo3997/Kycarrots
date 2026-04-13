<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>아이디/비밀번호 찾기 - asagong</title>
    <link rel="stylesheet" href="/common/front/lib/font-awesome/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/common/front/css/front_common.css?v=20240316">
    <style>
        .tabs-container {
            display: flex;
            margin-bottom: 2rem;
            border-bottom: 1px solid #e2e8f0;
        }
        .tab-item {
            flex: 1;
            text-align: center;
            padding: 1rem;
            cursor: pointer;
            font-weight: 600;
            color: #64748b;
            transition: all 0.3s ease;
            position: relative;
        }
        .tab-item.active {
            color: var(--primary);
        }
        .tab-item.active::after {
            content: '';
            position: absolute;
            bottom: -1px;
            left: 0;
            width: 100%;
            height: 2px;
            background: var(--primary);
        }
        .tab-content {
            display: none;
        }
        .tab-content.active {
            display: block;
        }
        .phone-input-group {
            display: flex;
            gap: 8px;
            align-items: center;
        }
        .phone-input-group input {
            text-align: center;
        }
        .result-box {
            background: #f8fafc;
            border-radius: 12px;
            padding: 1.5rem;
            margin-top: 1.5rem;
            text-align: center;
            display: none;
        }
        .result-id {
            font-size: 1.5rem;
            font-weight: 700;
            color: var(--primary);
            margin: 0.5rem 0;
        }
    </style>
</head>
<body class="bg-gray-50">
    <div id="loadingOverlay" class="loading-overlay">
        <div class="spinner"></div>
    </div>

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

<main class="main-content">
    <div class="login-card">
        <div class="login-header text-center">
            <h1 style="font-size: 1.75rem; font-weight: 700;">정보 찾기</h1>
            <p class="text-muted mt-2">아이디 또는 비밀번호를 찾으실 수 있습니다.</p>
        </div>

        <div class="tabs-container">
            <div class="tab-item active" data-tab="find-id">아이디 찾기</div>
            <div class="tab-item" data-tab="find-pw">비밀번호 찾기</div>
        </div>
        
        <!-- 아이디 찾기 -->
        <div id="find-id" class="tab-content active">
            <form id="findIdForm">
                <div class="form-group">
                    <label for="member_name">이름</label>
                    <input type="text" id="member_name" name="member_name" class="form-control" placeholder="가입 시 이름을 입력하세요" required>
                </div>
                <div class="form-group">
                    <label>휴대폰 번호</label>
                    <div class="phone-input-group">
                        <input type="tel" id="phone1" class="form-control" maxlength="3" value="010" readonly style="background-color: #f1f5f9; cursor: not-allowed; color: #64748b;">
                        <span>-</span>
                        <input type="tel" id="phone2" class="form-control" maxlength="4" inputmode="numeric" pattern="[0-9]*" placeholder="1234" required>
                        <span>-</span>
                        <input type="tel" id="phone3" class="form-control" maxlength="4" inputmode="numeric" pattern="[0-9]*" placeholder="5678" required>
                    </div>
                    <input type="hidden" id="member_phone" name="member_phone">
                </div>
                <button type="submit" class="btn-login" style="width:100%; height:50px; font-size:1.1rem; margin-top:1rem;">아이디 찾기</button>
            </form>
            
            <div id="id-result-box" class="result-box">
                <p>회원님의 아이디를 찾았습니다.</p>
                <div class="result-id" id="found-id"></div>
                <a href="/front/login.do" class="btn-login" style="display: inline-block; width: auto; padding: 0.5rem 2rem; margin-top: 1rem;">로그인하러 가기</a>
            </div>
        </div>

        <!-- 비밀번호 찾기 -->
        <div id="find-pw" class="tab-content">
            <form id="findPwForm">
                <p class="text-sm text-muted mb-4">가입 시 등록한 이메일을 입력하시면 비밀번호 재설정 링크를 보내드립니다.</p>
                <div class="form-group">
                    <label for="member_email">이메일</label>
                    <input type="email" id="member_email" name="member_email" class="form-control" placeholder="example@email.com" required>
                </div>
                <button type="submit" class="btn-login" style="width:100%; height:50px; font-size:1.1rem; margin-top:1rem;">재설정 메일 발송</button>
            </form>
            
            <div id="pw-result-box" class="result-box">
                <p style="color: #059669; font-weight: 600;">메일이 발송되었습니다!</p>
                <p class="text-sm mt-2">입력하신 이메일의 편지함을 확인해 주세요.<br>(스팸함도 확인 부탁드립니다.)</p>
            </div>
        </div>

        <div class="login-footer text-center" style="margin-top: 2rem; border-top: 1px solid #f1f5f9; padding-top: 1.5rem;">
            계정이 생각나셨나요? <a href="/front/login.do">로그인</a>
        </div>
    </div>
</main>

<script src="/common/front/lib/jquery-3.6.0.min.js"></script>
<script src="/common/front/js/front_common.js?v=20240316"></script>
<script>
    // Tab switching logic
    $('.tab-item').on('click', function() {
        const tabId = $(this).data('tab');
        $('.tab-item').removeClass('active');
        $(this).addClass('active');
        $('.tab-content').removeClass('active');
        $('#' + tabId).addClass('active');
        $('.result-box').hide();
    });

    // Auto-focus logic for phone inputs
    $('#phone1, #phone2').on('input', function() {
        if (this.value.length >= this.maxLength) {
            $(this).nextAll('input:first').focus();
        }
    });

    // Find ID submit
    $('#findIdForm').on('submit', function(e) {
        e.preventDefault();
        const phone = $('#phone1').val() + '-' + $('#phone2').val() + '-' + $('#phone3').val();
        $('#member_phone').val(phone);
        
        $('#loadingOverlay').show();
        
        $.ajax({
            url: '/mgt/member/findIdAjax.do',
            type: 'POST',
            data: $(this).serialize(),
            success: function(response) {
                $('#loadingOverlay').hide();
                const res = JSON.parse(response);
                if (res.resultStats.resultCode === 'ok') {
                    if (res.resultStats.user_id_exist) {
                        $('#found-id').text(res.resultStats.user_id_res);
                        $('#id-result-box').fadeIn();
                        $('#findIdForm').hide();
                    } else {
                        alert('일치하는 회원 정보가 없습니다.');
                    }
                }
            },
            error: function() {
                $('#loadingOverlay').hide();
                alert('처리 중 오류가 발생했습니다.');
            }
        });
    });

    // Find PW submit
    $('#findPwForm').on('submit', function(e) {
        e.preventDefault();
        const email = $('#member_email').val();
        
        $('#loadingOverlay').show();
        
        $.ajax({
            url: '/api/members/find-password',
            type: 'GET',
            data: { mail: email },
            success: function(res) {
                $('#loadingOverlay').hide();
                const code = (res && res.resultString ? (""+res.resultString).trim() : "0");
                if (code === "200") {
                    $('#pw-result-box').fadeIn();
                    $('#findPwForm').hide();
                } else if (code === "601") {
                    alert('일치하는 사용자 정보가 없습니다.');
                } else {
                    alert('메일 발송 중 오류가 발생했습니다.');
                }
            },
            error: function() {
                $('#loadingOverlay').hide();
                alert('서버 통신 중 오류가 발생했습니다.');
            }
        });
    });
</script>

<%@ include file="/common/frontinc/msg.jspf" %>
</body>
</html>
