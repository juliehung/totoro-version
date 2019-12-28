package io.dentall.totoro.repository.dao;

import io.dentall.totoro.domain.ExtendUser;
import io.dentall.totoro.domain.enumeration.AppointmentStatus;
import io.dentall.totoro.domain.enumeration.Blood;
import io.dentall.totoro.domain.enumeration.Gender;
import io.dentall.totoro.domain.enumeration.RegistrationStatus;

import java.time.Instant;
import java.time.LocalDate;

public class AppointmentDAO {
    // Appointment
    private final Long id;
    private final AppointmentStatus status;
    private final String subject;
    private final String note;
    private final Instant expectedArrivalTime;
    private final Integer requiredTreatmentTime;
    private final Boolean microscope;
    private final Boolean baseFloor;
    private final Integer colorId;
    private final Boolean archived;
    private final Boolean contacted;
    // Patient
    private final Long patientId;
    private final String name;
    private final String phone;
    private final Gender gender;
    private final LocalDate birth;
    private final String nationalId;
    private final String medicalId;
    private final String address;
    private final String email;
    private final Blood blood;
    private final String cardId;
    private final String vip;
    private final String emergencyName;
    private final String emergencyPhone;
    private final String emergencyAddress;
    private final String emergencyRelationship;
    private final Instant deleteDate;
    private final LocalDate scaling;
    private final String lineId;
    private final String fbId;
    private final String patientNote;
    private final String clinicNote;
    private final Instant writeIcTime;
    private final String mainNoticeChannel;
    private final String career;
    private final String marriage;
    private final Boolean newPatient;
    // Registration
    private final Long registrationId;
    private final RegistrationStatus registrationStatus;
    private final Instant arrivalTime;
    private final String type;
    private final Boolean onSite;
    private final Boolean noCard;
    // Doctor
    private final ExtendUser doctor;

    public AppointmentDAO(
        Long id,
        AppointmentStatus status,
        String subject,
        String note,
        Instant expectedArrivalTime,
        Integer requiredTreatmentTime,
        Boolean microscope,
        Boolean baseFloor,
        Integer colorId,
        Boolean archived,
        Boolean contacted,
        Long patientId,
        String name,
        String phone,
        Gender gender,
        LocalDate birth,
        String nationalId,
        String medicalId,
        String address,
        String email,
        Blood blood,
        String cardId,
        String vip,
        String emergencyName,
        String emergencyPhone,
        String emergencyAddress,
        String emergencyRelationship,
        Instant deleteDate,
        LocalDate scaling,
        String lineId,
        String fbId,
        String patientNote,
        String clinicNote,
        Instant writeIcTime,
        String mainNoticeChannel,
        String career,
        String marriage,
        Boolean newPatient,
        Long registrationId,
        RegistrationStatus registrationStatus,
        Instant arrivalTime,
        String type,
        Boolean onSite,
        Boolean noCard,
        ExtendUser doctor
    ) {
        this.id = id;
        this.status = status;
        this.subject = subject;
        this.note = note;
        this.expectedArrivalTime = expectedArrivalTime;
        this.requiredTreatmentTime = requiredTreatmentTime;
        this.microscope = microscope;
        this.baseFloor = baseFloor;
        this.colorId = colorId;
        this.archived = archived;
        this.contacted = contacted;
        this.patientId = patientId;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.birth = birth;
        this.nationalId = nationalId;
        this.medicalId = medicalId;
        this.address = address;
        this.email = email;
        this.blood = blood;
        this.cardId = cardId;
        this.vip = vip;
        this.emergencyName = emergencyName;
        this.emergencyPhone = emergencyPhone;
        this.emergencyAddress = emergencyAddress;
        this.emergencyRelationship = emergencyRelationship;
        this.deleteDate = deleteDate;
        this.scaling = scaling;
        this.lineId = lineId;
        this.fbId = fbId;
        this.patientNote = patientNote;
        this.clinicNote = clinicNote;
        this.writeIcTime = writeIcTime;
        this.mainNoticeChannel = mainNoticeChannel;
        this.career = career;
        this.marriage = marriage;
        this.newPatient = newPatient;
        this.registrationId = registrationId;
        this.registrationStatus = registrationStatus;
        this.arrivalTime = arrivalTime;
        this.type = type;
        this.onSite = onSite;
        this.noCard = noCard;
        this.doctor = doctor;
    }

    public Long getId() {
        return id;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public String getSubject() {
        return subject;
    }

    public String getNote() {
        return note;
    }

    public Instant getExpectedArrivalTime() {
        return expectedArrivalTime;
    }

    public Integer getRequiredTreatmentTime() {
        return requiredTreatmentTime;
    }

    public Boolean getMicroscope() {
        return microscope;
    }

    public Boolean getBaseFloor() {
        return baseFloor;
    }

    public Integer getColorId() {
        return colorId;
    }

    public Boolean getArchived() {
        return archived;
    }

    public Boolean getContacted() {
        return contacted;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public String getNationalId() {
        return nationalId;
    }

    public String getMedicalId() {
        return medicalId;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public Blood getBlood() {
        return blood;
    }

    public String getCardId() {
        return cardId;
    }

    public String getVip() {
        return vip;
    }

    public String getEmergencyName() {
        return emergencyName;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public String getEmergencyAddress() {
        return emergencyAddress;
    }

    public String getEmergencyRelationship() {
        return emergencyRelationship;
    }

    public Instant getDeleteDate() {
        return deleteDate;
    }

    public LocalDate getScaling() {
        return scaling;
    }

    public String getLineId() {
        return lineId;
    }

    public String getFbId() {
        return fbId;
    }

    public String getPatientNote() {
        return patientNote;
    }

    public String getClinicNote() {
        return clinicNote;
    }

    public Instant getWriteIcTime() {
        return writeIcTime;
    }

    public String getMainNoticeChannel() {
        return mainNoticeChannel;
    }

    public String getCareer() {
        return career;
    }

    public String getMarriage() {
        return marriage;
    }

    public Boolean getNewPatient() {
        return newPatient;
    }

    public Long getRegistrationId() {
        return registrationId;
    }

    public RegistrationStatus getRegistrationStatus() {
        return registrationStatus;
    }

    public Instant getArrivalTime() {
        return arrivalTime;
    }

    public String getType() {
        return type;
    }

    public Boolean getOnSite() {
        return onSite;
    }

    public Boolean getNoCard() {
        return noCard;
    }

    public ExtendUser getDoctor() {
        return doctor;
    }
}
