package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.service.util.DateTimeUtil.toLocalDate;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.groupingBy;

/**
 * 去年 date-10 季(Q1,Q2,Q3,Q4)
 */
public class QuarterByDailyOfLastYearSource extends AbstractSource<NhiMetricRawVM, Map<LocalDate, List<NhiMetricRawVM>>> {

    private final LocalDate begin;

    private final LocalDate end;

    public QuarterByDailyOfLastYearSource(MetricConfig metricConfig) {
        super(new QuarterOfLastYearSource(metricConfig));
        this.begin = toLocalDate(metricConfig.getQuarterRange().getBegin()).minusYears(1L);
        this.end = toLocalDate(metricConfig.getQuarterRange().getEnd()).minusYears(1L);
    }

    @Override
    public List<Map<LocalDate, List<NhiMetricRawVM>>> filter(List<NhiMetricRawVM> nhiMetricRawVMList) {
        Map<LocalDate, List<NhiMetricRawVM>> map = nhiMetricRawVMList.stream().collect(groupingBy(NhiMetricRawVM::getDisposalDate));

        // 補齊沒有資料的日期
        for (LocalDate date = begin; !date.isAfter(end); date = date.plusDays(1L)) {
            map.putIfAbsent(date, emptyList());
        }

        return singletonList(map);
    }

}
