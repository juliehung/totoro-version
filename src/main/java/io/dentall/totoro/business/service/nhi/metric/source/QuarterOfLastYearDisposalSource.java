package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static io.dentall.totoro.service.util.DateTimeUtil.toLocalDate;
import static java.util.stream.Collectors.toList;

/**
 * 去年 date-10 季(Q1,Q2,Q3,Q4)
 */
public class QuarterOfLastYearDisposalSource extends AbstractDisposalSource<MetricDisposal> {

    private final LocalDate begin;

    private final LocalDate end;

    public QuarterOfLastYearDisposalSource(MetricConfig metricConfig) {
        super(metricConfig.getSubjectDisposalSource());
        this.begin = toLocalDate(metricConfig.getQuarterRange().getBegin()).minusYears(1L);
        this.end = toLocalDate(metricConfig.getQuarterRange().getEnd()).minusYears(1L);
    }

    @Override
    public List<MetricDisposal> doFilter(Stream<MetricDisposal> source) {
        return source
            .filter(vm ->
                begin.isEqual(vm.getDisposalDate())
                    || end.isEqual(vm.getDisposalDate())
                    || (begin.isBefore(vm.getDisposalDate()) && end.isAfter(vm.getDisposalDate())))
            .collect(toList());
    }

}
