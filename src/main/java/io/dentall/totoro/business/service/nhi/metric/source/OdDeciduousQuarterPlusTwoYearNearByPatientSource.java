package io.dentall.totoro.business.service.nhi.metric.source;

public class OdDeciduousQuarterPlusTwoYearNearByPatientSource extends OdDeciduousByPatientSource {

    public OdDeciduousQuarterPlusTwoYearNearByPatientSource(MetricConfig metricConfig) {
        super(new OdQuarterPlusTwoYearNearSource(metricConfig));
    }

}
