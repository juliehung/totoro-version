package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.DoctorSummaryDto;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.DoctorSummary;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MonthSelectedDisposalSource;
import io.dentall.totoro.business.service.nhi.metric.source.MonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.List;

public class DoctorSummaryFormula extends AbstractFormula<List<DoctorSummaryDto>> {

    private final Source<MetricTooth, MetricTooth> source;

    private final Source<MetricDisposal, MetricDisposal> disposalSource;

    public DoctorSummaryFormula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new MonthSelectedSource(metricConfig);
        this.disposalSource = new MonthSelectedDisposalSource(metricConfig);
    }

    @Override
    protected List<DoctorSummaryDto> doCalculate(MetricConfig metricConfig) {
        DoctorSummary doctorSummary = new DoctorSummary(metricConfig, source, disposalSource).apply();
        return doctorSummary.getResult();
    }
}
