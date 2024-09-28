<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<jsp:useBean id="resultMap" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="authList"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
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
		// 목록
		function fnGoList(){
			$("#aform").attr({action:"/admin/user/selectPageListUserMgt.do", method:'post'}).submit();
		}
		// 사용자수정폼 이동
		function fnGoUpdateForm(user_no){
			$("#aform").action = "/admin/user/updateFormUserMgt.do";
			$("#aform").submit();
		}
		// 사용자삭제
		function fnGoDelete(user_no){
			if(confirm("삭제하시겠습니까?")){
				$("#aform").attr({action:"/admin/user/deleteUserMgt.do", method:'post'}).submit();
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
				
					<div class="box box-primary">
						<form role="form" id="aform" method="post" action="/admin/user/updateFormUserMgt.do" class="form-horizontal">
							<input type="hidden" id="user_no" name="user_no" value="<%=resultMap.getString("USER_NO") %>" />
							<input type="hidden" name="sch_type" value="<%=param.getString("sch_type")%>" />
							<input type="hidden" name="sch_text" value="<%=param.getString("sch_text")%>" />
							<input type="hidden" name="currentPage" value="<%=param.getString("currentPage")%>"/>
							<div class="box-body no-padding">
							
								<h4 class="subtitle"><i></i>회원기본정보</h4>
								<div class="tb-box2">	
									<div class="form-group">
										<span class="control-span col-sm-2">사용자ID :</span>
										<div class="col-sm-4">
											<%=resultMap.getString("USER_ID") %>
										</div>
										<span class="control-span col-sm-2">이름 :</span>
										<div class="col-sm-4">
											<%=resultMap.getString("USER_NM") %>
										</div>
									</div>
									<div class="form-group">
										<span class="control-span col-sm-2">연락처 :</span>
										<div class="col-sm-4">
											(<%=resultMap.getString("CTTPC_SE_NM") %>) <%=resultMap.getString("CTTPC") %>
										</div>
										<span class="control-span col-sm-2">이메일 :</span>
										<div class="col-sm-4">
											<%=resultMap.getString("EMAIL") %>
										</div>
									</div>
									<div class="form-group">
										<span class="control-span col-sm-2">사용자 권한 :</span>
										<div class="col-sm-4">
										<%
												if(authList != null){
													for(int i=0; i<authList.size(); i++) { 
														DataMap dataMap = (DataMap)authList.get(i);
														out.print(dataMap.getString("AUTHOR_NM")); 	
														if(i != authList.size()-1){
															out.print(", "); 	
														} 
													} 
												}
										%>

										</div>
										<span class="control-span col-sm-2">사용자 상태 코드 :</span>
										<div class="col-sm-4">
											<%=resultMap.getString("USER_STTUS_NM") %>
										</div>
									</div>
									<div class="form-group">
										<span class="control-span col-sm-2">지역:</span>
										<div class="col-sm-4">
											<%=resultMap.getString("AREA_NM") %>
										</div>
										<span class="control-span col-sm-2">등록일 :</span>
										<div class="col-sm-4">
											<%=resultMap.getString("REGIST_YMD") %>
										</div>
									</div>
								</div><!-- /.tb-box2 -->
							
							</div><!-- /.box-body -->
							<div class="box-footer">
								<div class="pull-right">
									<button type="button" class="btn btn-default" onclick="fnGoList(); return false;"><i class="fa fa-reply"></i> 목록</button>
									<button type="button" class="btn btn-info" onclick="fnGoUpdateForm(); return false;"><i class="fa fa-pencil"></i> 수정</button>
									<button type="button" class="btn btn-warning" onclick="fnGoDelete(); return false;"><i class="fa fa-trash"></i> 삭제</button>
								</div>
							</div>
						</form>
					</div><!-- /.box -->
					
			</div><!-- ./row -->
		</section><!-- /.content -->
	</div>
	<!-- footer -->
	<%@ include file="/common/inc/footer.jspf" %>
	<!-- //fooer -->
</div>
</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
