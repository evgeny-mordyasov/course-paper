package ru.gold.ordance.course.web.api;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum StatusCode {
    SUCCESS(0),
    CALL_ERROR(1),
    INVALID_RQ(2),
    VIOLATES_CONSTRAINT(3),
    UNAUTHORIZED(4),
    BANNED(5);

    private static final Map<Integer, StatusCode> CODES = Arrays.stream(values())
            .collect(Collectors.toMap(StatusCode::getCode, v -> v));

    private final int code;

    public static StatusCode of(int code) {
        StatusCode result = CODES.get(code);

        if (result == null) {
            throw new IllegalArgumentException("Status code " + code + " not found in " + CODES.values());
        } else {
            return result;
        }
    }

    StatusCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public String toString() {
        return "StatusCode{name='" + this.name() + '\'' + ",code=" + this.code + '}';
    }
}