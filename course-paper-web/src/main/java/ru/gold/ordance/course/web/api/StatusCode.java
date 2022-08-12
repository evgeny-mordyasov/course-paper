package ru.gold.ordance.course.web.api;

public enum StatusCode {
    SUCCESS("The request completed."),
    INVALID_RQ("The request validation failed."),
    NOT_FOUND("The content was not found."),
    VIOLATES_CONSTRAINT("The request violates the database constraint."),
    UNAUTHORIZED("The request contains invalid user data."),
    BANNED("The user was banned."),
    CALL_ERROR("The request failed.");

    private final String errorMessage;

    StatusCode(String msg) {
        errorMessage = msg;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}