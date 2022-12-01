package ru.gold.ordance.course.common.api;

public class Status {
    private final StatusCode code;
    private final String description;

    private Status(StatusCode code) {
        this.code = code;
        this.description = null;
    }

    public Status(StatusCode code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Status success() {
        return new Status(StatusCode.SUCCESS);
    }

    public static Status error(String msg) {
        return new Status(StatusCode.CALL_ERROR, msg);
    }

    public String toString() {
        return "Status{code=" + this.code + ", description='" + this.description + '\'' + '}';
    }
}