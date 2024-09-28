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
			$('#aform').attr({ action : '/mgt/mfileboard/selectBoard.do', method : 'get' }).submit();
		}
		
		// 수정
		function fnGoUpdate(){
			
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
				$('#aform').attr({ action : '/mgt/mfileboard/updateBoard.do', method : 'post' }).submit();
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
		<section class="content">
			<div class="row">

		        <div class="col-xs-12">
			          <div class="box box-primary">
			            <div class="box-header with-border"><h3 class="box-title">창고자료 수정</h3></div>
					<form role="form" id="aform" method="post" action="/mgt/mfileboard/updateBoard.do" enctype="multipart/form-data" class="form-horizontal">
						<input type="hidden" name="bbs_seq" 				value="<%=resultMap.getString("BBS_SEQ") %>" />
						<input type="hidden" name="atch_doc_id" 			value="<%=resultMap.getString("ATCH_DOC_ID") %>" />
						<input type="hidden" name="sch_type" 				value="<%=param.getString("sch_type")%>" />
						<input type="hidden" name="sch_text" 				value="<%=param.getString("sch_text")%>" />
						<input type="hidden" name="sch_bbs_se_code" 	value="<%=param.getString("sch_bbs_se_code")%>" />
						<input type="hidden" name="currentPage" 			value="<%=param.getString("currentPage")%>"/>
						<input type="hidden" name="file_id" />
						<input type="hidden" 	name="bbs_se_code_l"	id="bbs_se_code_l" 			 	value="R010170"  />
						<input type="hidden"  	name="bbs_se_code_m"	id="bbs_se_code_m"  			value="20" />
			
			              <div class="box-body">
							<div class="form-group">
			                  <label for="inputSubject" class="col-sm-2 control-label"> 제목</label>
			                  <div class="col-sm-10">
										<input type="text" class="form-control" name="sj" id="sj" placeholder="제목" value="<%=param.getString("sj", resultMap.getString("SJ")) %>" maxlength="100" />
			                  </div>
			                </div>
			
			                <!-- textarea -->
			                <div class="form-group">
			                  <label for="textareaContents" class="col-sm-2 control-label">내용</label>
							  <div class="col-sm-10">
										<textarea class="form-control" rows="3" name="cn" id="cn"><%=param.getString("cn", resultMap.getString("CN")) %></textarea>
							  </div>
			                </div>
			
			                <div class="form-group">
			                  <label for="inputFile" class="col-sm-2 control-label">첨부파일</label>
							  <div class="col-sm-10">
									<div class="outBox1 fileBoxWrap">
									</div>
									<div class="outBox2 ftRed">
									</div>
							  </div>
			                </div>
							<div class="form-group">
								<label class="col-sm-2 control-label">첨부파일 목록</label>
								<div class="col-sm-5">
								<% 
									//파일이 존재하지 않으면 첨부파일 목록태그 그리지 않음
									if(fileList.size() > 0){
								%>
								<%
												for(int i = 0; i < fileList.size(); i++) {
													AtFileVO fvo = (AtFileVO)fileList.get(i);
								%>
												<div>
													<a href="#"  onclick="fnDownload('<%=fvo.getFile_id()%>'); return false;">
														<img src = "/common/images/file_ext_ico/attach_<%=fvo.getFile_ext_nm().toLowerCase()%>.gif" width="16" height="16" class="attach_file" />
														<%=fvo.getFile_nm()%> (<%=fvo.getFile_size()/1000.0%>) KB
													</a>
													<a href="#" onclick="fnFileDel(this, '<%=fvo.getFile_id() %>'); return false;">[삭제]</a><br/>
												</div>
								<%
												}
									}
								%>
								
								</div>
							</div>

			
			              </div>

			              <div class="box-footer col-sm-offset-2">
									<button type="button" class="btn btn-default" onclick="fnDetail(); return false;"><i class="fa fa-reply"></i> 취소</button>
									<button type="button" class="btn btn-info" onclick="fnGoUpdate(); return false;"><i class="fa fa-pencil"></i> 확인</button>
			              </div>

			            </form>
			          </div>
		        </div><!--  col-xs-12 -->


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
