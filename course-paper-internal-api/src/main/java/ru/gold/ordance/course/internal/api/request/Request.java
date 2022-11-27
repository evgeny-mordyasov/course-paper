package ru.gold.ordance.course.internal.api.request;

import java.io.Serializable;

public interface Request extends Serializable {
    void validate();
}
