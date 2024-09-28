<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>
	<script type="text/javascript">
	//<![CDATA[
		function fnGoPage(currentPage){

			$("#currentPage").val(currentPage);
			$("#aform").attr({action:"/admin/code/selectPageListGroupCodeMgt.do", method:'post'}).submit();			
		}
		$(function(){
			//f_divView('main_0', 'main_0');	//메뉴구조 유지
		});

		function fnInsertForm(){		

			document.location.href="/admin/code/insertFormGroupCodeMgt.do";
		}

		function fnSelectCodeList(groupId){

			$("#group_id").val(groupId);
			$("#aform").attr({action:"/admin/code/selectListCodeMgt.do", method:'post'}).submit();
		}

		function fnSearch(){

			$("#aform").attr({action:"/admin/code/selectPageListGroupCodeMgt.do", method:'post'}).submit();
		}

		function fnDelete(){
			var delGrpCodeList = f_getList("del_grp_code");
			var chkFag = false;

			for(i = 0; i < delGrpCodeList.length; i++){
				if(delGrpCodeList[i].checked == true){
					chkFag = true;
					break;
				}
			}
			
			if(chkFag == false){

				alert("삭제할 그룹코드를 선택해 주세요.");
				return;
			}

			if(confirm("삭제하시겠습니까?")){

				$("#aform").attr({action:"/admin/code/deleteGroupCodeMgt.do", method:'post'}).submit();
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
	
	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper">
		<!-- Content Header (Page header) -->
		<section class="content-header">
		<div id="navi"><i class="fa fa-home f12 color-lgray"></i><span class="blind">home</span>><span class="text" id="spnavi"></span></div>
    	<div id="pagetitle">
    	</div>
		</section>

		<!-- Main content -->
		<section class="content container-fluid">
			<div class="row">
				<div class="col-12">
				<!-- content 영역 -->
				<form id="aform" method="post" action="/admin/code/selectPageListGroupCodeMgt.do">
					<input type="hidden" name="group_id" id="group_id" />

					<!-- top search box // -->
					<div class="box-header form-inline">

						<input type="text" name="sch_grp_code_nm" value="${param.sch_grp_code_nm }" class="form-control w-25" placeholder="그룹코드명">
						<button type="button" class="btn btn-top-search" onclick="fnSearch(); return false;"><i class="fa fa-search"></i></button>

					</div>
					<!-- // top search box -->

					<!-- box header // -->
					<div class="box-top col-12 row justify-content-between">

						<div class="col-auto text-left"><!-- footer box left -->
<!-- 검색결과 갯수
							<p class="searchword-result">검색결과 <strong>9,999</strong>개</p>
// -->
						</div>
						<div class="col-auto form-inline text-right"><!-- footer box right -->
<!-- sort combo
							<select class="form-control w-auto">
								<option>100개</option>
							</select>
// -->
							<button type="button" class="btn btn-delete" onclick="fnDelete(); return false;"><i class="fa fa-trash"></i> 삭제</button>
							<button type="button" class="btn btn-write" onclick="fnInsertForm(); return false;"><i class="fa fa-pen"></i> 등록</button>
						</div>

					</div>
					<!-- // box header -->

					<!-- list box // -->
					<div class="box-body no-pad-top table-responsive">
						<table class="table table-bordered table-hover">
							<colgroup>
								<col width="10" />
								<col width="50" />
								<col width="*" />
								<col width="*" />
								<col width="*" />
								<col width="150" />
								<col width="150" />
							</colgroup>
							<thead>
								<tr class="text-center">
									<th></th>
									<th>No</th>
									<th>그룹코드</th>
									<th>그룹코드명</th>
									<th>영문그룹코드명</th>
									<th>등록자</th>
									<th>등록일</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="group" items="${resultList}" varStatus="status">
								<tr style="cursor:pointer;cursor:hand;" class="text-center">
									<td><input type="checkbox" name="del_grp_code" value="${group.GROUP_ID }" /></td>
									<td class="text-center" onclick="fnSelectCodeList('${group.GROUP_ID}')">
										<c:out value="${pageNavigationVo.totalCount - (pageNavigationVo.currentPage-1) * pageNavigationVo.rowPerPage - status.index}" />
									</td>
									<td onclick="fnSelectCodeList('${group.GROUP_ID}')">${group.GROUP_ID }</td>
									<td onclick="fnSelectCodeList('${group.GROUP_ID}')" class="text-left">${group.GROUP_NM }</td>				
									<td onclick="fnSelectCodeList('${group.GROUP_ID}')"  class="text-left">${group.GROUP_NM_ENG}</td>
									<td onclick="fnSelectCodeList('${group.GROUP_ID}')" >${group.REGISTER_NM}</td>
									<td onclick="fnSelectCodeList('${group.GROUP_ID}')" >${group.REGIST_YMD}</td>						
								</tr>
								</c:forEach>
								<c:if test="${fn:length(resultList) == 0}">
								<tr>
									<td class="text-center" colspan="7"><spring:message code="msg.data.empty" /></td>								
								</tr>
								</c:if>
							</tbody>
						</table>
					</div>
					<!-- // list box -->

					<!-- box footer // -->
					<div class="box-footer text-center">
						<c:if test="${fn:length(resultList) > 0}">
							${navigationBar }
						</c:if>
					</div>
					<!-- // box footer -->

				</form>
				</div>
			</div>	
		</section>
	</div>
	<!-- footer -->
	<%@ include file="/common/inc/footer.jspf" %>
	<!--//footer -->
</div>
</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
