package io.dentall.totoro.business.service.nhi.metric.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Section12 {

    private MetricData L20;

    private MetricData L21;

    private MetricData L22;

    private MetricData L23;

    @JsonProperty("Lp20")
    public MetricData getL20() {
        return L20;
    }

    public void setL20(MetricData l20) {
        L20 = l20;
    }

    @JsonProperty("Lp21")
    public MetricData getL21() {
        return L21;
    }

    public void setL21(MetricData l21) {
        L21 = l21;
    }

    @JsonProperty("Lp22")
    public MetricData getL22() {
        return L22;
    }

    public void setL22(MetricData l22) {
        L22 = l22;
    }

    @JsonProperty("Lp23")
    public MetricData getL23() {
        return L23;
    }

    public void setL23(MetricData l23) {
        L23 = l23;
    }
}
