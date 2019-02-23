package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import io.dentall.totoro.domain.enumeration.TimeInterval;

import io.dentall.totoro.domain.enumeration.TimeType;

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
    @Column(name = "time_interval", nullable = false)
    private TimeInterval timeInterval;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "time_type", nullable = false)
    private TimeType timeType;

    @Pattern(regexp = "^([0-1][0-9]|2[0-3]):[0-5][0-9]$")
    @Column(name = "start_time")
    private String startTime;

    @Pattern(regexp = "^([0-1][0-9]|2[0-3]):[0-5][0-9]$")
    @Column(name = "end_time")
    private String endTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @ManyToOne
    @JsonIgnoreProperties(value = {"dominantPatients", "firstPatients", "appointments", "treatmentProcedures", "treatmentTasks", "procedures", "treatments", "calendars"}, allowSetters = true)
    private ExtendUser doctor;

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

    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    public Calendar timeInterval(TimeInterval timeInterval) {
        this.timeInterval = timeInterval;
        return this;
    }

    public void setTimeInterval(TimeInterval timeInterval) {
        this.timeInterval = timeInterval;
    }

    public TimeType getTimeType() {
        return timeType;
    }

    public Calendar timeType(TimeType timeType) {
        this.timeType = timeType;
        return this;
    }

    public void setTimeType(TimeType timeType) {
        this.timeType = timeType;
    }

    public String getStartTime() {
        return startTime;
    }

    public Calendar startTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Calendar endTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public ExtendUser getDoctor() {
        return doctor;
    }

    public Calendar doctor(ExtendUser doctor) {
        this.doctor = doctor;
        return this;
    }

    public void setDoctor(ExtendUser doctor) {
        this.doctor = doctor;
    }

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
            ", timeInterval='" + getTimeInterval() + "'" +
            ", timeType='" + getTimeType() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
