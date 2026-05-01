<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>

<html>
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title>앱 버전 수정 - <%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>
	
	<script type="text/javascript">
		function fnDetail(){
			$('#aform').attr({ action : '/mgt/appversion/selectAppVersion.do', method : 'get' }).submit();
		}

		function fnSave(){
			if($('#osType').val() == ''){
				alert('플랫폼을 선택해주세요.');
				$('#osType').focus();
				return;
			}
			if($('#latestVersion').val() == ''){
				alert('최신 버전을 입력해주세요.');
				$('#latestVersion').focus();
				return;
			}
			if($('#minVersion').val() == ''){
				alert('최소 지원 버전을 입력해주세요.');
				$('#minVersion').focus();
				return;
			}

			if(confirm('수정하시겠습니까?')){
				$('#aform').attr({ action : '/mgt/appversion/updateAppVersion.do', method : 'post' }).submit();
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
                <span class="blind">home</span> > 시스템관리 > <span class="text">앱 버전 수정</span>
            </div>
    	    <div id="pagetitle">
                <h1>앱 버전 수정</h1>
    	    </div>
		</section>

		<section class="content container-fluid">
			<div class="row">
		   		<div class="col-12">
					<div class="box box-primary">
						<form role="form" id="aform" method="post">
							<input type="hidden" name="OS_TYPE" value="${resultMap.OS_TYPE}" />
							<input type="hidden" name="LATEST_VERSION" value="${resultMap.LATEST_VERSION}" />
							
							<div class="box-body">
								<table class="table table-bordered">
									<colgroup>
										<col width="20%" />
										<col width="80%" />
									</colgroup>
									<tbody>
										<tr>
											<th>플랫폼 <span class="text-danger">*</span></th>
											<td>
												<select name="OS_TYPE" id="osType" class="form-control w-25">
													<option value="ANDROID" <c:if test="${resultMap.OS_TYPE == 'ANDROID'}">selected</c:if>>ANDROID</option>
													<option value="IOS" <c:if test="${resultMap.OS_TYPE == 'IOS'}">selected</c:if>>IOS</option>
												</select>
											</td>
										</tr>
										<tr>
											<th>최신 버전 <span class="text-danger">*</span></th>
											<td>
												<input type="text" name="LATEST_VERSION" id="latestVersion" class="form-control w-50" value="${resultMap.LATEST_VERSION}" />
											</td>
										</tr>
										<tr>
											<th>최소 지원 버전 <span class="text-danger">*</span></th>
											<td>
												<input type="text" name="MIN_VERSION" id="minVersion" class="form-control w-50" value="${resultMap.MIN_VERSION}" />
											</td>
										</tr>
										<tr>
											<th>업데이트 메시지</th>
											<td>
												<textarea name="UPDATE_MSG" id="updateMsg" class="form-control" rows="3">${resultMap.UPDATE_MSG}</textarea>
											</td>
										</tr>
										<tr>
											<th>스토어 URL</th>
											<td>
												<input type="text" name="STORE_URL" id="storeUrl" class="form-control" value="${resultMap.STORE_URL}" />
											</td>
										</tr>
										<tr>
											<th>사용여부</th>
											<td>
												<select name="useYn" id="useYn" class="form-control w-25">
													<option value="Y" <c:if test="${resultMap.USE_YN == 'Y'}">selected</c:if>>사용</option>
													<option value="N" <c:if test="${resultMap.USE_YN == 'N'}">selected</c:if>>미사용</option>
												</select>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							
							<div class="box-footer text-center">
								<button type="button" class="btn btn-default" onclick="fnDetail(); return false;">취소</button>
								<button type="button" class="btn btn-primary" onclick="fnSave(); return false;">수정</button>
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
