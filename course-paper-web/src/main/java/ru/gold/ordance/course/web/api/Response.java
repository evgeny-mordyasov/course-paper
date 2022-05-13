package ru.gold.ordance.course.web.api;

import java.io.Serializable;

public interface Response extends Serializable {
    Status getStatus();
}
