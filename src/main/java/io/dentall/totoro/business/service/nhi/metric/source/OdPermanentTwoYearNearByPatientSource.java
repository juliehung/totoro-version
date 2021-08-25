package io.dentall.totoro.business.service.nhi.metric.source;

public class OdPermanentTwoYearNearByPatientSource extends OdPermanentSource {

    public OdPermanentTwoYearNearByPatientSource(MetricConfig metricConfig) {
        super(new OdTwoYearNearSource(metricConfig));
    }

}
