package io.dentall.totoro.business.vm;

public class PatientSearchVM {

    private Long id;

    private String name;

    private String medicalId;

    public PatientSearchVM(Long id, String name, String medicalId) {
        this.id = id;
        this.name = name;
        this.medicalId = medicalId;
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
}
