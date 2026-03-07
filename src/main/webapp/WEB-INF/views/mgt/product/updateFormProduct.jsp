<%@ page import="com.whomade.kycarrots.entity.product.TnProductImageVo"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.StringUtil"%>
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
<jsp:useBean id="saleStatusComboStr"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="categoryMComboStr"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="areaMComboStr"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="unitCodeComboStr"  type="java.util.List" class="java.util.ArrayList" scope="request"/>


<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>
<%
	String currentEditorMode = resultMap.getString("EDITOR_MODE");
	if (currentEditorMode.equals("")) currentEditorMode = resultMap.getString("editorMode");
	
	// Handle MySQL TINYINT(1) as boolean behavior
	if (currentEditorMode.equalsIgnoreCase("true")) currentEditorMode = "1";
	else if (currentEditorMode.equalsIgnoreCase("false")) currentEditorMode = "0";

	if (currentEditorMode.equals("")) currentEditorMode = "3"; // Default to Plain Text
%>
<html>
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>
	<style>
	  .thumb-grid { display:flex; flex-wrap:wrap; }
	  .thumb-box { position:relative; margin:0 8px 8px 0; }
	  .product-thumb { width:200px; height:200px; object-fit:cover; border:1px solid #ccc; cursor:zoom-in; }
	  .badge-main { position:absolute; top:6px; left:6px; }
	  .thumb-actions { position:absolute; right:6px; bottom:6px; display:flex; gap:6px; }
	  .thumb-actions .btn { padding:2px 6px; font-size:12px; line-height:1.2; }
	  
	  /* Summernote custom styling */
	  .note-editor.note-frame { border: 1px solid #ced4da; }
	  .note-editor.note-frame .note-statusbar { display: none; }
	</style>
	<!-- Summernote CSS/JS -->
	<link rel="stylesheet" href="/common/summernote/summernote-bs4.css">
	<script type="text/javascript" src="/common/summernote/summernote-bs4.js"></script>
	<script type="text/javascript" src="/common/summernote/lang/summernote-ko-KR.js"></script>

	<script type="text/javascript">
	//<![CDATA[
		$(function(){
			rebuildImageMetas();
		    $('#aform').on('submit', function(){ rebuildImageMetas(); });

			// Summernote 초기화
			if ('<%=currentEditorMode%>' === '1') {
				initSummernote();
			}


			// Editor Mode 변경 시
			$('#editorMode').on('change', function() {
				if (this.value === '1') {
					initSummernote();
				} else {
					$('#description').summernote('destroy');
				}
			});
		});

		function initSummernote() {
			$('#description').summernote({
				height: 400,
				lang: 'ko-KR',
				toolbar: [
					['style', ['style']],
					['font', ['bold', 'underline', 'clear']],
					['color', ['color']],
					['para', ['ul', 'ol', 'paragraph']],
					['table', ['table']],
					['insert', ['link', 'picture', 'video']],
					['view', ['fullscreen', 'codeview', 'help']]
				],
				callbacks: {
					onImageUpload: function(files) {
						for (var i = 0; i < files.length; i++) {
							uploadSummernoteImage(files[i], this);
						}
					},
					onMediaDelete: function($target) {
						var src = $target.attr('src');
						deleteSummernoteImage(src);
					}
				}
			});
		}

		function uploadSummernoteImage(file, editor) {
			var data = new FormData();
			data.append("file", file);
			data.append("productId", $('#productId').val());

			$.ajax({
				url: '/mgt/product/uploadSummernoteImage.do',
				cache: false,
				contentType: false,
				processData: false,
				data: data,
				type: "POST",
				success: function(res) {
					$(editor).summernote('insertImage', res.url);
				},
				error: function(err) {
					console.error(err);
					alert("이미지 업로드에 실패했습니다.");
				}
			});
		}

		function deleteSummernoteImage(src) {
			$.ajax({
				url: '/mgt/product/deleteSummernoteImage.do',
				data: { src: src },
				type: "POST",
				success: function(res) {
					console.log("Image deleted from server");
				}
			});
		}
		
		// 상세
		function fnDetail(){
			$('#aform').attr({ action : '/mgt/product/selectProduct.do', method : 'get' }).submit();
		}

		// 미리보기
		function fnPreview() {
			var mode = $('#editorMode').val();
			var content = (mode === '1') ? $('#description').summernote('code') : $('#description').val();
			var $previewBody = $('#previewModalBody');

			// Clear previous content
			$previewBody.empty();

			if (mode === '1' || mode === '2') {
				// Summernote or Raw HTML: Render as-is
				$previewBody.html(content);
			} else {
				// Plain Text: Escape and convert newlines (matches selectProduct.jsp logic)
				var escaped = $('<div>').text(content).html();
				$previewBody.html(escaped.replace(/\n/g, '<br/>'));
			}
			$('#previewModal').modal('show');
		}

		// 수정
		function fnGoUpdate(){

			if($('[name=title]').val() == ''){
				alert('상품명을 입력해 주세요.');
				$('[name=title]').focus();
				return false;
			}
			if($('[name=price]').val() == ''){
				alert('상품가격을 입력해 주세요.');
				$('[name=price]').focus();
				return false;
			}
			$('[name=price]').val(removeComma($('[name=price]').val()));
			if($('[name=supplyPrice]').val() == ''){
				alert('본사 공급가격을 입력해 주세요.');
				$('[name=supplyPrice]').focus();
				return false;
			}
			$('[name=supplyPrice]').val(removeComma($('[name=supplyPrice]').val()));
/*
			if($('[name=desiredShippingDate]').val() == ''){
				alert('희망 출하일을 입력해 주세요.');
				$('[name=desiredShippingDate]').focus();
				return false;
			}
*/
			if($('[name=quantity]').val() == ''){
				alert('남은수량을 입력해 주세요.');
				$('[name=quantity]').focus();
				return false;
			}
			$('[name=quantity]').val(removeComma($('[name=quantity]').val()));

			if($('[name=unitCode]').val() == ''){
				alert('단위를 선택주세요.');
				$('[name=unitCode]').focus();
				return false;
			}
			if($('[name=categoryMid]').val() == ''){
				alert('카테고리를 선택주세요.');
				$('[name=categoryMid]').focus();
				return false;
			}
			if($('[name=categoryScls]').val() == ''){
				alert('카테고리(중)를 선택주세요.');
				$('[name=categoryScls]').focus();
				return false;
			}
			if($('[name=areaMid]').val() == ''){
				alert('지역를 선택주세요.');
				$('[name=areaMid]').focus();
				return false;
			}
			if($('[name=areaScls]').val() == ''){
				alert('지역(중)를 선택주세요.');
				$('[name=areaScls]').focus();
				return false;
			}

			if (!ensureImagesOrAlert()) return false;
			//console.log($('[name=imageMetasJson]').val())

			if(confirm('수정하시겠습니까?')){
				$('#aform').attr({ action : '/mgt/product/updateProduct.do', method : 'post' }).submit();
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

			<form role="form" id="aform" method="post" action="/mgt/product/updateProduct.do" enctype="multipart/form-data" class="form-horizontal">
                <input type="hidden" id="productId"             name="productId" 				value="<%=resultMap.getString("PRODUCT_ID") %>" />
                <input type="hidden" id="categoryGroup"         name="categoryGroup" 			value="R010610" />
				<input type="hidden" id="areaGroup"             name="areaGroup" 			    value="R010070" />
				<input type="hidden" id="unitGroup"             name="unitGroup" 			    value="R010620" />
				<input type="hidden" id="sch_type"              name="sch_type" 				value="<%=param.getString("sch_type")%>" />
				<input type="hidden" id="sch_text"              name="sch_text" 				value="<%=param.getString("sch_text")%>" />
                <input type="hidden" id="sch_sale_status_code"  name="sch_sale_status_code" 	value="<%=param.getString("sch_sale_status_code")%>" />
				<input type="hidden" id="sch_category_m_code"   name="sch_category_m_code" 	    value="<%=param.getString("sch_category_m_code")%>" />
				<input type="hidden" id="sch_area_m_code"       name="sch_area_m_code" 	        value="<%=param.getString("sch_area_m_code")%>" />
				<input type="hidden" id="currentPage"           name="currentPage" 			    value="<%=param.getString("currentPage")%>"/>
				<input type="hidden" name="imagesTouched" id="imagesTouched" value="0" />
			<div class="card">
			
				<h4 class="cardTitle"><i class="fa fa-caret-square-right"></i> 기본정보</h4>
				<div class="card-body viewForm">

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="saleStatus">판매상태</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<select id="saleStatus" name="saleStatus" class="form-control input-sm w-25" >
								<%=CommboUtil.getComboStr(saleStatusComboStr, "CODE", "CODE_NM", param.getString("saleStatus", resultMap.getString("SALE_STATUS")) , "C")%>
							</select>
						</div>
					</div>

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="title">상품명</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<input type="text" class="form-control" name="title" id="title" placeholder="제목" value="<%=param.getString("title", resultMap.getString("TITLE")) %>" maxlength="100" />
						</div>
					</div>

                    <div class="form-group row">
						<label  class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">상품가격</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
							<input type="text" class="form-control numeric w-25" name="price" id="price" placeholder="상품판매가" value="<%=StringUtil.setComma(param.getString("price", resultMap.getString("PRICE")))%>" maxlength="10" />
						</div>
                        <label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">본사공급원가</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
							<input type="text" class="form-control numeric w-25" name="supplyPrice" id="supplyPrice" placeholder="본사공급가격" value="<%=StringUtil.setComma(param.getString("supplyPrice", resultMap.getString("SUPPLY_PRICE")))%>" maxlength="10" />
						</div>
					</div>

                    <div class="form-group row">
                        <label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="taxType">과세구분</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
							<select id="taxType" name="taxType" class="form-control input-sm w-25" >
								<option value="TAX" <%= "TAX".equals(resultMap.getString("TAX_TYPE")) ? "selected" : "" %>>과세</option>
								<option value="FREE" <%= "FREE".equals(resultMap.getString("TAX_TYPE")) ? "selected" : "" %>>면세</option>
							</select>
						</div>
					</div>

                    <div class="form-group row">
                        <label  class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">남은수량</label>
                        <div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
                            <input type="text" class="form-control numeric w-25" name="quantity" id="quantity" placeholder="남은수량" value="<%=StringUtil.setComma(param.getString("quantity", resultMap.getString("QUANTITY"))) %>" maxlength="10" />
                        </div>
                        <label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">단위</label>
                        <div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
   						    <select id="unitCode" name="unitCode" class="form-control input-sm w-25" >
								<%=CommboUtil.getComboStr(unitCodeComboStr, "CODE", "CODE_NM", param.getString("unitCode", resultMap.getString("UNIT_CODE")) , "C")%>
							</select>
                        </div>
                    </div>

                    <div class="form-group row">
						<label  class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">카테고리</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
					        <select id="categoryMid" name="categoryMid" class="form-control input-sm w-25" style="display:inline">
								<%=CommboUtil.getComboStr(categoryMComboStr, "CODE", "CODE_NM", param.getString("categoryMid", resultMap.getString("CATEGORY_MID")) , "C")%>
							</select>
							<select id="categoryScls" name="categoryScls" class="form-control input-sm w-25" style="display:inline">
								<option value="">선택하세요</option>
							</select>
                        </div>
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">지역</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
					        <select id="areaMid" name="areaMid" class="form-control input-sm w-25" style="display:inline">
								<%=CommboUtil.getComboStr(areaMComboStr, "CODE", "CODE_NM", param.getString("areaMid", resultMap.getString("AREA_MID")) , "C")%>
							</select>
							<select id="areaScls" name="areaScls" class="form-control input-sm w-25" style="display:inline">
								<option value="">선택하세요</option>
							</select>
						</div>
					</div>

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">에디터 모드</label>
						<div class="col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<select id="editorMode" name="editorMode" class="form-control input-sm w-25">
								<option value="1" <%= "1".equals(currentEditorMode) ? "selected" : "" %>>Summernote 에디터</option>
								<option value="2" <%= "2".equals(currentEditorMode) ? "selected" : "" %>>Raw HTML 직접입력</option>
								<option value="3" <%= "3".equals(currentEditorMode) ? "selected" : "" %>>Plain Text (단순 텍스트)</option>
							</select>
						</div>
					</div>

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="description">
							상품 상세 설명
							<button type="button" class="btn btn-info btn-xs ml-2" onclick="fnPreview(); return false;">미리보기</button>
						</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<textarea class="form-control" rows="10" name="description" id="description"><%=param.getString("description", resultMap.getString("DESCRIPTION")) %></textarea>
						</div>
					</div>

					<div class="form-group row">
					  <label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">상품 이미지</label>
					  <div class="col-xs-12 col-sm-9 col-md-9 col-lg-10">

						<!-- 업로드 버튼 -->
						<div class="mb-2">
						  <button type="button" class="btn btn-secondary btn-sm" onclick="addNewImage(); return false;">
							<i class="fa fa-plus"></i> 이미지 추가
						  </button>
						  <small class="text-muted ml-2">최대 4장까지 등록 가능 (첫 번째가 대표)</small>
						</div>

						<!-- 썸네일 리스트 -->
						<div class="thumb-grid" id="productImages">
						  <%
							java.util.List<TnProductImageVo> imgs = new java.util.ArrayList<>();
							if (fileList != null) {
							  for (int i = 0; i < fileList.size(); i++) {
								TnProductImageVo iv = (TnProductImageVo) fileList.get(i);
								String code = iv.getImageCd();
								if (("1".equals(code) || "2".equals(code)) && imgs.size() < 4) {
								  imgs.add(iv);
								}
							  }
							}
							if (imgs.isEmpty()) {
						  %>
							<span class="text-muted empty-placeholder">이미지 없음</span>
						  <%
							} else {
							  for (int i = 0; i < imgs.size(); i++) {
								TnProductImageVo iv = imgs.get(i);
								String alt = (i == 0) ? "대표 이미지" : ("추가 이미지 " + i);
						  %>
							<div class="thumb-box" data-image-id="<%= iv.getImageId() %>">
							  <img src="<%= iv.getImageUrl() %>" class="img-thumbnail product-thumb"
								   alt="<%= alt %>" onclick="openImgModal(this)">
							  <% if (i == 0) { %>
								<span class="badge badge-primary badge-main">대표</span>
							  <% } %>
								  <div class="thumb-actions">
								  <button type="button" class="btn btn-primary btn-xs btn-designate"  onclick="setAsMain('<%= iv.getImageId() %>')">대표지정</button>
								  <button type="button" class="btn btn-danger btn-xs" onclick="deleteImage('<%= iv.getImageId() %>')">삭제</button>
								</div>
							</div>
						  <%
							  }
							}
						  %>
						</div>
						<!-- 현재 이미지 배열/대표 정보 제출용 -->
						<input type="hidden" name="imageMetasJson" id="imageMetasJson" />
						<!-- 새 파일 input들을 담아둘 컨테이너(숨김) -->
						<div id="newImageInputs" style="display:none;"></div>

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
	<!-- The Image Modal -->
	<div id="myModal" class="modal">
	  <span class="close">&times;</span>
	  <div class="myzoom">
	  <img class="modal-content" id="img01">
	  </div>
	  <div id="caption"></div>
	</div>

	<!-- Preview Modal -->
	<div class="modal fade" id="previewModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">상품 상세 설명 미리보기</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body" id="previewModalBody" style="min-height: 300px; max-height: 300px; overflow-y: auto;">
					<!-- Content injected by JS -->
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
				</div>
			</div>
		</div>
	</div>

	<!-- footer -->
	<%@ include file="/common/inc/footer.jspf" %>
	<!-- //fooer -->
</div>
<script type="text/javascript">
    $('#categoryMid').on('change', function(e) {
        fnGetSCodeList('R010610',this.value,$("#categoryScls"),'','C');
    });

    $('#areaMid').on('change', function(e) {
		fnGetSCodeList('R010070',this.value,$("#areaScls"),'','C');
	});

    $('#price').on('change', function(e) {
        this.value=setComma(this.value);
    });

    $('#supplyPrice').on('change', function(e) {
        this.value=setComma(this.value);
    });

    $('#quantity').on('change', function(e) {
        this.value=setComma(this.value);
    });

    fnGetSCodeList('R010610','<%=resultMap.getString("CATEGORY_MID")%>',$("#categoryScls"),'<%=resultMap.getString("CATEGORY_SCLS")%>','C');
    fnGetSCodeList('R010070','<%=resultMap.getString("AREA_MID")%>',$("#areaScls"),'<%=resultMap.getString("AREA_SCLS")%>','C');


  var MAX_IMAGES = 4;
  var newImageIndexCounter = 0;

  function touchImages(){
	  $('#imagesTouched').val('1');
  }
  function hasAtLeastOneImage(){
	  return $('#productImages .thumb-box').length >= 1;
  }

  function ensureImagesOrAlert(){
	  if (!hasAtLeastOneImage()){
		alert('상품 이미지는 최소 1장 이상 등록해야 합니다.');
		return false;
	  }
	  return true;
  }
  function openImgModal(imgEl){
    $('#img01').attr('src', imgEl.src);
    $('#caption').text(imgEl.alt || '');
    $('#myModal').fadeIn(100);
  }
  $('.close').on('click', function(){ $('#myModal').fadeOut(100); });
  $('#myModal').on('click', function(e){ if (e.target.id === 'myModal') $('#myModal').fadeOut(100); });

  // 대표 배지/버튼 토글 + '이미지 없음' 표시 관리
  function refreshMainUI(){
    var $grid  = $('#productImages');
    var $boxes = $grid.find('.thumb-box');

    // 배지/버튼 초기화
    $grid.find('.badge-main').remove();
    $boxes.find('.btn-designate').show();

    // 첫 번째 = 대표 → 배지 붙이고 ‘대표지정’ 버튼 숨김
    var $first = $boxes.first();
    if ($first.length){
      $first.append('<span class="badge badge-primary badge-main">대표</span>');
      $first.find('.btn-designate').hide();
    }
  }

  // 현재 DOM 순서 → imageMetasJson 생성 (첫 번째 대표)
  function rebuildImageMetas(){
    var arr = [];
    $('#productImages .thumb-box').each(function(idx){
      var imageId = $(this).data('imageId');  // 기존
      var newIdx  = $(this).data('newIndex'); // 신규/교체 시 사용 가능(현재 UI는 교체 미사용)
      var meta = {
        imageId: imageId ? String(imageId) : null,
        represent: (idx === 0 ? 1 : 0)
      };
      if (newIdx !== undefined) meta.newIndex = Number(newIdx);
      arr.push(meta);
    });
    $('#imageMetasJson').val(JSON.stringify(arr));
    refreshMainUI();
  }

  // 대표 지정: 해당 썸네일을 맨 앞으로
  function setAsMain(imageIdOrNew){
    var $box;
    if (imageIdOrNew && /^\d+$/.test(String(imageIdOrNew))) {
      $box = $('#productImages .thumb-box[data-image-id="'+imageIdOrNew+'"]'); // 기존
    } else {
      $box = $('#productImages .thumb-box[data-new-index="'+imageIdOrNew+'"]'); // 신규
    }
    if ($box && $box.length) {
      $('#productImages').prepend($box);
      rebuildImageMetas();
	  touchImages();
    }
  }

  // 기존 이미지 삭제 (서버 즉시 삭제)
  function deleteImage(imageId){
    if (!confirm('이미지를 삭제하시겠습니까?')) return;
    $.ajax({
      url: '/api/product/image/delete',
      type: 'POST',
      data: { imageId: imageId },
      success: function(){
        $('#productImages .thumb-box[data-image-id="'+imageId+'"]').remove();
        rebuildImageMetas();
		touchImages();
      },
      error: function(xhr){
        alert('삭제 실패: ' + (xhr.responseText || xhr.status));
      }
    });
  }

  // 신규 이미지 삭제 (클라이언트에서만 제거 + 해당 파일 input 제거)
  function deleteNewImage(newIndex){
    $('#productImages .thumb-box[data-new-index="'+newIndex+'"]').remove();
    $('#input-images-' + newIndex).remove();
    rebuildImageMetas();
	touchImages();
  }

  // 신규 이미지 추가
  function addNewImage(){
    var currCnt = $('#productImages .thumb-box').length;
    if (currCnt >= MAX_IMAGES) {
      alert('이미지는 최대 ' + MAX_IMAGES + '장까지 등록할 수 있습니다.');
      return;
    }
    var idx = newImageIndexCounter++;
    //var $inp = $('<input type="file" accept="image/*" name="images['+idx+']" id="input-images-'+idx+'">');
    var $inp = $('<input type="file" accept="image/*" name="upload" id="input-images-'+idx+'">');
    $inp.on('change', function(){
      if (!this.files || !this.files[0]) { $(this).remove(); return; }
      var url = URL.createObjectURL(this.files[0]);
      // 빈 placeholder 제거
      $('#productImages .empty-placeholder').remove();

      var thumb =
        '<div class="thumb-box" data-new-index="'+idx+'">'+
          '<img src="'+url+'" class="img-thumbnail product-thumb" alt="신규 이미지" onclick="openImgModal(this)">'+
          '<div class="thumb-actions">'+
            '<button type="button" class="btn btn-primary btn-xs btn-designate" onclick="setAsMain('+idx+')">대표지정</button>'+
            '<button type="button" class="btn btn-danger btn-xs" onclick="deleteNewImage('+idx+')">삭제</button>'+
          '</div>'+
        '</div>';

      $('#productImages').append(thumb);
      rebuildImageMetas();
	  touchImages();
    });
    $('#newImageInputs').append($inp);
    $inp.trigger('click');
  }
</script>
</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
