<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인 - Kycarrots</title>
    <link rel="stylesheet" href="/common/front/lib/font-awesome/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/common/front/css/front_common.css">
</head>
<body>

<header class="header">
    <a href="/shop/list.do" class="logo">
        <c:choose>
            <c:when test="${not empty branchInfo.LOGO_IMAGE_URL}">
                <img src="${branchInfo.LOGO_IMAGE_URL}" alt="Logo" style="height: 40px; border-radius: 8px;">
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

<div class="main-content">
<div class="login-card">
    <div class="login-header">
        <div class="logo"><i class="fas fa-shopping-bag"></i></div>
        <h1>Kycarrots 로그인</h1>
    </div>
    
    <form id="loginForm">
        <div class="form-group">
            <label for="user_id">아이디</label>
            <input type="text" id="user_id" name="user_id" class="form-control" placeholder="아이디를 입력하세요" required>
        </div>
        <div class="form-group">
            <label for="user_pw">비밀번호</label>
            <input type="password" id="user_pw" name="user_pw" class="form-control" placeholder="비밀번호를 입력하세요" required>
        </div>
        
        <div id="error-box" class="error-msg"></div>

        <button type="submit" class="btn-login">로그인</button>
    </form>

    <div class="login-footer">
        아직 회원이 아니신가요? <a href="/front/registForm.do">회원가입</a>
    </div>
</div>
</div>

<script src="/common/front/lib/jquery-3.6.0.min.js"></script>
<script src="/common/front/js/front_common.js"></script>
<script>
    $('#loginForm').on('submit', function(e) {
        e.preventDefault();
        const userId = $('#user_id').val();
        const userPw = $('#user_pw').val();
        const errorBox = $('#error-box');

        $.ajax({
            url: '/front/loginAjax.do',
            type: 'POST',
            data: {
                user_id: userId,
                user_pw: userPw
            },
            success: function(response) {
                const res = JSON.parse(response);
                if (res.resultStats.resultCode === 'ok') {
                    location.href = '/shop/list.do';
                } else {
                    errorBox.text(res.resultStats.resultMsg).show();
                }
            },
            error: function() {
                errorBox.text('로그인 처리 중 오류가 발생했습니다.').show();
            }
        });
    });
</script>

</body>
</html>
