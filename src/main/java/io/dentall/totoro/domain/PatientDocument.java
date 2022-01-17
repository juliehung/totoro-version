package io.dentall.totoro.domain;

import javax.persistence.*;
import java.util.Objects;

@NamedEntityGraph(
    name = "PatientDocument.all",
    attributeNodes = {
        @NamedAttributeNode("disposal"),
        @NamedAttributeNode("document")
    }
)
@Entity
@Table(name = "patient_document")
public class PatientDocument extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(
        name = "patientDocumentSeq",
        sequenceName = "patient_document_seq",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "patientDocumentSeq"
    )
    private Long id;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @OneToOne
    private PatientDocumentDisposal disposal;

    @OneToOne(cascade = CascadeType.MERGE)
    private Document document;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public PatientDocumentDisposal getDisposal() {
        return disposal;
    }

    public void setDisposal(PatientDocumentDisposal disposal) {
        this.disposal = disposal;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientDocument that = (PatientDocument) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PatientDocument{" +
            "id=" + id +
            ", patientId=" + patientId +
            ", disposal=" + disposal +
            ", document=" + document +
            '}';
    }

}
