package io.dentall.totoro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.dentall.totoro.domain.enumeration.TimeType;
import io.dentall.totoro.domain.enumeration.TimeInterval;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

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
     * Class for filtering TimeType
     */
    public static class TimeTypeFilter extends Filter<TimeType> {
    }

    /**
     * Class for filtering TimeInterval
     */
    public static class TimeIntervalFilter extends Filter<TimeInterval> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter start;

    private InstantFilter end;

    private TimeTypeFilter timeType;

    private TimeIntervalFilter timeInterval;

    private StringFilter note;

    private LongFilter doctorId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getStart() {
        return start;
    }

    public void setStart(InstantFilter start) {
        this.start = start;
    }

    public InstantFilter getEnd() {
        return end;
    }

    public void setEnd(InstantFilter end) {
        this.end = end;
    }

    public TimeTypeFilter getTimeType() {
        return timeType;
    }

    public void setTimeType(TimeTypeFilter timeType) {
        this.timeType = timeType;
    }

    public TimeIntervalFilter getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(TimeIntervalFilter timeInterval) {
        this.timeInterval = timeInterval;
    }

    public StringFilter getNote() {
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public LongFilter getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(LongFilter doctorId) {
        this.doctorId = doctorId;
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
            Objects.equals(start, that.start) &&
            Objects.equals(end, that.end) &&
            Objects.equals(timeType, that.timeType) &&
            Objects.equals(timeInterval, that.timeInterval) &&
            Objects.equals(note, that.note) &&
            Objects.equals(doctorId, that.doctorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            start,
            end,
            timeType,
            timeInterval,
            note,
            doctorId
        );
    }

    @Override
    public String toString() {
        return "CalendarCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (start != null ? "start=" + start + ", " : "") +
            (end != null ? "end=" + end + ", " : "") +
            (timeType != null ? "timeType=" + timeType + ", " : "") +
            (timeInterval != null ? "timeInterval=" + timeInterval + ", " : "") +
            (note != null ? "note=" + note + ", " : "") +
            (doctorId != null ? "note=" + doctorId + ", " : "") +
            "}";
    }

}
