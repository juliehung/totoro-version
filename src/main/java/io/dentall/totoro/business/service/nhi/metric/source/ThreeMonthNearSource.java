package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static io.dentall.totoro.service.util.DateTimeUtil.beginOfMonth;
import static io.dentall.totoro.service.util.DateTimeUtil.endOfMonth;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.util.stream.Collectors.toList;

/**
 * date-9 該月+前兩月
 */
public class ThreeMonthNearSource extends AbstractSource<MetricTooth> {

    private final LocalDate begin;

    private final LocalDate end;

    public ThreeMonthNearSource(MetricConfig metricConfig) {
        super(new HalfYearNearSource(metricConfig));
        LocalDate date = metricConfig.getBaseDate();
        this.begin = beginOfMonth(date).minus(2, MONTHS);
        this.end = endOfMonth(date);
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
