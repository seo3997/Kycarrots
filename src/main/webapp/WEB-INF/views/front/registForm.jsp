<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/inc/common.jspf" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입 - Kycarrots</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;600;700&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary: #2563eb;
            --primary-hover: #1d4ed8;
            --bg: #f8fafc;
            --card-bg: #ffffff;
            --text: #1e293b;
            --text-muted: #64748b;
            --radius: 12px;
            --shadow: 0 4px 6px -1px rgb(0 0 0 / 0.1);
        }

        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Outfit', sans-serif; background-color: var(--bg); color: var(--text); }

        .header { background: white; padding: 1rem 2rem; display: flex; justify-content: space-between; align-items: center; box-shadow: 0 1px 2px 0 rgb(0 0 0 / 0.05); }
        .logo { display: flex; align-items: center; gap: 0.75rem; text-decoration: none; color: var(--text); font-weight: 700; font-size: 1.25rem; }

        .main-content {
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: calc(100vh - 80px);
            padding: 2rem;
        }

        .regist-card {
            background: var(--card-bg);
            padding: 2.5rem;
            border-radius: 24px;
            box-shadow: var(--shadow);
            width: 100%;
            max-width: 450px;
        }

        .regist-header {
            text-align: center;
            margin-bottom: 2rem;
        }

        .logo {
            width: 60px;
            height: 60px;
            background: var(--primary);
            border-radius: 16px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 1.5rem;
            margin: 0 auto 1rem;
        }

        .regist-header h1 {
            font-size: 1.5rem;
            font-weight: 700;
            color: #1e3a8a;
        }

        .form-group {
            margin-bottom: 1.25rem;
        }

        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 500;
            font-size: 0.875rem;
        }

        .input-group {
            display: flex;
            gap: 0.5rem;
        }

        .form-control {
            width: 100%;
            padding: 0.75rem 1rem;
            border: 1px solid #e2e8f0;
            border-radius: var(--radius);
            font-size: 1rem;
            transition: border-color 0.2s, box-shadow 0.2s;
        }

        .form-control:focus {
            outline: none;
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.1);
        }

        .btn-check {
            padding: 0.75rem 1rem;
            background: #f1f5f9;
            color: var(--text);
            border: 1px solid #e2e8f0;
            border-radius: var(--radius);
            font-weight: 600;
            font-size: 0.875rem;
            cursor: pointer;
            white-space: nowrap;
            transition: all 0.2s;
        }

        .btn-check:hover {
            background: #e2e8f0;
        }

        .btn-regist {
            width: 100%;
            padding: 0.875rem;
            background: var(--primary);
            color: white;
            border: none;
            border-radius: var(--radius);
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.2s;
            margin-top: 1rem;
        }

        .btn-regist:hover {
            background: var(--primary-hover);
        }

        .regist-footer {
            margin-top: 1.5rem;
            text-align: center;
            font-size: 0.875rem;
            color: var(--text-muted);
        }

        .regist-footer a {
            color: var(--primary);
            text-decoration: none;
            font-weight: 600;
        }

        .alert-box {
            font-size: 0.875rem;
            margin-top: 0.5rem;
            padding: 0.75rem;
            border-radius: 8px;
            display: none;
        }
        .alert-error { background: #fef2f2; color: #ef4444; border: 1px solid #fee2e2; }
        .alert-success { background: #f0fdf4; color: #22c55e; border: 1px solid #dcfce7; }
    </style>
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
<div class="regist-card">
    <div class="regist-header">
        <div class="logo"><i class="fas fa-user-plus"></i></div>
        <h1>Kycarrots 회원가입</h1>
    </div>
    
    <form id="aform" method="post">
        <input type="hidden" id="idcheck" name="idcheck" value="N"/>
        
        <div class="form-group">
            <label for="user_id">아이디 (이메일)</label>
            <div class="input-group">
                <input type="email" name="user_id" id="user_id" class="form-control" placeholder="example@email.com" required maxlength="50">
                <button type="button" class="btn-check" id="btn_idcheck">중복확인</button>
            </div>
            <div id="check-alert" class="alert-box"></div>
        </div>

        <div class="form-group">
            <label for="password">비밀번호</label>
            <input type="password" name="password" id="password" class="form-control" placeholder="비밀번호를 입력하세요" required maxlength="50">
        </div>

        <div class="form-group">
            <label for="password_con">비밀번호 확인</label>
            <input type="password" name="password_con" id="password_con" class="form-control" placeholder="비밀번호를 한번 더 입력하세요" required maxlength="50">
        </div>

        <div class="form-group">
            <label for="cttpc">전화번호</label>
            <input type="tel" name="cttpc" id="cttpc" class="form-control" placeholder="010-0000-0000" pattern="[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}" required maxlength="20">
        </div>

        <div id="regist-alert" class="alert-box"></div>

        <button type="button" class="btn-regist" id="btn_save">가입하기</button>
    </form>

    <div class="regist-footer">
        이미 회원이신가요? <a href="/front/login.do">로그인</a>
    </div>
</div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="/common/js/common.js?version=6.6"></script>
<script>
    function fnCheckId(){
        const userId = $('#user_id').val();
        const alertBox = $('#check-alert');
        
        if(userId == ""){
            alert("아이디를 입력해주세요.");
            return;
        }

        const sUrl = "/front/memberIdCheckAjax.do";
        const sParam = {'user_id' : userId};
        const sType = "post";

        fnCallbackAjax(sUrl, sParam, sType, function(pResponse){
            if(pResponse.resultStats.resultCode == "ok"){
                if(pResponse.resultMap == 'Y'){
                    alertBox.removeClass('alert-success').addClass('alert-error').text(pResponse.resultStats.resultMsg).show();
                    $("#idcheck").val("N");
                } else {
                    alertBox.removeClass('alert-error').addClass('alert-success').text("사용 가능한 아이디입니다.").show();
                    $("#idcheck").val("Y");
                }
            }
        });
    }

    function fnGoInsert(){
        const userId = $("#user_id").val();
        const password = $("#password").val();
        const passwordCon = $("#password_con").val();
        const idcheck = $("#idcheck").val();
        const alertBox = $('#regist-alert');

        if(userId == ""){
            alert("아이디를 입력해주세요.");
            $("#user_id").focus();
            return;
        }

        const regex = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/; 
        if(regex.test(userId) === false) {  
            alert("이메일 양식을 확인해주세요.");
            return;  
        }

        if(idcheck == "N"){
            alert("아이디 중복확인을 해주세요.");
            return;
        }

        if(password == ""){
            alert("비밀번호를 입력해주세요.");
            $("#password").focus();
            return;
        }

        if(password != passwordCon){
            alert("비밀번호가 일치하지 않습니다.");
            return;
        }

        const cttpc = $("#cttpc").val();
        if(cttpc == ""){
            alert("전화번호를 입력해주세요.");
            $("#cttpc").focus();
            return;
        }

        const phoneRegex = /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$/;
        if(phoneRegex.test(cttpc) === false) {
            alert("올바른 전화번호 형식이 아닙니다. (예: 010-0000-0000)");
            $("#cttpc").focus();
            return;
        }

        if(confirm("회원가입을 진행하시겠습니까?")){
            const sUrl = "/front/memberSaveAjax.do";
            const fForm = $("#aform");
            
            fnCallbackFormAjax(sUrl, fForm, function(pResponse){
                if(pResponse.resultStats.resultCode == "ok"){
                    if(pResponse.resultMap == 'Y'){
                        alert("회원가입이 완료되었습니다.");
                        location.href = "/front/login.do";
                    } else {
                        alertBox.addClass('alert-error').text("처리 중 오류가 발생했습니다. 다시 시도해주세요.").show();
                    }
                }
            });
        }
    }

    $('#btn_idcheck').on('click', fnCheckId);
    $('#btn_save').on('click', fnGoInsert);
</script>

</body>
</html>
