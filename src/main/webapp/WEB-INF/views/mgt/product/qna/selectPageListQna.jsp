<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

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
			$('#aform').attr({ action : '/mgt/product/qna/selectPageListQna.do', method : 'get' }).submit();
		}
		
		function fnSearch(){
			$('#currentPage').val('1');
			$('#aform').attr({ action : '/mgt/product/qna/selectPageListQna.do', method : 'get' }).submit();
		}

		function fnDelete(qnaId){
			if(confirm("문의를 삭제하시겠습니까?")){
				$.ajax({
					url: '/rest/product/qna/delete',
					type: 'POST',
					data: { qnaId: qnaId },
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

		function fnAnswer(qnaId){
			const answer = prompt("답변 내용을 입력해주세요.");
			if(answer && answer.trim()){
				$.ajax({
					url: '/rest/product/qna/answer',
					type: 'POST',
					data: { qnaId: qnaId, answerContents: answer },
					success: function(res) {
						if(res.success) {
							alert("답변이 등록되었습니다.");
							location.reload();
						} else {
							alert(res.message || "답변 등록 중 오류가 발생했습니다.");
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
			<div id="navi"><i class="fa fa-home f12 color-lgray"></i> Home &rt; 상품관리 &rt; <span class="text">상품문의 관리</span></div>
			<div id="pagetitle">
				<h1>상품문의 관리</h1>
			</div>
		</section>

		<section class="content container-fluid">
			<div class="row">
				<div class="col-12">
					<form role="form" id="aform" method="get" action="/mgt/product/qna/selectPageListQna.do">
						<input type="hidden" id="currentPage" name="currentPage" value="<c:out value="${param.currentPage}"/>"/>
						
						<div class="box box-success">
							<div class="box-header with-border">
								<div class="form-row">
									<div class="col-md-10">
										<input type="text" class="form-control" name="sch_text" placeholder="제목 또는 내용 검색" value="<c:out value="${param.sch_text}"/>">
									</div>
									<div class="col-md-2 text-right">
										<button type="button" class="btn btn-success w-100" onclick="fnSearch();"><i class="fa fa-search"></i> 검색</button>
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
										<col style="width: 150px;">
									</colgroup>
									<thead>
										<tr class="bg-gray text-center">
											<th>No</th>
											<th>상품명</th>
											<th>상태</th>
											<th>제목</th>
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
												<td class="text-center">
													<c:choose>
														<c:when test="${item.QNA_STATUS eq '20'}">
															<span class="label label-success">답변완료</span>
														</c:when>
														<c:otherwise>
															<span class="label label-warning">대기중</span>
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:if test="${item.SECRET_YN eq 'Y'}">
														<i class="fa fa-lock text-muted"></i>
													</c:if>
													<c:out value="${item.TITLE}"/>
												</td>
												<td class="text-center"><c:out value="${item.USER_NM}"/></td>
												<td class="text-center"><c:out value="${item.REGIST_DT}"/></td>
												<td class="text-center">
													<button type="button" class="btn btn-xs btn-primary" onclick="fnAnswer('${item.QNA_ID}')">답변</button>
													<button type="button" class="btn btn-xs btn-danger" onclick="fnDelete('${item.QNA_ID}')">삭제</button>
												</td>
											</tr>
										</c:forEach>
										<c:if test="${empty resultList}">
											<tr>
												<td colspan="7" class="text-center">등록된 문의가 없습니다.</td>
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
