package ru.gold.ordance.course.web.utils;

import ru.gold.ordance.course.web.exception.ValidateException;

import static java.util.Objects.isNull;
import static org.apache.logging.log4j.util.Strings.isBlank;
import static ru.gold.ordance.course.base.spring.StaticContextAccessor.getPersistenceHelper;
import static ru.gold.ordance.course.common.utils.TestUtils.not;

public final class ValidatorUtils {
    private ValidatorUtils() {
    }

    public static void error(String message) {
        throw new ValidateException(message);
    }

    public static void errorString(String string, String fieldName) {
        if (isNull(string)) {
            error("The " + fieldName + " is null.");
        }

        if (isBlank(string)) {
            error("The " + fieldName + " is empty.");
        }
    }

    public static void errorEntityId(Long entityId) {
        errorId(entityId, "entityId");
    }

    private static void errorId(Long objectId, String fieldName) {
        if (isNull(objectId)) {
            error("The " + fieldName + " is null.");
        }

        if (objectId < 1) {
            error("The " + fieldName + " is not positive.");
        }
    }

    public static void errorObjectId(Class<?> clazz, Long objectId, String fieldName) {
       errorId(objectId, fieldName);

        if (not(getPersistenceHelper().existsById(clazz, objectId))) {
            error("The " + fieldName + " was not found in the database.");
        }
    }

    public static void errorTrue(boolean condition, String message) {
        if (condition) {
            error(message);
        }
    }

    public static void errorFalse(boolean condition, String message) {
        if (!condition) {
            error(message);
        }
    }
}
