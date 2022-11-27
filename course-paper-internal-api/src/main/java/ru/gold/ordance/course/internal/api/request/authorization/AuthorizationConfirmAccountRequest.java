package ru.gold.ordance.course.internal.api.request.authorization;

import lombok.*;

import static ru.gold.ordance.course.internal.api.utils.ValidatorUtils.errorString;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class AuthorizationConfirmAccountRequest implements AuthorizationRequest {
    private static final long serialVersionUID = 1L;

    private final String token;

    @Override
    public void validate() {
        errorString(getToken(), "token");
    }
}
