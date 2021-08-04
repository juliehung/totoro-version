package io.dentall.totoro.business.service.nhi.metric.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Section7 {

    private MetricData L7;

    private MetricData L8;

    @JsonProperty("Lp7")
    public MetricData getL7() {
        return L7;
    }

    public void setL7(MetricData l7) {
        L7 = l7;
    }

    @JsonProperty("Lp8")
    public MetricData getL8() {
        return L8;
    }

    public void setL8(MetricData l8) {
        L8 = l8;
    }
}
