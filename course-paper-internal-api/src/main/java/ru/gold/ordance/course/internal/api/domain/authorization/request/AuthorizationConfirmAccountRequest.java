package ru.gold.ordance.course.internal.api.domain.authorization.request;

import lombok.*;
import ru.gold.ordance.course.internal.api.domain.Request;

import static ru.gold.ordance.course.internal.api.utils.ValidatorUtils.errorObjectId;
import static ru.gold.ordance.course.internal.api.utils.ValidatorUtils.errorString;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class AuthorizationConfirmAccountRequest implements Request {
    private static final long serialVersionUID = 1L;

    private final Long clientId;
    private final String token;

    @Override
    public void validate() {
        errorObjectId(getClientId(), "clientId");
        errorString(getToken(), "token");
    }
}
