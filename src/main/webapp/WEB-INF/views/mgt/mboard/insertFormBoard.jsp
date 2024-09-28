<%@ page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.SysUtil"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="boardComboStr"  type="java.util.List" class="java.util.ArrayList" scope="request"/>

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
			fnComboStrFile('.fileBoxWrap', 0, 5);
		});
		
		// 목록
		function fnGoList(){
			$('#aform').attr({ action : '/mgt/mboard/selectPageListBoard.do', method : 'post' }).submit();
		}
		
		// 등록
		function fnGoInsert(){
			
			if($('[name=bbs_se_code_m]').val() == ''){
				alert('게시판구분을 선택하세요');
				$('[name=bbs_se_code_m]').focus();
				return false;
			}

			if($('[name=sj]').val() == ''){
				alert('제목을 입력해 주세요.');
				$('[name=sj]').focus();
				return false;
			}
			
			if($('#cn').val() == ''){
				alert('내용을 입력해 주세요.');
				$('#cn').focus();
				return false;
			}
			
			// 확장자 체크
			var msg = f_CheckExceptFileExt('upload');
			if(msg != ''){
				alert(msg);
				return false;
			}
			
			if(confirm('등록하시겠습니까?')){
				$('#aform').attr({ action : '/mgt/mboard/insertBoard.do', method : 'post' }).submit();
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
			<form role="form" id="aform" method="post" action="/mgt/mboard/insertBoard.do" enctype="multipart/form-data" class="form-horizontal">
				<input type="hidden" 	name="cn_doc_id"				id="cn_doc_id" 						value="<%=param.getString("cn_doc_id")%>" />
				<input type="hidden" 	name="bbs_se_code_l"			id="bbs_se_code_l" 			 		value="R010170"  />

			<div class="card">

				<h4 class="cardTitle"><i class="fa fa-caret-square-right"></i> 기본정보</h4>
				<div class="card-body viewForm">

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="bbs_se_code_m">게시판구분</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<select id="bbs_se_code_m" name="bbs_se_code_m" class="form-control input-sm" >
								<%=CommboUtil.getComboStr(boardComboStr, "CODE", "CODE_NM", param.getString("bbs_se_code_m") , "C")%>
							</select>
						</div>
					</div>

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="sj">제목</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<input type="text" class="form-control" name="sj" id="sj" placeholder="제목" value="<%=param.getString("sj") %>" maxlength="100" />
						</div>
					</div>

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="cn">내용</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<textarea class="form-control" rows="3" name="cn" id="cn"><%=param.getString("cn") %></textarea>
						</div>
					</div>

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="apply_cnt">첨부파일</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<div class="outBox1 fileBoxWrap"></div>
							<div class="outBox2 ftRed"></div>
						</div>
					</div>
					
					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="atch_yn_y">첨부파일 표시여부</label>
						
						<div class="checkbox col-xs-11 col-sm-8 col-md-3 col-lg-3">
							<div class="form-check form-check-inline">
								<input type="radio" name="atch_yn" id="atch_yn_y" value="Y" checked="checked" />
								<label for="atch_yn_y" class="form-check-label ml-1 mr-2">다운로드</label>
								<input type="radio" name="atch_yn" id="atch_yn_n" value="N" />
								<label for="atch_yn_n" class="form-check-label ml-1">화면표시</label>
							</div>
							
						</div>
						
						<div>
							<span><strong>화면표시 체크시 확장자는 이미지일 경우  소문자 jpg/png/gif/bmp,동영상일 경우 소문자 mp4로 업로드해주시기 바랍니다.</strong></span>
						</div>
						
					</div>
				
				</div>

				<div class="box-footer">
					<div class="text-center">
						<button type="button" class="btn btn-list" onclick="fnGoList(); return false;"><i class="fa fa-reply"></i> 목록</button>
						<button type="button" class="btn btn-write" onclick="fnGoInsert(); return false;"><i class="fa fa-pen"></i> 등록</button>
					</div>
				</div>

			</div>


			</form>
		</section>
	</div>
	<!-- footer -->
	<%@ include file="/common/inc/footer.jspf" %>
	<!-- //fooer -->
</div>
</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
