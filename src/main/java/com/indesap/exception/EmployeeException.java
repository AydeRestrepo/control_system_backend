package com.indesap.exception;

public class EmployeeException extends RuntimeException {

    private final ErrorType errorType;

    public EmployeeException(ErrorType errorType) {
        super(resolveMessage(errorType));
        this.errorType =errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    private static String resolveMessage(ErrorType errorType) {
        switch (errorType) {
            case USER_EXISTING:
                return "El usuario ya existe.";
            default:
                return "Desconocido.";
        }
    }}
