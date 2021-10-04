package io.dentall.totoro.business.service.nhi.metric.source;

/**
 * date-15 月(自選案件)
 */
public class OdMonthSelectedSource extends OdSource {

    public OdMonthSelectedSource(MetricConfig metricConfig) {
        super(new MonthSelectedSource(metricConfig));
    }

}
