package io.dentall.totoro.business.service.nhi.metric.vm;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import io.dentall.totoro.domain.User;

public class MetricLVM {

    private String type;

    @JsonIncludeProperties({"id", "firstName"})
    private User doctor;

    private Section5 section5;

    private Section6 section6;

    private Section7 section7;

    private Section8 section8;

    private Section10 section10;

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

    public Section6 getSection6() {
        return section6;
    }

    public void setSection6(Section6 section6) {
        this.section6 = section6;
    }

    public Section7 getSection7() {
        return section7;
    }

    public void setSection7(Section7 section7) {
        this.section7 = section7;
    }

    public Section8 getSection8() {
        return section8;
    }

    public void setSection8(Section8 section8) {
        this.section8 = section8;
    }

    public Section10 getSection10() {
        return section10;
    }

    public void setSection10(Section10 section10) {
        this.section10 = section10;
    }
}
