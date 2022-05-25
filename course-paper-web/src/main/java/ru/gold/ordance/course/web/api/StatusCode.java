package ru.gold.ordance.course.web.api;

public enum StatusCode {
    SUCCESS("Request completed."),
    INVALID_RQ("Request validation failed."),
    VIOLATES_CONSTRAINT("Request violates the database constraint."),
    UNAUTHORIZED("Request contains incorrect data."),
    BANNED("The user was banned."),
    CALL_ERROR("Request failed.");

    private final String errorMessage;

    StatusCode(String msg) {
        errorMessage = msg;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}