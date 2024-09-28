<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="param" 	class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="codeList"  type="java.util.List" class="java.util.ArrayList" scope="request"/>

<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<html>
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>
	<script type="text/javascript">
	//<![CDATA[
		$(function(){
			
		});

		function fnSelect(op_code){
			$("[name=op_code]").val(op_code);
			$("#aform").attr({action:"/mgt/sclas/selectSclasArea.do", method:'get'}).submit();
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
	
	<!-- Content Wrapper. Contains page content -->
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
				<!-- content 영역 -->
				<form id="aform" method="post" action="/mgt/areainfo/selectPageListAreaInfo.do">
					<input type="hidden" name="group_id"  value=<%=Const.upCodeArea %> />
					<input type="hidden" name="op_code" />
					<div class="box box-primary">
						<!--  
						<div class="box-header">
							<div class="form-group">
								<div class="col-sm-2">
								<div class="input-group input-group-sm">
										<input type="text" name="sch_auth_nm" value="${param.sch_auth_nm }" class="form-control input-sm pull-right" placeholder="지역">
										<span class="input-group-btn">
										<button type="button" class="btn btn-sm btn-info" onclick="fnSearch(); return false;"><i class="fa fa-search"></i></button>
									 </span>
								</div>
								</div>
							</div>
						</div>
						-->
						<div class="box-body no-pad-top table-responsive">
							<table class="table table-bordered table-hover">
								<colgroup>
									<col width="100px">
									<col width="*">
								</colgroup>
								<thead>
									<tr>
										<th class="text-center">No</th>
										<th class="text-center">지역</th>
									</tr>
								</thead>
								<tbody>
							<%
								int dataNo = codeList.size();
								for(int i = 0; i < codeList.size(); i++){
									DataMap dataMap = (DataMap) codeList.get(i);
							%>
								<tr style="cursor:pointer;cursor:hand;" onclick="fnSelect('<%=dataMap.getString("CODE")%>'); return false;">
									<td class="text-center"><%=dataNo-i%></td>
									<td class=""><%=dataMap.getString("CODE_NM") %></td>
								</tr>
							<%	}%>
								</tbody>
							</table>
						</div><!-- /.box-body -->
					</div>
				</form>
				<!-- content 영역 -->
				</div>
			</div><!-- //content -->	
		</section>
	</div>
	<!-- footer -->
	<%@ include file="/common/inc/footer.jspf" %>
	<!--//footer -->
</div>
</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
