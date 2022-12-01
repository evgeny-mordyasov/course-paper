package ru.gold.ordance.course.internal.api.domain.client.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.internal.api.domain.Request;

import static ru.gold.ordance.course.internal.api.utils.ValidatorUtils.errorString;

@AllArgsConstructor
@Getter
@ToString
public class ClientGetByEmailRequest implements Request {
    private static final long serialVersionUID = 1L;

    private final String email;

    @Override
    public void validate() {
        errorString(getEmail(), "email");
    }
}
