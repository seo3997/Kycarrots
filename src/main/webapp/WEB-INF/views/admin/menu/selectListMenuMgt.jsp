<%@page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ include file="/common/inc/common.jspf"%>
<%@ include file="/common/inc/docType.jspf"%>

<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap"%>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request" />
<jsp:useBean id="resultList" type="java.util.List" 	class="java.util.ArrayList" scope="request" />
<jsp:useBean id="menuTypeComboStr" type="java.util.List"	class="java.util.ArrayList" scope="request" />
<jsp:useBean id="ynComboStr" type="java.util.List" class="java.util.ArrayList" scope="request" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/inc/meta.jspf"%>
<title><%=headTitle%></title>
<%@ include file="/common/inc/cssScript.jspf"%>

<link rel="stylesheet" href="<%=jsRoot%>jsTree_new/themes/default/style.min.css" />
<script src="<%=jsRoot%>jsTree_new/jstree.min.js"></script>

<script type="text/javascript">
	//<![CDATA[
		$(function() {
			$('#jstree').jstree({
				"core" : {
					"themes" : {
						"icons" : false,
					}
				},
				"plugins" : [
					"state", "wholerow" 
				]
			});
		});	
		
	//]]>
	</script>

<script type="text/javascript">	
		
		//메뉴정보 조회
		function fnSelectMenuInfo(menu_id){

			var req ={	
				"menu_id" : menu_id
			};

			
			jQuery.ajax( {
				type : 'POST',
				dataType : 'json',
				url : '/admin/menu/selectMenuMgtAjax.do',
				data : req,
				success : function(param) {

					if(param.resultStats.resultCode == "error"){
						alert(param.resultStats.resultMsg);
						return;
					}
					
					$("#menu_id").val(param.resultMap.MENU_ID);
					$("#up_menu_id").val(param.resultMap.UP_MENU_ID);
					$("#up_menu_nm").val(param.resultMap.UP_MENU_NM);
					$("#menu_type_code").val(param.resultMap.MENU_TY_CODE);
					$("#menu_lv").val(param.resultMap.MENU_LEVEL);
					$("#menu_nm").val(param.resultMap.MENU_NM);
					$("#menu_nm_cn").val(param.resultMap.MENU_NM_CN);
					$("#url").val(param.resultMap.URL);
					$("#srt_ord").val(param.resultMap.SORT_ORDR);
					$("#disp_yn").val(param.resultMap.USE_YN);
					
					
					
					$("#sort_ordr_1").val(param.resultMap.SORT_ORDR_1);
					$("#sort_ordr_2").val(param.resultMap.SORT_ORDR_2);
					$("#sort_ordr_3").val(param.resultMap.SORT_ORDR_3);
					$("#sort_ordr_4").val(param.resultMap.SORT_ORDR_4);
					$("#sort_ordr_5").val(param.resultMap.SORT_ORDR_5);
					$("#sort_ordr_6").val(param.resultMap.SORT_ORDR_6);
					

					$("#insertArea").html("");
					$("#insertBtn").hide();
				},
				error : function(jqXHR, textStatus, thrownError){
					ajaxJsonErrorAlert(jqXHR, textStatus, thrownError)
				}
			});

		}

		var oldValue="";
		function fnSetOldValue(obj, idx){
			oldValue = obj.value;
		}

		function fnCompNewValue(obj, idx){

			if(oldValue != obj.value){
				$("#dup_chk_yn_"+idx).val("N");
			}
		}
		//코드정보 조회
		function fnSelectMenuExistYnAjax(idx){
			
			if($("#new_menu_id_"+idx).val() == ""){
				alert("코드ID를 입력해 주세요.");
				return;
			}
			
			if($("#new_menu_id_"+idx).val().length != 4){
				alert("신규 메뉴코드 ID를입력해 주세요.");
				return;
			}
			
			
			var req ={	
				"new_menu_id" : $("#menu_id").val()+$("#new_menu_id_"+idx).val()
			};

			jQuery.ajax( {
				type : 'POST',
				dataType : 'json',
				url : '/admin/menu/selectExistYnMenuMgtAjax.do',
				data : req,
				success : function(param) {
					
					if(param.resultStats.resultCode == "error"){
						alert(param.resultStats.resultMsg);
						return;
					}
					
					if(param.resultMap.EXIST_YN == "Y"){
						alert("메뉴ID가 존재합니다.");
					}
					else{
						alert("사용가능한 메뉴ID입니다.");
						$("#dup_chk_yn_"+idx).val("Y");
					}
					
				},
				error : function(jqXHR, textStatus, thrownError){
					ajaxJsonErrorAlert(jqXHR, textStatus, thrownError)
				}
			});

		}

		
		//입력폼 보기
		var removeNo = 0;
		
		function fnInsertForm(){

			if($("#menu_id").val() == ""){
				alert("메뉴를 선택해 주세요.");
				return;
			}
			
			
			
			var sortDepth = $("#menu_id").val().length / 4 + 1;
			if(sortDepth>6)
			{
				alert(" 6 Depth 이상으로는 메뉴를 생성할 수 없습니다.");
				return;
			}

			var nextSrtOrd = 0;	//next 정렬순서
			var newSrtOrdList = f_getList("new_srt_ord");
			for(i = 0; i < newSrtOrdList.length; i++){

				if(Number(nextSrtOrd) < Number(newSrtOrdList[i].value)){
					nextSrtOrd = Number(newSrtOrdList[i].value);					
				}				
			}
			nextSrtOrd = Number(nextSrtOrd) + 10;

			var menuTypeComboStr = "<select name=\"new_menu_type_code\" id=\"new_menu_type_code\" class=\"form-control\">";
				menuTypeComboStr += "	<%=CommboUtil.getComboStr(menuTypeComboStr, "CODE",
					"CODE_NM", "", "C")%>";
				menuTypeComboStr += "</select>";

			var ynComboStr = "<select name=\"new_disp_yn\" id=\"new_disp_yn\" class=\"form-control\" >";
				ynComboStr += "	<%=CommboUtil.getComboStr(ynComboStr, "CODE", "CODE_NM", "",
					"")%>";
				ynComboStr += "</select>";

			removeNo++;
			
			var layer = "";

				layer += "<div id=\"removeNo_"+removeNo+"\" class=\"addMenuForm\">";

				layer += "	<div class=\"form-group row\">";
				layer += "		<label for=\"dup_chk_yn_"+removeNo+"\" class=\"required control-label col-xs-12 col-sm-3 col-md-3 col-lg-2\">상위메뉴 ID</label>";
				layer += "		<div class=\"col-xs-12 col-sm-9 col-md-9 col-lg-9 form-inline\">";
				layer += "			<input type=\"text\" class=\"form-control\" name=\"\" value=\""+$("#menu_id").val()+"\" readonly />";
				layer += "			<input type=\"hidden\" class=\"btn btn-info\" name=\"dup_chk_yn\" id=\"dup_chk_yn_"+removeNo+"\" />";
				layer += "			<input type=\"text\" class=\"form-control\" name=\"new_menu_id\" id=\"new_menu_id_"+removeNo+"\"   onkeypress=\"return checkNum(event);\" onfocus=\"fnSetOldValue(this, '"+removeNo+"');\" onblur=\"fnCompNewValue(this, '"+removeNo+"');\" onkeyup=\"cfLengthCheck('메뉴ID는', this, 4);\"   maxlength=\"4\"/>";
				layer += "			<button type=\"button\" class=\"btn btn-default\" onclick=\"fnSelectMenuExistYnAjax('"+removeNo+"')\">중복확인</button>";
				layer += "		</div>";
				layer += "	</div>";
	
				layer += "	<div class=\"form-group row\">";
				layer += "		<label for=\"new_menu_nm\" class=\"required control-label col-xs-12 col-sm-3 col-md-3 col-lg-2\">메뉴명</label>";
				layer += "		<div class=\"col-xs-12 col-sm-9 col-md-9 col-lg-9\">";
				layer += "			<input type=\"text\" class=\"form-control\" name=\"new_menu_nm\" id=\"new_menu_nm\"  maxlength=\"100\" onkeyup=\"cfLengthCheck('메뉴명은', this, 100);\">";
				layer += "		</div>";
				layer += "	</div>";
			
				layer += "	<div class=\"form-group row\">";
				layer += "		<label for=\"new_menu_type_code\" class=\"required control-label col-xs-12 col-sm-3 col-md-3 col-lg-2\">메뉴유형</label>";
				layer += "		<div class=\"col-xs-12 col-sm-9 col-md-3 col-lg-4\">" + menuTypeComboStr + "</div>";
				layer += "		<div class=\"form-hidden-line w-100 d-sm-block d-md-none d-lg-none\" style=\"height: 1px; background-color: #ebebeb;\"></div>";
				layer += "		<label for=\"new_srt_ord\" class=\"control-label col-xs-12 col-sm-3 col-md-2 col-lg-2\">정렬순서</label>";
				layer += "		<div class=\"col-xs-12 col-sm-9 col-md-4 col-lg-4\">";
				layer += "			<input type=\"text\" class=\"form-control\" name=\"new_srt_ord\" id=\"new_srt_ord\" maxlength=\"4\" onkeypress=\"return checkNum(event);\" value=\""+nextSrtOrd+"\">";
				layer += "		</div>";
				layer += "	</div>";

				layer += "	<div class=\"form-group row\">";
				layer += "		<label for=\"new_disp_yn\" class=\"control-label col-xs-12 col-sm-3 col-md-3 col-lg-2\">전시여부</label>";
				layer += "		<div class=\"col-xs-12 col-sm-9 col-md-9 col-lg-9\">" + ynComboStr + "</div>";
				layer += "	</div>";

				layer += "	<div class=\"form-group row\">";
				layer += "		<label for=\"new_url\" class=\"control-label col-xs-12 col-sm-3 col-md-3 col-lg-2\">URL</label>";
				layer += "		<div class=\"col-xs-12 col-sm-9 col-md-9 col-lg-9\">";
				layer += "			<input type=\"text\" class=\"form-control\" name=\"new_url\" id=\"new_url\" maxlength=\"100\" onkeyup=\"cfLengthCheck('URL은', this, 100);\">";
				layer += "		</div>";
				layer += "	</div>";


				layer += "	<div class=\"box-footer\">";
				layer += "		<div class=\"text-right\">";
				layer += "			<button type=\"button\" class=\"btn btn-td-delete\" onclick=\"fnRemove('removeNo_"+removeNo+"'); return false\">삭제</button>";
				layer += "		</div>";
				layer += "	</div>";

				layer += "</div>";

			$("#insertAreaWrap").show();
			$("#insertArea").append(layer);

			$("#insertBtn").show();

			fnSetNumeric();
		}

		//입력폼 삭제
		function fnRemove(removeId){

			$("#"+removeId).remove();

			if($("#insertArea").html().length == 0){
				$("#insertAreaWrap").hide();
				$("#insertBtn").hide();
			}			
		}

		
		function fnUpdate(){

			if($("#menu_id").val() == ""){
				alert("메뉴를 선택해 주세요.");
				return;
			}
			else{

				if($("#menu_nm").val() == ""){
					alert("메뉴명을 입력해 주세요.");
					$("#menu_nm").focus();
					return;
				}
				
				
				if($("#menu_type_code").val() == ""){
					alert("메뉴유형을 입력해 주세요.");
					$("#menu_nm").focus();
					return;
				}
				fnUpdateCheck();
			}
		}
		function fnUpdateCheck()
		{
			var req ={	
					"up_menu_id" : $("#up_menu_id").val()
					,"menu_id" : $("#menu_id").val()
					,"srt_ord" : $("#srt_ord").val()
			};
			
			jQuery.ajax( {
				type : 'POST',
				dataType : 'json',
				url : '/admin/menu/selectExistSortMenuMgtAjax.do',
				data : req,
				success : function(param) {
					if(param.resultStats.resultCode == "error"){
						alert(param.resultStats.resultMsg);
						return;
					}
					if(param.resultMap.EXIST_SORT_YN == "Y"){
						alert("중복된 정렬 순서가 존재합니다.\n다른 정렬 순서번호를 이용하시기바랍니다.");
					}
					else{
						if(confirm("수정하시겠습니까?")){
							$("#aform").attr({action:"/admin/menu/updateMenuMgt.do", method:'post'}).submit();
						}
					}
				},
				error : function(jqXHR, textStatus, thrownError){
					ajaxJsonErrorAlert(jqXHR, textStatus, thrownError)
				}
			});
		}

		function fnInsert(){

			var newMenuIdList = f_getList("new_menu_id");
			var newMenuNmList = f_getList("new_menu_nm");
			var newMenuTypeCodeList = f_getList("new_menu_type_code");
			var newDispYnList = f_getList("new_disp_yn");
			
			var newStrOrdList = f_getList("new_srt_ord");
			
			var dupChkYnList = f_getList("dup_chk_yn");
			var newUrlList = f_getList("new_url");
			
			
			var menuIdDupYn = false;

			if(newMenuIdList.length == 0){

				alert("신규메뉴정보를 입력해 주세요.");
				return;
			}
			else{

				for(i = 0 ; i < newMenuIdList.length; i++){

					if(newMenuIdList[i].value == ""){
						alert("신규 메뉴ID를 입력해 주세요.");
						newMenuIdList[i].focus();
						return;
					}
					if(/^[0-9]{4}$/.test(newMenuIdList[i].value)==false){
						alert("신규 메뉴ID를 4자리수를 입력해 주세요.");
						newMenuIdList[i].focus();
						return;
					}

					if(dupChkYnList[i].value == "N"){
						alert($("#menu_id").val()+""+newMenuIdList[i].value+" 메뉴ID의 중복검사가 필요합니다.");
						return;
					}

					if(newMenuNmList[i].value == ""){
						alert("신규 메뉴명을 입력해 주세요.");
						newMenuNmList[i].focus();
						return;
					}

					if(newMenuTypeCodeList[i].value == ""){
						alert("신규 메뉴유형을 입력해 주세요.");
						newMenuTypeCodeList[i].focus();
						return;
					}
				}

				var dupCnt = 0;
				for(i = 0; i < newMenuIdList.length; i++){

					dupCnt = 0;

					for(k = 0; k < newMenuIdList.length; k++){

						if(newMenuIdList[i].value == newMenuIdList[k].value){
							dupCnt++;
							//alert(newMenuIdList[i]+":"+newMenuIdList[k]+":"+dupCnt);
							if(dupCnt > 1){
								menuIdDupYn = true;
								break;
							}
						}
					}
				}

				if(menuIdDupYn){
					alert("메뉴ID가 중복입력 되었습니다.");
					return;
				}

				if(confirm("등록하시겠습니까?")){
					$("#aform").attr({action:"/admin/menu/insertMenuMgt.do", method:'post'}).submit();
				}
			}

		}

		function fnDelete(){
			
			if($("#menu_id").val() == ""){
				alert("메뉴를 선택해 주세요.");
				return;
			}
			if($("#up_menu_id").val() == ""){
				alert("Root는 삭제할 수 없습니다.");
				return;
			}
			
			
			if(confirm("\nWARNNING!!!!\n\n하위 메뉴 포함하여 삭제됩니다. \n\n삭제하시겠습니까?\n")){
				$("#aform").attr({action:"/admin/menu/deleteMenuMgt.do", method:'post'}).submit();
			}
		}
	</script>
