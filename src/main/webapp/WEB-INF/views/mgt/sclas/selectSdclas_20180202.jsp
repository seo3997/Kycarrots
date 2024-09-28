<%@page import="com.whomade.kycarrots.framework.common.object.DataMap"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="param" 	class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="itemMap" 	class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="resultList"  type="java.util.List" class="java.util.ArrayList" scope="request"/>

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
			// 행삽입 버튼 클릭시
			$('#menuTable').on({
				'click' : function(e){
					e.preventDefault();
					
					var innerHtml = ''
					
					innerHtml += '<tr>';
					innerHtml += '	<td><input type="text" name="sdclas_code" class="form-control" /></td>';
					innerHtml += '	<td><input type="text" name="sdclas_nm" class="form-control" /></td>';
					innerHtml += '	<td class="text-center">';
					innerHtml += '		<button type="button" class="btn btn-warning btn-plus"><i class="fa fa-plus"></i> 행삽입</button>';
					innerHtml += '		<button type="button" class="btn btn-danger btn-minus"><i class="fa fa-minus"></i> 삭제</button>';
					innerHtml += '	</td>';
					innerHtml += '</tr>';
					
					$(this).parents('tr').eq(0).after(innerHtml);
				}
			}, '.btn-plus');
			
			// 행삭제 버튼 클릭시
			$('#menuTable').on({
				'click' : function(e){
					e.preventDefault();
					
					$(this).parents('tr').eq(0).remove();
				}
			}, '.btn-minus');
		});

		function fnList(){
			$("#aform").attr({action:"/mgt/sclas/selectPageListSdclas.do", method:'post'}).submit();
		}

		function fnUpdate(){
			var flag = true;
			
			// 이름 값체크
			$('[name=nm]').each(function(e){
				if($(this).val() == ''){
					alert('이름을 등록해주세요.');
					$(this).focus();
					flag = false;
					return false;
				}
			});
			
			if(!flag){
				return false;
			}

			if(confirm("수정하시겠습니까?")){
				$("#aform").attr({action:"/mgt/sclas/updateSdclas.do", method:'post'}).submit();
			}
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
	
	<!-- content -->
	<div class="content-wrapper">
		<!-- Content Header (Page header) -->
		<section class="content-header">
		<div id="navi"><i class="fa fa-home f12 color-lgray"></i><span class="blind">home</span>><span class="text" id="spnavi"></span></div>
    	<div id="pagetitle">
    	</div>
		</section>

		<!-- Main content -->
		<section class="content">
			<div class="row">
				<div class="col-xs-12">
					<form role="form" id="aform" method="post" action="/mgt/sclas/selectSdclas.do">
						<input type="hidden" name="group_id" value="<%=itemMap.getString("GROUP_ID") %>" />
						<input type="hidden" name="op_code" value="<%=itemMap.getString("CODE") %>" />
						<input type="hidden" name="sclas_code" value="<%=itemMap.getString("SCLAS_CODE") %>" />
						
						<div class="box box-primary">
							<div class="box-body">
								<div class="form-group">
									<label for="group_id">품목</label>
									<input type="text" class="form-control" value="<%=itemMap.getString("CODE_NM") %>-<%=itemMap.getString("SCLAS_NM") %>" disabled>
								</div>
							</div><!-- /.box-body -->
						</div>
					
						<div class="box box-primary">
							<div class="box-body table-responsive no-padding">
								<table id="menuTable" class="table table-bordered">
									<colgroup>
										<col width="200px">
										<col width="*">
										<col width="200px">
									</colgroup>
									<thead>
										<tr>
											<th class="text-center">품종코드</th>
											<th class="text-center">품종명</th>
											<th class="text-center">삭제</th>
										</tr>
									</thead>
									<tbody>
								<%
									int dataNo = resultList.size();
									if(resultList.size() > 0){
										for(int i = 0; i < resultList.size(); i++){
											DataMap dataMap = (DataMap) resultList.get(i);
								%>
										<tr>
											<td><input type="text" name="sdclas_code" class="form-control" value="<%=dataMap.getString("SDCLAS_CODE") %>" /></td>
											<td><input type="text" name="sdclas_nm" class="form-control"   value="<%=dataMap.getString("SDCLAS_NM") %>" /></td>
											<td class="text-center">
												<button type="button" class="btn btn-warning btn-plus"><i class="fa fa-plus"></i> 행삽입</button>
												<button type="button" class="btn btn-danger btn-minus"><i class="fa fa-minus"></i> 삭제</button>
											</td>
										</tr>
								<%
										}
									} else {
								%>
										<tr>
											<td><input type="text" name="sdclas_code" class="form-control" /></td>
											<td><input type="text" name="sdclas_nm" class="form-control" /></td>
											<td class="text-center">
												<button type="button" class="btn btn-warning btn-plus"><i class="fa fa-plus"></i> 행삽입</button>
												<button type="button" class="btn btn-danger btn-minus"><i class="fa fa-minus"></i> 삭제</button>
											</td>
										</tr>
								<%	
									}
								%>
									</tbody>
								</table>
							</div>
							<div class="box-footer">
								<button type="button" class="btn btn-default" onclick="fnList(); return false;"><i class="fa fa-reply"></i> 목록</button>
								<div class="pull-right">
									<button type="button" class="btn btn-info" onclick="fnUpdate(); return false;"><i class="fa fa-pencil"></i> 수정</button>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>	
		</section>
	</div>
	<!-- footer -->
	<%@ include file="/common/inc/footer.jspf" %>
	<!-- //fooer -->
</div>
</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
