package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.DisposalSummaryDto;
import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.service.nhi.metric.meta.DisposalSummary;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

public class DisposalSummaryFormula extends AbstractFormula<List<DisposalSummaryDto>> {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public DisposalSummaryFormula(Collector collector, Source<NhiMetricRawVM, NhiMetricRawVM> source) {
        super(collector);
        this.source = source;
    }

    @Override
    protected List<DisposalSummaryDto> doCalculate(Collector collector) {
        DisposalSummary disposalSummary = new DisposalSummary(collector, source.outputKey()).apply();
        return disposalSummary.getResult();
    }
}
