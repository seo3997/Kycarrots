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
		 rMateChartH5.create("chartSex1", "chartHolderSex1", "", "100%", "100%");
		 rMateChartH5.create("chartSex2", "chartHolderSex2", "", "100%", "100%");
		 rMateChartH5.create("chartSex3", "chartHolderSex3", "", "100%", "100%");

		 //연령
		 rMateChartH5.create("chartAge1", "chartHolderAge1", "", "100%", "100%");
		 rMateChartH5.create("chartAge2", "chartHolderAge2", "", "100%", "100%");
		 rMateChartH5.create("chartAge3", "chartHolderAge3", "", "100%", "100%");

		 //연령
		 rMateChartH5.create("chartSchool1", "chartHolderSchool1", "", "100%", "100%");
		 rMateChartH5.create("chartSchool2", "chartHolderSchool2", "", "100%", "100%");
		 rMateChartH5.create("chartSchool3", "chartHolderSchool3", "", "100%", "100%");

		 //건강상태
		 rMateChartH5.create("chartHealth1", "chartHolderHeath1", "", "100%", "100%");
		 rMateChartH5.create("chartHealth2", "chartHolderHeath2", "", "100%", "100%");
		 rMateChartH5.create("chartHealth3", "chartHolderHeath3", "", "100%", "100%");

		 //진료과목
		 rMateChartH5.create("chartSubject1", "chartHolderSubject1", "", "100%", "100%");
		 rMateChartH5.create("chartSubject2", "chartHolderSubject2", "", "100%", "100%");
		 rMateChartH5.create("chartSubject3", "chartHolderSubject3", "", "100%", "100%");
		 
		 //입원경로 
		 rMateChartH5.create("chartWay2", "chartHolderWay2", "", "100%", "100%");

		 //응급실유형 
		 rMateChartH5.create("chartEmerWay3", "chartHolderEmerWay3", "", "100%", "100%");
		 
		 //응답자유형
		 rMateChartH5.create("chartReWay3", "chartHolderReWay3", "", "100%", "100%");
		 
		 
		 // 스트링 형식으로 레이아웃 정의.
		 
		 function makeLayout(pTitle,pTotCnt){
			 
			 var layoutStr =
	              '<rMateChart backgroundColor="#FFFFFF" borderStyle="none">'
	                    +'<Options>'
	                       +'<Caption text="'+pTitle+'" />'
	                        +'<SubCaption text="('+ pTotCnt+' )" textAlign="right" />'
	                  +'</Options>'
	                  +'<Bar2DChart showDataTips="true" barWidthRatio="1" maxBarWidth="5">'
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
		  
		 // 차트 데이터
		 /*
		 var chartData =
		  [{"cateval":"여성", "rateval":40},
		   {"cateval":"남성", "rateval":60}];
		 */
		 //성별 데이터
		 var chartDataSex1 =<%=graphparam.getString("sex1Array")%>;
		 var chartDataSex2 =<%=graphparam.getString("sex2Array")%>;
		 var chartDataSex3 =<%=graphparam.getString("sex3Array")%>;

		 //연령 데이터
		 var chartDataAge1 =<%=graphparam.getString("age1Array")%>;
		 var chartDataAge2 =<%=graphparam.getString("age2Array")%>;
		 var chartDataAge3 =<%=graphparam.getString("age3Array")%>;

		 //학력 데이터
		 var chartDataSchool1 =<%=graphparam.getString("school1Array")%>;
		 var chartDataSchool2 =<%=graphparam.getString("school2Array")%>;

		 //건강상태 데이터
		 var chartDataHealth1 =<%=graphparam.getString("health1Array")%>;
		 var chartDataHealth2 =<%=graphparam.getString("health2Array")%>;

		 //진료과목 데이터
		 var chartDataSubject1 =<%=graphparam.getString("subject1Array")%>;
		 var chartDataSubject2 =<%=graphparam.getString("subject2Array")%>;

		 //진료과목 데이터
		 var chartDataWay2 =<%=graphparam.getString("way2Array")%>;

		 //응급실유형
		 var chartDataEmerWay2 =<%=graphparam.getString("emer3Array")%>;
		 
		 //응답자유형
		 var chartDataReWay2 =<%=graphparam.getString("reway3Array")%>;
		 
		 // rMateChartH5.calls 함수를 이용하여 차트의 준비가 끝나면 실행할 함수를 등록합니다.
		 //
		 // argument 1 - rMateChartH5.create시 설정한 차트 객체 아이디 값
		 // argument 2 - 차트준비가 완료되면 실행할 함수 명(key)과 설정될 전달인자 값(value)
		 // 
		 // 아래 내용은 
		 // 1. 차트 준비가 완료되면 첫 전달인자 값을 가진 차트 객체에 접근하여
		 // 2. 두 번째 전달인자 값의 key 명으로 정의된 함수에 value값을 전달인자로 설정하여 실행합니다.
		 
		 //성별
		 rMateChartH5.calls("chartSex1", {
		  "setLayout" : makeLayout('외래서비스','<%=graphparam.getString("entryCnt1")%>'),
		  "setData"   : chartDataSex1
		 });
		 rMateChartH5.calls("chartSex2", {
			  "setLayout" : makeLayout('입원서비스','<%=graphparam.getString("entryCnt2")%>'),
			  "setData"   : chartDataSex2
			 });
		 rMateChartH5.calls("chartSex3", {
			  "setLayout" : makeLayout('응급실','<%=graphparam.getString("entryCnt3")%>'),
			  "setData"   : chartDataSex3
			 });
		 
		 //연령
		 rMateChartH5.calls("chartAge1", {
			  "setLayout" : makeLayout('외래서비스','<%=graphparam.getString("entryCnt1")%>'),
			  "setData"   : chartDataAge1
			 });
		 rMateChartH5.calls("chartAge2", {
			  "setLayout" : makeLayout('입원서비스','<%=graphparam.getString("entryCnt2")%>'),
			  "setData"   : chartDataAge2
			 });
		 rMateChartH5.calls("chartAge3", {
			  "setLayout" : makeLayout('응급실','<%=graphparam.getString("entryCnt3")%>'),
			  "setData"   : chartDataAge3
			 });
		 
		 //최종학력
		 rMateChartH5.calls("chartSchool1", {
			  "setLayout" : makeLayout('외래서비스','<%=graphparam.getString("entryCnt1")%>'),
			  "setData"   : chartDataSchool1
			 });
		 rMateChartH5.calls("chartSchool2", {
			  "setLayout" : makeLayout('입원서비스','<%=graphparam.getString("entryCnt2")%>'),
			  "setData"   : chartDataSchool2
			 });

		 //건강상태
		 rMateChartH5.calls("chartHealth1", {
			  "setLayout" : makeLayout('외래서비스','<%=graphparam.getString("entryCnt1")%>'),
			  "setData"   : chartDataHealth1
			 });
		 rMateChartH5.calls("chartHealth2", {
			  "setLayout" : makeLayout('입원서비스','<%=graphparam.getString("entryCnt2")%>'),
			  "setData"   : chartDataHealth2
			 });

		 //진료과목
		 rMateChartH5.calls("chartSubject1", {
			  "setLayout" : makeLayout('외래서비스','<%=graphparam.getString("entryCnt1")%>'),
			  "setData"   : chartDataSubject1
			 });
		 rMateChartH5.calls("chartSubject2", {
			  "setLayout" : makeLayout('입원서비스','<%=graphparam.getString("entryCnt2")%>'),
			  "setData"   : chartDataSubject2
			 });

		 //입원경로
		 rMateChartH5.calls("chartWay2", {
			  "setLayout" : makeLayout('입원서비스','<%=graphparam.getString("entryCnt2")%>'),
			  "setData"   : chartDataWay2
			 });

		 //응급실유형
		 rMateChartH5.calls("chartEmerWay3", {
			  "setLayout" : makeLayout('응급실','<%=graphparam.getString("entryCnt2")%>'),
			  "setData"   : chartDataEmerWay2
			 });
		 
		 //응답자유형
		 rMateChartH5.calls("chartReWay3", {
			  "setLayout" : makeLayout('응답자','<%=graphparam.getString("entryCnt2")%>'),
			  "setData"   : chartDataReWay2
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
							<h5>1.응답자특성 ①</h5>
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
										<div class="alert alert-success" style="margin-bottom:0px">
											성별
										</div>
									</div>
									<div class="space"></div>
									<div class="row-fluid">
										<div class="span4">
										<div id="chartHolderSex1" class="graph-faddind-left" style="height:200px;"></div>
										</div>
										<div class="span4">
										<div id="chartHolderSex2" class="graph-faddind-left" style="height:200px;"></div>
										</div>
										<div class="span4">
										<div id="chartHolderSex3" class="graph-faddind-left" style="height:200px;"></div>
										</div>
									</div>
								<div class="space"></div>
								<div class="row-fluid">
									<div class="alert alert-success" style="margin-bottom:0px">연령</div>
								</div>
								<div class="space"></div>
								<div class="row-fluid">
									<div class="span4">
									<div id="chartHolderAge1" class="graph-faddind-left" style="height:300px;"></div>
									</div>
									<div class="span4">
									<div id="chartHolderAge2" class="graph-faddind-left" style="height:300px;"></div>
									</div>
									<div class="span4">
									<div id="chartHolderAge3" class="graph-faddind-left" style="height:300px;"></div>
									</div>
								</div>
			
								<div class="space"></div>
								<div class="row-fluid">
									<div class="alert alert-success" style="margin-bottom:0px">최종학력</div>
								</div>
								<div class="space"></div>
								<div class="row-fluid">
									<div class="span4">
									<div id="chartHolderSchool1" class="graph-faddind-left" style="height:200px;"></div>
									</div>
									<div class="span4">
									<div id="chartHolderSchool2" class="graph-faddind-left" style="height:200px;"></div>
									</div>
									<div class="span4">
									<div id="chartHolderSchool3" class="graph-faddind-left" style="height:200px;"></div>
									</div>
								</div>
			
								<div class="space"></div>
								<div class="row-fluid">
									<div class="alert alert-success" style="margin-bottom:0px">건강상태</div>
								</div>
								<div class="space"></div>
								<div class="row-fluid">
									<div class="span4">
									<div id="chartHolderHeath1" class="graph-faddind-left" style="height:300px;"></div>
									</div>
									<div class="span4">
									<div id="chartHolderHeath2" class="graph-faddind-left" style="height:300px;"></div>
									</div>
									<div class="span4">
									<div id="chartHolderHeath3" class="graph-faddind-left" style="height:300px;"></div>
									</div>
								</div>
	
								</div><!--widget-main  -->
							</div> <!-- widget-body-inner -->
						</div> <!-- widget-body -->
					</div><!--widget-box  -->
						
					<div class="space"></div>
					<div class="widget-box">
						<div class="widget-header header-color-orange">
							<h5>1.응답자특성 ②</h5>
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
										<div class="alert alert-success" style="margin-bottom:0px">진료과목</div>
									</div>
									<div class="space"></div>
									<div class="row-fluid">
										<div class="span4">
										<div id="chartHolderSubject1" class="graph-faddind-left" style="height:700px;"></div>
										</div>
										<div class="span4">
										<div id="chartHolderSubject2" class="graph-faddind-left" style="height:700px;"></div>
										</div>
										<div class="span4">
										<div id="chartHolderSubject3" class="graph-faddind-left" style="height:700px;"></div>
										</div>
									</div>
			
									<div class="space"></div>
									<div class="row-fluid">
										<div class="alert alert-success" style="margin-bottom:0px">입원경로</div>
									</div>
									<div class="space"></div>
									<div class="row-fluid">
										<div class="span4">
										<div id="chartHolderWay1" class="graph-faddind-left" style="height:200px;"></div>
										</div>
										<div class="span4">
										<div id="chartHolderWay2" class="graph-faddind-left" style="height:200px;"></div>
										</div>
										<div class="span4">
										<div id="chartHolderWay3" class="graph-faddind-left" style="height:200px;"></div>
										</div>
									</div>
			
									<div class="space"></div>
									<div class="row-fluid">
										<div class="alert alert-success" style="margin-bottom:0px">응급실유형</div>
									</div>
									<div class="space"></div>
									<div class="row-fluid">
										<div class="span4">
										<div id="chartHolderEmerWay1" class="graph-faddind-left" style="height:200px;"></div>
										</div>
										<div class="span4">
										<div id="chartHolderEmerWay2" class="graph-faddind-left" style="height:200px;"></div>
										</div>
										<div class="span4">
										<div id="chartHolderEmerWay3" class="graph-faddind-left" style="height:200px;"></div>
										</div>
									</div>

									<div class="space"></div>
									<div class="row-fluid">
										<div class="alert alert-success" style="margin-bottom:0px">응답자유형</div>
									</div>
									<div class="space"></div>
									<div class="row-fluid">
										<div class="span4">
										<div id="chartHolderReWay1" class="graph-faddind-left" style="height:200px;"></div>
										</div>
										<div class="span4">
										<div id="chartHolderReWay2" class="graph-faddind-left" style="height:200px;"></div>
										</div>
										<div class="span4">
										<div id="chartHolderReWay3" class="graph-faddind-left" style="height:200px;"></div>
										</div>
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
