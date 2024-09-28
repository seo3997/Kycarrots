package com.whomade.kycarrots.mgt.survey.vo;

import java.util.ArrayList;


public class TbResults {
	private	int RESULTS_ID;
	private	String SURVEY_ID;                     
	private	int SECTION_ID;                   
	private	int QUESTION_ID;                  
	private	String QUESTION_RESULT;	
	private	String OTHERANSWER_RESULT;
	private	String INSERT_DATE;
	private	String QUESTION_TYPE;
	private String OTHERANSWER;
	
	private ArrayList<TbResultsLabel> tbResultsLabels = new ArrayList<TbResultsLabel>();
	
	
	public int getRESULTS_ID() {
		return RESULTS_ID;
	}
	public void setRESULTS_ID(int rESULTS_ID) {
		RESULTS_ID = rESULTS_ID;
	}
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
	public String getINSERT_DATE() {
		return INSERT_DATE;
	}
	public void setINSERT_DATE(String iNSERT_DATE) {
		INSERT_DATE = iNSERT_DATE;
	}
	public ArrayList<TbResultsLabel> getTbResultsLabels() {
		return tbResultsLabels;
	}
	public void setTbResultsLabels(ArrayList<TbResultsLabel> tbResultsLabels) {
		this.tbResultsLabels = tbResultsLabels;
	}
	public String getQUESTION_TYPE() {
		return QUESTION_TYPE;
	}
	public void setQUESTION_TYPE(String qUESTION_TYPE) {
		QUESTION_TYPE = qUESTION_TYPE;
	}
	public String getOTHERANSWER() {
		return OTHERANSWER;
	}
	public void setOTHERANSWER(String oTHERANSWER) {
		OTHERANSWER = oTHERANSWER;
	}
	
	
}