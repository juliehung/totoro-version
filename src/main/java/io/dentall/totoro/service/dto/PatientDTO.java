package io.dentall.totoro.service.dto;

import io.dentall.totoro.domain.Patient;

import javax.validation.constraints.Null;
import java.util.List;

/**
 * A DTO representing a patient
 */
public class PatientDTO extends Patient {

    @Null(groups = CustomGroup.class)
    private String name;

    @Null(groups = CustomGroup.class)
    private String phone;

    private List<String> isPermanent;

    public List<String> getIsPermanent() {
        return isPermanent;
    }

    public void setIsPermanent(List<String> isPermanent) {
        this.isPermanent = isPermanent;
    }
}
