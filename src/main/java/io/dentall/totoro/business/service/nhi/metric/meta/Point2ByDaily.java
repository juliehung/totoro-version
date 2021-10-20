package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Point2ByDaily extends AbstractMetaCalculator<Map<LocalDate, Long>> {

    private final Source<MetricTooth, Map<LocalDate, List<MetricTooth>>> source;

    private final Source<MetricDisposal, Map<LocalDate, List<MetricDisposal>>> disposalSource;

    public Point2ByDaily(
        MetricConfig metricConfig,
        Source<MetricTooth, Map<LocalDate, List<MetricTooth>>> source,
        Source<MetricDisposal, Map<LocalDate, List<MetricDisposal>>> disposalSource) {
        this(metricConfig, null, source, disposalSource);
    }

    public Point2ByDaily(
        MetricConfig metricConfig,
        MetaConfig metaConfig,
        Source<MetricTooth, Map<LocalDate, List<MetricTooth>>> source,
        Source<MetricDisposal, Map<LocalDate, List<MetricDisposal>>> disposalSource) {
        super(metricConfig, metaConfig, new Source[]{source, disposalSource});
        this.source = source;
        this.disposalSource = disposalSource;
    }

    @Override
    public Map<LocalDate, Long> doCalculate(MetricConfig metricConfig) {
        Point1ByDaily point1 = new Point1ByDaily(metricConfig, source, disposalSource).apply();
        Point4ByDaily point4 = new Point4ByDaily(metricConfig, disposalSource).apply();
        Map<LocalDate, Long> point1Map = point1.getResult();
        Map<LocalDate, Long> point4Map = point4.getResult();

        Set<LocalDate> dates = point1Map.keySet();
        return dates.stream().reduce(new HashMap<>(),
            (map, date) -> {
                Long point1Points = point1Map.get(date);
                Long point4Points = point4Map.get(date);
                map.put(date, point1Points - point4Points);
                return map;
            },
            (map1, map2) -> {
                map1.putAll(map2);
                return map1;
            });
    }

}
