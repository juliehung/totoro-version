package io.dentall.totoro.business.service.nhi.metric.source;

public class OdDeciduousQuarterPlusThreeYearNearByPatientSource extends OdDeciduousByPatientSource {

    public OdDeciduousQuarterPlusThreeYearNearByPatientSource(MetricConfig metricConfig) {
        super(new OdQuarterPlusThreeYearNearSource(metricConfig));
    }

}
