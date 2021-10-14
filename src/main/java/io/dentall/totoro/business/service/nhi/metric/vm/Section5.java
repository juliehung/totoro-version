package io.dentall.totoro.business.service.nhi.metric.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Section5 {

    @JsonProperty("Lp1")
    private MetricData l1;

    @JsonProperty("Lp2")
    private MetricData l2;

    @JsonProperty("Lp3")
    private MetricData l3;

    @JsonProperty("Lp4")
    private MetricData l4;

    public MetricData getL1() {
        return l1;
    }

    public void setL1(MetricData l1) {
        this.l1 = l1;
    }

    public MetricData getL2() {
        return l2;
    }

    public void setL2(MetricData l2) {
        this.l2 = l2;
    }

    public MetricData getL3() {
        return l3;
    }

    public void setL3(MetricData l3) {
        this.l3 = l3;
    }

    public MetricData getL4() {
        return l4;
    }

    public void setL4(MetricData l4) {
        this.l4 = l4;
    }
}
