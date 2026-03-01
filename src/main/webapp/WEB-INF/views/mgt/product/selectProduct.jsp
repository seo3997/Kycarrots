<%@page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@ page import="com.whomade.kycarrots.entity.product.TnProductImageVo"%>
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
<jsp:useBean id="saleStatusComboStr"  type="java.util.List" class="java.util.ArrayList" scope="request"/>

<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>

<%
  String systemType   = Const.SYSTEM_TYPE;
  String role         = ssAuthorId; // 세션 권한
  String saleStatus   = resultMap.getString("SALE_STATUS","");
  String saleStatusNm = resultMap.getString("SALE_STATUS_NM","");
  boolean saleStatusEnable  = true;

  if("ROLE_SELL".equals(role) && "2".equals(systemType)) {
	  if ("0".equals(saleStatus) || "98".equals(saleStatus)) {
	     saleStatusEnable = true;
	  } else {
	     saleStatusEnable = false;
	  }
  }

%>
<html>
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>

   	<style>
   	  .thumb-grid { display:flex; flex-wrap:wrap; }
	  .thumb-box { position:relative; margin:0 8px 8px 0; }
	  .product-thumb {
		width:200px; height:200px;           /* 모두 동일 크기 */
		object-fit:cover;                    /* 비율 유지하며 잘라서 맞춤 */
		border:1px solid #ccc; cursor:zoom-in;
	  }
	  .badge-main { position:absolute; top:6px; left:6px; }
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
			function refreshStatusChangeBtn() {
			  var cur = $('#curSaleStatus').val();   // 서버에서 내려온 현재 상태
			  var now = $('#saleStatus').val();      // 사용자가 선택한 상태(혹은 hidden값)
			  var changed = now && (cur !== now);
			  $('#btnStatusChange').prop('disabled', !changed)
								   .toggleClass('disabled', !changed);
			}
			$(document).on('change', '#saleStatus', refreshStatusChangeBtn);


		});
		
		// 목록
		function fnGoList(){
			$('#aform').attr({ action : '/mgt/product/selectPageListProduct.do', method : 'get' }).submit();
		}
		
		// 수정폼 이동
		function fnGoUpdateForm(){
			$('#aform').attr({ action : '/mgt/product/updateFormProduct.do', method : 'get' }).submit();
		}

		 //삭제
		function fnGoDelete(){
			if(confirm('삭제하시겠습니까?')){
				$('#aform').attr({ action : '/mgt/product/deleteProduct.do', method : 'post' }).submit();
			}
		}

		 //상태변경
		function fnUpdateStatus(){
		  var sel = $('#saleStatus').val();
		  if (!sel){
			alert('변경할 판매상태를 선택해 주세요.');
			return;
		  }

		  if (sel === '98'){ // 반려
			openRejectModal();
			return;
		  }

		  if(confirm('상품 판매상태를 변경 하시겠습니까?')){
			$('#aform').attr({ action : '/mgt/product/updateProductStatus.do', method : 'post' }).submit();
		  }

		}

		// 첨부파일 다운로드
		function fnDownload(file_id){
			$('[name=file_id]').val(file_id);
			$('#aform').attr({ 'action' : '/common/file/FileDown.do' }).submit();
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

			<form role="form" id="aform" method="post" action="/mgt/product/selectProduct.do" class="form-horizontal" enctype="multipart/form-data">
				<input type="hidden" name="productId" value="<%=resultMap.getString("PRODUCT_ID") %>" />
				<!-- 검색관련 -->
				<input type="hidden" name="sch_type" value="<%=param.getString("sch_type")%>" />
				<input type="hidden" name="sch_text" value="<%=param.getString("sch_text")%>" />
				<input type="hidden" name="sch_sale_status_code" value="<%=param.getString("sch_sale_status_code") %>" />
				<input type="hidden" name="sch_category_m_code" value="<%=param.getString("sch_category_m_code")%>" />
				<input type="hidden" name="sch_area_m_code" value="<%=resultMap.getString("sch_area_m_code") %>" />
				<input type="hidden" name="currentPage" value="<%=param.getString("currentPage")%>"/>
				<input type="hidden" name="rejectReason" id="rejectReason" />
				<input type="hidden" name="curSaleStatus" id="curSaleStatus" value="<%=resultMap.getString("SALE_STATUS") %>"/>

			<div class="card">
				<div class="card-body viewForm">

					<div class="form-group row">
						<label  class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">판매상태</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
					        <% if (saleStatusEnable) { %>
					        <select id="saleStatus" name="saleStatus" class="form-control input-sm w-25" >
								<%=CommboUtil.getComboStr(saleStatusComboStr, "CODE", "CODE_NM", resultMap.getString("SALE_STATUS") , "C")%>
							</select>
							<% } else { %>
								<input type="hidden" name="saleStatus" id="saleStatus" value="<%=resultMap.getString("SALE_STATUS") %>"/>
					            <%=resultMap.getString("SALE_STATUS_NM")%>
					        <% } %>
						</div>
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">상품명</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
							<%=resultMap.getString("TITLE") %>
						</div>
					</div>
					<% if ("98".equals(resultMap.getString("SALE_STATUS"))) { %>
					  <div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">반려 사유</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
						  <!-- 개행 유지하고 안전하게 보여주고 싶으면 pre 사용 -->
						  <pre style="white-space:pre-wrap; margin:0;"><%= resultMap.getString("REJECT_REASON") %></pre>
						</div>
					  </div>
					<% } %>
					<div class="form-group row">
						<label  class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">상품가격</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
							<%=StringUtil.setComma(resultMap.getString("PRICE")) %>원
						</div>
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">본사공급가격</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
							<%=StringUtil.setComma(resultMap.getString("SUPPLY_PRICE")) %>원
						</div>
					</div>

					<div class="form-group row">
						<label  class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">과세구분</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
							<%= "TAX".equals(resultMap.getString("TAX_TYPE")) ? "과세" : "면세" %>
						</div>
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">수량</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
							<%=StringUtil.setComma(resultMap.getString("QUANTITY")) %>  <%=resultMap.getString("UNIT_CODE_NM") %>
						</div>
					</div>

					<div class="form-group row">
						<label  class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">카테고리</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
							<%=resultMap.getString("CATEGORY_MID_NM") %> / <%=resultMap.getString("CATEGORY_SCLS_NM") %>
						</div>
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">지역</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
							<%=resultMap.getString("AREA_MID_NM") %> / <%=resultMap.getString("AREA_SCLS_NM") %>
						</div>
					</div>
					<div class="form-group row">
						<label  class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">희망출하일</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
							<%=resultMap.getString("DESIRED_SHIPPING_DATE") %>
						</div>
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">등록일시</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
							<%=resultMap.getString("REGIST_DT") %>
						</div>
					</div>

					<div class="form-group row">
						<label  class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">등록자명</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
							<%=resultMap.getString("REGISTER_NM") %>
						</div>
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">등록자ID</label>
						<div class="col-xs-5 col-sm-3 col-md-3 col-lg-4">
							<%=resultMap.getString("REGISTER_ID") %>
						</div>
					</div>
					<div class="form-group row">
					  <label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">긴급사유</label>
					  <div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
						<%=resultMap.getHtml("DESCRIPTION") %>
					  </div>
					</div>

					<div class="form-group row">
					  <label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">상품 이미지</label>
					  <div class="col-xs-12 col-sm-9 col-md-9 col-lg-10">
						<%
						  java.util.List<TnProductImageVo> imgs = new java.util.ArrayList<>();
						  if (fileList != null) {
							for (int i = 0; i < fileList.size() && i < 4; i++) {
							  imgs.add((TnProductImageVo) fileList.get(i));
							}
						  }
						%>
						<% if (imgs.isEmpty()) { %>
						  <span class="text-muted">이미지 없음</span>
						<% } else { %>
						  <div class="thumb-grid">
							<% for (int i = 0; i < imgs.size(); i++) {
								 TnProductImageVo iv = imgs.get(i);
								 String alt = (i == 0) ? "대표 이미지" : ("추가 이미지 " + i);
							%>
							  <div class="thumb-box">
								<img src="<%= iv.getImageUrl() %>"
									 class="img-thumbnail product-thumb"
									 alt="<%= alt %>"
									 onclick="openImgModal(this)">
								<% if (i == 0) { %>
								  <span class="badge badge-primary badge-main">대표</span>
								<% } %>
							  </div>
							<% } %>
						  </div>
						<% } %>
					  </div>
					</div>

					
				</div>

				<div class="box-footer">
					<div class="text-center">
						<button type="button" class="btn btn-list" onclick="fnGoList(); return false;"><i class="fa fa-reply"></i> 목록</button>
		        
						<button type="button" class="btn btn-modify" onclick="fnGoUpdateForm(); return false;"><i class="fa fa-eraser"></i> 수정</button>

                        <button type="button" class="btn btn-delete" onclick="fnGoDelete(); return false;"><i class="fa fa-trash"></i> 삭제</button>
						<% if( (Const.ROLE_ADMIN.equals(ssAuthorId) || Const.ROLE_SELL.equals(ssAuthorId)|| Const.ROLE_PROJ.equals(ssAuthorId)) && (saleStatusEnable=true)){ %>
						<button type="button" class="btn btn-write" id="btnStatusChange" onclick="fnUpdateStatus(); return false;" disabled><i class="fa fa-sync"></i> 상태변경</button>
						<% } %>

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
	<!-- 반려 사유 전용 모달 (Bootstrap) -->
	<div id="rejectModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="rejectModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
		<div class="modal-content">
		  <div class="modal-header">
			<h5 class="modal-title" id="rejectModalLabel">반려 사유</h5>
			<button type="button" class="close" data-dismiss="modal" aria-label="Close">
			  <span aria-hidden="true">&times;</span>
			</button>
		  </div>
		  <div class="modal-body">
			<textarea id="rejectText" class="form-control" rows="5" placeholder="반려 사유를 입력해 주세요."></textarea>
		  </div>
		  <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
			<button type="button" class="btn btn-primary" id="btnRejectOk">확인</button>
		  </div>
		</div>
	  </div>
	</div>

	<!-- footer -->
	<%@ include file="/common/inc/footer.jspf" %>
	<!-- //fooer -->
</div>
<script type="text/javascript">
	// Get the modal
	var modal = $('#myModal');

	function openImgModal(imgEl){
		$('#img01').attr('src', imgEl.src);
		$('#caption').text(imgEl.alt || '');
		$('#myModal').fadeIn(100);
  	}
  	$('.close').on('click', function(){ $('#myModal').fadeOut(100); });
  	$('#myModal').on('click', function(e){
    	if (e.target.id === 'myModal') $('#myModal').fadeOut(100);
  	});
	// 반려 사유 모달 열기
	function openRejectModal(){
	  $('#rejectText').val('');
	  $('#rejectModal').modal('show');
	}

	// 반려 사유 모달 확인
	$('#btnRejectOk').on('click', function(){
	  var reason = ($('#rejectText').val() || '').trim();
	  if (!reason){
		alert('반려 사유를 입력해 주세요.');
		$('#rejectText').focus();
		return;
	  }
	  $('#rejectReason').val(reason);
	  $('#rejectModal').modal('hide');
	  // 상태변경 제출
	  $('#aform').attr({ action : '/mgt/product/updateProductStatus.do', method : 'post' }).submit();
	});

	var SYSTEM_TYPE = '<%=systemType%>';
  	var ROLE        = '<%=role%>';
	  // 역할/시스템타입별 허용 상태
	function allowedStatuses() {
		// 전체 코드 예: ['0','98','1','10','99']  // 0:승인요청, 98:반려, 1:판매중, 10:예약중, 99:판매완료
		if (SYSTEM_TYPE === '1') {
		  // 승인/반려 기능 없음 → 0,98 제거
		  return ['1','10','99'];
		}
		if (SYSTEM_TYPE === '2') {
		  if (ROLE === 'ROLE_SELL') return ['0','98']; // 판매자는 승인요청만
		  // 관리자/센터는 전체
		  return ['0','98','1','10','99'];
		}
		// 기본(안전)
		return ['0','98','1','10','99'];
	}
	 // 드롭다운 옵션 필터링
	  function filterSaleStatusOptions() {
		var $sel = $('#saleStatus');
		var allow = allowedStatuses();
		// 기존 선택값가 허용 밖이면 초기화
		if ($sel.val() && allow.indexOf($sel.val()) === -1) {
		  $sel.val('');
		}
		// 옵션 필터 (placeholder 빈값은 유지)
		$sel.find('option').each(function(){
		  var v = this.value;
		  if (!v) return; // '선택하세요' 유지
		  if (allow.indexOf(v) === -1) $(this).remove();
		});
	  }
	  filterSaleStatusOptions();
</script>

</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
