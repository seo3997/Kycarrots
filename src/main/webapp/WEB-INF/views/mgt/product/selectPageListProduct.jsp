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
<jsp:useBean id="saleStatusComboStr"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="cateooryMComboStr"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="areaMComboStr"  type="java.util.List" class="java.util.ArrayList" scope="request"/>



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
			$('#aform').attr({ action : '/mgt/product/selectPageListProduct.do', method : 'get' }).submit();
		}
		
		// 등록폼 이동
		function fnInsertForm(){
			$('#aform').attr({ action : '/mgt/product/insertFormProduct.do', method : 'post' }).submit();
		}

		// 상세조회
		function fnSelect(productId){
			$('[name=productId]').val(productId);
			$('#aform').attr({ action : '/mgt/product/selectProduct.do', method : 'get' }).submit();
		}
		
		// 검색
		function fnSearch(){
			$('#currentPage').val('1');
			$('#aform').attr({ action : '/mgt/product/selectPageListProduct.do', method : 'get' }).submit();
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
					<form role="form" id="aform" method="post" action="/mgt/product/selectPageListProduct.do">
					<input type="hidden" 	name="productId" />

					<div class="box-header">

						<div class="col-12 form-row">
							<div class="row col-md-6 mb-1 form-group form-inline">
								<label for="sch_sale_status_code" class="control-label col-md-2 px-0">판매 상태</label>
								<div class="col-md-9 px-0">
									<select id="sch_sale_status_code" name="sch_sale_status_code" class="form-control w-100">
										<%=CommboUtil.getComboStr(saleStatusComboStr, "CODE", "CODE_NM", param.getString("sch_sale_status_code") , "A")%>
									</select>
								</div>
							</div>
							<div class="row col-md-6 mb-1 form-group form-inline">
								<label for="sch_category_m_code" class="control-label col-md-2 px-0">카테고리</label>
								<div class="col-md-9 px-0">
									<select id="sch_category_m_code" name="sch_category_m_code" class="form-control w-100">
										<%=CommboUtil.getComboStr(cateooryMComboStr, "CODE", "CODE_NM", param.getString("sch_category_m_code") , "A")%>
									</select>
								</div>
							</div>
						</div>
						<div class="col-12 form-row">
								<div class="row col-md-6 mb-1 form-group form-inline">
								<label class="control-label col-md-2 px-0">상품명</label>
								<div class="col-md-9  px-0 form-inline">
									<input type="hidden"  id="search_list_st" name="sch_type" value="<%=param.getString("sch_type")%>" />
									<input type="text" class="form-control w-75" name="sch_text" title="검색어를 입력하세요." value="<%=param.getString("sch_text")%>" />
								</div>
							</div>
							<div class="row col-md-6 mb-1 form-group form-inline">
								<label for="sch_area_m_code" class="control-label col-md-2 px-0">지역</label>
								<div class="col-md-9 px-0">
									<select id="sch_area_m_code" name="sch_area_m_code" class="form-control w-100">
										<%=CommboUtil.getComboStr(areaMComboStr, "CODE", "CODE_NM", param.getString("sch_area_m_code") , "A")%>
									</select>
								</div>
							</div>
						</div>
						<hr />
						<div class="col text-right">
							<button type="button" class="btn btn-search ml-2" onclick="fnSearch(); return false;"><i class="fa fa-search"></i>검색</button>
						</div>

					</div>


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
							<button type="button" class="btn btn-info btn-write" onclick="fnInsertForm(); return false;"><i class="fa fa-plus"></i> 상품 등록</button>
							<% } %>
						</div>

					</div>
					<!-- // box header -->

					<!-- list box // -->
					<div class="box-body no-pad-top table-responsive">
						<table class="table table-bordered table-hover">
						 <colgroup>
						  <col style="width:6%;">   <!-- No -->
						  <col style="width:8%;">  <!-- 판매상태 -->
						  <col style="width:14%;">  <!-- 상품 -->
						  <col style="width:8%;">  <!-- 상품가격 -->
						  <col style="width:8%;">  <!-- 카테고리 -->
						  <col style="width:8%;">  <!-- 카테고리 -->
						  <col>  <!-- 내용 -->
						  <col style="width:10%;">  <!-- 작성일 -->
						</colgroup>
						<thead>
		                <tr class="text-center">
		                  <th>No</th>
		                  <th>판매상태</th>
		                  <th>상품</th>
		                  <th>상품가격</th>
		                  <th>카테고리</th>
		                  <th>지역</th>
		                  <th>내용</th>
		                  <th>작성일</th>
		                </tr>
		                </thead>
						<%
						int dataNo = pageNavigationVo.getCurrDataNo();
						for(int i = 0; i < resultList.size(); i++){
							DataMap dataMap = (DataMap) resultList.get(i);
						%>
		                <tr style="cursor:pointer;cursor:hand;" onclick="fnSelect('<%=dataMap.getString("PRODUCT_ID")%>'); return false;" class="text-center">
		                  <td><%=dataNo-i%></td>
						  <td class="text-left"><%=dataMap.getString("SALE_STATUS_NM") %></td>
						  <td class="text-left"><%=dataMap.getString("TITLE") %></td>
		                  <td class="text-right"><%=StringUtil.setComma(dataMap.getString("PRICE")) %>원</td>
		                  <td class="text-left"><%=dataMap.getString("CATEGORY_MID_NM") %> / <%=dataMap.getString("CATEGORY_SCLS_NM") %></td>
		                  <td class="text-left"><%=dataMap.getString("AREA_MID_NM") %> / <%=dataMap.getString("ASAREA_SCLS_NM") %></td>
		                  <td class="text-left"><%=StringUtil.getReSize(dataMap.getString("DESCRIPTION"),50) %></td>
		                  <td><%=dataMap.getString("REGIST_DT") %></td>
		                </tr>
						<%}%>
						<%if(resultList.size() == 0){%>
							<tr>
								<td class="text-center" colspan="5" ><spring:message code="msg.data.empty" /></td>
							</tr>
						<%}%>

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
	$('#sch_sale_status_code').on('change', function(e) {
		fnSearch();
	});
	$('#sch_category_m_code').on('change', function(e) {
		fnSearch();
	});
	$('#sch_area_m_code').on('change', function(e) {
		fnSearch();
	});

</script>


</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
