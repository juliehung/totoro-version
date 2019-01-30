package io.dentall.totoro.service.dto;

import io.dentall.totoro.domain.Patient;

import javax.validation.constraints.Null;

/**
 * A DTO representing a patient
 */
public class PatientDTO extends Patient {

    @Null(groups = NullGroup.class)
    private String name;

    @Null(groups = NullGroup.class)
    private String phone;
}
