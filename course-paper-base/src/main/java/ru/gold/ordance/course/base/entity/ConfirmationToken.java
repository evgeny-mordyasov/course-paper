package ru.gold.ordance.course.base.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Table(name = "CONFIRMATION_TOKEN")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder(toBuilder = true, setterPrefix = "with")
@ToString
public class ConfirmationToken implements AbstractEntity {
    private static final long serialVersionUID = 1L;

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
    private final String token = UUID.randomUUID().toString();

    @Column(name = "EXPIRY_DATE")
    private final LocalDateTime expiryDate = calculateExpiryDate();

    @OneToOne
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private final Client client;

    private LocalDateTime calculateExpiryDate() {
        Instant instant = Instant.now()
                .plus(24, ChronoUnit.HOURS);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public boolean isExpired() {
        return expiryDate.isBefore(LocalDateTime.now());
    }
}
