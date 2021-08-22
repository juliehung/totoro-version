package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Point2ByDaily extends SingleSourceMetaCalculator<Map<LocalDate, Long>> {


    public Point2ByDaily(MetricConfig metricConfig, Source<?, ?> source) {
        super(metricConfig, source);
    }

    @Override
    public Map<LocalDate, Long> doCalculate(MetricConfig metricConfig) {
        Point1ByDaily point1 = new Point1ByDaily(metricConfig, source()).apply();
        Point4ByDaily point4 = new Point4ByDaily(metricConfig, source()).apply();
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

    @Override
    public MetaType metaType() {
        return MetaType.Point2ByDaily;
    }
}
