package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.time.LocalDate;
import java.util.List;

import static io.dentall.totoro.service.util.DateTimeUtil.toLocalDate;
import static java.util.stream.Collectors.toList;

/**
 * 去年 date-10 季(Q1,Q2,Q3,Q4)
 */
public class QuarterOfLastYearSource extends AbstractSource<NhiMetricRawVM, NhiMetricRawVM> {

    private final LocalDate begin;

    private final LocalDate end;

    public QuarterOfLastYearSource(MetricConfig metricConfig) {
        super(metricConfig.getSubjectSource());
        this.begin = toLocalDate(metricConfig.getQuarterRange().getBegin()).minusYears(1L);
        this.end = toLocalDate(metricConfig.getQuarterRange().getEnd()).minusYears(1L);
    }

    @Override
    public List<NhiMetricRawVM> filter(List<NhiMetricRawVM> nhiMetricRawVMList) {
        return nhiMetricRawVMList.stream().parallel()
            .filter(vm ->
                begin.isEqual(vm.getDisposalDate())
                    || end.isEqual(vm.getDisposalDate())
                    || (begin.isBefore(vm.getDisposalDate()) && end.isAfter(vm.getDisposalDate())))
            .collect(toList());
    }

}
