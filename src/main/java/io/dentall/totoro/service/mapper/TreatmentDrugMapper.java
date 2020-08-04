package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.Drug;
import io.dentall.totoro.domain.TreatmentDrug;
import io.dentall.totoro.service.dto.table.TreatmentDrugsTable;
import org.springframework.stereotype.Service;

@Service
public class TreatmentDrugMapper {

    public TreatmentDrug treatmentDrugTableToTreatmentDrug(TreatmentDrugsTable treatmentDrugsTable) {
        TreatmentDrug treatmentDrug = new TreatmentDrug();

        treatmentDrug.setId(treatmentDrugsTable.getId());
        treatmentDrug.setDay(treatmentDrugsTable.getDay());
        treatmentDrug.setFrequency(treatmentDrugsTable.getFrequency());
        treatmentDrug.setWay(treatmentDrugsTable.getWay());
        treatmentDrug.setQuantity(treatmentDrugsTable.getQuantity());
        treatmentDrug.setTotalAmount(treatmentDrugsTable.getTotalAmount());

        if (treatmentDrugsTable.getDrug_Id() != null) {
            Drug drug = new Drug();
            drug.setId(treatmentDrugsTable.getDrug_Id());
            treatmentDrug.setDrug(drug);
        }

        return treatmentDrug;
    }
}
