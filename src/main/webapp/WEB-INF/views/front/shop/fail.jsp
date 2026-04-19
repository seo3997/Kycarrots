<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>결제 실패 - ${branchInfo.BRANCH_NAME}</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/common/front/css/front_common.css">
    <style>
        :root {
            --primary: #2563eb;
            --bg: #f8fafc;
        }
        body { font-family: 'Outfit', sans-serif; background-color: var(--bg); color: #1e293b; padding: 4rem 1rem; margin: 0; }
        .fail-card { background: white; width: 100%; max-width: 500px; margin: 0 auto; padding: 4rem 2rem; border-radius: 32px; box-shadow: 0 20px 40px rgba(0,0,0,0.06); box-sizing: border-box; text-align: center; }
        .icon { font-size: 4.5rem; color: #ef4444; margin-bottom: 2rem; }
        h1 { font-size: 1.75rem; font-weight: 700; margin-bottom: 1.25rem; line-height: 1.3; }
        p { color: #64748b; margin-bottom: 2.5rem; line-height: 1.6; }
        .error-info { background: #fef2f2; color: #991b1b; padding: 1.5rem; border-radius: 20px; font-size: 0.9rem; margin-bottom: 2.5rem; border: 1px solid #fee2e2; text-align: left; }
        .error-info b { display: block; margin-bottom: 0.5rem; color: #b91c1c; }
        .btn-home { display: inline-block; min-width: 200px; padding: 1rem 2.5rem; background: #64748b; color: white; text-decoration: none; border-radius: 16px; font-weight: 700; font-size: 1.05rem; transition: all 0.2s; }
        .btn-home:hover { transform: translateY(-2px); filter: brightness(1.1); }
        
        @media (max-width: 480px) {
            body { padding: 2rem 1rem; }
            .fail-card { padding: 3rem 1.5rem; border-radius: 24px; }
            .btn-home { width: 100%; box-sizing: border-box; }
            .icon { font-size: 3.5rem; }
            h1 { font-size: 1.5rem; }
        }
    </style>
</head>
<body>
<%@ include file="/common/frontinc/shop_header.jspf" %>

<main class="container" style="padding: 4rem 1rem;">
    <div class="fail-card">
        <i class="fas fa-times-circle icon"></i>
        <h1>결제에 실패했습니다.</h1>
        <p id="error-message">고객센터로 문의 바랍니다.</p>
        
        <div class="error-info">
            <b>결제 실패 원인</b>
            에러 코드: <span id="error-code"></span><br>
            상세 메시지: <span id="error-detail"></span>
        </div>

        <a href="/shop/list.do" class="btn-home">홈으로 가기</a>
        <a href="javascript:history.back();" style="display: block; margin-top: 1.5rem; color: #64748b; text-decoration: none;">다시 시도하기</a>
    </div>
</main>

<script>
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get('code');
    const message = urlParams.get('message');
    
    if (code) document.getElementById('error-code').innerText = code;
    if (message) {
        document.getElementById('error-message').innerText = message;
        document.getElementById('error-detail').innerText = message;
    }
</script>

</body>
</html>
