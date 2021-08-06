package io.dentall.totoro.business.service.nhi.metric.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Section6 {

    private MetricData L5;

    private MetricData L6;

    private List<TimeLineData> timeline;

    @JsonProperty("Lp5")
    public MetricData getL5() {
        return L5;
    }

    public void setL5(MetricData l5) {
        L5 = l5;
    }

    @JsonProperty("Lp6")
    public MetricData getL6() {
        return L6;
    }

    public void setL6(MetricData l6) {
        L6 = l6;
    }

    public List<TimeLineData> getTimeline() {
        return timeline;
    }

    public void setTimeline(List<TimeLineData> timeline) {
        this.timeline = timeline;
    }
}
