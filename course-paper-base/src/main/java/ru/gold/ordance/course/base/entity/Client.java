package ru.gold.ordance.course.base.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "CLIENT")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder(toBuilder = true, setterPrefix = "with")
@ToString
public class Client implements AbstractEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ENTITY_ID")
    @GeneratedValue(generator = "client_sequence-generator")
    @GenericGenerator(
            name = "client_sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "client_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            })
    private final Long entityId;

    @Column(name = "SURNAME")
    private final String surname;

    @Column(name = "NAME")
    private final String name;

    @Column(name = "PATRONYMIC")
    private final String patronymic;

    @Column(name = "EMAIL")
    private final String email;

    @Column(name = "PASSWORD")
    private final String password;

    @Enumerated(EnumType.STRING)
    private final Role role;

    @Column(name = "START_DATE")
    private final Timestamp startDate;

    @Column(name = "UPDATE_DATE")
    private final Timestamp updateDate;

    @Column(name = "IS_ACTIVE")
    private final boolean isActive;
}
