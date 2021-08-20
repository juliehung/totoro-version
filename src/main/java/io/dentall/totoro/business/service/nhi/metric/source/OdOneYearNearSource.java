package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;

import java.time.LocalDate;
import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.source.SourceId.OdOneYearNear;
import static io.dentall.totoro.service.util.DateTimeUtil.endOfMonth;
import static io.dentall.totoro.service.util.DateTimeUtil.isSameMonth;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class OdOneYearNearSource extends OdSource<OdDto> {

    private final LocalDate begin;

    public OdOneYearNearSource(LocalDate baseDate) {
        if (isSameMonth(baseDate)) {
            this.begin = baseDate.minus(365, DAYS);
        } else {
            this.begin = endOfMonth(baseDate).minus(365, DAYS);
        }
    }

    @Override
    public List<OdDto> doFilter(List<OdDto> source) {
        return source.stream().parallel()
            .filter(dto -> begin.isBefore(dto.getDisposalDate()) || begin.isEqual(dto.getDisposalDate()))
            .filter(dto -> codes.contains(dto.getCode()))
            .filter(dto -> isNotBlank(dto.getTooth()))
            .collect(toList());
    }

    @Override
    public String inputKey() {
        return OdOneYearNear.input();
    }

    @Override
    public String outputKey() {
        return OdOneYearNear.output();
    }
}
