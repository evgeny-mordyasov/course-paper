package ru.gold.ordance.course.web.rest.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import ru.gold.ordance.course.base.exception.NotFoundException;
import ru.gold.ordance.course.web.api.Request;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.Status;
import ru.gold.ordance.course.web.api.StatusCode;
import ru.gold.ordance.course.web.exception.ValidateException;

public final class RequestUtils {
    public static final String JSON = "application/json";

    public static final String CONSTRAINT_MESSAGE = "Request violates the database constraint.";

    public static final String UNAUTHORIZED_MESSAGE = "Request contains incorrect data.";

    public static final String BANNED_MESSAGE = "The user was banned.";

    private final static ObjectMapper mapper = new ObjectMapper();

    private RequestUtils() {
    }

    public static void handleResponse(Logger LOGGER, Response rs, Request rq, Exception e) {
        Status status = rs.getStatus();

        if (status.getCode() == StatusCode.SUCCESS) {
            LOGGER.info("Request completed. request: {}, response: {}", rq, rs);
            return;
        }

        if (status.getCode() == StatusCode.INVALID_RQ) {
            LOGGER.info("Request validation failed. request: {}, response: {}", rq, rs);
            return;
        }

        if (status.getCode() == StatusCode.VIOLATES_CONSTRAINT) {
            LOGGER.error(CONSTRAINT_MESSAGE + " request: {}, response: {}", rq, rs);
            return;
        }

        if (status.getCode() == StatusCode.UNAUTHORIZED) {
            LOGGER.error(UNAUTHORIZED_MESSAGE + " request: {}, response: {}", rq, rs);
            return;
        }

        if (status.getCode() == StatusCode.BANNED) {
            LOGGER.error(BANNED_MESSAGE + " request: {}, response: {}", rq, rs);
        } else {
            LOGGER.error("Request failed. request: {}, response: {}", rq, rs, e);
        }
    }

    public static Status toStatus(Exception e) {
        String errorMessage;
        StatusCode statusCode;

        if (e instanceof ValidateException || e instanceof NotFoundException) {
            errorMessage = e.getMessage();
            statusCode = StatusCode.INVALID_RQ;
        } else if (e instanceof DataIntegrityViolationException) {
            errorMessage = CONSTRAINT_MESSAGE;
            statusCode = StatusCode.VIOLATES_CONSTRAINT;
        } else if (e instanceof InternalAuthenticationServiceException) {
            errorMessage = UNAUTHORIZED_MESSAGE;
            statusCode = StatusCode.UNAUTHORIZED;
        } else if (e instanceof LockedException) {
            errorMessage = BANNED_MESSAGE;
            statusCode = StatusCode.BANNED;
        } else {
            errorMessage = e.getMessage();
            statusCode = StatusCode.CALL_ERROR;
        }

        return new Status()
                .withCode(statusCode)
                .withDescription(errorMessage);
    }

    public static String toJSON(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }
}
