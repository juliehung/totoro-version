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
 * Criteria class for the ProcedureType entity. This class is used in ProcedureTypeResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /procedure-types?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProcedureTypeCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter major;

    private StringFilter minor;

    private BooleanFilter display;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMajor() {
        return major;
    }

    public void setMajor(StringFilter major) {
        this.major = major;
    }

    public StringFilter getMinor() {
        return minor;
    }

    public void setMinor(StringFilter minor) {
        this.minor = minor;
    }

    public BooleanFilter getDisplay() {
        return display;
    }

    public void setDisplay(BooleanFilter display) {
        this.display = display;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProcedureTypeCriteria that = (ProcedureTypeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(major, that.major) &&
            Objects.equals(minor, that.minor) &&
            Objects.equals(display, that.display);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        major,
        minor,
        display
        );
    }

    @Override
    public String toString() {
        return "ProcedureTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (major != null ? "major=" + major + ", " : "") +
                (minor != null ? "minor=" + minor + ", " : "") +
                (display != null ? "display=" + display + ", " : "") +
            "}";
    }

}
