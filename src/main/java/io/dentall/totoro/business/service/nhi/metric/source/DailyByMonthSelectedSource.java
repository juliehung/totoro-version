package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static io.dentall.totoro.service.util.DateTimeUtil.beginOfMonth;
import static io.dentall.totoro.service.util.DateTimeUtil.endOfMonth;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.groupingBy;

/**
 * date-15 月(自選案件)
 */
public class DailyByMonthSelectedSource extends AbstractSource<Map<LocalDate, List<MetricTooth>>> {

    private final LocalDate begin;

    private final LocalDate end;

    public DailyByMonthSelectedSource(MetricConfig metricConfig) {
        super(new MonthSelectedSource(metricConfig));
        LocalDate date = metricConfig.getBaseDate();
        this.begin = beginOfMonth(date);
        this.end = endOfMonth(date);
    }

    @Override
    public List<Map<LocalDate, List<MetricTooth>>> doFilter(Stream<MetricTooth> source) {
        Map<LocalDate, List<MetricTooth>> map = source.collect(groupingBy(MetricTooth::getDisposalDate));

        // 補齊沒有資料的日期
        for (LocalDate date = begin; !date.isAfter(end); date = date.plusDays(1L)) {
            map.putIfAbsent(date, emptyList());
        }

        return singletonList(map);
    }

}
