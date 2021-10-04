package io.dentall.totoro.business.service.nhi.metric.source;

public class OdOneAndHalfYearNearSource extends OdSource {

    public OdOneAndHalfYearNearSource(MetricConfig metricConfig) {
        super(new OneAndHalfYearNearSource(metricConfig));
    }

}
