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
    <style>
        .header {
            background: rgba(255, 255, 255, 0.8);
            backdrop-filter: blur(12px);
            padding: 1rem 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            position: sticky;
            top: 0;
            z-index: 1000;
            box-shadow: 0 1px 2px 0 rgb(0 0 0 / 0.05);
        }

        .header .logo {
            display: flex;
            align-items: center;
            gap: 0.75rem;
            text-decoration: none;
            color: var(--text);
            font-weight: 700;
            font-size: 1.25rem;
            white-space: nowrap;
        }

        .header .logo img {
            height: 40px;
            border-radius: 8px;
        }

        .nav-links {
            display: flex;
            gap: 1.5rem;
            align-items: center;
            flex-shrink: 0;
        }

        .nav-links a {
            text-decoration: none;
            color: var(--text);
            font-weight: 500;
            font-size: 1rem;
            transition: color 0.2s;
            white-space: nowrap;
        }

        .nav-links a:hover { color: var(--primary); }

        .nav-links .btn-login {
            background: var(--primary);
            color: white !important;
            padding: 0.5rem 1.25rem;
            border-radius: var(--radius);
            white-space: nowrap;
            display: inline-flex;
            align-items: center;
            justify-content: center;
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

<main class="main-content">
    <div class="login-card">
        <div class="login-header text-center">
            <div class="logo"><i class="fas fa-sign-in-alt"></i></div>
            <h1 style="font-size: 1.75rem; font-weight: 700;">Kycarrots 로그인</h1>
            <p class="text-muted mt-2">서비스 이용을 위해 로그인해 주세요.</p>
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
            
            <div id="error-box" class="error-msg" style="display:none;"></div>

            <button type="submit" class="btn-login" style="width:100%; height:50px; font-size:1.1rem; margin-top:1rem;">로그인</button>
        </form>

        <div class="login-footer text-center">
            아직 회원이 아니신가요? <a href="/front/registForm.do">회원가입</a>
        </div>
    </div>
</main>

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
