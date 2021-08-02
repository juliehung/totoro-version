package io.dentall.totoro.business.service.nhi.metric.filter;

import io.dentall.totoro.business.service.nhi.metric.util.OdDto;

import java.time.LocalDate;
import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.filter.FilterKey.OdOneYearNear;
import static io.dentall.totoro.service.util.DateTimeUtil.endOfMonth;
import static io.dentall.totoro.service.util.DateTimeUtil.isSameMonth;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class OdOneYearNearSource extends OdSource<OdDto> {

    private final LocalDate begin;

    private final LocalDate end; // 這邊通常都應該是要拿到當季的第一天，如1/1、4/1、7/1、10/1

    public OdOneYearNearSource(LocalDate baseDate, LocalDate endDate) {
        if (isSameMonth(baseDate)) {
            this.begin = baseDate.minus(365, DAYS);
        } else {
            this.begin = endOfMonth(baseDate).minus(365, DAYS);
        }
        this.end = endDate;
    }

    @Override
    public List<OdDto> doFilter(List<OdDto> source) {
        return source.stream().parallel()
            // 因為資料來源是OneYearNearSource，它是用季的最後一天或是當天當基準點往前推365天，這邊所以要排除該季的所有處置單
            .filter(dto ->
                begin.isEqual(dto.getDisposalDate())
                    || end.isEqual(dto.getDisposalDate())
                    || (begin.isBefore(dto.getDisposalDate()) && end.isAfter(dto.getDisposalDate())))
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
