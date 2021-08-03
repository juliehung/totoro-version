package io.dentall.totoro.business.service.nhi.metric.filter;

import io.dentall.totoro.business.service.nhi.NhiSpecialCode;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.filter.FilterKey.SpecialCodeMonthSelected;
import static io.dentall.totoro.service.util.DateTimeUtil.beginOfMonth;
import static io.dentall.totoro.service.util.DateTimeUtil.endOfMonth;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.groupingBy;

/**
 * date-15 月(自選案件)
 */
public class SpecialCodeMonthSelectedSource extends AbstractSource<NhiMetricRawVM, Map<NhiSpecialCode, List<NhiMetricRawVM>>> {

    public SpecialCodeMonthSelectedSource() {
    }

    @Override
    public List<Map<NhiSpecialCode, List<NhiMetricRawVM>>> doFilter(List<NhiMetricRawVM> source) {
        return singletonList(source.stream().parallel()
            .filter(vm -> vm.getTreatmentProcedureSpecificCode() != null)
            .collect(groupingBy(NhiMetricRawVM::getTreatmentProcedureSpecificCode)));
    }

    @Override
    public String inputKey() {
        return SpecialCodeMonthSelected.input();
    }

    @Override
    public String outputKey() {
        return SpecialCodeMonthSelected.output();
    }
}
