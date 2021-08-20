package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.util.ToothUtil;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByVM;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * Point-11 (90001C+90002C+90003C+90016C+ 90018C+90019C+ 90020C) tooth
 */
public class EndoReTreatmentByTooth extends EndoTreatment {

    public EndoReTreatmentByTooth(Collector collector, MetaConfig config, String sourceName) {
        super(collector, config, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<NhiMetricRawVM> nhiMetricRawVMList = collector.retrieveSource(sourceName());
        Exclude exclude = getExclude();

        // 先取得每個病人的每個處置的牙齒
        Map<String, List<String>> teethByPatientTreatment = nhiMetricRawVMList.stream()
            .filter(vm -> codes.contains(vm.getTreatmentProcedureCode()))
            .filter(applyExcludeByVM(exclude))
            .collect(groupingBy(vm -> vm.getPatientId() + vm.getTreatmentProcedureCode()))
            .entrySet().stream()
            .reduce(new HashMap<>(), (map, entry) -> {

                map.compute(entry.getKey(), (key, list) -> {
                    list = ofNullable(list).orElse(new ArrayList<>());

                    List<String> teeth = entry.getValue().stream()
                        .map(NhiMetricRawVM::getTreatmentProcedureTooth)
                        .filter(StringUtils::isNotBlank)
                        .map(ToothUtil::splitA74)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());

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

    @Override
    public MetaType metaType() {
        return MetaType.EndoReTreatmentByTooth;
    }
}
