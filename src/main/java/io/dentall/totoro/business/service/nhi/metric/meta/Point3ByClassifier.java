package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyNewTreatmentPoint;
import static io.dentall.totoro.business.service.nhi.util.NhiProcedureUtil.isExaminationCodeAtSalary;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingBy;

/**
 * 診療費 病患點數  or 醫師點數
 */
public class Point3ByClassifier extends SingleSourceMetaCalculator<Map<Long, Long>> {

    private final Function<NhiMetricRawVM, Long> classifier;

    private final MetaType metaType;

    public Point3ByClassifier(MetricConfig metricConfig, MetaType metaType, Source<?, ?> source, Function<NhiMetricRawVM, Long> classifier) {
        this(metricConfig, null, metaType, source, classifier);
    }

    public Point3ByClassifier(MetricConfig metricConfig, MetaConfig metaConfig, MetaType metaType, Source<?, ?> source, Function<NhiMetricRawVM, Long> classifier) {
        super(metricConfig, metaConfig, source);
        this.classifier = classifier;
        this.metaType = metaType;
    }

    @Override
    public Map<Long, Long> doCalculate(MetricConfig metricConfig) {
        List<NhiMetricRawVM> nhiMetricRawVMList = metricConfig.retrieveSource(source().key());
        MetaConfig config = getConfig();

        return nhiMetricRawVMList.stream()
            .collect(groupingBy(classifier))
            .entrySet().stream()
            .reduce(new HashMap<>(),
                (map, entry) -> {
                    long keyId = entry.getKey();

                    map.compute(keyId, (key, point) -> {
                        long points = entry.getValue().stream()
                            .filter(vm -> !isExaminationCodeAtSalary(vm.getTreatmentProcedureCode()))
                            .mapToLong(vm -> applyNewTreatmentPoint(vm, config))
                            .sum();

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
