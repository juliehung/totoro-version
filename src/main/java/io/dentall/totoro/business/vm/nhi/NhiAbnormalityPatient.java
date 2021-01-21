package io.dentall.totoro.business.vm.nhi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;

public class NhiAbnormalityPatient implements Serializable {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String name;

    @JsonProperty
    private LocalDate birth;

    @JsonProperty
    private Boolean vipPatient;

    @JsonProperty
    private String medicalId;

    @JsonProperty
    private String cardNumber;

    @JsonProperty
    private LocalDate date;

    @JsonProperty
    private Integer count;

    @JsonProperty
    private Map<String, Double> ratioOf90004cTo90015c;

    @JsonProperty
    private Double code92013cPoint;

    public NhiAbnormalityPatient(Long id, String name, LocalDate birth, Boolean vipPatient, String medicalId, String cardNumber) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.vipPatient = vipPatient;
        this.medicalId = medicalId;
        this.cardNumber = cardNumber;
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

    public Boolean getVipPatient() {
        return vipPatient;
    }

    public void setVipPatient(Boolean vipPatient) {
        this.vipPatient = vipPatient;
    }

    public NhiAbnormalityPatient vipPatient(Boolean vipPatient) {
        this.vipPatient = vipPatient;
        return this;
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

    public Integer getCount() {
        return count;
    }

    public NhiAbnormalityPatient count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Map<String, Double> getRatioOf90004cTo90015c() {
        return ratioOf90004cTo90015c;
    }

    public NhiAbnormalityPatient ratioOf90004cTo90015c(Map<String, Double> ratioOf90004cTo90015c) {
        this.ratioOf90004cTo90015c = ratioOf90004cTo90015c;
        return this;
    }

    public void setRatioOf90004cTo90015c(Map<String, Double> ratioOf90004cTo90015c) {
        this.ratioOf90004cTo90015c = ratioOf90004cTo90015c;
    }

    public Double getCode92013cPoint() {
        return code92013cPoint;
    }

    public NhiAbnormalityPatient code92013cPoint(Double code92013cPoint) {
        this.code92013cPoint = code92013cPoint;
        return this;
    }

    public void setCode92013cPoint(Double code92013cPoint) {
        this.code92013cPoint = code92013cPoint;
    }
}
