package ru.gold.ordance.course.web.utils;

import lombok.extern.slf4j.Slf4j;
import ru.gold.ordance.course.internal.api.request.Request;

import static ru.gold.ordance.course.common.utils.StringWebLoggerUtils.getRequestTextFor;

@Slf4j
public final class WebLoggerUtils {
    private WebLoggerUtils() {
    }

    public static void loggingReceivedRequest(Request rq, String methodName) {
        log.info(getRequestTextFor(methodName) + " request received: {}", rq);
    }

    public static void loggingReceivedRequest(String methodName) {
        log.info(getRequestTextFor(methodName) + " request received.");
    }
}
