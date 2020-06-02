package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.Disposal;
import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.service.dto.table.NhiExtendDisposalTable;
import org.springframework.stereotype.Service;

@Service
public class NhiExtendDisposalMapper {
    public NhiExtendDisposal nhiExtendDisposalTableToNhiExtendDisposal(NhiExtendDisposalTable nhiExtendDisposalTable) {
        NhiExtendDisposal nhiExtendDisposal = new NhiExtendDisposal();

        nhiExtendDisposal.setId(nhiExtendDisposalTable.getId());
        nhiExtendDisposal.setA11(nhiExtendDisposalTable.getA11());
        nhiExtendDisposal.setA12(nhiExtendDisposalTable.getA12());
        nhiExtendDisposal.setA13(nhiExtendDisposalTable.getA13());
        nhiExtendDisposal.setA14(nhiExtendDisposalTable.getA14());
        nhiExtendDisposal.setA15(nhiExtendDisposalTable.getA15());
        nhiExtendDisposal.setA16(nhiExtendDisposalTable.getA16());
        nhiExtendDisposal.setA17(nhiExtendDisposalTable.getA17());
        nhiExtendDisposal.setA18(nhiExtendDisposalTable.getA18());
        nhiExtendDisposal.setA19(nhiExtendDisposalTable.getA19());
        nhiExtendDisposal.setA22(nhiExtendDisposalTable.getA22());
        nhiExtendDisposal.setA23(nhiExtendDisposalTable.getA23());
        nhiExtendDisposal.setA25(nhiExtendDisposalTable.getA25());
        nhiExtendDisposal.setA26(nhiExtendDisposalTable.getA26());
        nhiExtendDisposal.setA27(nhiExtendDisposalTable.getA27());
        nhiExtendDisposal.setA31(nhiExtendDisposalTable.getA31());
        nhiExtendDisposal.setA32(nhiExtendDisposalTable.getA32());
        nhiExtendDisposal.setA41(nhiExtendDisposalTable.getA41());
        nhiExtendDisposal.setA42(nhiExtendDisposalTable.getA42());
        nhiExtendDisposal.setA43(nhiExtendDisposalTable.getA43());
        nhiExtendDisposal.setA44(nhiExtendDisposalTable.getA44());
        nhiExtendDisposal.setA54(nhiExtendDisposalTable.getA54());
//        this will automatically set while setA17
//        nhiExtendDisposal.setDate(nhiExtendDisposalTable.getDate());
        nhiExtendDisposal.setUploadStatus(nhiExtendDisposalTable.getUploadStatus());
        nhiExtendDisposal.setExaminationCode(nhiExtendDisposalTable.getExaminationCode());
        nhiExtendDisposal.setExaminationPoint(nhiExtendDisposalTable.getExaminationPoint());
        nhiExtendDisposal.setPatientIdentity(nhiExtendDisposalTable.getPatientIdentity());
        nhiExtendDisposal.setPatientId(nhiExtendDisposalTable.getPatientId());
        nhiExtendDisposal.setCategory(nhiExtendDisposalTable.getCategory());
//        this will automatically set while setA54
//        nhiExtendDisposal.setReplenishmentDate(nhiExtendDisposalTable.getReplenishmentDate());
        nhiExtendDisposal.setCheckedMonthDeclaration(nhiExtendDisposalTable.getCheckedMonthDeclaration());
        nhiExtendDisposal.setCheckedAuditing(nhiExtendDisposalTable.getCheckedAuditing());

        Disposal disposal = new Disposal();
        disposal.setId(nhiExtendDisposalTable.getDisposal_Id());
        nhiExtendDisposal.setDisposal(disposal);

        return nhiExtendDisposal;
    }
}
