package ru.gold.ordance.course.internal.api.domain.client.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.internal.api.domain.GetByIdRequest;

@AllArgsConstructor
@Getter
@ToString
public class ClientGetByIdRequest implements GetByIdRequest {
    private static final long serialVersionUID = 1L;

    private final Long entityId;
}
