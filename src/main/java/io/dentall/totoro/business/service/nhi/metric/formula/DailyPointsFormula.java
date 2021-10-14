package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Point2ByDaily;
import io.dentall.totoro.business.service.nhi.metric.source.DailyByMonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static java.util.Comparator.naturalOrder;
import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toList;

/**
 * 健保每日申請點數
 */
public class DailyPointsFormula extends AbstractFormula<List<Entry<LocalDate, BigDecimal>>> {

    private final Source<MetricTooth, Map<LocalDate, List<MetricTooth>>> source;

    public DailyPointsFormula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new DailyByMonthSelectedSource(metricConfig);
    }

    @Override
    public List<Entry<LocalDate, BigDecimal>> doCalculate(MetricConfig metricConfig) {
        Point2ByDaily point2 = new Point2ByDaily(metricConfig, source).apply();
        Map<LocalDate, BigDecimal> result = point2.getResult().entrySet().stream().reduce(new HashMap<>(),
            (map, entry) -> {
                map.put(entry.getKey(), new BigDecimal(entry.getValue()));
                return map;
            },
            (map1, map2) -> {
                map1.putAll(map2);
                return map1;
            });

        return result.entrySet().stream().sorted(comparingByKey(naturalOrder())).collect(toList());
    }
}
