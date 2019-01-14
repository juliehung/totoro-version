package io.dentall.totoro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import io.dentall.totoro.domain.enumeration.IntervalType;

import io.dentall.totoro.domain.enumeration.DateType;

/**
 * A Calendar.
 */
@Entity
@Table(name = "calendar")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Calendar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private LocalDate date;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "interval_type", nullable = false)
    private IntervalType intervalType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "date_type", nullable = false)
    private DateType dateType;

    @NotNull
    @Pattern(regexp = "^([0-1][0-9]|2[0-3]):[0-5][0-9]$")
    @Column(name = "jhi_start", nullable = false)
    private String start;

    @NotNull
    @Pattern(regexp = "^([0-1][0-9]|2[0-3]):[0-5][0-9]$")
    @Column(name = "jhi_end", nullable = false)
    private String end;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Calendar date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public IntervalType getIntervalType() {
        return intervalType;
    }

    public Calendar intervalType(IntervalType intervalType) {
        this.intervalType = intervalType;
        return this;
    }

    public void setIntervalType(IntervalType intervalType) {
        this.intervalType = intervalType;
    }

    public DateType getDateType() {
        return dateType;
    }

    public Calendar dateType(DateType dateType) {
        this.dateType = dateType;
        return this;
    }

    public void setDateType(DateType dateType) {
        this.dateType = dateType;
    }

    public String getStart() {
        return start;
    }

    public Calendar start(String start) {
        this.start = start;
        return this;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public Calendar end(String end) {
        this.end = end;
        return this;
    }

    public void setEnd(String end) {
        this.end = end;
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
        Calendar calendar = (Calendar) o;
        if (calendar.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), calendar.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Calendar{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", intervalType='" + getIntervalType() + "'" +
            ", dateType='" + getDateType() + "'" +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            "}";
    }
}
