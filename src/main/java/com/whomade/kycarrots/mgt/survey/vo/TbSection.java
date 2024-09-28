package com.whomade.kycarrots.mgt.survey.vo;

import java.util.ArrayList;


public class TbSection {

	private	String SURVEY_ID ;
	private	int SECTION_ID ;
	private	String SECTION_TITLE ;
	private	String SECTION_HTMLTITLE ;
	
    private ArrayList<TbQuestion> QUESTIONS = new ArrayList<TbQuestion>();
	
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
	public String getSECTION_TITLE() {
		return SECTION_TITLE;
	}
	public void setSECTION_TITLE(String sECTION_TITLE) {
		SECTION_TITLE = sECTION_TITLE;
	}
	public String getSECTION_HTMLTITLE() {
		return SECTION_HTMLTITLE;
	}
	public void setSECTION_HTMLTITLE(String sECTION_HTMLTITLE) {
		SECTION_HTMLTITLE = sECTION_HTMLTITLE;
	}
	public ArrayList<TbQuestion> getQUESTIONS() {
		return QUESTIONS;
	}
	public void setQUESTIONS(ArrayList<TbQuestion> tbQuestions) {
		this.QUESTIONS = tbQuestions;
	}
	
	public void addQUESTIONS(TbQuestion tbQuestions) {
		this.QUESTIONS.add(tbQuestions);
	}

	public void setQUESTIONS(TbQuestion tbQuestion,int pindex) {
		this.QUESTIONS.set(pindex,tbQuestion);
	}
	

	
	
}