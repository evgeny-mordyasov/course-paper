package ru.gold.ordance.course.web.api.authorization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class AuthorizationTokenLifeRequest implements AuthorizationRequest {
    private static final long serialVersionUID = 1L;

    private final String token;
}
