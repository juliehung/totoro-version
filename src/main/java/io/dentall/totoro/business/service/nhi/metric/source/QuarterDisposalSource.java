package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static io.dentall.totoro.service.util.DateTimeUtil.toLocalDate;
import static java.util.stream.Collectors.toList;

/**
 * date-10 當季(Q1,Q2,Q3,Q4)
 */
public class QuarterDisposalSource extends AbstractDisposalSource<MetricDisposal> {

    private final LocalDate begin;

    private final LocalDate end;

    public QuarterDisposalSource(MetricConfig metricConfig) {
        super(metricConfig.getSubjectDisposalSource());
        this.begin = toLocalDate(metricConfig.getQuarterRange().getBegin());
        this.end = toLocalDate(metricConfig.getQuarterRange().getEnd());
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
