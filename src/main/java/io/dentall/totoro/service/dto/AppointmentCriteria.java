package io.dentall.totoro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.dentall.totoro.domain.enumeration.AppointmentStatus;
import io.dentall.totoro.service.filter.RegistrationTypeFilter;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the Appointment entity. This class is used in AppointmentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /appointments?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AppointmentCriteria implements Serializable {
    /**
     * Class for filtering AppointmentStatus
     */
    public static class AppointmentStatusFilter extends Filter<AppointmentStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private AppointmentStatusFilter status;

    private StringFilter subject;

    private StringFilter note;

    private InstantFilter expectedArrivalTime;

    private IntegerFilter requiredTreatmentTime;

    private BooleanFilter microscope;

    private BooleanFilter newPatient;

    private BooleanFilter baseFloor;

    private IntegerFilter colorId;

    private BooleanFilter archived;

    private LongFilter patientId;

    private LongFilter registrationId;

    private RegistrationTypeFilter registrationType;

    private IntegerFilter registrationTypeValue;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public AppointmentStatusFilter getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatusFilter status) {
        this.status = status;
    }

    public StringFilter getSubject() {
        return subject;
    }

    public void setSubject(StringFilter subject) {
        this.subject = subject;
    }

    public StringFilter getNote() {
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public InstantFilter getExpectedArrivalTime() {
        return expectedArrivalTime;
    }

    public void setExpectedArrivalTime(InstantFilter expectedArrivalTime) {
        this.expectedArrivalTime = expectedArrivalTime;
    }

    public IntegerFilter getRequiredTreatmentTime() {
        return requiredTreatmentTime;
    }

    public void setRequiredTreatmentTime(IntegerFilter requiredTreatmentTime) {
        this.requiredTreatmentTime = requiredTreatmentTime;
    }

    public BooleanFilter getMicroscope() {
        return microscope;
    }

    public void setMicroscope(BooleanFilter microscope) {
        this.microscope = microscope;
    }

    public BooleanFilter getNewPatient() {
        return newPatient;
    }

    public void setNewPatient(BooleanFilter newPatient) {
        this.newPatient = newPatient;
    }

    public BooleanFilter getBaseFloor() {
        return baseFloor;
    }

    public void setBaseFloor(BooleanFilter baseFloor) {
        this.baseFloor = baseFloor;
    }

    public IntegerFilter getColorId() {
        return colorId;
    }

    public void setColorId(IntegerFilter colorId) {
        this.colorId = colorId;
    }

    public BooleanFilter getArchived() {
        return archived;
    }

    public void setArchived(BooleanFilter archived) {
        this.archived = archived;
    }

    public LongFilter getPatientId() {
        return patientId;
    }

    public void setPatientId(LongFilter patientId) {
        this.patientId = patientId;
    }

    public LongFilter getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(LongFilter registrationId) {
        this.registrationId = registrationId;
    }

    public RegistrationTypeFilter getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(RegistrationTypeFilter registrationType) {
        this.registrationType = registrationType;
    }

    public IntegerFilter getRegistrationTypeValue() {
        return registrationTypeValue;
    }

    public void setRegistrationTypeValue(IntegerFilter registrationTypeValue) {
        this.registrationTypeValue = registrationTypeValue;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AppointmentCriteria that = (AppointmentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(status, that.status) &&
            Objects.equals(subject, that.subject) &&
            Objects.equals(note, that.note) &&
            Objects.equals(expectedArrivalTime, that.expectedArrivalTime) &&
            Objects.equals(requiredTreatmentTime, that.requiredTreatmentTime) &&
            Objects.equals(microscope, that.microscope) &&
            Objects.equals(newPatient, that.newPatient) &&
            Objects.equals(baseFloor, that.baseFloor) &&
            Objects.equals(colorId, that.colorId) &&
            Objects.equals(archived, that.archived) &&
            Objects.equals(patientId, that.patientId) &&
            Objects.equals(registrationId, that.registrationId) &&
                Objects.equals(registrationType, that.registrationType) &&
                Objects.equals(registrationTypeValue, that.registrationTypeValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        status,
        subject,
        note,
        expectedArrivalTime,
        requiredTreatmentTime,
        microscope,
        newPatient,
        baseFloor,
        colorId,
        archived,
        patientId,
        registrationId,
            registrationType,
            registrationTypeValue
        );
    }

    @Override
    public String toString() {
        return "AppointmentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (subject != null ? "subject=" + subject + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (expectedArrivalTime != null ? "expectedArrivalTime=" + expectedArrivalTime + ", " : "") +
                (requiredTreatmentTime != null ? "requiredTreatmentTime=" + requiredTreatmentTime + ", " : "") +
                (microscope != null ? "microscope=" + microscope + ", " : "") +
                (newPatient != null ? "newPatient=" + newPatient + ", " : "") +
                (baseFloor != null ? "baseFloor=" + baseFloor + ", " : "") +
                (colorId != null ? "colorId=" + colorId + ", " : "") +
                (archived != null ? "archived=" + archived + ", " : "") +
                (patientId != null ? "patientId=" + patientId + ", " : "") +
                (registrationId != null ? "registrationId=" + registrationId + ", " : "") +
            (registrationType != null ? "registrationType=" + registrationType + ", " : "") +
            (registrationTypeValue != null ? "registrationTypeValue=" + registrationTypeValue + ", " : "") +
            "}";
    }

}
