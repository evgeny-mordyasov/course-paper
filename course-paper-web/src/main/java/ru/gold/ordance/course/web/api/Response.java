package ru.gold.ordance.course.web.api;

import ru.gold.ordance.course.common.api.Status;

import java.io.Serializable;

public interface Response extends Serializable {
    Status getStatus();
}
