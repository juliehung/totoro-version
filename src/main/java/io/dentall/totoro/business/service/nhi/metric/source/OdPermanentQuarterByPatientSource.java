package io.dentall.totoro.business.service.nhi.metric.source;

public class OdPermanentQuarterByPatientSource extends OdPermanentByPatientSource {

    public OdPermanentQuarterByPatientSource(MetricConfig metricConfig) {
        super(new OdQuarterSource(metricConfig));
    }

}
