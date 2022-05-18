package com.iquinto.workingstudent.model.enums;

public enum State {
    AVAILABLE("available"),
    SCHEDULED("scheduled"),
    CONTRACTED("contracted"),
    RESERVED("reserve");

    private String text;
    State(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

}
