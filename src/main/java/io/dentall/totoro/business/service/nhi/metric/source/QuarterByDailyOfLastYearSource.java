package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static io.dentall.totoro.service.util.DateTimeUtil.toLocalDate;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.groupingBy;

/**
 * 去年 date-10 季(Q1,Q2,Q3,Q4)
 */
public class QuarterByDailyOfLastYearSource extends AbstractSource<Map<LocalDate, List<MetricTooth>>> {

    private final LocalDate begin;

    private final LocalDate end;

    public QuarterByDailyOfLastYearSource(MetricConfig metricConfig) {
        super(new QuarterOfLastYearSource(metricConfig));
        this.begin = toLocalDate(metricConfig.getQuarterRange().getBegin()).minusYears(1L);
        this.end = toLocalDate(metricConfig.getQuarterRange().getEnd()).minusYears(1L);
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
