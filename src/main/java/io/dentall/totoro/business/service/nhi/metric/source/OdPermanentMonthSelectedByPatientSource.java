package io.dentall.totoro.business.service.nhi.metric.source;

public class OdPermanentMonthSelectedByPatientSource extends OdPermanentByPatientSource {

    public OdPermanentMonthSelectedByPatientSource(MetricConfig metricConfig) {
        super(new OdMonthSelectedSource(metricConfig));
    }

}
