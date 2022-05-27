package ru.gold.ordance.course.base.utils;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static ru.gold.ordance.course.common.utils.StringUtils.getServiceTextFor;

public class ServiceLoggerUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceLoggerUtils.class);

    private ServiceLoggerUtils() {
    }

    public static void loggingStartOfServiceMethod(String methodName) {
        LOGGER.info(getServiceTextFor(methodName) + " method has started.");
    }

    public static void loggingEndOfServiceMethod(String methodName) {
        LOGGER.info(getServiceTextFor(methodName) + " method has finished.");
    }

    public static void loggingSizeOfList(List<?> list) {
        LOGGER.info("Size of list: {}", list.size());
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static void loggingOptionalValue(Optional<?> optional) {
        boolean isPresent = optional.isPresent();
        LOGGER.info((isPresent ? "Entity was found: {}" : "Entity not found."), isPresent ? optional.get() : Strings.EMPTY);
    }
}
