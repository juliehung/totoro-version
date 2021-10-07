package io.dentall.totoro.util;

import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.domain.enumeration.*;
import io.dentall.totoro.service.dto.table.DisposalTable;
import io.dentall.totoro.service.dto.table.NhiExtendDisposalTable;
import io.dentall.totoro.service.dto.table.NhiExtendTreatmentProcedureTable;
import io.dentall.totoro.service.dto.table.PatientTable;

import java.time.Instant;
import java.time.LocalDate;

public class TableGenerator {

    public static class DisposalTableGenerator implements DisposalTable {

        private Long id;

        public DisposalTableGenerator(Long id) {
            this.id = id;
        }

        @Override
        public String getCreatedBy() {
            return null;
        }

        @Override
        public String getLastModifiedBy() {
            return null;
        }

        @Override
        public Instant getCreatedDate() {
            return null;
        }

        @Override
        public Instant getLastModifiedDate() {
            return null;
        }

        @Override
        public Long getId() {
            return id;
        }

        @Override
        public DisposalStatus getStatus() {
            return null;
        }

        @Override
        public Double getTotal() {
            return null;
        }

        @Override
        public Instant getDateTime() {
            return null;
        }

        @Override
        public Instant getDateTimeEnd() {
            return null;
        }

        @Override
        public String getChiefComplaint() {
            return null;
        }

        @Override
        public String getRevisitContent() {
            return null;
        }

        @Override
        public DisposalRevisitInterval getRevisitInterval() {
            return null;
        }

        @Override
        public Integer getRevisitTreatmentTime() {
            return null;
        }

        @Override
        public String getRevisitComment() {
            return null;
        }

        @Override
        public Boolean getRevisitWillNotHappen() {
            return null;
        }

        @Override
        public Long getPrescription_Id() {
            return null;
        }

        @Override
        public Long getTodo_Id() {
            return null;
        }

        @Override
        public Long getRegistration_Id() {
            return null;
        }
    }

    public static class NhiExtendDisposalTableGenerator implements NhiExtendDisposalTable {

        private final Long id;

        private final String patientIdentity;

        private final String a23;

        public NhiExtendDisposalTableGenerator(NhiExtendDisposal ned) {
            this.id = ned.getId();
            this.patientIdentity = ned.getPatientIdentity();
            this.a23 = ned.getA23();
        }

        public NhiExtendDisposalTableGenerator(Long id, String patientIdentity) {
            this.id = id;
            this.patientIdentity = patientIdentity;
            this.a23 = "";
        }

        @Override
        public Long getId() {
            return this.id;
        }

        @Override
        public String getA11() {
            return null;
        }

        @Override
        public String getA12() {
            return null;
        }

        @Override
        public String getA13() {
            return null;
        }

        @Override
        public String getA14() {
            return null;
        }

        @Override
        public String getA15() {
            return null;
        }

        @Override
        public String getA16() {
            return null;
        }

        @Override
        public String getA17() {
            return null;
        }

        @Override
        public String getA18() {
            return null;
        }

        @Override
        public String getA19() {
            return null;
        }

        @Override
        public String getA22() {
            return null;
        }

        @Override
        public String getA23() {
            return null;
        }

        @Override
        public String getA25() {
            return null;
        }

        @Override
        public String getA26() {
            return null;
        }

        @Override
        public String getA27() {
            return null;
        }

        @Override
        public String getA31() {
            return null;
        }

        @Override
        public String getA32() {
            return null;
        }

        @Override
        public String getA41() {
            return null;
        }

        @Override
        public String getA42() {
            return null;
        }

        @Override
        public String getA43() {
            return null;
        }

        @Override
        public String getA44() {
            return null;
        }

        @Override
        public String getA54() {
            return null;
        }

        @Override
        public LocalDate getDate() {
            return null;
        }

        @Override
        public NhiExtendDisposalUploadStatus getUploadStatus() {
            return null;
        }

        @Override
        public String getExaminationCode() {
            return null;
        }

        @Override
        public Integer getExaminationPoint() {
            return null;
        }

        @Override
        public String getPatientIdentity() {
            return this.patientIdentity;
        }

        @Override
        public Long getPatientId() {
            return null;
        }

        @Override
        public String getCategory() {
            return null;
        }

        @Override
        public LocalDate getReplenishmentDate() {
            return null;
        }

        @Override
        public Boolean getCheckedMonthDeclaration() {
            return null;
        }

        @Override
        public Boolean getCheckedAuditing() {
            return null;
        }

        @Override
        public String getSerialNumber() {
            return null;
        }

        @Override
        public String getReferralHospitalCode() {
            return null;
        }

        @Override
        public Long getDependedTreatmentProcedureId() {
            return null;
        }

        @Override
        public Long getDisposal_Id() {
            return null;
        }
    }

