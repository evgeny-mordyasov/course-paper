package ru.gold.ordance.course.base.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Generated(value = GenerationTime.INSERT)
    @Column(name = "ROLE")
    private final Role role;

    @Generated(value = GenerationTime.INSERT)
    @Column(name = "CREATED_DATE")
    private final LocalDateTime createdDate;

    @Generated(value = GenerationTime.INSERT)
    @Column(name = "LAST_MODIFIED_DATE")
    private final LocalDateTime lastModifiedDate;

    @Generated(value = GenerationTime.INSERT)
    @Column(name = "IS_ACTIVE")
    private final boolean isActive;
}
