package ru.gold.ordance.course.internal.api.domain;

import java.io.Serializable;

public interface Request extends Serializable {
    void validate();
}
