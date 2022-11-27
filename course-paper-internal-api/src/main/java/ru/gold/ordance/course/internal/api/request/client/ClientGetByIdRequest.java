package ru.gold.ordance.course.internal.api.request.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.internal.api.request.GetByIdRequest;

@AllArgsConstructor
@Getter
@ToString
public class ClientGetByIdRequest implements GetByIdRequest {
    private static final long serialVersionUID = 1L;

    private final Long entityId;
}
