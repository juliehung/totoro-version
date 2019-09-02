package io.dentall.totoro.business.vm.nhi;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dentall.totoro.domain.Patient;

import java.io.Serializable;
import java.time.LocalDate;

public class NhiAbnormalityPatient implements Serializable {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String name;

    @JsonProperty
    private LocalDate birth;

    @JsonProperty
    private String medicalId;

    @JsonProperty
    private String cardNumber;

    @JsonProperty
    private LocalDate date;

    @JsonProperty
    private Long count;

    public NhiAbnormalityPatient(Patient patient) {
        id = patient.getId();
        name = patient.getName();
        birth = patient.getBirth();
        medicalId = patient.getMedicalId();
        cardNumber = patient.getNhiExtendPatient().getCardNumber();
    }

    public Long getId() {
        return id;
    }

    public NhiAbnormalityPatient id(Long id) {
        this.id = id;
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public NhiAbnormalityPatient name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public NhiAbnormalityPatient birth(LocalDate birth) {
        this.birth = birth;
        return this;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getMedicalId() {
        return medicalId;
    }

    public NhiAbnormalityPatient medicalId(String medicalId) {
        this.medicalId = medicalId;
        return this;
    }

    public void setMedicalId(String medicalId) {
        this.medicalId = medicalId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public NhiAbnormalityPatient cardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public NhiAbnormalityPatient date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getCount() {
        return count;
    }

    public NhiAbnormalityPatient count(Long count) {
        this.count = count;
        return this;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
