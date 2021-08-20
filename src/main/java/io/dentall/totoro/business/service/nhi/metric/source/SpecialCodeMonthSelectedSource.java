package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.NhiSpecialCode;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.NhiSpecialCode.OTHER;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.groupingBy;

/**
 * date-15 月(自選案件)
 */
public class SpecialCodeMonthSelectedSource extends AbstractSource<NhiMetricRawVM, Map<NhiSpecialCode, List<NhiMetricRawVM>>> {

    public SpecialCodeMonthSelectedSource(InputSource<NhiMetricRawVM> inputSource) {
        super(inputSource);
    }

    @Override
    public List<Map<NhiSpecialCode, List<NhiMetricRawVM>>> doFilter(List<NhiMetricRawVM> source) {
        return singletonList(source.stream().parallel()
            .filter(vm -> vm.getTreatmentProcedureSpecificCode() != null)
            .collect(groupingBy(vm -> vm.getTreatmentProcedureSpecificCode() != null ? vm.getTreatmentProcedureSpecificCode() : OTHER)));
    }

}
