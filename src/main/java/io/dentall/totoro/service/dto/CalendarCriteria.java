package io.dentall.totoro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.dentall.totoro.domain.enumeration.IntervalType;
import io.dentall.totoro.domain.enumeration.DateType;
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
     * Class for filtering IntervalType
     */
    public static class IntervalTypeFilter extends Filter<IntervalType> {
    }
    /**
     * Class for filtering DateType
     */
    public static class DateTypeFilter extends Filter<DateType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter date;

    private IntervalTypeFilter intervalType;

    private DateTypeFilter dateType;

    private StringFilter start;

    private StringFilter end;

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

    public IntervalTypeFilter getIntervalType() {
        return intervalType;
    }

    public void setIntervalType(IntervalTypeFilter intervalType) {
        this.intervalType = intervalType;
    }

    public DateTypeFilter getDateType() {
        return dateType;
    }

    public void setDateType(DateTypeFilter dateType) {
        this.dateType = dateType;
    }

    public StringFilter getStart() {
        return start;
    }

    public void setStart(StringFilter start) {
        this.start = start;
    }

    public StringFilter getEnd() {
        return end;
    }

    public void setEnd(StringFilter end) {
        this.end = end;
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
            Objects.equals(intervalType, that.intervalType) &&
            Objects.equals(dateType, that.dateType) &&
            Objects.equals(start, that.start) &&
            Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        date,
        intervalType,
        dateType,
        start,
        end
        );
    }

    @Override
    public String toString() {
        return "CalendarCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (intervalType != null ? "intervalType=" + intervalType + ", " : "") +
                (dateType != null ? "dateType=" + dateType + ", " : "") +
                (start != null ? "start=" + start + ", " : "") +
                (end != null ? "end=" + end + ", " : "") +
            "}";
    }

}
