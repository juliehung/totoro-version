package io.dentall.totoro.service.dto;

import io.dentall.totoro.domain.enumeration.AppointmentStatus;
import io.github.jhipster.service.filter.*;

import java.io.Serializable;
import java.util.Objects;

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

    private BooleanFilter baseFloor;

    private IntegerFilter colorId;

    private BooleanFilter archived;

    private BooleanFilter contacted;

    private LongFilter patientId;

    private LongFilter registrationId;

    private StringFilter registrationType;

    private LongFilter doctorId;

    private LongFilter treatmentProcedureId;

    private LongFilter disposalId;

    private BooleanFilter noCard;

    private InstantFilter arrivalTime;

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

    public BooleanFilter getContacted() {
        return contacted;
    }

    public void setContacted(BooleanFilter contacted) {
        this.contacted = contacted;
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

    public StringFilter getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(StringFilter registrationType) {
        this.registrationType = registrationType;
    }

    public LongFilter getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(LongFilter doctorId) {
        this.doctorId = doctorId;
    }

    public LongFilter getTreatmentProcedureId() {
        return treatmentProcedureId;
    }

    public void setTreatmentProcedureId(LongFilter treatmentProcedureId) {
        this.treatmentProcedureId = treatmentProcedureId;
    }

    public LongFilter getDisposalId() {
        return disposalId;
    }

    public void setDisposalId(LongFilter disposalId) {
        this.disposalId = disposalId;
    }

    public BooleanFilter getNoCard() {
        return noCard;
    }

    public void setNoCard(BooleanFilter noCard) {
        this.noCard = noCard;
    }

    public InstantFilter getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(InstantFilter arrivalTime) {
        this.arrivalTime = arrivalTime;
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
        return Objects.equals(id, that.id) &&
            Objects.equals(status, that.status) &&
            Objects.equals(subject, that.subject) &&
            Objects.equals(note, that.note) &&
            Objects.equals(expectedArrivalTime, that.expectedArrivalTime) &&
            Objects.equals(requiredTreatmentTime, that.requiredTreatmentTime) &&
            Objects.equals(microscope, that.microscope) &&
            Objects.equals(baseFloor, that.baseFloor) &&
            Objects.equals(colorId, that.colorId) &&
            Objects.equals(archived, that.archived) &&
            Objects.equals(contacted, that.contacted) &&
            Objects.equals(patientId, that.patientId) &&
            Objects.equals(registrationId, that.registrationId) &&
            Objects.equals(registrationType, that.registrationType) &&
            Objects.equals(doctorId, that.doctorId) &&
            Objects.equals(treatmentProcedureId, that.treatmentProcedureId) &&
            Objects.equals(disposalId, that.disposalId) &&
            Objects.equals(noCard, that.noCard) &&
            Objects.equals(arrivalTime, that.arrivalTime);
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
            baseFloor,
            colorId,
            archived,
            contacted,
            patientId,
            registrationId,
            registrationType,
            doctorId,
            treatmentProcedureId,
            disposalId,
            noCard,
            arrivalTime
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
            (baseFloor != null ? "baseFloor=" + baseFloor + ", " : "") +
            (colorId != null ? "colorId=" + colorId + ", " : "") +
            (archived != null ? "archived=" + archived + ", " : "") +
            (contacted != null ? "contacted=" + contacted + ", " : "") +
            (patientId != null ? "patientId=" + patientId + ", " : "") +
            (registrationId != null ? "registrationId=" + registrationId + ", " : "") +
            (registrationType != null ? "registrationType=" + registrationType + ", " : "") +
            (doctorId != null ? "doctorId=" + doctorId + ", " : "") +
            (treatmentProcedureId != null ? "treatmentProcedureId=" + treatmentProcedureId + ", " : "") +
            (disposalId != null ? "disposalId=" + disposalId + ", " : "") +
            (noCard != null ? "noCard=" + noCard + ", " : "") +
            (arrivalTime != null ? "arrivalTime=" + arrivalTime + ", " : "") +
            "}";
    }

    public boolean isOnlyPatientId() {
        return id == null &&
            status == null &&
            subject == null &&
            note == null &&
            expectedArrivalTime == null &&
            requiredTreatmentTime == null &&
            microscope == null &&
            baseFloor == null &&
            colorId == null &&
            archived == null &&
            contacted == null &&
            patientId != null &&
            registrationId == null &&
            registrationType == null &&
            doctorId == null &&
            treatmentProcedureId == null &&
            disposalId == null &&
            noCard == null &&
            arrivalTime == null;
    }
}
