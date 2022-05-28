package ru.gold.ordance.course.web.rest.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import ru.gold.ordance.course.web.api.Status;
import ru.gold.ordance.course.web.api.StatusCode;
import ru.gold.ordance.course.web.exception.ValidateException;

public final class RequestUtils {
    public static final String JSON = MediaType.APPLICATION_JSON_VALUE;

    private final static ObjectMapper mapper = new ObjectMapper();

    private RequestUtils() {
    }

    public static Status toStatus(Exception e) {
        if (e instanceof ValidateException || e instanceof FileSizeLimitExceededException) {
            return new Status()
                    .withCode(StatusCode.INVALID_RQ)
                    .withDescription(e.getMessage());
        }

        if (e instanceof DataIntegrityViolationException) {
            return new Status()
                    .withCode(StatusCode.VIOLATES_CONSTRAINT)
                    .withDescription(StatusCode.VIOLATES_CONSTRAINT.getErrorMessage());
        }

        if (e instanceof BadCredentialsException) {
            return new Status()
                    .withCode(StatusCode.UNAUTHORIZED)
                    .withDescription(StatusCode.UNAUTHORIZED.getErrorMessage());
        }

        if (e instanceof LockedException) {
            return new Status()
                    .withCode(StatusCode.BANNED)
                    .withDescription(StatusCode.BANNED.getErrorMessage());
        }

        return new Status()
                .withCode(StatusCode.CALL_ERROR)
                .withDescription(e.toString());
    }

    public static String toJSON(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }
}
