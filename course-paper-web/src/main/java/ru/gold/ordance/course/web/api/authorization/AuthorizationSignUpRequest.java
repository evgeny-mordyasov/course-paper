package ru.gold.ordance.course.web.api.authorization;

import lombok.*;

import static ru.gold.ordance.course.web.utils.ValidatorUtils.errorString;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class AuthorizationSignUpRequest implements AuthorizationRequest {
    private static final long serialVersionUID = 1L;

    private final String surname;
    private final String name;
    private final String patronymic;
    private final String email;
    private final String password;

    @Override
    public void validate() {
        errorString(getSurname(), "surname");
        errorString(getName(), "name");
        errorString(getPatronymic(), "patronymic");
        errorString(getEmail(), "email");
        errorString(getPassword(), "password");
    }
}
