package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.*;

import static java.lang.Long.valueOf;
import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * 診療費 病患點數(最高者)
 */
public class HighestPoint3ByPatient extends AbstractCalculator {


    public HighestPoint3ByPatient(String sourceName) {
        super(sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<NhiMetricRawVM> nhiMetricRawVMList = collector.retrieveSource(sourceName());

        Map<Long, Long> pointByPatient = nhiMetricRawVMList.stream()
            .collect(groupingBy(NhiMetricRawVM::getPatientId))
            .entrySet().stream()
            .reduce(new HashMap<Long, Long>(), (map, entry) -> {
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
