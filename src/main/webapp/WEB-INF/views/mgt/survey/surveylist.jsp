<%@page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@page import="com.whomade.kycarrots.mgt.survey.vo.TbSurvey"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>

<jsp:useBean id="resultList"  type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="param" class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="pageNavigationVo" class="com.whomade.kycarrots.framework.common.page.vo.pageNavigationVo" scope="request"/>
<jsp:useBean id="navigationBar" class="java.lang.String" scope="request"/>

<%
	String sOrgSurveyId=param.getString("OrgSurveyId");
%>

<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta charset="utf-8" />
		<title>MySurvey</title>

		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />

		<!--basic styles-->

		<link href="/common/assets/css/bootstrap.min.css" rel="stylesheet" />
		<link href="/common/assets/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="/common/assets/css/font-awesome.min.css" />

		<!--[if IE 7]>
		  <link rel="stylesheet" href="/common/assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!--page specific plugin styles-->

		<!--fonts-->

		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />

		<!--ace styles-->

		<link rel="stylesheet" href="/common/assets/css/ace.min.css" />
		<link rel="stylesheet" href="/common/assets/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="/common/assets/css/ace-skins.min.css" />

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="/common/assets/css/ace-ie.min.css" />
		<![endif]-->

		<!--inline styles if any-->
		<script language="JavaScript" type="text/javascript">
		<!--
			function new_window(freshurl) {
				  SmallWin = window.open(freshurl, 'SurvevPreview','scrollbars=yes,resizable=yes,toolbar=no,height=600,width=800');
				  SmallWin.opener.name = "subSurvevPreview";
			}

			function fc_btnSelect(pChgSurveyId) {
				if(!confirm("설문을 가져오기를 하시겠습니까?")) return ;
				opener.window.close();
				location.href="/mgt/survey/surveylist_ok.do?OrgSurveyId=<%=sOrgSurveyId%>&ChgSurveyId="+pChgSurveyId;	
			}
		
		//-->
		
		</script>
	</head>

	<body>

	<div class="container-fluid" id="main-container">
		<div class="row-fluid">
			<!--PAGE CONTENT BEGINS HERE-->
			<!--  
			<div class="span12">
				<div class="widget-box ">
					<div class="widget-header">
						<h4 class="lighter smaller">
							<i class="icon-comment blue"></i>
							Survey List
						</h4>
					</div>
				</div>
			</div>
			-->
<div class="row-fluid">
							<div class="span12">
								<div class="widget-box">
									<div class="widget-header header-color-blue">
										<h5 class="bigger lighter">
											<i class="icon-table"></i>
											Select Suvey
										</h5>
									</div>

									<div class="widget-body">
										<div class="widget-main no-padding">
											<table class="table table-striped table-bordered table-hover">
												<thead>
													<tr>
														<th>
															<i class="icon-user"></i>
															Servey Id
														</th>
														<th class="hidden-480">Apply</th>
													</tr>
												</thead>

												<tbody>

							<%
								int dataNo = pageNavigationVo.getCurrDataNo();
								String sEntryCnt="";
								for(int i = 0; i < resultList.size(); i++){
									DataMap dataMap = (DataMap) resultList.get(i);
									TbSurvey aTbSurvey = new TbSurvey();
									aTbSurvey.setSURVEY_ID(dataMap.getString("SURVEY_ID"));
									aTbSurvey.setSURVEY_TITLE(dataMap.getString("SURVEY_TITLE"));
									aTbSurvey.setOPENED(dataMap.getString("OPENED"));
									aTbSurvey.setCLOSED(dataMap.getString("CLOSED"));
							%>
													<tr>
														<td class="">
														<a href="JavaScript:new_window('/mgt/survey/entry.do?surveyId=<%=aTbSurvey.getSURVEY_ID()%>&surveyMode=0')"><%=aTbSurvey.getSURVEY_TITLE()%></a>
														</td>
														<td class="hidden-480">
															<button type="button" class="btn btn-mini btn-primary" onClick="fc_btnSelect('<%=aTbSurvey.getSURVEY_ID()%>');">Select</button>
														</td>
													</tr>
							<%
								}
							%>

												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div><!--/span-->
						</div>
</div>			
			
			<!--PAGE CONTENT ENDS HERE-->
		</div><!--/row-->
	</div><!--/.fluid-container#main-container-->

	<!--basic scripts-->

	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript">
		window.jQuery || document.write("<script src='/common/assets/js/jquery-1.9.1.min.js'>"+"<"+"/script>");
	</script>
	<script src="/common/assets/js/bootstrap.min.js"></script>

	<!--page specific plugin scripts-->

	<!--ace scripts-->

	<script src="/common/assets/js/ace-elements.min.js"></script>
	<script src="/common/assets/js/ace.min.js"></script>

	<!--inline scripts related to this page-->
	</body>
</html>
