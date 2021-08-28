package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static io.dentall.totoro.service.util.DateTimeUtil.toLocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class OdTwoYearNearSource extends OdSource<OdDto> {

    private final LocalDate begin;

    public OdTwoYearNearSource(MetricConfig metricConfig) {
        super(new OdThreeYearNearSource(metricConfig));
        this.begin = toLocalDate(metricConfig.getQuarterRange().getBegin()).minus(730, DAYS);
    }

    @Override
    public List<OdDto> doFilter(Stream<OdDto> source) {
        return source
            .filter(dto -> begin.isBefore(dto.getDisposalDate()) || begin.isEqual(dto.getDisposalDate()))
            .filter(dto -> codes.contains(dto.getTreatmentProcedureCode()))
            .filter(dto -> isNotBlank(dto.getTreatmentProcedureTooth()))
            .collect(toList());
    }

}
