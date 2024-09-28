<%@page import="com.whomade.kycarrots.mgt.survey.vo.TbResults"%>
<%@page import="com.whomade.kycarrots.framework.common.util.*"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@page import="edu.vt.ward.survey.*"%>
<%@page import="java.io.*" %>
<%@page import="java.util.*" %>
<%@page import="java.net.*" %>
<%@page import="java.text.*" %>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<jsp:useBean id="param" 				class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="survey" 				class="com.whomade.kycarrots.mgt.survey.vo.TbSurvey" scope="request"/>
<jsp:useBean id="SectionList"   		type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="QuestionList"  		type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="QuestionLableList"  	type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="tbResults"  			type="java.util.List" class="java.util.ArrayList" scope="request"/>


<%
	String surveyId 	= param.getString("surveyId");

	iSurveyEntryForm entryForm = new iSurveyEntryForm (surveyId);
	entryForm.setExitSurvey(true);
	entryForm.load(SectionList,QuestionList,QuestionLableList);

	int sectionNr = -1;
	if ( !StringUtil.isEmpty(param.getString("section"))) {
	 sectionNr = Integer.parseInt (param.getString( "section" ));
	}
	int questionNr = -1;
	if ( !StringUtil.isEmpty(param.getString("question"))) {
	  questionNr = Integer.parseInt (param.getString("question"));
	}
	int optionNr = -1;

	if ( !StringUtil.isEmpty(param.getString("option"))) {
	  optionNr = Integer.parseInt (param.getString("option"));
	}
	
	String pageTitle = "View Details";
	
	int iResultCnt=survey.getNUMENTRIES();


%>


<!doctype html>
<html lang="en"><head>
		<meta charset="utf-8">
		<title>Survey Tool</title>

		<meta name="description" content="Common Buttons &amp; Icons">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">

		<!--basic styles-->

		<link href="/common/assets/css/bootstrap.min.css" rel="stylesheet">
		<link href="/common/assets/css/bootstrap-responsive.min.css" rel="stylesheet">
		<link rel="stylesheet" href="/common/assets/css/font-awesome.min.css">

		<!--[if IE 7]>
		  <link rel="stylesheet" href="/common/assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!--page specific plugin styles-->

		<!--fonts-->

		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300">

		<!--ace styles-->

		<link rel="stylesheet" href="/common/assets/css/ace.min.css">
		<link rel="stylesheet" href="/common/assets/css/ace-responsive.min.css">
		<link rel="stylesheet" href="/common/assets/css/ace-skins.min.css">

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="/common/assets/css/ace-ie.min.css" />
		<![endif]-->

		<!--inline styles if any-->
	</head>
<body>
	<div id="page-content" class="clearfix">
		<div>
			<h3><small>&nbsp;View Summary&nbsp;<i class="icon-double-angle-right"></i></small>&nbsp;<img src="/common/img/current.png" style="width: 23px;height: 29px;">&nbsp;<%=pageTitle%></h3>
		</div><!--/.page-header-->
		<br>

		<form method="post" action="/mgt/survey/viewResults.do" name="form">
		<input type="hidden" name="surveyId" value="<%=surveyId%>">
		<input type="hidden" name="userNo" value="<%=param.getString("userNo")%>">
		<input type="hidden" name="surveyGroupId" value="<%=param.getString("surveyGroupId")%>">
		<input type="hidden" name="surveyYear" value="<%=param.getString("surveyYear")%>">
		<input type="hidden" name="surveyDegree" value="<%=param.getString("surveyDegree")%>">
		<div class="row-fluid">
		<div align="right">
		<input type="submit" name="backToSummary" value="<<  summary화면으로 이동" class="btn btn-small btn-warning">&nbsp;&nbsp;
		</div>
		</div>
		</form>
		
		<div class="row-fluid">
			<h2 class="icon-caret-right blue">&nbsp;<%=survey.getSURVEY_TITLE()%></h1>
			<div class="tab-content">
				<table cellspacing="1" cellpadding="2" width="100%" border="0">
				<tbody>
				<tr>
					<td>
						<table class="table table-striped table-bordered" >
						<tr>
						  <th class="data">&nbsp;</th>
						  <th class="data">&nbsp;</th>
						<%
						  // create table header
						   for (int irow=0;irow<entryForm.getNumSections();irow++){
						 	    iSection sectionEntryForm = entryForm.getSection ( irow );
							 	for ( int qi = 0; qi < sectionEntryForm.getNumQuestions (); qi++ ) {
							    Question question = sectionEntryForm.getQuestion ( qi );
							
							    if ( !question.getClass().getName ().equals( "edu.vt.ward.survey.InputComment" ) ) {
							      String questionText = question.getText ();
							
							      // if there's no question text but a label, take that instead
							      if ( question.getClass().getName ().equals( "edu.vt.ward.survey.InputTextline" ) ) {
							        if ( !questionText.equals("") ) {
							          questionText += "<br>";
							        }
							        questionText += ((InputTextline) question).getLabel ();
							      }
							
							      if ( qi == questionNr ) {
							        out.print( "  <th class=\"dataselected\" valign=\"bottom\" align=\"left\">" + questionText + "</th>\n" );
							      } else {
							        out.print( "  <th  valign=\"bottom\" align=\"left\">" + questionText + "</th>\n" );
							      }
							    } // end: if ( !question.getClass().getName ().equals( "edu.vt.ward.survey.InputComment" ) )
							   }
						   }
						%>

						<%
						    int RESULTS_ID=-1;
							ArrayList<String> aResult = null; 
							for (int i=0;i<tbResults.size();i++){
								TbResults aTbResult = (TbResults)tbResults.get(i);
								if (RESULTS_ID!=aTbResult.getRESULTS_ID()){
									  RESULTS_ID=aTbResult.getRESULTS_ID();
								  	  aResult = new ArrayList<String>();
									  for (int j=0;j<tbResults.size();j++){
										TbResults jTbResult = (TbResults)tbResults.get(j);
										if(RESULTS_ID==jTbResult.getRESULTS_ID()){
											if(!jTbResult.getQUESTION_TYPE().equals("inputComment"))
											aResult.add(entryForm.getResultsStr(jTbResult));
										}
									  }
						  
						%>
						<tr>
						  <td  width="1%" valign="top" nowrap><%=aTbResult.getRESULTS_ID()%><a href="/mgt/survey/deleteEntry.do?surveyId=<%=surveyId%>&entryId=<%=aTbResult.getRESULTS_ID()%>">delete</a></td>
						  <td  width="1%" valign="top" nowrap><a href="/mgt/survey/view.do?surveyId=<%=surveyId%>&entryId=<%=aTbResult.getRESULTS_ID()%>"><%=aTbResult.getINSERT_DATE()%></a> </td>
						  <%
						  	 for(int j=0;j<aResult.size();j++){
						  %>
						  <td valign='top'"><%=aResult.get(j)%><br></td>
						  <%
						  	 }
						  %>
						</tr>
						<%
								}
							}
						%>
						

						</table>
					</td>
				</tr>
				</tbody>
				</table>											
			</div><!--/tab-content-->
		</div><!--/row-fluid-->
	</div><!--/#page-content-->
</body>
</html>