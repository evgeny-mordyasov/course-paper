package ru.gold.ordance.course.web.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import ru.gold.ordance.course.web.api.Response;

import java.util.function.Supplier;

import static ru.gold.ordance.course.web.api.BaseErrorResponse.createFrom;

public final class RequestUtils {
    public static final String JSON = MediaType.APPLICATION_JSON_VALUE;

    private final static ObjectMapper mapper = new ObjectMapper();

    private RequestUtils() {
    }

    public static String toJSON(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    public static Response execute(Supplier<Response> action) {
        try {
            return action.get();
        } catch (Exception e) {
            return createFrom(e);
        }
    }
}
