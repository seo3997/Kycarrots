package com.whomade.kycarrots.mgt.survey.vo;


public class TbQuestionLabel {

	private	String SURVEY_ID;                     
	private	int SECTION_ID;                   
	private	int QUESTION_ID;                  
	private	int QUESTION_LABEL_ID;			
	private	String QUESTION_LABEL;              
	private	String EXPORTCODE; 
	private String SELECTED;
	private	long RESULT_CNT;
	
	
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
	public int getQUESTION_LABEL_ID() {
		return QUESTION_LABEL_ID;
	}
	public void setQUESTION_LABEL_ID(int qUESTION_LABEL_ID) {
		QUESTION_LABEL_ID = qUESTION_LABEL_ID;
	}
	public String getQUESTION_LABEL() {
		return QUESTION_LABEL;
	}
	public void setQUESTION_LABEL(String qUESTION_LABEL) {
		QUESTION_LABEL = qUESTION_LABEL;
	}
	public String getEXPORTCODE() {
		return EXPORTCODE;
	}
	public void setEXPORTCODE(String eXPORTCODE) {
		EXPORTCODE = eXPORTCODE;
	}
	public String getSELECTED() {
		return SELECTED;
	}
	public void setSELECTED(String sELECTED) {
		SELECTED = sELECTED;
	}
	public long getRESULT_CNT() {
		return RESULT_CNT;
	}
	public void setRESULT_CNT(long rESULT_CNT) {
		RESULT_CNT = rESULT_CNT;
	}

	
	
	
	
}