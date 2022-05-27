package ru.gold.ordance.course.web.utils;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gold.ordance.course.web.api.Request;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.StatusCode;

import static ru.gold.ordance.course.common.utils.StringUtils.getRequestTextFor;

public final class WebLoggerUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLoggerUtils.class);

    private WebLoggerUtils() {
    }

    public static void loggingReceivedRequest(Request rq, String methodName) {
        LOGGER.info(getRequestTextFor(methodName) + " request received: {}", rq);
    }

    public static void loggingReceivedRequest(String methodName) {
        LOGGER.info(getRequestTextFor(methodName) + " request received.");
    }

    public static void loggingSuccessResponse(Response rs) {
        LOGGER.info(rs.getErrorMessage() + " response: {}", rs);
    }

    public static void loggingSuccessResponse(Response rs, Request rq) {
        LOGGER.info(rs.getErrorMessage() + " response: {}, request: {}", rs, rq);
    }

    public static void loggingErrorResponse(Response rs, Exception e) {
        loggingErrorResponseCommon(rs, null, e);
    }

    public static void loggingErrorResponse(Response rs, Request rq, Exception e) {
        loggingErrorResponseCommon(rs, rq, e);
    }

    private static void loggingErrorResponseCommon(Response rs, Request rq, Exception e) {
        LOGGER.info(rs.getErrorMessage() + " response: {}, request: {}", rs, rq,
                rs.getCode() == StatusCode.CALL_ERROR ? e : Strings.EMPTY);
    }
}
