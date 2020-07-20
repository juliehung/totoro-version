package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.TreatmentPlan;
import io.dentall.totoro.domain.TreatmentTask;
import io.dentall.totoro.service.dto.table.TreatmentTaskTable;
import io.dentall.totoro.service.util.MapperUtil;

public class TreatmentTaskMapper {

    public static TreatmentTask treatmentTaskTableToTreatmentTask(TreatmentTaskTable treatmentTaskTable) {
        TreatmentTask treatmentTask = new TreatmentTask();

        treatmentTask.setId(treatmentTaskTable.getId());
        treatmentTask.setName(treatmentTaskTable.getName());
        treatmentTask.setNote(treatmentTaskTable.getNote());

        TreatmentPlan treatmentPlan = new TreatmentPlan();
        treatmentPlan.setId(treatmentTaskTable.getTreatmentPlan_Id());
        MapperUtil.setNullAuditing(treatmentPlan);
        treatmentTask.setTreatmentPlan(treatmentPlan);

        MapperUtil.setAuditing(treatmentTask, treatmentTaskTable);

        return treatmentTask;
    }
}
