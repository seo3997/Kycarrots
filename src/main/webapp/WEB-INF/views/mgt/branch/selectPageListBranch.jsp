<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>

<jsp:useBean id="resultList"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>

<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>

<html>
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title>지점 관리 - <%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>
	
	<script type="text/javascript">
		function fnSearch(){
			$('#currentPage').val('1');
			$('#aform').attr({ action : '/mgt/branch/selectPageListBranch.do', method : 'get' }).submit();
		}

		function fnGoPage(currentPage){
			$('#currentPage').val(currentPage);
			$('#aform').attr({ action : '/mgt/branch/selectPageListBranch.do', method : 'get' }).submit();
		}

		function fnInsertForm(){
			$('#aform').attr({ action : '/mgt/branch/insertFormBranch.do', method : 'get' }).submit();
		}

		function fnSelect(branchId){
			$('[name=branchId]').val(branchId);
			$('#aform').attr({ action : '/mgt/branch/selectBranch.do', method : 'get' }).submit();
		}

        function openRegisterModal() {
            // This is a placeholder for a registration modal or window
            alert("지점 등록 기능은 API로 개발되었습니다. 관리자 페이지 UI 기획에 맞춰 모달창 등으로 구현될 예정입니다.");
        }
	</script>
</head>
<body class="hold-transition skin-green-light sidebar-mini">
<div class="wrapper">
	<c:import url="/common/inc/header.do" charEncoding="utf-8" />
	<c:import url="/common/inc/menu.do" charEncoding="utf-8" />

	<div class="content-wrapper">
		<section class="content-header">
		    <div id="navi">
                <i class="fa fa-home f12 color-lgray"></i>
                <span class="blind">home</span> > 지점관리 > <span class="text">지점 리스트</span>
            </div>
    	    <div id="pagetitle">
                <h1>지점 및 판매자 관리</h1>
    	    </div>
		</section>

		<section class="content container-fluid">
			<div class="row">
		   		<div class="col-12">
					<form role="form" id="aform" method="get" action="/mgt/branch/selectPageListBranch.do">
						<input type="hidden" name="branchId" id="branchId" value="" />
					
					<!-- top search box // -->
					<div class="box-header form-inline">
						<input type="text" class="form-control w-25" name="sch_text" title="검색어를 입력하세요." value="${param.sch_text}" />
						<button type="button" class="btn btn-top-search" onclick="fnSearch(); return false;"><i class="fa fa-search"></i></button>
					</div>
					<!-- // top search box -->

					<!-- box header // -->
					<div class="box-top col-12 row justify-content-between">
						<div class="col-auto text-left py-2">
							총 <strong>${pageNavigationVo.totalCount}</strong>건
						</div>
						<div class="col-auto form-inline text-right">
							<button type="button" class="btn btn-info btn-write" onclick="fnInsertForm(); return false;"><i class="fa fa-plus"></i> 지점 등록</button>
						</div>
					</div>
					<!-- // box header -->

					<!-- list box // -->
					<div class="box-body no-pad-top table-responsive">
						<table class="table table-bordered table-hover">
			    			<thead>
				    				<tr class="text-center">
                                        <th>No</th>
					    				<th>지점명</th>
						    			<th>지점코드</th>
							    		<th>대표자</th>
								    	<th>사업자번호</th>
                                        <th>도메인</th>
                                        <th>상태</th>
									    <th>등록일</th>
								    </tr>
						    </thead>
						    <tbody>
                            <c:set var="dataNo" value="${pageNavigationVo.currDataNo}"/>							
						    <c:forEach var="item" items="${resultList}" varStatus="status">
							    <tr class="text-center">
                                    <td>${dataNo - status.index}</td>
								    <td class="text-left"><a href="#" onclick="fnSelect('${item.BRANCH_ID}'); return false;">${item.BRANCH_NAME}</a></td>
    								<td>${item.BRANCH_CODE}</td>
	    							<td>${item.REPRESENTATIVE_NAME}</td>
		    						<td>${item.BUSINESS_NUMBER}</td>
                                    <td class="text-left">${item.DOMAIN_URL}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${item.BRANCH_STATUS == 'RUNNING'}"><span class="badge badge-success">운영중</span></c:when>
                                            <c:otherwise><span class="badge badge-default">${item.BRANCH_STATUS}</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${item.REGIST_DT}</td>
							    </tr>
						    </c:forEach>
							<c:if test="${empty resultList}">
								<tr>
									<td class="text-center" colspan="8"><spring:message code="msg.data.empty" /></td>
								</tr>
							</c:if>
						    </tbody>
					    </table>
				    </div>
					<!-- // list box -->

					<!-- box footer // -->
					<div class="box-footer text-center">
						<c:if test="${fn:length(resultList) > 0}">
							${navigationBar}
						</c:if>
					</div>
					<!-- // box footer -->

				</form>
				</div>
			</div>
		</section>
	</div>
	
	<%@ include file="/common/inc/footer.jspf" %>
</div>

<form id="aform" method="get">
    <input type="hidden" id="currentPage" name="curPage" value="${param.curPage}">
</form>

</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
