package ru.gold.ordance.course.common.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.capitalize;

public final class StringWebLoggerUtils {
    private StringWebLoggerUtils() {
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
        if (!words.isEmpty()) {
            words.set(0, capitalize(words.get(0)));
        }
    }
}
