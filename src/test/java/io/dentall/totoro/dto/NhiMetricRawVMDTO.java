package io.dentall.totoro.dto;

import com.univocity.parsers.annotations.Parsed;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.util.Optional.ofNullable;

public class NhiMetricRawVMDTO implements NhiMetricRawVM {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Parsed
    private Long patientId;
    @Parsed
    private String patientBirth;
    @Parsed
    private String patientName;
    @Parsed
    private Long disposalId;
    @Parsed
    private String disposalDate;
    @Parsed
    private String cardNumber;
    @Parsed
    private String cardReplenishment;
    @Parsed
    private String nhiCategory;
    @Parsed
    private String partialBurden;
    @Parsed
    private String cardReplenishmentDisposalDate;
    @Parsed
    private String examCode;
    @Parsed
    private String examPoint;
    @Parsed
    private String patientIdentity;
    @Parsed
    private String serialNumber;
    @Parsed
    private Long treatmentProcedureTotal;
    @Parsed
    private Long nhiOriginPoint;
    @Parsed
    private String treatmentProcedureSpecificCode;
    @Parsed
    private String treatmentProcedureCode;
    @Parsed
    private String treatmentProcedureTooth;
    @Parsed
    private String treatmentProcedureSurface;
    @Parsed
    private Long doctorId;
    @Parsed
    private String doctorName;

    @Override
    public Long getPatientId() {
        return this.patientId;
    }

    @Override
    public String getPatientName() {
        return this.patientName;
    }

    @Override
    public LocalDate getPatientBirth() {
        return LocalDate.parse(this.patientBirth, dateTimeFormatter);
    }

    @Override
    public Long getDisposalId() {
        return this.disposalId;
    }

    @Override
    public LocalDate getDisposalDate() {
        return LocalDate.parse(this.disposalDate, dateTimeFormatter);
    }

    @Override
    public String getCardNumber() {
        return this.cardNumber;
    }

    @Override
    public String getCardReplenishment() {
        return this.cardReplenishment;
    }

    @Override
    public String getNhiCategory() {
        return this.nhiCategory;
    }

    @Override
    public String getPartialBurden() {
        return this.partialBurden;
    }

    @Override
    public LocalDate getCardReplenishmentDisposalDate() {
        return ofNullable(this.cardReplenishmentDisposalDate).map(date -> LocalDate.parse(date, dateTimeFormatter)).orElse(null);
    }

    @Override
    public String getExamCode() {
        return this.examCode;
    }

    @Override
    public String getExamPoint() {
        return this.examPoint;
    }

    @Override
    public String getPatientIdentity() {
        return this.patientIdentity;
    }

    @Override
    public String getSerialNumber() {
        return this.serialNumber;
    }

    @Override
    public String getTreatmentProcedureCode() {
        return this.treatmentProcedureCode;
    }

    @Override
    public String getTreatmentProcedureTooth() {
        return this.treatmentProcedureTooth;
    }

    @Override
    public String getTreatmentProcedureSurface() {
        return this.treatmentProcedureSurface;
    }

    @Override
    public Long getTreatmentProcedureTotal() {
        return this.treatmentProcedureTotal;
    }

    @Override
    public Long getNhiOriginPoint() {
        return this.nhiOriginPoint;
    }

    @Override
    public String getTreatmentProcedureSpecificCode() {
        return this.treatmentProcedureSpecificCode;
    }

    @Override
    public Long getDoctorId() {
        return this.doctorId;
    }

    @Override
    public String toString() {
        return "{"
            .concat(patientId == null ? "" : " \"patientId\": \"" + patientId + "\"")
            .concat(patientBirth == null ? "" : ", \"patientBirth\": \"" + patientBirth + "\"")
            .concat(patientName == null ? "" : ", \"patientName\": \"" + patientName + "\"")
            .concat(disposalId == null ? "" : ", \"disposalId\": \"" + disposalId + "\"")
            .concat(disposalDate == null ? "" : ", \"disposalDate\": \"" + disposalDate + "\"")
            .concat(cardNumber == null ? "" : ", \"cardNumber\": \"" + cardNumber + "\"")
            .concat(cardReplenishment == null ? "" : ", \"cardReplenishment\": \"" + cardReplenishment + "\"")
            .concat(nhiCategory == null ? "" : ", \"nhiCategory\": \"" + nhiCategory + "\"")
            .concat(partialBurden == null ? "" : ", \"partialBurden\": \"" + partialBurden + "\"")
            .concat(cardReplenishmentDisposalDate == null ? "" : ", \"cardReplenishmentDisposalDate\": \"" + cardReplenishmentDisposalDate + "\"")
            .concat(examCode == null ? "" : ", \"examCode\": \"" + examCode + "\"")
            .concat(examPoint == null ? "" : ", \"examPoint\": \"" + examPoint + "\"")
            .concat(patientIdentity == null ? "" : ", \"patientIdentity\": \"" + patientIdentity + "\"")
            .concat(serialNumber == null ? "" : ", \"serialNumber\": \"" + serialNumber + "\"")
            .concat(treatmentProcedureTotal == null ? "" : ", \"treatmentProcedureTotal\": \"" + treatmentProcedureTotal + "\"")
            .concat(nhiOriginPoint == null ? "" : ", \"nhiOriginPoint\": \"" + nhiOriginPoint + "\"")
            .concat(treatmentProcedureSpecificCode == null ? "" : ", \"treatmentProcedureSpecificCode\": \"" + treatmentProcedureSpecificCode + "\"")
            .concat(treatmentProcedureCode == null ? "" : ", \"treatmentProcedureCode\": \"" + treatmentProcedureCode + "\"")
            .concat(treatmentProcedureTooth == null ? "" : ", \"treatmentProcedureTooth\": \"" + treatmentProcedureTooth + "\"")
            .concat(treatmentProcedureSurface == null ? "" : ", \"treatmentProcedureSurface\": \"" + treatmentProcedureSurface + "\"")
            .concat(doctorId == null ? "" : ", \"doctorId\": \"" + doctorId + "\"")
            .concat(doctorName == null ? "" : ", \"doctorName\": \"" + doctorName + "\"")
            .concat("}");
    }

    @Override
    public String getDoctorName() {
        return this.doctorName;
    }
}
