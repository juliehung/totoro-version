package io.dentall.totoro.business.service.nhi.metric.source;

public class OdPermanentQuarterPlusTwoYearNearByPatientSource extends OdPermanentByPatientSource {

    public OdPermanentQuarterPlusTwoYearNearByPatientSource(MetricConfig metricConfig) {
        super(new OdQuarterPlusTwoYearNearSource(metricConfig));
    }

}
