package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;

/**
 * 往前追溯發生在特定時間區間之自家同患者同牙位有申請過OD或ENDO之顆數。
 */
public class EndoAndOdToothCount extends AbstractMetaCalculator<Long> {

    private final Source<?, ?> extSource;

    private final Source<?, ?> extPastSource;

    private final int dayShiftBegin;

    private final int dayShiftEnd;

    public EndoAndOdToothCount(MetricConfig metricConfig, Source<?, ?> extSource, Source<?, ?> extPastSource, int dayShiftBegin, int dayShiftEnd) {
        this(metricConfig, null, extSource, extPastSource, dayShiftBegin, dayShiftEnd);
    }

    public EndoAndOdToothCount(MetricConfig metricConfig, MetaConfig config, Source<?, ?> extSource, Source<?, ?> extPastSource, int dayShiftBegin, int dayShiftEnd) {
        super(metricConfig, config, new Source[]{extSource, extPastSource});
        this.extSource = extSource;
        this.extPastSource = extPastSource;
        this.dayShiftBegin = dayShiftBegin;
        this.dayShiftEnd = dayShiftEnd;
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<Map<Long, Map<String, List<OdDto>>>> source = metricConfig.retrieveSource(this.extSource.key());
        List<Map<Long, Map<String, List<OdDto>>>> pastSource = metricConfig.retrieveSource(this.extPastSource.key());
        Map<Long, Map<String, List<OdDto>>> sourceMap = source.get(0);
        Map<Long, Map<String, List<OdDto>>> pastSourceMap = pastSource.get(0);

        return sourceMap.entrySet().stream()
            .filter(entry -> pastSourceMap.get(entry.getKey()) != null && pastSourceMap.get(entry.getKey()).size() > 0)
            .mapToLong(entry -> {
                Long patientId = entry.getKey();
                Map<String, List<OdDto>> toothMap = entry.getValue();
                Map<String, List<OdDto>> toothPastMap = pastSourceMap.get(patientId);

                // 該季中如果同顆牙有多次，則取最後一次
                Map<String, Optional<OdDto>> lastToothDisposal = toothMap.values().stream()
                    .flatMap(Collection::stream)
                    .collect(groupingBy(OdDto::getTreatmentProcedureTooth, maxBy(comparing(OdDto::getDisposalDate))));

                return lastToothDisposal.entrySet().stream()
                    .filter(toothDisposal -> toothDisposal.getValue().isPresent())
                    .filter(toothDisposal -> toothPastMap.get(toothDisposal.getKey()) != null)
                    .filter(toothDisposal -> {
                        OdDto odDto = toothDisposal.getValue().get();
                        Iterator<OdDto> existReDtoItor = toothPastMap.get(toothDisposal.getKey()).stream()
                            .iterator();
                        boolean found = false;

                        while (existReDtoItor.hasNext() && !found) {
                            OdDto reDoDto = existReDtoItor.next();
                            long days = DAYS.between(reDoDto.getDisposalDate(), odDto.getDisposalDate());
                            if (days >= dayShiftBegin && days <= dayShiftEnd) {
                                found = true;
                            }
                        }

                        return found;
                    }).count();
            }).sum();
    }

    @Override
    public MetaType metaType() {
        return MetaType.EndoAndOdToothCount;
    }

}
