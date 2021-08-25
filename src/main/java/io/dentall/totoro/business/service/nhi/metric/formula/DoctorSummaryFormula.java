package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.DoctorSummaryDto;
import io.dentall.totoro.business.service.nhi.metric.meta.DoctorSummary;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

public class DoctorSummaryFormula extends AbstractFormula<List<DoctorSummaryDto>> {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public DoctorSummaryFormula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new MonthSelectedSource(metricConfig);
    }

    @Override
    protected List<DoctorSummaryDto> doCalculate(MetricConfig metricConfig) {
        DoctorSummary doctorSummary = new DoctorSummary(metricConfig, source).apply();
        return doctorSummary.getResult();
    }
}
