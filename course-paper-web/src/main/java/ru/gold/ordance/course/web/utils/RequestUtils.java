package ru.gold.ordance.course.web.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import ru.gold.ordance.course.web.api.Response;

import java.util.function.Supplier;

import static ru.gold.ordance.course.web.api.BaseErrorResponse.handleException;

@Slf4j
public final class RequestUtils {
    public static final String JSON = MediaType.APPLICATION_JSON_VALUE;

    private RequestUtils() {
    }

    public static Response execute(Supplier<Response> action) {
        Response response;

        try {
            response = action.get();
            log.info("Success response: {}", response);
        } catch (Exception e) {
            response = handleException(e);
            log.error("Error response: {}", response);
        }

        return response;
    }
}
