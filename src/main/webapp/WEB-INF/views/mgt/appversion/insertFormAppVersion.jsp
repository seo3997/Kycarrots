<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>

<html>
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title>앱 버전 등록 - <%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>
	
	<script type="text/javascript">
		function fnList(){
			$('#aform').attr({ action : '/mgt/appversion/selectPageListAppVersion.do', method : 'get' }).submit();
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

			if(confirm('등록하시겠습니까?')){
				$('#aform').attr({ action : '/mgt/appversion/insertAppVersion.do', method : 'post' }).submit();
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
                <span class="blind">home</span> > 시스템관리 > <span class="text">앱 버전 등록</span>
            </div>
    	    <div id="pagetitle">
                <h1>앱 버전 등록</h1>
            </div>
		</section>

		<section class="content container-fluid">
			<div class="row">
		   		<div class="col-12">
					<div class="box box-primary">
						<form role="form" id="aform" method="post">
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
													<option value="">선택</option>
													<option value="ANDROID">ANDROID</option>
													<option value="IOS">IOS</option>
													<option value="FLUTTER_ANDROID">FLUTTER_ANDROID</option>
													<option value="FLUTTER_IOS">FLUTTER_IOS</option>
												</select>
											</td>
										</tr>
										<tr>
											<th>최신 버전 <span class="text-danger">*</span></th>
											<td>
												<input type="text" name="LATEST_VERSION" id="latestVersion" class="form-control w-50" placeholder="ex: 1.1.0" />
											</td>
										</tr>
										<tr>
											<th>최소 지원 버전 <span class="text-danger">*</span></th>
											<td>
												<input type="text" name="MIN_VERSION" id="minVersion" class="form-control w-50" placeholder="ex: 1.0.0" />
												<p class="help-block">이 버전 미만의 사용자는 강제 업데이트 팝업이 노출됩니다.</p>
											</td>
										</tr>
										<tr>
											<th>업데이트 메시지</th>
											<td>
												<textarea name="UPDATE_MSG" id="updateMsg" class="form-control" rows="3">새로운 버전이 출시되었습니다. 최신 버전으로 업데이트 해주세요.</textarea>
											</td>
										</tr>
										<tr>
											<th>스토어 URL</th>
											<td>
												<input type="text" name="storeUrl" id="storeUrl" class="form-control" placeholder="https://play.google.com/..." />
											</td>
										</tr>
										<tr>
											<th>사용여부</th>
											<td>
												<select name="USE_YN" id="useYn" class="form-control w-25">
													<option value="Y">사용</option>
													<option value="N">미사용</option>
												</select>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							
							<div class="box-footer text-center">
								<button type="button" class="btn btn-default" onclick="fnList(); return false;">목록</button>
								<button type="button" class="btn btn-primary" onclick="fnSave(); return false;">등록</button>
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
