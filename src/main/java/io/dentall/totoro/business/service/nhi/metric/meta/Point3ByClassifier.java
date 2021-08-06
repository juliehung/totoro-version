package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static java.util.Comparator.naturalOrder;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingBy;

/**
 * 診療費 病患點數  or 醫師點數
 */
public class Point3ByClassifier extends SingleSourceCalculator<Map<Long, Long>> {

    private final Function<NhiMetricRawVM, Long> classifier;

    private final MetaType metaType;

    public Point3ByClassifier(Collector collector, MetaType metaType, String sourceName, Function<NhiMetricRawVM, Long> classifier) {
        super(collector, sourceName);
        this.classifier = classifier;
        this.metaType = metaType;
    }

    @Override
    public Map<Long, Long> doCalculate(Collector collector) {
        List<NhiMetricRawVM> nhiMetricRawVMList = collector.retrieveSource(sourceName());

        return nhiMetricRawVMList.stream()
            .collect(groupingBy(classifier))
            .entrySet().stream()
            .reduce(new HashMap<>(),
                (map, entry) -> {
                    long keyId = entry.getKey();

                    map.compute(keyId, (key, point) -> {
                        long points = entry.getValue().stream()
                            .map(NhiMetricRawVM::getTreatmentProcedureTotal)
                            .filter(Objects::nonNull)
                            .reduce(Long::sum)
                            .orElse(0L);
                        return ofNullable(point).orElse(0L) + points;
                    });

                    return map;
                },
                (accMap, map) -> {
                    accMap.putAll(map);
                    return accMap;
                });
    }

    @Override
    public MetaType metaType() {
        return metaType;
    }

}
