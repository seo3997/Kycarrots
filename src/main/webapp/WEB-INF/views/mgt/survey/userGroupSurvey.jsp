<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@ page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<jsp:useBean id="resultList"  			type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="param" 				class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="userSttusComboStr"  	type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="userSeComboStr"  		type="java.util.List" class="java.util.ArrayList" scope="request"/>

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
			$('[name=sch_user_se_code], [name=sch_user_sttus_code]').change(function(e){
				fnSearch();
			});
		});
		
		// 페이지 이동
		function fnGoPage(currentPage){
			$("#currentPage").val(currentPage);
			$("#aform").attr({action:"/mgt/survey/userGroupSurvey.do", method:'post'}).submit();
		}

		// 상세조회
		function fnSelect(user_no,survey_group_id){
			$("#user_no").val(user_no);
			$("#survey_group_id").val(survey_group_id);
			$("#aform").attr({action:"/mgt/survey/userGroupSurveyMgt.do", method:'post'}).submit();
		}
		// 검색
		function fnSearch(){
			$("#currentPage").val("1");
			$("#aform").attr({action:"/mgt/survey/userGroupSurvey.do", method:'post'}).submit();
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
					<form role="form" id="aform" method="post" action="/admin/user/selectPageListUserMgt.do">
						<input type="hidden" id="user_no" name="user_no" />
						<input type="hidden" id="survey_group_id" name="survey_group_id" />
						<div class="box box-primary">
							<div class="box-header">
									<div class="col-sm-10">
										<div class="form-group">
											<div class="col-sm-1 divnopadding">
												<select id="sch_user_sttus_code" name="sch_user_sttus_code" class="form-control input-sm">
													<%=CommboUtil.getComboStr(userSttusComboStr, "CODE", "CODE_NM", param.getString("sch_user_sttus_code") , "사용자상태")%>
												</select>
											</div>
											<div class="col-sm-1 divnopadding">
												<select id="search_list_st" name="sch_type" class="form-control input-sm">
													<option value="user_nm" <%if(param.getString("sch_type").equals("user_nm")){%>selected="selected"<%}%>>이름</option>
													<option value="user_id" <%if(param.getString("sch_type").equals("user_id")){%>selected="selected"<%}%>>사용자ID</option>											
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
										<col width="100px" />
										<col width="200px" />
										<col width="100px" />
										<col width="*" />
									</colgroup>
									<thead>
										<tr>
											<th class="text-center">No</th>
											<th class="text-center">관리자</th>
											<th class="text-center">관리자ID</th>
											<th class="text-center">평가그룹명</th>
										</tr>
									</thead>
									<tbody>
									<%
										int dataNo = 0;
									    String sUserId="";
										for(int i = 0; i < resultList.size(); i++){
											DataMap dataMap = (DataMap) resultList.get(i);
									%>
										<tr style="cursor:pointer;cursor:hand;" onclick="fnSelect('<%=dataMap.getString("USER_NO")%>','<%=dataMap.getString("SURVEY_GROUP_ID")%>')">
											<%
												if(!sUserId.equals(dataMap.getString("USER_ID"))) {
													dataNo++;
											%>
											<td class="text-center" rowspan="<%=dataMap.getString("ROWCNT")%>"><%=dataNo%></td>
											<td class="text-center" rowspan="<%=dataMap.getString("ROWCNT")%>"><%=dataMap.getString("USER_NM") %></td>
											<td class="text-center" rowspan="<%=dataMap.getString("ROWCNT")%>"> <%=dataMap.getString("USER_ID") %></td>
											<%
												} 
											
											
											%>
											<td class="text-center"><%=dataMap.getString("SURVEY_GROUP_NM") %></td>
										</tr>
									<%  
										 
										sUserId=dataMap.getString("USER_ID");
										}
									%>
									<%	if(resultList.size() == 0){	%>
										<tr>
											<td class="text-center" colspan="4" ><spring:message code="msg.data.empty" /></td>
										</tr>
									<%}%>
									</tbody>
								</table>
							</div><!-- /.box-body -->
							<div class="box-footer clearfix no-pad-top">
								<div class="pull-right">
									<!--  
									<button type="button" class="btn btn-info" onclick="fnInsertForm(); return false;"><i class="fa fa-pencil"></i> 등록</button>
									-->
								</div>
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
