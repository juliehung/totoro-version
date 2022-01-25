package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static io.dentall.totoro.business.service.nhi.metric.source.MetricConstants.CodesByEndo1;
import static io.dentall.totoro.business.service.nhi.metric.source.MetricConstants.CodesByOd;
import static io.dentall.totoro.service.util.DateTimeUtil.toLocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class EndoAndOdHalfYearNearSource extends AbstractSource<MetricTooth> {

    private final LocalDate begin;

    private final LocalDate end;

    public EndoAndOdHalfYearNearSource(MetricConfig metricConfig) {
        super(metricConfig.getSubjectSource());
        this.begin = toLocalDate(metricConfig.getQuarterRange().getBegin()).minus(180, DAYS);
        this.end = toLocalDate(metricConfig.getQuarterRange().getEnd());
    }

    @Override
    public List<MetricTooth> doFilter(Stream<MetricTooth> source) {
        return source
            .filter(vm -> (begin.isBefore(vm.getDisposalDate()) && end.isAfter(vm.getDisposalDate()))
                || begin.isEqual(vm.getDisposalDate())
                || end.isEqual(vm.getDisposalDate()))
            .filter(vm -> CodesByEndo1.contains(vm.getTreatmentProcedureCode()) || CodesByOd.contains(vm.getTreatmentProcedureCode()))
            .filter(vm -> isNotBlank(vm.getTooth()))
            .collect(toList());
    }

}