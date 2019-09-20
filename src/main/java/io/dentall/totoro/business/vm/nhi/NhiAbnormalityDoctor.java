package io.dentall.totoro.business.vm.nhi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class NhiAbnormalityDoctor implements Serializable {

    @JsonProperty
    private Long id;

    @JsonProperty
    private Integer count;

    @JsonProperty
    private Double point;

    @JsonProperty
    private List<NhiAbnormalityPatient> patients;

    private String login;

    public Long getId() {
        return id;
    }

    public NhiAbnormalityDoctor id(Long id) {
        this.id = id;
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public NhiAbnormalityDoctor count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getPoint() {
        return point;
    }

    public NhiAbnormalityDoctor point(Double point) {
        this.point = point;
        return this;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    public List<NhiAbnormalityPatient> getPatients() {
        return patients;
    }

    public void setPatients(List<NhiAbnormalityPatient> patients) {
        this.patients = patients;
    }

    public String getLogin() {
        return login;
    }

    public NhiAbnormalityDoctor login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public NhiAbnormalityDoctor increment(int v) {
        this.count = this.count + v;
        return this;
    }
}
