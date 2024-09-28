<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/inc/docType.jspf" %>
<%@ include file="/common/inc/common.jspf" %>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<link href="<%=cssRoot%>bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css" media="screen" />
	<link rel="stylesheet" href="/common/css/common.css?version=2"/>
	<script type="text/javascript" src="<%=jsRoot%>jquery/jquery-1.11.1.min.js"></script>
	<script type="text/javascript">
	//<![CDATA[
	function fnLogin(){

		if($("#user_id").val() == ""){
			alert("ID를 입력해 주세요.");
			$("#user_id").focus();
			return;
		}

		if($("#user_pwd").val() == ""){
			alert("패스워드를 입력해 주세요.");
			$("#user_pwd").focus();
			return;
		}

		saveid();
		$("#aform").attr({action:"/admin/login.do", method:'post'}).submit(); 
	}
	
	function setCookie (name, value, expires) {
		document.cookie = name + "=" + escape (value) + "; path=/; expires=" + expires.toGMTString();
	}

	function getCookie(Name) {
		var search = Name + "=";
		if (document.cookie.length > 0) { // 쿠키가 설정되어 있다면
			offset = document.cookie.indexOf(search);
			if (offset != -1) { // 쿠키가 존재하면
				offset += search.length;
				// set index of beginning of value
				end = document.cookie.indexOf(";", offset);
				// 쿠키 값의 마지막 위치 인덱스 번호 설정
				if (end == -1)
					end = document.cookie.length;
				return unescape(document.cookie.substring(offset, end));
			}
		}
		return "";
	}

	function saveid() {

		var form = document.aform;
		var expdate = new Date();
		// 기본적으로 30일동안 기억하게 함. 일수를 조절하려면 * 30에서 숫자를 조절하면 됨
		if (form.checkId.checked)
			expdate.setTime(expdate.getTime() + 1000 * 3600 * 24 * 30); // 30일
		else
			expdate.setTime(expdate.getTime() - 1); // 쿠키 삭제조건
		setCookie("saveid", form.id.value, expdate);
	}



	function getid(form) {
		form.checkId.checked = ((form.id.value = getCookie("saveid")) != "");
	}

	function fnInit() {
		getid(document.aform);
	}

	$(document).ready(function(){
		fnInit();
	});


	//로그인 페이지 관련
	$(function(){
		$('.button-checkbox').each(function(){
			var $widget = $(this),
				$button = $widget.find('button'),
				$checkbox = $widget.find('input:checkbox'),
				color = $button.data('color'),
				settings = {
						on: {
							icon: 'glyphicon glyphicon-check'
						},
						off: {
							icon: 'glyphicon glyphicon-unchecked'
						}
				};

			$button.on('click', function () {
				$checkbox.prop('checked', !$checkbox.is(':checked'));
				$checkbox.triggerHandler('change');
				saveid(document.aform);
				updateDisplay();
			});

			$checkbox.on('change', function () {
				updateDisplay();
			});

			function updateDisplay() {
				var isChecked = $checkbox.is(':checked');
				// Set the button's state
				$button.data('state', (isChecked) ? "on" : "off");

				// Set the button's icon
				$button.find('.state-icon')
					.removeClass()
					.addClass('state-icon ' + settings[$button.data('state')].icon);

				// Update the button's color
				if (isChecked) {
					$button
						.removeClass('btn-default')
						.addClass('btn-' + color + ' active');
				}
				else
				{
					$button
						.removeClass('btn-' + color + ' active')
						.addClass('btn-default');
				}
			}
			function init() {
				updateDisplay();
				// Inject the icon if applicable
				if ($button.find('.state-icon').length == 0) {
					$button.prepend('<i class="state-icon ' + settings[$button.data('state')].icon + '"></i> ');
				}
			}
			init();
		});
	});
	
	// ]]>
	</script>
</head>
<body>
<div id="wrapper">
	<div class="form-horizontal"><!-- form-horizontal -->
		<div class="box"><!-- box -->		
			<div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3"><!-- col-xs-12 -->				
					<form id="aform" name="aform" action ="/admin/login.do" method="post">
						<input type="hidden" name="ru" value="<%=param.getString("ru")%>" />
						<fieldset>
							<div class="box-wrap"><!-- box-wrap -->
								<!--  
								<h1 class="login-logo"><a href="/front/main.do"><img alt="Microbiz" src="/common/images/logo_login.png"/></a></h1>
								-->
								<h2 class="login-title"><span>planF 로그인</span></h2>
								<div class="login-box">
									<span class="login-ic"><i class="item-loc"></i>	</span>
									<div class="form-group">
										<label class="control-login-label col-sm-3" for="user_id" >아이디</label>
										<div class="col-sm-8">
											<input type="text" name="id" id="user_id" class="form-control input-lg" placeholder="아이디를 입력해주세요." value="">
										</div>
									</div>
									<div class="form-group pdb-15">
										<label class="control-login-label col-sm-3" for="user_pwd" >비밀번호</label>
										<div class="col-sm-8">
											<input type="password" name="pwd" id="user_pwd"  onKeyDown="javascript:if (event.keyCode == 13) { fnLogin(); }" class="form-control input-lg" placeholder="비밀번호를 입력해주세요." value="">
										</div>
									</div>
									
									<div class="row">
										<div class="col-sm-8 col-sm-offset-3">
											<input type="button" class="btn btn-lg btn-dpgray btn-block" onclick="fnLogin();" value="로그인">
										</div>
									</div>
									
									<div class="login-btnset">
										<span class="button-checkbox">
											<input type="checkbox" id="checkId" name="checkId" class="hidden">
											<button type="button" class="btn" data-color="info">아이디 기억하기</button>
										</span>
										<span class="button-checkbox">
											<input type="checkbox" id="checkAutoLogin" name="checkAutoLogin" class="hidden">
											<button type="button" class="btn" data-color="success">자동 로그인</button>
										</span>									
									</div>
									
									
								</div>
								
								<div class="bt-roundbox2">
									<div class="text-ln">아직 회원이 아니시라면 지금회원으로 가입하세요. <button type="button" class="btn btn-xs btn-dpgray" id="btn_memberAdd">회원가입</button></div>
									<div class="text-ln">아이디나 비밀번호를 분실하셨다면, 지금 찾아보세요. <button type="button" class="btn btn-xs btn-dpgray" id="btn_findId">아이디/비밀번호 찾기</button></div>
								</div>
							</div><!-- box-wrap -->
						</fieldset>
					</form>				
			</div><!-- col-xs-12 -->
		</div><!-- box -->	
	</div><!-- form-horizontal -->
</div>
<script type="text/javascript">
	$('#btn_memberAdd').on('click', function(e) {
   		location.href="/front/member/termAndConditions.do";
   	});
   	$('#btn_findId').on('click', function(e) {
   		location.href="/front/member/memberFindId.do";
   	});
</script>		

</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>