package ru.gold.ordance.course.web.api.client;

import lombok.*;
import ru.gold.ordance.course.base.entity.Role;

import java.io.Serializable;
import java.time.LocalDateTime;

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

    private final LocalDateTime createdDate;

    private final LocalDateTime lastModifiedDate;

    private final Role role;

    private final boolean isActive;
}
