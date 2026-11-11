package mx.edu.utez.CBRR_Edwin_Martinez_CC7.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
