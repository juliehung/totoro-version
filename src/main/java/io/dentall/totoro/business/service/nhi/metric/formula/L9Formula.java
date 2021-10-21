package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.HighestDoctorDto;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.HighestPoint1Doctor;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MonthSelectedDisposalSource;
import io.dentall.totoro.business.service.nhi.metric.source.MonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

/**
 * 醫師最高總點數佔比 ＠date-15＠ 的 醫師合計點數(最高者)/院所合計點數
 */
public class L9Formula extends AbstractFormula<HighestDoctorDto> {

    private final Source<MetricTooth, MetricTooth> source;

    private final Source<MetricDisposal, MetricDisposal> disposalSource;

    public L9Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new MonthSelectedSource(metricConfig);
        this.disposalSource = new MonthSelectedDisposalSource(metricConfig);
    }

    @Override
    public HighestDoctorDto doCalculate(MetricConfig metricConfig) {
        HighestPoint1Doctor highestPoint1Doctor = new HighestPoint1Doctor(metricConfig, source, disposalSource).apply();
        return highestPoint1Doctor.getResult();
    }
}
