package ru.gold.ordance.course.web.api.language;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.gold.ordance.course.web.api.SaveRequest;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class LanguageSaveRequest implements SaveRequest {
    private static final long serialVersionUID = 1L;

    private final String name;
}
