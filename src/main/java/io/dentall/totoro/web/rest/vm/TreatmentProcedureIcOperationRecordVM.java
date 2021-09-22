package io.dentall.totoro.web.rest.vm;

import io.dentall.totoro.domain.TreatmentProcedureIcOperationRecord;

import java.util.List;

public class TreatmentProcedureIcOperationRecordVM {
    private List<TreatmentProcedureIcOperationRecord> treatmentProcedureIcOperationRecords;

    public List<TreatmentProcedureIcOperationRecord> getTreatmentProcedureIcOperationRecords() {
        return treatmentProcedureIcOperationRecords;
    }

    public void setTreatmentProcedureIcOperationRecords(List<TreatmentProcedureIcOperationRecord> treatmentProcedureIcOperationRecords) {
        this.treatmentProcedureIcOperationRecords = treatmentProcedureIcOperationRecords;
    }
}
