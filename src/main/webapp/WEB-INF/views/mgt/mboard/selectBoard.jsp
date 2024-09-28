<%@page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.file.vo.AtFileVO"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@ page import="com.whomade.kycarrots.framework.common.util.SysUtil" %>
<%@ page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="resultMap" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="fileList" class="java.util.ArrayList" type="java.util.List" scope="request"/>
<jsp:useBean id="companyComboStr" class="java.util.ArrayList" type="java.util.List" scope="request" />
<jsp:useBean id="answerComboStr" type="java.util.List" class="java.util.ArrayList" scope="request" />

<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>

<html>
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>

   	<style type="text/css">
   			@keyframes resizeanim { from { opacity: 0; } to { opacity: 0; } } 
   			.resize-triggers {animation: 1ms resizeanim; visibility: hidden; opacity: 0;} 
   			.resize-triggers, .resize-triggers > div, .contract-trigger:before {content: " "; display: block; position: absolute; top: 0; left: 0; height: 100%; width: 100%; overflow: hidden;} 
   			.resize-triggers > div {background: #eee; overflow: auto;} 
   			.contract-trigger:before {width: 200%; height: 200%;}
    </style>

	<script type="text/javascript">
	//<![CDATA[
		$(function(){
			$('.attach_file').on({
				// 이미지가 없어서 error 날시
				'error' : function(){
					$(this).attr('src', '/common/images/file_ext_ico/attach_etc.gif');
				}
			});
		});
		
		// 목록
		function fnGoList(){
			$('#aform').attr({ action : '/mgt/mboard/selectPageListBoard.do', method : 'get' }).submit();
		}
		
		// 수정폼 이동
		function fnGoUpdateForm(){
			$('#aform').attr({ action : '/mgt/mboard/updateFormBoard.do', method : 'get' }).submit();
		}
		
		 //삭제
		function fnGoDelete(){
			if(confirm('삭제하시겠습니까?')){
				$('#aform').attr({ action : '/mgt/mboard/deleteBoard.do', method : 'post' }).submit();
			}
		}
		
		// 첨부파일 다운로드
		function fnDownload(file_id){
			$('[name=file_id]').val(file_id);
			$('#aform').attr({ 'action' : '/common/file/FileDown.do' }).submit();
		}
	
		// 게시판 코멘트 가져오기
		function fnListBoardComment(){
			 var sUrl		= "/mgt/board/selectPageListBoardComment.do";
			 var sParam	= {'comment_board'	:$('[name=comment_board]').val()
			 						};
			 var sType 	= "GET";
		
			 fnCallbackAjax(sUrl,sParam,sType,	function fncallback(pResponse){
					var results=pResponse.resultStats.resultList;
					
					console.log(results);
		            var trStr="";
		           	$( '#commentlist').empty();
		           	var irow=0;
		           	var tronClick="";
					$.each(results, function (i) {

    					//tronClick=	"'"+fnUndefined(results[i].AGCHM_STD_NATION_SEQ)+"'";

				      trStr = "<div class=\"box-comment\">";
				      trStr += "<img class=\"img-circle img-sm\" src=\"/common/dist/img/user3-128x128.jpg\" alt=\"User Image\">";
				      trStr += "<div class=\"comment-text\">";
				      trStr +=  "<span class=\"username\">"+results[i].REGISTER_NM+"<span class=\"text-muted pull-right\">"+results[i].COMMENT_DATE+"</span></span>";
				      trStr += results[i].COMMENT_CONTENT;
				   	  trStr += "</div>";
				   	  trStr += "<div class=\"pull-right\">";
				   	  trStr += "<button type=\"button\" class=\"btn btn-warning btn-sm\" onclick=\"fnCommentDelete("+results[i].COMMENT_NUM+"); return false;\"><i class=\"fa fa-trash\"></i> 삭제</button>";
					  trStr += "</div>";

						$('#commentlist').append(trStr);
						irow++;
					});
					
					if(irow==0){
						trStr="<div class=\"box-comment\"><div class=\"comment-text text-center\">댓글이 없습니다.</div></div>";
						$('#commentlist').append(trStr);
	   				}
				});

		}
	
		
		
	//]]>
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

			<form role="form" id="aform" method="post" action="/mgt/mboard/selectBoard.do" class="form-horizontal" enctype="multipart/form-data">
				<input type="hidden" name="bbs_seq" value="<%=resultMap.getString("BBS_SEQ") %>" />
				<!-- 검색관련 -->
				<input type="hidden" name="sch_type" value="<%=param.getString("sch_type")%>" />
				<input type="hidden" name="sch_text" value="<%=param.getString("sch_text")%>" />
				<input type="hidden" name="sch_bbs_se_code_m" value="<%=param.getString("sch_bbs_se_code_m") %>" />
				
				<%-- <input type="hidden" name="sch_bbs_se_code_m" value="<%=resultMap.getString("BBS_SE_CODE_M") %>" /> --%>
				
				<input type="hidden" name="sch_answerState_code" value="<%=param.getString("sch_answerState_code")%>" />
				<input type="hidden" name="currentPage" value="<%=param.getString("currentPage")%>"/>
				<input type="hidden" name="file_id" />
				

			<div class="card">
				<div class="card-body viewForm">

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="bbs_se_code_m">게시판구분</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<%=resultMap.getString("BBS_SE_NM") %>
						</div>
					</div>

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="bbs_se_code_m">제목</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<%=resultMap.getString("SJ") %>
						</div>
					</div>
					
					<div class="form-group row">
						<label for="" class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">등록자명</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
							<%=resultMap.getString("REGISTER_NM") %>
						</div>
						<label for="" class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">등록자ID</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
							<%=resultMap.getString("REGISTER_ID") %>
						</div>
					</div>

					
					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="bbs_se_code_m">등록일시</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<%=resultMap.getString("REGIST_DT") %>
						</div>
					</div>

					

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="bbs_se_code_m">내용</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<%=resultMap.getHtml("CN") %>
							<%
							if(fileList.size() > 0){
								for(int i = 0; i < fileList.size(); i++) {
									AtFileVO fvo = (AtFileVO)fileList.get(i);
							%>
							
							<!-- 첨부파일을 화면에 표시하는경우 -->
							<% if ("N".equals(resultMap.getString("ATCH_YN"))) { %>
								<% if ("jpg".equals(fvo.getFile_ext_nm()) || "png".equals(fvo.getFile_ext_nm()) || "gif".equals(fvo.getFile_ext_nm()) || "bmp".equals(fvo.getFile_ext_nm())) { %>
									<br><img src="<%=fvo.getFile_rltv_path() + fvo.getFile_id() + '.' + fvo.getFile_ext_nm() %>" style="max-width:100%; border:1px solid #999"><br>
								<% } else if ("mp4".equals(fvo.getFile_ext_nm())) { %>
									<br><div class="video"><video width="100%" height="auto" controls><source src="<%=fvo.getFile_rltv_path() + fvo.getFile_id() + '.' + fvo.getFile_ext_nm() %>" type="video/mp4"></video></div>
								<% } %>
							<!-- 첨부파일 다운로드인 경우 -->
							<% } else { %>
								<br>
								<div style="float:left">
									<strong>첨부파일</strong> &nbsp;
									<a href="#" onclick="fnDownload('<%=fvo.getFile_id()%>'); return false;">
										<img src="/common/images/file_ext_ico/attach_etc.gif" width="16" height="16" class="attach_file" />
										<%=fvo.getFile_nm()%> (<%=fvo.getFile_size()/1000.0%>) KB <br/>
									</a>
								</div>
							<% } %>
						<%
								}
							}
						%>
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
		</section>
	</div>

<!-- The Modal -->
<div id="myModal" class="modal">
  <span class="close">&times;</span>
  <div class="myzoom">
  <img class="modal-content" id="img01">
  </div>
  <div id="caption"></div>
</div>


	<!-- footer -->
	<%@ include file="/common/inc/footer.jspf" %>
	<!-- //fooer -->
</div>
<script type="text/javascript">
	//신규
	function fnClear(){
		$('#comment_content').val("");
	}

 	function fnInsertComment(){
		if($('#comment_content').val() == ''){
			alert('내용을 입력해 주세요.');
			$('#comment_content').focus();
			return false;
		}
		if(confirm('등록하시겠습니까?')){
			   alert('SUBMIT 이됨');

			   var sUrl		= "/mgt/board/insertBoardComment.do";
               var fFrom  =$("#cform");
				fnCallbackFormAjax(sUrl,fFrom,function fncallback(pResponse){
    				var results=pResponse.resultStats.resultList;
    				console.log("등록["+results+"]");
                	if(pResponse.resultStats.resultCode=="ok"){
                		alert("처리되었습니다.");
                		fnClear();
                		fnListBoardComment();
                	}
			 	});
 		}
	}
 	
 	$('#btn_comment').on('click', function(e) {
		if($('#comment_content').val() == ''){
			alert('내용을 입력해 주세요.');
			$('#comment_content').focus();
			return false;
		}
		if(confirm('등록하시겠습니까?')){
			   var sUrl		= "/mgt/board/insertBoardComment.do";
               var fFrom  =$("#cform");
				fnCallbackFormAjax(sUrl,fFrom,function fncallback(pResponse){
    				var results=pResponse.resultStats.resultList;
                	if(pResponse.resultStats.resultCode=="ok"){
                		alert("처리되었습니다.");
                		fnClear();
                		fnListBoardComment();
                	}
			 	});
 		}
	});
 
	// 삭제
	function fnCommentDelete(pComment_num){
		
		if(confirm('삭제하시겠습니까?')){
			 var sUrl 		= "/mgt/board/deleteBoardComment.do";
			 var sParam = "comment_num="+pComment_num;
			 var sType 	= "GET";
			 
			 fnCallbackAjax(sUrl,sParam,sType,function fncallback(pResponse){
    				var results=pResponse.resultStats.resultList;
                	if(pResponse.resultStats.resultCode=="ok"){
                		alert("처리되었습니다.");
                		fnListBoardComment();
                		fnClear();
                	}
			 });
			 
		}
	}

	// Get the modal
	var modal = $('#myModal');


	//코멘트 가져오기
	//fnListBoardComment();
	
</script>

</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
