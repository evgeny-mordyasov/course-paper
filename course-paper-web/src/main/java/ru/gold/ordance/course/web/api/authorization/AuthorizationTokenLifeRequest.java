package ru.gold.ordance.course.web.api.authorization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static ru.gold.ordance.course.web.utils.ValidatorUtils.errorString;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class AuthorizationTokenLifeRequest implements AuthorizationRequest {
    private static final long serialVersionUID = 1L;

    private final String token;

    @Override
    public void validate() {
        errorString(getToken(), "token");
    }
}
