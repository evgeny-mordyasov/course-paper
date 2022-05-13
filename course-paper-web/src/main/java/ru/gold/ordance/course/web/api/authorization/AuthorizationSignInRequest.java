package ru.gold.ordance.course.web.api.authorization;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class AuthorizationSignInRequest implements AuthorizationRequest {
    private static final long serialVersionUID = 1L;

    private final String email;

    private final String password;
}
