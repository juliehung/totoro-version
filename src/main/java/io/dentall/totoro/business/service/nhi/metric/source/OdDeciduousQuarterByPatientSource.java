package io.dentall.totoro.business.service.nhi.metric.source;

public class OdDeciduousQuarterByPatientSource extends OdDeciduousSource {

    public OdDeciduousQuarterByPatientSource(MetricConfig metricConfig) {
        super(new OdQuarterSource(metricConfig));
    }

}
