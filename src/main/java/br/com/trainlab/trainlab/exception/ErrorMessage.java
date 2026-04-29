package br.com.trainlab.trainlab.exception;

public enum ErrorMessage {
    USER_NOT_FOUND("USER_NOT_FOUND", "Usuário não encontrado"),
    WORKOUT_NOT_FOUND("WORKOUT_NOT_FOUND", "Treino não encontrado"),
    EXERCISE_NOT_FOUND("EXERCISE_NOT_FOUND", "Exercício não encontrado"),
    INVALID_CREDENTIALS("INVALID_CREDENTIALS", "Usuário ou senha invalidos");



    private String code;
    private String message;

    ErrorMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}
