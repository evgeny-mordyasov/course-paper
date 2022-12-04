package ru.gold.ordance.course.persistence.entity.impl;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import ru.gold.ordance.course.persistence.entity.AbstractEntity;
import ru.gold.ordance.course.persistence.entity.ContainingInternalEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "DOCUMENT_LANGUAGE")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder(toBuilder = true, setterPrefix = "with")
@ToString
public class LnkDocumentLanguage implements AbstractEntity, ContainingInternalEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ENTITY_ID")
    @GeneratedValue(generator = "lnk_sequence-generator")
    @GenericGenerator(
            name = "lnk_sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "lnk_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            })
    private final Long entityId;

    @ManyToOne
    @JoinColumn(name = "DOCUMENT_ID")
    private final Document document;

    @ManyToOne
    @JoinColumn(name = "LANGUAGE_ID")
    private final Language language;

    @Column(name = "DATA")
    @ToString.Exclude
    private byte[] data;

    @Override
    public List<AbstractEntity> getInternalEntities() {
        return List.of(document, language);
    }
}
