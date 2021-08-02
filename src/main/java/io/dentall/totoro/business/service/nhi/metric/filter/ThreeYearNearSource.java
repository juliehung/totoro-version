package io.dentall.totoro.business.service.nhi.metric.filter;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.time.LocalDate;
import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.filter.FilterKey.*;
import static io.dentall.totoro.service.util.DateTimeUtil.endOfMonth;
import static io.dentall.totoro.service.util.DateTimeUtil.isSameMonth;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.stream.Collectors.toList;

/**
 * date-12 一年 0~1095天
 */
public class ThreeYearNearSource extends AbstractSource<NhiMetricRawVM, NhiMetricRawVM>  {

    private final LocalDate begin;

    private final LocalDate end;

    public ThreeYearNearSource(LocalDate date) {
        if (isSameMonth(date)) {
            this.begin = date.minus(1095, DAYS);
            this.end = date;
        } else {
            this.begin = endOfMonth(date).minus(1095, DAYS);
            this.end = endOfMonth(date);
        }
    }

    @Override
    public List<NhiMetricRawVM> doFilter(List<NhiMetricRawVM> nhiMetricRawVMList) {
        return nhiMetricRawVMList.stream().parallel()
            .filter(vm ->
                begin.isEqual(vm.getDisposalDate())
                    || end.isEqual(vm.getDisposalDate())
                    || (begin.isBefore(vm.getDisposalDate()) && end.isAfter(vm.getDisposalDate())))
            .collect(toList());
    }

    @Override
    public String inputKey() {
        return ThreeYearNear.input();
    }

    @Override
    public String outputKey() {
        return ThreeYearNear.output();
    }
}
