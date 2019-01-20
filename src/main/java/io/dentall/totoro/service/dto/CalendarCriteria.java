package io.dentall.totoro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.dentall.totoro.domain.enumeration.TimeInterval;
import io.dentall.totoro.domain.enumeration.TimeType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the Calendar entity. This class is used in CalendarResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /calendars?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CalendarCriteria implements Serializable {
    /**
     * Class for filtering TimeInterval
     */
    public static class TimeIntervalFilter extends Filter<TimeInterval> {
    }
    /**
     * Class for filtering TimeType
     */
    public static class TimeTypeFilter extends Filter<TimeType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter date;

    private TimeIntervalFilter timeInterval;

    private TimeTypeFilter timeType;

    private StringFilter startTime;

    private StringFilter endTime;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public TimeIntervalFilter getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(TimeIntervalFilter timeInterval) {
        this.timeInterval = timeInterval;
    }

    public TimeTypeFilter getTimeType() {
        return timeType;
    }

    public void setTimeType(TimeTypeFilter timeType) {
        this.timeType = timeType;
    }

    public StringFilter getStartTime() {
        return startTime;
    }

    public void setStartTime(StringFilter startTime) {
        this.startTime = startTime;
    }

    public StringFilter getEndTime() {
        return endTime;
    }

    public void setEndTime(StringFilter endTime) {
        this.endTime = endTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CalendarCriteria that = (CalendarCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(date, that.date) &&
            Objects.equals(timeInterval, that.timeInterval) &&
            Objects.equals(timeType, that.timeType) &&
            Objects.equals(startTime, that.startTime) &&
            Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        date,
        timeInterval,
        timeType,
        startTime,
        endTime
        );
    }

    @Override
    public String toString() {
        return "CalendarCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (timeInterval != null ? "timeInterval=" + timeInterval + ", " : "") +
                (timeType != null ? "timeType=" + timeType + ", " : "") +
                (startTime != null ? "startTime=" + startTime + ", " : "") +
                (endTime != null ? "endTime=" + endTime + ", " : "") +
            "}";
    }

}
