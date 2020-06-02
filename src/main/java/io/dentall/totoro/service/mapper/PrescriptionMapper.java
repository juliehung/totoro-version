package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.Prescription;
import io.dentall.totoro.service.dto.table.PrescriptionTable;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionMapper {

    public Prescription prescriptionTableToPrescription(PrescriptionTable prescriptionTable) {
        Prescription prescription = new Prescription();

        prescription.setId(prescriptionTable.getId());
        prescription.setClinicAdministration(prescriptionTable.getClinicAdministration());
        prescription.setAntiInflammatoryDrug(prescriptionTable.getAntiInflammatoryDrug());
        prescription.setPain(prescriptionTable.getPain());
        prescription.setTakenAll(prescriptionTable.getTakenAll());
        prescription.setStatus(prescriptionTable.getStatus());
        prescription.setMode(prescriptionTable.getMode());

        return prescription;
    }
}
