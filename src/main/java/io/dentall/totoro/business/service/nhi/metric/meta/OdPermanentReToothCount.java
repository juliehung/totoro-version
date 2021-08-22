package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.*;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByDto;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;

/**
 * 往前追溯發生在特定時間區間之自家同患者同牙位恆牙有申請過OD之顆數。
 */
public class OdPermanentReToothCount extends AbstractMetaCalculator<Long> {

    private final Source<?, ?> odSource;

    private final Source<?, ?> odPastSource;

    private final int dayShiftBegin;

    private final int dayShiftEnd;

    public OdPermanentReToothCount(MetricConfig metricConfig, Source<?, ?> odSource, Source<?, ?> odPastSource, int dayShiftBegin, int dayShiftEnd) {
        this(metricConfig, null, odSource, odPastSource, dayShiftBegin, dayShiftEnd);
    }

    public OdPermanentReToothCount(MetricConfig metricConfig, MetaConfig config, Source<?, ?> odSource, Source<?, ?> odPastSource, int dayShiftBegin, int dayShiftEnd) {
        super(metricConfig, config, new Source[]{odSource, odPastSource});
        this.odSource = odSource;
        this.odPastSource = odPastSource;
        this.dayShiftBegin = dayShiftBegin;
        this.dayShiftEnd = dayShiftEnd;
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<Map<Long, Map<String, List<OdDto>>>> odSource = metricConfig.retrieveSource(this.odSource.key());
        List<Map<Long, Map<String, List<OdDto>>>> odPastSource = metricConfig.retrieveSource(this.odPastSource.key());
        Map<Long, Map<String, List<OdDto>>> odSourceMap = odSource.get(0);
        Map<Long, Map<String, List<OdDto>>> odPastSourceMap = odPastSource.get(0);
        Exclude exclude = getExclude();

        return odSourceMap.entrySet().stream()
            .filter(entry -> odPastSourceMap.get(entry.getKey()) != null && odPastSourceMap.get(entry.getKey()).size() > 0)
            .mapToLong(entry -> {
                Long patientId = entry.getKey();
                Map<String, List<OdDto>> odToothMap = entry.getValue();
                Map<String, List<OdDto>> odToothPastMap = odPastSourceMap.get(patientId);

                // 該季中如果同顆牙有多次，則取最後一次
                Map<String, Optional<OdDto>> odMap = odToothMap.values().stream()
                    .flatMap(Collection::stream)
                    .filter(applyExcludeByDto(exclude))
                    .collect(groupingBy(OdDto::getTooth, maxBy(comparing(OdDto::getDisposalDate))));

                return odMap.entrySet().stream()
                    .filter(entryOd -> entryOd.getValue().isPresent())
                    .filter(entryOd -> odToothPastMap.get(entryOd.getKey()) != null)
                    .filter(entryOd -> {
                        OdDto odDto = entryOd.getValue().get();
                        Iterator<OdDto> existReDtoItor = odToothPastMap.get(entryOd.getKey()).stream()
                            .filter(applyExcludeByDto(exclude))
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
        return MetaType.OdPermanentReToothCount;
    }

}
