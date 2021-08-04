package io.dentall.totoro.business.service.nhi.metric.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Section13 {

    private MetricData L24;

    @JsonProperty("Lp24")
    public MetricData getL24() {
        return L24;
    }

    public void setL24(MetricData l24) {
        L24 = l24;
    }
}
