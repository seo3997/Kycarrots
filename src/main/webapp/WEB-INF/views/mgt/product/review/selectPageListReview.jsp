<%@ page import="java.lang.String"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@ page import="com.whomade.kycarrots.framework.common.page.vo.pageNavigationVo" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="resultList"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="productList"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="pageNavigationVo" class="com.whomade.kycarrots.framework.common.page.vo.pageNavigationVo" scope="request"/>
<jsp:useBean id="navigationBar" class="java.lang.String" scope="request"/>

<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>

<html>
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>
	<script type="text/javascript">
	//<![CDATA[
		function fnGoPage(currentPage){
			$('#currentPage').val(currentPage);
			$('#aform').attr({ action : '/mgt/product/review/selectPageListReview.do', method : 'get' }).submit();
		}
		
		function fnSearch(){
			$('#currentPage').val('1');
			$('#aform').attr({ action : '/mgt/product/review/selectPageListReview.do', method : 'get' }).submit();
		}

		function fnDelete(reviewId){
			if(confirm("리뷰를 숨기시겠습니까?")){
				$.ajax({
					url: '/rest/product/review/delete',
					type: 'POST',
					data: { reviewId: reviewId },
					success: function(res) {
						if(res.success) {
							alert("처리되었습니다.");
							location.reload();
						} else {
							alert(res.message || "오류가 발생했습니다.");
						}
					}
				});
			}
		}

		function fnRestore(reviewId){
			if(confirm("리뷰를 다시 노출하시겠습니까?")){
				$.ajax({
					url: '/rest/product/review/restore',
					type: 'POST',
					data: { reviewId: reviewId },
					success: function(res) {
						if(res.success) {
							alert("처리되었습니다.");
							location.reload();
						} else {
							alert(res.message || "오류가 발생했습니다.");
						}
					}
				});
			}
		}
	//]]>
	</script>
</head>
<body class="hold-transition skin-green-light sidebar-mini">
<div class="wrapper">
	<c:import url="/common/inc/header.do" charEncoding="utf-8" />
	<c:import url="/common/inc/menu.do" charEncoding="utf-8" />

	<div class="content-wrapper">
		<section class="content-header">
			<div id="navi"><i class="fa fa-home f12 color-lgray"></i>상품관리 > <span class="text">상품리뷰 관리</span></div>
			<div id="pagetitle">
				<h1>상품리뷰 관리</h1>
			</div>
		</section>

		<section class="content container-fluid">
			<div class="row">
				<div class="col-12">
					<form role="form" id="aform" method="get" action="/mgt/product/review/selectPageListReview.do">
												
					<div class="box-header">
						<div class="col-12 form-row">
							<div class="row col-md-6 mb-1 form-group form-inline">
								<label class="control-label col-md-2 px-0">상품선택</label>
								<div class="col-md-9  px-0 form-inline">
									<select class="form-control w-75" name="productId">
										<option value="">전체</option>
										<%
										for(int i = 0; i < productList.size(); i++){
											DataMap pMap = (DataMap) productList.get(i);
										%>
											<option value="<%=pMap.getString("PRODUCT_ID")%>" <%=pMap.getString("PRODUCT_ID").equals(param.getString("productId")) ? "selected" : ""%>><%=pMap.getString("TITLE")%></option>
										<%}%>
									</select>
								</div>
							</div>
							<div class="row col-md-6 mb-1 form-group form-inline">
								<label class="control-label col-md-2 px-0">검색어</label>
								<div class="col-md-9  px-0 form-inline">
									<input type="text" class="form-control w-75" name="sch_text" title="내용 또는 작성자 검색" placeholder="검색어를 입력하세요." value="<%=param.getString("sch_text")%>" />
								</div>
							</div>
						</div>
						<hr />
						<div class="col text-right">
							<button type="button" class="btn btn-search ml-2" onclick="fnSearch(); return false;"><i class="fa fa-search"></i>검색</button>
						</div>
					</div>

					<!-- list box // -->
					<div class="box-body no-pad-top table-responsive">
						<table class="table table-bordered table-hover">
							<colgroup>
								<col style="width: 50px;">
								<col style="width: 150px;">
								<col style="width: 100px;">
								<col>
								<col style="width: 100px;">
								<col style="width: 120px;">
								<col style="width: 80px;">
							</colgroup>
							<thead>
								<tr class="text-center">
									<th>No</th>
									<th>상품명</th>
									<th>별점</th>
									<th>내용</th>
									<th>작성자</th>
									<th>등록일</th>
									<th>관리</th>
								</tr>
							</thead>
						<tbody>
						<%
						int dataNo = pageNavigationVo.getCurrDataNo();
						for(int i = 0; i < resultList.size(); i++){
							DataMap dataMap = (DataMap) resultList.get(i);
						%>
							<tr class="text-center" <% if("N".equals(dataMap.getString("DISPLAY_YN"))) { %>style="background-color: #f4f4f4; color: #999;"<% } %>>
								<td><%=dataNo-i%></td>
								<td class="text-left"><%=dataMap.getString("PRODUCT_NM") %></td>
								<td class="text-center text-yellow">
									<% 
										int rating = dataMap.getInt("RATING");
										for(int r=1; r<=5; r++) { 
									%>
										<%= r <= rating ? "★" : "☆" %>
									<% } %>
								</td>
								<td class="text-left">
									<% if(!dataMap.getString("FILE_RLTV_PATH").isEmpty()) { %>
										<i class="fa fa-image text-blue"></i>
									<% } %>
									<% if("N".equals(dataMap.getString("DISPLAY_YN"))) { %>
										<span class="label label-default">숨김</span>
									<% } %>
									<%=dataMap.getString("CONTENTS") %>
								</td>
								<td class="text-center"><%=dataMap.getString("USER_NM") %></td>
								<td class="text-center"><%=dataMap.getString("REGIST_DT") %></td>
								<td class="text-center">
									<% if("Y".equals(dataMap.getString("DISPLAY_YN"))) { %>
										<button type="button" class="btn btn-xs btn-danger" onclick="fnDelete('<%=dataMap.getString("REVIEW_ID")%>'); return false;">삭제</button>
									<% } else { %>
										<button type="button" class="btn btn-xs btn-primary" onclick="fnRestore('<%=dataMap.getString("REVIEW_ID")%>'); return false;">복구</button>
									<% } %>
								</td>
							</tr>
						<%}%>
						<%if(resultList.size() == 0){%>
							<tr>
								<td colspan="7" class="text-center">등록된 리뷰가 없습니다.</td>
							</tr>
						<%}%>
						</tbody>
						</table>
					</div>

					<div class="box-footer text-center">
						${navigationBar}
					</div>
					</form>
				</div>
			</div>
		</section>
	</div>

	<%@ include file="/common/inc/footer.jspf" %>
</div>

</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
