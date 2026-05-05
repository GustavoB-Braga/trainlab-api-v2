package br.com.trainlab.trainlab.model.enums;

public enum WorkoutType {
    HYPERTROPHY("Hipertrofia"),
    CARDIO("Cardio"),
    FUNCTIONAL("Funcional"),
    STRENGTH("Força"),
    ENDURANCE("Resistência");

    private String portuguese;

    WorkoutType(String portuguese){
        this.portuguese = portuguese;
    }

    public String getPortuguese() {
        return portuguese;
    }
}
