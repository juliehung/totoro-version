package io.dentall.totoro.business.service.nhi.metric.filter;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.time.LocalDate;
import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.filter.FilterKey.MonthSelected;
import static io.dentall.totoro.service.util.DateTimeUtil.beginOfMonth;
import static io.dentall.totoro.service.util.DateTimeUtil.endOfMonth;
import static java.util.stream.Collectors.toList;

/**
 * date-15 月(自選案件)
 */
public class MonthSelectedSource extends AbstractSource<NhiMetricRawVM, NhiMetricRawVM>  {

    private final LocalDate begin;

    private final LocalDate end;

    public MonthSelectedSource(LocalDate date) {
        this.begin = beginOfMonth(date);
        this.end = endOfMonth(date);
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
        return MonthSelected.input();
    }

    @Override
    public String outputKey() {
        return MonthSelected.output();
    }
}
