<%@page import="com.whomade.kycarrots.framework.common.object.DataMap"%>
<%@page import="com.whomade.kycarrots.mgt.survey.vo.TbUserGroup"%>
<%@page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="param" 				class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="resultMap" 			class="com.whomade.kycarrots.mgt.survey.vo.TbUserGroup" scope="request"/>
<jsp:useBean id="resultList"  			type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="resultDegreeList"  	type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="userHseComboStr"  type="java.util.List" class="java.util.ArrayList" scope="request"/>



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
			// 회원정보 수정 모달창이 뜰때
			$("#selectModal").on("show.bs.modal", function(event){
				 var button = $(event.relatedTarget) // Button that triggered the modal
				 var goUrl="";
				 
				 if("modalsurvey"==button.data("discd")) {
					 goUrl= "/mgt/modal/selectModalSurveys.do";
					 $(".modal-title").html("진단지선택");
				 }
						 
				 var dataString = '';
		          var modal = $(this);
		            $.ajax({
		                type: "POST",
		                url: goUrl,
		                data: dataString,
		                cache: false,
		                success: function (data) {
		                    modal.find('#modal-body').html(data);
		                },
		                error: function(err) {
		                    console.log(err);
		                }
		            });
		           
			});
		   $("#selectModal").on('hidden.bs.modal', function (e) {
	        });
		
		});

		// 설문 Callback
		function fnCallBackSurveyPopup(surveyId,surveyNm){
			$('[name=SURVEY_ID]').val(surveyId);
			//$('[name=SURVEY_NM]').val(surveyNm);
			$( "#btn-close" ).trigger( "click" );
		}

		function new_window(freshurl) {
			  SmallWin = window.open(freshurl, 'Survey','scrollbars=yes,resizable=yes,toolbar=no,height=600,width=800');
			  if (!isIE4())	{
			    if (window.focus) { SmallWin.focus(); }
			  }
			  if (SmallWin.opener == null) SmallWin.opener = window;
			  SmallWin.opener.name = "Main";
		}
		
		function new_graphwindow(freshurl) {
			  SmallWin = window.open(freshurl, 'GraphMain','scrollbars=yes,resizable=yes,toolbar=no,height=768,width=1366');
			  if (!isIE4())	{
			    if (window.focus) { SmallWin.focus(); }
			  }
			  if (SmallWin.opener == null) SmallWin.opener = window;
			  SmallWin.opener.name = "GraphMain";
		}

		function new_itemwindow(freshurl) {
			  SmallWin = window.open(freshurl, 'ItemMain','scrollbars=yes,resizable=yes,toolbar=no,height=768,width=800');
			  if (!isIE4())	{
			    if (window.focus) { SmallWin.focus(); }
			  }
			  if (SmallWin.opener == null) SmallWin.opener = window;
			  SmallWin.opener.name = "ItemMain";
		}
		
		function new_userlocation(freshurl){
			location.href = freshurl;
		}
		
		function fnList(pYear,pDegree){
			$('#aformList [name=survey_year]').val(pYear);
			$('#aformList [name=survey_degree]').val(pDegree);
			$("#aformList").attr({action:"/mgt/survey/userGroupSurveyMgt.do", method:'post'}).submit();
		}


		function fnSaveDegree(){
	
			if($('#aformdegree [name=SURVEY_YEAR]').val() == ''){
				alert('조사년도를 입력해주세요.');
				$('#aformdegree [name=SURVEY_YEAR]').focus();
				return false;
			}

			if($('#aformdegree [name=SURVEY_DEGREE]').val() == ''){
				alert('차수를를 입력해 주세요..');
				$('#aformdegree [name=SURVEY_DEGREE]').focus();
				return false;
			}

			if(confirm("저장 하시겠습니까?")){
				 var goUrl = "/mgt/survey/updateUserGroupDegreeSurvey.do";
				 //$('#aformdegree').attr({action:goUrl, method:'post'}).submit();
				 $("#aformdegree").attr("action", goUrl);
				 $("#aformdegree").attr("method", "post");
				 $('#aformdegree').submit();
				 
			}
		}
		
		function fnDeleteDegree(pYear,pDegree){
			if(confirm("삭제 하시겠습니까?")){
				 var goUrl = "/mgt/survey/deleteUserGroupDegreeSurvey.do";
				 $('#aformdegree [name=SURVEY_YEAR]').val(pYear);
				 $('#aformdegree [name=SURVEY_DEGREE]').val(pDegree);
				 $("#aformdegree").attr("action", goUrl);
				 $("#aformdegree").attr("method", "post");
				 $('#aformdegree').submit();
			}
		}

		function fnCreateData(pYear,pDegree){
			if(confirm("데이타 생성 하시겠습니까?")){
			 	var goUrl = "/mgt/survey/insertUserEntryResult.do";
				$('#aformList [name=survey_year]').val(pYear);
				$('#aformList [name=survey_degree]').val(pDegree);
				fnFormCreateAjax(goUrl); 
			}
		}
		
		function fnUpdate(){

			if($('#aform [name=SURVEY_NM]').val() == ''){
				alert('진단지명을 입력해주세요.');
				$('#aform [name=SURVEY_NM]').focus();
				return false;
			}

			if($('#aform [name=SORT_ORDR]').val() == ''){
				alert('순서를 입력해 주세요..');
				$('#aform [name=SORT_ORDR]').focus();
				return false;
			}

			if(confirm("저장 하시겠습니까?")){
				 var goUrl = "/mgt/survey/updateUserGroupSurvey.do";
				 fnFormAjax(goUrl); 
			}
		}

		function fnDelete(){

			if(confirm("삭제 하시겠습니까?")){
				 var goUrl = "/mgt/survey/deleteUserGroupSurvey.do";
				 fnFormAjax(goUrl); 
			}

			
		}

		
		// 값돌려주기
		function fnSelect(SURVEY_ID,SURVEY_NM,SORT_ORDR,SURVEY_YEAR,SURVEY_DEGREE){
			$('#aform [name=SURVEY_ID]').val(SURVEY_ID);
			$('#aform [name=SORT_ORDR]').val(SORT_ORDR);
			$('#aform [name=SURVEY_YEAR]').val(SURVEY_YEAR);
			$('#aform [name=SURVEY_DEGREE]').val(SURVEY_DEGREE);
		}
		//신규
		function fnClear(){
			$('#aform [name=SURVEY_ID]').val("");
			$('#aform [name=SORT_ORDR]').val("");
			$('#aform [name=SURVEY_YEAR]').val("");
			$('#aform [name=SURVEY_DEGREE]').val("");
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
    					tronClick=	"'"+results[i].SURVEY_ID+"'";
    					tronClick+=",'"+results[i].SORT_ORDR_NM+"'";
    					tronClick+=",'"+results[i].SORT_ORDR+"'";
    					tronClick+=",'"+results[i].SURVEY_YEAR+"'";
    					tronClick+=",'"+results[i].SURVEY_DEGREE+"'";
    					
    					trStr="<tr style=\"cursor:pointer;cursor:hand;\" onclick=\"fnSelect("+tronClick+")\">";
    					//trStr += "<td>"+results[i].SURVEY_GROUP_ID+"</td>";
    					trStr += "<td>"+results[i].SURVEY_ID+"</td>";
    					trStr += "<td>"+results[i].SORT_ORDR_NM+"</td>";
    					trStr += "<td>"+results[i].SURVEY_YEAR+"</td>";
    					trStr += "<td>"+results[i].SURVEY_DEGREE+"</td>";
    					trStr += "<td class=\"text-center\"><button type=\"button\" class=\"btn btn-primary\" 	 onclick=\"new_window('/mgt/survey/entry.do?surveyId="+results[i].SURVEY_ID+"&surveyMode=0&userNo="+results[i].USER_NO+"&surveyGroupId="+results[i].SURVEY_GROUP_ID+"')\"(); return false;\"><i class=\"fa fa-book\"></i> 진단지</button></td>";
    					trStr += "<td class=\"text-center\"><button type=\"button\" class=\"btn btn-primary\" 	 onclick=\"new_itemwindow('/mgt/survey/exportItems.do?surveyId="+results[i].SURVEY_ID+"&surveyMode=0&userNo="+results[i].USER_NO+"&surveyGroupId="+results[i].SURVEY_GROUP_ID+"')\"(); return false;\"><i class=\"fa fa-tag\"></i> 항목</button></td>";
    					trStr += "<td class=\"text-center\"><button type=\"button\" class=\"btn btn-primary\" 	 onclick=\"new_graphwindow('/mgt/survey/statisticsEntry.do?surveyId="+results[i].SURVEY_ID+"&surveyMode=0&userNo="+results[i].USER_NO+"&surveyGroupId="+results[i].SURVEY_GROUP_ID+"&surveyYear="+results[i].SURVEY_YEAR+"&surveyDegree="+results[i].SURVEY_DEGREE+"'); return false;\"><i class=\"fa fa-signal\"></i> 결과</button></td>";
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

		function fnFormCreateAjax(pUrl){
            console.log(pUrl);
            $.ajax({
                type: "post",
                url: pUrl,
				data : $('#aformList').serialize(), 
				dataType : 'json',
                cache: false,
                success: function (response) {
                	//console.log(response);
                	var resultCode="";
                	resultCode=response.resultStats.resultCode;
					if(resultCode=="ok"){
                		alert("처리되었습니다.");
                	}

    				
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
<form role="form" id="aformList" method="post">
<input type="hidden" name="user_no" 		id="user_no" 			value="<%=param.getString("USER_NO")%>"/>
<input type="hidden" name="survey_group_id" id="survey_group_id"  	value="<%=param.getString("SURVEY_GROUP_ID")%>"/>
<input type="hidden" name="survey_year" 	id="survey_year" 		/>
<input type="hidden" name="survey_degree" 	id="survey_degree"  	/>
</form>

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
									<label for="group_id">평가 그룹</label>
									<input type="text" class="form-control" value="<%=resultMap.getSURVEY_GROUP_NM()%>/<%=resultMap.getUSER_NM()%>" disabled>
								</div>
								<div class="col-xs-7">
									<div class="box box-primary">
										 <h4 class="subtitle"><i></i>조사년도/차수리스트</h4>
										 <div class="box-body table-responsive no-padding">
											<table id="menuTable" class="table table-hover table-bordered">
												<colgroup>
													<col width="*">
													<col width="100px">
													<col width="*">
													<col width="*">
													<col width="*">
												</colgroup>
												<thead>
													<tr>
														<th class="text-center">조사년도</th>
														<th class="text-center">차수</th>
														<th class="text-center">데이타생성</th>
														<th class="text-center">결과보기</th>
														<th class="text-center">삭제</th>
													</tr>
												</thead>
												<tbody>
												<%
													int dataDegreeNo = resultDegreeList.size();
											        String sBackgroud ="";
											
													if(resultDegreeList.size() > 0){
														for(int i = 0; i < resultDegreeList.size(); i++){
															DataMap dataMapDegree = (DataMap) resultDegreeList.get(i);
															
															if(dataMapDegree.getString("SURVEY_YEAR").equals(param.getString("SURVEY_YEAR")) && dataMapDegree.getString("SURVEY_DEGREE").equals(param.getString("SURVEY_DEGREE"))) {
																sBackgroud="background:#6a6ab3";
															}else{
																sBackgroud="";
															}
												%>
		
												<tr style="cursor:pointer;cursor:hand;<%=sBackgroud%>" >
														<td onclick="fnList('<%=dataMapDegree.getString("SURVEY_YEAR")%>','<%=dataMapDegree.getString("SURVEY_DEGREE")%>')"><%=dataMapDegree.getString("SURVEY_YEAR")%></td>
														<td onclick="fnList('<%=dataMapDegree.getString("SURVEY_YEAR")%>','<%=dataMapDegree.getString("SURVEY_DEGREE")%>')"><%=dataMapDegree.getString("SURVEY_DEGREE")%></td>
														<td class="text-center"><button type="button" class="btn btn-primary" onclick="fnCreateData('<%=dataMapDegree.getString("SURVEY_YEAR")%>','<%=dataMapDegree.getString("SURVEY_DEGREE")%>');return false;"  ><i class="fa fa-book"></i> 데이타생성</button></td>
														<td class="text-center"><button type="button" class="btn btn-primary" onclick="new_graphwindow('/mgt/survey/statisticsEntry.do?surveyMode=0&userNo=<%=dataMapDegree.getString("USER_NO")%>&surveyGroupId=<%=dataMapDegree.getString("SURVEY_GROUP_ID")%>&surveyYear=<%=dataMapDegree.getString("SURVEY_YEAR")%>&surveyDegree=<%=dataMapDegree.getString("SURVEY_DEGREE")%>');"><i class="fa fa-signal"></i> 결과</button></td>
														<td class="text-center"><button type="button" class="btn btn-primary" onclick="fnDeleteDegree('<%=dataMapDegree.getString("SURVEY_YEAR")%>','<%=dataMapDegree.getString("SURVEY_DEGREE")%>')"><i class="fa fa-book"></i> 삭제</button></td>
												</tr>
		
												<%
														}
													} else {
												%>
														<tr>
															<td class="text-center" colspan="5"><spring:message code="msg.data.empty" /></td>
														</tr>
												<%	
													}
												%>
											
												</tbody>
											</table>
										</div>
									</div>			
								</div>	
								<div class="col-xs-5">
									<div class="box box-primary">
									<h4 class="subtitle">
										<i></i>조시년도 차수등록
									</h4>
									<div class="tb-box form-horizontal">
										<form role="form" id="aformdegree" method="post">
										<input type="hidden" name="USER_NO" 		 			value="<%=param.getString("USER_NO")%>"/>
										<input type="hidden" name="SURVEY_GROUP_ID"  			value="<%=param.getString("SURVEY_GROUP_ID")%>"/>
											<div class="form-group">
												<label class="control-label col-sm-2" for="SURVEY_YEAR">조사년도</label>
												<div class="col-sm-3">
													<input type="text" class="form-control" name="SURVEY_YEAR" >
												</div>
												<div class="col-sm-2">
												</div>
												
												<label class="control-label col-sm-2" for="SURVEY_DEGREE">차수</label>
												<div class="col-sm-3">
													<input type="text" class="form-control" name="SURVEY_DEGREE" >
												</div>
											</div>
				
											<div class="box-footer">
													<div class="pull-right">
														<button type="button" class="btn btn-primary" onclick="fnSaveDegree(); return false;"><i class="fa fa-pencil"></i> 신규</button>
													</div>
											</div>
										</form>
										</div>
									</div>
								</div><!-- /.col-xs-5 -->
							</div><!-- /.box-body -->
						</div><!-- /.box box-primary -->
					
						<div class="col-xs-7">
							<div class="box box-primary">
								 <h4 class="subtitle"><i></i>진단지리스트</h4>
								<div class="box-body table-responsive no-padding">
									<table id="menuTable" class="table table-hover table-bordered">
										<colgroup>
											<col width="*">
											<col width="100px">
										</colgroup>
										<thead>
											<tr>
												<th class="text-center">진단지유형</th>
												<th class="text-center">설문ID</th>
												<th class="text-center">조사년도</th>
												<th class="text-center">차수</th>
												<th class="text-center">진단지보기</th>
												<th class="text-center">진단지항목</th>
												<th class="text-center">진단지실시간</th>
											</tr>
										</thead>
										<tbody>
									<%
										int dataNo = resultList.size();
										if(resultList.size() > 0){
											for(int i = 0; i < resultList.size(); i++){
												DataMap dataMap = (DataMap) resultList.get(i);
									%>
										<tr style="cursor:pointer;cursor:hand;" onclick="fnSelect('<%=dataMap.getString("SURVEY_ID")%>','<%=dataMap.getString("SORT_ORDR_NM")%>','<%=dataMap.getString("SORT_ORDR")%>','<%=dataMap.getString("SURVEY_YEAR")%>','<%=dataMap.getString("SURVEY_DEGREE")%>')">
												<td><%=dataMap.getString("SORT_ORDR_NM") %></td>
												<td><%=dataMap.getString("SURVEY_ID") %></td>
												<td><%=dataMap.getString("SURVEY_YEAR") %></td>
												<td><%=dataMap.getString("SURVEY_DEGREE") %></td>
												<td class="text-center"><button type="button" class="btn btn-primary" 	 onclick="new_window('/mgt/survey/entry.do?surveyId=<%=dataMap.getString("SURVEY_ID")%>&surveyMode=0&userNo=<%=dataMap.getString("USER_NO")%>&surveyGroupId=<%=dataMap.getString("SURVEY_GROUP_ID")%>&surveyYear=<%=dataMap.getString("SURVEY_YEAR")%>&surveyDegree=<%=dataMap.getString("SURVEY_DEGREE")%>')"(); return false;"><i class="fa fa-book"></i> 진단지</button></td>
												<td class="text-center"><button type="button" class="btn btn-primary" 	 onclick="new_itemwindow('/mgt/survey/exportItems.do?surveyId=<%=dataMap.getString("SURVEY_ID")%>&surveyMode=0&userNo=<%=dataMap.getString("USER_NO")%>&surveyGroupId=<%=dataMap.getString("SURVEY_GROUP_ID")%>&surveyYear=<%=dataMap.getString("SURVEY_YEAR")%>&surveyDegree=<%=dataMap.getString("SURVEY_DEGREE")%>'); return false;"><i class="fa  fa-tag"></i> 항목</button></td>
												<td class="text-center"><button type="button" class="btn btn-primary"    onclick="new_userlocation('/mgt/survey/userSurveyMenu.do?surveyId=<%=dataMap.getString("SURVEY_ID")%>&userNo=<%=dataMap.getString("USER_NO")%>&surveyGroupId=<%=dataMap.getString("SURVEY_GROUP_ID")%>&surveyYear=<%=dataMap.getString("SURVEY_YEAR")%>&surveyDegree=<%=dataMap.getString("SURVEY_DEGREE")%>');"><i class="fa fa-signal"></i> 결과</button></td>
											</tr>
									<%
											}
										} else {
									%>
											<tr>
												<td class="text-center" colspan="7"><spring:message code="msg.data.empty" /></td>
											</tr>
									<%	
										}
									%>
										</tbody>
									</table>
								</div>
						</div><!-- /.col-xs-6 -->

						</div>
						<div class="col-xs-5">
							<div class="box box-primary">
							<h4 class="subtitle">
								<i></i>진단지등록
							</h4>
							<div class="tb-box form-horizontal"">
								<form role="form" id="aform" method="post">
									<input type="hidden" name="USER_NO" 		id="USER_NO" 			value="<%=param.getString("USER_NO")%>"/>
									<input type="hidden" name="SURVEY_GROUP_ID" id="SURVEY_GROUP_ID"  	value="<%=param.getString("SURVEY_GROUP_ID")%>"/>
									<input type="hidden" name="SURVEY_YEAR" 	id="SURVEY_YEAR" />
									<input type="hidden" name="SURVEY_DEGREE" 	id="SURVEY_DEGREE"  />

									<div class="form-group">
										<label class="control-label col-sm-2" for="SURVEY_NM">설문ID</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" name="SURVEY_ID" id="SURVEY_ID" readonly />
										</div>
										<div class="col-sm-1">
												<button type="button" class="btn btn-default" data-toggle="modal" data-target="#selectModal" data-discd="modalsurvey">
													<i class="fa fa-search"></i> 
												</button>
										</div>

										<label class="control-label col-sm-2" for="SORT_ORDR">진단지유형</label>
										<div class="col-sm-3">
											<select id="SORT_ORDR" name="SORT_ORDR" class="form-control input-sm">
												<%=CommboUtil.getComboStr(userHseComboStr, "CODE", "CODE_NM", param.getString("SORT_ORDR"),"선택하세요")%>
											</select>
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

<!-- Trigger the modal with a button -->
<!-- Modal -->
<div id="selectModal" class="modal fade" role="dialog">
  <div class="modal-dialog modal-lg">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" id="modal-title">팝업제목</h4>
      </div>
       <div id="modal-body">
 		</div>
      <div class="modal-footer">
        <button type="button" id="btn-close" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
</div>

</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
