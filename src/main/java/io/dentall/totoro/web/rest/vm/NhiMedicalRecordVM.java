package io.dentall.totoro.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.dentall.totoro.domain.NhiMedicalRecord;

public class NhiMedicalRecordVM {

    @JsonUnwrapped
    private NhiMedicalRecord nhiMedicalRecord;

    private String mandarin;

    public NhiMedicalRecord getNhiMedicalRecord() {
        return nhiMedicalRecord;
    }

    public void setNhiMedicalRecord(NhiMedicalRecord nhiMedicalRecord) {
        this.nhiMedicalRecord = nhiMedicalRecord;
    }

    public String getMandarin() {
        return mandarin;
    }

    public void setMandarin(String mandarin) {
        this.mandarin = mandarin;
    }
}
