package ru.gold.ordance.course.web.api.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.web.api.GetByIdRequest;

@AllArgsConstructor
@Getter
@ToString
public class ClientGetByIdRequest implements GetByIdRequest {
    private static final long serialVersionUID = 1L;

    private final Long entityId;
}