    public static class NhiExtendTreatmentProcedureTableGenerator implements NhiExtendTreatmentProcedureTable {

        private final Long treatmentProcedureId;

        private final String a71;

        private final String a73;

        private final String a74;

        private final String a75;

        public NhiExtendTreatmentProcedureTableGenerator(NhiExtendTreatmentProcedure netp) {
            this.treatmentProcedureId = netp.getId();
            this.a71 = netp.getA71();
            this.a73 = netp.getA73();
            this.a74 = netp.getA74();
            this.a75 = netp.getA75();
        }

        public NhiExtendTreatmentProcedureTableGenerator(Long treatmentProcedureId, String a71, String a73, String a74, String a75) {
            this.treatmentProcedureId = treatmentProcedureId;
            this.a71 = a71;
            this.a73 = a73;
            this.a74 = a74;
            this.a75 = a75;
        }

        @Override
        public Long getTreatmentProcedure_Id() {
            return this.treatmentProcedureId;
        }

        @Override
        public String getA71() {
            return this.a71;
        }

        @Override
        public String getA72() {
            return null;
        }

        @Override
        public String getA73() {
            return this.a73;
        }

        @Override
        public String getA74() {
            return this.a74;
        }

        @Override
        public String getA75() {
            return null;
        }

        @Override
        public String getA76() {
            return null;
        }

        @Override
        public String getA77() {
            return null;
        }

        @Override
        public String getA78() {
            return null;
        }

        @Override
        public String getA79() {
            return null;
        }

        @Override
        public String getCheck() {
            return null;
        }

        @Override
        public Long getNhiExtendDisposal_Id() {
            return null;
        }
    }

    public static class PatientTableGenerator implements PatientTable {

        private final Long id;

        public PatientTableGenerator(Long id) {
            this.id = id;
        }

        @Override
        public Long getId() {
            return this.id;
        }

        @Override
        public String getDisplayName() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public String getPhone() {
            return null;
        }

        @Override
        public Gender getGender() {
            return null;
        }

        @Override
        public LocalDate getBirth() {
            return null;
        }

        @Override
        public String getNationalId() {
            return null;
        }

        @Override
        public String getMedicalId() {
            return null;
        }

        @Override
        public String getAddress() {
            return null;
        }

        @Override
        public String getEmail() {
            return null;
        }

        @Override
        public Blood getBlood() {
            return null;
        }

        @Override
        public String getCardId() {
            return null;
        }

        @Override
        public String getVip() {
            return null;
        }

        @Override
        public String getEmergencyName() {
            return null;
        }

        @Override
        public String getEmergencyPhone() {
            return null;
        }

        @Override
        public Instant getDeleteDate() {
            return null;
        }

        @Override
        public LocalDate getScaling() {
            return null;
        }

        @Override
        public String getLineId() {
            return null;
        }

        @Override
        public String getFbId() {
            return null;
        }

        @Override
        public String getNote() {
            return null;
        }

        @Override
        public String getClinicNote() {
            return null;
        }

        @Override
        public Instant getWriteIcTime() {
            return null;
        }

        @Override
        public String getCustomizedDisease() {
            return null;
        }

        @Override
        public String getCustomizedBloodDisease() {
            return null;
        }

        @Override
        public String getCustomizedAllergy() {
            return null;
        }

        @Override
        public String getCustomizedOther() {
            return null;
        }

        @Override
        public String getAvatarContentType() {
            return null;
        }

        @Override
        public Boolean getNewPatient() {
            return null;
        }

        @Override
        public String getEmergencyAddress() {
            return null;
        }

        @Override
        public String getEmergencyRelationship() {
            return null;
        }

        @Override
        public String getMainNoticeChannel() {
            return null;
        }

        @Override
        public String getCareer() {
            return null;
        }

        @Override
        public String getMarriage() {
            return null;
        }

        @Override
        public String getTeethGraphPermanentSwitch() {
            return null;
        }

        @Override
        public String getIntroducer() {
            return null;
        }

        @Override
        public LocalDate getDueDate() {
            return null;
        }

        @Override
        public Boolean getDisabled() {
            return null;
        }

        @Override
        public Long getQuestionnaire_Id() {
            return null;
        }

        @Override
        public Long getPatientIdentity_Id() {
            return null;
        }

        @Override
        public Long getLastDoctorUser_Id() {
            return null;
        }

        @Override
        public Long getFirstDoctorUser_Id() {
            return null;
        }

        @Override
        public String getCreatedBy() {
            return null;
        }

        @Override
        public String getLastModifiedBy() {
            return null;
        }

        @Override
        public Instant getCreatedDate() {
            return null;
        }

        @Override
        public Instant getLastModifiedDate() {
            return null;
        }

        @Override
        public String getCaseManager() {
            return null;
        }

        @Override
        public Boolean getVipPatient() {
            return null;
        }

    };
}