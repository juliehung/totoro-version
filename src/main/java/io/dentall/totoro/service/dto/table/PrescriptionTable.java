package io.dentall.totoro.service.dto.table;

import io.dentall.totoro.domain.enumeration.PrescriptionMode;
import io.dentall.totoro.domain.enumeration.PrescriptionStatus;

public interface PrescriptionTable {
    Long getId();
    Boolean getClinicAdministration();
    Boolean getAntiInflammatoryDrug();
    Boolean getPain();
    Boolean getTakenAll();
    PrescriptionStatus getStatus();
    PrescriptionMode getMode();
}
