<%@page import="com.whomade.kycarrots.framework.common.util.DateUtil"%>
<%@page import="com.whomade.kycarrots.framework.common.util.CommboUtil"%>
<%@page import="edu.vt.ward.survey.iSurveyEntryForm"%>
<%@ page import="com.whomade.kycarrots.framework.common.object.DataMap" %>
<%@ page import="com.whomade.kycarrots.framework.common.util.StringUtil" %>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<jsp:useBean id="param" 				class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="survey" 				class="com.whomade.kycarrots.mgt.survey.vo.TbSurvey" scope="request"/>
<jsp:useBean id="SectionList"   		type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="QuestionList"  		type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="QuestionLableList"  	type="java.util.List" class="java.util.ArrayList" scope="request"/>



<%
	String surveyId 	= param.getString("surveyId");
	System.out.println("editEntryForm surveyId["+surveyId+"]*********");

	iSurveyEntryForm entryForm = new iSurveyEntryForm (surveyId);
	entryForm.setExitSurvey(true);
	entryForm.load(SectionList,QuestionList,QuestionLableList);
	
	String pageTitle = "설문결과 내보내기";

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
		  SmallWin = window.open(freshurl, 'Survey','scrollbars=yes,resizable=yes,toolbar=no,height=400,width=600');
		  if (!isIE4())	{
		    if (window.focus) { SmallWin.focus(); }
		  }
		  if (SmallWin.opener == null) SmallWin.opener = window;
		  SmallWin.opener.name = "Main";
		}
		//-->
		</script>
	</head>
<body>
<form method="post" action="/mgt/survey/exportResults_ok.do" name="form">
 <input type="hidden" name="surveyId" value="<%=surveyId%>">
 <input type='hidden' name='userNo' value='<%=param.getString("userNo")%>'>
 <input type='hidden' name='surveyGroupId' value='<%=param.getString("surveyGroupId")%>'>
 <input type='hidden' name='surveyYear' value='<%=param.getString("surveyYear")%>'>
 <input type='hidden' name='surveyDegree' value='<%=param.getString("surveyDegree")%>'>

