package ru.gold.ordance.course.common.api;

public enum StatusCode {
    SUCCESS("The request completed."),
    INVALID_RQ("The request validation failed."),
    ENTITY_NOT_FOUND("The ID not found in the database."),
    VIOLATES_CONSTRAINT("The request violates the database constraint."),
    UNAUTHORIZED("The request contains invalid user data."),
    BANNED("The user is not activated."),
    CALL_ERROR("The request failed.");

    private final String errorMessage;

    StatusCode(String msg) {
        errorMessage = msg;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}