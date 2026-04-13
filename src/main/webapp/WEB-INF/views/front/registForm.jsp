<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/inc/common.jspf" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입 - asagong</title>
    <link rel="stylesheet" href="/common/front/lib/font-awesome/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/common/front/css/front_common.css?v=20240316">
</head>
<body>
    <div id="loadingOverlay" class="loading-overlay" style="display: flex;">
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
    <div class="regist-card">
        <div class="regist-header text-center">
            <div class="logo"><i class="fas fa-user-plus"></i></div>
            <h1 style="font-size: 1.75rem; font-weight: 700;">asagong 회원가입</h1>
            <p class="text-muted mt-2">필요한 정보를 입력하여 계정을 만들어주세요.</p>
        </div>
        
        <form id="aform" method="post">
            <input type="hidden" id="idcheck" name="idcheck" value="N"/>
            
            <div class="form-group">
                <label for="user_id">아이디 (이메일)</label>
                <div class="input-group">
                    <input type="email" name="user_id" id="user_id" class="form-control" placeholder="example@email.com" required maxlength="50">
                    <button type="button" class="btn-check" id="btn_idcheck" style="height: auto; min-width: 80px;">중복확인</button>
                </div>
                <div id="check-alert" class="alert-box" style="display:none; margin-top:0.5rem;"></div>
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
                <label for="user_nm">이름</label>
                <input type="text" name="user_nm" id="user_nm" class="form-control" placeholder="이름을 입력하세요" required maxlength="50">
            </div>

            <div class="form-group">
                <label for="cttpc">전화번호</label>
                <input type="tel" name="cttpc" id="cttpc" class="form-control" placeholder="010-0000-0000" pattern="[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}" required maxlength="20">
            </div>

            <input type="hidden" id="branch_id" name="branch_id" value="${branchInfo.BRANCH_ID}">

            <div id="regist-alert" class="alert-box" style="display:none; margin-top:1rem;"></div>

            <button type="button" class="btn-regist" id="btn_save" style="width:100%; height:50px; font-size:1.1rem; margin-top:1rem;">가입하기</button>
        </form>

        <div class="regist-footer text-center">
            이미 회원이신가요? <a href="/front/login.do">로그인</a>
        </div>
    </div>
</main>

<script src="/common/front/lib/jquery-3.6.0.min.js"></script>
<script src="/common/front/js/front_common.js?v=20240316"></script>
<script type="text/javascript" src="/common/js/common.js?version=6.6"></script>
<script>
    $(document).ready(function() {
        const branchIdLoad = parseInt("${branchInfo.BRANCH_ID}");
        if(isNaN(branchIdLoad) || branchIdLoad <= 2){
            alert("판매지점이 선택되지 않았습니다. 도메인을 확인하세요.");
            location.href = "/";
            return;
        }
    });

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

        const userNm = $("#user_nm").val();
        if(userNm == ""){
            alert("이름을 입력해주세요.");
            $("#user_nm").focus();
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

        const branchId = parseInt("${branchInfo.BRANCH_ID}");
        if(isNaN(branchId) || branchId <= 2){
            alert("판매지점이 선택되지 않았습니다. 도메인을 확인하세요.");
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

    // 전화번호 자동 하이픈 추가
    $('#cttpc').on('input', function() {
        let val = $(this).val().replace(/[^0-9]/g, '');
        if (val.length > 3 && val.length <= 7) {
            val = val.substring(0, 3) + '-' + val.substring(3);
        } else if (val.length > 7) {
            // 중간 번호가 3자리인지 4자리인지에 따라 하이픈 위치 조정
            if (val.length === 10) {
                 val = val.substring(0, 3) + '-' + val.substring(3, 6) + '-' + val.substring(6, 10);
            } else {
                 val = val.substring(0, 3) + '-' + val.substring(3, 7) + '-' + val.substring(7, 11);
            }
        }
        $(this).val(val);
    });

    $('#btn_idcheck').on('click', fnCheckId);
    $('#btn_save').on('click', fnGoInsert);
</script>

<%@ include file="/common/frontinc/msg.jspf" %>
</body>
</html>
