package io.dentall.totoro.business.service.nhi.metric.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Section6 {

    @JsonProperty("Lp5")
    private MetricData l5;

    @JsonProperty("Lp6")
    private MetricData l6;

    private List<TimeLineData> timeline;

    public MetricData getL5() {
        return l5;
    }

    public void setL5(MetricData l5) {
        this.l5 = l5;
    }

    public MetricData getL6() {
        return l6;
    }

    public void setL6(MetricData l6) {
        this.l6 = l6;
    }

    public List<TimeLineData> getTimeline() {
        return timeline;
    }

    public void setTimeline(List<TimeLineData> timeline) {
        this.timeline = timeline;
    }
}
