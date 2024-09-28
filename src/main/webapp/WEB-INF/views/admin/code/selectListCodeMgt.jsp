<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>
		
	<script type="text/javascript">
	//<![CDATA[
		function f_GoPage(currentPage){

			$("#currentPage").val(currentPage);
			$("#aform").attr({action:"/admin/code/selectPageListCodeMgt.do", method:'post'}).submit();			
		}

// 		function f_ChngFistPage(firstPage){

// 			$("#firstPage").val(firstPage);
// 			$("#currentPage").val(firstPage);
// 			$("#aform").attr({action:"/admin/code/selectCodePageList.do", method:'post'}).submit();			
// 		}
		$(function(){
			//f_divView('main_0', 'main_0');	//메뉴구조 유지
		});

		function fnRemoveLine(removeId){
			$("#"+removeId).remove();
		}

		//입력폼 보기
		var removeNo = 0;
		function fnInsertForm(){		

			var comboStr = "<select class=\"form-control\" name=\"use_yn\">";
				<c:forEach var="ynCode" items="${ynCodeList}">
				comboStr += "<option value=\"${ynCode.CODE }\" >${ynCode.CODE_NM }</option>";
				</c:forEach>
				comboStr += "</select>";

			removeNo++;

			var table = $("#codeTable");
			var htmlStr ="<tr id=\"id_new_code_"+removeNo+"\">";
			htmlStr +="	<td><input type=\"text\" class=\"form-control\" name=\"code\" onkeyup=\"cfLengthCheck('코드는', this, 4);\"/></td>";
			htmlStr +="	<td><input type=\"text\" class=\"form-control\" name=\"code_nm\" onkeyup=\"cfLengthCheck('코드명은', this, 300);\" /></td>";
			htmlStr +="	<td><input type=\"text\" class=\"form-control\" name=\"code_nm_eng\" onkeyup=\"cfLengthCheck('코드영문명은', this, 100);\" /></td>";
			htmlStr +="	<td><input type=\"text\" class=\"form-control\" name=\"attrb_1\" onkeyup=\"cfLengthCheck('속성1은', this, 100);\" /></td>";
			htmlStr +="	<td><input type=\"text\" class=\"form-control\" name=\"attrb_2\" onkeyup=\"cfLengthCheck('속성2는', this, 100);\" /></td>";
			htmlStr +="	<td><input type=\"text\" class=\"form-control\" name=\"attrb_3\" onkeyup=\"cfLengthCheck('속성3은', this, 100);\" /></td>";
			htmlStr +="	<td><input type=\"text\" class=\"form-control\" name=\"sort_ordr\" /></td>";
			htmlStr +="	<td>"+comboStr+"</td>";
			htmlStr +="	<td class=\"text-center\"><button type=\"button\" class=\"btn btn-td-delete\" onclick=\"fnRemoveLine('id_new_code_"+removeNo+"');\"><i class=\"fa fa-minus-circle\"></i> 삭제</button></td>";
			htmlStr +="</tr>";

			table.append(htmlStr);
		}		

		function fnList(){
// 			document.location.href="/admin/code/selectGroupCodePageList.do";
			$("#aform").attr({action:"/admin/code/selectPageListGroupCodeMgt.do", method:'post'}).submit();
		}

		function fnUpdate(){

			var codeList = f_getList("code");
			var codeNmList = f_getList("code_nm");
			var sortOrdrList = f_getList("sort_ordr");
			
			var codeDupYn = false;
			var codeDupCnt = 0;

			for(i = 0; i < codeList.length; i++){

				if(codeList[i].value == ""){
					alert("코드를 입력해 주세요.");
					codeList[i].focus();
					return;
				}

				if(codeNmList[i].value == ""){
					alert("코드명을 입력해 주세요.");
					codeNmList[i].focus();
					return;
				}
				if(sortOrdrList[i].value == ""){
					alert("순서를 입력해 주세요.");
					sortOrdrList[i].focus();
					return;
				}


				codeDupCnt = 0;
				for(k = 0; k < codeList.length; k++){

					if(codeList[i].value == codeList[k].value){
						codeDupCnt++;

						if(codeDupCnt > 1){
							codeDupYn = true;
							break;
						}
					}
				}
			}

			if(codeDupYn){
				alert("중복된 코드가 존재합니다.");
				return;
			}

			if(confirm("수정하시겠습니까?")){
				$("#aform").attr({action:"/admin/code/updateCodeMgt.do", method:'post'}).submit();
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
	
			<form role="form" id="aform" method="post" action="/admin/code/selectPageListCodeMgt.do">
			<input type="hidden" name="group_id" id="group_id" value="${param.group_id }"/>
			
			<div class="card">

				<div class="card-body">

					<div class="form-group row">
						<label for="group_id" class="col-form-label col-xs-12 col-sm-3 col-md-3 col-lg-2">그룹코드</label>
						<div class="col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<input type="text" class="form-control" id="group_id" name="group_id" value="${resultMap.GROUP_ID }" disabled>
						</div>
  					</div>
  
					<div class="form-group row">
						<label for="group_id" class="col-form-label col-xs-12 col-sm-3 col-md-3 col-lg-2">그룹코드명</label>
						<div class="col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<input type="text" class="form-control" id="group_nm" name="group_nm" placeholder="그룹코드명" value="${resultMap.GROUP_NM }" onkeyup="cfLengthCheck('그룹코드명은', this, 50);">
						</div>
  					</div>

					<div class="form-group row">
						<label for="group_id" class="col-form-label col-xs-12 col-sm-3 col-md-3 col-lg-2">영문그룹코드명</label>
						<div class="col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<input type="text" class="form-control" id="group_nm_eng" name="group_nm_eng" placeholder="영문그룹코드명" value="${resultMap.GROUP_NM_ENG }" onkeyup="cfLengthCheck('영문그룹코드명은', this, 50);">
						</div>
  					</div>
  
					<div class="form-group row">
						<label for="group_id" class="col-form-label col-xs-12 col-sm-3 col-md-3 col-lg-2">비고</label>
						<div class="col-xs-12 col-sm-9 col-md-9 col-lg-10">
							<textarea class="form-control" rows="3" name="rm" placeholder="비고" onkeyup="cfLengthCheck('비고는', this, 100);">${resultMap.RM }</textarea>
						</div>
  					</div>

				</div>

			</div>

			<div class="card">
				<div class="card-body">

							<div class="box-body table-responsive no-padding">
								<table id="codeTable" class="table table-bordered">
									<tr class="text-center">
										<th>코드</th>
										<th>코드명</th>
										<th>영문코드명</th>
										<th class="px100">속성1</th>
										<th class="px100">속성2</th>
										<th class="px100">속성3</th>
										<th class="px100">순서</th>
										<th class="px100">사용여부</th>
										<th>삭제</th>
									</tr>
									<c:forEach var="code" items="${resultList}" varStatus="status">
										<tr id="id_code_${status.count }">
											<td><input type="text" name="code" class="form-control" value="${code.CODE }" onkeyup="cfLengthCheck('코드는', this, 4);"/></td>
											<td><input type="text" name="code_nm" class="form-control" value="${code.CODE_NM }" onkeyup="cfLengthCheck('코드명은', this, 300);"/></td>				
											<td><input type="text" name="code_nm_eng" class="form-control" value="${code.CODE_NM_ENG }" onkeyup="cfLengthCheck('코드영문명은', this, 100);"/></td>
											<td><input type="text" name="attrb_1" class="form-control" value="${code.ATTRB_1 }" onkeyup="cfLengthCheck('속성1은', this, 100);"/></td>
											<td><input type="text" name="attrb_2" class="form-control" value="${code.ATTRB_2 }" onkeyup="cfLengthCheck('속성2는', this, 100);"/></td>
											<td><input type="text" name="attrb_3" class="form-control" value="${code.ATTRB_3 }" onkeyup="cfLengthCheck('속성3은', this, 100);"/></td>
											<td><input type="text" name="sort_ordr" class="form-control" value="${code.SORT_ORDR }" /></td>
											<td>
												<select class="form-control" name="use_yn">
													<c:forEach var="ynCode" items="${ynCodeList}">
														<option value="${ynCode.CODE }" <c:if test="${code.USE_YN == ynCode.CODE }">selected</c:if>>${ynCode.CODE_NM }</option>
													</c:forEach>
												</select>
											</td>
											<td class="text-center"><button type="button" class="btn btn-td-delete" onclick="fnRemoveLine('id_code_${status.count }');"><i class="fa fa-minus-circle"></i> 삭제</button></td>
										</tr>
									</c:forEach>
								</table>
							</div>

				</div>

				<div class="box-footer">
					<div class="text-center">
						<button type="button" class="btn btn-list" onclick="fnList(); return false;"><i class="fa fa-reply"></i> 목록</button>
						<button type="button" class="btn btn-modify" onclick="fnUpdate(); return false;"><i class="fa fa-eraser"></i> 수정</button>
						<button type="button" class="btn btn-write" onclick="fnInsertForm(); return false;"><i class="fa fa-plus"></i> 신규등록</button>
					</div>
				</div>

			</div>
			</form>

		</section>
	</div>
	<!-- footer -->
	<%@ include file="/common/inc/footer.jspf" %>
	<!-- //fooer -->
</div>
</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
