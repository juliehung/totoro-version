package io.dentall.totoro.business.vm;

import io.dentall.totoro.domain.enumeration.Gender;

import java.time.LocalDate;

public class PatientSearchVM {

    private Long id;

    private String name;

    private String medicalId;

    private LocalDate birth;

    private String phone;

    private String nationalId;

    private Gender gender;

    private Boolean vipPatient;

    public PatientSearchVM() {}

    public PatientSearchVM(Long id, String name, String medicalId, LocalDate birth, String phone, String nationalId, Gender gender, Boolean vipPatient) {
        this.id = id;
        this.name = name;
        this.medicalId = medicalId;
        this.birth = birth;
        this.phone = phone;
        this.nationalId = nationalId;
        this.gender = gender;
        this.vipPatient = vipPatient;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMedicalId() {
        return medicalId;
    }

    public void setMedicalId(String medicalId) {
        this.medicalId = medicalId;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public Boolean getVipPatient() {
        return vipPatient;
    }

    public void setVipPatient(Boolean vipPatient) {
        this.vipPatient = vipPatient;
    }

}
