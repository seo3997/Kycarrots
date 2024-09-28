<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ include file="/common/inc/common.jspf" %>
<% 
	String userAgent = request.getHeader("User-Agent");

	boolean headYn=true;
	if(userAgent.startsWith("MICROBIZ")) headYn=false;

%>


<script type="text/javascript">
$(document).ready(function(){
	$("#topMenuWrap li").on("click", function(){
		$("#topMenuWrap li").removeClass("active");
		$(this).addClass("active");
	});

	$('.sidebar-toggle').click(function(){
		$('#top_menu_1').is(':visible') ? $('#top_menu_1').hide() : $('#top_menu_1').show();
	});

	$(window).resize(function(){
		$('.dropdown-menu').removeClass('show');
	});


	
	// 회원정보 수정 모달창이 뜰때
	$("#myInfoModal").on("show.bs.modal", function(e){
		// 회원정보 가지고 온다.
		$.ajax({
			url : '/common/selectUserMgtAjax.do',
			data : { 'user_no' : '<%=ssUserNo%>'},
			type : 'get',
			cache:false,
			dataType : 'json',
			success : function(response){
				if(response.resultStats.resultCode == 'ok'){
					// 연락처 구분
					//var cttpcSeComboStr = getComboStr(response.cttpcSeComboStr, 'CODE', 'CODE_NM', response.resultMap.CTTPC_SE_CODE, '');
					//$('[name=modal_cttpc_se_code]').html(cttpcSeComboStr);
					// 데이터 셋팅
					$('#myInfoModal .user_id').val(response.resultMap.USER_ID);
					$('#myInfoModal .companynm').val(response.resultMap.COMPANY_NAME);
					$('#myInfoModal [name=modal_user_nm]').val(response.resultMap.USER_NM);

					$('#myInfoModal [name=modal_email]').val(response.resultMap.EMAIL);
					$('#myInfoModal [name=modal_cttpc1]').val(response.resultMap.CTTPC.split('-')[0]);
					$('#myInfoModal [name=modal_cttpc2]').val(response.resultMap.CTTPC.split('-')[1]);
					$('#myInfoModal [name=modal_cttpc3]').val(response.resultMap.CTTPC.split('-')[2]);

					$('#myInfoModal [name=modal_user_nm_chg]').val(response.resultMap.USER_NM);
					$('#myInfoModal [name=modal_cttpc_chg]').val(response.resultMap.CTTPC);
					$('#myInfoModal [name=modal_email_chg]').val(response.resultMap.EMAIL);

				} else {
					ajaxErrorMsg(response);
				}
				
			},
		});
		
	});

});

// 수정
function fnGoUpdateModal(){
	var cttpc = $("[name=modal_cttpc1]").val() + "-" + $("[name=modal_cttpc2]").val() + "-" + $("[name=modal_cttpc3]").val();
	$("[name=modal_cttpc]").val(cttpc);
	
	if($("[name=modal_ncnm]").val() == ""){
		alert("이름을 입력해 주세요.");
		$("[name=modal_ncnm]").focus();
		return;
	}

	if(confirm("수정하시겠습니까?")){
		// 회원정보 가지고 온다.
		$.ajax({
			url : '/common/updateUserMgtAjax.do',
			data : $('#modalInfo').serialize(),
			type : 'post',
	        cache: false,
			dataType : 'json',
			success : function(response){
				console.log(response);
				if(response.resultStats.resultCode == 'success'){
					alert('수정 하였습니다.');
					location.reload();
				} else {
					ajaxErrorMsg(response);
				}
			}
		});
	}
}

function fnClickMenuBtn(that) {
	// 	$("[data-toggle='offcanvas']").click();

//	var screenSizes = $.AdminLTE.options.screenSizes;
//	if ( !$(that).parent().hasClass("open") ) {
		//Enable sidebar push menu
//		if ($(window).width() > (screenSizes.sm - 1)) {
//			if ($("body").hasClass('sidebar-collapse')) {
// 				$("body").removeClass('sidebar-collapse').trigger('expanded.pushMenu');
//			} else {
//				$("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
//			}
//		}
		//Handle sidebar push menu for small screens
//		else {
//			if ($("body").hasClass('sidebar-open')) {
// 				$("body").removeClass('sidebar-open').removeClass('sidebar-collapse').trigger('collapsed.pushMenu');
//			} else {
				//$("body").addClass('sidebar-open').trigger('expanded.pushMenu');
//			}
//		}
		
//	}

}
</script>

