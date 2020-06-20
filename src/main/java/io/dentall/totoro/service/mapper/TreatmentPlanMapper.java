package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.Treatment;
import io.dentall.totoro.domain.TreatmentPlan;
import io.dentall.totoro.service.dto.table.TreatmentPlanTable;
import io.dentall.totoro.service.util.MapperUtil;
import org.springframework.stereotype.Service;

@Service
public class TreatmentPlanMapper {

    public TreatmentPlan treatmentPlanTableToTreatmentPlan(TreatmentPlanTable treatmentPlanTable) {
        TreatmentPlan treatmentPlan = new TreatmentPlan();

        treatmentPlan.setId(treatmentPlanTable.getId());
        treatmentPlan.setActivated(treatmentPlanTable.getActivated());
        treatmentPlan.setName(treatmentPlanTable.getName());

        Treatment treatment = new Treatment();
        treatment.setId(treatmentPlanTable.getTreatment_Id());
        MapperUtil.setNullAuditing(treatment);
        treatmentPlan.setTreatment(treatment);

        MapperUtil.setAuditing(treatmentPlan, treatmentPlanTable);

        return treatmentPlan;
    }
}
