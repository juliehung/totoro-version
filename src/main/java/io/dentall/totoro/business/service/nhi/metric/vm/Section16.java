package io.dentall.totoro.business.service.nhi.metric.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Section16 {

    private MetricData L49;

    private MetricData L50;

    private MetricData L51;

    private MetricData L52;

    @JsonProperty("Lp49")
    public MetricData getL49() {
        return L49;
    }

    public void setL49(MetricData l49) {
        L49 = l49;
    }

    @JsonProperty("Lp50")
    public MetricData getL50() {
        return L50;
    }

    public void setL50(MetricData l50) {
        L50 = l50;
    }

    @JsonProperty("Lp51")
    public MetricData getL51() {
        return L51;
    }

    public void setL51(MetricData l51) {
        L51 = l51;
    }

    @JsonProperty("Lp52")
    public MetricData getL52() {
        return L52;
    }

    public void setL52(MetricData l52) {
        L52 = l52;
    }
}
