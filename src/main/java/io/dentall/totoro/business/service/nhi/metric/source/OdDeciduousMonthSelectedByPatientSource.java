package io.dentall.totoro.business.service.nhi.metric.source;

public class OdDeciduousMonthSelectedByPatientSource extends OdDeciduousByPatientSource {

    public OdDeciduousMonthSelectedByPatientSource(MetricConfig metricConfig) {
        super(new OdMonthSelectedSource(metricConfig));
    }

}
