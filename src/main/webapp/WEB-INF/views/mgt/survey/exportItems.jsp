<%@page import="com.whomade.kycarrots.framework.common.util.*"%>
<%@page import="com.whomade.kycarrots.framework.common.object.DataMap"%>
<%@page import="edu.vt.ward.survey.iSurveyEntryForm"%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<jsp:useBean id="param" 				class="com.whomade.kycarrots.framework.common.object.DataMap" scope="request"/>
<jsp:useBean id="survey" 				class="com.whomade.kycarrots.mgt.survey.vo.TbSurvey" scope="request"/>
<jsp:useBean id="SectionList"   		type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="QuestionList"  		type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="QuestionLableList"  	type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="SectionUserList"   	type="java.util.List" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="QuestionUserList"  	type="java.util.List" class="java.util.ArrayList" scope="request"/>



<%
	String surveyId 	= param.getString("surveyId");
	System.out.println("editEntryForm surveyId["+surveyId+"]*********");

	iSurveyEntryForm entryForm = new iSurveyEntryForm (surveyId);
	entryForm.setExitSurvey(true);
	entryForm.load(SectionList,QuestionList,QuestionLableList);
	
	String pageTitle = "설문항목 레포트용";

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
    	<script src="/common/assets/js/jquery-1.9.1.min.js"></script>
		
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

		function f_savechk() {
			var rSectionTit="";
			$("input[id=sectioninclude]").each(function(index, item){
				if(item.checked) {
					rSectionTit = $("input[id=rsection]:eq("+index+")").val() ;
					if(rSectionTit==""){
						alert("체크가 되어있는 섹션의 타이틀을 입력하셔야합니다.\n"+(index+1)+"번 섹션타이틀을 확인하세요");
						return false;
					}
					
					//question체크
					var i=0;
					var rquestion = $("input[id=rinclude"+index+"]");
					$(rquestion).each(function(qindex, item){
						console.log('rinclude1['+qindex+"]");
						rQuestionTit = $("input[id=rquestion_"+index+"]:eq("+qindex+")").val() ;
						if(rQuestionTit==""){
							alert("체크가 되어있는 질문의 타이틀을 입력하셔야합니다.\n"+(index+1)+"번섹션 "+(qindex+1)+"번 질문타이틀을 확인하세요");
							i=1;
							return false;
						}

					})
					if(i==1) return false;
					
					//console.log('rSectionTit['+rSectionTit+"]");
				}
				
			})
			
			$("form").submit();
		}

		//-->
		</script>
	</head>
<body>
<form method="post" action="/mgt/survey/exportItems_ok.do" name="form" id="form">
<input type="hidden" name="surveyId" value="<%=surveyId%>">
<input type='hidden' name='USER_NO' 		value='<%=param.getString("userNo")%>'>
<input type='hidden' name='SURVEY_GROUP_ID' value='<%=param.getString("surveyGroupId")%>'>
<input type='hidden' name='SURVEY_YEAR' 	value='<%=param.getString("surveyYear")%>'>
<input type='hidden' name='SURVEY_DEGREE' 	value='<%=param.getString("surveyDegree")%>'>

