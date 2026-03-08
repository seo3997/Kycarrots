<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@ page import="com.whomade.kycarrots.framework.common.page.vo.pageNavigationVo" %>
<%@ page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@ page import="com.whomade.kycarrots.framework.common.constant.Const"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>

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
			$('#aform').attr({ action : '/mgt/product/qna/selectPageListQna.do', method : 'get' }).submit();
		}
		
		// 검색
		function fnSearch(){
			$('#currentPage').val('1');
			$('#aform').attr({ action : '/mgt/product/qna/selectPageListQna.do', method : 'get' }).submit();
		}

		// 삭제
		function fnDelete(qnaId){
			if(confirm("문의를 삭제하시겠습니까?")){
				$.post('/rest/product/qna/delete', { qnaId: qnaId }, function(res) {
					if(res.success) {
						alert("삭제되었습니다.");
						location.reload();
					} else {
						alert(res.message || "삭제 중 오류가 발생했습니다.");
					}
				});
			}
		}

		// 답변
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
			<div id="navi"><i class="fa fa-home f12 color-lgray"></i><span class="blind">home</span> &rt; <span class="text" id="spnavi">상품관리 &rt; 상품문의 관리</span></div>
			<div id="pagetitle">
				<h1>상품문의 관리</h1>
			</div>
		</section>

		<section class="content container-fluid">
			<div class="row">
				<div class="col-12">
					<form role="form" id="aform" method="get" action="/mgt/product/qna/selectPageListQna.do">
						
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
										<input type="text" class="form-control w-75" name="sch_text" title="제목 또는 내용 검색" placeholder="검색어를 입력하세요." value="<%=param.getString("sch_text")%>" />
									</div>
								</div>
							</div>
							<hr />
							<div class="col text-right">
								<button type="button" class="btn btn-search ml-2" onclick="fnSearch(); return false;"><i class="fa fa-search"></i>검색</button>
							</div>
						</div>

						<div class="box-body no-pad-top table-responsive">
							<table class="table table-bordered table-hover">
								<colgroup>
									<col style="width:6%;">   <!-- No -->
									<col style="width:15%;">  <!-- 상품명 -->
									<col style="width:8%;">   <!-- 상태 -->
									<col>                     <!-- 제목/내역 -->
									<col style="width:10%;">  <!-- 작성자 -->
									<col style="width:10%;">  <!-- 등록일 -->
									<col style="width:12%;">  <!-- 관리 -->
								</colgroup>
								<thead>
								<tr class="text-center">
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
								<%
								int dataNo = pageNavigationVo.getCurrDataNo();
								for(int i = 0; i < resultList.size(); i++){
									DataMap dataMap = (DataMap) resultList.get(i);
								%>
								<tr class="text-center">
									<td><%=dataNo-i%></td>
									<td class="text-left"><%=dataMap.getString("PRODUCT_NM") %></td>
									<td>
										<c:set var="status" value="<%=dataMap.getString(\"QNA_STATUS\")%>"/>
										<c:choose>
											<c:when test="${status eq '20'}">
												<span class="label label-success">답변완료</span>
											</c:when>
											<c:otherwise>
												<span class="label label-warning">대기중</span>
											</c:otherwise>
										</c:choose>
									</td>
									<td class="text-left">
										<% if(dataMap.getString("SECRET_YN").equals("Y")) { %>
											<i class="fa fa-lock text-muted" title="비밀글"></i>
										<% } %>
										<%=dataMap.getString("TITLE") %>
									</td>
									<td><%=dataMap.getString("USER_NM") %></td>
									<td><%=dataMap.getString("REGIST_DT") %></td>
									<td>
										<button type="button" class="btn btn-xs btn-primary" onclick="fnAnswer('<%=dataMap.getString("QNA_ID")%>'); return false;">답변</button>
										<button type="button" class="btn btn-xs btn-danger" onclick="fnDelete('<%=dataMap.getString("QNA_ID")%>'); return false;">삭제</button>
									</td>
								</tr>
								<%}%>
								<%if(resultList.size() == 0){%>
									<tr>
										<td class="text-center" colspan="7" ><spring:message code="msg.data.empty" /></td>
									</tr>
								<%}%>
								</tbody>
							</table>
						</div>

						<div class="box-footer text-center">
							<c:if test="${fn:length(resultList) > 0}">
								${navigationBar }
							</c:if>
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
