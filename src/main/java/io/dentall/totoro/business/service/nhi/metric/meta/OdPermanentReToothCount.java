package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.*;

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
        List<Map<Long, Map<String, List<MetricTooth>>>> odSource = metricConfig.retrieveSource(this.odSource.key());
        List<Map<Long, Map<String, List<MetricTooth>>>> odPastSource = metricConfig.retrieveSource(this.odPastSource.key());
        Map<Long, Map<String, List<MetricTooth>>> odSourceMap = odSource.get(0);
        Map<Long, Map<String, List<MetricTooth>>> odPastSourceMap = odPastSource.get(0);

        return odSourceMap.entrySet().stream()
            .filter(entry -> odPastSourceMap.get(entry.getKey()) != null && odPastSourceMap.get(entry.getKey()).size() > 0)
            .mapToLong(entry -> {
                Long patientId = entry.getKey();
                Map<String, List<MetricTooth>> odToothMap = entry.getValue();
                Map<String, List<MetricTooth>> odToothPastMap = odPastSourceMap.get(patientId);

                // 該季中如果同顆牙有多次，則取最後一次
                Map<String, Optional<MetricTooth>> odMap = odToothMap.values().stream()
                    .flatMap(Collection::stream)
                    .collect(groupingBy(MetricTooth::getTooth, maxBy(comparing(MetricTooth::getDisposalDate))));

                return odMap.entrySet().stream()
                    .filter(entryOd -> entryOd.getValue().isPresent())
                    .filter(entryOd -> odToothPastMap.get(entryOd.getKey()) != null)
                    .filter(entryOd -> {
                        MetricTooth metricTooth = entryOd.getValue().get();
                        Iterator<MetricTooth> existReDtoItor = odToothPastMap.get(entryOd.getKey()).stream().iterator();
                        boolean found = false;

                        while (existReDtoItor.hasNext() && !found) {
                            MetricTooth reDoDto = existReDtoItor.next();
                            long days = DAYS.between(reDoDto.getDisposalDate(), metricTooth.getDisposalDate());
                            if (days >= dayShiftBegin && days <= dayShiftEnd) {
                                found = true;
                            }
                        }

                        return found;
                    }).count();
            }).sum();
    }

}
