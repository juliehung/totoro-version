package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.Treatment;
import io.dentall.totoro.service.dto.table.TreatmentTable;
import io.dentall.totoro.service.util.MapperUtil;
import org.springframework.stereotype.Service;

@Service
public class TreatmentMapper {

    public Treatment treatmentTableToTreatment(TreatmentTable treatmentTable) {
        Treatment treatment = new Treatment();

        treatment.setId(treatmentTable.getId());
        treatment.setName(treatmentTable.getName());
        treatment.setChiefComplaint(treatmentTable.getChiefComplaint());
        treatment.setGoal(treatmentTable.getGoal());
        treatment.setNote(treatmentTable.getNote());
        treatment.setFinding(treatmentTable.getFinding());
        treatment.setType(treatmentTable.getType());

        Patient patient = new Patient();
        patient.setId(treatmentTable.getPatient_Id());
        MapperUtil.setNullAuditing(patient);
        treatment.setPatient(patient);

        MapperUtil.setAuditing(treatment, treatmentTable);

        return treatment;
    }
}
