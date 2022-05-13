package ru.gold.ordance.course.web.api.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.web.api.GetRequest;

@AllArgsConstructor
@Getter
@ToString
public class ClientGetByEmailRequest implements GetRequest {
    private static final long serialVersionUID = 1L;

    private final String email;
}
