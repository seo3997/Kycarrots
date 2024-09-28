<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@ page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<jsp:useBean id="resultList"  				type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="param" 					class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="pageNavigationVo" 	class="com.whomade.kycarrots.framework.common.page.vo.pageNavigationVo" scope="request"/>

<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>
	<script type="text/javascript">
	//<![CDATA[
		$(function(){

		});
		
		// 페이지 이동
		function fnGoPage(currentPage){
			$("#currentPage").val(currentPage);
			$("#aform").attr({action:"/admin/user/selectPageListUserHistoryMgt.do", method:'post'}).submit();
		}

		// 검색
		function fnSearch(){
			$("#currentPage").val("1");
			$("#aform").attr({action:"/admin/user/selectPageListUserHistoryMgt.do", method:'get'}).submit();
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
					<form role="form" id="aform" method="post" action="/admin/user/selectPageListUserHistoryMgt.do">
						<input type="hidden" id="user_no" name="user_no" />
						<div class="box box-primary">
							<div class="box-header">
									<div class="col-sm-10">
										<div class="form-group">
											<div class="col-sm-1 divnopadding">
												<select id="search_list_st" name="sch_type" class="form-control input-sm">
													<option value="user_nm" 	<%if(param.getString("sch_type").equals("user_nm")){%>selected="selected"<%}%>>이름</option>
													<option value="user_id" 		<%if(param.getString("sch_type").equals("user_id")){%>selected="selected"<%}%>>사용자ID</option>											
												</select>
											</div>
											<div class="col-sm-2 divnopadding">
												<div class="input-group input-group-sm">
														<input type="text" class="form-control" name="sch_text" title="검색어를 입력하세요." value="<%=param.getString("sch_text")%>" />
														<span class="input-group-btn">
															<button type="button" class="btn btn-sm btn-info" onclick="fnSearch(); return false;"><i class="fa fa-search"></i></button>
														</span>
												</div>
											</div>
										</div>
								</div>
							</div><!-- /.box-header -->
							<div class="box-body no-pad-top table-responsive">
								<table class="table table-hover table-bordered">
									<colgroup>
										<col width="10%" />
										<col width="10%" />
										<col width="10%" />
										<col width="10%" />
										<col width="20%" />
										<col width="*" />
										<col width="10%" />
									</colgroup>
									<thead>
										<tr>
											<th class="text-center">No</th>
											<th class="text-center">이름</th>
											<th class="text-center">사용자ID</th>
											<th class="text-center">권한</th>
											<th class="text-center">변경일시</th>
											<th class="text-center">변경항목</th>
											<th class="text-center">등록자</th>
										</tr>
									</thead>
									<tbody>
									<%
										String naviBar = (String)request.getAttribute("navigationBar");
										int dataNo = pageNavigationVo.getCurrDataNo();
										for(int i = 0; i < resultList.size(); i++){
											DataMap dataMap = (DataMap) resultList.get(i);
									%>
										<tr style="cursor:pointer;cursor:hand;" onclick="fnSelect('<%=dataMap.getString("USER_NO")%>')">
											<td class="text-center"><%=dataNo-i %></td>
											<td class="text-center"><%=dataMap.getString("USER_NM") %></td>
											<td><%=dataMap.getString("USER_ID") %></td>
											<td><%=dataMap.getString("USER_AUTHOR") %></td>
											<td class="text-center"><%=dataMap.getString("UPDT_DT") %></td>
											<td class="text-center"><%=dataMap.getString("USER_CHANGE_NM") %></td>
											<td class="text-center"><%=dataMap.getString("UPDUSR_ID") %></td>
										</tr>
									<%  }%>
									<%	if(resultList.size() == 0){	%>
										<tr>
											<td class="text-center" colspan="7" ><spring:message code="msg.data.empty" /></td>
										</tr>
									<%}%>
									</tbody>
								</table>
							</div><!-- /.box-body -->
							<div class="box-footer clearfix text-center">
								<%=naviBar %>
							</div>
							<div class="box-footer clearfix no-pad-top">
								<!-- 
								<div class="pull-right">
									<button type="button" class="btn btn-info" onclick="fnInsertForm(); return false;"><i class="fa fa-pencil"></i> 엑셀다운로드</button>
								</div>
								 -->
							</div>
						</div><!-- /.box -->
					</form>
				</div><!--  /.col-xs-12 -->
			</div><!-- ./row -->
		</section><!-- /.content -->
	</div><!-- /.content-wrapper -->
	
	<!-- footer -->
	<%@ include file="/common/inc/footer.jspf" %>
	<!-- //fooer -->
	
</div><!-- ./wrapper -->
</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
