<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>

<html>
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title>앱 버전 상세 - <%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>
	
	<script type="text/javascript">
		function fnList(){
			$('#aform').attr({ action : '/mgt/appversion/selectPageListAppVersion.do', method : 'get' }).submit();
		}

		function fnUpdateForm(){
			$('#aform').attr({ action : '/mgt/appversion/updateFormAppVersion.do', method : 'get' }).submit();
		}

		function fnSelect(APP_VER_NO){
			$('[name=APP_VER_NO]').val(APP_VER_NO);
			$('#aform').attr({ action : '/mgt/appversion/selectAppVersion.do', method : 'get' }).submit();
		}

		function fnDelete(){
			if(confirm('삭제하시겠습니까?')){
				$('#aform').attr({ action : '/mgt/appversion/deleteAppVersion.do', method : 'post' }).submit();
			}
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
                <span class="blind">home</span> > 시스템관리 > <span class="text">앱 버전 상세</span>
            </div>
    	    <div id="pagetitle">
                <h1>앱 버전 상세</h1>
    	    </div>
		</section>

		<section class="content container-fluid">
			<div class="row">
		   		<div class="col-12">
					<div class="box box-primary">
						<form role="form" id="aform" method="get">
							<input type="hidden" name="APP_VER_NO" value="${resultMap.APP_VER_NO}" />
							
							<div class="box-body">
								<table class="table table-bordered">
									<colgroup>
										<col width="20%" />
										<col width="80%" />
									</colgroup>
									<tbody>
										<tr>
											<th>플랫폼</th>
											<td>${resultMap.OS_TYPE}</td>
										</tr>
										<tr>
											<th>최신 버전</th>
											<td>${resultMap.LATEST_VERSION}</td>
										</tr>
										<tr>
											<th>최소 지원 버전</th>
											<td>${resultMap.MIN_VERSION}</td>
										</tr>
										<tr>
											<th>업데이트 메시지</th>
											<td>${fn:replace(resultMap.UPDATE_MSG, cn, br)}</td>
										</tr>
										<tr>
											<th>스토어 URL</th>
											<td><a href="${resultMap.STORE_URL}" target="_blank">${resultMap.STORE_URL}</a></td>
										</tr>
										<tr>
											<th>사용여부</th>
											<td>
												<c:choose>
													<c:when test="${resultMap.USE_YN == 'Y'}">사용중</c:when>
													<c:otherwise>미사용</c:otherwise>
												</c:choose>
											</td>
										</tr>
										<tr>
											<th>등록일</th>
											<td>${resultMap.REGIST_DT} (${resultMap.REGUSR_NO})</td>
										</tr>
										<tr>
											<th>수정일</th>
											<td>${resultMap.UPDT_DT} (${resultMap.UPDFUSR_NO})</td>
										</tr>
									</tbody>
								</table>
							</div>
							
							<div class="box-footer text-center">
								<button type="button" class="btn btn-default" onclick="fnList(); return false;">목록</button>
								<button type="button" class="btn btn-warning" onclick="fnUpdateForm(); return false;">수정</button>
								<button type="button" class="btn btn-danger" onclick="fnDelete(); return false;">삭제</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</section>
	</div>
	
	<%@ include file="/common/inc/footer.jspf" %>
</div>
</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
