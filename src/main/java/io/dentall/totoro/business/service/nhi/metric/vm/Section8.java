package io.dentall.totoro.business.service.nhi.metric.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Section8 {

    private MetricData L9;

    private MetricData L10;

    @JsonProperty("Lp9")
    public MetricData getL9() {
        return L9;
    }

    public void setL9(MetricData l9) {
        L9 = l9;
    }

    @JsonProperty("Lp10")
    public MetricData getL10() {
        return L10;
    }

    public void setL10(MetricData l10) {
        L10 = l10;
    }
}
