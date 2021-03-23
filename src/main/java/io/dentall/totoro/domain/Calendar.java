package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import io.dentall.totoro.domain.enumeration.TimeType;

import io.dentall.totoro.domain.enumeration.TimeInterval;

/**
 * A Calendar.
 */
@Entity
@Table(name = "calendar")
public class Calendar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_start", nullable = false)
    private Instant start;

    @NotNull
    @Column(name = "jhi_end", nullable = false)
    private Instant end;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "time_type", nullable = false)
    private TimeType timeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "time_interval")
    private TimeInterval timeInterval;

    @Column(name = "note")
    private String note;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @ManyToOne
    @JsonIgnoreProperties(value = {"lastPatients", "firstPatients", "appointments", "treatmentProcedures", "treatmentTasks", "procedures", "treatments", "calendars"}, allowSetters = true)
    private ExtendUser doctor;

    @Column(name = "day_off_cron")
    private String dayOffCron;

    @Column(name = "duration")
    private String duration;

    public Calendar duration(String duration) {
        this.duration = duration;
        return this;
    }

    public Calendar dayOffCron(String dayOffCron) {
        this.dayOffCron = dayOffCron;
        return this;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDayOffCron() {
        return dayOffCron;
    }

    public void setDayOffCron(String dayOffCron) {
        this.dayOffCron = dayOffCron;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStart() {
        return start;
    }

    public Calendar start(Instant start) {
        this.start = start;
        return this;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public Calendar end(Instant end) {
        this.end = end;
        return this;
    }

    public void setEnd(Instant end) {
        this.end = end;
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

    public String getNote() {
        return note;
    }

    public Calendar note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
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
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", timeType='" + getTimeType() + "'" +
            ", timeInterval='" + getTimeInterval() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
