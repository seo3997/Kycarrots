<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/inc/common.jspf" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입 - Kycarrots</title>
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
            <label for="user_nm">이름</label>
            <input type="text" name="user_nm" id="user_nm" class="form-control" placeholder="이름을 입력하세요" required maxlength="50">
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

<script src="/common/front/lib/jquery-3.6.0.min.js"></script>
<script src="/common/front/js/front_common.js"></script>
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

</body>
</html>
