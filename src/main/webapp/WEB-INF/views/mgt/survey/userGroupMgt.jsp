<%@page import="com.whomade.kycarrots.framework.common.object.DataMap"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="param" 		class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="resultMap" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="resultList"  	type="java.util.List" class="java.util.ArrayList" scope="request"/>

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
		});

		function fnList(){
			$("#aform").attr({action:"/mgt/sclas/selectPageListSclas.do", method:'post'}).submit();
		}

		function fnUpdate(){

			if($('[name=SURVEY_GROUP_NM]').val() == ''){
				alert('그룹명을 입력해주세요.');
				$('[name=SURVEY_GROUP_NM]').focus();
				return false;
			}

			if($('[name=SORT_ORDR]').val() == ''){
				alert('순서를 입력해 주세요..');
				$('[name=SORT_ORDR]').focus();
				return false;
			}

			if(confirm("저장 하시겠습니까?")){
				 var goUrl = "/mgt/survey/updateUserGroup.do";
				 fnFormAjax(goUrl); 
			}
		}

		function fnDelete(){

			if(confirm("삭제 하시겠습니까?")){
				 var goUrl = "/mgt/survey/deleteUserGroup.do";
				 fnFormAjax(goUrl); 
			}

			
		}

		
		// 값돌려주기
		function fnSelect(SURVEY_GROUP_ID,SURVEY_GROUP_NM,SORT_ORDR){
			$('[name=SURVEY_GROUP_ID]').val(SURVEY_GROUP_ID);
			$('[name=SURVEY_GROUP_NM]').val(SURVEY_GROUP_NM);
			$('[name=SORT_ORDR]').val(SORT_ORDR);
		}
		//신규
		function fnClear(){
			$('[name=SURVEY_GROUP_ID]').val("");
			$('[name=SURVEY_GROUP_NM]').val("");
			$('[name=SORT_ORDR]').val("");
		}

		function fnFormAjax(pUrl){
            console.log(pUrl);
            $.ajax({
                type: "post",
                url: pUrl,
				data : $('#aform').serialize(), 
				dataType : 'json',
                cache: false,
                success: function (response) {
                	
    				var results=response.resultStats.resultList;
                	//console.log(response);
                	var resultCode="";
					resultCode=response.resultStats.resultCode;
                	
					if(resultCode=="ok"){
                		alert("처리되었습니다.");
                	}
                	var trStr="";
                	 $( '#menuTable > tbody').empty();
  		           	var irow=0;

    				$.each(results, function (i) {
    					tronClick=	"'"+results[i].SURVEY_GROUP_ID+"'";
    					tronClick+=",'"+results[i].SURVEY_GROUP_NM+"'";
    					tronClick+=",'"+results[i].SORT_ORDR+"'";
    					
    					trStr="<tr style=\"cursor:pointer;cursor:hand;\" onclick=\"fnSelect("+tronClick+")\">";
    					//trStr += "<td>"+results[i].SURVEY_GROUP_ID+"</td>";
    					trStr += "<td>"+results[i].SURVEY_GROUP_NM+"</td>";
    					trStr += "<td class=\"text-right\">"+results[i].SORT_ORDR+"</td>";
    					trStr += "</tr>";
    					//console.log(trStr);
    					$('#menuTable').append(trStr);
    					irow++;
    				});
    				
					if(irow==0){
	   					trStr="<tr>	<td class=\"text-center\" colspan=\"2\" >데이타가 없습니다.</td></tr>";
	   					$('#menuTable').append(trStr);
	   				}

					fnClear();
    				
                },
                error: function(err) {
                    console.log(err);
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
				<div class="col-xs-12">
						<div class="box box-primary">
							<div class="box-body">
								<div class="form-group">
									<label for="group_id">관리자</label>
									<input type="text" class="form-control" value="<%=resultMap.getString("USER_NM")%>/<%=resultMap.getString("USER_ID")%>" disabled>
								</div>
							</div><!-- /.box-body -->
						</div>
					
						<div class="col-xs-6">
							<div class="box box-primary">
								 <h4 class="subtitle"><i></i>평가그룹리스트</h4>
								<div class="box-body table-responsive no-padding">
									<table id="menuTable" class="table table-hover table-bordered">
										<colgroup>
											<col width="*">
											<col width="100px">
										</colgroup>
										<thead>
											<tr>
												<th class="text-center">그룹명</th>
												<th class="text-center">순서</th>
											</tr>
										</thead>
										<tbody>
									<%
										int dataNo = resultList.size();
										if(resultList.size() > 0){
											for(int i = 0; i < resultList.size(); i++){
												DataMap dataMap = (DataMap) resultList.get(i);
									%>
										<tr style="cursor:pointer;cursor:hand;" onclick="fnSelect('<%=dataMap.getString("SURVEY_GROUP_ID")%>','<%=dataMap.getString("SURVEY_GROUP_NM")%>','<%=dataMap.getString("SORT_ORDR")%>')">
												<td><%=dataMap.getString("SURVEY_GROUP_NM") %></td>
												<td><%=dataMap.getString("SORT_ORDR") %></td>
											</tr>
									<%
											}
										} else {
									%>
											<tr>
												<td class="text-center" colspan="2"><spring:message code="msg.data.empty" /></td>
											</tr>
									<%	
										}
									%>
										</tbody>
									</table>
								</div>
						</div><!-- /.col-xs-6 -->

						</div>
						<div class="col-xs-6">
							<div class="box box-primary">
							<h4 class="subtitle">
								<i></i>평가그룹등록
							</h4>
							<div class="tb-box form-horizontal"">
								<form role="form" id="aform" method="post">
									<input type="hidden" name="USER_NO" id="USER_NO" value="<%=param.getString("USER_NO")%>"/>
									<input type="hidden" name="SURVEY_GROUP_ID" id="SURVEY_GROUP_ID" />

									<div class="form-group">
										<label class="control-label col-sm-2" for="SURVEY_GROUP_NM">그룹명</label>
										<div class="col-sm-3">
											<input type="text" class="form-control" name="SURVEY_GROUP_NM" id="SURVEY_GROUP_NM" />
										</div>
										<label class="control-label col-sm-2" for="SORT_ORDR">순서</label>
										<div class="col-sm-3">
											<input type="text" class="form-control" name="SORT_ORDR" id="SORT_ORDR"  />
										</div>
									</div>
		
									<div class="box-footer">
											<div class="pull-right">
												<button type="button" class="btn btn-primary" 	 onclick="fnClear(); return false;"><i class="fa fa-pencil"></i> 신규</button>
												<button type="button" class="btn btn-info"       onclick="fnUpdate(); return false;"><i class="fa fa-save"></i> 저장</button>
												<button type="button" class="btn btn-warning" 	 onclick="fnDelete(); return false;"><i class="fa fa-trash"></i> 삭제</button>
											</div>
									</div>
							</div><!-- /.box -->
							</form>
						</div><!-- /.col-xs-6 -->
					
				</div>
			</div>	
		</section>
	</div>
	<!-- footer -->
	<%@ include file="/common/inc/footer.jspf" %>
	<!-- //fooer -->
</div>
</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
