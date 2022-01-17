package io.dentall.totoro.web.rest.vm;

import java.time.Instant;
import java.util.Objects;

public class PatientDocumentDisposalVM {

    private Long id;

    private Instant dateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientDocumentDisposalVM that = (PatientDocumentDisposalVM) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PatientDocumentDisposal{" +
            "id=" + id +
            ", dateTime=" + dateTime +
            '}';
    }
}
