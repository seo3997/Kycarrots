package com.whomade.kycarrots.mgt.survey.vo;

import java.util.ArrayList;




public class TbSatisfaction {
	private	String SURVEY_ID;												//설문ID
	private	 int SECTION_ID;													//섹션ID
	private	String SECTION_TITLE;											//섹션타이틀
	private	String SECTION_CONT;										   //섹션머리말
	private	String SECTION_END;											//섹션마무리

	private ArrayList<TbSatisfactionItem> tbSatisfactionItem = new ArrayList<TbSatisfactionItem>();

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

	public String getSECTION_CONT() {
		return SECTION_CONT;
	}

	public void setSECTION_CONT(String sECTION_CONT) {
		SECTION_CONT = sECTION_CONT;
	}

	public String getSECTION_END() {
		return SECTION_END;
	}

	public void setSECTION_END(String sECTION_END) {
		SECTION_END = sECTION_END;
	}

	public ArrayList<TbSatisfactionItem> getTbSatisfactionItem() {
		return tbSatisfactionItem;
	}

	public void setTbSatisfactionItem(
			ArrayList<TbSatisfactionItem> tbSatisfactionItem) {
		this.tbSatisfactionItem = tbSatisfactionItem;
	}
	
	
}