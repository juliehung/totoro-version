package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.NhiExtendTreatmentDrug;
import io.dentall.totoro.service.dto.table.NhiExtendTreatmentDrugTable;
import org.springframework.stereotype.Service;

@Service
public class NhiExtendTreatmentDrugMapper {

    public NhiExtendTreatmentDrug nhiExtendTreatmentDrugTableToNhiExtendTreatmentDrug(NhiExtendTreatmentDrugTable nhiExtendTreatmentDrugTable) {
        NhiExtendTreatmentDrug nhiExtendTreatmentDrug = new NhiExtendTreatmentDrug();

        nhiExtendTreatmentDrug.setId(nhiExtendTreatmentDrugTable.getId());
        nhiExtendTreatmentDrug.setA71(nhiExtendTreatmentDrugTable.getA71());
        nhiExtendTreatmentDrug.setA72(nhiExtendTreatmentDrugTable.getA72());
        nhiExtendTreatmentDrug.setA73(nhiExtendTreatmentDrugTable.getA73());
        nhiExtendTreatmentDrug.setA74(nhiExtendTreatmentDrugTable.getA74());
        nhiExtendTreatmentDrug.setA75(nhiExtendTreatmentDrugTable.getA75());
        nhiExtendTreatmentDrug.setA76(nhiExtendTreatmentDrugTable.getA76());
        nhiExtendTreatmentDrug.setA77(nhiExtendTreatmentDrugTable.getA77());
        nhiExtendTreatmentDrug.setA78(nhiExtendTreatmentDrugTable.getA78());
        nhiExtendTreatmentDrug.setA79(nhiExtendTreatmentDrugTable.getA79());
        nhiExtendTreatmentDrug.setCheck(nhiExtendTreatmentDrugTable.getCheck());

        if (nhiExtendTreatmentDrugTable.getNhiExtendDisposal_Id() != null) {
            NhiExtendDisposal nhiExtendDisposal = new NhiExtendDisposal();
            nhiExtendDisposal.setId(nhiExtendTreatmentDrugTable.getNhiExtendDisposal_Id());
            nhiExtendTreatmentDrug.setNhiExtendDisposal(nhiExtendDisposal);
        }

        return nhiExtendTreatmentDrug;
    }
}
