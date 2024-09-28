<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@ include file="/common/inc/common.jspf" %>
<jsp:useBean id="projectComboStr" type="java.util.List"	class="java.util.ArrayList" scope="request" />

<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>




<script type="text/javascript">
var pageTitleHtml="<h1 id='spTitle'>"+"${cur_menu_nm}"+"</h1>";



$(document).ready(function(){
	//초기메뉴 세팅(운영관리)
	fnChgLeftMenu("${top_menu_id}");
	//$(".content-header h1").html("${cur_menu_nm}");
	$("#pagetitle").html(pageTitleHtml);
	//alert("${top_menu_nm}");
	$("#top_menu_1").text("${top_menu_nm}");
	
	// 템플릿 하단으로 숨기기위해 메뉴 길이 늘림 
	$(".main-sidebar").css("height", $(".wrapper")[0].scrollHeight);
})

function fnChgLeftMenu(menu_id){
	var param = { 
		'up_menu_id' : menu_id 
	};
	
	$.ajax({
		url : '/common/inc/selectLeftMenuListAjax.do',
		type : 'post',
		dataType : 'json',
		data : param,
		async: false,
		success : function(response){
			if(response.resultStats.resultCode == 'ok'){
				
				var subMenuList = response.resultStats.leftMenuList;
				var sideHtml = '';	
				var menuClassStr = '';
				var menuNum = 0;
				var viewSub = '';
				var menu = '';
				
				//메뉴 초기화
				$("#sidebar").html("");
				$("#tmpMenu").html("");
		
				//===========================================
				for(var i = 0; i < subMenuList.length; i++){
	
					//tmpMenu에서 DOM 로딩
					sideHtml = $("#tmpMenu").html();
					menuClassStr = '';
	
					// 메뉴가 4depth(16자리에서 사이드메뉴로 빠짐)에서 부터 활성화 클래스가 나온다.
					// 총메뉴가 6depth(24자리)까지 나오는데 6depth는 기능메뉴로서 5depth메뉴와 4depth메뉴를 활성화를 시켜주어야한다.
					// 그래서 0, 16자리까지 자른게 같으면 클래스를 활성화를 시켜준다.
					
					// 현재 URL 에 맵핑된 menu_id
					var c_menu = '${menu_id}';
					// 4depth메뉴가 같다면 활성화
					if(subMenuList[i].MENU_ID == c_menu.substring(0, 20)){
						menuClassStr = 'active';
						$("#left_menu_nm").val(subMenuList[i].MENU_NM);
						//$("#spTitle").html("게시판");
						$(".content-header #spnavi").append(">"+ '<span class="active">' + subMenuList[i].MENU_NM + '</span>');
					}
					/*
					if(subMenuList[i].MENU_ID == c_menu.substring(0, 16)){
						menuClassStr = 'active';
					}
					*/
					if(subMenuList[i].MENU_LEVEL == '4'){
						
						//메뉴유형(MENU_TY_CODE)
						//카테고리	10
						//메뉴	20
						//기능	30
						//console.log("subMenuList[i].MENU_NM["+subMenuList[i].MENU_NM+"]subMenuList[i].MENU_TY_CODE["+subMenuList[i].MENU_TY_CODE+"]");
						if(subMenuList[i].MENU_TY_CODE == '10'){
							sideHtml += '<li data-id="' + menu_id + '" class="treeview ' + menuClassStr + '">';
							/* sideHtml += '<a href=""><i class="fa fa-angle-double-right"></i><span>' + subMenuList[i].MENU_NM + '</span><i class="fa fa-angle-left pull-right"></i></a>'; */
							sideHtml += '<a href=""><i class="fa"></i><span>' + subMenuList[i].MENU_NM + '</span><i class="fa fa-angle-left pull-right"></i></a>';
							sideHtml += '<ul class="treeview-menu">';
							sideHtml += '</ul>';
							sideHtml += '</li>';
							menu = true;
						}
						else{
							sideHtml += '<li data-id="' + menu_id + '" class="' + menuClassStr + '">';
							/* sideHtml += '<a href="javascript:fnGoUrl(\'' + subMenuList[i].URL + '\', \'' + menu_id + '\');" ><i class="fa fa-angle-double-right"></i><span>' + subMenuList[i].MENU_NM + '</span></a>'; */
							sideHtml += '<a href="javascript:fnGoUrl(\'' + subMenuList[i].URL + '\', \'' + menu_id + '\');" ><i class="fa"></i><span> ' + subMenuList[i].MENU_NM + '</span></a>';
							sideHtml += '</li>';
							menu = false;
						}
						
						viewSub = menuClassStr;
						
						//tmpMenu에서 DOM 저장
						$("#tmpMenu").html(sideHtml);
					}
					else if(subMenuList[i].MENU_LEVEL == '5'){
						if(menu)
						{
							var off = '';
							// 5depth 메뉴까지 존재하는경우 20자리까지 잘라서 맞는경우만 active해준다.
							if(subMenuList[i].MENU_ID == c_menu.substring(0, 20)){
								off = 'active';
							}
// 							if(viewSub == 'active'){ off = 'active'; }
							
							//tmpMenu에서 4뎁스 생성
							$("#tmpMenu").find("ul").eq(-1).append('<li class="' + off + '"><a href="javascript:fnGoUrl(\'' + subMenuList[i].URL + '\', \'' + menu_id + '\');"><i class="fa"></i>' + subMenuList[i].MENU_NM + '</a></li>');
						}
					}
				}
				$("#sidebar").append($("#tmpMenu").html());

			} else {
			}
		},
		error : function(){
		}
	});
}

