package io.dentall.totoro.business.service.nhi.metric.source;

public class OdDeciduousOneAndHalfYearNearByPatientSource extends OdDeciduousSource {

    public OdDeciduousOneAndHalfYearNearByPatientSource(MetricConfig metricConfig) {
        super(new OdOneAndHalfYearNearSource(metricConfig));
    }

}
