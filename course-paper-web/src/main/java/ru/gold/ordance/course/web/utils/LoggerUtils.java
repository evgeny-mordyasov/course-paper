package ru.gold.ordance.course.web.utils;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gold.ordance.course.web.api.Request;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.StatusCode;

public final class LoggerUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerUtils.class);

    private LoggerUtils() {
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

    public static void loggingMessage(String message, Object ... objects) {
        LOGGER.info(message, objects);
    }

    public static void loggingFindAll() {
        LOGGER.info("Get all request received.");
    }

    public static void loggingFindById(Request rq) {
        loggingFindBy("id", rq);
    }

    public static void loggingFindByName(Request rq) {
        loggingFindBy("name", rq);
    }

    public static void loggingFindByEmail(Request rq) {
        loggingFindBy("email", rq);
    }

    private static void loggingFindBy(String field, Request rq) {
        LOGGER.info("Get by {} request received: {}", field, rq);
    }

    public static void loggingSave(Request rq) {
        LOGGER.info("Save request received: {}", rq);
    }

    public static void loggingUpdate(Request rq) {
        LOGGER.info("Update request received: {}", rq);
    }

    public static void loggingDeleteById(Request rq) {
        LOGGER.info("Delete by id request received: {}", rq);
    }
}
