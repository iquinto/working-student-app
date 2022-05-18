package com.iquinto.workingstudent.model.enums;

public enum DaysOfTheWeek {
    LUNES("Lunes"),
    MARTES("Martes"),
    MIERCOLES("Miercoles"),
    JUEVES("Jueves"),
    VIERNES("Viernes"),
    SABADO("Sabado"),
    DOMINGO("Domingo");


    private String text;
    DaysOfTheWeek(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }


    public static DaysOfTheWeek fromString(String text) {
        for (DaysOfTheWeek daysOfTheWeek : DaysOfTheWeek.values()) {
            if (daysOfTheWeek.text.equalsIgnoreCase(text)) {
                return daysOfTheWeek;
            }
        }
        return null;
    }
}
