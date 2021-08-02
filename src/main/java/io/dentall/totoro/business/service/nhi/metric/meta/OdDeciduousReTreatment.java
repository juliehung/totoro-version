package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.util.OdDto;

import java.time.Period;
import java.util.*;

import static java.time.Period.between;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;

/**
 * 往前追溯發生在特定時間區間之自家同患者同牙位乳牙有申請過OD之顆數。
 */
public class OdDeciduousReTreatment extends AbstractCalculator {

    private final String odSourceName;

    private final String odPastSourceName;

    private final int dayShiftBegin;

    private final int dayShiftEnd;

    public OdDeciduousReTreatment(Collector collector, String odSourceName, String odPastSourceName, int dayShiftBegin, int dayShiftEnd) {
        super(collector);
        this.odSourceName = odSourceName;
        this.odPastSourceName = odPastSourceName;
        this.dayShiftBegin = dayShiftBegin;
        this.dayShiftEnd = dayShiftEnd;
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<Map<Long, List<OdDto>>> odSource = collector.retrieveSource(odSourceName);
        List<Map<Long, List<OdDto>>> odPastSource = collector.retrieveSource(odPastSourceName);
        Map<Long, List<OdDto>> odSourceMap = odSource.get(0);
        Map<Long, List<OdDto>> odPastSourceMap = odPastSource.get(0);

        return odSourceMap.entrySet().stream()
            .filter(entry -> odPastSourceMap.get(entry.getKey()) != null && odPastSourceMap.get(entry.getKey()).size() > 0)
            .mapToLong(entry -> {
                Long patientId = entry.getKey();
                List<OdDto> odList = entry.getValue();
                List<OdDto> odPastList = odPastSourceMap.get(patientId);

                // 該季中如果同顆牙有多次，則取最後一次
                Map<String, Optional<OdDto>> odMap = odList.stream()
                    .collect(groupingBy(OdDto::getTooth, maxBy(comparing(OdDto::getDisposalDate))));

                Map<String, List<OdDto>> odPastMap = odPastList.stream()
                    .collect(groupingBy(OdDto::getTooth));

                return odMap.entrySet().stream()
                    .filter(entryOd -> entryOd.getValue().isPresent())
                    .filter(entryOd -> odPastMap.get(entryOd.getKey()) != null)
                    .filter(entryOd -> {
                        OdDto odDto = entryOd.getValue().get();
                        Iterator<OdDto> existReDtoItor = odPastMap.get(entryOd.getKey()).iterator();
                        boolean found = false;

                        while (existReDtoItor.hasNext() && !found) {
                            OdDto reDoDto = existReDtoItor.next();
                            Period period = between(reDoDto.getDisposalDate(), odDto.getDisposalDate());
                            if (period.getDays() >= dayShiftBegin && period.getDays() <= dayShiftEnd) {
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
        return odSourceName + "-" + odPastSourceName;
    }
}
