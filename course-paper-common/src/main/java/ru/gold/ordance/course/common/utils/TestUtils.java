package ru.gold.ordance.course.common.utils;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class TestUtils {
    private TestUtils() {
    }

    public static Long generateId() {
        return ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
    }

    public static int generatePositiveInt() {
        return ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
    }

    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    public static String randomFullFileName() {
        return String.format("%s.%s", randomString(), randomString());
    }

    public static boolean not(boolean condition) {
        return !condition;
    }
}
