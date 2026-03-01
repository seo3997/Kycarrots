<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>지점을 찾을 수 없습니다</title>
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@400;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Outfit', sans-serif; height: 100vh; display: flex; align-items: center; justify-content: center; background: #f1f5f9; color: #1e293b; text-align: center; }
        .card { background: white; padding: 3rem; border-radius: 20px; box-shadow: 0 10px 25px -5px rgba(0,0,0,0.1); max-width: 500px; }
        h1 { font-size: 2rem; margin-bottom: 1rem; color: #ef4444; }
        p { color: #64748b; margin-bottom: 2rem; }
        .btn { display: inline-block; padding: 0.75rem 1.5rem; background: #3b82f6; color: white; text-decoration: none; border-radius: 10px; font-weight: 600; }
    </style>
</head>
<body>
    <div class="card">
        <h1>Access Denied</h1>
        <p>요청하신 도메인에 연결된 지점 정보가 없거나, 현재 운영 중이 아닙니다.<br>관리자에게 문의해 주세요.</p>
        <a href="/" class="btn">홈으로 돌아가기</a>
    </div>
</body>
</html>
