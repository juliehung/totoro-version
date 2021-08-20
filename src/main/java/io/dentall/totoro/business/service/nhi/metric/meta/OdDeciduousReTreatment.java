package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.source.Collector;

import java.util.*;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByDto;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;

/**
 * 往前追溯發生在特定時間區間之自家同患者同牙位乳牙有申請過OD之顆數。
 */
public class OdDeciduousReTreatment extends AbstractCalculator<Long> {

    private final String odSourceName;

    private final String odPastSourceName;

    private final int dayShiftBegin;

    private final int dayShiftEnd;

    public OdDeciduousReTreatment(Collector collector, String odSourceName, String odPastSourceName, int dayShiftBegin, int dayShiftEnd) {
        this(collector, null, odSourceName, odPastSourceName, dayShiftBegin, dayShiftEnd);
    }

    public OdDeciduousReTreatment(Collector collector, MetaConfig config, String odSourceName, String odPastSourceName, int dayShiftBegin, int dayShiftEnd) {
        super(collector, config);
        this.odSourceName = odSourceName;
        this.odPastSourceName = odPastSourceName;
        this.dayShiftBegin = dayShiftBegin;
        this.dayShiftEnd = dayShiftEnd;
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<Map<Long, Map<String, List<OdDto>>>> odSource = collector.retrieveSource(odSourceName);
        List<Map<Long, Map<String, List<OdDto>>>> odPastSource = collector.retrieveSource(odPastSourceName);
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
        return MetaType.OdDeciduousReTreatment;
    }

    @Override
    public String sourceName() {
        return odSourceName + "+" + odPastSourceName;
    }
}
