package io.dentall.totoro.service.mapper;

import io.dentall.totoro.domain.Disposal;
import io.dentall.totoro.domain.Prescription;
import io.dentall.totoro.domain.Registration;
import io.dentall.totoro.service.dto.table.DisposalTable;
import io.dentall.totoro.service.util.MapperUtil;

public class DisposalMapper {

    public static Disposal disposalTableToDisposal(DisposalTable disposalTable) {
        Disposal disposal = new Disposal();

        disposal.setId(disposalTable.getId());
        disposal.setStatus(disposalTable.getStatus());
        disposal.setTotal(disposalTable.getTotal());
        disposal.setDateTime(disposalTable.getDateTime());
        disposal.setDateTimeEnd(disposalTable.getDateTimeEnd());
        disposal.setChiefComplaint(disposalTable.getChiefComplaint());
        disposal.setCreatedBy(disposalTable.getCreatedBy());
        disposal.setCreatedDate(disposalTable.getCreatedDate());
        disposal.setLastModifiedBy(disposalTable.getLastModifiedBy());
        disposal.setLastModifiedDate(disposalTable.getLastModifiedDate());
        disposal.setRevisitContent(disposalTable.getRevisitContent());
        disposal.setRevisitInterval(disposalTable.getRevisitInterval());
        disposal.setRevisitTreatmentTime(disposalTable.getRevisitTreatmentTime());
        disposal.setRevisitComment(disposalTable.getRevisitComment());
        disposal.setRevisitWillNotHappen(disposalTable.getRevisitWillNotHappen());

        Prescription prescription = new Prescription();
        prescription.setId(disposalTable.getPrescription_Id());
        disposal.setPrescription(prescription);

        Registration registration = new Registration();
        registration.setId(disposalTable.getRegistration_Id());
        MapperUtil.setNullAuditing(registration);
        disposal.setRegistration(registration);

        return disposal;
    }
}
