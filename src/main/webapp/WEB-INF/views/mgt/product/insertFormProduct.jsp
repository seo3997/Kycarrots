<%@ page import="com.whomade.kycarrots.entity.product.TnProductImageVo"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@ page import="com.whomade.kycarrots.framework.common.util.SysUtil"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="saleStatusComboStr"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="categoryMComboStr"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="areaMComboStr"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="unitCodeComboStr"  type="java.util.List" class="java.util.ArrayList" scope="request"/>

<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>
<%
   String saleStatus = "0";
   String saleStatusNm = "승인요청";

	String currentEditorMode = param.getString("editorMode");
	if (currentEditorMode.equals("")) currentEditorMode = "1"; // Default to Summernote for new registration
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

			// 하나의 모달을 사용자/센터 검색에 모두 재사용
			$("#selectModal").on("show.bs.modal", function (event) {
			  var $btn  = $(event.relatedTarget);
			  var discd = $btn.data("discd");           // modalUser | modalWholesaler
			  var goUrl = "";
			  var mode  = "";                           // 'user' | 'wh'

			  if (discd === "modalUser") {
				goUrl = "/api/members/wholesalers?memberCode=ROLE_SELL";
				$(".modal-title").text("판매자검색");
				mode = "user";
			  } else if (discd === "modalBranch") {
				goUrl = "/api/members/wholesalers?memberCode=ROLE_PROJ";
				$(".modal-title").text("센터검색");
				mode = "branch";
			  } else {
				return; // 정의되지 않은 호출은 무시
			  }

			  var $modal = $(this);
			  $modal.data("mode", mode);                // 선택 후 어디에 세팅할지 기억
			  $modal.find("#modal-body").html('<div class="p-3 text-center">Loading...</div>');

			  $.ajax({
				type: "GET",
				url: goUrl,
				dataType: "json",
				cache: false,
				success: function (list) {
				  if (!Array.isArray(list)) list = [];
				  // 테이블 렌더
				  var rows = list.map(function (u, i) {
					var no = u.userNo || "";
					var id = u.userId || "";
					var nm = u.userNm || "";
					return (
					  '<tr data-user-no="'+no+'" data-user-id="'+id+'" data-user-nm="'+nm+'">'+
						'<td class="text-center">'+(i+1)+'</td>'+
						'<td class="text-left">'+id+'</td>'+
						'<td class="text-left">'+nm+'</td>'+
						'<td class="text-center"><button type="button" class="btn btn-primary btn-xs js-pick">선택</button></td>'+
					  '</tr>'
					);
				  }).join("");

				  var html =
					'<div class="mb-2">'+
					  '<input type="text" class="form-control form-control-sm" id="sellerFilter" placeholder="아이디/이름 검색...">'+
					'</div>'+
					'<div class="table-responsive" style="max-height:360px;overflow:auto">'+
					  '<table class="table table-sm table-hover" id="sellerTable">'+
						'<thead><tr>'+
						  '<th style="width:60px">No</th>'+
						  '<th>아이디</th>'+
						  '<th>이름</th>'+
						  '<th style="width:80px">선택</th>'+
						'</tr></thead>'+
						'<tbody>'+(rows || '<tr><td colspan="4" class="text-center text-muted">데이터 없음</td></tr>')+'</tbody>'+
					  '</table>'+
					'</div>';

				  $modal.find("#modal-body").html(html);
				},
				statusCode: { 401: function(){ window.location.href = "/admin/login.do"; } },
				error: function (xhr) {
				  console.error(xhr);
				  $modal.find("#modal-body").html('<div class="text-danger p-3">조회 실패</div>');
				}
			  });
			});

			// 선택 버튼 클릭 → 분기해서 값 세팅
			$("#selectModal").on("click", "#sellerTable .js-pick", function () {
			  var $tr   = $(this).closest("tr");
			  var no    = $tr.data("userNo");
			  var id    = $tr.data("userId");
			  var nm    = $tr.data("userNm");
			  var mode  = $("#selectModal").data("mode");

			  if (mode === "user") {
				// 판매자 세팅
				$("#userNo").val(no);                        // hidden
				$("#sellerNmView").val(id + "/" + nm);       // 표시용
			  } else if (mode === "branch") {
				// 센터(중간센터) 세팅
				$("#branchId").val(no);                  // hidden
				$("#branchNmView").val(id + "/" + nm);       // 표시용
			  }
			  $("#selectModal").modal("hide");
			});

			// 간단 필터
			$("#selectModal").on("input", "#sellerFilter", function () {
			  var q = $(this).val().toLowerCase();
			  $("#sellerTable tbody tr").each(function () {
				var id = String($(this).data("userId") || "").toLowerCase();
				var nm = String($(this).data("userNm") || "").toLowerCase();
				$(this).toggle(id.indexOf(q) > -1 || nm.indexOf(q) > -1);
			  });
			});

			// 닫힐 때 내용 정리(선택)
			$("#selectModal").on("hidden.bs.modal", function(){
			  $(this).find("#modal-body").empty().end().removeData("mode");
			});

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
			// product id가 아직 없으면 "0"으로 전송
			data.append("productId", $('#productId').val() || "0");

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
		
		// 목록
		function fnGoList(){
			$('#aform').attr({ action : '/mgt/product/selectPageListProduct.do', method : 'post' }).submit();
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

		// 등록
		function fnGoInsert(){

			<% if(Const.ROLE_ADMIN.equals(ssAuthorId)){ %>
			if($('[name=userNo]').val() == ''){
				alert('판매자를 선택주세요.');
				return false;
			}
			<% } %>
			if($('[name=branchId]').val() == ''){
				alert('중간센터를 선택주세요.');
				return false;
			}

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


			if(confirm('등록하시겠습니까?')){
				$('#aform').attr({ action : '/mgt/product/insertProduct.do', method : 'post' }).submit();
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
			<form role="form" id="aform" method="post" action="/mgt/product/insertProduct.do" enctype="multipart/form-data" class="form-horizontal">
            	<input type="hidden" id="productId"             name="productId" 				 />
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
				<input type="hidden" id="saleStatus"           name="saleStatus" 			    value="<%=saleStatus%>"/>
			<div class="card">

				<h4 class="cardTitle"><i class="fa fa-caret-square-right"></i> 기본정보</h4>
				<div class="card-body viewForm">
					<!-- 판매자 -->
					<div class="form-group row">
					  <label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">판매상태</label>
					  <div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
							<%=saleStatusNm%>
					  </div>
					  <% if(Const.ROLE_ADMIN.equals(ssAuthorId)){ %>
					  <label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">판매자</label>
					  <div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
						<div class="input-group w-100">
						  <input type="text" class="form-control" id="sellerNmView" name="sellerNmView" placeholder="판매자를 선택하세요" readonly>
						  <div class="input-group-append">
							<button type="button"
									class="btn btn-secondary"
									data-toggle="modal"
									data-target="#selectModal"
									data-discd="modalUser">
							  <i class="fa fa-search"></i> 검색
							</button>
						  </div>
						</div>
						<!-- 실제 전송되는 값 -->
						<input type="hidden" id="userNo" name="userNo" value="">
					  </div>
					<%
					} else {
					%>
					  <label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2"></label>
					  <div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
						<input type="hidden" id="userNo" name="userNo" value="<%=ssUserNo%>">
					  </div>
					<%
					}
					%>
					</div>

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="saleStatus">중간센터</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-4">
						<div class="input-group w-20">
						  <input type="text" class="form-control" id="branchNmView" name="branchNmView" placeholder="중간센터를 선택하세요" readonly>
						  <div class="input-group-append">
							<button type="button"
									class="btn btn-secondary"
									data-toggle="modal"
									data-target="#selectModal"
									data-discd="modalBranch">
							  <i class="fa fa-search"></i> 검색
							</button>
						  </div>
						</div>
						<!-- 실제 전송되는 값 -->
						<input type="hidden" id="branchId" name="branchId">
						</div>
					</div>



					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="title">상품명</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<input type="text" class="form-control" name="title" id="title" placeholder="제목" value="<%=param.getString("title") %>" maxlength="100" />
						</div>
					</div>

                    <div class="form-group row">
						<label  class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">상품가격</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
							<input type="text" class="form-control numeric w-25" name="price" id="price" placeholder="상품판매가" value="<%=param.getString("price")%>" maxlength="10" />
						</div>
                        <label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">본사공급원가</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
							<input type="text" class="form-control numeric w-25" name="supplyPrice" id="supplyPrice" placeholder="본사공급가격" value="<%=param.getString("supplyPrice")%>" maxlength="10" />
						</div>
					</div>

                    <div class="form-group row">
                        <label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">과세구분</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
    						<select id="taxType" name="taxType" class="form-control input-sm w-25" >
								<option value="TAX" <%= "TAX".equals(param.getString("taxType")) ? "selected" : "" %>>과세</option>
								<option value="FREE" <%= "FREE".equals(param.getString("taxType")) ? "selected" : "" %>>면세</option>
							</select>
						</div>
					</div>

                    <div class="form-group row">
                        <label  class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">남은수량</label>
                        <div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
                            <input type="text" class="form-control numeric w-25" name="quantity" id="quantity" placeholder="남은수량" value="<%=param.getString("quantity") %>" maxlength="10" />
                        </div>
                        <label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">단위</label>
                        <div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
   						    <select id="unitCode" name="unitCode" class="form-control input-sm w-25" >
								<%=CommboUtil.getComboStr(unitCodeComboStr, "CODE", "CODE_NM", param.getString("unitCode") , "C")%>
							</select>
                        </div>
                    </div>

                    <div class="form-group row">
						<label  class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">카테고리</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
					        <select id="categoryMid" name="categoryMid" class="form-control input-sm w-25" style="display:inline">
								<%=CommboUtil.getComboStr(categoryMComboStr, "CODE", "CODE_NM", param.getString("categoryMid") , "C")%>
							</select>
							<select id="categoryScls" name="categoryScls" class="form-control input-sm w-25" style="display:inline">
								<option value="">선택하세요</option>
							</select>
                        </div>
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">지역</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
					        <select id="areaMid" name="areaMid" class="form-control input-sm w-25" style="display:inline">
								<%=CommboUtil.getComboStr(areaMComboStr, "CODE", "CODE_NM", param.getString("areaMid") , "C")%>
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
							<textarea class="form-control" rows="10" name="description" id="description"><%=param.getString("description") %></textarea>
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
						<button type="button" class="btn btn-list" onclick="fnGoList(); return false;"><i class="fa fa-reply"></i> 목록</button>
						<button type="button" class="btn btn-write" onclick="fnGoInsert(); return false;"><i class="fa fa-pen"></i> 등록</button>
					</div>
				</div>

			</div>


			</form>
		</section>
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