<div id="page-content" class="clearfix">
	<div>
		<h2><img src="/common/img/current.png" style="width: 23px;height: 29px;">&nbsp;<%=pageTitle%></h2>	
	</div>
	<div class="row-fluid">
	<div align="right">
     <input type="submit" name="export" value="OK" class="btn btn-small btn-warning">&nbsp;&nbsp;
 	 <input type="button" name="backToMenu" value="Cancel" class="btn btn-small" onClick="self.close();"><br>
	</div>
	</div>
	 
	<div class="row-fluid">
		<br>
		<div class="alert alert-info">  
		<br>
		 <b>What &quot;delimiter&quot; do you want to use to separate different questions?</b><br>
		 <label><input type="radio" name="exportDelimiter" value="semicolon" <%if ( survey.getEXPORTDELIMITER().equals (";") ) out.print("checked");%>><span class="lbl">Semicolon &quot;;&quot;<font color="#999999">(occurrences in the data will be substituted with &quot;;&quot;)</font></span></label>
		 <label><input type="radio" name="exportDelimiter" value="comma"	 <%if ( survey.getEXPORTDELIMITER().equals (",") ) out.print("checked");%>><span class="lbl">Comma &quot;,&quot; <font color="#999999">(occurrences in the data will be substituted with &quot;,&quot;)</font></span></label>
		 <label><input type="radio" name="exportDelimiter" value="pipe"		 <%if ( survey.getEXPORTDELIMITER().equals ("|") ) out.print("checked");%>><span class="lbl">Pipe symbol &quot;|&quot; <font color="#999999">(occurrences in the data will be substituted with &quot;|&quot;)</font></span></label>
		 <br>

		 <b>Do you want to include questions and labels into the data export?</b><br>
		 <label><input type="radio" name="exportIncludeQuestions" value="1" <% if ( survey.getEXPORTINCLUDEQUESTIONS().equals("1") ) out.print(" checked"); %>><span class="lbl">Yes</span></label>
		 <label><input type="radio" name="exportIncludeQuestions" value="0" <% if (!survey.getEXPORTINCLUDEQUESTIONS().equals("1") ) out.print(" checked"); %>><span class="lbl">No</span></label>
		 <br>
		 Below, select the questions you want to export:<br>
		</div>
		<div class="tab-content">							
			<div align="left">
				<% 
				    int i=0;
				    int j=0;
				    int QuestionId=0;
					StringBuffer s = new StringBuffer ( "" );
					StringBuffer rs = new StringBuffer ( "" );
					for (i=0;i<entryForm.getNumSections();i++){
				%>
						<a name="s_0"></a>
						<br>
						<h4><%=entryForm.getSection(i).getTitle()%></h4>
					<%  
						for (j = 0; j < entryForm.getSection(i).getNumQuestions (); j++ ) {
							QuestionId=entryForm.getSection(i).getQuestion(j).getQuestionId();
							 s = new StringBuffer ("" );
							 rs = new StringBuffer ("" );
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
										<% 										
											if ( !entryForm.getSection(i).getQuestion(j).getClass().getName().equals("edu.vt.ward.survey.InputComment") ) {
										        s.append ( "<label><input type=\"checkbox\" name=\"include_" + i + "_" + QuestionId + "\" value=\"1\"");
										        if ( entryForm.getSection(i).getQuestion(j).getExportInclude() )  	s.append ( " checked" );
										        	s.append ( "><span class='lbl'>include this question in the export</span></label>" );
										   	}else {
										        	s.append ( "&nbsp;" );
									    	}
										%>										
										<% 										
											if ( !entryForm.getSection(i).getQuestion(j).getClass().getName().equals("edu.vt.ward.survey.InputComment") ) {
												rs.append("<label><select style='width: 100px;margin-top: 10px;' id='summary_"+i + "_" + QuestionId+"' name='summary_"+i + "_" + QuestionId+"'  class='form-control'>");
												rs.append("<option value='0' "+StringUtil.selectSelected(entryForm.getSection(i).getQuestion(j).getSummaryCd(),"0")+" >아님</option>");
												rs.append("<option value='1' "+StringUtil.selectSelected(entryForm.getSection(i).getQuestion(j).getSummaryCd(),"1")+" >성별</option>");
												rs.append("<option value='2' "+StringUtil.selectSelected(entryForm.getSection(i).getQuestion(j).getSummaryCd(),"2")+">연령</option>");
												rs.append("<option value='3' "+StringUtil.selectSelected(entryForm.getSection(i).getQuestion(j).getSummaryCd(),"3")+">최종학력</option>");
												rs.append("<option value='4' "+StringUtil.selectSelected(entryForm.getSection(i).getQuestion(j).getSummaryCd(),"4")+">건강상태</option>");
												rs.append("<option value='5' "+StringUtil.selectSelected(entryForm.getSection(i).getQuestion(j).getSummaryCd(),"5")+">입원경로</option>");
												rs.append("<option value='6' "+StringUtil.selectSelected(entryForm.getSection(i).getQuestion(j).getSummaryCd(),"6")+">진료과목</option>");
												rs.append("<option value='7' "+StringUtil.selectSelected(entryForm.getSection(i).getQuestion(j).getSummaryCd(),"7")+">응급실유형</option>");
												rs.append("<option value='8' "+StringUtil.selectSelected(entryForm.getSection(i).getQuestion(j).getSummaryCd(),"8")+">응답자유형</option>");
												rs.append("<option value='9' "+StringUtil.selectSelected(entryForm.getSection(i).getQuestion(j).getSummaryCd(),"9")+">차원</option>");
												rs.append("<option value='10' "+StringUtil.selectSelected(entryForm.getSection(i).getQuestion(j).getSummaryCd(),"10")+">전반적만족도</option>");
												rs.append("<option value='11' "+StringUtil.selectSelected(entryForm.getSection(i).getQuestion(j).getSummaryCd(),"11")+">추천도</option>");
												rs.append("</select><span class='lbl'>summary Field</span></label>");
										   	}else {
										        	rs.append ( "&nbsp;" );
									    	}
										%>										
										<td style="color : #ffffff"><%=s.toString ()%></td>
										<td style="color"src/main/webapp/WEB-INF/jsp/com.whomade.kycarrots/mgt/emp" : #ffffff"><%=rs.toString ()%></td>
									</tr>
									</tbody>
									</table>
							  </td>
						  </tr>
						  <tr>
							<td class="globalsettings"><%= entryForm.getSection(i).getQuestion (j).getHTML_New(survey,entryForm.modeResultsExport,i, QuestionId )%></td>
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
				<% 
				    }
				%>
		
			</div><!-- left -->
		</div><!-- tab-content -->
	</div><!-- row-fluid -->
	 
	 
	<br>
	<div align="right">
     <input type="submit" name="export" value="OK" class="btn btn-small btn-warning">&nbsp;&nbsp;
 	 <input type="button" name="backToMenu" value="Cancel" class="btn btn-small" onClick="self.close();"><br>
	</div>
	 
</div><!--id="page-content"  -->	
</form>
</body>
</html>