package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.service.dto.table.NhiExtendTreatmentProcedureTable;
import org.springframework.stereotype.Service;

@Service
public class NhiExtendTreatmentProcedureMapper {
    public NhiExtendTreatmentProcedure nhiExtendTreatmentProcedureTableToNhiExtendTreatmentProcedureTable(NhiExtendTreatmentProcedureTable nhiExtendTreatmentProcedureTable) {
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure = new NhiExtendTreatmentProcedure();

        nhiExtendTreatmentProcedure.setId(nhiExtendTreatmentProcedureTable.getTreatmentProcedure_Id());
        nhiExtendTreatmentProcedure.setA71(nhiExtendTreatmentProcedureTable.getA71());
        nhiExtendTreatmentProcedure.setA72(nhiExtendTreatmentProcedureTable.getA72());
        nhiExtendTreatmentProcedure.setA73(nhiExtendTreatmentProcedureTable.getA73());
        nhiExtendTreatmentProcedure.setA74(nhiExtendTreatmentProcedureTable.getA74());
        nhiExtendTreatmentProcedure.setA75(nhiExtendTreatmentProcedureTable.getA75());
        nhiExtendTreatmentProcedure.setA76(nhiExtendTreatmentProcedureTable.getA76());
        nhiExtendTreatmentProcedure.setA77(nhiExtendTreatmentProcedureTable.getA77());
        nhiExtendTreatmentProcedure.setA78(nhiExtendTreatmentProcedureTable.getA78());
        nhiExtendTreatmentProcedure.setA79(nhiExtendTreatmentProcedureTable.getA79());
        nhiExtendTreatmentProcedure.setCheck(nhiExtendTreatmentProcedureTable.getCheck());

        NhiExtendDisposal nhiExtendDisposal = new NhiExtendDisposal();
        nhiExtendDisposal.setId(nhiExtendTreatmentProcedureTable.getNhiExtendDisposal_Id());
        nhiExtendTreatmentProcedure.setNhiExtendDisposal(nhiExtendDisposal);

        return nhiExtendTreatmentProcedure;
    }
}
