package ru.gold.ordance.course.common.api;

public class Status {
    private StatusCode code;
    private String description;

    public Status() {
    }

    public Status(StatusCode code) {
        this.code = code;
    }

    public StatusCode getCode() {
        return this.code;
    }

    public void setCode(StatusCode code) {
        this.code = code;
    }

    public Status withCode(StatusCode code) {
        this.code = code;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status withDescription(String description) {
        this.description = description;
        return this;
    }

    public String toString() {
        return "Status{code=" + this.code + ", description='" + this.description + '\'' + '}';
    }
}