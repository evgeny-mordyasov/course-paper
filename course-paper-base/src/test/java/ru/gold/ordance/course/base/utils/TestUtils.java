package ru.gold.ordance.course.base.utils;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class TestUtils {
    private TestUtils() {
    }

    public static Long generateId() {
        return ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
    }

    public static String randomString() {
        return UUID.randomUUID().toString();
    }
}
