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

	response.setContentType("text/plain");
	response.setHeader("Content-disposition","inline; filename=" + survey.getSURVEY_TITLE() + ".txt");
%>
<%
  // create table header
   out.print("Nr");
   out.print( survey.getEXPORTDELIMITER() );
   out.print( "Date" );
 
   for (int irow=0;irow<entryForm.getNumSections();irow++){
 	    iSection sectionEntryForm = entryForm.getSection ( irow );
	 	for ( int qi = 0; qi < sectionEntryForm.getNumQuestions (); qi++ ) {
	    Question question = sectionEntryForm.getQuestion ( qi );
	
	    if ( !question.getClass().getName ().equals( "edu.vt.ward.survey.InputComment" ) ) {
	      String questionText = question.getText ();
	      // if there's no question text but a label, take that instead
	      if ( question.getClass().getName ().equals( "edu.vt.ward.survey.InputTextline" ) ) {
	        questionText += ((InputTextline) question).getLabel ();
	      }
	      
          String text = StringUtil.translateExportDelimiters ( HTMLUtils.newLine2Space ( questionText ), survey.getEXPORTDELIMITER() );

          out.print(survey.getEXPORTDELIMITER());
	      out.print(questionText);
	    } // end: if ( !question.getClass().getName ().equals( "edu.vt.ward.survey.InputComment" ) )
	   }
   }
   
   out.print( "\n" );
%>
<%
    int RESULTS_ID=-1;
    int irow=0;
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
					aResult.add(entryForm.getExportStr(jTbResult,survey.getEXPORTDELIMITER()));
				}
			  }
		      out.print(irow+1);
		      out.print(survey.getEXPORTDELIMITER());
		      out.print(aTbResult.getINSERT_DATE());
		   	 for(int j=0;j<aResult.size();j++){
			    out.print(survey.getEXPORTDELIMITER());
		   		out.print(HTMLUtils.newLine2Space(aResult.get(j)));
			}
			out.print( "\n" );
			irow++;
		}
	}
%>


