package ru.gold.ordance.course.web.api.client;

import lombok.*;
import ru.gold.ordance.course.base.entity.Client;
import ru.gold.ordance.course.web.api.UpdateRequest;

import static ru.gold.ordance.course.web.utils.ValidatorUtils.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class ClientUpdateRequest implements UpdateRequest {
    private static final long serialVersionUID = 1L;

    private final Long entityId;

    private final String surname;

    private final String name;

    private final String patronymic;

    private final String password;

    @Override
    public void validate() {
        errorEntityId(getEntityId());
        errorString(getSurname(), "surname");
        errorString(getName(), "name");
        errorString(getPatronymic(), "patronymic");
        errorString(getPassword(), "password");
    }
}
