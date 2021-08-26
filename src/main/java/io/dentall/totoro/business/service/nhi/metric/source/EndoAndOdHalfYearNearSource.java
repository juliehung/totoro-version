package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.mapper.NhiMetricRawMapper;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static io.dentall.totoro.business.service.nhi.metric.source.MetricConstants.CodesByEndo1;
import static io.dentall.totoro.business.service.nhi.metric.source.MetricConstants.CodesByOd;
import static io.dentall.totoro.business.service.nhi.util.ToothUtil.splitA74;
import static io.dentall.totoro.service.util.DateTimeUtil.toLocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class EndoAndOdHalfYearNearSource extends AbstractSource<NhiMetricRawVM, OdDto> {

    private final LocalDate begin;

    public EndoAndOdHalfYearNearSource(MetricConfig metricConfig) {
        super(metricConfig.getSubjectSource());
        this.begin = toLocalDate(metricConfig.getQuarterRange().getBegin()).minus(180, DAYS);
    }

    @Override
    public List<OdDto> filter(List<NhiMetricRawVM> source) {
        AtomicInteger i = new AtomicInteger();
        return source.stream().parallel()
            .filter(vm -> begin.isBefore(vm.getDisposalDate()) || begin.isEqual(vm.getDisposalDate()))
            .filter(vm -> CodesByEndo1.contains(vm.getTreatmentProcedureCode()) || CodesByOd.contains(vm.getTreatmentProcedureCode()))
            .filter(vm -> isNotBlank(vm.getTreatmentProcedureTooth()))
            .map(vm -> {
                    List<String> teeth = splitA74(vm.getTreatmentProcedureTooth());
                    int seq = i.getAndIncrement();
                    return teeth.stream().map(tooth -> {
                            OdDto odDto = NhiMetricRawMapper.INSTANCE.mapToOdDto(vm);
                            odDto.setTooth(tooth);
                            odDto.setTreatmentSeq(seq);
                            return odDto;
                        }
                    ).collect(toList());
                }
            ).flatMap(Collection::stream)
            .collect(toList());
    }

}
