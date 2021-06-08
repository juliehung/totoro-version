package io.dentall.totoro.service.dto;

import io.dentall.totoro.domain.enumeration.Gender;

import java.time.LocalDate;

public interface PatientSearchDTO {

    Long getId();

    String getName();

    String getMedicalId();

    LocalDate getBirth();

    String getPhone();

    String getNationalId();

    Gender getGender();

    Boolean getVipPatient();
}
