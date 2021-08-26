package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByVM;
import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyNewTreatmentPoint;
import static io.dentall.totoro.business.service.nhi.util.NhiProcedureUtil.isExaminationCodeAtSalary;

/**
 * 診療費
 */
public class Point3ByDaily extends SingleSourceMetaCalculator<Map<LocalDate, Long>> {

    public Point3ByDaily(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Point3ByDaily(MetricConfig metricConfig, MetaConfig metaConfig, Source<?, ?> source) {
        super(metricConfig, metaConfig, source);
    }

    @Override
    public Map<LocalDate, Long> doCalculate(MetricConfig metricConfig) {
        List<Map<LocalDate, List<NhiMetricRawVM>>> source = metricConfig.retrieveSource(source().key());
        MetaConfig config = getConfig();
        Exclude exclude = getExclude();

        return source.get(0).entrySet().stream().reduce(new HashMap<>(),
            (map, entry) -> {
                LocalDate date = entry.getKey();
                List<NhiMetricRawVM> sourceByDate = entry.getValue();
                Long points = sourceByDate.stream()
                    .filter(vm -> !isExaminationCodeAtSalary(vm.getTreatmentProcedureCode()))
                    .filter(applyExcludeByVM(exclude))
                    .map(vm -> applyNewTreatmentPoint(vm, config))
                    .filter(Objects::nonNull)
                    .reduce(Long::sum)
                    .orElse(0L);
                map.put(date, points);
                return map;
            },
            (map1, map2) -> {
                map1.putAll(map2);
                return map1;
            });
    }

    @Override
    public MetaType metaType() {
        return MetaType.Point3ByDaily;
    }

}
