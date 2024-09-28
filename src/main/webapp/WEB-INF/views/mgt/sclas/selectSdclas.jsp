<%@page import="com.whomade.kycarrots.framework.common.object.DataMap"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="param" 		class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="itemMap" 	class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
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

			if($('[name=sclas_code]').val() == ''){
				alert('품목코드를 입력해 주세요.');
				$('[name=sclas_code]').focus();
				return false;
			}

			if($('[name=sclas_nm]').val() == ''){
				alert('품목명을 입력해 주세요.');
				$('[name=sclas_nm]').focus();
				return false;
			}
			
			if($('[name=sort_ordr]').val() == ''){
				alert('순서를 입력해 주세요.');
				$('[name=sort_ordr]').focus();
				return false;
			}
			

			if(confirm("저장 하시겠습니까?")){
				 var goUrl = "/mgt/sclas/updateSdclas.do";
				 fnFormAjax(goUrl); 
			}
		}

		function fnDelete(){

			if($('[name=sdclas_code]').val() == ''){
				alert('품종선택후  삭제 하세요.');
				return false;
			}

			
			if(confirm("삭제 하시겠습니까?")){
				 var goUrl = "/mgt/sclas/deleteSdclas.do";
				 fnFormAjax(goUrl); 
			}

			
		}

		
		// 값돌려주기
		function fnSelect(sdclas_code,sdclas_nm,attrb_1,attrb_2,attrb_3,sort_ordr){
			$('[name=sdclas_code]').val(sdclas_code);
			$('[name=sdclas_nm]').val(sdclas_nm);
			$('[name=attrb_1]').val(attrb_1);
			$('[name=attrb_2]').val(attrb_2);
			$('[name=attrb_3]').val(attrb_3);
			$('[name=sort_ordr]').val(sort_ordr);
		}
		//신규
		function fnClear(){
			$('[name=sdclas_code]').val("");
			$('[name=sdclas_nm]').val("");
			$('[name=attrb_1]').val("");
			$('[name=attrb_2]').val("");
			$('[name=attrb_3]').val("");
			$('[name=sort_ordr]').val("");
			
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
                	console.log(response);
                	var resultCode="";
                	resultCode=response.resultStats.resultCode;
                	if(resultCode=="du"){
                		alert("삭제된 중복 코드가 존재합니다.");
                		return;
                	}else if(resultCode=="ok"){
                		alert("처리되었습니다.");
                	}
                	
                	var trStr="";
                	 $( '#menuTable > tbody').empty();
 		           	var irow=0;
                	 
    				$.each(results, function (i) {
    					tronClick=	"'"+results[i].SDCLAS_CODE+"'";
    					tronClick+=",'"+results[i].SDCLAS_NM+"'";
						if(results[i].ATTRB_1)	tronClick+=",'"+results[i].ATTRB_1+"'";
    					else	tronClick+=",''";
						if(results[i].ATTRB_2)	tronClick+=",'"+results[i].ATTRB_2+"'";
    					else	tronClick+=",''";
						if(results[i].ATTRB_3)	tronClick+=",'"+results[i].ATTRB_3+"'";
    					else	tronClick+=",''";
    					tronClick+=",'"+results[i].SORT_ORDR+"'";
    					
    					trStr="<tr style=\"cursor:pointer;cursor:hand;\" onclick=\"fnSelect("+tronClick+")\">";
    					trStr += "<td>"+results[i].SDCLAS_CODE+"</td>";
    					trStr += "<td>"+results[i].SDCLAS_NM+"</td>";
						if(results[i].ATTRB_1)	trStr += "<td>"+results[i].ATTRB_1+"</td>";
    					else	trStr += "<td></td>";

    					if(results[i].ATTRB_2)	trStr += "<td>"+results[i].ATTRB_2+"</td>";
    					else	trStr += "<td></td>";

    					if(results[i].ATTRB_3)	trStr += "<td>"+results[i].ATTRB_3+"</td>";
    					else	trStr += "<td></td>";
    					
    					trStr += "<td class=\"text-right\">"+results[i].SORT_ORDR+"</td>";
    					//console.log(trStr);
    					$('#menuTable').append(trStr);
						irow++;

    				});
					if(irow==0){
	   					trStr="<tr>	<td class=\"text-center\" colspan=\"6\" >데이타가 없습니다.</td></tr>";
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
									<label for="group_id">시도/구군</label>
									<input type="text" class="form-control" value="<%=itemMap.getString("CODE_NM") %>-<%=itemMap.getString("SCLAS_NM") %>"" disabled>
								</div>
							</div><!-- /.box-body -->
						</div>
					
						<div class="col-xs-6">
							<div class="box box-primary">
								 <h4 class="subtitle"><i></i>동리스트</h4>
								<div class="box-body table-responsive no-padding">
									<table id="menuTable" class="table table-hover table-bordered">
										<colgroup>
											<col width="10%">
											<col width="*">
											<col width="15%">
											<col width="15%">
											<col width="15%">
											<col width="15%">
										</colgroup>
										<thead>
											<tr>
												<th class="text-center">동코드</th>
												<th class="text-center">동이름</th>
												<th class="text-center">속성1</th>
												<th class="text-center">속성2</th>
												<th class="text-center">속성3</th>
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
										<tr style="cursor:pointer;cursor:hand;" onclick="fnSelect('<%=dataMap.getString("SDCLAS_CODE")%>'
										                                                                                        ,'<%=dataMap.getString("SDCLAS_NM") %>'
										                                                                                        ,'<%=dataMap.getString("ATTRB_1") %>'
										                                                                                        ,'<%=dataMap.getString("ATTRB_2") %>'
										                                                                                        ,'<%=dataMap.getString("ATTRB_3") %>'
										                                                                                        ,'<%=dataMap.getString("SORT_ORDR") %>')
										                                                                                        ; return false;">
												<td><%=dataMap.getString("SDCLAS_CODE") %></td>
												<td><%=dataMap.getString("SDCLAS_NM") %></td>
												<td><%=dataMap.getString("ATTRB_1") %></td>
												<td><%=dataMap.getString("ATTRB_2") %></td>
												<td><%=dataMap.getString("ATTRB_3") %></td>
												<td class="text-right"><%=dataMap.getString("SORT_ORDR") %></td>
											</tr>
									<%
											}
										} else {
									%>
											<tr>
												<td class="text-center" colspan="6"><spring:message code="msg.data.empty" /></td>
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
								<i></i>동등록
							</h4>
							<div class="tb-box form-horizontal"">
								<form role="form" id="aform" method="post">
									<input type="hidden" name="group_id" 		value="<%=param.getString("group_id") %>" />
									<input type="hidden" name="op_code" 		value="<%=param.getString("op_code") %>" />
									<input type="hidden" name="sclas_code" 	value="<%=param.getString("sclas_code") %>" />
									<div class="form-group">
										<label class="control-label col-sm-2" for="sdclas_code">동코드</label>
										<div class="col-sm-3">
											<input type="text" class="form-control" name="sdclas_code" id="sdclas_code" />
										</div>
										<label class="control-label col-sm-2" for="sdclas_nm">동이름</label>
										<div class="col-sm-3">
											<input type="text" class="form-control" name="sdclas_nm" id="sdclas_nm"  />
										</div>
									</div>
									
									<div class="form-group">
										<label class="control-label col-sm-2" for="attrb_1">속성1</label>
										<div class="col-sm-3">
											<input type="text" class="form-control" name="attrb_1" id="attrb_1"  />
										</div>
										<label class="control-label col-sm-2" for="attrb_2">속성2</label>
										<div class="col-sm-3">
											<input type="text" class="form-control" name="attrb_2" id="attrb_2" />
										</div>
									</div>	
									<div class="form-group">
										<label class="control-label col-sm-2" for="attrb_3">속성3</label>
										<div class="col-sm-3">
											<input type="text" class="form-control numeric" name="attrb_3" id="attrb_3"  />
										</div>
										<label class="control-label col-sm-2" for="sort_ordr">순서</label>
										<div class="col-sm-3">
											<input type="text" class="form-control numeric" name="sort_ordr" id="sort_ordr" />
										</div>
									</div>	
	
									<div class="box-footer">
											<div class="pull-right">
												<button type="button" class="btn btn-primary" onclick="fnClear(); return false;"><i class="fa fa-pencil"></i> 신규</button>
												<button type="button" class="btn btn-info"    onclick="fnUpdate(); return false;"><i class="fa fa-save"></i> 저장</button>
												<button type="button" class="btn btn-warning" onclick="fnDelete(); return false;"><i class="fa fa-trash"></i> 삭제</button>
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
