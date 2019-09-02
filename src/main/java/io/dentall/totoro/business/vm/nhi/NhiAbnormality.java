package io.dentall.totoro.business.vm.nhi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class NhiAbnormality implements Serializable {

    @JsonProperty
    private List<NhiAbnormalityDoctor> frequentDoctors;

    @JsonProperty
    private List<NhiAbnormalityDoctor> code92003cDoctors;

    @JsonProperty
    private List<NhiAbnormalityDoctor> code92071cDoctors;

    @JsonProperty
    private List<NhiAbnormalityDoctor> code91013cDoctors;

    public List<NhiAbnormalityDoctor> getFrequentDoctors() {
        return frequentDoctors;
    }

    public void setFrequentDoctors(List<NhiAbnormalityDoctor> frequentDoctors) {
        this.frequentDoctors = frequentDoctors;
    }

    public List<NhiAbnormalityDoctor> getCode92003cDoctors() {
        return code92003cDoctors;
    }

    public void setCode92003cDoctors(List<NhiAbnormalityDoctor> code92003cDoctors) {
        this.code92003cDoctors = code92003cDoctors;
    }

    public List<NhiAbnormalityDoctor> getCode92071cDoctors() {
        return code92071cDoctors;
    }

    public void setCode92071cDoctors(List<NhiAbnormalityDoctor> code92071cDoctors) {
        this.code92071cDoctors = code92071cDoctors;
    }

    public List<NhiAbnormalityDoctor> getCode91013cDoctors() {
        return code91013cDoctors;
    }

    public void setCode91013cDoctors(List<NhiAbnormalityDoctor> code91013cDoctors) {
        this.code91013cDoctors = code91013cDoctors;
    }
}
