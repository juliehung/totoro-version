package io.dentall.totoro.util;

import io.dentall.totoro.domain.enumeration.Blood;
import io.dentall.totoro.domain.enumeration.Gender;
import io.dentall.totoro.domain.enumeration.NhiExtendDisposalUploadStatus;
import io.dentall.totoro.service.dto.table.NhiExtendDisposalTable;
import io.dentall.totoro.service.dto.table.NhiExtendTreatmentProcedureTable;
import io.dentall.totoro.service.dto.table.PatientTable;

import java.time.Instant;
import java.time.LocalDate;

public class TableGenerator {
    public static final NhiExtendDisposalTable nhiExtendDisposalTable = new NhiExtendDisposalTable() {
        @Override
        public Long getId() {
            return DataGenerator.ID_1;
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
            return null;
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
        public Long getDisposal_Id() {
            return null;
        }
    };

    public static final NhiExtendTreatmentProcedureTable nhiExtendTreatmentProcedureTable = new NhiExtendTreatmentProcedureTable() {
        @Override
        public Long getTreatmentProcedure_Id() {
            return DataGenerator.ID_1;
        }

        @Override
        public String getA71() {
            return DataGenerator.NHI_TREATMENT_DATE_NOW;
        }

        @Override
        public String getA72() {
            return null;
        }

        @Override
        public String getA73() {
            return null;
        }

        @Override
        public String getA74() {
            return null;
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
    };

    public static final PatientTable patientTable = new PatientTable() {
        @Override
        public Long getId() {
            return DataGenerator.ID_1;
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
    };
}
