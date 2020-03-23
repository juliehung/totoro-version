package io.dentall.totoro.business.vm;

import java.time.LocalDate;

public class PatientSearchVM {

    private Long id;

    private String name;

    private String medicalId;

    private LocalDate birth;


    public PatientSearchVM(Long id, String name, String medicalId, LocalDate birth) {
        this.id = id;
        this.name = name;
        this.medicalId = medicalId;
        this.birth = birth;
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
}
