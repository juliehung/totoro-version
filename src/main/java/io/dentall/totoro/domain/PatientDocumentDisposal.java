package io.dentall.totoro.domain;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "disposal")
public class PatientDocumentDisposal {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "date_time")
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
        PatientDocumentDisposal that = (PatientDocumentDisposal) o;
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
