package com.whomade.kycarrots.mgt.survey.vo;

import com.whomade.kycarrots.framework.common.object.DataMap;

import java.util.ArrayList;
import java.util.List;




public class TbStatistics {

	private	String SURVEY_ID ;
	private	int SECTION_ID_1;                    
	private	int QUESTION_ID_1;           
	private	int SECTION_ID_2;                    
	private	int QUESTION_ID_2;           
	private	int SECTION_ID_3;                    
	private	int QUESTION_ID_3;           
	private	int SECTION_ID_4;                    
	private	int QUESTION_ID_4;           
	private	int SECTION_ID_5;                    
	private	int QUESTION_ID_5;           
	private	int SECTION_ID_6;                    
	private	int QUESTION_ID_6;           
	private	int SECTION_ID_7;                    
	private	int QUESTION_ID_7;           
	private	int SECTION_ID_8;                    
	private	int QUESTION_ID_8;           
	private	int SECTION_ID_9;                    
	private	int QUESTION_ID_9;           

	private List<DataMap> dataMap = new  ArrayList<>();
	
	public TbStatistics(List<DataMap> dataMap,String SURVEY_ID) {
		this.SURVEY_ID=SURVEY_ID;
		this.dataMap=dataMap;
		
		for(DataMap aQuestion:this.dataMap){
			if(aQuestion.getString("SUMMARY_CD").equals("1")){
				SECTION_ID_1=aQuestion.getInt("SECTION_ID");
				QUESTION_ID_1=aQuestion.getInt("QUESTION_ID");
			}
			if(aQuestion.getString("SUMMARY_CD").equals("2")){
				SECTION_ID_2=aQuestion.getInt("SECTION_ID");
				QUESTION_ID_2=aQuestion.getInt("QUESTION_ID");
			}
			if(aQuestion.getString("SUMMARY_CD").equals("3")){
				SECTION_ID_3=aQuestion.getInt("SECTION_ID");
				QUESTION_ID_3=aQuestion.getInt("QUESTION_ID");
			}
			if(aQuestion.getString("SUMMARY_CD").equals("4")){
				SECTION_ID_4=aQuestion.getInt("SECTION_ID");
				QUESTION_ID_4=aQuestion.getInt("QUESTION_ID");
			}
			if(aQuestion.getString("SUMMARY_CD").equals("5")){
				SECTION_ID_5=aQuestion.getInt("SECTION_ID");
				QUESTION_ID_5=aQuestion.getInt("QUESTION_ID");
			}
			if(aQuestion.getString("SUMMARY_CD").equals("6")){
				SECTION_ID_6=aQuestion.getInt("SECTION_ID");
				QUESTION_ID_6=aQuestion.getInt("QUESTION_ID");
			}
			if(aQuestion.getString("SUMMARY_CD").equals("7")){
				SECTION_ID_7=aQuestion.getInt("SECTION_ID");
				QUESTION_ID_7=aQuestion.getInt("QUESTION_ID");
			}
			if(aQuestion.getString("SUMMARY_CD").equals("8")){
				SECTION_ID_8=aQuestion.getInt("SECTION_ID");
				QUESTION_ID_8=aQuestion.getInt("QUESTION_ID");
			}
			if(aQuestion.getString("SUMMARY_CD").equals("9")){
				SECTION_ID_9=aQuestion.getInt("SECTION_ID");
				QUESTION_ID_9=aQuestion.getInt("QUESTION_ID");
			}
		}

	}

	public String getSURVEY_ID() {
		return SURVEY_ID;
	}

	public void setSURVEY_ID(String sURVEY_ID) {
		SURVEY_ID = sURVEY_ID;
	}

	public int getSECTION_ID_1() {
		return SECTION_ID_1;
	}

	public void setSECTION_ID_1(int sECTION_ID_1) {
		SECTION_ID_1 = sECTION_ID_1;
	}

	public int getQUESTION_ID_1() {
		return QUESTION_ID_1;
	}

	public void setQUESTION_ID_1(int qUESTION_ID_1) {
		QUESTION_ID_1 = qUESTION_ID_1;
	}

	public int getSECTION_ID_2() {
		return SECTION_ID_2;
	}

	public void setSECTION_ID_2(int sECTION_ID_2) {
		SECTION_ID_2 = sECTION_ID_2;
	}

	public int getQUESTION_ID_2() {
		return QUESTION_ID_2;
	}

	public void setQUESTION_ID_2(int qUESTION_ID_2) {
		QUESTION_ID_2 = qUESTION_ID_2;
	}

	public int getSECTION_ID_3() {
		return SECTION_ID_3;
	}

	public void setSECTION_ID_3(int sECTION_ID_3) {
		SECTION_ID_3 = sECTION_ID_3;
	}

	public int getQUESTION_ID_3() {
		return QUESTION_ID_3;
	}

	public void setQUESTION_ID_3(int qUESTION_ID_3) {
		QUESTION_ID_3 = qUESTION_ID_3;
	}

	public int getSECTION_ID_4() {
		return SECTION_ID_4;
	}

	public void setSECTION_ID_4(int sECTION_ID_4) {
		SECTION_ID_4 = sECTION_ID_4;
	}

	public int getQUESTION_ID_4() {
		return QUESTION_ID_4;
	}

	public void setQUESTION_ID_4(int qUESTION_ID_4) {
		QUESTION_ID_4 = qUESTION_ID_4;
	}

	public int getSECTION_ID_5() {
		return SECTION_ID_5;
	}

	public void setSECTION_ID_5(int sECTION_ID_5) {
		SECTION_ID_5 = sECTION_ID_5;
	}

	public int getQUESTION_ID_5() {
		return QUESTION_ID_5;
	}

	public void setQUESTION_ID_5(int qUESTION_ID_5) {
		QUESTION_ID_5 = qUESTION_ID_5;
	}

	public int getSECTION_ID_6() {
		return SECTION_ID_6;
	}

	public void setSECTION_ID_6(int sECTION_ID_6) {
		SECTION_ID_6 = sECTION_ID_6;
	}

	public int getQUESTION_ID_6() {
		return QUESTION_ID_6;
	}

	public void setQUESTION_ID_6(int qUESTION_ID_6) {
		QUESTION_ID_6 = qUESTION_ID_6;
	}

	public int getSECTION_ID_7() {
		return SECTION_ID_7;
	}

	public void setSECTION_ID_7(int sECTION_ID_7) {
		SECTION_ID_7 = sECTION_ID_7;
	}

	public int getQUESTION_ID_7() {
		return QUESTION_ID_7;
	}

	public void setQUESTION_ID_7(int qUESTION_ID_7) {
		QUESTION_ID_7 = qUESTION_ID_7;
	}

	public int getSECTION_ID_8() {
		return SECTION_ID_8;
	}

	public void setSECTION_ID_8(int sECTION_ID_8) {
		SECTION_ID_8 = sECTION_ID_8;
	}

	public int getQUESTION_ID_8() {
		return QUESTION_ID_8;
	}

	public void setQUESTION_ID_8(int qUESTION_ID_8) {
		QUESTION_ID_8 = qUESTION_ID_8;
	}

	public int getSECTION_ID_9() {
		return SECTION_ID_9;
	}

	public void setSECTION_ID_9(int sECTION_ID_9) {
		SECTION_ID_9 = sECTION_ID_9;
	}

	public int getQUESTION_ID_9() {
		return QUESTION_ID_9;
	}

	public void setQUESTION_ID_9(int qUESTION_ID_9) {
		QUESTION_ID_9 = qUESTION_ID_9;
	}
	
	
	
}