</head>
<body class="hold-transition skin-green-light sidebar-mini">
	<div class="wrapper">

		<!-- 헤더  -->
		<c:import url="/common/inc/header.do" charEncoding="utf-8" />

		<!-- 좌측 메뉴 -->
		<c:import url="/common/inc/menu.do" charEncoding="utf-8" />

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
			<div id="navi"><i class="fa fa-home f12 color-lgray"></i><span class="blind">home</span>><span class="text" id="spnavi"></span></div>
	    	<div id="pagetitle" >
	    	</div>
			</section>

			<!-- Main content -->
			<section class="content container-fluid vw-page">
			
				<div class="row">
					<div class="col-5">
						<div class="card p-4">

							<div>
								<div class="form-inline text-right pr-3">
									<button class="btn btn-default" onclick="$('#jstree').jstree('close_all');"><i class="fa fa-folder"></i> 접기</button>
									<button class="btn btn-default ml-1" onclick="$('#jstree').jstree('open_all');"><i class="fa fa-folder-open"></i> 펼치기</button>
								</div>
							</div>

							<div class="left_list" id="jstree">
								<ul>
									<li>
										<%
											int dataCnt = resultList.size();
											int tagSumCnt = 0;
											int lvDept = 0;

											DataMap resutMap = new DataMap();
											DataMap oldResutMap = new DataMap();
											for (int i = 0; i < dataCnt; i++) {

												resutMap = (DataMap) resultList.get(i);

												if (i == 0) {
													out.println("<a class=\"pointer\" onclick=\"fnSelectMenuInfo('"
															+ resutMap.getString("MENU_ID")
															+ "', '"
															+ resutMap.getString("MENU_NM")
															+ "');return false;\">"
															+ resutMap.getString("MENU_NM") + "</a>");
												} else {

													oldResutMap = (DataMap) resultList.get(i - 1);

													if (oldResutMap.getInt("MENU_LEVEL") < resutMap.getInt("MENU_LEVEL")) { //하위레벨일경우
														out.print("<ul><li>");
														tagSumCnt++;
													}

													if (oldResutMap.getInt("MENU_LEVEL") == resutMap.getInt("MENU_LEVEL")) { //동레벨일경우
														out.print("</li><li>");
													}

													if (oldResutMap.getInt("MENU_LEVEL") > resutMap.getInt("MENU_LEVEL")) { //상위레벨일경우

														lvDept = oldResutMap.getInt("MENU_LEVEL")- resutMap.getInt("MENU_LEVEL");
														for (int k = 0; k < lvDept; k++) {
															out.println("</li></ul>");
															tagSumCnt--;
														}
														out.println("<li>");
													}

													out.println("<a href=\"#\" onclick=\"fnSelectMenuInfo('"
															+ resutMap.getString("MENU_ID") + "', '"
															+ resutMap.getString("MENU_NM")
															+ "');return false;\">"
															+ resutMap.getString("MENU_NM") + "</a>");
												}
											}

											for (int i = 0; i < tagSumCnt; i++) {
												out.println("</li></ul>");
												tagSumCnt--;
											}
										%>
									</li>
								</ul>
							</div>
						</div>
					</div>

					<div class="col-7">

						<form id="aform" method="post" action="/admin/menu/selectListMenuMgt.do">
							<input type="hidden" class="btn btn-info" name="sort_ordr_1" id="sort_ordr_1" />
							<input type="hidden" class="btn btn-info" name="sort_ordr_2" id="sort_ordr_2" />
							<input type="hidden" class="btn btn-info" name="sort_ordr_3" id="sort_ordr_3" />
							<input type="hidden" class="btn btn-info" name="sort_ordr_4" id="sort_ordr_4" />
							<input type="hidden" class="btn btn-info" name="sort_ordr_5" id="sort_ordr_5" />
							<input type="hidden" class="btn btn-info" name="sort_ordr_6" id="sort_ordr_6" />
						
						<div class="card">

							<div class="card-body">


				                <div class="form-group row">
									<label for="up_menu_id" class="required control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">상위메뉴 ID</label>
									<div class="col-xs-12 col-sm-9 col-md-3 col-lg-4"><input type="text" class="form-control" name="up_menu_id" id="up_menu_id" readonly></div>
									<div class="form-hidden-line w-100 d-sm-block d-md-none d-lg-none" style="height: 1px; background-color: #ebebeb;"></div>
				                  <label for="up_menu_nm" class="required control-label col-xs-12 col-sm-3 col-md-2 col-lg-2">상위메뉴명</label>
									<div class="col-xs-12 col-sm-9 col-md-4 col-lg-4"><input type="text" class="form-control" name="up_menu_nm" id="up_menu_nm" readonly></div>
				                </div>

				                <div class="form-group row">
									<label for="menu_id" class="required control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">메뉴 ID</label>
									<div class="col-xs-12 col-sm-9 col-md-3 col-lg-4"><input type="text" class="form-control" name="menu_id" id="menu_id" readonly></div>
									<div class="form-hidden-line w-100 d-sm-block d-md-none d-lg-none" style="height: 1px; background-color: #ebebeb;"></div>
				                  	<label for="menu_lv" class="required control-label col-xs-12 col-sm-3 col-md-2 col-lg-2">메뉴레벨</label>
									<div class="col-xs-12 col-sm-9 col-md-4 col-lg-4"><input type="text" class="form-control" name="menu_lv" id="menu_lv" readonly></div>
				                </div>

								<div class="form-group row">
									<label for="menu_nm" class="required control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">메뉴명</label>
									<div class="col-xs-12 col-sm-9 col-md-9 col-lg-9">
										<input type="text" class="form-control" name="menu_nm" id="menu_nm" onkeyup="cfLengthCheck('메뉴명은', this, 100);">
									</div>
				                </div>

				                <div class="form-group row">
									<label for="menu_type_code" class="required control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">메뉴유형</label>
									<div class="col-xs-12 col-sm-9 col-md-3 col-lg-4">
										<select name="menu_type_code" id="menu_type_code" class="form-control">
											<%=CommboUtil.getComboStr(menuTypeComboStr, "CODE","CODE_NM", "", "C")%>
										</select>
									</div>
									<div class="form-hidden-line w-100 d-sm-block d-md-none d-lg-none" style="height: 1px; background-color: #ebebeb;"></div>
				                  	<label for="srt_ord" class="required control-label col-xs-12 col-sm-3 col-md-2 col-lg-2">정렬순서</label>
									<div class="col-xs-12 col-sm-9 col-md-4 col-lg-4"><input type="text" class="form-control" name="srt_ord" id="srt_ord" onkeypress="return checkNum(event);" maxlength="4"></div>
				                </div>

								<div class="form-group row">
									<label for="disp_yn" class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">전시여부</label>
									<div class="col-xs-12 col-sm-9 col-md-9 col-lg-9">
										<select name="disp_yn" id="disp_yn" class="form-control">
											<%=CommboUtil.getComboStr(ynComboStr, "CODE", "CODE_NM", "", "")%>
										</select>
									</div>
				                </div>

								<div class="form-group row">
									<label for="url" class="control-label col-xs-12 col-sm-3 col-md-3 col-lg-2">URL</label>
									<div class="col-xs-12 col-sm-9 col-md-9 col-lg-9">
										<input type="text" class="form-control" name="url" id="url" onkeyup="cfLengthCheck('URL은', this, 100);">
									</div>
								</div>

							</div>

				            <div class="box-footer">
					            <div class="text-center">
									<button type="button" class="btn btn btn-modify" onclick="fnUpdate(); return false;"><i class="fa fa-eraser"></i> 수정</button>
									<button type="button" class="btn btn-delete" onclick="fnDelete(); return false;"><i class="fa fa-trash"></i> 삭제</button>
									<button type="button" class="btn btn-write" onclick="fnInsertForm(); return false;"><i class="fa fa-plus"></i> 신규등록</button>
								</div>
				            </div>

						</div>
						
						<div class="card" id="insertAreaWrap" style="display:none;">
							<div class="card-body">
								<div id="insertArea"></div>
							</div>

				            <div class="box-footer" id="insertBtn" style="display: none;">
					            <div class="text-center">
									<button type="button" class="btn btn-write" onclick="fnInsert(); return false;"><i class="fa fa-pencil"></i> 등록</button>
								</div>
							</div>

						</div>

					</form>
				</div><!-- col -->
			</div>
		</div>
				<!-- //content -->
	</section>


		<!-- footer -->
		<%@ include file="/common/inc/footer.jspf"%>
		<!--//footer -->
	</div>
</body>
</html>
<%@ include file="/common/inc/msg.jspf"%>