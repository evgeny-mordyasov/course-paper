package ru.gold.ordance.course.web.api.client;

import lombok.*;
import ru.gold.ordance.course.base.entity.Role;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class WebClient implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Long entityId;

    private final String surname;

    private final String name;

    private final String patronymic;

    private final String email;

    private final Role role;

    private final boolean isActive;
}
