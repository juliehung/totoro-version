package io.dentall.totoro.business.service.nhi.metric.source;

public class OdDeciduousThreeYearNearByPatientSource extends OdDeciduousSource {

    public OdDeciduousThreeYearNearByPatientSource(MetricConfig metricConfig) {
        super(new OdThreeYearNearSource(metricConfig));
    }

}
