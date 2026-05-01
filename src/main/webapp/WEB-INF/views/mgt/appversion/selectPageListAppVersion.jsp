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
	<title>앱 버전 관리 - <%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>
	
	<script type="text/javascript">
		function fnSearch(){
			$('#currentPage').val('1');
			$('#aform').attr({ action : '/mgt/appversion/selectPageListAppVersion.do', method : 'get' }).submit();
		}

		function fnGoPage(currentPage){
			$('#currentPage').val(currentPage);
			$('#aform').attr({ action : '/mgt/appversion/selectPageListAppVersion.do', method : 'get' }).submit();
		}

		function fnInsertForm(){
			$('#aform').attr({ action : '/mgt/appversion/insertFormAppVersion.do', method : 'get' }).submit();
		}

		function fnSelect(osType, latestVersion){
			$('[name=osType]').val(osType);
			$('[name=latestVersion]').val(latestVersion);
			$('#aform').attr({ action : '/mgt/appversion/selectAppVersion.do', method : 'get' }).submit();
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
                <span class="blind">home</span> > 시스템관리 > <span class="text">앱 버전 관리</span>
            </div>
    	    <div id="pagetitle">
                <h1>앱 버전 관리</h1>
    	    </div>
		</section>

		<section class="content container-fluid">
			<div class="row">
		   		<div class="col-12">
					<form role="form" id="aform" method="get" action="/mgt/appversion/selectPageListAppVersion.do">
						<input type="hidden" name="latestVersion" value="" />
						<input type="hidden" name="osType" value="" />
						<input type="hidden" name="latestVersion" value="" />
						<input type="hidden" id="currentPage" name="curPage" value="${param.curPage}">
					
					<!-- top search box // -->
					<div class="box-header form-inline">
                        <select name="osType" class="form-control mr-2">
                            <option value="">전체 플랫폼</option>
                            <option value="ANDROID" <c:if test="${param.osType == 'ANDROID'}">selected</c:if>>ANDROID</option>
                            <option value="IOS" <c:if test="${param.osType == 'IOS'}">selected</c:if>>IOS</option>
                        </select>
						<button type="button" class="btn btn-top-search" onclick="fnSearch(); return false;"><i class="fa fa-search"></i></button>
					</div>
					<!-- // top search box -->

					<!-- box header // -->
					<div class="box-top col-12 row justify-content-between">
						<div class="col-auto text-left py-2">
							총 <strong>${pageNavigationVo.totalCount}</strong>건
						</div>
						<div class="col-auto form-inline text-right">
							<button type="button" class="btn btn-info btn-write" onclick="fnInsertForm(); return false;"><i class="fa fa-plus"></i> 새 버전 등록</button>
						</div>
					</div>
					<!-- // box header -->

					<!-- list box // -->
					<div class="box-body no-pad-top table-responsive">
						<table class="table table-bordered table-hover">
			    			<thead>
				    				<tr class="text-center">
                                        <th>No</th>
					    				<th>플랫폼</th>
						    			<th>최신 버전</th>
							    		<th>최소 지원 버전</th>
								    	<th>사용여부</th>
									    <th>등록일</th>
								    </tr>
						    </thead>
						    <tbody>
                            <c:set var="dataNo" value="${pageNavigationVo.currDataNo}"/>							
						    <c:forEach var="item" items="${resultList}" varStatus="status">
							    <tr class="text-center">
                                    <td>${dataNo - status.index}</td>
								    <td>${item.OS_TYPE}</td>
    								<td><a href="#" onclick="fnSelect('${item.OS_TYPE}', '${item.LATEST_VERSION}'); return false;">${item.LATEST_VERSION}</a></td>
	    							<td>${item.MIN_VERSION}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${item.USE_YN == 'Y'}"><span class="badge badge-success">사용중</span></c:when>
                                            <c:otherwise><span class="badge badge-danger">미사용</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${item.REGIST_DT}</td>
							    </tr>
						    </c:forEach>
							<c:if test="${empty resultList}">
								<tr>
									<td class="text-center" colspan="6"><spring:message code="msg.data.empty" /></td>
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

</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
