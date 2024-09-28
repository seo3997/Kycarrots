package com.whomade.kycarrots.mgt.survey.dto;

import java.util.ArrayList;

public class QuestionDragDto {
    private String surveyId;
    private ArrayList<QuestionPosition> questionListPositions;

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public ArrayList<QuestionPosition> getQuestionListPositions() {
        return questionListPositions;
    }

    public void setQuestionListPositions(ArrayList<QuestionPosition> questionListPositions) {
        this.questionListPositions = questionListPositions;
    }
}