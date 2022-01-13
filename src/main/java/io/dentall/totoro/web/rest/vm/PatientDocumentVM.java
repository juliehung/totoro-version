package io.dentall.totoro.web.rest.vm;

import io.dentall.totoro.thumbnails.Thumbnails;

import java.util.List;
import java.util.Objects;

public class PatientDocumentVM {

    private Long id;

    private Long patientId;

    private PatientDocumentDisposalVM disposal;

    private DocumentVM document;

    private List<Thumbnails> thumbnailsList;

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

    public PatientDocumentDisposalVM getDisposal() {
        return disposal;
    }

    public void setDisposal(PatientDocumentDisposalVM disposal) {
        this.disposal = disposal;
    }

    public DocumentVM getDocument() {
        return document;
    }

    public void setDocument(DocumentVM document) {
        this.document = document;
    }

    public List<Thumbnails> getThumbnailsList() {
        return thumbnailsList;
    }

    public void setThumbnailsList(List<Thumbnails> thumbnailsList) {
        this.thumbnailsList = thumbnailsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientDocumentVM that = (PatientDocumentVM) o;
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
