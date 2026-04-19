<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/inc/common.jspf" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>정보수정 - ${branchInfo.BRANCH_NAME}</title>
    <link rel="stylesheet" href="/common/front/lib/font-awesome/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/common/front/css/front_common.css?v=20260419">
    <style>
        .profile-card {
            background: white;
            padding: 2.5rem;
            border-radius: 24px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.05);
            max-width: 500px;
            margin: 4rem auto;
        }
        .form-label { font-weight: 600; color: var(--text); margin-bottom: 0.5rem; display: block; }
        .readonly-field { background-color: #f8fafc; color: #64748b; cursor: not-allowed; }
    </style>
</head>
<body>
    <div id="loadingOverlay" class="loading-overlay" style="display: none;">
        <div class="spinner"></div>
    </div>

<%@ include file="/common/frontinc/shop_header.jspf" %>

<main class="container">
    <div class="profile-card">
        <div style="text-align: center; margin-bottom: 2.5rem;">
            <div style="width: 64px; height: 64px; background: var(--primary); border-radius: 20px; display: flex; align-items: center; justify-content: center; color: white; margin: 0 auto 1rem; font-size: 1.5rem;">
                <i class="fas fa-user-edit"></i>
            </div>
            <h1 style="font-size: 1.75rem; font-weight: 700;">회원 정보 수정</h1>
            <p style="color: #64748b;">회원님의 소중한 정보를 안전하게 관리하세요.</p>
        </div>

        <form id="profileForm">
            <div class="form-group" style="margin-bottom: 1.5rem;">
                <label class="form-label">아이디 (이메일)</label>
                <input type="text" class="form-control readonly-field" value="${userInfoVo.id}" readonly>
                <p style="font-size: 0.8rem; color: #94a3b8; mt-1;">아이디는 수정할 수 없습니다.</p>
            </div>

            <div class="form-group" style="margin-bottom: 1.5rem;">
                <label for="user_nm" class="form-label">이름</label>
                <input type="text" name="user_nm" id="user_nm" class="form-control" value="${userInfoVo.userNm}" placeholder="이름을 입력하세요" required>
            </div>

            <div class="form-group" style="margin-bottom: 2rem;">
                <label for="cttpc" class="form-label">연락처</label>
                <input type="tel" name="cttpc" id="cttpc" class="form-control" value="${userInfoVo.cttpc}" placeholder="010-0000-0000" required>
            </div>

            <div style="margin-top: 3rem; margin-bottom: 2rem; padding-top: 2rem; border-top: 1px solid #f1f5f9;">
                <h3 style="font-size: 1.1rem; font-weight: 700; margin-bottom: 1.5rem; color: var(--text);">비밀번호 수정 <span style="font-size: 0.8rem; font-weight: 400; color: #94a3b8; margin-left: 0.5rem;">(변경 시에만 입력)</span></h3>
                
                <div class="form-group" style="margin-bottom: 1.5rem;">
                    <label for="password" class="form-label">새 비밀번호</label>
                    <input type="password" name="password" id="password" class="form-control" placeholder="새 비밀번호를 입력하세요">
                </div>

                <div class="form-group">
                    <label for="password_con" class="form-label">새 비밀번호 확인</label>
                    <input type="password" name="password_con" id="password_con" class="form-control" placeholder="비밀번호를 한번 더 입력하세요">
                </div>
            </div>

            <button type="button" id="btn_update" class="btn-pay" style="width: 100%; padding: 1rem; font-size: 1.1rem; border-radius: 12px; font-weight: 600;">
                저장하기
            </button>
            
            <div style="text-align: center; margin-top: 1.5rem;">
                <a href="javascript:history.back();" style="color: #64748b; text-decoration: none; font-size: 0.9rem;">취소하고 돌아가기</a>
            </div>
        </form>
    </div>
</main>

<%@ include file="/common/frontinc/shop_footer.jspf" %>

<script src="/common/front/lib/jquery-3.6.0.min.js"></script>
<script src="/common/front/js/front_common.js?v=20240316"></script>
<script type="text/javascript" src="/common/js/common.js?version=6.6"></script>

<script>
    $(document).ready(function() {
        // 전화번호 자동 하이픈 추가
        $('#cttpc').on('input', function() {
            let val = $(this).val().replace(/[^0-9]/g, '').slice(0, 11);
            if (val.length < 4) return $(this).val(val);
            if (val.length < 7) return $(this).val(val.replace(/(\d{3})(\d{1,3})/, '$1-$2'));
            if (val.length < 11) return $(this).val(val.replace(/(\d{3})(\d{3})(\d{1,4})/, '$1-$2-$3'));
            return $(this).val(val.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3'));
        });

        $('#btn_update').on('click', function() {
            const userNm = $('#user_nm').val().trim();
            const cttpc = $('#cttpc').val().trim();
            const password = $('#password').val();
            const passwordCon = $('#password_con').val();

            if (!userNm) {
                alert('이름을 입력해주세요.');
                $('#user_nm').focus();
                return;
            }

            if (!cttpc) {
                alert('연락처를 입력해주세요.');
                $('#cttpc').focus();
                return;
            }

            const phoneRegex = /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$/;
            if (!phoneRegex.test(cttpc)) {
                alert('올바른 전화번호 형식이 아닙니다. (예: 010-0000-0000)');
                $('#cttpc').focus();
                return;
            }

            if (password || passwordCon) {
                if (password !== passwordCon) {
                    alert('새 비밀번호가 일치하지 않습니다.');
                    $('#password_con').focus();
                    return;
                }
                if (password.length < 4) {
                    alert('비밀번호는 4자 이상 입력해주세요.');
                    $('#password').focus();
                    return;
                }
            }

            if (confirm('회원 정보를 수정하시겠습니까?')) {
                $('#loadingOverlay').show();
                
                $.ajax({
                    url: '/front/memberUpdateAjax.do',
                    type: 'POST',
                    data: {
                        user_nm: userNm,
                        cttpc: cttpc,
                        password: password
                    },
                    dataType: 'json',
                    success: function(res) {
                        $('#loadingOverlay').hide();
                        if (res.resultMap === 'Y') {
                            alert('정보가 성공적으로 수정되었습니다.');
                            location.href = '/shop/list.do';
                        } else {
                            alert(res.resultStats.resultMsg || '오류가 발생했습니다.');
                        }
                    },
                    error: function() {
                        $('#loadingOverlay').hide();
                        alert('서버 통신 중 오류가 발생했습니다.');
                    }
                });
            }
        });
    });
</script>

<%@ include file="/common/frontinc/msg.jspf" %>
</body>
</html>
