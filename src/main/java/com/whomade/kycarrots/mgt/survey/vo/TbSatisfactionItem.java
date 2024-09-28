package com.whomade.kycarrots.mgt.survey.vo;

import java.util.ArrayList;



public class TbSatisfactionItem {
	private	String SURVEY_ID;												//설문ID
	private	 int SECTION_ID;													//섹션ID
	private	 int QUESTION_ID;												//질문ID
	private	String QUESTION_LABEL;										//설문Title

	private ArrayList<TbSurveyUserEntryResult> tbSurveyUserEntryResult = new ArrayList<TbSurveyUserEntryResult>();		//항목점수리스트
	
	
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
	public String getQUESTION_LABEL() {
		return QUESTION_LABEL;
	}
	public void setQUESTION_LABEL(String qUESTION_LABEL) {
		QUESTION_LABEL = qUESTION_LABEL;
	}
	public ArrayList<TbSurveyUserEntryResult> getTbSurveyUserEntryResult() {
		return tbSurveyUserEntryResult;
	}
	public void setTbSurveyUserEntryResult(
			ArrayList<TbSurveyUserEntryResult> tbSurveyUserEntryResult) {
		this.tbSurveyUserEntryResult = tbSurveyUserEntryResult;
	}

	
	
}