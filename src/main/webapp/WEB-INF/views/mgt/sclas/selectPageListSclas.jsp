<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="codeList"  			type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="itemComboStr"  	type="java.util.List" class="java.util.ArrayList" scope="request"/>



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
				$('[name=item_code]').change(function(e){
					fnSearch();
				});
		});

		function fnOpCodeSelect(op_code){
			$("[name=op_code]").val(op_code);
			$("#aform").attr({action:"/mgt/sclas/selectSclas.do", method:'get'}).submit();
		}
		
		function fnOpSclasCodeSelect(op_code,sclas_code){
			$("[name=op_code]").val(op_code);
			$("[name=sclas_code]").val(sclas_code);
			$("#aform").attr({action:"/mgt/sclas/selectSdclas.do", method:'get'}).submit();
		}

		
		// 검색
		function fnSearch(){
			$("#aform").attr({action:"/mgt/sclas/selectPageListSclas.do", method:'get'}).submit();
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
				<form id="aform" method="post" action="/mgt/sclas/selectPageListSclas.do">
					<input type="hidden" name="group_id"  value=<%=Const.upCodeArea%> />
					<input type="hidden" name="op_code" />
					<input type="hidden" name="sclas_code" />
					<div class="box box-primary">
						<div class="box-header">
						
							<div class="col-sm-10">
									<div class="form-group">
										<div class="col-sm-1 divnopadding">
											<select id="item_code" name="item_code" class="form-control input-sm">
												<%=CommboUtil.getComboStr(itemComboStr, "CODE", "CODE_NM", param.getString("item_code"), "A")%>
											</select>
										</div>
									</div>
							</div>
						   
								
						</div>
						<!-- /.box-header -->
						<div class="box-body no-pad-top table-responsive">
							<table class="table table-bordered table-hover">
								<colgroup>
									<col width="10%">
									<col width="20%">
									<col width="70%">
								</colgroup>
								<thead>
									<tr>
										<th class="text-center">시/도</th>
										<th class="text-center">구/군</th>
										<th class="text-center">동</th>
									</tr>
								</thead>
								<tbody>
							<%
								int dataNo = codeList.size();
								int irow=0;
								String rowcnt="";
								String itemNm="";
								for(int i = 0; i < codeList.size(); i++){
									DataMap dataMap = (DataMap) codeList.get(i);
									if("1".equals(dataMap.getString("DISID"))){				//품목류
										rowcnt=dataMap.getInt("SCLASCNT")+"";
										itemNm=dataMap.getString("CODE_NM");
										irow=0;
									}else{
										irow++;	
									}
							%>
							<%
									if(irow==1){
							%>
								<tr>
									<td class="text-center" rowspan="<%=rowcnt%>"><%=itemNm%></td>
									<td><%=dataMap.getString("SCLAS_NM") %></td>
									<td><button type="button" class="btn btn-primary" onclick="fnOpSclasCodeSelect('<%=dataMap.getString("CODE")%>','<%=dataMap.getString("SCLAS_CODE")%>'); return false;"><i class="fa fa-pencil"></i><%=dataMap.getString("SCLAS_NM") %> 동 등록</button></td>
								</tr>
							<%	
									} else if (irow>1){
							%>
								<tr>
									<td><%=dataMap.getString("SCLAS_NM") %></td>
									<td><button type="button" class="btn btn-primary"  onclick="fnOpSclasCodeSelect('<%=dataMap.getString("CODE")%>','<%=dataMap.getString("SCLAS_CODE")%>'); return false;"><i class="fa fa-pencil"></i><%=dataMap.getString("SCLAS_NM") %> 동 등록</button></td>
								</tr>
							<%	}
									
							%>
							<%}%>
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
