package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static io.dentall.totoro.service.util.DateTimeUtil.toLocalDate;
import static java.util.stream.Collectors.toList;

/**
 * date-10 當季(Q1,Q2,Q3,Q4)
 */
public class QuarterSource extends AbstractSource<MetricTooth> {

    private final LocalDate begin;

    private final LocalDate end;

    public QuarterSource(MetricConfig metricConfig) {
        super(metricConfig.getSubjectSource());
        this.begin = toLocalDate(metricConfig.getQuarterRange().getBegin());
        this.end = toLocalDate(metricConfig.getQuarterRange().getEnd());
    }

    @Override
    public List<MetricTooth> doFilter(Stream<MetricTooth> source) {
        return source
            .filter(vm ->
                begin.isEqual(vm.getDisposalDate())
                    || end.isEqual(vm.getDisposalDate())
                    || (begin.isBefore(vm.getDisposalDate()) && end.isAfter(vm.getDisposalDate())))
            .collect(toList());
    }

}