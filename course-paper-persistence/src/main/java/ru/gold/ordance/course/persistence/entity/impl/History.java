package ru.gold.ordance.course.persistence.entity.impl;

import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import ru.gold.ordance.course.persistence.entity.AbstractEntity;
import ru.gold.ordance.course.persistence.entity.ContainingInternalEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "DOCUMENT_READING_HISTORY")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder(toBuilder = true, setterPrefix = "with")
@ToString
public class History implements AbstractEntity, ContainingInternalEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ENTITY_ID")
    @GeneratedValue(generator = "doc_reading_h_sequence-generator")
    @GenericGenerator(
            name = "doc_reading_h_sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "doc_reading_h_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            })
    private final Long entityId;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private final Client client;

    @ManyToOne
    @JoinColumn(name = "DOCUMENT_ID")
    private final Document document;

    @ManyToOne
    @JoinColumn(name = "LANGUAGE_ID")
    private final Language language;

    @Generated(value = GenerationTime.INSERT)
    @Column(name = "READING_TIME", insertable = false)
    private final LocalDateTime readingTime;

    @Override
    public List<AbstractEntity> getInternalEntities() {
        return List.of(client, document, language);
    }
}
