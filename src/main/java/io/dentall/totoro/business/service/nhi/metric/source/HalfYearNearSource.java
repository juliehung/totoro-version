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
 * date-2 半年 0~180天
 */
public class HalfYearNearSource extends AbstractSource<MetricTooth> {

    private final LocalDate begin;

    private final LocalDate end;

    public HalfYearNearSource(MetricConfig metricConfig) {
        super(new OneYearNearSource(metricConfig));
        LocalDate date = metricConfig.getBaseDate();
        if (isSameMonth(date)) {
            this.begin = date.minus(180, DAYS);
            this.end = date;
        } else {
            this.begin = endOfMonth(date).minus(180, DAYS);
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