function fnGoUrl(url, top_menu_id) {
	var f = document.menuForm;
// 	f.top_menu_id.value = top_menu_id;
	f.action = url;
	f.submit();
	
}
</script>

<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">
	<section class="sidebar">
		<!-- Sidebar Menu -->
 		<p id="top_menu_1"></p>
		<ul id="sidebar" class="sidebar-menu">
		<!--  
			<li class="active"><a href="#"><i class="fa fa-link"></i> <span>템플릿</span></a></li>
			<li><a href="/admin/author/selectAuthorPageList.do"><i class="fa fa-link"></i> <span>권한관리</span></a></li>
			<li><a href="/admin/menu/selectMenuList.do"><i class="fa fa-link"></i> <span>메뉴관리</span></a></li>
			<li><a href="/admin/authmenu/selectAuthMenuPageList.do"><i class="fa fa-link"></i> <span>권한별 메뉴관리</span></a></li>
			<li><a href="/admin/code/selectGroupCodePageList.do"><i class="fa fa-link"></i> <span>코드관리</span></a></li>
			<li><a href="/admin/user/selectUserPageList.do"><i class="fa fa-link"></i> <span>사용자관리</span></a>
			</li>
			<li class="treeview">
				<a href="#"><i class="fa fa-link"></i><span>Multilevel</span><i class="fa fa-angle-left pull-right"></i></a>
				<ul class="treeview-menu">
					<li><a href="#">Link in level 2</a></li>
					<li><a href="#">Link in level 2</a></li>
				</ul>
			</li>!-- /.sidebar-menu -->
		</ul>
	</section>
	<!-- /.sidebar -->
	<div id="tmpMenu" style="display:none;">
	</div>
	
	<form name="menuForm" method="POST" action="">
<!-- 		<input type="hidden" name="top_menu_id"/> -->
	</form>
	
	<!-- 템플릿 주석처리 -->	
<!-- 	<ul class="sidebar-menu"> -->
<!-- 		<li> -->
<!-- 			<a href="/admin/template.do"><span>템플릿</span></a> -->
<!-- 		</li> -->
<!-- 	</ul> -->
	
</aside>

<input type="hidden"  name="left_menu_nm"  id="left_menu_nm"  /> 
