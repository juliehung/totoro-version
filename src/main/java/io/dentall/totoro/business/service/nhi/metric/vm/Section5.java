package io.dentall.totoro.business.service.nhi.metric.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Section5 {

    private MetricData L1;

    private MetricData L2;

    private MetricData L3;

    private MetricData L4;

    @JsonProperty("Lp1")
    public MetricData getL1() {
        return L1;
    }

    public void setL1(MetricData l1) {
        L1 = l1;
    }

    @JsonProperty("Lp2")
    public MetricData getL2() {
        return L2;
    }

    public void setL2(MetricData l2) {
        L2 = l2;
    }

    @JsonProperty("Lp3")
    public MetricData getL3() {
        return L3;
    }

    public void setL3(MetricData l3) {
        L3 = l3;
    }

    @JsonProperty("Lp4")
    public MetricData getL4() {
        return L4;
    }

    public void setL4(MetricData l4) {
        L4 = l4;
    }

}
