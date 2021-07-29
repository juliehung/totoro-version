package io.dentall.totoro.business.service.nhi.metric.vm;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import io.dentall.totoro.domain.User;

public class MetricLVM {

    private String type;

    @JsonIncludeProperties({"id", "firstName"})
    private User doctor;

    private Section5 section5;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public Section5 getSection5() {
        return section5;
    }

    public void setSection5(Section5 section5) {
        this.section5 = section5;
    }
}
