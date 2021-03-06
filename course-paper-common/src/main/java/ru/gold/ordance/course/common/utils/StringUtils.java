package ru.gold.ordance.course.common.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static ru.gold.ordance.course.common.utils.TestUtils.not;

public final class StringUtils {
    private StringUtils() {
    }

    public static String getRequestTextFor(String methodName) {
        List<String> lowerCaseWords = getLowerCaseWordsFrom(methodName);
        toUpperFirstLetterOfFirstWord(lowerCaseWords);

        return String.join(SPACE, lowerCaseWords);
    }

    private static List<String> getLowerCaseWordsFrom(String methodName) {
        return Arrays.stream(methodName.split("(?=\\p{Upper})"))
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    private static void toUpperFirstLetterOfFirstWord(List<String> words) {
        if (not(words.isEmpty())) {
            words.set(0, capitalize(words.get(0)));
        }
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

    public static String getFileExtension(String fullFileName) {
        return fullFileName.substring(fullFileName.indexOf("."));
    }
}
