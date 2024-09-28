<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>

<jsp:useBean id="resultList"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="pageNavigationVo" class="com.whomade.kycarrots.framework.common.page.vo.pageNavigationVo" scope="request"/>
<jsp:useBean id="navigationBar" class="java.lang.String" scope="request"/>
<jsp:useBean id="boardComboStr"  type="java.util.List" class="java.util.ArrayList" scope="request"/>



<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>

<html>
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>
	<script type="text/javascript">
	//<![CDATA[
		$(function(){
			// 검색 조건 엔터시 이벤트
			$('[name=sch_text]').on({
				'keydown' : function(e){
					if(e.which == 13){
						e.preventDefault();
					}
				},
				'keyup' : function(e){
					if(e.which == 13){
						fnSearch();
					}
				}
			});
		});
		
		// 페이지 이동
		function fnGoPage(currentPage){
			$('#currentPage').val(currentPage);
			$('#aform').attr({ action : '/mgt/group/selectPageListPlanFGroup.do', method : 'post' }).submit();
		}
		
		// 등록폼 이동
		function fnInsertForm(){
			$('#aform').attr({ action : '/mgt/group/insertFormPlanFGroup.do', method : 'post' }).submit();
		}

		// 상세조회
		function fnSelect(pGROUP_ID){
			$('[name=GROUP_ID]').val(pGROUP_ID);
			$('#aform').attr({ action : '/mgt/group/selectPlanFGroup.do', method : 'get' }).submit();
		}
		
		// 검색
		function fnSearch(){
			$('#currentPage').val('1');
			$('#aform').attr({ action : '/mgt/group/selectPageListPlanFGroup.do', method : 'post' }).submit();
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
		<div id="navi"><i class="fa fa-home f12 color-lgray"></i><span class="blind">home</span> &rt; <span class="text" id="spnavi"></span></div>
    	<div id="pagetitle">
    	</div>
		</section>
		<!-- Main content -->
		<section class="content container-fluid">
			<div class="row">
		   		<div class="col-12">
					<form role="form" id="aform" method="post" action="/mgt/group/selectPageListPlanFGroup.do">
					<input type="hidden" 	name="GROUP_ID" />

					<!-- top search box // -->
					<div class="box-header form-inline">
						<input type="text" class="form-control w-25" name="sch_text" title="검색어를 입력하세요." value="<%=param.getString("sch_text")%>" />
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
							<% if("ROLE_ADMIN".equals(ssAuthorId)){ %>
							<button type="button" class="btn btn-info btn-write" onclick="fnInsertForm(); return false;"><i class="fa fa-plus"></i> 포인트 등록</button>
							<% } %>
						</div>

					</div>
					<!-- // box header -->

					<!-- list box // -->
					<div class="box-body no-pad-top table-responsive">
						<table class="table table-bordered table-hover">
		                <tr class="text-center">
		                  <th>No</th>
		                  <th>회원사명</th>
		                  <th>포인트</th>
		                  <th>잔여포인트</th>
		                </tr>
		                <tr style="cursor:pointer;cursor:hand;" onclick="fnSelect('1'); return false;" class="text-center">
		                  <td>1</td>
		                  <td class="text-left">산학협력1</td>
		                  <td>24,000,000 Point</td>
		                  <td>10,000,000 Point</td>
		                </tr>
		                <tr style="cursor:pointer;cursor:hand;" onclick="fnSelect('1'); return false;" class="text-center">
		                  <td>1</td>
		                  <td class="text-left">산학협력1</td>
		                  <td>24,000,000 Point</td>
		                  <td>10,000,000 Point</td>
		                </tr>
		                <tr style="cursor:pointer;cursor:hand;" onclick="fnSelect('1'); return false;" class="text-center">
		                  <td>1</td>
		                  <td class="text-left">산학협력1</td>
		                  <td>24,000,000 Point</td>
		                  <td>10,000,000 Point</td>
		                </tr>

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
				<!-- content 영역 -->
				</div>
			</div><!-- //content -->	
		</section>
	</div>
	
	<!-- footer -->
	<%@ include file="/common/inc/footer.jspf" %>
	<!-- //fooer -->
	
</div><!-- ./wrapper -->

<script type="text/javascript">
</script>


</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
