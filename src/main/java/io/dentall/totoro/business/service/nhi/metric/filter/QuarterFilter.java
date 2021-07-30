package io.dentall.totoro.business.service.nhi.metric.filter;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
import io.dentall.totoro.service.util.DateTimeUtil;
import io.dentall.totoro.service.util.DateTimeUtil.BeginEnd;

import java.time.LocalDate;
import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.filter.FilterKey.MonthSelected;
import static io.dentall.totoro.business.service.nhi.metric.filter.FilterKey.Quarter;
import static io.dentall.totoro.service.util.DateTimeUtil.*;
import static java.util.stream.Collectors.toList;

public class QuarterFilter implements Filter {

    private final LocalDate begin;

    private final LocalDate end;

    public QuarterFilter(BeginEnd quarterRange) {
        this.begin = toLocalDate(quarterRange.getBegin());
        this.end = toLocalDate(quarterRange.getEnd());
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
    public FilterKey filterKey() {
        return Quarter;
    }
}
