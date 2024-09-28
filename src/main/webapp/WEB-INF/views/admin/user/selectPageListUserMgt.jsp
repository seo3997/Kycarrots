<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@ page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>

<jsp:useBean id="param" 				class="com.whomade.kycarrots.framework.common.object.DataMap" 			scope="request"/>
<jsp:useBean id="pageNavigationVo" 		class="com.whomade.kycarrots.framework.common.page.vo.pageNavigationVo" scope="request"/>
<jsp:useBean id="userSttusComboStr"  	class="java.util.ArrayList"	type="java.util.List"  				scope="request"/>
<jsp:useBean id="userSeComboStr"  		class="java.util.ArrayList"	type="java.util.List"  				scope="request"/>
<jsp:useBean id="companyCodeComboStr"  	class="java.util.ArrayList"	type="java.util.List"  				scope="request"/>
<jsp:useBean id="resultList"  			class="java.util.ArrayList"	type="java.util.List"  				scope="request"/>

<%@ include file="/common/inc/common.jspf" %>
<%@ include file="/common/inc/docType.jspf" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/inc/meta.jspf" %>
	<title><%=headTitle%></title>
	<%@ include file="/common/inc/cssScript.jspf" %>
	<script type="text/javascript">
	//<![CDATA[
		$(function(){
			$('[name=sch_user_se_code], [name=sch_user_sttus_code]').change(function(e){
				fnSearch();
			});

			fnGetAllUserAuthorList($("#sch_user_author"),'<%=param.getString("sch_user_author")%>','A');

			//날짜 검색 버튼 이벤트
			$("[name=optionsRadios]").on('change' , function(){
				var changeVal = $(this).val();
				fnRadioReset(changeVal);
			});
			
			// 회원정보 수정 모달창이 뜰때
			$("#selectModal").on("show.bs.modal", function(event){
				 var button = $(event.relatedTarget) // Button that triggered the modal
				 var goUrl="";
		
				 
				 if("modaluser"==button.data("discd")) {
					 goUrl= "/mgt/common/selectPopupAuthorUser.do";
					 $(".modal-title").html("일반회원검색");
				 }
				 
				 var dataString = '';
		          var modal = $(this);
		            $.ajax({
		                type: "GET",
		                url:goUrl,
		                data: dataString,
		                cache: false,
		                success: function (data) {
		                    //console.log(data);
							//$('.modal-backdrop').hide();
							//$('body').removeClass('modal-open'); // For scroll run				
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
		
		// 페이지 이동
		function fnGoPage(currentPage){
			$("#currentPage").val(currentPage);
			$("#aform").attr({action:"/admin/user/selectPageListUserMgt.do", method:'post'}).submit();
		}
		// 사용자등록폼 이동
		function fnInsertForm(){
			document.location.href="/admin/user/insertFormUserMgt.do";
		}
		// 상세조회
		function fnSelect(user_no){
			$("#user_no").val(user_no);
			$("#aform").attr({action:"/admin/user/selectUserMgt.do", method:'post'}).submit();
		}
		// 검색
		function fnSearch(){
			$("#currentPage").val("1");
			$("#aform").attr({action:"/admin/user/selectPageListUserMgt.do", method:'get'}).submit();
		}

		// 리셋 버튼 
		function fnReset(){
			//초기화 버튼 클릭시  , 기존 초기화 검색 => 인풋값 리셋으로 수정
			$.each($("#aform").find('select , input') , function(i , v){
				if($(v).prop('disabled') == false){
					if($(v).is('select')){
						$(v).find('option:eq(0)').prop('selected' , true);
					}else if($(v).is('input[type=text]')){
						$(v).val('');
					}else{
						
					}
				}
			});
			$("[name=optionsRadios]:eq(0)").trigger("click");
			fnSearch();
		}
		
		function fnCallBackUserPopup(userNo,userNm){
			//console.log(userNo);
			$( "#btn-close" ).trigger( "click" );
			document.location.href="/admin/user/updateUserFlagMgt.do?user_no="+userNo;

		}

		/**
		 * 기간 리셋
		 * @param changeVal : 날짜 일수
		 */
		function fnRadioReset(changeVal){
			if('option1' == changeVal){
				$('[name=start_dt]').val('');
				$('[name=end_dt]').val('');
				$('[name=start_dt]').prop('disabled' , true);
				$('[name=end_dt]').prop('disabled' , true);
			}else{
				$('[name=start_dt]').prop('disabled' , false);
				$('[name=end_dt]').prop('disabled' , false);
				
				if('option2' == changeVal){
					fnSchPeriod('1');
				}else if('option3' == changeVal){
					fnSchPeriod('7');
				}else if('option4' == changeVal){
					fnSchPeriod('30');
				}else if('option5' == changeVal){
					fnSchPeriod('180');
				}
			}
		}
		
		/**
		 * 기간별 검색 클릭 이벤트
		 * @param num : 날짜 일수
		 */
		function fnSchPeriod(num){
			var today = new Date();
			var termDt = new Date();
	        var fmt = 'YYYY-MM-DD';
	        var endDt	= new Date();

	        var nowMonth = (endDt.getMonth() + 1).toString();
			var nowDate  =  endDt.getDate().toString();	
			nowMonth 	= nowMonth.length==1?0+nowMonth : nowMonth;
			nowDate 	= nowDate.length==1?0+nowDate : nowMonth;
			var nowDate = endDt.getFullYear() + '-' + nowMonth  + '-' +nowDate ;
		
			$('[name=end_dt]').val(nowDate);
			if(num == '1'){
				termDt = moment(today);
				$('[name=start_dt]').val(nowDate);
			}else{
				if(num == '180'){
					termDt = moment(today).add('-6', 'M');
				}else if(num == '30'){
					termDt = moment(today).add('-30', 'd');
				}else if(num == '7'){
					termDt = moment(today).add('-7', 'd');
				}
				$('#start_dt').datetimepicker({format:"YYYY-MM-DD"}).data('DateTimePicker').date(termDt);
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
					<form role="form" id="aform" method="post" action="/admin/user/selectPageListUserMgt.do">
					<input type="hidden" id="user_no" name="user_no" />
					
					<div class="box-header">
						<div class="col-12 form-row">
							<% 
								if("ROLE_PROJ".equals(ssAuthorId) || "ROLE_INSP".equals(ssAuthorId)|| "ROLE_LAST".equals(ssAuthorId)) {
							%>
							<input type="hidden" id="sch_company_id" name="sch_company_id" value="" />
		    				<!-- 1. 사용자 상태로 검색  -->
							<div class="row col-md-6 mb-1 form-group form-inline">
								<label for="sch_project_id" class="control-label col-md-2 px-0">사용자 상태</label>
								<div class="col-md-9 px-0">
									<select id="sch_user_sttus_code" name="sch_user_sttus_code" class="form-control w-100">
										<%=CommboUtil.getComboStr(userSttusComboStr, "CODE", "CODE_NM", param.getString("sch_user_sttus_code") , "사용자상태")%>
									</select>
								</div>
							</div>
							<% 
								}else{
							%>
							
							<!--1. 업체명으로검색 -->
							<div class="row col-md-5 mb-1 form-group form-inline">
								<label for="sch_company_id" class="control-label col-md-2 px-0" style="">업체명</label>
								<div class="col-md-9 px-0">
									<select id="sch_company_id" name="sch_company_id" class="form-control w-100">
										<%=CommboUtil.getComboStr(companyCodeComboStr, "CODE", "CODE_NM", param.getString("sch_company_id"), "A")%>
									</select>
								</div>
							</div>
							<!-- 업체명으로검색// -->
							
							<!-- 2. 사용자 상태로 검색  -->
							<div class="row col-md-6 mb-1 form-group form-inline">
								<label for="sch_project_id" class="control-label col-md-2 px-0">사용자 상태</label>
								<div class="col-md-9 px-0">
									<select id="sch_user_sttus_code" name="sch_user_sttus_code" class="form-control w-100">
										<%=CommboUtil.getComboStr(userSttusComboStr, "CODE", "CODE_NM", param.getString("sch_user_sttus_code") , "사용자상태")%>
									</select>
								</div>
							</div>
							<% 
								}
							%>
							
							<!-- 3. 권한명으로 검색 -->	
							<div class="row col-md-5 mb-1 form-group form-inline">
								<label for="sch_pay_code_m" class="control-label col-md-2 px-0">권한명</label>
								<div class="col-md-9  px-0">
									<select id="sch_user_author" name="sch_user_author" class="form-control w-100" style="font-size:14px">
									<option value="">전체</option>
								</select>
								</div>
							</div>

							<!-- 상세검색 -->
							<div class="row col-md-6 mb-1 form-group form-inline">
								<label for="sch_text" class="control-label col-md-2 px-0">상세검색</label>
								<div class="col-md-9  px-0 form-inline">
									<select id="sch_type" name="sch_type" class="form-control w-25">
										<option value="user_nm" <%if(param.getString("sch_type").equals("user_nm")){%>selected="selected"<%}%>>이름</option>
										<option value="user_id" <%if(param.getString("sch_type").equals("user_id")){%>selected="selected"<%}%>>사용자ID</option>
									</select>
									<input type="text" class="form-control w-75" name="sch_text" title="검색어를 입력하세요." value="<%=param.getString("sch_text")%>" />
								</div>
							</div>


							<!-- 4. 가입일 -->
<%-- 
							<div class="row col-md-6 mb-1 form-group form-inline term-search">
								<label for="sch_text" class="control-label px-0">가입일</label>
								<div class="row px-0">
									
									<div class="col form-inline radioForm">
										<input type="radio" name="optionsRadios" id="optionsRadios1" class="form-check-input ml-n1" value="option1" <c:if test="${'option1' eq param.optionsRadios || empty param.optionsRadios}"> checked ="checked" </c:if> />
										<label class="form-check-label" for="optionsRadios1">전체</label>
												
										<input type="radio" name="optionsRadios" id="optionsRadios2" class="form-check-input ml-n1" value="option2" <c:if test="${'option2' eq param.optionsRadios}"> checked ="checked" </c:if> />
										<label class="form-check-label" for="optionsRadios2">오늘</label>

										<input type="radio" name="optionsRadios" id="optionsRadios3" class="form-check-input ml-n1" value="option3" <c:if test="${'option3' eq param.optionsRadios}"> checked ="checked" </c:if> />
										<label class="form-check-label" for="optionsRadios3">7일</label>

										<input type="radio" name="optionsRadios" id="optionsRadios4" class="form-check-input ml-n1" value="option4" <c:if test="${'option4' eq param.optionsRadios}"> checked ="checked" </c:if> />
										<label class="form-check-label" for="optionsRadios4">30일</label>

										<input type="radio" name="optionsRadios" id="optionsRadios5" class="form-check-input ml-n1" value="option5" <c:if test="${'option5' eq param.optionsRadios}"> checked ="checked" </c:if> />
										<label class="form-check-label" for="optionsRadios5">6개월</label>
									</div>

									<div class="col-6 form-inline term-input">
										<div class="input-group float-left date dateTimePicker" id="start_dt">
											<input type="text" class="form-control" name="start_dt" required="required" <c:if test="${'option1' eq param.optionsRadios || empty param.optionsRadios}"> disabled="disabled" </c:if> value="<%=param.getString("start_dt")%>" />
		                                    <span class="input-group-addon">
		                                    <!-- <span class="glyphicon glyphicon-calendar"></span> -->
		                                    	<i class="fa fa-calendar-alt"></i>
		                                    </span>
										</div>
										<span class="text-center float-left input-spacer">~</span>                                        
										<div class="input-group float-left date dateTimePicker" id="end_dt">
		                                    <input type="text" class="form-control" name="end_dt" required="required" <c:if test="${'option1' eq param.optionsRadios || empty param.optionsRadios}"> disabled="disabled" </c:if> value="<%=param.getString("end_dt")%>"   />
		                                    <span class="input-group-addon">
			                                    <!-- <span class="glyphicon glyphicon-calendar"></span> -->
			                                	<i class="fa fa-calendar-alt"></i>
		                                	</span>
										</div>
									</div>
								
								</div>
 
							</div>
--%>

							<div class="w-100"></div>								

							<div class="row col mb-1 form-group form-inline term-search">
								<label for="sch_text" class="control-label col-md-1 px-0">가입일</label>
								<div class="row px-0">
									
									<div class="col form-inline radioForm">
										<input type="radio" name="optionsRadios" id="optionsRadios1" class="form-check-input ml-n1" value="option1" <c:if test="${'option1' eq param.optionsRadios || empty param.optionsRadios}"> checked ="checked" </c:if> />
										<label class="form-check-label" for="optionsRadios1">전체</label>
												
										<input type="radio" name="optionsRadios" id="optionsRadios2" class="form-check-input ml-n1" value="option2" <c:if test="${'option2' eq param.optionsRadios}"> checked ="checked" </c:if> />
										<label class="form-check-label" for="optionsRadios2">오늘</label>

										<input type="radio" name="optionsRadios" id="optionsRadios3" class="form-check-input ml-n1" value="option3" <c:if test="${'option3' eq param.optionsRadios}"> checked ="checked" </c:if> />
										<label class="form-check-label" for="optionsRadios3">7일</label>

										<input type="radio" name="optionsRadios" id="optionsRadios4" class="form-check-input ml-n1" value="option4" <c:if test="${'option4' eq param.optionsRadios}"> checked ="checked" </c:if> />
										<label class="form-check-label" for="optionsRadios4">30일</label>

										<input type="radio" name="optionsRadios" id="optionsRadios5" class="form-check-input ml-n1" value="option5" <c:if test="${'option5' eq param.optionsRadios}"> checked ="checked" </c:if> />
										<label class="form-check-label" for="optionsRadios5">6개월</label>
									</div>

									<div class="col-auto form-inline term-input">
										<div class="input-group float-left date dateTimePicker" id="start_dt">
											<input type="text" class="form-control" name="start_dt" required="required" <c:if test="${'option1' eq param.optionsRadios || empty param.optionsRadios}"> disabled="disabled" </c:if> value="<%=param.getString("start_dt")%>" />
		                                    <span class="input-group-addon">
		                                    <!-- <span class="glyphicon glyphicon-calendar"></span> -->
		                                    	<i class="fa fa-calendar-alt"></i>
		                                    </span>
										</div>
										<span class="text-center float-left input-spacer">~</span>                                        
										<div class="input-group float-left date dateTimePicker" id="end_dt">
		                                    <input type="text" class="form-control" name="end_dt" required="required" <c:if test="${'option1' eq param.optionsRadios || empty param.optionsRadios}"> disabled="disabled" </c:if> value="<%=param.getString("end_dt")%>"   />
		                                    <span class="input-group-addon">
			                                    <!-- <span class="glyphicon glyphicon-calendar"></span> -->
			                                	<i class="fa fa-calendar-alt"></i>
		                                	</span>
										</div>
									</div>
								
								</div>

							</div>

						</div>
						<hr />
						
						<div class="col text-right">
							<button type="button" onclick="fnReset(); return false;" class="btn btn-reset"><i class="fa fa-history"></i> 초기화</button>
							<button type="button" class="btn btn-search ml-2" onclick="fnSearch(); return false;"><i class="fa fa-search"></i>검색</button>
						</div>

						
						
					</div>


					<!-- box header // -->
					<div class="box-top col-12 row justify-content-between">
						<div class="col-auto text-left"><!-- footer box left -->

							<p class="searchword-result">검색결과 <strong><%=param.getString("totalCount")%></strong>개</p>

						</div>
						<div class="col-auto form-inline text-right"><!-- footer box right -->
<!-- sort combo
							<select class="form-control w-auto"><option>100개</option></select>
// -->
							<% if("ROLE_PROJ".equals(ssAuthorId) || "ROLE_INSP".equals(ssAuthorId) && "20".equals(param.getString("company_id"))) { %>
							<button type="button" class="btn btn-write" data-toggle="modal" data-target="#selectModal"  data-discd="modaluser"><i class="fa fa-plus"></i> 일반회원 검수자등록</button>
							<% } %>
							<button type="button" class="btn btn-write" onclick="fnInsertForm(); return false;"><i class="fa fa-pen"></i> 등록</button>
						</div>

					</div>
					<!-- // box header -->
					
					<!-- list box // -->
					<div class="box-body no-pad-top table-responsive">
						<table class="table table-bordered table-hover">
						<!-- 	<colgroup>
								<col width="10%" />
								<col width="*" />
								<col width="10%" />
								<col width="10%" />
								<col width="10%" />
								<col width="15%" />
								<col width="10%" />
								<col width="10%" />
								<col width="10%" />
							</colgroup> -->
							<thead>
								<tr class="text-center">
									<th>No</th>
									<th>사용자No</th>
									<th>사용자ID</th>
									<th>이름</th>
									<th>권한명</th>
									<th>휴대폰번호</th>
									<th>가입일</th>
									<th>최근접속일</th>
									<th>상태</th>
									<th>등록자</th>
								</tr>
							</thead>
							<tbody>
							<%
								String naviBar = (String)request.getAttribute("navigationBar");
								int dataNo = pageNavigationVo.getCurrDataNo();
								for(int i = 0; i < resultList.size(); i++){
									DataMap dataMap = (DataMap) resultList.get(i);
							%>
								<tr class="text-center" style="cursor:pointer;cursor:hand;" onclick="fnSelect('<%=dataMap.getString("USER_NO")%>')">
									<td><%=dataNo-i %></td>
									<td><%=dataMap.getString("USER_NO") %></td>
									<td class="text-left"><%=dataMap.getString("USER_ID") %></td>
									<td><%=dataMap.getString("USER_NM") %></td>
									<td><%=dataMap.getString("AUTHOR_NM") %></td>
									<td><%=dataMap.getString("CTTPC") %></td>
									<td><%=dataMap.getString("REGIST_YMD") %></td>
									<td><%=dataMap.getString("LOGIN_YMD") %></td>
									<td><%=dataMap.getString("USER_STTUS_NM") %></td>
									<td><%=dataMap.getString("REGISTER_NM") %></td>
								</tr>
							<%  }%>
							<%	if(resultList.size() == 0){	%>
								<tr>
									<td class="text-center" colspan="11" ><spring:message code="msg.data.empty" /></td>
								</tr>
							<%}%>
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
	<!-- //fooer -->
	
</div><!-- ./wrapper -->
<!-- Modal -->
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
        <button type="button" id="btn-close" class="btn btn-list" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
	$('#sch_company_id').on('change', function(e) {
		fnSearch();
	});

</script>

</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
