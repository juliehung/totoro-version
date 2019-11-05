package io.dentall.totoro.business.vm;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.dentall.totoro.domain.Patient;

import java.time.Instant;

public class PatientVM {

    @JsonUnwrapped
    private Patient patient;

    private Instant firstVisitDate;

    public PatientVM() { }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Instant getFirstVisitDate() {
        return firstVisitDate;
    }

    public void setFirstVisitDate(Instant firstVisitDate) {
        this.firstVisitDate = firstVisitDate;
    }
}
