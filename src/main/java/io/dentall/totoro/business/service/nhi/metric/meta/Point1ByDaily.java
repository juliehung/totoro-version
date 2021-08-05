package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 診療費
 */
public class Point1ByDaily extends SingleSourceCalculator<Map<LocalDate, Long>> {

    public Point1ByDaily(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    @Override
    public Map<LocalDate, Long> doCalculate(Collector collector) {
        Exam1ByDaily exam1 = new Exam1ByDaily(collector, sourceName()).apply();
        Exam2ByDaily exam2 = new Exam2ByDaily(collector, sourceName()).apply();
        Exam3ByDaily exam3 = new Exam3ByDaily(collector, sourceName()).apply();
        Exam4ByDaily exam4 = new Exam4ByDaily(collector, sourceName()).apply();
        Point3ByDaily point3 = new Point3ByDaily(collector, sourceName()).apply();

        Map<LocalDate, Long> exam1Map = exam1.getResult();
        Map<LocalDate, Long> exam2Map = exam2.getResult();
        Map<LocalDate, Long> exam3Map = exam3.getResult();
        Map<LocalDate, Long> exam4Map = exam4.getResult();
        Map<LocalDate, Long> point3Map = point3.getResult();
        Set<LocalDate> dates = exam1Map.keySet();

        return dates.stream().reduce(new HashMap<>(),
            (map, date) -> {
                Long exam1Points = exam1Map.get(date);
                Long exam2Points = exam2Map.get(date);
                Long exam3Points = exam3Map.get(date);
                Long exam4Points = exam4Map.get(date);
                Long point3Points = point3Map.get(date);
                map.put(date, point3Points + exam1Points + exam2Points + exam3Points + exam4Points);
                return map;
            },
            (map1, map2) -> {
                map1.putAll(map2);
                return map1;
            });
    }

    @Override
    public MetaType metaType() {
        return MetaType.Point1ByDaily;
    }

}
