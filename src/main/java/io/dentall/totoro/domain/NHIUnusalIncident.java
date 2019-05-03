package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A NHIUnusalIncident.
 */
@Entity
@Table(name = "nhi_unusal_incident")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NHIUnusalIncident implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_start", nullable = false)
    private Instant start;

    @Column(name = "jhi_end")
    private Instant end;

    @ManyToOne
    @JsonIgnoreProperties("")
    private NHIUnusalContent nhiUnusalContent;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStart() {
        return start;
    }

    public NHIUnusalIncident start(Instant start) {
        this.start = start;
        return this;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public NHIUnusalIncident end(Instant end) {
        this.end = end;
        return this;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public NHIUnusalContent getNhiUnusalContent() {
        return nhiUnusalContent;
    }

    public NHIUnusalIncident nhiUnusalContent(NHIUnusalContent nhiUnusalContent) {
        this.nhiUnusalContent = nhiUnusalContent;
        return this;
    }

    public void setNhiUnusalContent(NHIUnusalContent nhiUnusalContent) {
        this.nhiUnusalContent = nhiUnusalContent;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NHIUnusalIncident nhiUnusalIncident = (NHIUnusalIncident) o;
        if (nhiUnusalIncident.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nhiUnusalIncident.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NHIUnusalIncident{" +
            "id=" + getId() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            "}";
    }
}
