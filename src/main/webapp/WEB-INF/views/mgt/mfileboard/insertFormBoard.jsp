<%@ page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.SysUtil"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="bbsSeComboStr"  type="java.util.List" class="java.util.ArrayList" scope="request"/>

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
			$('#aform').attr({ action : '/mgt/mfileboard/selectPageListBoard.do', method : 'post' }).submit();
		}
		
		// 등록
		function fnGoInsert(){
			
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
				$('#aform').attr({ action : '/mgt/mfileboard/insertBoard.do', method : 'post' }).submit();
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
			            <div class="box-header with-border"><h3 class="box-title">창고자료 등록</h3></div>
						 <form role="form" id="aform" method="post" action="/mgt/mfileboard/insertBoard.do" enctype="multipart/form-data" class="form-horizontal">
							<input type="hidden" 	name="cn_doc_id"				id="cn_doc_id" 						value="<%=param.getString("cn_doc_id")%>" />
							<input type="hidden" 	name="bbs_se_code_l"		id="bbs_se_code_l" 			 	value="R010170"  />
							<input type="hidden"  	name="bbs_se_code_m"	id="bbs_se_code_m"  			value="<%=param.getString("bbs_se_code_m")%>" />
			
			              <div class="box-body">
							<div class="form-group">
			                  <label for="inputSubject" class="col-sm-2 control-label">제목</label>
			                  <div class="col-sm-10">
										<input type="text" class="form-control" name="sj" id="sj" placeholder="제목" value="<%=param.getString("sj") %>" maxlength="100" />
			                  </div>
			                </div>
			
			                <!-- textarea -->
			                <div class="form-group">
			                  <label for="textareaContents" class="col-sm-2 control-label">내용</label>
							  <div class="col-sm-10">
										<textarea class="form-control" rows="3" name="cn" id="cn"><%=param.getString("cn") %></textarea>
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
			
			              </div>

			              <div class="box-footer col-sm-offset-2">
									<button type="button" class="btn btn-default" onclick="fnGoList(); return false;"><i class="fa fa-reply"></i> 목록</button>
									<button type="button" class="btn btn-info" onclick="fnGoInsert(); return false;"><i class="fa fa-pencil"></i> 등록</button>
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
