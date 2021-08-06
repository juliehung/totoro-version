package io.dentall.totoro.business.service.nhi.metric.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Section7 {

    private MetricData L7;

    private MetricData L8;

    private List<TimeLineData> timeline;

    @JsonProperty("Lp7")
    public MetricData getL7() {
        return L7;
    }

    public void setL7(MetricData l7) {
        L7 = l7;
    }

    @JsonProperty("Lp8")
    public MetricData getL8() {
        return L8;
    }

    public void setL8(MetricData l8) {
        L8 = l8;
    }

    public List<TimeLineData> getTimeline() {
        return timeline;
    }

    public void setTimeline(List<TimeLineData> timeline) {
        this.timeline = timeline;
    }
}
