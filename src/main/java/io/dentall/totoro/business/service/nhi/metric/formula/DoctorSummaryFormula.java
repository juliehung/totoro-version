package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.DoctorSummaryDto;
import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.filter.Source;
import io.dentall.totoro.business.service.nhi.metric.meta.DoctorSummary;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

public class DoctorSummaryFormula extends AbstractFormula<List<DoctorSummaryDto>> {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public DoctorSummaryFormula(Collector collector, Source<NhiMetricRawVM, NhiMetricRawVM> source) {
        super(collector);
        this.source = source;
    }

    @Override
    protected List<DoctorSummaryDto> doCalculate(Collector collector) {
        DoctorSummary doctorSummary = new DoctorSummary(collector, source.outputKey()).apply();
        return doctorSummary.getResult();
    }
}
