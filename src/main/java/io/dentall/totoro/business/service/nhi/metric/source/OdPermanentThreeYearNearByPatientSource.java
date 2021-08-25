package io.dentall.totoro.business.service.nhi.metric.source;

public class OdPermanentThreeYearNearByPatientSource extends OdPermanentSource {

    public OdPermanentThreeYearNearByPatientSource(MetricConfig metricConfig) {
        super(new OdThreeYearNearSource(metricConfig));
    }

}
