package io.dentall.totoro.business.service.nhi.metric.source;

public class OdPermanentQuarterPlusOneYearNearByPatientSource extends OdPermanentByPatientSource {

    public OdPermanentQuarterPlusOneYearNearByPatientSource(MetricConfig metricConfig) {
        super(new OdQuarterPlusOneYearNearSource(metricConfig));
    }

}