<div id="page-content" class="clearfix">
	<div>
		<h2><img src="/common/img/current.png" style="width: 23px;height: 29px;">&nbsp;<%=pageTitle%></h2>	
	</div>
	<div class="row-fluid">
	<div align="right">
     <input type="button" name="export" value="OK" class="btn btn-small btn-warning" onClick="f_savechk();">&nbsp;&nbsp;
 	 <input type="button" name="backToMenu" value="Cancel" class="btn btn-small" onClick="self.close();"><br>
	</div>
	</div>
	 
	<div class="row-fluid">
		<br>
		<div class="tab-content">							
			<div align="left">
				<% 
				    int i=0;
				    int j=0;
				    int QuestionId=0;
				    String sectionTitleR="";
				    String sectionCont="";
				    String sectionEnd="";
				    String sectionInclude="";
				    String sectionChecked="";
				    String questionTitleR="";
				    String questionInclude="";
				    String questionChecked="";
				    DataMap sectionMap = new DataMap();
				    DataMap questionMap = new DataMap();
				    
				    StringBuffer s = new StringBuffer ( "" );
					StringBuffer rs = new StringBuffer ( "" );
					for (i=0;i<entryForm.getNumSections();i++){
						sectionMap=StringUtil.sectionUser(i,entryForm.getSection(i).getTitle(),SectionUserList);
						sectionTitleR=sectionMap.getString("SECTION_RTITLE");
					    sectionCont=sectionMap.getString("SECTION_CONT");;
					    sectionEnd=sectionMap.getString("SECTION_END");;
					    sectionInclude=sectionMap.getString("EXPORTINCLUDER");
					    if("1".equals(sectionInclude)) sectionChecked="checked";
					    else sectionChecked="";
				%>
						<a name="s_0"></a>
						<br>
						<h4>
						<label><input type='checkbox' id='sectioninclude' name='sectioninclude_<%=i%>' value='1' <%=sectionChecked%>><span class='lbl'>include this section in the export</span></label>
						<%=entryForm.getSection(i).getTitle()%> 
						<input type='text' style='width:300px;margin-top: 10px;' id='rsection' name='section_<%=i%>' value="<%=sectionTitleR%>" class='form-control'>
						</h4>
						<h5>
						<b>세션주용내용:</b> <font color="#999999"></font><br>
						<textarea id='section_cont' name="section_cont_<%=i%>" wrap="physical" cols="60" class='span12' rows="3"><%=sectionCont%></textarea><br><br>
						</h5>						
					<%  
						for (j = 0; j < entryForm.getSection(i).getNumQuestions (); j++ ) {
							QuestionId=entryForm.getSection(i).getQuestion(j).getQuestionId();
							questionMap=StringUtil.questionUser(i,QuestionId,entryForm.getSection(i).getQuestion(j).getText(),QuestionUserList);
							questionTitleR = questionMap.getString("QUESTION_RTITLE");
							questionInclude= questionMap.getString("EXPORTINCLUDER");
						    if("1".equals(questionInclude)) questionChecked="checked";
						    else questionChecked="";

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
										        s.append ( "<label><input type=\"checkbox\" id='rinclude"+i+"' name=\"include_" + i + "_" + QuestionId + "\" value=\"1\"");
										        s.append ( " "+questionChecked);
										        s.append ( "><span class='lbl'>include this question in the export</span></label>" );
										   	}else {
										        	s.append ( "&nbsp;" );
									    	}
										%>										
										<% 										
											if ( !entryForm.getSection(i).getQuestion(j).getClass().getName().equals("edu.vt.ward.survey.InputComment") ) {
												rs.append("<label><input type='text' style='width:300px;margin-top: 10px;' id='rquestion_"+i+"' name='rquestion_"+i + "_" + QuestionId+"'  class='form-control' value='"+questionTitleR+"'>");
												rs.append("<span class='lbl' style='color: #FFFFFF;'>Question Title </span></label>");
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

					<h5>
					<b>세션마무리내용:</b> <font color="#999999"></font><br>
					<textarea name="section_end_<%=i%>" wrap="physical" cols="60" class='span12' rows="3"><%=sectionEnd%></textarea><br><br>
					</h5>						
					
				<% 
				    }
				%>
		
			</div><!-- left -->
		</div><!-- tab-content -->
	</div><!-- row-fluid -->
	 
	 
	<br>
	<div align="right">
     <input type="button" name="export" value="OK" class="btn btn-small btn-warning" onClick="f_savechk();">&nbsp;&nbsp;
 	 <input type="button" name="backToMenu" value="Cancel" class="btn btn-small" onClick="self.close();"><br>
	</div>
	 
</div><!--id="page-content"  -->	
</form>
</body>
</html>