package io.dentall.totoro.business.service.nhi.metric.source;

public class OdDeciduousQuarterPlusOneYearNearByPatientSource extends OdDeciduousByPatientSource {

    public OdDeciduousQuarterPlusOneYearNearByPatientSource(MetricConfig metricConfig) {
        super(new OdQuarterPlusOneYearNearSource(metricConfig));
    }

}
