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
<html>
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>
	
	<script type="text/javascript">
	//<![CDATA[
		$(function(){
            $('#desiredShippingDate').datetimepicker(
               { format: 'YYYY-MM-DD' }).on('dp.change', function (e) {
			});

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
			$('#aform').attr({ action : '/mgt/product/selectProduct.do', method : 'get' }).submit();
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
				<input type="hidden" id="areaGroup"              name="areaGroup" 			    value="R010070" />
				<input type="hidden" id="sch_type"              name="sch_type" 				value="<%=param.getString("sch_type")%>" />
				<input type="hidden" id="sch_text"              name="sch_text" 				value="<%=param.getString("sch_text")%>" />
                <input type="hidden" id="sch_sale_status_code"  name="sch_sale_status_code" 	value="<%=param.getString("sch_sale_status_code")%>" />
				<input type="hidden" id="sch_category_m_code"   name="sch_category_m_code" 	    value="<%=param.getString("sch_category_m_code")%>" />
				<input type="hidden" id="sch_area_m_code"       name="sch_area_m_code" 	        value="<%=param.getString("sch_area_m_code")%>" />
				<input type="hidden" id="currentPage"           name="currentPage" 			    value="<%=param.getString("currentPage")%>"/>

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
							<input type="text" class="form-control numeric w-25" name="price" id="price" placeholder="상품가격" value="<%=StringUtil.setComma(param.getString("title", resultMap.getString("PRICE")))%>" maxlength="10" />
						</div>
                        <label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">희망 출하일</label>
                        <div class="col-xs-12 col-sm-9 col-md-3 col-lg-4">
                          <div class="input-group date dateTimePicker" id="desiredShippingDate">
                            <input type="text" class="form-control" name="desiredShippingDate" required="required" maxlength="12"  value="<%=param.getString("desiredShippingDate", resultMap.getString("DESIRED_SHIPPING_DATE")) %>"/>
                            <span class="input-group-addon" id="btnDesiredShippingDate" style="cursor:pointer;">
                              <i class="fa fa-calendar-alt" style="bottom:1px;"></i>
                            </span>
                          </div>
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
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="description">긴급사유</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<textarea class="form-control" rows="5" name="description" id="description"><%=param.getString("description", resultMap.getString("DESCRIPTION")) %></textarea>
						</div>
					</div>

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2" for="bbs_se_code_m">상품이미지</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<div class="outBox1 fileBoxWrap"></div>
							<div class="outBox2 ftRed"></div>
						</div>
					</div>
					

					<div class="form-group row">
						<label class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">상품이미지</label>
						<div class="checkbox col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<% //파일이 존재하지 않으면 첨부파일 목록태그 그리지 않음
								if(fileList.size() > 0){
							%>
							<%
								for(int i = 0; i < fileList.size(); i++) {
                                    TnProductImageVo fvo = (TnProductImageVo)fileList.get(i);
							%>
							<a href="#"  onclick="fnDownload('<%=fvo.getImageId()%>'); return false;">
							<img src = "<%=fvo.getImageUrl()%>" width="16" height="16" class="attach_file" />
							</a>
							<a href="#" onclick="fnFileDel(this, '<%=fvo.getImageId() %>'); return false;">[삭제]</a><br/>
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

    $('#quantity').on('change', function(e) {
        this.value=setComma(this.value);
    });

    fnGetSCodeList('R010610','<%=resultMap.getString("CATEGORY_MID")%>',$("#categoryScls"),'<%=resultMap.getString("CATEGORY_SCLS")%>','C');
    fnGetSCodeList('R010070','<%=resultMap.getString("AREA_MID")%>',$("#areaScls"),'<%=resultMap.getString("AREA_SCLS")%>','C');
</script>
</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
