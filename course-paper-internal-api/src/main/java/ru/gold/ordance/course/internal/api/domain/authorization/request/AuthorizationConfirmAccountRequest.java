package ru.gold.ordance.course.internal.api.domain.authorization.request;

import lombok.*;
import ru.gold.ordance.course.internal.api.domain.Request;

import static ru.gold.ordance.course.internal.api.utils.ValidatorUtils.errorString;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class AuthorizationConfirmAccountRequest implements Request {
    private static final long serialVersionUID = 1L;

    private final String token;

    @Override
    public void validate() {
        errorString(getToken(), "token");
    }
}
