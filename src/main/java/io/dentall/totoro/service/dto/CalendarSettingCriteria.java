package io.dentall.totoro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.dentall.totoro.domain.enumeration.TimeInterval;
import io.dentall.totoro.domain.enumeration.WeekDay;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the CalendarSetting entity. This class is used in CalendarSettingResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /calendar-settings?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CalendarSettingCriteria implements Serializable {
    /**
     * Class for filtering TimeInterval
     */
    public static class TimeIntervalFilter extends Filter<TimeInterval> {
    }
    /**
     * Class for filtering WeekDay
     */
    public static class WeekDayFilter extends Filter<WeekDay> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter startTime;

    private StringFilter endTime;

    private TimeIntervalFilter timeInterval;

    private WeekDayFilter weekday;

    private LocalDateFilter startDate;

    private LocalDateFilter endDate;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public TimeIntervalFilter getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(TimeIntervalFilter timeInterval) {
        this.timeInterval = timeInterval;
    }

    public WeekDayFilter getWeekday() {
        return weekday;
    }

    public void setWeekday(WeekDayFilter weekday) {
        this.weekday = weekday;
    }

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public LocalDateFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateFilter endDate) {
        this.endDate = endDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CalendarSettingCriteria that = (CalendarSettingCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(startTime, that.startTime) &&
            Objects.equals(endTime, that.endTime) &&
            Objects.equals(timeInterval, that.timeInterval) &&
            Objects.equals(weekday, that.weekday) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        startTime,
        endTime,
        timeInterval,
        weekday,
        startDate,
        endDate
        );
    }

    @Override
    public String toString() {
        return "CalendarSettingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (startTime != null ? "startTime=" + startTime + ", " : "") +
                (endTime != null ? "endTime=" + endTime + ", " : "") +
                (timeInterval != null ? "timeInterval=" + timeInterval + ", " : "") +
                (weekday != null ? "weekday=" + weekday + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
            "}";
    }

}
