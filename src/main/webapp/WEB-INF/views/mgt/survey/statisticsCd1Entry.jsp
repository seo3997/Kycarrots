<%@page import="com.whomade.kycarrots.framework.common.util.*"%>
<%@page import="edu.vt.ward.survey.iSurveyEntryForm"%>
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
<jsp:useBean id="dim3"   				type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="surveyYear16s"  		type="java.util.List" class="java.util.ArrayList" scope="request"/>


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
	
	String multiStrDisName1="";
	String multiStrDisName2="";
	String multiStrDisName3="";
	String multiStrDisName4="";
	
	for(int i=0; i<surveyYear16s.size();i++){
		if(i==0) multiStrDisName1 = (String)surveyYear16s.get(i);
		else if(i==1) multiStrDisName2 = (String)surveyYear16s.get(i);
		else if(i==2) multiStrDisName3 = (String)surveyYear16s.get(i);
		else if(i==3) multiStrDisName4 = (String)surveyYear16s.get(i);
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
		 rMateChartH5.create("chartDim1", "chartHolderDim1", "", "100%", "100%");
		 rMateChartH5.create("chartDim2", "chartHolderDim2", "", "100%", "100%");
		 rMateChartH5.create("chartDim3", "chartHolderDim3", "", "100%", "100%");
		 rMateChartH5.create("chartDim4", "chartHolderDim4", "", "100%", "100%");
		 
		 rMateChartH5.create("chartDim5", "chartHolderSubject1", "", "100%", "100%");
		 rMateChartH5.create("chartDim6", "chartHolderSubject2", "", "100%", "100%");
		 rMateChartH5.create("chartDim7", "chartHolderSubject3", "", "100%", "100%");
		 
		 rMateChartH5.create("chartDim31", "chartHolderDim31", "", "100%", "100%");
		 rMateChartH5.create("chartDim32", "chartHolderDim32", "", "100%", "100%");
		 rMateChartH5.create("chartDim33", "chartHolderDim33", "", "100%", "100%");
		 rMateChartH5.create("chartDim34", "chartHolderDim34", "", "100%", "100%");
		 
		 rMateChartH5.create("chartDim41", "chartHolderDim41", "", "100%", "100%");
		 rMateChartH5.create("chartDim42", "chartHolderDim42", "", "100%", "100%");
		 rMateChartH5.create("chartDim43", "chartHolderDim43", "", "100%", "100%");
		 rMateChartH5.create("chartDim44", "chartHolderDim44", "", "100%", "100%");

		 rMateChartH5.create("chartDim51", "chartHolderDim51", "", "100%", "100%");
		 rMateChartH5.create("chartDim52", "chartHolderDim52", "", "100%", "100%");
		 rMateChartH5.create("chartDim53", "chartHolderDim53", "", "100%", "100%");
		 rMateChartH5.create("chartDim54", "chartHolderDim54", "", "100%", "100%");

		 rMateChartH5.create("chartDim61", "chartHolderDim61", "", "100%", "100%");
		 rMateChartH5.create("chartDim62", "chartHolderDim62", "", "100%", "100%");
		 rMateChartH5.create("chartDim63", "chartHolderDim63", "", "100%", "100%");
		 
		 // 스트링 형식으로 레이아웃 정의.
		 
		 function makeLayout(pTitle,pTotCnt){
			 
			 var layoutStr =
	              '<rMateChart backgroundColor="#FFFFFF" borderStyle="none">'
	                    +'<Options>'
	                       +'<Caption text="'+pTitle+'" />'
	                        +'<SubCaption text="" textAlign="right" />'
	                  +'</Options>'
	                  +'<Combination2DChart   showDataTips="true" barWidthRatio="1" maxBarWidth="5">'
		                  +'<horizontalAxis>'
	                      	+'<CategoryAxis categoryField="cateval"/>'
	                   	  +'</horizontalAxis>'
	                 	  +'<verticalAxis>'
		                    +'<LinearAxis maximum="100" interval="10"/>'
	                 	  +'</verticalAxis>'
	                   	  +'<series>'
		                    +'<Column2DSeries labelPosition="outside" yField="rateval" displayName="비율" >'
		                      +'<fills>'
                            	/* Series안에 색을 채울 때(여러색) fills에 주목 */
                              +'<SolidColor color="#0066CC"/>'
                              +'<SolidColor color="#66CCFF"/>'
                              +'<SolidColor color="#66CCFF"/>'
                              +'<SolidColor color="#66CCFF"/>'
                          	  +'</fills>'
        	                +'</Column2DSeries>'
        	                
        	                +'<Line2DSeries radius="6" yField="rateval" displayName="비율" itemRenderer="CircleItemRenderer">'
                               +'<verticalAxis>'
   		                       +'<LinearAxis maximum="100" interval="10"/>'
                               +'</verticalAxis>'
                               +'<showDataEffect>'
                               +'<SeriesInterpolate/>'
                               +'</showDataEffect>'
                               +'<lineStroke>'
                               +'<Stroke color="#f9bd03" weight="4"/>'
                               +'</lineStroke>'
                               +'<stroke>'
                               +'<Stroke color="#f9bd03" weight="3"/>'
                              +'</stroke>'
                            +'</Line2DSeries>'
        	                
	                      +'</series>'
	                  +'</Combination2DChart  >'
	               +'</rMateChart>';

			return layoutStr;			 
			 
		 }
		 
var layoutStr =
             '<rMateChart backgroundColor="#FFFFFF" borderStyle="none">'
                   +'<Options>'
                      +'<Caption text="외래서비스" />'
                       +'<SubCaption text="( 150 )" textAlign="right" />'
                 +'</Options>'
                 +'<Bar2DChart showDataTips="true" barWidthRatio="1" maxBarWidth="30">'
                       +'<horizontalAxis>'
                           +'<LinearAxis maximum="100"/>'
                        +'</horizontalAxis>'
                      +'<verticalAxis>'
                         +'<CategoryAxis categoryField="cateval"/>'
                      +'</verticalAxis>'
                        +'<series>'
                           +'<Bar2DSeries labelPosition="outside" xField="rateval" displayName="비율"  color="#000000" insideLabelYOffset="0">'
                              +'<showDataEffect>'
                                   +'<SeriesInterpolate/>'
                               +'</showDataEffect>'
                          +'</Bar2DSeries>'
                     +'</series>'
                  +'</Bar2DChart>'
              +'</rMateChart>';
      		// 스트링 형식으로 레이아웃 정의.
      		var layoutMultiStr =
      		 '<rMateChart backgroundColor="#FFFFFF" borderStyle="none">'
      		       +'<Options>'
      		          +'<Caption text=""/>'
      		           +'<SubCaption text="(n=150)" textAlign="right" />'
      		         +'<Legend useVisibleCheck="true" defaultMouseOverAction="false" />'
      		       +'</Options>'
      		     +'<Combination2DChart  showDataTips="true" columnWidthRatio="0.4">'
      		         +'<horizontalAxis>'
      		               +'<CategoryAxis categoryField="SECTON_TITLE"/>'
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
      		              +'<Column2DSet type="clustered" showTotalLabel="true" totalLabelJsFunction="totalFunc">'
      		                    +'<series>'
      		               /*  Column2D Stacked 를 생성시에는 Column2DSeries를 최소 2개 정의합니다 */
      		                     +'<Column2DSeries labelPosition="inside" yField="GRADE_04_VAL" displayName="<%=multiStrDisName1%>"  color="#ffffff" strokeJsFunction="strokeFunction">'
      		                           +'<showDataEffect>'
      		                               +'<SeriesInterpolate/>'
      		                           +'</showDataEffect>'
      		                      +'</Column2DSeries>'
      		                      
      		                     <%
      		                     		if(!"".equals(multiStrDisName2)){
      		                     %>
      		                      +'<Column2DSeries labelPosition="inside" yField="GRADE_03_VAL" displayName="<%=multiStrDisName2%>"  color="#ffffff" strokeJsFunction="strokeFunction">'
      		                           +'<showDataEffect>'
      		                               +'<SeriesInterpolate/>'
      		                           +'</showDataEffect>'
      		                      +'</Column2DSeries>'
       		                     <%
      		                     		}
		                     	 %>
      		                      
      		                     <%
		                     		if(!"".equals(multiStrDisName3)){
		                     	 %>
      		                      +'<Column2DSeries labelPosition="inside" yField="GRADE_02_VAL" displayName="<%=multiStrDisName3%>"  color="#ffffff" strokeJsFunction="strokeFunction">'
      	                         +'<showDataEffect>'
      	                            +'<SeriesInterpolate/>'
      	                         +'</showDataEffect>'
      			                    +'</Column2DSeries>'
          		                   <%
  		                     		}
			                       %>

	      		                     <%
			                     		if(!"".equals(multiStrDisName4)){
			                     	 %>
      			                    +'<Column2DSeries labelPosition="inside" yField="GRADE_01_VAL" displayName="<%=multiStrDisName4%>"  color="#ffffff" strokeJsFunction="strokeFunction">'
      			                    +'<showDataEffect>'
      			                       +'<SeriesInterpolate/>'
      			                   +'</showDataEffect>'
      			                  +'</Column2DSeries>'
         		                   <%
	  		                     		}
				                   %>
      			                  
      		                  +'</series>'
      		              +'</Column2DSet>'
      		                +'<Line2DSeries radius="6" yField="GRADE_AVG_VAL" displayName="평균" labelPosition="down" itemRenderer="CircleItemRenderer">'
      	                   +'<verticalAxis>'
      	                       +'<LinearAxis id="vAxis2" maximum="100" interval="10"/>'
      	                      +'</verticalAxis>'
      	                      +'<showDataEffect>'
      	                         +'<SeriesInterpolate/>'
      	                     +'</showDataEffect>'
      	                    +'<lineStroke>'
      	                         +'<Stroke color="#f9bd03" weight="4"/>'
      	                     +'</lineStroke>'
      	                    +'<stroke>'
      	                         +'<Stroke color="#f9bd03" weight="3"/>'
      	                     +'</stroke>'
      	                +'</Line2DSeries>'
      		         +'</series>'
      		      +'</Combination2DChart >'
      		   +'</rMateChart>';
		               
		 // 차트 데이터
		 /*
		 var chartData =
		  [{"cateval":"여성", "rateval":40},
		   {"cateval":"남성", "rateval":60}];
		 */
		 //성별 데이터
		 var summary1Array =<%=graphparam.getString("summary1Array")%>;
		 var summary2Array =<%=graphparam.getString("summary2Array")%>;
		 var summary3Array =<%=graphparam.getString("summary3Array")%>;
		 var summary4Array =<%=graphparam.getString("summary4Array")%>;
		 var summary5Array =<%=graphparam.getString("summary5Array")%>;
		 var summary6Array =<%=graphparam.getString("summary6Array")%>;
		 var summary7Array =<%=graphparam.getString("summary7Array")%>;
		 var summary31Array =<%=graphparam.getString("summary31Array")%>;
		 var summary32Array =<%=graphparam.getString("summary32Array")%>;
		 var summary33Array =<%=graphparam.getString("summary33Array")%>;
		 var summary34Array =<%=graphparam.getString("summary34Array")%>;
		 var summary41Array =<%=graphparam.getString("summary41Array")%>;
		 var summary42Array =<%=graphparam.getString("summary42Array")%>;
		 var summary43Array =<%=graphparam.getString("summary43Array")%>;
		 var summary44Array =<%=graphparam.getString("summary44Array")%>;
		 var summary51Array =<%=graphparam.getString("summary51Array")%>;
		 var summary52Array =<%=graphparam.getString("summary52Array")%>;
		 var summary53Array =<%=graphparam.getString("summary53Array")%>;
		 var summary54Array =<%=graphparam.getString("summary54Array")%>;
		 var summary61Array =<%=graphparam.getString("summary61Array")%>;
		 var summary62Array =<%=graphparam.getString("summary62Array")%>;
		 var summary63Array =<%=graphparam.getString("summary63Array")%>;

		 function custonSort(a, b) {
			  if(a.cateseq == b.cateseq){ return 0} return  a.cateseq > b.cateseq ? 1 : -1;
		 }
		 summary1Array.sort(custonSort);
		 summary2Array.sort(custonSort);
		 summary3Array.sort(custonSort);
		 summary4Array.sort(custonSort);
		 summary5Array.sort(custonSort);
		 summary6Array.sort(custonSort);
		 summary7Array.sort(custonSort);

		 
		 // rMateChartH5.calls 함수를 이용하여 차트의 준비가 끝나면 실행할 함수를 등록합니다.
		 //
		 // argument 1 - rMateChartH5.create시 설정한 차트 객체 아이디 값
		 // argument 2 - 차트준비가 완료되면 실행할 함수 명(key)과 설정될 전달인자 값(value)
		 // 
		 // 아래 내용은 
		 // 1. 차트 준비가 완료되면 첫 전달인자 값을 가진 차트 객체에 접근하여
		 // 2. 두 번째 전달인자 값의 key 명으로 정의된 함수에 value값을 전달인자로 설정하여 실행합니다.
		 
		 //환자경험종합평가
		 rMateChartH5.calls("chartDim1", {
		  "setLayout" : makeLayout('',''),
		  "setData"   : summary1Array
		 });
		 rMateChartH5.calls("chartDim2", {
			  "setLayout" : makeLayout('',''),
			  "setData"   : summary2Array
			 });
		 rMateChartH5.calls("chartDim3", {
			  "setLayout" : makeLayout('',''),
			  "setData"   : summary3Array
			 });
		 rMateChartH5.calls("chartDim4", {
			  "setLayout" : makeLayout('',''),
			  "setData"   : summary4Array
		 });

		 rMateChartH5.calls("chartDim5", {
			  "setLayout" : makeLayout('',''),
			  "setData"   : summary5Array
			 });

		 rMateChartH5.calls("chartDim6", {
			  "setLayout" : makeLayout('',''),
			  "setData"   : summary6Array
			 });
		 
		 rMateChartH5.calls("chartDim7", {
			  "setLayout" : makeLayout('',''),
			  "setData"   : summary7Array
			 });

		 rMateChartH5.calls("chartDim31", {
			  "setLayout" : makeLayout('',''),
			  "setData"   : summary31Array
			 });

		 rMateChartH5.calls("chartDim32", {
			  "setLayout" : makeLayout('',''),
			  "setData"   : summary32Array
			 });
		 
		 rMateChartH5.calls("chartDim33", {
			  "setLayout" : makeLayout('',''),
			  "setData"   : summary33Array
			 });
		 
		 rMateChartH5.calls("chartDim34", {
			  "setLayout" : makeLayout('',''),
			  "setData"   : summary34Array
			 });

		 rMateChartH5.calls("chartDim41", {
			  "setLayout" : makeLayout('',''),
			  "setData"   : summary41Array
			 });

		 rMateChartH5.calls("chartDim42", {
			  "setLayout" : makeLayout('',''),
			  "setData"   : summary42Array
			 });
		 
		 rMateChartH5.calls("chartDim43", {
			  "setLayout" : makeLayout('',''),
			  "setData"   : summary43Array
			 });
		 
		 rMateChartH5.calls("chartDim44", {
			  "setLayout" : makeLayout('',''),
			  "setData"   : summary44Array
			 });

		 rMateChartH5.calls("chartDim51", {
			  "setLayout" : makeLayout('',''),
			  "setData"   : summary51Array
			 });

		 rMateChartH5.calls("chartDim52", {
			  "setLayout" : makeLayout('',''),
			  "setData"   : summary52Array
			 });
		 
		 rMateChartH5.calls("chartDim53", {
			  "setLayout" : makeLayout('',''),
			  "setData"   : summary53Array
			 });
		 
		 rMateChartH5.calls("chartDim54", {
			  "setLayout" : makeLayout('',''),
			  "setData"   : summary54Array
			 });

		 rMateChartH5.calls("chartDim61", {
			  "setLayout" : layoutMultiStr,
			  "setData"   : summary61Array
			 });
		 
		 rMateChartH5.calls("chartDim62", {
			  "setLayout" : layoutMultiStr,
			  "setData"   : summary62Array
			 });

		 rMateChartH5.calls("chartDim63", {
			  "setLayout" : layoutMultiStr,
			  "setData"   : summary63Array
			 });
		 
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
						<div class="space"></div>
						<div class="widget-header header-color-orange">
							<h5>1.환자경험종합평가</h5>
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
									<!--  
									<div class="row-fluid">
										<div class="alert alert-success" style="margin-bottom:0px">종합만족도(100점만점기준)</div>
									</div>
									-->
									<div class="row-fluid">
										<div class="span6 graph-text-center alert-success">종합만족도(100점만점기준)</div>
										<div class="span6 graph-text-center alert-info">체감만족도(100점만점기준)</div>
									</div>

									<div class="space"></div>
									<div class="row-fluid">
										<div class="span6">
										<div id="chartHolderDim1" class="graph-faddind-left" style="height:250px;"></div>
										</div>
										<div class="span6">
										<div id="chartHolderDim2" class="graph-faddind-left" style="height:250px;"></div>
										</div>
									</div>

									<div class="space"></div>
									<div class="row-fluid">
										<div class="span6 graph-text-center alert-success">추천지수(NPS)</div>
										<div class="span6 graph-text-center alert-info">총차원만족도(100점만점기준)</div>
									</div>

									<div class="space"></div>
									<div class="row-fluid">
										<div class="span6">
										<div id="chartHolderDim3" class="graph-faddind-left" style="height:250px;"></div>

										<table id="menuTable" class="table table-hover table-bordered">
										<colgroup>
											<col width="*">
											<col width="100px">
										</colgroup>
										<thead>
											<tr>
												<th class="text-center"></th>
												<th class="text-center">병원전체</th>
												<th class="text-center">외래서비스</th>
												<th class="text-center">입원서비스</th>
												<th class="text-center">응급실</th>
											</tr>
										</thead>
										<tbody>
									    <%
									    	DataMap dataMapH = (DataMap) dim3.get(0);
									    	DataMap dataMapL = (DataMap) dim3.get(1);
									    
									    %>
										<tr style="cursor:pointer;cursor:hand;" onclick="fnSelect('1558442363716','외래환자경험 평가','10','2019','2')">
												<td>P romoters</td>
												<td style="text-align: right;"><%=dataMapH.getString("Survey99")%></td>
												<td style="text-align: right;"><%=dataMapH.getString("Survey10")%></td>
												<td style="text-align: right;"><%=dataMapH.getString("Survey20")%></td>
												<td style="text-align: right;"><%=dataMapH.getString("Survey30")%></td>
											</tr>
									
										<tr style="cursor:pointer;cursor:hand;" onclick="fnSelect('1558364535368','입원환자경험 평가','20','2019','2')">
												<td>Detractors</td>
												<td style="text-align: right;"><%=dataMapL.getString("Survey99")%></td>
												<td style="text-align: right;"><%=dataMapL.getString("Survey10")%></td>
												<td style="text-align: right;"><%=dataMapL.getString("Survey20")%></td>
												<td style="text-align: right;"><%=dataMapL.getString("Survey30")%></td>
											</tr>
										</tbody>
									</table>

										</div>
										<div class="span6">
										<div id="chartHolderDim4" class="graph-faddind-left" style="height:250px;"></div>
										</div>
									</div>
								<div>
								*종합만족도= (차원만족도평균X0.7) + (체감만족도X0.3) 
								</div>
								<div>
								** 추천지수(NPS): Promoter(9,10점응답률) –Detractors(0~6점응답률) / 11점척도(0~10점)
								</div>
	
								</div><!--widget-main  -->
							</div> <!-- widget-body-inner -->
						</div> <!-- widget-body -->
					</div><!--widget-box  -->
						
					<div class="space"></div>
					<div class="widget-box">
						<div class="widget-header header-color-orange">
							<h5>2.환자경험 차원평가</h5>
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
									<div class="row-fluid">
										<div class="alert alert-success" style="margin-bottom:0px">외래서비스(100점만점기준)</div>
									</div>
									<div class="space"></div>
									<div class="row-fluid">
										<div id="chartHolderSubject1" class="graph-faddind-left" style="height:250px;"></div>
									</div>

									<div class="space"></div>
									<div class="row-fluid">
										<div class="alert alert-success" style="margin-bottom:0px">입원서비스(100점만점기준)</div>
									</div>
									<div class="space"></div>
									<div class="row-fluid">
										<div id="chartHolderSubject2" class="graph-faddind-left" style="height:250px;"></div>
									</div>
									
									<div class="space"></div>
									<div class="row-fluid">
										<div class="alert alert-success" style="margin-bottom:0px">응급실(100점만점기준)</div>
									</div>
									<div class="space"></div>
									<div class="row-fluid">
										<div id="chartHolderSubject3" class="graph-faddind-left" style="height:250px;"></div>
									</div>
															
	
								</div><!--widget-main  -->
							</div> <!-- widget-body-inner -->
						</div> <!-- widget-body -->
					</div><!--widget-box  -->
					
					<div class="space"></div>
					<div class="widget-box">
						<div class="widget-header header-color-orange">
							<h5>3.환자경험 조사차수별  비교평가 ①비교평가 종합만족도</h5>
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
									<div class="row-fluid">
										<div class="span3 graph-text-center alert-success">병원전체</div>
										<div class="span3 graph-text-center alert-info">외래서비스</div>
										<div class="span3 graph-text-center alert-success">입원서비스</div>
										<div class="span3 graph-text-center alert-info">응급실</div>
									</div>
									<div class="space"></div>
									<div class="row-fluid">
										<div class="span3">
										<div id="chartHolderDim31" class="graph-faddind-left" style="height:250px;"></div>
										</div>
										<div class="span3">
										<div id="chartHolderDim32" class="graph-faddind-left" style="height:250px;"></div>
										</div>
										<div class="span3">
										<div id="chartHolderDim33" class="graph-faddind-left" style="height:250px;"></div>
										</div>
										<div class="span3">
										<div id="chartHolderDim34" class="graph-faddind-left" style="height:250px;"></div>
										</div>
									</div>
								</div><!--widget-main  -->
							</div> <!-- widget-body-inner -->
						</div> <!-- widget-body -->
					</div><!--widget-box  -->

					<div class="space"></div>
					<div class="widget-box">
						<div class="widget-header header-color-orange">
							<h5>3.환자경험 조사차수별  비교평가 ②추천지수(NPS)</h5>
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
									<div class="row-fluid">
										<div class="span3 graph-text-center alert-success">병원전체</div>
										<div class="span3 graph-text-center alert-info">외래서비스</div>
										<div class="span3 graph-text-center alert-success">입원서비스</div>
										<div class="span3 graph-text-center alert-info">응급실</div>
									</div>
									<div class="space"></div>
									<div class="row-fluid">
										<div class="span3">
										<div id="chartHolderDim41" class="graph-faddind-left" style="height:250px;"></div>
										</div>
										<div class="span3">
										<div id="chartHolderDim42" class="graph-faddind-left" style="height:250px;"></div>
										</div>
										<div class="span3">
										<div id="chartHolderDim43" class="graph-faddind-left" style="height:250px;"></div>
										</div>
										<div class="span3">
										<div id="chartHolderDim44" class="graph-faddind-left" style="height:250px;"></div>
										</div>
									</div>
								</div><!--widget-main  -->
							</div> <!-- widget-body-inner -->
						</div> <!-- widget-body -->
					</div><!--widget-box  -->
							
					<div class="space"></div>
					<div class="widget-box">
						<div class="widget-header header-color-orange">
							<h5>3.환자경험 조사차수별  비교평가  ③체감만족도</h5>
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
									<div class="row-fluid">
										<div class="span3 graph-text-center alert-success">병원전체</div>
										<div class="span3 graph-text-center alert-info">외래서비스</div>
										<div class="span3 graph-text-center alert-success">입원서비스</div>
										<div class="span3 graph-text-center alert-info">응급실</div>
									</div>
									<div class="space"></div>
									<div class="row-fluid">
										<div class="span3">
										<div id="chartHolderDim51" class="graph-faddind-left" style="height:250px;"></div>
										</div>
										<div class="span3">
										<div id="chartHolderDim52" class="graph-faddind-left" style="height:250px;"></div>
										</div>
										<div class="span3">
										<div id="chartHolderDim53" class="graph-faddind-left" style="height:250px;"></div>
										</div>
										<div class="span3">
										<div id="chartHolderDim54" class="graph-faddind-left" style="height:250px;"></div>
										</div>
									</div>
								</div><!--widget-main  -->
							</div> <!-- widget-body-inner -->
						</div> <!-- widget-body -->
					</div><!--widget-box  -->

					<div class="space"></div>
					<div class="widget-box">
						<div class="widget-header header-color-orange">
							<h5>3.환자경험 조사차수별 비교평가  ④차원만족도</h5>
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
									<div class="row-fluid">
										<div class="alert alert-success" style="margin-bottom:0px">외래서비스(100점만점기준)</div>
									</div>
									<div class="space"></div>
									<div class="row-fluid">
										<div id="chartHolderDim61" class="graph-faddind-left" style="height:250px;"></div>
									</div>

									<div class="space"></div>
									<div class="row-fluid">
										<div class="alert alert-success" style="margin-bottom:0px">입원서비스(100점만점기준)</div>
									</div>
									<div class="space"></div>
									<div class="row-fluid">
										<div id="chartHolderDim62" class="graph-faddind-left" style="height:250px;"></div>
									</div>
									
									<div class="space"></div>
									<div class="row-fluid">
										<div class="alert alert-success" style="margin-bottom:0px">응급실(100점만점기준)</div>
									</div>
									<div class="space"></div>
									<div class="row-fluid">
										<div id="chartHolderDim63" class="graph-faddind-left" style="height:250px;"></div>
									</div>
															
	
								</div><!--widget-main  -->
							</div> <!-- widget-body-inner -->
						</div> <!-- widget-body -->
					</div><!--widget-box  -->



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

	</body>
</html>
