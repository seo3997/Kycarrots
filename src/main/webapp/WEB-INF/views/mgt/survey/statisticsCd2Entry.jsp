<%@page import="com.whomade.kycarrots.framework.common.util.*"%>
<%@page import="edu.vt.ward.survey.iSurveyEntryForm"%>
<%@page import="com.whomade.kycarrots.mgt.survey.vo.*"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.io.*" %>
<%@page import="java.util.*" %>
<%@page import="java.net.*" %>
<%@page import="java.text.*" %>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>

<jsp:useBean id="param" 				class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="graphparam" 			class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="surveyCds"  			type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="tbSatisfactions"  		type="java.util.List" class="java.util.ArrayList" scope="request"/>




<%
	String surveyId 	= param.getString("surveyId");
	DateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
	Date nowDate = new Date();
	String tempDate = sdFormat.format(nowDate);
    String sSurveyUserTitle="";
    
	if(!param.getString("surveyGroupId").equals("")){
		sSurveyUserTitle=param.getString("SURVEY_GROUP_NM")+" "+param.getString("surveyYear")+"년도"+param.getString("surveyDegree")+"차";
	}
	
	String statisticsCd="1";
	if(!param.getString("statisticsCd").equals("")){
		statisticsCd=param.getString("statisticsCd");
	}

	String sortOrdr="10";
	if(!param.getString("sortOrdr").equals("")){
		sortOrdr=param.getString("sortOrdr");
	}
	
	String layoutCdStr="layoutStr";
    if(sortOrdr.equals("30")){
    	layoutCdStr="layoutStr5";
    }
	
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
		<link href="/common/assets/css/survey.css?version=1.3" rel="stylesheet" />
		<link rel="stylesheet" href="/common/assets/css/font-awesome.min.css" />
  		<link rel="stylesheet" href="/common/assets/css/jquery.printpage.css" type="text/css" media="screen" />

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
    	<script src="/common/assets/js/jquery-1.9.1.min.js"></script>
 		<script type="text/javascript" src="/common/assets/js/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="/common/assets/js/jquery.fileupload.js"></script>
		<script src="/common/assets/js/jquery.iframe-transport.js"></script>

		<script src="/common/assets/js/bootstrap.min.js"></script>
		<script src="/common/assets/js/ace-elements.min.js"></script>
		<script src="/common/assets/js/ace.min.js"></script>

	  	<script src="/common/rMate/LicenseKey/rMateChartH5License.js"></script>
	  	<script src="/common/rMate/rMateChartH5/JS/rMateChartH5.js"></script>
	  	<link rel="stylesheet" href="/common/rMate/rMateChartH5/Assets/Css/rMateChartH5.css"/>


		<!--inline styles if any-->
		<script language="JavaScript" type="text/javascript">
			function fn_save()
			{
				if(!confirm("전송 하시겠습니까?")) return ;
				form.submit();
			}
		</script>		
	</head>

	<body>
	<div id="page-content" class="clearfix">
		<div class="widget-box transparent invoice-box">
			<div class="widget-header widget-header-large">
				<h3 class="grey lighter pull-left position-relative">
					<i class="icon-leaf green"></i>
					Survey Statistics
				</h3>

				<div class="widget-toolbar no-border invoice-info">
					<span class="invoice-info-label">survey:</span>
					<span class="red"><%=sSurveyUserTitle%></span>
					<br>
					<span class="invoice-info-label">Date:</span>
					<span class="blue"><%=tempDate%></span>
					<br>
				</div>

				<div class="widget-toolbar hidden-480">
					<span class="print">print</span>
					<!--  
					<a href="#">
						<i class="icon-print"></i>
					</a>
					-->
				</div>
			</div><!-- widget-header -->

			<div class="widget-body">
				<div class="widget-main padding-24">
					<div class="hr hr8 hr-double hr-dotted"></div>
					<!--  
					<div class="row-fluid">
						<div class="alert alert-warn" style="margin-bottom:0px;height: 30px;">
							<div class="span9"><%=sSurveyUserTitle%></div>
							<div class="span3">	
							</div>
						</div>
					</div>
					<div class="space"></div>
					<div class="row-fluid">
						<div class="span4 graph-text-center alert-success">외래서비스</div>
						<div class="span4 graph-text-center alert-info">입원서비스</div>
						<div class="span4 graph-text-center alert-danger">응급실</div>
					</div>
					-->
					<div class="widget-box">
						<div class="widget-header header-color-dark">
							<h5 style="padding: 5px 0 0 0;"><%=sSurveyUserTitle%></h5>
							<div class="widget-toolbar no-border">
							</div>
							<div class="widget-toolbar" style="padding: 10px 5px 0 0;">
					            <select id="statistics_cd"	name="statistics_cd">
									<option value="1" <%=StringUtil.selectSelected(statisticsCd,"1") %>>응답자특성</option>
									<option value="2" <%=StringUtil.selectSelected(statisticsCd,"2") %>>요약</option>
					          		<%
					          			int iSurveyCd=3;
					          			for(int i = 0; i < surveyCds.size(); i++){
					          				DataMap surveyCdMap = (DataMap) surveyCds.get(i);
					          		%>
									<option value="<%=iSurveyCd%>" <%=StringUtil.selectSelected(statisticsCd,""+iSurveyCd) %>><%=surveyCdMap.getString("SORT_ORDR_NM")%></option>
					          		<%
					          				iSurveyCd++;
					          			}
					          		%>	

					            </select>
							</div>
						</div>
						
						<%
						int spansize=3;
						String itemColor="";
						for(int i = 0; i < tbSatisfactions.size(); i++){
							TbSatisfaction aTbSatisfaction = (TbSatisfaction) tbSatisfactions.get(i);
						%>
						<div class="space"></div>
						<div class="widget-box">
						<div class="widget-header header-color-orange">
							<h5><%=aTbSatisfaction.getSECTION_TITLE()%></h5>
							<div class="widget-toolbar no-border">
							</div>
							<div class="widget-toolbar">
								<a href="#" data-action="collapse">
									<i class="1 bigger-125 icon-chevron-up"></i>
								</a>
							</div>
						</div>
						<div class="widget-body">
							<div class="widget-body-inner" style="display: block;">
								<div class="widget-main">
									<% 
									        ArrayList<TbSatisfactionItem> tbSatisfactionItem =aTbSatisfaction.getTbSatisfactionItem();
									        int itemSize=tbSatisfactionItem.size();
									        if (itemSize>4) itemSize=4;
									    	if(tbSatisfactionItem.size()==1) spansize=12;
									    	else if(tbSatisfactionItem.size()==2) spansize=6;
									    	else if(tbSatisfactionItem.size()==3) spansize=4;
									    	else if(tbSatisfactionItem.size()==4) spansize=3;
									    	else if(tbSatisfactionItem.size()>=5) spansize=3;
									%>
									<div class="row-fluid">
									    <% 
									    	for(int j=0;j<itemSize;j++){
									    		TbSatisfactionItem aTbSatisfactionItem = tbSatisfactionItem.get(j);
									    		if(j==0)itemColor="alert-success";
									    		else if(j==1)itemColor="alert-info";
									    		else if(j==2)itemColor="alert-error";
									    		else itemColor="alert-success";
										%>
										<div class="span<%=spansize%> graph-text-center <%=itemColor%>"><%=aTbSatisfactionItem.getQUESTION_LABEL()%></div>
										<%
									    	}
										%>
									</div>
									<div class="space"></div>
									<div class="row-fluid">
									    <% 
									    	for(int j=0;j<itemSize;j++){
									    		TbSatisfactionItem aTbSatisfactionItem = tbSatisfactionItem.get(j); 
									    %>
										<div class="span<%=spansize%>">
										<div id="chartHolderDimAvg_<%=aTbSatisfactionItem.getSECTION_ID()%>_<%=aTbSatisfactionItem.getQUESTION_ID()%>" class="graph-faddind-left" style="height:200px;"></div>
										</div>
										<%
									    	}
										%>
									</div>
									
									<div class="space"></div>
									<div class="row-fluid">
									    <% 
									    	for(int j=0;j<itemSize;j++){
									    		TbSatisfactionItem aTbSatisfactionItem = tbSatisfactionItem.get(j); 
									    %>
										<div class="span<%=spansize%>">
										<div id="chartHolderDim_<%=aTbSatisfactionItem.getSECTION_ID()%>_<%=aTbSatisfactionItem.getQUESTION_ID()%>" class="graph-faddind-left" style="height:350px;"></div>
										</div>
										<%
									    	}
										%>
									</div>
									
									<% if(tbSatisfactionItem.size()>4) {%>
									<div class="space"></div>
									<div class="row-fluid">
									    <% 
									    	for(int j=itemSize;j<tbSatisfactionItem.size();j++){
									    		TbSatisfactionItem aTbSatisfactionItem = tbSatisfactionItem.get(j);
									    		if(j==0)itemColor="alert-success";
									    		else if(j==1)itemColor="alert-info";
									    		else if(j==2)itemColor="alert-error";
									    		else itemColor="alert-success";
										%>
										<div class="span<%=spansize%> graph-text-center <%=itemColor%>"><%=aTbSatisfactionItem.getQUESTION_LABEL()%></div>
										<%
									    	}
										%>
									</div>
									<div class="space"></div>
									<div class="row-fluid">
									    <% 
									    	for(int j=itemSize;j<tbSatisfactionItem.size();j++){
									    		TbSatisfactionItem aTbSatisfactionItem = tbSatisfactionItem.get(j); 
									    %>
										<div class="span<%=spansize%>">
										<div id="chartHolderDimAvg_<%=aTbSatisfactionItem.getSECTION_ID()%>_<%=aTbSatisfactionItem.getQUESTION_ID()%>" class="graph-faddind-left" style="height:200px;"></div>
										</div>
										<%
									    	}
										%>
									</div>

									<div class="space"></div>
									<div class="row-fluid">
									    <% 
									    	for(int j=itemSize;j<tbSatisfactionItem.size();j++){
									    		TbSatisfactionItem aTbSatisfactionItem = tbSatisfactionItem.get(j); 
									    %>
										<div class="span<%=spansize%>">
										<div id="chartHolderDim_<%=aTbSatisfactionItem.getSECTION_ID()%>_<%=aTbSatisfactionItem.getQUESTION_ID()%>" class="graph-faddind-left" style="height:350px;"></div>
										</div>
										<%
									    	}
										%>
									</div>
									<% }%>
	
								</div><!--widget-main  -->
							</div> <!-- widget-body-inner -->
						</div> <!-- widget-body -->
					</div><!--widget-box  -->
						
						<%
							}
						
						%>
							


					<div class="space"></div>
					<div class="hr hr8 hr-double hr-dotted"></div>
					<div class="space-6"></div>
					<div class="form-actions-right">
					<button class="btn" type="close" onClick="self.close();">
					<i class="icon-undo bigger-110"></i>
					Close
					</button>
					</div>
					<div class="form-actions">
							Thank you for choosing whomade products.
							We believe you will be satisfied by our services.
					</div>
				
				</div><!-- widget-main -->
			</div><!-- widget-body -->
		
		</div><!-- widget-box -->
	</div><!--page-content-->

	<!--basic scripts-->

	<script src="/common/assets/js/jquery.printpage.js"></script>
	<script>
	     $(document).ready(function() {
	    	 $('span.print').printPage();
	     });

	     $("#statistics_cd").on("change",function(e){
	 		if ($(this).val() =="1")	{
	 			location.href="/mgt/survey/statisticsEntry.do?userNo=<%=param.getString("userNo")%>&surveyGroupId=<%=param.getString("surveyGroupId")%>&surveyYear=<%=param.getString("surveyYear")%>&surveyDegree=<%=param.getString("surveyDegree")%>&statisticsCd=1";
	 		}else if ($(this).val() =="2")	{
	 			location.href="/mgt/survey/statisticsCd1Entry.do?userNo=<%=param.getString("userNo")%>&surveyGroupId=<%=param.getString("surveyGroupId")%>&surveyYear=<%=param.getString("surveyYear")%>&surveyDegree=<%=param.getString("surveyDegree")%>&statisticsCd=2";
	 		}else if ($(this).val() =="3")	{
	 			location.href="/mgt/survey/statisticsCd2Entry.do?userNo=<%=param.getString("userNo")%>&surveyGroupId=<%=param.getString("surveyGroupId")%>&surveyYear=<%=param.getString("surveyYear")%>&surveyDegree=<%=param.getString("surveyDegree")%>&statisticsCd=3";
	 		}else if ($(this).val() =="4")	{
	 			location.href="/mgt/survey/statisticsCd2Entry.do?userNo=<%=param.getString("userNo")%>&surveyGroupId=<%=param.getString("surveyGroupId")%>&surveyYear=<%=param.getString("surveyYear")%>&surveyDegree=<%=param.getString("surveyDegree")%>&statisticsCd=4";
	 		}else if ($(this).val() =="5")	{
	 			location.href="/mgt/survey/statisticsCd2Entry.do?userNo=<%=param.getString("userNo")%>&surveyGroupId=<%=param.getString("surveyGroupId")%>&surveyYear=<%=param.getString("surveyYear")%>&surveyDegree=<%=param.getString("surveyDegree")%>&statisticsCd=5";
	 		}
	 	});

	     
	     
	</script>
   	<script>
		$(function() {
		});
   	</script>
	<script type="text/javascript">
		 // -----------------------차트 설정 시작-----------------------
		  	 
		 // rMateChart 를 생성합니다.
		 // 파라메터 (순서대로)
		 //  1. 차트의 id ( 임의로 지정하십시오. )
		 //  2. 차트가 위치할 div 의 id (즉, 차트의 부모 div 의 id 입니다.)
		 //  3. 차트 생성 시 필요한 환경 변수들의 묶음인 chartVars
		 //  4. 차트의 가로 사이즈 (생략 가능, 생략 시 100%)
		 //  5. 차트의 세로 사이즈 (생략 가능, 생략 시 100%)
		 // 성별
		 
		// 스트링 형식으로 레이아웃 정의.
		var layoutStr =
		 '<rMateChart backgroundColor="#FFFFFF" borderStyle="none">'
		       +'<Options>'
		          +'<Caption text=""/>'
		           +'<SubCaption text="" textAlign="right" />'
		         +'<Legend useVisibleCheck="true" defaultMouseOverAction="false" />'
		       +'</Options>'
		     +'<Combination2DChart  showDataTips="true" columnWidthRatio="0.4">'
		         +'<horizontalAxis>'
		               +'<CategoryAxis categoryField="YEARDEGREE"/>'
		          +'</horizontalAxis>'
		          +'<verticalAxis>'
		             +'<LinearAxis maximum="100" interval="10"/>'
		          +'</verticalAxis>'
		            +'<series>'
		               /*
		              type 속성을 stacked로 변경
		                type속성으로는
		               clustered : 일반적인 다중데이터(차트의 멀티시리즈)방식으로 데이터를 표현합니다.(Default)
		              stacked : 데이터를 위에 쌓아 올린 방식으로 표현 합니다.
		                overlaid : 수치 데이터 값을 겹쳐서 표현 합니다. 주로 목표 위치와 현재 위치를 나타낼 때 많이 쓰입니다.
		                100% : 차트의 수치 데이터를 퍼센티지로 계산 후 값을 퍼센티지로 나타냅니다.
		               */
		              +'<Column2DSet type="stacked" showTotalLabel="true" totalLabelJsFunction="totalFunc">'
		                    +'<series>'
		               /*  Column2D Stacked 를 생성시에는 Column2DSeries를 최소 2개 정의합니다 */
		                     +'<Column2DSeries labelPosition="inside" yField="GRADE_04_VAL" displayName="강한긍정"  color="#ffffff" strokeJsFunction="strokeFunction">'
		                           +'<showDataEffect>'
		                               +'<SeriesInterpolate/>'
		                           +'</showDataEffect>'
		                      +'</Column2DSeries>'
		                      +'<Column2DSeries labelPosition="inside" yField="GRADE_03_VAL" displayName="긍정"  color="#ffffff" strokeJsFunction="strokeFunction">'
		                           +'<showDataEffect>'
		                               +'<SeriesInterpolate/>'
		                           +'</showDataEffect>'
		                      +'</Column2DSeries>'
		                      +'<Column2DSeries labelPosition="inside" yField="GRADE_02_VAL" displayName="부정"  color="#ffffff" strokeJsFunction="strokeFunction">'
	                         +'<showDataEffect>'
	                            +'<SeriesInterpolate/>'
	                         +'</showDataEffect>'
			                    +'</Column2DSeries>'
			                    +'<Column2DSeries labelPosition="inside" yField="GRADE_01_VAL" displayName="강한부정"  color="#ffffff" strokeJsFunction="strokeFunction">'
			                    +'<showDataEffect>'
			                       +'<SeriesInterpolate/>'
			                   +'</showDataEffect>'
			                  +'</Column2DSeries>'
		                  +'</series>'
		              +'</Column2DSet>'
		         +'</series>'
		      +'</Combination2DChart >'
		   +'</rMateChart>';
			var layoutStr5 =
				 '<rMateChart backgroundColor="#FFFFFF" borderStyle="none">'
				       +'<Options>'
				          +'<Caption text=""/>'
				           +'<SubCaption text="" textAlign="right" />'
				         +'<Legend useVisibleCheck="true" defaultMouseOverAction="false" />'
				       +'</Options>'
				     +'<Combination2DChart  showDataTips="true" columnWidthRatio="0.4">'
				         +'<horizontalAxis>'
				               +'<CategoryAxis categoryField="YEARDEGREE"/>'
				          +'</horizontalAxis>'
				          +'<verticalAxis>'
				             +'<LinearAxis maximum="100" interval="10"/>'
				          +'</verticalAxis>'
				            +'<series>'
				               /*
				              type 속성을 stacked로 변경
				                type속성으로는
				               clustered : 일반적인 다중데이터(차트의 멀티시리즈)방식으로 데이터를 표현합니다.(Default)
				              stacked : 데이터를 위에 쌓아 올린 방식으로 표현 합니다.
				                overlaid : 수치 데이터 값을 겹쳐서 표현 합니다. 주로 목표 위치와 현재 위치를 나타낼 때 많이 쓰입니다.
				                100% : 차트의 수치 데이터를 퍼센티지로 계산 후 값을 퍼센티지로 나타냅니다.
				               */
				              +'<Column2DSet type="stacked" showTotalLabel="true" totalLabelJsFunction="totalFunc">'
				                    +'<series>'
				               /*  Column2D Stacked 를 생성시에는 Column2DSeries를 최소 2개 정의합니다 */
				                     +'<Column2DSeries labelPosition="inside" yField="GRADE_05_VAL" displayName="강한긍정"  color="#ffffff" strokeJsFunction="strokeFunction">'
				                           +'<showDataEffect>'
				                               +'<SeriesInterpolate/>'
				                           +'</showDataEffect>'
				                      +'</Column2DSeries>'
					                     +'<Column2DSeries labelPosition="inside" yField="GRADE_04_VAL" displayName="긍정"  color="#ffffff" strokeJsFunction="strokeFunction">'
				                           +'<showDataEffect>'
				                               +'<SeriesInterpolate/>'
				                           +'</showDataEffect>'
				                      +'</Column2DSeries>'
				                      +'<Column2DSeries labelPosition="inside" yField="GRADE_03_VAL" displayName="보통"  color="#ffffff" strokeJsFunction="strokeFunction">'
				                           +'<showDataEffect>'
				                               +'<SeriesInterpolate/>'
				                           +'</showDataEffect>'
				                      +'</Column2DSeries>'
				                      +'<Column2DSeries labelPosition="inside" yField="GRADE_02_VAL" displayName="부정"  color="#ffffff" strokeJsFunction="strokeFunction">'
			                         +'<showDataEffect>'
			                            +'<SeriesInterpolate/>'
			                         +'</showDataEffect>'
					                    +'</Column2DSeries>'
					                    +'<Column2DSeries labelPosition="inside" yField="GRADE_01_VAL" displayName="강한부정"  color="#ffffff" strokeJsFunction="strokeFunction">'
					                    +'<showDataEffect>'
					                       +'<SeriesInterpolate/>'
					                   +'</showDataEffect>'
					                  +'</Column2DSeries>'
				                  +'</series>'
				              +'</Column2DSet>'
				         +'</series>'
				      +'</Combination2DChart >'
				   +'</rMateChart>';

		// 스트링 형식으로 레이아웃 정의.
		   var layoutAvgStr =
		            '<rMateChart  backgroundColor="#ffffff" borderStyle="none">'
		                 +'<Options>'
		                     +'<Caption text="평균"/>'
		                     +'<SubCaption text="" />'
		                      +'<Legend useVisibleCheck="true"/>'
		                  +'</Options>'
		                +'<Line2DChart showDataTips="true" dataTipDisplayMode="axis" paddingTop="0">'
		                    +'<horizontalAxis>'
		                          +'<CategoryAxis categoryField="YEARDEGREE" padding="0.2"/>'
		                       +'</horizontalAxis>'
		                     +'<verticalAxis>'
		                        +'<LinearAxis maximum="100"/>'
		                       +'</verticalAxis>'
		                       +'<series>'
		                          /*
		                         itemRenderer는 Tip이 보여지는 영역차트 부분에 ItemRenderer에서 제공하는 모양을 그려줍니다
		                         이 예제에서는 Diamond입니다
		                         사용할 수 있는 도형을 모두 표현한 예제는 Chart Samples 의 범례 예제를 참고하십시오.
		                         */
		                         +'<Line2DSeries yField="GRADE_AVG_VAL" fill="#ffffff" radius="5" displayName="평균" itemRenderer="RectangleItemRenderer">'
		                              +'<showDataEffect>'
		                                  +'<SeriesInterpolate/>'
		                              +'</showDataEffect>'
		                         +'</Line2DSeries>'
		                       +'</series>'
		                     +'<annotationElements>'
		                          +'<CrossRangeZoomer zoomType="horizontal" fontSize="11" color="#FFFFFF" verticalLabelPlacement="bottom" horizontalLabelPlacement="left" enableZooming="false" enableCrossHair="false">'
		                           +'</CrossRangeZoomer>'
		                       +'</annotationElements>'
		                 +'</Line2DChart>'
		            +'</rMateChart>';		   
		   
		// 차트 데이터
		/*
		var chartData =
	    [{"YEARDEGREE":"19년도2차", "GRADE_04_VAL":12, "GRADE_03_VAL":11, "GRADE_02_VAL":12, "GRADE_01_VAL":12, "GRADE_AVG_VAL":74.6},
	     {"YEARDEGREE":"19년도1차", "GRADE_04_VAL":14, "GRADE_03_VAL":19, "GRADE_02_VAL":17, "GRADE_01_VAL":12, "GRADE_AVG_VAL":84.3},
	     {"YEARDEGREE":"18년도2차", "GRADE_04_VAL":23, "GRADE_03_VAL":25, "GRADE_02_VAL":20, "GRADE_01_VAL":12, "GRADE_AVG_VAL":86.0}
	     ];
		*/
		 // rMateChartH5.calls 함수를 이용하여 차트의 준비가 끝나면 실행할 함수를 등록합니다.
		 //
		 // argument 1 - rMateChartH5.create시 설정한 차트 객체 아이디 값
		 // argument 2 - 차트준비가 완료되면 실행할 함수 명(key)과 설정될 전달인자 값(value)
		 // 
		 // 아래 내용은 
		 // 1. 차트 준비가 완료되면 첫 전달인자 값을 가진 차트 객체에 접근하여
		 // 2. 두 번째 전달인자 값의 key 명으로 정의된 함수에 value값을 전달인자로 설정하여 실행합니다.
	 	//환자경험종합평가
	 	/*
		 rMateChartH5.create("chartDim1", "chartHolderDim1", "", "100%", "100%");
		 rMateChartH5.calls("chartDim1", {
		  "setLayout" : layoutStr,
		  "setData"   : chartData
		 });

		 rMateChartH5.create("chartDimAvg1", "chartHolderDimAvg1", "", "100%", "100%");
		 rMateChartH5.calls("chartDimAvg1", {
		  "setLayout" : layoutAvgStr,
		  "setData"   : chartData
		 });
		 */
		  <% 
  			ArrayList<TbSurveyUserEntryResult> aSatisfactionItemVals = new ArrayList<TbSurveyUserEntryResult>();			
		    TbSurveyUserEntryResult aTbSurveyUserEntryResult =null;
		    JSONArray jsonArray =new JSONArray();
			JSONObject RateJSON = null;
		  
			for(int i = 0; i < tbSatisfactions.size(); i++){
				TbSatisfaction aTbSatisfaction = (TbSatisfaction) tbSatisfactions.get(i);

		  		ArrayList<TbSatisfactionItem> tbSatisfactionItem =aTbSatisfaction.getTbSatisfactionItem();
	    
		    	for(int j=0;j<tbSatisfactionItem.size();j++){
		    		TbSatisfactionItem aTbSatisfactionItem = tbSatisfactionItem.get(j);
		  			aSatisfactionItemVals = aTbSatisfactionItem.getTbSurveyUserEntryResult();
		  			jsonArray =new JSONArray();
		  			for(int k=0;k<aSatisfactionItemVals.size();k++){
		  				aTbSurveyUserEntryResult=new TbSurveyUserEntryResult();
		  				aTbSurveyUserEntryResult = aSatisfactionItemVals.get(k);
			  			RateJSON  =new JSONObject();
			  			RateJSON.put("YEARDEGREE", aTbSurveyUserEntryResult.getSURVEY_YEAR()+"년도"+aTbSurveyUserEntryResult.getSURVEY_DEGREE()+"차");
			  			RateJSON.put("GRADE_05_VAL", aTbSurveyUserEntryResult.getGRADE_05_VAL());
			  			RateJSON.put("GRADE_04_VAL", aTbSurveyUserEntryResult.getGRADE_04_VAL());
			  			RateJSON.put("GRADE_03_VAL", aTbSurveyUserEntryResult.getGRADE_03_VAL());
			  			RateJSON.put("GRADE_02_VAL", aTbSurveyUserEntryResult.getGRADE_02_VAL());
			  			RateJSON.put("GRADE_01_VAL", aTbSurveyUserEntryResult.getGRADE_01_VAL());
			  			RateJSON.put("GRADE_AVG_VAL", aTbSurveyUserEntryResult.getGRADE_AVG_VAL());
			  			jsonArray.add(RateJSON);
		  			}
		  			
		 %>
			var chartData_<%=aTbSatisfactionItem.getSECTION_ID()%>_<%=aTbSatisfactionItem.getQUESTION_ID()%> =<%=jsonArray.toString()%>;
		 	 rMateChartH5.create("chartDimAvg_<%=aTbSatisfactionItem.getSECTION_ID()%>_<%=aTbSatisfactionItem.getQUESTION_ID()%>", "chartHolderDimAvg_<%=aTbSatisfactionItem.getSECTION_ID()%>_<%=aTbSatisfactionItem.getQUESTION_ID()%>", "", "100%", "100%");
			 rMateChartH5.calls("chartDimAvg_<%=aTbSatisfactionItem.getSECTION_ID()%>_<%=aTbSatisfactionItem.getQUESTION_ID()%>", {
			  "setLayout" : layoutAvgStr,
			  "setData"   : chartData_<%=aTbSatisfactionItem.getSECTION_ID()%>_<%=aTbSatisfactionItem.getQUESTION_ID()%>
			 });
			 
		 	 rMateChartH5.create("chartDim_<%=aTbSatisfactionItem.getSECTION_ID()%>_<%=aTbSatisfactionItem.getQUESTION_ID()%>", "chartHolderDim_<%=aTbSatisfactionItem.getSECTION_ID()%>_<%=aTbSatisfactionItem.getQUESTION_ID()%>", "", "100%", "100%");
			 rMateChartH5.calls("chartDim_<%=aTbSatisfactionItem.getSECTION_ID()%>_<%=aTbSatisfactionItem.getQUESTION_ID()%>", {
			  "setLayout" : <%=layoutCdStr%>,
			  "setData"   : chartData_<%=aTbSatisfactionItem.getSECTION_ID()%>_<%=aTbSatisfactionItem.getQUESTION_ID()%>
			 });

		 <%
		    	}
		    }
		 %>

		 
		   // 스택 수치 합 사용자 정의 함수
		   function totalFunc(index, data, value){
		     if(index == 6)
		         return insertComma(value);
		     return "";
		   }
		    
		   //숫자에 천단위 콤마 찍어 반환하는 함수.
		   function insertComma(n) {
		       var reg = /(^[+-]?\d+)(\d{3})/; // 정규식
		     n += "";
		       while (reg.test(n))
		    n = n.replace(reg, '$1' + "," + '$2');
		     return n;
		   }
		    
		   function strokeFunction(id, index, data, values){
		    if(values[0] == "Jul")
		         return {
		               color : "#000",
		            weight : 2
		         };
		   }
		 		 
		 
		 /**
		  * rMateChartH5 3.0이후 버전에서 제공하고 있는 테마기능을 사용하시려면 아래 내용을 설정하여 주십시오.
		  * 테마 기능을 사용하지 않으시려면 아래 내용은 삭제 혹은 주석처리 하셔도 됩니다.
		  *
		  * -- rMateChartH5.themes에 등록되어있는 테마 목록 --
		  * - simple
		  * - cyber
		  * - modern
		  * - lovely
		  * - pastel
		  * -------------------------------------------------
		  *
		  * rMateChartH5.themes 변수는 theme.js에서 정의하고 있습니다.
		  */
		 rMateChartH5.registerTheme(rMateChartH5.themes);
		  
		 /**
		  * 샘플 내의 테마 버튼 클릭 시 호출되는 함수입니다.
		  * 접근하는 차트 객체의 테마를 변경합니다.
		  * 파라메터로 넘어오는 값
		  * - simple
		  * - cyber
		  * - modern
		  * - lovely
		  * - pastel
		  * - default
		  *
		  * default : 테마를 적용하기 전 기본 형태를 출력합니다.
		  */
		 function rMateChartH5ChangeTheme(theme){
		     document.getElementById("chartSex1").setTheme(theme);
		 }
		 
		 // -----------------------차트 설정 끝 -----------------------	  	</script>
	</script>		


	</body>
</html>
