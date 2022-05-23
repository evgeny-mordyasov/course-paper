package ru.gold.ordance.course.web.api.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.web.api.GetRequest;

import static ru.gold.ordance.course.web.utils.ValidatorUtils.errorString;

@AllArgsConstructor
@Getter
@ToString
public class ClientGetByEmailRequest implements GetRequest {
    private static final long serialVersionUID = 1L;

    private final String email;

    @Override
    public void validate() {
        errorString(getEmail(), "email");
    }
}
