package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static io.dentall.totoro.service.util.DateTimeUtil.toLocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class OdQuarterPlusOneAndHalfYearNearSource extends OdSource {

    private final LocalDate begin;

    public OdQuarterPlusOneAndHalfYearNearSource(MetricConfig metricConfig) {
        super(new OdQuarterPlusTwoYearNearSource(metricConfig));
        this.begin = toLocalDate(metricConfig.getQuarterRange().getBegin()).minus(450, DAYS);
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
