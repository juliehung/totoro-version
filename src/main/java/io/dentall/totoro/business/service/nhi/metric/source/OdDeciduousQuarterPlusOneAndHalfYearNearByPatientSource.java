package io.dentall.totoro.business.service.nhi.metric.source;

public class OdDeciduousQuarterPlusOneAndHalfYearNearByPatientSource extends OdDeciduousByPatientSource {

    public OdDeciduousQuarterPlusOneAndHalfYearNearByPatientSource(MetricConfig metricConfig) {
        super(new OdQuarterPlusOneAndHalfYearNearSource(metricConfig));
    }

}
