package ru.gold.ordance.course.web.validate;

import ru.gold.ordance.course.web.exception.ValidateException;

import static java.util.Objects.isNull;
import static org.apache.logging.log4j.util.Strings.isBlank;

final class ValidateHelper {
    private ValidateHelper() {
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

    public static void errorObjectId(Long objectId, String fieldName) {
        if (isNull(objectId)) {
            error("The " + fieldName + " is null.");
        }

        if (objectId < 1) {
            error("The " + fieldName + " is not positive.");
        }
    }

    public static void errorEntityId(Long entityId) {
        errorObjectId(entityId, "entityId");
    }
}
