<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/inc/docType.jspf" %>
<%@ include file="/common/inc/common.jspf" %>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title><%=headTitle%></title>

  <script type="text/javascript" src="<%=jsRoot%>jquery/jquery-1.11.1.min.js"></script>
  <script type="text/javascript" src="<%=jsRoot%>jquery-ui/jquery-ui-1.10.3.custom.min.js"></script>
  <link href="<%=cssRoot%>bootstrap/bootstrap.css" rel="stylesheet" type="text/css" media="screen">
  <script src="<%=jsRoot%>bootstrap/bootstrap.js"></script>

  <link href="<%=cssRoot%>bootstrap/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" media="screen">
  <script type="text/javascript" src="<%=jsRoot%>bootstrap/moment-with-locales.min.js"></script>
  <script type="text/javascript" src="<%=jsRoot%>bootstrap/bootstrap-datetimepicker.min.js"></script>

  <!-- AdminLTE Setup -->
  <link rel="stylesheet" href="<%=cssRoot%>AdminLTE/font-awesome.min.css">
  <link rel="stylesheet" href="<%=cssRoot%>AdminLTE/ionicons.min.css">
  <link rel="stylesheet" href="<%=cssRoot%>AdminLTE/AdminLTE.css">
  <link rel="stylesheet" href="<%=cssRoot%>AdminLTE/skin-green-light.css">
  <script type="text/javascript" src="<%=jsRoot%>AdminLTE/app.js"></script>

  <!-- icheck -->
  <link rel="stylesheet" href="<%=jsRoot%>icheck/skins/flat/blue.css">
  <script type="text/javascript" src="<%=jsRoot%>icheck/icheck.min.js"></script>

  <script type="text/javascript" src="<%=jsRoot%>common.js"></script>
  <link rel="stylesheet" href="<%=cssRoot%>common.css">

  <style>
    .login_wrap { max-width: 560px; margin: 40px auto; }
    .login_wrap .btn.btn-block { height: 52px; font-size: 18px; border-radius: 8px; }
  </style>

	<script type="text/javascript">
	//<![CDATA[
		function openModal(id){ document.getElementById(id).style.display='block'; }
		function closeModal(id){ document.getElementById(id).style.display='none'; }

		function fnResetPw(){
            var $btn = $("#btnResetPw");

			var pw = $("#member_new_pw").val();
			var pw_chk = $("#member_new_pw_chk").val();

			var uid = $("#uid").val();
			var sel = $("#sel").val();
			var ver = $("#ver").val();

			// 링크 파라미터 누락 시 차단
			if(!uid || !sel || !ver){
				$("#msg").text("유효하지 않은 접근입니다. 메일의 링크를 다시 확인해 주세요.");
				openModal('modal-default4');
				return;
			}

			// 비밀번호 규칙 검사
			var num = pw.search(/[0-9]/g);
			var eng = pw.search(/[a-z]/ig);
			var spe = pw.search(/[`~!@#$%^&*|\\'\";:\/?_+\-=()\[\]{}.,<>]/g);
            /*
			if(pw.length === 0){
				$("#msg").text("비밀번호를 입력해주세요"); openModal('modal-default4'); return;
			}else if(pw.length < 8 || pw.length > 20){
				$("#msg").text("8자리 ~ 20자리 이내로 입력해주세요."); openModal('modal-default4'); return;
			}else if(/\s/.test(pw)){
				$("#msg").text("비밀번호는 공백 없이 입력해주세요."); openModal('modal-default4'); return;
			}else if(num < 0 || eng < 0 || spe < 0){
				$("#msg").text("영문, 숫자, 특수문자를 혼합하여 입력해주세요."); openModal('modal-default4'); return;
			}else if(!pw_chk){
				$("#msg").text("비밀번호 확인을 입력해주세요."); openModal('modal-default4'); return;
			}else if(pw !== pw_chk){
				$("#msg").text("비밀번호가 일치하지 않습니다."); openModal('modal-default4'); return;
			}
            */
			if(pw.length === 0){
				$("#msg").text("비밀번호를 입력해주세요"); openModal('modal-default4'); return;
			}else if(/\s/.test(pw)){
				$("#msg").text("비밀번호는 공백 없이 입력해주세요."); openModal('modal-default4'); return;
			}else if(!pw_chk){
				$("#msg").text("비밀번호 확인을 입력해주세요."); openModal('modal-default4'); return;
			}else if(pw !== pw_chk){
				$("#msg").text("비밀번호가 일치하지 않습니다."); openModal('modal-default4'); return;
			}
           setLoading($btn, true, "비밀번호 재설정");
			// /api/members/reset/change 호출 (쿼리: uid/sel/ver, 바디: JSON)
			$.ajax({
				type: "POST",
				url: "/api/members/reset/change.do?uid=" + encodeURIComponent(uid)
				   + "&sel=" + encodeURIComponent(sel)
				   + "&ver=" + encodeURIComponent(ver),
				data: JSON.stringify({ newPassword: pw, confirmPassword: pw_chk }),
				contentType: "application/json",
				dataType: "json",
				success: function(res){
                    setLoading($btn, false, "비밀번호 재설정");
					var code = res && res.resultString ? (""+res.resultString).trim() : "0";
					if(code === "200"){
						openModal('modal-default3');
					} else if(code === "604") {
						$("#msg").text("링크가 만료되었거나 유효하지 않습니다."); openModal('modal-default4');
					} else {
						$("#msg").text("처리 중 오류가 발생했습니다."); openModal('modal-default4');
					}
				},
				error: function(){
                    setLoading($btn, false, "비밀번호 재설정");
					$("#msg").text("서버 통신 오류가 발생했습니다."); openModal('modal-default4');
				}
			});
		}

		// 초기 링크 파라미터 검증(없으면 안내)
		$(function(){
			var uid = $("#uid").val(), sel=$("#sel").val(), ver=$("#ver").val();
			if(!uid || !sel || !ver){
				$("#msg").text("유효하지 않은 접근입니다. 메일의 링크를 다시 확인해 주세요.");
				openModal('modal-default4');
			}
		});
	// ]]>
	</script>
</head>

<body class="hold-transition login_page">
  <div class="login_wrap">
    <h2 class="h3">비밀번호 재설정</h2>
    <p class="txt">8~20자리 영문, 숫자, 특수문자 조합으로 입력해 주세요.</p>

    <form id='reset_pw_form' method="post">
      <!-- 메일 링크 파라미터 주입: uid/sel/ver -->
      <input type="hidden" id="uid" name="uid" value="<%=request.getParameter("uid")!=null?request.getParameter("uid"):""%>">
      <input type="hidden" id="sel" name="sel" value="<%=request.getParameter("sel")!=null?request.getParameter("sel"):""%>">
      <input type="hidden" id="ver" name="ver" value="<%=request.getParameter("ver")!=null?request.getParameter("ver"):""%>">

      <div class="login-box-body">
        <div class="form-group has-feedback">
          <input type="password" class="form-control" id='member_new_pw' name="member_new_pw" placeholder="새로운 비밀번호 입력">
        </div>
        <div class="form-group has-feedback">
          <input type="password" class="form-control" id='member_new_pw_chk' name="member_new_pw_chk" placeholder="비밀번호 확인">
        </div>
        <div class="row">
          <div class="col-xs-12">
            <button type="button" id="btnResetPw" class="btn btn-primary btn-block btn-flat" onclick="fnResetPw();">확인</button>
          </div>
        </div>
      </div>
    </form>
  </div>

  <div class="modal modal_theme_oh" id="modal-default4">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          <div class="find_id">
            <p class="txt" id="msg" style="font-size: 22px; margin-bottom:20px;"></p>
            <button type="button" class="btn btn-primary" style="font-size: 18px;" onclick="closeModal('modal-default4')">확인</button>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="modal modal_theme_oh" id="modal-default3">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          <div class="find_id">
            <p class="txt2" style="font-size: 24px;color: #37a1e4; font-weight: 500;">비밀번호 재설정이 완료되었습니다.</p>
            <p class="txt">새로운 비밀번호로 다시 로그인해주세요.</p>
            <!--
            <button type="button" class="btn btn-primary" style="font-size: 18px;" onclick="location.href='/admin/login.do'">확인</button>
            -->
          </div>
        </div>
      </div>
    </div>
  </div>

<script type="text/javascript">
  function setLoading($btn, isLoading, textWhenIdle){
    if (isLoading) {
      $btn.prop("disabled", true)
          .data("orig-text", textWhenIdle || $btn.text())
          .html('<i class="fa fa-spinner fa-spin"></i> 처리 중...');
    } else {
      var t = $btn.data("orig-text") || textWhenIdle || "확인";
      $btn.prop("disabled", false).html(t);
    }
  }
</script>

</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
