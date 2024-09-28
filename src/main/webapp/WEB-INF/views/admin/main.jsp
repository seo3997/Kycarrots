<%@page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="param" 			class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="resultBoardList" 	type="java.util.List" class="java.util.ArrayList" scope="request"/>



<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>
<html>
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>

</head>
<body class="hold-transition skin-green-light" class="inspMgr">
<div class="wrapper">

	<!-- 헤더  -->
	<c:import url="/common/inc/header.do" charEncoding="utf-8" />
	<!-- 좌측 메뉴 -->


		<!-- Main content -->
		<!-- @@ 메인 콘텐츠 -->
		<div class="content-wrapper"  >
			<section class="content container-fluid">
<!-- 
				<h1 class="h5"></h1>
 -->
				<div class="row inspMgr-dash-row1">

					<div class="col-md-3">
						<!-- 좌측 박스 // -->
						<h2>재무계획작성 관리</h2>
						<div class="card">
							<!--
								반복이 필요하면
								여기부터 //							
							-->
							<div class="card-body"><i class="d-block">재무계획작성건수</i>
								<span class="d-block text-right confMgr-num">1<small class="confMgr-txt">건</small></span>
							</div>
							<!--
								// 여기까지 아래로 복붙!
							-->

							<div class="card-body"><i class="d-block">재무계획완료건수</i>
								<span class="d-block text-right confMgr-num">1<small class="confMgr-txt">명</small></span>
							</div>

						</div>
						<!-- // 좌측 박스 -->
					</div>

					<div class="col-md-9">

					<h2>
						게시판
						<small class="ml-4"></small>
						<a class="card-more" href="/mgt/mboard/selectPageListBoard.do"><i class="fa fa-plus"></i><span class="sr-only">게시판 전체보기</span></a>
					</h2>

					<!-- 우측 테이블 // -->
		            <div class="box-body table-responsive no-padding">
						<table class="table table-hover">
						<tr>
							<th>No</th>
							<th>제목</th>
							<th>등록자</th>
							<th>등록일자</th>
						</tr>
						<%
						for(int i = 0; i < resultBoardList.size(); i++){
							DataMap dataMap = (DataMap) resultBoardList.get(i);
						%>
						<tr class="text-center">
							<td><%=(i+1)%></td>
							<td><%=dataMap.getString("SJ")%></td>
							<td><%=dataMap.getString("REGISTER_NM") %></td>
							<td><%=dataMap.getString("REGIST_DT") %></td>
		                </tr>
						<%}%>
						<%	if(resultBoardList.size() == 0){	%>
						<tr>
							<td class="text-center" colspan="4" ><spring:message code="msg.data.empty" /></td>
						</tr>
						<%}%>
						</table>

		            </div>
					<!-- // 우측 테이블 -->
				</div>
			</div>
			
<!-- ----------------------------------------------------------------------------------------------------------------------------------------- -->


		</section>
	</div>
	<!-- footer -->
	<%@ include file="/common/inc/footer.jspf" %>
	<!-- //fooer -->
</div>


</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
