package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.ExtendUser;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.PatientIdentity;
import io.dentall.totoro.domain.Questionnaire;
import io.dentall.totoro.service.dto.table.PatientTable;

public class PatientMapper {
    public static Patient patientTableToPatient(PatientTable patientTable) {
        Patient patient = new Patient();

        patient.setId(patientTable.getId());
        patient.setName(patientTable.getName());
        patient.setPhone(patientTable.getPhone());
        patient.setGender(patientTable.getGender());
        patient.setBirth(patientTable.getBirth());
        patient.setNationalId(patientTable.getNationalId());
        patient.setMedicalId(patientTable.getMedicalId());
        patient.setAddress(patientTable.getAddress());
        patient.setEmail(patientTable.getEmail());
        patient.setBlood(patientTable.getBlood());
        patient.setCardId(patientTable.getCardId());
        patient.setVip(patientTable.getVip());
        patient.setEmergencyName(patientTable.getEmergencyName());
        patient.setEmergencyPhone(patientTable.getEmergencyPhone());
        patient.setDeleteDate(patientTable.getDeleteDate());
        patient.setScaling(patientTable.getScaling());
        patient.setLineId(patientTable.getLineId());
        patient.setFbId(patientTable.getFbId());
        patient.setNote(patientTable.getNote());
        patient.setClinicNote(patientTable.getClinicNote());
        patient.setWriteIcTime(patientTable.getWriteIcTime());
//        patient.setAvata(patientTable.getAvata());
        patient.setAvatarContentType(patientTable.getAvatarContentType());
        patient.setNewPatient(patientTable.getNewPatient());
        patient.setEmergencyAddress(patientTable.getEmergencyAddress());
        patient.setEmergencyRelationship(patientTable.getEmergencyRelationship());
        patient.setMainNoticeChannel(patientTable.getMainNoticeChannel());
        patient.setCareer(patientTable.getCareer());
        patient.setMarriage(patientTable.getMarriage());
        patient.setTeethGraphPermanentSwitch(patientTable.getTeethGraphPermanentSwitch());
        patient.setIntroducer(patientTable.getIntroducer());
        patient.setDueDate(patientTable.getDueDate());

        // Relationship
        if (patientTable.getQuestionnaire_Id() != null) {
            Questionnaire questionnaire = new Questionnaire();
            questionnaire.setId(patientTable.getQuestionnaire_Id());
        }

        if (patientTable.getPatientIdentity_Id() != null) {
            PatientIdentity patientidentity = new PatientIdentity();
            patientidentity.setId(patientTable.getPatientIdentity_Id());
        }

        if (patientTable.getLastDoctorUser_Id() != null) {
            ExtendUser lastdoctoruser = new ExtendUser();
            lastdoctoruser.setId(patientTable.getLastDoctorUser_Id());
        }

        if (patientTable.getFirstDoctorUser_Id() != null) {
            ExtendUser firstdoctoruser = new ExtendUser();
            firstdoctoruser.setId(patientTable.getFirstDoctorUser_Id());
        }

        return patient;
    }
}
