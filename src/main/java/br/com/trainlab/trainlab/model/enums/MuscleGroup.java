package br.com.trainlab.trainlab.model.enums;

public enum MuscleGroup {
    CHEST("Peito"),
    BACK("Costas"),
    LEGS("Pernas"),
    SHOULDERS("Ombros"),
    BICEPS("Bíceps"),
    TRICEPS("Tríceps"),
    ABS("Abdômen"),
    GLUTES("Glúteos");

    private String portuguese;

    MuscleGroup(String portuguese){
        this.portuguese = portuguese;
    }

    public String getPortuguese() {
        return portuguese;
    }
}
