package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.calculatePt;

/**
 * 每天看診人數/就醫人數 不重複病患數量
 */
public class Pt1ByDaily extends SingleSourceMetaCalculator<Map<LocalDate, Long>> {

    public Pt1ByDaily(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Pt1ByDaily(MetricConfig metricConfig, MetaConfig metaConfig, Source<?, ?> source) {
        super(metricConfig, metaConfig, source);
    }

    @Override
    public Map<LocalDate, Long> doCalculate(MetricConfig metricConfig) {
        List<Map<LocalDate, List<MetricTooth>>> source = metricConfig.retrieveSource(source().key());

        return source.get(0).entrySet().stream().reduce(new HashMap<>(),
            (map, entry) -> {
                LocalDate date = entry.getKey();
                List<MetricTooth> sourceByDate = entry.getValue();
                Long value = sourceByDate.stream()
                    .reduce(0L, calculatePt(), Long::sum);
                map.put(date, value);
                return map;
            },
            (map1, map2) -> {
                map1.putAll(map2);
                return map1;
            });
    }

}
