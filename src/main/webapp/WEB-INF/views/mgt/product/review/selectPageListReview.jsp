<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="resultList"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
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
			if(confirm("리뷰를 삭제하시겠습니까?")){
				$.ajax({
					url: '/rest/product/review/delete',
					type: 'POST',
					data: { reviewId: reviewId },
					success: function(res) {
						if(res.success) {
							alert("삭제되었습니다.");
							location.reload();
						} else {
							alert(res.message || "삭제 중 오류가 발생했습니다.");
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
			<div id="navi"><i class="fa fa-home f12 color-lgray"></i> Home &rt; 상품관리 &rt; <span class="text">상품리뷰 관리</span></div>
			<div id="pagetitle">
				<h1>상품리뷰 관리</h1>
			</div>
		</section>

		<section class="content container-fluid">
			<div class="row">
				<div class="col-12">
					<form role="form" id="aform" method="get" action="/mgt/product/review/selectPageListReview.do">
						<input type="hidden" id="currentPage" name="currentPage" value="<c:out value="${param.currentPage}"/>"/>
						
						<div class="box box-primary">
							<div class="box-header with-border">
								<div class="form-row">
									<div class="col-md-10">
										<input type="text" class="form-control" name="sch_text" placeholder="내용 또는 작성자 검색" value="<c:out value="${param.sch_text}"/>">
									</div>
									<div class="col-md-2 text-right">
										<button type="button" class="btn btn-primary w-100" onclick="fnSearch();"><i class="fa fa-search"></i> 검색</button>
									</div>
								</div>
							</div>

							<div class="box-body table-responsive no-padding">
								<table class="table table-hover table-bordered">
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
										<tr class="bg-gray text-center">
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
										<c:forEach var="item" items="${resultList}" varStatus="status">
											<tr>
												<td class="text-center">${totalCount - ((param.currentPage - 1) * 10) - status.index}</td>
												<td><c:out value="${item.PRODUCT_NM}"/></td>
												<td class="text-center text-yellow">
													<c:forEach begin="1" end="${item.RATING}">★</c:forEach><c:forEach begin="1" end="${5 - item.RATING}">☆</c:forEach>
												</td>
												<td>
													<c:if test="${not empty item.FILE_RLTV_PATH}">
														<i class="fa fa-image text-blue"></i>
													</c:if>
													<c:out value="${item.CONTENTS}"/>
												</td>
												<td class="text-center"><c:out value="${item.USER_NM}"/></td>
												<td class="text-center"><c:out value="${item.REGIST_DT}"/></td>
												<td class="text-center">
													<button type="button" class="btn btn-xs btn-danger" onclick="fnDelete('${item.REVIEW_ID}')">삭제</button>
												</td>
											</tr>
										</c:forEach>
										<c:if test="${empty resultList}">
											<tr>
												<td colspan="7" class="text-center">등록된 리뷰가 없습니다.</td>
											</tr>
										</c:if>
									</tbody>
								</table>
							</div>

							<div class="box-footer text-center">
								${navigationBar}
							</div>
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
