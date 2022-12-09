package ru.gold.ordance.course.persistence.entity.impl;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import ru.gold.ordance.course.persistence.entity.AbstractEntity;
import ru.gold.ordance.course.persistence.entity.ContainingInternalEntity;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name = "CONFIRMATION_TOKEN")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder(toBuilder = true, setterPrefix = "with")
@ToString
public class ConfirmationToken implements AbstractEntity, ContainingInternalEntity {
    private static final long serialVersionUID = 1L;

    private static final int MIN_TOKEN_VALUE = 100000;
    private static final int MAX_TOKEN_VALUE = 999999;

    @Id
    @Column(name = "ENTITY_ID")
    @GeneratedValue(generator = "confirmation_token_sequence-generator")
    @GenericGenerator(
            name = "confirmation_token_sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "confirmation_token_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            })
    private final Long entityId;

    @Column(name = "TOKEN")
    private final String token = generateToken();

    @Column(name = "EXPIRY_DATE")
    private final LocalDateTime expiryDate = calculateExpiryDate();

    @OneToOne
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private final Client client;

    private LocalDateTime calculateExpiryDate() {
        Instant instant = Instant.now().plus(1, ChronoUnit.HOURS);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public boolean isExpired() {
        return expiryDate.isBefore(LocalDateTime.now());
    }

    @Override
    public List<AbstractEntity> getInternalEntities() {
        return List.of(client);
    }

    private static String generateToken() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(MIN_TOKEN_VALUE, MAX_TOKEN_VALUE + 1));
    }
}
