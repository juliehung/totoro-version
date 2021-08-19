package io.dentall.totoro.business.service.nhi.metric.dto;

import io.dentall.totoro.business.service.nhi.NhiSpecialCode;

import java.time.LocalDate;
import java.util.Date;

public class OdDto {

    private long patientId;

    private long disposalId;

    private LocalDate disposalDate;

    private String code;

    private String tooth;

    private String surface;

    private String category;

    private NhiSpecialCode specificCode;

    private String cardNumber;

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public long getDisposalId() {
        return disposalId;
    }

    public void setDisposalId(long disposalId) {
        this.disposalId = disposalId;
    }

    public LocalDate getDisposalDate() {
        return disposalDate;
    }

    public void setDisposalDate(LocalDate disposalDate) {
        this.disposalDate = disposalDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTooth() {
        return tooth;
    }

    public void setTooth(String tooth) {
        this.tooth = tooth;
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public NhiSpecialCode getSpecificCode() {
        return specificCode;
    }

    public void setSpecificCode(NhiSpecialCode specificCode) {
        this.specificCode = specificCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
