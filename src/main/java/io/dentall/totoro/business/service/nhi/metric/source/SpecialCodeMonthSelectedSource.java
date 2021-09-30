package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.NhiSpecialCode;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTreatment;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static io.dentall.totoro.business.service.nhi.NhiSpecialCode.OTHER;
import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingBy;

/**
 * date-15 月(自選案件)
 */
public class SpecialCodeMonthSelectedSource extends AbstractSource<Map<NhiSpecialCode, List<MetricTreatment>>> {

    public SpecialCodeMonthSelectedSource(MetricConfig metricConfig) {
        super(new MonthSelectedSource(metricConfig));
    }

    @Override
    public List<Map<NhiSpecialCode, List<MetricTreatment>>> doFilter(Stream<MetricTooth> source) {
        return singletonList(source.map(MetricTooth::getTreatment).distinct().collect(groupingBy(vm -> mapToSpecialCode(vm.getTreatmentProcedureSpecificCode()))));
    }

    private NhiSpecialCode mapToSpecialCode(String specialCode) {
        try {
            return ofNullable(specialCode).map(NhiSpecialCode::valueOf).orElse(OTHER);
        } catch (IllegalArgumentException e) {
            return OTHER;
        }
    }

}
