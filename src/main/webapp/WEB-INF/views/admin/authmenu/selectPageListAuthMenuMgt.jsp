<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>
	<script type="text/javascript">
	//<![CDATA[
		function fnGoPage(currentPage){
			$("#currentPage").val(currentPage);
			$("#aform").attr({action:"/admin/author/selectPageListAuthorMgt.do", method:'post'}).submit();
		}
		
		function fnInsertForm(){

			document.location.href="/admin/author/insertFormAuthorMgt.do";
		}

		function fnAuthMenuList(auth_id){
			//ROLE_TEST	테스트관리자	테스트관리자	1	2015/09/01	admin	2015-09-01 오후 8:07:47

			$("#author_id").val(auth_id);
			$("#aform").attr({action:"/admin/authmenu/selectListAuthMenuMgt.do", method:'post'}).submit();
		}


		function fnSearch(){

			$("#aform").attr({action:"/admin/author/selectPageListAuthorMgt.do", method:'post'}).submit();
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
	
	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper">
		<!-- Content Header (Page header) -->
		<section class="content-header">
		<div id="navi"><i class="fa fa-home f12 color-lgray"></i><span class="blind">home</span>><span class="text" id="spnavi"></span></div>
    	<div id="pagetitle">
    	</div>
		</section>
		
		<!-- Main content -->
		<section class="content container-fluid">
			<div class="row">
				<div class="col-12">
					<form role="form" id="aform" method="post" action="/admin/authmenu/selectPageListAuthMenuMgt.do">
						<input type="hidden" id="author_id" name="author_id" />

						<!-- top search box // -->
						<div class="box-header form-inline">

							<input type="text" name="sch_auth_nm" class="form-control w-25" placeholder="권한명" value="${param.sch_auth_nm }" />
							<button type="button" class="btn btn-top-search" onclick="fnSearch(); return false;"><i class="fa fa-search"></i></button>

						</div>
						<!-- // top search box -->

					<!-- box header // -->
					<div class="box-top col-12 row justify-content-between">

						<div class="col-auto text-left"><!-- footer box left -->
<!-- 검색결과 갯수
							<p class="searchword-result">검색결과 <strong>9,999</strong>개</p>
// -->
						</div>
						<div class="col-auto form-inline text-right"><!-- footer box right -->
<!-- sort combo
							<select class="form-control w-auto">
								<option>100개</option>
							</select>
// -->
						</div>

					</div>
					<!-- // box header -->

						<!-- list box // -->
						<div class="box-body no-pad-top table-responsive">
							<table class="table table-bordered table-hover">
								<colgroup>
									<col width="50">
									<col width="*">
									<col width="150">
									<col width="150">
								</colgroup>
								<thead>
									<tr>
										<th class="text-center">No</th>
										<th class="text-center">권한명</th>
										<th class="text-center">등록자</th>
										<th class="text-center">등록일</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="author" items="${resultList}" varStatus="status">
									<tr style="cursor:pointer;cursor:hand;" onclick="fnAuthMenuList('${author.AUTHOR_ID}')">
										<td class="text-center"><c:out value="${pageNavigationVo.totalCount - (pageNavigationVo.currentPage-1) * pageNavigationVo.rowPerPage - status.index}" /></td>
										<td>${author.AUTHOR_NM }</td>
										<td class="text-center">${author.NCNM }</td>
										<td class="text-center">${author.RGST_YMD }</td>
									</tr>
									</c:forEach>
									<c:if test="${fn:length(resultList) == 0}">
									<tr>
										<td class="text-center" colspan="4" ><spring:message code="msg.data.empty" /></td>
									</tr>
									</c:if>
								</tbody>
							</table>
						</div>
						<!-- // list box -->

						<!-- box footer // -->
						<div class="box-footer text-center">
							<c:if test="${fn:length(resultList) > 0}">
								${navigationBar }
							</c:if>
						</div>
						<!-- // box footer -->

					</form>
				</div>
			</div>
		</section>
	</div>
	
	<!-- footer -->
	<%@ include file="/common/inc/footer.jspf" %>
	<!-- //fooer -->
	
</div><!-- ./wrapper -->
</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
