package io.dentall.totoro.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.dentall.totoro.domain.Disposal;

public class DisposalV2VM {

    @JsonUnwrapped
    private Disposal disposal;

    private Long doctorId;

    public Disposal getDisposal() {
        return disposal;
    }

    public void setDisposal(Disposal disposal) {
        this.disposal = disposal;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}
