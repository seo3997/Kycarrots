<%@page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@page import="edu.vt.ward.survey.iSurveyEntryForm"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>

<jsp:useBean id="param" 				class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="survey" 				class="com.whomade.kycarrots.mgt.survey.vo.TbSurvey" scope="request"/>
<jsp:useBean id="SectionList"   		type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="QuestionList"  		type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="QuestionLableList"  	type="java.util.List" class="java.util.ArrayList" scope="request"/>



<%
	String surveyId 	= param.getString("surveyId");
	String pid 			= param.getString("userNo");
	System.out.println("editEntryForm surveyId["+surveyId+"]*********");

	iSurveyEntryForm entryForm = new iSurveyEntryForm (surveyId);
	entryForm.setExitSurvey(true);
	entryForm.load(SectionList,QuestionList,QuestionLableList);
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
		<script language="JavaScript" type="text/javascript"><!--
			function isIE4()
			{ return( navigator.appName.indexOf("Microsoft") != -1 && (navigator.appVersion.charAt(0)=='4') ); }
			
			function new_window(freshurl) {
				  SmallWin = window.open(freshurl, 'Survey','scrollbars=yes,resizable=yes,toolbar=no,height=600,width=800');
				  if (!isIE4())	{
				    if (window.focus) { SmallWin.focus(); }
				  }
				  if (SmallWin.opener == null) SmallWin.opener = window;
				  SmallWin.opener.name = "Main";
			}
			
			function new_serveyList(freshurl) {
				  serveyListWin = window.open(freshurl, 'SurveyRegist','scrollbars=yes,resizable=yes,toolbar=no,height=600,width=800');
				  serveyListWin.opener.name = "SurveyRegist";
			}
			
			function fn_done() {
				opener.location.href="/mgt/survey/manageSurveyMenu.do?surveyId=<%=surveyId%>";
				self.close();
			}
			
		//-->
		</script>
	</head>
<body>
<form method="post" action="http://smartmot.cafe24.com/isurvey/editEntryForm.jsp" name="form">
<input type="hidden" name="surveyId" value="<%=surveyId%>">

