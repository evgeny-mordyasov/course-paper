package ru.gold.ordance.course.base.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

import static org.springframework.util.StringUtils.hasText;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {
    @Override
    public String convertToDatabaseColumn(Role role) {
        if (role == null) {
            return null;
        }

        return role.name();
    }

    @Override
    public Role convertToEntityAttribute(String role) {
        if (!hasText(role)) {
            return null;
        }

        return Stream.of(Role.values())
                .filter(r -> role.equals(r.name()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
