package io.dentall.totoro.business.service.nhi.metric.source;

public class OdDeciduousOneAndHalfYearNearByPatientSource extends OdDeciduousByPatientSource {

    public OdDeciduousOneAndHalfYearNearByPatientSource(MetricConfig metricConfig) {
        super(new OdOneAndHalfYearNearSource(metricConfig));
    }

}
