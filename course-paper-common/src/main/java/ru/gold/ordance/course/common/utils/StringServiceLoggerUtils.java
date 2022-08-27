package ru.gold.ordance.course.common.utils;

public final class StringServiceLoggerUtils {
    private StringServiceLoggerUtils() {
    }

    public static String getServiceTextFor(String classAndMethodNames) {
        String className = getTextBeforeSubstring(classAndMethodNames, ".");
        String fullMethodName = getTextAfterSubstring(classAndMethodNames, ".");

        String entityName = getTextBeforeSubstring(className, "ServiceImpl");
        String methodName = getTextBeforeSubstring(fullMethodName, "(");

        return String.format("%s: %s", entityName, methodName);
    }

    private static String getTextBeforeSubstring(String string, String substring) {
        return string.substring(0, string.indexOf(substring));
    }

    private static String getTextAfterSubstring(String string, String substring) {
        return string.substring(string.indexOf(substring) + 1);
    }
}
