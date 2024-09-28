package com.whomade.kycarrots.mgt.survey.dto;

import java.util.ArrayList;
import java.util.List;

public class SectionDragDto {
	private String surveyId;
	private ArrayList<SectionPosition> sectionListPositions;
	 
	public String getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
	public ArrayList<SectionPosition> getSectionListPositions() {
		return sectionListPositions;
	}
	public void setSectionListPositions(ArrayList<SectionPosition> sectionListPositions) {
		this.sectionListPositions = sectionListPositions;
	}

}
