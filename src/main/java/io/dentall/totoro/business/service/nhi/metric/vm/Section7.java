package io.dentall.totoro.business.service.nhi.metric.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Section7 {

    @JsonProperty("Lp7")
    private MetricData l7;

    @JsonProperty("Lp8")
    private MetricData l8;

    private List<TimeLineData> timeline;

    public MetricData getL7() {
        return l7;
    }

    public void setL7(MetricData l7) {
        this.l7 = l7;
    }

    public MetricData getL8() {
        return l8;
    }

    public void setL8(MetricData l8) {
        this.l8 = l8;
    }

    public List<TimeLineData> getTimeline() {
        return timeline;
    }

    public void setTimeline(List<TimeLineData> timeline) {
        this.timeline = timeline;
    }
}
