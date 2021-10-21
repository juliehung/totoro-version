package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;

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
public class DailyByMonthSelectedDisposalSource extends AbstractDisposalSource<Map<LocalDate, List<MetricDisposal>>> {

    private final LocalDate begin;

    private final LocalDate end;

    public DailyByMonthSelectedDisposalSource(MetricConfig metricConfig) {
        super(new MonthSelectedDisposalSource(metricConfig));
        LocalDate date = metricConfig.getBaseDate();
        this.begin = beginOfMonth(date);
        this.end = endOfMonth(date);
    }

    @Override
    public List<Map<LocalDate, List<MetricDisposal>>> doFilter(Stream<MetricDisposal> source) {
        Map<LocalDate, List<MetricDisposal>> map = source.collect(groupingBy(MetricDisposal::getDisposalDate));

        // 補齊沒有資料的日期
        for (LocalDate date = begin; !date.isAfter(end); date = date.plusDays(1L)) {
            map.putIfAbsent(date, emptyList());
        }

        return singletonList(map);
    }

}
