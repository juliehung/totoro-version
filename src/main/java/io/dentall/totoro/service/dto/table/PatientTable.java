package io.dentall.totoro.service.dto.table;

import io.dentall.totoro.domain.enumeration.Blood;
import io.dentall.totoro.domain.enumeration.Gender;

import java.time.Instant;
import java.time.LocalDate;

public interface PatientTable extends AuditingElement {
    Long getId();
    String getDisplayName();
    String getName();
    String getPhone();
    Gender getGender();
    LocalDate getBirth();
    String getNationalId();
    String getMedicalId();
    String getAddress();
    String getEmail();
    Blood getBlood();
    String getCardId();
    String getVip();
    String getEmergencyName();
    String getEmergencyPhone();
    Instant getDeleteDate();
    LocalDate getScaling();
    String getLineId();
    String getFbId();
    String getNote();
    String getClinicNote();
    Instant getWriteIcTime();
    String getCustomizedDisease();
    String getCustomizedBloodDisease();
    String getCustomizedAllergy();
    String getCustomizedOther();

//    byte[] getAvatar();
    String getAvatarContentType();
    Boolean getNewPatient();
    String getEmergencyAddress();
    String getEmergencyRelationship();
    String getMainNoticeChannel();
    String getCareer();
    String getMarriage();
    String getTeethGraphPermanentSwitch();
    String getIntroducer();
    LocalDate getDueDate();
    Boolean getDisabled();

    Long getQuestionnaire_Id();
    Long getPatientIdentity_Id();
    Long getLastDoctorUser_Id();
    Long getFirstDoctorUser_Id();

    String getCaseManager();
    Boolean getVipPatient();
}
