package io.dentall.totoro.business.service.nhi.metric.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Section16 {

    @JsonProperty("Lp49")
    private MetricData l49;

    @JsonProperty("Lp50")
    private MetricData l50;

    @JsonProperty("Lp51")
    private MetricData l51;

    @JsonProperty("Lp52")
    private MetricData l52;

    public MetricData getL49() {
        return l49;
    }

    public void setL49(MetricData l49) {
        this.l49 = l49;
    }

    public MetricData getL50() {
        return l50;
    }

    public void setL50(MetricData l50) {
        this.l50 = l50;
    }

    public MetricData getL51() {
        return l51;
    }

    public void setL51(MetricData l51) {
        this.l51 = l51;
    }

    public MetricData getL52() {
        return l52;
    }

    public void setL52(MetricData l52) {
        this.l52 = l52;
    }
}
