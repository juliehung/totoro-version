package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.service.dto.table.TreatmentProcedureTable;
import org.springframework.stereotype.Service;

@Service
public class TreatmentProcedureMapper {
    public TreatmentProcedure TreatmentProcedureTableToTreatmentProcedure(TreatmentProcedureTable treatmentProcedureTable) {
        TreatmentProcedure treatmentProcedure = new TreatmentProcedure();

        treatmentProcedure.setId(treatmentProcedureTable.getId());
        treatmentProcedure.setStatus(treatmentProcedureTable.getStatus());
        treatmentProcedure.setQuantity(treatmentProcedureTable.getQuantity());
        treatmentProcedure.setTotal(treatmentProcedureTable.getTotal());
        treatmentProcedure.setNote(treatmentProcedureTable.getNote());
        treatmentProcedure.setCompletedDate(treatmentProcedureTable.getCompletedDate());
        treatmentProcedure.setPrice(treatmentProcedureTable.getPrice());
        treatmentProcedure.setNhiCategory(treatmentProcedureTable.getNhiCategory());
        treatmentProcedure.setNhiDescription(treatmentProcedureTable.getNhiDescription());
        treatmentProcedure.setNhiIcd10Cm(treatmentProcedureTable.getNhiIcd10Cm());
        treatmentProcedure.setCreatedDate(treatmentProcedureTable.getCreatedDate());
        treatmentProcedure.setCreatedBy(treatmentProcedureTable.getCreatedBy());
        treatmentProcedure.setLastModifiedDate(treatmentProcedureTable.getLastModifiedDate());
        treatmentProcedure.setLastModifiedBy(treatmentProcedureTable.getLastModifiedBy());

        if (treatmentProcedureTable.getNhiProcedure_Id() != null) {
            NhiProcedure nhiProcedure = new NhiProcedure();
            nhiProcedure.setId(treatmentProcedureTable.getNhiProcedure_Id());
            treatmentProcedure.setNhiProcedure(nhiProcedure);
        }

        if (treatmentProcedureTable.getProcedure_Id() != null) {
            Procedure procedure = new Procedure();
            procedure.setId(treatmentProcedureTable.getProcedure_Id());
            treatmentProcedure.setProcedure(procedure);
        }

        if (treatmentProcedureTable.getDoctor_Id() != null) {
            ExtendUser extendUser = new ExtendUser();
            extendUser.setId(treatmentProcedureTable.getDoctor_Id());
            treatmentProcedure.setDoctor(extendUser);
        }

        if (treatmentProcedureTable.getNhiExtendTreatmentProcedure_Id() != null) {
            NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure = new NhiExtendTreatmentProcedure();
            nhiExtendTreatmentProcedure.setId(treatmentProcedureTable.getNhiExtendTreatmentProcedure_Id());
            treatmentProcedure.setNhiExtendTreatmentProcedure(nhiExtendTreatmentProcedure);
        }

        return treatmentProcedure;
    }
}
