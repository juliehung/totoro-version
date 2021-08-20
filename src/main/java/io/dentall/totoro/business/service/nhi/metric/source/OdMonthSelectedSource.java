package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;

import java.time.LocalDate;
import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.source.SourceId.OdMonthSelected;
import static io.dentall.totoro.service.util.DateTimeUtil.beginOfMonth;
import static io.dentall.totoro.service.util.DateTimeUtil.endOfMonth;
import static java.util.stream.Collectors.toList;

/**
 * date-15 月(自選案件)
 */
public class OdMonthSelectedSource extends OdSource<OdDto>  {

    private final LocalDate begin;

    private final LocalDate end;

    public OdMonthSelectedSource(LocalDate date) {
        this.begin = beginOfMonth(date);
        this.end = endOfMonth(date);
    }

    @Override
    public List<OdDto> doFilter(List<OdDto> source) {
        return source.stream().parallel()
            .filter(dto ->
                begin.isEqual(dto.getDisposalDate())
                    || end.isEqual(dto.getDisposalDate())
                    || (begin.isBefore(dto.getDisposalDate()) && end.isAfter(dto.getDisposalDate())))
            .collect(toList());
    }

    @Override
    public String inputKey() {
        return OdMonthSelected.input();
    }

    @Override
    public String outputKey() {
        return OdMonthSelected.output();
    }
}
