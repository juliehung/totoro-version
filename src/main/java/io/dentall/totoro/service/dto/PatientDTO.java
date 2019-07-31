package io.dentall.totoro.service.dto;

import io.dentall.totoro.domain.Patient;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A DTO representing a patient
 */
public class PatientDTO extends Patient {

    @Null(groups = NullGroup.class)
    private String name;

    @Null(groups = NullGroup.class)
    private String phone;

    private List<String> isPermanent;

    public List<String> getIsPermanent() {
        return isPermanent;
    }

    public void setIsPermanent(List<String> isPermanent) {
        this.isPermanent = isPermanent;
    }
}
