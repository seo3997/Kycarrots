<%@ page import="com.whomade.kycarrots.framework.common.util.file.vo.AtFileVO"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.SysUtil"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="resultMap" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="fileList"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
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
			fnComboStrFile('.fileBoxWrap', <%=fileList.size()%>, 5);
			
			$('.attach_file').on({
				// 이미지가 없어서 error 날시
				'error' : function(){
					$(this).attr('src', '/common/images/file_ext_ico/attach_etc.gif');
				}
			});
		});
		
		// 상세
		function fnDetail(){
			$('#aform').attr({ action : '/mgt/mboard/selectBoard.do', method : 'get' }).submit();
		}
		
		// 수정
		function fnGoUpdate(){

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
			
			
			if($('[name=cn]').val() == ''){
				alert('내용을 입력해 주세요.');
				$('[name=cn]').focus();
				return false;
			}
			
			// 확장자 체크
			var msg = f_CheckExceptFileExt('upload');
			if(msg != ''){
				alert(msg);
				return false;
			}
			
			if(confirm('수정하시겠습니까?')){
				$('#aform').attr({ action : '/mgt/mboard/updateBoard.do', method : 'post' }).submit();
			}
		}
		
		//파일 다운로드
		function fnDownload(file_id){
			$('[name=file_id]').val(file_id);
			$('#aform').attr({ 'action' : '/common/file/FileDown.do' }).submit();
		}
		
		//서버에 있는 파일 삭제
		function fnFileDel(obj, file_id){
			
			if(confirm('삭제하시겠습니까?')){
				
				var param = { 'file_id' : file_id };
				
				jQuery.ajax( {
					type : 'POST',
					dataType : 'json',
					url : '/common/file/deleteFileInfAjax.do',
					data : param,
					success : function(param) {

						if(param.resultStats.resultCode == 'error'){
							alert(param.resultStats.resultMsg);
							return;
						}else{
							alert(param.resultStats.resultMsg);
							location.reload();
						}
					},
					error : function(jqXHR, textStatus, thrownError){
						ajaxJsonErrorAlert(jqXHR, textStatus, thrownError)
					}
				});

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

			<form role="form" id="aform" method="post" action="/mgt/mboard/updateBoard.do" enctype="multipart/form-data" class="form-horizontal">
				<input type="hidden" name="bbs_seq" 				value="<%=resultMap.getString("BBS_SEQ") %>" />
				<input type="hidden" name="atch_doc_id" 			value="<%=resultMap.getString("ATCH_DOC_ID") %>" />
				<input type="hidden" name="sch_type" 				value="<%=param.getString("sch_type")%>" />
				<input type="hidden" name="sch_text" 				value="<%=param.getString("sch_text")%>" />
				<input type="hidden" name="sch_bbs_se_code_m" 		value="<%=param.getString("sch_bbs_se_code_m")%>" />
				<input type="hidden" name="currentPage" 			value="<%=param.getString("currentPage")%>"/>
				<input type="hidden" name="file_id" />
				<input type="hidden" name="bbs_se_code_l"	id="bbs_se_code_l" 			 	value="R010170"  />

			<div class="card">
			
				<h4 class="cardTitle"><i class="fa fa-caret-square-right"></i> 기본정보</h4>
				<div class="card-body viewForm">

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="bbs_se_code_m">게시판구분</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<select id="bbs_se_code_m" name="bbs_se_code_m" class="form-control input-sm" >
								<%=CommboUtil.getComboStr(boardComboStr, "CODE", "CODE_NM", param.getString("bbs_se_code_m", resultMap.getString("BBS_SE_CODE_M")) , "C")%>
							</select>
						</div>
					</div>

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="bbs_se_code_m">제목</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<input type="text" class="form-control" name="sj" id="sj" placeholder="제목" value="<%=param.getString("sj", resultMap.getString("SJ")) %>" maxlength="100" />
						</div>
					</div>

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="bbs_se_code_m">내용</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<textarea class="form-control" rows="5" name="cn" id="cn"><%=param.getString("cn", resultMap.getString("CN")) %></textarea>
						</div>
					</div>

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="bbs_se_code_m">첨부파일</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<div class="outBox1 fileBoxWrap"></div>
							<div class="outBox2 ftRed"></div>
						</div>
					</div>
					
					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="atch_yn_y">첨부파일 표시여부</label>
						<div class="checkbox col-xs-11 col-sm-8 col-md-3 col-lg-3">
							<div class="form-check form-check-inline">
								<input type="radio" name="atch_yn" id="atch_yn_y" value="Y" <c:if test="${'Y' eq resultMap.getString('ATCH_YN') || empty resultMap.getString('ATCH_YN')}"> checked="checked"</c:if> />
								<label for="atch_yn_y" class="form-check-label ml-1 mr-2">다운로드</label>
								<input type="radio" name="atch_yn" id="atch_yn_n" value="N" <c:if test="${'N' eq resultMap.getString('ATCH_YN') }"> checked="checked"</c:if> />
								<label for="atch_yn_n" class="form-check-label ml-1">화면표시</label>
							</div>
						</div>
						<div>
							<span><strong>화면표시 체크시 확장자는 이미지일 경우  소문자 jpg/png/gif/bmp,동영상일 경우 소문자 mp4로 업로드해주시기 바랍니다.</strong></span>
						</div>
					</div>

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="bbs_se_code_m">첨부파일 목록</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<% //파일이 존재하지 않으면 첨부파일 목록태그 그리지 않음
								if(fileList.size() > 0){
							%>
							<%
								for(int i = 0; i < fileList.size(); i++) {
											AtFileVO fvo = (AtFileVO)fileList.get(i);
							%>
							<a href="#"  onclick="fnDownload('<%=fvo.getFile_id()%>'); return false;">
							<img src = "/common/images/file_ext_ico/attach_<%=fvo.getFile_ext_nm().toLowerCase()%>.gif" width="16" height="16" class="attach_file" />
							<%=fvo.getFile_nm()%> (<%=fvo.getFile_size()/1000.0%>) KB
							</a>
							<a href="#" onclick="fnFileDel(this, '<%=fvo.getFile_id() %>'); return false;">[삭제]</a><br/>
							<%
									}
								}
							%>
						</div>
					</div>

				</div>

				<div class="box-footer">
             		<div class="text-center">
						<button type="button" class="btn btn-reset" onclick="fnDetail(); return false;"><i class="fa fa-reply"></i> 취소</button>
						<button type="button" class="btn btn-write" onclick="fnGoUpdate(); return false;"><i class="fa fa-pen"></i> 확인</button>
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
</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
