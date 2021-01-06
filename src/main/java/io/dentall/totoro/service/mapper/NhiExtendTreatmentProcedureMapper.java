package io.dentall.totoro.service.mapper;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import io.dentall.totoro.domain.Disposal;
import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.service.dto.NhiExtendDisposal2;
import io.dentall.totoro.service.dto.NhiExtendTreatmentProcedureDTO;
import io.dentall.totoro.service.dto.table.NhiExtendTreatmentProcedureTable;

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

    public static NhiExtendTreatmentProcedure nhiExtendTreatmentProcedureTableToNhiExtendTreatmentProcedureTable2(NhiExtendTreatmentProcedureDTO nhiExtendTreatmentProcedureDto) {
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure = new NhiExtendTreatmentProcedure();

        nhiExtendTreatmentProcedure.setId(nhiExtendTreatmentProcedureDto.getId());
        nhiExtendTreatmentProcedure.setA71(nhiExtendTreatmentProcedureDto.getA71());
        nhiExtendTreatmentProcedure.setA72(nhiExtendTreatmentProcedureDto.getA72());
        nhiExtendTreatmentProcedure.setA73(nhiExtendTreatmentProcedureDto.getA73());
        nhiExtendTreatmentProcedure.setA74(nhiExtendTreatmentProcedureDto.getA74());
        nhiExtendTreatmentProcedure.setA75(nhiExtendTreatmentProcedureDto.getA75());
        nhiExtendTreatmentProcedure.setA76(nhiExtendTreatmentProcedureDto.getA76());
        nhiExtendTreatmentProcedure.setA77(nhiExtendTreatmentProcedureDto.getA77());
        nhiExtendTreatmentProcedure.setA78(nhiExtendTreatmentProcedureDto.getA78());
        nhiExtendTreatmentProcedure.setA79(nhiExtendTreatmentProcedureDto.getA79());
        nhiExtendTreatmentProcedure.setCheck(nhiExtendTreatmentProcedureDto.getCheck());

        NhiExtendDisposal2 nhiExtendDisposal = new NhiExtendDisposal2();
        nhiExtendDisposal.setA14(nhiExtendTreatmentProcedureDto.getNhiExtendDisposalA14());
        nhiExtendDisposal.setDate(nhiExtendTreatmentProcedureDto.getNhiExtendDisposalDate());
        nhiExtendDisposal.setReplenishmentDate(nhiExtendTreatmentProcedureDto.getNhiExtendDisposalReplenishmentDate());

        Set<NhiExtendDisposal> sets = new HashSet<>();
        sets.add(nhiExtendDisposal);

        Disposal disposal = new Disposal();
        disposal.setNhiExtendDisposals(sets);

        TreatmentProcedure treatmentProcedure = new TreatmentProcedure();
        treatmentProcedure.setDisposal(disposal);

        nhiExtendTreatmentProcedure.setTreatmentProcedure(treatmentProcedure);

        return nhiExtendTreatmentProcedure;
    }
}
