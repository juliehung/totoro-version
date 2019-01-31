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
 * Criteria class for the Treatment entity. This class is used in TreatmentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /treatments?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TreatmentCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter chiefComplaint;

    private StringFilter goal;

    private StringFilter note;

    private StringFilter finding;

    private LongFilter patientId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(StringFilter chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }

    public StringFilter getGoal() {
        return goal;
    }

    public void setGoal(StringFilter goal) {
        this.goal = goal;
    }

    public StringFilter getNote() {
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public StringFilter getFinding() {
        return finding;
    }

    public void setFinding(StringFilter finding) {
        this.finding = finding;
    }

    public LongFilter getPatientId() {
        return patientId;
    }

    public void setPatientId(LongFilter patientId) {
        this.patientId = patientId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TreatmentCriteria that = (TreatmentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(chiefComplaint, that.chiefComplaint) &&
            Objects.equals(goal, that.goal) &&
            Objects.equals(note, that.note) &&
            Objects.equals(finding, that.finding) &&
            Objects.equals(patientId, that.patientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        chiefComplaint,
        goal,
        note,
        finding,
        patientId
        );
    }

    @Override
    public String toString() {
        return "TreatmentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (chiefComplaint != null ? "chiefComplaint=" + chiefComplaint + ", " : "") +
                (goal != null ? "goal=" + goal + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (finding != null ? "finding=" + finding + ", " : "") +
                (patientId != null ? "patientId=" + patientId + ", " : "") +
            "}";
    }

}
