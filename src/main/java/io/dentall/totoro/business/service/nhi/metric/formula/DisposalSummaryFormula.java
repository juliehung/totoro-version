package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.DisposalSummaryDto;
import io.dentall.totoro.business.service.nhi.metric.meta.DisposalSummary;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

public class DisposalSummaryFormula extends AbstractFormula<List<DisposalSummaryDto>> {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public DisposalSummaryFormula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new MonthSelectedSource(metricConfig);
    }

    @Override
    protected List<DisposalSummaryDto> doCalculate(MetricConfig metricConfig) {
        DisposalSummary disposalSummary = new DisposalSummary(metricConfig, source).apply();
        return disposalSummary.getResult();
    }
}
