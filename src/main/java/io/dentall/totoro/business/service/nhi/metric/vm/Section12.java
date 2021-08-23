package io.dentall.totoro.business.service.nhi.metric.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Section12 {

    @JsonProperty("Lp20")
    private MetricData l20;

    @JsonProperty("Lp21")
    private MetricData l21;

    @JsonProperty("Lp22")
    private MetricData l22;

    @JsonProperty("Lp23")
    private MetricData l23;

    public MetricData getL20() {
        return l20;
    }

    public void setL20(MetricData l20) {
        this.l20 = l20;
    }

    public MetricData getL21() {
        return l21;
    }

    public void setL21(MetricData l21) {
        this.l21 = l21;
    }

    public MetricData getL22() {
        return l22;
    }

    public void setL22(MetricData l22) {
        this.l22 = l22;
    }

    public MetricData getL23() {
        return l23;
    }

    public void setL23(MetricData l23) {
        this.l23 = l23;
    }
}
