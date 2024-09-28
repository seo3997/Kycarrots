<%@page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.file.vo.AtFileVO"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@ page import="com.whomade.kycarrots.framework.common.util.EgovPropertiesUtil" %>
<%@ page import="com.whomade.kycarrots.framework.common.util.SysUtil" %>
<%@ page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="resultMap" 	class="com.whomade.kycarrots.framework.common.object.DataMap" 	scope="request"/>
<jsp:useBean id="resultList"  	class="java.util.ArrayList"		  						scope="request"	type="java.util.List"/>
<jsp:useBean id="param" 		class="com.whomade.kycarrots.framework.common.object.DataMap" 	scope="request"/>

<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>

<html>
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>


	<script type="text/javascript">
		$(function(){
		});
		
		// 목록
		function fnGoList(){
			$('#aform').attr({ action : '/mgt/group/selectPageListPlanFGroup.do', method : 'POST' }).submit();
		}
		
		// 수정폼 이동
		function fnGoUpdateForm(){
			$('#aform').attr({ action : '/mgt/group/updateFormPlanFGroup.do', method : 'POST' }).submit();
		}
		
		 //삭제
		function fnGoDelete(){
			if(confirm('삭제하시겠습니까?')){
				$('#aform').attr({ action : '/mgt/group/deletePlanFGroup.do', method : 'POST' }).submit();
			}
		}

		function fn_popup(pGroupId){
			console.log("pGroupId:",pGroupId);
			window.open('/mgt/group/sendMailForm.do?GROUP_ID='+pGroupId, 'SendMail', 'width=800, height=600, left=0, right=0, toolbar=no, location=no, menubar=no, scrollbars=no, resizable=no');
		}
		
	</script>
	<style type="text/css">
	</style>
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

			<form role="form" id="aform" method="post" action="/mgt/group/selectPlanFGroup.do" class="form-horizontal" enctype="multipart/form-data">
				<input type="hidden" name="GROUP_ID" value="<%=resultMap.getString("GROUP_ID") %>" />
				<!-- 검색관련 -->
				<input type="hidden" name="sch_text" value="<%=param.getString("sch_text")%>" />
				<input type="hidden" name="currentPage" value="<%=param.getString("currentPage")%>"/>
				

			<div class="card">
				<h4 class="cardTitle"><i class="fa fa-caret-square-right"></i> 바우처 기본정보</h4>
				<div class="card-body viewForm">

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="bbs_se_code_m">바우처코드</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							0001
						</div>
					</div>

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="bbs_se_code_m">유효기간</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
								2023.07.01 ~ 2025.02.29
						</div>
					</div>
					
					<div class="form-group row">
						<label for="" class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">입금액</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
							10,000,000 원
						</div>
						<label for="" class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">포인트</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
							12,000,000 Point						</div>
					</div>
					<div class="form-group row">
						<label for="" class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">잔여포인트</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
							5,000,000 Point
						</div>
						<label for="" class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">참여교원</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
							이동국
						</div>
					</div>

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="bbs_se_code_m">내용</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<%=resultMap.getHtml("GROUP_ETC") %>
							<br>
							dkdkdkdkdk
							<br>
							dkdkdkdkdk
							<br>
							dkdkdkdkdk
							
						</div>
					</div>
					
					
				</div>

				<div class="box-footer">
					<div class="text-center">
						<button type="button" class="btn btn-list" onclick="fnGoList(); return false;"><i class="fa fa-reply"></i> 목록</button>
		        
						<button type="button" class="btn btn-modify" onclick="fnGoUpdateForm(); return false;"><i class="fa fa-eraser"></i> 수정</button>
					
						<button type="button" class="btn btn-delete" onclick="fnGoDelete(); return false;"><i class="fa fa-trash"></i> 삭제</button>

					</div>
				</div>

			</div>

			</form>
			
			<!-- list box // -->
			<div class="card">
				<h4 class="cardTitle"><i class="fa fa-caret-square-right"></i> 산학협력 승인내역 리스트</h4>
				<div class="card-body viewForm">
				<table class="table table-bordered table-hover">
                <tr class="text-center">
                  <th width="5%">No</th>
                  <th width="15%">회원사</th>
                  <th>참여교원</th>
                  <th width="15%">분배신청</th>
                  <th width="15%">신청형황</th>
                </tr>
				<%
				for(int i = 0; i < resultList.size(); i++){
					DataMap dataMap = (DataMap) resultList.get(i);
				%>
                <tr>
                  <td><%=(i+1)%></td>
                  <td class="text-left"><%=dataMap.getString("USER_ID") %></td>
                  <td class="text-left"><%=dataMap.getString("PLANF_TITLE") %></td>
                  <td><%=dataMap.getString("REGIST_DT") %></td>
                </tr>
				<%}%>
				<%if(resultList.size() == 0){%>
                <tr>
                  <td>1</td>
                  <td class="text-left">산학협력1</td>
                  <td>이동국</td>
                  <td class="text-right">2,000,000</td>
                  <td>요청</td>
                </tr>
                <tr>
                  <td>1</td>
                  <td class="text-left">산학협력2</td>
                  <td>홍길동</td>
                  <td class="text-right">1,000,000</td>
                  <td>승인</td>
                </tr>
				<%}%>
                

				</table>
				</div>
			</div>
			<!-- // list box -->
			
			
		</section>
	</div>

	<!-- footer -->
	<%@ include file="/common/inc/footer.jspf" %>
	<!-- //fooer -->
</div>

</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
