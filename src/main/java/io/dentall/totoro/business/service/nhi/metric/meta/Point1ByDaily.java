package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.domain.Holiday;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.isHoliday;

/**
 * 診療費
 */
public class Point1ByDaily extends SingleSourceMetaCalculator<Map<LocalDate, Long>> {

    public Point1ByDaily(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Point1ByDaily(MetricConfig metricConfig, MetaConfig metaConfig, Source<?, ?> source) {
        super(metricConfig, metaConfig, source);
    }

    @Override
    public Map<LocalDate, Long> doCalculate(MetricConfig metricConfig) {
        MetaConfig config = getConfig();
        Exam1ByDaily exam1 = new Exam1ByDaily(metricConfig, config, source()).apply();
        Exam2ByDaily exam2 = new Exam2ByDaily(metricConfig, config, source()).apply();
        Exam3ByDaily exam3 = new Exam3ByDaily(metricConfig, config, source()).apply();
        Exam4ByDaily exam4 = new Exam4ByDaily(metricConfig, config, source()).apply();
        Point3ByDaily point3 = new Point3ByDaily(metricConfig, config, source()).apply();

        Map<LocalDate, Long> exam1Map = exam1.getResult();
        Map<LocalDate, Long> exam2Map = exam2.getResult();
        Map<LocalDate, Long> exam3Map = exam3.getResult();
        Map<LocalDate, Long> exam4Map = exam4.getResult();
        Map<LocalDate, Long> point3Map = point3.getResult();
        Set<LocalDate> dates = exam1Map.keySet();

        Map<LocalDate, Optional<Holiday>> holidayMap = metricConfig.getHolidayMap();
        boolean exclude20000Point1ByDay = config.isExclude20000Point1ByDay();

        return dates.stream().reduce(new HashMap<>(),
            (map, date) -> {
                Long exam1Points = exam1Map.get(date);
                Long exam2Points = exam2Map.get(date);
                Long exam3Points = exam3Map.get(date);
                Long exam4Points = exam4Map.get(date);
                Long point3Points = point3Map.get(date);
                long totalPoints = point3Points + exam1Points + exam2Points + exam3Points + exam4Points;

                if (exclude20000Point1ByDay && totalPoints > 0 && isHoliday(date, holidayMap)) {
                    if (totalPoints <= 20000L) {
                        totalPoints = 0L;
                    } else {
                        totalPoints -= 20000L;
                    }
                }

                map.put(date, totalPoints);
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
