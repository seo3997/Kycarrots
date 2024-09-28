<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<jsp:useBean id="resultMap" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="authList"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="userAuthList"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="userSttusComboStr"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="cttpcSeComboStr"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="areaCodeComboStr"  type="java.util.List" class="java.util.ArrayList" scope="request"/>


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
			// 사용자 권한 체크 
			<%	for(int i=0; i<userAuthList.size(); i++){
					DataMap dataMap = (DataMap)userAuthList.get(i);
					String author_id = dataMap.getString("AUTHOR_ID");
			%>
					$("[value=<%=author_id%>]").eq(0).attr("checked", "true");
			<%	} %>

			$("#area_se_code_m").change(function(){
				fnGetSCodeList($('#area_se_code_l').val(),this.value,$("#area_se_code_s"),'','C');				
			});
			
			$("#area_se_code_s").change(function(){
				fnGetSDCodeList($('#area_se_code_l').val(),$('#area_se_code_m').val(),this.value,$("#area_se_code_d"),'','C');				
			});
		
			
		});
		
		// 상세
		function fnDetail(){
			$("#aform").attr({action:"/admin/user/selectUserMgt.do", method:'post'}).submit();
		}
		
		// 수정
		function fnGoUpdate(){
			var cttpc = $("#cttpc1").val() + "-" + $("#cttpc2").val() + "-" + $("#cttpc3").val();
			$("#cttpc").val(cttpc);
			
			if($("#user_nm").val() == ""){
				alert("이름을 입력해 주세요.");
				$("#user_nm").focus();
				return false;
			}

			if($("input:radio[name='author_id']").is(":checked") == false){
				alert("사용자 권한을 체크해 주세요");
				return false;
			}

			if(confirm("수정하시겠습니까?")){
				$("#aform").attr({action:"/admin/user/updateUserMgt.do", method:'post'}).submit();
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
		<section class="content container-fluid vw-page">
					<form role="form" id="aform" method="post" action="/admin/user/updateUserMgt.do" class="form-horizontal">
						<input type="hidden" id="user_no" 				name="user_no" 				value="<%=resultMap.getString("USER_NO") %>" />
						<input type="hidden" id="cttpc" 					name="cttpc" 					value="<%=resultMap.getString("CTTPC") %>" />
						<input type="hidden" id="cttpc_se_code" 		name="cttpc_se_code" 	value="10" />
						<input type="hidden" id="user_nm_chg" 		name="user_nm_chg" 		value="<%=resultMap.getString("USER_NM") %>" />
						<input type="hidden" id="cttpc_chg" 			name="cttpc_chg" 			value="<%=resultMap.getString("CTTPC") %>" />
						<input type="hidden" id="email_chg" 			name="email_chg" 	 		value="<%=resultMap.getString("EMAIL") %>" />
						<input type="hidden" 	name="area_se_code_l"	id="area_se_code_l" 			value="R010070"  />
						
					<div class="card">
						<h4 class="cardTitle"><i class="fa fa-caret-square-right"></i> 기본정보</h4>
						<div class="card-body viewForm">

							<div class="form-group row">
								<label class="control-label col-sm-1" for="user_id">사용자ID</label>
					    		<div class="col-sm-5">
										<input type="text" class="form-control" id="user_id" name="user_id" placeholder="사용자ID" maxlength="50" value="<%=resultMap.getString("USER_ID") %>" readonly="readonly" />
								</div>
								<label class="control-label col-sm-1" for="password">비밀번호</label>
					    		<div class="col-sm-5">
										<input type="password" class="form-control" id="password" name="password" placeholder="패스워드" maxlength="50" />
								</div>
							</div>
								    	
							<div class="form-group row">
								<label class="control-label col-sm-1" for="user_nm">이름</label>
					    		<div class="col-sm-5">
										<input type="text" class="form-control" id="user_nm" name="user_nm" placeholder="이름" maxlength="50" value="<%=resultMap.getString("USER_NM") %>" />
								</div>
								<label class="control-label col-sm-1" for="cttpc_se_code">연락처</label>
					    		<div class="col-sm-5">
					    				<!--  
					    				<div style="width:100px; float:left;">
											<select class="form-control" id="cttpc_se_code" name="cttpc_se_code" >
												<%=CommboUtil.getComboStr(cttpcSeComboStr, "CODE", "CODE_NM", resultMap.getString("CTTPC_SE_CODE"), "")%>
											</select>
										</div>
										-->
					    				<div class="tele60">
											<input type="text" id="cttpc1" name="cttpc1" class="form-control numeric" maxlength="3" title="전화번호 첫번째 입력칸입니다" value="<%if(!resultMap.getString("CTTPC").equals("")){%><%=resultMap.getString("CTTPC").split("-", 3)[0]%><%}%>" />
										</div>
										<span class="form-control-static telespan">-</span>
					    				<div class="tele70">
											<input type="text" id="cttpc2" name="cttpc2" class="form-control numeric" maxlength="4" title="전화번호 두번째 입력칸입니다" value="<%if(!resultMap.getString("CTTPC").equals("")){%><%=resultMap.getString("CTTPC").split("-", 3)[1]%><%}%>" />
										</div>
										<span class="form-control-static telespan">-</span>
					    				<div class="tele70">
											<input type="text" id="cttpc3" name="cttpc3" class="form-control numeric" maxlength="4" title="전화번호 세번째 입력칸입니다" value="<%if(!resultMap.getString("CTTPC").equals("")){%><%=resultMap.getString("CTTPC").split("-", 3)[2]%><%}%>" />
										</div>
								 </div>
							 </div>
	
							<div class="form-group row">
									<label class="control-label col-sm-1" for="email">이메일</label>
						    		<div class="col-sm-5">
											<input type="text" class="form-control" id="email" name="email" placeholder="이메일" onkeyup="cfLengthCheck('이메일은', this, 50);" value="<%=resultMap.getString("EMAIL") %>" />
									</div>
									<label class="control-label col-sm-1" for="user_sttus_code">사용자 상태 코드</label>
						    		<div class="col-sm-5">
											<select class="form-control" id="user_sttus_code" name="user_sttus_code" >
												<%=CommboUtil.getComboStr(userSttusComboStr, "CODE", "CODE_NM", resultMap.getString("USER_STTUS_CODE"), "")%>
											</select>
									</div>
							</div>
	
							<div class="form-group row">
								<label class="control-label col-sm-1" for="area_code">지역</label>
					    		<div class="col-sm-2">
										<select class="form-control" name="area_se_code_m" id="area_se_code_m">
											<%=CommboUtil.getComboStr(areaCodeComboStr, "CODE", "CODE_NM", resultMap.getString("AREA_CODE"), "C")%>
										</select>
								</div>
					    		<div class="col-sm-2">
									<select class="form-control"  name="area_se_code_s"  id="area_se_code_s" >
										<option value="">선택하세요</option>
									</select>
								</div>
					    		<div class="col-sm-2">
									<select class="form-control"  name="area_se_code_d"  id="area_se_code_d" >
										<option value="">선택하세요</option>
									</select>
								</div>
					    	</div>

						</div>

						<h4 class="cardTitle"><i class="fa fa-caret-square-right"></i> 권한</h4>
						<div class="card-body viewForm">

							<div class="form-group row">
									<label class="control-label col-sm-1" for="area_code">권한선택</label>
						    		<div class="col-sm-5">
										<%
											String  userRollchecked="";
											for(int i=0; i<authList.size(); i++) {
												DataMap authMap = (DataMap)authList.get(i);
												String authorName = "author" + i;  
										%>
											<div class="checkbox">
												<label for="<%=authorName%>">
													<input type="radio" name="author_id" id="<%=authorName%>" value="<%=authMap.getString("AUTHOR_ID") %>" />
													<%=authMap.getString("AUTHOR_NM") %>
												</label>
											</div>
										<%	}  %>
									</div>
							</div>
						</div>
							
						<div class="box-footer">
							<div class="text-center">
								<button type="button" class="btn btn-reset" onclick="fnDetail(); return false;"><i class="fa fa-reply"></i> 취소</button>
								<button type="button" class="btn btn-write" onclick="fnGoUpdate(); return false;"><i class="fa fa-pencil"></i> 확인</button>
							</div>
						</div>
					
					</div>
											
					</form>
					
		</section><!-- /.content -->
	</div>
	<!-- footer -->
	<%@ include file="/common/inc/footer.jspf" %>
	<!-- //fooer -->
</div>
<!-- Trigger the modal with a button -->
<!-- Modal -->
<div id="selectModal" class="modal fade" role="dialog">
  <div class="modal-dialog modal-lg">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">팝업제목</h4>
      </div>
       <div id="modal-body">
 		
 		</div>
      <div class="modal-footer">
        <button type="button" id="btn-close" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
   	
   	$('input[name=author_id]').on('change', function(e) {
   		
   		if("ROLE_EXT"==this.value){
   			$(".userauth").show();
   		}else{
   			$(".userauth").hide();
   			
   			$( "#btnfrmhs" ).trigger( "click" );
   			$( "#btnhsmp" ).trigger( "click" );
   			$( "#btnapc" ).trigger( "click" );
   			$( "#btnxptcp" ).trigger( "click" );
   		}
   		
	});
   	
   	$('#btnfrmhs').on('click', function(e) {
		$("#frmhs_seq").val("");
		$("#owner_nm").val("");
   	});
   	
   	$('#btnhsmp').on('click', function(e) {
		$("#hsmp_seq").val("");
		$("#hsmp_nm").val("");
   	});

   	$('#btnapc').on('click', function(e) {
		$("#apc_seq").val("");
		$("#apc_nm").val("");
   	});
   	
   	$('#btnxptcp').on('click', function(e) {
		$("#xptcp_seq").val("");
		$("#xptcp_nm").val("");
   	});

   	
</script>

<script language="javascript">
	fnGetSCodeList($('#area_se_code_l').val(), $('#area_se_code_m').val(), $("#area_se_code_s"),'<%=resultMap.getString("AREA_SE_CODE_S")%>','C');
	fnGetSDCodeList($('#area_se_code_l').val(),$('#area_se_code_m').val(),$("#area_se_code_s").val(),$("#area_se_code_d"),'<%=resultMap.getString("AREA_SE_CODE_D")%>','C');				

</script>

</body>


</html>
<%@ include file="/common/inc/msg.jspf" %>