<div id="page-content" class="clearfix">
	<div>
		<h2><img src="/common/img/current.png" style="width: 23px;height: 29px;">&nbsp;입력양식 편집</h2>	
	</div>
	<div class="row-fluid">
	<div align="right">
	<input type="button" name="saveSurvey" value="Done" class="btn btn-small btn-warning" onclick="fn_done();">&nbsp;&nbsp;
	<input type="button" name="preview" 	value="Preview" class="btn btn-small  btn-success" onclick="JavaScript:new_window('/mgt/survey/entry.do?surveyId=<%=surveyId%>&surveyMode=2')">&nbsp;&nbsp;
	<input type="button" name="btnSelect" 	value="Survey Select" class="btn btn-small  btn-primary" onclick="JavaScript:new_serveyList('/mgt/survey/surveylist.do?pid=<%=pid%>&OrgSurveyId=<%=surveyId%>')">
	</div>
	</div>

	<div class="row-fluid">
		<br>
		<div class="tab-content">							
			<div align="left">
				<% 
				    int i=0;
				    int j=0;
				    int iQuestionCnt=0;
				    int QuestionId=0;
				    int SectionId=0;
					for (i=0;i<entryForm.getNumSections();i++){
						iQuestionCnt=iQuestionCnt+entryForm.getSection(i).getNumQuestions();
						SectionId=entryForm.getSection(i).getSectionId(); 
						
				%>
						<a name="s_0"></a>
						<br>
						<h4><%=entryForm.getSection(i).getTitle()%></h4>
					<%  
						for (j = 0; j < entryForm.getSection(i).getNumQuestions (); j++ ) {
							QuestionId=entryForm.getSection(i).getQuestion (j).getQuestionId();

					%>
					<a name="q_<%=i%>_<%=QuestionId%>"></a>
					<table cellspacing="0" cellpadding="0" border="0"  bordercolor = "#5D5D5D" width="100%">
					<tbody>
					<tr>
						<td bgcolor="#ffffff">
							<table cellspacing="1" cellpadding="2" border="0" width="100%">
							<tbody>
							<tr>
								<td bgcolor="#5D5D5D">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tbody>
									<tr>
										<td style="color : #ffffff"><%=entryForm.getSection(i).getQuestion (j).getQuestionTypeTextHTML ()%></td>
										<td nowrap="" align="right"> <a href="/mgt/survey/editQuestion.do?surveyId=<%=surveyId%>&section=<%=i%>&question=<%=QuestionId%>" style="font-size:80%;color:#ffffff">edit</a>&nbsp;&nbsp;&nbsp;
																	<a href="/mgt/survey/icopyQuestion.do?surveyId=<%=surveyId%>&section=<%=i%>&question=<%=QuestionId%>" style="font-size:80%;color:#ffffff">copy</a>&nbsp;&nbsp;&nbsp;
																	<a href="/mgt/survey/ideleteQuestion.do?surveyId=<%=surveyId%>&section=<%=i%>&question=<%=QuestionId%>" style="font-size:80%;color:#ffffff">delete</a>&nbsp;&nbsp;&nbsp; 
																	<a href="/mgt/survey/iupQuestion.do?surveyId=<%=surveyId%>&section=<%=i%>&question=<%=QuestionId%>" style="font-size:80%;color:#ffffff">move up</a>&nbsp;&nbsp;&nbsp;
																	<a href="/mgt/survey/idownQuestion.do?surveyId=<%=surveyId%>&section=<%=i%>&question=<%=QuestionId%>" style="font-size:80%;color:#ffffff">move down</a>&nbsp;&nbsp;&nbsp; 
																	<a href="/mgt/survey/addQuestion.do?surveyId=<%=surveyId%>&section=<%=i%>&question=<%=QuestionId%>&above=Y" style="font-size:80%;color:#ffffff">add question above</a>&nbsp;&nbsp;&nbsp;
																	<a href="/mgt/survey/editQuestion.do?surveyId=<%=surveyId%>&type=comment&section=<%=i%>&question=<%=QuestionId%>&above=Y" style="font-size:80%;color:#ffffff">add text above</a>
										</td>
									</tr>
									</tbody>
									</table>
							  </td>
						  </tr>
						  <tr>
							<td class="globalsettings"><%= entryForm.getSection(i).getQuestion (j).getHTML_New(survey,entryForm.modeEdit,i, QuestionId )%></td>
						  </tr>
						  </tbody>
						  </table>
					  </td>
				    </tr>
				    </tbody>
				    </table>
					<%  
						}
					%>
		
				 <br>
				 <div align = "right">
				 <a href="/mgt/survey/addQuestion.do?surveyId=<%=surveyId%>&section=<%=i%>&question=<%=QuestionId+1%>&above=N" style="font-size:80%"><input type="button" name="Add question here" value="add question here" span class="btn btn-mini btn-light"></a>&nbsp; 
				 <a href="/mgt/survey/editQuestion.do?surveyId=<%=surveyId%>&type=comment&section=<%=i%>&question=<%=QuestionId+1%>&above=N" style="font-size:80%"><input type="button" name="Add text here" value="add text here" span class="btn btn-mini btn-light"></a>&nbsp; 
				 <a href="/mgt/survey/addSection.do?surveyId=<%=surveyId%>&section=<%=i+1%>" style="font-size:80%"><input type="button" name="Add section" value="Add section" span class="btn btn-mini btn-light"></a>&nbsp; 
				 <a href="/mgt/survey/deleteSection.do?surveyId=<%=surveyId%>&section=<%=SectionId%>" style="font-size:80%"><input type="button" name="Delete Section" value="Delete Section" span class="btn btn-mini btn-light" ></a>&nbsp; 
				 <a href="/mgt/survey/editSection.do?surveyId=<%=surveyId%>&section=<%=SectionId%>" style="font-size:80%"><input type="button" name="Edit Section" value="Edit Section" span class="btn btn-mini btn-light" ></a>&nbsp; 
				 </div>
				<% 
				    }
				%>
		
			</div><!-- left -->
		</div><!-- tab-content -->
	</div><!-- row-fluid -->
	 
	<br>
	<div align="right">
	<input type="button" name="saveSurvey" value="Done" class="btn btn-small btn-warning" onclick="fn_done();">&nbsp;&nbsp;
	<input type="button" name="preview" value="Preview" class="btn btn-small  btn-success" onclick="JavaScript:new_window('/mgt/survey/entry.do?surveyId=<%=surveyId%>&surveyMode=2')">&nbsp;&nbsp;
	<input type="button" name="btnSelect" value="Survey Select" class="btn btn-small  btn-primary" onclick="JavaScript:new_serveyList('/mgt/survey/surveylist.do?pid=<%=pid%>&OrgSurveyId=<%=surveyId%>')">
	</div>
	 
</div><!--id="page-content"  -->	
</form>
</body>

</html>
