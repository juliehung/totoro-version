package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Comparator.naturalOrder;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingBy;

/**
 * 診療費 病患點數(最高者)
 */
public class HighestPoint3ByPatient extends SingleSourceCalculator {

    public HighestPoint3ByPatient(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<NhiMetricRawVM> nhiMetricRawVMList = collector.retrieveSource(sourceName());

        Map<Long, Long> pointByPatient = nhiMetricRawVMList.stream()
            .collect(groupingBy(NhiMetricRawVM::getPatientId))
            .entrySet().stream()
            .reduce(new HashMap<>(), (map, entry) -> {
                long patientId = entry.getKey();

                map.compute(patientId, (key, point) -> {
                    long points = entry.getValue().stream()
                        .map(NhiMetricRawVM::getTreatmentProcedureTotal)
                        .filter(Objects::nonNull)
                        .reduce(Long::sum)
                        .orElse(0L);
                    return ofNullable(point).orElse(0L) + points;
                });

                return map;
            }, (accMap, map) -> {
                accMap.putAll(map);
                return accMap;
            });

        return pointByPatient.values().stream().max(naturalOrder()).orElse(0L);
    }

    @Override
    public MetaType metaType() {
        return MetaType.HighestPoint3ByPatient;
    }

}
