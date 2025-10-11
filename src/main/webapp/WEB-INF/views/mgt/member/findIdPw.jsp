<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/inc/docType.jspf" %>
<%@ include file="/common/inc/common.jspf" %>

<jsp:useBean id="resultList"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
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
  .login_wrap { max-width: 980px; margin: 40px auto; }
  .nav-tabs-custom>.nav-tabs>li>a { font-weight:600; }
  .txt2 { color:#666; line-height:1.6; }

  /* 버튼 크기 강화 */
  .login_wrap .btn { padding: 14px 18px; font-size: 16px; }
  .login_wrap .btn.btn-block { height: 52px; line-height: 26px; border-radius: 8px; }
  .modal_theme_oh .btn { padding: 12px 18px; height: 48px; border-radius: 8px; }
  @media (max-width: 480px) {
    .login_wrap .btn.btn-block { height: 48px; font-size: 15px; }
  }
</style>

	<script type="text/javascript">
	//<![CDATA[
		function openModal(id){ document.getElementById(id).style.display='block'; }
		function closeModal(id){ document.getElementById(id).style.display='none'; }

		// 공통 AJAX POST
		function fnCallbackFormAjax(pUrl,pForm,pFncallback){
		    $.ajax({
		        type: "post",
		        url: pUrl,
				data : pForm.serialize(),
				dataType : 'json',
		        cache: false,
		        success: function (response) {
		            if (typeof pFncallback === "function") {
		            	pFncallback(response);
		            }
		        },
		        error: function(err) { console.log(err); }
		   });
		}

		/* =====================
		   아이디 찾기 (이메일 기준)
		   - 휴대폰 방식 제거
		===================== */
        function fnFindIdAjax(){
          var $btn = $("#btnFindId");
          var name = $("#member_name").val().trim();
          var p1 = $("#member_phone1").val().replace(/\D/g,'');
          var p2 = $("#member_phone2").val().replace(/\D/g,'');
          var p3 = $("#member_phone3").val().replace(/\D/g,'');

          if(name === ""){
            $("#msg").text("이름을 입력하세요"); openModal('modal-default4'); return;
          }
          if(p1 === "" || p2 === "" || p3 === ""){
            $("#msg").text("휴대폰 번호를 모두 입력하세요"); openModal('modal-default4'); return;
          }
          var digits = p1 + p2 + p3;
          if(digits.length < 10){
            $("#msg").text("휴대폰 번호를 정확히 입력하세요"); openModal('modal-default4'); return;
          }
          // hidden 필드에 조립된 번호 설정
          $("#member_phone").val(p1+"-"+p2+"-"+p3);
           setLoading($btn, true, "아이디 찾기");

          var sUrl = "/mgt/member/findIdAjax.do";
          var fFrom  = $("#sch_id_form_email"); // 폼 id가 이 이름인지 확인!
          fnCallbackFormAjax(sUrl, fFrom, function(pResponse){
            if(pResponse && pResponse.resultStats && pResponse.resultStats.resultCode === "ok"){
              setLoading($btn, false, "아이디 찾기");
              if(pResponse.resultStats.user_id_exist === true){
                var results = pResponse.resultStats.user_id_res;
                $("#txt_id").text(results);
                openModal('modal-default');
              } else {
                openModal('modal-default2');
              }
            } else {
              setLoading($btn, false, "아이디 찾기");
              $("#msg").text("오류가 발생했습니다."); openModal('modal-default4');
            }
          });
        }

		/* =====================
		   비밀번호 재설정 메일 발송
		   - 이메일만 입력 (서버: GET /api/members/find-password?mail=)
		===================== */
        function fnSendPwMailAjax(){
          var $btn = $("#btnSendPw");
          var email = $("#member_email").val().trim(); // ← 필드 id 확인!
          if (email === "") {
            $("#msg").text("이메일을 입력하세요"); openModal('modal-default4'); return;
          }
          setLoading($btn, true, "비밀번호 재설정 메일 받기");
          $.ajax({
            type: "get",
            url: "/api/members/find-password",
            data: { mail: email },
            dataType: "json",
            success: function(res){
              var code = (res && res.resultString ? (""+res.resultString).trim() : "0");
              setLoading($btn, false, "비밀번호 재설정 메일 받기");
              if (code === "200") {
                openModal('modal-default3');       // 발송 완료
              } else if (code === "601") {
                openModal('modal-default2');       // 일치하는 사용자 없음
              } else {
                $("#msg").text("메일 발송 중 오류가 발생했습니다.");
                openModal('modal-default4');
              }
            },
            error: function(){
              setLoading($btn, false, "비밀번호 재설정 메일 받기");
              $("#msg").text("서버 통신 오류가 발생했습니다."); openModal('modal-default4');
            }
          });
        }
        $(function(){
            function nextOnMax($this, nextId){
              var max = parseInt($this.attr("maxlength") || "0", 10);
              if(max > 0 && $this.val().length >= max && nextId){
                $("#"+nextId).focus();
              }
            }
            $("#member_phone1, #member_phone2, #member_phone3").on("input", function(){
              this.value = this.value.replace(/\D/g,'');
            });
            $("#member_phone1").on("input", function(){ nextOnMax($(this), "member_phone2"); });
            $("#member_phone2").on("input", function(){ nextOnMax($(this), "member_phone3"); });
          });
	// ]]>
	</script>
</head>

<body class="hold-transition login_page">
  <div class="login_wrap">
    <div class="nav-tabs-custom">
      <ul class="nav nav-tabs">
        <li class="active"><a href="#tab_find_id" data-toggle="tab" aria-expanded="true">아이디 찾기</a></li>
        <li class=""><a href="#tab_reset_pw" data-toggle="tab" aria-expanded="false">비밀번호 재설정</a></li>
      </ul>
      <div class="tab-content">

        <!-- 아이디 찾기: 이메일 기준 -->
        <div class="tab-pane active" id="tab_find_id">
          <form id='sch_id_form_email' method="post">
            <p class="txt">가입 시 입력한 이름과 전화번호을 입력하세요.</p>
            <div class="box-body">
              <div class="form-group">
                <input type="text" class="form-control" id='member_name' name='member_name' placeholder="이름 입력">
              </div>
            <div class="form-group phone-group">
              <div class="row">
                <div class="col-xs-4">
                  <input type="tel" class="form-control" id="member_phone1"
                         maxlength="3" inputmode="numeric" pattern="[0-9]*" placeholder="010">
                </div>
                <div class="col-xs-4">
                  <input type="tel" class="form-control" id="member_phone2"
                         maxlength="4" inputmode="numeric" pattern="[0-9]*" placeholder="1234">
                </div>
                <div class="col-xs-4">
                  <input type="tel" class="form-control" id="member_phone3"
                         maxlength="4" inputmode="numeric" pattern="[0-9]*" placeholder="5678">
                </div>
              </div>
              <!-- 조립해서 서버로 보낼 hidden -->
              <input type="hidden" id="member_phone" name="member_phone">
            </div>
            </div>
            <div class="box-footer text-right">
              <button type="button" id="btnFindId" class="btn btn-primary btn-block" onclick="fnFindIdAjax();">아이디 찾기</button>
            </div>
          </form>
        </div>

        <!-- 비밀번호 재설정: 이메일만 입력 -->
        <div class="tab-pane" id="tab_reset_pw">
          <form id='sch_pw_form' method="post">
            <p class="txt2">가입 시 등록한 이메일을 입력하세요.<br>입력하신 이메일로 비밀번호를 재설정할 수 있는 링크가 발송됩니다.</p>
            <div class="box-body">
              <div class="form-group">
                <input type="email" class="form-control" id='member_email' name="member_email" placeholder="이메일 입력">
              </div>
            </div>
            <div class="box-footer text-right">
              <button type="button" id="btnSendPw" class="btn btn-primary btn-block" onclick="fnSendPwMailAjax();">비밀번호 재설정 메일 받기</button>
            </div>
          </form>
        </div>

      </div>
    </div>
  </div>

  <!-- 모달들 -->
  <div class="modal modal_theme_oh" id="modal-default">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
        <div class="modal-body">
          <div class="find_id">
            <p class="txt">회원님의 아이디를 찾았습니다.</p>
            <p class="txt_id" id="txt_id"></p>
            <button type="button" class="btn btn-primary" onclick="closeModal('modal-default')">확인</button>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="modal modal_theme_oh" id="modal-default2">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          <div class="find_id">
            <p class="txt2">일치하는 정보가 없습니다.</p>
            <p class="txt">입력한 정보와 일치하는 회원정보가 없습니다.<br>다시 한번 확인하세요.</p>
            <button type="button" class="btn btn-primary" onclick="closeModal('modal-default2')">확인</button>
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
            <p class="txt2" style="font-size: 24px;color: #37a1e4; font-weight: 500;">비밀번호 재설정 메일을 발송하였습니다.</p>
            <p class="txt">메일이 도착하지 않았다면 스팸메일함을 확인해 주세요.</p>
            <button type="button" class="btn btn-primary" onclick="closeModal('modal-default3')">확인</button>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="modal modal_theme_oh" id="modal-default4">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          <div class="find_id">
            <p class="txt" id="msg" style="font-size: 22px; margin-bottom:20px;"></p>
            <button type="button" class="btn btn-primary" onclick="closeModal('modal-default4')">확인</button>
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
