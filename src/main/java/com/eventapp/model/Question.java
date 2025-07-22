package com.eventapp.model;

import java.util.List;

public class Question {

    private String question;
    private String type; // e.g., text, yesno, checkbox
    private List<String> options; // Only used for types like dropdown, radiobox, checkbox

    // --- Getters and Setters ---

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

}
