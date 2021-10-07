package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.HighestPatientDto;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.HighestPoint1Patient;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

/**
 * 病患最高總點數佔比 ＠date-15＠ 的 病患合計點數(最高者)/@Point-1@
 */
public class L10Formula extends AbstractFormula<HighestPatientDto> {

    private final Source<MetricTooth, MetricTooth> source;

    public L10Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new MonthSelectedSource(metricConfig);
    }

    @Override
    public HighestPatientDto doCalculate(MetricConfig metricConfig) {
        HighestPoint1Patient highestPoint1Patient = new HighestPoint1Patient(metricConfig, source).apply();
        return highestPoint1Patient.getResult();
    }
}
