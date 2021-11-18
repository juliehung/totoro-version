package io.dentall.totoro.service.dto;

import io.dentall.totoro.domain.enumeration.TreatmentType;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the Treatment entity. This class is used in TreatmentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /treatments?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TreatmentCriteria implements Serializable {
    /**
     * Class for filtering TreatmentType
     */
    public static class TreatmentTypeFilter extends Filter<TreatmentType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter chiefComplaint;

    private StringFilter goal;

    private StringFilter note;

    private StringFilter finding;

    private TreatmentTypeFilter type;

    private LongFilter patientId;

    private LongFilter treatmentPlanId;

    private boolean ignoreTodo = false;

    private boolean eagerload = false;

    private boolean tpCompleted = false;

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

    public TreatmentTypeFilter getType() {
        return type;
    }

    public void setType(TreatmentTypeFilter type) {
        this.type = type;
    }

    public LongFilter getPatientId() {
        return patientId;
    }

    public void setPatientId(LongFilter patientId) {
        this.patientId = patientId;
    }

    public LongFilter getTreatmentPlanId() {
        return treatmentPlanId;
    }

    public void setTreatmentPlanId(LongFilter treatmentPlanId) {
        this.treatmentPlanId = treatmentPlanId;
    }

    public boolean getIgnoreTodo() {
        return ignoreTodo;
    }

    public void setIgnoreTodo(boolean ignoreTodo) {
        this.ignoreTodo = ignoreTodo;
    }

    public boolean getEagerload() {
        return eagerload;
    }

    public void setEagerload(boolean eagerload) {
        this.eagerload = eagerload;
    }

    public boolean getTpCompleted() {
        return tpCompleted;
    }

    public void setTpCompleted(boolean tpCompleted) {
        this.tpCompleted = tpCompleted;
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
        return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(chiefComplaint, that.chiefComplaint) &&
            Objects.equals(goal, that.goal) &&
            Objects.equals(note, that.note) &&
            Objects.equals(finding, that.finding) &&
            Objects.equals(type, that.type) &&
            Objects.equals(patientId, that.patientId) &&
            Objects.equals(treatmentPlanId, that.treatmentPlanId) &&
            Objects.equals(ignoreTodo, that.ignoreTodo) &&
            Objects.equals(eagerload, that.eagerload) &&
            Objects.equals(tpCompleted, that.tpCompleted);
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
            type,
            patientId,
            treatmentPlanId,
            ignoreTodo,
            eagerload,
            tpCompleted
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
            (type != null ? "type=" + type + ", " : "") +
            (patientId != null ? "patientId=" + patientId + ", " : "") +
            (treatmentPlanId != null ? "treatmentPlanId=" + treatmentPlanId + ", " : "") +
            ("ignoreTodo=" + ignoreTodo + ", ") +
            ("eagerload=" + eagerload + ", ") +
            ("tpCompleted=" + tpCompleted + ", ") +
            "}";
    }

    public boolean isOnlyPatientId() {
        return id == null &&
            name == null &&
            chiefComplaint == null &&
            goal == null &&
            note == null &&
            finding == null &&
            type == null &&
            patientId != null &&
            treatmentPlanId == null &&
            !ignoreTodo &&
            !eagerload &&
            !tpCompleted;
    }
}