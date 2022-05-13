package ru.gold.ordance.course.base.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder(toBuilder = true, setterPrefix = "with")
@ToString
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"email"}) })
public class Client implements AbstractEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "client_sequence-generator")
    @GenericGenerator(
            name = "client_sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "client_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            })
    private final Long id;

    private final String surname;

    private final String name;

    private final String patronymic;

    private final String email;

    private final String password;

    @Enumerated(EnumType.STRING)
    private final Role role;

    private final boolean isActive;
}
