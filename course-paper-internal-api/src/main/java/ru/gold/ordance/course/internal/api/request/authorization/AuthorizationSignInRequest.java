package ru.gold.ordance.course.internal.api.request.authorization;

import lombok.*;

import static ru.gold.ordance.course.internal.api.utils.ValidatorUtils.errorString;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class AuthorizationSignInRequest implements AuthorizationRequest {
    private static final long serialVersionUID = 1L;

    private final String email;
    private final String password;

    @Override
    public void validate() {
        errorString(getEmail(), "email");
        errorString(getPassword(), "password");
    }
}
