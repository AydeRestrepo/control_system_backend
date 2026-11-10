package com.indesap.exception;

public class SessionException extends RuntimeException {

    private final ErrorType errorType;

    public SessionException(ErrorType errorType) {
       super(resolveMessage(errorType));
       this.errorType =errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    private static String resolveMessage(ErrorType errorType) {
        switch (errorType) {
            case CODE_NOT_FOUND:
                return "Código no existe.";
            case SESSION_ALREADY_ACTIVE:
                return "Ya tienes una jornada activa.";
            case WITHOUT_SESSION_ACTIVE:
                return "No tienes jornada activa para terminar.";
            default:
                return "Desconocido.";
        }
    }
}
