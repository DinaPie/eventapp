package com.eventapp.dto;

import java.util.List;

public class AnswerDto {
    private String questionId;
    private String answer; // For text, number, dropdown, radio, yes/no
    private List<String> answerList; // For checkbox

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<String> answerList) {
        this.answerList = answerList;
    }
}

