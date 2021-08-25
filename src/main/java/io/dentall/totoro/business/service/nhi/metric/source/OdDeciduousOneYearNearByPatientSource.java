package io.dentall.totoro.business.service.nhi.metric.source;

public class OdDeciduousOneYearNearByPatientSource extends OdDeciduousSource {

    public OdDeciduousOneYearNearByPatientSource(MetricConfig metricConfig) {
        super(new OdOneYearNearSource(metricConfig));
    }

}
