package io.dentall.totoro.business.service.nhi.metric.source;

public class OdPermanentQuarterPlusThreeYearNearByPatientSource extends OdPermanentByPatientSource {

    public OdPermanentQuarterPlusThreeYearNearByPatientSource(MetricConfig metricConfig) {
        super(new OdQuarterPlusThreeYearNearSource(metricConfig));
    }

}
