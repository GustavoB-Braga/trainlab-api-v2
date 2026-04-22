package br.com.trainlab.trainlab.exception;

public record ErrorResponse(java.time.LocalDateTime now, int value, String resourceNotFound, String message) {
}
