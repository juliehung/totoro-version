package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static io.dentall.totoro.service.util.DateTimeUtil.toLocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class OdQuarterPlusTwoYearNearSource extends OdSource {

    private final LocalDate begin;

    public OdQuarterPlusTwoYearNearSource(MetricConfig metricConfig) {
        super(new OdQuarterPlusThreeYearNearSource(metricConfig));
        this.begin = toLocalDate(metricConfig.getQuarterRange().getBegin()).minus(730, DAYS);
    }

    @Override
    public List<MetricTooth> doFilter(Stream<MetricTooth> source) {
        return source
            .filter(dto -> begin.isBefore(dto.getDisposalDate()) || begin.isEqual(dto.getDisposalDate()))
            .filter(dto -> codes.contains(dto.getTreatmentProcedureCode()))
            .filter(dto -> isNotBlank(dto.getTooth()))
            .collect(toList());
    }

}