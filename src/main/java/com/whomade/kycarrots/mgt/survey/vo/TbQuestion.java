package com.whomade.kycarrots.mgt.survey.vo;

import java.util.ArrayList;


public class TbQuestion {

	private	String SURVEY_ID;                     
	private	int SECTION_ID;                    
	private	int QUESTION_ID;           
	private String QUESTION_TYPE;
	private	String SHOWDIVIDER; 					
	private	String EXPORTINCLUDE;                 
	private	String EXPORTEXPAND	;				
	private	String QUESTIONTEXT;                	
	private	String QUESTION_CD;                   
	private	String QUESTION_ISHTML;				
	private	String QUESTION_LAYOUT;               
	private	String QUESTION_LABEL;                
	private	String OTHERANSWER;		
	private	String TEXTCOLS;               	 	  
	private	String TEXTROWS;               	 	  
	private	String TEXTSIZE;               	 	  
	private	String MAXLENGTH;
	private	long RESULT_CNT;
	private	long OTHERANSWER_RESULT_CNT;
	private	String QUESTION_RESULT;	
	private	String OTHERANSWER_RESULT;
	private	String SUMMARY_CD;
	private	String SUMMARY_NM;
	private	String QUESTION_TYPETEXT_HTML;
	private	String HTML_NEW;
	
	
    private ArrayList<TbQuestionLabel> QUESTIONLABELS = new ArrayList<TbQuestionLabel>();

	
	
	public String getSURVEY_ID() {
		return SURVEY_ID;
	}
	public void setSURVEY_ID(String sURVEY_ID) {
		SURVEY_ID = sURVEY_ID;
	}
	
	
	public int getSECTION_ID() {
		return SECTION_ID;
	}
	public void setSECTION_ID(int sECTION_ID) {
		SECTION_ID = sECTION_ID;
	}
	public int getQUESTION_ID() {
		return QUESTION_ID;
	}
	public void setQUESTION_ID(int qUESTION_ID) {
		QUESTION_ID = qUESTION_ID;
	}
	public String getQUESTION_TYPE() {
		return QUESTION_TYPE;
	}
	public void setQUESTION_TYPE(String qUESTION_TYPE) {
		QUESTION_TYPE = qUESTION_TYPE;
	}
	public String getSHOWDIVIDER() {
		return SHOWDIVIDER;
	}
	public void setSHOWDIVIDER(String sHOWDIVIDER) {
		SHOWDIVIDER = sHOWDIVIDER;
	}
	public String getEXPORTINCLUDE() {
		return EXPORTINCLUDE;
	}
	public void setEXPORTINCLUDE(String eXPORTINCLUDE) {
		EXPORTINCLUDE = eXPORTINCLUDE;
	}
	public String getEXPORTEXPAND() {
		return EXPORTEXPAND;
	}
	public void setEXPORTEXPAND(String eXPORTEXPAND) {
		EXPORTEXPAND = eXPORTEXPAND;
	}
	public String getQUESTIONTEXT() {
		return QUESTIONTEXT;
	}
	public void setQUESTIONTEXT(String qUESTIONTEXT) {
		QUESTIONTEXT = qUESTIONTEXT;
	}
	public String getQUESTION_CD() {
		return QUESTION_CD;
	}
	public void setQUESTION_CD(String qUESTION_CD) {
		QUESTION_CD = qUESTION_CD;
	}
	public String getQUESTION_ISHTML() {
		return QUESTION_ISHTML;
	}
	public void setQUESTION_ISHTML(String qUESTION_ISHTML) {
		QUESTION_ISHTML = qUESTION_ISHTML;
	}
	public String getQUESTION_LAYOUT() {
		return QUESTION_LAYOUT;
	}
	public void setQUESTION_LAYOUT(String qUESTION_LAYOUT) {
		QUESTION_LAYOUT = qUESTION_LAYOUT;
	}
	public String getQUESTION_LABEL() {
		return QUESTION_LABEL;
	}
	public void setQUESTION_LABEL(String qUESTION_LABEL) {
		QUESTION_LABEL = qUESTION_LABEL;
	}
	public String getOTHERANSWER() {
		return OTHERANSWER;
	}
	public void setOTHERANSWER(String oTHERANSWER) {
		OTHERANSWER = oTHERANSWER;
	}
	public String getTEXTCOLS() {
		return TEXTCOLS;
	}
	public void setTEXTCOLS(String tEXTCOLS) {
		TEXTCOLS = tEXTCOLS;
	}
	public String getTEXTROWS() {
		return TEXTROWS;
	}
	public void setTEXTROWS(String tEXTROWS) {
		TEXTROWS = tEXTROWS;
	}
	public String getTEXTSIZE() {
		return TEXTSIZE;
	}
	public void setTEXTSIZE(String tEXTSIZE) {
		TEXTSIZE = tEXTSIZE;
	}
	public String getMAXLENGTH() {
		return MAXLENGTH;
	}
	public void setMAXLENGTH(String mAXLENGTH) {
		MAXLENGTH = mAXLENGTH;
	}
	public ArrayList<TbQuestionLabel> getQUESTIONLABELS() {
		return QUESTIONLABELS;
	}
	public void setQUESTIONLABELS(ArrayList<TbQuestionLabel> tbQuestionLabels) {
		this.QUESTIONLABELS = tbQuestionLabels;
	}

	public long getRESULT_CNT() {
		return RESULT_CNT;
	}
	public void setRESULT_CNT(long rESULT_CNT) {
		RESULT_CNT = rESULT_CNT;
	}
	public String getQUESTION_RESULT() {
		return QUESTION_RESULT;
	}
	public void setQUESTION_RESULT(String qUESTION_RESULT) {
		QUESTION_RESULT = qUESTION_RESULT;
	}
	public String getOTHERANSWER_RESULT() {
		return OTHERANSWER_RESULT;
	}
	public void setOTHERANSWER_RESULT(String oTHERANSWER_RESULT) {
		OTHERANSWER_RESULT = oTHERANSWER_RESULT;
	}
	public long getOTHERANSWER_RESULT_CNT() {
		return OTHERANSWER_RESULT_CNT;
	}
	public void setOTHERANSWER_RESULT_CNT(long oTHERANSWER_RESULT_CNT) {
		OTHERANSWER_RESULT_CNT = oTHERANSWER_RESULT_CNT;
	}
	public String getSUMMARY_CD() {
		return SUMMARY_CD;
	}
	public void setSUMMARY_CD(String sUMMARY_CD) {
		SUMMARY_CD = sUMMARY_CD;
	}
	public String getSUMMARY_NM() {
		return SUMMARY_NM;
	}
	public void setSUMMARY_NM(String sUMMARY_NM) {
		SUMMARY_NM = sUMMARY_NM;
	}
	public String getQUESTION_TYPETEXT_HTML() {
		return QUESTION_TYPETEXT_HTML;
	}
	public void setQUESTION_TYPETEXT_HTML(String qUESTION_TYPETEXT_HTML) {
		QUESTION_TYPETEXT_HTML = qUESTION_TYPETEXT_HTML;
	}
	public String getHTML_NEW() {
		return HTML_NEW;
	}
	public void setHTML_NEW(String hTML_NEW) {
		HTML_NEW = hTML_NEW;
	}

	
	
}