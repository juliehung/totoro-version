package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static io.dentall.totoro.service.util.DateTimeUtil.endOfMonth;
import static io.dentall.totoro.service.util.DateTimeUtil.isSameMonth;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.stream.Collectors.toList;

/**
 * date-12 一年 0~1095天
 */
public class ThreeYearNearSource extends AbstractSource<MetricTooth> {

    private final LocalDate begin;

    private final LocalDate end;

    public ThreeYearNearSource(MetricConfig metricConfig) {
        super(metricConfig.getSubjectSource());
        LocalDate date = metricConfig.getBaseDate();
        if (isSameMonth(date)) {
            this.begin = date.minus(1095, DAYS);
            this.end = date;
        } else {
            this.begin = endOfMonth(date).minus(1095, DAYS);
            this.end = endOfMonth(date);
        }
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