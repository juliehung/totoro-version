package io.dentall.totoro.business.vm.nhi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class NhiAbnormality implements Serializable {

    @JsonProperty
    private List<NhiAbnormalityDoctor> frequentDoctors;

    private static final int frequentLimit = 9;

    @JsonProperty
    private List<NhiAbnormalityDoctor> code92013cAvgPointDoctors;

    private static final int code92013cAvgPointLimit = 110;

    @JsonProperty
    private List<NhiAbnormalityDoctor> ratioOf90004cTo90015cDoctors;

    private static final double ratioOf90004cTo90015cLimit = 0.4;

    @JsonProperty
    private double statisticNo048 = 0.0;

    private final double statisticNo048Limit = 0.1;

    @JsonProperty
    private List<NhiAbnormalityDoctor> statisticNo048Doctors;

    @JsonProperty
    private List<NhiAbnormalityDoctor> code92003cDoctors;

    private static final int code92003cLimit = 20;

    @JsonProperty
    private List<NhiAbnormalityDoctor> code92071cDoctors;

    private static final int code92071cLimit = 40;

    @JsonProperty
    private List<NhiAbnormalityDoctor> code91013cDoctors;

    private static final int code91013cLimit = 24;

    public List<NhiAbnormalityDoctor> getFrequentDoctors() {
        return frequentDoctors;
    }

    public void setFrequentDoctors(List<NhiAbnormalityDoctor> frequentDoctors) {
        this.frequentDoctors = frequentDoctors;
    }

    @JsonProperty
    public int getFrequentLimit() {
        return frequentLimit;
    }

    public List<NhiAbnormalityDoctor> getCode92013cAvgPointDoctors() {
        return code92013cAvgPointDoctors;
    }

    public void setCode92013cAvgPointDoctors(List<NhiAbnormalityDoctor> code92013cAvgPointDoctors) {
        this.code92013cAvgPointDoctors = code92013cAvgPointDoctors;
    }

    @JsonProperty
    public int getCode92013cAvgPointLimit() {
        return code92013cAvgPointLimit;
    }

    public List<NhiAbnormalityDoctor> getRatioOf90004cTo90015cDoctors() {
        return ratioOf90004cTo90015cDoctors;
    }

    public void setRatioOf90004cTo90015cDoctors(List<NhiAbnormalityDoctor> ratioOf90004cTo90015cDoctors) {
        this.ratioOf90004cTo90015cDoctors = ratioOf90004cTo90015cDoctors;
    }

    @JsonProperty
    public double getRatioOf90004cTo90015cLimit() {
        return ratioOf90004cTo90015cLimit;
    }

    public double getStatisticNo048() {
        return statisticNo048;
    }

    public void setStatisticNo048(double statisticNo048) {
        this.statisticNo048 = statisticNo048;
    }

    @JsonProperty
    public double getStatisticNo048Limit() {
        return statisticNo048Limit;
    }

    public List<NhiAbnormalityDoctor> getStatisticNo048Doctors() {
        return statisticNo048Doctors;
    }

    public void setStatisticNo048Doctors(List<NhiAbnormalityDoctor> statisticNo048Doctors) {
        this.statisticNo048Doctors = statisticNo048Doctors;
    }

    public List<NhiAbnormalityDoctor> getCode92003cDoctors() {
        return code92003cDoctors;
    }

    public void setCode92003cDoctors(List<NhiAbnormalityDoctor> code92003cDoctors) {
        this.code92003cDoctors = code92003cDoctors;
    }

    @JsonProperty
    public int getCode92003cLimit() {
        return code92003cLimit;
    }

    public List<NhiAbnormalityDoctor> getCode92071cDoctors() {
        return code92071cDoctors;
    }

    public void setCode92071cDoctors(List<NhiAbnormalityDoctor> code92071cDoctors) {
        this.code92071cDoctors = code92071cDoctors;
    }

    @JsonProperty
    public int getCode92071cLimit() {
        return code92071cLimit;
    }

    public List<NhiAbnormalityDoctor> getCode91013cDoctors() {
        return code91013cDoctors;
    }

    public void setCode91013cDoctors(List<NhiAbnormalityDoctor> code91013cDoctors) {
        this.code91013cDoctors = code91013cDoctors;
    }

    @JsonProperty
    public int getCode91013cLimit() {
        return code91013cLimit;
    }
}
