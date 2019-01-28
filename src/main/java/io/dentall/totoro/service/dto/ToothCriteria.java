package io.dentall.totoro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Tooth entity. This class is used in ToothResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /teeth?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ToothCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter position;

    private StringFilter before;

    private StringFilter planned;

    private StringFilter after;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPosition() {
        return position;
    }

    public void setPosition(StringFilter position) {
        this.position = position;
    }

    public StringFilter getBefore() {
        return before;
    }

    public void setBefore(StringFilter before) {
        this.before = before;
    }

    public StringFilter getPlanned() {
        return planned;
    }

    public void setPlanned(StringFilter planned) {
        this.planned = planned;
    }

    public StringFilter getAfter() {
        return after;
    }

    public void setAfter(StringFilter after) {
        this.after = after;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ToothCriteria that = (ToothCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(position, that.position) &&
            Objects.equals(before, that.before) &&
            Objects.equals(planned, that.planned) &&
            Objects.equals(after, that.after);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        position,
        before,
        planned,
        after
        );
    }

    @Override
    public String toString() {
        return "ToothCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (position != null ? "position=" + position + ", " : "") +
                (before != null ? "before=" + before + ", " : "") +
                (planned != null ? "planned=" + planned + ", " : "") +
                (after != null ? "after=" + after + ", " : "") +
            "}";
    }

}
