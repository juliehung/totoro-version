package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static io.dentall.totoro.service.util.DateTimeUtil.beginOfMonth;
import static io.dentall.totoro.service.util.DateTimeUtil.endOfMonth;
import static java.util.stream.Collectors.toList;

/**
 * date-15 月(自選案件)
 */
public class MonthSelectedDisposalSource extends AbstractDisposalSource<MetricDisposal> {

    private final LocalDate begin;

    private final LocalDate end;

    public MonthSelectedDisposalSource(MetricConfig metricConfig) {
        super(new QuarterDisposalSource(metricConfig));
        this.begin = beginOfMonth(metricConfig.getBaseDate());
        this.end = endOfMonth(metricConfig.getBaseDate());
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
