<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<jsp:useBean id="resultMap" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="authList"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>

	<script type="text/javascript">
	//<![CDATA[
		$(function(){
		});
		// 목록
		function fnGoList(){
			$("#aform").attr({action:"/admin/user/selectPageListUserMgt.do", method:'post'}).submit();
		}
		// 사용자수정폼 이동
		function fnGoUpdateForm(user_no){
			$("#aform").action = "/admin/user/updateFormUserMgt.do";
			$("#aform").submit();
		}
		// 사용자삭제
		function fnGoDelete(user_no){
			if(confirm("삭제하시겠습니까?")){
				$("#aform").attr({action:"/admin/user/deleteUserMgt.do", method:'post'}).submit();
			}			
		}
	//]]>
	</script>
</head>
<body class="hold-transition skin-green-light sidebar-mini">
<div class="wrapper">
	
	<!-- 헤더  -->
	<c:import url="/common/inc/header.do" charEncoding="utf-8" />
	
	<!-- 좌측 메뉴 -->
	<c:import url="/common/inc/menu.do" charEncoding="utf-8" />
	
	<!-- content -->
	<div class="content-wrapper">
		<!-- Content Header (Page header) -->
		<section class="content-header">
		<div id="navi"><i class="fa fa-home f12 color-lgray"></i><span class="blind">home</span>><span class="text" id="spnavi"></span></div>
    	<div id="pagetitle">
    	</div>
		</section>

		<!-- Main content -->
		<section class="content container-fluid vw-page">
			<form role="form" id="aform" method="post" action="/admin/user/updateFormUserMgt.do" class="form-horizontal">
				<input type="hidden" id="user_no" name="user_no" value="<%=resultMap.getString("USER_NO") %>" />
				<input type="hidden" name="sch_type" value="<%=param.getString("sch_type")%>" />
				<input type="hidden" name="sch_text" value="<%=param.getString("sch_text")%>" />
				<input type="hidden" name="currentPage" id="currentPage" value="<%=param.getString("currentPage")%>"/>

			<div class="card">

				<h4 class="cardTitle"><i class="fa fa-caret-square-right"></i> 회원기본정보</h4>
				
				<div class="card-body viewForm">



					<div class="form-group row">
						<label for="" class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">사용자ID</label>
						<div class="col-xs-12 col-sm-9 col-md-3 col-lg-4">
							<%=resultMap.getString("USER_ID") %>
						</div>
						<div class="form-hidden-line w-100 d-sm-block d-md-none d-lg-none" style="height: 1px; background-color: #ebebeb;"></div>
						<label for="" class="control-label col-xs-12 col-sm-3 col-md-2 col-lg-2">이름</label>
						<div class="col-xs-12 col-sm-9 col-md-4 col-lg-4">
							<%=resultMap.getString("USER_NM") %>
						</div>
					</div>

					<div class="form-group row">
						<label for="" class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">휴대폰번호</label>
						<div class="col-xs-12 col-sm-9 col-md-3 col-lg-4">
							<%=resultMap.getString("CTTPC") %>
							<!--  
							(<%=resultMap.getString("CTTPC_SE_NM") %>) <%=resultMap.getString("CTTPC") %>
							-->
						</div>
						<div class="form-hidden-line w-100 d-sm-block d-md-none d-lg-none" style="height: 1px; background-color: #ebebeb;"></div>
						<label for="" class="control-label col-xs-12 col-sm-3 col-md-2 col-lg-2">이메일</label>
						<div class="col-xs-12 col-sm-9 col-md-4 col-lg-4">
							<%=resultMap.getString("EMAIL") %>
						</div>
					</div>

					<div class="form-group row">
						<label for="" class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">사용자 권한</label>
						<div class="col-xs-12 col-sm-9 col-md-3 col-lg-4">
							<%
								String sUrerAuthorId = "";
								if(authList != null){
									for(int i=0; i<authList.size(); i++) { 
										DataMap dataMap = (DataMap)authList.get(i);
										out.print(dataMap.getString("AUTHOR_NM"));
										sUrerAuthorId = dataMap.getString("AUTHOR_ID");
										if(i != authList.size()-1){
											out.print(", "); 	
										} 
									} 
								}
							%>
						</div>
						<div class="form-hidden-line w-100 d-sm-block d-md-none d-lg-none" style="height: 1px; background-color: #ebebeb;"></div>
						<label for="" class="control-label col-xs-12 col-sm-3 col-md-2 col-lg-2">사용자 상태</label>
						<div class="col-xs-12 col-sm-9 col-md-4 col-lg-4">
							<%=resultMap.getString("USER_STTUS_NM") %>
						</div>
					</div>

					<div class="form-group row">
						<label for="" class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">업체명</label>
						<div class="col-xs-12 col-sm-9 col-md-3 col-lg-4">
							<%=resultMap.getString("COMPANY_NAME") %>
						</div>
						<div class="form-hidden-line w-100 d-sm-block d-md-none d-lg-none" style="height: 1px; background-color: #ebebeb;"></div>
						<label for="" class="control-label col-xs-12 col-sm-3 col-md-2 col-lg-2">등록일</label>
						<div class="col-xs-12 col-sm-9 col-md-4 col-lg-4">
							<%=resultMap.getString("REGIST_YMD") %>
						</div>
					</div>
					
					<div class="form-group row">
						<label for="CTTPC_SE_CODE" class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">사용자 레벨</label>
						<div class="col-xs-12 col-sm-9 col-md-3 col-lg-4">
							<%=resultMap.getString("CTTPC_SE_NM") %>
						</div>
					</div>


					<% 
						if("ROLE_PUBD".equals(resultMap.getString("AUTHOR_ID")) || "ROLE_LCONF".equals(resultMap.getString("AUTHOR_ID"))){
					%>
					<div class="form-group row">
						<label for="" class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">카테고리</label>
						<div class="col-xs-12 col-sm-9 col-md-3 col-lg-4">
							<%=resultMap.getString("PROJECT_COMPANY_NM")+" / "+resultMap.getString("PROJECT_CODE_MNM") %>
						</div>
						<div class="form-hidden-line w-100 d-sm-block d-md-none d-lg-none" style="height: 1px; background-color: #ebebeb;"></div>
						<label for="" class="control-label col-xs-12 col-sm-3 col-md-2 col-lg-2">검수건수</label>
						<div class="col-xs-12 col-sm-9 col-md-4 col-lg-4">
							<%=resultMap.getString("APPLY_CNT") %>
						</div>
					</div>
					<% 
						}
					%>

				</div>

				<div class="box-footer">
					<div class="text-center">
					<%-- <% 
						if("ROLE_PROJ".equals(ssAuthorId) && "ROLE_DCONF".equals(sUrerAuthorId)) {
					%>
						<button type="button" class="btn btn-list" onclick="fnGoList(); return false;"><i class="fa fa-reply"></i> 목록</button>

					<% 
						} else {
					%> --%>
						<button type="button" class="btn btn-list" onclick="fnGoList(); return false;"><i class="fa fa-reply"></i> 목록</button>
						<% 
						if(!resultMap.getString("USER_STTUS_NM").equals("탈퇴")) {
						%>
						<button type="button" class="btn btn-modify" onclick="fnGoUpdateForm(); return false;"><i class="fa fa-eraser"></i> 수정</button>
						<button type="button" class="btn btn-delete" onclick="fnGoDelete(); return false;"><i class="fa fa-trash"></i> 삭제</button>
						<% 
							} 
						%>
				<%-- 	<% 
						} 
					%> --%>
					</div>
				</div>

			</div>

			</form>
		</section>
	</div>
	<!-- footer -->
	<%@ include file="/common/inc/footer.jspf" %>
	<!-- //fooer -->
</div>
</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
