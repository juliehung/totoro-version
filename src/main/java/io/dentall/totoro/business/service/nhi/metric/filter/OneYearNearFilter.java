package io.dentall.totoro.business.service.nhi.metric.filter;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.filter.FilterKey.OneYearNear;
import static io.dentall.totoro.business.service.nhi.metric.filter.FilterKey.ThreeMonthNear;
import static io.dentall.totoro.service.util.DateTimeUtil.*;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.util.stream.Collectors.toList;

/**
 * date-3 一年 0~365天
 */
public class OneYearNearFilter implements Filter {

    private final LocalDate begin;

    private final LocalDate end;

    public OneYearNearFilter(LocalDate date) {
        if (isSameMonth(date)) {
            this.begin = date.minus(365, DAYS);
            this.end = date;
        } else {
            this.begin = endOfMonth(date).minus(365, DAYS);
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
        return OneYearNear.input();
    }

    @Override
    public String outputKey() {
        return OneYearNear.output();
    }
}
