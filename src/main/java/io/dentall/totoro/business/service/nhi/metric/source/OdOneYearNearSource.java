package io.dentall.totoro.business.service.nhi.metric.source;

public class OdOneYearNearSource extends OdSource {

    public OdOneYearNearSource(MetricConfig metricConfig) {
        super(new OneYearNearSource(metricConfig));
    }

}
