package io.dentall.totoro.business.service.nhi.metric.source;

public class OdPermanentQuarterByPatientSource extends OdPermanentSource {

    public OdPermanentQuarterByPatientSource(MetricConfig metricConfig) {
        super(new OdQuarterSource(metricConfig));
    }

}