<!-- Main Header -->
<header class="main-header">
<%  if(headYn)
	{
%>
	<!-- Logo -->
	<a href="/mgt/main/dashBoard.do" class="logo">
		<!-- mini logo for sidebar mini 50x50 pixels -->
		<span class="logo-mini"><b>planF</b></span>
		<!-- logo for regular state and mobile devices -->
		<span class="logo-lg"><b>planF</b></span>
	</a>

	<!-- Header Navbar -->
	<!-- <nav class="navbar navbar-static-top" role="navigation"> -->
	<nav class="navbar navbar-expand-lg navbar-light">
		<a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
			<!-- <img src="/common/images/aside-tipin.png" alt="Toggle navigation" /> -->
			<i class="fa fa-sign-in-alt"><span class="sr-only">서브메뉴 밀어넣기</span></i>
		</a>
		<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
			<ul id="navbarNavDropdown" class="nav navbar-nav">
			<c:forEach var="result" items="${topMenuList }" varStatus="status">
				<c:choose>
					<c:when test="${empty top_menu_id && status.count == 1 }">
					<li class="active">
					</c:when>
					<c:when test="${top_menu_id == result.MENU_ID }">
					<li class="nav-item dropdown active">
					</c:when>
					<c:otherwise>
					<li class="nav-item dropdown">
					</c:otherwise>
					</c:choose>
						<a href="${result.URL}">${result.MENU_NM }</a>
					</li>
				</c:forEach>
			</ul>
		</div>

		<!-- Navbar Right Menu -->
		<div class="navbar-custom-menu">
			<ul class="nav navbar-nav">
				<!-- User Account Menu -->
				<li class="user user-menu">
					<!-- Menu Toggle Button -->
					<a href="#" data-toggle="modal" data-target="#myInfoModal">
						<span><i class="fa fa-user"></i></span>
						<span><%=ssUserNm%></span>
					</a>
				</li>
				<!-- Control Sidebar Toggle Button -->
				<li>
					<a href="/admin/logout.do">
						<span><i class="fa fa-lock"></i></span>
						<span>Logout</span>
					</a>
				</li>

				<li class="dropdown visible-xs-block">
					<a href="#" onclick="fnClickMenuBtn(this); return false;" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="true"><span><i class="fa fa-th"></i></span>
					</a>
					<ul class="dropdown-menu" role="menu">
						<c:forEach var="result" items="${topMenuList }" varStatus="status">
							<c:choose>
								<c:when test="${empty top_menu_id && status.count == 1 }">
								<li class="user-header active">
								</c:when>
								<c:when test="${top_menu_id == result.MENU_ID }">
								<li class="user-header active">
								</c:when>
								<c:otherwise>
								<li class="user-header">
								</c:otherwise>
							</c:choose>
							<a href="${result.URL}">${result.MENU_NM }</a>
							</li>
						</c:forEach>
					</ul>
				</li>

			</ul>
		</div>
		<!-- // Navbar Right Menu -->

	</nav>
</header>

<!-- 나의정보 수정창 -->
<div class="modal fade" id="myInfoModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document" style="width:70%">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="myModalLabel">나의 정보</h4>
			</div>
			<div class="modal-body">
				<form id="modalInfo" action="/admin/user/updateUserMgtAjax.do" method="post">
					<input type="hidden" id="modal_cttpc" 				name="modal_cttpc" />
					<input type="hidden" id="modal_user_no" 			name="modal_user_no" value="<%=ssUserNo %>" />

					<input type="hidden" id="modal_user_nm_chg" 	name="modal_user_nm_chg" 	/>
					<input type="hidden" id="modal_cttpc_chg" 			name="modal_cttpc_chg" 		/>
					<input type="hidden" id="modal_email_chg" 		name="modal_email_chg" 	 	/>



					<div class="my-info row">


							<label for="user_id" class="col-2 col-form-label">사용자ID</label>
							<div class="col-10">
								<input type="text" id="user_id" class="form-control user_id" disabled="disabled" />
							</div>

							<label for="modal_password" class="col-2 col-form-label">비밀번호</label>
							<div class="col-10">
								<input type="password" class="form-control" id="modal_password" name="modal_password" placeholder="비밀번호" maxlength="50" />
							</div>

							<label for="modal_user_nm" class="col-2 col-form-label">이름</label>
							<div class="col-10">
								<input type="text" class="form-control" id="modal_user_nm" name="modal_user_nm" placeholder="이름" />
							</div>

							<label for="modal_email" class="col-2 col-form-label">이메일</label>
							<div class="col-10">
								<input type="text" class="form-control" id="modal_email" name="modal_email" placeholder="이메일" />
							</div>

							<label for="modal_cttpc1" class="col-2 col-form-label">연락처</label>
							<div class="col-10 form-inline">
								<input type="hidden" name="modal_cttpc_se_code" value="10">
								<input type="text" id="modal_cttpc1" name="modal_cttpc1" class="col-3 form-control numeric" maxlength="3" title="전화번호 첫번째 입력칸입니다" />
								<span>&nbsp;-&nbsp;</span>
								<input type="text" id="modal_cttpc2" name="modal_cttpc2" class="col-3 form-control numeric" maxlength="4" title="전화번호 두번째 입력칸입니다" />
								<span>&nbsp;-&nbsp;</span>
								<input type="text" id="modal_cttpc3" name="modal_cttpc3" class="col-3 form-control numeric" maxlength="4" title="전화번호 세번째 입력칸입니다" />
							</div>

							<label for="companynm" class="col-2 col-form-label">업체명</label>
							<div class="col-10">
								<input type="text" id="companynm" class="form-control companynm" placeholder="업체명" disabled="disabled" />
							</div>

					</div>

				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-close" data-dismiss="modal" id="btnuser-close"><i class="fa fa-window-close"></i> 닫기</button>
				<button type="button" class="btn btn-modify" onclick="fnGoUpdateModal(); return false;"><i class="fa fa-pencil"></i> 수정</button>
			</div>
		</div>
	</div>
<%
	}
%>
	
</div>

	<script type="text/javascript">
		$(".content-header #spnavi").html($('#top_menu_nm').val());
	</script>


<input type="hidden"  name="top_menu_nm"  id="top_menu_nm"  value="${top_menu_nm}" /> 

