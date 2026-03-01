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
    <style>
        :root {
            --primary: #2563eb;
            --bg: #f8fafc;
        }
        body { font-family: 'Outfit', sans-serif; background-color: var(--bg); text-align: center; padding: 4rem 2rem; }
        .fail-card { background: white; max-width: 500px; margin: 0 auto; padding: 3rem; border-radius: 24px; box-shadow: 0 10px 25px rgba(0,0,0,0.05); }
        .icon { font-size: 4rem; color: #ef4444; margin-bottom: 1.5rem; }
        h1 { font-size: 1.75rem; margin-bottom: 1rem; }
        p { color: #64748b; margin-bottom: 2rem; }
        .btn-home { display: inline-block; padding: 1rem 2rem; background: var(--primary); color: white; text-decoration: none; border-radius: 12px; font-weight: 600; }
    </style>
</head>
<body>

<div class="fail-card">
    <i class="fas fa-times-circle icon"></i>
    <h1>결제에 실패했습니다.</h1>
    <p id="error-message">고객센터로 문의 바랍니다.</p>
    
    <div style="background: #fff1f2; color: #e11d48; padding: 1rem; border-radius: 8px; font-size: 0.875rem; margin-bottom: 2rem;">
        에러 코드: <span id="error-code"></span><br>
        상세 원인: <span id="error-detail"></span>
    </div>

    <a href="/shop/list.do" class="btn-home">홈으로 가기</a>
    <a href="javascript:history.back();" style="display: block; margin-top: 1rem; color: #64748b; text-decoration: none;">다시 시도하기</a>
</div>

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
