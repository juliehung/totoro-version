package io.dentall.totoro.business.service.nhi.metric.source;

public class OdDeciduousQuarterByPatientSource extends OdDeciduousByPatientSource {

    public OdDeciduousQuarterByPatientSource(MetricConfig metricConfig) {
        super(new OdQuarterSource(metricConfig));
    }

}
