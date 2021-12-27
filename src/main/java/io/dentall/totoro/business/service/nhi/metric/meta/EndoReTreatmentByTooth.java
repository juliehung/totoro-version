package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.*;

/**
 * Point-11 (90001C+90002C+90003C+90016C+ 90018C+90019C+ 90020C) tooth
 */
public class EndoReTreatmentByTooth extends EndoTreatment {

    public EndoReTreatmentByTooth(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<MetricTooth> source = metricConfig.retrieveSource(source().key());

        // 先取得每個病人的每個處置的牙齒
        Map<String, List<String>> teethByPatientTreatment = source.stream()
            .filter(vm -> codes.contains(vm.getTreatmentProcedureCode()))
            .collect(groupingBy(vm -> vm.getPatientId() + vm.getTreatmentProcedureCode()))
            .entrySet().stream()
            .reduce(new HashMap<>(), (map, entry) -> {

                map.compute(entry.getKey(), (key, list) -> {
                    list = ofNullable(list).orElse(new ArrayList<>());

                    List<String> teeth = entry.getValue().stream()
                        .map(MetricTooth::getTooth)
                        .filter(StringUtils::isNotBlank)
                        .collect(toList());

                    list.addAll(teeth);
                    return list;
                });

                return map;
            }, (accMap, map) -> {
                accMap.putAll(map);
                return accMap;
            });

        // 計算每個病人的每個處置中同一個牙齒治過超過1次的不重複數量
        return teethByPatientTreatment.values().stream()
            .mapToLong(teeth -> teeth.stream()
                .collect(groupingBy(tooth -> tooth, counting()))
                .entrySet().stream()
                .filter(counting -> counting.getValue() > 1)
                .count())
            .sum();
    }

}