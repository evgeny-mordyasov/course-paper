package ru.gold.ordance.course.base;

import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.entity.Client;
import ru.gold.ordance.course.base.entity.Role;

import static ru.gold.ordance.course.common.utils.TestUtils.randomString;

public final class EntityGenerator {
    private EntityGenerator() {
    }

    public static Client createClient() {
        return createBaseClient().build();
    }

    public static Client createClient(Long id) {
        return createBaseClient()
                .withId(id)
                .build();
    }

    public static Client createClient(String email) {
        return createBaseClient()
                .withEmail(email)
                .build();
    }

    private static Client.ClientBuilder createBaseClient() {
        return Client.builder()
                .withSurname(randomString())
                .withName(randomString())
                .withPatronymic(randomString())
                .withEmail(randomString())
                .withPassword(randomString())
                .withRole(Role.USER)
                .withIsActive(true);
    }

    public static Classification createClassification() {
        return Classification.builder()
                .withName(randomString())
                .build();
    }

    public static Classification createClassification(Long id) {
        return Classification.builder()
                .withId(id)
                .withName(randomString())
                .build();
    }

    public static Classification createClassification(String name) {
        return Classification.builder()
                .withName(name)
                .build();
    }
}
