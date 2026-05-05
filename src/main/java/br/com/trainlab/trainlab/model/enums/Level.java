package br.com.trainlab.trainlab.model.enums;

public enum Level {
    BEGINNER("Iniciante"),
    INTERMEDIATE("Intermediário"),
    ADVANCED("Avançado");


    private String portuguese;

    Level(String portuguese) {
        this.portuguese = portuguese;
    }

    public String getPortuguese() {
        return portuguese;
    }
}
