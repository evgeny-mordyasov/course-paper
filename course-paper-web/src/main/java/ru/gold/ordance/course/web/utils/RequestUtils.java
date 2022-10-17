package ru.gold.ordance.course.web.utils;

import org.springframework.http.MediaType;
import ru.gold.ordance.course.web.api.Response;

import java.util.function.Supplier;

import static ru.gold.ordance.course.web.api.BaseErrorResponse.createFrom;

public final class RequestUtils {
    public static final String JSON = MediaType.APPLICATION_JSON_VALUE;

    private RequestUtils() {
    }

    public static Response execute(Supplier<Response> action) {
        try {
            return action.get();
        } catch (Exception e) {
            return createFrom(e);
        }
    }
}
