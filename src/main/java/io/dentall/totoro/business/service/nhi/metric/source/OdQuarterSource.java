package io.dentall.totoro.business.service.nhi.metric.source;

public class OdQuarterSource extends OdSource {

    public OdQuarterSource(MetricConfig metricConfig) {
        super(new QuarterSource(metricConfig));
    }

}
