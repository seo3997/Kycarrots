<%@page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>

<jsp:useBean id="resultList"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="pageNavigationVo" class="com.whomade.kycarrots.framework.common.page.vo.pageNavigationVo" scope="request"/>
<jsp:useBean id="navigationBar" class="java.lang.String" scope="request"/>




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
			// 검색 조건 엔터시 이벤트
			$('[name=sch_text]').on({
				'keydown' : function(e){
					if(e.which == 13){
						e.preventDefault();
					}
				},
				'keyup' : function(e){
					if(e.which == 13){
						fnSearch();
					}
				}
			});
		});
		
		// 페이지 이동
		function fnGoPage(currentPage){
			$('#currentPage').val(currentPage);
			$('#aform').attr({ action : '/mgt/mfileboard/selectPageListBoard.do', method : 'get' }).submit();
		}
		
		// 등록폼 이동
		function fnInsertForm(){
			$('#aform').attr({ action : '/mgt/mfileboard/insertFormBoard.do', method : 'post' }).submit();
		}

		// 상세조회
		function fnSelect(bbs_seq){
			$('[name=bbs_seq]').val(bbs_seq);
			$('#aform').attr({ action : '/mgt/mfileboard/selectBoard.do', method : 'get' }).submit();
		}
		
		// 검색
		function fnSearch(){
			$('#currentPage').val('1');
			$('#aform').attr({ action : '/mgt/mfileboard/selectPageListBoard.do', method : 'get' }).submit();
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
	<jsp:useBean id="top_menu_id" class="java.lang.String" scope="request"/>

	<!-- Content Wrapper. Contains page content -->
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
					<form role="form" id="aform" method="post" action="/mgt/mfileboard/selectPageListBoard.do">
					<input type="hidden"  id="sch_bbs_se_code" name="sch_bbs_se_code" value="30"/>
					<input type="hidden" 	name="bbs_seq" />
					<input type="hidden" 	name="bbs_se_code_l"	id="bbs_se_code_l" 			 	value="R010170"  />
					<input type="hidden"  	name="bbs_se_code_m"	id="bbs_se_code_m"  			value="<%=param.getString("bbs_se_code_m")%>" />

	          		<div class="box">
			            <div class="box-header">
			              <h3 class="box-title">창고자료 목록</h3>
			              <div class="box-tools">
			                	<div class="input-group input-group-sm" style="width: 150px;">
										<input type="hidden"  id="search_list_st" name="sch_type" 										value="<%=param.getString("sch_type")%>" />
										<input type="text" class="form-control" name="sch_text" title="검색어를 입력하세요." 	value="<%=param.getString("sch_text")%>" />
										<span class="input-group-btn">
											<button type="button" class="btn btn-sm btn-info" onclick="fnSearch(); return false;"><i class="fa fa-search"></i></button>
										</span>
			                  </div>
			                </div>
			            </div> <!-- /.box-header -->
			            <div class="box-body table-responsive no-padding">
			              <table class="table table-hover">
			                <tr>
			                  <th>No</th>
			                  <th>자료제목</th>
			                  <th>작성자</th>
			                  <th>작성일</th>
			                </tr>
							<%
							int dataNo = pageNavigationVo.getCurrDataNo();
							for(int i = 0; i < resultList.size(); i++){
								DataMap dataMap = (DataMap) resultList.get(i);
							%>
			                <tr style="cursor:pointer;cursor:hand;" onclick="fnSelect('<%=dataMap.getString("BBS_SEQ")%>'); return false;">
			                  <td><%=dataNo-i%></td>
			                  <td><%=dataMap.getString("SJ") %></td>
			                  <td><%=dataMap.getString("REGISTER_NM") %></td>
			                  <td><%=dataMap.getString("REGIST_DT") %></td>
			                </tr>
							<%}%>
							<%if(resultList.size() == 0){%>
								<tr>
									<td class="text-center" colspan="5" ><spring:message code="msg.data.empty" /></td>
								</tr>
							<%}%>

			              </table>
			            </div>
			            <div class="box-footer clearfix">
			              <%=navigationBar %>
						  <div class="pull-right">
							<a href="javascript:fnInsertForm()" class="btn btn-block btn-primary">창고자료 추가</a>
						  </div>
			            </div>
	          		</div><!--  /.box -->
					</form>
       			</div><!--  /.col-xs-12 -->
			
			</div><!-- ./row -->
		</section><!-- /.content -->
	</div><!-- /.content-wrapper -->
	
	<!-- footer -->
	<%@ include file="/common/inc/footer.jspf" %>
	<!-- //fooer -->
	
</div><!-- ./wrapper -->


</body>
</html>
<%@ include file="/common/inc/msg.jspf" %>
