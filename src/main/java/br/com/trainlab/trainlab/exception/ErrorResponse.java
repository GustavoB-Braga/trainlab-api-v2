package br.com.trainlab.trainlab.exception;

public record ErrorResponse(java.time.LocalDate now, int value, String resourceNotFound, String message) {
}
