package io.dentall.totoro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import io.dentall.totoro.domain.enumeration.TimeInterval;

import io.dentall.totoro.domain.enumeration.WeekDay;

/**
 * A CalendarSetting.
 */
@Entity
@Table(name = "calendar_setting")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CalendarSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Pattern(regexp = "^([0-1][0-9]|2[0-3]):[0-5][0-9]$")
    @Column(name = "start_time", nullable = false)
    private String startTime;

    @NotNull
    @Pattern(regexp = "^([0-1][0-9]|2[0-3]):[0-5][0-9]$")
    @Column(name = "end_time", nullable = false)
    private String endTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "time_interval", nullable = false)
    private TimeInterval timeInterval;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "weekday", nullable = false)
    private WeekDay weekday;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public CalendarSetting startTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public CalendarSetting endTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    public CalendarSetting timeInterval(TimeInterval timeInterval) {
        this.timeInterval = timeInterval;
        return this;
    }

    public void setTimeInterval(TimeInterval timeInterval) {
        this.timeInterval = timeInterval;
    }

    public WeekDay getWeekday() {
        return weekday;
    }

    public CalendarSetting weekday(WeekDay weekday) {
        this.weekday = weekday;
        return this;
    }

    public void setWeekday(WeekDay weekday) {
        this.weekday = weekday;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public CalendarSetting startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public CalendarSetting endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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
        CalendarSetting calendarSetting = (CalendarSetting) o;
        if (calendarSetting.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), calendarSetting.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CalendarSetting{" +
            "id=" + getId() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", timeInterval='" + getTimeInterval() + "'" +
            ", weekday='" + getWeekday() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
