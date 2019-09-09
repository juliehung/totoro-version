package io.dentall.totoro.business.vm.nhi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class NhiAbnormality implements Serializable {

    @JsonProperty
    private List<NhiAbnormalityDoctor> frequentDoctors;

    @JsonProperty
    private int frequentLimit = 9;

    @JsonProperty
    private List<NhiAbnormalityDoctor> code92013cAvgPointDoctors;

    @JsonProperty
    private int code92013cAvgPointLimit = 110;

    @JsonProperty
    private List<NhiAbnormalityDoctor> ratioOf90004cTo90015cDoctors;

    @JsonProperty
    private double ratioOf90004cTo90015cLimit = 0.4;

    @JsonProperty
    private List<NhiAbnormalityDoctor> code92003cDoctors;

    @JsonProperty
    private int code92003cLimit = 20;

    @JsonProperty
    private List<NhiAbnormalityDoctor> code92071cDoctors;

    @JsonProperty
    private int code92071cLimit = 40;

    @JsonProperty
    private List<NhiAbnormalityDoctor> code91013cDoctors;

    @JsonProperty
    private int code91013cLimit = 24;

    public List<NhiAbnormalityDoctor> getFrequentDoctors() {
        return frequentDoctors;
    }

    public void setFrequentDoctors(List<NhiAbnormalityDoctor> frequentDoctors) {
        this.frequentDoctors = frequentDoctors;
    }

    public List<NhiAbnormalityDoctor> getCode92013cAvgPointDoctors() {
        return code92013cAvgPointDoctors;
    }

    public void setCode92013cAvgPointDoctors(List<NhiAbnormalityDoctor> code92013cAvgPointDoctors) {
        this.code92013cAvgPointDoctors = code92013cAvgPointDoctors;
    }

    public List<NhiAbnormalityDoctor> getCode92003cDoctors() {
        return code92003cDoctors;
    }

    public void setCode92003cDoctors(List<NhiAbnormalityDoctor> code92003cDoctors) {
        this.code92003cDoctors = code92003cDoctors;
    }

    public List<NhiAbnormalityDoctor> getRatioOf90004cTo90015cDoctors() {
        return ratioOf90004cTo90015cDoctors;
    }

    public void setRatioOf90004cTo90015cDoctors(
        List<NhiAbnormalityDoctor> ratioOf90004cTo90015cDoctors
    ) {
        this.ratioOf90004cTo90015cDoctors = ratioOf90004cTo90015cDoctors;
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
