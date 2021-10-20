package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部分負擔點數
 */
public class Point4ByDaily extends SingleSourceMetaCalculator<Map<LocalDate, Long>> {

    public Point4ByDaily(MetricConfig metricConfig, Source<MetricDisposal, Map<LocalDate, List<MetricDisposal>>> source) {
        this(metricConfig, null, source);
    }

    public Point4ByDaily(MetricConfig metricConfig, MetaConfig metaConfig, Source<MetricDisposal, Map<LocalDate, List<MetricDisposal>>> source) {
        super(metricConfig, metaConfig, source);
    }

    @Override
    public Map<LocalDate, Long> doCalculate(MetricConfig metricConfig) {
        List<Map<LocalDate, List<MetricDisposal>>> source = metricConfig.retrieveSource(source().key());

        return source.get(0).entrySet().stream().reduce(new HashMap<>(),
            (map, entry) -> {
                LocalDate date = entry.getKey();
                List<MetricDisposal> sourceByDate = entry.getValue();
                Long points = sourceByDate.stream()
                    .map(MetricDisposal::getPartialBurden)
                    .filter(StringUtils::isNotBlank)
                    .map(Long::valueOf)
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

}
