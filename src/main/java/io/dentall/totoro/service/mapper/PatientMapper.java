package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.PatientIdentity;
import io.dentall.totoro.domain.Questionnaire;
import io.dentall.totoro.service.dto.table.PatientTable;
import io.dentall.totoro.service.util.MapperUtil;

public class PatientMapper {

    public static Patient patientTableToPatient(PatientTable patientTable) {
        Patient patient = new Patient();

        patient.setId(patientTable.getId());
        patient.setDisplayName(patientTable.getDisplayName());
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
        patient.setCustomizedAllergy(patientTable.getCustomizedAllergy());
        patient.setCustomizedBloodDisease(patientTable.getCustomizedBloodDisease());
        patient.setCustomizedDisease(patientTable.getCustomizedDisease());
        patient.setCustomizedOther(patientTable.getCustomizedOther());
//    byte[] getAvatar();
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
        patient.setCaseManager(patientTable.getCaseManager());
        patient.setVipPatient(patientTable.getVipPatient());

        // Relationship
        if (patientTable.getQuestionnaire_Id() != null) {
            Questionnaire questionnaire = new Questionnaire();
            questionnaire.setId(patientTable.getQuestionnaire_Id());
            MapperUtil.setNullAuditing(questionnaire);

            patient.setQuestionnaire(questionnaire);
        }

        if (patientTable.getPatientIdentity_Id() != null) {
            PatientIdentity patientIdentity = new PatientIdentity();
            patientIdentity.setId(patientTable.getPatientIdentity_Id());

            patient.setPatientIdentity(patientIdentity);
        }

        MapperUtil.setAuditing(patient, patientTable);

        return patient;
    }
}
