package io.dentall.totoro.business.service.nhi.metric.filter;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.time.LocalDate;
import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.filter.FilterKey.OneYearNear;
import static io.dentall.totoro.service.util.DateTimeUtil.endOfMonth;
import static io.dentall.totoro.service.util.DateTimeUtil.isSameMonth;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.stream.Collectors.toList;

/**
 * date-2 半年 0~180天
 */
public class HalfYearNearSource extends AbstractSource<NhiMetricRawVM, NhiMetricRawVM> {

    private final LocalDate begin;

    private final LocalDate end;

    public HalfYearNearSource(LocalDate date) {
        if (isSameMonth(date)) {
            this.begin = date.minus(180, DAYS);
            this.end = date;
        } else {
            this.begin = endOfMonth(date).minus(180, DAYS);
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
