package io.dentall.totoro.business.service.nhi.metric.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Section17 {

    @JsonProperty("Lp53")
    private MetricData l53;

    @JsonProperty("Lp54")
    private MetricData l54;

    @JsonProperty("Lp55")
    private MetricData l55;

    @JsonProperty("Lp56")
    private MetricData l56;

    public MetricData getL53() {
        return l53;
    }

    public void setL53(MetricData l53) {
        this.l53 = l53;
    }

    public MetricData getL54() {
        return l54;
    }

    public void setL54(MetricData l54) {
        this.l54 = l54;
    }

    public MetricData getL55() {
        return l55;
    }

    public void setL55(MetricData l55) {
        this.l55 = l55;
    }

    public MetricData getL56() {
        return l56;
    }

    public void setL56(MetricData l56) {
        this.l56 = l56;
    }
}
