package ru.gold.ordance.course.common.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.capitalize;

public final class StringUtils {
    private StringUtils() {
    }

    public static String getRequestName(String methodName) {
        int firstWordIndex = 0;
        String [] methodWords = methodName.split("(?=\\p{Upper})");
        List<String> lowerCaseWords = Arrays.stream(methodWords)
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        lowerCaseWords.set(firstWordIndex, capitalize(lowerCaseWords.get(firstWordIndex)));
        return String.join(SPACE, lowerCaseWords);
    }
}
