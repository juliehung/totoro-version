package io.dentall.totoro.business.service.nhi.metric.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Section13 {

    @JsonProperty("Lp24")
    private MetricData l24;

    public MetricData getL24() {
        return l24;
    }

    public void setL24(MetricData l24) {
        this.l24 = l24;
    }
}
