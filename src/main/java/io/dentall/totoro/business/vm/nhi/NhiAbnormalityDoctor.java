package io.dentall.totoro.business.vm.nhi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class NhiAbnormalityDoctor implements Serializable {

    @JsonProperty
    private Long id;

    @JsonProperty
    private int count;

    @JsonProperty
    private double point;

    @JsonProperty
    private List<NhiAbnormalityPatient> patients;

    public Long getId() {
        return id;
    }

    public NhiAbnormalityDoctor id(Long id) {
        this.id = id;
        return this;
    }

    public void setLogin(Long id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public NhiAbnormalityDoctor count(int count) {
        this.count = count;
        return this;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPoint() {
        return point;
    }

    public NhiAbnormalityDoctor point(double point) {
        this.point = point;
        return this;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public List<NhiAbnormalityPatient> getPatients() {
        return patients;
    }

    public void setPatients(List<NhiAbnormalityPatient> patients) {
        this.patients = patients;
    }
}
