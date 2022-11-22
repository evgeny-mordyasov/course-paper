package ru.gold.ordance.course.base.service.core.sub.impl;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.entity.ConfirmationToken;
import ru.gold.ordance.course.base.persistence.repository.ConfirmationTokenRepository;
import ru.gold.ordance.course.base.service.core.sub.ConfirmationTokenService;

@Transactional(isolation = Isolation.READ_COMMITTED)
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
    private final ConfirmationTokenRepository repository;

    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public ConfirmationToken getByToken(String token) {
        return repository.getByToken(token);
    }

    @Override
    public ConfirmationToken save(ConfirmationToken entity) {
        return repository.save(entity);
    }
}
