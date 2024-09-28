<%@page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.file.vo.AtFileVO"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@ page import="com.whomade.kycarrots.framework.common.util.SysUtil" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="resultMap" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="fileList" class="java.util.ArrayList" type="java.util.List" scope="request"/>


<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>
<script type="text/javascript" src="/common/js/pinchzoom/pinch-zoom.umd.js"></script>

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
			$('#aform').attr({ action : '/mgt/mfileboard/selectPageListBoard.do', method : 'get' }).submit();
		}
		
		// 수정폼 이동
		function fnGoUpdateForm(){
			$('#aform').attr({ action : '/mgt/mfileboard/updateFormBoard.do', method : 'get' }).submit();
		}
		
		 //삭제
		function fnGoDelete(){
			if(confirm('삭제하시겠습니까?')){
				$('#aform').attr({ action : '/mgt/mfileboard/deleteBoard.do', method : 'post' }).submit();
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
			
				<div class="col-md-12">
				     <div class="box box-widget">
	            	 	<div class="box-header with-border">
						  <div class="callout callout-info">
						    <h3 class="text-left"><%=resultMap.getString("SJ") %></h3>
						  </div>
			              <div class="user-block">
			                <img class="img-circle" src="/common/dist/img/user1-128x128.jpg" alt="User Image">
			                <span class="username"><a href="#"><%=resultMap.getString("REGISTER_NM") %></a></span>
			                <span class="description"><%=resultMap.getString("REGIST_DT") %></span>
			              </div>
						</div><!-- /.box-header -->
			             <div class="box-body">
				              <!-- post text -->
				              <p><%=resultMap.htmlTagFilterRestore(resultMap.getHtml("CN")) %></p>

						<div class="col-xs-12">
							<form role="form" id="aform" method="post" action="/mgt/mfileboard/selectBoard.do" class="form-horizontal">
							<input type="hidden" name="bbs_seq" value="<%=resultMap.getString("BBS_SEQ") %>" />
							<!-- 검색관련 -->
							<input type="hidden" name="sch_type" value="<%=param.getString("sch_type")%>" />
							<input type="hidden" name="sch_text" value="<%=param.getString("sch_text")%>" />
							<input type="hidden" name="sch_bbs_se_code" value="<%=param.getString("sch_bbs_se_code")%>" />
							<input type="hidden" name="currentPage" value="<%=param.getString("currentPage")%>"/>
							<input type="hidden" name="file_id" />
							<div class="post">
			                  <div class="row margin-bottom">
			                    <div class="col-sm-6">
									<%
										if(fileList.size() > 0){
											for(int i = 0; i < fileList.size(); i++) {
												AtFileVO fvo = (AtFileVO)fileList.get(i);
												if(SysUtil.fileImgyn(fvo.getFile_ext_nm())){
									%>
			                      				<img class="img-responsive" src="<%=fvo.getFile_rltv_path()+fvo.getFile_id()+"."+fvo.getFile_ext_nm()%>" alt="Photo">
									<%
											}else{
									%>
												<a href="#"  onclick="fnDownload('<%=fvo.getFile_id()%>'); return false;">
													<img src = "/common/images/file_ext_ico/attach_<%=fvo.getFile_ext_nm().toLowerCase()%>.gif" width="16" height="16" class="attach_file" />
													<%=fvo.getFile_nm()%> (<%=fvo.getFile_size()/1000.0%>) KB <br/>
												</a>
									<%
											}
									%>
									<%
											}
										}
									%>
			                    </div>
			                  </div>
			                </div>
							</form>
						</div><!--  /.col-xs-12 -->
						</div><!-- /.box-body -->


			            <div class="box-footer box-comments" id="commentlist">
				              <!-- 
				              <div class="box-comment">
				                <img class="img-circle img-sm" src="/common/dist/img/user3-128x128.jpg" alt="User Image">
				                <div class="comment-text">
				                      <span class="username">Maria Gonzales<span class="text-muted pull-right">8:03 PM Today</span></span>
				                  		It is a long established fact that a reader will be distractedby the readable content of a page when looking at its layout.
				                </div>
				                <div class="pull-right">
								<button type="button" class="btn btn-warning btn-sm" onclick="fnCommentDelete(1); return false;"><i class="fa fa-trash"></i> 삭제</button>
								</div>		
				              </div>
				              <div class="box-comment">
				                <img class="img-circle img-sm" src="/common/dist/img/user3-128x128.jpg" alt="User Image">
				                <div class="comment-text">
				                      <span class="username">Maria Gonzales<span class="text-muted pull-right">8:03 PM Today</span></span>
				                  		It is a long established fact that a reader will be distractedby the readable content of a page when looking at its layout.
				                </div>
				                <div class="pull-right">
								<button type="button" class="btn btn-warning btn-sm" onclick="fnCommentDelete(1); return false;"><i class="fa fa-trash"></i> 삭제</button>
								</div>		
				              </div>
				              <div class="box-comment">
				                <div class="comment-text">
				                  		데이타가 없습니다.
				                </div>
				              </div>
 								-->
				              
				         </div><!-- /.box-footer -->
				         <div class="box-footer">
								<form role="form" id="cform" method="post">
								<input type="hidden" name="comment_board"			id="comment_board" 		value="<%=resultMap.getString("BBS_SEQ") %>"/>
								<input type="hidden" name="comment_level"			id="comment_level" 			value="0"/>
								<input type="hidden" name="comment_orderno" 		id="comment_orderno"		value="0"/>
				                <img class="img-responsive img-circle img-sm" src="/common/dist/img/user4-128x128.jpg" alt="Alt Text">
				                <!-- .img-push is used to add margin to elements next to floating images -->
				                <div class="img-push">
									<textarea class="form-control input-sm" rows="3" id="comment_content"  name="comment_content"></textarea>
									<div class="text-right" style="margin-top:5px;">
										<button type="button" class="btn btn-sm btn-primary" id="btn_comment">댓글등록</button>
									</div>
				                </div>
				              </form>
				         </div><!-- /.box-footer -->
				     </div><!-- /.box -->
					 <div class="text-right">
					 				<button type="button" class="btn btn-default" onclick="fnGoList(); return false;"><i class="fa fa-reply"></i> 목록</button>
									<button type="button" class="btn btn-info" onclick="fnGoUpdateForm(); return false;"><i class="fa fa-pencil"></i> 수정</button>
									<button type="button" class="btn btn-warning" onclick="fnGoDelete(); return false;"><i class="fa fa-trash"></i> 삭제</button>
					 </div>
				</div><!--  /.col-xs-12 -->
			
			
			
			</div><!-- ./row -->
		</section><!-- /.content -->
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

	// Get the image and insert it inside the modal - use its "alt" text as a caption
	var img = $(".img-responsive");
	var modalImg =$("#img01");
	var captionText = $("#caption");
	
	img.click(function(e){
		modal.css("display","block");
		modalImg.attr("src",$(this).attr("src"));
		captionText.html($(this).attr("alt"));
	});
	

	// Get the <span> element that closes the modal
	var span = $(".close");

	// When the user clicks on <span> (x), close the modal
	span.click(function(e){
		modal.css("display","none");
	});

    new window.PinchZoom.default(document.querySelector('div.myzoom'), {});
	
	//코멘트 가져오기
	fnListBoardComment();
	window.location	="iwebaction:{'ACTION_CODE':'ACT1027','':'','ACTION_PARAM':{'APP_ID':'SEARCHNGO'}}";
	

</script>

</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
