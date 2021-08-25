package io.dentall.totoro.business.service.nhi.metric.source;

public class OdPermanentOneYearNearByPatientSource extends OdPermanentSource {

    public OdPermanentOneYearNearByPatientSource(MetricConfig metricConfig) {
        super(new OdOneYearNearSource(metricConfig));
    }

}
