<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호 재설정 - asagong</title>
    <link rel="stylesheet" href="/common/front/lib/font-awesome/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/common/front/css/front_common.css?v=20260419">
    <style>
        .reset-card {
            background: #ffffff;
            border-radius: 24px;
            padding: 2.5rem 2rem;
            box-shadow: 0 10px 25px rgba(0,0,0,0.05);
            max-width: 450px;
            margin: 2rem auto;
        }
        .success-box {
            text-align: center;
            display: none;
        }
        .success-icon {
            width: 80px;
            height: 80px;
            background: #ecfdf5;
            color: #10b981;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 2.5rem;
            margin: 0 auto 1.5rem;
        }
    </style>
</head>
<body class="bg-gray-50">
    <div id="loadingOverlay" class="loading-overlay">
        <div class="spinner"></div>
    </div>

<header class="header">
    <a href="${domainUrl}/shop/list.do" class="logo">
        <div style="width: 36px; height: 36px; background: var(--primary); border-radius: 8px; display: flex; align-items: center; justify-content: center; color: white;">
            <i class="fas fa-shopping-bag" style="font-size: 0.9rem;"></i>
        </div>
        <span>asagong</span>
    </a>
</header>

<main class="main-content">
    <div class="reset-card">
        <div id="resetFormSection">
            <div class="login-header text-center">
                <h1 style="font-size: 1.75rem; font-weight: 700;">비밀번호 재설정</h1>
                <p class="text-muted mt-2">새로운 비밀번호를 입력해 주세요.</p>
            </div>

            <form id="resetPwForm" class="mt-4">
                <input type="hidden" id="uid" name="uid" value="<%=request.getParameter("uid")!=null?request.getParameter("uid"):""%>">
                <input type="hidden" id="sel" name="sel" value="<%=request.getParameter("sel")!=null?request.getParameter("sel"):""%>">
                <input type="hidden" id="ver" name="ver" value="<%=request.getParameter("ver")!=null?request.getParameter("ver"):""%>">

                <div class="form-group">
                    <label for="new_pw">새 비밀번호</label>
                    <input type="password" id="new_pw" name="new_pw" class="form-control" placeholder="8~20자리 영문, 숫자, 특수문자 조합" required>
                </div>
                <div class="form-group">
                    <label for="confirm_pw">비밀번호 확인</label>
                    <input type="password" id="confirm_pw" name="confirm_pw" class="form-control" placeholder="다시 한번 입력해 주세요" required>
                </div>

                <button type="submit" class="btn-login" style="width:100%; height:50px; font-size:1.1rem; margin-top:1rem;">비밀번호 변경하기</button>
            </form>
        </div>

        <div id="successSection" class="success-box">
            <div class="success-icon">
                <i class="fas fa-check"></i>
            </div>
            <h2 style="font-size: 1.5rem; font-weight: 700; color: #1e293b;">변경 완료!</h2>
            <p class="text-muted mt-3 mb-4">비밀번호가 성공적으로 변경되었습니다.<br>새로운 비밀번호로 로그인해 주세요.</p>
            <a href="${domainUrl}/front/login.do" class="btn-login" style="display: block; width: 100%; height: 50px; line-height: 50px; font-size: 1.1rem;">로그인하러 가기</a>
        </div>
    </div>
</main>

<script src="/common/front/lib/jquery-3.6.0.min.js"></script>
<script>
    $(function() {
        const uid = $('#uid').val();
        const sel = $('#sel').val();
        const ver = $('#ver').val();

        if(!uid || !sel || !ver) {
            alert('유효하지 않은 접근입니다. 메일의 링크를 다시 확인해 주세요.');
            location.href = '/front/login.do';
            return;
        }

        $('#resetPwForm').on('submit', function(e) {
            e.preventDefault();
            const pw = $('#new_pw').val();
            const pwChk = $('#confirm_pw').val();

            if(pw !== pwChk) {
                alert('비밀번호가 일치하지 않습니다.');
                return;
            }

            if(pw.length < 8) {
                alert('비밀번호는 8자리 이상이어야 합니다.');
                return;
            }

            $('#loadingOverlay').show();

            $.ajax({
                type: "POST",
                url: "/api/members/reset/change.do?uid=" + encodeURIComponent(uid)
                   + "&sel=" + encodeURIComponent(sel)
                   + "&ver=" + encodeURIComponent(ver),
                data: JSON.stringify({ newPassword: pw, confirmPassword: pwChk }),
                contentType: "application/json",
                dataType: "json",
                success: function(res) {
                    $('#loadingOverlay').hide();
                    const code = (res && res.resultString ? (""+res.resultString).trim() : "0");
                    if(code === "200") {
                        $('#resetFormSection').hide();
                        $('#successSection').fadeIn();
                    } else if(code === "604") {
                        alert("링크가 만료되었거나 유효하지 않습니다.");
                    } else {
                        alert("처리 중 오류가 발생했습니다.");
                    }
                },
                error: function() {
                    $('#loadingOverlay').hide();
                    alert("서버 통신 오류가 발생했습니다.");
                }
            });
        });
    });
</script>
</body>
</html>
