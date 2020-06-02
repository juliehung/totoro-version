package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.Disposal;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.Tooth;
import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.service.dto.table.ToothTable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ToothMapper {

    public Set<Tooth> toothSetToToothSet(Set<ToothTable> toothTableSet) {
       return toothTableSet.stream()
           .filter(Objects::nonNull)
           .map(this::toothTableToTooth)
           .collect(Collectors.toSet());
    }

    public Tooth toothTableToTooth(ToothTable toothTable) {
        Tooth tooth = new Tooth();
        tooth.setId(toothTable.getId());
        tooth.setPosition(toothTable.getPosition());
        tooth.setSurface(toothTable.getSurface());
        tooth.setStatus(toothTable.getStatus());

        TreatmentProcedure treatmentProcedure = new TreatmentProcedure();
        treatmentProcedure.setId(toothTable.getTreatmentProcedure_Id());
        tooth.setTreatmentProcedure(treatmentProcedure);

        Disposal disposal = new Disposal();
        disposal.setId(toothTable.getDisposal_Id());
        tooth.setDisposal(disposal);

        Patient patient = new Patient();
        patient.setId(toothTable.getPatient_Id());
        tooth.setPatient(patient);

        return tooth;
    }
}
