package io.dentall.totoro.business.service.nhi.metric.source;

public class OdDeciduousTwoYearNearByPatientSource extends OdDeciduousSource {

    public OdDeciduousTwoYearNearByPatientSource(MetricConfig metricConfig) {
        super(new OdTwoYearNearSource(metricConfig));
    }

}
