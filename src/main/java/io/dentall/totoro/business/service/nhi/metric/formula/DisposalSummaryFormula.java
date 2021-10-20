package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.DisposalSummaryDto;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.meta.DisposalSummary;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MonthSelectedDisposalSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.List;

public class DisposalSummaryFormula extends AbstractFormula<List<DisposalSummaryDto>> {

    private final Source<MetricDisposal, MetricDisposal> source;

    public DisposalSummaryFormula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new MonthSelectedDisposalSource(metricConfig);
    }

    @Override
    protected List<DisposalSummaryDto> doCalculate(MetricConfig metricConfig) {
        DisposalSummary disposalSummary = new DisposalSummary(metricConfig, source).apply();
        return disposalSummary.getResult();
    }
}
