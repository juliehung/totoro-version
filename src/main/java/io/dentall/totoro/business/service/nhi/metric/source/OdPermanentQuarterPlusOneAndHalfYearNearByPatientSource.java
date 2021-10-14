package io.dentall.totoro.business.service.nhi.metric.source;

public class OdPermanentQuarterPlusOneAndHalfYearNearByPatientSource extends OdPermanentByPatientSource {

    public OdPermanentQuarterPlusOneAndHalfYearNearByPatientSource(MetricConfig metricConfig) {
        super(new OdQuarterPlusOneAndHalfYearNearSource(metricConfig));
    }

}
